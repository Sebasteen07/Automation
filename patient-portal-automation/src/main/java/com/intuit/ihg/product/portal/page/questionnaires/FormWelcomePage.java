package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormWelcomePage extends BasePageObject
{


	public FormWelcomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath="//a[contains(@href,'demographics')]")
	private WebElement btnContinue;


	/**
	 * @Description:Click on Continue Button
	 * @return
	 * @throws Exception
	 */
	public FormBasicInfoPage clickContinueButton() throws Exception{
		IHGUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		btnContinue.click();
		return PageFactory.initElements(driver, FormBasicInfoPage.class);

	}

}
