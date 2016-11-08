package com.medfusion.product.patientportal2.flows;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.pojo.Jalapeno;
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
	String payNowWithRandomValues(WebDriver driver, Jalapeno portal) throws Exception;

	/**
	 * Pay Now with given values
	 * 
	 * @param driver
	 * @param portal URL is needed
	 * @param payNow CreditCard, patientName, dateOfBirth, patientAccountNumber, amount, location,
	 *        paymentComment, emailAddress
	 * @return confirmation number if payment succeeds
	 * @throws Exception
	 */

	String payNow(WebDriver driver, Jalapeno portal, PayNowInfo payNow) throws Exception;
}
