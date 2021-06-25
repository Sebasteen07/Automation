// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

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

	@FindBy(how = How.ID, using = "links-tab")
	private WebElement linksTab;
	
	@FindBy(how = How.ID, using = "alert-tab")
	private WebElement announcementTab;


	public SettingsTab(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public PatientFlow gotoPatientFlowTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		commonMethods.highlightElement(patientFlowTab);
		patientFlowTab.click();
		return PageFactory.initElements(driver, PatientFlow.class);
	}

	public AccessRules gotoAccessTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		commonMethods.highlightElement(accessTab);
		accessTab.click();
		jse.executeScript("window.scrollBy(0,550)", "");
		return PageFactory.initElements(driver, AccessRules.class);
	}

	public AccessRules gotoPatientConfigurationTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		accessTab.click();
		return PageFactory.initElements(driver, AccessRules.class);
	}

	public AdminPatientMatching gotoPatientMatchingTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		patientMatchingTab.click();
		return PageFactory.initElements(driver, AdminPatientMatching.class);
	}

	public AdminReseller gotoResellerTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		resellerTab.click();
		return PageFactory.initElements(driver, AdminReseller.class);
	}

	public AlertNotification gotoAlertNotificationTab() {
		alertNotificationTab.click();
		return PageFactory.initElements(driver, AlertNotification.class);
	}

	public AdminAppointment gotoAdminAppointmentTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		commonMethods.highlightElement(appointmentTab);
		appointmentTab.click();
		return PageFactory.initElements(driver, AdminAppointment.class);
	}

	public InsuranceCarrier gotoInsuranceCarrierTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		insuranceCarrierTab.click();
		return PageFactory.initElements(driver, InsuranceCarrier.class);
	}

	public PSS2PracticeConfiguration gotoPracticeConfigTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		practiceConfigTab.click();
		return PageFactory.initElements(driver, PSS2PracticeConfiguration.class);
	}

	public LinkTab linksTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		linksTab.click();
		return PageFactory.initElements(driver, LinkTab.class);

	}
	public AnnouncementsTab goToAnnouncementTab() {
		IHGUtil.waitForElement(driver, 3, patientFlowTab);
		announcementTab.click();
		return PageFactory.initElements(driver, AnnouncementsTab.class);

	}
}
