package com.intuit.ihg.product.apiehcore.test;


import org.apache.log4j.Logger;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.allscripts.uai.schemas._2010._02._15.AllscriptsMessageEnvelope;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.intuit.dc.framework.tracking.constants.TrackingEnumHolder;
import com.intuit.dc.framework.tracking.entity.Message;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.eh.core.dto.DataJob;
import com.intuit.ihg.eh.core.dto.ProcessingResponse;
import com.intuit.ihg.product.apiehcore.page.DataJobIDPage;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPI;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPITestData;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIUtil;
import com.intuit.ihg.product.apiehcore.utils.EhcoreTrackingDBUtils;
import com.intuit.ihg.product.apiehcore.utils.constants.DataJobConstant;
import com.intuit.ihg.product.apiehcore.utils.constants.EhcoreAPIConstants;
import com.mongodb.Mongo;

/**
 * @author bkrishnankutty
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */

public class EhcoreAPIAcceptanceTests extends BaseTestNGWebDriver{
	private static final Logger logger = Logger.getLogger(EhcoreAPIAcceptanceTests.class);
	private static DataJob dj = null;
	private static String  CCDIMPORT_MSG_TYPE = "ccd";
	private static SortedMap<String,String> actualCDM= new TreeMap<String,String>();
	private static boolean status = false;
	private static String activityName = "Raw Message Persistance";
	private static String CCDExport_MSG_TYPE = EhcoreAPIConstants.CCDExport;

