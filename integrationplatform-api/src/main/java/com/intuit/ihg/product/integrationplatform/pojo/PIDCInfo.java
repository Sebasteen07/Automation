package com.intuit.ihg.product.integrationplatform.pojo;

import java.util.ArrayList;

public class PIDCInfo {
	private String restUrl = "";
	private String practiceId = "";
	private ArrayList<String> race = null;
	private ArrayList<String> ethnicity = null;
	private ArrayList<String> gender = null;
	private ArrayList<String> preferredLanguage= null;
	private ArrayList<String> preferredCommunication= null;
	private ArrayList<String> genderIdentity=null;
	private ArrayList<String> sexualOrientation=null;
	private ArrayList<String> stateNodeValue=null;

	private String batchSize = "";	
	private String username = "";
	private String password = "";

	private String patientPath = "";
	private String responsePath = "";
	private String oAuthProperty = "";
	private String oAuthKeyStore = "";
	private String oAuthAppToken = "";
	private String oAuthUsername = "";
	private String oAuthPassword = "";
	private String birthDay = "";
	private String zipCode = "";
	private String SSN = "";
	private String email = "";
	private String patientPassword = "";
	private String secretQuestion = "";
	private String secretAnswer = "";
	private String practiceURL = "";
	private String practiceUserName = "";
	private String practicePassword = "";
	private String lastName = "";
	private String city = "";
	private String state = "";
	private String address1 = "";
	private String address2 = "";
	private String homePhoneNo = "";
	private String insurance_Type = "";
	private String insurance_Name = "";
	private String relation = "";
	private String maritalStatus = "";
	private String chooseCommunication = "";
	private String batch_PatientPath = "";
	private String portalURL = "";
	private String portalRestUrl = "";
	private String healthKeyPatientUserName = "";
	private String healthKeyPatientPath = "";
	private String insuranceHealthKeyPatientUserName = "";
	private String insurancePortalURL = "";
	private String insurancePortalRestURL = "";
	private String insurancePatientID = "";
	private String insuranceHealthKeyPatientUserName1 = "";
	private String insurancePatientID1 = "";
	private String secondInsuranceName = "";
	private String testPatientIDUserName = "";
	private String fnameSC = "";
	private String lnameSC = "";
	private String portalVersion = "";
	private String csvFilePath = "";
	public ArrayList<PatientDetail> patientDetailList = new ArrayList<PatientDetail>();	
	private String restUrl_20="";
	private String practiceId_PIDC_20="";
	private String oAuthAppToken_20 = "";
	private String oAuthUsername_20 = "";
	private String oAuthPassword_20 = "";
	private String preferredLanguageType="";
	private String token;
	private String precheckSubscriberPatientRestURL="";
	private String SubscriberPracticeID = "";
	
	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrlExternal) {
		restUrl = restUrlExternal;
	}

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String patientExternalId) {
		practiceId = patientExternalId;
	}

	public ArrayList<String> getGender() {
		return gender;
	}

	public void setGender(ArrayList<String> genderExternal) {
		gender = genderExternal;
	}

	public String getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}

	public ArrayList<String> getRace() {
		return race;
	}

	public void setRace(ArrayList<String> race) {
		this.race = race;
	}

	public ArrayList<String> getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(ArrayList<String> ethnicity) {
		this.ethnicity = ethnicity;
	}

	public ArrayList<String> getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(ArrayList<String> preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public ArrayList<String> getPreferredCommunication() {
		return preferredCommunication;
	}

	public void setPreferredCommunication(ArrayList<String> preferredCommunication) {
		this.preferredCommunication = preferredCommunication;
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

	public String getPatientPath() {
		return patientPath;
	}

	public void setPatientPath(String patientPath) {
		this.patientPath = patientPath;
	}

	public String getResponsePath() {
		return responsePath;
	}

	public void setResponsePath(String responsePath) {
		this.responsePath = responsePath;
	}

	public String getoAuthProperty() {
		return oAuthProperty;
	}

	public void setoAuthProperty(String oAuthProperty) {
		this.oAuthProperty = oAuthProperty;
	}

	public String getoAuthKeyStore() {
		return oAuthKeyStore;
	}

	public void setoAuthKeyStore(String oAuthKeyStore) {
		this.oAuthKeyStore = oAuthKeyStore;
	}

	public String getoAuthAppToken() {
		return oAuthAppToken;
	}

	public void setoAuthAppToken(String oAuthAppToken) {
		this.oAuthAppToken = oAuthAppToken;
	}

	public String getoAuthPassword() {
		return oAuthPassword;
	}

	public void setoAuthPassword(String oAuthPassword) {
		this.oAuthPassword = oAuthPassword;
	}

	public String getoAuthUsername() {
		return oAuthUsername;
	}

	public void setoAuthUsername(String oAuthUsername) {
		this.oAuthUsername = oAuthUsername;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPatientPassword() {
		return patientPassword;
	}

	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getPracticeURL() {
		return practiceURL;
	}

	public void setPracticeURL(String practiceURL) {
		this.practiceURL = practiceURL;
	}

	public String getPracticeUserName() {
		return practiceUserName;
	}

	public void setPracticeUserName(String practiceUserName) {
		this.practiceUserName = practiceUserName;
	}

	public String getPracticePassword() {
		return practicePassword;
	}

	public void setPracticePassword(String practicePassword) {
		this.practicePassword = practicePassword;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getHomePhoneNo() {
		return homePhoneNo;
	}

	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}

	public String getInsurance_Type() {
		return insurance_Type;
	}

	public void setInsurance_Type(String insurance_Type) {
		this.insurance_Type = insurance_Type;
	}

	public String getInsurance_Name() {
		return insurance_Name;
	}

	public void setInsurance_Name(String insurance_Name) {
		this.insurance_Name = insurance_Name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getChooseCommunication() {
		return chooseCommunication;
	}

	public void setChooseCommunication(String chooseCommunication) {
		this.chooseCommunication = chooseCommunication;
	}

	public String getBatch_PatientPath() {
		return batch_PatientPath;
	}

	public void setBatch_PatientPath(String batch_PatientPath) {
		this.batch_PatientPath = batch_PatientPath;
	}

	public String getPortalURL() {
		return portalURL;
	}

	public void setPortalURL(String portalURL) {
		this.portalURL = portalURL;
	}

	public String getHealthKeyPatientUserName() {
		return healthKeyPatientUserName;
	}

	public void setHealthKeyPatientUserName(String healthKeyPatientUserName) {
		this.healthKeyPatientUserName = healthKeyPatientUserName;
	}

	public String getPortalRestUrl() {
		return portalRestUrl;
	}

	public void setPortalRestUrl(String portalRestUrl) {
		this.portalRestUrl = portalRestUrl;
	}

	public String getHealthKeyPatientPath() {
		return this.healthKeyPatientPath;
	}

	public void setHealthKeyPatientPath(String healthKeyPatientPath) {
		this.healthKeyPatientPath = healthKeyPatientPath;
	}

	public String getInsuranceHealthKeyPatientUserName() {
		return insuranceHealthKeyPatientUserName;
	}

	public void setInsuranceHealthKeyPatientUserName(
			String insuranceHealthKeyPatientUserName) {
		this.insuranceHealthKeyPatientUserName = insuranceHealthKeyPatientUserName;
	}

	public String getInsurancePortalRestURL() {
		return insurancePortalRestURL;
	}

	public void setInsurancePortalRestURL(String insurancePortalRestURL) {
		this.insurancePortalRestURL = insurancePortalRestURL;
	}

	public String getInsurancePortalURL() {
		return insurancePortalURL;
	}

	public void setInsurancePortalURL(String insurancePortalURL) {
		this.insurancePortalURL = insurancePortalURL;
	}

	public String getInsurancePatientID() {
		return insurancePatientID;
	}

	public void setInsurancePatientID(String insurancePatientID) {
		this.insurancePatientID = insurancePatientID;
	}

	public String getInsuranceHealthKeyPatientUserName1() {
		return insuranceHealthKeyPatientUserName1;
	}

	public void setInsuranceHealthKeyPatientUserName1(
			String insuranceHealthKeyPatientUserName1) {
		this.insuranceHealthKeyPatientUserName1 = insuranceHealthKeyPatientUserName1;
	}

	public String getInsurancePatientID1() {
		return insurancePatientID1;
	}

	public void setInsurancePatientID1(String insurancePatientID1) {
		this.insurancePatientID1 = insurancePatientID1;
	}

	public String getSecondInsuranceName() {
		return secondInsuranceName;
	}

	public void setSecondInsuranceName(String secondInsuranceName) {
		this.secondInsuranceName = secondInsuranceName;
	}

	public String getTestPatientIDUserName() {
		return testPatientIDUserName;
	}

	public void setTestPatientIDUserName(String testPatientIDUserName) {
		this.testPatientIDUserName = testPatientIDUserName;
	}

	public String getFnameSC() {
		return fnameSC;
	}

	public void setFnameSC(String fnameSC) {
		this.fnameSC = fnameSC;
	}

	public String getLnameSC() {
		return lnameSC;
	}

	public void setLnameSC(String lnameSC) {
		this.lnameSC = lnameSC;
	}

	public String getPortalVersion() {
		return portalVersion;
	}

	public void setPortalVersion(String portalVersion) {
		this.portalVersion = portalVersion;
	}

	public String getCsvFilePath() {
		return csvFilePath;
	}

	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public ArrayList<String> getGenderIdentity() {
		return genderIdentity;
	}

	public void setGenderIdentity(ArrayList<String> genderIdentity) {
		this.genderIdentity = genderIdentity;
	}

	public ArrayList<String> getSexualOrientation() {
		return sexualOrientation;
	}

	public void setSexualOrientation(ArrayList<String> sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}

	public ArrayList<PatientDetail> getPatientDetailList() {
		return patientDetailList;
	}

	public void setPatientDetailList(ArrayList<PatientDetail> patientDetailList) {
		this.patientDetailList = patientDetailList;
	}

	public String getRestUrl_20() {
		return restUrl_20;
	}

	public void setRestUrl_20(String restUrl_20) {
		this.restUrl_20 = restUrl_20;
	}

	public String getPracticeId_PIDC_20() {
		return practiceId_PIDC_20;
	}

	public void setPracticeId_PIDC_20(String practiceId_PIDC_20) {
		this.practiceId_PIDC_20 = practiceId_PIDC_20;
	}

	public String getoAuthAppToken_20() {
		return oAuthAppToken_20;
	}

	public void setoAuthAppToken_20(String oAuthAppToken_20) {
		this.oAuthAppToken_20 = oAuthAppToken_20;
	}

	public String getoAuthUsername_20() {
		return oAuthUsername_20;
	}

	public void setoAuthUsername_20(String oAuthUsername_20) {
		this.oAuthUsername_20 = oAuthUsername_20;
	}

	public String getoAuthPassword_20() {
		return oAuthPassword_20;
	}

	public void setoAuthPassword_20(String oAuthPassword_20) {
		this.oAuthPassword_20 = oAuthPassword_20;
	}

	public String getPreferredLanguageType() {
		return preferredLanguageType;
	}

	public void setPreferredLanguageType(String preferredLanguageType) {
		this.preferredLanguageType = preferredLanguageType;
	}
	
	public ArrayList<String> getStateNodeValue() {
		return stateNodeValue;
	}

	public void setStateNodeValue(ArrayList<String> stateNodeValue) {
		this.stateNodeValue = stateNodeValue;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPrecheckSubscriberPatientRestURL() {
		return precheckSubscriberPatientRestURL;
	}

	public void setPrecheckSubscriberPatientRestURL(String precheckSubscriberPatientRestURL) {
		this.precheckSubscriberPatientRestURL = precheckSubscriberPatientRestURL;
	}
	
	public String getSubscriberPracticeID() {
		return SubscriberPracticeID;
	}

	public void setSubscriberPracticeID(String SubscriberPracticeID) {
		this.SubscriberPracticeID = SubscriberPracticeID;
	}

}