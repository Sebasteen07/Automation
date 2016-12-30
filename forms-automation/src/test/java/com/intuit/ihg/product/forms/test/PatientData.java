package com.intuit.ihg.product.forms.test;

public class PatientData {

	private String email;
	private String firstName;
	private String lastName;
	private String dob;
	private String password;
	private String url;

	public PatientData() {}

	public PatientData(String email, String firstName, String lastName, String dob, String password, String url) {
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDob(dob);
		this.setPassword(password);
		this.setUrl(url);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
