// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.helpers.TransactionResourceDetails;
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

    @Test(priority = 4, enabled = true)
    public void testInstaMedViewReceiptForCardSale() throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        Validations validate = new Validations();
        Response response = transaction.makeASale(
                    testData.getProperty("instamed.mmid"), testData.getProperty("transaction.amount"),
                    testData.getProperty("account.number"), testData.getProperty("consumer.name"),
                    testData.getProperty("payment.source.patient.portal"), testData.getProperty("cvv"),
                    testData.getProperty("type"), testData.getProperty("card.number"),
                    testData.getProperty("expiration.number1"), testData.getProperty("bin"),
                    testData.getProperty("zipcode"), testData.getProperty("last.name"),
                    testData.getProperty("address.line1"), testData.getProperty("city"),
                    testData.getProperty("state"), testData.getProperty("first.name"));

        JsonPath jsonPathSale = new JsonPath(response.asString());

        Response responseOfViewReceipt =
                transaction.viewReceipt(testData.getProperty("base.url.v2"), testData.getProperty("instamed.mmid"),
                        jsonPathSale.get("externalTransactionId").toString(), jsonPathSale.get("orderId").toString());

        JsonPath jsonpath = new JsonPath(responseOfViewReceipt.asString());
        validate.verifyInstamedReceiptCommonDetails(jsonpath);

        Assert.assertTrue(jsonpath.get("cardType").equals(testData.getProperty("type4")));
        Assert.assertTrue(jsonpath.get("cardSuffix")
                    .equals(testData.getProperty("card.number").substring(testData.getProperty("card.number").length()-4)));
        Assert.assertTrue(jsonpath.get("patientAccount").equals(testData.getProperty("account.number")));
        Assert.assertTrue(jsonpath.get("consumerName").equals(testData.getProperty("consumer.name")));
        Assert.assertTrue(jsonpath.get("cardHolderName").equals(testData.getProperty("first.name")+" "+testData.getProperty("last.name")));
        Assert.assertNull(jsonpath.get("instamedDetail.accountType"), "Account Type is not null for card cale");
        Assert.assertNull(jsonpath.get("instamedDetail.routingNumber"), "Routing Number is not null for card sale");
        Assert.assertNull(jsonpath.get("instamedDetail.accountHolderName"), "Account Holder Name is not null for card sale");

    }

}
