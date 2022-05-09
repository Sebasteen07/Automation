package com.medfusion.payment_modulator.utils;

import java.io.IOException;

import com.medfusion.common.utils.IHGUtil;
import org.testng.annotations.DataProvider;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.gateway_proxy.utils.MPUsersUtility;

import io.restassured.response.Response;

public class ModulatorTestData extends GatewayProxyBaseTest {

	@DataProvider(name = "mod_ele_authorize_invalid_data")
	public static Object[][] dataProvider() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ testData.getProperty("element.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 200, "CardNumber Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, "INVALID CARD INFO" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, "ExpirationMonth Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "Invalid ExpirationMonth Format" },

		};
	}

	@DataProvider(name = "mod_ele_capture_invalid_data")
	public static Object[][] dataProviderCapture() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {
				
				
				  { "2560791219", testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 404, "Not Found" },
				  
				  { " ", testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 404, "Not Found" },
				  
				  { testData.getProperty("element.mmid"), " ",
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 200, "TransactionID required" },
				  
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 200, "Invalid Transaction Type" },
				  
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), "0",
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 400, "Bad Request" },
				  
				  // Comment its approving currently INVALID CARD INFO
				  
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), "",
				  testData.getProperty("expiration.number"), 200, "Invalid Transaction Type" },
				 
				  
				  // message from auth INVALID CARD INFO getting Invalid Transaction Status
				  
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), "41111111111111112222",
				  testData.getProperty("expiration.number"), 200, "Invalid Transaction Type" },

				  // auth ExpirationMonth Required getting Invalid Transaction Status
				  
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  " ",200, "Invalid Transaction Type" },
				  
				  // auth Invalid ExpirationMonth Format Invalid Transaction Status
				  
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  "2301", 200, "Invalid Transaction Type" },
				  
				  
				  // Transaction with invalid legnth
				  
				  { testData.getProperty("element.mmid"), "12345",
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 200, "TRANSACTION NOT FOUND" },
				  
				  // Transaction from diffrent env added as invalid
				  {
				  testData.getProperty("element.mmid"), "1635954508",
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 200, "TRANSACTION NOT FOUND" },
				  
				  // Transaction Id with invalid special chars
				  
				  { testData.getProperty("element.mmid"), "$$$$$$$$$$$$$$$$",
				  testData.getProperty("order.id"), testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 200, "TransactionID required" },
				  
				  // Order Id from diffrent env - got internal server error but error message
				  // should be diffrent 
				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  "bf9fd5db-82b4-4be0-9d01-f30f118943b",
				  testData.getProperty("transaction.amount"),
				  testData.getProperty("account.number"),
				  testData.getProperty("payment.source"), testData.getProperty("card.number"),
				  testData.getProperty("expiration.number"), 500, "Internal Server Error" },
				 
				 
		};
	}

	@DataProvider(name = "mod_ele_sale_invalid_data")
	public static Object[][] dataProviderSale() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ testData.getProperty("element.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 200, "CardNumber Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, "INVALID CARD INFO" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, "ExpirationMonth Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "Invalid ExpirationMonth Format" },

		};
	}

	@DataProvider(name = "mod_ele_void_invalid_data")
	public static Object[][] dataProviderVoid() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ " ", testData.getProperty("external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found" },

				// With account no blank is able to void

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "", testData.getProperty("expiration.number"), 200,
						"Invalid Transaction Status" },

				// Getting error message as Invalid Transaction Status instead of

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "41111111111111112222",
						testData.getProperty("expiration.number"), 200, "Invalid Transaction Status" },

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"), "", 200,
						"Invalid Transaction Status" },

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"), "2301", 200,
						"Invalid Transaction Status" },

				// Diffrent env transaction Id

				{ testData.getProperty("element.mmid"), "1635954508", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "TRANSACTION NOT FOUND" },

				{ testData.getProperty("element.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Unexpected Server Error" },

		};
	}

	@DataProvider(name = "mod_ele_refund_invalid_data")
	public static Object[][] dataProviderRefund() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ " ", testData.getProperty("external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found" },

				// With account no blank is able to void

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "", testData.getProperty("expiration.number"), 200,
						"CardNumber Required" },

				// Getting error message as Invalid Transaction Status instead of

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "41111111111111112222",
						testData.getProperty("expiration.number"), 200, "INVALID CARD INFO" },

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"), "", 200,
						"ExpirationMonth Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"), "2301", 200,
						"Invalid ExpirationMonth Format" },

				// Diffrent env transaction Id

				{ testData.getProperty("element.mmid"), "1111111111", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "0199", 500, "Unexpected Server Error" },

				{ testData.getProperty("element.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Unexpected Server Error" },

				// Currently failing as no check on payment source
				/*
				 * { testData.getProperty("element.mmid"),testData.getProperty(
				 * "external.transaction.id"), testData.getProperty("transaction.amount"),
				 * testData.getProperty("account.number"), "IV",
				 * testData.getProperty("card.number"), "0199", 500, "Unexpected Server Error"
				 * },
				 */
		};
	}

	@DataProvider(name = "mod_ele_chargeback_invalid_data")
	public static Object[][] dataProviderChargeBack() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {
				
				  { " ", testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("chargeback.amount"),
				  404, "Not Found" },
				  
				  { testData.getProperty("element.mmid"), " ",
				  testData.getProperty("order.id"), testData.getProperty("chargeback.amount"),
				  500, "Error retrieving transaction for paymentId" },
				  
				  { testData.getProperty("element.mmid"), "1234567",
				  testData.getProperty("order.id"), testData.getProperty("chargeback.amount"),
				  500, "Error retrieving transaction for paymentId" },
				  
				  //Not validating blank order Id ,able to proceed
				  
				  { testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"), "",
				  testData.getProperty("chargeback.amount"),200, "Not Found" },

		};
	}

	@DataProvider(name = "test")
	public static Object[][] dpMethod() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] { { testData.getProperty("comment") }, };
	}

	@DataProvider(name = "mod_paypal_authorize_invalid_data")
	public static Object[][] dataProviderPaypalAuth() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ testData.getProperty("paypal.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 200, "Invalid account number" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, "Invalid account number" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, "Invalid expiration date:" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "Invalid expiration date: 2301" },

		};
	}

	@DataProvider(name = "mod_paypal_capture_invalid_data")
	public static Object[][] dataProviderPaypalCapture() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560809200", testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" }, //400 - Not Found in demo

				{ " ", testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ testData.getProperty("paypal.mmid"), " ",
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "Approved" },

				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" }, //200 Approved in demo

				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), "0",
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "Approved" },

				// Comment its approving currently INVALID CARD INFO

				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 500, "Internal Server Error" }, //200 Approved in demo

				// message from auth INVALID CARD INFO getting Invalid Transaction Status

				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "41111111111111112222",
						testData.getProperty("expiration.number"), 500, "Internal Server Error" }, //200 Approved

				// auth ExpirationMonth Required getting Invalid Transaction Status

				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						" ",500, "Internal Server Error" },   //200 Approved

				// auth Invalid ExpirationMonth Format Invalid Transaction Status

				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						"2301", 500, "Internal Server Error" },    //200 Approved


				// Transaction with invalid legnth

				{ testData.getProperty("paypal.mmid"), "12345",
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				// Transaction from diffrent env added as invalid
				{
						testData.getProperty("paypal.mmid"), "1635954508",
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				// Transaction Id with invalid special chars

				{ testData.getProperty("paypal.mmid"), "$$$$$$$$$$$$$$$$",
						testData.getProperty("paypal.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "Approved" },

				// Order Id from diffrent env - got internal server error but error message
				// should be diffrent
				{ testData.getProperty("paypal.mmid"),
						testData.getProperty("paypal.external.txn.id"),
						"bf9fd5db-82b4-4be0-9d01-f30f118943b",
						testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" }, //200 Approved

		};
	}

	@DataProvider(name = "mod_paypal_sale_invalid_data")
	public static Object[][] dataProviderPaypalSale() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ testData.getProperty("paypal.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 200, "Invalid account number" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, "Invalid account number" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, "Invalid expiration date:" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "Invalid expiration date: 2301" },

		};
	}

	@DataProvider(name = "mod_paypal_void_invalid_data")
	public static Object[][] dataProviderPaypalVoid() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				{ " ", testData.getProperty("paypal.external.txn.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found" },

				// With account no blank is able to void

				{ testData.getProperty("paypal.mmid"), testData.getProperty("paypal.external.txn.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "", testData.getProperty("expiration.number"), 500,
						"Internal Server Error" },

				// Getting error message as Invalid Transaction Status instead of

				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "41111111111111112222",
						testData.getProperty("expiration.number"), 200, "Invalid Transaction Status" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"), "", 200,
						"Invalid Transaction Status" },

				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"), "2301", 200,
						"Invalid Transaction Status" },

				// Diffrent env transaction Id

				{ testData.getProperty("paypal.mmid"), "1635954508", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "TRANSACTION NOT FOUND" },

				{ testData.getProperty("paypal.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Unexpected Server Error" },

		};
	}

	@DataProvider(name = "mod_paypal_refund_invalid_data")
	public static Object[][] dataProviderPaypalRefund() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ " ", testData.getProperty("external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found" },

				// With account no blank is able to void

//				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), "", testData.getProperty("expiration.number"), 200,
//						"CardNumber Required" },
//
//				// Getting error message as Invalid Transaction Status instead of
//
//				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), "41111111111111112222",
//						testData.getProperty("expiration.number"), 200, "INVALID CARD INFO" },
//
//				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"), "", 200,
//						"ExpirationMonth Required" },
//
//				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"), "2301", 200,
//						"Invalid ExpirationMonth Format" },

				// Diffrent env transaction Id

				{ testData.getProperty("paypal.mmid"), "1111111111", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "0199", 500, "Unexpected Server Error" },

				{ testData.getProperty("paypal.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Unexpected Server Error" },

//				{ testData.getProperty("paypal.mmid"), testData.getProperty("external.transaction.id"),
//						IHGUtil.createRandomNumericString(3), testData.getProperty("account.number"), "IV",
//						testData.getProperty("card.number"), "0199", 200, "Approved"},

		};
	}

	@DataProvider(name = "mod_qbpay_sale_invalid_data")
	public static Object[][] dataProviderQBPayAuth() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ testData.getProperty("qbpay.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, "card.number is invalid." },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, "ExpirationYear. is invalid." },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "invalid." },

		};
	}

	@DataProvider(name = "mod_qbpay_void_invalid_data")
	public static Object[][] dataProviderQBPayVoid() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

