//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.apiehcore.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.allscripts.uai.schemas._2010._02._15.AllscriptsMessageEnvelope;

import org.testng.annotations.Test;

import com.intuit.dc.framework.tracking.constants.TrackingEnumHolder;
import com.intuit.dc.framework.tracking.entity.Message;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.eh.core.dto.DataJob;
import com.intuit.ihg.eh.core.dto.ProcessingResponse;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPI;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPITestData;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIUtil;
import com.intuit.ihg.product.apiehcore.utils.EhcoreTrackingDBUtils;
import com.intuit.ihg.product.apiehcore.utils.constants.CCDExportConstants;
import com.intuit.ihg.product.apiehcore.utils.constants.CCDImportConstants;
import com.intuit.ihg.product.apiehcore.utils.constants.DataJobConstant;
import com.intuit.ihg.product.apiehcore.utils.constants.EhcoreAPIConstants;
import com.intuit.ihg.product.object.maps.apiehcore.page.DataJobIDPage;
import com.mongodb.Mongo;

public class EhcoreAPIAcceptanceTests extends BaseTestNGWebDriver {
	private static DataJob dj = null;
	private static String CCDIMPORT_MSG_TYPE = "ccd";
	private static SortedMap<String, String> actualCDM = new TreeMap<String, String>();
	private static boolean status = false;
	private static String nodePath = "Header[1]";
	private static String nodeName = "Type";

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

