package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InsuranceCarrier extends SettingsTab {

	@FindBy(how = How.ID, using = "radior0")
	private WebElement appointmentCommentOption;

	@FindBy(how = How.ID, using = "radior1")
	private WebElement chiefCompliant;

	@FindBy(how = How.ID, using = "showinsuranceatstart")
	private WebElement showInsuranceAtStart;

	@FindBy(how = How.ID, using = "insuranceMandatory")
	private WebElement insuranceMandatory;

	@FindBy(how = How.ID, using = "selfpay")
	private WebElement selfPay;

	@FindBy(how = How.ID, using = "showInsuranceCarrierFromPM")
	private WebElement showInsuranceCarrierFromPM;

	@FindBy(how = How.XPATH, using = "//*[@id=\"insurance\"]/fieldset/div/div/button")
	private WebElement buttonSave;

	public InsuranceCarrier(WebDriver driver) {
		super(driver);
	}

	public Boolean isShowInsuranceAtStartEnabled() {
		return showInsuranceAtStart.isSelected();
	}

	public void enableAppointmentCommentOption() {
		if (!appointmentCommentOption.isSelected()) {
			appointmentCommentOption.click();
			buttonSave.click();
		}
	}

	public void enableshowInsuranceAtStart() {
		if (!showInsuranceAtStart.isSelected()) {
			showInsuranceAtStart.click();
			buttonSave.click();
		}
		log("showInsuranceAtStart= " + showInsuranceAtStart.isSelected());
	}

	public void enableInsuranceMandatory() {
		insuranceMandatory.click();
		buttonSave.click();
	}

	public void enableSelfPay() {
		selfPay.click();
		buttonSave.click();
	}

	public void enableShowInsuranceCarrier() {
		showInsuranceCarrierFromPM.click();
		buttonSave.click();
	}

	public void selectAppointmentCommentOption() {
		appointmentCommentOption.click();
		buttonSave.click();
	}

	public void selectChiefCompliant() {
		chiefCompliant.click();
		buttonSave.click();
	}
}