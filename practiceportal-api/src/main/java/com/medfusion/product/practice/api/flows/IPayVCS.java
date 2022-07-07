package com.medfusion.product.practice.api.flows;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.VCSPaymentInfo;

/**
 * Interface for Practice portal VCS payment, will require PracticeInfo with credentials and PaymentInfo to pay with LOG OUT BEFORE CALLING THIS INTERFACE
 * 
 * @author Jakub
 * 
 */
public interface IPayVCS {
	/**
	 * Simple VCS payment using infos, requires driver and infos
	 */
	public boolean simplePay(WebDriver driver, Practice practiceInfo, VCSPaymentInfo paymentInfo) throws InterruptedException;
}
