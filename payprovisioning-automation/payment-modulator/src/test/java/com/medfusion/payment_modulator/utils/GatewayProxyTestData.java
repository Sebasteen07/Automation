package com.medfusion.payment_modulator.utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;

public class GatewayProxyTestData extends GatewayProxyBaseTest {

	@DataProvider(name = "refund_data")
	public static Object[][] dataProvider() throws Exception {
		testData = new PropertyFileLoader();
		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {
				
				
				  { token," ",testData.getProperty("test.pay.customer.uuid")
				  ,"Testing refund with blank mmid ", testData.getProperty("customer.id"),
				  testData.getProperty("external.transaction.id"),testData.getProperty(
				  "order.id"), testData.getProperty("transaction.amount"),400,"For input string: \" \""},
				  
				  { token,testData.getProperty("proxy.mmid")," "
				  ,"Testing refund with blank customer uuid  ",
				  testData.getProperty("customer.id"),
				  testData.getProperty("external.transaction.id"),testData.getProperty(
				  "order.id"), testData.getProperty("transaction.amount"),404
				  ,"No message available"},
				  
				  { token,"2560807700",testData.getProperty("test.pay.customer.uuid")
				  ,"Testing refund with invalid mmid  ", testData.getProperty("customer.id"),
				  testData.getProperty("external.transaction.id"),testData.getProperty(
				  "order.id"), testData.getProperty("transaction.amount"),403 ," " },
				  
				  { token,testData.getProperty("proxy.mmid"),testData.getProperty(
				  "test.pay.customer.uuid"),"Testing refund with blank transaction id ",
				  testData.getProperty("customer.id"), " ",testData.getProperty("order.id"),
				  testData.getProperty("transaction.amount"),500,"500 Internal Server Error" },
				  
				  { token,testData.getProperty("proxy.mmid"),testData.getProperty(
				  "test.pay.customer.uuid"),"Testing refund with transaction amount as 0",
				  testData.getProperty("customer.id"),
				  testData.getProperty("external.transaction.id"),testData.getProperty(
				  "order.id"),"0",400 ,"Amount should be greater than zero"} ,
				  
				  { token,testData.getProperty("proxy.mmid"),testData.getProperty(
				  "test.pay.customer.uuid"),"Testing refund with diffrent customer Id ",
				  "87b7e73a-ddab-4849-9af9-467b32c95ef2",
				  testData.getProperty("external.transaction.id"),testData.getProperty(
				  "order.id"),
				  testData.getProperty("transaction.amount"),400,"CustomerId is invalid" }
				   
		};
	}

	@DataProvider(name = "test")
	public static Object[][] dpMethod() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] { { testData.getProperty("comment") }, };
	}

	@DataProvider(name = "txn_data_for_http_400_statuscodes")
	public Object[][] dpMethodForAuthorize() {
		return new Object[][]{
				{testData.getProperty("payment.source"),testData.getProperty("type"),
						testData.getProperty("card.number"),testData.getProperty("expiration.number"),
						testData.getProperty("test.pay.customer.uuid")+"erd", testData.getProperty("proxy.mmid")},
				{"",testData.getProperty("type"),testData.getProperty("card.number"),
						testData.getProperty("expiration.number"),testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid")},
				{testData.getProperty("payment.source"),"",testData.getProperty("card.number"),
						testData.getProperty("expiration.number"),testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid")},
				{testData.getProperty("payment.source"),testData.getProperty("type"),
						testData.getProperty("card.number"),testData.getProperty("expiration.number"),
						testData.getProperty("test.pay.customer.uuid"), ""},
				{"RFD",testData.getProperty("type"),
						testData.getProperty("card.number"),testData.getProperty("expiration.number"),
						testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid")},
				{testData.getProperty("payment.source"),"OP",
						testData.getProperty("card.number"),testData.getProperty("expiration.number"),
						testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid")},
		};
	}

}
