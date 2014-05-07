package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;

/**
 * @author bkrishnankutty
 * @Date 7/10/2013
 * @Description :- Page Object for SiteGen Export Personnel
 * @Note :
 */
public class ExportPersonnelPage extends BasePageObject {

	@FindBy(name = "exportStaffBtn")
	private WebElement btnExportStaff;

	@FindBy(linkText = "Download exported file")
	private WebElement lnkDownloadExportedFile;

	public ExportPersonnelPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnExportStaff);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Click on button btnExportStaff
	 * @return void
	 */
	public void clickBtnExportStaff() {
		IHGUtil.PrintMethodName();
		btnExportStaff.click();

	}

	/**
	 * @author bkrishnankutty
	 * 
	 * @Desc:-Simulates Export staff download link click by accessing the link
	 *                  URL and downloading it via the URLStatusChecker class.
	 *                  Will return a boolean value indicating if the download
	 *                  was successful or not.
	 * 
	 * @return the http status code from the download
	 */
	public int clickLinkDownloadExportStaff() throws Exception {
		IHGUtil.PrintMethodName();
		return validateBlueButtonDownload(
				lnkDownloadExportedFile.getAttribute("href"), RequestMethod.GET);
	}

	private int validateBlueButtonDownload(String url, RequestMethod method)
			throws URISyntaxException, IOException {
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
		urlChecker.setURIToCheck(url);
		urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		urlChecker.mimicWebDriverCookieState(true);
		return urlChecker.getHTTPStatusCode();
	}

}
