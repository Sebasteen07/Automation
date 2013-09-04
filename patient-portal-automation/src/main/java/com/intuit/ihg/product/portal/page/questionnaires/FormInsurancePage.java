package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormInsurancePage extends BasePageObject 
{


	public FormInsurancePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_insurance")
	WebElement selfPay;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;


	/**
	 * @Description:Set Self Pay
	 * @throws Exception
	 */

	public void setSelfPay() throws Exception 
	{
		Thread.sleep(20000);
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		selfPay.click();

	}

	/**
	 * @Description:Click on Save and Continue Button
	 * @return FormOtherProvidersPage
	 * @throws Exception
	 */
	public FormOtherProvidersPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormOtherProvidersPage.class);
	}

	/**
	 * @Description:Set Insurance Form Fields
	 * @return FormOtherProvidersPage
	 * @throws Exception
	 */
	public FormOtherProvidersPage setInsuranceFormFields() throws Exception
	{
		setSelfPay();

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormOtherProvidersPage.class);


	}


}
