// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.decisionTree;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class ManageGeneralInformation extends ManageDecisionTree {
	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/ul/li/button[2]")
	private WebElement saveGeneralInformationBtn;

	@FindBy(how = How.XPATH, using = "//a[@title='Back']")
	private WebElement backToDecisionTreeBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='DraftAndPublishModal']/div/div/div[2]/button[3]")
	private WebElement publishGeneralInformation;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[1]/a/span")
	private WebElement englishLangGeneralInformation;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[2]/a/span")
	private WebElement espanolLangGeneralInformation;
	
	@FindBy(how = How.XPATH, using = "//*[@id='DraftAndPublishModal']/div/div/div[2]/button[2]")
	private WebElement saveAsDraftGeneralInformation;
	
	@FindBy(how = How.XPATH, using = "//*[@id='DraftAndPublishModal']/div/div/div[2]/button[1]")
	private WebElement returnForEditingGeneralInformation;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect]/div[1]/div/a")
	private WebElement addNodesBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[2]/div/span")
	private List<WebElement> clickOnEngLangField;
	
	@FindBy(how = How.XPATH, using = "//*[@id='displayNames[EN]']")
	private WebElement fillEngLangField;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[2]/div/span")
	private List<WebElement> clickOnEsLangField;
	
	@FindBy(how = How.XPATH, using = "//*[@id='displayNames[ES]']")
	private WebElement fillEsLangField;
	
	@FindBy(how = How.XPATH, using = "//*[@id='appointmentType']")
	private WebElement fillSearchAppointmentType;
	
	@FindBy(how = How.XPATH, using = "//*[@id='content']/div[2]/ng-component/div/div/section/header/legend/span")
	private WebElement ClickOnCustomizeText;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[2]/div/div[2]/ul/li")
	private WebElement setAppointmentType;
	
	@FindBy(how = How.XPATH, using = "//*[@class='tree-element-main expanded']/div[2]/div")
	private WebElement addReason;
	
	@FindBy(how = How.XPATH, using = "//*[@id='reason']")
	private WebElement fillAddReasonField;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[2]/a[1]")
	private WebElement addApptReason;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[2]/a[2]")
	private WebElement removeApptReason;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[1]/a[1]")
	private WebElement firstBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[1]/a[2]")
	private WebElement secondBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[1]/a[3]")
	private WebElement thirdBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[1]/a[4]")
	private WebElement fourthBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[1]/a[5]")
	private WebElement fifthBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='rect']/div[2]")
	private WebElement textInsideNodes;
	
	@FindBy(how = How.XPATH, using = "//*[@id='removeModal']/div/div/div[2]/button[1]")
	private WebElement cancelDeleteNodesBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='removeModal']/div/div/div[2]/button[2]")
	private WebElement removeNodesBtn;
	
	public ManageGeneralInformation(WebDriver driver) {
		super(driver);
	}
	
	public void addQuestionInDecisionTree(String questionForAppointment) throws InterruptedException {
		IHGUtil.waitForElement(driver, 60, englishLangGeneralInformation);
		englishLangGeneralInformation.click();
		javascriptClick(firstBtn);
		clickOnEngLangField.get(1).click();
		IHGUtil.waitForElement(driver, 60, fillEngLangField);
		fillEngLangField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		fillEngLangField.sendKeys(questionForAppointment);
		log("Question added in Decision Tree");
		ClickOnCustomizeText.click();
	}
	
	public void addQuestionInEsLanguageDecisionTree(String questionForAppointment) throws InterruptedException {
		Thread.sleep(2000);
		espanolLangGeneralInformation.click();
		clickOnEsLangField.get(1).click();
		IHGUtil.waitForElement(driver, 60, fillEsLangField);
		fillEsLangField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		fillEsLangField.sendKeys(questionForAppointment);
		log("Question added in Spanish language");
		ClickOnCustomizeText.click();
	}
	
	public void addAnswerOneInDecisionTree(String decisionTreeAnswer) throws InterruptedException {
		Thread.sleep(2000);
		javascriptClick(thirdBtn);
		clickOnEngLangField.get(2).click();
		IHGUtil.waitForElement(driver, 60, fillEngLangField);
		fillEngLangField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		fillEngLangField.sendKeys(decisionTreeAnswer);
		log("First Answer added in Decision Tree");
		ClickOnCustomizeText.click();
	}
	
	public void addAnswerOneInEsLanguageDecisionTree(String decisionTreeAnswer) throws InterruptedException {
		Thread.sleep(2000);
		clickOnEsLangField.get(2).click();
		IHGUtil.waitForElement(driver, 60, fillEsLangField);
		fillEsLangField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		fillEsLangField.sendKeys(decisionTreeAnswer);
		log("First Answer added in Spanish language");
		ClickOnCustomizeText.click();
	}
	
	public void addAnswerTwoInDecisionTree(String decisionTreeAnswer1) throws InterruptedException {
		Thread.sleep(2000);
		javascriptClick(thirdBtn);
		clickOnEngLangField.get(3).click();
		IHGUtil.waitForElement(driver, 60, fillEngLangField);
		fillEngLangField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		fillEngLangField.sendKeys(decisionTreeAnswer1);
		log("Second Answer added in Decision Tree");
		ClickOnCustomizeText.click();
	}
	
	public void addAnswerTwoInEsLanguageDecisionTree(String decisionTreeAnswer1) throws InterruptedException {
		Thread.sleep(2000);
		clickOnEsLangField.get(3).click();
		IHGUtil.waitForElement(driver, 60, fillEsLangField);
		fillEsLangField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
		fillEsLangField.sendKeys(decisionTreeAnswer1);
		log("Second Answer added in Spanish language");
		ClickOnCustomizeText.click();
	}
	
	public void setApptTypeDecisionTree(String appointmentType) throws InterruptedException {
		Thread.sleep(5000);
		englishLangGeneralInformation.click();
		javascriptClick(fourthBtn);
		fillSearchAppointmentType.sendKeys(appointmentType);
		setAppointmentType.click();
		log("Appointment type added");
	}
	
	public void setApptTypeDecisionTreeSet2(String appointmentType) throws InterruptedException {
		javascriptClick(fourthBtn);
		fillSearchAppointmentType.sendKeys(appointmentType);
		setAppointmentType.click();
		log("Appointment type added");
	}
	
	public void addReasonGeneralInfo(String reasonForAppointment) {
		javascriptClick(addReason);
		fillAddReasonField.sendKeys(reasonForAppointment);
		addApptReason.click();
		log("Decision Tree published successfully");
	}
	
	public void setApptTypeForAnswerTwoInDecisionTreeSet(String appointmentType1) throws InterruptedException {
		javascriptClick(fourthBtn);
		fillSearchAppointmentType.sendKeys(appointmentType1);
		setAppointmentType.click();
		log("Appointment type added for second answer");
	}
	
	public void addReasonForAnswerTwo(String reasonForAppointment1) {
		javascriptClick(addReason);
		fillAddReasonField.sendKeys(reasonForAppointment1);
		addApptReason.click();
		log("Reason for second Answer added successfully");
	}
	
	public void publishGeneralInfo() throws InterruptedException {
		javascriptClick(saveGeneralInformationBtn);
		IHGUtil.waitForElement(driver, 60, publishGeneralInformation);
		publishGeneralInformation.click();
		log("Decision Tree published successfully");
	}
	
	public void saveAsDraftGeneralInfo() {
		javascriptClick(saveGeneralInformationBtn);
		IHGUtil.waitForElement(driver, 60, saveAsDraftGeneralInformation);
		saveAsDraftGeneralInformation.click();
		log("Decision Tree saved successfully");
	}
	
}