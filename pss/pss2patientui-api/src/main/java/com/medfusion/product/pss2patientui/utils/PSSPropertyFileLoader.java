// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSPropertyFileLoader {
	private static Properties property = new Properties();

	public PSSPropertyFileLoader() throws IOException {
		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";
		InputStream url = ClassLoader.getSystemResourceAsStream("data-driven/" + propertyFileNameString);
		property.load(url);
	}

	public void setAppointmentResponseGW(Appointment appointValues) {
		Log4jUtil.log("Loading data for GW appointment related cases..");
		appointValues.setIsinsuranceVisible(false);
		appointValues.setIsstartpointPresent(false);
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.gw"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.gw"));
		appointValues.setTimeMarkValue(property.getProperty("timeMarkValue.GW"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("ageRuleMonthFirstParameterGW"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("ageRuleMonthSecondParameterGW"));
		appointValues.setLinkProvider(property.getProperty("link.provider.gw"));
		appointValues.setLinkLocation(property.getProperty("link.location.gw"));
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeGW"));
		appointValues.setDatetime(property.getProperty("datetimeGW"));
		appointValues.setLocation(property.getProperty("locationGW"));
		appointValues.setPassword(property.getProperty("passwordGW"));
		appointValues.setProvider(property.getProperty("providerGW"));
		appointValues.setSpeciality(property.getProperty("specialityGW"));
		appointValues.setUsername(property.getProperty("usernameGW"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.gw"));
		appointValues.setAppointmentFlow(property.getProperty("appointmentFlowGW"));
		appointValues.setFirstTimeUser(property.getProperty("isFirstTimeUserGW"));
		appointValues.setFirstName(property.getProperty("firstNameGW"));
		appointValues.setLastName(property.getProperty("lastNameGW"));
		appointValues.setEmail(property.getProperty("emailGW"));
		appointValues.setDob(property.getProperty("dobGW"));
		appointValues.setGender(property.getProperty("genderGW"));
		appointValues.setZipCode(property.getProperty("zipCodeGW"));
		appointValues.setPrimaryNumber(property.getProperty("primaryNumberGW"));
		appointValues.setUrlIPD(property.getProperty("urlIDPGW"));
		appointValues.setCity(property.getProperty("cityGW"));
		appointValues.setStreet(property.getProperty("streetGW"));
		appointValues.setPatientUserName(property.getProperty("patientusernameGW"));
		appointValues.setPatientPassword(property.getProperty("patientpasswordGW"));
		appointValues.setPatientPortalURL(property.getProperty("patientportalurlGW"));
		appointValues.setPatientPortalUserName(property.getProperty("patientportalusernameGW"));
		appointValues.setPatientPortalPassword(property.getProperty("patientportalpasswordGW"));
		appointValues.setEmaiSubject(property.getProperty("emaiSubject"));
		appointValues.setFindInEmail(property.getProperty("findInEmail"));
		appointValues.setRetries(property.getProperty("retries"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.gw"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointmentScheduledAtPMGW"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellationPolicyTextGW"));
		appointValues.setAppointmentList(property.getProperty("appointmentListGW"));
		appointValues.setLocationList(property.getProperty("locationListGW"));
		appointValues.setProviderList(property.getProperty("providerListGW"));
		appointValues.setIsAgeRuleApplied(property.getProperty("isAgeRuleAppliedGW"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underAgePatientUNGW"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underAgePatientPasswordGW"));
		appointValues.setAssociatedProvider1(property.getProperty("associatedProvider1GW"));
		appointValues.setAssociatedLocation1(property.getProperty("associatedLocation1GW"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associatedSpeciality1GW"));
		appointValues.setAssociatedApt1(property.getProperty("associatedApt1GW"));
		appointValues.setAssociatedProvider2(property.getProperty("associatedProvider2GW"));
		appointValues.setAssociatedLocation2(property.getProperty("associatedLocation2GW"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associatedSpeciality2GW"));
		appointValues.setAssociatedApt2(property.getProperty("associatedApt2GW"));
		appointValues.setAssociatedProvider3(property.getProperty("associatedProvider3GW"));
		appointValues.setAssociatedLocation3(property.getProperty("associatedLocation3GW"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associatedSpeciality3GW"));
		appointValues.setAssociatedApt3(property.getProperty("associatedApt3GW"));
		appointValues.setOldPatientUserName(property.getProperty("oldPatientUserNameGW"));
		appointValues.setOldPatientPassword(property.getProperty("oldPatientPasswordGW"));
		appointValues.setProviderImageAPI(property.getProperty("providerImageAPIGW"));
		appointValues.setGmailUserName(property.getProperty("gmailUserNameGW"));
		appointValues.setPopUpMessege(property.getProperty("popUpMessege"));
		appointValues.setNextAvailiableText(property.getProperty("next.availi.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.gw"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.gw"));

		appointValues.setFirstNameCarePatient(property.getProperty("firstNameCarePatientGW"));
		appointValues.setLastNameCarePatient(property.getProperty("lastNameCarePatientGW"));
		appointValues.setDobCarePatient(property.getProperty("dobCarePatientGW"));
		appointValues.setGenderCarePatient(property.getProperty("genderCarePatientGW"));
		appointValues.setEmailCarePatient(property.getProperty("emailCarePatientGW"));
		appointValues.setPhoneCarePatient(property.getProperty("phone.care.patient.gw"));
		appointValues.setZipCarePatient(property.getProperty("zipCarePatientGW"));
		appointValues.setCareProvider(property.getProperty("provider.care.patient.gw"));
		appointValues.setMemberID(property.getProperty("memberID"));
		appointValues.setGroupID(property.getProperty("groupID"));
		appointValues.setInsurancePhone(property.getProperty("insrancePhone"));
	}

	public void setAdminGW(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("adminusernameGW"));
		adminuser.setPassword(property.getProperty("adminpasswordGW"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practiceIdGW"));
		adminuser.setIsInsuranceDisplayed(false);
		adminuser.setIsstartpointPresent(false);

	}

	public void setAdminGE(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("admin.username.ge"));
		adminuser.setPassword(property.getProperty("admin.password.ge"));
		adminuser.setPracticeId(property.getProperty("practice.id.ge"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practiceIdGE"));
	}

	public void setAdminNG(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("admin.user.name.ng"));
		adminuser.setPassword(property.getProperty("admin.password.ng"));
		adminuser.setPracticeId(property.getProperty("practice.id.ng"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practiceIdNG"));
	}

	public void setAdminAthena(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("adminusernameAthena"));
		adminuser.setPassword(property.getProperty("adminpasswordAthena"));
		adminuser.setPracticeId(property.getProperty("practiceIdAthena"));
		adminuser.setRule(property.getProperty("rule"));
	}

	public void setAdminAT(AdminUser adminuser) {
		Log4jUtil.log("Admin data loading");
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("adminusernameAt"));
		Log4jUtil.log("adminusernameAT " + adminuser.getUser());
		adminuser.setPassword(property.getProperty("adminpasswordAt"));
		Log4jUtil.log("adminpasswordAT " + adminuser.getPassword());
		adminuser.setPracticeId(property.getProperty("practiceIdAT"));
		adminuser.setRule(property.getProperty("rule"));
	}

	public void setAppointmentResponseAT(Appointment appointValues) {
		Log4jUtil.log("Loading data for ATHENA appointment related cases..");
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.at"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.at"));
		appointValues.setTimeMarkValue(property.getProperty("timeMarkValue.AT"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("ageRuleMonthFirstParameterAT"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("ageRuleMonthSecondParameterAT"));
		appointValues.setLinkProvider(property.getProperty("link.provider.at"));
		appointValues.setLinkLocation(property.getProperty("link.location.at"));
		appointValues.setMaxperDay(property.getProperty("maxperDayAT"));
		appointValues.setUrlAnonymous(property.getProperty("anonymous.url.at"));
		Log4jUtil.log(appointValues.getUrlAnonymous());
		appointValues.setIsInsuranceEnabled(true);
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeAT"));
		appointValues.setDatetime(property.getProperty("datetimeAT"));
		appointValues.setLocation(property.getProperty("locationAT"));
		appointValues.setPassword(property.getProperty("passwordAT"));
		appointValues.setProvider(property.getProperty("providerAT"));
		appointValues.setSpeciality(property.getProperty("specialityAT"));
		appointValues.setUsername(property.getProperty("usernameAT"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.at"));
		appointValues.setAppointmentFlow(property.getProperty("appointmentFlowAT"));
		appointValues.setFirstTimeUser(property.getProperty("isFirstTimeUserAT"));
		appointValues.setFirstName(property.getProperty("firstNameAT"));
		appointValues.setLastName(property.getProperty("lastNameAT"));
		appointValues.setEmail(property.getProperty("emailAT"));
		appointValues.setDob(property.getProperty("dobAT"));
		appointValues.setGender(property.getProperty("genderAT"));
		appointValues.setZipCode(property.getProperty("zipCodeAT"));
		appointValues.setPrimaryNumber(property.getProperty("primaryNumberAT"));
		appointValues.setUrlIPD(property.getProperty("urlIDPAT"));
		appointValues.setCity(property.getProperty("cityAT"));
		appointValues.setStreet(property.getProperty("streetAT"));
		appointValues.setPatientUserName(property.getProperty("patientusernameAT"));
		appointValues.setPatientPassword(property.getProperty("patientpasswordAT"));
		appointValues.setPatientPortalURL(property.getProperty("patientportalurlAT"));
		appointValues.setPatientPortalUserName(property.getProperty("patientportalusernameAT"));
		appointValues.setPatientPortalPassword(property.getProperty("patientportalpasswordAT"));
		appointValues.setEmaiSubject(property.getProperty("emaiSubject"));
		appointValues.setFindInEmail(property.getProperty("findInEmail"));
		appointValues.setRetries(property.getProperty("retries"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.at"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointmentScheduledAtPMAT"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellationPolicyTextAT"));
		appointValues.setAppointmentList(property.getProperty("appointmentListAT"));
		appointValues.setLocationList(property.getProperty("locationListAT"));
		appointValues.setProviderList(property.getProperty("providerListAT"));
		appointValues.setIsAgeRuleApplied(property.getProperty("isAgeRuleAppliedAT"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underAgePatientUNAT"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underAgePatientPasswordAT"));
		appointValues.setAssociatedProvider1(property.getProperty("associatedProvider1AT"));
		appointValues.setAssociatedLocation1(property.getProperty("associatedLocation1AT"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associatedSpeciality1AT"));
		appointValues.setAssociatedApt1(property.getProperty("associatedApt1AT"));
		appointValues.setAssociatedProvider2(property.getProperty("associatedProvider2AT"));
		appointValues.setAssociatedLocation2(property.getProperty("associatedLocation2AT"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associatedSpeciality2AT"));
		appointValues.setAssociatedApt2(property.getProperty("associatedApt2AT"));
		appointValues.setAssociatedProvider3(property.getProperty("associatedProvider3AT"));
		appointValues.setAssociatedLocation3(property.getProperty("associatedLocation3AT"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associatedSpeciality3AT"));
		appointValues.setAssociatedApt3(property.getProperty("associatedApt3AT"));
		appointValues.setOldPatientUserName(property.getProperty("oldPatientUserNameAT"));
		appointValues.setOldPatientPassword(property.getProperty("oldPatientPasswordAT"));
		appointValues.setProviderImageAPI(property.getProperty("providerImageAPIAT"));
		appointValues.setGmailUserName(property.getProperty("emailAT"));
		appointValues.setPopUpMessege(property.getProperty("popUpMessege"));
		appointValues.setNextAvailiableText(property.getProperty("next.availi.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.at"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.at"));
		appointValues.setMemberID(property.getProperty("memberID"));
		appointValues.setGroupID(property.getProperty("groupID"));
		appointValues.setInsurancePhone(property.getProperty("insrancePhone"));

	}

	public void setAppointmentResponseGE(Appointment appointValues) {	
		Log4jUtil.log("Loading data for GE appointment related cases..");
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.ge"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.ge"));
		appointValues.setTimeMarkValue(property.getProperty("timeMarkValue.GE"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("ageRuleMonthFirstParameterGE"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("ageRuleMonthSecondParameterGE"));
		appointValues.setLinkProvider(property.getProperty("link.provider.ge"));
		appointValues.setLinkLocation(property.getProperty("link.location.ge"));

		appointValues.setMaxperDay(property.getProperty("maxperDayGE"));
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeGE"));
		appointValues.setDatetime(property.getProperty("datetimeGE"));
		appointValues.setLocation(property.getProperty("locationGE"));
		appointValues.setPassword(property.getProperty("passwordGE"));
		appointValues.setProvider(property.getProperty("providerGE"));
		appointValues.setSpeciality(property.getProperty("specialityGE"));
		appointValues.setUsername(property.getProperty("usernameGE"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.ng"));
		appointValues.setAppointmentFlow(property.getProperty("appointmentFlowGE"));
		appointValues.setFirstTimeUser(property.getProperty("isFirstTimeUserGE"));
		appointValues.setFirstName(property.getProperty("firstNameGE"));
		appointValues.setLastName(property.getProperty("lastNameGE"));
		appointValues.setEmail(property.getProperty("emailGE"));
		appointValues.setDob(property.getProperty("dobGE"));
		appointValues.setGender(property.getProperty("genderGE"));
		appointValues.setZipCode(property.getProperty("zipCodeGE"));
		appointValues.setPrimaryNumber(property.getProperty("primaryNumberGE"));
		appointValues.setUrlIPD(property.getProperty("urlIDPGE"));
		appointValues.setCity(property.getProperty("cityGE"));
		appointValues.setStreet(property.getProperty("streetGE"));
		appointValues.setPatientUserName(property.getProperty("patientusernameGE"));
		appointValues.setPatientPassword(property.getProperty("patientpasswordGE"));
		appointValues.setPatientPortalURL(property.getProperty("patientportalurlGE"));
		appointValues.setPatientPortalUserName(property.getProperty("patientportalusernameGE"));
		appointValues.setPatientPortalPassword(property.getProperty("patientportalpasswordGE"));
		appointValues.setEmaiSubject(property.getProperty("emaiSubject"));
		appointValues.setFindInEmail(property.getProperty("findInEmail"));
		appointValues.setRetries(property.getProperty("retries"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.ge"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointmentScheduledAtPMGE"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellationPolicyTextGE"));
		appointValues.setAppointmentList(property.getProperty("appointmentListGE"));
		appointValues.setLocationList(property.getProperty("locationListGE"));
		appointValues.setProviderList(property.getProperty("providerListGE"));
		appointValues.setIsAgeRuleApplied(property.getProperty("isAgeRuleAppliedGE"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underAgePatientUNGE"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underAgePatientPasswordGE"));
		appointValues.setAssociatedProvider1(property.getProperty("associatedProvider1GE"));
		appointValues.setAssociatedLocation1(property.getProperty("associatedLocation1GE"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associatedSpeciality1GE"));
		appointValues.setAssociatedApt1(property.getProperty("associatedApt1GE"));
		appointValues.setAssociatedProvider2(property.getProperty("associatedProvider2GE"));
		appointValues.setAssociatedLocation2(property.getProperty("associatedLocation2GE"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associatedSpeciality2GE"));
		appointValues.setAssociatedApt2(property.getProperty("associatedApt2GE"));
		appointValues.setAssociatedProvider3(property.getProperty("associatedProvider3GE"));
		appointValues.setAssociatedLocation3(property.getProperty("associatedLocation3GE"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associatedSpeciality3GE"));
		appointValues.setAssociatedApt3(property.getProperty("associatedApt3GE"));
		appointValues.setOldPatientUserName(property.getProperty("oldPatientUserNameGE"));
		appointValues.setOldPatientPassword(property.getProperty("oldPatientPasswordGE"));
		appointValues.setProviderImageAPI(property.getProperty("providerImageAPIGE"));
		appointValues.setGmailUserName(property.getProperty("emailGE"));
		appointValues.setPopUpMessege(property.getProperty("popUpMessege"));
		appointValues.setNextAvailiableText(property.getProperty("next.availi.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.ge"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.ge"));


		appointValues.setFirstNameCarePatient(property.getProperty("firstNameCarePatientGE"));
		appointValues.setLastNameCarePatient(property.getProperty("lastNameCarePatientGE"));
		appointValues.setDobCarePatient(property.getProperty("dobCarePatientGE"));
		appointValues.setGenderCarePatient(property.getProperty("genderCarePatientGE"));
		appointValues.setEmailCarePatient(property.getProperty("emailCarePatientGE"));
		appointValues.setPhoneCarePatient(property.getProperty("phone.care.patient.ge"));
		appointValues.setZipCarePatient(property.getProperty("zipCarePatientGE"));
		appointValues.setCareProvider(property.getProperty("provider.care.patient.ge"));
		appointValues.setMemberID(property.getProperty("memberID"));
		appointValues.setGroupID(property.getProperty("groupID"));
		appointValues.setInsurancePhone(property.getProperty("insrancePhone"));

	}

	public void setAppointmentResponseNG(Appointment appointValues) {
		Log4jUtil.log("Loading data for Next Gen appointment related cases..");
		appointValues.setUrlAnonymous(property.getProperty("anonymous.url.ng"));
		Log4jUtil.log(appointValues.getUrlAnonymous());
		appointValues.setIsInsuranceEnabled(true);
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.ng"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.ng"));
		appointValues.setTimeMarkValue(property.getProperty("timeMarkValue.NG"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("ageRuleMonthFirstParameterNG"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("ageRuleMonthSecondParameterNG"));
		appointValues.setLinkProvider(property.getProperty("link.provider.ng"));
		appointValues.setLinkLocation(property.getProperty("link.location.ng"));
		appointValues.setMaxperDay(property.getProperty("maxperDayNG"));
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeNG"));
		appointValues.setDatetime(property.getProperty("datetimeNG"));
		appointValues.setLocation(property.getProperty("locationNG"));
		appointValues.setPassword(property.getProperty("passwordNG"));
		appointValues.setProvider(property.getProperty("provider.ng"));
		appointValues.setSpeciality(property.getProperty("specialityNG"));
		appointValues.setUsername(property.getProperty("usernameNG"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.ng"));
		appointValues.setAppointmentFlow(property.getProperty("appointmentFlowNG"));
		appointValues.setFirstTimeUser(property.getProperty("isFirstTimeUserNG"));
		appointValues.setFirstName(property.getProperty("firstNameNG"));
		appointValues.setLastName(property.getProperty("lastNameNG"));
		appointValues.setEmail(property.getProperty("emailNG"));
		appointValues.setDob(property.getProperty("dobNG"));
		appointValues.setGender(property.getProperty("genderNG"));
		appointValues.setZipCode(property.getProperty("zipCodeNG"));
		appointValues.setPrimaryNumber(property.getProperty("primaryNumberNG"));
		appointValues.setUrlIPD(property.getProperty("urlIDPNG"));
		appointValues.setCity(property.getProperty("cityNG"));
		appointValues.setStreet(property.getProperty("streetNG"));
		appointValues.setPatientUserName(property.getProperty("patientusernameNG"));
		appointValues.setPatientPassword(property.getProperty("patientpasswordNG"));
		appointValues.setPatientPortalURL(property.getProperty("patientportalurlNG"));
		appointValues.setPatientPortalUserName(property.getProperty("patientportalusernameNG"));
		appointValues.setPatientPortalPassword(property.getProperty("patientportalpasswordNG"));
		appointValues.setEmaiSubject(property.getProperty("emaiSubject"));
		appointValues.setFindInEmail(property.getProperty("findInEmail"));
		appointValues.setRetries(property.getProperty("retries"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.ng"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointmentScheduledAtPMNG"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellationPolicyTextNG"));
		appointValues.setAppointmentList(property.getProperty("appointmentListNG"));
		appointValues.setLocationList(property.getProperty("locationListNG"));
		appointValues.setProviderList(property.getProperty("providerListNG"));
		appointValues.setIsAgeRuleApplied(property.getProperty("isAgeRuleAppliedNG"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underAgePatientUNNG"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underAgePatientPasswordNG"));
		appointValues.setAssociatedProvider1(property.getProperty("associatedProvider1NG"));
		appointValues.setAssociatedLocation1(property.getProperty("associatedLocation1NG"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associatedSpeciality1NG"));
		appointValues.setAssociatedApt1(property.getProperty("associatedApt1NG"));
		appointValues.setAssociatedProvider2(property.getProperty("associatedProvider2NG"));
		appointValues.setAssociatedLocation2(property.getProperty("associatedLocation2NG"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associatedSpeciality2NG"));
		appointValues.setAssociatedApt2(property.getProperty("associatedApt2NG"));
		appointValues.setAssociatedProvider3(property.getProperty("associatedProvider3NG"));
		appointValues.setAssociatedLocation3(property.getProperty("associatedLocation3NG"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associatedSpeciality3NG"));
		appointValues.setAssociatedApt3(property.getProperty("associatedApt3NG"));
		appointValues.setOldPatientUserName(property.getProperty("oldPatientUserNameNG"));
		appointValues.setOldPatientPassword(property.getProperty("oldPatientPasswordNG"));
		appointValues.setProviderImageAPI(property.getProperty("providerImageAPING"));
		appointValues.setGmailUserName(property.getProperty("emailNG"));
		appointValues.setPopUpMessege(property.getProperty("popUpMessege"));
		appointValues.setNextAvailiableText(property.getProperty("next.availi.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.ng"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.ng"));

		appointValues.setFirstNameCarePatient(property.getProperty("firstNameCarePatientNG"));
		appointValues.setLastNameCarePatient(property.getProperty("lastNameCarePatientNG"));
		appointValues.setDobCarePatient(property.getProperty("dobCarePatientNG"));
		appointValues.setGenderCarePatient(property.getProperty("genderCarePatientNG"));
		appointValues.setEmailCarePatient(property.getProperty("emailCarePatientNG"));
		appointValues.setPhoneCarePatient(property.getProperty("phone.care.patient.ng"));
		appointValues.setZipCarePatient(property.getProperty("zipCarePatientNG"));
		appointValues.setCareProvider(property.getProperty("provider.care.patient.ng"));
		appointValues.setMemberID(property.getProperty("memberID"));
		appointValues.setGroupID(property.getProperty("groupID"));
		appointValues.setInsurancePhone(property.getProperty("insrancePhone"));
		
		appointValues.setPreSchedDays(Integer.parseInt(property.getProperty("preSchedDays")));
	}

	public void setAppointmentResponseAthena(Appointment appointValues) {
		appointValues.setUrlAnonymous(property.getProperty("urlAnonymousAthena"));
		appointValues.setIsInsuranceEnabled(true);
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeAthena"));
		appointValues.setLocation(property.getProperty("locationAthena"));
		appointValues.setProvider(property.getProperty("providerAthena"));
		appointValues.setDob(property.getProperty("dobAthena"));
		appointValues.setFirstName(property.getProperty("firstNameGW"));
		appointValues.setLastName(property.getProperty("lastNameGW"));
		appointValues.setEmail(property.getProperty("emailGW"));
		appointValues.setGender(property.getProperty("genderNG"));
		appointValues.setZipCode(property.getProperty("zipCodeNG"));
		appointValues.setPrimaryNumber(property.getProperty("primaryNumberNG"));
	}

	public void setRestAPIData(Appointment appointValues) {

		appointValues.setBaseurl_BookRule(property.getProperty("baseurl_bookRule_Patient"));
		appointValues.setBaseurl_LocationRule(property.getProperty("baseurl_locationRule_Patient"));
		appointValues.setBaseurl_APT(property.getProperty("baseurl_appttypeRule_Patient"));
		appointValues.setBaseurl_AvailableSlots(property.getProperty("baseurl_availableslots_Patient"));
		appointValues.setBaseurl_ScheduleAppointment(property.getProperty("baseurl_scheduleaptt_Patient"));
		appointValues.setPracticeId("practiceIdNG");
		appointValues.setAccessTokenURL(property.getProperty("accessToken_BaseUrl"));
		appointValues.setBasicURI(property.getProperty("baseurl"));
		appointValues.setPracticeId(property.getProperty("practice.id.NG"));
		appointValues.setPracticeDisplayName(property.getProperty("practiceDisplayNameNG"));
		appointValues.setPatientId(property.getProperty("patientIdNG"));
		appointValues.setStartDateTime(property.getProperty("startDateTime"));
		appointValues.setEndDateTime(property.getProperty("endDateTime"));
		appointValues.setApptid(property.getProperty("apptid"));
		appointValues.setFirstName(property.getProperty("firstNameNG"));
		appointValues.setLastName(property.getProperty("firstNameNG"));
		appointValues.setSlotStartTime(property.getProperty("slotStartTime"));
		appointValues.setSlotEndTime(property.getProperty("slotEndTime"));

	}

	public void setRestAPIDataGE(Appointment appointValues) {
		appointValues.setPracticeId(property.getProperty("practiceid.ge"));
		appointValues.setPatientId(property.getProperty("patientid.ge"));
		appointValues.setBasicURI(property.getProperty("base.url.ge"));
		appointValues.setPracticeDisplayName(property.getProperty("practice.display.name.ge"));
		appointValues.setApptid(property.getProperty("appt.id.ge"));
		appointValues.setPracticeIdGE(property.getProperty("practice.id.ge"));
		appointValues.setSsoPatientId(property.getProperty("sso.patient.id"));
		appointValues.setStartDateTime(property.getProperty("start.date.time.ge"));
		appointValues.setEndDateTime(property.getProperty("end.date.time.ge"));
		appointValues.setResourceId(property.getProperty("resource.id.ge"));
		appointValues.setSlotSize(property.getProperty("slot.size.ge"));
		appointValues.setLocationId(property.getProperty("location.id.ge"));
		appointValues.setApptStatusId(property.getProperty("appt.status.id.ge"));
		appointValues.setApptStatusPatientId(property.getProperty("appt.status.patient.id.ge"));
		appointValues.setSlotId(property.getProperty("slotid.ge"));
		appointValues.setHealthCheckDatabaseName(property.getProperty("health.check.database.name.ge"));
		appointValues.setApptStatusStartDateTime(property.getProperty("appt.status.start.date.time.ge"));
		appointValues.setPastAppointmentsResourceName(property.getProperty("past.appointments.resource.name.ge"));
		appointValues.setPastAppointmentsLocationname(property.getProperty("past.appointments.location.name.ge"));
		appointValues.setUpcomingApptresourceName(property.getProperty("upcoming.appt.resource.name.ge"));
		appointValues.setUpcomingApptlocationName(property.getProperty("upcoming.appt.location.name.ge"));
		appointValues.setDemographicsFirstName(property.getProperty("demographics.first.name.ge"));
		appointValues.setDemographicsLastName(property.getProperty("demographics.last.name.ge"));
		appointValues.setMatchPatientFirstName(property.getProperty("match.patient.first.name.ge"));
		appointValues.setMatchPatientLastName(property.getProperty("match.patient.last.name.ge"));
		appointValues.setMatchPatientId(property.getProperty("match.patient.id"));
	}

	public void setRestAPIDataPatientModulator(Appointment appointValues) {
		appointValues.setPatientId(property.getProperty("patient.id.pm"));
		appointValues.setPracticeId(property.getProperty("practice.id.pm"));
		appointValues.setAccessTokenURL(property.getProperty("access.token.base.url"));
		appointValues.setBasicURI(property.getProperty("base.url.patient.modulator"));
		appointValues.setBaseUrlHealth(property.getProperty("base.url.health"));
		appointValues.setApptTypeNextAvailableAccessTokenUrl(property.getProperty("appt.type.next.available.access.token.url"));
		appointValues.setBookByNextAvailableAccessTokenUrl(property.getProperty("book.by.next.available.access.token.url"));
		appointValues.setApptDetailGuidId(property.getProperty("appt.detail.guid.id"));
		appointValues.setAnonymousPracticeId(property.getProperty("practice.from.guid.anonymous.practice.id"));
		appointValues.setAnonymousGuidId(property.getProperty("anonymous.guid.id"));
		appointValues.setLinksValueGuidId(property.getProperty("links.value.guid.id"));
		appointValues.setLinksDetailGuidId(property.getProperty("links.detail.guid.id"));
		appointValues.setLinksDetailPatientId(property.getProperty("links.detail.patient.id"));
		appointValues.setLogoutguidId(property.getProperty("logout.guid.id"));
		appointValues.setLoginlessGuidId(property.getProperty("loginless.guid.id"));
		appointValues.setLoginlessPrcticeId(property.getProperty("loginless.prctice.id"));
		appointValues.setTokenForLoginlessGuidId(property.getProperty("token.For.loginless.guid.id"));
		appointValues.setSessionConfigurationExpirationTime(property.getProperty("session.configuration.expiration.time"));
		appointValues.setPracticeFromGuidSsoId(property.getProperty("practice.from.guid.sso.id"));
		appointValues.setPracticeSsoId(property.getProperty("practice.sso.id"));
		appointValues.setGetImagesBookId(property.getProperty("get.images.book.id"));
		appointValues.setSpecialtyByRulePatientId(property.getProperty("specialty.by.rule.patient.id"));
		appointValues.setAppointmentId(property.getProperty("appointment.id"));
		appointValues.setAppointmentPracticeName(property.getProperty("appointment.practice.name"));
		appointValues.setApptTypeNextAvailablePracticeId(property.getProperty("appt.type.next.available.practice.id"));
		appointValues.setApptTypeNextAvailablePatientId(property.getProperty("appt.type.next.available.patient.id"));
		appointValues.setBooksBynextAvailablePracticeId(property.getProperty("books.by.next.available.practice.id"));
		appointValues.setBooksBynextAvailablePatientId(property.getProperty("books.by.next.available.patient.id"));
		appointValues.setApptDetailDisplayName(property.getProperty("appt.detail.display.name"));
		appointValues.setLinksValueGuidPracticeName(property.getProperty("links.value.guid.practice.name"));
		appointValues.setTimezonePracticeName(property.getProperty("timezone.practice.name"));
		appointValues.setApptDetailLocationDisplayName(property.getProperty("appt.detail.location.display.name"));
		appointValues.setApptDetailAppointmentTypeName(property.getProperty("appt.detail.appointment.type.name"));
		appointValues.setPatientDemographicsFirstName(property.getProperty("patient.demographics.first.name"));
		appointValues.setValidateProviderLinkDisplayName(property.getProperty("validate.provider.link.display.name"));
		appointValues.setLocationsByNextAvailableId(property.getProperty("locations.by.next.available.id"));
		appointValues.setAppointmentPatientId(property.getProperty("appointment.patient.id"));
		appointValues.setAppointmentLocationName(property.getProperty("appointment.location.name"));
		appointValues.setApptTypeNextAvailableId(property.getProperty("appt.type.next.available.id"));
		appointValues.setBooksBynextAvailableId(property.getProperty("books.by.next.available.id"));
		appointValues.setValidateProviderLinkId(property.getProperty("validate.provider.link.id"));
	}
}
