package com.intuit.ihg.product.community.page.RxRenewal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class RxRenewalChoosePharmacy extends BasePageObject {

	@FindBy(how = How.XPATH, using = "(//input[@name='existing_pharm' and @value='other_pharm' ])")
	public WebElement chooseNewPharmacy;

	@FindBy(how = How.ID, using = "addr_search")
	public WebElement addressTextBox;

	@FindBy(how = How.ID, using = "pharmacy_search_btn")
	public WebElement PharmacySearchButton;

	@FindBy(how = How.CSS, using = "button[type=submit]")
	public WebElement btnContinue;

	public RxRenewalChoosePharmacy(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	public void selectPharmacy(String sPharmacy) throws InterruptedException {

		// Choose New Pharmacy

		chooseNewPharmacy.click();

		Thread.sleep(2000);
		addressTextBox.clear();
		addressTextBox.sendKeys(sPharmacy);
		PharmacySearchButton.click();
		// Waiting for location_listing element to show up
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.visibilityOfElementLocated(By
						.id("location_listing")));

		btnContinue.click();

	}

}
