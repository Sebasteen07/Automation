// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
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

	@FindBy(xpath = "//patientmatch//div//div[3]//div//button[@class='btn btn-primary' and @style='margin-right:10px']")
	private WebElement saveBtnPAtientMatching;

	@FindBy(xpath = "//a[@id='tab43-tab']")
	private WebElement genderMap;

	@FindBy(xpath = "//*[@id='gendermapcheckbox0']")
	private WebElement genderbox0Status;

	@FindBy(xpath = "//*[@id='gendermapcheckbox1']")
	private WebElement genderbox1Status;

	@FindBy(xpath = "//*[@id='gendermapcheckbox2']")
	private WebElement genderbox2Status;

	@FindBy(xpath = "//*[@id='gendermapcheckbox3']")
	private WebElement genderbox3Status;

	@FindBy(xpath = "//tbody/tr[1]/td[1]/div[1]/label[1]/i[1]")
	private WebElement genderbox0click;

	@FindBy(xpath = "//tbody/tr[2]/td[1]/div[1]/label[1]/i[1]")
	private WebElement genderbox1click;

	@FindBy(xpath = "//tbody/tr[3]/td[1]/div[1]/label[1]/i[1]")
	private WebElement genderbox2click;

	@FindBy(xpath = "//tbody/tr[4]/td[1]/div[1]/label[1]/i[1]")
	private WebElement genderbox3click;


	public AdminPatientMatching(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public void patientMatchingSelection() throws InterruptedException {

		ArrayList<String> list = new ArrayList<String>();
		list.add("Preferred Phone Number");
		list.add("Zip Code");
		list.add("Date Of Birth");
		list.add("Sex assigned at birth");
		list.add("Email Address");
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

	public Boolean isgenderBox0True() {
		return Boolean.valueOf(genderbox0Status.getAttribute("ng-reflect-model"));
	}

	public Boolean isgenderBox1True() {
		return Boolean.valueOf(genderbox1Status.getAttribute("ng-reflect-model"));
	}

	public Boolean isgenderBox2True() {
		return Boolean.valueOf(genderbox2Status.getAttribute("ng-reflect-model"));
	}

	public Boolean isgenderBox3True() {
		return Boolean.valueOf(genderbox3Status.getAttribute("ng-reflect-model"));
	}

	public void gendermap() {
		genderMap.click();
		log("Clicked on Gender Map button");
	}

	public void allToggleGendermap() {
		if (isgenderBox0True() == false) {
			genderbox0click.click();
		}
		if (isgenderBox1True() == false) {
			genderbox1click.click();
		}
		if (isgenderBox2True() == false) {
			genderbox2click.click();
		}
		if (isgenderBox3True() == false) {
			genderbox3click.click();
		}
		log("Successfully On the all gender map toggle button");
	}
}


