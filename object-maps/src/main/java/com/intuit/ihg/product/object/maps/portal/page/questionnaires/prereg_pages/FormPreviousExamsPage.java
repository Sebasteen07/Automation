package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormPreviousExamsPage extends PortalFormPage {

	@FindBy(id="procedures_other_line_autocomplete")
	WebElement examortestName;

	@FindBy(xpath="//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(id="procedures_other_line_frequency_type")
	WebElement testTimeFrame;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;


	public FormPreviousExamsPage(WebDriver driver)  {
		super(driver);
	}
	
	/**
	 * @Description:Set Test Type
	 * @return
	 * @throws Exception
	 */
	public void setTest(String type) throws Exception {
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
	public void setTestTimeFrame(String type) throws Exception	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(testTimeFrame);
		selector.selectByVisibleText(type);
	}
}
