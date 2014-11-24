package com.intuit.ihg.product.integrationplatform.utils;

/**
 * @author bkrishnankutty
 * @Date 5/Aug/2013
 * @Description :-
 * @Note :
 */

public class IntegrationConstants {

	public final static int FIND_ELEMENTS_MAX_WAIT_SECONDS = 3;
	public final static int SELENIUM_IMPLICIT_WAIT_SECONDS = 30;
	public final static String QUESTION_SUBJECT = "Subject";
	public final static String QUESTION_TYPE = "QuestionType";
	public final static String QUESTION_MESSAGE = "Message";
	public static final String SECURE_MESSAGE = "SecureMessage";
	public static final String MESSAGE_ID = "messageId";
	public static final String FROM = "From";
	public static final String TO = "To";
	public static final String SUBJECT = "Subject";
	public static final String PROCESSING_STATE = "State";
	public static final String STATE_COMPLETED = "COMPLETED";
	public static final String LOCATION_HEADER = "Location";
	public static final String PRACTICE_ID = "PracticePatientId";
	public static final String HOME_ADDRESS = "HomeAddress";
	public static final String LINE1 = "Line1";
	public static final String LINE2 = "Line2";
	public static final String MESSAGE_REPLY = "Reply";
	public static final String PRACTICE_PATIENT_ID = "PracticePatientId";
	public static final String PATIENT = "Patient";
	public static final Object NAME = "Name";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "LastName";
	public static final String EMAIL_ADDRESS = "EmailAddress";
	public static final String STATUS = "Status";
	public static final String REGISTERED = "REGISTERED";
	public static final String CCD_MESSAGE_SUBJECT = "New Health Information Import";
	//Appointment Request constants
	public static final String REASON = "Reason";
	public static final String CREATED_TIME = "CreatedDateTime";
	public static final String UPDATE_TIME = "UpdatedDateTime";
	public static final String MESSAGE_THREAD_ID = "MessageThreadId";
	public static final String APPOINTMENT = "Appointment";
	public static final String APPOINTMENT_ID = "id";
	public static final String APPOINTMENT_REQUEST = "AppointmentRequest";
	public static final String COMMUNICATION = "Communication";
	public static final String SENT_DATE = "Sent";
	public static final String SCHEDULED_DATE = "ScheduledDateTime";
	public static final String AR_SM_SUBJECT = "This is reply to Appointment";
	public static final String AR_SM_BODY = "New Reply to AR for";
	
	//Prescription Constant
	public static final String MEDICATION = "Medication";
	public static final String DOSAGE = "200gm";
	public static final String QUANTITY = "2";
	public final static String PATIENT_PAGE = "My Patient Page";
	public final static String RENEWAL_CONFIRMATION = "Thank you for submitting your Prescription Renewal.";
	public static final String MEDICATION_NAME_TAG = "MedicationName";
	public static final CharSequence NO_OF_REFILLS = "12";
	public static final CharSequence PRESCRIPTION_NO = "#12345";
	public static final CharSequence ADDITIONAL_INFO = "New prescription request";
	
	//New added variables for Post Prescription
	public static final String PRESCRIPTION = "Prescription";
	public static final String PRESCRIPTION_ID = "id";
	public static final String PRESCRIPTION_RENEWAL_REQUEST = "PrescriptionRenewalRequest";
	public static final String REQUESTED_PROVIDER = "RequestedProvider";
	public static final String REQUESTED_LOCATION = "RequestedLocation";
	public static final String DOSAGE_TAG = "MedicationDosage";
	public static final String QUANTITY_TAG = "Quantity";
	public static final String REFILL_NUMBER_TAG = "RefillNumber";
	public static final String PRESCRIPTION_NUMBER_TAG = "PrescriptionNumber";
	public static final String ADDITIONAL_INFO_TAG = "AdditionalInformation";	
	
	//Read Communication
	public static final String READCOMMUNICATION = "ReadCommunication";
	public static final String READDATETIMESTAMP = "readdatetimestamp";
	
	//All Script CCD
	public static final String TRANSPORTSTATUS = "ns4:TransportStatus";
	public static final String CCDSTATUS = "Success";
	
	//Form Export
	public static final String MEDFUSIONPATIENTID = "IntuitPatientId";
	public static final String CCDTAG = "CcdXml";
	
	//Added new constants for PIDC Regression 
	public static final String GENDER = "Gender";
	public static final String SSN = "SocialSecurityNumber";
	public static final String CITY = "City";
	public static final String ZIPCODE = "ZipCode";
	public static final String HOMEPHONE = "HomePhone";
	
	public static final String MIDDLENAME = "MiddleName";
	public static final String MOBILEPHONE = "MobilePhone";
	public static final String WORKPHONE = "WorkPhone";
	
	public static final String PREFERREDLANGUAGE = "PreferredLanguage";
	public static final String RACE = "Race";
	public static final String ETHINICITY = "Ethnicity";
	public static final String MARRITALSTATUS = "MaritalStatus";
	public static final String CHOOSECOMMUNICATION= "PreferredCommunication";
	
	public static final String PATIENTRELATIONTOSUBSCRIBER= "PatientRelationToSubscriber";
	
	public static final String PRIMARYINSURANCE = "PrimaryInsurance";
	public static final String POLICYNUMBER = "PolicyNumber";
	public static final String COMPANYNAME = "CompanyName";
	
	public static final String DATEOFBIRTH = "DateOfBirth";
	public static final String SUBSCRIBERDATEOFBIRTH = "SubscriberDateOfBirth";
	public static final String CLAIMSPHONE = "ClaimsPhone";
	public static final String SUBSCRIBERSSN = "SubscriberSocialSecurityNumber";
	public static final String GROUPNUMBER = "GroupNumber";
	public static final String MEDFUSIONID = "MedfusionPatientId";
	public static final String NName = "Name";
}
