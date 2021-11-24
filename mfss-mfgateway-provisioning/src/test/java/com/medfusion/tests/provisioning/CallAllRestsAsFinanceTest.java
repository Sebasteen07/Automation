package com.medfusion.tests.provisioning;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.medfusion.factory.ProvisioningFactory;
import com.medfusion.factory.pojos.provisioning.ApiVersions;
import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.tests.BaseRestTest;
import com.medfusion.util.Data;

/**
 * Created by lhrub on 18.01.2016.
 */
public class CallAllRestsAsFinanceTest extends BaseRestTest {



    private String baseUrl = Data.get("url") + "/provisioning-api/services/v";

    @Test
    public void testGetMerchantByVariousIds() {
        logger.info("Testing with the newest api.");
        getMerchantByVariousIds(ApiVersions.BASE_API_VERSION);
        logger.info("Testing with the old api.");
        getMerchantByVariousIds(ApiVersions.OLD_API_VERSION);
    }

    public void getMerchantByVariousIds(int apiVersion) {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()), Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"), financeUser.getRole());


        logger.info("Retrieving merchant by MMID:" + expectedMerchant.mmid);
        HttpGet httpGet = buildHttpGet(baseUrl + apiVersion + "/application/1/merchants/" + expectedMerchant.mmid,
                financeUser);
        String merchantByMmid = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion), new JSONObject(merchantByMmid),
                false);
        
        logger.info("Retrieving merchant by External ID:" + expectedMerchant.externalId);
        httpGet = buildHttpGet(
                baseUrl + apiVersion + "/application/1/merchants?customerId=" + expectedMerchant.externalId,
                financeUser);
        String merchantByCustomerId = executeRequest(httpGet);
        
        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(merchantByCustomerId), false);
        
        logger.info("Retrieving merchant by ElementPS Acceptor ID:" + expectedMerchant.elementAcceptorId);
        httpGet = buildHttpGet(
                baseUrl + apiVersion + "/application/1/merchants?elementMid=" + expectedMerchant.elementAcceptorId,
                financeUser);
        String merchantByElementId = executeRequest(httpGet);
        
        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(merchantByElementId), false);

        logger.info("Retrieving merchant by Vantiv Litle (PBF) MID:" + expectedMerchant.vantivPbfMid);
        httpGet = buildHttpGet(
                baseUrl + apiVersion + "/application/1/merchants?litleMid=" + expectedMerchant.vantivPbfMid,
                financeUser);
        String merchantBylitleMid = executeRequest(httpGet);       
        
        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(merchantBylitleMid), false);
    }

    @Test
    public void getInnerStructuresOfMerchant() {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()),
                Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"),
                financeUser.getRole());

        logger.info("Retrieving Account Details of merchant: " + expectedMerchant.mmid);
        HttpGet httpGet = buildHttpGet(
                baseUrl + ApiVersions.ACCOUNT_DETAILS_API_VERSION + "/application/1/merchants/" + expectedMerchant.mmid
                        + "/accountDetails",
                financeUser);
        String accountsIds = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getAccountsIdsAsJSON(), new JSONObject(accountsIds), false);

        logger.info("Retrieving Statement of merchant: " + expectedMerchant.mmid);
        httpGet = buildHttpGet(
                baseUrl + ApiVersions.STATEMENTS_API_VERSION + "/application/1/merchants/" + expectedMerchant.mmid
                        + "/statements",
                financeUser);
        String statements = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getStatementOptionsAsJSON(), new JSONObject(statements), false);

        logger.info("Retrieving Rates of merchant: " + expectedMerchant.mmid);
        httpGet = buildHttpGet(
                baseUrl + ApiVersions.RATE_API_VERSION + "/application/1/merchants/" + expectedMerchant.mmid + "/rates",
                financeUser);
        String rates = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getRatesAsJSON(ApiVersions.RATE_API_VERSION), new JSONObject(rates),
                false);

        logger.info("Retrieving Frauds of merchant: " + expectedMerchant.mmid);
        httpGet = buildHttpGet(
                baseUrl + ApiVersions.FRAUD_API_VERSION + "/application/1/merchants/" + expectedMerchant.mmid
                        + "/frauds",
                financeUser);
        String frauds = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getFraudsAsJSON(), new JSONObject(frauds), false);

        logger.info("Retrieving Status of merchant: " + expectedMerchant.mmid);
        httpGet = buildHttpGet(
                baseUrl + ApiVersions.BASE_API_VERSION + "/application/1/merchants/" + expectedMerchant.mmid
                        + "/status",
                financeUser);
        String status = executeRequest(httpGet);
        Assert.assertEquals("\"" + expectedMerchant.status + "\"", status);

        httpGet = buildHttpGet(
                baseUrl + ApiVersions.OLD_API_VERSION + "/application/1/merchants/" + expectedMerchant.mmid + "/status",
                financeUser);
        status = executeRequest(httpGet);
        Assert.assertEquals("\"" + expectedMerchant.status + "\"", status);
    }

    @Test
    public void testSearchMerchant() {
        logger.info("Testing with the newest api.");
        searchMerchant(ApiVersions.BASE_API_VERSION);
        logger.info("Testing with the old api.");
        searchMerchant(ApiVersions.OLD_API_VERSION);
    }

    public void searchMerchant(int apiVersion) {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()),
                Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"),
                financeUser.getRole());

        logger.info("Searching for merchant by MMID: " + expectedMerchant.mmid);
        HttpGet httpGet = buildHttpGet(
                baseUrl + apiVersion + "/application/1/merchants/search?searchFor=" + expectedMerchant.mmid,
                financeUser);
        String actualMerchantByMmid = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(actualMerchantByMmid.substring(1, actualMerchantByMmid.length() - 1)), false);

        logger.info("Searching for merchant by Name: " + expectedMerchant.name);
        httpGet = buildHttpGet(
                baseUrl + apiVersion + "/application/1/merchants/search?searchFor=" + expectedMerchant.name,
                financeUser);
        String actualMerchantByName = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(actualMerchantByName.substring(1, actualMerchantByName.length() - 1)), false);

        logger.info("Searching for merchant by External ID: " + expectedMerchant.externalId);
        httpGet = buildHttpGet(
                baseUrl + apiVersion + "/application/1/merchants/search?searchFor=" + expectedMerchant.externalId,
                financeUser);
        String actualMerchantByExternalId = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(actualMerchantByExternalId.substring(1, actualMerchantByExternalId.length() - 1)),
                false);
    }

    @Test
    public void testCreateAndUpdateMerchant() {
        logger.info("Testing with the newest api.");
        createAndUpdateMerchant(ApiVersions.BASE_API_VERSION, ApiVersions.RATE_API_VERSION);
        logger.info("Testing with the old api.");
        createAndUpdateMerchant(ApiVersions.OLD_API_VERSION, ApiVersions.OLD_RATE_API_VERSION);
    }

    public void createAndUpdateMerchant(int apiVersion, int ratesVersion) {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()),
                Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"),
                financeUser.getRole());
        ProvisioningFactory.randomizeMerchantIdentifiers(expectedMerchant);
        Merchant expecteUpdatedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("updatedMerchant"),
                financeUser.getRole());
        ProvisioningFactory.randomizeMerchantIdentifiers(expecteUpdatedMerchant);

        logger.info("Creating new merchant");
        HttpPost httpPost = buildHttpPost(baseUrl + apiVersion + "/application/1/merchants", financeUser,
                expectedMerchant.getGeneralInfoAsJSON());
        String newMerchant = executeRequest(httpPost);
        Long mmid = new JSONObject(newMerchant).getLong("id");

        Assert.assertNotNull("Merchant does not have MMID", mmid);
        Assert.assertEquals("MMID does not have proper length", mmid.toString().length(), 10);

        logger.info("Initializing Ratesl for merchant with MMID \" + mmid");
        httpPost = buildHttpPost(baseUrl + ApiVersions.RATE_API_VERSION + "/application/1/merchants/" + mmid + "/rates",
                financeUser, expectedMerchant.getRatesAsJSON(ratesVersion));
        String initializedRates = executeRequest(httpPost);

        logger.info("Updating each merchant's section: with MMID: " + mmid);
        HttpPut httpPut = buildHttpPut(baseUrl + apiVersion + "/application/1/merchants/" + mmid + "/generalinfo",
                financeUser, expecteUpdatedMerchant.getGeneralInfoAsJSON());
        executeRequest(httpPut);
        httpPut = buildHttpPut(
                baseUrl + ApiVersions.ACCOUNT_DETAILS_API_VERSION + "/application/1/merchants/" + mmid
                        + "/accountDetails",
                financeUser, expecteUpdatedMerchant.getAccountsIdsAsJSON());
        executeRequest(httpPut);
        httpPut = buildHttpPut(
                baseUrl + ApiVersions.STATEMENTS_API_VERSION + "/application/1/merchants/" + mmid + "/statements",
                financeUser, expecteUpdatedMerchant.getStatementOptionsAsJSON());
        executeRequest(httpPut);
        httpPut = buildHttpPut(baseUrl + ApiVersions.RATE_API_VERSION + "/application/1/merchants/" + mmid + "/rates",
                financeUser, expecteUpdatedMerchant.getRatesAsJSON(ratesVersion));
        executeRequest(httpPut);
        httpPut = buildHttpPut(baseUrl + ApiVersions.FRAUD_API_VERSION + "/application/1/merchants/" + mmid + "/frauds",
                financeUser, expecteUpdatedMerchant.getFraudsAsJSON());
        executeRequest(httpPut);

        HttpGet httpGet = buildHttpGet(baseUrl + apiVersion + "/application/1/merchants/" + mmid, financeUser);
        String actualUpdatedMerchantByMmid = executeRequest(httpGet);

        JSONAssert.assertEquals(expectedMerchant.getRatesAsJSON(ratesVersion), new JSONObject(initializedRates), false);
        JSONAssert.assertEquals(expecteUpdatedMerchant.getMerchantDetailAsJSON(apiVersion),
                new JSONObject(actualUpdatedMerchantByMmid), false);
    }
}
