package com.medfusion.gateway_proxy.utils;

import java.io.IOException;

import com.medfusion.common.utils.IHGUtil;
import org.testng.annotations.DataProvider;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.gateway_proxy.utils.MPUsersUtility;

public class GatewayProxyTestData extends GatewayProxyBaseTest {
	String env = GatewayProxyUtils.getEnvironmentType().toString();

	@DataProvider(name = "refund_data")
	public static Object[][] dataProvider() throws Exception {
		testData = new PropertyFileLoader();
		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {

				{ token, " ", testData.getProperty("test.pay.customer.uuid"), "Testing refund with blank mmid ",
						testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), testData.getProperty("transaction.amount"), 500,
						"HTTP 500 Internal Server Error" },

				{ token, testData.getProperty("proxy.mmid"), " ", "Testing refund with blank customer uuid  ",
						testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), testData.getProperty("transaction.amount"), 404,
						"No message available" },

				{ token, "2560807700", testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with invalid mmid  ", testData.getProperty("customer.id"),
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"),
						testData.getProperty("transaction.amount"), 403, " " },

				{ token, testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with blank transaction id ", testData.getProperty("customer.id"), " ",
						testData.getProperty("order.id"), testData.getProperty("transaction.amount"), 500,
						"Could not find transaction" },

