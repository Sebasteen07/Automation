package com.intuit.ihg.product.phr.page.portaltophr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.page.PhrHomePage;



public class IntuitAcceptPrivacyPolicy extends BasePageObject{
	
	@FindBy(name="touCheckboxValue")
	private WebElement chkIntuitTermsofServiceAndPrivacyPolicy;
	
	@FindBy(css="input.custom_text_button")
	private WebElement btnAccept;
		

	public IntuitAcceptPrivacyPolicy(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Accept the terms and condition from phr side
	 * @return
	 */
	public PhrHomePage acceptIntuitTermsAndCondition()
	{
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		log("=============="+driver.getCurrentUrl()+"==============");
		log("=============="+driver.getPageSource().toString()+"==============");
		chkIntuitTermsofServiceAndPrivacyPolicy.click();
		btnAccept.click();
		return PageFactory.initElements(driver, PhrHomePage.class);
	}

}
