package com.medfusion.product.jalapeno;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.PreferenceDeliveryPage.PreferenceDeliveryPage;

public class PreferenceDeliverySelection extends BaseTestNGWebDriver {

	public enum Method {ELECTRONIC, PAPER};
	
	public JalapenoHomePage SelectDeliveryMethod(WebDriver driver, Method method){
		
		PreferenceDeliveryPage preferenceDeliveryPage = new PreferenceDeliveryPage(driver);
		
		switch(method){
		
			case ELECTRONIC :
				preferenceDeliveryPage.clickElectronicPaymentPreference();
				break;
			case PAPER:
				preferenceDeliveryPage.clickPaperPaymentPreference();
				break;
		}
		
		preferenceDeliveryPage.clickOKButton();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
}
