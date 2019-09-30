package com.mss.solar.optaplanner.svsc.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mss.solar.optaplanner.common.DistanceType;
import com.mss.solar.optaplanner.common.VrpType;
import com.mss.solar.optaplanner.domain.VehicleRoutingLocations;
import com.mss.solar.optaplanner.svsc.VrpGeneratorService;

@RestController
@Validated
public class VrpGeneratorServiceImpl implements VrpGeneratorService {

	private static final Logger log = Logger.getLogger(VrpGeneratorServiceImpl.class);

	@Value("${googleApiKey}")
	private String googleApiKey;

	@Value("${vrp.url}")
	private String vrpFile;

	@Override
	public String generateVRP(@RequestBody List<VehicleRoutingLocations> routingLocations,
			@PathVariable String loadNumber) {

		log.info("Generating vrp file");

		int locationSize = routingLocations.size();

		generateVRP(routingLocations, locationSize, 1, 1, 100, DistanceType.ROAD_DISTANCE_KM, VrpType.BASIC,
				loadNumber);

		generateVRP(routingLocations, locationSize, 1, 1, 100, DistanceType.ROAD_DISTANCE_TIME, VrpType.BASIC,
				loadNumber);

		return "vrp generated successfully";
	}

	public String generateVRP(List<VehicleRoutingLocations> locationList, int locationListSize, int depotListSize,
			int vehicleListSize, int capacity, DistanceType distanceType, VrpType vrpType, String loadNumber) {

		String name = loadNumber + distanceType.getFileSuffix() + vrpType.getFileSuffix()
				+ (depotListSize != 1 ? "-d" + depotListSize : "") + "-n" /* + locationListSize */ + "-k"
				+ vehicleListSize;
		File vrpOutputFile = createVrpOutputFile(name, distanceType, vrpType, depotListSize != 1);
		BufferedWriter vrpWriter = null;
		try {
			vrpWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vrpOutputFile), "UTF-8"));
			vrpWriter = writeHeaders(vrpWriter, locationListSize, capacity, distanceType, vrpType, name);
			// writeHubCoordSection(vrpWriter, distanceType, hubList);
			writeNodeCoordSection(vrpWriter, locationList);
			writeEdgeWeightSection(vrpWriter, distanceType, locationList);
			writeDemandSection(vrpWriter, locationListSize, depotListSize, vehicleListSize, capacity, locationList,
					vrpType);
			writeDepotSection(vrpWriter, locationList, depotListSize);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read the locationFile (" + ") or write the vrpOutputFile ("
					+ vrpOutputFile.getName() + ").", e);
		} finally {

			IOUtils.closeQuietly(vrpWriter);

		}

		log.info("Generated: {}" + vrpOutputFile);

		return "vrp file created successfully";
	}

	private File createVrpOutputFile(String name, DistanceType distanceType, VrpType vrpType, boolean multidepot) {

		File vrpOutputFile = new File(vrpFile + "/" + name + ".vrp");
		if (!vrpOutputFile.getParentFile().exists()) {
			throw new IllegalArgumentException(
					"The vrpOutputFile parent directory (" + vrpOutputFile.getParentFile() + ") does not exist.");
		}
		return vrpOutputFile;
	}

	private BufferedWriter writeHeaders(BufferedWriter vrpWriter, int locationListSize, int capacity,
			DistanceType distanceType, VrpType vrpType, String name) throws IOException {
		vrpWriter.write("NAME: " + name + "\n");
		vrpWriter.write("COMMENT: Generated for OptaPlanner Examples"
				+ (distanceType == DistanceType.AIR_DISTANCE ? "" : " with googlemaps") + " by Bharathi.\n");
		vrpWriter.write("COMMENT: https://www.optaplanner.org/community/research.html\n");
		vrpWriter.write("TYPE: " + vrpType.getHeaderType() + "\n");
		vrpWriter.write("DIMENSION: " + locationListSize + "\n");
		if (distanceType.isRoad()) {
			if (distanceType.isSegmented()) {
				vrpWriter.write("EDGE_WEIGHT_TYPE: SEGMENTED_EXPLICIT\n");
				vrpWriter.write("EDGE_WEIGHT_FORMAT: HUB_AND_NEARBY_MATRIX\n");
			} else {
				vrpWriter.write("EDGE_WEIGHT_TYPE: EXPLICIT\n");
				vrpWriter.write("EDGE_WEIGHT_FORMAT: FULL_MATRIX\n");
			}
			vrpWriter.write("EDGE_WEIGHT_UNIT_OF_MEASUREMENT: " + distanceType.getUnitOfMeasurement() + "\n");
		} else {
			vrpWriter.write("EDGE_WEIGHT_TYPE: EUC_2D\n");
		}
		vrpWriter.write("CAPACITY: " + capacity + "\n");
		return vrpWriter;
	}

	private void writeNodeCoordSection(BufferedWriter vrpWriter, List<VehicleRoutingLocations> locationList)
			throws IOException {
		vrpWriter.write("NODE_COORD_SECTION\n");
		for (VehicleRoutingLocations location : locationList) {
			vrpWriter.write(location.getId() + " " + location.getLatitude() + " " + location.getLongitude()
					+ (location.getName() != null ? " " + location.getName().replaceAll(" ", "_") : "") + "\n");
		}
	}

	private void writeEdgeWeightSection(BufferedWriter vrpWriter, DistanceType distanceType,
			List<VehicleRoutingLocations> locationList) throws IOException {
		if (distanceType.isRoad()) {
			DecimalFormat distanceFormat = new DecimalFormat("0.0");
			if (!distanceType.isSegmented()) {
				vrpWriter.write("EDGE_WEIGHT_SECTION\n");
				if (distanceType == distanceType.ROAD_DISTANCE_TIME) {
					for (VehicleRoutingLocations fromLocation : locationList) {
						for (VehicleRoutingLocations toLocation : locationList) {
							Long time;
							if (fromLocation == toLocation) {
								time = 0l;
							} else {
								time = calculateTime(fromLocation.getLatitude(), fromLocation.getLongitude(),
										toLocation.getLatitude(), toLocation.getLongitude());
								if (time == 0.0) {
									throw new IllegalArgumentException("The fromLocation (" + fromLocation
											+ ") and toLocation (" + toLocation + ") have a zero distance.");
								}
							}
							vrpWriter.write(time + " ");
						}
						vrpWriter.write("\n");
						log.info("time calculated for location ({})." + fromLocation);
					}
				} else {

					for (VehicleRoutingLocations fromLocation : locationList) {
						for (VehicleRoutingLocations toLocation : locationList) {
							double distance;
							if (fromLocation == toLocation) {
								distance = 0l;
							} else {
								distance = calculateDistance(fromLocation.getLatitude(), fromLocation.getLongitude(),
										toLocation.getLatitude(), toLocation.getLongitude());
								if (distance == 0.0) {
									throw new IllegalArgumentException("The fromLocation (" + fromLocation
											+ ") and toLocation (" + toLocation + ") have a zero distance.");
								}
							}
							vrpWriter.write(distance + " ");
						}
						vrpWriter.write("\n");
						log.info("All distances calculated for location ({})." + fromLocation);
					}

				}
			}

		}
	}

	private void writeDemandSection(BufferedWriter vrpWriter, int locationListSize, int depotListSize,
			int vehicleListSize, int capacity, List<VehicleRoutingLocations> locationList, VrpType vrpType)
			throws IOException {
		vrpWriter.append("DEMAND_SECTION\n");
		// maximumDemand is 2 times the averageDemand. And the averageDemand is 2/3th of
		// available capacity
		int maximumDemand = (4 * vehicleListSize * capacity) / (locationListSize * 3);
		int minReadyTime = 7 * 60 * 60; // 7:00
		int maxWindowTimeInHalfHours = 12 * 2; // 12 hours
		int maxDueTime = minReadyTime + maxWindowTimeInHalfHours * 30 * 60; // 19:00
		int customerServiceDuration = 5 * 60; // 5 minutes
		int i = 0;
		Random random = new Random(37);
		for (VehicleRoutingLocations location : locationList) {
			String line;
			if (i < depotListSize) {
				line = location.getId() + " " + location.getDemand();
				if (vrpType == VrpType.TIMEWINDOWED) {
					// Depot open from 7:00 until 19:00
					line += " " + minReadyTime + " " + maxDueTime + " 0";
				}
			} else {
				line = location.getId() + " " + location.getDemand();
				if (vrpType == VrpType.TIMEWINDOWED) {
					int windowTimeInHalfHours = (4 * 2) + random.nextInt((4 * 2) + 1); // 4 to 8 hours
					int readyTime = minReadyTime
							+ random.nextInt(maxWindowTimeInHalfHours - windowTimeInHalfHours + 1) * 30 * 60;
					int dueTime = readyTime + (windowTimeInHalfHours * 30 * 60);
					line += " " + readyTime + " " + dueTime + " " + customerServiceDuration;
				}
			}
			vrpWriter.append(line).append("\n");
			i++;
		}
	}

	private void writeDepotSection(BufferedWriter vrpWriter, List<VehicleRoutingLocations> locationList,
			int depotListSize) throws IOException {
		vrpWriter.append("DEPOT_SECTION\n");
		for (int i = 0; i < depotListSize; i++) {
			VehicleRoutingLocations location = locationList.get(i);
			vrpWriter.append(Long.toString(location.getId())).append("\n");
		}
		vrpWriter.append("-1\n");
		vrpWriter.append("EOF\n");
	}

	public Long calculateTime(double fromLat, double fromLong, double toLat, double toLong) {
		log.debug("time calculation");
		String uriResponse = null;
		Long minimumTime = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "https://maps.googleapis.com/maps/api/directions/json?";
			uriResponse = restTemplate.getForObject(url + "origin=" + fromLat + ", " + fromLong + "&destination="
					+ toLat + ", " + toLong + "&sensor=false&mode=driving&alternatives=true&key=" + googleApiKey,
					String.class);

			JSONObject object = new JSONObject(uriResponse);

			JSONArray jsonArray = (JSONArray) object.get("routes");
			List<Long> timeList = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObject = (JSONObject) jsonArray.get(i);

				JSONArray childArray = (JSONArray) jsonObject.get("legs");

				for (int j = 0; j < childArray.length(); j++) {

					JSONObject jsonObject1 = (JSONObject) childArray.get(j);

					JSONObject timeJson = (JSONObject) jsonObject1.get("duration");
					long duration = timeJson.getLong("value");

					timeList.add(duration);
				}
			}
			List<Long> result = timeList.stream().sorted((o1, o2) -> Long.valueOf(o1).compareTo(Long.valueOf(o2)))
					.collect(Collectors.toList());
			minimumTime = result.get(0);
		} catch (Exception exception) {
			log.error("Unable to find the Distance ", exception);
		}
		return minimumTime;

	}

	public double calculateDistance(double fromLat, double fromLong, double toLat, double toLong) {
		log.debug("distance calculation");
		String uriResponse = null;
		double minimumTime = 0.0;
		double distance = 0.0;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "https://maps.googleapis.com/maps/api/directions/json?";
			uriResponse = restTemplate.getForObject(url + "origin=" + fromLat + ", " + fromLong + "&destination="
					+ toLat + ", " + toLong + "&sensor=false&mode=driving&alternatives=true&key=" + googleApiKey,
					String.class);

			JSONObject object = new JSONObject(uriResponse);

			JSONArray jsonArray = (JSONArray) object.get("routes");
			List<Double> distanceList = new ArrayList<>();
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

					distanceList.add(distance);
				}
			}
			List<Double> result = distanceList.stream()
					.sorted((o1, o2) -> Double.valueOf(o1).compareTo(Double.valueOf(o2))).collect(Collectors.toList());
			minimumTime = result.get(0);
		} catch (Exception exception) {
			log.error("Unable to find the Distance ", exception);
		}
		return minimumTime;

	}

}
