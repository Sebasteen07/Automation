package com.medfusion.payment_modulator.utils;

import java.io.IOException;

import com.medfusion.common.utils.IHGUtil;
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
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), "", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"), "",
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), "1220", testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), "" } };
	}

	@DataProvider(name = "txn_data_for_sale")
	public Object[][] dpMethodDigitalWalletSale() {
		return new Object[][]{
				{testData.getProperty("test.pay.customer.uuid")+"hgf",
						testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id"),
						testData.getProperty("external.card.id"), Integer.parseInt(IHGUtil.createRandomNumericString(4))},
				{testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id"),
						testData.getProperty("external.card.id"), 0},
				{testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id"),
						testData.getProperty("external.card.id")+"iut", Integer.parseInt(IHGUtil.createRandomNumericString(4))},
				{testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id")+"tre",
						testData.getProperty("external.card.id"), Integer.parseInt(IHGUtil.createRandomNumericString(4))},
				{testData.getProperty("test.pay.customer.uuid"),
						testData.getProperty("proxy.mmid")+"oiu", testData.getProperty("external.wallet.id"),
						testData.getProperty("external.card.id"), Integer.parseInt(IHGUtil.createRandomNumericString(4))}

		};
	}

	@DataProvider(name = "txn_data_for_proxy_sale")
	public Object[][] dpMethodGatewayProxySale() {
		return new Object[][]{
				{"", testData.getProperty("test.pay.customer.uuid"),
						IHGUtil.createRandomNumericString(4)},
				{testData.getProperty("proxy.mmid"), "",
						IHGUtil.createRandomNumericString(4)},
				{testData.getProperty("proxy.mmid"), testData.getProperty("test.pay.customer.uuid"),
						"0"},
				{testData.getProperty("proxy.mmid"), "75164da9-5747-449f-be45-c2e28f3ff77o",
						IHGUtil.createRandomNumericString(4)},
				{"2560807796", testData.getProperty("test.pay.customer.uuid"),
						IHGUtil.createRandomNumericString(4)},

		};
	}
}
