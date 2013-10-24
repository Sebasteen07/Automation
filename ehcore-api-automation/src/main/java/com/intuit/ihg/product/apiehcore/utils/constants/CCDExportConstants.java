package com.intuit.ihg.product.apiehcore.utils.constants;

public class CCDExportConstants {
		
	/**
	 * CCD Message -Inbound and Outbound Flow Details (Phr <==>Allscripts)
	 */
	public static final String C_TEST_DATA = "src/test/resources/testfiles/";
	public static final String SAMPLE_DATA = "target/test-classes/testfiles/";
	
	public static final String AS_CCDEXPORT_TEST_DATA = "src/test/resources/testfiles/ccdexport/allscripts/nonconsolidatedccd/";
	// Allscripts adapter CCD IMPORT/EXPORT Message Details
	public static final String AS_CCDEXPORT_RAW = AS_CCDEXPORT_TEST_DATA + "valid/request/";
	public static final String AS_CCDEXPORT_REQ = AS_CCDEXPORT_TEST_DATA + "valid/request/";
	public static final String AS_CCDEXPORT_INVALID_REQ = AS_CCDEXPORT_TEST_DATA + "invalid/request/";
	public static final String AS_CCDEXPORT_INVALID_ERROR = AS_CCDEXPORT_TEST_DATA
			+ "invalid/expectedresponse/";
	public static final String QUESTIONNAIRE_TEST_DATA = "src/main/resources/questionnaire/test_data/";

	public static final String QUESTIONNAIRE_SAMPLE_DATA = "target/test-classes/questionnaire/test_data/";
	
	public static final String AS_CCD_EXPORT = "internal/exportccd/";
	
	public static final String ALLSCRIPTS_ADAPTER_FORMEXPORT_WS_URL = "allscripts_formsexport_ws_url";
	public static final String ALLSCRIPTS_ADAPTER_CCDEXPORT_WS_URL = "allscripts_ccdexport_ws_url";
	
	public static final String SAMPLE_ALLSCRIPTS_FORMS_EXPORT_INPUT_DATA = QUESTIONNAIRE_TEST_DATA
				+ "export/allscripts_ehr/validQuestionnaireMessage/input/";
	// KeyRegistry Enrichment Response
	public static final String QUESTIONNAIRE_EXPORT_DATA_INVALIDKEY = QUESTIONNAIRE_TEST_DATA
			+ "export/validQuestionnaireMessage/input/invalidkey/";
	public static final String QUESTIONNAIRE_EXPORT_DATA_ENRICHED_INVALIDKEY = QUESTIONNAIRE_TEST_DATA
			+ "export/validQuestionnaireMessage/expected/enriched/invalidkey/";
	
	
	/**
	 * CCD Export
	 */
	public static final String CCD_EXPORT_DATA ="src/test/resources/testfiles/ccdexport/ccdmessage/nonconsolidatedccd/valid/request/"; 
	public static final String SAMPLE_CCD_EXPORT_DATA = SAMPLE_DATA	+ "/ccdexport/validCCDMessageType/input/";
	public static final String CCD_EXPORT_DATA_RAW =  "src/test/resources/testfiles/ccdexport/ccdmessage/nonconsolidatedccd/valid/expectedresponse/"  ;  
	public static final String INVALID_CCD_EXPORT_DATA = "src/test/resources/testfiles/ccdexport/ccdmessage/nonconsolidatedccd/invalid/request/";
	public static final String CCD_EXPORT_DATA_ERROR =  "src/test/resources/testfiles/ccdexport/ccdmessage/nonconsolidatedccd/invalid/expectedresponse/";
	public static final String SAMPLE_INVALID_CCD_EXPORT_DATA = SAMPLE_DATA	+ "ccdexport/invalidCCDMessageType/input/";
	
	// QuestionnaireExport Message Details
	public static final String QUESTIONNAIRE_EXPORT_DATA = QUESTIONNAIRE_TEST_DATA
			+ "export/validQuestionnaireMessage/input/";
	public static final String SAMPLE_QUESTIONNAIRE_EXPORT_DATA = QUESTIONNAIRE_SAMPLE_DATA
			+ "export/validQuestionnaireMessage/input/";
	public static final String QUESTIONNAIRE_EXPORT_DATA_RAW = QUESTIONNAIRE_TEST_DATA
			+ "export/validQuestionnaireMessage/expected/raw/";
	public static final String QUESTIONNAIRE_EXPORT_DATA_ENRICHED = QUESTIONNAIRE_TEST_DATA
			+ "export/validQuestionnaireMessage/expected/enriched/";
	
	// invalid Questionnaire Message Details
	public static final String INVALID_QUESTIONNAIRE_EXPORT_DATA = QUESTIONNAIRE_TEST_DATA
			+ "export/invalidQuestionnaireMessage/input/";
	public static final String QUESTIONNAIRE_EXPORT_DATA_ERROR = QUESTIONNAIRE_TEST_DATA
			+ "export/invalidQuestionnaireMessage/expected/error/";
	public static final String SAMPLE_INVALID_QUESTIONNAIRE_EXPORT_DATA = QUESTIONNAIRE_SAMPLE_DATA
			+ "export/invalidQuestionnaireMessage/input/";

	/**
	 * Questionnaire Message - Outbound Flow details(Questionnaire
	 * ==>Allscripts)
	 */

	// Allscripts adapter QuestionnaireExport Message Details
	public static final String SAMPLE_ALLSCRIPTS_FORMS_EXPORT_EXPECTED = QUESTIONNAIRE_TEST_DATA
			+ "export/allscripts_ehr/validQuestionnaireMessage/expected/";
	public static final String INVALID_ALLSCRIPTS_EXPORT_DATA = QUESTIONNAIRE_TEST_DATA
			+ "export/allscripts_ehr/invalidQuestionnaireMessage/input/";
	public static final String INVALID_ALLSCRIPTS_EXPORT_EXPECTED = QUESTIONNAIRE_TEST_DATA
			+ "export/allscripts_ehr/invalidQuestionnaireMessage/expected/error/";


}
