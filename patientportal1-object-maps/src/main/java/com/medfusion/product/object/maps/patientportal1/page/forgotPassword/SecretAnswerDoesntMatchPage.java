package com.medfusion.product.object.maps.patientportal1.page.forgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.*;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class SecretAnswerDoesntMatchPage extends BasePageObject {

	@FindBy(xpath = "//a[contains(.,'OK, take me to my practice')]")
	private WebElement btnTakeMeToPractice;


	public SecretAnswerDoesntMatchPage(WebDriver driver) {
		super(driver);
	}

	public void verifyPracticeButtonPresent(WebDriver driver) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		assertTrue(IHGUtil.waitForElement(driver, 10, btnTakeMeToPractice));

	}

}
