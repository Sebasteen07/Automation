package com.intuit.ihg.product.community.page.CreateAnAccount;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class CreateAnAccountHandleIframe extends BasePageObject{

	@FindBy(how = How.TAG_NAME, using = "iframe")
	public WebElement iframe;
	
	public CreateAnAccountHandleIframe(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	public CreateAnAccountPatientPage handleIframeAccount() throws InterruptedException {
		driver.switchTo().frame(iframe);
        return PageFactory.initElements(driver, CreateAnAccountPatientPage.class);
    }
	
	
}
