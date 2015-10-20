package com.medfusion.product.object.maps.precheck.page.AppointmentDetails;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class AppointmentDetailsPage extends BasePageObject {
	
	
	@FindBy(how = How.ID, using = "appointmentDate")
	public WebElement appointmentDate;

	@FindBy(how = How.ID, using = "appointmentTime")
	public WebElement appointmentTime;
	
	@FindBy(how = How.ID, using = "locationName")
	public WebElement locationName;

	@FindBy(how = How.ID, using = "providerName")
	public WebElement providerName;

	@FindBy(how = How.ID, using = "patientId")
	public WebElement patientId;

	@FindBy(how = How.ID, using = "patientFirstName")
	public WebElement patientFirstName;

	@FindBy(how = How.ID, using = "patientMiddleName")
	public WebElement patientMiddleName;

	@FindBy(how = How.ID, using = "patientLastName")
	public WebElement patientLastName;

	@FindBy(how = How.ID, using = "patientDateOfBirth")
	public WebElement patientDOB;

	@FindBy(how = How.ID, using = "patientMailingAddressLineOne")
	public WebElement patientMailingAddessLine1;

	@FindBy(how = How.ID, using = "patientMailingAddressLineTwo")
	public WebElement patientMailingAddessLine2;

	@FindBy(how = How.ID, using = "patientCity")
	public WebElement patientCity;

	@FindBy(how = How.ID, using = "patientState")
	public WebElement patientState;

	@FindBy(how = How.ID, using = "patientZip")
	public WebElement patientZip;

	@FindBy(how = How.ID, using = "patientPhone")
	public WebElement patientPhoneNumber;

	@FindBy(how = How.ID, using = "patientEmail")
	public WebElement patientEmail;

	@FindBy(how = How.ID, using = "patientCopayment")
	public WebElement patientCopayment;

	@FindBy(how = How.ID, using = "patientOutstandingBalance")
	public WebElement patientOutstandingBalance;

	@FindBy(how = How.ID, using = "createButton")
	public WebElement createButton;
	

	public AppointmentDetailsPage(WebDriver driver) {

		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void scheduleAppointment(String appointmentDateS, String location, String providerNameS, String patientIdS, String patientFirstNameS,
									String patientMiddleNameS, String patientLastNameS, String patientDOBS, String patientMailingAddessLine1S,
									String patientMailingAddessLine2S, String patientCityS, String patientZipS, String patientPhoneNumberS,
									String patientEmailS, String patientCopaymentS, String patientOutstandingBalanceS) {
		IHGUtil.PrintMethodName();
		appointmentDate.sendKeys(appointmentDateS);
		locationName.click();
		locationName.sendKeys(location);
		providerName.sendKeys(providerNameS);
		patientId.sendKeys(patientIdS);
		patientFirstName.sendKeys(patientFirstNameS);
		patientMiddleName.sendKeys(patientMiddleNameS);
		patientLastName.sendKeys(patientLastNameS);
		patientDOB.sendKeys(patientDOBS);
		patientMailingAddessLine1.click();
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