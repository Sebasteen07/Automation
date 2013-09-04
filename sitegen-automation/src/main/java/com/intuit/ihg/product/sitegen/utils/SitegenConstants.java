package com.intuit.ihg.product.sitegen.utils;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Constant java for sitegen project
 * @Note :
 */

public class SitegenConstants {

	public final static int FIND_ELEMENTS_MAX_WAIT_SECONDS = 3;
	public final static int SELENIUM_IMPLICIT_WAIT_SECONDS = 30;

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
	public final static String PASSWORD = "intuit123";

	// US6146:- Testcase testPermission
	public final static String PERSONNELTYPE1 = "Nurses";
	public final static String PERSONNELTYPE2 = "Practice Administrator";
	public final static String SOLUTIONS = "Appointment Request";
	public final static String LOCATIONS = "All Locations";
	public final static String USERS = "Auto, Sitegen";

	// US6154 :- Integration Set UP &&
	// US6153 :- Integration Engine
	public final static String EXTERNAL_SYSTEM = "Allscripts Practice Management System";
	public final static String CHANNEL = "Web Services";
	public final static String INTEGRATION_NAME = "Sitegen Automation";
	public final static String REVIEWTYPE = "Never Review";



	//for Custom Forms
	public final static String FORMTYPE = "Custom Health Form";
	public final static String FORMTITLE = "Auto CustomForm";
	public final static String FORMINSTRUCTIONS = "Automation Custom Health Form Instrcutions";
	public final static String FORMMESSAGE = "Automation Custom Health Form Message";
	public final static String FORMCATEGORY = "Insurance and Referrals";
	public final static String FORMQUESTION1 = "Provide Insurance Information";
	public final static String FORMQUESTION2 = "Patient Name"; 
	public final static String FORMQUESTION3 = "Vitals information";
	public final static String FORMANSWERSET1 = "Insurance";
	public final static String FORMANSWERSET2 = "Demographics" ;
	public final static String FORMANSWERSET3 = "Vitals";
	public final static String FORMLAYOUTPAGE = "1";

	//for Merchant AccountSetup for Practise through Paypal
	public final static String PROCESSORVALUE1 = "PayPal";
	public final static String PROCESSORVALUE2 = "QuickBooks Merchant Service";
	public final static String SetUPPracticeUserName = "MedfusionTest";
	public final static String SetUPPracticePassword = "1MedfusionTest";
	public final static String PartnerValue1 = "VeriSign";
	public final static String PartnerValue2 = "Medfusion";
	public final static String PartnerValue3 = "PayPal";
	public final static String expSuccessMessage = "Saved Merchant Account Settings.";

	//for Merchant AccountSetup for Practise through QBMS
	public final static String merchantAcctQBMSToken = "TGT-89-ekYCphhcqO2giValwo8eVA";
	public final static String merchantAcctQBMSTokenForPROD = "SDK-TGT-114-Sf0PrGeE78PRcCpnliGOFg";
	public final static String statusValue1 = "Production";
	public final static String statusValue2 = "Test";

   //Import Staff and Export Staff
	public final static String IMPORTSTAFFFILENAME = "FileImportStaff.csv";
	public final static String FILEIMPORTSTATUS = "Finished";
	public final static String FILEPATH ="testfiles/FileImportStaff.csv";


}
