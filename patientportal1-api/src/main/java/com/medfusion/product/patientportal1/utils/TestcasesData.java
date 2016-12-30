package com.medfusion.product.patientportal1.utils;

import java.net.URL;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.pojo.Portal;


public class TestcasesData {

	Portal portalobj = null;
	ExcelSheetReader excelReader = null;

	public TestcasesData(Portal portal) throws Exception {
		String temp = IHGUtil.getEnvironmentType().toString();// which enviroment data need to picked
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); // file name
		excelReader = new ExcelSheetReader(url.getFile());// reading the entire file
		portalobj = (Portal) excelReader.getSingleExcelRow(portal, temp);// filtering the entire file
	}

	public String getTitle() {
		return portalobj.title;
	}

	public String geturl() {

		return portalobj.url;
	}


	public String getUsername() {
		return portalobj.username;
	}


	public String getPassword() {
		return portalobj.password;
	}

	public String getPreferredProvider() {
		return portalobj.preferredprovider;
	}


	public String getFirstName() {
		return portalobj.patientFirstName;
	}


	public String getLastName() {
		return portalobj.patientLastName;
	}


	public String getEmail() {
		return portalobj.patientEmail;
	}


	public String getPhoneNumber() {
		return portalobj.patientPhoneNumber;
	}

	public String getPhoneType() {
		return portalobj.patientPhoneType;
	}


	public String getDob_Month() {
		return portalobj.patientDob_Month;
	}


	public String getDob_Day() {
		return portalobj.patientDob_Day;
	}

	public String getDob_Year() {
		return portalobj.patientDob_Year;
	}

	public String getDOB() {
		return portalobj.patientDob_Month + "/" + portalobj.patientDob_Day + "/" + portalobj.patientDob_Year;
	}

	public String getZip() {
		return portalobj.patientZip;
	}


	public String getSSN() {
		return portalobj.patientSSN;
	}


	public String getSecretQuestion() {
		return portalobj.patientSecretQuestion;
	}

	public String getAnswer() {
		return portalobj.patientAnswer;
	}

	public String getPreferredLocation() {
		return portalobj.patientPreferredLocation;
	}

	public String getPreferredDoctor() {
		Log4jUtil.log("Patient Preferred Provider: " + portalobj.patientPreferredDoctor);
		return portalobj.patientPreferredDoctor;
	}

	public String getAddress() {
		return portalobj.address;
	}

	public String getAddressCity() {
		return portalobj.city;
	}

	public String getAddressState() {
		return portalobj.state;
	}

	public String getFamilyUN() {
		return portalobj.Family_Account_User_Name;
	}

	public String getFamilyPW() {
		return portalobj.Family_Password;
	}

	public String getRelationship() {
		return portalobj.Relationship;
	}

	public String getHealthKeyPracticeUrl() {
		return portalobj.healthKeyPracticeUrl;
	}

	public String getFormsPortal1UrlSecondary() {
		return portalobj.formsPortal1UrlSecondary;
	}

	public String getFormsPIUrlSecondary() {
		return portalobj.formsPIUrlSecondary;
	}

	public String getFormsPIUrlPrimary() {
		return portalobj.formsPIUrlPrimary;
	}

	public String getPreferredLocationBeta() {
		return portalobj.preferredLocationBeta;
	}

	public String getFormsPortal1UrlPrimary() {
		return portalobj.formsPortal1UrlPrimary;
	}

	public String getCCDRestUrlPrimary() {
		return portalobj.ccdRestUrlPrimary;
	}

	public String getCCDRestUrlSecondary() {
		return portalobj.ccdRestUrlSecondary;
	}

	public String getSecureNotificationUser() {
		return portalobj.secureMessageNotificationUserEmail;
	}

	public String getSecureNotificationUserPassword() {
		return portalobj.secureMessageNotificationUserPassword;
	}


}
