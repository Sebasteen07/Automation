// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
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
	private String urlLinkGen;
	private String popUpMessege;

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
	private String isAppointmentPopup;
	private Boolean isNextDayBooking = false;
	private String appointmentScheduledFromPM;
	private String cancellationPolicyText;
	private ArrayList<PatientMatch> patientMatchList = new ArrayList<PatientMatch>();
	private Boolean isAdminActive = false;
	private String appointmentList;
	private String providerList;
	private String locationList;

	private String associatedProvider1;
	private String associatedLocation1;
	private String associatedSpeciality1;
	private String associatedApt1;

	private String associatedProvider2;
	private String associatedLocation2;
	private String associatedSpeciality2;
	private String associatedApt2;

	private String associatedProvider3;
	private String associatedLocation3;
	private String associatedSpeciality3;
	private String associatedApt3;

	private String isAgeRuleApplied;
	private String underAgePatientUserName;
	private String underAgePatientPassword;

	private String firstNameCarePatient;
	private String lastNameCarePatient;
	private String dobCarePatient;
	private String genderCarePatient;

	private String emailCarePatient;
	private String phoneCarePatient;
	private String zipCarePatient;
	private String careProvider;

	private Boolean isCancelApt = true;
	private Boolean isInsuranceEnabled = false;

	private String oldPatientUserName;
	private String oldPatientPassword;

	private int leadtimeDay;
	private int leadtimeHour;
	private int leadtimeMinute;

	private String currentDate;
	private String currentTimeZone;
	private String businesshourStartTime;
	private String businesshourEndTime;
	private String maxperDay;
	private String gmailUserName;
	private String linkProvider;
	private String linkLocation;
	private boolean lastQuestionOptional=false;
	
	private String memberID;
	private String groupID;
	private String insurancePhone;

	private String ageRuleMonthFirst;
	private String ageRuleMonthSecond;

	private Boolean isCancelButtonPresent;

	private int displaySlotCountLength;
	private Boolean isNextMonthSlotAvail;
	private String nextDateAvaliable;
	private int calanderDaysAvaiable;
	private Boolean isCalanderDateDisplayed;
	private Boolean isProviderImageDisplayed;

	private Boolean isSearchLocationDisplayed;

	private String providerImageAPI;

	private String baseurl_BookRule;
	private String baseurl_LocationRule;
	private String baseurl_AvailableSlots;
	private String baseurl_ScheduleAppointment;

	private String baseurl_APT;

	private String practiceId;
	private String basicURI;
	private String practiceDisplayName;
	private String patientId;

	private String startDateTime;
	private String endDateTime;
	private String apptid;
	private String slotStartTime;
	private String slotEndTime;

	// for REST Assured
	private String accessTokenURL;
	private String accessToken;

	private String urlAnonymousNG;
	private boolean isAnonymousFlow = false;
	private boolean isinsuranceVisible = false;
	private boolean isstartpointPresent = false;
	private boolean resourcetoggleStatus = false;
	private boolean accepttoggleStatus = false;

	private boolean showCancellationRescheduleReason = false;
	private boolean showCancellationReasonPM = false;
	private boolean isFutureApt = false;
	private boolean pcptoggleState = false;

	private String timeMarkValue;
	private String linkProviderURL;

	
	private boolean insuranceDetails = false;

	public Appointment(String providerConfig, String usernameConfig, String passwordConfig, String datetimeConfig,
			String locationConfig, String appointmentConfig, String specialityConfig) {
		this.username = usernameConfig;
		this.password = passwordConfig;
		this.provider = providerConfig;
		this.location = locationConfig;
		this.datetime = datetimeConfig;
		this.appointmenttype = appointmentConfig;
		this.speciality = specialityConfig;
	}

	public Appointment() {}	

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getInsurancePhone() {
		return insurancePhone;
	}

	public void setInsurancePhone(String insurancePhone) {
		this.insurancePhone = insurancePhone;
	}

	public boolean isInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(boolean insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	public String getLinkProviderURL() {
		return linkProviderURL;
	}

	public void setLinkProviderURL(String linkProviderURL) {
		this.linkProviderURL = linkProviderURL;
	}
	
	public boolean isLastQuestionOptional() {
		return lastQuestionOptional;
	}

	public void setLastQuestionOptional(boolean lastQuestionOptional) {
		this.lastQuestionOptional = lastQuestionOptional;
	}

	public String getTimeMarkValue() {
		return timeMarkValue;
	}

	public void setTimeMarkValue(String timeMarkValue) {
		this.timeMarkValue = timeMarkValue;
	}

	public String getLinkLocation() {
		return linkLocation;
	}

	public void setLinkLocation(String linkLocation) {
		this.linkLocation = linkLocation;

	}
		
	public String getSlotStartTime() {
		return slotStartTime;
	}

	public void setSlotStartTime(String slotStartTime) {
		this.slotStartTime = slotStartTime;
	}

	public String getSlotEndTime() {
		return slotEndTime;
	}

	public void setSlotEndTime(String slotEndTime) {
		this.slotEndTime = slotEndTime;
	}

	public String getApptid() {
		return apptid;
	}

	public void setApptid(String apptid) {
		this.apptid = apptid;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getAccessTokenURL() {
		return accessTokenURL;
	}

	public void setAccessTokenURL(String accessTokenURL) {
		this.accessTokenURL = accessTokenURL;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getBasicURI() {
		return basicURI;
	}

	public void setBasicURI(String basicURI) {
		this.basicURI = basicURI;
	}
	
	
	public String getBaseurl_APT() {
		return baseurl_APT;
	}

	public void setBaseurl_APT(String baseurl_APT) {
		this.baseurl_APT = baseurl_APT;
	}

	public String getGenderCarePatient() {
		return genderCarePatient;
	}

	public void setGenderCarePatient(String genderCarePatient) {
		this.genderCarePatient = genderCarePatient;
	}

	public String getCareProvider() {
		return careProvider;
	}

	public void setCareProvider(String careProvider) {
		this.careProvider = careProvider;
	}

	public String getFirstNameCarePatient() {
		return firstNameCarePatient;
	}

	public void setFirstNameCarePatient(String firstNameCarePatient) {
		this.firstNameCarePatient = firstNameCarePatient;
	}

	public String getLastNameCarePatient() {
		return lastNameCarePatient;
	}

	public void setLastNameCarePatient(String lastNameCarePatient) {
		this.lastNameCarePatient = lastNameCarePatient;
	}

	public String getDobCarePatient() {
		return dobCarePatient;
	}

	public void setDobCarePatient(String dobCarePatient) {
		this.dobCarePatient = dobCarePatient;
	}

	public String getEmailCarePatient() {
		return emailCarePatient;
	}

	public void setEmailCarePatient(String emailCarePatient) {
		this.emailCarePatient = emailCarePatient;
	}

	public String getPhoneCarePatient() {
		return phoneCarePatient;
	}

	public void setPhoneCarePatient(String phoneCarePatient) {
		this.phoneCarePatient = phoneCarePatient;
	}

	public String getZipCarePatient() {
		return zipCarePatient;
	}

	public void setZipCarePatient(String zipCarePatient) {
		this.zipCarePatient = zipCarePatient;
	}

	public boolean isPcptoggleState() {
		return pcptoggleState;
	}

	public void setPcptoggleState(boolean pcptoggleState) {
		this.pcptoggleState = pcptoggleState;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	private String urlAnonymous;

	public String getPracticeDisplayName() {
		return practiceDisplayName;
	}

	public void setPracticeDisplayName(String practiceDisplayName) {
		this.practiceDisplayName = practiceDisplayName;
	}



	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

	public String getPopUpMessege() {
		return popUpMessege;
	}

	public void setPopUpMessege(String popUpMessege) {
		this.popUpMessege = popUpMessege;
	}

	public String getAgeRuleMonthFirst() {
		return ageRuleMonthFirst;
	}

	public void setAgeRuleMonthFirst(String ageRuleMonthFirst) {
		this.ageRuleMonthFirst = ageRuleMonthFirst;
	}

	public String getAgeRuleMonthSecond() {
		return ageRuleMonthSecond;
	}

	public void setAgeRuleMonthSecond(String ageRuleMonthSecond) {
		this.ageRuleMonthSecond = ageRuleMonthSecond;
	}

	public boolean isFutureApt() {
		return isFutureApt;
	}

	public void setFutureApt(boolean isFutureApt) {
		this.isFutureApt = isFutureApt;
	}

	public boolean isResourcetoggleStatus() {
		return resourcetoggleStatus;
	}

	public void setResourcetoggleStatus(boolean resourcetoggleStatus) {
		this.resourcetoggleStatus = resourcetoggleStatus;
	}

	public boolean isAccepttoggleStatus() {
		return accepttoggleStatus;
	}

	public void setAccepttoggleStatus(boolean accepttoggleStatus) {
		this.accepttoggleStatus = accepttoggleStatus;
	}

	public String getGmailUserName() {
		return gmailUserName;
	}

	public void setGmailUserName(String gmailUserName) {
		this.gmailUserName = gmailUserName;
	}

	public String getMaxperDay() {
		return maxperDay;
	}

	public void setMaxperDay(String maxperDay) {
		this.maxperDay = maxperDay;
	}

	public String getBusinesshourStartTime() {
		return businesshourStartTime;
	}

	public void setBusinesshourStartTime(String businesshourStartTime) {
		this.businesshourStartTime = businesshourStartTime;
	}

	public String getBusinesshourEndTime() {
		return businesshourEndTime;
	}

	public void setBusinesshourEndTime(String businesshourEndTime) {
		this.businesshourEndTime = businesshourEndTime;
	}

	public String getCurrentTimeZone() {
		return currentTimeZone;
	}

	public void setCurrentTimeZone(String currentTimeZone) {
		this.currentTimeZone = currentTimeZone;
	}

	public String getNextDateAvaliable() {
		return nextDateAvaliable;
	}

	public void setNextDateAvaliable(String nextDateAvaliable) {
		this.nextDateAvaliable = nextDateAvaliable;
	}

	public String getBaseurl_BookRule() {
		return baseurl_BookRule;
	}

	public void setBaseurl_BookRule(String baseurl_BookRule) {
		this.baseurl_BookRule = baseurl_BookRule;
	}

	public String getBaseurl_LocationRule() {
		return baseurl_LocationRule;
	}

	public void setBaseurl_LocationRule(String baseurl_LocationRule) {
		this.baseurl_LocationRule = baseurl_LocationRule;
	}

	public String getBaseurl_AvailableSlots() {
		return baseurl_AvailableSlots;
	}

	public void setBaseurl_AvailableSlots(String baseurl_AvailableSlots) {
		this.baseurl_AvailableSlots = baseurl_AvailableSlots;
	}

	public String getBaseurl_ScheduleAppointment() {
		return baseurl_ScheduleAppointment;
	}

	public void setBaseurl_ScheduleAppointment(String baseurl_ScheduleAppointment) {
		this.baseurl_ScheduleAppointment = baseurl_ScheduleAppointment;
	}

	public boolean isShowCancellationRescheduleReason() {
		return showCancellationRescheduleReason;
	}

	public void setShowCancellationRescheduleReason(boolean showCancellationRescheduleReason) {
		this.showCancellationRescheduleReason = showCancellationRescheduleReason;
	}

	public boolean isShowCancellationReasonPM() {
		return showCancellationReasonPM;
	}

	public void setShowCancellationReasonPM(boolean showCancellationReasonPM) {
		this.showCancellationReasonPM = showCancellationReasonPM;
	}

	public boolean isIsstartpointPresent() {
		return isstartpointPresent;
	}

	public int getLeadtimeDay() {
		return leadtimeDay;
	}

	public void setLeadtimeDay(int leadtimeDay) {
		this.leadtimeDay = leadtimeDay;
	}

	public int getLeadtimeHour() {
		return leadtimeHour;
	}

	public void setLeadtimeHour(int leadtimeHour) {
		this.leadtimeHour = leadtimeHour;
	}

	public int getLeadtimeMinute() {
		return leadtimeMinute;
	}

	public void setLeadtimeMinute(int leadtimeMinute) {
		this.leadtimeMinute = leadtimeMinute;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public void setIsstartpointPresent(boolean isstartpointPresent) {
		this.isstartpointPresent = isstartpointPresent;
	}

	public boolean isIsinsuranceVisible() {
		return isinsuranceVisible;
	}

	public void setIsinsuranceVisible(boolean isinsuranceVisible) {
		this.isinsuranceVisible = isinsuranceVisible;
	}

	public String getUrlAnonymousNG() {
		return urlAnonymousNG;
	}

	public void setUrlAnonymousNG(String urlAnonymousNG) {
		this.urlAnonymousNG = urlAnonymousNG;
	}

	public boolean isAnonymousFlow() {
		return isAnonymousFlow;
	}

	public void setAnonymousFlow(boolean isAnonymousFlow) {
		this.isAnonymousFlow = isAnonymousFlow;
	}

	public String getUrlAnonymous() {
		return urlAnonymous;
	}

	public void setUrlAnonymous(String urlAnonymous) {
		this.urlAnonymous = urlAnonymous;
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

	public String getLinkProvider() {
		return linkProvider;
	}

	public void setLinkProvider(String linkProvider) {
		this.linkProvider = linkProvider;
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

	public String getUrlLinkGen() {
		return urlLinkGen;
	}

	public void setUrlLinkGen(String urlLinkGen) {
		this.urlLinkGen = urlLinkGen;
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

	public String getIsAppointmentPopup() {
		return isAppointmentPopup;
	}

	public void setIsAppointmentPopup(String isAppointmentPopupGW) {
		this.isAppointmentPopup = isAppointmentPopupGW;
	}

	public Boolean getIsNextDayBooking() {
		return isNextDayBooking;
	}

	public void setIsNextDayBooking(Boolean isNextDayBooking) {
		this.isNextDayBooking = isNextDayBooking;
	}

	public String getAppointmentScheduledFromPM() {
		return appointmentScheduledFromPM;
	}

	public void setAppointmentScheduledFromPM(String appointmentScheduledFromPM) {
		this.appointmentScheduledFromPM = appointmentScheduledFromPM;
	}

	public String getCancellationPolicyText() {
		return cancellationPolicyText;
	}

	public void setCancellationPolicyText(String cancellationPolicyText) {
		this.cancellationPolicyText = cancellationPolicyText;
	}

	public Boolean getIsAdminActive() {
		return isAdminActive;
	}

	public void setIsAdminActive(Boolean isAdminActive) {
		this.isAdminActive = isAdminActive;
	}

	public String getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(String appointmentList) {
		this.appointmentList = appointmentList;
	}

	public String getProviderList() {
		return providerList;
	}

	public void setProviderList(String providerList) {
		this.providerList = providerList;
	}

	public String getLocationList() {
		return locationList;
	}

	public void setLocationList(String locationList) {
		this.locationList = locationList;
	}

	public String getAssociatedProvider1() {
		return associatedProvider1;
	}

	public void setAssociatedProvider1(String associatedProvider1) {
		this.associatedProvider1 = associatedProvider1;
	}

	public String getAssociatedLocation1() {
		return associatedLocation1;
	}

	public void setAssociatedLocation1(String associatedLocation1) {
		this.associatedLocation1 = associatedLocation1;
	}

	public String getAssociatedSpeciality1() {
		return associatedSpeciality1;
	}

	public void setAssociatedSpeciality1(String associatedSpeciality1) {
		this.associatedSpeciality1 = associatedSpeciality1;
	}

	public String getAssociatedApt1() {
		return associatedApt1;
	}

	public void setAssociatedApt1(String associatedApt1) {
		this.associatedApt1 = associatedApt1;
	}

	public String getAssociatedProvider2() {
		return associatedProvider2;
	}

	public void setAssociatedProvider2(String associatedProvider2) {
		this.associatedProvider2 = associatedProvider2;
	}

	public String getAssociatedLocation2() {
		return associatedLocation2;
	}

	public void setAssociatedLocation2(String associatedLocation2) {
		this.associatedLocation2 = associatedLocation2;
	}

	public String getAssociatedSpeciality2() {
		return associatedSpeciality2;
	}

	public void setAssociatedSpeciality2(String associatedSpeciality2) {
		this.associatedSpeciality2 = associatedSpeciality2;
	}

	public String getAssociatedApt2() {
		return associatedApt2;
	}

	public void setAssociatedApt2(String associatedApt2) {
		this.associatedApt2 = associatedApt2;
	}

	public String getAssociatedProvider3() {
		return associatedProvider3;
	}

	public void setAssociatedProvider3(String associatedProvider3) {
		this.associatedProvider3 = associatedProvider3;
	}

	public String getAssociatedLocation3() {
		return associatedLocation3;
	}

	public void setAssociatedLocation3(String associatedLocation3) {
		this.associatedLocation3 = associatedLocation3;
	}

	public String getAssociatedSpeciality3() {
		return associatedSpeciality3;
	}

	public void setAssociatedSpeciality3(String associatedSpeciality3) {
		this.associatedSpeciality3 = associatedSpeciality3;
	}

	public String getAssociatedApt3() {
		return associatedApt3;
	}

	public void setAssociatedApt3(String associatedApt3) {
		this.associatedApt3 = associatedApt3;
	}

	public String getIsAgeRuleApplied() {
		return isAgeRuleApplied;
	}

	public void setIsAgeRuleApplied(String isAgeRuleApplied) {
		this.isAgeRuleApplied = isAgeRuleApplied;
	}

	public String getUnderAgePatientUserName() {
		return underAgePatientUserName;
	}

	public void setUnderAgePatientUserName(String underAgePatientUserName) {
		this.underAgePatientUserName = underAgePatientUserName;
	}

	public String getUnderAgePatientPassword() {
		return underAgePatientPassword;
	}

	public void setUnderAgePatientPassword(String underAgePatientPassword) {
		this.underAgePatientPassword = underAgePatientPassword;
	}

	public Boolean getIsCancelApt() {
		return isCancelApt;
	}

	public void setIsCancelApt(Boolean isCancelApt) {
		this.isCancelApt = isCancelApt;
	}

	public Boolean getIsInsuranceEnabled() {
		return isInsuranceEnabled;
	}

	public void setIsInsuranceEnabled(Boolean isInsuranceEnabled) {
		this.isInsuranceEnabled = isInsuranceEnabled;
	}

	public String getOldPatientUserName() {
		return oldPatientUserName;
	}

	public void setOldPatientUserName(String oldPatientUserName) {
		this.oldPatientUserName = oldPatientUserName;
	}

	public String getOldPatientPassword() {
		return oldPatientPassword;
	}

	public void setOldPatientPassword(String oldPatientPassword) {
		this.oldPatientPassword = oldPatientPassword;
	}

	public Boolean getIsCancelButtonPresent() {
		return isCancelButtonPresent;
	}

	public void setIsCancelButtonPresent(Boolean isCancelButtonPresent) {
		this.isCancelButtonPresent = isCancelButtonPresent;
	}

	public int getDisplaySlotCountLength() {
		return displaySlotCountLength;
	}

	public void setDisplaySlotCountLength(int displaySlotCountLength) {
		this.displaySlotCountLength = displaySlotCountLength;
	}

	public Boolean getIsNextMonthSlotAvail() {
		return isNextMonthSlotAvail;
	}

	public void setIsNextMonthSlotAvail(Boolean isNextMonthSlotAvail) {
		this.isNextMonthSlotAvail = isNextMonthSlotAvail;
	}

	public Boolean getIsCalanderDateDisplayed() {
		return isCalanderDateDisplayed;
	}

	public void setIsCalanderDateDisplayed(Boolean isCalanderDateDisplayed) {
		this.isCalanderDateDisplayed = isCalanderDateDisplayed;
	}

	public Boolean getIsProviderImageDisplayed() {
		return isProviderImageDisplayed;
	}

	public void setIsProviderImageDisplayed(Boolean isProviderImageDisplayed) {
		this.isProviderImageDisplayed = isProviderImageDisplayed;
	}

	public Boolean getIsSearchLocationDisplayed() {
		return isSearchLocationDisplayed;
	}

	public void setIsSearchLocationDisplayed(Boolean isSearchLocationDisplayed) {
		this.isSearchLocationDisplayed = isSearchLocationDisplayed;
	}

	public int getCalanderDaysAvaiable() {
		return calanderDaysAvaiable;
	}

	public void setCalanderDaysAvaiable(int calanderDaysAvaiable) {
		this.calanderDaysAvaiable = calanderDaysAvaiable;
	}

	public String getProviderImageAPI() {
		return providerImageAPI;
	}

	public void setProviderImageAPI(String providerImageAPI) {
		this.providerImageAPI = providerImageAPI;
	}

}
