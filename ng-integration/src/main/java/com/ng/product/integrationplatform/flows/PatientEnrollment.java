package com.ng.product.integrationplatform.flows;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.YopMail;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

public class PatientEnrollment {

	static int arg_timeOut = 1800;
	private static final String INVITE_EMAIL_SUBJECT_PATIENT = "You're invited to create a Patient Portal account at";

	public static void verifyProcessingStatusto4(String person_id) throws Throwable {
		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		if (processingStatus.equals("3")) {
			Log4jUtil.log("Processing status is " + processingStatus + " i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
				if (processingStatus.equals("4")) {
					Log4jUtil.log("Step End: Processing status is " + processingStatus
							+ " i.e. Processing status is completed");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
	}

	public static void VerifyGetPIDCCall(PropertyFileLoader propertyLoaderObj, Long timestamp, String person_nbr,
			String FName, String LName, String portalStatus, String integrationID)
			throws IOException, ParserConfigurationException, SAXException, InterruptedException {

		Thread.sleep(20000);
		Log4jUtil.log("Step Begins: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getPIDCURL().replaceAll("integrationID", integrationID) + "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());
		Thread.sleep(2000);
		if (portalStatus.equalsIgnoreCase("Registered")) {
			RestUtils.isPatientRegistered(propertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""),
					FName, LName, null);
		} else if (portalStatus.equalsIgnoreCase("DEACTIVATED")) {
			RestUtils.isPatientDeactivatedorDeleted(propertyLoaderObj.getResponsePath(),
					person_nbr.trim().replace("\t", ""), FName, LName, null, portalStatus);
		} else if (portalStatus.equalsIgnoreCase("DELETED")) {
			RestUtils.isPatientDeactivatedorDeleted(propertyLoaderObj.getResponsePath(),
					person_nbr.trim().replace("\t", ""), FName, LName, null, portalStatus);
		} else {
			Log4jUtil.log("Invalid Portal Status");
		}

		Log4jUtil.log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");
	}

	public static void verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyFileLoader propertyLoaderObj,
			String person_id, String integrationID) throws Throwable {

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + person_id.trim() + "'");
		String emailStatusQuery = "select top 1 status from processingstatus_entity where entityidentifier ='"
				+ person_nbr.trim().replace("\t", "") + "' order by createdts desc";
		String processingStatusIDQuery = "select top 1 processingstatus_id from processingstatus_entity where entityidentifier ='"
				+ person_nbr.trim().replace("\t", "") + "' order by createdts desc";

		Log4jUtil.log("Step Begins: Verify the status of MF agent job");
		DBUtils.executeQueryOnDB("MFAgentDB", emailStatusQuery);
		String jobStatus1 = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (" + processingStatusIDQuery + ")");

		if (jobStatus1.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				jobStatus1 = DBUtils.executeQueryOnDB("MFAgentDB",
						"select status from processingstatus where id = (" + processingStatusIDQuery + ")");
				if ((jobStatus1.equalsIgnoreCase("Pending")) || (jobStatus1.equalsIgnoreCase("COMPLETED"))) {
					Log4jUtil.log("Step End: Request is sent to RSDK successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		Log4jUtil.log("Status of MF agent job " + jobStatus1);
		if (!jobStatus1.isEmpty()) {
			if (jobStatus1.equalsIgnoreCase("Pending"))
				Log4jUtil.log("Step End: Request is sent to RSDK and inprocess with job status " + jobStatus1);
			else if (jobStatus1.equalsIgnoreCase("COMPLETED"))
				Log4jUtil.log("Registration URL is received");
		} else {
			Log4jUtil.log("Please check Bad request or MF agent is not working");
		}

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select jobid from processingstatus where id = (" + processingStatusIDQuery + ")");

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select messageid from processingstatus where id = (" + processingStatusIDQuery + ")");

		Log4jUtil.log("Step Begins: Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getOAuthUsername(),
				propertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		Log4jUtil.log(
				"Step Begins: Do get processing status call and verify the processing time of registration mail to be received");

		Thread.sleep(60000);
		Log4jUtil
				.log("Step Begins: Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus = DBUtils.executeQueryOnDB("MFAgentDB", emailStatusQuery);

		if (emailStatus.equalsIgnoreCase("Pending")) {
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus = DBUtils.executeQueryOnDB("MFAgentDB", emailStatusQuery);
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
					Log4jUtil.log("Step End: Mail sent to patient successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(emailStatus, "equals", "COMPLETED");

		String jobStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (" + processingStatusIDQuery + ")");
		CommonUtils.VerifyTwoValues(jobStatus, "equals", "COMPLETED");
		Log4jUtil.log("Step End: The processing status of MF agent job is " + jobStatus
				+ " registration mail is sent to patient for enrollment");
	}

	public static void verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(
			PropertyFileLoader propertyLoaderObj, String person_id, String practice_id, String integrationID)
			throws Throwable {
		String processingStatusQuery = "select processing_status from pxp_enrollments where person_id = '"
				+ person_id.trim() + "' and practice_id='" + practice_id.trim() + "'";
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB", processingStatusQuery);
		if (processing_status.equals("1") || processing_status.equals("2")) {
			Log4jUtil.log("Processing status is " + processing_status + " i.e. Enrollment record is sent to RSDK.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", processingStatusQuery);
				if (processing_status.equals("2") || processing_status.equals("3")) {
					verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, person_id,
							integrationID);
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", processingStatusQuery);
				if (processing_status.equals("3")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. RSDK has sent mail to the guardian");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		} else if (processing_status.equals("3"))
			Log4jUtil.log(
					"Step End: Processing status is " + processing_status + " i.e. RSDK has sent mail to the guardian");
	}

	public static String enrollPatientWithoutGetProcessingStatusValidation(PropertyFileLoader propertyLoaderObj,
			String enterprisebaseURL, NGAPIUtils ngAPIUtils, WebDriver driver, NewPatient createPatient,
			String person_id, String practiceName, String integrationID, String enterprise_id, String practice_id)
			throws Throwable {
		Log4jUtil.log("Step Begins: Enrollling the patient in practice portal for " + practiceName);
		Log4jUtil.log("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);
		Log4jUtil.log("Step End: MF agent initiate the enrollment automatically");

		Log4jUtil.log(
				"Step Begins: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		Log4jUtil.log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"));

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				person_id, practice_id, integrationID);

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + practiceName
				+ "     :   " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		Log4jUtil.log("Step Begins: Logging into YopMail and getting Patient Activation url for first Practice");
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_PATIENT + practiceName, JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT,
				80);
		assertNotNull(activationUrl, "Error: Activation link not found.");

		Log4jUtil.log(
				"Step Begins: Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		Log4jUtil.log("Step Begins: Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		Log4jUtil.log("Step Begins: Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();

		verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, person_id, practice_id, enterprise_id);
		Log4jUtil.log("Patient is enrolled successfully to " + practiceName);
		return person_id;
	}

	public static void verifyPatientEnrollmentStatus(String enterprisebaseURL, NGAPIUtils ngAPIUtils, String personId,
			String practice_id, String enterprise_id) throws Throwable {
		Log4jUtil.log("Step Begins: Find the patient and check if he is registered");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		if (processing_status.equals("3")) {
			Log4jUtil.log("Processing status is " + processing_status + " i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
								+ "' and practice_id='" + practice_id + "'");
				if (processing_status.equals("4")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. Processing status is completed");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		Log4jUtil.log("Step Begins: Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		CommonUtils.VerifyTwoValues(enrollment_status2, "equals", "9");
		processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		CommonUtils.VerifyTwoValues(processing_status, "equals", "4");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterprise_id, practice_id);
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String getEnrollmentStatusresponse2 = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"),
				"equals", "Completed");
		Log4jUtil.log("Result: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"));
		Log4jUtil.log("Step End: Patient enrollment status is " + enrollment_status2 + " which is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"));
	}

	public static void verifyPatientEnrollmentDeletedStatus(String enterprisebaseURL, String personId,
			String practice_id, String enterprise_id) throws Throwable {
		Log4jUtil.log(
				"Step Begins: Verify the enrollment and processing status of patient in pxp enrollment table for practice_id "
						+ practice_id);
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		if (processing_status.equals("3") || processing_status.equals("4")) {
			Log4jUtil.log("Processing status is " + processing_status + " RSDK is processing the request");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
								+ "' and practice_id='" + practice_id + "'");
				if (processing_status.equals("5")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. Enrollment record is deleted successfully.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processing_status, "equals", "5");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "11");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterprise_id, practice_id);
		Log4jUtil.log("Step Begins: Verify the enrollment status of patient for practice_id " + practice_id);
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "status"), "equals",
				"11");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"),
				"equals", "Account Deleted");
		Log4jUtil.log("Step End: Patient enrollment status is " + enrollment_status + " which is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"));
	}

	public static void verifyPatientEnrollmentDeactivatedStatus(String enterprisebaseURL, String personId,
			String practice_id, String enterprise_id) throws Throwable {
		Log4jUtil.log(
				"Step Begins: Verify the enrollment and processing status of patient in pxp enrollment table for practice_id "
						+ practice_id);
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		if (processing_status.equals("3") || processing_status.equals("4")) {
			Log4jUtil.log("Processing status is " + processing_status + " RSDK is processing the request");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim()
								+ "' and practice_id='" + practice_id + "'");
				if (processing_status.equals("6")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. Enrollment record is deactivated successfully.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processing_status, "equals", "6");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim()
						+ "' and practice_id='" + practice_id + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "10");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterprise_id, practice_id);
		Log4jUtil.log("Step Begins: Verify the enrollment status of patient for practice_id " + practice_id);
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "status"), "equals",
				"10");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"),
				"equals", "Deactivated");
		Log4jUtil.log("Step End: Patient enrollment status is " + enrollment_status + " which is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"));
	}

}
