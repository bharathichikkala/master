package com.mss.solar.optaplanner.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class CustomerDetails {
	//@Id
	protected String locationName;
	protected double latitude;
	protected double longitude;
	protected int demand;

	public CustomerDetails() {
	}

	public CustomerDetails(String locationName, double latitude, double longitude, int demand) {
		this.locationName = locationName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.demand = demand;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}
}
