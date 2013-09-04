package com.intuit.ihg.product.portal.page.myAccount.AccountActivity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class ViewAccountActivityPage extends BasePageObject {
	@FindBy(linkText = "View Account Activity")
	private WebElement viewAccountActivity;


	public ViewAccountActivityPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Click on view account activity
	 * @throws InterruptedException
	 * @throws Exception
	 */

	
	public void clickOnViewAccountActivity() throws InterruptedException, Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		log("Click on View Account Activity");
		viewAccountActivity.click();
	
	}


}
