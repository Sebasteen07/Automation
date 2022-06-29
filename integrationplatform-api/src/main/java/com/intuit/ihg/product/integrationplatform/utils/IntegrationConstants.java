// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

public class IntegrationConstants {
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
	public static final String TIMESTAMP_HEADER = "Last-Timestamp";
	public static final String PRACTICE_ID = "PracticePatientId";
	public static final String HOME_ADDRESS = "HomeAddress";
	public static final String LINE1 = "Line1";
	public static final String LINE2 = "Line2";
	public static final String MESSAGE_REPLY = "This is response to doctor's message";
	public static final String PRACTICE_PATIENT_ID = "PracticePatientId";
	public static final String PATIENT = "Patient";
	public static final Object NAME = "Name";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "LastName";
	public static final String EMAIL_ADDRESS = "EmailAddress";
	public static final String STATUS = "Status";
	public static final String REGISTERED = "REGISTERED";
	public static final String CCD_MESSAGE_SUBJECT = "You have a new health data summary";
	public static final String PORTALSTATUS = "PortalStatus";
	public static final String DEACTIVATED = "DEACTIVATED";
	public static final String DELETED = "DELETED";
	public static final String STATE_ERRORED = "ERROR";
	public static final String ATTACHMENT_URL ="AttachmentUrl";
	public static final String FILE_NAME = "FileName";
	public static final String DATA_JOB_ID = "DataJobId";


	// Appointment Request constants
	public static final String REASON = "Reason";
	public static final String VIDEOPREFERENCE = "VideoPreference";
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
	public static final String AR_REASON = "Carpel Tunnel";
	public static final String APPOINTMENTID = "Appointment";
	public static final String APPOINTMENTIDHEADER = "appointmentid";

	// Prescription Constant
	public static final String MEDICATION = "Medication";
	public static final String DOSAGE = "200gm";
	public static final String QUANTITY = "2";
	public final static String PATIENT_PAGE = "My Patient Page";
	public final static String RENEWAL_CONFIRMATION = "Thank you for submitting your Prescription Renewal.";
	public static final String MEDICATION_NAME_TAG = "MedicationName";
	public static final CharSequence NO_OF_REFILLS = "12";
	public static final CharSequence PRESCRIPTION_NO = "#12345";
	public static final CharSequence ADDITIONAL_INFO = "New prescription request";

	// New added variables for Post Prescription
	public static final String PRESCRIPTION = "Prescription";
	public static final String ID = "id";
	public static final String PRESCRIPTION_RENEWAL_REQUEST = "PrescriptionRenewalRequest";
	public static final String REQUESTED_PROVIDER = "RequestedProvider";
	public static final String REQUESTED_LOCATION = "RequestedLocation";
	public static final String DOSAGE_TAG = "MedicationDosage";
	public static final String QUANTITY_TAG = "Quantity";
	public static final String REFILL_NUMBER_TAG = "RefillNumber";
	public static final String PRESCRIPTION_NUMBER_TAG = "PrescriptionNumber";
	public static final String ADDITIONAL_INFO_TAG = "AdditionalInformation";
	public static final String EXTERNAL_MEDICATION_ID = "ExternalMedicationId";
	public static final String EXTERNAL_SYSTEM_ID = "ExternalSystemId";

	//new added variables for post prescription API
	public static final String RXRENEWAL_SUBJECT_TAG = "Prescription Renewal Approved";
	public final static String MEDICATIONNAME = "Albuterol Sulfate (5 MG/ML) 0.5% Inhalation Nebulization Solution";

	// Read Communication
	public static final String READCOMMUNICATION = "ReadCommunication";
	public static final String READDATETIMESTAMP = "readdatetimestamp";

	// All Script CCD
	public static final String TRANSPORTSTATUS = "ns4:TransportStatus";
	public static final String CCDSTATUS = "Success";

	// Form Export
	public static final String MEDFUSIONPATIENTID = "IntuitPatientId";
	public static final String CCDTAG = "CcdXml";

	// Added new constants for PIDC Regression
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
	public static final String CHOOSECOMMUNICATION = "PreferredCommunication";
	public static final String GENDERIDENTITY = "GenderIdentity";
	public static final String SEXUALORIENTATION = "SexualOrientation";

	public static final String PATIENTRELATIONTOSUBSCRIBER = "PatientRelationToSubscriber";