//				{ "2560791219", testData.getProperty("qbpay.external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"),
//						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				{ " ", testData.getProperty("qbpay.external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found" },

				// With account no blank is able to void

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("qbpay.external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), "", testData.getProperty("expiration.number"), 500,
//						"Internal Server Error" },

				// Getting error message as Invalid Transaction Status instead of

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("qbpay.external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), "41111111111111112222",
//						testData.getProperty("expiration.number"), 200, "Approved" },

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("qbpay.external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"), "", 200,
//						"Invalid Transaction Status" },
//
//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("qbpay.external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"), "2301", 200,
//						"Invalid Transaction Status" },

				// Diffrent env transaction Id

				{ testData.getProperty("qbpay.mmid"), "1635954508", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "TRANSACTION NOT FOUND" },

				{ testData.getProperty("qbpay.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Unexpected Server Error" },

		};
	}

	@DataProvider(name = "mod_qbpay_refund_invalid_data")
	public static Object[][] dataProviderQBPayRefund() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ " ", testData.getProperty("external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found" },

				// With account no blank is able to void

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "", testData.getProperty("expiration.number"), 500,
						"Internal Server Error" },

				// Getting error message as Invalid Transaction Status instead of

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), "41111111111111112222",
//						testData.getProperty("expiration.number"), 200, "amount is invalid." },

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"), "", 200,
//						"amount is invalid." },

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("external.transaction.id"),
//						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
//						testData.getProperty("payment.source"), testData.getProperty("card.number"), "2301", 200,
//						"amount is invalid." },

				// Diffrent env transaction Id

				{ testData.getProperty("qbpay.mmid"), "1111111111", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "0199", 500, "Unexpected Server Error" },

				{ testData.getProperty("qbpay.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Unexpected Server Error" },

//				{ testData.getProperty("qbpay.mmid"), testData.getProperty("external.transaction.id"),
//						IHGUtil.createRandomNumericString(3), testData.getProperty("account.number"), "IV",
//						testData.getProperty("card.number"), "0199", 200, "amount is invalid."},

		};
	}

	@DataProvider(name = "mod_qbpay_authorize_invalid_data")
	public static Object[][] dataProviderQBPAYAuth() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },
				{ testData.getProperty("qbpay.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, "card.number is invalid." },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, "ExpirationYear. is invalid." },

				{ testData.getProperty("qbpay.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, "card.expmonth is invalid." },

		};
	}

	@DataProvider(name = "mod_qbpay_capture_invalid_data")
	public static Object[][] dataProviderQBPAYCapture() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560809200", testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				{ " ", testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found" },

				{ testData.getProperty("qbpay.mmid"), " ",
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "Approved" },

				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), "0",
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request" },

				// Comment its approving currently INVALID CARD INFO

				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				// message from auth INVALID CARD INFO getting Invalid Transaction Status

				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), "41111111111111112222",
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				// auth ExpirationMonth Required getting Invalid Transaction Status

				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						" ",500, "Internal Server Error" },

				// auth Invalid ExpirationMonth Format Invalid Transaction Status

				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						"2301", 500, "Internal Server Error" },


				// Transaction with invalid legnth

				{ testData.getProperty("qbpay.mmid"), "12345",
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				// Transaction from diffrent env added as invalid
				{
						testData.getProperty("qbpay.mmid"), "1635954508",
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },

				// Transaction Id with invalid special chars

				{ testData.getProperty("qbpay.mmid"), "$$$$$$$$$$$$$$$$",
						testData.getProperty("qbpay.order.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "Approved" },

				// Order Id from diffrent env - got internal server error but error message
				// should be diffrent
				{ testData.getProperty("qbpay.mmid"),
						testData.getProperty("qbpay.external.transaction.id"),
						"bf9fd5db-82b4-4be0-9d01-f30f118943b",
						testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 500, "Internal Server Error" },
		};
	}

}
