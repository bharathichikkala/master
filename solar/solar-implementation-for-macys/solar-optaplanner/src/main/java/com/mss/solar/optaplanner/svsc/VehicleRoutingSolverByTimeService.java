package com.mss.solar.optaplanner.svsc;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/routing/solver")
public interface VehicleRoutingSolverByTimeService {
	
	@GetMapping(value = "/solution/time/{loadNumber}")
	@ResponseBody
	VehicleRoutingSolution retrieveOrCreateSolutionByTime(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/time/solve/{loadNumber}")
	@ResponseBody
	boolean solveByTime(@PathVariable String loadNumber);

	@PostMapping(value = "/solution/time/terminateEarly/{loadNumber}")
	@ResponseBody
	boolean terminateEarlyByTime(@PathVariable String loadNumber);
	
	

}
