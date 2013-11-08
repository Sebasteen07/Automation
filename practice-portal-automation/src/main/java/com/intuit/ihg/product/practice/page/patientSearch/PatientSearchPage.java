package com.intuit.ihg.product.practice.page.patientSearch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.intuit.ihg.product.practice.page.patientactivation.PatientactivationPage;
import com.intuit.ihg.product.practice.utils.PracticeConstants;

public class PatientSearchPage extends BasePageObject{
	
	public PatientSearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//table[@class='searchForm']//input[@name='member_first_nme']")
	private WebElement firstName;
	
	@FindBy(xpath="//table[@class='searchForm']//input[@name='member_last_nme']")
	private WebElement lastName;
	
	@FindBy(xpath="//table[@class='searchForm']//input[@name='member_email']")
	private WebElement email;
	
	@FindBy(xpath="//table[@class='searchForm']//input[@id='search_now']")
	private WebElement searchForPatient;
	
	@FindBy(xpath="//table[@class='sort-table']/tbody/tr/td/a")
	public WebElement searchResult;
	
	@FindBy(linkText="Add a New Patient")
	private WebElement addNewPatient;
	
	@FindBy( xpath = ".//input[@name='searchParams:0:input']")
	private WebElement firstNameField;
	
	@FindBy( xpath = ".//input[@name='searchParams:1:input']")
	private WebElement lastNameField;
	
	@FindBy( xpath = ".//input[@name='buttons:submit']")
	private WebElement searchButton;
	
	
	/**
	 * @Description:Set Patient First Name
	 */
	public void setFirstName()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, firstName);
		firstName.clear();
		firstName.sendKeys(PracticeConstants.PatientFirstName);
	}
	
	/**
	 * @Description:Set Patient Last Name
	 */
	public void setLastName()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, lastName);
		lastName.clear();
		lastName.sendKeys(PracticeConstants.PatientLastName);
	}
	
	/**
	 * @Description:Set Email
	 */
	public void setEmail()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, email);
		email.clear();
		email.sendKeys(PracticeConstants.PatientEmail);
	}
	
	
	
	/**
	 * @Description:Set Patient Search Fields
	 */
	public void setPatientSearchFields()
	{
		IHGUtil.PrintMethodName();
		setFirstName();
		setLastName();
		searchForPatient.click();
	}
	
	public PatientactivationPage clickOnAddNewPatient() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, addNewPatient);
		addNewPatient.click();
		return PageFactory.initElements(driver, PatientactivationPage.class);
	}
	
	public void searchPatient(String fName, String lName) {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, firstNameField);
		firstNameField.sendKeys(fName);
		lastNameField.sendKeys(lName);
		searchButton.click();
		
	}
	
	public void searchForPatientInPatientSearch(String fName, String lName) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, firstName);
		firstName.clear();
		firstName.sendKeys(fName);
		IHGUtil.waitForElement(driver, 15, lastName);
		lastName.clear();
		lastName.sendKeys(lName);
		IHGUtil.waitForElement(driver, 10, searchForPatient);
		searchForPatient.click();
		Thread.sleep(5000);
	}
	

	public boolean isTransactionPresent(String amount, String fName, String lName) {
		IHGUtil.PrintMethodName();
		StringBuilder amont = new StringBuilder(amount);
		amont = amont.insert(1, ",");
		log("Ammmmmmountttt*******:"+amont);
		return driver.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), '"+amont+"')]/ancestor::tr/td//a/span[contains(text(), '"+lName+", "+fName+"')]")).isDisplayed();
	}
	
	public PayMyBillOnlinePage selectTheTransaction(String amount,  String fName, String lName) {
		IHGUtil.PrintMethodName();
		StringBuilder amont = new StringBuilder(amount);
		amont = amont.insert(1, ",");
		driver.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), '"+amont+"')]/ancestor::tr/td//a/span[contains(text(), '"+lName+", "+fName+"')]")).click();
		return PageFactory.initElements(driver, PayMyBillOnlinePage.class);
	}
	

}
