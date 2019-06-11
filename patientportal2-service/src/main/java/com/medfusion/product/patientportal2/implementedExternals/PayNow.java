package com.medfusion.product.patientportal2.implementedExternals;

import static org.testng.AssertJUnit.assertTrue;

import com.medfusion.product.patientportal2.pojo.PortalBasic;
import org.openqa.selenium.WebDriver;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowCompletePage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowPage;
import com.medfusion.product.patientportal2.flows.IPayNow;
import com.medfusion.product.patientportal2.pojo.PayNowInfo;

public class PayNow implements IPayNow {

		@Override
		public String payNowWithRandomValues(WebDriver driver, PortalBasic portal) throws Exception {
				PayNowInfo payInfo = new PayNowInfo();
				return payNow(driver, portal, payInfo);
		}

		@Override
		public String payNow(WebDriver driver, PortalBasic portal, PayNowInfo payNowInfo) throws Exception {
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, portal.url);
				JalapenoPayNowPage payNowPage = loginPage.clickPayNowButton();
				assertTrue(payNowPage.assessPayNowPageElements());
				payNowPage.fillInputs(driver, payNowInfo);
				JalapenoPayNowCompletePage payDonePage = payNowPage.processPayment();
				return (payDonePage.getConfirmationNumber());
		}
}
