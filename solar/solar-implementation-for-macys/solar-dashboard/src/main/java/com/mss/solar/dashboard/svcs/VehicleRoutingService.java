package com.mss.solar.dashboard.svcs;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mss.solar.dashboard.common.RestApiUrlConstants;
import com.mss.solar.dashboard.domain.VehicleRoutingLocations;

@RequestMapping(value = "/api/vehiclerouting")
public interface VehicleRoutingService {

	final String MODULE_NAME = "VehicleRoutingService";

	@GetMapping(RestApiUrlConstants.VEHICLE_ROUTING_BY_LOADNUMBER)
	@ResponseBody
	List<VehicleRoutingLocations> routingByLoadNumber(@PathVariable String loadNumber);

	@GetMapping(RestApiUrlConstants.GENERATE_VRP_FILE)
	@ResponseBody
	String generateVRP(@PathVariable String loadNumber);

}
