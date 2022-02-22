// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;
import com.medfusion.product.object.maps.pss2.page.util.DateMatcher;

public class AnonymousPatientInformation extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//img[@class='logoforheader']")
	private WebElement logoAthena;

	@FindBy(how = How.XPATH, using = "//h1[@class='h3color']//span[contains(text(),'Patient information')]")
	private WebElement pageHeading;

	@FindBy(how = How.XPATH, using = "//input[@id='FN']")
	private WebElement patientFatherName;

	@FindBy(how = How.XPATH, using = "//input[@id='LN']")
	private WebElement patientMotherName;

	@FindBy(how = How.XPATH, using = "//span[@class='glyphicon glyphicon-calendar']")
	private WebElement datePicker;

	@FindBy(how = How.XPATH, using = "//select[@id='GENDER']")
	private WebElement selectGender;

	@FindBy(how = How.ID, using = "PHONE")
	private WebElement perferredPhoneNumber;

	@FindBy(how = How.ID, using = "EMAIL")
	private WebElement inputEmail;

	@FindBy(how = How.ID, using = "submitAnonymous")
	private WebElement submitBtn;

	@FindBy(how = How.XPATH, using = "//div[@id='g-recaptcha']//div//div//iframe[1]")
	private WebElement recaptchaFrame;

	@FindBy(how = How.XPATH, using = "//div[@id='rc-anchor-container']")
	private WebElement recaptchaBox;

	@FindBy(how = How.XPATH, using = "//span[@class='recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked rc-anchor-checkbox']")
	private WebElement recaptchaClick;

	@FindBy(how = How.XPATH, using = "//div[@class='acceptpolicy']//label")
	private WebElement privacyPolicyCheckbox;

	@FindBy(how = How.XPATH, using = "//*[@class='modal fade in']/div/div/div[2]/p/pre/div[1]")
	private WebElement multiplePatientMsg;

	public AnonymousPatientInformation(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public ConfirmationPage fillPatientFormWithoutPrivacyPolicy(String firstName, String lastName, String dob, String email, String gender, String phoneNumber)
			throws InterruptedException {
		log("phoneNumber= " + phoneNumber);
		commonMethods.highlightElement(patientFatherName);
		patientFatherName.sendKeys(firstName);
		commonMethods.highlightElement(patientMotherName);
		patientMotherName.sendKeys(lastName);
		commonMethods.highlightElement(datePicker);
		datePicker.click();
		DateMatcher dateMatcher = new DateMatcher();
		dateMatcher.selectDate(dob, driver);
		commonMethods.highlightElement(selectGender);
		selectGender.click();
		Select selectGenderType = new Select(selectGender);
		selectGenderType.selectByValue(gender);
		commonMethods.highlightElement(perferredPhoneNumber);
		perferredPhoneNumber.sendKeys(phoneNumber);
		commonMethods.highlightElement(inputEmail);
		inputEmail.sendKeys(email);
		jse.executeScript("window.scrollBy(0,350)", "");
		Thread.sleep(2000);
		driver.switchTo().frame(recaptchaFrame);
		commonMethods.highlightElement(recaptchaBox);
		recaptchaClick.click();
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		log("formfilled ...");
		Thread.sleep(4000);
		commonMethods.highlightElement(submitBtn);
		submitBtn.click();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}


	public ConfirmationPage fillPatientFormWithPrivacyPolicy(String firstName, String lastName, String dob, String email, String gender, String phoneNumber)
			throws InterruptedException {
		log("phoneNumber= " + phoneNumber);
		commonMethods.highlightElement(patientFatherName);
		patientFatherName.sendKeys(firstName);
		commonMethods.highlightElement(patientMotherName);
		patientMotherName.sendKeys(lastName);
		commonMethods.highlightElement(datePicker);
		datePicker.click();
		DateMatcher dateMatcher = new DateMatcher();
		dateMatcher.selectDate(dob, driver);
		commonMethods.highlightElement(selectGender);
		selectGender.click();
		Select selectGenderType = new Select(selectGender);
		selectGenderType.selectByValue(gender);
		commonMethods.highlightElement(perferredPhoneNumber);
		perferredPhoneNumber.sendKeys(phoneNumber);
		commonMethods.highlightElement(inputEmail);
		inputEmail.sendKeys(email);
		commonMethods.highlightElement(privacyPolicyCheckbox);
		log("formfilled ...");
		privacyPolicyCheckbox.click();
		log("Privacy Policy has been checked successfully");
		jse.executeScript("window.scrollBy(0,350)", "");
		Thread.sleep(2000);
		driver.switchTo().frame(recaptchaFrame);
		commonMethods.highlightElement(recaptchaBox);
		recaptchaClick.click();
		Thread.sleep(3000);
		driver.switchTo().parentFrame();
		log("formfilled ...");
		Thread.sleep(4000);
		commonMethods.highlightElement(submitBtn);
		submitBtn.click();
		return PageFactory.initElements(driver, ConfirmationPage.class);
	}

	public String multiplePatientMessage() {
		String multiplePatientMessage = multiplePatientMsg.getText();
		return multiplePatientMessage;
	}
}
