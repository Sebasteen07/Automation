//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.pharmacy;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class ManageYourPharmacies extends BasePageObject {

	@FindBy(xpath = "//input[@value='Add New Pharmacy']")
	private WebElement btnAddNewPharmacy;

	@FindBy(xpath = "//div[@id='availablePharmaciesParams']//tbody/tr[1]/td[2]")
	private WebElement lnkEditPharmacy;

	@FindBy(xpath = "//div[@id='availablePharmaciesParams']//tbody/tr[1]/td[1]")
	private WebElement pharmacyInList;

	@FindBy(xpath = "//input[@name='exportPharmaciesBtn']")
	private WebElement btnExportPharmacy;

	@FindBy(xpath = "//input[@name='importPharmaciesBtn']")
	private WebElement btnImportPharmacy;

	@FindBy(xpath = "//span[text()='Edit Pharmacy']")
	private WebElement editPharmacyLink;

	@FindBy(xpath = "//input[@value='Delete Pharmacy']")
	private WebElement btnDelete;

	public ManageYourPharmacies(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public AddPharmacyPage clickOnAddPharmacyButton() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnAddNewPharmacy.click();
		return PageFactory.initElements(driver, AddPharmacyPage.class);
	}

	public String clickonEditPharmacyLink() throws InterruptedException {
		String pharmacyName = pharmacyInList.getText();
		lnkEditPharmacy.click();
		return pharmacyName;
	}

	public boolean confirmPharmacyInTable(String pharmacyName) {
		if ((pharmacyInList.isDisplayed())) {
			return true;
		}

		return false;

	}

	private ManageYourPharmacies removePharmacy(WebElement removeButton) {
		removeButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(btnDelete));
		btnDelete.click();
		return this;
	}

	private ArrayList<WebElement> getPharmacyList() {
		return (ArrayList<WebElement>) driver.findElements(By.xpath("//span[text()='Edit Pharmacy']"));
	}

	public boolean isAnyPharmacyPresent() {
		return getPharmacyList().size() > 0;
	}

	public void removeAllPharmacy() throws InterruptedException {
		log("Removing of displayed Pharmacy");
		ArrayList<WebElement> pharmacy = getPharmacyList();
		for (int i = 0; i < pharmacy.size(); i++) {
			if (pharmacy.size() > 0) {
				log("Count of displayed Pharmacy: " + pharmacy.size());
				int removedPharmacy = 0;

				ArrayList<WebElement> removeButtons = (ArrayList<WebElement>) driver
						.findElements(By.xpath("//span[text()='Edit Pharmacy']"));
				for (int j = 0; j < removeButtons.size(); j++) {
					if (removeButtons.get(j).isDisplayed()) {
						removePharmacy(removeButtons.get(j));
						log("pharmacy #" + ++removedPharmacy + " removed");
						driver.get(driver.getCurrentUrl()); //Refresh the page until removed pharmacy is not in the list
						Thread.sleep(5000);
						driver.get(driver.getCurrentUrl());
						break;

					}
				}
			} else {
				log("No previous Pharmacy is displayed");

			}
		}
	}

	public void waitTillPharmacyRemoval() {
		for(int i=1;i<=getPharmacyList().size();i++)
		{
			log("Refresh Page");
			driver.get(driver.getCurrentUrl()); //Refresh the page until removed pharmacy is not in the list
		}
		
	}

}
