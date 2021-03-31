// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import com.medfusion.common.utils.PropertyFileLoader;


public class PaypalTransactions extends BaseRest {
	
	protected PropertyFileLoader testData;
	Boolean flag = false;
	
	
	@BeforeTest
	 public void setBaseUri() throws Exception{
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();
			
	}
	
	@Test
	public void makePaypalAuthorizeCapture() throws Exception {
		makeAuthorizeCapture(testData.getProperty("paypalmmid"), flag);
		
	}
	
	@Test
	public void makeAPaypalSale() throws Exception {
		modulatorSale(testData.getProperty("paypalmmid"), flag);
	
	}
	
	@Test
	public void voidAPaypalSale() throws Exception {
		voidASale(testData.getProperty("paypalmmid"), flag);
		
	}
	
	@Test
	public void refundAPaypalSale() throws Exception {
		refundASale(testData.getProperty("paypalmmid"),testData.getProperty("fullrefundamount"), flag);
		
	}
	
	@Test
	public void partialRefundAPaypalSale() throws Exception {
		Boolean flag = false;
		refundASale(testData.getProperty("paypalmmid"),testData.getProperty("partialrefundamount"), flag);
		
	}
}