				{ token, testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with transaction amount as 0", testData.getProperty("customer.id"),
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), "0", 400,
						"Amount should be greater than zero" },

				{ token, testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with diffrent customer Id ", "87b7e73a-ddab-4849-9af9-467b32c95ef2",
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"),
						testData.getProperty("transaction.amount"), 400, "CustomerId is invalid" }

		};
	}

	@DataProvider(name = "void_data")
	public static Object[][] dataProvider1() throws Exception {
		testData = new PropertyFileLoader();
		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {

				{ token, " ", testData.getProperty("test.pay.customer.uuid"), "Testing refund with blank mmid ",
						testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), 500, "HTTP 500 Internal Server Error" },

				{ token, testData.getProperty("proxy.mmid"), " ", "Testing refund with blank customer uuid  ",
						testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), 404, "No message available" },

				{ token, "2560807700", testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with invalid mmid  ", testData.getProperty("customer.id"),
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), 403, " " },

				{ token, testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with blank transaction id ", testData.getProperty("customer.id"), " ",
						testData.getProperty("order.id"), 500, "Could not find transaction" },

				{ token, testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with diffrent customer Id ", "12855",
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), 400,
						"CustomerId is invalid" }

		};
	}

	@DataProvider(name = "test")
	public static Object[][] dpMethod() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] { { testData.getProperty("comment") }, };
	}

	@DataProvider(name = "txn_data_for_http_400_statuscodes")
	public Object[][] dpMethodForAuthorizeAndCapture() {
		return new Object[][] {
				{ testData.getProperty("payment.source"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("test.pay.customer.uuid") + "erd", testData.getProperty("proxy.mmid") },
				{ "", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid") },
				{ testData.getProperty("payment.source"), "", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid") },
				{ testData.getProperty("payment.source"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("test.pay.customer.uuid"), "" },
				{ "RFD", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid") },
				{ testData.getProperty("payment.source"), "OP", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid") }, };
	}

	@DataProvider(name = "card_details")
	public Object[][] dpMethodDigitalWallet() throws IOException {
		PropertyFileLoad testData = new PropertyFileLoad(env);
		return new Object[][] {
				{testData.getProperty("test.pay.customer.uuid"),  "", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{testData.getProperty("test.pay.customer.uuid"), testData.getProperty("consumer.name"), "", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{testData.getProperty("test.pay.customer.uuid"), testData.getProperty("consumer.name"), testData.getProperty("type"), "",
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{testData.getProperty("test.pay.customer.uuid"), testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), "1220", testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{testData.getProperty("test.pay.customer.uuid"), testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), "", true },
				{testData.getProperty("test.pay.customer.uuid"), testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), "", true },
						};
	}

	@DataProvider(name = "txn_data_for_sale")
	public Object[][] dpMethodDigitalWalletSale() {
		return new Object[][] {
				{ testData.getProperty("test.pay.customer.uuid") + "hgf", testData.getProperty("proxy.mmid"),
						testData.getProperty("external.wallet.id"), testData.getProperty("external.card.id"),
						Integer.parseInt(IHGUtil.createRandomNumericString(4)) },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("external.wallet.id"), testData.getProperty("external.card.id"), 0 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("external.wallet.id"), testData.getProperty("external.card.id") + "iut",
						Integer.parseInt(IHGUtil.createRandomNumericString(4)) },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("external.wallet.id") + "tre", testData.getProperty("external.card.id"),
						Integer.parseInt(IHGUtil.createRandomNumericString(4)) },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid") + "oiu",
						testData.getProperty("external.wallet.id"), testData.getProperty("external.card.id"),
						Integer.parseInt(IHGUtil.createRandomNumericString(4)) }

		};
	}

	@DataProvider(name = "txn_data_for_proxy_sale")
	public Object[][] dpMethodGatewayProxySale() {
		return new Object[][] {
				{ "", testData.getProperty("test.pay.customer.uuid"), IHGUtil.createRandomNumericString(4) },
				{ testData.getProperty("proxy.mmid"), "", IHGUtil.createRandomNumericString(4) },
				{ testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"), "0" },
				{ testData.getProperty("proxy.mmid"), "75164da9-5747-449f-be45-c2e28f3ff77o",
						IHGUtil.createRandomNumericString(4) },
				{ "2560807796", testData.getProperty("test.pay.customer.uuid"), IHGUtil.createRandomNumericString(4) },

		};
	}

	@DataProvider(name = "get_txn")
	public Object[][] dpMethodForGetTxn() {
		return new Object[][] { { "", testData.getProperty("proxy.mmid"), getEpochDate(-1), getEpochDate(0), "VCS" },
				{ testData.getProperty("test.pay.customer.uuid"), "", getEpochDate(-1), getEpochDate(0), "VCS" },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), getEpochDate(-10),
						getEpochDate(0), "VCS" },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), getEpochDate(-1),
						getEpochDate(10), "VCS" }, };
	}

	@DataProvider(name = "get_txns_for_different_sources")
	public Object[][] dpMethodForGetTxnPostiveCases() {
		return new Object[][] {
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("epoch.time.saturday"), testData.getProperty("epoch.time.sunday"), "" },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("epoc.start.cpos.txn"), testData.getProperty("epoc.end.cpos.txn"), "VCS" },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("epoc.start.cpos.txn"), testData.getProperty("epoc.end.cpos.txn"),
						"CPOS" },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("epoc.start.olbp.txn"), testData.getProperty("epoc.end.olbp.txn"),
						"OLBP" }, };
	}

	@DataProvider(name = "CB_data_inavild_create")
	public static Object[][] dataProvider_CB() throws IOException {
		testData = new PropertyFileLoader();
		String token = MPUsersUtility.getCredentialsEncodedInBase("FINANCE");
		return new Object[][] {
				{ token, testData.getProperty("proxy.chargeback.url"), " ",
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), "1",
						"HTTP 500 Internal Server Error", 500 },
				{ token, testData.getProperty("proxy.chargeback.url"), testData.getProperty("proxy.mmid"), " ",
						testData.getProperty("order.id"), "1",
						"Field error in object 'chargeback' on field 'parentExternalTransactionId'", 400 },
				{ token, testData.getProperty("proxy.chargeback.url"), testData.getProperty("proxy.mmid"),
						testData.getProperty("external.transaction.id"), " ", "1",
						"Field error in object 'chargeback' on field 'parentOrderId'", 400 },
				{ token, testData.getProperty("proxy.chargeback.url"), testData.getProperty("proxy.mmid"),
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), "0",
						"Field error in object 'chargeback' on field 'chargebackAmount'", 400 },
				{ "invalid" + token, testData.getProperty("proxy.chargeback.url"), testData.getProperty("proxy.mmid"),
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), "1", "",
						403 },
				// { token ,testData.getProperty("proxy.chargeback.url"),"2560809338"
				// ,testData.getProperty("external.transaction.id"),testData.getProperty("order.id"),"1"
				// ,"",403},

		};

	}

	@DataProvider(name = "update_card")
	public static Object[][] dataProvider_update_card() throws IOException {
		testData = new PropertyFileLoader();
		String zipcode = IHGUtil.createRandomZip();

		return new Object[][] {

				{ testData.getProperty("card.alias1"), "", false, 400, "Zip code is mandatory" },
				{ "", "12345", false, 400, "Card Alias is mandatory" },
				{ testData.getProperty("card.alias1"), "1", false, 400,
						"Zip code should be 5 digits or 9 digits number" },
				{ testData.getProperty("card.alias1"), "1234567890", false, 400,
						"Zip code should be 5 digits or 9 digits number" },
				{ testData.getProperty("card.alias1"), "$$$$$", false, 400,
						"Zip code should be 5 digits or 9 digits number" },

		};

	}

	@DataProvider(name = "CB_data_invaild_get")
	public static Object[][] dataProvider_CB_get() throws IOException {
		testData = new PropertyFileLoader();
		String token = MPUsersUtility.getCredentialsEncodedInBase("FINANCE");
		return new Object[][] { { token, " ", "HTTP 500 Internal Server Error", 500 },
				{ token, "$$$$", "Failed to convert value of type", 500 },
				{ "invalid" + token, testData.getProperty("proxy.mmid"), "", 403 }, };

	}

	@DataProvider(name = "delete_invalid_data")
	public static Object[][] dataProvider_delete_card() throws Exception {
		testData = new PropertyFileLoader();
		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {

				{ token, " ", testData.getProperty("external.wallet.id"), testData.getProperty("external.card.id"), 404,
						"No message available" },
				{ token, "87b7e73a-ddab-4849-9af9-467b32c95ef4", testData.getProperty("external.wallet.id"),
						testData.getProperty("external.card.id"), 400, "Customer UUID is invalid" },

				{ token, testData.getProperty("test.pay.customer.uuid.other"),
						testData.getProperty("external.wallet.id"), testData.getProperty("external.card.id"), 403, "" },

				{ token, testData.getProperty("test.pay.customer.uuid"), " ", testData.getProperty("external.card.id"),
						500,
						"HTTP 500 Internal Server Error" },

				{ token, testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("external.wallet.id") + "abcdef", testData.getProperty("external.card.id"),
						404,
						"The card with external_card_id " + testData.getProperty("external.card.id")
								+ " does not exist" },

				{ token, testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("external.wallet.id") + "abcdef", testData.getProperty("external.card.id"),
						404,
						"The card with external_card_id " + testData.getProperty("external.card.id")
								+ " does not exist" },

				{ token, testData.getProperty("test.pay.customer.uuid"), testData.getProperty("external.wallet.id"),
						"invalid", 404, "The card with external_card_id invalid does not exist" },

				{ token, testData.getProperty("test.pay.customer.uuid"), testData.getProperty("external.wallet.id"),
						testData.getProperty("external.card.id"), 404, "The card with external_card_id "
								+ testData.getProperty("external.card.id") + " does not exist" },

		};

	}

	@DataProvider(name = "get_list_of_cards_invalid_data")
	public static Object[][] dataProvider_get_card() throws Exception {
		testData = new PropertyFileLoader();
		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {

				{ token, "$$$$$", 500, "500 INTERNAL_SERVER_ERROR \"Internal Server Error\""},

				{ token, testData.getProperty("external.wallet.id") + "invalid", 404,
						"The wallet with external_wallet_id " + testData.getProperty("external.wallet.id") + "invalid"
								+ " does not exist" },

		};

	}

	@DataProvider(name = "add_one_or_more_cards_invalid_data")
	public Object[][] dpMethodDigitalWalletAddCard() throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {

				{ testData.getProperty("test.pay.customer.uuid"), token, " ", testData.getProperty("consumer.name"),
						testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 500,
						"HTTP 500 Internal Server Error" },
				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), " ", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 400,
						"Card type is mandatory" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						" ", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 400, null },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"), "12345678901234567",
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 400,
						"Card number should be of 16 digits for card type = VI" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"), " ",
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 400,
						"Card number is mandatory" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), "1212", testData.getProperty("zipcode"), true, 400,
						" Card expiry date should not be in the past" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), " ", testData.getProperty("zipcode"), true, 400, null },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), "21122029", testData.getProperty("zipcode"), true, 400,
						"Card expiry date should be of format mmyy" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), "1234", true,
						400, "Zip code should be 5 digits or 9 digits number" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), "1234567890",
						true, 400, "Zip code should be 5 digits or 9 digits number" },

				{ testData.getProperty("test.pay.customer.uuid.other"), token,
						testData.getProperty("external.wallet.id"), testData.getProperty("consumer.name"),
						testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), false, 403, null },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"), " ", true, 400,
						null },

		};
	}

	@DataProvider(name = "cards_details")
	public Object[][] dpMethodCards() {
		return new Object[][] {
				{ testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
						testData.getProperty("card.alias1"), testData.getProperty("zipcode1"), false,
						testData.getProperty("consumer.name"), testData.getProperty("type2"),
						testData.getProperty("card.number2"), testData.getProperty("expiration.number2"),
						testData.getProperty("card.alias2"), testData.getProperty("zipcode2"), false,
						testData.getProperty("consumer.name"), testData.getProperty("type3"),
						testData.getProperty("card.number3"), testData.getProperty("expiration.number3"),
						testData.getProperty("card.alias3"), testData.getProperty("zipcode3"), false, 400,
						"One card has to be marked primary in a wallet" },

				{ testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
						testData.getProperty("card.alias1"), testData.getProperty("zipcode1"), true,
						testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
						testData.getProperty("card.alias2"), testData.getProperty("zipcode1"), false,
						testData.getProperty("consumer.name"), testData.getProperty("type3"),
						testData.getProperty("card.number3"), testData.getProperty("expiration.number3"),
						testData.getProperty("card.alias3"), testData.getProperty("zipcode3"), false, 400,
						"Cannot save duplicate cards" },

				{ testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), "1220", testData.getProperty("card.alias1"),
						testData.getProperty("zipcode1"), true, testData.getProperty("consumer.name"),
						testData.getProperty("type2"), testData.getProperty("card.number2"),
						testData.getProperty("expiration.number2"), testData.getProperty("card.alias2"),
						testData.getProperty("zipcode2"), false, testData.getProperty("consumer.name"),
						testData.getProperty("type3"), testData.getProperty("card.number3"),
						testData.getProperty("expiration.number3"), testData.getProperty("card.alias3"),
						testData.getProperty("zipcode3"), false, 400, " Card expiry date should not be in the past" },

				{ testData.getProperty("consumer.name"), testData.getProperty("type1"), " ", "1220",
						testData.getProperty("card.alias1"), testData.getProperty("zipcode1"), true,
						testData.getProperty("consumer.name"), testData.getProperty("type2"),
						testData.getProperty("card.number2"), testData.getProperty("expiration.number2"),
						testData.getProperty("card.alias2"), testData.getProperty("zipcode2"), false,
						testData.getProperty("consumer.name"), testData.getProperty("type3"),
						testData.getProperty("card.number3"), testData.getProperty("expiration.number3"),
						testData.getProperty("card.alias3"), testData.getProperty("zipcode3"), false, 400,
						"Card number is mandatory" },

				{ testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), " ", testData.getProperty("card.alias1"),
						testData.getProperty("zipcode1"), true, testData.getProperty("consumer.name"),
						testData.getProperty("type2"), testData.getProperty("card.number2"),
						testData.getProperty("expiration.number2"), testData.getProperty("card.alias2"),
						testData.getProperty("zipcode2"), false, testData.getProperty("consumer.name"),
						testData.getProperty("type3"), testData.getProperty("card.number3"),
						testData.getProperty("expiration.number3"), testData.getProperty("card.alias3"),
						testData.getProperty("zipcode3"), false, 400, "Card expiry date is mandatory" },

				{ testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
						testData.getProperty("card.alias1"), " ", true, testData.getProperty("consumer.name"),
						testData.getProperty("type2"), testData.getProperty("card.number2"),
						testData.getProperty("expiration.number2"), testData.getProperty("card.alias2"),
						testData.getProperty("zipcode2"), false, testData.getProperty("consumer.name"),
						testData.getProperty("type3"), testData.getProperty("card.number3"),
						testData.getProperty("expiration.number3"), testData.getProperty("card.alias3"),
						testData.getProperty("zipcode3"), false, 400, "Zip code is mandatory" },

				{ testData.getProperty("consumer.name"), testData.getProperty("type1"),
						testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
						testData.getProperty("card.alias1"), testData.getProperty("zipcode1"), true,
						testData.getProperty("consumer.name"), testData.getProperty("type2"),
						testData.getProperty("card.number2"), testData.getProperty("expiration.number2"),
						testData.getProperty("card.alias2"), testData.getProperty("zipcode2"), true,
						testData.getProperty("consumer.name"), testData.getProperty("type3"),
						testData.getProperty("card.number3"), testData.getProperty("expiration.number3"),
						testData.getProperty("card.alias3"), testData.getProperty("zipcode3"), true, 400,
						"In a wallet there can be only one primary card" } };
	}

	@DataProvider(name = "get_txns_for_receipt_data")
	public Object[][] dpMethodForGetReceiptData() {
		return new Object[][] {
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.vcs.transactionId"), testData.getProperty("proxy.vcs.orderId"),
						testData.getProperty("payment.source"), 200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.olbp.transactionId"), testData.getProperty("proxy.olbp.orderId"),
						testData.getProperty("olbp.payment.type"), 200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.chbk.transactionId"), testData.getProperty("proxy.chbk.orderId"),
						testData.getProperty("chbk.payment.type"), 200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.refund.transactionId"),
						testData.getProperty("proxy.refund.orderId"), testData.getProperty("refund.payment.type"),
						200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.void.transactionId"), testData.getProperty("proxy.void.orderId"),
						testData.getProperty("void.payment.type"), 200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.void.transactionId"), testData.getProperty("proxy.void.orderId"),
						testData.getProperty("void.payment.type"), 200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.void.transactionId"),
						testData.getProperty("proxy.declined.orderId"), testData.getProperty("cpos.payment.type"),
						200 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
						testData.getProperty("proxy.declined.transactionId"),
						testData.getProperty("proxy.void.orderId"), testData.getProperty("void.payment.type"), 204 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"), "12345678",
						testData.getProperty("proxy.vcs.orderId"), testData.getProperty("payment.source"), 204 },
				{ testData.getProperty("test.pay.customer.uuid"), testData.getProperty("element.mmid"),
						testData.getProperty("proxy.vcs.transactionId"), "jvdgerebdsvbkdvbakd",
						testData.getProperty("payment.source"), 204 }

		};
	}

	@DataProvider(name = "empty_Wallet_Add_Cards")
	public Object[][] dpEmptyWallet() {
		return new Object[][] {

//	Refernce to input data addded
//{noOfCrads, noofCrdsToBeTrue, noOfCrdsToBeFalse,noOfCrdsToBeNull,noofcardstobeommited,noOfCardsTobeRandom ,statusCodeVerify,validationMessage},

				{ 1, 0, 1, 0, 0, 0, 200, "" },

				{ 1, 0, 0, 1, 0, 0, 200, "" }, { 1, 0, 0, 0, 1, 0, 200, "" },
				{ 1, 0, 0, 0, 0, 1, 500, "JSON parse error: Cannot deserialize value of type" },

				{ 2, 1, 0, 1, 0, 0, 200, "" },

				{ 2, 2, 0, 0, 0, 0, 400, "In a wallet there can be only one primary card" },
				{ 2, 0, 2, 0, 0, 0, 400, "One card has to be marked primary in a wallet" },

				{ 2, 1, 1, 0, 0, 0, 200, "" }, { 2, 1, 0, 1, 0, 0, 200, "" },

				{ 2, 0, 0, 2, 0, 0, 400, "One card has to be marked primary in a wallet" },

				{ 2, 1, 0, 0, 1, 0, 200, "" },

				{ 2, 0, 0, 0, 2, 0, 400, "One card has to be marked primary in a wallet" },

				{ 3, 0, 3, 0, 0, 0, 400, "One card has to be marked primary in a wallet" },

				{ 3, 3, 0, 0, 0, 0, 400, "In a wallet there can be only one primary card" },

				{ 3, 0, 0, 3, 0, 0, 400, "One card has to be marked primary in a wallet" },
				{ 3, 0, 0, 0, 3, 0, 400, "One card has to be marked primary in a wallet" },
				{ 3, 0, 0, 0, 0, 3, 500, "JSON parse error: Cannot deserialize value of type" },

		};
	}

	@DataProvider(name = "delete_card_with_sepcific_flag")
	public Object[][] deleteCardWithSepcificFlag() {
		return new Object[][] {

				// Refernce to input data addded
				// {noOfCrads, noofCrdsToBeTrue,
				// noOfCrdsToBeFalse,noOfCrdsToBeNull,noofcardstobeommited,noOfCardsTobeRandom
				// ,statusCodeVerify,validationMessage},

				{ 1, 1, 0, 0, 0, 0, "true", 200, "", 0 },

				{ 2, 1, 1, 0, 0, 0, "true", 400,
						"The primary card cannot be deleted from a wallet if the wallet has more than one card", 2 },
				{ 2, 1, 1, 0, 0, 0, "false", 200, "", 1 },

				{ 4, 1, 3, 0, 0, 0, "true", 400,
						"The primary card cannot be deleted from a wallet if the wallet has more than one card", 4 },

		};
	}

	@DataProvider(name = "Add_Multiple_Cards_with_specific_flag")
	public Object[][] AddmultipleCrds() {
		return new Object[][] {

//	Refernce to input data addded
				/*
				 * int noOfCardsToBeCreated,int noofCrdsCreatedToBeTrue ,int
				 * noOfCrdsCreatedToBefalse, int noOfCardsToBeAdded, int noofCrdsToBeAddedTrue,
				 * int noOfCrdsToBeAddedFalse, int noOfCrdsToBeAddedNull, int
				 * noOfCrdsToBeAddedAsOmitted, int noOfcardsTobeAddedAsRandom,int
				 * statusCodeVerify, String verifyValidationMessage,int noOfCardsAfterDeletion
				 */

				{ 1, 1, 0, 1, 1, 0, 0, 0, 0, 200, "", "true" },

				{ 1, 1, 0, 1, 0, 1, 0, 0, 0, 200, "", "false" },

				{ 1, 1, 0, 1, 0, 0, 1, 0, 0, 200, "", "false" },

				{ 1, 1, 0, 1, 0, 0, 0, 1, 0, 200, "", "false" },

				{ 1, 1, 0, 2, 0, 2, 0, 0, 0, 200, "", "false" },

				{ 1, 1, 0, 2, 2, 0, 0, 0, 0, 400, "In a wallet there can be only one primary card", "" },

				{ 1, 1, 0, 2, 1, 1, 0, 0, 0, 200, "", "true" },

				{ 1, 1, 0, 2, 0, 0, 2, 0, 0, 200, "", "false" },

				{ 1, 1, 0, 2, 0, 0, 0, 2, 0, 200, "", "false" },

				{ 2, 1, 1, 2, 2, 0, 0, 0, 0, 400, "", "In a wallet there can be only one primary card" },

				{ 2, 1, 1, 2, 0, 2, 0, 0, 0, 200, "", "false" },

				{ 2, 1, 1, 2, 1, 1, 0, 0, 0, 200, "", "true" },

				{ 2, 1, 1, 2, 0, 0, 2, 0, 0, 200, "", "false" },

				{ 2, 1, 1, 2, 0, 0, 0, 2, 0, 200, "", "false" },

		};
	}
}