package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import java.net.URL;
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
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

/**
 * @author bkrishnankutty
 * @Date 7/9/2013
 * @Description :- Page Object for ImportPersonnelAndPhysicians Page
 * @Note :
 */

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

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public ImportPersonnelAndPhysiciansPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6,
					btnGenerateCSVHeaderTemplate);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- upload the import staff doc in .csv format
	 * @param
	 * @return ImportOrExportProgressPage
	 */
	public ImportOrExportProgressPage clickbtnimportStaffFile() {
		IHGUtil.PrintMethodName();
		URL url = ClassLoader.getSystemResource(SitegenConstants.FILEPATH);
		log("########################File Path" + url);
		Assert.assertNotNull(url, "### getSystemResource returned null: ["
				+ SitegenConstants.FILEPATH + "] ");
		fileUploadimportStaffFile.sendKeys(url.toString());
		btnimportStaff.click();
		return PageFactory.initElements(driver,
				ImportOrExportProgressPage.class);
	}

	/**
	 * @author bkrishnankutty
	 * @return CreateDate from the tables FirstRow
	 */
	public String getImportStaffCreateDate() {
		return tableFirstRowCreateDate.getText();
	}

	/**
	 * @author bkrishnankutty
	 * @return FileName (String Format) from the tables FirstRow
	 */
	public String getFileName() {
		return tableFirstRowFileName.getText();
	}

	/**
	 * @author bkrishnankutty
	 * @return Status (String Format) from the tables FirstRow
	 */
	public String getStatus() {
		return tableFirstRowStatus.getText();
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Assertion of CreationDate ie verify ImportStaff CreationDate in
	 *         table with Current EST time or server time
	 * 
	 * @param importStaffCreateDate
	 * @param currentEstTiming
	 * @return true or false
	 * @throws ParseException
	 */

	public boolean verifyImportStaffCreationDate(String importStaffCreateDate,
			String currentEstTiming) throws ParseException {

		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
		SimpleDateFormat sdf2 = new SimpleDateFormat();
		
		sdf1.setTimeZone(TimeZone.getTimeZone("EST"));
		sdf2.setTimeZone(TimeZone.getTimeZone("EST"));
		
		Date d1 = sdf1.parse(importStaffCreateDate);
		Date d2 = sdf2.parse(currentEstTiming);
		
		
		cal.setTime(d1);
		cal.getTime();
		int diff =IHGUtil.calculate_Date_Difference_in_Hours(d1, d2);

		if (diff > 4) {
			return false;
		} else
			return true;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link ExportPersonnel
	 * @param
	 * @return ExportPersonnel Page
	 */
	public ExportPersonnelPage clickLinkExportPersonnel() {
		IHGUtil.PrintMethodName();
		lnkExportPersonnel.click();
		return PageFactory.initElements(driver, ExportPersonnelPage.class);
	}

}
