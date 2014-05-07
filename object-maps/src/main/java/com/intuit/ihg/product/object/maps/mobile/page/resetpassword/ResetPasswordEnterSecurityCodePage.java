package com.intuit.ihg.product.object.maps.mobile.page.resetpassword;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;
import com.intuit.ihg.product.object.maps.mobile.page.MobileHomePage;

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
public class ResetPasswordEnterSecurityCodePage extends MobileBasePage {

    @FindBy( how = How.ID, using="resetCode")
	private WebElement securityCode;

	@FindBy( how = How.ID, using="resetConfirmUserId")
	private WebElement userId;

    @FindBy( how = How.ID, using="confirmSigninButton")
	private WebElement confirmSigninButton;


    public ResetPasswordEnterSecurityCodePage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage enterSecCodeAndSubmit(String secCode) throws InterruptedException {
        waitForSecCode(driver,10);
        securityCode.sendKeys(secCode);
        confirmSigninButton.click();
        if (isErrorMsgPresent())
        return PageFactory.initElements(driver, ResetPasswordEnterSecurityCodePage.class);
        else return PageFactory.initElements(driver, MobileHomePage.class);

    }

    public void waitForSecCode(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, securityCode);
    }

    public String getUserId(){
        return userId.getText();
    }
}
