package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormAllergiesPage extends BasePageObject
{


	public FormAllergiesPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_drug_allergies")
	WebElement noDrugAllergies;

	@FindBy(id="idonot_food_allergies")
	WebElement noFoodAllergies;

	@FindBy(id="idonot_environmental_allergies")
	WebElement noEnvironmentalAllergies;



	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Drug Allergies
	 * @throws Exception
	 */
	public void setNoDrugAllergies() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noDrugAllergies.click();

	}

	/**
	 * @Description:Set No Food Allergies
	 * @throws Exception
	 */
	public void setNoFoodAllergies() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noFoodAllergies.click();

	}

	/**
	 * @Description:Set No Environment Allergies
	 * @throws Exception
	 */
	public void setNoEnvironmentalAllergies() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noEnvironmentalAllergies.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */	
	public FormVaccinePage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormVaccinePage.class);
	}


	/**
	 * @Description:Set Allergies Form Fields
	 * @return FormVaccinePage
	 * @throws Exception
	 */
	public FormVaccinePage setAllergiesFormFields() throws Exception
	{
		setNoDrugAllergies();

		setNoFoodAllergies();

		setNoEnvironmentalAllergies();

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormVaccinePage.class);

	}

}
