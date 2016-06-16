package com.medfusion.product.object.maps.patientportal1.page.myAccount.manageHealthInfo;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ManageHealthInfoPage extends BasePageObject {

    public static final String PAGE_NAME = "Manage Health Info Page";

    @FindBy(partialLinkText = "Download My Data")
    private WebElement lnkPdfDownload;

    @FindBy(linkText = "Download text-only version")
    private WebElement lnkTxtDownload;

    @FindBy(linkText = "View Account Activity")
    private WebElement lnkViewAcctActivity;

    @FindBy(linkText = "Lean more about Blue Button")
    private WebElement lnkBlueButtonLearnMore;

    public ManageHealthInfoPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Simulates PDF Blue Button download link click by accessing the link URL and
     * downloading it via the URLStatusChecker class.
     * Will return a boolean value indicating if the download was successful or not.
     *
     * @return the http status code from the download
     */
    public int clickBlueButtonDownloadPdf() throws Exception {
        IHGUtil.PrintMethodName();
        PortalUtil.setPortalFrame(driver);

        return validateBlueButtonDownload(lnkPdfDownload.getAttribute("href"), RequestMethod.GET);
    }

    /**
     * Simulates Text Blue Button download link click by accessing the link URL and
     * downloading it via the URLStatusChecker class.
     * Will return a boolean value indicating if the download was successful or not.
     *
     * @return the http status code from the download
     */
    public int clickBlueButtonDownloadText() throws Exception {
        IHGUtil.PrintMethodName();
        PortalUtil.setPortalFrame(driver);

        return validateBlueButtonDownload(lnkPdfDownload.getAttribute("href"), RequestMethod.GET);
    }

    private int validateBlueButtonDownload(String url, RequestMethod method) throws URISyntaxException, IOException {
        URLStatusChecker urlChecker = new URLStatusChecker(driver);

        urlChecker.setURIToCheck(url);
        urlChecker.setHTTPRequestMethod(RequestMethod.GET);
        urlChecker.mimicWebDriverCookieState(true);

        return urlChecker.getHTTPStatusCode();
    }

}
