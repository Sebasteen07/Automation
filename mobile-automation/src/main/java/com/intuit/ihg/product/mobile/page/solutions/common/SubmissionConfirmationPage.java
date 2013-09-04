package com.intuit.ihg.product.mobile.page.solutions.common;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.MobileHomePage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageInboxPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmissionConfirmationPage extends MobileBasePage {


    //@FindBy(how = How.XPATH, using = "//a[@href='#home' and @data-role='button']")
    @FindBy(linkText="Close")
    private WebElement closeBtn;


    @FindBy(how = How.XPATH, using = "//label[@id='forgotUsrIDThanksEmail']")
    private WebElement forgotUserIdEmail;

    @FindBy(how = How.XPATH, using = "//div[@id='forgotUserIDThanks']/div/div/h1")
    private WebElement forgotUserIdTitle;


    public SubmissionConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public MobileHomePage clickClose() throws InterruptedException {
        Thread.sleep(2000);
        waitForCloseBtn(driver, 20);
        IHGUtil.PrintMethodName();
        closeBtn.click();

        return PageFactory.initElements(driver, MobileHomePage.class);
    }

    public void waitForCloseBtn(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, closeBtn);
    }

    public String getForgotUserIdEmailText() {
        return forgotUserIdEmail.getText();
    }

    public String getForgotUserIdTitle() {
        return forgotUserIdTitle.getText();
    }

}
