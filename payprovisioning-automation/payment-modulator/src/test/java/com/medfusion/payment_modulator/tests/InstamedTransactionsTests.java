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

    @Test(priority = 1, dataProvider = "mod_instamed_positive_scenarios", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSale(String txnAmt, String cardNumer, String cardType, String cvv,
                                 String paymentSource, String responseMsg, String responseCode) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        Validations validate = new Validations();

        Response response = transaction.makeASale(testData.getProperty("instamed.mmid"), txnAmt,
                testData.getProperty("account.number"), testData.getProperty("consumer.name"),
                paymentSource, cvv, cardType, cardNumer, testData.getProperty("expiration.number1"),
                testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));
        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath jsonPath = new JsonPath(response.asString());

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("message").toString().contains(responseMsg));
            Assert.assertTrue(jsonPath.get("responseCode").toString().contains(responseCode));
        }
        validate.verifyInstaMedTransactionDetails(responseCode, jsonPath);
    }

    @Test(priority = 2, dataProvider = "mod_instamed_sale_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSaleNegativeScenarios(String mmid, String transactionAmount, String cvv, String paymentSource,
                                                  String cardNumber, String expirationNumber, int statusCodeVerify,
                                                  String errorText, String verifyErrorMessage) throws Exception {

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

        if (jsonPath.get("message") != null && jsonPath.get("error") != null ) {
            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
            Assert.assertTrue(jsonPath.get("error").toString().contains(errorText));
        }
    }

    @Test(priority = 3, dataProvider = "mod_instamed_different_merchants", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedSaleForMultipleMerchants(String mmid, String paymentSource, int statusCodeVerify,
                                                     String verifyErrorText, String verifyMessageText) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeASale(mmid,
                testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"),
                testData.getProperty("type"), testData.getProperty("card.number"),
                testData.getProperty("expiration.number1"), testData.getProperty("bin"),
                testData.getProperty("zipcode"), testData.getProperty("last.name"),
                testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
                testData.getProperty("first.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyMessageText));
            if(verifyMessageText.isEmpty()){
                jsonPath.get("propertyMessages").equals("Could not find outlet");
            }
        }
    }

}
