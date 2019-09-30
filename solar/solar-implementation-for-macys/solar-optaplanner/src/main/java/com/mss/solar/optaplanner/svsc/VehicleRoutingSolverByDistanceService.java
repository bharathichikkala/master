package com.mss.solar.optaplanner.svsc;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/routing/solver")
public interface VehicleRoutingSolverByDistanceService {
	
	

	@GetMapping(value = "/solution/distance/{loadNumber}")
	@ResponseBody
	VehicleRoutingSolution retrieveOrCreateSolutionByDistance(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/distance/solve/{loadNumber}")
	@ResponseBody
	boolean solveByDistance(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/distance/terminateEarly/{loadNumber}")
	@ResponseBody
	boolean terminateEarlyByDistance(@PathVariable String loadNumber);
	

}
