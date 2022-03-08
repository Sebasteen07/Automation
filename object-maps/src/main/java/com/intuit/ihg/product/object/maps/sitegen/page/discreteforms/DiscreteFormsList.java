//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.AllergiesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.BasicInformationAboutYouPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CurrentSymptomsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.CustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.EmergencyContactInformationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.FormFamilyHistoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.HealthInsuranceInformationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.MedicationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.OtherDoctorsYouSeen;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.PastMedicalHistoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.ProceduresPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.SecondaryHealthInsurancePage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.SocialHistoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.SocialHistoryPage.QuestionType;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.SurgeriesAndHospitalizationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.VaccinationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.WelcomeScreenPage;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.medfusion.common.utils.IHGConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;

public class DiscreteFormsList extends BasePageObject {

	private static final String ALL_FORMS_OPTIONS_XPATH = "//td[contains(@class,'table_options')]";
	private static final String FORM_OPTIONS_XPATH = ALL_FORMS_OPTIONS_XPATH + "[preceding-sibling::td/a/text()='%s']";
	private static final String PUBLISHED_FORM_OPTIONS_SELECTOR = "[a/text()='Unpublish']";
	private static final String UNPUBLISHED_FORM_OPTIONS_SELECTOR = "[a/text()='Publish']";

	@FindBy(xpath = ".//div[@id='modalDialog']//div/button[@class = 'red yes']")
	private WebElement yesDeleteButton;

	@FindBy(xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/" + "a[text() = '" + SitegenConstants.FORMS_CUSTOM_FORM_INITIAL_NAME + "']")
	private WebElement customFormButton;
	
	@FindBy(linkText = SitegenConstants.FORMS_CUSTOM_FORM_INITIAL_NAME)
	private WebElement customFormLink;

	@FindBy(id = "addCalculatedForm")
	private WebElement calculatedFormButton;

	@FindBy(xpath = "//tr[@data-form-type='calculated']/td[@class='table_options last']/" + "a[contains(text(),'Preview')]")
	private WebElement calculatedFormPreview;

	@FindBy(xpath = "//a[contains(text(),'Registration & Health History Form')]")
	private WebElement registrationHealthHistoryFormButton;

	@FindBy(linkText = SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME)
	private WebElement lnkGeneralRegAndHealthHistory;

	@FindBy(xpath = ".//div[@class='admin_inner']//" + "table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a")
	private WebElement lnkAutomationPracticeDiscreteForm;

	@FindBy(name = "custom_form_name")
	private WebElement lnkCustomForm;

	@FindBy(id = "save_config_form")
	private WebElement btnSaveForms;

	@FindBy(id = "importForm")
	private WebElement importBtn;

	@FindBy(id = "importFormData")
	private WebElement importBrowseBtn;

	@FindBy(id = "importButton")
	private WebElement importImportBtn;

	@FindBy(id = "successMessage")
	private WebElement importSuccessMessage;

	@FindBy(id = "errorMessage")
	private WebElement importErrorMessage;

	@FindBy(xpath = "//button[contains(@class,'closeSearchDialog')]")
	private WebElement importCloseBtn;

	@FindBy(xpath = "(//table)[2]/tbody[1]//a[1]")
	private WebElement lastCreatedFormLink; // the most upper in the unpublished
	// table

	private final int waitingSeconds = 30;
	private final int waitingPeriodMS = 500;

	private String welcomeMessage = "Welcome to our wonderful testing form. If you are not an automated test, something is wrong";

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public DiscreteFormsList(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	private WebElement getFormOptions(String discreteFormName) {
		return driver.findElement(By.xpath(String.format(FORM_OPTIONS_XPATH, discreteFormName)));
	}

	private List<WebElement> getPublishedFormsOptions(String formName) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		List<WebElement> opt = driver.findElements(By.xpath(String.format(FORM_OPTIONS_XPATH, formName) + PUBLISHED_FORM_OPTIONS_SELECTOR));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return opt;
	}

	private WebElement getUnpublishedFormsOption(String unpublishedFormName) {
		return driver.findElement(By.xpath(String.format(FORM_OPTIONS_XPATH, unpublishedFormName) + UNPUBLISHED_FORM_OPTIONS_SELECTOR));
	}

	private List<WebElement> getUnpublishedFormsOptions(String formName) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		List<WebElement> opt = driver.findElements(By.xpath(String.format(FORM_OPTIONS_XPATH, formName) + UNPUBLISHED_FORM_OPTIONS_SELECTOR));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return opt;
	}

