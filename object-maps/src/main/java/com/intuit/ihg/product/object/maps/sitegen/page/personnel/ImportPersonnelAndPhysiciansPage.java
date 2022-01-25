//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

public class ImportPersonnelAndPhysiciansPage extends BasePageObject {

	@FindBy(name = "generateCSVHeaderBtn")
	private WebElement btnGenerateCSVHeaderTemplate;

	@FindBy(name = "importStaffFile")
	private WebElement fileUploadimportStaffFile;

	@FindBy(name = "importStaffBtn")
	private WebElement btnimportStaff;

	@FindBy(linkText = "Export Personnel")
	private WebElement lnkExportPersonnel;

	@FindBy(xpath = "//table[@id = 'MfAjaxFallbackDefaultDataTable']")
	private WebElement tableStaffImportList;

	@FindBy(xpath = "//table[@id = 'MfAjaxFallbackDefaultDataTable']/tbody/tr")
	private WebElement tableRowStaffImportList;

	@FindBy(xpath = "//table[@id = 'MfAjaxFallbackDefaultDataTable']/tbody/tr[1]/td[1]")
	private WebElement tableFirstRowCreateDate;

	@FindBy(xpath = "//table[@id = 'MfAjaxFallbackDefaultDataTable']/tbody/tr[1]/td[2]")
	private WebElement tableFirstRowFileName;

	@FindBy(xpath = "//table[@id = 'MfAjaxFallbackDefaultDataTable']/tbody/tr[1]/td[5]")
	private WebElement tableFirstRowStatus;

	public ImportPersonnelAndPhysiciansPage(WebDriver driver) {
		super(driver);
	}

	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, btnGenerateCSVHeaderTemplate);
		} catch (Exception e) {
			// Catch any element not found errors
		}
		return result;
	}

	public ImportOrExportProgressPage clickbtnimportStaffFile() {
		IHGUtil.PrintMethodName();
		File url = new File(SitegenConstants.FILEPATH);
		log("########################File Path" + url.getPath());
		assertNotNull(url, "### getSystemResource returned null: [" + SitegenConstants.FILEPATH + "] ");
		fileUploadimportStaffFile.sendKeys(url.getAbsolutePath());
		btnimportStaff.click();
		return PageFactory.initElements(driver, ImportOrExportProgressPage.class);
	}

	public String getImportStaffCreateDate() {
		return tableFirstRowCreateDate.getText();
	}

	public String getFileName() {
		return tableFirstRowFileName.getText();
	}

	public String getStatus() {
		return tableFirstRowStatus.getText();
	}

	public boolean verifyImportStaffCreationDate(String importStaffCreateDate, String currentEstTiming) throws ParseException {

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
		SimpleDateFormat sdf2 = new SimpleDateFormat();

		sdf1.setTimeZone(TimeZone.getTimeZone("EST"));
		sdf2.setTimeZone(TimeZone.getTimeZone("EST"));

		Date d1 = sdf1.parse(importStaffCreateDate);
		Date d2 = sdf2.parse(currentEstTiming);


		cal.setTime(d1);
		cal.getTime();
		int diff = IHGUtil.calculate_Date_Difference_in_Hours(d1, d2);

		if (diff > 4) {
			return false;
		} else
			return true;
	}

	public ExportPersonnelPage clickLinkExportPersonnel() {
		IHGUtil.PrintMethodName();
		lnkExportPersonnel.click();
		return PageFactory.initElements(driver, ExportPersonnelPage.class);
	}

}
