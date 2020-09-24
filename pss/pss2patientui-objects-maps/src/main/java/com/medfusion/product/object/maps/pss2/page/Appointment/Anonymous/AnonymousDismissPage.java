//Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class AnonymousDismissPage extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//div[@id='myModalsss']//button[@class='dismissbuttons']")
	private WebElement dismissBtn;

	public AnonymousDismissPage(WebDriver driver, String url) {
		super(driver, url);
		
	}

	@Override
	public boolean areBasicPageElementsPresent() {
	
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(dismissBtn);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public HomePage clickDismiss() throws InterruptedException {
		commonMethods.highlightElement(dismissBtn);
		Thread.sleep(1000);
		dismissBtn.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

}
