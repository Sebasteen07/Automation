package com.intuit.ihg.product.mobile.page;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.resetpassword.ResetPasswordEnterUserIdPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/20/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileSignInPage extends MobileBasePage {

        public static final String pageUrl = "https://dev3.dev.medfusion.net/mobile/";

        @FindBy( how = How.ID, using="userid")
        private WebElement username;

        @FindBy( how = How.ID, using="password")
        private WebElement password;

        @FindBy( how = How.XPATH, using="//*[@id='loginSubmit' or @id='signinButton']")
        private WebElement login;

        @FindBy( how = How.ID, using="cantAccessLink")
        private WebElement cantAccessLink;

        @FindBy( how = How.ID, using="rememberId")
        private WebElement rememberId;

        @FindBy(xpath="//a[@id='signout' or contains(@*,'doSignOut') or contains(@onclick,'doSignOut') or contains(@href,'doSignOut')]")
        private WebElement logout;

        public MobileSignInPage(WebDriver driver) {
            super(driver);
            // TODO Auto-generated constructor stub
        }

        public MobileSignInPage(WebDriver driver, String sURL) {

            super(driver);

            IHGUtil.PrintMethodName();

            log("URL " + sURL);

            driver.get(sURL);

            maxWindow();

            PageFactory.initElements(driver, this);
        }

        public MobileSignInPage(WebDriver driver, String env, String sURL) {
            super(driver);
            IHGUtil.PrintMethodName();
            sURL = sURL!=null?sURL:processUrlForEnv(pageUrl,env);
            log("URL " + sURL);
            driver.get(sURL);
            maxWindow();
            PageFactory.initElements(driver, this);
        }

        public MobileHomePage login( String sUsername, String sPassword ) throws InterruptedException {

            IHGUtil.PrintMethodName();
            log( "### DEBUG LOGIN: [" + sUsername + "] [" + sPassword + "]" );

            username.click();
            username.clear();
            username.sendKeys( sUsername );

            password.click();
            password.clear();
            password.sendKeys( sPassword );

            waitforLoginLink(driver, 60);
            login.click();

            return PageFactory.initElements(driver, MobileHomePage.class);
        }

        public void waitforLoginLink(WebDriver driver,int n)
        {   IHGUtil.PrintMethodName();
            IHGUtil.waitForElement(driver,n, login);
        }

         public ResetPasswordEnterUserIdPage clickCantAccessLink() {

            IHGUtil.PrintMethodName();

            cantAccessLink.click();

            return PageFactory.initElements(driver, ResetPasswordEnterUserIdPage.class);
         }

    }