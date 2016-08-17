package com.medfusion.product.object.maps.patientportal2.page.PayNow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoPayNowCompletePage extends IHGUtil {

	public JalapenoPayNowCompletePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
		IHGUtil.setFrame(driver, "iframebody");
	}

	public String getConfirmationNumber() {
		IHGUtil.PrintMethodName();
		String source = driver.getPageSource();
		return source.substring(source.indexOf("confirmation number is ") + 23, source.indexOf(". Please"));
	}
}
