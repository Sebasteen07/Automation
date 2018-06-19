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
		appointValues.setEmaiSubject("emaiSubject");
		appointValues.setFindInEmail("findInEmail");
		appointValues.setRetries("retries");
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
		appointValues.setEmaiSubject("emaiSubject");
		appointValues.setFindInEmail("findInEmail");
		appointValues.setRetries("retries");
	}
}