package com.intuit.ihg.product.mobile.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class MobileTestCaseData {


		Mobile mobileobj=null;
		ExcelSheetReader excelReader=null;
		
		public MobileTestCaseData(Mobile mobile) throws Exception {
			// which enviroment data need to picked
			String temp = IHGUtil.getEnvironmentType().toString();
			// file name
			URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
			System.out.println(temp);
			System.out.println("url: "+url);
		
			// reading the entire file
			excelReader = new ExcelSheetReader(url.getFile());
			System.out.println(excelReader.toString());
			//filtering the entire file
			mobileobj=(Mobile) excelReader.getSingleExcelRow(mobile,temp);
		}
		
		public String getUrl() {
			return mobileobj.testUrl;
		}

		public String getUserName() {
			return mobileobj.userName;
		}

		public String getPassword() {
			return mobileobj.userPassword;
		}

		public String getUserEmail() {
			return mobileobj.userEmail;
		}

		public String getUserPhone() {
			return mobileobj.userPhone;
		}

		public String getUserPhoneType() {
			return mobileobj.userPhoneType;
		}

		public String getUserBirthMonth() {
			return mobileobj.userBirthMonth;
		}

		public String getUserBirthDay() {
			return mobileobj.userBirthDay;
		}

		public String getUserBirthYear() {
			return mobileobj.userBirthYear;
		}

		public String getUserZipCode() {
			return mobileobj.userZipCode;
		}

		public String getUserSocialNumber() {
			return mobileobj.userSocialNumber;
		}

		public String getUserSecretQuestion() {
			return mobileobj.userSecretQuestion;
		}

		public String getUserAnswer() {
			return mobileobj.userAnswer;
		}

		public String getUserLocation() {
			return mobileobj.userLocation;
		}

		public String getUserPrefferedDoctor() {
			return mobileobj.userPrefferedDoctor;
		}

		public String getUserAddress() {
			return mobileobj.userAddress;
		}

		public String getUserCity() {
			return mobileobj.userCity;
		}

		public String getUserState() {
			return mobileobj.userState;
		}

		public String getAppointmentDoctor() {
			return mobileobj.appointmentDoctor;
		}

		public String getAppointmentLocation() {
			return mobileobj.appointmentLocation;
		}
		
		public String getAppointmentDoctorSingleLoc() {
			return mobileobj.appointmentDoctorSingleLoc;
		}

		public String getPracticeUrl() {
			return mobileobj.practiceUrl;
		}

		public String getPracticePortalAppointmentDoctorUserName() {
			return mobileobj.practiceAppointmentDoctorUsername;
		}

		public String getPracticePortalAppointmentDoctorPassword() {
			return mobileobj.practiceAppointmentDoctorPassword;
		}

		public String getGmailUName() {
			return mobileobj.gmailUName;
		}

		public String getGmailPassword() {
			return mobileobj.gmailPassword;
		}

		public String getGmailMessage() {
			return mobileobj.gmailMessage;
		}
		
		public String getAskAQuestionPractice() {
			return mobileobj.askAQuestionPractice;
		}

		public String getAskAQuestionType() {
			return mobileobj.askAQuestionType;
		}

		public String getAskAQuestionDoctor() {
			return mobileobj.askAQuestionDoctor;
		}

		public String getAskAQuestionLocation() {
			return mobileobj.askAQuestionLocation;
		}

		public String getPracticePortalAskAQuestionDoctorUserName() {
			return mobileobj.practiceAskAQuestionDoctorUsername;
		}

		public String getPracticePortalAskAQuestionDoctorPassword() {
			return mobileobj.practiceAskAQuestionDoctorPassword;
		}

		public String getBillPayPracticeName() {
			return mobileobj.billPayPracticeName;
		}

		public String getBillPayPracticeLocation() {
			return mobileobj.billPayPracticeLocation;
		}

		public String getPracticePortalBillPayDoctorUserName() {
			return mobileobj.practiceBillPayDoctorUsername;
		}

		public String getPracticePortalBillPayDoctorPassword() {
			return mobileobj.practiceBillPayDoctorPassword;
		}

		public String getGmailBillPayReceipt() {
			return mobileobj.gmailBillPayReceipt;
		}

		public String getMedicine() {
			return mobileobj.medicine;
		}

		public String getRxDoctor() {
			return mobileobj.rxDoctor;
		}

		public String getPracticePortalRxDoctorUserName() {
			return mobileobj.practiceRxDoctorUserName;
		}

		public String getPharmacy() {
			return mobileobj.pharmacy;
		}

		public String getPracticePortalRxDoctorPassword() {
			return mobileobj.practiceRxDoctorPassword;
		}

		public String getCCDUserName() {	
			return mobileobj.ccdUserName;
		}

		public String getCCDUserPassword() {
			return mobileobj.ccdUserPassword;
		}
		
		public String getForgotUserName() {
			return mobileobj.forgotUserName;
		}

		public String getForgotPassword() {
			return mobileobj.forgotPassword;
		}
		
	} 

