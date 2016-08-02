package com.intuit.ihg.product.object.maps.phr.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;


public class PhrManagePermissionsPage extends BasePageObject{

	@FindBy(css="a[href*='emergencyTerms']")
	private WebElement btnAllowEmergencyAccess;

//	<a class="permission_anchortag1" href="/phr/ui/action/emergencyAccess.do">Edit</a>
	@FindBy(css="a[class*='permission_anchortag1']")
	private WebElement btnEdit;

	public PhrManagePermissionsPage(WebDriver driver) {
		super(driver);
	}

	public boolean hasAllowEmergencyAccess() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil ihgUtil = new IHGUtil(driver);
		return ihgUtil.isFoundBasedOnCssSelector( "a[href*='emergencyTerms']" );
	}

	public PhrEmergencyTermsOfUse clickAllowEmergencyAccess() {

		btnAllowEmergencyAccess.click();

		return PageFactory.initElements(driver, PhrEmergencyTermsOfUse.class);
	}
	

	public PhrEmergencyAccessPage clickEdit(){
		IHGUtil.PrintMethodName();
		btnEdit.click();
		return PageFactory.initElements(driver, PhrEmergencyAccessPage.class);
	}

}
