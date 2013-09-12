package com.intuit.ihg.product.apiehcore.utils;

import java.net.HttpURLConnection;

import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author bkrishnankutty, bbinisha
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */

public class EhcoreAPIConstants {

	// # connection protcol. Can either be http or https
	public final static String protocol = "http";
	// # URL for REST APIs
	public final static String ws_url = "/eh/v1/";

	// # to check the reprocess flow - retriable exception
	public final static String reprocessReq_port = "8680";

	// #IHG_CCD_DC_TRACKING DB Connection details - oracle
	public final static String DB_CCD_PORT = "1521";
	public final static String DB_CCD_USER = "DCT";
	public final static String DB_CCD_PASS = "Intuit123";

	// Mongo DB connection details
	public final static String MONGO_DBPATTERN = "2:\\w+(-*_*\\w)*";
	public final static String MONGO_COLLECTIONPATTERN = "2:\\w+(-*_*\\w)*";
	public final static String MONGO_USERNAME = "IhgMongoUser";
	public final static String MONGO_PASSWORD = "Intuit1234";

	// Mongo DB connection Details for CDM Message
	public final static String MONGO_DBNAME = "cdmList";
	public final static String MONGO_COLLECTIONNAME = "ccdimport";
	public final static String MONGO_ATTRIBUTE = "messageId";
	public final static String MONGO_NODENAME = "cdm";

	// INTUITPATIENTID Details
	public final static String INTUITPATIENTID_ADD_ENTITY = "47910";
	public final static String INTUITPATIENTID_ADD_ELEMENT = "47913";
	public final static String INTUITPATIENTID_UPDATE = "47914";
	public final static String INTUITPATIENTID_CCCD = "47915";
	public final static String INTUITPATIENTID_NOKNOWN_CCCD = "47916";
	public final static String INTUITPATIENTID_ADD_ENTITY_CCCD = "47917";
	public final static String INTUITPATIENTID_ADD_ELEMENT_CCCD = "47919";
	public final static String INTUITPATIENTID_UPDATE_CCCD = "47920";
	public final static String INTUITPATIENTID_INVALIDCCDNOTIFICATION = "47921";

	// CCD
	public final static String VALID_CCD = "validCCD";
	public static final String INVALIDCCD = "invalidCCD";
	public static final String INVALID_XML_VALIDATION = "invalidCCDXMLValidation";
	public static final String INVALID_CCD_NOTIFICATION = "invalidCCDNotification";
	public static final String NULL_CODE_SOCIAL_HISTORY = "nullCodeCCDSocialHistory";
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

	public static final String QUESTIONNAIRE_SAMPLE_DATA = "target/test-classes/questionnaire/test_data/";
	public static final String SAMPLE_DATA = "target/test-classes/testfiles/";

	public static final String CONTENTTYPE = "application/xml";
	/**
	 * CCD Message -Inbound and Outbound Flow Details (Phr <==>Allscripts)
	 */
	// Data job Details
	public static final String TEST_DATA = "src/test/resources/testfiles/";
	public static final String C_TEST_DATA = "src/main/resources/c_ccd/testdata/";
	public static final String META_INF = "target/test-classes/classes/META-INF/xsd/";
	public static final String SAMPLE_CDM_LIST = SAMPLE_DATA
			+ "changedetectionmessage/expected/entity/";
	public static final String ACTUAL_CDM_LIST = SAMPLE_DATA
			+ "changedetectionmessage/actual/testactual/";
	public static final String DATAJOB_XSD = "src/test/resources/testfiles/xsd/DataJob.xsd";

	// Actual CCD Message (From RAW,ENRICHED ,SNAPSHOT node)
	public static final String MARSHAL_CCD = SAMPLE_DATA + "marshal_ccd/";

	// Data job
	public static final String URL_DATAJOB = "data.job";
	public static final String EXPECTEDRESPONSEMESSAGE_DATAJOB = "OK";
	public static final String DATA_JOB_INPUT = TEST_DATA
			+ IHGUtil.getEnvironmentType() + "/datajob/";
	public static final String SAMPLE_DATA_JOB_INPUT = SAMPLE_DATA
			+ IHGUtil.getEnvironmentType() + "/datajob/";
	public static final String DJ_MGR_PROC_TIME = "60";

	// Expected Response
	public static final String EXPECTEDRESPONSE_ACCEPTED = "Accepted";
	public static final String EXPECTEDRESPONSE_BADREQUEST = "BadRequest";

	// Allscripts-CCD Import
	public static final String AS_CCD = "ASCCDImport";
	public static final String AS_CCD_EXPORT = "ASCCDExport";

