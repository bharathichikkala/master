package com.mss.solar.optaplanner.svsc;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mss.solar.optaplanner.domain.VehicleRoutingLocations;

@RequestMapping(value = "/api/vrp")
public interface VrpGeneratorService {

	@PostMapping(value = "/vrpgenerator/{loadNumber}")
	@ResponseBody
	String generateVRP(@RequestBody List<VehicleRoutingLocations> routingLocations, @PathVariable String loadNumber);

}
