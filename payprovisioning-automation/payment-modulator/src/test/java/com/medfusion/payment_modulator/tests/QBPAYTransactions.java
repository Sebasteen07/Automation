// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import com.medfusion.common.utils.PropertyFileLoader;


public class QBPAYTransactions extends BaseRest {
	
	protected PropertyFileLoader testData;
	Boolean flag = false;
	
	
	@BeforeTest
	 public void setBaseUri() throws Exception{
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();
			
	}
	
	
	@Test
	public void makeAQBPAYSale() throws Exception {
		modulatorSale(testData.getProperty("qbpaymmid"), flag);
	
	}
	
	@Test
	public void voidAQBPAYSale() throws Exception {
		voidASale(testData.getProperty("qbpaymmid"), flag);
		
	}
	
	@Test
	public void refundAQBPAYSale() throws Exception {
		refundASale(testData.getProperty("qbpaymmid"),testData.getProperty("fullrefundamount"), flag);
		
	}
	
	@Test
	public void partialRefundAQBPAYSale() throws Exception {
		refundASale(testData.getProperty("qbpaymmid"),testData.getProperty("partialrefundamount"), flag);
		
	}
}
