package com.medfusion.product.object.maps.patientportal1.page;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author pharlid
 * @Date 5-8-2014
 * @Description Page Object for AChecker
 */

public class AChecker extends BasePageObject {

	public static final String SUCCESS_MESSAGE = "Congratulations! No known problems.";
	public static final String ACECKER_URL = "http://achecker.ca/checker/index.php";

	// The Options link
	@FindBy(how = How.XPATH, using = ".//*[@id='center-content']/table/tbody/tr/td/div/form/div/fieldset/div[5]/h2/a")
	public WebElement options; // = driver.findElement(By.linkText("Options"));

	// The WCAG 2.0 (Level A) radio button under Options
	@FindBy(how = How.ID, using = "radio_gid_7")
	public WebElement WCAGLevelAOption;
	
	@FindBy(how = How.ID, using = "radio_gid_8")
	public WebElement WCAGLevelAAOption;
	
	@FindBy(how = How.ID, using = "radio_gid_9")
	public WebElement WCAGLevelAAAOption;

	// The Paste HTML Markup tab
	@FindBy(how = How.ID, using = "AC_menu_by_paste")
	public WebElement tabPaste;

	// The text field in which to paste HTML
	@FindBy(how = How.ID, using = "checkpaste")
	public WebElement pasteField;

	// The Check it button
	@FindBy(how = How.ID, using = "validate_paste")
	public WebElement validateButton;

	// The spinner shown when waiting
	@FindBy(how = How.ID, using = "AC_spinner_by_paste")
	public WebElement validationSpinner;

	// Success message
	@FindBy(how = How.ID, using = "AC_congrats_msg_for_errors")
	public WebElement successMessage;

	@FindBy(how = How.ID, using = "AC_errors")
	public WebElement errors;

	public AChecker(WebDriver driver) {
		super(driver);
		driver.manage().deleteAllCookies();
		driver.get(ACECKER_URL);
		//driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public enum LevelOfWCAG {
		A, AA, AAA;
	}

	// Navigates to Paste HTML Markup and sets the options to WCAG 2.0 (Level A/AA/AAA)
	public void setupLevel(LevelOfWCAG level) {
		openOptions();
		switch (level) {
			case A:
				javascriptClick(WCAGLevelAOption);
				break;
			case AA:
				javascriptClick(WCAGLevelAAOption);
				break;
			case AAA:
				javascriptClick(WCAGLevelAAAOption);
				break;
		}
		closeOptions();
	}

	// Assuming the validation window is selected, paste from clipboard, click validate and wait for spinner
	public void validate() throws InterruptedException {
		log("It will start validating the source code by pasting it on the clipboard");
		javascriptClick(pasteField);
		IHGUtil.waitForElement(driver, 50, pasteField);
		pasteField.sendKeys(Keys.CONTROL, "a");
		pasteField.sendKeys(Keys.CONTROL, "v");
		log("It clicked on the pastefield and had sended the keys");
		Thread.sleep(5000);
		javascriptClick(validateButton);
		log("It clicked on the validate button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("AC_spinner_by_paste")));
		if (errors.isDisplayed()) {
			log(errors.getText());
		}
		Assert.assertEquals(SUCCESS_MESSAGE, successMessage.getText());
		System.out.println("We got the success Message");
	}
	
	private void openOptions(){
		tabPaste.click();
		options.click();
		System.out.println("It clicked on tabPaste  and Option Button");
	}
	
	private void closeOptions(){
		options.click();
	}
}
