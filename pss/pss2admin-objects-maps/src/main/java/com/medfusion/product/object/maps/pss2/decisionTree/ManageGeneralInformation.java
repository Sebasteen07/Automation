// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.decisionTree;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

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
	
	@FindBy(how = How.XPATH, using = "//*[@id='appointmentType']")
	private WebElement fillSearchAppointmentType;
	
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
	
	public void setApptTypeDecisionTree(String appointmentType) throws InterruptedException {
		Thread.sleep(5000);
		englishLangGeneralInformation.click();
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
	
	public void publishGeneralInfo() throws InterruptedException {
		javascriptClick(saveGeneralInformationBtn);
		publishGeneralInformation.click();
		backToDecisionTreeBtn.click();
		log("Decision Tree published successfully");
	}
	
}