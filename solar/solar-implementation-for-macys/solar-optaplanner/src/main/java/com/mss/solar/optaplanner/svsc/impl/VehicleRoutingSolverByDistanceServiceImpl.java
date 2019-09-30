package com.mss.solar.optaplanner.svsc.impl;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingImporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.mss.solar.optaplanner.svsc.VehicleRoutingSolverByDistanceService;

@RestController
@Validated
public class VehicleRoutingSolverByDistanceServiceImpl implements VehicleRoutingSolverByDistanceService {

private static final Logger log = Logger.getLogger(VehicleRoutingSolverByDistanceServiceImpl.class);
	
	@Value("${vrp.url}")
	private String vrpFile;

	private static final String SOLVER_CONFIG = "org/optaplanner/examples/vehiclerouting/solver/vehicleRoutingSolverConfig.xml";

	private SolverFactory<VehicleRoutingSolution> solverFactory;

	private ExecutorService executor;

	private Map<String, VehicleRoutingSolution> sessionSolutionMap;
	private Map<String, Solver<VehicleRoutingSolution>> sessionSolverMap;

	@PostConstruct
	public synchronized void init() {
		log.info("initializing solver config");
		solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
		// Always terminate a solver after 2 minutes
		solverFactory.getSolverConfig().setTerminationConfig(new TerminationConfig().withMinutesSpentLimit(2L));
		executor = Executors.newFixedThreadPool(2); // Only 2 because the other examples have their own Executor

		sessionSolutionMap = new ConcurrentHashMap<>();
		sessionSolverMap = new ConcurrentHashMap<>();
	}

	@PreDestroy
	public synchronized void destroy() {
		log.info("destory solver config");
		for (Solver<VehicleRoutingSolution> solver : sessionSolverMap.values()) {
			solver.terminateEarly();
		}
		executor.shutdown();
	}

	@Override
	public synchronized VehicleRoutingSolution retrieveOrCreateSolutionByDistance(String loadNumber) {
		log.info("retrive or create a solution");
		VehicleRoutingSolution solution = sessionSolutionMap.get(loadNumber);
		if (solution == null) {
			File inputFile=new File(vrpFile+"/"+loadNumber+"-road-km-n-k1.vrp");
		if (!inputFile.exists()) {
				throw new IllegalArgumentException(
						"The vrp file is not exists");
			}
			 solution = (VehicleRoutingSolution) new VehicleRoutingImporter()
					.readSolution(inputFile);
			sessionSolutionMap.put(loadNumber, solution);
		}
		return solution;
	}

	@Override
	public synchronized boolean solveByDistance(String loadNumber) {
        final Solver<VehicleRoutingSolution> solver = solverFactory.buildSolver();
        solver.addEventListener(event -> {
            VehicleRoutingSolution bestSolution = event.getNewBestSolution();
            synchronized (VehicleRoutingSolverByDistanceServiceImpl.this) {
                sessionSolutionMap.put(loadNumber, bestSolution);
            }
        });
        if (sessionSolverMap.containsKey(loadNumber)) {
            return false;
        }
        sessionSolverMap.put(loadNumber, solver);
        final VehicleRoutingSolution solution = retrieveOrCreateSolutionByDistance(loadNumber);
        executor.submit((Runnable) () -> {
            VehicleRoutingSolution bestSolution = solver.solve(solution);
            synchronized (VehicleRoutingSolverByDistanceServiceImpl.this) {
                sessionSolutionMap.put(loadNumber, bestSolution);
                sessionSolverMap.remove(loadNumber);
            }
        });
        return true;
    }

	@Override
	public synchronized boolean terminateEarlyByDistance(String loadNumber) {
		log.info("terminate rouing optimization");
		Solver<VehicleRoutingSolution> solver = sessionSolverMap.remove(loadNumber);
		if (solver != null) {
			solver.terminateEarly();
			return true;
		} else {
			return false;
		}
	}

}