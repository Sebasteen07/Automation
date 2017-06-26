package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormMedicationsPage extends PortalFormPage {

	public FormMedicationsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "idonot_medications")
	WebElement noMedications;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath = ".//*[@id='section5']/section/div[2]/a")
	private WebElement btnSubmit;
	

	@FindBy(id = "medications_other_field_autocomplete")
	private WebElement medications;
	
	@FindBy(id = "medications_other_field_frequency")
	private WebElement taken;
	
	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	/**
	 * @Description:Set No Medications
	 * @throws Exception
	 */
	public void setNoMedications() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 20, noMedications);
		if (noMedications.isSelected() == false)
			noMedications.click();
	}

	public FormAllergiesPage setMedicationFormFields_20(String medicine, String input) throws Exception {
		fillMedicationFormFields_new(medicine);
		Settakeninterval(input);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormAllergiesPage.class);

	}
	/**
	 * @Description:Set Medication Form Fields
	 * @return FormAllergiesPage
	 * @throws Exception
	 */
	public FormAllergiesPage setMedicationFormFields() throws Exception {
		setNoMedications();

		return clickSaveContinue(FormAllergiesPage.class);

	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public void fillMedicationFormFields() throws Exception {
		setNoMedications();
		saveAndContinuebtn.click();

		IHGUtil.waitForElement(driver, 10, btnSubmit);
		btnSubmit.click();
		// return PageFactory.initElements(driver, MyPatientPage.class);
	}
	public void fillMedicationFormFields_new(String medicine) throws Exception {
		Thread.sleep(2000);
		medications.clear();
		medications.sendKeys(medicine);
		medications.sendKeys(Keys.TAB);
		Thread.sleep(4000);
		autoComplete.click();
	}
	public void Settakeninterval(String input) throws InterruptedException
	{
		Thread.sleep(1000);
		taken.sendKeys(input);
		Thread.sleep(1000);
		
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Medications"))).isDisplayed();
	}
}
