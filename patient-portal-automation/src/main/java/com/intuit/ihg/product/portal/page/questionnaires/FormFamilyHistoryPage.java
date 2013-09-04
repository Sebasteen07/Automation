package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormFamilyHistoryPage extends BasePageObject
{

	public FormFamilyHistoryPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	@FindBy(id="idonot_familymedicalhistory")
	WebElement noFamilyHistory;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Family History
	 * @return
	 * @throws Exception
	 */
	public void setNoFamilyHistory() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noFamilyHistory.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public FormSocialHistoryPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormSocialHistoryPage.class);
	}

	/**
	 * @Description:Set Family History Form Fields
	 * @return FormSocialHistoryPage
	 * @throws Exception
	 */
	public FormSocialHistoryPage setFamilyHistoryFormFields() throws Exception
	{
		setNoFamilyHistory();

		clickSaveAndContinueButton();
		return PageFactory.initElements(driver, FormSocialHistoryPage.class);

	}

}
