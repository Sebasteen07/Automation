package com.medfusion.product.pss2patientui.pojo;

import java.util.ArrayList;

import com.medfusion.product.pss2patientui.utils.PatientMatch;

public class Appointment {
	private String dob;
	private String city;
	private String email;
	private String gender;
	private String street;
	private String urlIPD;
	private String zipCode;
	private String provider;
	private String username;
	private String password;
	private String location;
	private String datetime;
	private String lastName;
	private String firstName;
	private String speciality;
	private String icsFilePath;
	private String urlLoginLess;
	private String firstTimeUser;
	private String primaryNumber;
	private String patientUserName;
	private String patientPassword;
	private String appointmenttype;
	private String appointmentFlow;
	private String patientPortalURL;
	private String isExistingPatient;
	private String patientPortalUserName;
	private String patientPortalPassword;
	private String emaiSubject;
	private String findInEmail;
	private String retries;

	private ArrayList<PatientMatch> patientMatchList = new ArrayList<PatientMatch>();

	public Appointment(String providerConfig, String usernameConfig, String passwordConfig, String datetimeConfig, String locationConfig,
			String appointmentConfig, String specialityConfig) {
		this.username = usernameConfig;
		this.password = passwordConfig;
		this.provider = providerConfig;
		this.location = locationConfig;
		this.datetime = datetimeConfig;
		this.appointmenttype = appointmentConfig;
		this.speciality = specialityConfig;
	}

	public Appointment() {
	}

	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getAppointmenttype() {
		return appointmenttype;
	}
	public void setAppointmenttype(String appointmenttype) {
		this.appointmenttype = appointmenttype;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getAppointmentFlow() {
		return appointmentFlow;
	}

	public void setAppointmentFlow(String appointmentFlow) {
		this.appointmentFlow = appointmentFlow;
	}

	public String getFirstTimeUser() {
		return firstTimeUser;
	}

	public void setFirstTimeUser(String firstTimeUser) {
		this.firstTimeUser = firstTimeUser;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPrimaryNumber() {
		return primaryNumber;
	}

	public void setPrimaryNumber(String primaryNumber) {
		this.primaryNumber = primaryNumber;
	}

	public String getUrlLoginLess() {
		return urlLoginLess;
	}

	public void setUrlLoginLess(String urlLoginLess) {
		this.urlLoginLess = urlLoginLess;
	}

	public String getUrlIPD() {
		return urlIPD;
	}

	public void setUrlIPD(String urlIPD) {
		this.urlIPD = urlIPD;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIcsFilePath() {
		return icsFilePath;
	}

	public void setIcsFilePath(String icsFilePath) {
		this.icsFilePath = icsFilePath;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getIsExistingPatient() {
		return isExistingPatient;
	}

	public void setIsExistingPatient(String isExistingPatient) {
		this.isExistingPatient = isExistingPatient;
	}

	public String getPatientPassword() {
		return patientPassword;
	}

	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}

	public String getPatientUserName() {
		return patientUserName;
	}

	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}

	public ArrayList<PatientMatch> getPatientMatchList() {
		return patientMatchList;
	}

	public void setPatientMatchList(ArrayList<PatientMatch> patientMatchList) {
		this.patientMatchList = patientMatchList;
	}

	public String getPatientPortalURL() {
		return patientPortalURL;
	}

	public void setPatientPortalURL(String patientPortalURL) {
		this.patientPortalURL = patientPortalURL;
	}

	public String getPatientPortalUserName() {
		return patientPortalUserName;
	}

	public void setPatientPortalUserName(String patientPortalUserName) {
		this.patientPortalUserName = patientPortalUserName;
	}

	public String getPatientPortalPassword() {
		return patientPortalPassword;
	}

	public void setPatientPortalPassword(String patientPortalPassword) {
		this.patientPortalPassword = patientPortalPassword;
	}

	public String getEmaiSubject() {
		return emaiSubject;
	}

	public void setEmaiSubject(String emaiSubject) {
		this.emaiSubject = emaiSubject;
	}

	public String getFindInEmail() {
		return findInEmail;
	}

	public void setFindInEmail(String findInEmail) {
		this.findInEmail = findInEmail;
	}

	public String getRetries() {
		return retries;
	}

	public void setRetries(String retries) {
		this.retries = retries;
	}

}