	public static final String PRIMARYINSURANCE = "PrimaryInsurance";
	public static final String SECONDARYINSURANCE = "SecondaryInsurance";
	public static final String TERTIARYINSURANCE = "TertiaryInsurance";
	public static final String POLICYNUMBER = "PolicyNumber";
	public static final String COMPANYNAME = "CompanyName";
	public static final String SUBSCRIBERNAME = "SubscriberName";
	public static final String FIRSTNAME = "FirstName";
	public static final String SUBSCRIBERID = "SubscriberId";
	public static final String EFFECTIVEDATE = "EffectiveDate";
	public static final String INSURANCEIMAGES = "InsuranceImages";
	public static final String FRONTIMAGELINK = "FrontImageLink";
	public static final String BACKIMAGELINK = "BackImageLink";
	public static final String FILENAME = "fileName";
	public static final String MIMETYPE = "mimeType";

	public static final String DATEOFBIRTH = "DateOfBirth";
	public static final String SUBSCRIBERDATEOFBIRTH = "SubscriberDateOfBirth";
	public static final String CLAIMSPHONE = "ClaimsPhone";
	public static final String SUBSCRIBERSSN = "SubscriberSocialSecurityNumber";
	public static final String GROUPNUMBER = "GroupNumber";
	public static final String MEDFUSIONID = "MedfusionPatientId";
	public static final String NName = "Name";

	public static final String PAYMENTTYPE = "PaymentType";
	public static final String ACCOUNTNUMBER = "PatientAccountNumber";
	public static final String PAYMENTSTATUS = "PaymentStatus";
	public static final String AMOUNT = "AmountPaid";
	public static final String PAYMENTINFO = "PaymentInformation";
	public static final String SUBMITTED = "SUBMITTED";
	public static final String POSTED = "POSTED";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String CCTYPE = "CCType";
	public static final String LASTDIGITS = "CCLast4Digits";
	public static final String CONFIRMNUMBER = "ConfirmationNumber";
	public static final String PAYMENT = "Payment";
	public static final String PAYNOWPAYMENT = "Pay Now Payment";
	public static final String VCSPAYMENT = "VCS Payment";
	public static final String BILLPAYMENT = "BillPayment";
	public static final String SOURCE = "Source";

	// Email notification constant
	public static final String EMAIL_BODY_LINK = "fuseaction=com.start";
	public static final String PI_EMAIL_BODY_LINK = "portal/#/user";

	// Statement Preference
	public static final String STMT_PREF = "StatementPreference";
	public static final String MEDFUSION_MEMBER_ID = "MedfusionMemberId";
	public static final String PREFERENCES = "Preferences";
	public static final String PREFERENCE = "Preference";
	public static final String PREF_NAME = "Name";
	public static final String PREF_VALUE = "Value";

//CCD Information Constant
	public static final String ASSOCIATEDPERSON = "associatedPerson";
	public static final String ASSIGNEDPERSON = "assignedPerson";
	public static final String ASSIGNEDENTITY = "assignedEntity";
	public static final String ASSOCIATEDENTITY = "associatedEntity";
	public static final String GIVENNAME = "given";
	public static final String FAMILY = "family";
	public static final String DOCUMENTATIONOF = "documentationOf";
	public static final String REPRESENTEDORGANIZATION = "representedOrganization";
	public static final String SUBSTANCEADMINISTRATION = "substanceAdministration";
	public static final String ENTRYRELATIONSHIP = "entryRelationship";
	public static final String COMPONENT = "component";
	public static final String CONTENT = "content";
	public static final String ADMINISTRATIVEGENDERCODE = "administrativeGenderCode";
	public static final String ATRRIBUTE1 = "displayName";

	// CCD PDFBatch Constants
	public static final String PATIENTFORM = "PatientForm";
	public static final String MEDFUSIONMEMBERID = "MedfusionMemberId";
	public static final String PRACTICEPATIENTID = "PracticePatientId";
	public static final String PDFURLLINK = "PdfUrlLink";
	public static final String LASTUPDATED = "LastUpdated";

	// CCD HeaderDetails Constants
	public static final String CCDMESSAGEHEADERS = "CcdMessageHeaders";
	public static final String ROUTINGMAP = "RoutingMap";
	public static final String VALUE = "Value";
	public static final String KEYVALUEPAIR = "KeyValuePair";
	public static final String FORMTYPE = "pre-reg";
	public static final String NEXTURI = "next-uri";
	public static final String STATE = "State";

	// onDemand Health Data
	public static final String PATIENTID = "PatientId";
	public static final String PRACTICEID = "PracticeId";
	public static final String HEALTHDATA = "HealthData";
	public static final String STARTDATE = "StartDate";
	public static final String ENDDATE = "EndDate";

	// Login Event Data
	public static final String RESOURCETYPE = "ResourceType";
	public static final String EVENTRECORDEDTIMESTAMP = "EventRecordedTimestamp";
	public static final String PRACTICEPATIENTID_login = "PracticePatientId";

	//StatementMessagePayloadMini
	
	public static final String STATEMENTDATE = "StatementDate";
}
