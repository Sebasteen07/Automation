package com.intuit.ihg.product.community.page.solutions.Messages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;



public class MessagePage extends BasePageObject {
		
	public WebElement email;
	
	public MessagePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	
	public MessageDetailPage clickMessage(String sSubject) throws InterruptedException {
        email = driver.findElement(By.xpath("// * [contains(text(),'" + sSubject + "')]"));
        email.click();
        return PageFactory.initElements(driver, MessageDetailPage.class);
    }
}
