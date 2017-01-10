package com.medfusion.product.object.maps.practice.page.onlinebillpay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class OnlineBillPayVerifyPage extends BasePageObject {

	public OnlineBillPayVerifyPage(WebDriver driver) {
		super(driver);
	}

	public boolean MessageSentNotification() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		boolean result = false;

		try {
			result = driver.findElement(By.xpath("//span[contains(text(),'Message Sent')]")).isDisplayed();

		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

}
