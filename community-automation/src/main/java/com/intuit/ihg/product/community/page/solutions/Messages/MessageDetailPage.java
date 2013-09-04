package com.intuit.ihg.product.community.page.solutions.Messages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;


public class MessageDetailPage extends BasePageObject {
	
	
	public WebElement btnReviewHealthInformation;
	
	public MessageDetailPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	public boolean isSubjectLocated(String sSubject) {
		IHGUtil.PrintMethodName();
		
		boolean result = false;
		try {
			result = driver.findElement(By.xpath("// * [contains(text(),'" + sSubject + "')]")).isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	public MessageIframeHandlePage clickReviewHealthInformation() throws InterruptedException {
		btnReviewHealthInformation = driver.findElement(By.xpath("//a[contains(text(),'Review Health Information')]"));
		btnReviewHealthInformation.click();
        return PageFactory.initElements(driver, MessageIframeHandlePage.class);
    }
	
	
}