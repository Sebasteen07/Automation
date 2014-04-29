package com.intuit.ihg.product.community.page.solutions.Messages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;


public class MessageIframeHandlePage extends BasePageObject{

	@FindBy(how = How.TAG_NAME, using = "iframe")
	public WebElement CCDViewFrame;
	
	public MessageIframeHandlePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	public MessageHealthInformationPage handleIframe() throws InterruptedException {
		driver.switchTo().frame(CCDViewFrame);
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("personalHeader")));
        return PageFactory.initElements(driver, MessageHealthInformationPage.class);
    }
	
}
