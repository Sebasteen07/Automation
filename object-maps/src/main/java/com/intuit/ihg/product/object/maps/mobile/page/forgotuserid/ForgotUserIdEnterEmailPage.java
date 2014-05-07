package com.intuit.ihg.product.object.maps.mobile.page.forgotuserid;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForgotUserIdEnterEmailPage extends MobileBasePage {

    @FindBy( how = How.ID, using="forgotUsrIdEmail")
	private WebElement email;
  
    @FindBy( how = How.XPATH, using="//a[@id='acctForgotUserIDSubmit']")
	private WebElement submit;

    public ForgotUserIdEnterEmailPage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage enterEmailAndSubmit(String emailId) throws InterruptedException {
        IHGUtil.waitForElement(driver, 6, email);
        email.sendKeys(emailId);
        Thread.sleep(1000);
        submit.click();
        if (isErrorMsgPresent())
        return PageFactory.initElements(driver, ForgotUserIdEnterEmailPage.class);
        else return PageFactory.initElements(driver, ForgotUserIdEnterSecretAnswerPage.class);

    }
}
