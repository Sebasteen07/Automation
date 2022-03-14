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
	private String emailSubject;
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
	private int preSchedDays;
	private boolean maxPerDayStatus;
	private String currentDate;

	private String currentTimeZone;
	private String businesshourStartTime;

	private String businesshourEndTime;
	private String maxperDay;
	private String gmailUserName;
	private String linkProvider;
	private String linkLocation;
	private boolean lastQuestionOptional = false;

	private String memberID;
	private String groupID;
	private String insurancePhone;

	private String ageRuleMonthFirst;
	private String ageRuleMonthSecond;
	private String excludeSlotFirstValue;
	private String excludeSlotSecondValue;
	private String firstHour;
	private String firstMinute;
	private String slotValue;

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
	private String locationId;
	private String resourceId;
	private String slotId;
	private String slotSize;
	private String baseUrlHealth;
	private String apptTypeNextAvailableAccessTokenUrl;
	private String bookByNextAvailableAccessTokenUrl;

	// for REST Assured
	private String accessTokenURL;
	private String accessToken;

	private String urlAnonymousNG;
	private boolean isAnonymousFlow = false;
	private boolean insuranceVisible = false;
	private boolean insuranceAtEnd=false;
	private boolean startPointPresent = false;
	private boolean resourcetoggleStatus = false;
	private boolean accepttoggleStatus = false;
	private boolean preventBacktoBackToggleStatus = false;
	private boolean appointmentStacking= false;

	private boolean showCancellationRescheduleReason = false;
	
	private boolean showCancellationReasonPM = false;
	private boolean isFutureApt = false;
	private boolean pcptoggleState = false;

	private String timeMarkValue;
	private String linkProviderURL;
	private String linkLocationURL;
	private String nextAvailiableText;

	// GE adapter
	private String practiceIdGE;
	private String ssoPatientId;
	private String apptStatusId;
	private String apptStatusPatientId;
	private String healthCheckDatabaseName;
	private String apptStatusStartDateTime;
	private String pastAppointmentsResourceName;
	private String pastAppointmentsLocationname;
	private String upcomingApptresourceName;
	private String upcomingApptlocationName;
	private String demographicsFirstName;
	private String demographicsLastName;
	private String matchPatientFirstName;
	private String matchPatientLastName;
	private String matchPatientId;

	// Patient Modulator
	private String apptDetailGuidId;
	private String anonymousGuidId;
	private String anonymousPracticeId;
	private String linksValueGuidId;
	private String linksDetailGuidId;
	private String linksDetailPatientId;
	private String logoutguidId;
	private String loginlessGuidId;
	private String loginlessPrcticeId;
	private String tokenForLoginlessGuidId;
	private String sessionConfigurationExpirationTime;
	private String practiceFromGuidSsoId;
	private String practiceSsoId;
	private String getImagesBookId;
	private String validateProviderLinkId;
	private String specialtyByRulePracticeId;
	private String specialtyByRulePatientId;
	private String appointmentId;
	private String appointmentPracticeName;
	private String apptTypeNextAvailablePracticeId;
	private String apptTypeNextAvailablePatientId;
	private String booksBynextAvailablePracticeId;
	private String booksBynextAvailablePatientId;
	private String apptDetailDisplayName;
	private String linksValueGuidPracticeName;
	private String timezonePracticeName;
	private String apptDetailLocationDisplayName;
	private String apptDetailAppointmentTypeName;
	private String patientDemographicsFirstName;
	private String validateProviderLinkDisplayName;
	private String locationsByNextAvailableId;
	private String appointmentPatientId;
	private String appointmentLocationName;
	private String apptTypeNextAvailableId;
	private String booksBynextAvailableId;
	
	private String appointmentIdApp;
	private String bookIdApp;
	private String locationIdApp;
	private String patientDemographicsLastName;
	private String patientDemographicsEmail;
	private String patientDemographicsZipCode;
	private String patientDemographicsGender;
	private String patientDemographicsPhoneNo;
	private String patientDemographicsDOB;

	private String patientIdPm;
	private String patientIdReschedule;
	private String patientIdAvailableSlots;
	private String patientIdAppointmentTypesRule;
	private String displayName;

	private String locationTimeZoneCode;
	private String pastAppointsmentsByPage;
	private String patientType;
	private String ScheduleDate;
	private String ScheduleTime;
	private String RescheduleSlotId;
	private String RescheduleDateTime;
	private String Rescheduledate;
	private boolean insuranceDetails = false;
	private String bookIdAppointment;
	private String rescheduleAppId;
	private String appSlotId;

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

	public boolean isAppointmentStacking() {
		return appointmentStacking;
	}

	public void setAppointmentStacking(boolean appointmentStacking) {
		this.appointmentStacking = appointmentStacking;
	}

	public boolean isPreventBacktoBackToggleStatus() {
		return preventBacktoBackToggleStatus;
	}

	public void setPreventBacktoBackToggleStatus(boolean preventBacktoBackToggleStatus) {
		this.preventBacktoBackToggleStatus = preventBacktoBackToggleStatus;
	}

	public int getPreSchedDays() {
		return preSchedDays;
	}

	public void setPreSchedDays(int preSchedDays) {
		this.preSchedDays = preSchedDays;
	}

	public Appointment() {
	}

	public boolean isMaxPerDayStatus() {
		return maxPerDayStatus;
	}

	public void setMaxPerDayStatus(boolean maxPerDayStatus) {
		this.maxPerDayStatus = maxPerDayStatus;
	}

	public boolean isInsuranceAtEnd() {
		return insuranceAtEnd;
	}

	public void setInsuranceAtEnd(boolean insuranceAtEnd) {
		this.insuranceAtEnd = insuranceAtEnd;
	}

	public String getPatientDemographicsDOB() {
		return patientDemographicsDOB;
	}

	public void setPatientDemographicsDOB(String patientDemographicsDOB) {
		this.patientDemographicsDOB = patientDemographicsDOB;
	}

	public String getPatientDemographicsEmail() {
		return patientDemographicsEmail;
	}

	public void setPatientDemographicsEmail(String patientDemographicsEmail) {
		this.patientDemographicsEmail = patientDemographicsEmail;
	}

	public String getPatientDemographicsZipCode() {
		return patientDemographicsZipCode;
	}

	public void setPatientDemographicsZipCode(String patientDemographicsZipCode) {
		this.patientDemographicsZipCode = patientDemographicsZipCode;
	}

	public String getPatientDemographicsGender() {
		return patientDemographicsGender;
	}

	public void setPatientDemographicsGender(String patientDemographicsGender) {
		this.patientDemographicsGender = patientDemographicsGender;
	}

	public String getPatientDemographicsPhoneNo() {
		return patientDemographicsPhoneNo;
	}

	public void setPatientDemographicsPhoneNo(String patientDemographicsPhoneNo) {
		this.patientDemographicsPhoneNo = patientDemographicsPhoneNo;
	}
	
	
	public String getPatientDemographicsLastName() {
		return patientDemographicsLastName;
	}

	public void setPatientDemographicsLastName(String patientDemographicsLastName) {
		this.patientDemographicsLastName = patientDemographicsLastName;
	}


	public String getAppointmentIdApp() {
		return appointmentIdApp;
	}

	public void setAppointmentIdApp(String appointmentIdApp) {
		this.appointmentIdApp = appointmentIdApp;
	}

	public String getBookIdApp() {
		return bookIdApp;
	}

	public void setBookIdApp(String bookIdApp) {
		this.bookIdApp = bookIdApp;
	}

	public String getLocationIdApp() {
		return locationIdApp;
	}

	public void setLocationIdApp(String locationIdApp) {
		this.locationIdApp = locationIdApp;
	}


	public String getSlotValue() {
		return slotValue;
	}

	public void setSlotValue(String slotValue) {
		this.slotValue = slotValue;
	}

	public String getFirstHour() {
		return firstHour;
	}

	public void setFirstHour(String firstHour) {
		this.firstHour = firstHour;
	}

	public String getFirstMinute() {
		return firstMinute;
	}

	public void setFirstMinute(String firstMinute) {
		this.firstMinute = firstMinute;
	}

	public String getNextAvailiableText() {
		return nextAvailiableText;
	}

	public void setNextAvailiableText(String nextAvailiableText) {
		this.nextAvailiableText = nextAvailiableText;
	}

	public String getExcludeSlotFirstValue() {
		return excludeSlotFirstValue;
	}

	public void setExcludeSlotFirstValue(String excludeSlotFirstValue) {
		this.excludeSlotFirstValue = excludeSlotFirstValue;
	}

	public String getExcludeSlotSecondValue() {
		return excludeSlotSecondValue;
	}

	public void setExcludeSlotSecondValue(String excludeSlotSecondValue) {
		this.excludeSlotSecondValue = excludeSlotSecondValue;
	}

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

	public String getLinkLocationURL() {
		return linkLocationURL;
	}

	public void setLinkLocationURL(String linkLocationURL) {
		this.linkLocationURL = linkLocationURL;
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

	public boolean isStartPointPresent() {
		return startPointPresent;
	}

	public void setStartPointPresent(boolean startPointPresent) {
		this.startPointPresent = startPointPresent;
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

	public boolean isInsuranceVisible() {
		return insuranceVisible;
	}

	public void setInsuranceVisible(boolean insuranceVisible) {
		this.insuranceVisible = insuranceVisible;
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

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
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

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getSlotId() {
		return slotId;
	}

	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	public String getSlotSize() {
		return slotSize;
	}

	public void setSlotSize(String slotSize) {
		this.slotSize = slotSize;
	}

	public String getBaseUrlHealth() {
		return baseUrlHealth;
	}

	public void setBaseUrlHealth(String baseUrl_Health) {
		this.baseUrlHealth = baseUrl_Health;
	}

	public String getApptTypeNextAvailableAccessTokenUrl() {
		return apptTypeNextAvailableAccessTokenUrl;
	}

	public void setApptTypeNextAvailableAccessTokenUrl(String apptTypeNextAvailableAccessTokenUrl) {
		this.apptTypeNextAvailableAccessTokenUrl = apptTypeNextAvailableAccessTokenUrl;
	}

	public String getBookByNextAvailableAccessTokenUrl() {
		return bookByNextAvailableAccessTokenUrl;
	}

	public void setBookByNextAvailableAccessTokenUrl(String bookByNextAvailableAccessTokenUrl) {
		this.bookByNextAvailableAccessTokenUrl = bookByNextAvailableAccessTokenUrl;
	}

	public String getHealthCheckDatabaseName() {
		return healthCheckDatabaseName;
	}

	public void setHealthCheckDatabaseName(String healthCheckDatabaseName) {
		this.healthCheckDatabaseName = healthCheckDatabaseName;
	}

	public String getApptStatusId() {
		return apptStatusId;
	}

	public void setApptStatusId(String apptStatusId) {
		this.apptStatusId = apptStatusId;
	}

	public String getApptStatusPatientId() {
		return apptStatusPatientId;
	}

	public void setApptStatusPatientId(String apptStatusPatientId) {
		this.apptStatusPatientId = apptStatusPatientId;
	}

	public String getApptStatusStartDateTime() {
		return apptStatusStartDateTime;
	}

	public void setApptStatusStartDateTime(String apptStatusStartDateTime) {
		this.apptStatusStartDateTime = apptStatusStartDateTime;
	}

	public String getPastAppointmentsResourceName() {
		return pastAppointmentsResourceName;
	}

	public void setPastAppointmentsResourceName(String pastAppointmentsResourceName) {
		this.pastAppointmentsResourceName = pastAppointmentsResourceName;
	}

	public String getPastAppointmentsLocationname() {
		return pastAppointmentsLocationname;
	}

	public void setPastAppointmentsLocationname(String pastAppointmentsLocationname) {
		this.pastAppointmentsLocationname = pastAppointmentsLocationname;
	}

	public String getUpcomingApptresourceName() {
		return upcomingApptresourceName;
	}

	public void setUpcomingApptresourceName(String upcomingApptresourceName) {
		this.upcomingApptresourceName = upcomingApptresourceName;
	}

	public String getUpcomingApptlocationName() {
		return upcomingApptlocationName;
	}

	public void setUpcomingApptlocationName(String upcomingApptlocationName) {
		this.upcomingApptlocationName = upcomingApptlocationName;
	}

	public String getDemographicsFirstName() {
		return demographicsFirstName;
	}

	public void setDemographicsFirstName(String demographicsFirstName) {
		this.demographicsFirstName = demographicsFirstName;
	}

	public String getDemographicsLastName() {
		return demographicsLastName;
	}

	public void setDemographicsLastName(String demographicsLastName) {
		this.demographicsLastName = demographicsLastName;
	}

	public String getMatchPatientFirstName() {
		return matchPatientFirstName;
	}

	public void setMatchPatientFirstName(String matchPatientFirstName) {
		this.matchPatientFirstName = matchPatientFirstName;
	}

	public String getMatchPatientLastName() {
		return matchPatientLastName;
	}

	public void setMatchPatientLastName(String matchPatientLastName) {
		this.matchPatientLastName = matchPatientLastName;
	}

	public String getApptDetailGuidId() {
		return apptDetailGuidId;
	}

	public void setApptDetailGuidId(String apptDetailGuidId) {
		this.apptDetailGuidId = apptDetailGuidId;
	}

	public String getAnonymousGuidId() {
		return anonymousGuidId;
	}

	public void setAnonymousGuidId(String anonymousGuidId) {
		this.anonymousGuidId = anonymousGuidId;
	}

	public String getAnonymousPracticeId() {
		return anonymousPracticeId;
	}

	public void setAnonymousPracticeId(String anonymousPracticeId) {
		this.anonymousPracticeId = anonymousPracticeId;
	}

	public String getLinksValueGuidId() {
		return linksValueGuidId;
	}

	public void setLinksValueGuidId(String linksValueGuidId) {
		this.linksValueGuidId = linksValueGuidId;
	}

	public String getLinksDetailGuidId() {
		return linksDetailGuidId;
	}

	public void setLinksDetailGuidId(String linksDetailGuidId) {
		this.linksDetailGuidId = linksDetailGuidId;
	}

	public String getLinksDetailPatientId() {
		return linksDetailPatientId;
	}

	public void setLinksDetailPatientId(String linksDetailPatientId) {
		this.linksDetailPatientId = linksDetailPatientId;
	}

	public String getLogoutguidId() {
		return logoutguidId;
	}

	public void setLogoutguidId(String logoutguidId) {
		this.logoutguidId = logoutguidId;
	}

	public String getLoginlessGuidId() {
		return loginlessGuidId;
	}

	public void setLoginlessGuidId(String loginlessGuidId) {
		this.loginlessGuidId = loginlessGuidId;
	}

	public String getLoginlessPrcticeId() {
		return loginlessPrcticeId;
	}

	public void setLoginlessPrcticeId(String loginlessPrcticeId) {
		this.loginlessPrcticeId = loginlessPrcticeId;
	}

	public String getTokenForLoginlessGuidId() {
		return tokenForLoginlessGuidId;
	}

	public void setTokenForLoginlessGuidId(String tokenForLoginlessGuidId) {
		this.tokenForLoginlessGuidId = tokenForLoginlessGuidId;
	}

	public String getSessionConfigurationExpirationTime() {
		return sessionConfigurationExpirationTime;
	}

	public void setSessionConfigurationExpirationTime(String sessionConfigurationExpirationTime) {
		this.sessionConfigurationExpirationTime = sessionConfigurationExpirationTime;
	}

	public String getPracticeFromGuidSsoId() {
		return practiceFromGuidSsoId;
	}

	public void setPracticeFromGuidSsoId(String practiceFromGuidSsoId) {
		this.practiceFromGuidSsoId = practiceFromGuidSsoId;
	}

	public String getPracticeSsoId() {
		return practiceSsoId;
	}

	public void setPracticeSsoId(String practiceSsoId) {
		this.practiceSsoId = practiceSsoId;
	}

	public String getGetImagesBookId() {
		return getImagesBookId;
	}

	public void setGetImagesBookId(String getImagesBookId) {
		this.getImagesBookId = getImagesBookId;
	}

	public String getSpecialtyByRulePracticeId() {
		return specialtyByRulePracticeId;
	}

	public void setSpecialtyByRulePracticeId(String specialtyByRulePracticeId) {
		this.specialtyByRulePracticeId = specialtyByRulePracticeId;
	}

	public String getSpecialtyByRulePatientId() {
		return specialtyByRulePatientId;
	}

	public void setSpecialtyByRulePatientId(String specialtyByRulePatientId) {
		this.specialtyByRulePatientId = specialtyByRulePatientId;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getAppointmentPracticeName() {
		return appointmentPracticeName;
	}

	public void setAppointmentPracticeName(String appointmentPracticeName) {
		this.appointmentPracticeName = appointmentPracticeName;
	}

	public String getApptTypeNextAvailablePracticeId() {
		return apptTypeNextAvailablePracticeId;
	}

	public void setApptTypeNextAvailablePracticeId(String apptTypeNextAvailablePracticeId) {
		this.apptTypeNextAvailablePracticeId = apptTypeNextAvailablePracticeId;
	}

	public String getApptTypeNextAvailablePatientId() {
		return apptTypeNextAvailablePatientId;
	}

	public void setApptTypeNextAvailablePatientId(String apptTypeNextAvailablePatientId) {
		this.apptTypeNextAvailablePatientId = apptTypeNextAvailablePatientId;
	}

	public String getBooksBynextAvailablePracticeId() {
		return booksBynextAvailablePracticeId;
	}

	public void setBooksBynextAvailablePracticeId(String booksBynextAvailablePracticeId) {
		this.booksBynextAvailablePracticeId = booksBynextAvailablePracticeId;
	}

	public String getBooksBynextAvailablePatientId() {
		return booksBynextAvailablePatientId;
	}

	public void setBooksBynextAvailablePatientId(String booksBynextAvailablePatientId) {
		this.booksBynextAvailablePatientId = booksBynextAvailablePatientId;
	}

	public String getApptDetailDisplayName() {
		return apptDetailDisplayName;
	}

	public void setApptDetailDisplayName(String apptDetailDisplayName) {
		this.apptDetailDisplayName = apptDetailDisplayName;
	}

	public String getLinksValueGuidPracticeName() {
		return linksValueGuidPracticeName;
	}

	public void setLinksValueGuidPracticeName(String linksValueGuidPracticeName) {
		this.linksValueGuidPracticeName = linksValueGuidPracticeName;
	}

	public String getTimezonePracticeName() {
		return timezonePracticeName;
	}

	public void setTimezonePracticeName(String timezonePracticeName) {
		this.timezonePracticeName = timezonePracticeName;
	}

	public String getApptDetailLocationDisplayName() {
		return apptDetailLocationDisplayName;
	}

	public void setApptDetailLocationDisplayName(String apptDetailLocationDisplayName) {
		this.apptDetailLocationDisplayName = apptDetailLocationDisplayName;
	}

	public String getApptDetailAppointmentTypeName() {
		return apptDetailAppointmentTypeName;
	}

	public void setApptDetailAppointmentTypeName(String apptDetailAppointmentTypeName) {
		this.apptDetailAppointmentTypeName = apptDetailAppointmentTypeName;
	}

	public String getPatientDemographicsFirstName() {
		return patientDemographicsFirstName;
	}

	public void setPatientDemographicsFirstName(String patientDemographicsFirstName) {
		this.patientDemographicsFirstName = patientDemographicsFirstName;
	}

	public String getValidateProviderLinkDisplayName() {
		return validateProviderLinkDisplayName;
	}

	public void setValidateProviderLinkDisplayName(String validateProviderLinkDisplayName) {
		this.validateProviderLinkDisplayName = validateProviderLinkDisplayName;
	}

	public String getLocationsByNextAvailableId() {
		return locationsByNextAvailableId;
	}

	public void setLocationsByNextAvailableId(String locationsByNextAvailableId) {
		this.locationsByNextAvailableId = locationsByNextAvailableId;
	}

	public String getAppointmentPatientId() {
		return appointmentPatientId;
	}

	public void setAppointmentPatientId(String appointmentPatientId) {
		this.appointmentPatientId = appointmentPatientId;
	}

	public String getAppointmentLocationName() {
		return appointmentLocationName;
	}

	public void setAppointmentLocationName(String appointmentLocationName) {
		this.appointmentLocationName = appointmentLocationName;
	}

	public String getApptTypeNextAvailableId() {
		return apptTypeNextAvailableId;
	}

	public void setApptTypeNextAvailableId(String apptTypeNextAvailableId) {
		this.apptTypeNextAvailableId = apptTypeNextAvailableId;
	}

	public String getBooksBynextAvailableId() {
		return booksBynextAvailableId;
	}

	public void setBooksBynextAvailableId(String booksBynextAvailableId) {
		this.booksBynextAvailableId = booksBynextAvailableId;
	}

	public String getValidateProviderLinkId() {
		return validateProviderLinkId;
	}

	public void setValidateProviderLinkId(String validateProviderLinkId) {
		this.validateProviderLinkId = validateProviderLinkId;
	}

	public String getSsoPatientId() {
		return ssoPatientId;
	}

	public void setSsoPatientId(String ssoPatientId) {
		this.ssoPatientId = ssoPatientId;
	}

	public String getPracticeIdGE() {
		return practiceIdGE;
	}

	public void setPracticeIdGE(String practiceIdGE) {
		this.practiceIdGE = practiceIdGE;
	}

	public String getMatchPatientId() {
		return matchPatientId;
	}

	public void setMatchPatientId(String matchPatientId) {
		this.matchPatientId = matchPatientId;
	}
	
	public String getPatientIdPm() {
		return patientIdPm;
	}

	public void setPatientIdPm(String patientIdPm) {
		this.patientIdPm = patientIdPm;
	}

	public String getPatientIdReschedule() {
		return patientIdReschedule;
	}

	public void setPatientIdReschedule(String patientIdReschedule) {
		this.patientIdReschedule = patientIdReschedule;
	}

	public String getPatientIdAvailableSlots() {
		return patientIdAvailableSlots;
	}

	public void setPatientIdAvailableSlots(String patientIdAvailableSlots) {
		this.patientIdAvailableSlots = patientIdAvailableSlots;
	}

	public String getPatientIdAppointmentTypesRule() {
		return patientIdAppointmentTypesRule;
	}

	public void setPatientIdAppointmentTypesRule(String patientIdAppointmentTypesRule) {
		this.patientIdAppointmentTypesRule = patientIdAppointmentTypesRule;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLocationTimeZoneCode() {
		return locationTimeZoneCode;
	}

	public void setLocationTimeZoneCode(String locationTimeZoneCode) {
		this.locationTimeZoneCode = locationTimeZoneCode;
	}

	public String getPastAppointsmentsByPage() {
		return pastAppointsmentsByPage;
	}

	public void setPastAppointsmentsByPage(String pastAppointsmentsByPage) {
		this.pastAppointsmentsByPage = pastAppointsmentsByPage;
	}
	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}
	public String getScheduleDate() {
		return ScheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		ScheduleDate = scheduleDate;
	}
	public String getScheduleTime() {
		return ScheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		ScheduleTime = scheduleTime;
	}

	public String getRescheduleSlotId() {
		return RescheduleSlotId;
	}

	public void setRescheduleSlotId(String rescheduleSlotId) {
		RescheduleSlotId = rescheduleSlotId;
	}

	public String getRescheduleDateTime() {
		return RescheduleDateTime;
	}

	public void setRescheduleDateTime(String rescheduleDateTime) {
		RescheduleDateTime = rescheduleDateTime;
	}

	public String getRescheduledate() {
		return Rescheduledate;
	}

	public void setRescheduledate(String rescheduledate) {
		Rescheduledate = rescheduledate;
	}
	
	public String getBookIdAppointment() {
		return bookIdAppointment;
	}

	public void setBookIdAppointment(String bookIdAppointment) {
		this.bookIdAppointment = bookIdAppointment;
	}

	public String getRescheduleAppId() {
		return rescheduleAppId;
	}

	public void setRescheduleAppId(String rescheduleAppId) {
		this.rescheduleAppId = rescheduleAppId;
	}

	public String getAppSlotId() {
		return appSlotId;
	}

	public void setAppSlotId(String appSlotId) {
		this.appSlotId = appSlotId;
	}
}
