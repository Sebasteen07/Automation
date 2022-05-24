// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;

public class ModulatorTestData extends GatewayProxyBaseTest {

	@DataProvider(name = "mod_ele_authorize_invalid_data")
	public static Object[][] dataProvider() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found", "Merchant account for id 2560791219 not found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found", "" },
				{ testData.getProperty("element.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request", "Transaction amount cannot be less than 1" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 200, null, "CardNumber Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, null, "INVALID CARD INFO" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, null, "ExpirationMonth Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, null, "Invalid ExpirationMonth Format" },

		};
	}

	@DataProvider(name = "mod_ele_capture_invalid_data")
	public static Object[][] dataProviderCapture() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {
				
				  { "2560791219", testData.getProperty("external.transaction.id"),
				  testData.getProperty("transaction.amount"),
				  404, "Not Found" },
				  
				  { " ", testData.getProperty("external.transaction.id"),
				  testData.getProperty("transaction.amount"),
				  404, "Not Found" },
				  
				  { testData.getProperty("element.mmid"), null,
				  testData.getProperty("transaction.amount"),
				  200, "TransactionID required" },

				  { testData.getProperty("element.mmid"),
				  testData.getProperty("external.transaction.id"),
				  "0",
				  400, "Bad Request" },
				  
				  // Transaction with invalid legnth
				  
				  { testData.getProperty("element.mmid"), "12345",
				  testData.getProperty("transaction.amount"),
				  200, "TRANSACTION NOT FOUND" },

				  // Transaction Id with invalid special chars
				  
				  { testData.getProperty("element.mmid"), "$$$$$$$$$$$$$$$$",
				  testData.getProperty("transaction.amount"),
				  200, "TransactionID required" },

		};
	}

	@DataProvider(name = "mod_ele_capture_200_invalid_scenarios")
	public static Object[][] dataProviderCapture200Scenarios() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				// Transaction with invalid legnth

				{ testData.getProperty("element.mmid"), "12345",
						testData.getProperty("order.id"), testData.getProperty("account.number"),
						testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "TRANSACTION NOT FOUND" },

				{ testData.getProperty("element.mmid"), null,
						testData.getProperty("order.id"), testData.getProperty("account.number"),
						testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "TransactionID required" },

				// Transaction Id with invalid special chars

				{ testData.getProperty("element.mmid"), "$$$$$$$$$$$$$$$$",
						testData.getProperty("order.id"), testData.getProperty("account.number"),
						testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 200, "TransactionID required" },

				{ testData.getProperty("element.mmid"),
						testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), testData.getProperty("account.number"),
						"41111111111111112222",
						testData.getProperty("expiration.number"), 200, "Invalid Transaction Type" },
		};
	}

	@DataProvider(name = "mod_ele_sale_invalid_data")
	public static Object[][] dataProviderSale() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found", "Merchant account for id 2560791219 not found" },
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found", "" },
				{ testData.getProperty("element.mmid"), "0", testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 400, "Bad Request", "Transaction amount cannot be less than 1" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number"), 200, null, "CardNumber Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						"41111111111111112222", testData.getProperty("expiration.number"), 200, null, "INVALID CARD INFO" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 200, null, "ExpirationMonth Required" },

				{ testData.getProperty("element.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 200, null, "Invalid ExpirationMonth Format" },

		};
	}

	@DataProvider(name = "mod_ele_void_invalid_data")
	public static Object[][] dataProviderVoid() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found", "Merchant account for id 2560791219 not found" },

				{ " ", testData.getProperty("external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found", "" },

				{ testData.getProperty("element.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 500,
						"Internal Server Error", "Could not find transaction for paymentId = $$" },

				{ testData.getProperty("element.mmid"), null, testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 500,
						"Internal Server Error", "Null value was assigned to a property" },

		};
	}

	@DataProvider(name = "mod_ele_refund_invalid_data")
	public static Object[][] dataProviderRefund() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				{ "2560791219", testData.getProperty("external.transaction.id"),
						testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), 404, "Not Found", "Merchant account for id 2560791219 not found" },

				{ " ", testData.getProperty("external.transaction.id"), testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), 404,
						"Not Found", ""},

				{ testData.getProperty("element.mmid"), "$$", testData.getProperty("transaction.amount"),
						testData.getProperty("account.number"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "2301", 500, "Internal Server Error",
						"Could not find transaction for paymentId = $$" },
		};
	}

	@DataProvider(name = "mod_ele_chargeback_invalid_data")
	public static Object[][] dataProviderChargeBack() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {
				
				  { " ", testData.getProperty("external.transaction.id"),
				  testData.getProperty("order.id"), testData.getProperty("chargeback.amount"),
				  400, "Bad Request", "Failed to convert value of type" },
				  
				  { testData.getProperty("element.mmid"), " ",
				  testData.getProperty("order.id"), testData.getProperty("chargeback.amount"),
				  500, "Internal Server Error", "Could not find transaction" },
				  
				  { testData.getProperty("element.mmid"), "1234567",
				  testData.getProperty("order.id"), testData.getProperty("chargeback.amount"),
				  500, "Internal Server Error", "Could not find transaction" },
				  
				  //Not validating blank order Id ,able to proceed
				  
				  { testData.getProperty("element.mmid"), testData.getProperty("external.transaction.id"), "",
				  testData.getProperty("chargeback.amount"),500, "Internal Server Error", "Could not find transaction" },

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

	@DataProvider(name = "mod_instamed_sale_invalid_data")
	public static Object[][] dataProviderInstamed() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {

				//Non-Instamed mmid
				{ "2560819831", testData.getProperty("transaction.amount"), testData.getProperty("cvv"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 404,
						"Not Found", "Merchant account for id 2560819831 not found" },

				//No mmid
				{ " ", testData.getProperty("transaction.amount"), testData.getProperty("cvv"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 404,
						"Not Found", "" },

				//mmid in characters
				{ "!@#$", testData.getProperty("transaction.amount"), testData.getProperty("cvv"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 400,
						"Bad Request", "For input string: \"!@#$\"" },

				//0 transaction amount
				{ testData.getProperty("instamed.mmid"), "0", testData.getProperty("cvv"),
						testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 400,
						"Bad Request", "Transaction amount cannot be less than 1"},

				//blank card number
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"), "",
						testData.getProperty("expiration.number1"), 400, "Bad Request",
						null },

				//incorrect card number
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"), "5424180279791711",
						testData.getProperty("expiration.number1"), 400, "Bad Request", null },

				//characters for card number
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"), "!@#$%^&*!@#",
						testData.getProperty("expiration.number1"), 400, "Bad Request", null },

				//without expiry date
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "", 400, "Bad Request", null },

				//expired date
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "1218", 400, "Bad Request", null },

				//incorrect expiry format
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "12-25", 400, "Bad Request", null},

				//incorrect expiry format
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "25-11", 400, "Bad Request", null },

				//characters as expiry format
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), "**!@", 400, "Bad Request", null },

				//MM/YY expiry format
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), testData.getProperty("payment.source"),
						testData.getProperty("card.number"), testData.getProperty("instamed.card.expiration.number"),
						200, "Approved", null },

				//characters as CVV number
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						"$#%", testData.getProperty("payment.source"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 400, "Bad Request", null },

				//Invalid payment source
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), "ABC", testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 400,
						"Bad Request", "Payment source provided is not valid" },

				//Characters as payment source
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), "#$%", testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 400,
						"Bad Request", "Payment source provided is not valid" },

				//Null payment source
				{ testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
						testData.getProperty("cvv"), "", testData.getProperty("card.number"),
						testData.getProperty("expiration.number1"), 400,
						"Bad Request", "Payment source provided is not valid" },

		};
	}

	@DataProvider(name = "mod_instamed_positive_scenarios")
	public static Object[][] dataProviderPositiveFlows() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {
				//Payment source for patient portal, VISA as card type
				{ testData.getProperty("transaction.amount"), testData.getProperty("card.number"),
						testData.getProperty("type"), testData.getProperty("cvv"),
						testData.getProperty("payment.source.patient.portal"), "Approved", "000"},

				//Precheck-Copay payment source, DISCOVER as card type
				{ testData.getProperty("transaction.amount"), testData.getProperty("instamed.card.number.discover"),
						testData.getProperty("card.type.discover"), testData.getProperty("cvv"),
						testData.getProperty("payment.source.precheck.copay"), "Approved", "000"},

				//Precheck-Balance payment source, AMERICAN EXPRESS as card type
				{ testData.getProperty("transaction.amount"), testData.getProperty("instamed.card.number.amex"),
						testData.getProperty("card.type.amex"), testData.getProperty("cvv.amex"),
						testData.getProperty("payment.source.precheck.balance"), "Approved", "000"},

				//VCS Payment source, MASTERCARD as card type
				{ testData.getProperty("transaction.amount"), testData.getProperty("instamed.card.number.mc"),
						testData.getProperty("card.type.mastercard"), testData.getProperty("cvv"),
						testData.getProperty("payment.source"), "Approved", "000"},

				//Declined Request
				{ testData.getProperty("transaction.amount.decline"), testData.getProperty("card.number"),
						testData.getProperty("type"), testData.getProperty("cvv"),
						testData.getProperty("payment.source.patient.portal"), "Declined", "005"},

				//Partial Approved Request
				{ testData.getProperty("transaction.amount.partial.approval"), testData.getProperty("card.number"),
						testData.getProperty("type"), testData.getProperty("cvv"),
						testData.getProperty("payment.source.patient.portal"), "Declined", "051"},
		};
	}

	@DataProvider(name = "mod_instamed_different_merchants")
	public static Object[][] dataProviderDifferentMerchants() throws Exception {
		testData = new PropertyFileLoader();

		return new Object[][] {
				//Merchant without Terminal ID for PAYN
				{ testData.getProperty("instamed.mmid.without.terminalid"),
						testData.getProperty("payment.source.patient.portal"), 400, "Bad Request",
						"Payment source provided is not valid for merchant" },

				//Merchant with incorrect Terminal ID for PAYN
				{ testData.getProperty("instamed.mmid.incorrect.terminalid"),
						testData.getProperty("payment.source.patient.portal"), 400, "Bad Request", null },

				//Merchant without Terminal ID for PRCC
				{ testData.getProperty("instamed.mmid.without.terminalid"),
						testData.getProperty("payment.source.precheck.balance"), 400, "Bad Request",
						"Payment source provided is not valid for merchant"  },

				//Merchant with incorrect Terminal ID for PRCC
				{ testData.getProperty("instamed.mmid.incorrect.terminalid"),
						testData.getProperty("payment.source.precheck.balance"), 400, "Bad Request", null },

				//Merchant without correct store ID
				{ testData.getProperty("instamed.mmid.incorrect.storeid"),
						testData.getProperty("payment.source.patient.portal"), 400, "Bad Request", null },

				//Merchant without correct Merchant ID
				{ testData.getProperty("instamed.mmid.incorrect.mid"),
						testData.getProperty("payment.source.patient.portal"), 400, "Bad Request", null },

//				//Merchant with incorrect Client ID & Client Secret
				{ testData.getProperty("instamed.mmid.incorrect.clientid.secret"),
				testData.getProperty("payment.source.patient.portal"), 401, "Unauthorized",
						"Invalid API key/secret pair." },

				//Merchant without Client ID & Client Secret
				{ testData.getProperty("instamed.mmid.without.clientid.secret"),
						testData.getProperty("payment.source.patient.portal"), 400, "Bad Request",
						"Missing API key/secret pair" },

		};
	}

}
