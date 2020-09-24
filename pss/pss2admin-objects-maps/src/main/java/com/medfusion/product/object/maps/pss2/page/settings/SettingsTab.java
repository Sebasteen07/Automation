//Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class SettingsTab extends PSS2MenuPage {

	@FindBy(how = How.ID, using = "basic-tab")
	private WebElement practiceConfigTab;

	@FindBy(how = How.ID, using = "par-tab")
	private WebElement accessTab;

	@FindBy(how = How.ID, using = "flow-tab")
	private WebElement patientFlowTab;

	@FindBy(how = How.ID, using = "alert-tab")
	private WebElement alertNotificationTab;

	@FindBy(how = How.ID, using = "appt-tab")
	private WebElement appointmentTab;

	@FindBy(how = How.ID, using = "patientmatch-tab")
	private WebElement patientMatchingTab;

	@FindBy(how = How.ID, using = "reseller-tab")
	private WebElement resellerTab;

	@FindBy(how = How.ID, using = "insurance-tab")
	private WebElement insuranceCarrierTab;

	public SettingsTab(WebDriver driver) {
		super(driver);
	}

	public PatientFlow gotoPatientFlowTab() {
		patientFlowTab.click();
		return PageFactory.initElements(driver, PatientFlow.class);
	}

	public AccessRules gotoAccessTab() {
		accessTab.click();
		jse.executeScript("window.scrollBy(0,550)", "");
		return PageFactory.initElements(driver, AccessRules.class);
	}

	public AccessRules gotoPatientConfigurationTab() {
		accessTab.click();
		return PageFactory.initElements(driver, AccessRules.class);
	}

	public AdminPatientMatching gotoPatientMatchingTab() {
		patientMatchingTab.click();
		return PageFactory.initElements(driver, AdminPatientMatching.class);
	}

	public AdminReseller gotoResellerTab() {
		resellerTab.click();
		return PageFactory.initElements(driver, AdminReseller.class);
	}

	public AlertNotification gotoAlertNotificationTab() {
		alertNotificationTab.click();
		return PageFactory.initElements(driver, AlertNotification.class);
	}

	public AdminAppointment gotoAdminAppointmentTab() {
		appointmentTab.click();
		return PageFactory.initElements(driver, AdminAppointment.class);
	}
	
	public InsuranceCarrier gotoInsuranceCarrierTab() {
		insuranceCarrierTab.click();
		return PageFactory.initElements(driver, InsuranceCarrier.class);
	}

	public PSS2PracticeConfiguration gotoPracticeConfigTab() {
		practiceConfigTab.click();
		return PageFactory.initElements(driver, PSS2PracticeConfiguration.class);
	}
}