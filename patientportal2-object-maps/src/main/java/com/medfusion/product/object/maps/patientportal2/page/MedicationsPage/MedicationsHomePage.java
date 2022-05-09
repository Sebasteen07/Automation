// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class MedicationsHomePage extends JalapenoMenu {

	@FindBy(how = How.ID, using = "button-rx-request")
	private WebElement btnRxRequest;

	@FindBy(how = How.XPATH, using = "//li[@id='medicationsTabActiveTab']")
	private WebElement tabActiveMed;

	@FindBy(how = How.XPATH, using = "//li[@id='medicationsTabInactiveTab']")
	private WebElement tabInctiveMed;

	public MedicationsHomePage(WebDriver driver) {
		super(driver);

	}

	public void clickOnRxRequest() throws InterruptedException {
		log("Clicking on Rx Request button in Medications Home Page");
		Thread.sleep(2000);
		btnRxRequest.click();
	}

	public boolean isRxRequestBtnDisplayed() {
		log("Verify RxRequest Button");
		try {
			return btnRxRequest.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Verify RXRequest button view access for Trusted Rep shoud not display");
			return false;
		}
	}
}
