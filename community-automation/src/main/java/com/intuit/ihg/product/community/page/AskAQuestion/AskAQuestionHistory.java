package com.intuit.ihg.product.community.page.AskAQuestion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class AskAQuestionHistory extends BasePageObject {
	
	public WebElement result;
	
	public AskAQuestionHistory(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void findAskAQuestion(String sQuestion) throws InterruptedException {
	
		result = driver.findElement(By.xpath("// * [contains(text(),'"+sQuestion+"')]"));
		result.click();
		
	}
	
	public boolean checkAskAQuestionHistory(String sQuestion) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		result = driver.findElement(By.xpath("// * [contains(text(),'"+sQuestion+"')]"));
		result.click();		
		return true;
		
	}
	
}
