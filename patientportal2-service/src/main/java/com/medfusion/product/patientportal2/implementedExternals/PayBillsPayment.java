package com.medfusion.product.patientportal2.implementedExternals;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.flows.IPayBillsPayment;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.PatientInfo;
import com.medfusion.product.patientportal2.pojo.Portal;

public class PayBillsPayment implements IPayBillsPayment{

	//TODO JUST A STUB
	@Override
	public boolean payBillsPayment(WebDriver driver, Portal portInfo, PatientInfo patInfo, CreditCard cardInfo) {
		try{
			driver.navigate();
		}
		catch (Exception e){
			return false;
		}
		return false;
	}

}
