package com.intuit.ihg.product.portal.page.healthform;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitHistoryPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class HealthFormPage extends BasePageObject {
	
    public static final String PAGE_NAME = "Insurance Health Form Page";
	
	@FindBy(linkText="Insurance Health Form ( Testing)")
	private WebElement lnkInsuranceHealthForm;
	
	@FindBy(name="footer:submit")
	private WebElement btnSubmit;
	
	@FindBy(linkText="click here")
	private WebElement lnkclickForPdfDownload;
	
	@FindBy(xpath="//div[@class='heading1']")
	public WebElement InsuranceHelthform;
	
	@FindBy(xpath="//div[@id='formcontainer']//table/tbody/tr[5]/td")
	public WebElement Patientname;
	
	public HealthFormPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives an indication if the page loaded as expected
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		
		boolean result = false;
		try {
			result = lnkInsuranceHealthForm.isDisplayed();
		} catch (Exception e) {
			// Catch element not found error
		}
		return result;
	}
	
	
	
	/**
	 * Click on the link InsuranceHealthForm download 
	 * and move to the next frame 
	 */
	public void fillInsuranceHealthForm() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 20, lnkInsuranceHealthForm);
		lnkInsuranceHealthForm.click();
		btnSubmit.click();
	}
	
	
	/**
     * Simulates Text Insurance HealthFormdownload link click by accessing the link URL and
     * downloading it via the URLStatusChecker class.
     * 
     * Will return a boolean value indicating if the download was successful or not.
     *
     * @return the http status code from the download
     */
	public int clickInsuranceHealthFormDownloadText() throws Exception {
		IHGUtil.PrintMethodName();
	    PortalUtil.setPortalFrame(driver);
	    
	    return insuranceHealthFormDownload(lnkInsuranceHealthForm.getAttribute("href"), RequestMethod.GET);
	}
	
	/**
	 * steps 1:-sets URL that you want to perform an HTTP Status Check upon
	 * steps 2:-Set the HTTP Request Method (Defaults to 'GET')
	 * steps 3:-Mimic the cookie state of WebDriver (Defaults to true) This will enable you to access files that are only available when logged in.
	 * Perform an HTTP Status check and return the response code as int (200,300 etc)
	 * @param url
	 * @param method
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	
	private int insuranceHealthFormDownload(String url, RequestMethod method) throws URISyntaxException, IOException {
		
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
	    urlChecker.setURIToCheck(url);
	    urlChecker.setHTTPRequestMethod(RequestMethod.GET);
	    urlChecker.mimicWebDriverCookieState(true);
	    
	    return urlChecker.getHTTPStatusCode();
	}

}
