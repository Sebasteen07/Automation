package com.intuit.ihg.product.mobile.page.solutions.common;

import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.makepayment.MakeAPayment;
import com.intuit.ihg.product.mobile.page.makepayment.NewCard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectAPracticePage extends MobileBasePage {
	
	private WebElement practice;
	
    public SelectAPracticePage(WebDriver driver) {
        super(driver);
    }
  

    //a[contains(text(),'66215')]
    public MobileBasePage selectPractice(String pracId){
        practice = driver.findElement(By.xpath("//a[contains(text(),'"+pracId+"')]"));
        practice.click();
        return PageFactory.initElements(driver, MobileBasePage.class);
    }
    
    //a[contains(text(),'IHGQA Automation NonIntegrated')]
    public MakeAPayment selectPracticeUsingString(String pracId){
        practice = driver.findElement(By.xpath("//a[contains(text(),'"+pracId+"')]"));
        practice.click();
        return PageFactory.initElements(driver, MakeAPayment.class);
    }
}

