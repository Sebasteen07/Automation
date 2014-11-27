package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import static org.testng.AssertJUnit.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.SocialHistoryPage.QuestionType;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
import com.intuit.ihg.common.utils.IHGConstants;

	/**
	 * 
	 * @author bbinisha
	 * @ Date : 11/12/2013
	 */

public class DiscreteFormsPage extends BasePageObject{

	@FindBy ( xpath = ".//div[@id='modalDialog']//div/button[@class = 'red yes']")
	private WebElement yesDeleteButton;

	@FindBy ( xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/a[text() = 'Custom Form']")
	private WebElement customFormButton;

	/*@FindBy ( xpath = ".//div[@class = 'admin_inner']/div[@class ='new_discrete_form']/a[@class ='button blue']")
	private WebElement registrationHealthHistoryFormButton;*/
	
	@FindBy(xpath = "//a[contains(text(),'Registration & Health History Form')]")
	private WebElement registrationHealthHistoryFormButton;
	
	@FindBy(linkText="General Registration and Health History") 
	private WebElement lnkGeneralRegAndHealthHistory;
	
	@FindBy(xpath=".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td[@class='first']/a") 
	private WebElement lnkAutomationPracticeDiscreteForm;
	
	@FindBy(name="custom_form_name") 
	private WebElement lnkCustomForm;
	
	@FindBy(id="save_config_form") 
	private WebElement btnSaveForms;
	
	private int waitingPeriodSeconds = 10;
	
	private String welcomeMessage = "Welcome to our wonderful testing form. If you are not an automated test, something is wrong";
	
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	//Constructor
	public DiscreteFormsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * @param xpath - xpath used to find the forms in forms config
	 * @param count - number of the items found by the xpath
	 * @throws InterruptedException
	 */
	public void waitForFormToDissappear(String xpath, int count) throws InterruptedException {
		float timePassed = 0;
		
		while (driver.findElements(By.xpath(xpath)).size() == count) {
			Thread.sleep(500);
			timePassed += 0.5;
			if (timePassed > waitingPeriodSeconds) {
				fail("Waiting for form to be deleted is taking too long");
			}
		}
	}
	
	/**
	 * Description: Deletes all the unpublished forms present in the Discrete Forms page.
	 * @throws Exception
	 */
	public void deleteAllUnPublishedForms() throws Exception {
		List<WebElement> deleteButtons;
		IHGUtil.PrintMethodName();
		String xpath = ".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td/a[@class='delete']";	
		int count = driver.findElements(By.xpath(xpath)).size();
		log("Number of UnPublished rows is :" + count);
		
		while (count > 0) {
			deleteButtons = driver.findElements(By.xpath(xpath));
			deleteButtons.get(0).click();
			yesDeleteButton.click();
			waitForFormToDissappear(xpath, count);
			count--;
		}
	}

	/**
	 * Description: Unpublishes all the published forms present in the Discrete Forms page.
	 * @throws Exception
	 */
	public void unpublishAllForms() throws Exception {
		List<WebElement> unpublishButtonList; 
		
		IHGUtil.PrintMethodName();
		String xpath = ".//div[@class='admin_inner']//table[@class = 'tablesorter tablesorter-default' ]/tbody/tr/td/a[@class='unpublish']";	
		int count = driver.findElements(By.xpath(xpath)).size();
		log("Number of Published rows is :" + count);
		
		while (count > 0) {		
			 unpublishButtonList = driver.findElements(By.xpath(xpath));
            unpublishButtonList.get(0).click();	
			waitForFormToDissappear(xpath, count);
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
		Thread.sleep(5000);
	}

	
	
	/**
	 *  Description : Rename the created discrete form
	 * @throws Exception
	 */
	public void renameDiscreteForm(String uniqueDiscreteFormName) throws Exception {
		IHGUtil.PrintMethodName();
	
		lnkGeneralRegAndHealthHistory.click();
		Thread.sleep(1000);
		
		lnkCustomForm.clear();
		//Thread.sleep(5000);
		
		lnkCustomForm.sendKeys(uniqueDiscreteFormName);
		//Thread.sleep(5000);
		btnSaveForms.click();	
	}
	
	
	/**
	 * Description : Publish the Saved Form.
	 * @param formName : Form name of the form which needs to be deleted.
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
	 * @param discrete True for making discrete form, false to create custom 
	 * @return Name of the newly created form
	 */
	public void initializePracticeForNewForm() throws Exception {
		// name for the new form
	
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		unpublishAllForms();
		deleteAllUnPublishedForms();
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		createNewDiscreteForm();
	}
	
	public void prepareFormForTest(String newFormName) throws Exception {
		log("Open form and change welcome page text");
		WelcomeScreenPage pWelcomeScreenPage = openDiscreteForm("General Registration and Health History");
		pWelcomeScreenPage.clickWelcomeMessagePage();
		pWelcomeScreenPage.setWelcomeMessage(welcomeMessage);
		log("Rename the form");
		pWelcomeScreenPage.setFormName(newFormName);
		
		log("substep 1: Click on Basic Information About You");
		BasicInformationAboutYouPage pBasicInfoAboutYou = pWelcomeScreenPage.clickLnkBasicInfoAboutYou();
		log("select some basic questions to appear in the form");
		pBasicInfoAboutYou.selectBasicInfo();
		
		log("substep 2: Click on Emergency Contact Information");
		EmergencyContactInformationPage pEmergencyContactInfoPage = pBasicInfoAboutYou.clickLnkEmergency();
		pEmergencyContactInfoPage.selectBasicInfo();
		
		log("substep 3a: Click on Health Insurance Information");
		HealthInsuranceInformationPage pHealthInsuranceInfoPage = pEmergencyContactInfoPage.clicklnkInsurance();
		pHealthInsuranceInfoPage.selectInsuranceCompanyQuestion();
		
		log("substep 3b: Click on Secondary Health Insurance Information");	
		SecondaryHealthInsurancePage pSecondaryHealthInsurancePage = pHealthInsuranceInfoPage.clicklnkSecondaryInsurance() ;
		pSecondaryHealthInsurancePage.selectInsuranceCompanyQuestion();
		
		log("substep 4: Click on Other Doctors You Have Seen");
		OtherDoctorsYouSeen pOtherDoctorsYouSeen = pSecondaryHealthInsurancePage.clicklnkOtherDoctors();
		
		log("substep 5: Click on Current Symptoms");
		CurrentSymptomsPage pCurrentSymptomsPage = pOtherDoctorsYouSeen.clicklnkCurrentSymptoms();
		pCurrentSymptomsPage.selectBasicSymptoms();
		
		log("substep 6: Click on Medications link");
		MedicationsPage pMedicationPage = pCurrentSymptomsPage.clicklnkMedications();
	
		log("substep 7: Click on Allergies link");
		Allergiespage pAllergiesPage = pMedicationPage.clicklnkAllergies();
				
		log("substep 8: Click on Vaccinations");
		VaccinationsPage pVaccinationPage = pAllergiesPage.clicklnkVaccinations();
				
		log("substep 9: Click on SurgeriesAndHospitalizationsPage");
		SurgeriesAndHospitalizationsPage pSurgeriesAndHospitalizationsPage = pVaccinationPage.clicklnkSurgsHosps();
		
		log("substep 10: Click on Exam Test and  Procedures");
		ExamsTestsAndProceduresPage pExamsTestsAndProceduresPage = pSurgeriesAndHospitalizationsPage.clicklnkProcedures();
		
		log("substep 11: Click on Illness and Conditions");
		IllnessesAndConditionsPage pIllnessesAndConditionsPage = pExamsTestsAndProceduresPage.clicklnkConditions();
		
		log("substep 12: Click on Family Medical History");
		FormFamilyHistoryPage pFamilyMedicalHistoryPage = pIllnessesAndConditionsPage.clicklnkFamilyHistory();
		
		log("substep 13: Click on Social History the last page of discrete form");
		SocialHistoryPage socialPage = pFamilyMedicalHistoryPage.clicklnkSocialHistory();
		socialPage.showThisPage();
		
		log("substep 14: Try to save the form with uncomplete question");
		testAddingQuestion(socialPage);
		
		socialPage.clickSave();
	}

	public void testAddingQuestion(SocialHistoryPage socialPage) throws Exception {
		socialPage.clickAddSection();
		socialPage.clickOnNewSection();
		socialPage.setSectionName("Additional questions");
		socialPage.clickInsertItemButton();
		socialPage.setQuestionName("added question");
		socialPage.clickSave();
		socialPage.errorMessageAppearedTest();
		socialPage.setQuestionType(QuestionType.multiSelect);
		socialPage.clickSave();
		socialPage.errorMessageAppearedTest();
		socialPage.setMultiSelectAnswers("1, 2, 3, or 4");
	}
	
}
