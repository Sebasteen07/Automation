package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormMedicationsPage extends BasePageObject
{


	public FormMedicationsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "idonot_medications")
	WebElement noMedications;


	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Medications
	 * @throws Exception
	 */
	public void setNoMedications() throws Exception {
		if (noMedications.isSelected() == false)
			noMedications.click();
	}

	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends BasePageObject> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, nextPageClass);
	}

	/**
	 * @Description:Set Medication Form Fields
	 * @return FormAllergiesPage
	 * @throws Exception
	 */
	public FormAllergiesPage setMedicationFormFields() throws Exception {
		setNoMedications();

		return clickSaveAndContinueButton(FormAllergiesPage.class);

	}

}
