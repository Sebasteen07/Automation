// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;

public class OnlineAppointmentScheduling extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//button[@class='dismissbuttons']//span[contains(text(),'Dismiss')]")
	private WebElement dismissBtn;

	@FindBy(how = How.XPATH, using = "//button[@class='dismissbuttons']//span[contains(text(),'Dismiss')]")
	private WebElement buttonNewPatient;

	@FindBy(how = How.XPATH, using = ".//div[@class=\"col-sm-10\"]/div[2]/a/span")
	private WebElement buttonExistingPatient;

	public OnlineAppointmentScheduling(WebDriver driver) {
		super(driver);
	}

	public OnlineAppointmentScheduling(WebDriver driver, String url) {
		super(driver, url);
	}

	public ExistingPatient selectExistingPatient() {
		buttonExistingPatient.click();
		return PageFactory.initElements(driver, ExistingPatient.class);
	}

	public LoginlessPatientInformation selectExistingPatientLoginLess() {
		buttonExistingPatient.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}

	public NewPatientLoginPage selectNewPatient() {
		buttonNewPatient.click();
		return PageFactory.initElements(driver, NewPatientLoginPage.class);
	}

	public LoginlessPatientInformation selectNewPatientLoginLess() {
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}

	public LoginlessPatientInformation clickDismiss() {
		buttonNewPatient.click();
		return PageFactory.initElements(driver, LoginlessPatientInformation.class);
	}

	public ExistingPatientIDP selectExistingPatientIDP() {
		//buttonExistingPatient.click();
		return PageFactory.initElements(driver, ExistingPatientIDP.class);
	}

	public NewPatientIDP selectNewPatientIDP() {
		buttonNewPatient.click();
		return PageFactory.initElements(driver, NewPatientIDP.class);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		//IHGUtil.waitForElement(driver, 80, buttonNewPatient);
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(buttonNewPatient);
		webElementsList.add(buttonExistingPatient);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

}
