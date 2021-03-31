// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.util.List;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.helpers.TransactionResourceDetails;

public class BaseRest {
	
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;


	public static void setupRequestSpecBuilder() throws IOException
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("baseurl");
		requestSpec	 = new RequestSpecBuilder()
	   .setContentType(ContentType.JSON).and()
	   .addFilter(new ResponseLoggingFilter())
	   .addFilter(new RequestLoggingFilter())
	   .build();
	}

	public void setupResponsetSpecBuilder()
	{
		responseSpec = new ResponseSpecBuilder()
	   .expectStatusCode(200)
	   .expectContentType(ContentType.JSON)
	   .build();

	}
	
	public void makeAuthorizeCapture(String mmid, Boolean flag) throws Exception {
		TransactionResourceDetails transaction = new TransactionResourceDetails();
		
		//POST authorize 
		List<String> transactiondetails = transaction.makeAnAuthorize(mmid, flag);
		String transactionid = transactiondetails.get(0);
		String orderid = transactiondetails.get(1);
		
		//POST capture
		transaction.makeACapture(mmid, transactionid, orderid);
		
	}
	
	public void makeASale(Boolean flag) throws Exception {
		modulatorSale(testData.getProperty("elementmmid"), flag);
	
	}
	
	public void voidASale(String mmid, Boolean flag) throws Exception {
		TransactionResourceDetails transaction = new TransactionResourceDetails();
		
		//Post a Sale
		List<String> transactiondetails = modulatorSale( mmid, flag);
		String transactionid = transactiondetails.get(0);
		
		//POST Void 
		transaction.makeAVoid(mmid, transactionid);
		
	}
	
	public void refundASale(String mmid, String refundamount, Boolean flag) throws Exception {
		TransactionResourceDetails transaction = new TransactionResourceDetails();
		
		//Post a Sale
		List<String> transactiondetails = modulatorSale(mmid, flag);
		String transactionid = transactiondetails.get(0);
		
		//POST refund 
		transaction.makeARefund(mmid,transactionid,refundamount);
		
	}
	
	public void chargeBackASale(String mmid, String chargebackurl, Boolean flag ) throws Exception {
		TransactionResourceDetails transaction = new TransactionResourceDetails();
		
		//Post a Sale
		List<String> transactiondetails = modulatorSale(mmid, flag);
		String transactionid = transactiondetails.get(0);
		String orderid = transactiondetails.get(1);
		
		//POST chargeback 
		transaction.makeAChargeback(mmid,transactionid,orderid,chargebackurl);
		
	}
	
	public void makePartialRefund(String mmid, String refundamount, Boolean flag) throws Exception {
		TransactionResourceDetails transaction = new TransactionResourceDetails();
		
		//Post a Sale
		List<String> transactiondetails = modulatorSale(mmid , flag);
		String transactionid = transactiondetails.get(0);
		
		//POST a partial refund 
		transaction.makeARefund(mmid,transactionid,refundamount);
		
	}
	
	public List<String> modulatorSale(String mmid, Boolean flag) throws Exception {
		TransactionResourceDetails transaction = new TransactionResourceDetails();
		
		//POST sale 
		List<String> transactiondetails = transaction.makeASale(mmid);
		return transactiondetails;
		
		
	}

	
	
	

}
