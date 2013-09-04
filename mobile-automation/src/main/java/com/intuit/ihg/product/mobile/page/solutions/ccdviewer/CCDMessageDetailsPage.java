package com.intuit.ihg.product.mobile.page.solutions.ccdviewer;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageDetailsPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CCDMessageDetailsPage extends MessageDetailsPage {

    @FindBy(xpath="//a[@id='btnCCD']")
    private WebElement btnReviewHealthInfo;

    @FindBy(linkText="privacy statement")
    private WebElement privacyStmt;

    @FindBy(linkText="Terms of Service.")
    private WebElement termsOfService;

    public CCDMessageDetailsPage(WebDriver driver) {
        super(driver);
    }


    public CCDViewerListPage clickReviewHealthInfo() throws InterruptedException {
        waitForBtn(driver, 20);
        IHGUtil.PrintMethodName();
        btnReviewHealthInfo.click();
        return PageFactory.initElements(driver, CCDViewerListPage.class);
    }

    public void waitForBtn(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        try{
            IHGUtil.waitForElement(driver, n, btnReviewHealthInfo);
        }catch (TimeoutException e){
          log("WARN : Timeout occurred waiting for CCD Viewer button");
        }

    }

}
