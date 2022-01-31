//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class ImportOrExportProgressPage extends BasePageObject {

	@FindBy(linkText = "Import Personnel and Physicians")
	private WebElement lnkImportPersonnelAndPhysicians;

	public ImportOrExportProgressPage(WebDriver driver) {
		super(driver);
	}

	public ImportPersonnelAndPhysiciansPage clickLinkImportPersonnelAndPhysicians() throws InterruptedException {
		IHGUtil.PrintMethodName();
		lnkImportPersonnelAndPhysicians.click();
		Thread.sleep(10000);
		return PageFactory.initElements(driver, ImportPersonnelAndPhysiciansPage.class);
	}

}
