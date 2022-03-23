package com.medfusion.product.practice.implementedExternals;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.practice.api.flows.IPatientActivation;
import com.medfusion.product.practice.api.pojo.PatientInfo;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.tests.PatientActivationSearchTest;

public class PatientActivation implements IPatientActivation {

	@Override
	public PatientInfo activatePatient(WebDriver driver, PropertyFileLoader testData, String mail)
			throws InterruptedException, ClassNotFoundException, IllegalAccessException, IOException {
		System.out.println(this.getClass().getName());
		System.out.println("Setting up a patient through patient activation");

		// RCMUtil util = new RCMUtil(driver);
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		System.out.println("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		PatientInfo patInfo = new PatientInfo();
		patInfo.email = mail;
		System.out.println("Patient Activation on Practice Portal");
		// String patMail = "eStMf."+IHGUtil.createRandomNumericString(6)+"@mailinator.com";
		return patientActivationSearchTest.PatientActivation(driver, practiceTestData, mail, testData.getDoctorLogin(), testData.getDoctorPassword(),
				testData.getPortalUrl(), patInfo);

	}

	@Override
	public PatientInfo editPatientRSDKExternalID(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo)
			throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException {
	    return editPatientSetExternalID( driver,  testData,  patientInfo,"78");
	}
	/**
	 *  Sets the external id to any external system, the element name of the box filled is "patientid_%externalSystemId" 
	 *  e.g. patientid_78 for rsdk, patientid_79 for elekta, etc...
	 * @param driver
	 * @param testData
	 * @param patientInfo
	 * @param elementName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public PatientInfo editPatientSetExternalID(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo,String externalSystemId)
            throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException {
        System.out.println("Starting flow to set externalId for system: " + externalSystemId);        
        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("portal.url"));
        PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

        System.out.println("Click on Patient Search Link");
        PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

        System.out.println("Set Patient Search Fields");
        pPatientSearchPage.searchForPatientInPatientSearch(patientInfo.firstName, patientInfo.lastName);

        System.out.println("Open Patient Dashboard");
        PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.clickOnPatient(patientInfo.firstName, patientInfo.lastName);

        // save email
        System.out.println("@@@@@@@@@@@@@@@@@ " + patientInfo.email);

        System.out.println("Click Edit ID");
        List<WebElement> editButtons = driver.findElements(By.linkText("Edit"));
        editButtons.get(3).click();

        // read memberid and update MRN ( = external memberid)
        System.out.println("Update ID");
        String memberId = driver.findElement(By.xpath("//form[@name = 'edituserinfo']/table/tbody/tr[5]/td[2]")).getText();
        System.out.println("Found memberId: " + memberId);
        patientInfo.memberId = memberId;
        WebElement rsdkId = driver.findElement(By.name("patientid_" + externalSystemId));
        rsdkId.sendKeys(patientInfo.firstName);
        driver.findElement(By.name("submitted")).click();
        if (pPatientDashboardPage.getFeedback().contains("Patient Id(s) Updated") != true)
            throw new RuntimeException("ID Update failed!");

        patientInfo.billingAccountNumber = -1;
        patientInfo.practicePatientId = patientInfo.firstName;
        return patientInfo;
    }
	@Override
	public PatientInfo editPatientSetPrimaryId(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo, String emrid)
            throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException {
        System.out.println("Starting flow to set primary external id for patient, emrid: " + emrid);        
        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("portal.url"));
        PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getDoctorLogin(), testData.getDoctorPassword());

        System.out.println("Click on Patient Search Link");
        PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

        System.out.println("Set Patient Search Fields");
        pPatientSearchPage.searchForPatientInPatientSearch(patientInfo.firstName, patientInfo.lastName);

        System.out.println("Open Patient Dashboard");
        PatientDashboardPage pPatientDashboardPage = pPatientSearchPage.clickOnPatient(patientInfo.firstName, patientInfo.lastName);

        // save email
        System.out.println("@@@@@@@@@@@@@@@@@ " + patientInfo.email);

        System.out.println("Click Edit ID");
        List<WebElement> editButtons = driver.findElements(By.linkText("Edit"));
        editButtons.get(3).click();

        // read memberid and update MRN ( = external memberid)
        System.out.println("Update ID");
        String memberId = driver.findElement(By.xpath("//form[@name = 'edituserinfo']/table/tbody/tr[5]/td[2]")).getText();
        System.out.println("Found memberId: " + memberId);
        patientInfo.memberId = memberId;
        WebElement emridBox = driver.findElement(By.name("emrid"));
        emridBox.sendKeys(emrid);
        driver.findElement(By.name("submitted")).click();
        if (pPatientDashboardPage.getFeedback().contains("Patient Id(s) Updated") != true)
            throw new RuntimeException("ID Update failed!");        
        patientInfo.billingAccountNumber = -1;
        patientInfo.practicePatientId = emrid;
        return patientInfo;
    }

}
