package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormIllnessConditionsPage extends BasePageObject
{

	public FormIllnessConditionsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_conditions")
	WebElement noConditions;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Conditions
	 * @return
	 * @throws Exception
	 */
	public void setNoConditions() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noConditions.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public FormFamilyHistoryPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormFamilyHistoryPage.class);
	}

	/**
	 * @Description:Set Illness Condition Form Fields
	 * @return FormFamilyHistoryPage
	 * @throws Exception
	 */
	public FormFamilyHistoryPage setIllnessConditionFormFields() throws Exception
	{
		setNoConditions();

		clickSaveAndContinueButton();
		return PageFactory.initElements(driver, FormFamilyHistoryPage.class);


	}
}
