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
		modulatorSale(testData.getProperty("qbpay.mmid"), flag);
	
	}
	
	@Test
	public void voidAQBPAYSale() throws Exception {
		voidASale(testData.getProperty("qbpay.mmid"), flag);
		
	}
	
	@Test
	public void refundAQBPAYSale() throws Exception {
		refundASale(testData.getProperty("qbpay.mmid"),testData.getProperty("full.refund.amount"), flag);
		
	}
	
	@Test
	public void partialRefundAQBPAYSale() throws Exception {
		refundASale(testData.getProperty("qbpay.mmid"),testData.getProperty("partial.refund.amount"), flag);
		
	}
}
