//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;

public class ExportPersonnelPage extends BasePageObject {

	@FindBy(name = "exportStaffBtn")
	private WebElement btnExportStaff;

	@FindBy(linkText = "Download exported file")
	private WebElement lnkDownloadExportedFile;

	public ExportPersonnelPage(WebDriver driver) {
		super(driver);
	}

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

	public void clickBtnExportStaff() {
		IHGUtil.PrintMethodName();
		btnExportStaff.click();

	}

	public int clickLinkDownloadExportStaff() throws Exception {
		IHGUtil.PrintMethodName();
		return validateBlueButtonDownload(lnkDownloadExportedFile.getAttribute("href"), RequestMethod.GET);
	}

	private int validateBlueButtonDownload(String url, RequestMethod method) throws URISyntaxException, IOException {
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
		urlChecker.setURIToCheck(url);
		urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		urlChecker.mimicWebDriverCookieState(true);
		return urlChecker.getHTTPStatusCode();
	}
}
