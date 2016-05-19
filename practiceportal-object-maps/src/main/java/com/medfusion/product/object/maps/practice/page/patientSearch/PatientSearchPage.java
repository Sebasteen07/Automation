package com.medfusion.product.object.maps.practice.page.patientSearch;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.practice.api.utils.PracticeConstants;

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
	
	@FindBy( xpath = "//input[@value='Email Username']")
	private WebElement	emailUserName;
	
	@FindBy(name="email")
	private WebElement newEmail;
	
	@FindBy(name="confirm_email")
	private WebElement newEmailConfirm;
	
	@FindBy(name="submitted")
	private WebElement updateEmail;
	
	@FindBy(xpath="//table[@class='searchForm']//input[@name='member_type']")
	private List<WebElement> patientStatus;
	
	private WebElement patient;
	
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
	
	public PatientActivationPage clickOnAddNewPatient() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, addNewPatient);
		addNewPatient.click();
		return PageFactory.initElements(driver, PatientActivationPage.class);
	}
	
	public void searchPatient(String fName, String lName) {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, firstNameField);
		firstNameField.sendKeys(fName);
		lastNameField.sendKeys(lName);
		searchButton.click();
		
	}
	
	public void searchForPatientInPatientSearch(String fName, String lName) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, firstName);
		firstName.clear();
		firstName.sendKeys(fName);
		IHGUtil.waitForElement(driver, 15, lastName);
		lastName.clear();
		lastName.sendKeys(lName);
		IHGUtil.waitForElement(driver, 12, searchForPatient);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchForPatient.click();	
	}

	public boolean isTransactionPresent(String amount, String fName, String lName) {
		IHGUtil.PrintMethodName();
		log("Amount searched for: "+amount);
		return driver.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), '"+amount+"')]/ancestor::tr/td//a/span[contains(text(), '"+lName+", "+fName+"')]")).isDisplayed();
	}
	
	public PayMyBillOnlinePage selectTheTransaction(String amount,  String fName, String lName) {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), '"+amount+"')]/ancestor::tr/td//a/span[contains(text(), '"+lName+", "+fName+"')]")).click();
		return PageFactory.initElements(driver, PayMyBillOnlinePage.class);
	}
	
	public PatientDashboardPage clickOnPatient(String frstNm, String lstNm) throws InterruptedException
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,30,searchResult);
		patient = driver.findElement(By.xpath("//a[@title='Click to View/Edit "+lstNm+", "+frstNm+"']"));
		IHGUtil.waitForElement(driver, 30, patient);
		patient.click();
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	}
	
	public PatientDashboardPage sendUserNameEmail() throws InterruptedException
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,10,emailUserName);
		emailUserName.click();
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	}
	
	public PatientDashboardPage changeEmail(String baseEmail){
		// create new email string from baseEmail@something.com to baseEmail+1234568@something.com
		String concatEmailWith = "+"+String.valueOf(System.currentTimeMillis());
		StringBuffer sbNewEmail = new StringBuffer(baseEmail);
		sbNewEmail.insert(baseEmail.indexOf("@"), concatEmailWith);
		
		log("Change email to: "+sbNewEmail.toString());
		
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, newEmail);		
		newEmail.sendKeys(sbNewEmail.toString());
		newEmailConfirm.sendKeys(sbNewEmail.toString());
		
		IHGUtil.waitForElement(driver, 15, updateEmail);
		updateEmail.click();
		
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	
	}
	
	public PatientDashboardPage changeEmailWithoutModify(String baseEmail){
		IHGUtil.PrintMethodName();
		
		IHGUtil.waitForElement(driver, 15, newEmail);		
		newEmail.sendKeys(baseEmail);
		newEmailConfirm.sendKeys(baseEmail);
		
		IHGUtil.waitForElement(driver, 15, updateEmail);
		updateEmail.click();
		
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	
	}
	
	public void searchAllPatientInPatientSearch(String fName, String lName,int status) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, firstName);
		firstName.clear();
		firstName.sendKeys(fName);
		IHGUtil.waitForElement(driver, 15, lastName);
		lastName.clear();
		lastName.sendKeys(lName);
		
		for (WebElement pstatus : patientStatus)
		{
			if(Integer.parseInt(pstatus.getAttribute("value"))==status)
			{
				pstatus.click();
			}
		}
		IHGUtil.waitForElement(driver, 10, searchForPatient);
		Thread.sleep(2000);
		searchForPatient.click();	
	}
}
