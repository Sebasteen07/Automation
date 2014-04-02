package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormCurrentSymptomsPage extends BasePageObject
{


	public FormCurrentSymptomsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_symptoms_general")
	WebElement noSymptoms;
	
	@FindBy(id="chills_symptom_general")
	WebElement checkChills;
	
	@FindBy(id="insomnia_symptom_general")
	WebElement checkInsomnia;
	
	@FindBy(id="bruising_symptom_blood")
	WebElement checkBruising;
	
	@FindBy(id="earache_sumptom_ent")
	WebElement checkEarache;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Symptoms
	 * @throws Exception
	 */
	public void setNoSymptoms() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noSymptoms.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public FormMedicationsPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormMedicationsPage.class);
	}

	/**
	 * @Description: Set no Current Symptoms Form Fields and continue
	 * @return FormMedicationsPage
	 * @throws Exception
	 */
	public FormMedicationsPage setCurrentSymptomsFormFields() throws Exception
	{
		setNoSymptoms();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormMedicationsPage.class);
	}

	/**
	 * @Description: Set some basic symptom for later PDF test
	 * @throws: Exception
	 */
	public void setBasicSymptoms() throws Exception 
	{
		checkChills.click();
		checkInsomnia.click();
		checkBruising.click();
		checkEarache.click();
	}

}
