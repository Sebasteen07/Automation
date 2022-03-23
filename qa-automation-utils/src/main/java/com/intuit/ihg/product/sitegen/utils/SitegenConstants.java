//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.sitegen.utils;


public class SitegenConstants {
	// US6144:- Testcase testLocation
	public final static String PRACTICENAME = "SiteGenAutomation";
	public final static String ADDRESS = "San Diego";
	public final static String CITY = "San Diego";
	public final static String STATE = "California";
	public final static String COUNTRY = "United States";
	public final static String ZIPCODE = "92274";
	public final static String TELEPHONE = "9589631234";
	public final static String EMAIL = "ihgqaAutoamtion@gmail.com";
	public final static String CONTACT = "ihgqaAutoamtion";

	// US6145:- Testcase testPhysician
	public final static String FIRSTNAME = "SiteGen";
	public final static String LASTNAME = "Auto";
	public final static String TITLE = "DR";
	public final static String DEANUMBER = "123456";
	public final static String PASSWORD = "ENC(g5I5yyL/AqZYQAVx5uLGM6uXTboyMqwc)";

	// US6146:- Testcase testPermission
	public final static String PERSONNELTYPE1 = "Nurses";
	public final static String PERSONNELTYPE2 = "Practice Administrator";
	public final static String SOLUTIONS = "Appointment Request";
	public final static String LOCATIONS = "All Locations";
	public final static String USERS = "All Users";

	// US6154 :- Integration Set UP &&
	// US6153 :- Integration Engine
	public final static String EXTERNAL_SYSTEM = "Allscripts Practice Management System";
	public final static String CHANNEL = "Web Services";
	public final static String INTEGRATION_NAME = "Sitegen Automation";
	public final static String REVIEWTYPE = "Never Review";

	// for Custom Forms
	public final static String FORMTYPE = "Custom Health Form";
	public final static String FORMTITLE = "Auto CustomForm";
	public final static String FORMINSTRUCTIONS = "Automation Custom Health Form Instructions";
	public final static String FORMMESSAGE = "Automation Custom Health Form Message";
	public final static String FORMCATEGORY = "Insurance and Referrals";
	public final static String FORMCATEGORY2 = "Demographics";
	public final static String FORMCATEGORY3 = "Vital Signs";
	public final static String FORMQUESTION1 = "Provide Insurance Information";
	public final static String FORMQUESTION2 = "Provide Demographics information";
	public final static String FORMQUESTION3 = "Vitals information";
	public final static String FORMANSWERSET1 = "Insurance";
	public final static String FORMANSWERSET2 = "Demographics";
	public final static String FORMANSWERSET3 = "Vitals";
	public final static String FORMLAYOUTPAGE = "3";
	public final static String FORMLAYOUTPAGE2 = "1";
	public final static String FORMLAYOUTPAGE3 = "2";
	public final static String FORMLAYOUTPAGE0 = "0";

	// for Merchant AccountSetup for Practise through Paypal
	public final static String PROCESSORVALUE1 = "PayPal";
	public final static String PROCESSORVALUE2 = "QuickBooks Merchant Service";
	public final static String SetUPPracticeUserName = "MedfusionTest";
	public final static String SetUPPracticePassword = "ENC(XQ1EJDae9FrQE9O3OJYpF8A6wW0pMG3j)";
	public final static String PartnerValue1 = "VeriSign";
	public final static String PartnerValue2 = "Medfusion";
	public final static String PartnerValue3 = "PayPal";
	public final static String expSuccessMessage = "Saved Merchant Account Settings.";

	// for Merchant AccountSetup for Practise through QBMS
	public final static String merchantAcctQBMSToken = "TGT-89-ekYCphhcqO2giValwo8eVA";
	public final static String merchantAcctQBMSTokenForPROD = "SDK-TGT-114-Sf0PrGeE78PRcCpnliGOFg";
	public final static String statusValue1 = "Production";
	public final static String statusValue2 = "Test";

