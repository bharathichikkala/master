package com.mss.solar.optaplanner.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class VehicleRouteDetails {
	//@Id
	protected String depotLocationName;
	protected double depotLatitude;
	protected double depotLongitude;

	protected String hexColor;
	protected int capacity;
	protected int demandTotal;
	@OneToMany
	protected List<CustomerDetails> customerList;

	public String getDepotLocationName() {
		return depotLocationName;
	}

	public void setDepotLocationName(String depotLocationName) {
		this.depotLocationName = depotLocationName;
	}

	public double getDepotLatitude() {
		return depotLatitude;
	}

	public void setDepotLatitude(double depotLatitude) {
		this.depotLatitude = depotLatitude;
	}

	public double getDepotLongitude() {
		return depotLongitude;
	}

	public void setDepotLongitude(double depotLongitude) {
		this.depotLongitude = depotLongitude;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getDemandTotal() {
		return demandTotal;
	}

	public void setDemandTotal(int demandTotal) {
		this.demandTotal = demandTotal;
	}

	public List<CustomerDetails> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerDetails> customerList) {
		this.customerList = customerList;
	}

}