// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.CancResc;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class PatientIdentificationPage extends PSS2MainPage {
	
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Patient information']")
	private WebElement headingPI;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Cancel']")
	private WebElement cancelBtn;
	
	@FindBy(how = How.XPATH, using = "//input[@id='FN']")
	private WebElement inputFirstName;
	
	@FindBy(how = How.XPATH, using = "//input[@id='LN']")
	private WebElement inputLastName;
	
	@FindBy(how = How.XPATH, using = "//div[@id='g-recaptcha']//div//div//iframe[1]")
	private WebElement recaptchaFrame;

	@FindBy(how = How.XPATH, using = "//div[@id='rc-anchor-container']")
	private WebElement recaptchaBox;

	@FindBy(how = How.XPATH, using = "//span[@class='recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked rc-anchor-checkbox']")
	private WebElement recaptchaClick;
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Submit')]")
	private WebElement buttonNext;
	
	@FindBy(how = How.XPATH, using = "//div[@id='myModalsss']//button[@class='dismissbuttons']")
	private WebElement dismissButtons;
	
	@FindBy(how = How.XPATH, using = "//div[@id='myModalsss']/div/div/div[3]/button/span")
	private WebElement dismissButtonsAnonymous;
	
	
	public PatientIdentificationPage(WebDriver driver) {
		super(driver);

	}
	
	public PatientIdentificationPage(WebDriver driver, String url) {
		super(driver, url);

	}
	
	public Boolean isPopUP() {
		if (dismissButtons.isDisplayed() == true) {
			return true;
		}
		return false;
	}

	public void popUPClick() {
		dismissButtons.click();
	}
	
	public Boolean isPopUPAn() {
		if (dismissButtonsAnonymous.isDisplayed() == true) {
			return true;
		}
		return false;
	}

	public void popUPAnClick() {
		dismissButtonsAnonymous.click();
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		
		ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
		webElementList.add(headingPI);
		webElementList.add(cancelBtn);
		webElementList.add(buttonNext);
		
		return new IHGUtil(driver).assessAllPageElements(webElementList, getClass());
	}
	
	CommonMethods commonMethods = new CommonMethods(driver);
	
	public CancelRescheduleDecisionPage fillPatientForm(String firstName, String lastName) throws InterruptedException {

		inputFirstName.sendKeys(firstName);
		log("firstName= " + firstName);
		inputLastName.sendKeys(lastName);
		log("lastName= " + lastName);
		
		driver.switchTo().frame(recaptchaFrame);
		commonMethods.highlightElement(recaptchaBox);
		recaptchaClick.click();
		Thread.sleep(3000);
		driver.switchTo().parentFrame();

		log("........Captcha clicked......");

		wait.until(ExpectedConditions.elementToBeClickable(buttonNext));
		commonMethods.highlightElement(buttonNext);
		buttonNext.click();
		log("Submit Button cliked ...");
		return PageFactory.initElements(driver, CancelRescheduleDecisionPage.class);
	}

}
