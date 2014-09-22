package com.intuit.ihg.product.apiehcore.utils.constants;

public class DataJobConstant {
	
	
	  public static final String SAMPLE_DATA = "target/test-classes/testfiles/";
	  public static final String TEST_DATA = "src/test/resources/testfiles/";
	  public static final String META_INF = "target/test-classes/classes/META-INF/xsd/";
	  
	
	// Data job Details
		
		
		
		public static final String SAMPLE_CDM_LIST = SAMPLE_DATA
				+ "changedetectionmessage/expected/entity/";
		public static final String ACTUAL_CDM_LIST = SAMPLE_DATA
				+ "changedetectionmessage/actual/testactual/";
		public static final String DATAJOB_XSD = "src/test/resources/testfiles/xsd/DataJob.xsd";
		
		// Data job
		public static final String URL_DATAJOB = "data.job";
		public static final String EXPECTEDRESPONSEMESSAGE_DATAJOB = "OK";
		public static final String DATA_JOB_INPUT = TEST_DATA + "/datajob/";
		public static final String SAMPLE_DATA_JOB_INPUT = SAMPLE_DATA
				+ "/datajob/";
		public static final String DJ_MGR_PROC_TIME = "60";

}
