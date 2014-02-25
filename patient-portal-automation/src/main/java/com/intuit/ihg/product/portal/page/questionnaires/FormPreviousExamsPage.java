package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormPreviousExamsPage extends BasePageObject
{

	public FormPreviousExamsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(id="procedures_other_line_autocomplete")
	WebElement examortestName;

	@FindBy(xpath="//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(id="procedures_other_line_frequency_type")
	WebElement testTimeFrame;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;


	/**
	 * @Description:Set Test Type
	 * @return
	 * @throws Exception
	 */
	public void setTest(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		examortestName.clear();
		examortestName.sendKeys(type);
		examortestName.sendKeys(Keys.TAB);
		Thread.sleep(10000);
		autoComplete.click();
	}

	/**
	 * @Description:Set Test TimeFrame
	 * @return
	 * @throws Exception
	 */
	public void setTestTimeFrame(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(testTimeFrame);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public FormIllnessConditionsPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver,FormIllnessConditionsPage.class);
	}

	/**
	 * @Description:Set Previous Exams Form Fields
	 * @return FormIllnessConditionsPage
	 * @throws Exception
	 */
	public FormIllnessConditionsPage setPreviousExamsFormFields() throws Exception
	{

		//setTestTimeFrame(PortalConstants.TestTimeFrame);
		//setTest(PortalConstants.Test);

		clickSaveAndContinueButton();
		return PageFactory.initElements(driver,FormIllnessConditionsPage.class);

	}

}
