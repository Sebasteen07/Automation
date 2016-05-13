package com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.patientportal1.page.questionnaires.PortalFormPage;

import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;

public class FormMedicationsPage extends PortalFormPage 
{


	public FormMedicationsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "idonot_medications")
	WebElement noMedications;


	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;
	
	@FindBy(xpath=".//*[@id='section5']/section/div[2]/a")
	private WebElement btnSubmit;

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
	public MyPatientPage fillMedicationFormFields() throws Exception {
		setNoMedications();
		saveAndContinuebtn.click();
		
		IHGUtil.waitForElement(driver, 10, btnSubmit);
		btnSubmit.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
}
