// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class AdminPatientMatching extends SettingsTab {

	@FindAll({ @FindBy(xpath = "//div/div[2]/div/div/table/tbody/tr") })
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
	
	@FindBy(xpath = "//div[@class='row profile']/div[4]/div/a")
	private WebElement addCustomFields;
	
	@FindBy(xpath = "//tbody[@role='rowgroup']/tr/td[4]/a")
	private WebElement removeCustomFields;

	@FindBy(xpath = "(//*[@id='entityMap'])[1]")
	private WebElement setFieldNameInEngLanguage;
	
	@FindBy(xpath = "(//*[@id='entityMap'])[2]")
	private WebElement setFieldNameInEsLanguage;
			
	@FindBy(xpath = "//a[@title='Add Custom Field']")
	private WebElement saveCustomField;
	
	@FindBy(xpath = "//app-patientmatch/div/div[3]/div/button")
	private WebElement saveButton1;
	
	@FindBy(xpath = "//div[@class='col-md-12 pt-4 ng-star-inserted']/div/button")
	private WebElement saveButton2;
	
	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/a")
	private WebElement settingBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/ul")
	private WebElement logoutBtn;

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
		int pmSize = patientMatchingList.size();
		log("Size of patientMatchingList -- " + pmSize);

		for (int i = 3; i <= pmSize; i++) {
			log("Value of i --> " + i);
			int k = i - 1;
			WebElement toSelect = driver.findElement(By.xpath("//input[@id='pmm" + k + "']"));

			WebElement searchCheckbox = driver
					.findElement(By.xpath("//input[@id='pmm" + k + "']/following-sibling::label"));

			WebElement label = driver
					.findElement(By.xpath("//app-patientmatch[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[" + i
							+ "]/td[1]/span[1]/a"));

			commonMethods.highlightElement(label);
			String labelText = label.getText();
			commonMethods.highlightElement(toSelect);
			boolean valueOfSearchStatus = toSelect.isSelected();
			log("Status of Checkbox TOSELECT -" + valueOfSearchStatus);
			log("LABEL " + labelText);

			if (list.contains(labelText)) {
				log("Patient Matching Criteria is available in list and can be changed");
				if (valueOfSearchStatus == false) {
					commonMethods.highlightElement(toSelect);
					Thread.sleep(1000);
					jse.executeScript("arguments[0].click();", searchCheckbox);
					Thread.sleep(3000);
					log("Patient Matching Criteria Added " + labelText);
					Thread.sleep(3000);
					log("LABEL " + labelText);
				}
			}
		}
		scrollAndWait(1000, 0, 1000);
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
	
	public void addCustomField(String customFieldName) {
		IHGUtil.waitForElement(driver, 60, addCustomFields);
		addCustomFields.click();
		Select Select = new Select(driver.findElement(By.name("customfield")));
		Select.selectByIndex(1);
		setFieldNameInEngLanguage.sendKeys(customFieldName);
		setFieldNameInEsLanguage.sendKeys(customFieldName);
		Select Select1 = new Select(driver.findElement(By.name("custommetadata")));
		Select1.selectByIndex(2);
		saveCustomField.click();
		javascriptClick(saveButton2);
		javascriptClick(saveButton1);
		log("Custom Field added as Alternate Number");
	}
	
	public void removeCustomField() {
		javascriptClick(removeCustomFields);
		log("Custom Field removed");
	}
	
	public void logout() {
		settingBtn.click();
		logoutBtn.click();
	}
}
