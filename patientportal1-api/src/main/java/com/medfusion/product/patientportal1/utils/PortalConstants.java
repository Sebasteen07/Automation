package com.medfusion.product.patientportal1.utils;

public class PortalConstants {
	public final static int FIND_ELEMENTS_MAX_WAIT_SECONDS = 3;
	public final static int SELENIUM_IMPLICIT_WAIT_SECONDS = 30;

	/*
	 * TO REFACTOR: Constant naming should be consistent, ideally according Java standards - Use ALL_UPPER_CASE for your named constants, separating words with
	 * the underscore character.
	 */
	public final static String PreferredTimeFrame = "First Available";
	public final static String PreferredDay = "Monday";
	public final static String ChoosePreferredTime = "Morning";
	public final static String ApptReason = "Test";
	public final static String WhichIsMoreImportant = "Specific Provider";
	public final static String RelationWithPatient = "Child";
	public final static String CardholderName = "Luke Rigth";
	public final static String CreditCardType = "Visa";
	public final static String CreditCardNumber = "4111111111111111";
	/*
	 * VISA 4222222222222 (13)Characters VISA 4111111111111111 (16)Characters VISA 4012888888881881 (16)Characters
	 */
	public final static String Year = "2023";
	public final static String Month = "01";
	public final static String InsuranceType = "Primary Health Insurance";
	public final static String InsuranceName = "UnitedHealthCare";
	public final static String InsuranceRelation = "Self";

	public final static String Symptom = "Cough";
	public final static String EMAIL_ForgotPassword_SUBJECT = "Password reset response from %s";
	public final static String PORTAL_TITLE = "IHGQA Automation NonIntegrated";
	public final static String TextInForgotPasswordEmailLink = "Confirm my password";

	// Medication Fields
	public final static String MedicineName1 = "FiberCon 1 Tablet";
	public final static String MedicineNameOne = "Reglan 1 Tablet";
	public final static String MedicineName2 = "Hydrocortisone 1 1";
	public final static String MedicineNameTwo = "Zofran 1 Tablet";
	public final static String MedicationName = "Albuterol Sulfate (5 MG/ML) 0.5% Inhalation Nebulization Solution";
	public final static String Dosage = "21";
	public final static String PharmacyList = "CVS, 1 Pharm Cr, Cary, NC";
	public final static String RenewalConfirmation = "Thank you for submitting your Prescription Renewal.";
	public final static String MyPatientPage = "My Patient Page";

	public final static String Subject = "Subject";

	// Added variables for Prescription test in Integration Platform
	public final static String Quantity = "3";
	public final static String No_Of_Refills = "12.00";
	public final static String Prescription_No = "#12345";
	public final static String Additional_Info = "New prescription request";
	public static final String Medication_Dosage = "MedicationDosage";

	public static final String Medication_Name_Tag = "MedicationName";
	public static final String Quantity_Tag = "Quantity";
	public static final String Refill_Number_Tag = "RefillNumber";
	public static final String Prescription_Number_Tag = "PrescriptionNumber";
	public static final String Additional_Information_Tag = "AdditionalInformation";

	// new added variables for post prescription API
	public static final String RxRenewal_Subject_Tag = "Prescription Renewal Approved";

	// MakePayment Fields
	public final static String PatientAccountNumber = "137353065771";
	public final static String PaymentAmount = "100.00";
	public final static String PaymentComment = "TestOnlineBillPayment";
	public final static String CardType = "Automation Patient 1111";
	public final static String NewCardType = "Add New Card";
	public final static String PaymentConfirmation = "Thank You for your payment";

	// Discrete Forms
	public final static String MaritalStatus = "Married";
	public final static String State = "CA";
	public final static String PrimaryPhoneType = "Home";
	public final static String Sex = "Male";
	public final static String PreferredCommunication = "Secure Email";
	public final static String PreferredLanguage = "English";
	public final static String Race = "Asian";
	public final static String Ethnicity = "Unreported";
	public final static String WhoIsFillingOutForm = "Self";

	public final static String FirstName = "QA";
	public final static String LastName = "Automation";
	public final static String Relation = "Wife";
	public final static String Mobile = "Mobile";

	public final static String Tetanus = "Within the last 10 years";
	public final static String HPV = "Within the last 2 years";
	public final static String Influenza = "Within the last 6 months";
	public final static String Pneumonia = "Within the last 2 years";

	public final static String SurgeryName = "Caesarean hysterectomy";
	public final static String SurgeryTimeFrame = "0-12 months ago";
	public final static String HospitalizationReason = "Pneumococcal arthritis";
	public final static String HospitalizationTimeFrame = "0-12 months ago";

	public final static String Test = "Ace bandage";
	public final static String TestTimeFrame = "0-12 months ago";

	public final static String NewPatientActivationMessage = "You're invited to create a Patient Portal";
	public final static String NewPatientActivationMessageLinkText = "Sign Up!";
	public final static String NewPatientActivationMessageLink = "fuseaction=home.unlock";
	public final static String DateOfBirtSlashFormat = "01/11/1987";
	public final static String DateOfBirthMonth = "January";
	public final static String DateOfBirthDay = "11";
	public final static String DateOfBirthYear = "1987";

}
