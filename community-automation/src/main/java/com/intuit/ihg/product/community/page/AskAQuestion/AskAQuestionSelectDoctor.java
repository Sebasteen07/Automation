package com.intuit.ihg.product.community.page.AskAQuestion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class AskAQuestionSelectDoctor extends BasePageObject {

	// Page Objects

	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Search for a different recipient')]")
	public WebElement serachForDifferentDoctor;

	public AskAQuestionSelectDoctor(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// Selecting Doctor
	public AskAQuestionSelectLocation SelectDoctor(String sDoctor) throws InterruptedException {

		IHGUtil.PrintMethodName();
		driver.findElement(
				By.xpath("// * [contains(text(),'" + sDoctor + "')]")).click();
		btnContinue.click();
		return PageFactory.initElements(driver, AskAQuestionSelectLocation.class);
	}

}
