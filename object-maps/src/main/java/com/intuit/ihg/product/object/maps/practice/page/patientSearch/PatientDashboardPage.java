package com.intuit.ihg.product.object.maps.practice.page.patientSearch;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PatientDashboardPage extends BasePageObject{
	@FindBy(xpath="//a[contains(.,'Send Password Reset Email To Patient')]")
	private WebElement userPasswordEmail;
	
	@FindBy(xpath="//td/strong[text()='Email']/../following-sibling::node()/a")
	private WebElement editEmail;
	
	@FindBy(xpath="//a[contains(.,'Send email with the username to the patient')]")
	private WebElement userIdEmail;
	
	@FindBy(xpath=".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[5]/td[2]/a")
	private WebElement editPatientID;
	
	@FindBy(xpath=".//*[@id='content']/form/table/tbody/tr[5]/td[2]")
	private WebElement txtMedfusionID;
	
	@FindBy(name="emrid")
	private WebElement txtexternalID;
	
	@FindBy(name="submitted")
	private WebElement btnUpdateInfo;
	
	@FindBy(xpath=".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[5]/td[2]")
	private WebElement lblPatientID;
	
	@FindBy(xpath=".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[11]/td[2]")
	private WebElement lblunlockLink;
	
	@FindBy(xpath=".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[13]/td[2]")
	private WebElement lblactivationCode;
		
	private WebElement feedback;
	
	public static String medfusionID = null;
	public String patientID = null;
	
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
		IHGUtil.waitForElement( driver, 10, editPatientID );
		editPatientID.click( );
		IHGUtil.waitForElement( driver, 30, txtMedfusionID );
		patientID=txtMedfusionID.getText().toString();
		medfusionID(patientID);
		String emrID=patientID.concat("2014");
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
	public String medfusionID(String patientID)
	{
		return patientID;
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

}