	public DiscreteFormsList unpublishForms(String formName) throws Exception {
		IHGUtil utils = new IHGUtil(driver);
		IHGUtil.PrintMethodName();
		WebElement unpublishButton;
		List<WebElement> publishedFormsOptions = getPublishedFormsOptions(formName);
		for (WebElement formOption : publishedFormsOptions) {
			unpublishButton = formOption.findElement(By.linkText("Unpublish"));
			unpublishButton.click();
			utils.waitForElementToDisappear(unpublishButton, waitingPeriodMS, waitingSeconds);
		}
		publishedFormsOptions = getPublishedFormsOptions(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME);
		for (WebElement formOption : publishedFormsOptions) {
			unpublishButton = formOption.findElement(By.linkText("Unpublish"));
			unpublishButton.click();
			utils.waitForElementToDisappear(unpublishButton, waitingPeriodMS, waitingSeconds);
		}
		publishedFormsOptions = getPublishedFormsOptions(SitegenConstants.FORMS_CUSTOM_FORM_INITIAL_NAME);
		for (WebElement formOption : publishedFormsOptions) {
			unpublishButton = formOption.findElement(By.linkText("Unpublish"));
			unpublishButton.click();
			utils.waitForElementToDisappear(unpublishButton, waitingPeriodMS, waitingSeconds);
		}
		return this;
	}

	public void deleteUnpublishedForms(String formName) throws Exception {
		IHGUtil utils = new IHGUtil(driver);
		WebElement deleteButton;
		List<WebElement> unpublishedFormsOptions = getUnpublishedFormsOptions(formName);
		for (WebElement formOption : unpublishedFormsOptions) {
			deleteButton = formOption.findElement(By.linkText("Delete"));
			javascriptClick(deleteButton);
			yesDeleteButton.click();
			utils.waitForElementToDisappear(deleteButton, waitingPeriodMS, waitingSeconds);
		}
		unpublishedFormsOptions = getUnpublishedFormsOptions(SitegenConstants.FORMS_REGISTRATION_FORM_INITIAL_NAME);
		for (WebElement formOption : unpublishedFormsOptions) {
			deleteButton = formOption.findElement(By.linkText("Delete"));
			javascriptClick(deleteButton);
			javascriptClick(yesDeleteButton);
			utils.waitForElementToDisappear(deleteButton, waitingPeriodMS, waitingSeconds);
		}
		unpublishedFormsOptions = getUnpublishedFormsOptions(SitegenConstants.FORMS_CUSTOM_FORM_INITIAL_NAME);
		for (WebElement formOption : unpublishedFormsOptions) {
			deleteButton = formOption.findElement(By.linkText("Delete"));
			deleteButton.click();
			yesDeleteButton.click();
			utils.waitForElementToDisappear(deleteButton, waitingPeriodMS, waitingSeconds);
		}
	}

