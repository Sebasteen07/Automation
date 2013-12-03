package com.intuit.ihg.product.mobile.page.solutions.askaquestion;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectALocationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectAQuestionPage extends MobileBasePage {

    @FindBy(xpath="//a[text()='Online Consultation']")
    private WebElement btnOnlineConsultation;

    private WebElement question;


    public SelectAQuestionPage(WebDriver driver) {
        super(driver);
    }
    
    public SelectALocationPage selectQuestion(String questionName) throws InterruptedException{
    	Thread.sleep(2000);
        question = driver.findElement(By.xpath("//a[contains(.,'"+questionName+"')]"));
        IHGUtil.waitForElement(driver, 10, question);
        question.click();
        Thread.sleep(2000);
        return PageFactory.initElements(driver, SelectALocationPage.class);
    }
}
