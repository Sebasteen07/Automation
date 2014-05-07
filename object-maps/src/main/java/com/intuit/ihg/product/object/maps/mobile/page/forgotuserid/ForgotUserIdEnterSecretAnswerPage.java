package com.intuit.ihg.product.object.maps.mobile.page.forgotuserid;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SubmissionConfirmationPage;

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
    
    @FindBy(how = How.XPATH, using = "//a[@id='forgotUserID2Submit']")
    private WebElement submit;

    public ForgotUserIdEnterSecretAnswerPage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage enterSecretAnsSubmit(String secAnswer) throws InterruptedException {
    	IHGUtil.waitForElement(driver, 6, securityAnswer);
        securityAnswer.sendKeys(secAnswer);
        Thread.sleep(1000);
        submit.click();
        Thread.sleep(1000);
        if (isErrorMsgPresent())
            return PageFactory.initElements(driver, ForgotUserIdEnterSecretAnswerPage.class);
        else return PageFactory.initElements(driver, SubmissionConfirmationPage.class);

    }
}
