// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;



public class ElementTransactions extends BaseRest{
	
	protected static PropertyFileLoader testData;
	
	@BeforeTest
	public void setUp() throws IOException{
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();
	}	
	
	@Test
	public void makeElementAuthorizeCapture() throws Exception {
		Boolean flag = true;
		makeAuthorizeCapture(testData.getProperty("elementmmid"), flag);
		
	}
	
	@Test
	public void makeAnElementSale() throws Exception {
		Boolean flag = true;
		modulatorSale(testData.getProperty("elementmmid"), flag);
	
	}
	
	@Test
	public void voidAnElementSale() throws Exception {
		Boolean flag = true;
		voidASale(testData.getProperty("elementmmid"), flag);
		
	}
	
	@Test
	public void refundAnElementSale() throws Exception {
		Boolean flag = true;
		refundASale(testData.getProperty("elementmmid"),testData.getProperty("fullrefundamount"), flag);
		
	}
	
	@Test
	public void partialRefundAnElementSale() throws Exception {
		Boolean flag = true;
		refundASale(testData.getProperty("elementmmid"),testData.getProperty("partialrefundamount"), flag);
		
	}
	
	@Test
	public void createChargeBack() throws Exception {
		Boolean flag = true;
		chargeBackASale(testData.getProperty("elementmmid"),testData.getProperty("chargebackfrommoduator"), flag);
		
	}

	
	
	

}
