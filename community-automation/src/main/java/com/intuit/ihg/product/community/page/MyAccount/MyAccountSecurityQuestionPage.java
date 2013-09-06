package com.intuit.ihg.product.community.page.MyAccount;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class MyAccountSecurityQuestionPage extends BasePageObject {

	// Page Objects

	@FindBy(how = How.ID, using = "securityquestion")
	public WebElement securityQuestion;

	@FindBy(how = How.ID, using = "answer")
	public WebElement answer;

	@FindBy(how = How.XPATH, using = "//div[@id='sidebar_security']//button[@type='submit']")
	public WebElement btnSaveChanges;

	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'Success')]")
	public WebElement successNotification;

	public MyAccountSecurityQuestionPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean areElementsDisplayed() throws InterruptedException {

		WebElement[] elements = { securityQuestion, answer };

		for (WebElement element : elements) {
			if (!element.isDisplayed()) {
				log("Element :" + element.toString() + " is not displayed");
				return false;
			}
		}
		return true;
	}

	public void setSecuityQuestion(String sQuestion, String sAnswer)
			throws Exception {

		securityQuestion.sendKeys(sQuestion);
		answer.sendKeys(sAnswer);
		btnSaveChanges.click();
	}

	public boolean sucessNotification(WebDriver driver) throws Exception {

		WebElement notification = (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//h2[contains(text(),'Success')]")));

		return successNotification.isDisplayed();

	}

}
