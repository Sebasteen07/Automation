package com.intuit.ihg.product.object.maps.practice.page.onlinebillpay;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeConstants;

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
	
	@FindBy( xpath = "//select[@name='statementDate:month']/option[@selected='selected']")
	private WebElement statementMonthDropDwnSelected;
		
	@FindBy( xpath = "//select[@name='statementDate:day']/option[@selected='selected']")
	private WebElement statementDateDropDwnSelected;
	
	@FindBy( xpath = "//select[@name='statementDate:year']/option[@selected='selected']")
	private WebElement statementYearDropDwnSelected;
	
	@FindBy( xpath ="//input[@value='Upload Statement']")
	private WebElement uploadStatementButton;
	
	
	
//	@FindBy(xpath ="//span[text() ='eStatement successfully uploaded']")
	@FindBy(xpath = ".//*[@class='info']//span")
	private WebElement successMsg;
	
	@FindBy( xpath = "//select[@name='paymentDueDate:month']")
	private WebElement paymentDueMonthDropDwn;
		
	@FindBy( xpath = "//select[@name='paymentDueDate:day']")
	private WebElement paymentDueDateDropDwn;
	
	@FindBy( xpath = "//select[@name='paymentDueDate:year']")
	private WebElement paymentDueYearDropDwn;
	
	public eStatementUploadPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void enterPatientDetails(String firstName, String lastName, String patientID) {
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
		
		driver.findElements(By.xpath(".//form[@id='resultsForm']/table/tbody/tr")).size();
		driver.findElement(By.xpath(".//form[@id='resultsForm']/table/tbody/tr")).click();
			
	}
	
	public void enterEStatementInfo(String location, String uniqueID, String dueAmt) throws Exception {

		IHGUtil.PrintMethodName();
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		
		log("Enter location ID");;
		Select selLoc = new Select(locationIDDropDwn);
		selLoc.selectByIndex(1);		

		log("Enter Unique ID and Due amout.");
		uniqueIDField.sendKeys(uniqueID);
		dueAmountField.sendKeys(dueAmt);		

		log("eStatement date remains today");
				
		log("Enter the payment due date next year");

		Select dueDate = new Select (paymentDueDateDropDwn);
		dueDate.selectByValue(statementDateDropDwnSelected.getAttribute("value"));
		Select dueMonth = new Select (paymentDueMonthDropDwn);
		dueMonth.selectByValue(statementMonthDropDwnSelected.getAttribute("value"));
		
		int thisYear = Integer.valueOf(statementYearDropDwnSelected.getAttribute("value"));
		String nextYear = Integer.valueOf(thisYear + 1).toString();
		Select dueYear = new Select (paymentDueYearDropDwn);
		dueYear.selectByValue(nextYear);
		
	}
	
	public void browseFile() throws Exception {
		IHGUtil.PrintMethodName();				
		URL eStatementPath = ClassLoader.getSystemResource(PracticeConstants.eStatementFilePath);
		log("########################File Path " + eStatementPath);
		Assert.assertNotNull(eStatementPath);
		//Providing FullPath to the eStatementPDF
		WebElement browseButton = driver.findElement(By.xpath("//input[@name='fileName:fileField']"));
		browseButton.sendKeys(eStatementPath.toString());
	}
	
	public void clickOnUploadStatementButton() throws Exception {
		IHGUtil.PrintMethodName();
		uploadStatementButton.click();
	}
	
	public boolean isEStatementUploadedSuccessfully() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		log("Success Message :"+successMsg.getText());
		return successMsg.getText().contains(successMessage);
	}
}
