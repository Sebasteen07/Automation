//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.EnvironmentTypeUtil.EnvironmentType;
import com.medfusion.common.utils.IHGUtil;

public class PhrDocumentsPage extends BasePageObject {

	@FindBy(xpath = "//em[text()='Log out']")
	private WebElement btnLogout;

	@FindBy(xpath = "(//td[@class='dtein'])[1]")
	private WebElement firstDate;

	@FindBy(xpath = "id('id0')")
	private WebElement btnNewHealthInformation;

	@FindBy(xpath = ".//a[@class = 'green_button']")
	private WebElement btnViewHealthInformation;

	@FindBy(linkText = "Send my information")
	private WebElement btnShareWithDoctor;

	@FindBy(xpath = "//html/body/div[2]/form/div[1]/input[@type='text']")
	private WebElement textBoxToEnterEmailAddress;

	@FindBy(xpath = "//form/div/button")
	private WebElement btnSendToShareTheHealthInformation;

	@FindBy(xpath = "//form/div/span")
	private WebElement textBoxResponseMsg;

	@FindBy(linkText = "Close")
	private WebElement btnClose;

	@FindBy(id = "closeCcd")
	private WebElement btnCloseViewer;

	String[] myDirectAddresses = { "ihg!!!qa@service.directaddress.net", "ihg_qa@service.address.net",
			"ihg_qa@gmail.com", "ihg_qa@direct.healthvault.com" };

	String[] myDirectResponses = { "ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"Service temporarily unavailable.", "Service temporarily unavailable.",
			"Service temporarily unavailable." };

	String[] myDirectResponsesDev3 = { "ihg!!!qa@service.directaddress.net is invalid. Please correct the format.",
			"Service temporarily unavailable.", "Service temporarily unavailable.",
			"Service temporarily unavailable." };

	public PhrDocumentsPage(WebDriver driver) {
		super(driver);
	}

	public String getFirstDate() {
		IHGUtil.PrintMethodName();
		return firstDate.getText();
	}

	public void clickFirstCcdInTheList() throws Exception {
		IHGUtil.PrintMethodName();
		btnNewHealthInformation.click();
		Thread.sleep(5000);
	}

	public void clickViewHealthInformation() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnViewHealthInformation.click();
		Thread.sleep(1000);
	}

	public void clickOnShareWithADoctor() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnShareWithDoctor.click();
	}

	public WebElement enterDirectAddress() throws InterruptedException {
		IHGUtil.PrintMethodName();
		return textBoxToEnterEmailAddress;
	}

	public void clickOnSendToShareWithAnotherDoctor() throws InterruptedException {
		IHGUtil.PrintMethodName();
		btnSendToShareTheHealthInformation.click();
	}

	public String getResponseAfterSending() throws InterruptedException {
		IHGUtil.PrintMethodName();
		return textBoxResponseMsg.getText();
	}

	public void clickOnCloseAfterSharingTheHealthInformation() throws InterruptedException {
		IHGUtil.PrintMethodName();
		if (btnClose.isDisplayed()) {
			btnClose.click();
		} else {
			log("### PhrDocumentsPage.btnClose  --- Could not find Close Button");
		}
	}

	public void clickOnCloseViewer() throws InterruptedException {
		IHGUtil.PrintMethodName();
		if (btnCloseViewer.isDisplayed()) {
			btnCloseViewer.click();
		} else {
			log("### PhrDocumentsPage.btnCloseViewer --- Could not find Close Viewer");
		}
	}

	public void addAddressesAndValidate() throws InterruptedException {
		for (int i = 0; i < myDirectAddresses.length; i++) {
			enterDirectAddress().sendKeys(myDirectAddresses[i]);
			clickOnSendToShareWithAnotherDoctor();
			Thread.sleep(20000);
			String textBoxResponseMsg = getResponseAfterSending();
			assertEquals(myDirectResponses[i], textBoxResponseMsg);
			enterDirectAddress().clear();
		}
	}

	public void addAddressesAndValidateDev3() throws InterruptedException {
		for (int i = 0; i < myDirectAddresses.length; i++) {
			enterDirectAddress().sendKeys(myDirectAddresses[i]);
			clickOnSendToShareWithAnotherDoctor();
			Thread.sleep(20000);
			String textBoxResponseMsg = getResponseAfterSending();
			assertEquals(myDirectResponsesDev3[i], textBoxResponseMsg);
			enterDirectAddress().clear();
		}
	}

	public String getPstTimings() {
		Date now = new Date();
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("PST"));
		String expectedPST = dateFormatGmt.format(now);
		return expectedPST;
	}

	public void refreshPage(WebDriver driver) throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(10000);
	}

	public void closeViewer() throws InterruptedException {
		driver.switchTo().frame(0);
		IHGUtil util = new IHGUtil(driver);
		if (util.checkCcdType() == false) {
			clickOnCloseViewer();
		}

		else if (util.checkCcdType() == true) {
			clickOnShareWithADoctor();
			if (IHGUtil.getEnvironmentType().equals(EnvironmentType.DEV3)) {
				addAddressesAndValidateDev3();
				clickOnCloseAfterSharingTheHealthInformation();
				Thread.sleep(2000);
				clickOnCloseViewer();
			} else {
				addAddressesAndValidate();
				clickOnCloseAfterSharingTheHealthInformation();
				Thread.sleep(2000);
				clickOnCloseViewer();
			}
		}
	}

	public PhrLoginPage clickLogout() {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);
		btnLogout.click();
		return PageFactory.initElements(driver, PhrLoginPage.class);
	}
}
