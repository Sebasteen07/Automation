//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.patientportal2.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.patientportal2.pojo.PortalBasic;

//TODO: never used? to delete?
public class JalapenoTestCaseData {

		PortalBasic jalapeno = null;
		ExcelSheetReader excelReader = null;

		public JalapenoTestCaseData(PortalBasic portal) throws Exception {
				String temp = IHGUtil.getEnvironmentType().toString();// which enviroment data need to picked
				URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); // file name
				excelReader = new ExcelSheetReader(url.getFile());// reading the entire file
				portal = (PortalBasic) excelReader.getSingleExcelRow(portal, temp);// filtering the entire file
		}

		public String getTitle() {
				return jalapeno.title;
		}

		public String geturl() {

				return jalapeno.url;
		}


		public String getUsername() {
				return jalapeno.username;
		}


		public String getPassword() {
				return jalapeno.password;
		}

		public String getpreferredprovider() {
				return jalapeno.preferredprovider;
		}


		public String getFirstName() {
				return jalapeno.patientFirstName;
		}


		public String getLastName() {
				return jalapeno.patientLastName;
		}


		public String getEmail() {
				return jalapeno.patientEmail;
		}


		public String getPhoneNumber() {
				return jalapeno.patientPhoneNumber;
		}

		public String getPhoneType() {
				return jalapeno.patientPhoneType;
		}


		public String getDob_Month() {
				return jalapeno.patientDob_Month;
		}


		public String getDob_Day() {
				return jalapeno.patientDob_Day;
		}

		public String getDob_Year() {
				return jalapeno.patientDob_Year;
		}

		public String getZip() {
				return jalapeno.patientZip;
		}


		public String getSSN() {
				return jalapeno.patientSSN;
		}


		public String getSecretQuestion() {
				return jalapeno.patientSecretQuestion;
		}

		public String getAnswer() {
				return jalapeno.patientAnswer;
		}

		public String getPreferredLocation() {
				return jalapeno.patientPreferredLocation;
		}

		public String getPreferredDoctor() {
				Log4jUtil.log("Patient Preferred Provider: " + jalapeno.patientPreferredDoctor);
				return jalapeno.patientPreferredDoctor;
		}

		public String getAddress() {
				return jalapeno.address;
		}

		public String getAddressCity() {
				return jalapeno.city;
		}

		public String getAddressState() {
				return jalapeno.state;
		}

		public String getFamilyUN() {
				return jalapeno.Family_Account_User_Name;
		}

		public String getFamilyPW() {
				return jalapeno.Family_Password;
		}

		public String getRelationship() {
				return jalapeno.Relationship;
		}

		public String getHealthKeyPracticeUrl() {
				return jalapeno.healthKeyPracticeUrl;
		}

		public String getFormsUrl() {
				return jalapeno.formsUrl;
		}

		public String getPIFormsUrl() {
				return jalapeno.formsPIUrl;
		}

		public String getPIFormsAltUrl() {

				return jalapeno.formsAltPIUrl;
		}

		public String getPreferredLocationBeta() {
				return jalapeno.preferredLocationBeta;
		}

		public String getFormsAltUrl() {
				return jalapeno.formsAltUrl;
		}

		public String getRestUrl() {
				return jalapeno.restUrl;
		}
}
