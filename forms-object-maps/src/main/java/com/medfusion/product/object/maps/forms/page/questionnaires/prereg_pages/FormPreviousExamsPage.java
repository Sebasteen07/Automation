package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormPreviousExamsPage extends PortalFormPage {

	@FindBy(id = "procedures_other_line_autocomplete")
	WebElement examortestName;

	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(id = "procedures_other_line_frequency_type")
	WebElement testTimeFrame;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	public FormPreviousExamsPage(WebDriver driver) {
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
	public void setTest_20(String type) throws Exception {
		PortalUtil.PrintMethodName();
		examortestName.clear();
		examortestName.sendKeys(type);
		examortestName.sendKeys(Keys.TAB);
		autoComplete.click();
	}
	/**
	 * @Description:Set Test TimeFrame
	 * @return
	 * @throws Exception
	 */
	public void setTestTimeFrame(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(testTimeFrame);
		selector.selectByVisibleText(type);
	}
	public void setTestTimeFrame_20(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(testTimeFrame);
		selector.selectByVisibleText(type);
	}
	public FormPastMedicalHistoryPage fillTestDetails(String Test1, String TestTimeFrame1) throws Exception
	{
		
		setTest_20(Test1);
		setTestTimeFrame_20(TestTimeFrame1);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormPastMedicalHistoryPage.class);
		
	}
	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Previous Exams, Tests & Procedures"))).isDisplayed();
	}
}