	/**
	 * @Author:- bbinisha
	 * @Date :
	 * @User Strory in Rally :
	 * @StepsToReproduce:
	  =================================================
	 * @AreaImpacted :-
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests", "FuctionalTests" })
	public void test_TrackingDBConnection() throws Exception {

		log("Testing 'test_TrackingDBConnection' test case.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());

		log("Entering test method ... ");
		Connection c = EhcoreTrackingDBUtils.getDBConnection();
		if (c != null) {
			log("connected to DB");
		}
		log("Exiting test method ... ");

	}
	
	/**
	 * @Author:- bbinisha
	 * @Date :
	 * @User Strory in Rally :
	 * @StepsToReproduce:
	  =================================================
	 * @AreaImpacted :-
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests", "FuctionalTests" })
	public void test_MongoDBConnection() throws Exception {
		Mongo m;
		try {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			m = new Mongo(testData.getMongoServerAddress());

			// m.getDatabaseNames();
			if (m != null) {
				log("connected to mongo DB " + m.toString());
			}
			m.close();

		} catch (Exception ex) {
			if (ex != null) {
				log("cannot connect to MONGODB");
			}
			m = null;
		}
	}

	/**
	 * @Author:-bkrishnankutty
	 * @Date:-5/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * 
	 * Test -Open Data Job with Transmission Status "INIT".
     * Check the Response Code and Status in Tracking DB
     * Expected result is that it should work fine-200 OK
       ======================================================

	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testOpenDataJob() throws Exception {

		log("*****Test Case: testOpenDataJob*****");
		log("*****Execution Environment: *****" + IHGUtil.getEnvironmentType());
		log("*****Entering open_DataJob test method ... *****");
		
		DataJob dj = new DataJob();
		
		log(DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		log("*****Open Data Job with Transmission Status INIT*****");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		log("*****Check the Response Code and Status in Tracking DB :- Expected result =200*****");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(djId,
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		log("*****Exiting open_DataJob test method ...*****");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * 
	 * Test -Open Data Job with Transmission Status "INIT".
	 * Test-Open Two Datajob's and Check datajob id's are
	 * different. Expected result is that it should work fine  -200 OK
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openTwoDataJobs() throws Exception {

		log("*****Test Case: openTwoDataJobs*****");
		log("*****Execution Environment: *****" + IHGUtil.getEnvironmentType());
		log("*****Entering open_DataJob test method ...*****");

		DataJob dj = new DataJob();
		
		log(DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		log("*****Test -Open Data Job with Transmission Status INIT.*****");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());
		String djId1 = dj.getDataJobId();

		log("*****Test-Open Two Datajob's and Check datajob id's are different.*****");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId2 = dj.getDataJobId();

		log("*****verify that the two datajobIds are different*****");
		assertFalse(djId1.equalsIgnoreCase(djId2));

		log("*****Exiting openTwoDataJobs test method ...*****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * 
	 * 
	 * ======================================================
	 *
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openWithOtherTransmissionStatus() throws Exception {

		log("*****Test Case: openWithOtherTransmissionStatus*****");
		log("*****Execution Environment:***** " + IHGUtil.getEnvironmentType());
		log("*****Entering openWithOtherTransmissionStatus test method ...***** ");

		EhcoreAPIUtil
				.openDJWithTransmissionEnd(TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END
						.toString());

		log("*****Exiting openWithOtherTransmissionStatus test method ...***** ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-Open Datajob with invalid content-type in Request
	 * header EDI should return an error.415 -Unsupported Media Type
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithInvalidContentType() throws Exception {
		
		log("*****Test Case: openDataJobWithInvalidContentType*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("Entering openDataJobWithInvalidContentType test method ... ");
		log("*****Open Datajob with invalid content-type in Request, Expected Error - 415 -Unsupported Media Type*****");
		
		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob", "", "",
				"invalid", "UnsuportedType");

		log("*****Exiting openDataJobWithInvalidContentType test method ...***** ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: 
	 * Test-Open Datajob with null content-type in Request
	 *  header EDI should return an error.400- BAD REQUEST
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithNullContentType() throws Exception {
		
		log("*****Test Case: openDataJobWithInvalidContentType*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		log("*****Entering openDataJobWithInvalidContentType test method ... *****");

		log("*******open Datajob with null content-type in Request header EDI should return an error.400- BAD REQUEST*****");
		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob", "", "", "null",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("Exiting openDataJobWithInvalidContentType test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: 
	 * Test-Open Datajob without content-type in Request
	 * header EDI should return an error.415 UnsupportedMedia type
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithoutContentType() throws Exception {
		
		log("*****Test Case: openDataJobWithInvalidContentType*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering openDataJobWithoutContentType test method ... *****");
		
        log("***** Open Datajob without content-type in Request header EDI should return an error.415 *****");
		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob", "", "", "blank",
				"UnsuportedType");

		log("*****Exiting openDataJobWithoutContentType test method ...***** ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 *  Test-Open Datajob with invalid URL EDI should return
	 *  an error.404-Not Found
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithInvalidURL() throws Exception {
		
		log("*****Test Case: openDataJobWithInvalidContentType*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJobWithInvalidURL test method ...****** ");
        log("pen Datajob with invalid URL EDI should return an error.404-Not Found********");
		EhcoreAPIUtil.sendRequestWithInvalidURL(" ", "opendatajob");

		log("Exiting openDataJobWithInvalidURL test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 *  Test- open a datajob with an invalid xml by removing
	 *  required element EDI should return an error.400-Bad Request
	 *======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJob_RemoveRequiredElement() throws Exception {

		log("*****Test Case: openDataJob_RemoveRequiredElement*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering openDataJob_RemoveRequiredElement test method ...*****");

		EhcoreAPIUtil.openBlankDataJob(null,
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(),
				"removeReqNode");

		log("*****Exiting openDataJob_RemoveRequiredElement test method ...*****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-open a datajob with invalid element values. EDI
	 * should return an error.400-Bad Request
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithInvalidValues() throws Exception {

		log("*****Test Case: openDataJobWithInvalidValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering openDataJobWithInvalidValues test method ...*****");

		EhcoreAPIUtil.openBlankDataJob("invalid",
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "invalid");

		log("*****Exiting openDataJobWithInvalidValues test method ...*****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: 
	 * Test- open a datajob with Blank element values. EDI
	 * should return an error .400-BAD REQUEST.
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithBlankValues() throws Exception {

		log("*****Test Case: openDataJobWithBlankValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering openDataJobWithBlankValues test method ...*****");

		EhcoreAPIUtil.openBlankDataJob("",
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "blank");

		log("*****Exiting openDataJobWithBlankValues test method ...*****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test- open a datajob with null element values. EDI
	 * should return an error.400-Bad Request
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithNullValues() throws Exception {

		log("*****Test Case: openDataJobWithNullValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering openDataJobWithNullValues test method ...*****");

		EhcoreAPIUtil.openBlankDataJob("null",
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "null");

		log("*****Exiting openDataJobWithNullValues test method ... *****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test- open a datajob with emptyString element values.
	 * EDI should return an error.400-Bad Request
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithEmptyStringValues() throws Exception {

		log("*****Test Case: openDataJobWithEmptyStringValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering openDataJobWithEmptyStringValues test method ...*****");

		EhcoreAPIUtil
				.openEmptyStringDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
						.toString());

		log("*****Exiting openDataJobWithEmptyStringValues test method ...***** ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: 
	 * Test-Open a Datajob and close the same Expected result
	 * is that it should work fine.200-OK
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobValidId() throws Exception {
		
		log("*****Test Case: completeDataJobValidId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering completeDataJobValidId test method ...***** ");

		log("*****open datajob and retrieve its datajobId*****");
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String openDatajobId = dj.getDataJobId();

		log("*****close the same datajob with Transmission Status :- TRANSMISSION_END*****");
		DataJob dj1 = EhcoreAPIUtil.completeDataJob(openDatajobId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("*****check the datajob id's are same.*****");
		assertTrue(openDatajobId.equalsIgnoreCase(dj1.getDataJobId()),
				"Open and Complete DataJob Id's are same");

		log("*****check the status in Trcking DB*****");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(openDatajobId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("*****Exiting completeDataJobValidId test method ... *****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-Close Datajob with invalid data job id EDI should
	 * return an error.400 -BAD REQUEST
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidId() throws Exception {
		
		log("*****Test Case: completeDataJobWithInvalidId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering completeDataJobWithInvalidId test method ...*****");

		// complete datajob with invalid datajob id
		String djId = "invalid_djId";
		EhcoreAPIUtil.completeDataJobInvalidId(djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting completeDataJobWithInvalidId test method ...*****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-Close Datajob with null data job id EDI should
	 * return an error.400-BAD REQUEST
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithNullDatajobId() throws Exception {
		
		log("*****Test Case: completeDataJobWithNullDatajobId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering completeDataJobWithNullDatajobId test method ... *****");

		EhcoreAPIUtil.completeDataJobInvalidId("null",
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting completeDataJobWithNullDatajobId test method ...*****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: 
	 * Test-Close Datajob with blank data job id EDI should
	 *  return an error.400-BAD REQUEST
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeWithBlankDatajobId() throws Exception {

		log("*****Test Case: completeWithBlankDatajobId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering completeWithBlankDatajobId test method ...*****");

		EhcoreAPIUtil.completeDataJobInvalidId("",
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting completeWithBlankDatajobId test method ... *****");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test- close datajob without datajobId element EDI
	 * should return an error.400-Bad Request
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void complete_WithoutDataJobId() throws Exception {
		
		log("*****Test Case: complete_WithoutDataJobId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("*****Entering complete_WithoutDataJobId test method ... *****");

		String djId = null;

		EhcoreAPIUtil.completeDataJobInvalidId(djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting complete_WithoutDataJobId test method ...***** ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Tests close datajob with valid djId and other
	 * transmission status-Not TRANSMISSION_END EDI should
	 * return an error.400-Bad Request
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeWithOtherTransmissionStatus() throws Exception {

		log("Entering CompleteWithOtherTransmissionStatus test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());

		EhcoreAPIUtil.completeDataJobInvalidId(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("Exiting CompleteWithOtherTransmissionStatus test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-Close Datajob with invalid content-type in
	 * Request header EDI should return an error.415-Unsupported Media Type
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidContentType() throws Exception {

		log("Entering completeDataJobWithInvalidContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob", djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				"invalid", "UnsuportedType");

		log("Exiting completeDataJobWithInvalidContentType test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-Close Datajob with null content-type in Request
	 * header EDI should return an error-Response Code -400-BAD REQUEST
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithNullContentType() throws Exception {

		log("Entering completeDataJobWithNullContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob", djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				"null", EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("Exiting completeDataJobWithNullContentType test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-Close Datajob without content-type in Request
	 * header EDI should return an error-Response Code -400
	 * BAD REQUEST(sandbox) or 415 -UnsupportedType(Dev2)
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithoutContentType() throws Exception {

		log("Entering completeDataJobWithoutContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob", djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				"blank", "UnsuportedType");

		log("Exiting completeDataJobWithoutContentType test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Tests close datajob with invalid url EDI should return an error.404 - Not Found
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidURL() throws Exception {

		log("Entering completeDataJobWithInvalidURL test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());
		String djId = dj.getDataJobId();
		EhcoreAPIUtil.sendRequestWithInvalidURL(djId, "closedatajob");

		log("Exiting completeDataJobWithInvalidURL test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Tests close datajob with valid djId & transStatus and
	 * invalidXML by removing required element EDI should
	 * return an error.400-Bad Request
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDatatjob_RemoveRequiredElement() throws Exception {

		log("Entering completeDatatjob_RemoveRequiredElement test method ... ");

		String value = null;

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				value);

		log("Exiting completeDatatjob_RemoveRequiredElement test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test-close a datajob with invalid element values. EDI
	 * should return an error.400-Bad Request
	 *  ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidValues() throws Exception {

		log("Entering completeDataJobWithInvalidValues test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());

		EhcoreAPIUtil.completeDataJobWithInvalid(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("Exiting completeDataJobWithInvalidValues test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test- close a datajob with Blank element values. EDI
	 * should return an error .400-BAD REQUEST.
	 * ======================================================
	  * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithBlankValues() throws Exception {

		log("Entering completeDataJobWithBlankValues test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT
				.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				"");

		log("Exiting completeDataJobWithBlankValues test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Tests close datajob with valid djId and other required
	 * elements are null EDI should return an error.400-Bad Request
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithNullValues() throws Exception {

		log("Entering completeDataJobWithNullValues test method ... ");

		DataJob dj1 = EhcoreAPIUtil
				.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				"null");

		log("Exiting completeDataJobWithNullValues test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test- close a datajob with emptyString element values.
	 * EDI should return an error.400-Bad Request
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithEmptyStringValues() throws Exception {

		log("Entering completeDataJobWithEmptyStringValues test method ... ");
		DataJob dj1 = EhcoreAPIUtil
				.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.CompleteDJWithEmptyStringValues(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("Exiting completeDataJobWithEmptyStringValues test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:
	 * Test- Complete datajob with INIT will create a new
	 * DatajobID It should work fine and return response
	 * 200-OK with new datajobId.
	 * ======================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithINIT() throws Exception {

		log("Entering completeDataJobWithINIT test method ... ");

		// Open a datajob and update with TRANSMISSION_END.
		DataJob dj1 = EhcoreAPIUtil
				.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.completeDataJob(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		// Again update the same datajob with INIT
		DataJob dj2 = EhcoreAPIUtil.completeDataJob(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		// check both datajob id's are different.
		assertFalse(dj1.getDataJobId().equalsIgnoreCase(dj2.getDataJobId()));

		log("Exiting completeDataJobWithINIT test method ... ");
	}
	
	
	//########################################################################################################################################################################
	
	//*******************************End of Data Job Test cases *************************************************************************************************************
	
	//*******************************CCD Import Testcases starts here ******************************************************************************************************* 
	
	//#######################################################################################################################################################################

	/**
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCCDImport() throws Exception {

		log("Test Case: testCCDImport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		log("Exiting test method setup ... ");	
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_START.toString());

	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	   
	 * Test- Test - check the status of 'Raw Data XSD Validation' in Activity table in Tracking DB for validCCD and assert for it.
	 * Expected Result :200 OK with Completed Status
	 ** =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCCDImportRawDataXSDValidation() throws Exception {

		log("Test Case: testCCDImportRawDataXSDValidation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.RAWXSDVALIDATION));
	}



	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	
	 * Test - check the status of 'XML Validation ' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testXMLValidationForValidCCD() throws Exception {
		log("Test Case: testXMLValidationForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.XMLVALIDATION));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	
	 * Test - check the status of 'Raw Message Retrieval' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRawMessageRetrieveForValidCcd() throws Exception {
		log("Test Case: testRawMessageRetrieveForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.RAWRETRIEVAL));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	
	 * Test - Test - check the status of 'Keyregistry Enrichment of SimpleCDA' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testKREnrichmentforValidCCD() throws Exception {

		log("Test Case: testKREnrichmentforValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(),EhcoreAPIConstants.KEYREGISTRY));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	
	 * Test - check the status of 'Actor ID Validation' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 * @throws Exception 
	 */
	//@Test
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testActorIDValidationForValidCcd() throws Exception {
		log("Test Case: testActorIDValidationForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.ACTOR_ID));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	
	 * Test - check the status of 'Publish CCD Notification to Hub' in Tracking DB for validCCD
	 * Expected Result :202 Accepted
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testPublishCCDNotificationForValidCCD() throws Exception {

		log("Test Case: testPublishCCDNotificationForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(),EhcoreAPIConstants.PUBLISHNOTIFICATION));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'CCD XSD Validation' in Tracking DB for validCCD
	 * Expected Result :202 Accepted
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCCDXSDValidationForValidCcd() throws Exception {
		log("Test Case: testCCDXSDValidationForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.CCDXSDVALIDATION));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'ccd2simpleTranslation' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCCD2SimpleTranslateForValidCcd() throws Exception {
		log("Test Case: testCCD2SimpleTranslateForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(),EhcoreAPIConstants.CCD2SIMPLE));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'Snapshot Creation Activity' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCreateSnapshotForValidCCD() throws Exception {
		log("Test Case: testCreateSnapshotForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(),EhcoreAPIConstants.SNAPSHOTCREATION));
	}




	/** 
	 * Test-Transmit CCD input and check the message status(Completed) and 
	 * datajob_status(Transmission_Start) in Tracking DB.
	 * Expected result -HTTP response 200 & true.
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCheckTransmissionStart() throws Exception {

		log("Test Case: TestASImportMessage");
		log("Test Case: testCheckTransmissionStart");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());

		log("Entering check_TransmissionStart test method ... ");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,EhcoreAPIConstants.AS_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);

	}


	/** 
	 * @Author:Shanthala
	 * @Date:-13/Sep/2013
	 * @User Story ID in Rally : 6774
	 * Test - check the status of 'Raw Message Persistance' in Tracking DB for validCCD
	 * Expected Result :200 OK with Completed Status
	 * @throws Exception 
	 */		
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRawMsgPersistForCCD() throws Exception {
		log("Test Case: testRawMsgPersistForCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = EhcoreAPIConstants.CCD_RAW + "RawCCDExchange1.xml";

		/*	dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);*/
		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.RAWPERSISTANCE));

		log("Check mongoDB response using API");
		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(),obj_ref_id,expectedCcd);
	}


	/**
	 * @Author:Shanthala
	 * @Date:-13/Sep/2013
	 * @User Story ID in Rally : 6774
	 * Test - check the status of 'Change Detection Activity' for new patient.
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testChangeDetectionForNewPatient() throws Exception {
		log("Test Case: testChangeDetectionForNewPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);


		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = EhcoreAPIConstants.CCD_RAW + "RawCCDExchange1.xml";

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(),EhcoreAPIConstants.CHANGEDETECTION));

		log("Get Objectref details from tracking DB");
		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(),obj_ref_id,expectedCcd);


		log("Check mongoDB response using API");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),msgDetails.get(0).getQid().toString());
		System.out.println("*******************getting message details*verifyExpectedMessageProcStatus*****************I ");

		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(0).getMessageGuid(),EhcoreAPIConstants.NODE_PATH,EhcoreAPIConstants.NODE_NAME,"");
		//Get the Actual CDM Message and compare with expected using message_guid in MongoDB
		log("ActualCDM Size::"+ actualCDM.size());
		assertEquals(1,actualCDM.size());
		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.NEW_CCD,actualCDM);
	}



	/**
	 * @Author:Shanthala
	 * @Date:-13/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'DeltaSummaryEnrichmentActivity' in Tracking DB for validCCD
	 * Expected Result :202 Accepted
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testDeltaEnrichmentForValidCcd() throws Exception {
		log("Test Case: testDeltaEnrichmentForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(),EhcoreAPIConstants.DELTAENRICHMENT));
	}


	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'simple2hubcanonical' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testSimple2HubCanonicalForCCD() throws Exception {
		log("Test Case: testSimple2HubCanonicalForCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(),EhcoreAPIConstants.SIMPLE2HUBCANONICAL));
	}



	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'Transform Message to an Attachment Service based Claim Check' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testClaimCheckForValidCCD() throws Exception {
		log("Test Case: testClaimCheckForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
     	EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(),EhcoreAPIConstants.CLAIMCHECK));
	}



	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:
	 * Test - check the status of 'Transform Message to an Attachment Service based Claim Check' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testHubPublishForCCD() throws Exception {
		log("Test Case: testHubPublishForCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(),EhcoreAPIConstants.PUBLISHCANONICAL));
	}



	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * Test - check the status of 'Enriched Message Persist ' in Tracking DB for validCCD
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testEnrichedMsgPersistForValidCcd() throws Exception {
		log("Test Case: testEnrichedMsgPersistForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		//String expectedCcd = EhcoreAPIConstants.CCD_RAW + "RawCCDExchange1.xml";
		String expectedCcd = EhcoreAPIConstants.CCD_ENRICHED + "EnrichedCCDExchange1.xml";

		/*
				dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
				EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);*/
		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.ENRICHED));

		log("Check mongo DB status using API");
		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString(),obj_ref_id,expectedCcd);
	}



	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * Test - check the status of 'SnapshotMessagePersist' in Tracking DB for validCCD
	 * The Snapshot node in mongoDB returns the current response for the new patient.
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testSnapshotMsgPersistForValidCCD() throws Exception {
		log("Test Case: testSnapshotMsgPersistForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = EhcoreAPIConstants.CCD_SNAPSHOT + "SnapshotCCDExchange1.xml";

		/*
				dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
				EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);*/
		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.SNAPSHOTPERSIST));

		log("Check mongo DB status using API");
		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),obj_ref_id,expectedCcd);
	}



	/** 
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * Test - New Patient :'Delta Message Persist' for Non Consolidated CCD
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testDeltaMsgPersistForNewPatient() throws Exception {
		log("Test Case: testDeltaMsgPersistForNewPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = EhcoreAPIConstants.CCD_DELTA + "DeltaCCDExchange1.xml";


		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),obj_ref_id,expectedCcd);

	}

	/**
	 * @Author:Shanthala
	 * @Date:-12/Sep/2013
	 * @User Story ID in Rally : 6774
	 * @StepsToReproduce:	
	 * Test - check the status of 'SnapshotMessageRetrieve' for same patient 
	 * without any updates(add/update/delete).
	 * Expected Result :200 OK,Merged Snapshot should return the updated message.
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testSnapshotMessageWithNoUpdates() throws Exception {
		log("Test Case: testSnapshotMessageWithNoUpdates");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expectedCcd = EhcoreAPIConstants.CCD_SNAPSHOT + "SnapshotCCDExchange1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.CCDImport);

		//Transmit CCD again for the same patient(with the same subject_id)
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(),msgDetails.get(0).getSubjectId(),EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::"+status);
		if(status){

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(),EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			//Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString()); 
			//check snapshot version contains the merged response of previous and current version using XMLUnit.
			//checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),obj_ref_id,expectedCcd);

		}else{
			Assert.fail("Error in Snapshot Message Retrieve for Subject ID ::"+ msgDetails.get(0).getSubjectId()+"Datajob id ::"+dj.getDataJobId());
		}

		log("Exiting checkSnapshotMessageWithNoUpdates test method ... ");	
	}




	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test-Transmit CCD input and check the message status(Completed) and 
	 * datajob_status(Transmission_Start) in Tracking DB.
	 * Expected result -HTTP response 200 & true.
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASImportMessageTransmissionStart() throws Exception {
		log("Test Case: testASImportMessageTransmissionStart");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		AllscriptsMessageEnvelope response =EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);
		log("Exiting testASImportMessageTransmissionStart test method ... ");	
	}


	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test -Transmit 2 CCD Msgs and check QID's are different. and
	 * check the message status(Completed) and datajob status(DONE) in Tracking DB. 
	 * Expected result -HTTP response 200 & true.
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASImportMessageJobStatusDone() throws Exception {
		log("Test Case: testASImportMessageJobStatusDone");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

		log("Exiting testASImportMessageJobStatusDone test method ... ");	
	}

	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test - check the status of 'Raw Message Persistance ' in Tracking DB for ccdexport
	 * Expected Result :202 
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASImportRawMessagePersist() throws Exception {
		log("Test Case: testASImportRawMessagePersist");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = EhcoreAPIConstants.AS_RAW+ "AS_RawResponse.xml";

		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		msgDetails =  EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);//EHCoreTestConsts.EH_REST_API_Consts.AS_CCDImport);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.AS_RAWPERSIST));
		//Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		//check the ENRICHED Message in ObjectStore(Mongo DB)and compare with expectedCcd using XMLUnit
		//checkMsgInMongoDB(obj_ref_id,expectedCcd);
		//EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(),obj_ref_id,expectedCcd);
		log("Exiting testASImportRawMessagePersist test method ... ");			

	}


	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test - check the status of 'XML Validation' in Tracking DB for CCDExportMessage
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASImportXMLValidation() throws Exception {
		log("Test Case: testASImportXMLValidation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.AS_XMLVALIDATION));

		log("Exiting testASImportXMLValidation test method ... ");			

	}


	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test - check the status of 'Raw Message Retrieval ' in Tracking DB for Questionnaire Export
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASImportRawMessageRetrieval() throws Exception {
		log("Test Case: testASImportRawMessageRetrieval");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();

		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(),EhcoreAPIConstants.AS_RAWRETRIEVAL));

		log("Exiting testASImportRawMessageRetrieval test method ... ");			

	}


	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test - check the status of 'Translate Smooks 2 CCDExchange' in Tracking DB
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASTranslationSmooks2CcdExchange() throws Exception {
		log("Test Case: testASTranslationSmooks2CcdExchange");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();

		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(),EhcoreAPIConstants.AS_TRANSLATION));

		log("Exiting testASTranslationSmooks2CcdExchange test method ... ");			

	}



	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6779
	 * @StepsToReproduce:	
	 * Test - check the status of 'DC publish Activity' in Tracking DB for CCDExportMessage
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testASImportDCPublishActivity() throws Exception {
		log("Test Case: testASImportDCPublishActivity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();

		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(EhcoreAPIConstants.AS_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(),EhcoreAPIConstants.AS_PUBLISH));

		log("Exiting testASImportDCPublishActivity test method ... ");			

	}

	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6782
	 * @StepsToReproduce:	
	 * Test-Transmit valid CCDExport message and 
	 * check response , message status(Completed) and datajob status(Done)  in Tracking DB.
	 * Expected result -HTTP response 200 & true.
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCCDExportMessageJobStatusTypeDone() throws Exception {
		log("Test Case: testCCDExportMessageJobStatusTypeDone");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("step1:- getting value from the excel");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		
		log("step2:- sending the request and getting the response");
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(testData.getPatientID(),EhcoreAPIConstants.AS_CCD_EXPORT);
		
		log("step3:- sending the request and getting the response");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),EhcoreAPIConstants.AS_CCD_EXPORT);// .EH_REST_API_Consts.AS_CCDExport);
		
		log("step4:- sending the request and getting the response");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		log("Exiting testCCDExportMessageJobStatusTypeDone test method ... ");			
	}


	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6782
	 * @StepsToReproduce:	
	 * Test - check the status of 'Raw Message Retrieval ' in Tracking DB for Questionnaire Export
	 * Expected Result :200 OK
	 * @throws Exception 
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRawMessageRetrieveForAllScriptAdapterCCDExportMsg() throws Exception {
		log("Test Case: testRawMessageRetrieveForAllScriptAdapterCCDExportMsg");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(testData.getPatientID(),EhcoreAPIConstants.AS_CCD_EXPORT);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),EhcoreAPIConstants.AS_CCD_EXPORT);// .EH_REST_API_Consts.AS_CCDExport);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(),EhcoreAPIConstants.AS_RAWRETRIEVAL));

		log("Exiting testRawMessageRetrieveForAllScriptAdapterCCDExportMsg test method ... ");			
	}


	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6782
	 * @StepsToReproduce:	
	 * Test - check the status of 'Route Message to Practice' in Tracking DB for CCDExportMessage
	 * Expected Result :200 OK
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testDCPublish2ConnectHubActivity() throws Exception {
		log("Test Case: testDCPublish2ConnectHubActivity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(testData.getPatientID(),EhcoreAPIConstants.AS_CCD_EXPORT);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),EhcoreAPIConstants.AS_CCD_EXPORT);// .EH_REST_API_Consts.AS_CCDExport);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		//TODO KNOWN ISSUE:Retriable Exception @ROUTE Activity
		//assertTrue(isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(),EHCoreTestConsts.ASEXPORT_PUBLISH));
		log("Exiting testDCPublish2ConnectHubActivity test method ... ");			
	}

	/**
	 * @Author:Shanthala
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6782
	 * @StepsToReproduce:	
	 * Test - check the status of 'Translate Simple CCD to CCD' in Tracking DB
	 * for ccdexport Expected Result :200 OK
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testAllScriptAdapterCCDExportTranslateSimpleCCDtoCDA() throws Exception {
		log("Test Case: testAllScriptAdapterCCDExportTranslateSimpleCCDtoCDA");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1 :- Getting data from excel ");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();
        
		log("Step 2 :- Sending request ");
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(testData.getPatientID(),EhcoreAPIConstants.AS_CCD_EXPORT);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),EhcoreAPIConstants.AS_CCD_EXPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(),EhcoreAPIConstants.ASEXPORT_TRANSLATION));
		log("Exiting testAllScriptAdapterCCDExportTranslateSimpleCCDtoCDA test method ... ");			
	}

	/**
	 * @Author:Shanthala 
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6782
	 * @StepsToReproduce:	
	 * Test - check the status of 'Raw Message Persistance' in Tracking DB for ccdexport
	 * Expected Result :202 
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRawMessagePersistForValidMessage() throws Exception {
		log("Test Case: testRawMessagePersistForValidMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		//	String expectedCcd = EhcoreAPIConstants.AS_CCDEXPORT_RAW+ "AS_Export_Raw.xml";

		ProcessingResponse response = util.sendCCDExportMessage(testData.getPatientID(),EhcoreAPIConstants.AS_CCD_EXPORT);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),EhcoreAPIConstants.AS_CCD_EXPORT);// .EH_REST_API_Consts.AS_CCDExport);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.AS_RAWPERSIST));
		//Pass nodeType and get the obj_ref_id in Tracking DB
		//	String obj_ref_id  =  EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString()); 
		//check the RAW Message in ObjectStore(Mongo DB)and compare with expectedXML using XMLUnit
		//	EhcoreMongoDBUtils.checkMsgInMongoDB(obj_ref_id,expectedCcd);

		log("Exiting testRawMessagePersistForValidMessage test method ... ");
	}

	/**
	 * @Author: bbinisha 
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6780
	 * @StepsToReproduce:	
	 * Test - Test valid ccd export job status
	 * Expected Result : Valid CCD export DONE status
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testCCDExportMessageJobStatusType_Done() throws Exception {
		log("Test Case: testCCDExportMessageJobStatusType_Done");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Sending valid ccd export message");
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg");

		log("Validating the process 'COMPLETED' status message");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.CCDExport);

		log("Validating the process 'DONE' status message");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

	}

	/**
	 * @Author: bbinisha
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6783
	 * @StepsToReproduce:	
	 * Test - 
	 * Expected Result :
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRawMessagePersisitanceForValidCCDMessage() throws Exception {

		log("Test Case: testRawMessagePersisitanceForValidCCDMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Entering checkRawMessagePersisitanceForValidCCDMessage test method ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		String expectedCcd = EhcoreAPIConstants.CCD_EXPORT_DATA_RAW+ "RawCCDMessageType.xml";
		ProcessingResponse response = util.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg");

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDExport_MSG_TYPE);
		
		log("Verify the tracking DB status");
		verifyTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),activityName));

		//Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString()); 
		//check the RAW Message in ObjectStore(Mongo DB)and compare with expectedXML using XMLUnit
		//checkMsgInMongoDB(obj_ref_id,expectedCcd);

		log("Verify the Mongo DB response using API");
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(),obj_ref_id,expectedCcd);

		log("Exiting checkRawMessagePersisitanceForValidCCDMessage test method ... ");

	}

	/**
	 * @Author: bbinisha
	 * @Date:-16/Sep/2013
	 * @User Story ID in Rally : US6784
	 * @StepsToReproduce:	
	 * Test - 
	 * Expected Result :
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRawMessageRetrieveForCCDExport() throws Exception {

		log("Test Case: testRawMessageRetrieveForCCDExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Entering checkRawMessageRetrieveForCCDExport test method ... ");
		
		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDExport_MSG_TYPE);
		
		log("Verify the Tracking DB response.");
		verifyTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(),activityName));

		log("Exiting checkRawMessageRetrieveForCCDExport test method ... ");

	}

	/**
	 * @Author: bbinisha 
	 * @Date:-17/Sep/2013
	 * @User Story ID in Rally : US6785
	 * @StepsToReproduce:	
	 * Test - 
	 * Expected Result :
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testTranslateSimpleCCDtoCCD() throws Exception {

		log("Test Case: testTranslateSimpleCCDtoCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering checkTranslateSimpleCCDtoCCDForValidCCDMsg test method ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDExport_MSG_TYPE);
		
		log("Verify the Tracking DB status");
		verifyTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(),activityName));

		log("Exiting checkTranslateSimpleCCDtoCCDForValidCCDMsg test method ... ");

	}

	/**
	 * @Author: bbinisha
	 * @Date:-17/Sep/2013
	 * @User Story ID in Rally : US6786
	 * @StepsToReproduce:	
	 * Test - 
	 * Expected Result :
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testTranslateCDMtoCCDExchange() throws Exception {

		log("Test Case: testTranslateSimpleCCDtoCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Entering checkTranslateCDMtoCCDExchange test method ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDExport_MSG_TYPE);
		
		log("Verify the DB status");
		verifyTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(),activityName));

		log("Exiting checkTranslateCDMtoCCDExchange test method ... ");

	}

	/**
	 * @Author: bbinisha
	 * @Date:-17/Sep/2013
	 * @User Story ID in Rally : US6787
	 * @StepsToReproduce:	
	 * Test - 
	 * Expected Result :
	 * @throws Exception 
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testRouteMsgToPractice() throws Exception {

		log("Test Case: testTranslateSimpleCCDtoCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Entering checkRouteMsgToPractice test method ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = util.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDExport_MSG_TYPE);
		
		log("Verify the DB Status.");
		verifyTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(),activityName));

		log("Exiting checkRouteMsgToPractice test method ... ");

	}
	
	/** @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send invalid export message ccd.
		 * Expected Result : Processing_Status_Type::ERROR
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDExportJobStatusType_Error() throws Exception {

			log("Test Case: testCCDExportJobStatusType_Error");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send CCD  export message");
			ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),"invalidExportMsg");

			log("Verify the Message procesing status");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDExport_MSG_TYPE);

			log("Verify the Data processing status");
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		}

		/** @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send Invalid CCD message
		 * Expected Result :  HTTP response message: Bad Request;HTTP response code: 400
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDExportSendMessageWithBlankUPN() throws Exception {

			log("Test Case: testCCDExportSendMessageWithBlankUPN");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Exiting test method setup ... ");	
			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			log("Send Invalid CCD message and verify the processing status");		
			EhcoreAPIUtil.sendInvalidCCDMessage("",UUID.randomUUID().toString(),"InvalidCCDExport");
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send Invalid CCD message with ***NULL UPN
		 * Expected Result : HTTP response message: Bad Request;HTTP response code: 400
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendMessageWithNullUPN() throws Exception {

			log("Test Case: testSendMessageWithNullUPN");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send Invalid CCD message with ***NULL UPN and verify the processing status");
			EhcoreAPIUtil.sendInvalidCCDMessage("null",UUID.randomUUID().toString(),"InvalidCCDExport");
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send Invalid CCD message with Blank Message ID
		 * Expected Result : HTTP response message: Bad Request,HTTP response code: 400.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendMessageWithBlankMsgId() throws Exception {

			log("Test Case: testSendMessageWithBlankMsgId");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send Invalid CCD message with Blank Message ID and verify the processing status");
			EhcoreAPIUtil.sendInvalidCCDMessage(UUID.randomUUID().toString(),"","InvalidCCDExport");
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send Invalid CCD message with NULL Message ID
		 * Expected Result : HTTP response message: Bad Request,HTTP response code: 400.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendMessageWithNullMsgId() throws Exception {

			log("Test Case: testSendMessageWithNullMsgId");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send Invalid CCD message with NULL Message ID and verify the processing status");
			EhcoreAPIUtil.sendInvalidCCDMessage(UUID.randomUUID().toString(),"null","InvalidCCDExport");
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Verify the processing status after sending CCD message with empty string UPN
		 * Expected Result : Processing_Status_Type::COMPLETED.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendMessageWithEmptyStringUPN() throws Exception {

			log("Test Case: testSendMessageWithEmptyStringUPN");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send CCD message with empty string UPN");
			ProcessingResponse response = EhcoreAPIUtil.sendMessage_EmptyStringValues("","export");

			log("Verify the processing status after sending CCD message with empty string UPN");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDExport_MSG_TYPE);

		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send CCD with INVALID Content type and verify the status.
		 * Expected Result : HTTP response message: Unsupported Media Type, HTTP response code: 415.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendValidMsgWithInvalidContentType() throws Exception {

			log("Test Case: testSendValidMsgWithInvalidContentType");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send CCD with INVALID Content type and verify the status.");
			EhcoreAPIUtil.requestWithInvalidHeader("ccdexport","","","invalid","UnsuportedType");		
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send CCD with NULL contant ype and verify the processing status.
		 * Expected Result : HTTP response code: 400, HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendValidMsgWithNullContentType() throws Exception {

			log("Test Case: testSendValidMsgWithNullContentType");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send CCD with NULL contant ype and verify the processing status");
			EhcoreAPIUtil.requestWithInvalidHeader("ccdexport","","","null","BadRequest");		
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send valid message without content type and verify the processing status.
		 * Expected Result : HTTP response code: 400, HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendValidMsgWithoutContentType() throws Exception {

			log("Test Case: testSendValidMsgWithoutContentType");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send valid message without content type and verify the processing status.");
			EhcoreAPIUtil.requestWithInvalidHeader("ccdexport","","","null","BadRequest");		
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6795
		 * @StepsToReproduce:	
		 * Test - Send CCD message with invalid URL and verify the processing status.
		 * Expected Result : HTTP response code: 400, HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testSendMsgWithInvalidURL() throws Exception {

			log("Test Case: testSendMsgWithInvalidURL");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Send CCD message with invalid URL and verify the processing status.");
			EhcoreAPIUtil.sendRequestWithInvalidURL("","ccdexport");		
		}

		/**
		 * @Author: bbinisha
		 * @Date:-20/Sep/2013
		 * @User Story ID in Rally : US6796
		 * @StepsToReproduce:	
		 * Test - Test Raw Message Persisitance For InvalidCCDMessage
		 * Expected Result :
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testRawMessagePersisitanceForInvalidCCDMessage() throws Exception {

			log("Test Case: testRawMessagePersisitanceForInvalidCCDMessage");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			List<Message> msgDetails = new ArrayList<Message>();

			log("Setting the expected CCD");
			String expectedCcd = EhcoreAPIConstants.CCD_EXPORT_DATA_ERROR + "ErrorCCDMessageType.xml";

			log("Send invalid ccd export message.");
			ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),"invalidExportMsg");

			log("Verify the message processing status.");
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDExport_MSG_TYPE);

			log("Pass nodeType and get the obj_ref_id in Tracking DB");
			String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());

			log("check the ERROR Message in ObjectStore(Mongo DB)and compare with expectedCcd using XMLUnit");
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(),obj_ref_id,expectedCcd);

		}

	
		/**
		 * @Author: bbinisha
		 * @Date:-23/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test -Transmit 2 CCD Msgs and check QID's are different. and
		 * 		  Check the message status(Completed) and datajob status(DONE) in Tracking DB.
		 * Expected Result : HTTP response 200 & true.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testTwoValidCCDJobStatusDone() throws Exception {

			log("Test Case: testTwoValidCCDJobStatusDone");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");

			log("Exiting test method setup ... ");	
			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			List<Message> msg = new ArrayList<Message>();

			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			log("Step 1: open data job");
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

			log("Step 2: Send the CCD message");
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
			msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);

			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
			msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);

			assertFalse(msg.get(0).getQid().equals(msg.get(1).getQid()));

			log("Verify the CCD message status is 'Completed'");
			EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());
			
			log("Verify the Data job message status is 'Done' in Tracking DB");
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

		}

		/**
		 * @Author: bbinisha 
		 * @Date:-23/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test -Transmit 2 CCD Msgs(valid & invalid) and
		 * 		  Check the message status(Completed,Error) and datajob status(Partially Done) in Tracking DB. 
		 * Expected Result : HTTP response 200 & true.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testTwoCCDsValidAndInvalidStatusPartiallyDone() throws Exception {

			log("Test Case: testTwoCCDsValidAndInvalidStatusPartiallyDone");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			log("Step 1: open data job");
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

			log("Step 2: Send the CCD message1");
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
			
			log("Verify the CCD message status is 'Completed'");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);

			log("Step 2: Send the invalid CCD message");
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.INVALIDCCD);
			
			log("Verify the CCD message status is 'ERROR'");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(1,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			
			log("Verify the CCD message status is 'Completed'");
			EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());
			
			log("Verify the Data job message status is 'Partially Done' in Tracking DB");
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.PARTIALLY_DONE.toString());

		}

		/**
		 * @Author: bbinisha 
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test -Transmit invalid CCD and check the message status(Error)
		 * 		  and datajob status(Error) in Tracking DB.
		 * Expected Result : HTTP response code: 400; HTTP response message: Bad Request & true.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testInvalidCCDStatusTypeError() throws Exception {

			log("Test Case: testInvalidCCDStatusTypeError");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Exiting test method setup ... ");	
			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			log("Send invalid CCD message");
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",testData.getPatientID(),EhcoreAPIConstants.INVALIDCCD);
			
			log("Verify the ccd message status 'ERROR'");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			
			log("Verify the data job status 'ERROR'");
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test-Transmit valid ccd with invalid Content type in Header
		 * EDI should return an error-Response Code:415 Unsupported Media Type
		 * Expected Result : HTTP response code: 415; HTTP response message: Unsupported Media Type.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithInvalidContentType() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithInvalidContentType");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			log("Transmit valid ccd with invalid Content type in Header and Verify error response:415");
			EhcoreAPIUtil.requestWithInvalidHeader("ccdimport","","","invalid","UnsuportedType");

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test-Transmit valid ccd data with null Content type in Header
		 * 		  EDI should return an error-Response Code:400 Bad Request
		 * Expected Result : HTTP response code: 400; HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithNullContentType() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithNullContentType");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			log("Transmit valid ccd data with null Content type in Header and Verify error response:400");
			EhcoreAPIUtil.requestWithInvalidHeader("ccdimport","","","null","BadRequest");

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test-Open Datajob without content-type in Request header
		 * 		  EDI should return an error.415 UnsupportedMedia type.
		 * Expected Result : HTTP response code: 415; HTTP response message: UnsupportedMedia type.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithoutContentType() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithoutContentType");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			log("Open Datajob without content-type in Request header and Verify error response:415");
			EhcoreAPIUtil.requestWithInvalidHeader("ccdimport","","","blank","UnsuportedType");
		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test-Transmit valid ccd data with invalid datajobId
		 * 		  EDI should return an error-Response Code:400 Bad Request
		 * Expected Result : HTTP response code: 400; HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithInvalidDataJobID() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithInvalidDataJobID");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			log("Transmit valid ccd data with invalid datajobId and verify error-Response Code:400 Bad Request");
			String invalid_djId = "invalid_djId";
			EhcoreAPIUtil.sendWithInvalidDetails(invalid_djId);

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test-Transmit valid ccd data with null datajobId
		 * 		  EDI should return an error-Response Code:400 Bad Request
		 * Expected Result : HTTP response code: 400; HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithNullDataJobID() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithNullDataJobID");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			String null_djId = "null";
			log("Transmit valid ccd data with null datajobId and verify error-Response Code:400 Bad Request.");
			EhcoreAPIUtil.sendWithInvalidDetails(null_djId);

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test-Transmit valid ccd with invalid URL
		 * 		  EDI should return an error-Response Code:404 Not Found
		 * Expected Result : HTTP response code: 404; HTTP response message: Not Found.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithInvalidURL() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithInvalidURL");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			log("Open the data job");
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			
			String djId = dj.getDataJobId();
			
			log("Transmit valid ccd with invalid URL and verify error-Response Code:404 Not Found.");
			EhcoreAPIUtil.sendRequestWithInvalidURL(djId,"ccdimport");

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test - Transmit invalidCCDExchange( with blank UPN )
		 * 		  EDI shoul return an error -Response 400 BAD REQUEST
		 * Expected Result : HTTP response code: 400; HTTP response message: Bad Request.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithBlankUPN() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithBlankUPN");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

		
			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");


			//Open a datajob and get the datajob id
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			String djId = dj.getDataJobId();

			//Transmit valid CCD with null UPN       
			EhcoreAPIUtil.sendMessage_InvalidUPN(djId,"","BadRequest");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test - Valid CCD with null UniversalPatientNumber(UPN) 
		 * Expected Result :: 202 with ERROR@Actor ID validation
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithNullUPN() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithNullUPN");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			//Open a datajob and get the datajob id
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			String djId = dj.getDataJobId();

			//Transmit valid CCD with null UPN       
			EhcoreAPIUtil.sendMessage_InvalidUPN(djId,"null","Accepted");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test - Test - valid CCD with empty string UniversalPatientNumber(UPN) 
		 * Expected Result :: 202 with ERROR@Actor ID validation.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportsendValidMsgWithEmptyStringUPN() throws Exception {

			log("Test Case: testCCDImportsendValidMsgWithEmptyStringUPN");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");


			//Open a datajob and get the datajob id
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			//Transmit valid CCD without UPN        
			EhcoreAPIUtil.sendMessage_EmptyStringValues(dj.getDataJobId(),"import");
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test- Close an already closed datajob.
		 * Expected Result :200 OK.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportCompleteInActiveDatajob() throws Exception {

			log("Test Case: testCCDImportCompleteInActiveDatajob");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			//Open a datajob and transmit input and update with TRANSMISSION_END.

			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.VALID_CCD);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);

			EhcoreAPIUtil.completeDataJob(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

			log("check the transmission status in Tracking DB");
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

			log("Again update datajob with TRANSMISSION_END");
			EhcoreAPIUtil.completeDataJob(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test- Social History Additional Scenarios
		 * NullSocialHistory_CCDSample1:CDExchange+Null Code in Social History
		 * Expected Result : 202 Accepted,Error@CCD XSD Validation
		 * 					 <code codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" code=""/>.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportNullCodeSocialHistory() throws Exception {

			log("Test Case: testCCDImportNullCodeSocialHistory");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);


			List<Message> msgDetails = new ArrayList<Message>();
			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.NULL_CODE_SOCIAL_HISTORY);
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.CCDXSDVALIDATION));

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test- Datajob Status :TRANSMISSION_START.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportTransStartConsolidatedCCD() throws Exception {

			log("Test Case: testCCDImportTransStartConsolidatedCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),EhcoreAPIConstants.INTUITPATIENTID_CCCD,EhcoreAPIConstants.c_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_START.toString());

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test- Datajob Status : DONE.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportJobStatusTypeDoneConsolidatedCCD() throws Exception {

			log("Test Case: testCCDImportJobStatusTypeDoneConsolidatedCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",EhcoreAPIConstants.INTUITPATIENTID_CCCD,EhcoreAPIConstants.c_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test- Datajob Status : Partially-Done.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportJobStatusPartiallyDoneConsolidatedCCD() throws Exception {

			log("Test Case: testCCDImportJobStatusPartiallyDoneConsolidatedCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),EhcoreAPIConstants.INTUITPATIENTID_CCCD,EhcoreAPIConstants.c_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);

			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),"",EhcoreAPIConstants.invalidc_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(1,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);

			EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.PARTIALLY_DONE.toString());

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test- Datajob Status : Error.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportJobStatusErrorConsolidatedCCD() throws Exception {

			log("Test Case: testCCDImportJobStatusErrorConsolidatedCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			ProcessingResponse response = EhcoreAPIUtil.sendMessage("","",EhcoreAPIConstants.invalidc_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test-NullSocialHistory_CCDSample1:CDExchange+Null Code in Social History
		 * Expected Result :202 Accepted,Error@CCD XSD Validation
		 * <code codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED CT" code=""/>.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportNullCodeSocialHistory_CCCD() throws Exception {

			log("Test Case: testCCDImportNullCodeSocialHistory_CCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			List<Message> msgDetails = new ArrayList<Message>();
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",EhcoreAPIConstants.INTUITPATIENTID_CCCD,EhcoreAPIConstants.nullCodeCCCDSocialHistory);
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.CCDXSDVALIDATION));

		}

		/**
		 * @Author: bbinisha
		 * @Date:-24/Sep/2013
		 * @User Story ID in Rally : US6802
		 * @StepsToReproduce:	
		 * Test-US4990-NoKnown items in Allery,Medication,Problems,Procedures,Results
		 * Expected:202 and No response @PHR and Change Detection.
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportNoKnownC_CCD() throws Exception {

			log("Test Case: testCCDImportNullCodeSocialHistory_CCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
			log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,EhcoreAPIConstants.noknown_c_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

		}
		
	/**
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally : US6799
		 * @StepsToReproduce:	   
		  * Test - check the status of 'Raw Data XSD Validation' in Tracking DB for invalidCCD( invalid 'DateOfBirth')
		 * Expected Result :400 BAD REQUEST
		 ** =============================================================
		 * @throws Exception
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportRawDataXSDValidationForInvalidCCD() throws Exception {

			log("Test Case: testCCDImportRawDataXSDValidationForInvalidCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.INVALIDCCD);
			List<Message> msgDetails = new ArrayList<Message>();
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.RAWXSDVALIDATION));
		}

		
		/**
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally : US6799
		 * @StepsToReproduce:	   
		 * C-CCD(Consolidated CCD) Request to check "Raw Data XSD Validation"
    	 ** =============================================================
		 * @throws Exception
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportRawDataXSDValidationForvalid_C_CCD() throws Exception {

			log("Test Case: testCCDImportRawDataXSDValidationForInvalid_C_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.C_CCD);
			List<Message> msgDetails = new ArrayList<Message>();
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.RAWXSDVALIDATION));
		}
		
	
		/**
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally : US6799
		 * @StepsToReproduce:	   
		 * Test - check the status of 'Raw Data XSD Validation' in Tracking DB for invalidCCD( invalid 'DateOfBirth')
		 * Expected Result :400 BAD REQUEST
     
		 ** =============================================================
		 * @throws Exception
		 */
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testCCDImportRawDataXSDValidationForInvalid_C_CCD() throws Exception {

			log("Test Case: testCCDImportRawDataXSDValidationForInvalid_C_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
			EhcoreAPIUtil.sendMessage(dj.getDataJobId(),testData.getPatientID(),EhcoreAPIConstants.C_CCD);
			List<Message> msgDetails = new ArrayList<Message>();
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.RAWXSDVALIDATION));
		}
		
	/** 
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally :US6799
		 *	Test - check the status of 'Error Message Persist' in Tracking DB for invalidCCD
		 *  check the DeadLetterQ status in ActiveMQ.
		 * 	Expected Result :200 OK with Error Status
		 * @throws Exception 
		 */		
		@Test(enabled = true, groups = {"Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testErrorRawMsgPersistForCCD() throws Exception {
			log("Test Case: testErrorRawMsgPersistForCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

			List<Message> msgDetails = new ArrayList<Message>();
			String expectedCcd = EhcoreAPIConstants.CCD_ERROR + "ErrorCCDExchange1.xml";
			
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",testData.getPatientID(),EhcoreAPIConstants.INVALID_CCD);
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.CCDXSDVALIDATION));

			log("Check mongoDB response using API");
			String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString());
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString(),obj_ref_id,expectedCcd);
		}
		
		
		/** @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally :US6799
		 * Test - check the status of 'Raw Message Persistance' in Tracking DB for valid consolidatedCCD
		 * Expected Result :200 OK with Completed Status
		 * @throws Exception 
		 */		
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testRawMsgPersistFor_C_CCD() throws Exception {
			log("Test Case: testRawMsgPersistFor_C_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			log("Open DataJob");
	
			List<Message> msgDetails = new ArrayList<Message>();
			String expectedCcd = EhcoreAPIConstants.CCD_RAW + "RawCCCD1.xml";
			log("Check tracking DB message_status as completed");

			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",EhcoreAPIConstants.INTUITPATIENTID_CCCD,EhcoreAPIConstants.C_CCD);
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.CCDXSDVALIDATION));

			log("Check mongoDB response using API");
			String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(),obj_ref_id,expectedCcd);
		}
			
		/** * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally :US6799
		  * C-CCD(Consolidated CCD) Invalid Request to check "Error Message Persist"
		 * @throws Exception 
		 */		
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testErrorRawMsgPersistFor_C_CCD() throws Exception {
			log("Test Case: testErrorRawMsgPersistFor_C_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			log("Open DataJob");
	
			List<Message> msgDetails = new ArrayList<Message>();
			String expectedCcd = EhcoreAPIConstants.C_CCD_ERROR + "ErrorCCCD1.xml";
			
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("","",EhcoreAPIConstants.invalidc_ccd);
			EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.ERRORPERSISTANCE));
			
			log("Check mongoDB response using API");
			String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString());
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString(),obj_ref_id,expectedCcd);			
		}
	    
	    
	    /** 
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally :US6799
		 * US4990-NoKnown Consolidated CCD items in Allery,Medication,Problems,Procedures,Results
	     * Expected:202 
		 * @throws Exception 
		 */		
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testRawMsgPersistForNoKnownC_CCD() throws Exception {
			log("Test Case: testRawMsgPersistForNoKnownC_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			List<Message> msgDetails = new ArrayList<Message>();
			String expectedCcd = EhcoreAPIConstants.C_CCD_RAW + "RawNoKnownCCCD1.xml";
			
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,EhcoreAPIConstants.noknown_c_ccd);
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.RAWPERSISTANCE));

			log("Check mongoDB response using API");
			String obj_ref_id  = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString(),obj_ref_id,expectedCcd);
		}
		
		
		
		/**
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally:US6799
		 * @StepsToReproduce:	
		 * Test - check the status of 'XML Validation ' in Tracking DB for invalidCCD
		 * Expected Result :400 OK,Error @ "XML Validation " Activity,COMPLETED @'Error Message Persist'
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testXMLValidationForInValidCCD() throws Exception {
			log("Test Case: testXMLValidationForInValidCCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			DataJob dj = new DataJob();
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			
			List<Message> msgDetails = new ArrayList<Message>();
			
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("","",EhcoreAPIConstants.INVALID_XML_VALIDATION);
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.XMLVALIDATION));
			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.ERRORPERSISTANCE));
		}
		
		
		
		/**
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally:US6799
		 * @StepsToReproduce:	
		  * C-CCD(Consolidated CCD) Request to check "XML Validation "
		 * Expected Result :200 OK
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testXMLValidationForValid_C_CCD() throws Exception {
			log("Test Case: testXMLValidationForValid_C_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("",EhcoreAPIConstants.INTUITPATIENTID_CCCD,EhcoreAPIConstants.C_CCD);
			List<Message> msgDetails = new ArrayList<Message>();
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),CCDIMPORT_MSG_TYPE);
			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.XMLVALIDATION));
		}			
		
		
		
		/**
		 * @Author:Shanthala
		 * @Date:-11/Oct/2013
		 * @User Story ID in Rally:US6799
		 * @StepsToReproduce:	
		 * C-CCD(Consolidated CCD) invalid Request to check "XML Validation "
		 * Expected Result :200 OK
		 * @throws Exception 
		 */
		@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
		public void testXMLValidationForInValid_C_CCD() throws Exception {
			log("Test Case: testXMLValidationForInValid_C_CCD");
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());

			log("Entering test method setup ... ");
			
			ProcessingResponse response = EhcoreAPIUtil.sendMessage("","",EhcoreAPIConstants.INVALID_C_CCD_XML_VALIDATION);
			List<Message> msgDetails = new ArrayList<Message>();
			msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(),CCDIMPORT_MSG_TYPE);
			
			assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(),EhcoreAPIConstants.XMLVALIDATION));
			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(),EhcoreAPIConstants.ERRORPERSISTANCE));
			
		
		}				
		
}	
		