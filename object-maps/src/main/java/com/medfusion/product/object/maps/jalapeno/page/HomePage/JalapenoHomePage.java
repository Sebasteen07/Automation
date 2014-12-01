package com.medfusion.product.object.maps.jalapeno.page.HomePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;

public class JalapenoHomePage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "signout")
	private WebElement signOutButton;
	
	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
		
		}

	public JalapenoLoginPage logout(WebDriver driver) {
		
		signOutButton.click();
		
		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}
}
