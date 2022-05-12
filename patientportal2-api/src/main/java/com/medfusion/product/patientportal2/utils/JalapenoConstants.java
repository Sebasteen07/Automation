// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.patientportal2.utils;

public class JalapenoConstants {
	public final static String CREDIT_CARD_TYPE = "Visa";
	public final static String CREDIT_CARD_NUMBER = "4111111111111111";
	/*
	 * VISA 4222222222222 (13)Characters VISA 4111111111111111 (16)Characters
	 * VISA 4012888888881881 (16)Characters
	 */
	public final static String YEAR = "2023";

	// Medication Fields
	public final static String MEDICATION_NAME = "Albuterol Sulfate (5 MG/ML) 0.5% Inhalation Nebulization Solution";
	public final static String DOSAGE = "100 MG";

	// Added variables for Prescription test in Integration Platform
	public final static String QUANTITY = "3";
	public final static String NO_OF_REFILLS = "12.00";
	public final static String PRESCRIPTION_NO = "#12345";
	public final static String ADDITIONAL_INFO = "New prescription request";
	public static final String MEDICATION_DOSAGE = "MedicationDosage";

	public static final String MEDICATION_NAME_TAG = "MedicationName";
	public static final String QUANTITY_TAG = "Quantity";
	public static final String REFILL_NUMBER_TAG = "RefillNumber";
	public static final String PRESCRIPTION_NUMBER_TAG = "PrescriptionNumber";
	public static final String ADDITIONAL_INFO_TAG = "AdditionalInformation";

	//new added variables for post prescription API
	public static final String RX_RENEWAL_SUBJECT_TAG = "Prescription Renewal Approved";

	// Discrete Forms
	public final static String STATE = "CA";
	public final static String PRIMARY_PHONE_TYPE = "Home";
	public final static String SEX = "Male";

	public final static String FIRST_NAME = "QA";
	public final static String LAST_NAME = "Automation";
	public final static String RELATION = "Wife";
	public final static String MOBILE = "Mobile";

	public final static String TETANUS = "Within the last 10 years";
	public final static String HPV = "Within the last 2 years";
	public final static String INFLUENZA = "Within the last 6 months";
	public final static String PNEUMONIA = "Within the last 2 years";

	public final static String NEW_PATIENT_ACTIVATION_MESSAGE = "re invited to create a Patient Portal";
	public final static String NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT = "Sign Up!";
	public final static String DATE_OF_BIRTH_MONTH = "January";
	public final static String DATE_OF_BIRTH_MONTH_NO = "1";
	public final static String DATE_OF_BIRTH_DAY = "11";
	public final static String DATE_OF_BIRTH_YEAR = "1987";
	
	//Resend Patient Invites
	public final static String FROM_MONTH = "Mar";
	public final static String FROM_DAY = "1";
	public final static String FROM_YEAR = "2022";
	
	public final static String TO_MONTH = "Jun";
	public final static String TO_DAY = "1";
	public final static String TO_YEAR = "2022";
	
}
