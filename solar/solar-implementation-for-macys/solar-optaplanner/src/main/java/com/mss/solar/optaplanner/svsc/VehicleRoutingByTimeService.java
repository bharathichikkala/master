package com.mss.solar.optaplanner.svsc;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mss.solar.optaplanner.domain.VehicleRoutingLocations;
import com.mss.solar.optaplanner.model.Message;
import com.mss.solar.optaplanner.model.VehicleRoutingSolutionDetails;

@RequestMapping(value = "/api/routing")
public interface VehicleRoutingByTimeService {

	@GetMapping(value = "/solution/time/{loadNumber}")
	@ResponseBody
	VehicleRoutingSolutionDetails getSolutionByTime(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/time/solve/{loadNumber}")
	@ResponseBody
	Message solveByTime(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/time/terminateEarly/{loadNumber}")
	@ResponseBody
	Message terminateEarlyByTime(@PathVariable String loadNumber);

	@GetMapping(value = "/routing/{loadNumber}")
	@ResponseBody
	List<VehicleRoutingLocations> routingByLoadNumber(@PathVariable String loadNumber);
	
	@GetMapping(value = "/routesolving/{loadNumber}")
	@ResponseBody
	Message routingsolvingByLoadNumber(@PathVariable String loadNumber);
	
	@GetMapping(value = "/solution/time/getTimeAndDistance/{loadNumber}")
	@ResponseBody
	VehicleRoutingSolutionDetails getDistanceByTime(@PathVariable String loadNumber);
	
	

}
