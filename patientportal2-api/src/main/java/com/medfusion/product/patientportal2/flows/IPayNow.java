package com.medfusion.product.patientportal2.flows;

import com.medfusion.product.patientportal2.pojo.PortalBasic;
import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.pojo.PayNowInfo;

public interface IPayNow {

		/**
		 * Pay Now with random values
		 *
		 * @param driver
		 * @param portal URL is needed
		 * @return confirmation number if payment succeeds
		 * @throws Exception
		 */
		String payNowWithRandomValues(WebDriver driver, PortalBasic portal) throws Exception;

		/**
		 * Pay Now with given values
		 *
		 * @param driver
		 * @param portal URL is needed
		 * @param payNow CreditCard, patientName, dateOfBirth, patientAccountNumber, amount, location, paymentComment, emailAddress
		 * @return confirmation number if payment succeeds
		 * @throws Exception
		 */

		String payNow(WebDriver driver, PortalBasic portal, PayNowInfo payNow) throws Exception;
}
