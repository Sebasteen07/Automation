// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class AdminPatientMatching extends SettingsTab {

	@FindAll({@FindBy(xpath = "//patientmatch//div//div//div//div//table[@class=\"table table-hover\"]/tbody[1]/tr")})
	private List<WebElement> patientMatchingList;

	@FindBy(
			xpath = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[6]/div[2]/div[1]/patientmatch[1]/div[1]/div[2]/div[1]/button[1]")
	private WebElement saveBtnPAtientMatching;

	public AdminPatientMatching(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public void patientMatchingSelection() throws InterruptedException {

		ArrayList<String> list = new ArrayList<String>();
		list.add("Preferred Phone Number");
		list.add("Zip Code");
		log("List ------>" + list);
		for (int i = 0; i < patientMatchingList.size(); i++) {
			log("Size of patientMatchingList -- " + patientMatchingList.size());
			log("Value of i --> " + i);
			WebElement toSelect = driver.findElement(By.xpath("//input[@id='pmm" + i + "']"));
			WebElement label =
					driver.findElement(By.xpath("//patientmatch//div//div//div//div//table[@class='table table-hover']/tbody[1]/tr[" + (i + 1) + "]/td[1]"));
			WebElement matchingCriteria = driver.findElement(By.xpath("//input[@id='pi" + i + "']"));
			String labelText = label.getText();
			String valueOfSearch = toSelect.getAttribute("ng-reflect-model");
			log("LABEL " + labelText + " " + valueOfSearch + "  " + "MATCHING CRITERIA " + matchingCriteria.getAttribute("ng-reflect-model"));
			log("-------");
			if (list.contains(labelText)) {
				log("Patient Matching Criteria is available in list and can be changed");
				if (valueOfSearch.equalsIgnoreCase("false")) {
					commonMethods.highlightElement(toSelect);
					Thread.sleep(1000);
					jse.executeScript("arguments[0].click();", toSelect);
					log("Patient Matching Criteria Added " + labelText);
					log("LABEL " + labelText + " " + toSelect.getAttribute("ng-reflect-model") + "  " + "MATCHING CRITERIA "
							+ matchingCriteria.getAttribute("ng-reflect-model"));
				}
			}
		}
		saveBtnPAtientMatching.click();
	}
}


