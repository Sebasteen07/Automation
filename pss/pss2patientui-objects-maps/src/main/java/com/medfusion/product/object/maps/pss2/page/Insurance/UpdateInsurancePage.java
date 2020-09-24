// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Insurance;

import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class UpdateInsurancePage extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//*[@id=\"pssinsurance\"]/div[1]/form/div[1]/div/div/div/div[2]/input")
	private WebElement selectInsuranceCarrier;

	@FindBy(how = How.XPATH, using = "//*[@id=\"pssinsurance\"]/div[1]/form/div[1]/div/div/span")
	private WebElement selectArrow;

	@FindBy(how = How.ID, using = "insurancecarrier")
	private WebElement inputInsuranceCarrier;

	@FindBy(how = How.ID, using = "memeberid")
	private WebElement inputMemberID;

	@FindBy(how = How.ID, using = "groupid")
	private WebElement inputGroupID;

	@FindBy(how = How.ID, using = "phoneNumber")
	private WebElement inputInsurancePhone;

	@FindBy(how = How.ID, using = "donotupdate")
	private WebElement buttonDontUpdateInsurance;

	@FindBy(how = How.ID, using = "updateinfo")
	private WebElement buttonUpdateInsuranceInfo;

	public UpdateInsurancePage(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputInsuranceCarrier);
		webElementsList.add(inputMemberID);
		webElementsList.add(inputGroupID);
		webElementsList.add(inputInsurancePhone);
		webElementsList.add(buttonDontUpdateInsurance);
		webElementsList.add(buttonUpdateInsuranceInfo);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public ConfirmationPage skipInsuranceUpdate() throws InterruptedException {
		jse.executeScript("window.scrollBy(0,350)", "");
		commonMethods.highlightElement(buttonDontUpdateInsurance);
		Thread.sleep(2000);
		buttonDontUpdateInsurance.click();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}

	public HomePage skipInsuranceUpdateAnonymous() throws InterruptedException {
		jse.executeScript("window.scrollBy(0,350)", "");
		commonMethods.highlightElement(buttonDontUpdateInsurance);
		Thread.sleep(2000);
		buttonDontUpdateInsurance.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

	public void skipInsuranceUpdateOnHomePage() throws InterruptedException {
		log("don't update Insurance");
		Thread.sleep(2000);
		jse.executeScript("window.scrollBy(0,450)", "");
		commonMethods.highlightElement(buttonDontUpdateInsurance);
		buttonDontUpdateInsurance.click();
	}

	public void selectInsurance(String insuranceName, String memberID, String groupID, String phoneNumber) {
		log("In selectInsurance of UpdateInsurance page.");
		selectInsuranceCarrier.sendKeys(insuranceName, Keys.TAB);
		inputMemberID.clear();
		inputMemberID.sendKeys(memberID);
		inputGroupID.clear();
		inputGroupID.sendKeys(groupID);
		inputInsurancePhone.clear();
		inputInsurancePhone.sendKeys(phoneNumber);
		buttonUpdateInsuranceInfo.click();
	}
}
