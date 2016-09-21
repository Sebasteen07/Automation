package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 6/11/2013
 * @Description :- Page Object for SiteGen Import/ExportProgress Page
 * @Note :
 */

public class ImportOrExportProgressPage extends BasePageObject {

	@FindBy(linkText = "Import Personnel and Physicians")
	private WebElement lnkImportPersonnelAndPhysicians;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public ImportOrExportProgressPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on link lnkImportPersonnelAndPhysicians
	 * @return ImportPersonnelAndPhysiciansPage
	 * @throws InterruptedException
	 * 
	 */
	public ImportPersonnelAndPhysiciansPage clickLinkImportPersonnelAndPhysicians() throws InterruptedException {
		IHGUtil.PrintMethodName();
		lnkImportPersonnelAndPhysicians.click();
		Thread.sleep(10000);
		return PageFactory.initElements(driver, ImportPersonnelAndPhysiciansPage.class);
	}

}
