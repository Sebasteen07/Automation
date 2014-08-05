package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class SpecialCharFormSecondPage extends BasePageObject {
	
	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(id = "custom_mappingid_customsecond_section_0_5")
	private WebElement severeButt;
	
	@FindBy(id = "custom_mappingid_customsecond_section_consentanswer_custom_1")
	private WebElement consent;
	
	@FindBy(xpath="//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy( xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;

	
	
	public SpecialCharFormSecondPage(WebDriver driver) {
		super(driver);
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
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return nextPageClass == null ? null : PageFactory.initElements(driver, nextPageClass);
	}
	
	public void selectAnswerQuoteMark() {
		severeButt.click();
	}
	
	/**
	 * @brief Signs consent with a name containing quote marks
	 */
	public void signConsent() {
		consent.sendKeys("Frankie \"Tester\" Testguy");
	}
	
	/**
	 * @Description:Click on Submit Form Button
	 * @return
	 * @throws Exception
	 */
	public void submitForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(iframe);

		submitForm.click();
		
		try {
			wait.until( ExpectedConditions.not( ExpectedConditions.visibilityOf(iframe) ) );
		} catch (NoSuchElementException e) {
			log("Form element not found, ready to continue");
		}
	}
}
