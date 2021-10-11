//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class ReportingRestTests extends ReportingAcceptanceTests {

    @Test(enabled = true, groups = { "ReportingAcceptanceTests" })
    public void testSale() throws ClientProtocolException, IOException {
        HttpClient httpClient = flows.createHttpClientWithBrowserCookies(driver);
        HttpPost postSale = new HttpPost(
                testData.getProperty("reporting.url") + "/payment-reporting-api/services/mfgateway/v1/sale/2560791218");
        postSale.setHeader("Content-Type", "application/json");
        JSONObject payLoad = new JSONObject();
        JSONObject card = new JSONObject();
		card.put("cvvNumber", "123").put("type", "VI").put("accountNumber", "4111111111111111").put("expirationDate", "1225");
        JSONObject merchant = new JSONObject("{\"paymentSource\":\"VCS\"}");
		JSONObject address = new JSONObject("{\"name\":\"Jane\",\"zipCode\":\"12345\"}");
        JSONObject consumer = new JSONObject("{\"accountNumber\":\"test\",\"consumerName\":\"Test\"}");
		payLoad.put("transactionAmount", 1500).put("card", card)
                .put("mfGatewayMerchant", merchant).put("billToAddress", address).put("mfGatewayConsumer", consumer);

        log(payLoad.toString());

        HttpEntity httpEntity = new StringEntity(payLoad.toString());
        postSale.setEntity(httpEntity);
        HttpResponse response = httpClient.execute(postSale);
        log("Response: " + EntityUtils.toString(response.getEntity()));
        assertEquals(200, response.getStatusLine().getStatusCode());
        flows.logOut(driver);
    }
}
