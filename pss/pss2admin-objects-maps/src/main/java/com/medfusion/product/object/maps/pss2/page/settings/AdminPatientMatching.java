// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class AdminPatientMatching extends SettingsTab {

	// @FindAll({@FindBy(xpath = "//*[@id=\"patientmatch\"]/div[1]/div/table/tbody/tr")})
	// private List<WebElement> patientMatchingList;

	@FindAll({@FindBy(xpath = "//patientmatch//div//div//div//div//table[@class=\"table table-hover\"]/tbody[1]/tr")})
	private List<WebElement> patientMatchingList;

	public AdminPatientMatching(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
	}

	public void patientMatchingSelection() {

		for (int i = 1; i < patientMatchingList.size(); i++) {
			// WebElement toSelect = driver.findElement(By.xpath("//*[@id=\"patientmatch\"]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[1]/div/input"));
			// WebElement label = driver.findElement(By.xpath("//*[@id=\"patientmatch\"]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[2]/span/a"));
			// WebElement matchingCriteria = driver.findElement(By.xpath("//*[@id=\"patientmatch\"]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[3]/div/input"));
			// log(toSelect.getText() + "" + label.getText() + "" + matchingCriteria.getText());


			WebElement toSelect = driver.findElement(By.xpath("//input[@id='pmm" + i + "']"));

			WebElement label = driver.findElement(By.xpath("//patientmatch//div//div//div//div//table[@class='table table-hover']/tbody[1]/tr[" + i + "]/td[1]"));
			WebElement matchingCriteria = driver.findElement(By.xpath("//input[@id='pi" + i + "']"));
			log("label " + label.getText() + " " + toSelect.getAttribute("ng-reflect-model") + "  " + "matchingCriteria "
					+ matchingCriteria.getAttribute("ng-reflect-model"));
			log("-------");


		}
	}
}


