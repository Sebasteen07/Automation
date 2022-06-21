package com.intuit.ihg.product.integrationplatform.utils;

import java.util.ArrayList;


public class AppointmentData {
	public String URL = "";
	public String UserName = "";
	public String Password = "";
	public String RestUrl = "";
	public String ResponsePath = "";
	public String From = "";
	public String AppointmentPath = "";
	public String OAuthProperty = "";
	public String OAuthKeyStore = "";
	public String OAuthAppToken = "";
	public String OAuthUsername = "";
	public String OAuthPassword = "";
	public String PreferredDoctor = "";
	public String PhoneNumber = "";
	public String PracticeURL = "";
	public String PracticeUserName = "";
	public String PracticePassword = "";
	public String EmailUserName = "";
	public String PracticeName = "";
	public String PatientPracticeId = "";
	public String MFPracticeId = "";
	public String MFPatientId = "";
	
	public String PreviousAppointmentId="";
	public String PATIENT_INVITE_RESTURL="";
	public String PATIENT_EXTERNAL_ID="";
	public String LastName="";
	public String FirstName="";
	public String SecretQuestion="";
	public String SecretAnswer="";
	public String HomePhoneNo="";
	public String BatchSize="";
	public String Status="";
	public String Time = "";
	public String Location = "";
	
	public String Type = "";
	public String Reason = "";
	public String Description = "";
	
	public String appointmentType = "";

	public ArrayList<AppointmentDetail> appointmentDetailList = new ArrayList<AppointmentDetail>();
	public String csvFilePath="";
	
	public String portalURL="";
	public String practiceUserName="";
	public String practicePassword="";
	
	public String AppointmentTypeName="";
	public String AppointmentTypeID="";
	public String AppointmentCategoryName="";
	public String AppointmentCategoryID="";
	public String ActiveFlag="";
	public String Comment="";
	public String AppointmentTypeUrl="";
	public String AppointmentRequestV3URL="";
	public String AppointmentRequestV4URL="";
	public String PATIENT_INVITE_RESTV3URL="";
	public String PATIENT_INVITE_RESTV4URL="";
	public String DCF_ADMINTOOL_URL = "";
}