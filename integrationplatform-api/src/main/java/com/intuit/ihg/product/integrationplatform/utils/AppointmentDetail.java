package com.intuit.ihg.product.integrationplatform.utils;

public class AppointmentDetail {
	private String time;
	private String status;
	private String providerName;
	private String location;
	
	public AppointmentDetail(String time,String status,String providerName,String location) {
		this.time = time;
		this.status = status;
		this.providerName = providerName;
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
