package com.intuit.ihg.product.mobile.page.solutions.askaquestion;

import com.intuit.ihg.product.mobile.page.MobileBasePage;
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

    public MobileBasePage selectQuestion(int questionId){
        question = driver.findElement(By.xpath("//a[@questionid='urn:vnd:ihg:portal:other:aas:" + questionId + "']")); //18027
        question.click();
        return PageFactory.initElements(driver, MobileBasePage.class);
    }
}
