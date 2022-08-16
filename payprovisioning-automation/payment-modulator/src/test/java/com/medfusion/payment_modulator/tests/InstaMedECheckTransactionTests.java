package com.medfusion.payment_modulator.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.helpers.DigitalWalletResource;
import com.medfusion.digital_wallet.utils.DigitalWalletAPIUtils;
import com.medfusion.digital_wallet.utils.DigitalWalletTestData;
import com.medfusion.payment_modulator.helpers.TransactionResourceDetails;
import com.medfusion.payment_modulator.utils.ModulatorTestData;
import com.medfusion.payment_modulator.utils.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class InstaMedECheckTransactionTests extends BaseRest {

    protected static PropertyFileLoader testData;

    @BeforeTest
    public void setUp() throws IOException {
        testData = new PropertyFileLoader();
        setupRequestSpecBuilderV5();
        setupResponsetSpecBuilder();
    }

    @Test(priority = 1, dataProvider =  "instamed_echeck_positive_scenarios", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedECheckSale(String amount, String responseCode, String responseMessage) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        Validations validate = new Validations();

        Response response = transaction.makeAECheckSale(testData.getProperty("instamed.mmid"),
                amount, testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
                testData.getProperty("bank.account.type"), testData.getProperty("bank.account.number"),
                testData.getProperty("bank.routing.number"), testData.getProperty("bank.account.holder.first.name"),
                testData.getProperty("bank.account.holder.last.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        validate.verifyInstaMedTransactionDetails(jsonPath.getString("responseCode"), jsonPath);

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("responseCode").toString().contains(responseCode));
            Assert.assertTrue(jsonPath.get("message").toString().contains(responseMessage));
        }
    }

    @Test(priority = 2, dataProvider = "mod_instamed_different_merchants", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedECheckSaleForMultipleMerchants(String mmid, String paymentSource, int statusCodeVerify,
                                                           String verifyErrorText, String verifyMessageText) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeAECheckSale(mmid, testData.getProperty("transaction.amount"),
                testData.getProperty("account.number"), testData.getProperty("consumer.name"),
                paymentSource, testData.getProperty("bank.account.type"),
                testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                testData.getProperty("bank.account.holder.first.name"), testData.getProperty("bank.account.holder.last.name"));

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyMessageText));
            if (verifyMessageText.isEmpty()) {
                jsonPath.get("propertyMessages").equals("Could not find outlet");
            }
        }
    }

    @Test(priority = 3, dataProvider = "echeck_sale", dataProviderClass = ModulatorTestData.class, enabled = true)
    public void testInstaMedECheckSaleNegativeScenarios(String mmid, String transactionAmount, String paymentSource,
                                                        String bankAccountType, String bankAccountNumber, String bankRoutingNumber,
                                                        String checkHolderFirstName, String checkHolderLastName, int statusCodeVerify,
                                                        String errorText, String verifyErrorMessage, String prop) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        Response response = transaction.makeAECheckSale(mmid,
                transactionAmount, testData.getProperty("account.number"),
                testData.getProperty("consumer.name"), paymentSource, bankAccountType,
                bankAccountNumber, bankRoutingNumber, checkHolderFirstName, checkHolderLastName);

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath, "Response was null");
        Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

        if (jsonPath.get("message") != null && jsonPath.get("error") != null) {
            Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
            Assert.assertTrue(jsonPath.get("error").toString().contains(errorText));
        }
        Assert.assertTrue(jsonPath.get("propertyMessages").toString().contains(prop));
    }

    @Test(priority = 4, enabled = true)
    public void testInstaMedViewReceiptForECheckSale() throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        Validations validate = new Validations();
        Response response = transaction.makeAECheckSale(testData.getProperty("instamed.mmid"),
                    testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
                    testData.getProperty("consumer.name"), testData.getProperty("payment.source.patient.portal"),
                    testData.getProperty("bank.account.type"), testData.getProperty("bank.account.number"),
                    testData.getProperty("bank.routing.number"), testData.getProperty("bank.account.holder.first.name"),
                    testData.getProperty("bank.account.holder.last.name"));

        JsonPath jsonPathSale = new JsonPath(response.asString());

        Response responseOfViewReceipt =
                transaction.viewReceipt(testData.getProperty("base.url.v2"), testData.getProperty("instamed.mmid"),
                        jsonPathSale.get("externalTransactionId").toString(), jsonPathSale.get("orderId").toString());

        JsonPath jsonpath = new JsonPath(responseOfViewReceipt.asString());
        Assert.assertNotNull(jsonpath, "Response was null");
        validate.verifyInstamedReceiptCommonDetails(jsonpath);

        Assert.assertNull(jsonpath.get("cardType"));
        Assert.assertNull(jsonpath.get("cardHolderName"));
        Assert.assertTrue(jsonpath.get("cardSuffix")
                    .equals(testData.getProperty("bank.account.number").substring(testData.getProperty("bank.account.number").length()-4)));
        Assert.assertTrue(jsonpath.get("patientAccount").equals(testData.getProperty("account.number")));
        Assert.assertTrue(jsonpath.get("consumerName").equals(testData.getProperty("consumer.name")));
        Assert.assertTrue(jsonpath.get("instamedDetail.accountType").equals(testData.getProperty("bank.account.type")));
        Assert.assertTrue(jsonpath.get("instamedDetail.routingNumber").equals(testData.getProperty("bank.routing.number")));
        Assert.assertTrue(jsonpath.get("instamedDetail.accountHolderName")
                    .equals(testData.getProperty("bank.account.holder.first.name")+" "+testData.getProperty("bank.account.holder.last.name")));

    }

    @Test(priority=5, enabled = true, dataProvider = "sale", dataProviderClass = DigitalWalletTestData.class)
    public void testSaleUsingSavedPaymentMethodOnFile(String consumerName,  String source,
                                                      String paymentMethodId, String amount, String response,
                                                      String responseCode) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        Response response1 = transaction.saleUsingSavedCard(testData.getProperty("instamed.mmid"),
                testData.getProperty("account.number"), consumerName, source, paymentMethodId , Integer.parseInt(amount));

        JsonPath jsonPath = new JsonPath(response1.asString());
        Assert.assertNotNull(jsonPath.get("responseTime"));
        Assert.assertNotNull(jsonPath.get("orderId"));
        Assert.assertNotNull(jsonPath.get("externalTransactionId"));
        Assert.assertNotNull(jsonPath.get("authCode"));

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("message").toString().contains(response));
            Assert.assertTrue(jsonPath.get("error").toString().contains(responseCode));
        }
    }

    @Test(priority=6, enabled = true, dataProvider = "sale_invalid_data", dataProviderClass = DigitalWalletTestData.class)
    public void testSaleUsingSavedPaymentMethodOnFileWithInvalidData(String consumerName,  String source,
                                                      String paymentMethodId, String amount, int status,
                                                      String error, String message) throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        Response response1 = transaction.saleUsingSavedCard(testData.getProperty("instamed.mmid"),
                testData.getProperty("account.number"), consumerName, source, paymentMethodId , Integer.parseInt(amount));

        JsonPath jsonPath = new JsonPath(response1.asString());
        Assert.assertTrue(jsonPath.getInt("status") == status);
        Assert.assertTrue(jsonPath.get("error").toString().contains(error));

        if (jsonPath.get("message") != null) {
            Assert.assertTrue(jsonPath.get("message").toString().contains(message)); }
        else{
            Assert.assertTrue(jsonPath.get("propertyMessages").toString().contains(message)); }
    }
}


