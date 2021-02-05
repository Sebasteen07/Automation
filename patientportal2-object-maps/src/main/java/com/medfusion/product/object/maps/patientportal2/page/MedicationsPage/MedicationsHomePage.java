// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class MedicationsHomePage extends JalapenoMenu {
	
	@FindBy(how = How.ID, using = "button-rx-request")
	private WebElement btnRxRequest;

	@FindBy(how=How.XPATH,using="//li[@id='medicationsTabActiveTab']")
	private WebElement tabActiveMed;
	
	@FindBy(how=How.XPATH,using="//li[@id='medicationsTabInactiveTab']")
	private WebElement tabInctiveMed;
	
	public MedicationsHomePage(WebDriver driver) {
		super(driver);
		
	}
	
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(btnRxRequest);

		for (int i = 0; i < 2; i++) {
			int attempt = i + 1;
			log("Checking page elements, attempt: " + attempt, Level.INFO);
			if (areMenuElementsPresent() && assessPageElements(webElementsList, 120)) {
				log("All basic elements are present", Level.INFO);
				return true;
			} else {
				log("Attempt " + attempt + " failed: Some elements are missing, reloading page", Level.INFO);
				driver.navigate().refresh();
			}
		}
		return false;
	}
	
	public void clickOnRxRequest()
	{
		log("Clicking on Rx Request button in Medications Home Page");
		btnRxRequest.click();
	}
	}


