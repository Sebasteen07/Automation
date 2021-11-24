// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.pojo;

public class Appointment {
	private boolean generateEmailTrue = true;
	private boolean generateEmailFalse = false;
	private boolean generateTextTrue = true;
	private boolean generateTextFalse = false;
	private String pmAppointmentId = null;
	private String pmPatientId = null;
	private String pmPracticeId = null;
	private boolean pssSetting = true;
	public static String apptId;
	public static String patientId;
	public static long plus20Minutes;

	public boolean isGenerateEmailTrue() {
		return generateEmailTrue;
	}

	public void setGenerateEmailTrue(boolean generateEmailTrue) {
		this.generateEmailTrue = generateEmailTrue;
	}

	public boolean isGenerateEmailFalse() {
		return generateEmailFalse;
	}

	public void setGenerateEmailFalse(boolean generateEmailFalse) {
		this.generateEmailFalse = generateEmailFalse;
	}

	public boolean isGenerateTextTrue() {
		return generateTextTrue;
	}

	public void setGenerateTextTrue(boolean generateTextTrue) {
		this.generateTextTrue = generateTextTrue;
	}

	public boolean isGenerateTextFalse() {
		return generateTextFalse;
	}

	public void setGenerateTextFalse(boolean generateTextFalse) {
		this.generateTextFalse = generateTextFalse;
	}

	public String getPmAppointmentId() {
		return pmAppointmentId;
	}

	public void setPmAppointmentId(String pmAppointmentId) {
		this.pmAppointmentId = pmAppointmentId;
	}

	public String getPmPatientId() {
		return pmPatientId;
	}

	public void setPmPatientId(String pmPatientId) {
		this.pmPatientId = pmPatientId;
	}

	public String getPmPracticeId() {
		return pmPracticeId;
	}

	public void setPmPracticeId(String pmPracticeId) {
		this.pmPracticeId = pmPracticeId;
	}

	public boolean isPssSetting() {
		return pssSetting;
	}

	public void setPssSetting(boolean pssSetting) {
		this.pssSetting = pssSetting;
	}
}
