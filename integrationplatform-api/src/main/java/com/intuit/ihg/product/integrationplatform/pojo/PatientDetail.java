package com.intuit.ihg.product.integrationplatform.pojo;

public class PatientDetail {
	private String race;
	private String ethnicity;
	private String gender;
	private String preferredLanguage;
	
	private String preferredCommunication;
	private String genderIdentity;
	private String sexualOrientation;
	private String stateNodeValue;
	
	public PatientDetail(String race,String ethnicity,String gender,String preferredLanguage, String preferredCommunication, String genderIdentity,String sexualOrientation,String stateNodeValue) {
		this.race=race;
		this.ethnicity=ethnicity;
		this.gender=gender;
		this.preferredLanguage=preferredLanguage;
		this.preferredCommunication=preferredCommunication;
		this.genderIdentity=genderIdentity;
		this.sexualOrientation=sexualOrientation;
		this.stateNodeValue = stateNodeValue;
		
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPreferredLanguage() {
		return preferredLanguage;
	}
	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}
	public String getGenderIdentity() {
		return genderIdentity;
	}
	public void setGenderIdentity(String genderIdentity) {
		this.genderIdentity = genderIdentity;
	}
	public String getPreferredCommunication() {
		return preferredCommunication;
	}
	public void setPreferredCommunication(String preferredCommunication) {
		this.preferredCommunication = preferredCommunication;
	}
	public String getSexualOrientation() {
		return sexualOrientation;
	}
	public void setSexualOrientation(String sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}
	public String getStateNodeValue() {
		return stateNodeValue;
	}
	public void setStateNodeValue(String stateNodeValue) {
		this.stateNodeValue = stateNodeValue;
	}
}
