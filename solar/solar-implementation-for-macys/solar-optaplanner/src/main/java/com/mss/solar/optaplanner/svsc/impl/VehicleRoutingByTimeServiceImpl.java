package com.mss.solar.optaplanner.svsc.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.examples.vehiclerouting.domain.Customer;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.location.Location;
import org.optaplanner.swing.impl.TangoColorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mss.solar.optaplanner.domain.DistanceTime;
import com.mss.solar.optaplanner.domain.LoadDetails;
import com.mss.solar.optaplanner.domain.SkidDrops;
import com.mss.solar.optaplanner.domain.VehicleRoutingLocations;
import com.mss.solar.optaplanner.model.CustomerDetails;
import com.mss.solar.optaplanner.model.Message;
import com.mss.solar.optaplanner.model.VehicleRouteDetails;
import com.mss.solar.optaplanner.model.VehicleRoutingSolutionDetails;
import com.mss.solar.optaplanner.repos.LoadDetailsRepository;
import com.mss.solar.optaplanner.svsc.VehicleRoutingByTimeService;

@RestController
@Validated
public class VehicleRoutingByTimeServiceImpl implements VehicleRoutingByTimeService {

	private static final Logger log = Logger.getLogger(VehicleRoutingByTimeServiceImpl.class);

	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

	@Autowired
	private LoadDetailsRepository loadDetailsRepo;

	@Autowired
	private VrpGeneratorServiceImpl vrpGeneratorService;

	@Autowired
	private VehicleRoutingSolverByTimeServiceImpl solverManager;

	@Autowired
	private VehicleRoutingByDistanceServiceImpl vehicleRoutingByDistanceService;

	@Value("${googleApiKey}")
	private String googleApiKey;

	@Override
	public VehicleRoutingSolutionDetails getSolutionByTime(@PathVariable String loadNumber) {
		log.info("getting solution for vehicle routing");
		VehicleRoutingSolution solution = solverManager.retrieveOrCreateSolutionByTime(loadNumber);
		return convertToVehicleRoutingSolution(solution);
	}

