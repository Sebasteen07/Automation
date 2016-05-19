package com.medfusion.product.patientportal2.flows;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.PatientInfo;
import com.medfusion.product.patientportal2.pojo.Portal;

public interface IPayBillsPayment {
	public boolean payBillsPayment(WebDriver driver, Portal portInfo, PatientInfo patInfo, CreditCard cardInfo);
}
