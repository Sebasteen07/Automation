package com.intuit.ihg.product.apiehcore.test;


import org.apache.log4j.Logger;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import com.allscripts.uai.schemas._2010._02._15.AllscriptsMessageEnvelope;
import org.testng.annotations.Test;
import com.intuit.dc.framework.tracking.constants.TrackingEnumHolder;
import com.intuit.dc.framework.tracking.entity.Message;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.eh.core.dto.DataJob;
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

		log("Test Case: testCCDImportTestCCDImportRawDataXSDValidation");
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
    	log("Test Case: testcheckXMLValidationForValidCCD");
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
		log("Test Case: testcheckRawMessageRetrieveForValidCcd");
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
		
		log("Test Case: testcheckXMLValidationForValidCCD");
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
		log("Test Case: testcheckActorIDValidationForValidCcd");
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
		
		log("Test Case: testcheckActorIDValidationForValidCcd");
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
		log("Test Case: testcheckActorIDValidationForValidCcd");
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
		log("Test Case: testcheckActorIDValidationForValidCcd");
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
		log("Test Case: testcheckActorIDValidationForValidCcd");
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
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0,response.getMessageDispatchHeader().getID(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EhcoreAPIConstants.AS_CCDImport);

	}
		

}