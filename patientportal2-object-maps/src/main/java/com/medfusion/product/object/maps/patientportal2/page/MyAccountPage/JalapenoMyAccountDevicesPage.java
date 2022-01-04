//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoMyAccountDevicesPage extends JalapenoMyAccountPage {

		@FindBy(how = How.XPATH, using = "//*[@id='devicemanagement']/div[2]/div/div/p")
		private WebElement devicesTable;
		
		@FindBy(how = How.XPATH, using = "//a[contains(text(),'Remove')]")
		private WebElement btnRemoveDevice;

		public JalapenoMyAccountDevicesPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
				driver.manage().window().maximize();
				PageFactory.initElements(driver, this);
		}
		
		public boolean isRemoveDeviceDisplayed() {
			boolean ret = false;
			try {
				ret = btnRemoveDevice.isDisplayed();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
		}

}
