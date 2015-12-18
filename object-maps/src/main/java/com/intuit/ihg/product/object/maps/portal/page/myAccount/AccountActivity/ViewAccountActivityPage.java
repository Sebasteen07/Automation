package com.intuit.ihg.product.object.maps.portal.page.myAccount.AccountActivity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class ViewAccountActivityPage extends BasePageObject {
	@FindBy(linkText = "View Account Activity")
	private WebElement viewAccountActivity;
	
	@FindBy(linkText = "Close Viewer")
	private WebElement closeAccountActivity;
	

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
		IHGUtil.waitForElement(driver, 20, viewAccountActivity);
		viewAccountActivity.click();
	
	}
	
	public boolean isAccountActivityDisplayed() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Activity Log']")));
		return IHGUtil.waitForElement(driver, 20, closeAccountActivity);
	}
	
	


}
