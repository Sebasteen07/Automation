package com.intuit.ihg.product.community.page.AskAQuestion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author yvaddavalli
 * 
 */
public class AskAQuestionSelectLocation extends BasePageObject {

	// Page Objects

	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;

	public AskAQuestionSelectLocation(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	// Selecting Location
	public AskAQuestionMessage SelectLocation(String sLocation) throws InterruptedException {

		IHGUtil.PrintMethodName();
		driver.findElement(
				By.xpath("// * [contains(text(),'" + sLocation + "')]"))
				.click();
		btnContinue.click();
		return PageFactory.initElements(driver, AskAQuestionMessage.class);
	}
}