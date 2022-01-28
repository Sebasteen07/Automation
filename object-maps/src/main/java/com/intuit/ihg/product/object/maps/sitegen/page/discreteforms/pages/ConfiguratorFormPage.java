//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.medfusion.common.utils.IHGUtil;

public class ConfiguratorFormPage extends BasePageObject {


	public static final String QUESTION_REQUIRED_INFO_BY_QUESTION_TITLE = "(//label[text()='%s']/preceding-sibling::input)[2]";
	public static final String QUESTION_REQUIRED_ASTERISK_BY_QUESTION_TITLE = "//label[text()='%s']/preceding-sibling::a";
	public static final String QUESTION_CHECKBOX_BY_QUESTION_TITLE = "//label[text()='%s']/preceding-sibling::input[@type='checkbox']";
	public static final String SECTIONLINK_BY_ORDINAL_NUMBER = "//div[contains(@class,'section_list')]/ul[2]/li[%d]";

	@FindBy(className = "back")
	private WebElement backToTheList;

	@FindBy(id = "save_config_form")
	private WebElement saveButton;

	@FindBy(xpath = "//button[@class='green yes backToList']")
	private WebElement dialogSaveButton;

	@FindBy(xpath = "//a[@class='button blue backFloating']")
	private WebElement floatingBackButton;

	@FindBy(id = "save_config_form_floating")
	private WebElement floatingSaveButton;

	@FindBy(xpath = "//button[@class='blue no backToList']")
	private WebElement dialogCloseFormButton;

	@FindBy(xpath = "//a[@class='closeDialog red']")
	private WebElement closeDialogButton;

	@FindBy(id = "loading")
	private WebElement loadingNotification;

	@FindBy(id = "custom_form_name")
	private WebElement formNameInput;

	@FindBy(xpath = "//div[@class='configuration_section socialhistory_section']/p/a")
	private WebElement newSectionButt;

	@FindBy(id = "custom_form_name")
	private WebElement formNameField;

	public ConfiguratorFormPage(WebDriver driver) {
		super(driver);
		jse = (JavascriptExecutor) driver;
	}

	public DiscreteFormsList clickBackToTheList() throws InterruptedException {
		scrollAndWait(0, 0, 5000);
		backToTheList.click();
		return PageFactory.initElements(driver, DiscreteFormsList.class);
	}

	public void clickDialogSaveButton() {
		dialogSaveButton.click();
	}

	public void clickDialogCloseFormButton() {
		dialogCloseFormButton.click();
	}

	public void clickCloseDialogButton() {
		closeDialogButton.click();
	}

	public ConfiguratorFormPage saveOpenedForm() throws InterruptedException {
		IHGUtil utils = new IHGUtil(driver);
		// it's passing here too quickly so we need explicit wait
		Thread.sleep(5000);
		scrollAndWait(0, 0, 500);
		saveButton.click();
		utils.waitForElementToDisappear(loadingNotification, 2000, 10);
		return this;
	}

	public void clickOnNewSection() throws InterruptedException {
	    javascriptClick(newSection);
	}

	// LEFT SIDE MENU CONTENT

	@FindBy(xpath = "//li[@data-section='immunizations']/a")
	private WebElement lnkVaccinations;

	@FindBy(xpath = "//li[@data-section='demographics']/a")
	private WebElement lnkBasicInformationAboutYou;

	@FindBy(xpath = "//li[@data-section='emergencycontact']/a")
	private WebElement lnkEmergencyContactInformation;

	@FindBy(xpath = "//li[@data-section='medications_section']/a")
	private WebElement lnkMedications;

	@FindBy(xpath = "//li[@data-section='insurance']/a")
	private WebElement lnkInsurance;

	@FindBy(xpath = "//li[@data-section='conditions_section']/a")
	private WebElement lnkPastMedicalHistory;

	@FindBy(xpath = "//li[@data-section='socialhistory_section']/a")
	private WebElement lnkSocialHistory;

