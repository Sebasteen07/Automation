package com.medfusion.product.pss2patientui.utils;

public class PatientMatch {

	private String email;
	private String zipCode;
	private String firstName;
	private String insuranceId;
	private String gender;
	private String dob;
	private String phoneNumber;
	private String lastName;

	public PatientMatch(String _email, String _zipCode, String _firstName, String _insuranceId, String _gender, String _dob, String _phone, String _lastName) {
		this.email = _email;
		this.zipCode = _zipCode;
		this.firstName = _firstName;
		this.insuranceId = _insuranceId;
		this.gender = _gender;
		this.dob = _dob;
		this.phoneNumber = _phone;
		this.lastName = _lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
