// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class LinkTab extends SettingsTab {

	@FindBy(how = How.XPATH, using = "//*[@id='links']/div/div/legend/strong/a/i")
	private WebElement linkplusButton;

	@FindBy(how = How.XPATH, using = "//input[@id='search-link']")
	private WebElement searchLink;

	@FindBy(how = How.XPATH, using = "//*[@class='form-group col-lg-3 col-xs-6']/div")
	private WebElement clickdropType;

	@FindBy(how = How.XPATH, using = "//select[@name='logintype']")
	private WebElement typeselect;

	@FindBy(how = How.XPATH, using = "//*[@id='links']/div[1]/div[2]/div[2]/ng-multiselect-dropdown/div/div[1]/span/span[2]/span")
	private WebElement locationselect;

	@FindBy(how = How.XPATH, using = "//*[@id='links']/div[1]/div[2]/div[3]/ng-multiselect-dropdown/div/div[1]/span/span[2]/span")
	private WebElement resourceselect;

	@FindBy(how = How.XPATH, using = "//*[@id='links']/div[1]/div[3]/fieldset/div[1]/button")
	private WebElement createLinkButton;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'100')]")
	private WebElement lastpage100;

	@FindAll({@FindBy(xpath = "//*[@class='multiselect-item-checkbox']")})
	private List<WebElement> checklistLocation;

	@FindBy(how = How.XPATH, using = "//tbody/tr/td[5]/a[1]/i[1]")
	private WebElement removelink;

	@FindBy(how = How.XPATH, using = "//*[@class='fa fa-link']")
	private WebElement copyLink;

	// @FindBy(how = How.XPATH, using = "//*[@id='links']/div/div/div[2]/table/tbody[2]/tr/td")
	// private WebElement noProviderConfigured;

	@FindAll({@FindBy(xpath = "//*[@id='links']/div/div/div[2]/table/tbody[2]/tr/td")})
	private List<WebElement> noProviderConfigured;

	public LinkTab(WebDriver driver) {
		super(driver);
	}

	public void searchLinkandRemove(String provider) {
		searchLink.clear();
		searchLink.sendKeys(provider);
		log("Search the Link for " + provider);
		if (noProviderConfigured.size() > 0) {
			searchLink.clear();
			log("Link is Not avaliable For" + provider);
		} else {
			log("Link is avaliable For" + provider);
			removelink.click();
			log("Successfully Removed the link form link tab");
		}
	}

	public void searchLink(String provider) throws InterruptedException {
		log("going to search link agin");
		Thread.sleep(3000);
		searchLink.clear();
		Thread.sleep(3000);
		searchLink.sendKeys(provider);
	}

	public void addLink(String locationConfig, String providerConfig) throws InterruptedException {
		linkplusButton.click();
		log("Clicked on link plus button");
		clickdropType.click();
		log("Clicked on type  button");
		Select type = new Select(typeselect);
		type.selectByVisibleText("LOGINLESS");
		locationselect.click();
		log("LocationTypeList " + checklistLocation.size());
		for (int i = 0; i < checklistLocation.size(); i++) {
			if (checklistLocation.get(i).getText().contains(locationConfig)) {
				checklistLocation.get(i).click();
				log("Location checkbox selected");
			}
		}
		Thread.sleep(3000);
		resourceselect.click();
		log("ProviderTypeList " + checklistLocation.size());
		for (int i = 0; i < checklistLocation.size(); i++) {
			if (checklistLocation.get(i).getText().contains(providerConfig)) {
				checklistLocation.get(i).click();
				log("Provider checkbox selected");

			}
		}
		createLinkButton.click();
		log("Clicked on Create link Button");
	}

	public String getURL(String provider) throws InterruptedException, HeadlessException, UnsupportedFlavorException, IOException {
		searchLink(provider);
		copyLink.click();
		String link = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		log("Link is   " + link);
		return link;

	}

	public void removelink() {
		lastpage100.click();
	}
}
