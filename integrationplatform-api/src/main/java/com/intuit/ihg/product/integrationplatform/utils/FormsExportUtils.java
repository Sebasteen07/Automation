package com.intuit.ihg.product.integrationplatform.utils;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormAllergiesPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormBasicInfoPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormCurrentSymptomsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormEmergencyContactPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormFamilyHistoryPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormInsurancePage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormMedicationsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormOtherProvidersPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormPastMedicalHistoryPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormPreviousExamsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormSecondaryInsurancePage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormSocialHistoryPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormSurgeriesHospitalizationsPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormVaccinePage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class FormsExportUtils {
	public void ccdExchangeFormsImport(WebDriver driver, int testType) throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();	
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		Long timestamp = System.currentTimeMillis();
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.patientfilepath_FE;
		Scanner scanner = new Scanner(new FileReader(workingDir));
		Map<String, String> formsMap = new LinkedHashMap<String, String>();
		String line;
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (!line.startsWith(" ") && !line.isEmpty())
			{
				String[] columns = line.split("=");
				formsMap.put(columns[0], columns[1]);
			}
		}
		scanner.close();
		List<String> list = new ArrayList<String>(formsMap.values());
		testData.relFirstNAme=list.get(0);
		testData.relLastNAme=list.get(1);
		testData.relation1=list.get(2);
		testData.phonenumber1=list.get(3);
		testData.phonetype1=list.get(4);
		testData.NameofPrimaryInsurance=list.get(5);
		testData.NameofsecondaryInsurance=list.get(6);
		testData.tetanus1=list.get(7);
		testData.HPV1=list.get(8);
		testData.Influenza1=list.get(9);
		testData.Pneumonia1=list.get(10);
		testData.SurgeryName=list.get(11);
		testData.SurgeryTimeFrame=list.get(12);
		testData.HospitalizationReason=list.get(13);
		testData.HospitalizationTimeFrame=list.get(14);
		testData.Test=list.get(15);
		testData.TestTimeFrame=list.get(16);
		testData.NameofDoctorSpeciality=list.get(17);
		testData.NameDosage=list.get(18);
		testData.OtherMedicalhistory=list.get(19);
		testData.FamilyMember=list.get(20);
		testData.sex=list.get(21);
		testData.state1=list.get(22);
		testData.times=list.get(23);
		testData.exercise=list.get(24);
		testData.day=list.get(25);
		String getURL=null;
		String externalPatientID=null;
		String patientID=null;
		String pdfFileLocation="";
		for (int i = 1; i < 3; i++) {
			Log4jUtil.log("Step 1: Log in to the Patient Portal");        
			JalapenoLoginPage loginPage =new JalapenoLoginPage(driver,testData.url_FE);
			String randomString = IHGUtil.createRandomNumericString();
			PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
			String email = IHGUtil.createRandomEmailAddress(testData.patientEmailAddress1_FE);
			String Zipcode = IHGUtil.createRandomZip();

			Log4jUtil.log("Step 2: Fill in Patient Data");
			String firstName=testData.patientFirstName_FE+randomString;
			String username=testData.patientuserid_FE+randomString;
			String lastName=testData.patientLastName_FE+randomString;
			patientDemographicPage.fillInPatientData(firstName,lastName,email,testData.patientDOBMonthtext_FE,testData.patientDOBDay1_FE,testData.patientDOBYear_FE,Gender.FEMALE,Zipcode ,testData.patientAddress1_FE, testData.patientAddress2_FE, testData.patientCity_FE, testData.patientState_FE);
			SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
			JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(username,testData.patientPassword1_FE,testData.patientSecretQuestion_FE,testData.patientSecretAnswer_FE,testData.patientHomePhoneNo_FE,3);
			JalapenoMenu jalapenoMenuPage=new JalapenoHomePage (driver);

			Log4jUtil.log("Step 3: Logout");
			Thread.sleep(5000);
			jalapenoMenuPage.clickOnLogout();

			if(i==1)
			{
				Log4jUtil.log("Step 4: Login to Practice Portal");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.practiceURL_FE);
				PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.practiceUserName_FE, testData.practicePassword_FE);

				Log4jUtil.log("Step 5: Click on Patient Search Link");
				PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

				Log4jUtil.log("Step 6: Set Patient Search Fields");
				pPatientSearchPage.searchForPatientInPatientSearch(firstName, lastName);

				Log4jUtil.log("Step 8: Click on Patient");
				PatientDashboardPage patientPage = pPatientSearchPage.clickOnPatient(firstName, lastName);

				Thread.sleep(12000);
				Log4jUtil.log("Step 9: Set External Patient ID");

				externalPatientID=patientPage.setExternalPatientID_20();
				Log4jUtil.log("External Patient Id:-" + externalPatientID);

				patientID=patientPage.patientID;
				Log4jUtil.log("Medfusion Patient ID:-" + patientID);

				Log4jUtil.log("Step 10: Logout of Practice Portal");
				pPracticeHomePage.logOut();
			}
			
			Log4jUtil.log("Step 11: Login to Patient Portal for submitting 'Patient Registration' Discrete Form");
			new PortalLoginPage(driver, testData.url_FE);
			loginPage.login(username, testData.patientPassword1_FE);
			Thread.sleep(5000);
            jalapenoHomePage.clickOnMenuHealthForms();
			
            Log4jUtil.log("Step 12: Click on Registration button ");
			HealthFormListPage healthListpage= new HealthFormListPage(driver);
			
			Thread.sleep(6000);
			Log4jUtil.log("Step 13 : Navigate to HealthForms");
			healthListpage.clickOnHealthFormsRegistrationLink();
		
			String FormName = healthListpage.getFormName();
			Log4jUtil.log("Form name is "+FormName);
			
			Thread.sleep(1000);
			FormBasicInfoPage pFormBasicInfoPage=PageFactory.initElements(driver, FormBasicInfoPage.class);
			
			Log4jUtil.log("Step 14 : Fill in Basic Info of Patient");
			PortalUtil.setPortalFrame(driver);
			pFormBasicInfoPage.setBasicInfoFromFields_20(testData.state1,testData.phonetype1,testData.sex);

			Log4jUtil.log("Step 15: Fill in Emergency Contact info ");
			PortalUtil.setPortalFrame(driver);
			FormEmergencyContactPage paFormEmergencyContactPage = PageFactory.initElements(driver, FormEmergencyContactPage.class);
			paFormEmergencyContactPage.fillEmergencyContactFormFields_20(testData.relFirstNAme, testData.relLastNAme, testData.relation1, testData.phonenumber1, testData.phonetype1);
			WebElement Save =driver.findElement(By.xpath("//input[@type='submit' and @value='Save & Continue']"));

			Log4jUtil.log("Step 16: Fill in Primary Insurance Details");
			FormInsurancePage pFormInsurancePage=PageFactory.initElements(driver, FormInsurancePage.class);
			Thread.sleep(1000);
			pFormInsurancePage.fillfirstInsurance(testData.NameofPrimaryInsurance, firstName);

			Log4jUtil.log("Step 17: Fill in Secondary Insurance Details");
			FormSecondaryInsurancePage pFormSecondaryInsurancePage= PageFactory.initElements(driver, FormSecondaryInsurancePage.class);
			pFormSecondaryInsurancePage.fillSecondInsurance(testData.NameofsecondaryInsurance,firstName);

			Log4jUtil.log("Step 18: Set Providers Details");
			FormOtherProvidersPage pFormOtherProvidersPage=PageFactory.initElements(driver, FormOtherProvidersPage.class);
			pFormOtherProvidersPage.SetProvidername(testData.NameofDoctorSpeciality);
			Save.click();

			Log4jUtil.log("Step 19:Fill in CurrentSymptomspage Info ");
			FormCurrentSymptomsPage pFormCurrentSymptomsPage=PageFactory.initElements(driver, FormCurrentSymptomsPage.class);
			pFormCurrentSymptomsPage.setBasicSymptoms();
			Save.click();

			Log4jUtil.log("Step 20: Fill in Medications Info");
			FormMedicationsPage pFormMedicationsPage=PageFactory.initElements(driver, FormMedicationsPage.class);
			pFormMedicationsPage.setMedicationFormFields_20(testData.NameDosage,testData.times);

			Log4jUtil.log("Step 21: Fill in No Allergies");
			PortalUtil.setPortalFrame(driver);
			FormAllergiesPage pFormAllergiesPage=PageFactory.initElements(driver, FormAllergiesPage.class);
			pFormAllergiesPage.SetAllergies();

			Log4jUtil.log("Step 22: Fill in Vaccines");
			FormVaccinePage pFormVaccinePage =PageFactory.initElements(driver, FormVaccinePage.class);
			pFormVaccinePage.SetVaccines(testData.tetanus1,testData.HPV1,testData.Influenza1,testData.Pneumonia1);

			Log4jUtil.log("Step 23: Fill in surgeries & Hospitalizations");
			FormSurgeriesHospitalizationsPage pFormSurgeriesHospitalizationsPage=PageFactory.initElements(driver, FormSurgeriesHospitalizationsPage.class);
			pFormSurgeriesHospitalizationsPage.fillSurgiesform(testData.SurgeryName,testData.SurgeryTimeFrame,testData.HospitalizationReason,testData.HospitalizationTimeFrame);

			Log4jUtil.log("Step 24:Fill in Test Details");
			FormPreviousExamsPage pFormPreviousExamsPage=PageFactory.initElements(driver, FormPreviousExamsPage.class);
			pFormPreviousExamsPage.fillTestDetails(testData.Test,testData.TestTimeFrame);

			Log4jUtil.log("Step 25: Fill in Past Medical History");
			FormPastMedicalHistoryPage pFormPastMedicalHistoryPage=PageFactory.initElements(driver, FormPastMedicalHistoryPage.class);
			pFormPastMedicalHistoryPage.checkMononucleosis_20();

			Log4jUtil.log("Step 26: Fill in Family History Details");
			FormFamilyHistoryPage pFormFamilyHistoryPage=PageFactory.initElements(driver, FormFamilyHistoryPage.class);
			pFormFamilyHistoryPage.SetFamilyHistory(testData.OtherMedicalhistory, testData.FamilyMember);

			Log4jUtil.log("Step 27: Fill in Social History Details");
			FormSocialHistoryPage pFormSocialHistoryPage=PageFactory.initElements(driver, FormSocialHistoryPage.class);
			pFormSocialHistoryPage.fillexercisedetails(testData.exercise,testData.day);
			Thread.sleep(5000);

			if(i==1)
			{
				PortalUtil.setPortalFrame(driver);
				Log4jUtil.log("Step for patient with External ID: Downloading PDf file from Application");
				driver.findElement(By.xpath("//a[contains(text(),'View as PDF')]")).click();
				if( driver instanceof FirefoxDriver) {
					Robot rb = new Robot(); 
					Thread.sleep(7000);
					rb.keyPress(KeyEvent.VK_ENTER);
					rb.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(2000);
				}
				Thread.sleep(6000);
				String UIPDF=System.getProperty("user.dir");
				String downloadFile = UIPDF+testData.downloadFileLocation;
				File f = new File(downloadFile);
				ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
			   
			   Log4jUtil.log("downloadFileLocation : "+downloadFile);
			   Log4jUtil.log("FileName is: "+names.get(0));
			   pdfFileLocation =downloadFile+names.get(0);
			   Log4jUtil.log("File location is "+pdfFileLocation);
			}
		
			Log4jUtil.log("Step 28: Wait 120 seconds, so the message can be processed");
			Thread.sleep(60000);
			
			Log4jUtil.log("Step 29: Setup Oauth client");
			RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE, testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);

			Long since = timestamp / 1000L - 60 * 24;
			System.out.println("Since time is "+since);

			if (i == 1) {
				getURL = testData.ccd_url1_FE + "/" + externalPatientID;

			} else {
				Log4jUtil.log("Verify both the patient details in ccdExchangeBatch response ");
				getURL = testData.ccd_url1_FE + "Batch";
			}
			
			Log4jUtil.log("Step 30:  Getting forms (CCDs) since timestamp: " + since + " using ccdExchange API");
			Log4jUtil.log("CCD _URL is "+ testData.ccd_url1_FE);
			RestUtils.setupHttpGetRequest(getURL + "?since=" + since + ",0", testData.responsePath_CCD1_FE);
			Thread.sleep(2000);
			RestUtils.verifyCCDHeaderDetails(testData.responsePath_CCD1_FE,FormName);
			
			List<String> patientList = RestUtils.patientDatails;
			patientList.add(externalPatientID);
			patientList.add(patientID);
			patientList.add(firstName);

			for (int j = 0; j < i; j++) {
				if(i==1)
				{
					Log4jUtil.log("Step 31: Validate PatientDemographics and CCD details in the response");
					RestUtils.isPatientAppeared(testData.responsePath_CCD1_FE, patientList.get(0),patientList.get(1), patientList.get(2));
				}
				if (i == 2) {
					patientList.remove(0);
					patientList.remove(0);
					patientList.remove(0);
				}

			}
			if(i==1)
			{
				Log4jUtil.log("Step 32 :Verify Patients CCD Data in ResponseXML");
				RestUtils.verifyPatientCCDFormInfo(testData.responsePath_CCD1_FE, list);
			}

			Thread.sleep(5000);
			String GetPatientPDfURL= "";
			if(i==2) {
			    GetPatientPDfURL=RestUtils.verifyCCDHeaderDetailsandGetURL(testData.responsePath_CCD1_FE,externalPatientID);
				Log4jUtil.log("Verify both the patient details in ccdExchangeBatch response ");
				Log4jUtil.log("Step 31: Setup Oauth client");
				RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE, testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);
				
				Log4jUtil.log("Step 32: Save PDF file returned as response from RESTAPI " + since + " using ccdExchange API");
				
				RestUtils.setupHttpGetRequestExceptoAuthforPDF(GetPatientPDfURL + "?since=" + since + ",0", testData.responsePDF_FE);
				Thread.sleep(4000);
				
				long timeStamp204 = System.currentTimeMillis();
			    Long since1= timeStamp204 / 1000 - 60 * 24;
				
				getURL = testData.ccd_PDfUrl_FE + "Batch";
				RestUtils.setupHttpGetRequest(getURL+ "?since=" + since1 + ",0", testData.responsePath_CCD1_FE);
				
				RestUtils.verifyPDFBatchDetails(testData.responsePath_CCD1_FE,externalPatientID,patientID);
				Thread.sleep(2000);
				RestUtils.comparePDFfiles(pdfFileLocation, testData.responsePDF_FE);
			}
				
			if(i==2 && testType==1) {
				getURL = testData.ccd_url1_FE + "Batch";
				Thread.sleep(10000);
				Log4jUtil.log("Step 33: Verify 204 response when no new forms is present");
				long timeStamp204 = System.currentTimeMillis();
				Long sinceTime = timeStamp204 / 1000;
				RestUtils.setupHttpGetRequestExceptOauth(getURL + "?since=" + sinceTime + ",0", testData.responsePath_CCD1_FE);
				Thread.sleep(800);
				Log4jUtil.log("Step 34: Invoke ccdExchangeBatch api when there are no forms");
				String ccdExchangeBatchURL = RestUtils.headerUrl;
				RestUtils.setupHttpGetRequestExceptOauth(ccdExchangeBatchURL, testData.responsePath_CCD1_FE);
			}

			Thread.sleep(6000);
			Log4jUtil.log("Logout from patient portal");
			driver.switchTo().defaultContent();
			jalapenoMenuPage.clickOnMenuHome();
			jalapenoMenuPage.clickOnLogout();
		}
	}
}
