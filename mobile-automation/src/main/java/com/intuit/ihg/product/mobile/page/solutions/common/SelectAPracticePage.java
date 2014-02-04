package com.intuit.ihg.product.mobile.page.solutions.common;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.makepayment.MakeAPayment;
import com.intuit.ihg.product.mobile.page.solutions.askaquestion.SelectAQuestionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    public SelectAQuestionPage selectPractice(String pracId) throws InterruptedException{
        practice = driver.findElement(By.xpath("//a[contains(text(),'"+pracId+"')]"));
        IHGUtil.waitForElement(driver, 10, practice);
        Thread.sleep(2000);
        practice.click();
        return PageFactory.initElements(driver, SelectAQuestionPage.class);
    }
    
    //a[contains(text(),'IHGQA Automation NonIntegrated')]
    public MobileBasePage selectPracticeUsingString(String pracId) throws InterruptedException{
        practice = driver.findElement(By.xpath("//a[contains(text(),'"+pracId+"')]"));
        IHGUtil.waitForElement(driver, 10, practice);
        Thread.sleep(2000);
        practice.click();
        System.out.println(this.getHeaderText());
        if (null!=this.getHeaderText() && getHeaderText().contains("Make A Payment")) {
        	
            return PageFactory.initElements(driver, SelectALocationPage.class);        
        }
        return PageFactory.initElements(driver, MakeAPayment.class);
    }
}

