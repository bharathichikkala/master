package com.mss.solar.dashboard.svcs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mss.solar.dashboard.common.Utils;
import com.mss.solar.dashboard.domain.LoadDetails;
import com.mss.solar.dashboard.domain.SkidDrops;
import com.mss.solar.dashboard.domain.VehicleRoutingLocations;
import com.mss.solar.dashboard.repos.LoadDetailsRepository;
import com.mss.solar.dashboard.repos.LocationRepository;
import com.mss.solar.dashboard.svcs.VehicleRoutingService;

@RestController
@Validated
public class VehicleRoutingServiceImpl implements VehicleRoutingService {

	private static Logger log = LoggerFactory.getLogger(LoadDetailsServiceImpl.class);

	@Autowired
	private LoadDetailsRepository loadDetailsRepo;

	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	private Utils utils;
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public List<VehicleRoutingLocations> routingByLoadNumber(@PathVariable String loadNumber) {
		log.info("getting loadDetails for routing");

		List<VehicleRoutingLocations> routingLocations = new ArrayList<>();
		Integer id =0;
		try {
			LoadDetails loaddetails = loadDetailsRepo.findByLoadNumber(loadNumber);

			VehicleRoutingLocations originLocations = new VehicleRoutingLocations();
			originLocations.setId(id);
			id = id+1;
			originLocations.setName(loaddetails.getOriginLocNbr().getAddress());
			originLocations.setLatitude(loaddetails.getOriginLocNbr().getLatitude());
			originLocations.setLongitude(loaddetails.getOriginLocNbr().getLongitude());
			originLocations.setDemand(0);

			routingLocations.add(originLocations);

			Set<SkidDrops> skids = loaddetails.getSkidDrops();

			for (SkidDrops drops : skids) {

				VehicleRoutingLocations skidLocations = new VehicleRoutingLocations();
				skidLocations.setId(id);
				id = id+1;
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
	public String generateVRP(@PathVariable String loadNumber) {

		log.info("Generating vrp file from locations data");
		
		List<VehicleRoutingLocations> routingLocations = routingByLoadNumber(loadNumber);
		
		List<ServiceInstance> instances = discoveryClient.getInstances("solar-optaplanner");

		String baseURL = instances.get(0).getUri().toString();
		
		RestTemplate restTemplate = new RestTemplate();

		String url = baseURL + "/api/vrp/vrpgenerator/"+loadNumber;
		
		HttpEntity<List<VehicleRoutingLocations>> reqEntity = new HttpEntity<>(routingLocations);

		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, reqEntity,
				String.class);

		

		return result.getBody();
	}

}
