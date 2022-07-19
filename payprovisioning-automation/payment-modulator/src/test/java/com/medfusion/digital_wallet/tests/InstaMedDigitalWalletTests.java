// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.tests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.helpers.DigitalWalletResource;
import com.medfusion.digital_wallet.utils.DigitalWalletAPIUtils;
import com.medfusion.digital_wallet.utils.DigitalWalletTestData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class InstaMedDigitalWalletTests extends DigitalWalletBaseTest {

    @BeforeTest
    public void setUp() throws Exception {
        testData = new PropertyFileLoader();
        setupRequestSpecBuilderv2Endpoint();
    }

    @Test(priority = 1, dataProvider = "valid_data", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testCreateInstaMedWallet(String defaultPaymentMethod, String patientUrn, String alias, String bankHolderName,
                                         String bankHolderLastName, String accountType, String accountNumber, String routingNo) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String patientId = IHGUtil.createRandomNumericString(36);

        Response response = digitalWallet.createInstaMedWallet(token, testData.getProperty("enterprise.id"),
                patientId, testData.getProperty("instamed.mmid"),
                defaultPaymentMethod, patientUrn, alias, bankHolderName, bankHolderLastName,
                accountType, accountNumber, routingNo);

        JsonPath jsonPath = new JsonPath(response.asString());

        Assert.assertTrue(response.getStatusCode() == 201);
        Assert.assertTrue(jsonPath.get("mmid").toString().contains(testData.getProperty("instamed.mmid")));
        Assert.assertTrue(jsonPath.get("defaultPaymentMethod").toString().equals(testData.getProperty("default.payment.method")));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountAlias").toString().contains(alias));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountHolderFirstName").toString().contains(bankHolderName));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountHolderLastName").toString().contains(bankHolderLastName));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountType").toString().contains(accountType));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountNumberLastFour").toString().contains("6789"));
        Assert.assertTrue(jsonPath.get("accounts.bankRoutingNumber").toString().contains(routingNo));
        Assert.assertTrue(jsonPath.get("accounts.primaryAccount").toString().contains("true"));

    }

    @Test(priority = 2, dataProvider = "wallet_invalid_data", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testCreateInstaMedWalletWithInvalidData(String mmid, String defaultPaymentMethod, String patientUrn, String accountAlias,
                                                        String accountHolderFirstName, String accountHolderLastName, String accountType,
                                                        String accountNumber, String routingNumber, int status,
                                                        String error, String message) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String patientId = IHGUtil.createRandomNumericString(36);

        Response response = digitalWallet.createInstaMedWallet(token, testData.getProperty("enterprise.id"),
                patientId, mmid, defaultPaymentMethod, patientUrn, accountAlias, accountHolderFirstName, accountHolderLastName,
                accountType, accountNumber, routingNumber);

        JsonPath jsonPath = new JsonPath(response.asString());

        if (jsonPath.get("error") != null) {
            Assert.assertTrue(jsonPath.get("status").equals(status));
            Assert.assertTrue(jsonPath.get("message").toString().contains(message));
            Assert.assertTrue(jsonPath.get("error").toString().contains(error));
        }
    }

    @Test(priority = 2, dataProvider = "mod_instamed_different_merchants", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testCreateWalletForInvalidMerchants(String mmid, String patientUrn, int statusCodeVerify,
                                                           String verifyErrorText, String verifyMessageText) throws Exception {

        String token = DigitalWalletAPIUtils.getTokenForCustomer();

        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String patientId = IHGUtil.createRandomNumericString(36);

        Response response = digitalWallet.createInstaMedWallet(token, testData.getProperty("enterprise.id"),
                patientId, mmid, testData.getProperty("default.payment.method.echeck"),
                patientUrn, testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"));

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

    @Test(priority = 1, dataProvider = "valid_data", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testAddAccountToExistingInstaMedWallet(String defaultPaymentMethod, String patientUrn, String alias,
                                                       String bankHolderName, String bankHolderLastName, String accountType,
                                                       String accountNumber, String routingNo) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String accountNo = IHGUtil.createRandomNumericString(9);

        Response response = digitalWallet.addAccountToExistingInstaMedWallet(token, testData.getProperty("enterprise.id"),
                testData.getProperty("patient.id"), testData.getProperty("instamed.mmid"),
                defaultPaymentMethod, alias, bankHolderName,
                bankHolderLastName, accountType,
                accountNo, routingNo, true);

        JsonPath jsonPath = new JsonPath(response.asString());

        Assert.assertTrue(response.getStatusCode() == 201);
        Assert.assertTrue(jsonPath.get("defaultPaymentMethod").toString().equals(testData.getProperty("default.payment.method")));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountAlias").toString().contains(alias));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountHolderFirstName").toString()
                .contains(bankHolderName));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountHolderLastName").toString()
                .contains(bankHolderLastName));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountType").toString()
                .contains(accountType));
        Assert.assertTrue(jsonPath.get("accounts.bankAccountNumberLastFour").toString()
                .contains(accountNo.substring(accountNo.length()-4)));
        Assert.assertTrue(jsonPath.get("accounts.bankRoutingNumber").toString()
                .contains(testData.getProperty("bank.routing.number")));
        Assert.assertTrue(jsonPath.get("accounts.primaryAccount").toString().contains("true"));
    }

    @Test(priority = 1, dataProvider = "instamed_different_patients", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testAddDuplicateAccountToExistingInstaMedWalletBadRequest(String patientId, int status, String error, String message) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();

        Response response = digitalWallet.addAccountToExistingInstaMedWallet(token, testData.getProperty("enterprise.id"),
                patientId, testData.getProperty("instamed.mmid"),
                testData.getProperty("default.payment.method.echeck"),
                testData.getProperty("account.alias"),
                testData.getProperty("bank.account.holder.first.name"), testData.getProperty("bank.account.holder.last.name"),
                testData.getProperty("bank.account.type"),
                testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"), true);

        JsonPath jsonPath = new JsonPath(response.asString());

        if (jsonPath.get("error") != null) {
            Assert.assertTrue(jsonPath.get("status").equals(status));
            Assert.assertTrue(jsonPath.get("message").toString().contains(message));
            Assert.assertTrue(jsonPath.get("error").toString().contains(error));
        }
    }

    @Test(priority = 2, dataProvider = "wallet_invalid_data", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testAddAccountToInstaMedWalletWithInvalidData(String mmid, String defaultPaymentMethod, String patientUrn, String accountAlias,
                                                        String accountHolderFirstName, String accountHolderLastName, String accountType,
                                                        String accountNumber, String routingNumber, int status,
                                                        String error, String message) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String accountNo = IHGUtil.createRandomNumericString(9);

        Response response = digitalWallet.addAccountToExistingInstaMedWallet(token, testData.getProperty("enterprise.id"),
                testData.getProperty("patient.id"), mmid, defaultPaymentMethod, accountAlias, accountHolderFirstName, accountHolderLastName,
                accountType, accountNo, routingNumber, false);

        JsonPath jsonPath = new JsonPath(response.asString());

        if (jsonPath.get("error") != null) {
            Assert.assertTrue(jsonPath.get("status").equals(status));
            Assert.assertTrue(jsonPath.get("message").toString().contains(message));
            Assert.assertTrue(jsonPath.get("error").toString().contains(error));
        }
    }


    @Test(priority = 1, dataProvider = "valid_card_data", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testCreateInstaMedWalletUsingCard(String defaultPaymentMethod, String patientUrn, String alias, String cardExpiryDate,
                                         String cardHolderName, String cardNumber, String cardType, String cvv) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String patientId = IHGUtil.createRandomNumericString(36);

        Response response = digitalWallet.createInstaMedWalletForACard(token, testData.getProperty("enterprise.id"),
                patientId, testData.getProperty("instamed.mmid"),
                defaultPaymentMethod, patientUrn, alias, cardExpiryDate, cardHolderName, cardNumber, cardType,
                cvv);

        JsonPath jsonPath = new JsonPath(response.asString());

        Assert.assertTrue(response.getStatusCode() == 201);
        Assert.assertTrue(jsonPath.get("mmid").toString().contains(testData.getProperty("instamed.mmid")));
        Assert.assertTrue(jsonPath.get("defaultPaymentMethod").toString().equals(testData.getProperty("deafult.payment.method.card")));
        Assert.assertTrue(jsonPath.get("cards.cardAlias").toString().contains(alias));
        Assert.assertTrue(jsonPath.get("cards.cardHolderName").toString().contains(cardHolderName));
        Assert.assertTrue(jsonPath.get("cards.cardNumberLastFour").toString().contains(cardNumber.substring(cardNumber.length()-4)));
        Assert.assertTrue(jsonPath.get("cards.cardType").toString().contains(cardType));
        Assert.assertTrue(jsonPath.get("cards.cardExpiryDate").toString().contains(cardExpiryDate));
        Assert.assertTrue(jsonPath.get("cards.primaryCard").toString().contains("true"));
        Assert.assertTrue(jsonPath.get("cards.isInstaMed").toString().contains("true"));

    }

    @Test(priority = 2, dataProvider = "wallet_invalid_card_data", dataProviderClass = DigitalWalletTestData.class, enabled = true)
    public void testCreateInstaMedWalletForACardWithInvalidData(String mmid, String defaultPaymentMethod, String patientUrn, String alias,
                                                        String cardExpiryDate, String cardHolderName, String cardNumber,
                                                        String cardType, String cvv, int status,
                                                        String error, String message, String propertyMessage) throws Exception {
        String token = DigitalWalletAPIUtils.getTokenForCustomer();
        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String patientId = IHGUtil.createRandomNumericString(36);

        Response response = digitalWallet.createInstaMedWalletForACard(token, testData.getProperty("enterprise.id"),
                patientId, mmid, defaultPaymentMethod, patientUrn, alias, cardExpiryDate, cardHolderName, cardNumber, cardType,
                cvv);

        JsonPath jsonPath = new JsonPath(response.asString());

        if (jsonPath.get("error") != null) {
            Assert.assertTrue(jsonPath.get("status").equals(status));
               Assert.assertTrue(jsonPath.get("error").toString().contains(error));
            if (message.isEmpty()) {
                jsonPath.get("propertyMessages").toString().contains(propertyMessage);
            }else {
                Assert.assertTrue(jsonPath.get("message").toString().contains(message));
            }
        }
    }
}