	// CCDImport Message Details
	public static final String QUESTIONNAIRE_TEST_DATA = "src/main/resources/questionnaire/test_data/";
	public static final String AS_TEST_DATA = "src/test/resources/testfiles/adapters/allscripts/";
	public static final String CCD = TEST_DATA
			+ "ccdimport/validCCDExchange1/input/";
	public static final String CCD_EXPORT_DATA = TEST_DATA
			+ "ccdexport/validCCDMessageType/input/";
	public static final String AS_REQ = AS_TEST_DATA + "validCCDimport/input/";
	public static final String AS_CCDEXPORT_REQ = AS_TEST_DATA
			+ "validCCDexport/input/";
	public static final String SAMPLE_ALLSCRIPTS_FORMS_EXPORT_INPUT_DATA = QUESTIONNAIRE_TEST_DATA
			+ "export/allscripts_ehr/validQuestionnaireMessage/input/";
	public static final String REPROCESS_DATA = TEST_DATA + "reprocessmessage/";
	public static final String REPROCESSREQ_PORT = "reprocessReq_port";
	// Test Import CCD
	public static final String SAMPLE_CCD = SAMPLE_DATA
			+ IHGUtil.getEnvironmentType()
			+ "/ccdimport/validCCDExchange1/input/";
	// CCDImport Message Details
	public static final String CCD1 = TEST_DATA + IHGUtil.getEnvironmentType()
			+ "/ccdimport/validCCDExchange1/input/";
	public static final String C_CCD = C_TEST_DATA
			+ IHGUtil.getEnvironmentType()
			+ "ccdimport/validCCDExchange/input/";
	public static final String INVALID_C_CCD = C_TEST_DATA
			+ IHGUtil.getEnvironmentType()
			+ "ccdimport/invalidCCDExchange/input/";
	public static final String SAMPLE_C_CCD = SAMPLE_DATA
			+ IHGUtil.getEnvironmentType()
			+ "c_ccd/ccdimport/validCCDExchange/input/";
	public static final String INVALID_CCD = TEST_DATA
			+ "ccdimport/invalidCCDExchange1/input/";

	public static final String CCDEXCHANGE_XSD = "src/test/resources/testfiles/xsd/CCDExchange.xsd";
	public static final String CCD_XSD = "src/test/resources/testfiles/xsd/CCD.xsd";
	public static final String PROCESSING_RESPONSE_XSD = "src/test/resources/testfiles/xsd/ProcessingResponse.xsd";

	// Kavithas EhcoreTestConsts :- EHDC -Consolidated CCD
	public static final String ConsolidatedCCD = "ConsolidatedCCD";
	public static final String NOKNOWN_C_CCD = "NoKnownCCCD";
	public static final String INVALIDC_CCD = "invalidConsolidatedCCD";
	public static final String INVALID_C_CCD_XML_VALIDATION = "invalidCCCDXMLValidation";
	public static final String INVALID_C_CCD_NOTIFICATION = "invalidCCCDNotification";
	public static final String NULL_CODE_C_CCD_SOCIALHISTORY = "nullCodeCCCDSocialHistory";

	// Kavithas EH_REST_API_Consts Interface
	public static final String DATAJOB = "data.job";
	public static final String CCDImport = "ccd";
	public static final String CCDExport = "internal/ccd";
	public static final String REPROCESS = "internal/reprocess.request";

	public static final String AS_CCDImport = "importccd";
	public static final String AS_CCDExport = "internal/exportccd";

	// Kavithas UtilConsts Interface
	// Actual CCD Message (From RAW,ENRICHED ,SNAPSHOT node)
	public static final String ACTUAL_CCD = SAMPLE_DATA + "ccd_actual/";
	
	//CCD Import-Activity Details    
    public static final String RAWXSDVALIDATION = "Raw Data XSD Validation";
	public static final String RAWPERSISTANCE = "Raw Message Persistance";
	public static final String XMLVALIDATION = "XML Validation ";
	public static final String RAWRETRIEVAL = "Raw Message Retrieval";
	public static final String KEYREGISTRY = "Keyregistry Enrichment of SimpleCDA";
	public static final String ACTOR_ID = "Actor ID Validation";
	public static final String PUBLISHNOTIFICATION = "Publish CCD Notification to Hub";
	public static final String CCDXSDVALIDATION = "CCD XSD Validation";
	public static final String CCD2SIMPLE = "ccd2simpleTranslation";
	public static final String ENRICHED = "Enriched Message Persist";
	public static final String SNAPSHOTCREATION = "Snapshot Creation Activity";
	public static final String SNAPSHOTRETRIEVE = "SnapshotMessageRetrieve";
	public static final String SNAPSHOTPERSIST = "SnapshotMessagePersist";
	public static final String CHANGEDETECTION = "Change Detection Activity";
	public static final String DELTAENRICHMENT = "DeltaSummaryEnrichmentActivity";
	public static final String DELTAMESSAGEPERSIST = "Delta Message Persist";
	public static final String SIMPLE2HUBCANONICAL = "simple2hubcanonical";

	public static final String CLAIMCHECK = "Transform Message to an Attachment Service based Claim Check";
	public static final String PUBLISHCANONICAL = "Publish CCD Canonical to Hub";

	public static final String ERRORPERSISTANCE = "Error Message Persist";
	

	//Allscripts adapter CCD IMPORT/EXPORT Message Details
	public static final String AS_INVALID_REQ = AS_TEST_DATA +"invalidCCDimport/input/";
	
}
