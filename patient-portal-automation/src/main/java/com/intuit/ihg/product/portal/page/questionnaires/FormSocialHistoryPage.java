package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormSocialHistoryPage extends BasePageObject
{

	public FormSocialHistoryPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath="//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy( xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;
	
	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public void clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
	}

	

	
	/**
	 * @Description:Click on Submit Form Button
	 * @return
	 * @throws Exception
	 */
	public void submitForm() throws Exception
	{
		Thread.sleep(10000);
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(iframe);

		submitForm.click();

	}

	/**
	 * @Description:Set Social History Form Fields
	 * @return FormSocialHistoryPage
	 * @throws Exception
	 */
	public void setSocialHistoryFormFields() throws Exception
	{
		clickSaveAndContinueButton();

		submitForm();
	}

}
