package com.intuit.ihg.product.apiehcore.utils.constants;

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
	public static final String PROTOCOL = "http";
	// # URL for REST APIs
	public static final String WS_URL = "/eh/v1/";
	
	// # port = 80
	public static final String PORT = "80";

	// # to check the reprocess flow - retriable exception
	// # reprocessReq_port = 8680
	public final static String reprocessReq_port = "8680";

    public static final String POST_REQUEST = "POST";
	public static final String GET_REQUEST = "GET";
	public static int EXPECTEDHttpCode = HttpURLConnection.HTTP_OK;

	// # IHG_CCD_DC_TRACKING DB Connection details - oracle
	public final static String DB_CCD_PORT = "1521";
	public final static String DB_CCD_USER = "DCT";
	public final static String DB_CCD_PASS = "Intuit123";

	// # Mongo DB connection details
	public final static String MONGO_DBPATTERN = "2:\\w+(-*_*\\w)*";
	public final static String MONGO_COLLECTIONPATTERN = "2:\\w+(-*_*\\w)*";
	public final static String MONGO_USERNAME = "IhgMongoUser";
	public final static String MONGO_PASSWORD = "Intuit1234";

	// # Mongo DB connection Details for CDM Message
	public final static String MONGO_DBNAME = "cdmList";
	public final static String MONGO_COLLECTIONNAME = "ccdimport";
	public final static String MONGO_ATTRIBUTE = "messageId";
	public final static String MONGO_NODENAME = "cdm";
	
	// CCD HOLD
	public final static String VALID_CCD = "validCCD";
	public static final String INVALIDCCD = "invalidCCD";
	public static final String INVALID_XML_VALIDATION = "invalidCCDXMLValidation";
	public static final String INVALID_CCD_NOTIFICATION = "invalidCCDNotification";
	public static final String NULL_CODE_SOCIAL_HISTORY = "nullCodeCCDSocialHistory";
	// Kavithas EhcoreTestConsts :- EHDC -Consolidated CCD
	public static final String ConsolidatedCCD = "ConsolidatedCCD";
	public static final String NOKNOWN_C_CCD = "NoKnownCCCD";
	public static final String INVALIDC_CCD = "invalidConsolidatedCCD";
	public static final String INVALID_C_CCD_XML_VALIDATION = "invalidCCCDXMLValidation";
	public static final String INVALID_C_CCD_NOTIFICATION = "invalidCCCDNotification";
	public static final String NULL_CODE_C_CCD_SOCIALHISTORY = "nullCodeCCCDSocialHistory";
		
	
	
	public static final String DELTA_RESPONSE = "deltaresponse";
	public static final String NODE_RESPONSE = "noderesponse";


	//   %%%%%%%%%%%%% TO EXCEL
	public static final String HOST = "dev3vip-eh-core-svc.qhg.local";
	// dev3vip-eh-core-svc.qhg.local
	

	// INTUITPATIENTID Details  %%%%%%%%%%%%% TO EXCEL
	public final static String INTUITPATIENTID_ADD_ENTITY = "47910";
	public final static String INTUITPATIENTID_ADD_ELEMENT = "47913";
	public final static String INTUITPATIENTID_UPDATE = "47914";
	public final static String INTUITPATIENTID_CCCD = "47915";
	public final static String INTUITPATIENTID_NOKNOWN_CCCD = "47916";
	public final static String INTUITPATIENTID_ADD_ENTITY_CCCD = "47917";
	public final static String INTUITPATIENTID_ADD_ELEMENT_CCCD = "47919";
	public final static String INTUITPATIENTID_UPDATE_CCCD = "47920";
	public final static String INTUITPATIENTID_INVALIDCCDNOTIFICATION = "47921";
	

	
	public static final String PORTAL_REST_URI_FOR_TEST = "PortalRestUrl";
	public static final String QUESTIONNAIRE_SAMPLE_DATA = "target/test-classes/questionnaire/test_data/";
	public static final String SAMPLE_DATA = "target/test-classes/testfiles/";
	public static final String CONTENTTYPE = "application/xml";
	/**
	 * CCD Message -Inbound and Outbound Flow Details (Phr <==>Allscripts)
	 */
	
	public static final String TEST_DATA = "src/test/resources/testfiles/";
