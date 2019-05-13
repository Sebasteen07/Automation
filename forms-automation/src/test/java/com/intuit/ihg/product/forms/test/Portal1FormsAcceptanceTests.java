package com.intuit.ihg.product.forms.test;

import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Portal1FormsAcceptanceTests extends FormsAcceptanceTestsUtils {
		private PropertyFileLoader testData;

		@BeforeMethod(alwaysRun = true)
		public void setUpFormsTest() throws Exception {
				log(this.getClass().getName());
				log("Execution Environment: " + IHGUtil.getEnvironmentType());
				log("Execution Browser: " + TestConfig.getBrowserType());

				log("Getting Test Data");
				testData = new PropertyFileLoader();
		}


		@Test(groups = "OldPortalForms")
		public void testQuotationMarksInFormPortal1() throws Exception {
				testQuotationMarksInForm(Utils.loginPortal1AndOpenFormsList(driver, PracticeType.SECONDARY, testData));
		}

		@Test(groups = "OldPortalForms")
		public void testFormPdfCcdPortal1() throws Exception {
				PatientData p = new PatientData();
				MyPatientPage home = Utils.createAndLoginPatientPortal1(driver, PracticeType.SECONDARY, p);
				testFormPdfCcd(home.clickOnHealthForms(), testData.getProperty("getCCDUrl2"));
				driver.switchTo().defaultContent();
				home.logout(driver);
				log("Step 6: Test if the DOB has not been changed");
				MyAccountPage pMyAccountPage = Utils.loginPortal1(driver, PracticeType.SECONDARY, p.getEmail(), p.getPassword(), testData).clickMyAccountLink();
				//pMyAccountPage.getDOB() can get leading zeroes on months < 10
				assertEquals(pMyAccountPage.getDOB().replaceFirst("^0*", ""), p.getDob());
		}

		@Test(groups = "OldPortalForms")
		public void testFormPracticePortalPortal1() throws Exception {
				testFormPracticePortal(Utils.loginPortal1AndOpenFormsList(driver, PracticeType.SECONDARY, testData), testData.getProperty("practiceUrl"),
						testData.getProperty("practiceUsername2"), testData.getProperty("practicePassword2"));
		}

		@Test(groups = "OldPortalForms")
		public void testPartiallyCompletedFormPortal1() throws Exception {
				testPartiallyCompletedForm(Utils.loginPortal1AndOpenFormsList(driver, PracticeType.SECONDARY, testData), testData.getProperty("practiceUrl"),
						testData.getProperty("practiceUsername2"), testData.getProperty("practicePassword2"));
		}

		@Test(groups = "OldPortalForms")
		public void testFormPatientDashboardPortal1() throws Exception {
				PatientData p = new PatientData();
				MyPatientPage home = Utils.createAndLoginPatientPortal1(driver, PracticeType.SECONDARY, p);
				testFormPatientDashboard(home.clickOnHealthForms(), p.getEmail(), p.getFirstName(), p.getLastName(), testData.getProperty("practiceUrl"),
						testData.getProperty("practiceUsername2"), testData.getProperty("practicePassword2"));
		}

		@Test(groups = "OldPortalForms")
		public void testCustomFormWithFUPsPortal1() throws Exception {
				testCustomFormWithFUPs(Utils.loginPortal1AndOpenFormsList(driver, PracticeType.SECONDARY, testData));
		}

		@Test(groups = "OldPortalForms")
		public void testEGQEnabledPortal1() throws Exception {
				log("create patient and login to Portal 1");
				PatientData p = new PatientData();
				MyPatientPage home = Utils.createAndLoginPatientPortal1(driver, PracticeType.SECONDARY, p);
				testEGQEnabled(home.clickOnHealthForms(), testData.getProperty("getCCDUrl2"));
				log("verify that value in my account has been changed based on form answer");
				home = PageFactory.initElements(driver, MyPatientPage.class);
				MyAccountPage pMyAccountPage = home.clickMyAccountLink();
				assertTrue(pMyAccountPage.getGender() == Patient.GenderExtended.DECLINED);
		}
}
