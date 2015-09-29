package com.medfusion.product.object.maps.precheck.page.AppointmentDetails;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class AppointmentDetailsPage extends BasePageObject {
	
	
	@FindBy(how = How.XPATH, using = ".//*[@id='appointmentDate']")
	public WebElement appointmentDate;

	@FindBy(how = How.XPATH, using = "id_appointmentTime")
	public WebElement appointmentTime;

	@FindBy(how = How.XPATH, using = ".//*[@id='providerName']")
	public WebElement providerName;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientId']")
	public WebElement patientId;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientFirstName']")
	public WebElement patientFirstName;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientMiddleName']")
	public WebElement patientMiddleName;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientLastName']")
	public WebElement patientLastName;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientDateOfBirth']")
	public WebElement patientDOB;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientMailingAddressLineOne']")
	public WebElement patientMailingAddessLine1;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientMailingAddressLineTwo']")
	public WebElement patientMailingAddessLine2;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientCity']")
	public WebElement patientCity;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientState']")
	public WebElement patientState;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientZip']")
	public WebElement patientZip;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientPhone']")
	public WebElement patientPhoneNumber;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientEmail']")
	public WebElement patientEmail;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientCopayment']")
	public WebElement patientCopayment;

	@FindBy(how = How.XPATH, using = ".//*[@id='patientOutstandingBalance']")
	public WebElement patientOutstandingBalance;

	@FindBy(how = How.XPATH, using = ".//*[@id='createButton']")
	public WebElement createButton;
	

	public AppointmentDetailsPage(WebDriver driver) {

		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void scheduleAppointment(String appointmentDateS, String providerNameS, String patientIdS, String patientFirstNameS,
									String patientMiddleNameS, String patientLastNameS, String patientDOBS, String patientMailingAddessLine1S,
									String patientMailingAddessLine2S, String patientCityS, String patientZipS, String patientPhoneNumberS,
									String patientEmailS, String patientCopaymentS, String patientOutstandingBalanceS) {
		IHGUtil.PrintMethodName();
		appointmentDate.sendKeys(appointmentDateS);
		providerName.sendKeys(providerNameS);
		patientId.sendKeys(patientIdS);
		patientFirstName.sendKeys(patientFirstNameS);
		patientMiddleName.sendKeys(patientMiddleNameS);
		patientLastName.sendKeys(patientLastNameS);
		patientDOB.sendKeys(patientDOBS);
		patientMailingAddessLine1.sendKeys(patientMailingAddessLine1S);
		patientMailingAddessLine2.sendKeys(patientMailingAddessLine2S);
		patientCity.sendKeys(patientCityS);
		patientZip.sendKeys(patientZipS);
		patientPhoneNumber.sendKeys(patientPhoneNumberS);
		patientEmail.sendKeys(patientEmailS);
		patientCopayment.sendKeys(patientCopaymentS);
		patientOutstandingBalance.sendKeys(patientOutstandingBalanceS);
		createButton.click();
	}
	
}