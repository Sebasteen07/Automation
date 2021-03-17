package com.intuit.ihg.product.smintegration.utils;

import java.net.HttpURLConnection;

import com.medfusion.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */

public class SmIntegrationConstants {
	// SM Constants
	public final static String INTEGRATIONSTATUS_ID = "7";
	public final static String INTEGRATIONSTATUS_ID_COLUMN = "intchangeintegrationstid";
	public final static String PPIA_STATUS_COLUMN = "ppia_status";
	public final static String PPIA_STATE_COLUMN = "ppia_state";
	public final static String PPIASTATUS_VALUE = "2";
	public final static String PPIA_STATUS_VALUE1 = "1";
	public final static String PPIASTATE_VALUE1 = "2";
	public final static String PPIASTATE_VALUE2 = "5";
	public final static String PPIA_ID_COLUMN = "ppia_id";
	public final static String PPIAL_RESPONSE_COLUMN = "ppial_response";
	public final static String PPIAL_RESPONSE_VALUE = "CONNECTHUB_ENDPOINT_DOWN";

	public final static String PPIA_REQUEST_XML_COLUMN = "ppia_request_xml";
	public final static String INTEGRATIONSTATUS_INITIAL_VALUE = "2";
	public final static String PPIA_STATUS = "Status";
	public final static String PPIA_STATE = "State";
	public final static String PPIA_REQUEST_XML = "ppia_request_xml";
	public final static String MEMBERID_COLUMN = "memberid";

	// Patient Registration
	// Patient Registration
	public final static int PPIA_PPI_ID_OUTBOUND = 3001;
	public final static int PPIA_PPI_ID_INBOUND = 3006;

	public static String EXPECTEDRESPONSEMESSAGE_DATAJOB = "OK";
	public static int EXPECTEDHttpCode = HttpURLConnection.HTTP_OK;
	// Property File Details
	public static final String PROTOCOL = "protocol";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String WS_URL = "ws_url";
	public static final String DELTA_RESPONSE = "deltaresponse";
	public static final String NODE_RESPONSE = "noderesponse";

	public static final String POST_REQUEST = "POST";
	public static final String GET_REQUEST = "GET";
	public static final String ALLSCRIPTS_ADAPTER_FORMEXPORT_WS_URL = "allscripts_formsexport_ws_url";
	public static final String ALLSCRIPTS_ADAPTER_CCDEXPORT_WS_URL = "allscripts_ccdexport_ws_url";
	public static final String ALLSCRIPTS_ADAPTER_CCDIMPORT_WS_URL = "allscripts_ccdimport_ws_url";
	public static final String PORTAL_REST_URI_FOR_TEST = "PortalRestUrl";

	public static final String SAMPLE_DATA = "target/test-classes/testfiles/";
	public static final String TEST_DATA = "src/test/resources/testfiles/";



	// Patient Registration INBOUND

	public static final String PATIENT_REGISTRATION_REQUEST = TEST_DATA + IHGUtil.getEnvironmentType() + "/patientregistration/";
	public static final String PATIENT_REGISTRATION_RESPONSE = SAMPLE_DATA + IHGUtil.getEnvironmentType() + "/patientregistration/";

	public static final String TAG_ALLSCRIPTPATIENTID = "tns:AllscriptsPatientID";
	public static final String ALLSCRIPTPATIENTID_OLDVALUE = "??2013080601??";
	public static final String TAG_REQUESTORPATIENTID = "tns:RequestorPatientID";
	public static final String REQUESTORPATIENTID_OLDVALUE = "??49317??";
	public static final String TAG_MEMBERID = "mombt:CodeValue";
	public static final String MEMBERID_OLDVALUE = "???49317???";
	public static final String TAG_PROVIDERID = "mombt:CodeValue";
	public static final String PROVIDERID_OLDVALUE = "??22393??";
	public static final String TAG_SITEID = "Value";
	public static final String SITEID_OLDVALUE = "??20971??";

	// Health Reminder INBOUND
	public static final String PATIENT_HEALTH_REMINDER_REQUEST = TEST_DATA + IHGUtil.getEnvironmentType() + "/healthreminder/";
	public static final String PATIENT_HEALTH_REMINDER_RESPONSE = SAMPLE_DATA + IHGUtil.getEnvironmentType() + "/healthreminder/";

