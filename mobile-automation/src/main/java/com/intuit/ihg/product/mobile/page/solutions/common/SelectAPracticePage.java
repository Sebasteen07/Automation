package com.intuit.ihg.product.mobile.page.solutions.common;

import com.intuit.ihg.product.mobile.page.MobileBasePage;
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
    public SelectAPracticePage(WebDriver driver) {
        super(driver);
    }
    private WebElement practice;


    public MobileBasePage selectPractice(int pracId){
        practice = driver.findElement(By.xpath("//a[@practiceid='" + pracId + "']")); //66215
        practice.click();
        return PageFactory.initElements(driver, MobileBasePage.class);
    }
}
