// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;



public class ElementTransactions extends BaseRest{
	
	protected static PropertyFileLoader testData;
	Boolean flag = true;
	
	@BeforeTest
	public void setUp() throws IOException{
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();
	}	
	
	@Test
	public void makeElementAuthorizeCapture() throws Exception {
		makeAuthorizeCapture(testData.getProperty("element.mmid"), flag);
		
	}
	
	@Test
	public void makeAnElementSale() throws Exception {
		modulatorSale(testData.getProperty("element.mmid"), flag);
	
	}
	
	@Test
	public void voidAnElementSale() throws Exception {
		voidASale(testData.getProperty("element.mmid"), flag);
		
	}
	
	@Test
	public void refundAnElementSale() throws Exception {
		refundASale(testData.getProperty("element.mmid"),testData.getProperty("full.refund.amount"), flag);
		
	}
	
	@Test
	public void partialRefundAnElementSale() throws Exception {
		refundASale(testData.getProperty("element.mmid"),testData.getProperty("partial.refund.amount"), flag);
		
	}
	
	@Test
	public void createChargeBack() throws Exception {
		chargeBackASale(testData.getProperty("element.mmid"),testData.getProperty("chargeback.from.moduator"), flag);
		
	}

	
	
	

}
