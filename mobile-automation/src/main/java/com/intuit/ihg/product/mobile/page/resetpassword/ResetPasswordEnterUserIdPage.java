package com.intuit.ihg.product.mobile.page.resetpassword;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.forgotuserid.ForgotUserIdEnterEmailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResetPasswordEnterUserIdPage extends MobileBasePage {

    @FindBy( how = How.ID, using="resetPwdUserId")
	private WebElement userId;

	@FindBy( how = How.CSS, using="a[href*='acctForgotUserID']")
	private WebElement forgotUserID;

    @FindBy( how = How.ID, using="acctResetPswrdSubmit")
	private WebElement submit;

    public ResetPasswordEnterUserIdPage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage enterUserIdAndSubmit(String userName) throws InterruptedException {
        waitForUserId(driver, 10);
        userId.sendKeys(userName);
        submit.click();
        if (isErrorMsgPresent())
        return PageFactory.initElements(driver, ResetPasswordEnterUserIdPage.class);
        else return PageFactory.initElements(driver, ResetPasswordEnterNewPasswordPage.class);

    }

    public void waitForUserId(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, userId);
    }

    public ForgotUserIdEnterEmailPage clickForgotUserId() throws InterruptedException {
        waitForSubmit(driver,10);
        forgotUserID.click();
        return PageFactory.initElements(driver, ForgotUserIdEnterEmailPage.class);
    }

    public void waitForSubmit(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, submit);
    }
}
