package com.intuit.ihg.product.support.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import net.medfusion.encounter.ws.WebServiceConstants;
import net.medfusion.integrations.webservices.common.exceptions.WebServiceExceptionInvalidParameter;
import net.medfusion.integrations.webservices.common.query.Criteria;
import net.medfusion.integrations.webservices.medfusion.MedfusionServiceLocator;
import net.medfusion.integrations.webservices.medfusion.MedfusionSoapBindingStub;
import net.medfusion.integrations.webservices.medfusion.objects.Comm;
import net.medfusion.integrations.webservices.medfusion.objects.CommAttachment;
import net.medfusion.integrations.webservices.medfusion.objects.CommDetail;
import net.medfusion.integrations.webservices.medfusion.objects.Field;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.CcdType;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.support.utils.Support;
import com.intuit.ihg.product.support.utils.SupportConstants;
import com.intuit.ihg.product.support.utils.SupportTestcasesData;
import com.intuit.ihg.product.support.utils.SupportUtil;
import com.intuit.ihg.product.support.utils.WebserviceUtils;

/**
 * 
 * @author bbinisha
 *
 */
public class SupportTeamAcceptanceTests extends BaseTestNGWebDriver {
	
	private MedfusionServiceLocator locator = null; 
	private MedfusionSoapBindingStub service = null;
	private BufferedWriter buff = null;
	

	//Date Provider for testSendCommunicationsWithAttachment test
	@DataProvider(name = "patientData") 
	public Object[][] patientDatafor3Patients() {
		Object[][] obj = new Object[][] { {8527925, 19948, 263944, SupportConstants.MESSAGESUBJECT1, SupportConstants.MESSAGE1 },			
				{8528000, 19948, 263944, SupportConstants.MESSAGESUBJECT2, SupportConstants.MESSAGE2 },
				{3104305, 19948, 263944, SupportConstants.MESSAGESUBJECT3, SupportConstants.MESSAGE3 }

		};
		return obj;
	}