	public CustomFormPage createAndOpenCustomForm(String formName) throws Exception {
		int countOfUnpublishedForms = getCountOfUnpublishedForms();
		customFormButton.click();
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(ALL_FORMS_OPTIONS_XPATH + UNPUBLISHED_FORM_OPTIONS_SELECTOR), countOfUnpublishedForms + 1));
		CustomFormPage customFormPage = clickOnLastCreatedForm(CustomFormPage.class);
		customFormPage.setFormName(formName);
		customFormPage.saveForm();
		return customFormPage;
	}

	public WelcomeScreenPage createAndOpenRegistrationForm(String formName) throws Exception {
		int countOfUnpublishedForms = getCountOfUnpublishedForms();
		javascriptClick(registrationHealthHistoryFormButton);
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(ALL_FORMS_OPTIONS_XPATH + UNPUBLISHED_FORM_OPTIONS_SELECTOR), countOfUnpublishedForms + 1));
		WelcomeScreenPage welcomePage = clickOnLastCreatedForm(WelcomeScreenPage.class);
		welcomePage.setFormName(formName);
		welcomePage.saveOpenedForm();
		return welcomePage;
	}

	private <T> T clickOnLastCreatedForm(Class<T> nextPageClass) {
		javascriptClick(lastCreatedFormLink);
		return PageFactory.initElements(driver, nextPageClass);
	}

	public DiscreteFormsList publishForm(String discreteFormName) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil utils = new IHGUtil(driver);
		WebElement formOptions = getUnpublishedFormsOption(discreteFormName);
		javascriptClick(formOptions.findElement(By.linkText("Publish")));
		utils.waitForElementToDisappear(formOptions, waitingPeriodMS, waitingSeconds);
		return this;
	}

	public void exportForm(String discreteFormName) throws Exception {
		IHGUtil.PrintMethodName();
		log("Deleting previously downloaded file");
		Path exportedFilePath = Paths.get(System.getProperty("user.dir") + "\\" + discreteFormName + ".txt");
		Files.deleteIfExists(exportedFilePath);
		log("Exporting file");
		WebElement formOptions = getFormOptions(discreteFormName);
		formOptions.findElement(By.linkText("Export")).click();
		File downloadedFile = new File(exportedFilePath.toString());
		int tries = 5;
		while (downloadedFile.length() == 0 && tries > 0) {
			TimeUnit.SECONDS.sleep(2);
			tries--;
		}
		if (tries == 0) {
			throw new IllegalStateException("Exported file is empty");
		}
	}
	
	public int getCountOfUnpublishedForms() {
		return driver.findElements(By.xpath(ALL_FORMS_OPTIONS_XPATH + UNPUBLISHED_FORM_OPTIONS_SELECTOR)).size();
	}

	public FormWelcomePage openUnpublishedFormPreview(String unpublishedFormName) throws Exception {
		getUnpublishedFormsOption(unpublishedFormName).findElement(By.linkText("Preview")).click();
		return PageFactory.initElements(driver, FormWelcomePage.class);
	}

	public void importForm(String discreteFormName) throws Exception {
		importBtn.click();
		importBrowseBtn.sendKeys(System.getProperty("user.dir") + "\\" + discreteFormName + ".txt");
		importImportBtn.click();
		try {
		wait.until(ExpectedConditions.textToBePresentInElement(importSuccessMessage, "success"));
		} catch (TimeoutException ex) {
			wait.until(ExpectedConditions.visibilityOf(importErrorMessage));
			throw new IllegalStateException("Form was not imported. Error message from UI: " + importErrorMessage.getText());
		}
		importCloseBtn.click();
		IHGUtil.waitForElement(driver, 10, getFormOptions(discreteFormName));
	}

	public WelcomeScreenPage openDiscreteForm(String formName) throws Exception {
		return openForm(formName, WelcomeScreenPage.class);
	}

	public CustomFormPage openCustomForm(String formName) throws Exception {
		return openForm(formName, CustomFormPage.class);
	}

	private <T> T openForm(String formName, Class<T> nextPageClass) throws InterruptedException {
		IHGUtil.PrintMethodName();
		javascriptClick(driver.findElement(By.xpath("//a[text()='" + formName + "']")));
		SitegenlUtil.switchToNewWindow(driver);
		return PageFactory.initElements(driver, nextPageClass);
	}

	public BasicInformationAboutYouPage clicklnkAutomationPracticeDiscreteForm() throws InterruptedException {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		IHGUtil.waitForElement(driver, 30, lnkAutomationPracticeDiscreteForm);
		lnkAutomationPracticeDiscreteForm.click();

		// Close the browser window
		return PageFactory.initElements(driver, BasicInformationAboutYouPage.class);
	}

	public boolean isPageLoaded() {
		boolean result = false;
		try {
			result = registrationHealthHistoryFormButton.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Basic element of the Discrete Forms page not found page is probably not loaded");
		}
		return result;
	}

	public boolean addCalculatedForm(String calculatedFormName) throws Exception {
		IHGUtil.PrintMethodName();
		System.out.println("CLICK ON CALCULATED FORM BUTTON");
		IHGUtil.waitForElement(driver, 30, calculatedFormButton);
		calculatedFormButton.click();
		CalculatedFormDirectory pCalculatedFormDirectory = new CalculatedFormDirectory(driver);
		boolean foundForm = pCalculatedFormDirectory.isSearchedFormFound(calculatedFormName);
		if (foundForm) {
			pCalculatedFormDirectory.selectFound();
			pCalculatedFormDirectory.addSelected();
		}

		pCalculatedFormDirectory.closeDirectory();
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

		return foundForm;
	}

	public boolean searchCalculatedForm(String calculatedFormName) throws Exception {
		IHGUtil.PrintMethodName();
		System.out.println("CLICK ON CALCULATED FORM BUTTON");
		IHGUtil.waitForElement(driver, 30, calculatedFormButton);
		calculatedFormButton.click();
		CalculatedFormDirectory pCalculatedFormDirectory = new CalculatedFormDirectory(driver);
		boolean foundForm = pCalculatedFormDirectory.isSearchedFormFound(calculatedFormName);
		pCalculatedFormDirectory.closeDirectory();
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return foundForm;
	}

	public void initializePracticeForNewForm(String newFormName) throws Exception {
		// name for the new form
		Thread.sleep(4000);
		unpublishForms(newFormName);
		deleteUnpublishedForms(newFormName);
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

	}

	public void prepareFormForTest(WelcomeScreenPage welcomePage) throws Exception {
		log("Change welcome page text");
		welcomePage.clickWelcomeMessagePage();
		welcomePage.setWelcomeMessage(welcomeMessage);

		log("substep 1: Click on Basic Information About You");
		BasicInformationAboutYouPage basicInfoPage = welcomePage.clicklnkBasicInfoAboutYourPage();
		basicInfoPage.selectBasicInfo();

		log("substep 2: Click on Emergency Contact Information");
		EmergencyContactInformationPage emergencyContactPage = basicInfoPage.clickLnkEmergency();
		emergencyContactPage.selectBasicInfo();

		log("substep 3a: Click on Health Insurance Information");
		HealthInsuranceInformationPage insurancePage = emergencyContactPage.clicklnkInsurance();
		insurancePage.selectInsuranceCompanyQuestion();

		log("substep 3b: Click on Secondary Health Insurance Information");
		SecondaryHealthInsurancePage secondaryInsurancePage = insurancePage.clicklnkSecondaryInsurance();
		secondaryInsurancePage.selectInsuranceCompanyQuestion();

		log("substep 4: Click on Other Doctors You Have Seen");
		OtherDoctorsYouSeen otherDocspage = secondaryInsurancePage.clicklnkOtherDoctors();

		log("substep 5: Click on Current Symptoms");
		CurrentSymptomsPage symptomsPage = otherDocspage.clicklnkCurrentSymptoms();
		symptomsPage.selectBasicSymptoms();

		scrollAndWait(0, 0, 0);
		log("substep 6: Go through the rest of the pages");
		MedicationsPage medicationsPage = symptomsPage.clicklnkMedications();
		AllergiesPage allergiesPage = medicationsPage.clicklnkAllergies();
		VaccinationsPage vaccinationPage = allergiesPage.clicklnkVaccinations();
		SurgeriesAndHospitalizationsPage surgsHospsPage = vaccinationPage.clicklnkSurgsHosps();
		ProceduresPage proceduresPage = surgsHospsPage.clicklnkProcedures();
		PastMedicalHistoryPage conditionsPage = proceduresPage.clicklnkPastMedicalHistory();
		FormFamilyHistoryPage familyMedicalHistoryPage = conditionsPage.clicklnkFamilyHistory();

		log("substep 7: Click on Social History the last page of discrete form");
		SocialHistoryPage socialPage = familyMedicalHistoryPage.clicklnkSocialHistory();
		socialPage.showThisPage();

		log("substep 8: Try to save the form with incomplete question");
		testAddingQuestion(socialPage);

		log("substep 9: Save and close the form");
		socialPage.saveOpenedForm();
		socialPage.clickBackToTheList();
	}

	public void testAddingQuestion(SocialHistoryPage socialPage) throws Exception {
		Thread.sleep(500);
		socialPage.clickAddSection();
		socialPage.clickOnNewSection();
		socialPage.setSectionName("Additional questions");
		socialPage.clickInsertItemButton();
		socialPage.setQuestionName("added question");
		socialPage.saveOpenedForm();
		socialPage.setQuestionType(QuestionType.multiSelect);
		socialPage.saveOpenedForm();
		socialPage.addPossibleAnswer("1 - 2");
		socialPage.addPossibleAnswer("3 or 4");
		socialPage.clickBackToTheList();
		socialPage.clickCloseDialogButton();
	}

	public FormWelcomePage openCalculatedFormPreview() {
		calculatedFormPreview.click();		
		return PageFactory.initElements(driver, FormWelcomePage.class);
	}

	public DiscreteFormsList editFormsWelcomePage(String formName, String newWelcomeMessage) throws Exception {
		WelcomeScreenPage welcomePage = openDiscreteForm(formName);
		welcomePage.setWelcomeMessage(newWelcomeMessage).saveOpenedForm().clickBackToTheList();
		return this;
	}
}
