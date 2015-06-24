package com.intuit.ihg.product.object.maps.portal.page;

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

/** @author pharlid
 *  @Date 5-8-2014
 *  @Description Page Object for AChecker
 */

public class AChecker extends BasePageObject {

	public static final String SUCCESS_MESSAGE = "Congratulations! No known problems.";
	public static final String ACECKER_URL = "http://achecker.ca/checker/index.php";
	
	// The Options link
	@FindBy(how = How.XPATH, using = ".//*[@id='center-content']/table/tbody/tr/td/div/form/div/fieldset/div[5]/h2/a")
	public WebElement options; //= driver.findElement(By.linkText("Options"));
	
	// The WCAG 2.0 (Level A) radio button under Options
	@FindBy(how = How.ID, using = "radio_gid_7")
	public WebElement WCAGOption;
	
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
	
	public AChecker(WebDriver driver) {
		super(driver);
		driver.manage().deleteAllCookies();
		driver.get(ACECKER_URL);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	// Navigates to Paste HTML Markup and sets the options to WCAG 2.0 (Level A)
	public void Setup()
	{
		tabPaste.click();
		options.click();
		WCAGOption.click();
		options.click();
	}
	
	// Assuming the validation window is selected, paste from clipboard, click validate and wait for spinner
	public void Validate()
	{
		pasteField.click();
		pasteField.sendKeys(Keys.CONTROL, "a");
		pasteField.sendKeys(Keys.CONTROL, "v");
		validateButton.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("AC_spinner_by_paste")));
		Assert.assertEquals(SUCCESS_MESSAGE, successMessage.getText());
	}
}