	// Import Staff and Export Staff
	public final static String IMPORTSTAFFFILENAME = "FileImportStaff.csv";
	public final static String FILEIMPORTSTATUS = "Finished";
	public final static String FILEPATH = "target/test-classes/testfiles/FileImportStaff.csv";

	// Custom Forms item types
	public final static String CUSTOMFORM_ITEM_TYPE1 = "Question";
	public final static String CUSTOMFORM_ITEM_TYPE2 = "Heading";
	public final static String QUESTION_TYPE1 = "Single-Line Text";
	public final static String QUESTION_TYPE2 = "Multi-Line Text";
	public final static String QUESTION_TYPE3 = "Multi-Select";
	public final static String QUESTION_TYPE4 = "Single Select";
	public final static String OPTION1 = "Yes";
	public final static String OPTION2 = "No";
	public final static String HEADINGTITLE = "A Custom Form";
	public final static String AVAILABLE_ANSWERS = "1,2,3,4";
	public final static String AVAILABLE_ANSWERS2 = "Happy, Anxious, Depressed, Tired, Something else";
	public final static String AVAILABLE_ANSWERS3 = "Fever, Fatigue,Cough";
	public final static String AVAILABLE_ANSWERS4 = "1,2,3,4,5,6,7,8,9,10";
	public final static String AVAILABLE_ANSWERS5 = "Mild, Moderate, Severe";
	public final static String QUESTIONTITLE1 = "How do you feel?";
	public final static String QUESTIONTITLE2 = "How do you feel on a scale from 1 to 4?";
	public final static String QUESTIONTITLE3 = "What is your favorite color?";
	public final static String QUESTIONTITLE4 = "Describe your problem:";
	public final static String QUESTIONTITLE5 = "Your Favourite Color";
	public final static String QUESTIONTITLE6 = "Why do you like that color?";
	public final static String QUESTIONTITLE7 = "Do you have any of these symptoms?";
	public final static String QUESTIONTITLE8 = "If yes, choose its severity?.";
	public final static String CUSTOMFORMNAME = "Auto Form";
	public final static String PATIENT_FIRSTNAME = "AutoPatient";
	public final static String PATIENT_LASTTNAME = "medfusion";
	public final static String PATIENT_DOBMONTH = "January";
	public final static String PATIENT_DOBDAY = "1";
	public final static String PATIENT_DOBYEAR = "1987";

	public final static String DISCRETEFORMNAME = "Automation Discrete Form";
	public final static String DISCRETEFORM_PATIENT_FIRSTNAME = "";
	public final static String DISCRETEFORM_PATIENT_LASTTNAME = "";
	public final static String DISCRETEFORM_PATIENT_DOBMONTH = "January";
	public final static String DISCRETEFORM_PATIENT_DOBDAY = "1";
	public final static String DISCRETEFORM_PATIENT_DOBYEAR = "1987";
	public final static String DISCRETEFORM_WELCOME_MESSAGE = "New welcome message for patien.";

	public final static String CALCULATED_PHQ9_FORM = "Patient Health Questionnaire-9 (PHQ-9)";
	public final static String CALCULATED_ADHD_FORM = "Adult ADHD Self-Report Scale (ASRS-v1.1) Symptom Checklist";
	public final static String CALCULATED_PHQ2_FORM = "Patient Health Questionnaire-2 (PHQ-2)";

	public final static String PDF_CCD_FORM = "Form output test";
	public final static String PRACTICE_FORM = "Form for Practice view test";
	public final static String SPECIAL_CHARS_FORM = "Quotation mark \" custom form";
	public final static String FORM_EXPORT_IMPORT = "FormExportImport";
	public final static String FORM_EGQ_NAME = "EGQ Form";
	public final static String FORM_FUP_SG = "SG FUP Form";

	public final static String FORMS_REGISTRATION_FORM_INITIAL_NAME = "General Registration and Health History";
	public final static String FORMS_CUSTOM_FORM_INITIAL_NAME = "Custom Form";

	// Add new Pharmacy
	public final static String PHARMACYNAME = "Automation Pharmacy";
	public final static String PHARMACYLOCATION = "Automation-Location-1";
}
