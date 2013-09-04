package com.intuit.ihg.product.practice.page.onlinebillpay;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class eStatementUploadPage extends BasePageObject {

	public static final String noRecords ="No Records Found";
	public static final String successMessage ="eStatement successfully uploaded";
	
	@FindBy( xpath = ".//input[@name = 'searchParams:0:input']")
	private WebElement firstNameField;
	
	@FindBy( xpath = ".//input[@name = 'searchParams:1:input']")
	private WebElement lastNameField;
	
	@FindBy( xpath = ".//input[@name = 'searchParams:2:input']")
	private WebElement patientIDField;
	
	@FindBy( xpath = ".//input[@name = 'buttons:submit']")
	private WebElement searchPatientButton;

	@FindBy( xpath = ".//select[@name= 'locationID']")
	private WebElement locationIDDropDwn;
	
	@FindBy( xpath =".//input[@name='uniqueID']")
	private WebElement uniqueIDField;
		
	@FindBy( xpath =".//input[@name='dueAmount']")
	private WebElement dueAmountField;
	
	@FindBy( xpath = ".//select[@name='statementDate:month']")
	private WebElement statementDateDropDwn;
		
	@FindBy( xpath = ".//select[@name='statementDate:day']")
	private WebElement statementMonthDropDwn;
	
	@FindBy( xpath = ".//select[@name='statementDate:year']")
	private WebElement statementYearDropDwn;
	
	@FindBy( xpath ="//input[@value='Upload Statement']")
	private WebElement uploadStatementButton;
	
	@FindBy( xpath = ".//input[@name='fileName:fileField']")
	private WebElement browseButton;
	
//	@FindBy(xpath ="//span[text() ='eStatement successfully uploaded']")
	@FindBy(xpath = ".//*[@class='info']//span")
	private WebElement successMsg;
	
	@FindBy( xpath = ".//select[@name='paymentDueDate:month']")
	private WebElement paymentDueMonthDropDwn;
		
	@FindBy( xpath = ".//select[@name='paymentDueDate:day']")
	private WebElement paymentDueDateDropDwn;
	
	@FindBy( xpath = ".//select[@name='paymentDueDate:year']")
	private WebElement paymentDueYearDropDwn;
	
	public eStatementUploadPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void enterPatientDetails(String firstName, String lastName, String patientID) {
		IHGUtil.PrintMethodName();

		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		firstNameField.clear();
		firstNameField.sendKeys(firstName);
		lastNameField.clear();
		lastNameField.sendKeys(lastName);
		patientIDField.clear();
		patientIDField.sendKeys(patientID);
		
		searchPatientButton.click();
	}
	
	public boolean isNoRecordsFound() {
		WebElement ele = driver.findElement(By.xpath(".//form[@id='resultsForm']/table/tfoot/tr/td/span[@class='norecords']"));
		return ele.getText().contains(noRecords);
	}
	
	public void selectTheRecords(String fName, String lName) {
		IHGUtil.PrintMethodName();
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		
		int size = driver.findElements(By.xpath(".//form[@id='resultsForm']/table/tbody/tr")).size();
		 driver.findElement(By.xpath(".//form[@id='resultsForm']/table/tbody/tr")).click();
			
	}
	
	public void enterEStatementInfo(String location, String uniqueID, String dueAmt) throws Exception {

		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		
		log("Enter location ID");;
		Select selLoc = new Select(locationIDDropDwn);
		selLoc.selectByIndex(1);
		
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		log("Enter Unique ID and Due amout.");
		uniqueIDField.sendKeys(uniqueID);
		dueAmountField.sendKeys(dueAmt);
		
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		log("Enter the eStatement  date");
		Select selDate = new Select(statementDateDropDwn);
		selDate.selectByValue("1");
		
		Thread.sleep(3000);
		Select selMonth = new Select(statementMonthDropDwn);
		selMonth.selectByValue("1");
		
		Thread.sleep(3000);
		Select selYear = new Select(statementYearDropDwn);
		selYear.selectByValue("2013");
		
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		log("Enter the payment due date");
		Select selDueDate = new Select(paymentDueMonthDropDwn);
		selDueDate.selectByIndex(5);
		
		Thread.sleep(3000);
		Select selDueMonth = new Select(paymentDueDateDropDwn);
		selDueMonth.selectByIndex(1);
		
		Thread.sleep(3000);
		Select selDueYear = new Select(paymentDueYearDropDwn);
		selDueYear.selectByValue("2013");
		Thread.sleep(3000);
	}
	
	public void browseFile(String filePath) throws Exception {

		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 10, browseButton);
		Robot robot = new Robot();

		if (TestConfig.getBrowserType().equals(BrowserTypeUtil.BrowserType.iexplore)) {
			log("Attaching the file.");
			browseButton.sendKeys(filePath);
		} else  {
			Thread.sleep(3000);
			log("Clicking on Browse button");
			try {
				browseButton.click();
			}catch(Exception e) {
				browseButton.click();
				
			}
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(5000);
			PracticeUtil pUtil = new PracticeUtil(driver);
			log("Attaching the file.");
			String[] args={"UploadFile.exe","FF",filePath,"35000"};
			pUtil.setExeArg(args);
			pUtil.run();
			Thread.sleep(20000);
		}

	}
	
	public void clickOnUploadStatementButton() throws Exception {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		uploadStatementButton.click();
		Thread.sleep(5000);
	}
	
	public boolean isEStatementUploadedSuccessfully() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		log("Success Message :"+successMsg.getText());
		return successMsg.getText().contains(successMessage);
	}
}