	protected VehicleRoutingSolutionDetails convertToVehicleRoutingSolution(VehicleRoutingSolution solution) {
		VehicleRoutingSolutionDetails jsonSolution = new VehicleRoutingSolutionDetails();
		jsonSolution.setName(solution.getName());
		List<CustomerDetails> jsonCustomerList = new ArrayList<>(solution.getCustomerList().size());
		for (Customer customer : solution.getCustomerList()) {
			Location customerLocation = customer.getLocation();
			jsonCustomerList.add(new CustomerDetails(customerLocation.getName(), customerLocation.getLatitude(),
					customerLocation.getLongitude(), customer.getDemand()));
		}
		jsonSolution.setCustomerList(jsonCustomerList);
		List<VehicleRouteDetails> jsonVehicleRouteList = new ArrayList<>(solution.getVehicleList().size());
		TangoColorFactory tangoColorFactory = new TangoColorFactory();
		for (Vehicle vehicle : solution.getVehicleList()) {
			VehicleRouteDetails jsonVehicleRoute = new VehicleRouteDetails();
			Location depotLocation = vehicle.getDepot().getLocation();
			jsonVehicleRoute.setDepotLocationName(depotLocation.getName());
			jsonVehicleRoute.setDepotLatitude(depotLocation.getLatitude());
			jsonVehicleRoute.setDepotLongitude(depotLocation.getLongitude());
			jsonVehicleRoute.setCapacity(vehicle.getCapacity());
			Color color = tangoColorFactory.pickColor(vehicle);
			jsonVehicleRoute
					.setHexColor(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
			Customer customer = vehicle.getNextCustomer();
			int demandTotal = 0;
			List<CustomerDetails> jsonVehicleCustomerList = new ArrayList<>();
			while (customer != null) {
				Location customerLocation = customer.getLocation();
				demandTotal += customer.getDemand();
				jsonVehicleCustomerList.add(new CustomerDetails(customerLocation.getName(),
						customerLocation.getLatitude(), customerLocation.getLongitude(), customer.getDemand()));
				customer = customer.getNextCustomer();
			}
			jsonVehicleRoute.setDemandTotal(demandTotal);
			jsonVehicleRoute.setCustomerList(jsonVehicleCustomerList);
			jsonVehicleRouteList.add(jsonVehicleRoute);
		}
		jsonSolution.setVehicleRouteList(jsonVehicleRouteList);
		HardSoftLongScore score = solution.getScore();
		jsonSolution.setFeasible(score != null && score.isFeasible());
		jsonSolution.setTime(solution.getDistanceString(NUMBER_FORMAT));
		return jsonSolution;
	}

	@Override
	public Message solveByTime(@PathVariable String loadNumber) {
		log.info("solving vehicle routing");
		boolean success = solverManager.solveByTime(loadNumber);
		return new Message(success ? "Solving started." : "Solver was already running.");
	}

	@Override
	public Message terminateEarlyByTime(@PathVariable String loadNumber) {
		log.info("terminating vehicle routing solving process");
		boolean success = solverManager.terminateEarlyByTime(loadNumber);
		return new Message(success ? "Solver terminating early." : "Solver was already terminated.");
	}

	@Override
	public List<VehicleRoutingLocations> routingByLoadNumber(@PathVariable String loadNumber) {
		log.info("getting loadDetails for routing");

		List<VehicleRoutingLocations> routingLocations = new ArrayList<>();
		Integer id = 0;
		try {
			LoadDetails loaddetails = loadDetailsRepo.findByLoadNumber(loadNumber);

			VehicleRoutingLocations originLocations = new VehicleRoutingLocations();
			originLocations.setId(id);
			id = id + 1;
			originLocations.setName(loaddetails.getOriginLocNbr().getAddress());
			originLocations.setLatitude(loaddetails.getOriginLocNbr().getLatitude());
			originLocations.setLongitude(loaddetails.getOriginLocNbr().getLongitude());
			originLocations.setDemand(0);

			routingLocations.add(originLocations);

			Set<SkidDrops> skids = loaddetails.getSkidDrops();

			for (SkidDrops drops : skids) {

				VehicleRoutingLocations skidLocations = new VehicleRoutingLocations();
				skidLocations.setId(id);
				id = id + 1;
				skidLocations.setName(drops.getDestLocNbr().getAddress());
				skidLocations.setLatitude(drops.getDestLocNbr().getLatitude());
				skidLocations.setLongitude(drops.getDestLocNbr().getLongitude());
				skidLocations.setDemand(drops.getTotalCartons());

				routingLocations.add(skidLocations);

			}

		} catch (Exception e) {
			log.error("Failed to get routing locations ");
		}

		return routingLocations;
	}

	@Override
	public Message routingsolvingByLoadNumber(@PathVariable String loadNumber) {
		log.info("getting routing solution");

		List<VehicleRoutingLocations> routingLocations = routingByLoadNumber(loadNumber);

		vrpGeneratorService.generateVRP(routingLocations, loadNumber);

		Message solve = solveByTime(loadNumber);

		solve = vehicleRoutingByDistanceService.solveByDistance(loadNumber);

		return solve;

	}

	@Override
	public VehicleRoutingSolutionDetails getDistanceByTime(@PathVariable String loadNumber) {

		log.info("Getting distance based on time route");

		VehicleRoutingSolutionDetails timeResult = getSolutionByTime(loadNumber);
		try {

			List<VehicleRouteDetails> vehicleRouteDetails = timeResult.getVehicleRouteList();
			VehicleRouteDetails routeDetails = vehicleRouteDetails.get(0);

			double depotLatitude = routeDetails.getDepotLatitude();
			double depotLongitude = routeDetails.getDepotLongitude();

			List<CustomerDetails> customerDetails = routeDetails.getCustomerList();

			double runningDistance = 0.0;

			double originLatitude = depotLatitude;
			double originLongitude = depotLongitude;

			for (CustomerDetails skid : customerDetails) {
				/*double distance = calculateDistanceByTime(originLatitude, originLongitude, skid.getLatitude(),
						skid.getLongitude());*/
				
				double distance = vrpGeneratorService.calculateDistance(originLatitude, originLongitude, skid.getLatitude(),
						skid.getLongitude());

				runningDistance = runningDistance + distance;
				originLatitude = skid.getLatitude();
				originLongitude = skid.getLongitude();

			}
			
			
			double returnDistance = calculateDistanceByTime(originLatitude, originLongitude, depotLatitude,
					depotLongitude);
			
			double totalDistance =runningDistance+returnDistance;

			String distance = Double.toString(totalDistance);

			if (distance.contains(".")) {
				int size=distance.length();
				int index = distance.indexOf('.');

				String km = distance.substring(0, index);

				String meters = distance.substring(index+1, size);
				
				if(meters.length()>2)
				{
					meters = meters.substring(0,3);
					
					
				}else {
				int result = Integer.parseInt(meters);
				result =result*100;
				meters=Integer.toString(result);
				}
				String distanceInKm = km + "km " + meters + "m";
				timeResult.setDistance(distanceInKm);
			} else {
				String distanceInKm = distance + "km " + "0" + "m";
				timeResult.setDistance(distanceInKm);
			}

		} catch (Exception exception) {
			log.error("Unable to get the time for distance ", exception);
		}

		return timeResult;
	}

	public Double calculateDistanceByTime(double fromLat, double fromLong, double toLat, double toLong) {
		log.debug("time calculation");
		String uriResponse = null;
		double minimumDistance = 0.0;
		double distance = 0.0;
		List<DistanceTime> distanceTime = new ArrayList<>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "https://maps.googleapis.com/maps/api/directions/json?";
			uriResponse = restTemplate.getForObject(url + "origin=" + fromLat + ", " + fromLong + "&destination="
					+ toLat + ", " + toLong + "&sensor=false&mode=driving&alternatives=true&key=" + googleApiKey,
					String.class);

			JSONObject object = new JSONObject(uriResponse);

			JSONArray jsonArray = (JSONArray) object.get("routes");
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObject = (JSONObject) jsonArray.get(i);

				JSONArray childArray = (JSONArray) jsonObject.get("legs");

				for (int j = 0; j < childArray.length(); j++) {

					JSONObject jsonObject1 = (JSONObject) childArray.get(j);

					JSONObject distanceJson = (JSONObject) jsonObject1.get("distance");
					String distance1 = distanceJson.getString("text");
					if (distance1.contains("mi")) {
						distance = Double.parseDouble(distance1.substring(0, (distance1.length() - 3))) * 1.60934;// mi
																													// to
																													// km
					} else if (distance1.contains("meters")) {

						distance = Double.parseDouble(distance1.substring(0, (distance1.length() - 7))) * 0.001; // meters
																													// to
																													// km
					} else {

						distance = Double.parseDouble(distance1.substring(0, (distance1.length() - 3))); // km

					}

					JSONObject timeJson = (JSONObject) jsonObject1.get("duration");
					long duration = timeJson.getLong("value");

					DistanceTime value = new DistanceTime();

					value.setDistance(distance);
					value.setTime(duration);

					distanceTime.add(value);
				}
			}

			List<DistanceTime> result = distanceTime.stream()
					.sorted((o1, o2) -> Long.valueOf(o1.getTime()).compareTo(Long.valueOf(o2.getTime())))
					.collect(Collectors.toList());
			minimumDistance = result.get(0).getDistance();
		} catch (Exception exception) {
			log.error("Unable to find the Distance ", exception);
		}
		return minimumDistance;

	}

}