//	public static final String C_TEST_DATA = "src/main/resources/c_ccd/testdata/";
	public static final String C_TEST_DATA = "src/test/resources/testfiles/";
	// Actual CCD Message (From RAW,ENRICHED ,SNAPSHOT node)
	public static final String MARSHAL_CCD = SAMPLE_DATA + "marshal_ccd/";

	
	
	public static final String addentityC_CCD = "add_entity_CCCD";
	public static final String addelementC_CCD = "add_element_CCCD";
	public static final String updateC_CCD = "update_CCCD";
	public static final String deleteC_CCD = "delete_CCCD";
	public static final String delete2C_CCD = "delete2_CCCD";
	
	 public static final String newC_CCD = "New_CCCD";

	// Expected Response
	public static final String EXPECTEDRESPONSE_ACCEPTED = "Accepted";
	public static final String EXPECTEDRESPONSE_BADREQUEST = "BadRequest";

	// CCDImport Message Details
	public static final String QUESTIONNAIRE_TEST_DATA = "src/main/resources/questionnaire/test_data/";

	
		/*
	 * public static final String AS_REQ = AS_TEST_DATA +
	 * "validCCDimport/input/"; public static final String AS_CCDEXPORT_REQ =
	 * AS_TEST_DATA + "validCCDexport/input/";
	 */
	
	public static final String REPROCESS_DATA = TEST_DATA + "reprocessmessage/";
	public static final String REPROCESSREQ_PORT = "reprocessReq_port";
	
	public static final String CCDEXCHANGE_XSD = "src/test/resources/testfiles/xsd/CCDExchange.xsd";
	public static final String CCD_XSD = "src/test/resources/testfiles/xsd/CCD.xsd";
	public static final String PROCESSING_RESPONSE_XSD = "src/test/resources/testfiles/xsd/ProcessingResponse.xsd";

	
	// Kavithas EH_REST_API_Consts Interface
	public static final String DATAJOB = "data.job";
	public static final String CCDImport = "ccd";
	public static final String CCDExport = "internal/ccd";
	public static final String REPROCESS = "internal/reprocess.request";
	
	// Kavithas UtilConsts Interface
	// Actual CCD Message (From RAW,ENRICHED ,SNAPSHOT node)
	public static final String ACTUAL_CCD = SAMPLE_DATA + "ccd_actual/";

	// CCD Import-Activity Details
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
	
	// Allscripts Adapter CCD Import Activity Details
		public static final String AS_RAWPERSIST = "RawMessagePersist";
		public static final String AS_XMLVALIDATION = "XML Validation ";
		public static final String AS_RAWRETRIEVAL = "RawMessageRetrieval";
		public static final String AS_TRANSLATION = "TranslationSmooks2CcdExchange";
		public static final String AS_PUBLISH = "DCPublishActivity";
		
		// Allscripts Adapter CCD Export Activity Details

				public static final String ASEXPORT_TRANSLATION = "TranslationSmooks2ASMessageEnvelope";
				public static final String ASEXPORT_PUBLISH = "DCPublish2ConnectHubActivity";


		// Processing Time to check CDM Message in Mongo DB

	// # time (in seconds) that the tests should wait to get the CDM Message
	// from mongo DB
	public static final String CDM_MSG_PROC_TIME = "90";

	public static String NODE_PATH = "Header[1]";
	public static String NODE_NAME = "Type";
	public static String NEW_CCD = "NewCCD";// Change Detection for new patient
	

	public static final String addentityCCD = "add_entity_CCD";
	public static final String addelementCCD = "add_element_CCD";
	public static final String updateCCD = "update_CCD";
	public static final String deleteCCD = "delete_CCD";
	public static final String delete2CCD = "delete2_CCD";

	public static final String ADD_ENTITY_C_CCD_REQ = C_TEST_DATA
			+ "changedetectionmessage/input/entity/";
	public static final String ADD_ELEMENT_C_CCD_REQ = C_TEST_DATA
			+ "changedetectionmessage/input/element/";
	public static final String SNAPSHOT_ENTITY_C_CCD_RES = C_TEST_DATA
			+ "snapshotretrieve/expected/entity/";
	public static final String SNAPSHOT_ELEMENT_C_CCD_RES = C_TEST_DATA
			+ "snapshotretrieve/expected/element/";
	public static final String ADD_ENTITY_C_CCD_RES = C_TEST_DATA
			+ "changedetectionmessage/expected/entity/";
	public static final String ADD_ELEMENT_C_CCD_RES = C_TEST_DATA
			+ "changedetectionmessage/expected/element/";


	// Snapshot and change Detection Details
	public static final String ADD_ENTITY_CCD_REQ = TEST_DATA
			+ "changedetectionmessage/input/entity/";
	public static final String ADD_ELEMENT_CCD_REQ = TEST_DATA
			+ "changedetectionmessage/input/element/";

	public static final String ADD_ENTITY_CCD_RES = TEST_DATA
			+ "changedetectionmessage/expected/entity/";
	public static final String ADD_ELEMENT_CCD_RES = TEST_DATA
			+ "changedetectionmessage/expected/element/";

	public static final String SAMPLE_UPDATE_CCD = SAMPLE_DATA
			+ "changedetectionmessage/input/entity/";

	public static final String SNAPSHOT_ENTITY_CCD = TEST_DATA
			+ "snapshotretrieve/expected/entity/";
	public static final String SNAPSHOT_ELEMENT_CCD = TEST_DATA
			+ "snapshotretrieve/expected/element/";

	

	// Reprocess Message Details

	public static final String SAMPLE_REPROCESS_DATA = SAMPLE_DATA
			+ "reprocessmessage/";

	// Actual CCD Message (From RAW,ENRICHED ,SNAPSHOT node)

	// Actual & EXPECTED CDM Message

	
	public static final String KEYREGISTRY_ENRICHMENT_RESPONSE = SAMPLE_DATA
			+ "keyregistry_enrichment/";
	// Request xml updates
	//%%%%%%%%%%%%%%%%%%%%%excel
	public static final String KEYREGISTRY_PRACTICEID = "urn:vnd:ihg:keyregistry:practice:19462a02-5b51-4577-8404-6e4194e175cc"; // EHCoreTestConfigReader.getConfigItemValue("KeyRegistry_PracticeID");
	public static final String KEYREGISTRY_PATIENTID = "urn:vnd:ihg:keyregistry:patient:5bcba7ca-f36f-4551-99c2-d2b024884ab9";
	public static final String KEYREGISTRY_PROVIDERID = "urn:vnd:ihg:keyregistry:staff:95b48dd7-732a-4ace-ad7b-6451e0812e61";

	//%%%%%%%%%%%excel
	public static final String KR_PRACTICEID = "40069";
	public static final String KR_PATIENTID = "70063";
	public static final String KR_PROVIDERID = "40073";

	public static final String PORTALKEY_PRACTICEID = "0000021089";
	public static final String PORTALKEY_PATIENTID = "EEHR44698";
	public static final String PORTALKEY_PROVIDERID = "0000026067";

	// Portal Key Enrichment Response
	public static final String PORTALKEY_ENRICHMENT_RESPONSE = SAMPLE_DATA
			+ "portalkey_enrichment/";

	
	// CCDMessageType XSD -to validate request xml
	public static final String CCDMESSAGETYPE_XSD = "src/main/resources/questionnaire/xsd/messages/CCDMessageType.xsd";


	
	
	// Data job
			public static final String URL_DATAJOB = "data.job";
			public static final String EXPECTEDRESPONSEMESSAGE_DATAJOB = "OK";
			public static final String DATA_JOB_INPUT = TEST_DATA + "/datajob/";
			public static final String SAMPLE_DATA_JOB_INPUT = SAMPLE_DATA
					+ "/datajob/";
			public static final String DJ_MGR_PROC_TIME = "60";

			// EHDC -Consolidated CCD
			public static final String c_ccd = "ConsolidatedCCD";
			public static final String noknown_c_ccd = "NoKnownCCCD";
			public static final String invalidc_ccd = "invalidConsolidatedCCD";
			public static final String invalidCCCDXMLValidation = "invalidCCCDXMLValidation";
			public static final String invalidCCCDNotification = "invalidCCCDNotification";
			public static final String nullCodeCCCDSocialHistory = "nullCodeCCCDSocialHistory";

			// public static final String TEST_DATA = "src/main/resources/testdata/";
			
			 //Expected Response
		    public static final String expectedResponse_Accepted = "Accepted";
		    public static final String expectedResponse_BadRequest = "BadRequest";

		    //EHDC -Consolidated CCD CHANGING small to caps
		    public static final String C_CCD = "ConsolidatedCCD";
}
