package com.intuit.ihg.product.object.maps.practice.page.patientSearch;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PatientDashboardPage extends BasePageObject{
	@FindBy(xpath="//a[contains(.,'Send Password Reset Email To Patient')]")
	private WebElement userPasswordEmail;
	
	@FindBy(xpath="//td/strong[text()='Email']/../following-sibling::node()/a")
	private WebElement editEmail;
	
	@FindBy(xpath="//a[contains(.,'Send email with the username to the patient')]")
	private WebElement userIdEmail;
	
	@FindBy(xpath=".//table[@class='demographics']/tbody/tr[5]/td[2]/a")
	private WebElement editPatientID;
	
	@FindBy(xpath=".//*[@id='content']/form/table/tbody/tr[5]/td[2]")
	private WebElement txtMedfusionID;
	
	@FindBy(name="emrid")
	private WebElement txtexternalID;
	
	@FindBy(name="submitted")
	private WebElement btnUpdateInfo;
	
	@FindBy(xpath=".//table[@class='demographics']/tbody/tr[5]/td[2]")
	private WebElement lblPatientID;
	
	@FindBy(xpath=".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[11]/td[2]")
	private WebElement lblunlockLink;
	
	@FindBy(xpath=".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[13]/td[2]")
	private WebElement lblactivationCode;
	
	@FindBy(xpath=".//table[@width='650px']/tbody/tr[7]/td[2]/input")
	private WebElement externalID;
	
	@FindBy(xpath=".//table[@width='650px']/tbody/tr[8]/td[2]/input")
	private WebElement externalID1;
	
	@FindBy(xpath=".//table[@class='demographics']/tbody/tr[2]/td[2]")
	private WebElement patientName;
	
	@FindBy(xpath=".//table[@class='demographics']/tbody/tr[8]/td[2]")
	private WebElement lblPatientSource;
	
	@FindBy(xpath=".//table[@class='demographics']/tbody/tr[9]/td[2]")
	private WebElement lblPatientStatus;
	
	private WebElement feedback;
	
	public static String medfusionID = null;
	public String patientID = null;
	public String patientSource = "Locked - Integration";
	public String patientStatus = "Invitation Sent";
	
	public PatientDashboardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	/**
	 * @throws InterruptedException 
	 * @Description:click on Patient with name
	 */
	public PatientSearchPage sendEmailUserID () throws InterruptedException
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, userIdEmail);
		userIdEmail.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
		
	}
	
	public String getFeedback() throws InterruptedException{
		feedback = driver.findElement(By.xpath("//li[@class='feedbackPanelINFO']"));
		IHGUtil.waitForElement(driver, 15, feedback);
		return feedback.getText();
	}
	
	public PatientSearchPage clickEditEmail (){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement( driver, 10, editEmail );
		editEmail.click( );
		return PageFactory.initElements( driver, PatientSearchPage.class );
	}
	/**
	 * Read MedfusionID and Set ExternalPatientID  
	 * @return ExternalPatientID
	 */
	public String setExternalPatientID (){
		IHGUtil.PrintMethodName();
		editPatientLink();
		medfusionID();
		String emrID=IHGUtil.createRandomNumericString();
		txtexternalID.clear();
		txtexternalID.sendKeys(emrID);
		btnUpdateInfo.click();
		IHGUtil.waitForElement(driver, 10, lblPatientID);
		Assert.assertTrue("patient ID is not set", lblPatientID.getText().contains(emrID));
		return emrID;
	}
	/**
	 * 
	 * @param patientID
	 * @return medfusionID
	 */
	public String medfusionID()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement( driver, 30, txtMedfusionID );
		patientID=txtMedfusionID.getText().toString();
		return patientID;
	}
	
	/**
	 * 
	 */
	public void editPatientLink()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement( driver, 60, editPatientID );
		editPatientID.click( );
	}
	/*
	 * return Activation Link
	 */
	public String unlockLink()
	{
		IHGUtil.waitForElement(driver, 60, lblunlockLink);
		return lblunlockLink.getText().toString();
		
	}
	/*
	 * return Activation Code
	 */
	public String activationCode()
	{
		IHGUtil.waitForElement(driver, 60, lblactivationCode);
		return lblactivationCode.getText().toString();
		
	}
	/**
	 * 
	 * @param configExternalID
	 * @return 
	 */
	public String externalID(String configExternalID)
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement( driver, 60, editPatientID );
		editPatientID.click( );
		IHGUtil.waitForElement( driver, 60, externalID );
		String External_ID=externalID.getAttribute("value").toString()+externalID1.getAttribute("value").toString();
		BaseTestSoftAssert.verifyEquals(External_ID, configExternalID, "Patient has different External patient ID than expected. External patient ID is: " + External_ID);
		Assert.assertTrue("External patient ID is not set", External_ID.equalsIgnoreCase(configExternalID));
		return externalID.getAttribute("value").toString();
	}
	
	/**
	 * 
	 */
	public void verifyDetails(String PatientID,String fName,String lName)
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement( driver, 60, patientName );
		BaseTestSoftAssert.verifyEquals(patientName.getText(),(fName+" "+lName+"   Edit"),"First Name & Last Name did not matched");
		BaseTestSoftAssert.verifyEquals(lblPatientID.getText(),(PatientID+"   Edit"),"PatientID did not matched");
		BaseTestSoftAssert.verifyEquals(lblPatientSource.getText(),patientSource,"Patient Source did not matched");
		BaseTestSoftAssert.verifyEquals(lblPatientStatus.getText(),patientStatus,"Patient Status did not matched");
		
	}

}
