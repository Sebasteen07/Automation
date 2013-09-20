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
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIConstants;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPITestData;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIUtil;
import com.intuit.ihg.product.apiehcore.utils.EhcoreTrackingDBUtils;
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
	 * 
	 * 
	 * =================================================
	 * @AreaImpacted :-
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests", "FuctionalTests"} ) 
	public void test_TrackingDBConnection() throws Exception {

		log("Testing 'test_TrackingDBConnection' test case.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());


		logger.debug("Entering test method ... ");
		Connection c= EhcoreTrackingDBUtils.getDBConnection();
		if(c!=null)
		{
			logger.debug("connected to DB");
		}
		logger.debug("Exiting test method ... ");


	}

	@Test(enabled = true, groups = {"AcceptanceTests", "FuctionalTests"} ) 
	public void test_MongoDBConnection() throws Exception {
		Mongo m;
		try { 
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			m = new Mongo(testData.getMongoServerAddress()); 

			//   m.getDatabaseNames();
			if(m!=null)
			{
				logger.debug("connected to mongo DB "+m.toString());
			}
			m.close();    

		}		catch (Exception ex) { 
			if(ex!=null)
			{
				logger.debug("cannot connect to MONGODB");
			}
			m = null; 
		} 
	}


	/**
	 * @Author:-bkrishnankutty
	 * @Date:-5/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:

	 * Test -Open Data Job with Transmission Status "INIT".
	 * Check the Response Code and Status in Tracking DB
	 * Expected result is that it should work fine-200 OK
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testOpenDataJob() throws Exception {

		log("Test Case: testOpenDataJob");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering open_DataJob test method ... ");

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		String djId = dj.getDataJobId();

		//Check the status in DB
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(djId,TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		log("Exiting open_DataJob test method ... ");
	}



	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:

	 * Test -Open Data Job with Transmission Status "INIT".
	 * Test-Open Two Datajob's and Check datajob id's are different.
	 * Expected result is that it should work fine -200 OK
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openTwoDataJobs() throws Exception {

		log("Test Case: openTwoDataJobs");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering open_DataJob test method ... ");

		log("Entering test method setup ... ");


		DataJob dj = new DataJob();
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		dj= DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId1 = dj.getDataJobId();

		dj= DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId2 = dj.getDataJobId();

		//verify that the two datajobIds are different
		assertFalse(djId1.equalsIgnoreCase(djId2));

		logger.debug("Exiting openTwoDataJobs test method ... ");

	}



	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:

	 * Test -Open Data Job with Transmission Status "INIT".
	 * Test-Open Two Datajob's and Check datajob id's are different.
	 * Expected result is that it should work fine -200 OK
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openWithOtherTransmissionStatus() throws Exception {

		log("Test Case: openWithOtherTransmissionStatus");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering openWithOtherTransmissionStatus test method ... ");

		EhcoreAPIUtil.openDJWithTransmissionEnd(TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		logger.debug("Exiting openWithOtherTransmissionStatus test method ... ");



	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:

	 * Test-Open Datajob with invalid content-type in Request header
	 * EDI should return an error.415 -Unsupported Media Type
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithInvalidContentType() throws Exception {


		logger.debug("Entering openDataJobWithInvalidContentType test method ... ");

		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob","","","invalid","UnsuportedType");

		logger.debug("Exiting openDataJobWithInvalidContentType test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-04/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Open Datajob with null content-type in Request header
	 * EDI should return an error.400- BAD REQUEST
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithNullContentType() throws Exception {


		logger.debug("Entering openDataJobWithInvalidContentType test method ... ");

		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob","","","null",EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		logger.debug("Exiting openDataJobWithInvalidContentType test method ... ");

	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Open Datajob without content-type in Request header
	 * EDI should return an error.415 UnsupportedMedia type
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithoutContentType() throws Exception {


		logger.debug("Entering openDataJobWithoutContentType test method ... ");

		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob","","","blank","UnsuportedType");

		logger.debug("Exiting openDataJobWithoutContentType test method ... ");

	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Open Datajob with invalid URL
	 * EDI should return an error.404-Not Found
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithInvalidURL() throws Exception {

		logger.debug("Entering openDataJobWithInvalidURL test method ... ");

		EhcoreAPIUtil.sendRequestWithInvalidURL(" ","opendatajob");

		logger.debug("Exiting openDataJobWithInvalidURL test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- open a datajob with an invalid xml by removing required element
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJob_RemoveRequiredElement() throws Exception {


		logger.debug("Entering openDataJob_RemoveRequiredElement test method ... ");

		EhcoreAPIUtil.openBlankDataJob(null,TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(),"removeReqNode");

		logger.debug("Exiting openDataJob_RemoveRequiredElement test method ... ");

	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-05/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-open a datajob with invalid element values.
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithInvalidValues() throws Exception {


		logger.debug("Entering openDataJobWithInvalidValues test method ... ");

		EhcoreAPIUtil.openBlankDataJob("invalid",TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(),"invalid");

		logger.debug("Exiting openDataJobWithInvalidValues test method ... ");


	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- open a datajob with Blank element values.
	 * EDI should return an error .400-BAD REQUEST.
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithBlankValues() throws Exception {

		logger.debug("Entering openDataJobWithBlankValues test method ... ");

		EhcoreAPIUtil.openBlankDataJob("",TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(),"blank");

		logger.debug("Exiting openDataJobWithBlankValues test method ... ");

	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- open a datajob with null element values.
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithNullValues() throws Exception {

		logger.debug("Entering openDataJobWithNullValues test method ... ");

		EhcoreAPIUtil.openBlankDataJob("null",TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(),"null");

		logger.debug("Exiting openDataJobWithNullValues test method ... ");

	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- open a datajob with emptyString element values.
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void openDataJobWithEmptyStringValues() throws Exception {

		logger.debug("Entering openDataJobWithEmptyStringValues test method ... ");

		EhcoreAPIUtil.openEmptyStringDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		logger.debug("Exiting openDataJobWithEmptyStringValues test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Open a Datajob and close the same
	 * Expected result is that it should work fine.200-OK
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobValidId() throws Exception {

		logger.debug("Entering completeDataJobValidId test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String openDatajobId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		DataJob dj1= EhcoreAPIUtil.completeDataJob(openDatajobId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		//check the datajob id's are same.
		assertTrue(openDatajobId.equalsIgnoreCase(dj1.getDataJobId()),"Open and Complete DataJob Id's are same");

		//check the status in Trcking DB 
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(openDatajobId,TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		logger.debug("Exiting completeDataJobValidId test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Close Datajob with invalid data job id
	 * EDI should return an error.400 -BAD REQUEST
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithInvalidId() throws Exception {

		logger.debug("Entering completeDataJobWithInvalidId test method ... ");

		// complete datajob with invalid datajob id
		String djId = "invalid_djId";
		EhcoreAPIUtil.completeDataJobInvalidId(djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		logger.debug("Exiting completeDataJobWithInvalidId test method ... ");

	}



	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Close Datajob with null data job id
	 * EDI should return an error.400-BAD REQUEST
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithNullDatajobId() throws Exception {


		logger.debug("Entering completeDataJobWithNullDatajobId test method ... ");

		EhcoreAPIUtil.completeDataJobInvalidId("null", TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		logger.debug("Exiting completeDataJobWithNullDatajobId test method ... ");

	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Close Datajob with blank data job id
	 * EDI should return an error.400-BAD REQUEST
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeWithBlankDatajobId() throws Exception {

		logger.debug("Entering completeWithBlankDatajobId test method ... ");

		EhcoreAPIUtil.completeDataJobInvalidId("", TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		logger.debug("Exiting completeWithBlankDatajobId test method ... ");

	}



	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- close datajob without datajobId element
	 *  EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void complete_WithoutDataJobId() throws Exception {

		logger.debug("Entering complete_WithoutDataJobId test method ... ");

		String djId = null;

		EhcoreAPIUtil.completeDataJobInvalidId(djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		logger.debug("Exiting complete_WithoutDataJobId test method ... ");

	}




	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Tests close datajob with valid djId and other transmission status-Not TRANSMISSION_END
	 *  EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeWithOtherTransmissionStatus() throws Exception {

		logger.debug("Entering CompleteWithOtherTransmissionStatus test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDataJobInvalidId(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.DONE.toString(),EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		logger.debug("Exiting CompleteWithOtherTransmissionStatus test method ... ");

	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Close Datajob with invalid content-type in Request header
	 * EDI should return an error.415-Unsupported Media Type
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithInvalidContentType() throws Exception {

		logger.debug("Entering completeDataJobWithInvalidContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob",djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),"invalid","UnsuportedType");

		logger.debug("Exiting completeDataJobWithInvalidContentType test method ... ");
	}



	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Close Datajob with null content-type in Request header
	 * EDI should return an error-Response Code -400-BAD REQUEST
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithNullContentType() throws Exception {

		logger.debug("Entering completeDataJobWithNullContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob",djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),"null",EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);


		logger.debug("Exiting completeDataJobWithNullContentType test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-Close Datajob without content-type in Request header
	 * EDI should return an error-Response Code -400 BAD REQUEST(sandbox) or 415 -UnsupportedType(Dev2)
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithoutContentType() throws Exception {

		logger.debug("Entering completeDataJobWithoutContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob",djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),"blank","UnsuportedType");


		logger.debug("Exiting completeDataJobWithoutContentType test method ... ");
	}

	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Tests close datajob with invalid url 
	 *  EDI should return an error.404 - Not Found
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithInvalidURL() throws Exception {

		logger.debug("Entering completeDataJobWithInvalidURL test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();
		EhcoreAPIUtil.sendRequestWithInvalidURL(djId,"closedatajob");

		logger.debug("Exiting completeDataJobWithInvalidURL test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Tests close datajob with valid djId & transStatus and invalidXML by removing required element
	 *  EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDatatjob_RemoveRequiredElement() throws Exception {

		logger.debug("Entering completeDatatjob_RemoveRequiredElement test method ... ");

		String value = null;

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),value);

		logger.debug("Exiting completeDatatjob_RemoveRequiredElement test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test-close a datajob with invalid element values.
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithInvalidValues() throws Exception { 


		logger.debug("Entering completeDataJobWithInvalidValues test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDataJobWithInvalid(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		logger.debug("Exiting completeDataJobWithInvalidValues test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- close a datajob with Blank element values.
	 * EDI should return an error .400-BAD REQUEST.
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithBlankValues() throws Exception { 


		logger.debug("Entering completeDataJobWithBlankValues test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),"");


		logger.debug("Exiting completeDataJobWithBlankValues test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Tests close datajob with valid djId and other required elements are null
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithNullValues() throws Exception {

		logger.debug("Entering completeDataJobWithNullValues test method ... ");

		DataJob dj1 = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj1.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),"null");

		logger.debug("Exiting completeDataJobWithNullValues test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- close a datajob with emptyString element values.
	 * EDI should return an error.400-Bad Request
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithEmptyStringValues() throws Exception { 


		logger.debug("Entering completeDataJobWithEmptyStringValues test method ... ");
		DataJob dj1 = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.CompleteDJWithEmptyStringValues(dj1.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		logger.debug("Exiting completeDataJobWithEmptyStringValues test method ... ");
	}


	/**
	 * @Author:-Kiran_GT
	 * @Date:-06/Sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce:	   
	 * Test- Complete datajob with INIT will create a new DatajobID
	 * It should work fine and return response 200-OK with new datajobId.
	 *
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void completeDataJobWithINIT() throws Exception { 


		logger.debug("Entering completeDataJobWithINIT test method ... ");

		//Open a datajob and update with TRANSMISSION_END.
		DataJob dj1 = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.completeDataJob(dj1.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		//Again update the same datajob with INIT
		DataJob dj2 = EhcoreAPIUtil.completeDataJob(dj1.getDataJobId(),TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		//check both datajob id's are different.
		assertFalse(dj1.getDataJobId().equalsIgnoreCase(dj2.getDataJobId()));

		logger.debug("Exiting completeDataJobWithINIT test method ... ");
	}

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
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

	@Test(enabled = false, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testChangeDetectionForNewPatient() throws Exception {
		log("Test Case: testChangeDetectionForNewPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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
		log("Exiting test method setup ... ");	

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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

	@Test(enabled = false, groups = {"Allscripts_Acceptance","Allscripts_Functional"}, retryAnalyzer=RetryAnalyzer.class)
	public void testDeltaMsgPersistForNewPatient() throws Exception {
		log("Test Case: testDeltaMsgPersistForNewPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");
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
		logger.debug("Previous snapshot status ::"+status);
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
		logger.debug("Exiting testASImportRawMessagePersist test method ... ");			

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

		logger.debug("Exiting testRawMessagePersistForValidMessage test method ... ");
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

		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

		log("Entering test method setup ... ");

		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

		log("Exiting test method setup ... ");	
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

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

}




