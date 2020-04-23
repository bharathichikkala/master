package com.mss.solar.dashboard.domain;

public class DistanceCalculation {
	
	private SkidDrops skidDrop;
	private Location locationForSkid;
	private Double distance;
	private String distanceAndTime;
	public SkidDrops getSkidDrop() {
		return skidDrop;
	}
	public void setSkidDrop(SkidDrops skidDrop) {
		this.skidDrop = skidDrop;
	}
	public Location getLocationForSkid() {
		return locationForSkid;
	}
	public void setLocationForSkid(Location locationForSkid) {
		this.locationForSkid = locationForSkid;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getDistanceAndTime() {
		return distanceAndTime;
	}
	public void setDistanceAndTime(String distanceAndTime) {
		this.distanceAndTime = distanceAndTime;
	}

}