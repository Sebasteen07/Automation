package com.intuit.ihg.product.community.utils;

import java.net.URL;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class CommunityTestData {

	Community communityobj = null;
	ExcelSheetReader excelReader = null;

	public CommunityTestData(Community community) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		communityobj = (Community) excelReader.getSingleExcelRow(community,
				temp);

	}

	public String getUrl() {
		return communityobj.communityUrl;
	}

	public String getUserName() {
		return communityobj.userName;
	}

	public String getPassword() {
		return communityobj.userPassword;
	}

	public String getUserEmail() {
		return communityobj.userEmail;
	}

	public String getUserPhone() {
		return communityobj.userPhone;
	}

	public String getUserPhoneType() {
		return communityobj.userPhoneType;
	}

	public String getUserBirthMonth() {
		return communityobj.userBirthMonth;
	}

	public String getUserBirthDay() {
		return communityobj.userBirthDay;
	}

	public String getUserBirthYear() {
		return communityobj.userBirthYear;
	}

	public String getUserZipCode() {
		return communityobj.userZipCode;
	}

	public String getUserSocialNumber() {
		return communityobj.userSocialNumber;
	}

	public String getUserSecretQuestion() {
		return communityobj.userSecretQuestion;
	}

	public String getUserAnswer() {
		return communityobj.userAnswer;
	}

	public String getUserLocation() {
		return communityobj.userLocation;
	}

	public String getUserPrefferedDoctor() {
		return communityobj.userPrefferedDoctor;
	}

	public String getUserAddress() {
		return communityobj.userAddress;
	}

	public String getUserCity() {
		return communityobj.userCity;
	}

	public String getUserState() {
		return communityobj.userState;
	}

	public String getAppointmentDoctor() {
		return communityobj.appointmentDoctor;
	}

	public String getAppointmentLocation() {
		return communityobj.appointmentLocation;
	}

	public String getPracticeUrl() {
		return communityobj.practiceUrl;
	}

	public String getPracticePortalAppointmentDoctorUserName() {
		return communityobj.practiceAppointmentDoctorUsername;
	}

	public String getPracticePortalAppointmentDoctorPassword() {
		return communityobj.practiceAppointmentDoctorPassword;
	}

	public String getGmailUName() {
		return communityobj.gmailUName;
	}

	public String getGmailPassword() {
		return communityobj.gmailPassword;

	}

	public String getGmailMessage() {
		return communityobj.gmailMessage;

	}

	public String getAskAQuestionType() {
		return communityobj.askAQuestionType;
	}

	public String getAskAQuestionDoctor() {
		return communityobj.askAQuestionDoctor;
	}

	public String getAskAQuestionLocation() {
		return communityobj.askAQuestionLocation;
	}

	public String getPracticePortalAskAQuestionDoctorUserName() {
		return communityobj.practiceAskAQuestionDoctorUsername;
	}

	public String getPracticePortalAskAQuestionDoctorPassword() {
		return communityobj.practiceAskAQuestionDoctorPassword;
	}

	public String getBillPayPracticeName() {
		return communityobj.billPayPracticeName;
	}

	public String getBillPayPracticeLocation() {
		return communityobj.billPayPracticeLocation;
	}

	public String getPracticePortalBillPayDoctorUserName() {
		return communityobj.practiceBillPayDoctorUsername;
	}

	public String getPracticePortalBillPayDoctorPassword() {
		return communityobj.practiceBillPayDoctorPassword;
	}

	public String getGmailBillPayReceipt() {
		return communityobj.gmailBillPayReceipt;
	}

	public String getMedicine() {
		return communityobj.medicine;
	}

	public String getRxDoctor() {
		return communityobj.rxDoctor;
	}

	public String getPracticePortalRxDoctorUserName() {
		return communityobj.practiceRxDoctorUserName;
	}

	public String getPharmacy() {
		return communityobj.pharmacy;
	}

	public String getPracticePortalRxDoctorPassword() {
		return communityobj.practiceRxDoctorPassword;
	}

	public String getCCDUserName() {
		
		return communityobj.ccdUserName;
	}

	public String getCCDUserPassword() {
		return communityobj.ccdUserPassword;
	}

}
