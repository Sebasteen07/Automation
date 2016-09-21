package com.medfusion.product.patientportal2.pojo;

import java.util.Date;

public class Appointment {

	private Date date;
	private String provider;
	private String location;
	private boolean canceled;

	public Appointment(Date date, String provider, String location, boolean canceled) {
		this.date = date;
		this.provider = provider;
		this.location = location;
		this.canceled = canceled;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
}
