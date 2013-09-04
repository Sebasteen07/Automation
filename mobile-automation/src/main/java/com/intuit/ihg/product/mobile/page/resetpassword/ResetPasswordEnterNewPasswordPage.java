package com.intuit.ihg.product.mobile.page.resetpassword;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
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
public class ResetPasswordEnterNewPasswordPage extends MobileBasePage {

    @FindBy( how = How.ID, using="secQ")	// <label id="secQ">What was the make of your first car?</label>
	private WebElement securityQuestion;	// Read-only.

	@FindBy( how = How.ID, using="securityAnswer")
	private WebElement securityAnswer;

	@FindBy( how = How.ID, using="newPassword")
	private WebElement newPassword;

	@FindBy( how = How.ID, using="newPasswordConfirm")
	private WebElement newPasswordConfirm;

    @FindBy( how = How.ID, using="resetPasswordSubmit")
	private WebElement submit;

    public ResetPasswordEnterNewPasswordPage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage enterNewPasswordAndSubmit(String secAns, String pwd, String newpwd) throws InterruptedException {
        waitForSubmit(driver,10);
        securityAnswer.sendKeys(secAns);
        newPassword.sendKeys(pwd);
        newPasswordConfirm.sendKeys(newpwd);
        submit.click();
        if (isErrorMsgPresent())
        return PageFactory.initElements(driver, ResetPasswordEnterNewPasswordPage.class);
        else return PageFactory.initElements(driver, ResetPasswordEnterSecurityCodePage.class);

    }

    public void waitForSubmit(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, submit);
    }

    public String getSecretQn(){
        return securityQuestion.getText();
    }
}
