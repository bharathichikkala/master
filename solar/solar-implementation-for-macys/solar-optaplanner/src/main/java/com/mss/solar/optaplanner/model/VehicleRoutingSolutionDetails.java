package com.mss.solar.optaplanner.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class VehicleRoutingSolutionDetails {

	//@Id
	protected String name;
	@OneToMany
	protected List<CustomerDetails> customerList;
	@OneToMany
	protected List<VehicleRouteDetails> vehicleRouteList;

	protected Boolean feasible;
	protected String distance;
	protected String time;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomerDetails> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerDetails> customerList) {
		this.customerList = customerList;
	}

	public List<VehicleRouteDetails> getVehicleRouteList() {
		return vehicleRouteList;
	}

	public void setVehicleRouteList(List<VehicleRouteDetails> vehicleRouteList) {
		this.vehicleRouteList = vehicleRouteList;
	}

	public Boolean getFeasible() {
		return feasible;
	}

	public void setFeasible(Boolean feasible) {
		this.feasible = feasible;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

}
