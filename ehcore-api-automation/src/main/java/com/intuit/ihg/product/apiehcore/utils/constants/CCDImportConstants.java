package com.intuit.ihg.product.apiehcore.utils.constants;

	public class CCDImportConstants {
	
	
		/**
		 * CCD Message -Inbound and Outbound Flow Details (Phr <==>Allscripts)
		 */
		public static final String SAMPLE_DATA = "target/test-classes/testfiles/";
		
		// Allscripts-CCD Import
		public static final String AS_CCD = "ASCCDImport";
		// public static final String AS_CCD_EXPORT = "ASCCDExport";
		// AS_CCDExport
		public static final String AS_CCD_IMPORT = "importccd";
		
		
		public static final String ALLSCRIPTS_ADAPTER_CCDIMPORT_WS_URL = "allscripts_ccdimport_ws_url";
		
		// public static final String AS_TEST_DATA =
		// "src/test/resources/testfiles/adapters/allscripts/";
		
		public static final String CCD = "src/test/resources/testfiles/ccdimport/validCCDExchange1/input/";
		
			
		public static final String AS_RAW = "src/test/resources/testfiles/ccdimport/allscripts/nonconsolidatedccd/valid/expectedresponse/";
					
		// public static final String AS_RAW = AS_TEST_DATA
		// +"validCCDimport/expected/raw/";
		public static final String AS_REQ = "src/test/resources/testfiles/ccdimport/allscripts/nonconsolidatedccd/valid/request/";
		public static final String AS_INVALID_REQ = "src/test/resources/testfiles/ccdimport/allscripts/nonconsolidatedccd/invalid/request/";
		public static final String AS_INVALID_ERROR = "src/test/resources/testfiles/ccdimport/allscripts/nonconsolidatedccd/invalid/expectedresponse/";
		
		// Allscripts Request
		public static final String AS_REQ1 = AS_REQ
				+ "Professional_AllisonReed_1.xml";
		public static final String AS_REQ2 = AS_REQ
				+ "C-CCD_mockup_Allscripts_AllisonReed.xml";
		public static final String AS_REQ3 = AS_REQ
				+ "C-CCD_mockup_Allscripts_HealthyPatient.xml";
		
		public static final String SAMPLE_CCD = SAMPLE_DATA	+ "/ccdimport/validCCDExchange1/input/";
		
		// CCDImport Message Details
		
		//public static final String C_TEST_DATA = "src/test/resources/testfiles/";
		
		public static final String CCD1 = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/valid/request/";
		
		//public static final String C_CCD = C_TEST_DATA + "ccdimport/validCCDExchange/input/";           CHECK with further configuration
		 //Consolidated CCD Details
		 public static final String C_CCD = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/valid/request/";
		
		
		// Test Import CCD
		public static final String INVALID_C_CCD = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/invalid/request/" ; 
				
		public static final String SAMPLE_C_CCD = SAMPLE_DATA	+ "c_ccd/ccdimport/validCCDExchange/input/";
		
		public static final String INVALID_CCD = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/invalid/request/";
		
		// Allscripts adapter CCD IMPORT/EXPORT Message Details
		
		public static final String CCD_RAW = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/valid/expectedresponse/raw/";
		
		public static final String EXPECTED_C_CDMLIST = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/valid/expectedresponse/cdm/"; 
		
		// Actual & EXPECTED CDM Message
		public static final String ACTUAL_CDMLIST = SAMPLE_DATA	+ "ccdimport/validCCDExchange1/actual/cdm/";
		//public static final String EXPECTED_CDMLIST = "src/test/resources/testfiles/ccdimport/validCCDExchange1/expected/cdm/";
		public static final String EXPECTED_CDMLIST = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/valid/expectedresponse/cdm/";
		//\src\test\resources\testfiles\ccdimport\ccdexchange\nonconsolidatedccd\valid\expectedresponse\cdm

		
		public static final String C_CCD_RAW = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/valid/expectedresponse/raw/";
		public static final String C_CCD_ERROR = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/invalid/expectedresponse";
		public static final String C_CCD_ENRICHED ="src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/valid/expectedresponse/enriched/";
		public static final String C_CCD_SNAPSHOT = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/valid/expectedresponse/snapshot/";
		public static final String C_CCD_DELTA = "src/test/resources/testfiles/ccdimport/ccdexchange/consolidatedccd/valid/expectedresponse/delta/";

		// CCDImport Message Details
		public static final String CCD_MESSAGE = "src/test/resources/testfiles/ccdmessage/";  //CHECK IT
		public static final String CCD_ENRICHED = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/valid/expectedresponse/enriched/";
		public static final String CCD_SNAPSHOT = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/valid/expectedresponse/snapshot/";
		public static final String CCD_DELTA = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/valid/expectedresponse/delta/";

		public static final String CCD_ERROR = "src/test/resources/testfiles/ccdimport/ccdexchange/nonconsolidatedccd/invalid/expectedresponse/";
		
		public static final String SAMPLE_INVALID_CCD = SAMPLE_DATA
				+ "ccdimport/invalidCCDExchange1/input/";			

}
