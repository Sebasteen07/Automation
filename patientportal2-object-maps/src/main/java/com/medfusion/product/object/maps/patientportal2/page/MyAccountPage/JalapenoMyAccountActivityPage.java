//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoMyAccountActivityPage extends JalapenoMyAccountPage {

		@FindBy(how = How.XPATH, using = "//*[@id='frame']/p")
		private WebElement tableUserLabel;

		public JalapenoMyAccountActivityPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public JalapenoMyAccountDevicesPage goToDevicesTab(WebDriver driver) {
				log("Click on My Devices");
				devicesTab.click();
				return PageFactory.initElements(driver, JalapenoMyAccountDevicesPage.class);
		}
}
