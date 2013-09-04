package com.intuit.ihg.product.mobile.page.forgotuserid;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.common.SubmissionConfirmationPage;
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
public class ForgotUserIdEnterSecretAnswerPage extends MobileBasePage {
    @FindBy(how = How.ID, using = "forgotUsrSecQ")
    // <label id="forgotUsrSecQ">What was the make of your first car?</label>
    private WebElement securityQuestion;    // Read-only.

    @FindBy(how = How.ID, using = "forgotUsrSecurityAnswer")
    private WebElement securityAnswer;

    @FindBy(how = How.ID, using = "forgotUserID2Submit")
    private WebElement submit;

    public ForgotUserIdEnterSecretAnswerPage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage enterSecretAnsSubmit(String secAnswer) throws InterruptedException {
        waitForSecAnswer(driver, 10);
        securityAnswer.sendKeys(secAnswer);
        submit.click();
        if (isErrorMsgPresent())
            return PageFactory.initElements(driver, ForgotUserIdEnterSecretAnswerPage.class);
        else return PageFactory.initElements(driver, SubmissionConfirmationPage.class);

    }

    public void waitForSecAnswer(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, securityAnswer);
    }
}
