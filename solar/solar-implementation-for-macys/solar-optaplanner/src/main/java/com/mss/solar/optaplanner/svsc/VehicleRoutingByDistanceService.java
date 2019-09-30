package com.mss.solar.optaplanner.svsc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mss.solar.optaplanner.model.Message;
import com.mss.solar.optaplanner.model.VehicleRoutingSolutionDetails;

@RequestMapping(value = "/api/routing")
public interface VehicleRoutingByDistanceService {
	
	@GetMapping(value = "/solution/distance/{loadNumber}")
	@ResponseBody
	VehicleRoutingSolutionDetails getSolutionByDistance(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/distance/solve/{loadNumber}")
	@ResponseBody
	Message solveByDistance(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/distance/terminateEarly/{loadNumber}")
	@ResponseBody
	Message terminateEarlyByDistance(@PathVariable String loadNumber);
	
	@GetMapping(value = "/solution/distance/getTimeAndDistance/{loadNumber}")
	@ResponseBody
	VehicleRoutingSolutionDetails getTimeByDistance(@PathVariable String loadNumber);
	
	
	

}
