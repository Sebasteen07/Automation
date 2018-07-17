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
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeGW"));
		appointValues.setDatetime(property.getProperty("datetimeGW"));
		appointValues.setLocation(property.getProperty("locationGW"));
		appointValues.setPassword(property.getProperty("passwordGW"));
		appointValues.setProvider(property.getProperty("providerGW"));
		appointValues.setSpeciality(property.getProperty("specialityGW"));
		appointValues.setUsername(property.getProperty("usernameGW"));
		appointValues.setUrlLoginLess(property.getProperty("urlLoginLessGW"));
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
		appointValues.setIsAppointmentPopup(property.getProperty("isAppointmentPopupGW"));
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

	}

	public void setAdminGW(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("adminusernameGW"));
		adminuser.setPassword(property.getProperty("adminpasswordGW"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practiceIdGW"));
	}

	public void setAdminGE(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("adminusernameGE"));
		adminuser.setPassword(property.getProperty("adminpasswordGE"));
		adminuser.setPracticeId(property.getProperty("practiceIdGE"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practiceIdGE"));
	}

	public void setAdminNG(AdminUser adminuser) {
		adminuser.setAdminUrl(property.getProperty("adminurl"));
		adminuser.setUser(property.getProperty("adminusernameNG"));
		adminuser.setPassword(property.getProperty("adminpasswordNG"));
		adminuser.setPracticeId(property.getProperty("practiceIdNG"));
		adminuser.setRule(property.getProperty("rule"));
		adminuser.setPracticeId(property.getProperty("practiceIdNG"));
	}

	public void setAppointmentResponseGE(Appointment appointValues) {
		Log4jUtil.log("Loading data for GE appointment related cases..");
		appointValues.setAppointmenttype(property.getProperty("appointmenttypeGE"));
		appointValues.setDatetime(property.getProperty("datetimeGE"));
		appointValues.setLocation(property.getProperty("locationGE"));
		appointValues.setPassword(property.getProperty("passwordGE"));
		appointValues.setProvider(property.getProperty("providerGE"));
		appointValues.setSpeciality(property.getProperty("specialityGE"));
		appointValues.setUsername(property.getProperty("usernameGE"));
		appointValues.setUrlLoginLess(property.getProperty("urlLoginLessGE"));
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
		appointValues.setIsAppointmentPopup(property.getProperty("isAppointmentPopupGE"));
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

	}
}