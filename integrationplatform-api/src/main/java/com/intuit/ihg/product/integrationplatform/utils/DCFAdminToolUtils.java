// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class DCFAdminToolUtils extends MedfusionPage{

	private static final int TIME_TO_WAIT_MS = 10000;

	@FindBy(how = How.XPATH, using = "//input[@id='userid']")
	private WebElement userIDField;

	@FindBy(how = How.XPATH, using = "//input[@id='pwd']")
	private WebElement passwordField;

	@FindBy(how = How.XPATH, using = "//input[@id='ldapsubmit']")
	private WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//div[.='All Data Channel']")
	private WebElement allDataChannelLink;

	@FindBy(how = How.XPATH, using = "//select[@id='dfname']")
	private WebElement selectDCName;


	@FindBy(how = How.XPATH, using = "//input[@id='dataJobId']")
	private WebElement dataJobIdField;

	@FindBy(how = How.XPATH, using = "//input[@id='showMessagePanel']")
	private WebElement searchButton;

	@FindBy(how = How.XPATH, using = "//li[.='Reprocess']/a")
	private WebElement reprocessButton;

	@FindBy(how = How.XPATH, using = "//li[.='Reprocess']/..//a[.='Exception']")
	private WebElement exceptionButton;


	@FindBy(how = How.XPATH,
			using = "//li[.='Reprocess']/..//a[.='Exception']/../../..//div[@class='activityDetailsInnerlist' and contains(.,'Google Maps API did not return a timezone for zipcode')]")
	private WebElement exceptionMessageTimezoneZipcode;

	@FindBy(how = How.XPATH, using = "//div[@id='logout']")
	private WebElement logoutButton;

	public DCFAdminToolUtils(WebDriver driver, String url) {
	    super(driver, url);
	}

	public DCFAdminToolUtils(WebDriver driver) {
	    super(driver);
	}

	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// body[@class='bodyinbox yscrollbar']//div[contains(text(),'" + text + "')]")).isDisplayed();
	}

	public void checkReprocessorButton(String url, String dataJobId, String condition) {
		try {
			this.driver.get(url);

			userIDField.sendKeys("SunilXavier");
			passwordField.sendKeys("Jul@s2022");

			submitButton.click();

			allDataChannelLink.click();

			Select select = new Select(selectDCName);
			select.selectByVisibleText("All");

			dataJobIdField.sendKeys(dataJobId);

			searchButton.click();

			if (IHGUtil.exists(driver, 50, reprocessButton)) {
				Actions action = new Actions(driver);
				action.moveToElement(exceptionButton).build().perform();
				if (condition.contains("invalidzip")) {
					exceptionButton.click();
					assertTrue(exceptionMessageTimezoneZipcode.getText().contains("Google Maps API did not return a timezone for zipcode"),
							"Zip code invalid Google api exception message found.");

				} else {
					log("Reprocess button found");
					assertTrue(true);
				}

			}

		} catch (Exception e) {
			log(e.getMessage());
			assertTrue(false);
		}

	}
	}

