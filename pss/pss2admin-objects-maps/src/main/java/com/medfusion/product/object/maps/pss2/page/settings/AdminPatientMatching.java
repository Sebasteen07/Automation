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

	@FindAll({ @FindBy(xpath = "//*[@id=\"tab41\"]/patientmatch/div/div[2]/div/div/table/tbody/tr") })
	private List<WebElement> patientMatchingList;

	@FindBy(xpath = "//div[@id='tab41']//button[@type='submit'][normalize-space()='Save']")
	private WebElement saveBtnPAtientMatching;

	@FindBy(xpath = "//a[@id='tab43-tab']")
	private WebElement genderMap;

	@FindBy(xpath = "//label[@for='gendermapcheckbox0']//input")
	private WebElement genderbox0Status;

	@FindBy(xpath = "//label[@for='gendermapcheckbox1']//input")
	private WebElement genderbox1Status;

	@FindBy(xpath = "//label[@for='gendermapcheckbox2']//input")
	private WebElement genderbox2Status;

	@FindBy(xpath = "//label[@for='gendermapcheckbox3']//input")
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

	@Override
	public boolean areBasicPageElementsPresent() {
		return false;
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
		log("Size of patientMatchingList -- " + patientMatchingList.size());

		for (int i = 4; i < patientMatchingList.size(); i++) {
			log("Value of i --> " + i);
			WebElement toSelect = driver.findElement(By.xpath("//input[@id='pmm" + i + "']"));
			WebElement searchCheckbox = driver
					.findElement(By.xpath("//input[@id='pmm" + i + "']/following-sibling::label"));
			WebElement label = driver.findElement(By
					.xpath("//*[@id=\"tab41\"]/patientmatch/div/div[2]/div/div/table/tbody/tr[" + (i + 1) + "]/td[1]"));
			WebElement matchingCriteria = driver.findElement(By.xpath("//input[@id='pi" + i + "']"));

			String labelText = label.getText();

			boolean valueOfSearchStatus = toSelect.isSelected();
			boolean matchingCriteriaStatus = matchingCriteria.isEnabled();

			log("LABEL " + labelText + " " + valueOfSearchStatus + "  " + "MATCHING CRITERIA "
					+ matchingCriteriaStatus);
			log("-------");
			if (list.contains(labelText)) {
				log("Patient Matching Criteria is available in list and can be changed");

				if (valueOfSearchStatus == false) {

					commonMethods.highlightElement(toSelect);
					Thread.sleep(1000);
					jse.executeScript("arguments[0].click();", searchCheckbox);
					Thread.sleep(3000);
					log("Patient Matching Criteria Added " + labelText);
					Thread.sleep(3000);
					log("LABEL " + labelText + " Search Criteria->" + valueOfSearchStatus + " MATCHING CRITERIA-> "
							+ matchingCriteriaStatus);
				}
			}
		}
		saveBtnPAtientMatching.click();
	}

	public Boolean isgenderBox0True() {
		return genderbox0Status.isSelected();
	}

	public Boolean isgenderBox1True() {
		return genderbox1Status.isSelected();
	}

	public Boolean isgenderBox2True() {
		return genderbox2Status.isSelected();
	}

	public Boolean isgenderBox3True() {
		return genderbox3Status.isSelected();
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
		log("Successfully On the all gender map toggle button");
	}
}
