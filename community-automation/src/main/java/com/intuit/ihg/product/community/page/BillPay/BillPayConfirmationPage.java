package com.intuit.ihg.product.community.page.BillPay;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class BillPayConfirmationPage extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Search for a different doctor or staff member')]")
	public WebElement serachForDifferentDoctor;
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnMakeAPayment;

		public BillPayConfirmationPage(WebDriver driver) {
			super(driver);
			PageFactory.initElements(driver, this);
			
	}
}