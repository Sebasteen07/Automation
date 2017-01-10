package com.medfusion.product.object.maps.precheck.page.patient;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;


public class PatientHomePage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "//div[@class='mainContent']/h5")
	private WebElement appointmentDateTime;

	@FindBy(how = How.ID, using = "logoutIconButton")
	private WebElement logoutButton;

	@FindBy(how = How.ID, using = "demographicsHeader")
	private WebElement demographicsButton;

	@FindBy(how = How.ID, using = "insuranceHeader")
	private WebElement insuranceButton;

	@FindBy(how = How.ID, using = "coPayHeader")
	private WebElement coPayButton;

	@FindBy(how = How.ID, using = "balanceHeader")
	private WebElement balanceButton;

	@FindBy(how = How.XPATH, using = "//div[@id='demographicsWrapper']/span[@class='fa fa-fw fa-lg fa-check'][@aria-hidden='false']")
	private WebElement demographicsCheckMark;

	@FindBy(how = How.XPATH, using = "//div[@id='insuranceWrapper']/span[@class='fa fa-fw fa-lg fa-check'][@aria-hidden='false']")
	private WebElement insuranceCheckMark;


	public PatientHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public PatientLoginPage logout() {
		IHGUtil.PrintMethodName();
		logoutButton.click();
		return PageFactory.initElements(driver, PatientLoginPage.class);
	}

	public String getAppointmentDateTime() {
		return appointmentDateTime.getText();
	}

	public DemographicsPage clickDemographics() {
		demographicsButton.click();
		return PageFactory.initElements(driver, DemographicsPage.class);
	}

	public InsurancePage clickInsurance() {
		insuranceButton.click();
		return PageFactory.initElements(driver, InsurancePage.class);
	}

	public boolean isDemographicsFinished() {
		return IHGUtil.waitForElement(driver, 15, demographicsCheckMark);
	}

	public boolean isInsuranceFinished() {
		return IHGUtil.waitForElement(driver, 15, insuranceCheckMark);
	}



}
