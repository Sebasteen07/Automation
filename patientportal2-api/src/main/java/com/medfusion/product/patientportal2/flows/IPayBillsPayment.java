package com.medfusion.product.patientportal2.flows;

import com.medfusion.product.patientportal2.pojo.PortalBasic;
import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.PatientInfo;

public interface IPayBillsPayment {
		String payBillsPayment(WebDriver driver, PortalBasic portInfo, PatientInfo patInfo, CreditCard creditCard, String amount);

		String payBillsPayment(WebDriver driver, PortalBasic portInfo, PatientInfo patInfo, CreditCard creditCard, String amount, String location);
}
