package com.intuit.ihg.common.utils.dataprovider;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.intuit.ihg.common.utils.IHGUtil;

public class PropertyFileLoader {

	private Properties property = new Properties();

	public String getUrl() {
		return property.getProperty("url");
	}
	
	public String getDifferentUrl() {
		return property.getProperty("differentUrl");
	}

	public String getUserId() {

		return property.getProperty("userid");
	}

	public String getPassword() {

		return property.getProperty("password");
	}

	public String getFirstName() {
		return property.getProperty("FirstName");
	}

	public String getLastName() {

		return property.getProperty("LastName");
	}

	public String getEmail() {

		return property.getProperty("email");
	}

	public String getDOBDay() {
		return property.getProperty("DOBDay");
	}

	public String getDOBMonth() {

		return property.getProperty("DOBMonth");
	}

	public String getDOBYear() {

		return property.getProperty("DOBYear");
	}

	public String getZipCode() {
		return property.getProperty("ZipCode");
	}

	public String getSecretQuestion() {

		return property.getProperty("SecretQuestion");
	}

	public String getSecretAnswer() {
		return property.getProperty("SecretAnswer");
	}

	public String getphoneNumer() {
		return property.getProperty("phoneNumer");
	}

	public String getHealthKey6Of6FirstnameSamePractice() {
		return property.getProperty("HealthKey6Of6FirstnameSamePractice");
	}

	public String getHealthKey6Of6LastnameSamePractice() {

		return property.getProperty("HealthKey6Of6LastnameSamePractice");
	}

	public String getHealthKey6Of6EmailSamePractice() {

		return property.getProperty("HealthKey6Of6EmailSamePractice");
	}

	public String getHealthKey6Of6DOBMonthSamePractice() {
		return property.getProperty("HealthKey6Of6DOBMonthSamePractice");
	}

	public String getHealthKey6Of6DOBDaySamePractice() {

		return property.getProperty("HealthKey6Of6DOBDaySamePractice");
	}

	public String getHealthKey6Of6DOBYearSamePractice() {
		return property.getProperty("HealthKey6Of6DOBYearSamePractice");
	}

	public String getHealthKey6Of6ZipSamePractice() {
		return property.getProperty("HealthKey6Of6ZipSamePractice");
	}
	
	public String getHealthKey6Of6FirstnameInactive() {
		return property.getProperty("HealthKey6Of6FirstnameInactive");
	}

	public String getHealthKey6Of6LastnameInactive() {

		return property.getProperty("HealthKey6Of6LastnameInactive");
	}

	public String getHealthKey6Of6EmailInactive() {

		return property.getProperty("HealthKey6Of6EmailInactive");
	}

	public String getHealthKey6Of6DOBMonthInactive() {
		return property.getProperty("HealthKey6Of6DOBMonthInactive");
	}

	public String getHealthKey6Of6DOBDayInactive() {

		return property.getProperty("HealthKey6Of6DOBDayInactive");
	}

	public String getHealthKey6Of6DOBYearInactive() {
		return property.getProperty("HealthKey6Of6DOBYearInactive");
	}

	public String getHealthKey6Of6ZipInactive() {
		return property.getProperty("HealthKey6Of6ZipInactive");
	}
	
	public String getDoctorLogin() {
		return property.getProperty("doctorLogin");	 
	}
	
	public String getDoctorPassword() {
		return property.getProperty("doctorPassword");	 
	}
	
	public String getPortalUrl() {
		return property.getProperty("portalUrl");	 
	}
	
	public String getHarakiriUrl() {
		return property.getProperty("harakiriUrl");
	}
	
	public String getPractice2Url() {
		return property.getProperty("practice2Url");
	}
	
	public String getCCDPatientUsername() {
		return property.getProperty("ccdPatientUsername");
	}
	public String getRcmStatementRest() {
		return property.getProperty("rcmStatementRest");
	}
	public String getStatementBalanceDue() {
		return property.getProperty("statementBalanceDue");
	}
	public PropertyFileLoader() throws IOException {

		String env = IHGUtil.getEnvironmentType().toString();
		String propertyFileNameString = env + ".properties";

		URL url = ClassLoader.getSystemResource("data-driven/" + propertyFileNameString);
		FileReader inputStream = new FileReader(url.getFile());
		property.load(inputStream);

	}

}
