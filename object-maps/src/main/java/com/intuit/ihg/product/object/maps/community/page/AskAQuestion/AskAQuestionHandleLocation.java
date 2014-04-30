package com.intuit.ihg.product.object.maps.community.page.AskAQuestion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;


public class AskAQuestionHandleLocation extends BasePageObject{
	
	public AskAQuestionHandleLocation(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public boolean checkPageTitle(){
		
		if (driver.findElements(By.xpath("//h2[contains(text(),'Select A Location For Your Question')]") ).size() != 0) 
		{
			return true;	
		}
		
	 return false;
	}
}
