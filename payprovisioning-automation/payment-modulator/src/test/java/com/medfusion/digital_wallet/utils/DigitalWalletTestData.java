// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.utils;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.tests.DigitalWalletBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import org.testng.annotations.DataProvider;

public class DigitalWalletTestData extends DigitalWalletBaseTest {

    @DataProvider(name = "valid_data")
    public static Object[][] dataProviderValidInput() throws Exception {
        testData = new PropertyFileLoader();
        String token = GatewayProxyUtils.getTokenForCustomer();
        return new Object[][]{

                {testData.getProperty("default.payment.method.echeck"), testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {testData.getProperty("default.payment.method.echeck"), testData.getProperty("patient.urn.precheck.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {testData.getProperty("default.payment.method.echeck"), testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type.saving"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {testData.getProperty("default.payment.method.echeck"), testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type.business"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {testData.getProperty("default.payment.method.echeck"), testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), "Test-fname",
                        "Patient-fname", testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {testData.getProperty("default.payment.method.echeck"), testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {"", testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},

                {" ", testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number")},
        };
    }


    @DataProvider(name = "wallet_invalid_data")
    public static Object[][] dataProvider() throws Exception {
        testData = new PropertyFileLoader();
        String token = GatewayProxyUtils.getTokenForCustomer();
        return new Object[][]{
                {testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.practice.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Provided payment from is not valid"},

                {testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.consumer.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Provided payment from is not valid"},

                {testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.phr.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Provided payment from is not valid"},

                {testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.partner.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Provided payment from is not valid"},

                {   testData.getProperty("instamed.mmid"), "Test", testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Default payment method should be one of CARD or ECHECK"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), "",
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account holder first name is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), " ",
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account holder first name is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        "", testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account holder last name is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        " ", testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account holder last name is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), "",
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account type is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), " ",
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account type is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), "Test",
                        testData.getProperty("bank.account.number"), testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account type should be one of CHECKING, SAVINGS Or BUSINESS"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        "", testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account number is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        " ", testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account number is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        "Test@#$%", testData.getProperty("bank.routing.number"),
                        400, "Bad Request", "Bank account number should only contain numbers and must be between 1 and 30 characters"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), "",
                        400, "Bad Request", "Bank routing number is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), " ",
                        400, "Bad Request", "Bank routing number is mandatory"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), "Test",
                        400, "Bad Request", "Bank routing number should only contain 9 digits number"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), "1234",
                        400, "Bad Request", "Bank routing number should only contain 9 digits number"},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("default.payment.method.echeck"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("bank.account.holder.last.name"), testData.getProperty("bank.account.type"),
                        testData.getProperty("bank.account.number"), "!@#*&",
                        400, "Bad Request", "Bank routing number should only contain 9 digits number"},


        };
    }

    @DataProvider(name = "mod_instamed_different_merchants")
    public static Object[][] dataProviderDifferentMerchants() throws Exception {
        testData = new PropertyFileLoader();

        return new Object[][] {
                //Merchant without Terminal ID for PAYN and portal urn
                { testData.getProperty("instamed.mmid.without.terminalid"),
                        testData.getProperty("patient.urn.portal.person"), 400, "Bad Request",
                        "Provided payment from is not valid" },

                //Merchant with incorrect Terminal ID for PAYN
                { testData.getProperty("instamed.mmid.incorrect.terminalid"),
                        testData.getProperty("patient.urn.portal.person"), 400, "Bad Request", null },

                //Merchant without Terminal ID for PRCC and precheck urn
                { testData.getProperty("instamed.mmid.without.terminalid"),
                        testData.getProperty("patient.urn.precheck.person"), 400, "Bad Request",
                        "Provided payment from is not valid"  },

                //Merchant with incorrect Terminal ID for PRCC
                { testData.getProperty("instamed.mmid.incorrect.terminalid"),
                        testData.getProperty("patient.urn.precheck.person"), 400, "Bad Request", null },

                //Merchant without correct store ID
                { testData.getProperty("instamed.mmid.incorrect.storeid"),
                        testData.getProperty("patient.urn.portal.person"), 400, "Bad Request", null },

                //Merchant without correct Merchant ID
                { testData.getProperty("instamed.mmid.incorrect.mid"),
                        testData.getProperty("patient.urn.portal.person"), 400, "Bad Request", null },

//				//Merchant with incorrect Client ID & Client Secret
                { testData.getProperty("instamed.mmid.incorrect.clientid.secret"),
                        testData.getProperty("patient.urn.portal.person"), 401, "Unauthorized",
                        "Invalid API key/secret pair." },

                //Merchant without Client ID & Client Secret
                { testData.getProperty("instamed.mmid.without.clientid.secret"),
                        testData.getProperty("patient.urn.portal.person"), 400, "Bad Request",
                        "Missing API key/secret pair" },

        };
    }

    @DataProvider(name = "instamed_different_patients")
    public static Object[][] dataProviderDifferentPatients() throws Exception {
        testData = new PropertyFileLoader();

        return new Object[][] {
                //Duplicate Scenario
                { testData.getProperty("patient.id"), 400, "Bad Request", "The account already exists in the wallet" },

                //Wallet does not exists scenario
                {IHGUtil.createRandomNumericString(36), 400, "Bad Request", "The patient does not have associated wallet" },

        };
    }

    @DataProvider(name = "valid_card_data")
    public static Object[][] dataProviderValidInputCard() throws Exception {
        testData = new PropertyFileLoader();
        String token = GatewayProxyUtils.getTokenForCustomer();
        return new Object[][]{

                {testData.getProperty("deafult.payment.method.card"), testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv")},

                {"", testData.getProperty("patient.urn.precheck.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"), testData.getProperty("card.number1"),
                        testData.getProperty("type1"), testData.getProperty("cvv")},

                {" ", testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"), testData.getProperty("instamed.card.number.mc"),
                        testData.getProperty("type3"), testData.getProperty("cvv")},

                {"ECHECK", testData.getProperty("patient.urn.precheck.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"), testData.getProperty("card.number2"),
                        testData.getProperty("type2"), testData.getProperty("cvv.amex")},
        };
    }

    @DataProvider(name = "wallet_invalid_card_data")
    public static Object[][] dataProviderCardData() throws Exception {
        testData = new PropertyFileLoader();
        String token = GatewayProxyUtils.getTokenForCustomer();
        return new Object[][]{
                {testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.practice.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Provided payment from is not valid", ""},

                {testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.consumer.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Provided payment from is not valid", ""},

                {testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.phr.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Provided payment from is not valid", ""},

                {testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.partner.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Provided payment from is not valid", ""},

                {   testData.getProperty("instamed.mmid"), "Test", testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Default payment method should be one of CARD or ECHECK", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        "", testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card holder name is mandatory", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        " ", testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request",
                        "Card holder name must be between 1 and 25 characters, and could also contain hyphen (-), apostrophe ( ' ), period ( . ) and/or numbers", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), "",
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card type is mandatory", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), " ",
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card type is mandatory", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), "TEST",
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card type should be one of VI, MC, AX or DI", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        "", testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card number is mandatory", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        " ", testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card number is mandatory", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        "Test", testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card number should be of 13, 16 or 19 digits for card type = VI", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), "12/20",
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card expiry date should not be in the past", ""},

                {   testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), "1226",
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"),
                        testData.getProperty("cvv"),
                        400, "Bad Request", "Card expiry date should be of format mm/yy", ""},

                { testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"), "",
                        400, "Bad Request", "Card cvv should be 3 digits or 4 digits number", ""},

                {  testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"), " ",
                        400, "Bad Request", "Card cvv should be 3 digits or 4 digits number", ""},

                {  testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number2"), testData.getProperty("type2"), testData.getProperty("cvv"),
                        400, "Bad Request", "", "must be 4 digits"},

                {  testData.getProperty("instamed.mmid"), testData.getProperty("deafult.payment.method.card"),
                        testData.getProperty("patient.urn.portal.person"),
                        testData.getProperty("account.alias"), testData.getProperty("instamed.card.expiration.number"),
                        testData.getProperty("bank.account.holder.first.name"),
                        testData.getProperty("card.number4"), testData.getProperty("type4"), testData.getProperty("cvv.amex"),
                        400, "Bad Request", "", "must be 3 digits"}
        };
    }
}
