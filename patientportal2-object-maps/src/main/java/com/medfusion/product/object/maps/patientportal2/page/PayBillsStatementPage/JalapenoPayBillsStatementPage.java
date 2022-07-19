// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.PayBillsStatementPage;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;

public class JalapenoPayBillsStatementPage extends MedfusionPage {

		@FindBy(how = How.LINK_TEXT, using = "My Account")
		private WebElement myAccount;

		@FindBy(how = How.ID, using = "statementDiv")
		private WebElement statementBlock;

		@FindBy(how = How.XPATH, using = "//div[@id='balanceDue']/span[@class='amountDue ng-binding']")
		private WebElement balanceDue;

		@FindBy(how = How.ID, using = "open-top-loggedIn-btn")
		private WebElement rightDropdownButton;

		@FindBy(how = How.ID, using = "signout_dropdown")
		private WebElement signoutDropdownButton;

		@FindBy(how = How.ID, using = "makepayment")
		private WebElement makePaymentButton;

		public JalapenoPayBillsStatementPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public JalapenoLoginPage logout(WebDriver driver) {

				IHGUtil.PrintMethodName();
				log("Trying to click on Logout button - regular resolution");

				try {
						WebElement signoutButton = driver.findElement(By.id("signout"));
						signoutButton.click();
				} catch (Exception ex) {
						log("Did not find Logout button, trying mobile version size");
						rightDropdownButton.click();
						signoutDropdownButton.click();
				}

				return PageFactory.initElements(driver, JalapenoLoginPage.class);
		}

		public String getBalanceDue(WebDriver driver) {
				try {
						log("Waiting for balance element.");
						WebDriverWait wait = new WebDriverWait(driver, 20);
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='balanceDue']/span/span")));
						WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
						log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
						return balance.getText();
				} catch (Exception ex) {
						log("Exception from element caught, rechecking");
						WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
						log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
						return balance.getText();
				}
		}

}