	@FindBy(xpath = "//li[@data-section='secondary_insurance']/a")
	private WebElement lnkSecondaryInsurance;

	@FindBy(xpath = "//li[@data-section='familymedicalhistory_section']/a")
	private WebElement lnkFamilyHistory;

	@FindBy(xpath = "//li[@data-section='allergies']/a")
	private WebElement lnkAllergies;

	@FindBy(xpath = "//li[@data-section='currentsymptoms']/a")
	private WebElement lnkCurrentSymptoms;

	@FindBy(xpath = "//li[@data-section='currentproviders']/a")
	private WebElement lnkOtherDoctors;

	@FindBy(xpath = "//li[@data-section='procedures_section']/a")
	private WebElement lnkProcedures;

	@FindBy(xpath = "//li[@data-section='surgerieshospitalizations_section']/a")
	private WebElement lnkSurgsHosps;

	@FindBy(xpath = "//li[@data-section='welcome']/a")
	private WebElement lnkWelcome;

	@FindBy(xpath = "//li[@class='additional']/a")
	private WebElement newSection;

	public VaccinationsPage clicklnkVaccinations() throws InterruptedException {
		SitegenlUtil.setDefaultFrame(driver);
		Thread.sleep(500);
		lnkVaccinations.click();
		return PageFactory.initElements(driver, VaccinationsPage.class);
	}

