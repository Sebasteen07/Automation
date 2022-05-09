package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;

public class FormsPage extends BaseTest {

		public FormsPage(WebDriver driver) {
			PageFactory.initElements(driver, this);
		}

}