	public final static int PATIENTHEALTH_REMINDER_INBOUND_PPIA_PPI_ID = 3011;
	public final static int PATIENTHEALTH_REMINDER_OUTBOUND_PPIA_PPI_ID = 3014;
	public final static String PATIENTHEALTH_REMINDER_OUTBOUND_MESSAGE_SUBJECT =
			"Please get back ASAP! 17-APRIL-2013__NOUNVERBIDENTIFIER__HealthReminder__NOUNVERBIDENTIFIER__Acknowledged";
	public final static String PATIENTHEALTH_REMINDER_OUTBOUND_MESSAGE_BODY = "Health Reminder has been read by the Patient";

	// Patient Invite
	public static final String PATIENT_INVITE_REQUEST = TEST_DATA + IHGUtil.getEnvironmentType() + "/patientinvite/";
	public static final String PATIENT_INVITE_RESPONSE = SAMPLE_DATA + IHGUtil.getEnvironmentType() + "/patientinvite/";

	public static final String TAG_ALLSCRIPTPATIENTMRN = "mombt:Mrn";
	public static final String TAG_FIRSTNAME = "mombt:First";
	public static final String PATIENTFIRSTNAME_OLDVALUE = "??Patient??";
	public static final String TAG_LASTNAME = "mombt:Last";
	public static final String PATIENTLASTNAME_OLDVALUE = "??2013080602??";
	public static final String TAG_EMAIL = "mombt:EmailAddress";
	public static final String PATIENTEMAIL_OLDVALUE = "??tejorart+2013080602@gmail.com??";
	public static final String TAG_ADDRESS = "mombt:StreetAddress1";
	public static final String PATIENTADDRESS_OLDVALUE = "??202,??";
	public static final String TAG_CITY = "mombt:City";
	public static final String PATIENTCITY_OLDVALUE = "??Cary??";
	public static final String TAG_POSTALCODE = "mombt:PostalCode";
	public static final String PATIENTPOSTALCODE_OLDVALUE = "??27511??";
	public static final String TAG_STATE = "mombt:StateOrProvince";
	public static final String PATIENTSTATE_OLDVALUE = "??NC??";
	public static final String PATIENTSTATE_NEWVALUE = "CA";
	public final static int PATIENTINVITE_PPIA_PPI_ID = 3010;

	// Appointment request Inbound
	public static final String APPOINTMENT_REQUEST_PATH = TEST_DATA + IHGUtil.getEnvironmentType() + "/appointmentRequest/";
	public static final String APPOINTMENT_RESPONSE_PATH = SAMPLE_DATA + IHGUtil.getEnvironmentType() + "/appointmentRequest/";

	public final static int APPOINTMENT_REQUEST_INBOUND_PPIA_PPI_ID = 3008;
	public static final String TAG_REPLY_TO_MESSAGE_ID = "mombt:ReplyToMessageID";
	public static final String REPLY_TO_MESSAGE_VALUE = "??147047??";
	public static final String APPOINT_EMAIL_OLD_CONTENT = "??Please be avaliable on scheduled time??";
	public static final String APPOINT_EMAIL_NEW_CONTENT = "Please be avaliable on scheduled time";
	public static final String SUBJECT_NEW_APPOINTMENT = "AR";
	public static final String SUBJECT_OLD_APPOINTMENT = "??test AR msg??";
	public static final String APPOINTMENT_REQUEST = TEST_DATA + IHGUtil.getEnvironmentType() + "/appointmentRequest/";
	public static final String TAG_PATIENT_ID = "mombt:CodeValue";
	public static final String TAG_ALLSCRIPT_ID = "mombt:Mrn";
	public static final String ALLSCRIPT_OLD_VALUE = "?20130801276?";
	public final static int APPOINTTMENT_REQUEST_OUTBOUND_PPIA_PPI_ID = 3002;
	public final static String APPOINTTMENT_REQUEST_ID = "apptreqid";



	// ProviderInitiatedMessage

	public static final String PROVIDERINITIATEDMESSAGE_REQUEST = TEST_DATA + IHGUtil.getEnvironmentType() + "/providerInitiatedMessage/";
	public static final String PROVIDERINITIATEDMESSAGE_RESPONSE = SAMPLE_DATA + IHGUtil.getEnvironmentType() + "/providerInitiatedMessage/";


	public static final String TAG_SUBJECT = "mombt:Subject";
	public static final String SUBJECT_OLDVALUE = "??test web msg??";
	public static final String SUBJECT_NEWVALUE = "test web msg";
	public static final String TAG_EMAILCONTENT = "mombt:Content";
	public static final String EMAILCONTENT_OLDVALUE = "??Testing message flow??";
	public static final String EMAILCONTENT_NEWVALUE = "Testing message flow";
	public final static int PROVIDERINITIATEDMESSAGE_PPIA_PPI_ID = 3007;



}
