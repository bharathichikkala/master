package com.mss.solar.optaplanner.svsc.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
import com.mss.solar.optaplanner.model.CustomerDetails;
import com.mss.solar.optaplanner.model.Message;
import com.mss.solar.optaplanner.model.VehicleRouteDetails;
import com.mss.solar.optaplanner.model.VehicleRoutingSolutionDetails;
import com.mss.solar.optaplanner.svsc.VehicleRoutingByDistanceService;

@RestController
@Validated
public class VehicleRoutingByDistanceServiceImpl implements VehicleRoutingByDistanceService {

	private static final Logger log = Logger.getLogger(VehicleRoutingByDistanceServiceImpl.class);

	private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

	@Value("${googleApiKey}")
	private String googleApiKey;

	@Autowired
	private VehicleRoutingSolverByDistanceServiceImpl solverManager;

	@Autowired
	private VrpGeneratorServiceImpl vrpGeneratorService;


	@Override
	public VehicleRoutingSolutionDetails getSolutionByDistance(@PathVariable String loadNumber) {
		log.info("getting solution for vehicle routing");
		VehicleRoutingSolution solution = solverManager.retrieveOrCreateSolutionByDistance(loadNumber);
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
		jsonSolution.setDistance(solution.getDistanceString(NUMBER_FORMAT));
		return jsonSolution;
	}

	@Override
	public Message solveByDistance(@PathVariable String loadNumber) {
		log.info("solving vehicle routing");
		boolean success = solverManager.solveByDistance(loadNumber);
		return new Message(success ? "Solving started." : "Solver was already running.");
	}

	@Override
	public Message terminateEarlyByDistance(@PathVariable String loadNumber) {
		log.info("terminating vehicle routing solving process");
		boolean success = solverManager.terminateEarlyByDistance(loadNumber);
		return new Message(success ? "Solver terminating early." : "Solver was already terminated.");
	}

	@Override
	public VehicleRoutingSolutionDetails getTimeByDistance(@PathVariable String loadNumber) {

		log.info("calculating time for distance");
		VehicleRoutingSolutionDetails distanceResult = getSolutionByDistance(loadNumber);
		try {

			List<VehicleRouteDetails> vehicleRouteDetails = distanceResult.getVehicleRouteList();
			VehicleRouteDetails routeDetails = vehicleRouteDetails.get(0);

			double depotLatitude = routeDetails.getDepotLatitude();
			double depotLongitude = routeDetails.getDepotLongitude();

			List<CustomerDetails> customerDetails = routeDetails.getCustomerList();

			Long runningTime = 0l;

			double originLatitude = depotLatitude;
			double originLongitude = depotLongitude;

			for (CustomerDetails skid : customerDetails) {
				/*long time = calculateTimeByDistance(originLatitude, originLongitude, skid.getLatitude(),
						skid.getLongitude());
*/
				long time = vrpGeneratorService.calculateTime(originLatitude, originLongitude, skid.getLatitude(),
						skid.getLongitude());
				runningTime = runningTime + time;
				originLatitude = skid.getLatitude();
				originLongitude = skid.getLongitude();

			}
			long returnTime = calculateTimeByDistance(originLatitude, originLongitude, depotLatitude,
					depotLongitude);
			Long totalTime =runningTime+returnTime;
			
			long hours = totalTime / 3600;
			long minutes = (totalTime % 3600) / 60;
			long seconds = totalTime % 60;

			String resultTime = hours + "h " + minutes + "m " + seconds + "s " + "0" + "ms";

			distanceResult.setTime(resultTime);

		} catch (Exception exception) {
			log.error("Unable to get the time for distance ", exception);
		}

		return distanceResult;
	}

	public Long calculateTimeByDistance(double fromLat, double fromLong, double toLat, double toLong) {
		log.debug("time calculation");
		String uriResponse = null;
		Long minimumTime = null;
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
					
					DistanceTime value =new DistanceTime();
					
					value.setDistance(distance);
					value.setTime(duration);
					
					distanceTime.add(value);
				}
			}
			
			List<DistanceTime> result = distanceTime.stream()
					.sorted((o1, o2) -> Double.valueOf(o1.getDistance()).compareTo(Double.valueOf(o2.getDistance())))
					.collect(Collectors.toList());
			minimumTime =  result.get(0).getTime();
		} catch (Exception exception) {
			log.error("Unable to find the Distance ", exception);
		}
		return minimumTime;

	}

}