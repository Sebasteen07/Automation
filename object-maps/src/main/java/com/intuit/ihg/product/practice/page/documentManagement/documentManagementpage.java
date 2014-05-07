package com.intuit.ihg.product.practice.page.documentManagement;

import java.awt.AWTException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

/**
 * 
 * @author bbinisha
 *
 */

public class documentManagementpage  extends BasePageObject {

	@FindBy (linkText = "Upload document")
	private WebElement uploadDocLink;

	@FindBy (xpath = ".//table[@class='searchForm']//input[@name='memberNameTextField']")
	private WebElement patientNameField;

	@FindBy( xpath = ".//table[@class='searchForm']//input[@name='file:fileField']")
	private WebElement browseButton;

	@FindBy( xpath = ".//input[@value='Upload Document']")
	private WebElement uploadDocumentButton;

	@FindBy( xpath =".//div[@class='feedback']//span[@class ='feedbackPanelINFO']")
	private WebElement uploadSuccessMsg;

	@FindBy (xpath = ".//input[@name = 'addPatient']")
	private WebElement addNewPatientButton;

	@FindBy( xpath = ".//input[@id='new_firstname']")
	private WebElement firstNameField;

	@FindBy( xpath = ".//input[@id='new_lastname']")
	private WebElement lastNameField;

	@FindBy( xpath = ".//input[@id='new_patientnumber']")
	private WebElement patientID;

	@FindBy( xpath = ".//*[@id='addnewpatient']//input[@value='M']")
	private WebElement genderOptionM;

	@FindBy( xpath = ".//input[@id='new_email']")
	private WebElement emailField;

	@FindBy( xpath = ".//input[@id='new_email_confirm']")
	private WebElement confirmEmail;

	@FindBy( xpath = ".//*[@id='addnewpatient']//select[@name='new_dobmonth']")
	private WebElement selectMonthDropDwn;

	@FindBy( xpath = ".//*[@id='addnewpatient']//select[@name='new_dobday']")
	private WebElement selectDayDropDwn;

	@FindBy (xpath =".//*[@id='addnewpatient']//input[@name ='new_dobyear']" )
	private WebElement yearField;

	@FindBy( xpath = ".//input[@id='new_zip']")
	private WebElement zipField;

	@FindBy( xpath = ".//*[@id='addnewpatient']//input[@value='Add a patient']")
	private WebElement addAPatientButton;


	/**
	 * @author bbinisha
	 * @param driver
	 */
	public documentManagementpage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bbinisha
	 * @Desc : To click on Upload Document Link.
	 */
	public void clickOnUploadDocLink() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, uploadDocLink);
		uploadDocLink.click();
	}

	/**
	 * @author bbinisha
	 * @throws AWTException 
	 * @throws InterruptedException 
	 * @Desc : To enter the patient name in the patient name field.
	 */
	public void enterPatientName (String patientName) throws AWTException, InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, patientNameField);
		patientNameField.click();
		patientNameField.sendKeys(patientName);
		PracticeUtil pUtil = new PracticeUtil(driver);
		Thread.sleep(3000);
		pUtil.pressDownKey();
		pUtil.pressEnterKey();
	}


	/**
	 * @author bbinisha
	 * @throws Exception 
	 * @Desc : To browse the file and upload. Dependency on BrowserType.
	 */
	public void browseFile() throws Exception {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, browseButton);

		URL textFilePath = ClassLoader.getSystemResource(PracticeConstants.textFilePath);
		browseButton.sendKeys(textFilePath.getPath());

	}

	/**
	 * @author bbinisha
	 * @Desc : To click on Upload Document button.
	 */
	public void uploadDocument() {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, uploadDocumentButton);
		uploadDocumentButton.click();
	}

	/**
	 * @author bbinisha
	 * @Desc : To verify document upload success message.
	 * @return
	 */
	public boolean checkUploadSuccessMessage() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		return uploadSuccessMsg.getText().equals(PracticeConstants.docUploadSuccessMsg);
	}

	/**
	 * @author bbinisha
	 * @Desc : To add a new patient in each run.
	 * @return
	 */
	public void addNewPatient(String fName, String lName, String patientId, String email, String value, String year, String zipCode) {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, addNewPatientButton);	
		addNewPatientButton.click();

		java.util.Calendar cal = java.util.Calendar.getInstance();
		long firstName;		
		firstName = cal.getTimeInMillis() % 1000000;	
		fName = fName + (new Long(firstName)).toString();

		firstNameField.sendKeys(fName);
		lastNameField.sendKeys(lName);
		patientID.sendKeys(patientId);
		genderOptionM.click();
		emailField.sendKeys(email);
		confirmEmail.sendKeys(email);
		Select sel1 = new Select(selectMonthDropDwn);
		sel1.selectByValue(value);
		Select sel2 = new Select(selectDayDropDwn);
		sel2.selectByValue(value);
		yearField.sendKeys(year);
		zipField.sendKeys(zipCode);
		addAPatientButton.click();
	}

}
