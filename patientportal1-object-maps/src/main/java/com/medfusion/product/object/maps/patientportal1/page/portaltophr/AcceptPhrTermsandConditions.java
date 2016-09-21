package com.medfusion.product.object.maps.patientportal1.page.portaltophr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class AcceptPhrTermsandConditions extends BasePageObject {

	@FindBy(xpath = "//input[@name='accept']")
	private WebElement btnAccept;


	public AcceptPhrTermsandConditions(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Accept the Intuit terms and condition from portal side
	 * 
	 * @return
	 */
	public WebDriver clickbtnAccept() {
		IHGUtil.PrintMethodName();
		btnAccept.click();
		return driver;
		// return PageFactory.initElements(driver, Object.class);
	}

}
