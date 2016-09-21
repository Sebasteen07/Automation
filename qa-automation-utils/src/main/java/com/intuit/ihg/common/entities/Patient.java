package com.intuit.ihg.common.entities;

/**
 * Created by IntelliJ IDEA. User: vvalsan Date: 3/26/13 Time: 4:08 PM To change this template use File | Settings | File Templates.
 */
public class Patient {

	private String userName;
	private String password;
	private String emailId;
	private String pracSearchString;
	private String gmailUName;
	private String gmailPassword;
	private String secQuestion;
	private String secAnswer;
	private String streetAddress;
	private String city;
	private String state;
	private String phoneNumber;
	private String zipCode;
	private String phoneType;
	private String sex;
	private String maritalStatus;
	private String communicationMethod;
	private String prefferedLanguage;
	private String race;
	private String ethnicity;


	public Patient() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPracSearchString() {
		return pracSearchString;
	}

	public void setPracSearchString(String pracSearchString) {
		this.pracSearchString = pracSearchString;
	}

	public String getGmailUName() {
		return gmailUName;
	}

	public void setGmailUName(String gmailUName) {
		this.gmailUName = gmailUName;
	}

	public String getGmailPassword() {
		return gmailPassword;
	}

	public void setGmailPassword(String gmailPassword) {
		this.gmailPassword = gmailPassword;
	}

	public String getSecQuestion() {
		return secQuestion;
	}

	public void setSecQuestion(String secQuestion) {
		this.secQuestion = secQuestion;
	}

	public String getSecAnswer() {
		return secAnswer;
	}

	public void setSecAnswer(String secAnswer) {
		this.secAnswer = secAnswer;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCommunicationMethod() {
		return communicationMethod;
	}

	public void setCommunicationMethod(String communicationMethod) {
		this.communicationMethod = communicationMethod;
	}

	public String getPrefferedLanguage() {
		return prefferedLanguage;
	}

	public void setPrefferedLanguage(String prefferedLanguage) {
		this.prefferedLanguage = prefferedLanguage;
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

	public String toString() {
		return "[UserName=" + userName + "|Password=" + password + "|Email=" + emailId + "|GmailId=" + gmailUName + "]";
	}
}


