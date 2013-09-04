package com.intuit.ihg.product.phr.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;


public class PhrSharingPage extends BasePageObject{


	@FindBy(css="a[class*='linkToSharing']")
	private WebElement btnSharing;

	// <a class="linkToPermissions" href="/phr/ui/action/sharing.do">Manage Permissions</a>

	@FindBy(css="a[class*='linkToPermissions']")
	private WebElement btnManage;

	public PhrSharingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public PhrSharingPrivacySettingsPage clickSharingAndPrivacySettings() {	

		IHGUtil.PrintMethodName();

		btnSharing.click();

		return PageFactory.initElements(driver, PhrSharingPrivacySettingsPage.class);
	}

	public PhrManagePermissionsPage clickManagePermissionsPage() {	

		IHGUtil.PrintMethodName();

		btnManage.click();

		return PageFactory.initElements(driver, PhrManagePermissionsPage.class);
	}

}
