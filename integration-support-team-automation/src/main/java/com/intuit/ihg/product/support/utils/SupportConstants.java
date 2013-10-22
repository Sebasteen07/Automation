package com.intuit.ihg.product.support.utils;




public class SupportConstants {
	public final static int FIND_ELEMENTS_MAX_WAIT_SECONDS = 3;
	public final static int SELENIUM_IMPLICIT_WAIT_SECONDS = 30;

	public static final String intuitPatientID = "47426";
	public static final String ccd = "validCCD";
	public static final String CONFIG_FILE = "PropFile";

	/* Host and port where web-services are hosted
	host = 10.136.253.93
	port = 8080*/
	public static final String PROTOCOL = "http";
	public static final String HOST = "10.136.253.93";
	public static final String PORT = "8080";

	/*	# URL for REST APIs 
 	ws_url = /eh/v1/ */

	public static final String WS_URL = "/eh/v1/";
	public static final String DELTA_RESPONSE = "deltaresponse";
	public static final String NODE_RESPONSE = "noderesponse";


	public static final String MedicationName ="Alf";


	//Emergency Contact

	public static final String[] ArrayofFirstName = { "Luke", "Darth", "James", "Leonard" };
	public static final String[] ArrayofLastName = { "Luke", "Darth", "James", "Leonard" };
	public static final String[] ArrayofRelationShip = { "Attorney", "Husband", "Wife" };

	//Add and Remove Diagnoses
	public static final String healthInfoPageTitle = "Medications";
	public static final String diagnoses = "Alzheimer's Disease";

	//Add and Remove Laboratory Test Result
	public static final String testName = "Cholestrol";
	public static final String resultInterpretation = "N";

	//Vital Signs
	public static final String valueForMonth = "01";
	public static final String valueForDate = "01";
	public static final String weight = "80";
	public static final String height = "5";
	public static final String bp1 = "80";
	public static final String bp2 = "18";
	public static final String temperature = "90";
	public static final String pulse = "72";
	public static final String respirationRate = "86";
	public static final String vitalSign = "Jan 01, 2013 - Weight";

}
