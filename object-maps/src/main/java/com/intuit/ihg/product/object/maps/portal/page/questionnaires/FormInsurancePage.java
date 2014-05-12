package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormInsurancePage extends BasePageObject 
{


	public FormInsurancePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_primary_insurance")
	private WebElement selfPay;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;


	/**
	 * @Description:Set Self Pay
	 * @throws Exception
	 */

	public void setSelfPay() throws Exception 
	{
		Thread.sleep(2000);
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		selfPay.click();

	}

	/**
	 * @Description:Click on Save and Continue Button
	 * @return FormOtherProvidersPage, if not selected Self pay retuns FormSecondaryInsurancePage
	 * @throws Exception
	 */
	public BasePageObject clickSaveAndContinueButton(boolean selfPayTrue) throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		if(selfPayTrue)
			return PageFactory.initElements(driver, FormOtherProvidersPage.class);
		else
			return PageFactory.initElements(driver, FormSecondaryInsurancePage.class);
	}

	/**
	 * @Description:Set no insurance (self pay)
	 * @return FormOtherProvidersPage
	 * @throws Exception
	 */
	public FormOtherProvidersPage setSelfPayInsurance() throws Exception
	{
		setSelfPay();

		clickSaveAndContinueButton(true);

		return PageFactory.initElements(driver, FormOtherProvidersPage.class);

	}

	

}
