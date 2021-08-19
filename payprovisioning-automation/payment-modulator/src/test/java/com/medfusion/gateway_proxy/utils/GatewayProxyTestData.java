package com.medfusion.gateway_proxy.utils;

import java.io.IOException;

import com.medfusion.common.utils.IHGUtil;
import org.testng.annotations.DataProvider;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.gateway_proxy.utils.MPUsersUtility;

public class GatewayProxyTestData extends GatewayProxyBaseTest {

	@DataProvider(name = "refund_data")
	public static Object[][] dataProvider() throws Exception {
		testData = new PropertyFileLoader();
		String token = GatewayProxyUtils.getTokenForCustomer();
		return new Object[][] {

				{ token, " ", testData.getProperty("test.pay.customer.uuid"), "Testing refund with blank mmid ",
						testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), testData.getProperty("transaction.amount"), 400,
						"For input string: \" \"" },

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
						"500 Internal Server Error" },

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
						testData.getProperty("order.id"), 400, "For input string: \" \"" },

				{ token, testData.getProperty("proxy.mmid"), " ", "Testing refund with blank customer uuid  ",
						testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
						testData.getProperty("order.id"), 404, "No message available" },

				{ token, "2560807700", testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with invalid mmid  ", testData.getProperty("customer.id"),
						testData.getProperty("external.transaction.id"), testData.getProperty("order.id"), 403, " " },

				{ token, testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"Testing refund with blank transaction id ", testData.getProperty("customer.id"), " ",
						testData.getProperty("order.id"), 500, "Unexpected Server Error" },

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
	public Object[][] dpMethodDigitalWallet() {
		return new Object[][] {
				{ "", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{ testData.getProperty("consumer.name"), "", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"), "",
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), "1220", testData.getProperty("card.alias"),
						testData.getProperty("zipcode"), true },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), "", true },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), "", true },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), testData.getProperty("zipcode"), false } };
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
						"Failed to convert value of type", 500 },
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
						401 },
				// { token ,testData.getProperty("proxy.chargeback.url"),"2560809338"
				// ,testData.getProperty("external.transaction.id"),testData.getProperty("order.id"),"1"
				// ,"",403},

		};

	}


	@DataProvider(name = "update_card")
	public static Object[][] dataProvider_update_card() throws IOException {
		testData = new PropertyFileLoader();
		String zipcode = IHGUtil.createRandomZip();

		return new Object[][] { { "", zipcode, true }, { testData.getProperty("card.alias1"), "987654321234", true },
				{ testData.getProperty("card.alias1"), "123", true }, { testData.getProperty("card.alias1"), "", true },
				{ testData.getProperty("card.alias1"), zipcode, false } };

	}

	@DataProvider(name = "CB_data_invaild_get")
	public static Object[][] dataProvider_CB_get() throws IOException {
		testData = new PropertyFileLoader();
		String token = MPUsersUtility.getCredentialsEncodedInBase("FINANCE");
		return new Object[][] { { token, " ", "Failed to convert value of type", 500 },
				{ token, "$$$$", "Failed to convert value of type", 500 },
				{ "invalid" + token, testData.getProperty("proxy.mmid"), "", 401 }, };

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
						404,
						"The card with external_card_id " + testData.getProperty("external.card.id")
								+ " does not exist" },

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

				{ token, "$$$$$", 404, "The wallet with external_wallet_id " + "$$$$$" + " does not exist" },

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
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 404,
						"The wallet with external_wallet_id   does not exist" },

				{ testData.getProperty("test.pay.customer.uuid"), token, testData.getProperty("external.wallet.id"),
						"A", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("zipcode"), true, 400, " " },

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

}