	public BasicInformationAboutYouPage clicklnkBasicInfoAboutYourPage() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkBasicInformationAboutYou);
		lnkBasicInformationAboutYou.click();
		return PageFactory.initElements(driver, BasicInformationAboutYouPage.class);
	}

	public EmergencyContactInformationPage clickLnkEmergency() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		lnkEmergencyContactInformation.click();
		return PageFactory.initElements(driver, EmergencyContactInformationPage.class);
	}

	public MedicationsPage clicklnkMedications() {

		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkMedications);
		lnkMedications.click();

		return PageFactory.initElements(driver, MedicationsPage.class);
	}

	public HealthInsuranceInformationPage clicklnkInsurance() {
		lnkInsurance.click();
		return PageFactory.initElements(driver, HealthInsuranceInformationPage.class);
	}

	public PastMedicalHistoryPage clicklnkPastMedicalHistory() throws InterruptedException {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkPastMedicalHistory);
		scrollAndWait(0, 0, 1000);
		lnkPastMedicalHistory.click();
		return PageFactory.initElements(driver, PastMedicalHistoryPage.class);
	}

	public SocialHistoryPage clicklnkSocialHistory() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkSocialHistory);
		javascriptClick(lnkSocialHistory);
		return PageFactory.initElements(driver, SocialHistoryPage.class);
	}

	public SecondaryHealthInsurancePage clicklnkSecondaryInsurance() {
		lnkSecondaryInsurance.click();
		return PageFactory.initElements(driver, SecondaryHealthInsurancePage.class);
	}

	public FormFamilyHistoryPage clicklnkFamilyHistory() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkFamilyHistory);
		lnkFamilyHistory.click();
		return PageFactory.initElements(driver, FormFamilyHistoryPage.class);
	}

	public AllergiesPage clicklnkAllergies() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkAllergies);
		lnkAllergies.click();
		return PageFactory.initElements(driver, AllergiesPage.class);
	}

	public CurrentSymptomsPage clicklnkCurrentSymptoms() {
		lnkCurrentSymptoms.click();
		return PageFactory.initElements(driver, CurrentSymptomsPage.class);
	}

	public OtherDoctorsYouSeen clicklnkOtherDoctors() {
		lnkOtherDoctors.click();
		return PageFactory.initElements(driver, OtherDoctorsYouSeen.class);
	}

	public ProceduresPage clicklnkProcedures() throws InterruptedException {
		IHGUtil.waitForElement(driver, 30, lnkProcedures);
		lnkProcedures.click();
		return PageFactory.initElements(driver, ProceduresPage.class);
	}

	public SurgeriesAndHospitalizationsPage clicklnkSurgsHosps() {
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkSurgsHosps);
		lnkSurgsHosps.click();
		return PageFactory.initElements(driver, SurgeriesAndHospitalizationsPage.class);
	}
	
	public WelcomeScreenPage clickWelcomeMessagePage() {
		lnkWelcome.click();
		return PageFactory.initElements(driver, WelcomeScreenPage.class);
	}

	public void clickAddSection() throws InterruptedException {
		scrollAndWait(0, 0, 0);
		newSectionButt.click();
		Thread.sleep(500);
	}

	public String getCurrentDisplayedPageTitle() {
		return driver.findElement(By.xpath("//li[contains(@class,'selected')]//em")).getText();
	}

	public boolean isQuestionRequired(String questionLabel) {
		String booleanString = driver.findElement(By.xpath(String.format(QUESTION_REQUIRED_INFO_BY_QUESTION_TITLE, questionLabel))).getAttribute("value");
		if ("1".equals(booleanString))
			return true;
		return false;
	}

	public void clickQuestionRequiredAsterisk(String questionLabel) {
		WebElement asterisk = driver.findElement(By.xpath(String.format(QUESTION_REQUIRED_ASTERISK_BY_QUESTION_TITLE, questionLabel)));
		if(!asterisk.isDisplayed()){
			throw new IllegalStateException("question: " + questionLabel + " is not displayed, currently on page: " + getCurrentDisplayedPageTitle());
		}
		asterisk.click();
	}

	public void makeQuestionOptional(String questionLabel) {
		if (isQuestionRequired(questionLabel))
			clickQuestionRequiredAsterisk(questionLabel);
	}

	public void makeQuestionRequired(String questionLabel) {
		if (!isQuestionRequired(questionLabel))
			clickQuestionRequiredAsterisk(questionLabel);
	}

	public boolean isQuestionInForm(String questionLabel) {
		return driver.findElement(By.xpath(String.format(QUESTION_CHECKBOX_BY_QUESTION_TITLE, questionLabel))).isSelected();
	}

	public void addQuestionToForm(String questionLabel){
		if (!isQuestionInForm(questionLabel)) {
			WebElement questionChckbox = driver.findElement(By.xpath(String.format(QUESTION_CHECKBOX_BY_QUESTION_TITLE, questionLabel)));
			if (!questionChckbox.isDisplayed())
				throw new IllegalStateException("question: " + questionLabel + " is not displayed, currently on page: " + getCurrentDisplayedPageTitle());
			questionChckbox.click();
		}
	}

	public void removeQuestionFromForm(String questionLabel) {
		if (isQuestionInForm(questionLabel)) {
			WebElement questionChckbox = driver.findElement(By.xpath(String.format(QUESTION_CHECKBOX_BY_QUESTION_TITLE, questionLabel)));
			if (!questionChckbox.isDisplayed())
				throw new IllegalStateException("question: " + questionLabel + " is not displayed, currently on page: " + getCurrentDisplayedPageTitle());
			questionChckbox.click();
		}
	}

	public void hideSections(int[] sectionNumbers) throws InterruptedException {
		for (int i : sectionNumbers) {
			scrollAndWait(0, 0, 500);
			goToSection(i);
			hideCurrentSection();
		}
	}

	public void goToSection(int ordinalNumber) {
		driver.findElement(By.xpath(String.format(SECTIONLINK_BY_ORDINAL_NUMBER, ordinalNumber + 1))).click();
	}

	public void hideCurrentSection() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		WebElement hideSectionInput = driver.findElement(By.xpath("//input[contains(@id,'hide_')][./ancestor::div[2][contains(@style,'block')]]"));
		if (!hideSectionInput.isSelected())
			hideSectionInput.click();
	}

	public void setFormName(String newFormName) throws InterruptedException {
		formNameField.clear();
		formNameField.sendKeys(newFormName);
		saveOpenedForm();
	}

}
