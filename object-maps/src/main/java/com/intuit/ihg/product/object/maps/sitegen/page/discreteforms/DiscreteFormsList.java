package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;


import java.util.List;
import java.util.concurrent.TimeUnit;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.SocialHistoryPage.QuestionType;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.intuit.ihg.common.utils.IHGConstants;

	/**
	 * 
	 * @author bbinisha
	 * @ Date : 11/12/2013
	 */

public class DiscreteFormsList extends BasePageObject {

	@FindBy(xpath = ".//div[@id='modalDialog']//div/button[@class = 'red yes']")
	private WebElement yesDeleteButton;

	@FindBy(xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/" +
            "a[text() = 'Custom Form']")
	private WebElement customFormButton;
	
	@FindBy(id = "addCalculatedForm")
	private WebElement calculatedFormButton;

    @FindBy(xpath = "//tr[@data-form-type='calculated']/td[@class='table_options last']/" +
            "a[contains(text(),'Preview')]")
    private WebElement calculatedFormPreview;

	@FindBy(xpath = "//a[contains(text(),'Registration & Health History Form')]")
	private WebElement registrationHealthHistoryFormButton;
	
	@FindBy(linkText = "General Registration and Health History")
	private WebElement lnkGeneralRegAndHealthHistory;
	
	@FindBy(xpath = ".//div[@class='admin_inner']//" +
            "table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a")
	private WebElement lnkAutomationPracticeDiscreteForm;
	
	@FindBy(name = "custom_form_name")
	private WebElement lnkCustomForm;
	
	@FindBy(id="save_config_form") 
	private WebElement btnSaveForms;
	
	private final int waitingPeriodSeconds = 8;
	
	private String welcomeMessage = "Welcome to our wonderful testing form. If you are not an automated test, something is wrong";
	
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	//Constructor
	public DiscreteFormsList(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Description: Deletes all the unpublished forms present in the Discrete Forms page.
	 * @throws Exception
	 */
	public void deleteAllUnPublishedForms() throws Exception {
		List<WebElement> deleteButtons;
        IHGUtil utils = new IHGUtil(driver);

		IHGUtil.PrintMethodName();
		String xpath = ".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td/a[@class='delete']";
		int count = driver.findElements(By.xpath(xpath)).size();
		log("Number of UnPublished rows is :" + count);
		
		while (count > 0) {
			deleteButtons = driver.findElements(By.xpath(xpath));
			deleteButtons.get(0).click();
			yesDeleteButton.click();
//			waitForFormToDisappear(xpath, count);
            utils.waitForElementToDisappear(deleteButtons.get(count - 1), 1000, waitingPeriodSeconds);
			count--;
		}
	}

	/**
	 * Description: Unpublishes all the published forms present in the Discrete Forms page.
	 * @throws Exception
	 */
	public void unpublishAllForms() throws Exception {
		List<WebElement> unpublishButtonList;
        IHGUtil utils = new IHGUtil(driver);
		
		IHGUtil.PrintMethodName();
		String xpath = ".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td/a[@class='unpublish']";	
		int count = driver.findElements(By.xpath(xpath)).size();
		log("Number of Published rows is :" + count);
		
		while (count > 0) {		
			unpublishButtonList = driver.findElements(By.xpath(xpath));
            unpublishButtonList.get(0).click();
            utils.waitForElementToDisappear(unpublishButtonList.get(count-1), 1000, waitingPeriodSeconds);
			count--;
		}
	}

	/**
	 * Description : Creates new custom form
	 * @throws Exception
	 */
	public void createANewCustomForm () throws Exception {
		IHGUtil.PrintMethodName();
		customFormButton.click();
		Thread.sleep(5000);
	}

	/**
	 * Description : Open the newly created custom form.
	 * @return
	 * @throws Exception
	 */
	public CustomFormPage openCustomForm() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a")).click();
		Thread.sleep(5000);
		SitegenlUtil.switchToNewWindow(driver);
		return PageFactory.initElements(driver, CustomFormPage.class);
	}

	/**
	 *  Description : Creates new discrete form
	 * @throws Exception
	 */
	public void createNewDiscreteForm() throws Exception {
		IHGUtil.PrintMethodName();
		System.out.println("CLICK ON DISCRETE FORM");
		registrationHealthHistoryFormButton.click();

	}

