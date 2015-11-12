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
	private WebElement appointmentDate;

	@FindBy(how = How.ID, using = "appointmentTime")
	private WebElement appointmentTime;
	
	@FindBy(how = How.ID, using = "locationName")
	private WebElement locationName;

	@FindBy(how = How.ID, using = "providerName")
	private WebElement providerName;

	@FindBy(how = How.ID, using = "patientId")
	private WebElement patientId;

	@FindBy(how = How.ID, using = "patientFirstName")
	private WebElement patientFirstName;

	@FindBy(how = How.ID, using = "patientMiddleName")
	private WebElement patientMiddleName;

	@FindBy(how = How.ID, using = "patientLastName")
	private WebElement patientLastName;

	@FindBy(how = How.ID, using = "patientDateOfBirth")
	private WebElement patientDOB;

	@FindBy(how = How.ID, using = "patientMailingAddressLineOne")
	private WebElement patientMailingAddessLine1;

	@FindBy(how = How.ID, using = "patientMailingAddressLineTwo")
	private WebElement patientMailingAddessLine2;

	@FindBy(how = How.ID, using = "patientCity")
	private WebElement patientCity;

	@FindBy(how = How.ID, using = "patientState")
	private WebElement patientState;

	@FindBy(how = How.ID, using = "patientZip")
	private WebElement patientZip;

	@FindBy(how = How.ID, using = "patientPhone")
	private WebElement patientPhoneNumber;

	@FindBy(how = How.ID, using = "patientEmail")
	private WebElement patientEmail;

	@FindBy(how = How.ID, using = "patientCopayment")
	private WebElement patientCopayment;

	@FindBy(how = How.ID, using = "patientOutstandingBalance")
	private WebElement patientOutstandingBalance;

	@FindBy(how = How.ID, using = "createButton")
	private WebElement createButton;
	

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