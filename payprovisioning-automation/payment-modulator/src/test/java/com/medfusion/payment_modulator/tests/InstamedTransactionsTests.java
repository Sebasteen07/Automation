package com.medfusion.payment_modulator.tests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.helpers.TransactionResourceDetails;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.ModulatorTestData;
import com.medfusion.payment_modulator.utils.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class InstamedTransactionsTests extends BaseRest {

    protected static PropertyFileLoader testData;

    @BeforeTest
    public void setUp() throws IOException {
        testData = new PropertyFileLoader();
        setupRequestSpecBuilder();
        setupResponsetSpecBuilder();
    }

    @Test(priority = 5, enabled = true)
    public void testInstaMedSaleCall() throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeASale(testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
                testData.getProperty("account.number"), testData.getProperty("consumer.name"),
                testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
                testData.getProperty("card.number"), testData.getProperty("instamed.card.expiration.number"),
                testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));

        Assert.assertEquals(response.getStatusCode(), 200);

        Validations validate = new Validations();
        validate.verifyInstaMedTransactionDetails(response.asString());
    }

    @Test(priority = 6, dataProvider = "mod_instamed_sale_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSaleNegativeScenarios(String mmid, String transactionAmount, String cvv, String paymentSource,
                                                  String cardNumber, String expirationNumber, int statusCodeVerify,
                                                  String error, String verifyErrorMessage) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeASale(mmid, transactionAmount, testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), paymentSource, cvv,
                testData.getProperty("type"), cardNumber, expirationNumber, testData.getProperty("bin"),
                testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("error").toString().contains(error));
            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
        }
    }

    @Test(priority = 2, dataProvider = "mod_instamed_different_payment_sources", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSaleForMultiplePaymentSources(String paymentSource, int statusCodeVerify,
                                                               String verifyErrorMessage) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeASale(testData.getProperty("instamed.mmid"),
                IHGUtil.createRandomNumericString(3), testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"),
                testData.getProperty("type"), testData.getProperty("card.number"),
                testData.getProperty("instamed.card.expiration.number"), testData.getProperty("bin"),
                testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null) {

            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
        }
    }

    @Test(priority = 2, dataProvider = "mod_instamed_different_card_types", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSaleForMultipleCardTypes(String cardNumber, String type, int statusCodeVerify,
                                                          String verifyErrorMessage) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeASale(testData.getProperty("instamed.mmid"),
                IHGUtil.createRandomNumericString(3), testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), testData.getProperty("payment.source"), testData.getProperty("cvv"),
                type, cardNumber, testData.getProperty("instamed.card.expiration.number"), testData.getProperty("bin"),
                testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null) {

            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
        }
    }

    @Test(priority = 2, dataProvider = "mod_instamed_different_merchants", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSaleForMultipleMerchants(String mmid, int statusCodeVerify,
                                                     String verifyErrorMessage) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeASale(mmid,
                IHGUtil.createRandomNumericString(3), testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), testData.getProperty("payment.source"), testData.getProperty("cvv"),
                testData.getProperty("type"), testData.getProperty("card.number"),
                testData.getProperty("instamed.card.expiration.number"), testData.getProperty("bin"),
                testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null) {

            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
        }
    }
}
