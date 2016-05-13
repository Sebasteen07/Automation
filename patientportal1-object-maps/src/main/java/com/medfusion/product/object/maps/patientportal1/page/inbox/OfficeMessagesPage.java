package com.medfusion.product.object.maps.patientportal1.page.inbox;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class OfficeMessagesPage extends BasePageObject {

	public OfficeMessagesPage(WebDriver driver) {
		super(driver);

	}
	
	public boolean findUnreadMessage(String sSubject) throws InterruptedException {

		IHGUtil.PrintMethodName();
		PortalUtil.setConsolidatedInboxFrame(driver);
		Thread.sleep(8000);
		boolean unreadMessageFound = false;

		// Looking for the unread messages with specific which can be
		// located by specific CSS selector
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> message = driver.findElements(By.cssSelector(".messageArea tr.unread"));

		for (WebElement webElement : message) {

			if (webElement.getText().contains(sSubject)) {
				unreadMessageFound = true;
			}
		}
		// Setting back to 30 as it is default value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return unreadMessageFound;
	}
}
