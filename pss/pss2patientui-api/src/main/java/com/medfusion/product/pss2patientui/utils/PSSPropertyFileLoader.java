// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
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

	// Universal property loading
	public String getProperty(String prop) throws NullPointerException {
		if (property.getProperty(prop) == null)
			throw new NullPointerException("Property " + prop + " not found in the property file.");
		return property.getProperty(prop);
	}

	public void setAppointmentResponseGW(Appointment appointValues) {
		Log4jUtil.log("Loading data for GW appointment related cases..");
		appointValues.setInsuranceVisible(false);
		appointValues.setStartPointPresent(false);
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.gw"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.gw"));
		appointValues.setPracticeDisplayName(property.getProperty("practice.name.gw"));
		appointValues.setTimeMarkValue(property.getProperty("timemark.value.gw"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("agerule.firstparameter.gw"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("agerule.secondparameter.gw"));
		appointValues.setLinkProvider(property.getProperty("link.provider.gw"));
		appointValues.setLinkLocation(property.getProperty("link.location.gw"));
		appointValues.setAppointmenttype(property.getProperty("appointmenttype.gw"));
		appointValues.setDecisionTreeName(property.getProperty("decision.tree.name.gw"));
		appointValues.setDatetime(property.getProperty("datetime.gw"));
		appointValues.setLocation(property.getProperty("location.gw"));
		appointValues.setPassword(property.getProperty("password.gw"));
		appointValues.setProvider(property.getProperty("provider.gw"));
		appointValues.setSpeciality(property.getProperty("speciality.gw"));
		appointValues.setUsername(property.getProperty("username.gw"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.gw"));
		appointValues.setAppointmentFlow(property.getProperty("appointment.flow.gw"));
		appointValues.setFirstTimeUser(property.getProperty("isfirsttime.user.gw"));
		appointValues.setFirstName(property.getProperty("firstname.gw"));
		appointValues.setLastName(property.getProperty("lastname.gw"));
		appointValues.setEmail(property.getProperty("email.gw"));
		appointValues.setDob(property.getProperty("dob.gw"));
		appointValues.setGender(property.getProperty("gender.gw"));
		appointValues.setZipCode(property.getProperty("zipcode.gw"));
		appointValues.setPrimaryNumber(property.getProperty("primarynumber.gw"));
		appointValues.setUrlIPD(property.getProperty("url.idp.gw"));
		appointValues.setCity(property.getProperty("city.gw"));
		appointValues.setStreet(property.getProperty("street.gw"));
		appointValues.setPatientUserName(property.getProperty("patient.username.gw"));
		appointValues.setPatientPassword(property.getProperty("patient.password.gw"));
		appointValues.setPatientPortalURL(property.getProperty("patientportal.url.gw"));
		appointValues.setPatientPortalUserName(property.getProperty("patientportal.username.gw"));
		appointValues.setPatientPortalPassword(property.getProperty("patientportal.password.gw"));

		appointValues.setRetries(property.getProperty("retries.count.count"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.gw"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointment.scheduled.at.pm.gw"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellation.policy.text.gw"));
		appointValues.setAppointmentList(property.getProperty("appointment.list.gw"));
		appointValues.setLocationList(property.getProperty("location.list.gw"));
		appointValues.setProviderList(property.getProperty("provider.list.gw"));
		appointValues.setIsAgeRuleApplied(property.getProperty("is.age.rule.applied.gw"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underage.patient.username.gw"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underage.patient.password.gw"));
		appointValues.setAssociatedProvider1(property.getProperty("associated.provider1.gw"));
		appointValues.setAssociatedLocation1(property.getProperty("associated.location1.gw"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associated.speciality1.gw"));
		appointValues.setAssociatedApt1(property.getProperty("associated.apt1.gw"));
		appointValues.setAssociatedProvider2(property.getProperty("associated.provider2.gw"));
		appointValues.setAssociatedLocation2(property.getProperty("associated.location2.gw"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associated.speciality2.gw"));
		appointValues.setAssociatedApt2(property.getProperty("associated.apt2.gw"));
		appointValues.setAssociatedProvider3(property.getProperty("associated.provider3.gw"));
		appointValues.setAssociatedLocation3(property.getProperty("associated.location3.gw"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associated.speciality3.gw"));
		appointValues.setAssociatedApt3(property.getProperty("associated.apt3.gw"));
		appointValues.setOldPatientUserName(property.getProperty("oldpatient.username.gw"));
		appointValues.setOldPatientPassword(property.getProperty("oldpatient.password.gw"));
		appointValues.setProviderImageAPI(property.getProperty("provider.image.api.gw"));
		appointValues.setGmailUserName(property.getProperty("email.gw"));
		appointValues.setPopUpMessege(property.getProperty("popup.messege"));
		appointValues.setNextAvailiableText(property.getProperty("nextavailable.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.gw"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.gw"));
		appointValues.setSlotValue(property.getProperty("slotvalue.gw"));

		appointValues.setFirstNameCarePatient(property.getProperty("firstname.carepatient.gw"));
		appointValues.setLastNameCarePatient(property.getProperty("lastname.carepatient.gw"));
		appointValues.setDobCarePatient(property.getProperty("dob.carepatient.gw"));
		appointValues.setGenderCarePatient(property.getProperty("gender.carepatient.gw"));
		appointValues.setEmailCarePatient(property.getProperty("email.carepatient.gw"));
		appointValues.setPhoneCarePatient(property.getProperty("phone.care.patient.gw"));
		appointValues.setZipCarePatient(property.getProperty("zip.carepatient.gw"));
		appointValues.setCareProvider(property.getProperty("provider.care.patient.gw"));
		appointValues.setMemberID(property.getProperty("member.id"));
		appointValues.setGroupID(property.getProperty("group.id"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setInsurancePhone(property.getProperty("insrance.phone"));
		appointValues.setMaxperDay(property.getProperty("max.per.day.gw"));
	}

	public void setAdminGW(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("admin.url"));
		adminuser.setUser(property.getProperty("admin.username.gw"));
		adminuser.setPassword(property.getProperty("admin.password.gw"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practice.id.gw.ui"));
		adminuser.setIsInsuranceDisplayed(false);
		adminuser.setIsstartpointPresent(false);

	}

	public void setAdminGE(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("admin.url"));
		adminuser.setUser(property.getProperty("admin.username.ge"));
		adminuser.setPassword(property.getProperty("admin.password.ge"));
		adminuser.setPracticeId(property.getProperty("practice.id.ge.ui"));
		adminuser.setRule(property.getProperty("rule"));
	}

	public void setAdminNG(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("admin.url"));
		adminuser.setUser(property.getProperty("admin.user.name.ng"));
		adminuser.setPassword(property.getProperty("admin.password.ng"));
		adminuser.setPracticeId(property.getProperty("practice.id.ng.ui"));
		adminuser.setRule(property.getProperty("rule"));
	}
	
	public void setAdminNG24249(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("admin.url"));
		adminuser.setUser(property.getProperty("preventsched.admin.user.name.ng"));
		adminuser.setPassword(property.getProperty("preventsched.admin.password.ng"));
		adminuser.setPracticeId(property.getProperty("preventsched.practice.id.ng"));
	}

	public void setAdminAthena(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("admin.url"));
		adminuser.setUser(property.getProperty("adminusernameAthena"));
		adminuser.setPassword(property.getProperty("adminpasswordAthena"));
		adminuser.setPracticeId(property.getProperty("practiceIdAthena"));
		adminuser.setRule(property.getProperty("rule"));
	}

	public void setAdminAT(AdminUser adminuser) {
		Log4jUtil.log("Admin data loading");
		adminuser.setAdminUrl(property.getProperty("admin.url"));
		adminuser.setUser(property.getProperty("admin.user.name.at"));
		Log4jUtil.log("adminusernameAT " + adminuser.getUser());
		adminuser.setPassword(property.getProperty("admin.password.at"));
		Log4jUtil.log("adminpasswordAT " + adminuser.getPassword());
		adminuser.setPracticeId(property.getProperty("practice.id.at.ui"));
		adminuser.setRule(property.getProperty("rule"));
	}

	public void setAppointmentResponseAT(Appointment appointValues) {
		Log4jUtil.log("Loading data for ATHENA appointment related cases..");
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.at"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.at"));
		appointValues.setPracticeDisplayName(property.getProperty("practice.name.at"));
		appointValues.setTimeMarkValue(property.getProperty("time.mark.value.at"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("age.rule.month.first.parameter.at"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("age.rule.month.second.parameter.at"));
		appointValues.setLinkProvider(property.getProperty("link.provider.at"));
		appointValues.setLinkLocation(property.getProperty("link.location.at"));
		appointValues.setMaxperDay(property.getProperty("max.per.day.at"));
		appointValues.setUrlAnonymous(property.getProperty("anonymous.url.at"));
		Log4jUtil.log(appointValues.getUrlAnonymous());
		appointValues.setIsInsuranceEnabled(true);
		appointValues.setAppointmenttype(property.getProperty("appointmenttype.at"));
		appointValues.setDatetime(property.getProperty("date.time.at"));
		appointValues.setLocation(property.getProperty("location.at"));
		appointValues.setPassword(property.getProperty("password.at"));
		appointValues.setProvider(property.getProperty("provider.at"));
		appointValues.setSpeciality(property.getProperty("speciality.at"));
		appointValues.setUsername(property.getProperty("username.at"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.at"));
		appointValues.setAppointmentFlow(property.getProperty("appointment.flow.at"));
		appointValues.setFirstTimeUser(property.getProperty("is.first.time.user.at"));
		appointValues.setFirstName(property.getProperty("ll.first.name.at"));
		appointValues.setLastName(property.getProperty("ll.last.name.at"));
		appointValues.setEmail(property.getProperty("ll.email.at"));
		appointValues.setDob(property.getProperty("ll.dob.at"));
		appointValues.setGender(property.getProperty("ll.gender.at"));
		appointValues.setZipCode(property.getProperty("ll.zip.code.at"));
		appointValues.setPrimaryNumber(property.getProperty("ll.primary.number.at"));
		appointValues.setUrlIPD(property.getProperty("url.idp.at"));
		appointValues.setCity(property.getProperty("city.at"));
		appointValues.setStreet(property.getProperty("street.at"));
		appointValues.setPatientUserName(property.getProperty("patient.username.at"));
		appointValues.setPatientPassword(property.getProperty("patient.password.at"));
		appointValues.setPatientPortalURL(property.getProperty("patient.portal.url.at"));
		appointValues.setPatientPortalUserName(property.getProperty("patient.portal.username.at"));
		appointValues.setPatientPortalPassword(property.getProperty("patient.portal.password.at"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setRetries(property.getProperty("retries.count"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.at"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointment.scheduled.at.pm.at"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellation.policy.text.at"));
		appointValues.setAppointmentList(property.getProperty("appointment.list.at"));
		appointValues.setLocationList(property.getProperty("location.list.at"));
		appointValues.setProviderList(property.getProperty("provider.list.at"));
		appointValues.setIsAgeRuleApplied(property.getProperty("is.age.rule.applied.at"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underage.patient.username.at"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underage.patient.password.at"));
		appointValues.setAssociatedProvider1(property.getProperty("associated.provider1.at"));
		appointValues.setAssociatedLocation1(property.getProperty("associated.location1.at"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associated.speciality1.at"));
		appointValues.setAssociatedApt1(property.getProperty("associated.apt1.at"));
		appointValues.setAssociatedProvider2(property.getProperty("associated.provider2.at"));
		appointValues.setAssociatedLocation2(property.getProperty("associated.location2.at"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associated.speciality2.at"));
		appointValues.setAssociatedApt2(property.getProperty("associated.apt2.at"));
		appointValues.setAssociatedProvider3(property.getProperty("associated.provider3.at"));
		appointValues.setAssociatedLocation3(property.getProperty("associated.location3.at"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associated.speciality3.at"));
		appointValues.setAssociatedApt3(property.getProperty("associated.apt3.at"));
		appointValues.setGmailUserName(property.getProperty("ll.email.at"));
		appointValues.setPopUpMessege(property.getProperty("popup.messege"));
		appointValues.setNextAvailiableText(property.getProperty("nextavailable.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.at"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.at"));
		appointValues.setMemberID(property.getProperty("member.id"));
		appointValues.setGroupID(property.getProperty("group.id"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setInsurancePhone(property.getProperty("insrance.phone"));
		appointValues.setSlotValue(property.getProperty("slotvalue.at"));
	}

	public void setAppointmentResponseGE(Appointment appointValues) {
		Log4jUtil.log("Loading data for GE appointment related cases..");
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.ge"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.ge"));
		appointValues.setPracticeDisplayName(property.getProperty("practice.display.name.ge"));
		appointValues.setTimeMarkValue(property.getProperty("time.mark.value.ge"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("age.rule.month.first.parameter.ge"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("age.rule.month.second.parameter.ge"));
		appointValues.setLinkProvider(property.getProperty("link.provider.ge"));
		appointValues.setLinkLocation(property.getProperty("link.location.ge"));
		appointValues.setMaxperDay(property.getProperty("max.per.day.ge"));
		appointValues.setAppointmenttype(property.getProperty("appointment.type.ge"));
		appointValues.setDecisionTreeName(property.getProperty("decision.tree.name.ge"));
		appointValues.setDatetime(property.getProperty("datetime.ge"));
		appointValues.setLocation(property.getProperty("location.ge"));
		appointValues.setPassword(property.getProperty("password.ge"));
		appointValues.setProvider(property.getProperty("provider.ge"));
		appointValues.setSpeciality(property.getProperty("specialty.ge"));
		appointValues.setUsername(property.getProperty("username.ge"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.ge"));
		appointValues.setAppointmentFlow(property.getProperty("appointment.flow.ge"));
		appointValues.setFirstTimeUser(property.getProperty("is.first.time.user.ge"));
		appointValues.setFirstName(property.getProperty("first.name.ge"));
		appointValues.setLastName(property.getProperty("last.name.ge"));
		appointValues.setEmail(property.getProperty("email.ge"));
		appointValues.setDob(property.getProperty("dob.ge"));
		appointValues.setGender(property.getProperty("gender.ge"));
		appointValues.setZipCode(property.getProperty("zip.code.ge"));
		appointValues.setPrimaryNumber(property.getProperty("primary.number.ge"));
		appointValues.setUrlIPD(property.getProperty("url.idp.ge"));
		appointValues.setCity(property.getProperty("city.ge"));
		appointValues.setStreet(property.getProperty("street.ge"));
		appointValues.setPatientUserName(property.getProperty("patient.username.ge"));
		appointValues.setPatientPassword(property.getProperty("patient.password.ge"));
		appointValues.setPatientPortalURL(property.getProperty("patient.portal.url.ge"));
		appointValues.setPatientPortalUserName(property.getProperty("patient.portal.username.ge"));
		appointValues.setPatientPortalPassword(property.getProperty("patient.portal.password.ge"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setRetries(property.getProperty("retries.count"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.ge"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointment.scheduled.at.pm.ge"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellation.policy.text.ge"));
		appointValues.setAppointmentList(property.getProperty("appointment.list.ge"));
		appointValues.setLocationList(property.getProperty("location.list.ge"));
		appointValues.setProviderList(property.getProperty("provider.list.ge"));
		appointValues.setIsAgeRuleApplied(property.getProperty("is.age.rule.applied"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underage.patient.username.ge"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underage.patient.password.ge"));
		appointValues.setAssociatedProvider1(property.getProperty("associated.provider1.ge"));
		appointValues.setAssociatedLocation1(property.getProperty("associated.location1.ge"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associated.specialty1.ge"));
		appointValues.setAssociatedApt1(property.getProperty("associated.apt1.ge"));
		appointValues.setAssociatedProvider2(property.getProperty("associated.provider2.ge"));
		appointValues.setAssociatedLocation2(property.getProperty("associated.location2.ge"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associated.specialty2.ge"));
		appointValues.setAssociatedApt2(property.getProperty("associated.apt2.ge"));
		appointValues.setAssociatedProvider3(property.getProperty("associated.provider3.ge"));
		appointValues.setAssociatedLocation3(property.getProperty("associated.location3.ge"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associated.specialty3.ge"));
		appointValues.setAssociatedApt3(property.getProperty("associated.apt3.ge"));
		appointValues.setOldPatientUserName(property.getProperty("old.patient.username.ge"));
		appointValues.setOldPatientPassword(property.getProperty("old.patient.password.ge"));
		appointValues.setGmailUserName(property.getProperty("email.ge"));
		appointValues.setPopUpMessege(property.getProperty("popup.messege"));
		appointValues.setNextAvailiableText(property.getProperty("nextavailable.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.ge"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.ge"));
		appointValues.setSlotValue(property.getProperty("slotvalue.ge"));
		appointValues.setFirstNameCarePatient(property.getProperty("first.name.care.patient.ge"));
		appointValues.setLastNameCarePatient(property.getProperty("last.name.care.patient.ge"));
		appointValues.setDobCarePatient(property.getProperty("dob.care.patient.ge"));
		appointValues.setGenderCarePatient(property.getProperty("gender.care.patient.ge"));
		appointValues.setEmailCarePatient(property.getProperty("email.care.patient.ge"));
		appointValues.setPhoneCarePatient(property.getProperty("phone.care.patient.ge"));
		appointValues.setZipCarePatient(property.getProperty("zip.care.patient.ge"));
		appointValues.setCareProvider(property.getProperty("provider.care.patient.ge"));
		appointValues.setMemberID(property.getProperty("member.id"));
		appointValues.setGroupID(property.getProperty("group.id"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setInsurancePhone(property.getProperty("insrance.phone"));
	}

	public void setAppointmentResponseNG(Appointment appointValues) {
		Log4jUtil.log("Loading data for Next Gen appointment related cases..");
		appointValues.setUrlAnonymous(property.getProperty("anonymous.url.ng"));
		Log4jUtil.log(appointValues.getUrlAnonymous());
		appointValues.setIsInsuranceEnabled(true);
		appointValues.setPracticeDisplayName(property.getProperty("practice.name.am"));
		appointValues.setLinkLocationURL(property.getProperty("link.location.url.ng"));
		appointValues.setLinkProviderURL(property.getProperty("link.provider.url.ng"));
		appointValues.setTimeMarkValue(property.getProperty("timeMarkValue.NG"));
		appointValues.setAgeRuleMonthFirst(property.getProperty("age.rule.month.first.parameter.ng"));
		appointValues.setAgeRuleMonthSecond(property.getProperty("age.rule.month.second.parameter.ng"));
		appointValues.setLinkProvider(property.getProperty("link.provider.ng"));
		appointValues.setLinkLocation(property.getProperty("link.location.ng"));
		appointValues.setMaxperDay(property.getProperty("max.per.day.ng"));
		appointValues.setAppointmenttype(property.getProperty("appointment.type.ng"));
		appointValues.setDatetime(property.getProperty("datetime.ng"));
		appointValues.setLocation(property.getProperty("location.ng"));
		appointValues.setDecisionTreeName(property.getProperty("decision.tree.name.ng"));
		appointValues.setPassword(property.getProperty("password.ng"));
		appointValues.setProvider(property.getProperty("provider.ng"));
		appointValues.setSpeciality(property.getProperty("speciality.ng"));
		appointValues.setUsername(property.getProperty("username.ng"));
		appointValues.setUrlLoginLess(property.getProperty("loginless.url.ng"));
		appointValues.setAppointmentFlow(property.getProperty("appointment.flow.ng"));
		appointValues.setFirstTimeUser(property.getProperty("is.first.time.user.ng"));
		appointValues.setFirstName(property.getProperty("firstname.ng"));
		appointValues.setLastName(property.getProperty("lastname.ng"));
		appointValues.setEmail(property.getProperty("email.ng"));
		appointValues.setDob(property.getProperty("dob.ng"));
		appointValues.setGender(property.getProperty("gender.ng"));
		appointValues.setZipCode(property.getProperty("zip.code.ng"));
		appointValues.setPrimaryNumber(property.getProperty("primary.number.ng"));
		appointValues.setUrlIPD(property.getProperty("url.idp.ng"));
		appointValues.setCity(property.getProperty("city.ng"));
		appointValues.setStreet(property.getProperty("street.ng"));
		appointValues.setPatientUserName(property.getProperty("patient.username.ng"));
		appointValues.setPatientPassword(property.getProperty("patient.password.ng"));
		appointValues.setPatientPortalURL(property.getProperty("patient.portal.url.ng"));
		appointValues.setPatientPortalUserName(property.getProperty("patient.portal.username.ng"));
		appointValues.setPatientPortalPassword(property.getProperty("patient.portal.password.ng"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setRetries(property.getProperty("retries.count"));
		appointValues.setIsAppointmentPopup(property.getProperty("is.appointment.popup.ng"));
		appointValues.setAppointmentScheduledFromPM(property.getProperty("appointment.scheduled.at.pm.ng"));
		appointValues.setCancellationPolicyText(property.getProperty("cancellation.policy.text.ng"));
		appointValues.setAppointmentList(property.getProperty("appointment.list.ng"));
		appointValues.setLocationList(property.getProperty("location.list.ng"));
		appointValues.setProviderList(property.getProperty("provider.list.ng"));
		appointValues.setIsAgeRuleApplied(property.getProperty("is.age.rule.applied.ng"));
		appointValues.setUnderAgePatientUserName(property.getProperty("underage.patient.username.ng"));
		appointValues.setUnderAgePatientPassword(property.getProperty("underage.patient.password.ng"));
		appointValues.setAssociatedProvider1(property.getProperty("associated.provider1.ng"));
		appointValues.setAssociatedLocation1(property.getProperty("associated.location1.ng"));
		appointValues.setAssociatedSpeciality1(property.getProperty("associated.specialty1.ng"));
		appointValues.setAssociatedApt1(property.getProperty("associated.apt1.ng"));
		appointValues.setAssociatedProvider2(property.getProperty("associated.provider2.ng"));
		appointValues.setAssociatedLocation2(property.getProperty("associated.location2.ng"));
		appointValues.setAssociatedSpeciality2(property.getProperty("associated.specialty2.ng"));
		appointValues.setAssociatedApt2(property.getProperty("associated.apt2.ng"));
		appointValues.setAssociatedProvider3(property.getProperty("associated.provider3.ng"));
		appointValues.setAssociatedLocation3(property.getProperty("associated.location3.ng"));
		appointValues.setAssociatedSpeciality3(property.getProperty("associated.specialty3.ng"));
		appointValues.setAssociatedApt3(property.getProperty("associated.apt3.ng"));
		appointValues.setOldPatientUserName(property.getProperty("old.patient.username.ng"));
		appointValues.setOldPatientPassword(property.getProperty("old.patient.password.ng"));
		appointValues.setProviderImageAPI(property.getProperty("providerImageAPING"));
		appointValues.setGmailUserName(property.getProperty("email.ng"));
		appointValues.setPopUpMessege(property.getProperty("popup.messege"));
		appointValues.setNextAvailiableText(property.getProperty("nextavailable.text"));
		appointValues.setExcludeSlotFirstValue(property.getProperty("exclude.firstvalue.ng"));
		appointValues.setExcludeSlotSecondValue(property.getProperty("exclude.secondvalue.ng"));
		appointValues.setSlotValue(property.getProperty("slotvalue.ng"));
		appointValues.setSlotSize(property.getProperty("slotsize.ng04"));
		appointValues.setFirstNameCarePatient(property.getProperty("first.name.care.patient.ng"));
		appointValues.setLastNameCarePatient(property.getProperty("last.name.care.patient.ng"));
		appointValues.setDobCarePatient(property.getProperty("dob.care.patient.ng"));
		appointValues.setGenderCarePatient(property.getProperty("gender.care.patient.ng"));
		appointValues.setEmailCarePatient(property.getProperty("email.care.patient.ng"));
		appointValues.setPhoneCarePatient(property.getProperty("phone.care.patient.ng"));
		appointValues.setZipCarePatient(property.getProperty("zip.care.patient.ng"));
		appointValues.setCareProvider(property.getProperty("provider.care.patient.ng"));
		appointValues.setMemberID(property.getProperty("member.id"));
		appointValues.setGroupID(property.getProperty("group.id"));
		appointValues.setEmailSubject(property.getProperty("email.subject"));
		appointValues.setFindInEmail(property.getProperty("find.in.email"));
		appointValues.setInsurancePhone(property.getProperty("insrance.phone"));
		appointValues.setShowCancellationReasonPM(true);
		appointValues.setShowCancellationRescheduleReason(true);
		appointValues.setPreSchedDays(Integer.parseInt(property.getProperty("preventscheddays.ng")));
	}

	public void setAppointmentResponseAthena(Appointment appointValues) {
		appointValues.setUrlAnonymous(property.getProperty("url.anonymous.athena"));
		appointValues.setIsInsuranceEnabled(true);
		appointValues.setAppointmenttype(property.getProperty("appointment.type.athena"));
		appointValues.setLocation(property.getProperty("location.athena"));
		appointValues.setProvider(property.getProperty("provider.at"));
		appointValues.setDob(property.getProperty("dob.athena"));
		appointValues.setFirstName(property.getProperty("first.name.gw"));
		appointValues.setLastName(property.getProperty("last.name.gw"));
		appointValues.setEmail(property.getProperty("email.gw"));
		appointValues.setGender(property.getProperty("gender.ng"));
		appointValues.setZipCode(property.getProperty("zip.code.ng"));
		appointValues.setPrimaryNumber(property.getProperty("primary.number.ng"));
	}

	public void setRestAPIData(Appointment appointValues) {
		appointValues.setBasicURI(property.getProperty("base.url.ng"));
		appointValues.setPracticeId(property.getProperty("practice.id.ng"));
		appointValues.setPracticeDisplayName(property.getProperty("practice.displayname.ng"));
		appointValues.setPatientId(property.getProperty("patient.id.ng"));
		appointValues.setStartDateTime(property.getProperty("start.date.time.ng"));
		appointValues.setEndDateTime(property.getProperty("end.date.time.ng"));
		appointValues.setApptid(property.getProperty("appt.id.ng"));
		appointValues.setFirstName(property.getProperty("first.name.ng"));
		appointValues.setLastName(property.getProperty("first.name.ng"));
		appointValues.setSlotStartTime(property.getProperty("slot.start.time.ng"));
		appointValues.setSlotEndTime(property.getProperty("slot.end.time.ng"));
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
		appointValues.setApptTypeNextAvailableAccessTokenUrl(
				property.getProperty("appt.type.next.available.access.token.url"));
		appointValues
				.setBookByNextAvailableAccessTokenUrl(property.getProperty("book.by.next.available.access.token.url"));
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
		appointValues
				.setSessionConfigurationExpirationTime(property.getProperty("session.configuration.expiration.time"));
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
		appointValues.setAppointmentIdApp(property.getProperty("appointment.detail.id"));
		appointValues.setBookIdApp(property.getProperty("book.detail.id"));
		appointValues.setLocationIdApp(property.getProperty("location.detail.id"));
		appointValues.setPatientDemographicsLastName(property.getProperty("patient.demographics.last.name"));
		appointValues.setPatientDemographicsEmail(property.getProperty("patient.demographics.email"));
		appointValues.setPatientDemographicsGender(property.getProperty("patient.demographics.gender"));
		appointValues.setPatientDemographicsPhoneNo(property.getProperty("patient.demographics.phoneno"));
		appointValues.setPatientDemographicsZipCode(property.getProperty("patient.demographics.zipcode"));
		appointValues.setPatientDemographicsDOB(property.getProperty("patient.demographics.dob"));
		appointValues.setPatientIdPm(property.getProperty("patient.id"));
		appointValues.setPatientIdReschedule(property.getProperty("patient.id.reschedule"));
		appointValues.setPatientIdAvailableSlots(property.getProperty("patient.id.available.slots"));
		appointValues.setPatientIdAppointmentTypesRule(property.getProperty("patient.id.appointment.types.rule"));
		appointValues.setDisplayName(property.getProperty("books.rule.display.name"));
		appointValues.setLocationTimeZoneCode(property.getProperty("location.timezone.code"));
		appointValues.setPastAppointsmentsByPage(property.getProperty("past.appointments.page"));
		appointValues.setPatientType(property.getProperty("patient.type"));
		appointValues.setScheduleDate(property.getProperty("schedule.slot.date"));
		appointValues.setScheduleTime(property.getProperty("schedule.slot.time"));
		appointValues.setRescheduleSlotId(property.getProperty("reschedule.slot.id"));
		appointValues.setRescheduleDateTime(property.getProperty("reschedule.slot.date.time"));
		appointValues.setBookIdAppointment(property.getProperty("book.id"));
		appointValues.setRescheduleAppId(property.getProperty("reschedule.app.id"));
		appointValues.setAppSlotId(property.getProperty("appointment.slot.id"));
	}
}