	/**
	 * Description : Publish the Saved Form.
	 * @param uniqueDiscreteFormName : Form name of the form which needs to be deleted.
	 * @throws Exception
	 */
	public void publishTheSavedForm(String uniqueDiscreteFormName) throws Exception {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath(".//a[text()='"+uniqueDiscreteFormName+"']/ancestor::td/following-sibling::td/a[@class='publish']")).click();
		Thread.sleep(3000);
	}
	
	
	/**
	 * Description : Open the newly created discrete Form.
	 * @return
	 * @throws Exception
	 */
	public WelcomeScreenPage openDiscreteForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(1000);
		// Find the form by name
		driver.findElement(By.xpath("//a[contains(text(), '" + formName + "')]")).click();
		Thread.sleep(1000);
		SitegenlUtil.switchToNewWindow(driver);
		return PageFactory.initElements(driver, WelcomeScreenPage.class);
	}
	
	/**
	 * Click on link - General Registration and Health History	
	 * @return
	 * @throws InterruptedException 
	 */
	
	public BasicInformationAboutYouPage clicklnkAutomationPracticeDiscreteForm() throws InterruptedException {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		
		IHGUtil.waitForElement(driver, 30, lnkAutomationPracticeDiscreteForm);
		lnkAutomationPracticeDiscreteForm.click();
			
		// Close the browser window
		return PageFactory.initElements(driver,BasicInformationAboutYouPage.class);
	}
	
	/**
	 * Checks if the Patient Forms page is loaded by checking if crucial element of it is present
	 * @return True if the button is present, false otherwise
	 */
	public boolean isPageLoaded() {
		boolean result = false;
		try {
			result = registrationHealthHistoryFormButton.isDisplayed();
		} catch (NoSuchElementException e){
			log("Basic element of the Discrete Forms page not found page is probably not loaded");
		}
		return result;
	}
	
	/**
	 * Prepares practice for automated test - unpublishes and deletes all forms and creates a new one
	 * @return Name of the newly created form
	 */
	
	public void createNewForm() throws Exception {
		createNewDiscreteForm();
        try {
            driver.findElement(By.xpath("//a[contains(text(), 'General Registration and Health History')]"));
        } catch (NoSuchElementException e) {
            log("Form not visible after its creation! It's needed to reload the page. This is because of a defect!!!");
            driver.navigate().refresh();
            driver.findElement(By.xpath("//a[contains(text(), 'General Registration and Health History')]"));
        }
        log("A new form successfully created");
	}
	
	public boolean addCalculatedForm(String calculatedFormName) throws Exception {
		IHGUtil.PrintMethodName();
		System.out.println("CLICK ON CALCULATED FORM BUTTON");
		IHGUtil.waitForElement(driver, 30, calculatedFormButton);
		calculatedFormButton.click();
		CalculatedFormDirectory pCalculatedFormDirectory = new CalculatedFormDirectory(driver);
		boolean foundForm=pCalculatedFormDirectory.searchForm(calculatedFormName);
		if (foundForm)
		{
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
		boolean foundForm=pCalculatedFormDirectory.searchForm(calculatedFormName);
		pCalculatedFormDirectory.closeDirectory();
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return foundForm;
	}
	
	public void initializePracticeForNewForm() throws Exception {
		// name for the new form
	
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		unpublishAllForms();
		deleteAllUnPublishedForms();
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

	}
	
	public void prepareFormForTest(String newFormName) throws Exception {
		log("Open form and change welcome page text");
		WelcomeScreenPage welcomePage = openDiscreteForm("General Registration and Health History");
		welcomePage.clickWelcomeMessagePage();
		welcomePage.setWelcomeMessage(welcomeMessage);
		log("Rename the form");
		welcomePage.setFormName(newFormName);
		
		log("substep 1: Click on Basic Information About You");
		BasicInformationAboutYouPage basicInfoPage = welcomePage.clickLnkBasicInfoAboutYou();
		basicInfoPage.selectBasicInfo();

		log("substep 2: Click on Emergency Contact Information");
		EmergencyContactInformationPage emergencyContactPage = basicInfoPage.clickLnkEmergency();
		emergencyContactPage.selectBasicInfo();

		log("substep 3a: Click on Health Insurance Information");
		HealthInsuranceInformationPage insurancePage = emergencyContactPage.clicklnkInsurance();
		insurancePage.selectInsuranceCompanyQuestion();

		log("substep 3b: Click on Secondary Health Insurance Information");
		SecondaryHealthInsurancePage secondaryInsurancePage =
                insurancePage.clicklnkSecondaryInsurance();
		secondaryInsurancePage.selectInsuranceCompanyQuestion();
		
		log("substep 4: Click on Other Doctors You Have Seen");
		OtherDoctorsYouSeen otherDocspage = secondaryInsurancePage.clicklnkOtherDoctors();
		
		log("substep 5: Click on Current Symptoms");
		CurrentSymptomsPage symptomsPage = otherDocspage.clicklnkCurrentSymptoms();
		symptomsPage.selectBasicSymptoms();

        log("substep 6: Go through the rest of the pages");
		MedicationsPage medicationsPage = symptomsPage.clicklnkMedications();
		AllergiesPage allergiesPage = medicationsPage.clicklnkAllergies();
		VaccinationsPage vaccinationPage = allergiesPage.clicklnkVaccinations();
		SurgeriesAndHospitalizationsPage surgsHospsPage = vaccinationPage.clicklnkSurgsHosps();
		ExamsTestsAndProceduresPage proceduresPage = surgsHospsPage.clicklnkProcedures();
		IllnessesAndConditionsPage conditionsPage = proceduresPage.clicklnkConditions();
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
		socialPage.clickAddSection();
		socialPage.clickOnNewSection();
		socialPage.setSectionName("Additional questions");
		socialPage.clickInsertItemButton();
		socialPage.setQuestionName("added question");
		socialPage.clickSaveButton();
//		socialPage.errorMessageAppearedTest();
		socialPage.setQuestionType(QuestionType.multiSelect);
		socialPage.clickSaveButton();
//		socialPage.errorMessageAppearedTest();
		socialPage.setMultiSelectAnswers("1, 2, 3, or 4");
        socialPage.clickBackToTheList();
        socialPage.clickCloseDialogButton();
	}

    public FormWelcomePage openCalculatedFormPreview() {
        calculatedFormPreview.click();
        return PageFactory.initElements(driver, FormWelcomePage.class);
    }
}