	/**
	 * @author:- bbinisha
	 * @Date:- 11- Nov- 2013
	 * @User Story ID in Rally : US6870
	 * @StepsToReproduce:
	 * Description : Send communication to patients with corresponding attachments.
	 * Environment : PROD
	 * =============================================================
	 * @throws Exception
	 * =============================================================
	 */
	@Test(enabled = true, dataProvider = "patientData", groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendCommunicationsWithAttachment(int memberID, int practiceID, int staffID, String subject, String message) throws Exception {

		log("Test Case: testSendCommunicationsWithAttachment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		System.out.println("Setting up  Connection");
		Support supportObj = new Support();
		SupportTestcasesData testData = new SupportTestcasesData(supportObj);
		locator = new MedfusionServiceLocator();
		locator.setmedfusionEndpointAddress(testData.getUrl());

		service = (MedfusionSoapBindingStub) locator.getmedfusion();
		
		String userName = testData.getUsername();
		service._setProperty("javax.xml.rpc.security.auth.username", userName);
		service.setUsername(userName);
		service.setPassword(testData.getPassword());
		service.testConnection();

		if (!new File("dump.txt").exists())
			buff = new BufferedWriter(new FileWriter(new File("dump.txt")));
		else {
			new File("dump.txt").delete();
			buff = new BufferedWriter(new FileWriter(new File("dump.txt")));
		}
		
		log("Connected Successfully.");
		
		try {
			System.out
			.println("Get Communications that are unread from last week");
			Calendar cal = new GregorianCalendar();
			cal.add(GregorianCalendar.DAY_OF_WEEK, -7);
			Criteria[] commCrit = new Criteria[3];
			commCrit[0] = new Criteria(
					WebServiceConstants.FIELD_COMM_CREATIONDATE,
					WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
			commCrit[1] = new Criteria(
					WebServiceConstants.FIELD_COMMDETAIL_READFLAG,
					WebServiceConstants.OPERATION_EQUALS, new Boolean(false));
			commCrit[2] = new Criteria(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
					WebServiceConstants.OPERATION_EQUALS, new Integer(
							staffID));
			commCrit[2] = new Criteria(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
					WebServiceConstants.OPERATION_EQUALS, new Integer(
							staffID));

			Comm[] resultComms = service.getCommsByCriteria(practiceID,commCrit);
			WebserviceUtils.dumpResults(resultComms);

			System.out
			.println("Send a Generic Communication from Staff to Member");
			Comm communication = new Comm();

			Vector<Field> fieldVector = new Vector<Field>();

			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_MEMBERID,
					new Integer(memberID)));
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_PRACTICEID,
					new Integer(practiceID)));
			
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_SUBJECT,
					subject));
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_MESSAGE,
					subject));
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_SERVICETYPE,
					WebServiceConstants.FIELD_COMM_SERVICETYPE_GENERIC));   //for last two patient EM
			
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_CONTAINERID,
					-1));
			
			// build comm details
			CommDetail[] cds = new CommDetail[2];
			Vector<Field> detailFieldVector = new Vector<Field>();
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_MEMBERID, null));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
					new Integer(staffID)));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_TYPE,
					WebServiceConstants.FIELD_COMMDETAIL_TYPE_FROM));

			net.medfusion.integrations.webservices.medfusion.objects.Fields theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
			theDetailFields
			.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
					.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
					                                                                            .size()]));
			cds[0] = new CommDetail();
			cds[0].setFields(theDetailFields);

			detailFieldVector = new Vector<Field>();
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_MEMBERID,
					new Integer(memberID)));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID, null));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_TYPE,
					WebServiceConstants.FIELD_COMMDETAIL_TYPE_TO));

			theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
			theDetailFields
			.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
					.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
					                                                                            .size()]));
			cds[1] = new CommDetail();
			cds[1].setFields(theDetailFields);

			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_DETAILS, cds));

			// build comm attachment
			File testFile = new File(SupportConstants.ATTACHMENT_FILE1);
			if (testFile.exists()) {
				System.out.println("Building Comm Attachment...");
				CommAttachment[] ca = new CommAttachment[1];
				Vector<Field> attachmentFieldVector = new Vector<Field>();
				attachmentFieldVector
				.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
						WebServiceConstants.FIELD_COMMATTACHMENT_NAME,
						"MRI Attachment.pdf"));

				byte[] file = SupportUtil.getBytesFromFile(testFile);
				attachmentFieldVector
				.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
						WebServiceConstants.FIELD_COMMATTACHMENT_FILE,
						file));

				net.medfusion.integrations.webservices.medfusion.objects.Fields theAttachmentFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
				theAttachmentFields
				.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) attachmentFieldVector
						.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[attachmentFieldVector
						                                                                            .size()]));
				ca[0] = new CommAttachment();
				ca[0].setFields(theAttachmentFields);

				fieldVector
				.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
						WebServiceConstants.FIELD_COMM_ATTACHMENTS, ca));
			}

			// add fields to Communication
			net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
			theFields
			.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
					.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
					                                                                            .size()]));
			communication.setFields(theFields);

			System.out
			.println("Communication is : " + communication.toString());

			int commId = service.sendCommunication(communication);
			System.out.println("Got back CommId : " + commId);
			SupportUtil.lineBreak();

			
		} catch (WebServiceExceptionInvalidParameter e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
			
		buff.close();
	}	

	//Test Data for testSendCCD test.
	@DataProvider(name = "ccdData") 
	public Object[][] ccdDataForPatients() {
		Object[][] obj = new Object[][] { {IHGUtil.getConsolidatedCCD(), IHGUtil.getEnvironmentType().toString(), "8527925" }, 
				{IHGUtil.getConsolidatedCCD(), IHGUtil.getEnvironmentType().toString(), "8528000" } ,
				{IHGUtil.getConsolidatedCCD(), IHGUtil.getEnvironmentType().toString(), "3104305" }

		};
		return obj;
	}

	/**
	 * @author bbinisha
	 * Description : Post ccd for patients with patient id 8527925, 8528000 and 3104305.
	 * @Date : 11-Nov-2013
	 * @User Story ID in Rally : US6870
	 * Environment : PROD
	 * Note :- In order to send to more patients with same practice id and staff id, 
	 * should update the DataProvider with the details. 
	 * ===============================================================================
	 * @throws Exception
	 * ===============================================================================
	 */
	@Test(enabled = true, dataProvider = "ccdData", groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendCCD(CcdType ccdType, String env, String memberID) throws Exception {

		log("Test Case ***********: testSendCCD()");
		
		Support supportObj = new Support();
		SupportTestcasesData testData = new SupportTestcasesData(supportObj);
		log("All Script URL **********"+testData.getAllScriptAdaptorUrl());	
		
		String url = testData.getAllScriptAdaptorUrl();

		Assert.assertNotNull( 
				"### Test property WEBSERVICE_URL not defined", 
				url);

		log("Getting the ccd file path");
		String inputFilePath = SupportConstants.XML_REQUEST_PATH+"CCD_Updated.xml";
		String inputXml = SupportUtil.fileToString(inputFilePath);
		SupportUtil.wait(3000);
		
		log("Updating the Input Xml with patient "+memberID+"'s details");
		String updatedReqXmlInString = SupportUtil.updateXML(inputXml,SupportConstants.TAG_INTUITPATIENT_ID, SupportConstants.REQUESTOR_INTUITPATIENTID_OLDVALUE,memberID);
		
		SupportUtil.wait(2000);
		log("Updated XML***************\n"+updatedReqXmlInString);

		log("Post the ccd request");
		SupportUtil.postCCD1Request(url, updatedReqXmlInString);

	}

	//Data Provider for testLabResultForPatient test.
	@DataProvider(name = "labResults") 
	public Object[][] labResultsFor3Patients() {
		Object[][] obj = new Object[][] { {8527925, 19948, 263944, SupportConstants.SUBJECTFORPATIENT1, SupportConstants.MESSAGEFORPATIENT1}, 
				{8528000, 19948, 263944, SupportConstants.SUBJECTFORPATIENT2, SupportConstants.MESSAGEFORPATIENT2 },
				{3104305, 19948, 263944, SupportConstants.SUBJECTFORPATIENT3, SupportConstants.MESSAGEFORPATIENT3}
		};
		return obj;
	}
	
	/**
	 * @author bbinisha
	 * @Date :11-Nov-2013
	 * @User Story : US6870
	 * @Steps to reproduce
	 * Description : Sending Lab results for 3 Patients
	 * Environment : PROD
	 * =====================================================
	 * @throws Exception
	 * =====================================================
	 */
	@Test(enabled = false, dataProvider = "labResults", groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLabResultForPatient(int memberID, int practiceID, int staffID, String subject, String message) throws Exception {

		log("Test Case: testSendCommunicationsWithAttachment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		System.out.println("Setting up  Connection");
		Support supportObj = new Support();
		SupportTestcasesData testData = new SupportTestcasesData(supportObj);
		locator = new MedfusionServiceLocator();
		locator.setmedfusionEndpointAddress(testData.getUrl());

		service = (MedfusionSoapBindingStub) locator.getmedfusion();
		
		String userName = testData.getUsername();
		service._setProperty("javax.xml.rpc.security.auth.username", userName);
		service.setUsername(userName);
		service.setPassword(testData.getPassword());
		service.testConnection();

		if (!new File("dump.txt").exists())
			buff = new BufferedWriter(new FileWriter(new File("dump.txt")));
		else {
			new File("dump.txt").delete();
			buff = new BufferedWriter(new FileWriter(new File("dump.txt")));
		}
		
		log("Connected Successfully.");
				
		try {
			System.out
			.println("Get Communications that are unread from last week");
			Calendar cal = new GregorianCalendar();
			cal.add(GregorianCalendar.DAY_OF_WEEK, -7);
			Criteria[] commCrit = new Criteria[3];
			commCrit[0] = new Criteria(
					WebServiceConstants.FIELD_COMM_CREATIONDATE,
					WebServiceConstants.OPERATION_GREATERTHAN, cal.getTime());
			commCrit[1] = new Criteria(
					WebServiceConstants.FIELD_COMMDETAIL_READFLAG,
					WebServiceConstants.OPERATION_EQUALS, new Boolean(false));
			commCrit[2] = new Criteria(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
					WebServiceConstants.OPERATION_EQUALS, new Integer(
							staffID));
			commCrit[2] = new Criteria(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
					WebServiceConstants.OPERATION_EQUALS, new Integer(
							staffID));

			Comm[] resultComms = service.getCommsByCriteria(practiceID,commCrit);
			WebserviceUtils.dumpResults(resultComms);

			System.out
			.println("Send a Generic Communication from Staff to Member");
			Comm communication = new Comm();

			Vector<Field> fieldVector = new Vector<Field>();

			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_MEMBERID,
					new Integer(memberID)));
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_PRACTICEID,
					new Integer(practiceID)));
			
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_SUBJECT,
					subject));
			String inputFilePath = SupportConstants.MESSAGE_PATH+message;
			String inputXml = SupportUtil.fileToString(inputFilePath);
			
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_MESSAGE,
					inputXml));
			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_SERVICETYPE,
					WebServiceConstants.FIELD_COMM_SERVICETYPE_EM)); //changed from FIELD_COMM_SERVICETYPE_GENERIC

			// build comm details
			CommDetail[] cds = new CommDetail[2];
			Vector<Field> detailFieldVector = new Vector<Field>();
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_MEMBERID, null));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID,
					new Integer(staffID)));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_TYPE,
					WebServiceConstants.FIELD_COMMDETAIL_TYPE_FROM));

			net.medfusion.integrations.webservices.medfusion.objects.Fields theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
			theDetailFields
			.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
					.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
					                                                                            .size()]));
			cds[0] = new CommDetail();
			cds[0].setFields(theDetailFields);

			detailFieldVector = new Vector<Field>();
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_MEMBERID,
					new Integer(memberID)));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_STAFFID, null));
			detailFieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMMDETAIL_TYPE,
					WebServiceConstants.FIELD_COMMDETAIL_TYPE_TO));

			theDetailFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
			theDetailFields
			.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) detailFieldVector
					.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[detailFieldVector
					                                                                            .size()]));
			cds[1] = new CommDetail();
			cds[1].setFields(theDetailFields);

			fieldVector
			.add(new net.medfusion.integrations.webservices.medfusion.objects.Field(
					WebServiceConstants.FIELD_COMM_DETAILS, cds));


			// add fields to Communication
			net.medfusion.integrations.webservices.medfusion.objects.Fields theFields = new net.medfusion.integrations.webservices.medfusion.objects.Fields();
			theFields
			.setFields((net.medfusion.integrations.webservices.medfusion.objects.Field[]) fieldVector
					.toArray(new net.medfusion.integrations.webservices.medfusion.objects.Field[fieldVector
					                                                                            .size()]));
			communication.setFields(theFields);

			System.out
			.println("Communication is : " + communication.toString());

			int commId = service.sendCommunication(communication);
			System.out.println("Got back CommId : " + commId);
			SupportUtil.lineBreak();

			WebserviceUtils.dumpResults(resultComms);
		} catch (WebServiceExceptionInvalidParameter e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		buff.close();
	}	
	
	
	
}






