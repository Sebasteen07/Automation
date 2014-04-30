package com.intuit.ihg.product.object.maps.community.page.RxRenewal;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class RxRenewalSearchDoctor extends BasePageObject {

	@FindBy(how = How.CSS, using = "a.show_search > strong")
	public WebElement searchForDifferentDoctor;

	@FindBy(how = How.ID, using = "drsearch")
	public WebElement searchTextBox;

	@FindBy(how = How.ID, using = "searchbtn")
	public WebElement searchButton;

	@FindBy(how = How.CSS, using = "button[type=submit]")
	public WebElement btnContinue;

	public RxRenewalSearchDoctor(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public RxRenewalChoosePharmacy selectDoctor(String sDoctor) throws InterruptedException {

		driver.findElement(By.xpath("// * [contains(text(),'" + sDoctor + "')]")).click();
		btnContinue.click();

		// Setting time out to 30 as it is default value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return PageFactory.initElements(driver, RxRenewalChoosePharmacy.class);
	}

}