	@Test(enabled = true, groups = { "AcceptanceTests", "FuctionalTests" })
	public void test_MongoDBConnection() throws Exception {
		Mongo m;
		try {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			m = new Mongo(testData.getMongoServerAddress());

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
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(djId, TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		log("*****Exiting open_DataJob test method ...*****");
	}

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
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId1 = dj.getDataJobId();

		log("*****Test-Open Two Datajob's and Check datajob id's are different.*****");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId2 = dj.getDataJobId();

		log("*****verify that the two datajobIds are different*****");
		assertFalse(djId1.equalsIgnoreCase(djId2));

		log("*****Exiting openTwoDataJobs test method ...*****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openWithOtherTransmissionStatus() throws Exception {

		log("*****Test Case: openWithOtherTransmissionStatus*****");
		log("*****Execution Environment:***** " + IHGUtil.getEnvironmentType());
		log("*****Entering openWithOtherTransmissionStatus test method ...***** ");

		EhcoreAPIUtil.openDJWithTransmissionEnd(TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("*****Exiting openWithOtherTransmissionStatus test method ...***** ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithInvalidContentType() throws Exception {

		log("*****Test Case: openDataJobWithInvalidContentType*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("Entering openDataJobWithInvalidContentType test method ... ");
		log("*****Open Datajob with invalid content-type in Request, Expected Error - 415 -Unsupported Media Type*****");

		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob", "", "", "invalid", "UnsuportedType");

		log("*****Exiting openDataJobWithInvalidContentType test method ...***** ");

	}

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

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithoutContentType() throws Exception {

		log("*****Test Case: openDataJobWithInvalidContentType*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJobWithoutContentType test method ... *****");

		log("***** Open Datajob without content-type in Request header EDI should return an error.415 *****");
		EhcoreAPIUtil.requestWithInvalidHeader("opendatajob", "", "", "blank", "UnsuportedType");

		log("*****Exiting openDataJobWithoutContentType test method ...***** ");

	}

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

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJob_RemoveRequiredElement() throws Exception {

		log("*****Test Case: openDataJob_RemoveRequiredElement*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJob_RemoveRequiredElement test method ...*****");

		EhcoreAPIUtil.openBlankDataJob(null, TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "removeReqNode");

		log("*****Exiting openDataJob_RemoveRequiredElement test method ...*****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithInvalidValues() throws Exception {

		log("*****Test Case: openDataJobWithInvalidValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJobWithInvalidValues test method ...*****");

		EhcoreAPIUtil.openBlankDataJob("invalid", TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "invalid");

		log("*****Exiting openDataJobWithInvalidValues test method ...*****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithBlankValues() throws Exception {

		log("*****Test Case: openDataJobWithBlankValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJobWithBlankValues test method ...*****");

		EhcoreAPIUtil.openBlankDataJob("", TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "blank");

		log("*****Exiting openDataJobWithBlankValues test method ...*****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithNullValues() throws Exception {

		log("*****Test Case: openDataJobWithNullValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJobWithNullValues test method ...*****");

		EhcoreAPIUtil.openBlankDataJob("null", TrackingEnumHolder.DATAJOB_STATUS.INIT.toString(), "null");

		log("*****Exiting openDataJobWithNullValues test method ... *****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void openDataJobWithEmptyStringValues() throws Exception {

		log("*****Test Case: openDataJobWithEmptyStringValues*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering openDataJobWithEmptyStringValues test method ...*****");

		EhcoreAPIUtil.openEmptyStringDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		log("*****Exiting openDataJobWithEmptyStringValues test method ...***** ");

	}

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
		assertTrue(openDatajobId.equalsIgnoreCase(dj1.getDataJobId()), "Open and Complete DataJob Id's are same");

		log("*****check the status in Trcking DB*****");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(openDatajobId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("*****Exiting completeDataJobValidId test method ... *****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidId() throws Exception {

		log("*****Test Case: completeDataJobWithInvalidId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering completeDataJobWithInvalidId test method ...*****");

		String djId = "invalid_djId";
		EhcoreAPIUtil.completeDataJobInvalidId(djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting completeDataJobWithInvalidId test method ...*****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithNullDatajobId() throws Exception {

		log("*****Test Case: completeDataJobWithNullDatajobId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering completeDataJobWithNullDatajobId test method ... *****");

		EhcoreAPIUtil.completeDataJobInvalidId("null", TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting completeDataJobWithNullDatajobId test method ...*****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeWithBlankDatajobId() throws Exception {

		log("*****Test Case: completeWithBlankDatajobId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering completeWithBlankDatajobId test method ...*****");

		EhcoreAPIUtil.completeDataJobInvalidId("", TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting completeWithBlankDatajobId test method ... *****");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void complete_WithoutDataJobId() throws Exception {

		log("*****Test Case: complete_WithoutDataJobId*****");
		log("*****Execution Environment: " + IHGUtil.getEnvironmentType());

		log("*****Entering complete_WithoutDataJobId test method ... *****");

		String djId = null;

		EhcoreAPIUtil.completeDataJobInvalidId(djId, TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("*****Exiting complete_WithoutDataJobId test method ...***** ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeWithOtherTransmissionStatus() throws Exception {

		log("Entering CompleteWithOtherTransmissionStatus test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDataJobInvalidId(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.DONE.toString(),
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("Exiting CompleteWithOtherTransmissionStatus test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidContentType() throws Exception {

		log("Entering completeDataJobWithInvalidContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob", djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(), "invalid", "UnsuportedType");

		log("Exiting completeDataJobWithInvalidContentType test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithNullContentType() throws Exception {

		log("Entering completeDataJobWithNullContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob", djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(), "null",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);

		log("Exiting completeDataJobWithNullContentType test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithoutContentType() throws Exception {

		log("Entering completeDataJobWithoutContentType test method ... ");

		// open datajob and retrieve its datajobId
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// close the same datajob with Transmission Status "TRANSMISSION_END"
		EhcoreAPIUtil.requestWithInvalidHeader("closedatajob", djId,
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(), "blank", "UnsuportedType");

		log("Exiting completeDataJobWithoutContentType test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidURL() throws Exception {

		log("Entering completeDataJobWithInvalidURL test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();
		EhcoreAPIUtil.sendRequestWithInvalidURL(djId, "closedatajob");

		log("Exiting completeDataJobWithInvalidURL test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDatatjob_RemoveRequiredElement() throws Exception {

		log("Entering completeDatatjob_RemoveRequiredElement test method ... ");

		String value = null;

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(), value);

		log("Exiting completeDatatjob_RemoveRequiredElement test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithInvalidValues() throws Exception {

		log("Entering completeDataJobWithInvalidValues test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDataJobWithInvalid(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("Exiting completeDataJobWithInvalidValues test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithBlankValues() throws Exception {

		log("Entering completeDataJobWithBlankValues test method ... ");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(), "");

		log("Exiting completeDataJobWithBlankValues test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithNullValues() throws Exception {

		log("Entering completeDataJobWithNullValues test method ... ");

		DataJob dj1 = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.completeDJWithInvalidXml(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString(), "null");

		log("Exiting completeDataJobWithNullValues test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithEmptyStringValues() throws Exception {

		log("Entering completeDataJobWithEmptyStringValues test method ... ");
		DataJob dj1 = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.CompleteDJWithEmptyStringValues(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("Exiting completeDataJobWithEmptyStringValues test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void completeDataJobWithINIT() throws Exception {

		log("Entering completeDataJobWithINIT test method ... ");

		// Open a datajob and update with TRANSMISSION_END.
		DataJob dj1 = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.completeDataJob(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		// Again update the same datajob with INIT
		DataJob dj2 = EhcoreAPIUtil.completeDataJob(dj1.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		// check both datajob id's are different.
		assertFalse(dj1.getDataJobId().equalsIgnoreCase(dj2.getDataJobId()));

		log("Exiting completeDataJobWithINIT test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_START.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportRawDataXSDValidation() throws Exception {

		log("Test Case: testCCDImportRawDataXSDValidation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString

				(), EhcoreAPIConstants.RAWXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testXMLValidationForValidCCD() throws Exception {
		log("Test Case: testXMLValidationForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString

				(), EhcoreAPIConstants.XMLVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessageRetrieveForValidCcd() throws Exception {
		log("Test Case: testRawMessageRetrieveForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString

				(), EhcoreAPIConstants.RAWRETRIEVAL));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIdEnrichmentforValidCCD() throws Exception {

		log("Test Case: testKREnrichmentforValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString

				(), EhcoreAPIConstants.IDENRICHMENT));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActorIDValidationForValidCcd() throws Exception {
		log("Test Case: testActorIDValidationForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString

				(), EhcoreAPIConstants.ACTOR_ID));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPublishCCDNotificationForValidCCD() throws Exception {

		log("Test Case: testPublishCCDNotificationForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString

				(), EhcoreAPIConstants.PUBLISHNOTIFICATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDXSDValidationForValidCcd() throws Exception {
		log("Test Case: testCCDXSDValidationForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString

				(), EhcoreAPIConstants.CCDXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCD2SimpleTranslateForValidCcd() throws Exception {
		log("Test Case: testCCD2SimpleTranslateForValidCcd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString

				(), EhcoreAPIConstants.CCD2SIMPLE));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateSnapshotForValidCCD() throws Exception {
		log("Test Case: testCreateSnapshotForValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString

				(), EhcoreAPIConstants.SNAPSHOTCREATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCheckTransmissionStart() throws Exception {

		log("Test Case: TestASImportMessage");
		log("Test Case: testCheckTransmissionStart");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());

		log("Entering check_TransmissionStart test method ... ");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDImportConstants.AS_CCD_IMPORT);

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.CCD_RAW + "RawCCDExchange1.xml";

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString

				(), EhcoreAPIConstants.RAWPERSISTANCE));

		log("Check mongoDB response using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString

				());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(), obj_ref_id,
				expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testChangeDetectionForNewPatient() throws Exception {
		log("Test Case: testChangeDetectionForNewPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.CCD_RAW + "RawCCDExchange1.xml";

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		log("Get Objectref details from tracking DB");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(), obj_ref_id,
				expectedCcd);

		log("Check mongoDB response using API");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), msgDetails.get(0).getQid().toString());

		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(0).getMessageGuid(), EhcoreAPIConstants.NODE_PATH,
				EhcoreAPIConstants.NODE_NAME, "");
		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		log("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());
		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.NEW_CCD, actualCDM);
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString

				(), EhcoreAPIConstants.DELTAENRICHMENT));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString

				(), EhcoreAPIConstants.SIMPLE2HUBCANONICAL));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString

				(), EhcoreAPIConstants.CLAIMCHECK));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString

				(), EhcoreAPIConstants.PUBLISHCANONICAL));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		// String expectedCcd = EhcoreAPIConstants.CCD_RAW + "RawCCDExchange1.xml";
		String expectedCcd = CCDImportConstants.CCD_ENRICHED + "EnrichedCCDExchange1.xml";

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString

				(), EhcoreAPIConstants.ENRICHED));

		log("Check mongo DB status using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString(),
				obj_ref_id, expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.CCD_SNAPSHOT + "SnapshotCCDExchange1.xml";

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString

				(), EhcoreAPIConstants.SNAPSHOTPERSIST));

		log("Check mongo DB status using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
				obj_ref_id, expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersistForNewPatient() throws Exception {
		log("Test Case: testDeltaMsgPersistForNewPatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.CCD_DELTA + "DeltaCCDExchange1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString

				(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedCcd);

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotMessageWithNoUpdates() throws Exception {
		log("Test Case: testSnapshotMessageWithNoUpdates");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		String expectedCcd = CCDImportConstants.CCD_SNAPSHOT + "SnapshotCCDExchange1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD again for the same patient(with the same subject_id)
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedCcd);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting checkSnapshotMessageWithNoUpdates test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASImportMessageTransmissionStart() throws Exception {
		log("Test Case: testASImportMessageTransmissionStart");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDImportConstants.AS_CCD_IMPORT);
		log("Exiting testASImportMessageTransmissionStart test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASImportMessageJobStatusDone() throws Exception {
		log("Test Case: testASImportMessageJobStatusDone");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);

		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDImportConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

		log("Exiting testASImportMessageJobStatusDone test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASImportRawMessagePersist() throws Exception {
		log("Test Case: testASImportRawMessagePersist");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID

		(), TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDImportConstants.AS_CCD_IMPORT);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString

				(), EhcoreAPIConstants.AS_RAWPERSIST));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString

				());
		log("Exiting testASImportRawMessagePersist test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASImportXMLValidation() throws Exception {
		log("Test Case: testASImportXMLValidation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDImportConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString

				(), EhcoreAPIConstants.AS_XMLVALIDATION));

		log("Exiting testASImportXMLValidation test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASImportRawMessageRetrieval() throws Exception {
		log("Test Case: testASImportRawMessageRetrieval");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);

		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDImportConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString

				(), EhcoreAPIConstants.AS_RAWRETRIEVAL));

		log("Exiting testASImportRawMessageRetrieval test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASTranslationSmooks2CcdExchange() throws Exception {
		log("Test Case: testASTranslationSmooks2CcdExchange");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);

		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDImportConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString

				(), EhcoreAPIConstants.AS_TRANSLATION));

		log("Exiting testASTranslationSmooks2CcdExchange test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASImportDCPublishActivity() throws Exception {
		log("Test Case: testASImportDCPublishActivity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msg = new ArrayList<Message>();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		AllscriptsMessageEnvelope response = EhcoreAPIUtil.sendAllscriptsImportMessage(testData,
				CCDImportConstants.AS_CCD);

		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageDispatchHeader().getID(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDImportConstants.AS_CCD_IMPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(msg.get(0).getDataJob().getDataJobGuid(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msg.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString

				(), EhcoreAPIConstants.AS_PUBLISH));

		log("Exiting testASImportDCPublishActivity test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessageRetrieveForAllScriptAdapterCCDExportMsg() throws Exception {
		log("Test Case: testRawMessageRetrieveForAllScriptAdapterCCDExportMsg");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				CCDExportConstants.AS_CCD_EXPORT);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), CCDExportConstants.AS_CCD_EXPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString

				(), EhcoreAPIConstants.AS_RAWRETRIEVAL));

		log("Exiting testRawMessageRetrieveForAllScriptAdapterCCDExportMsg test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDCPublish2ConnectHubActivity() throws Exception {
		log("Test Case: testDCPublish2ConnectHubActivity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				CCDExportConstants.AS_CCD_EXPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		log("Exiting testDCPublish2ConnectHubActivity test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllScriptAdapterCCDExportTranslateSimpleCCDtoCDA() throws Exception {
		log("Test Case: testAllScriptAdapterCCDExportTranslateSimpleCCDtoCDA");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1 :- Getting data from excel ");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Step 2 :- Sending request ");

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				CCDExportConstants.AS_CCD_EXPORT);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), CCDExportConstants.AS_CCD_EXPORT);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString

				(), EhcoreAPIConstants.ASEXPORT_TRANSLATION));
		log("Exiting testAllScriptAdapterCCDExportTranslateSimpleCCDtoCDA test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessagePersistForValidMessage() throws Exception {
		log("Test Case: testRawMessagePersistForValidMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				CCDExportConstants.AS_CCD_EXPORT);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), CCDExportConstants.AS_CCD_EXPORT);// .EH_REST_API_Consts.AS_CCDExport);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString

				(), EhcoreAPIConstants.AS_RAWPERSIST));
		log("Exiting testRawMessagePersistForValidMessage test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDExportMessageJobStatusType_ERROR() throws Exception {
		log("Test Case: testCCDExportMessageJobStatusType_Done");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Sending valid ccd export message");
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"validExportMsg");
		log("Validating the process 'ERROR' status message");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), EhcoreAPIConstants.CCDEXPORT);

		log("Validating the process 'DONE' status message");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());
		/*
		 * ProcessingResponse response =
		 * EHCoreUtil.sendCCDExportMessage(UUID.randomUUID().toString(),"validExportMsg"
		 * ); EHCoreTestUtil.verifyExpectedMessageProcStatus(0,response.getDataJobId(),
		 * TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),EHCoreTestConsts.
		 * EH_REST_API_Consts.CCDExport);
		 * EHCoreTestUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
		 * TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());
		 */

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessagePersistanceForValidCCDMessage() throws Exception {

		log("Test Case: testRawMessagePersisitanceForValidCCDMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDExportConstants.CCD_EXPORT_DATA_RAW + "RawCCDMessageType.xml";
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"validExportMsg");

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), EhcoreAPIConstants.CCDEXPORT);
		log("Verify the tracking DB status");
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.RAWPERSISTANCE));

		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());

		log("Verify the Mongo DB response using API");
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(), obj_ref_id,
				expectedCcd);

		log("Exiting checkRawMessagePersisitanceForValidCCDMessage test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessageRetrieveForCCDExport() throws Exception {

		log("Test Case: testRawMessageRetrieveForCCDExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		log("Entering checkRawMessageRetrieveForCCDExport test method ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), EhcoreAPIConstants.CCDEXPORT);

		log("Verify the Tracking DB response.");
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.RAWRETRIEVAL));

		log("Exiting checkRawMessageRetrieveForCCDExport test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTranslateSimpleCCDtoCCD() throws Exception {

		log("Test Case: testTranslateSimpleCCDtoCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering checkTranslateSimpleCCDtoCCDForValidCCDMsg test method ... ");

		List<Message> msgDetails = new ArrayList<Message>();
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), EhcoreAPIConstants.CCDEXPORT);

		log("Verify the Tracking DB status");
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString

				(), EhcoreAPIConstants.TRANSLATESIMPLECCDTOCCD));

		log("Exiting checkTranslateSimpleCCDtoCCDForValidCCDMsg test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTranslateCDMtoCCDExchange() throws Exception {

		log("Test Case: testTranslateCDMtoCCDExchange");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"validExportMsg");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), EhcoreAPIConstants.CCDEXPORT);

		log("Verify the DB status");
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString

				(), EhcoreAPIConstants.TRANSLATECDMTOCCDEXCHANGE));

		log("Exiting testTranslateCDMtoCCDExchange test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRouteMsgToPractice() throws Exception {

		log("Test Case: testRouteMsgToPractice");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering checkRouteMsgToPractice test method ... ");

		// List<Message> msgDetails = new ArrayList<Message>();

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"validExportMsg");
		// msgDetails =
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), EhcoreAPIConstants.CCDEXPORT);

		log("Verify the DB Status.");
		log("Exiting testRouteMsgToPractice test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDExportJobStatusType_Error() throws Exception {

		log("Test Case: testCCDExportJobStatusType_Error");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send CCD  export message");
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"invalidExportMsg");

		log("Verify the Message procesing status");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), EhcoreAPIConstants.CCDEXPORT);

		log("Verify the Data processing status");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDExportSendMessageWithBlankUPN() throws Exception {

		log("Test Case: testCCDExportSendMessageWithBlankUPN");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Exiting test method setup ... ");
		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		log("Send Invalid CCD message and verify the processing status");
		EhcoreAPIUtil.sendInvalidCCDMessage("", UUID.randomUUID().toString(), "InvalidCCDExport");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendMessageWithNullUPN() throws Exception {

		log("Test Case: testSendMessageWithNullUPN");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send Invalid CCD message with ***NULL UPN and verify the processing status");
		EhcoreAPIUtil.sendInvalidCCDMessage("null", UUID.randomUUID().toString(), "InvalidCCDExport");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendMessageWithBlankMsgId() throws Exception {

		log("Test Case: testSendMessageWithBlankMsgId");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send Invalid CCD message with Blank Message ID and verify the processing status");
		EhcoreAPIUtil.sendInvalidCCDMessage(UUID.randomUUID().toString(), "", "InvalidCCDExport");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendMessageWithNullMsgId() throws Exception {

		log("Test Case: testSendMessageWithNullMsgId");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send Invalid CCD message with NULL Message ID and verify the processing status");
		EhcoreAPIUtil.sendInvalidCCDMessage(UUID.randomUUID().toString(), "null", "InvalidCCDExport");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendMessageWithEmptyStringUPN() throws Exception {

		log("Test Case: testSendMessageWithEmptyStringUPN");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send CCD message with empty string UPN");
		ProcessingResponse response = EhcoreAPIUtil.sendMessage_EmptyStringValues("", "export");

		log("Verify the processing status after sending CCD message with empty string UPN");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), EhcoreAPIConstants.CCDEXPORT);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendValidMsgWithInvalidContentType() throws Exception {

		log("Test Case: testSendValidMsgWithInvalidContentType");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send CCD with INVALID Content type and verify the status.");
		EhcoreAPIUtil.requestWithInvalidHeader("ccdexport", "", "", "invalid", "UnsuportedType");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendValidMsgWithNullContentType() throws Exception {

		log("Test Case: testSendValidMsgWithNullContentType");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send CCD with NULL contant ype and verify the processing status");
		EhcoreAPIUtil.requestWithInvalidHeader("ccdexport", "", "", "null", "BadRequest");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendValidMsgWithoutContentType() throws Exception {

		log("Test Case: testSendValidMsgWithoutContentType");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send valid message without content type and verify the processing status.");
		EhcoreAPIUtil.requestWithInvalidHeader("ccdexport", "", "", "null", "BadRequest");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendMsgWithInvalidURL() throws Exception {

		log("Test Case: testSendMsgWithInvalidURL");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Send CCD message with invalid URL and verify the processing status.");
		EhcoreAPIUtil.sendRequestWithInvalidURL("", "ccdexport");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessagePersisitanceForInvalidCCDMessage() throws Exception {

		log("Test Case: testRawMessagePersisitanceForInvalidCCDMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();

		log("Setting the expected CCD");
		String expectedCcd = CCDExportConstants.CCD_EXPORT_DATA_ERROR + "ErrorCCDMessageType.xml";

		log("Send invalid ccd export message.");
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(UUID.randomUUID().toString(),
				"invalidExportMsg");

		log("Verify the message processing status.");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), EhcoreAPIConstants.CCDEXPORT);

		log("Pass nodeType and get the obj_ref_id in Tracking DB");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());

		log("check the ERROR Message in ObjectStore(Mongo DB)and compare with expectedCcd using XMLUnit");
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(), obj_ref_id,
				expectedCcd);

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);

		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msg = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);

		assertFalse(msg.get(0).getQid().equals(msg.get(1).getQid()));

		log("Verify the CCD message status is 'Completed'");
		EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("Verify the Data job message status is 'Done' in Tracking DB");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		log("Verify the CCD message status is 'Completed'");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		log("Step 2: Send the invalid CCD message");
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.INVALIDCCD);

		log("Verify the CCD message status is 'ERROR'");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		log("Verify the CCD message status is 'Completed'");
		EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("Verify the Data job message status is 'Partially Done' in Tracking DB");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.PARTIALLY_DONE.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", testData.getPatientID(),
				EhcoreAPIConstants.INVALIDCCD);

		log("Verify the ccd message status 'ERROR'");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		log("Verify the data job status 'ERROR'");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportsendValidMsgWithInvalidContentType() throws Exception {

		log("Test Case: testCCDImportsendValidMsgWithInvalidContentType");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		log("Transmit valid ccd with invalid Content type in Header and Verify error response:415");
		EhcoreAPIUtil.requestWithInvalidHeader("ccdimport", "", "", "invalid", "UnsuportedType");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportsendValidMsgWithNullContentType() throws Exception {

		log("Test Case: testCCDImportsendValidMsgWithNullContentType");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		log("Transmit valid ccd data with null Content type in Header and Verify error response:400");
		EhcoreAPIUtil.requestWithInvalidHeader("ccdimport", "", "", "null", "BadRequest");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportsendValidMsgWithoutContentType() throws Exception {

		log("Test Case: testCCDImportsendValidMsgWithoutContentType");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		log("Open Datajob without content-type in Request header and Verify error response:415");
		EhcoreAPIUtil.requestWithInvalidHeader("ccdimport", "", "", "blank", "UnsuportedType");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendRequestWithInvalidURL(djId, "ccdimport");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportsendValidMsgWithBlankUPN() throws Exception {

		log("Test Case: testCCDImportsendValidMsgWithBlankUPN");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		// Open a datajob and get the datajob id
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// Transmit valid CCD with null UPN
		EhcoreAPIUtil.sendMessage_InvalidUPN(djId, "", "BadRequest");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportsendValidMsgWithNullUPN() throws Exception {

		log("Test Case: testCCDImportsendValidMsgWithNullUPN");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		// Open a datajob and get the datajob id
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		String djId = dj.getDataJobId();

		// Transmit valid CCD with null UPN
		EhcoreAPIUtil.sendMessage_InvalidUPN(djId, "null", "Accepted");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportsendValidMsgWithEmptyStringUPN() throws Exception {

		log("Test Case: testCCDImportsendValidMsgWithEmptyStringUPN");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		// Open a datajob and get the datajob id
		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		// Transmit valid CCD without UPN
		EhcoreAPIUtil.sendMessage_EmptyStringValues(dj.getDataJobId(), "import");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportCompleteInActiveDatajob() throws Exception {

		log("Test Case: testCCDImportCompleteInActiveDatajob");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		// Open a datajob and transmit input and update with TRANSMISSION_END.

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

		log("check the transmission status in Tracking DB");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

		log("Again update datajob with TRANSMISSION_END");
		EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
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
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(),
				EhcoreAPIConstants.NULL_CODE_SOCIAL_HISTORY);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.CCDXSDVALIDATION));

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportTransStartConsolidatedCCD() throws Exception {

		log("Test Case: testCCDImportTransStartConsolidatedCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.c_ccd);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_START.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportJobStatusTypeDoneConsolidatedCCD() throws Exception {

		log("Test Case: testCCDImportJobStatusTypeDoneConsolidatedCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.c_ccd);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportJobStatusPartiallyDoneConsolidatedCCD() throws Exception {

		log("Test Case: testCCDImportJobStatusPartiallyDoneConsolidatedCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		dj = EhcoreAPIUtil.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());

		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.c_ccd);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), "", EhcoreAPIConstants.invalidc_ccd);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		EhcoreAPIUtil.completeDataJob(dj.getDataJobId(), TrackingEnumHolder.DATAJOB_STATUS.TRANSMISSION_END.toString());
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(dj.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.PARTIALLY_DONE.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportJobStatusErrorConsolidatedCCD() throws Exception {

		log("Test Case: testCCDImportJobStatusErrorConsolidatedCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", "", EhcoreAPIConstants.invalidc_ccd);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportNullCodeSocialHistory_CCCD() throws Exception {

		log("Test Case: testCCDImportNullCodeSocialHistory_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		List<Message> msgDetails = new ArrayList<Message>();
		ProcessingResponse response = EhcoreAPIUtil.sendMessage

		("", EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.nullCodeCCCDSocialHistory);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.CCDXSDVALIDATION));

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportNoKnownC_CCD() throws Exception {

		log("Test Case: testCCDImportNullCodeSocialHistory_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log(EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml");
		log(EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,
				EhcoreAPIConstants.noknown_c_ccd);
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.DONE.toString());

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportRawDataXSDValidationForInvalidCCD() throws Exception {

		log("Test Case: testCCDImportRawDataXSDValidationForInvalidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.INVALIDCCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.RAWXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportRawDataXSDValidationForvalid_C_CCD() throws Exception {

		log("Test Case: testCCDImportRawDataXSDValidationForInvalid_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.RAWXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportRawDataXSDValidationForInvalid_C_CCD() throws Exception {

		log("Test Case: testCCDImportRawDataXSDValidationForInvalid_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.invalidc_ccd);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.RAWXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASCCDExportMessageJobStatusTypeDone() throws Exception {
		log("Test Case: testASCCDExportMessageJobStatusTypeDone");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step1:- getting value from the excel");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("step2:- sending the request and getting the response");
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				CCDExportConstants.AS_CCD_EXPORT);

		log("step3:- sending the request and getting the response");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), CCDExportConstants.AS_CCD_EXPORT);

		log("step4:- sending the request and getting the response");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		log("Exiting testASCCDExportMessageJobStatusTypeDone test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testErrorRawMsgPersistForCCD() throws Exception {
		log("Test Case: testErrorRawMsgPersistForCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.CCD_ERROR + "ErrorCCDExchange1.xml";

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", testData.getPatientID(),
				EhcoreAPIConstants.INVALIDCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.ERRORPERSISTANCE));// EhcoreAPIConstants.CCDXSDVALIDATION));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		log("Check mongoDB response using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString(),
				obj_ref_id, expectedCcd);

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMsgPersistFor_C_CCD() throws Exception {
		log("Test Case: testRawMsgPersistFor_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.C_CCD_RAW + "RawCCCD1.xml";
		log("Check tracking DB message_status as completed");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.CCDXSDVALIDATION));

		log("Check mongoDB response using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString(), obj_ref_id,
				expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testErrorRawMsgPersistFor_C_CCD() throws Exception {
		log("Test Case: testErrorRawMsgPersistFor_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		log("Open DataJob");

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.C_CCD_ERROR + "ErrorCCCD1.xml";

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", "", EhcoreAPIConstants.invalidc_ccd);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.ERRORPERSISTANCE));

		log("Check mongoDB response using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString(),
				obj_ref_id, expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMsgPersistForNoKnownC_CCD() throws Exception {
		log("Test Case: testRawMsgPersistForNoKnownC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedCcd = CCDImportConstants.C_CCD_RAW + "RawNoKnownCCCD1.xml";

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,
				EhcoreAPIConstants.noknown_c_ccd);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.RAWPERSISTANCE));

		log("Check mongoDB response using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ERROR.toString(),
				obj_ref_id, expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testXMLValidationForInValidCCD() throws Exception {

		log("Test Case: testXMLValidationForInValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		List<Message> msgDetails = new ArrayList<Message>();

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", "", EhcoreAPIConstants.INVALID_XML_VALIDATION);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.XMLVALIDATION));
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.ERRORPERSISTANCE));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testXMLValidationForValid_C_CCD() throws Exception {
		log("Test Case: testXMLValidationForValid_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.XMLVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testXMLValidationForInValid_C_CCD() throws Exception {
		log("Test Case: testXMLValidationForInValid_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", "",
				EhcoreAPIConstants.INVALID_C_CCD_XML_VALIDATION);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.XMLVALIDATION));
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.ERRORPERSISTANCE));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessageRetrieveForValidC_CCD() throws Exception {
		log("Test Case: checkRawMessageRetrieveForValidC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.RAWRETRIEVAL));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIdEnrichmentforValidC_CCD() throws Exception {
		log("Test Case: testKREnrichmentforValidC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.IDENRICHMENT));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActionIdValidationC_CCD() throws Exception {
		log("Test Case: testActionIdValidationC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.ACTOR_ID));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCD2SimpleTranslateForC_CCD() throws Exception {
		log("Test Case: testCCD2SimpleTranslateForC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(), EhcoreAPIConstants.CCD2SIMPLE));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateSnapshotForValidC_CCD() throws Exception {
		log("Test Case: testCCD2SimpleTranslateForC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.SNAPSHOTCREATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaEnrichmentForValidC_CCD() throws Exception {
		log("Test Case: testDeltaEnrichmentForValidC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.DELTAENRICHMENT));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSimple2HubCanonicalForC_CCD() throws Exception {
		log("Test Case: testSimple2HubCanonicalForC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.TRANSLATE.toString(), EhcoreAPIConstants.SIMPLE2HUBCANONICAL));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testClaimCheckForValidC_CCD() throws Exception {
		log("Test Case: testSimple2HubCanonicalForC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(), EhcoreAPIConstants.CLAIMCHECK));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHubPublishForC_CCD() throws Exception {
		log("Test Case: testHubPublishForC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(), EhcoreAPIConstants.PUBLISHCANONICAL));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPublishCCDNotificationForValidC_CCD() throws Exception {
		log("Test Case: testPublishCCDNotificationForValidC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(), EhcoreAPIConstants.PUBLISHNOTIFICATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPublishCCDNotificationForInValidCCD() throws Exception {

		log("Test Case: testPublishCCDNotificationForInValidCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("",
				EhcoreAPIConstants.INTUITPATIENTID_INVALIDCCDNOTIFICATION, EhcoreAPIConstants.INVALID_CCD_NOTIFICATION);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(), EhcoreAPIConstants.PUBLISHNOTIFICATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPublishCCDNotificationForInValid_C_CCD() throws Exception {

		log("Test Case: testPublishCCDNotificationForInValid_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("",
				EhcoreAPIConstants.INTUITPATIENTID_INVALIDCCDNOTIFICATION, EhcoreAPIConstants.INVALID_CCD_NOTIFICATION);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ROUTE.toString(), EhcoreAPIConstants.PUBLISHNOTIFICATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDXSDValidationForValidC_CCD() throws Exception {

		log("Test Case: testCCDXSDValidationForValidC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.CCDXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDXSDValidationForInvalidCcd() throws Exception {

		log("Test Case: CCDXSDValidationForInvalidCcd()");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("",
				EhcoreAPIConstants.INTUITPATIENTID_INVALIDCCDNOTIFICATION, EhcoreAPIConstants.INVALID_CCD_NOTIFICATION);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);

		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.CCDXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDXSDValidationForInvalid_C_Ccd() throws Exception {
		log("Test Case: testCCDXSDValidationForInvalid_C_Ccd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		ProcessingResponse response = EhcoreAPIUtil.sendMessage

		("", EhcoreAPIConstants.INTUITPATIENTID_INVALIDCCDNOTIFICATION, EhcoreAPIConstants.invalidCCCDNotification);
		List<Message> msgDetails = new ArrayList<Message>();
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDIMPORT_MSG_TYPE);
		assertFalse(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.VALIDATE.toString(), EhcoreAPIConstants.CCDXSDVALIDATION));
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEnrichedMsgPersistForValid_C_Ccd() throws Exception {
		log("Test Case: testEnrichedMsgPersistForValid_C_Ccd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Open DataJob");
		List<Message> msgDetails = new ArrayList<Message>();

		String expectedCcd = CCDImportConstants.C_CCD_ENRICHED + "EnrichedCCCD1.xml";

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.ENRICHED));

		log("Check mongo DB status using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString(),
				obj_ref_id, expectedCcd);
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEnrichedMsgPersistForNoKnownC_Ccd() throws Exception {
		log("Test Case: testEnrichedMsgPersistForNoKnownC_Ccd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Open DataJob");
		List<Message> msgDetails = new ArrayList<Message>();

		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,
				EhcoreAPIConstants.noknown_c_ccd);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);

		String expected_C_Ccd = CCDImportConstants.C_CCD_ENRICHED + "EnrichedNoKnownCCCD1.xml";

		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.ENRICHED));

		log("Check mongo DB status using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.ENRICHED.toString(),
				obj_ref_id, expected_C_Ccd);
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithAddedMessage_Entity() throws Exception {
		log("Test Case: testSnapshotWithAddedMessage_Entity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expectedCcd_entity = EhcoreAPIConstants.SNAPSHOT_ENTITY_CCD + "add_entity_Snapshot.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addentityCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedCcd_entity);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting checkSnapshotMessageWithNoUpdates test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithAddedMessage_Element() throws Exception {
		log("Test Case: testSnapshotWithAddedMessage_Element");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expectedCcd_element = EhcoreAPIConstants.SNAPSHOT_ELEMENT_CCD + "add_element_Snapshot.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with added details for the same patient(with the same
		// subject_id).Added @ Entity level(2 Problem Sections).
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addelementCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString

			(), obj_ref_id, expectedCcd_element);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting checkSnapshotMessageWithNoUpdates test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithUpdatedMessage() throws Exception {
		log("Test Case: testSnapshotWithUpdatedMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expectedCcd = EhcoreAPIConstants.SNAPSHOT_ENTITY_CCD + "update_Snapshot.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with updated details for the same patient(with the same
		// subject_id)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.updateCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedCcd);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting checkSnapshotMessageWithNoUpdates test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithDeletedMessage() throws Exception {
		log("Test Case: testSnapshotWithDeletedMessage");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		List<Message> msgDetails = new ArrayList<Message>();

		String expectedCcd = CCDImportConstants.CCD_SNAPSHOT + "SnapshotCCDExchange1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with updated details for the same patient(with the same
		// subject_id)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.deleteCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.delete2CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(2, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(2).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(2).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(2).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedCcd);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting checkSnapshotMessageWithNoUpdates test method ... ");

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotMessageWithNoUpdates_C_CCD() throws Exception {
		log("Test Case: testSnapshotMessageWithNoUpdates_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expected_C_Ccd = CCDImportConstants.C_CCD_SNAPSHOT + "SnapshotCCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with updated details for the same patient(with the same
		// subject_id)
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(), EhcoreAPIConstants.c_ccd);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expected_C_Ccd);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting testSnapshotMessageWithNoUpdates_C_CCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithAddedMessage_Entity_C_CCD() throws Exception {
		log("Test Case: testSnapshotWithAddedMessage_Entity_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		String expectedCcd_entity = EhcoreAPIConstants.SNAPSHOT_ENTITY_C_CCD_RES + "add_entity_Snapshot_CCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ENTITY_CCCD,
				EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with added details for the same patient(Added 1 Vital Signs
		// componenet :02SAT ).
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addentityC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedCcd_entity);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting testSnapshotMessageWithNoUpdates_C_CCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithUpdatedMessage_C_CCD() throws Exception {
		log("Test Case: testSnapshotWithUpdatedMessage_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expectedCcd = EhcoreAPIConstants.SNAPSHOT_ENTITY_C_CCD_RES + "update_Snapshot_CCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_UPDATE_CCCD,
				EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with updated details for the same patient(with the same
		// subject_id)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.updateC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedCcd);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting testSnapshotMessageWithNoUpdates_C_CCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotWithDeletedMessage_C_CCD() throws Exception {
		log("Test Case: testSnapshotWithDeletedMessage_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Entering checkSnapshotMessageWithNoUpdates test method ... ");
		String expectedC_Ccd = CCDImportConstants.C_CCD_SNAPSHOT + "SnapshotCCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Transmit CCD with deleted details for the same patient(with the same
		// subject_id):Delete Social History:FormerSmoker
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.deleteC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		// Delete ; Vital Sign :Height
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.delete2C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(2, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), EhcoreAPIConstants.CCDImport);

		status = EhcoreTrackingDBUtils.isSnapshotPreviousVersionPresent(dj.getDataJobId(),
				msgDetails.get(1).getQid().toString());
		log("Previous snapshot status ::" + status);
		if (status) {

			assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.ACTIVITY_TYPE.RETRIEVE.toString(), EhcoreAPIConstants.SNAPSHOTRETRIEVE));
			// Pass nodeType and get the obj_ref_id in Tracking DB
			String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

			(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
			// check snapshot version contains the merged response of previous and current
			// version using XMLUnit.
			// checkMsgInMongoDB(obj_ref_id,expectedCcd);
			EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
					obj_ref_id, expectedC_Ccd);

		} else {
			fail("Error in Snapshot Message Retrieve for Subject ID ::" + msgDetails.get(0).getSubjectId()
					+ "Datajob id ::" + dj.getDataJobId());
		}

		log("Exiting testSnapshotMessageWithNoUpdates_C_CCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotMsgPersistForValidC_CCD() throws Exception {
		log("Test Case: testSnapshotMsgPersistForValidC_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedC_Ccd = CCDImportConstants.C_CCD_SNAPSHOT + "SnapshotCCCD1.xml";

		log("Check tracking DB message_status as completed");
		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_CCCD,
				EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.SNAPSHOTPERSIST));

		log("Check mongo DB status using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
				obj_ref_id, expectedC_Ccd);
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSnapshotMsgPersistForNoKnownC_Ccd() throws Exception {
		log("Test Case: testSnapshotMsgPersistForNoKnownC_Ccd");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedC_Ccd = CCDImportConstants.C_CCD_SNAPSHOT + "SnapshotNoKnownCCCD1.xml";

		log("Check tracking DB message_status as completed");
		ProcessingResponse response = EhcoreAPIUtil.sendMessage("", EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,
				EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.SNAPSHOTPERSIST));

		log("Check mongo DB status using API");
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString());
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.SNAPSHOT.toString(),
				obj_ref_id, expectedC_Ccd);
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithNoUpdates() throws Exception {
		log("Test Case: testCDWithNoUpdates");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message again without any updates
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "NoUpdates");
		assertEquals(0, actualCDM.size());

		/**
		 * It shows complete CCDMessage with NoUpdates in CCD Items for
		 * ex:<AllergyList/> Get the Actual CDM Message and compare with expected using
		 * message_guid in MongoDB
		 */
		// EHCoreUtil.compareCDMList("NoUpdates",actualCDM);
		log("Exiting checkChangeDetectionWithNoUpdates test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithAdd_Entity() throws Exception {
		log("Test Case: testCDWithAdd_Entity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ENTITY,
				EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message again without any updates
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addentityCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), msgDetails.get(0).getQid().toString());
		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());
		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.addentityCCD, actualCDM);

		log("Exiting checkCDWithAdd_Entity test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithAdd_Element() throws Exception {
		log("Test Case: testCDWithAdd_Element");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ELEMENT,
				EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message with added element details.(added medication drug code
		// displayname)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addelementCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		/**
		 * To get the Added CDM List we need to check the second message in this
		 * subject_id.
		 */
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), msgDetails.get(0).getQid().toString());
		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB SLF jar error
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());
		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.addelementCCD, actualCDM);
		log("Exiting testCDWithAdd_Element test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithUpdatedDetails() throws Exception {
		log("Test Case: testCDWithUpdatedDetails");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_UPDATE,
				EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message with updated details(updated Allery section)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.updateCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));
		/**
		 * To get the Added CDM List we need to check the second message in this
		 * subject_id.
		 */
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), msgDetails.get(0).getQid().toString());
		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());
		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.updateCCD, actualCDM);
		log("Exiting testCDWithAdd_Element test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithDeletedDetails() throws Exception {
		log("Test Case: testCDWithDeletedDetails");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message with deleted details(deleted Immunization section)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.deleteCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "NoUpdates");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(0, actualCDM.size());
		// EhcoreAPIUtil.compareCDMList("NoUpdates",actualCDM);
		log("Exiting testCDWithDeletedDetails test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDForNewPatient_CCCD() throws Exception {
		log("Test Case: testCDForNewPatient_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		/**
		 * To get the CDM List we need to check the first message in this subject_id.
		 */
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), msgDetails.get(0).getQid().toString());

		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(0).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());
		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.newC_CCD, actualCDM);
		log("Exiting testCDForNewPatient_CCCD test method ... ");
	}
	
	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithNoUpdates_CCCD() throws Exception {
		log("Test Case: testCDWithNoUpdates_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);

		log("Check tracking DB message_status as completed");
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message again without any updates
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(), EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), msgDetails.get(0).getQid().toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "NoUpdates");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(0, actualCDM.size());
		/**
		 * It shows complete CCDMessage with NoUpdates in CCD Items for
		 * ex:<AllergyList/> Get the Actual CDM Message and compare with expected using
		 * message_guid in MongoDB
		 */
		// EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.newC_CCD,actualCDM);
		log("Exiting testCDWithNoUpdates_CCCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithAdd_Entity_CCCD() throws Exception {
		log("Test Case: testCDWithAdd_Entity_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ENTITY_CCCD,
				EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// send the same message with added sections @entity level(Added 2 Social
		// history-Chews Tobacco and one element in Pregnant)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addentityC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), msgDetails.get(0).getQid().toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		/**
		 * To get the Added CDM List we need to check the second message in this
		 * subject_id.
		 */

		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());

		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.addentityC_CCD, actualCDM);
		log("Exiting testCDWithAdd_Entity_CCCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithUpdate_CCCD() throws Exception {
		log("Test Case: testCDWithUpdate_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> cCCDmsgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_UPDATE_CCCD,
				EhcoreAPIConstants.C_CCD);

		cCCDmsgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(cCCDmsgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message with updated details(updated Allery section)
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), cCCDmsgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.updateC_CCD);
		cCCDmsgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);// cCCDmsgDetails.get(0).getQid().toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(cCCDmsgDetails.get(1).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		/**
		 * To get the Added CDM List we need to check the second message in this
		 * subject_id.
		 */
		cCCDmsgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), cCCDmsgDetails.get(0).getQid().toString());
		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		actualCDM = EhcoreAPIUtil.getActualCDMList(cCCDmsgDetails.get(1).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());

		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.updateC_CCD, actualCDM);
		log("Exiting testCDWithUpdate_CCCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDWithDelete_CCCD() throws Exception {
		log("Test Case: testCDWithDelete_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		// Send the same message with deleted details
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.deleteC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), msgDetails.get(0).getQid().toString());
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));
		// Get the Actual CDM Message and compare with expected using message_guid in
		// MongoDB
		// msgDetails =
		// EhcoreAPIUtil.verifyExpectedMessageProcStatus(1,dj.getDataJobId(),TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(),msgDetails.get(0).getQid().toString());

		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(1).getMessageGuid(), nodePath, nodeName, "NoUpdates");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(0, actualCDM.size());

		EhcoreAPIUtil.compareCDMList("NoUpdates", actualCDM);
		log("Exiting testCDWithDelete_CCCD test method ... ");
	}
	
	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCDForNoKnown_CCCD() throws Exception {
		log("Test Case: testCDForNoKnown_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();
		// EhcoreAPI ehcoreApi = new EhcoreAPI();
		// EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();

		log("Open DataJob");
		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_NOKNOWN_CCCD,
				EhcoreAPIConstants.NOKNOWN_C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.ENRICH.toString(), EhcoreAPIConstants.CHANGEDETECTION));

		/**
		 * To get the CDM List we need to check the first message in this subject_id.
		 */
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), msgDetails.get(0).getQid().toString());
		actualCDM = EhcoreAPIUtil.getActualCDMList(msgDetails.get(0).getMessageGuid(), nodePath, nodeName, "");
		System.out.println("****************************ACTUAL CDM LIST*******************");
		System.out.println("ActualCDM Size::" + actualCDM.size());
		assertEquals(1, actualCDM.size());

		EhcoreAPIUtil.compareCDMList(EhcoreAPIConstants.NOKNOWN_C_CCD, actualCDM);
		log("Exiting testCDForNoKnown_CCCD test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersistWithNoUpdates() throws Exception {
		log("Test Case: testDeltaMsgPersistWithNoUpdates");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.CCD_DELTA + "NoUpdates_DeltaCCD.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * Send the same request again without any updates
		 */
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(), EhcoreAPIConstants.VALID_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_AddEntity() throws Exception {
		log("Test Case: testDeltaMsgPersist_AddEntity");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.CCD_DELTA + "AddEntity_DeltaCCD.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ENTITY,
				EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * send the same message with added sections @entity level(Added 2 problems)
		 */
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addentityCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_AddElement() throws Exception {
		log("Test Case: testDeltaMsgPersist_AddElement");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.CCD_DELTA + "AddElement_DeltaCCD.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ELEMENT,
				EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addelementCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString(),
				TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_UpdateDetails() throws Exception {
		log("Test Case: testDeltaMsgPersist_UpdateDetails");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.CCD_DELTA + "Update_DeltaCCD.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_UPDATE,
				EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * Send the same message with updated details(updated Medication)
		 */
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.updateCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_DeleteDetails() throws Exception {
		log("Test Case: testDeltaMsgPersist_DeleteDetails");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.CCD_DELTA + "NoUpdates_DeltaCCD.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.deleteCCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersistForNewPatient_CCCD() throws Exception {
		log("Test Case: testDeltaMsgPersistForNewPatient_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.C_CCD_DELTA + "DeltaCCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), msgDetails.get(0).getQid().toString());
		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersistWithNoUpdates_C_CCD() throws Exception {
		log("Test Case: testDeltaMsgPersistWithNoUpdates_C_CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.C_CCD_DELTA + "NoUpdates_Delta_CCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * Send the same request again without any updates
		 */
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(), EhcoreAPIConstants.C_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}
	
	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_AddEntity_CCCD() throws Exception {
		log("Test Case: testDeltaMsgPersist_AddEntity_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.C_CCD_DELTA + "AddEntity_Delta_CCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_ADD_ENTITY_CCCD,
				EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * send the same message with added sections @entity level(Added 2 Social
		 * history-Chews Tobacco and one element in Pregnant)
		 */
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.addentityC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_Update_CCCD() throws Exception {
		log("Test Case: testDeltaMsgPersist_Update_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.C_CCD_DELTA + "Update_Delta_CCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_UPDATE_CCCD,
				EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * Send the same message with updated details(updated Social History section)
		 */
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.updateC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeltaMsgPersist_Delete_CCCD() throws Exception {
		log("Test Case: testDeltaMsgPersist_Delete_CCCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Entering test method setup ... ");
		DataJob dj = new DataJob();

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), testData.getPatientID(), EhcoreAPIConstants.VALID_CCD);

		List<Message> msgDetails = new ArrayList<Message>();
		String expectedResponse = CCDImportConstants.C_CCD_DELTA + "NoUpdates_Delta_CCCD1.xml";

		dj = DataJobIDPage.openDataJob(TrackingEnumHolder.DATAJOB_STATUS.INIT.toString());
		EhcoreAPIUtil.sendMessage(dj.getDataJobId(), EhcoreAPIConstants.INTUITPATIENTID_CCCD, EhcoreAPIConstants.C_CCD);

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(0).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));
		/**
		 * Send the same message with updated details(updated Medication)
		 */
		EhcoreAPIUtil.sendUpdatedMessage(dj.getDataJobId(), msgDetails.get(0).getSubjectId(),
				EhcoreAPIConstants.deleteC_CCD);
		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(1, dj.getDataJobId(),
				TrackingEnumHolder.MESSAGE_STATUS.COMPLETED.toString

				(), CCDIMPORT_MSG_TYPE);
		assertTrue(EhcoreTrackingDBUtils.isActivityStatusCompleted(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.ACTIVITY_TYPE.PERSIST.toString(), EhcoreAPIConstants.DELTAMESSAGEPERSIST));

		// Pass nodeType and get the obj_ref_id in Tracking DB
		String obj_ref_id = EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(1).getQid().toString

		(), TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString());
		System.out.println("obj_ref_id::" + obj_ref_id);
		EhcoreAPIUtil.verifyMongoDBResponseUsingAPI(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString(),
				obj_ref_id, expectedResponse);

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testASCCDExportMessageJobStatusTypeERROR() throws Exception {
		log("Test Case: testASCCDExportMessageJobStatusTypeERROR");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step1:- getting value from the excel");
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		log("step2:- sending the request and getting the response");
		// EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				"invalidAllScriptsCCDExportMsg");

		log("step3:- sending the request and getting the response");
		EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString

				(), CCDExportConstants.AS_CCD_EXPORT);

		log("step4:- sending the request and getting the response");
		EhcoreAPIUtil.verifyExpectedDataJobProcStatus(response.getDataJobId(),
				TrackingEnumHolder.DATAJOB_STATUS.ERROR.toString());

		log("Exiting testCCDExportMessageJobStatusTypeDone test method ... ");
	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendValidMsgWithInvalidContentTypeForASExport() throws Exception {
		log("Test Case: testSendValidMsgWithInvalidContentTypeForASExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Open Datajob without content-type in Request header and Verify error response:415");
		EhcoreAPIUtil.requestWithInvalidHeader(CCDExportConstants.AS_CCD_EXPORT, "", "", "invalid", "UnsuportedType");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendValidMsgWithNullContentTypeASExport() throws Exception {
		log("Test Case: testSendValidMsgWithNullContentTypeASExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Open Datajob without content-type in Request header and  error-Response Code:400 Bad Request");
		EhcoreAPIUtil.requestWithInvalidHeader(CCDExportConstants.AS_CCD_EXPORT, "", "", "null", "BadRequest");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendValidMsgWithoutContentTypeASExport() throws Exception {
		log("Test Case: testSendValidMsgWithoutContentTypeASExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Open Datajob without content-type in Request header and  return an error.415 UnsupportedMedia type");
		EhcoreAPIUtil.requestWithInvalidHeader(CCDExportConstants.AS_CCD_EXPORT, "", "", "blank", "UnsuportedType");

	}

	@Test(enabled = true, groups = { "Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendMsgWithInvalidURLASExport() throws Exception {
		log("Test Case: testSendMsgWithInvalidURLASExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Open Datajob without content-type in Request header and  error-Response Code:404 Not Found");
		EhcoreAPIUtil.sendRequestWithInvalidURL("", CCDExportConstants.AS_CCD_EXPORT);

	}

	@Test(enabled = true, groups = { "Allscripts_Acceptance",
			"Allscripts_Functional" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRawMessagePersisitanceForInvalidMessageASExport() throws Exception {
		log("Test Case: testRawMessagePersisitanceForInvalidMessageASExport");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		List<Message> msgDetails = new ArrayList<Message>();
		// EhcoreAPIUtil util =new EhcoreAPIUtil(driver);
		// String expectedCcd = CCDExportConstants.AS_CCDEXPORT_INVALID_ERROR+
		// "AllScriptsAdapter_CCDExport_ErrorResponse.xml";

		ProcessingResponse response = EhcoreAPIUtil.sendCCDExportMessage(testData.getPatientID(),
				"invalidAllScriptsCCDExportMsg");
		;

		msgDetails = EhcoreAPIUtil.verifyExpectedMessageProcStatus(0, response.getMessageId(),
				TrackingEnumHolder.MESSAGE_STATUS.ERROR.toString(), CCDExportConstants.AS_CCD_EXPORT);

		// Pass nodeType and get the obj_ref_id in Tracking DB
		// String obj_ref_id =
		EhcoreTrackingDBUtils.getObjRefDetails(msgDetails.get(0).getQid().toString(),
				TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.RAW.toString());
		log("Exiting testRawMessagePersistForValidMessage test method ... ");
	}

}
