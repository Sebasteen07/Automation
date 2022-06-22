//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import com.medfusion.common.utils.YopMail;
import com.medfusion.utils.TransactionResourceDetails;
import io.restassured.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ReportingRestTests extends ReportingAcceptanceTests {

    @Test(priority = 1, enabled = true, groups = { "ReportingAcceptanceTests" })
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

    @Test(priority = 2, enabled = true, groups = { "ReportingAcceptanceTests" })
    public void testInstaMedEmailReceiptForECheckSale() throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();
        logStep("Hit the send receipt endpoint");
        Response response = transaction.sendReceipt(testData.getProperty("base.url.v1"),
                testData.getProperty("customer.id"),
                testData.getProperty("email.address"),
                testData.getProperty("echeck.order.id"), testData.getProperty("echeck.transaction.id"));

        Assert.assertTrue(response.getStatusCode() == 200);
        log("Receipt was sent successfully");

        YopMail yopMail = new YopMail(driver);
        String emailContent = testData.getProperty("receipt.payment.status") + "\n" +testData.getProperty("echeck.receipt.amount")+ "\n"
                + testData.getProperty("echeck.receipt.date") + testData.getProperty("echeck.receipt.type")+ "\n"
                + testData.getProperty("echeck.receipt.patient.name") + testData.getProperty("echeck.receipt.patient.account") + "\n"
                + testData.getProperty("echeck.receipt.patient.auth.code") + testData.getProperty("echeck.receipt.account.type") + "\n"
                + testData.getProperty("echeck.receipt.account.holder.name") + testData.getProperty("echeck.receipt.account.number") + "\n"
                + testData.getProperty("echeck.receipt.routing.number") + testData.getProperty("echeck.receipt.response.message") + "\n"
                + testData.getProperty("echeck.receipt.agreement");
        log("The full text present in the email is " + emailContent);

        logStep("Verify if email is present in the inbox");
        yopMail.isMessageInEmailInbox(testData.getProperty("email.address"),
                testData.getProperty("email.subject"), emailContent, 3);
        log("Email is present in the inbox");

        logStep("Verify if email the email content is correct");
        yopMail.getEmailContent(testData.getProperty("email.address"),testData.getProperty("email.subject"),
                emailContent, 3);


    }

    @Test(priority = 3, enabled = true, groups = { "ReportingAcceptanceTests" })
    public void testInstaMedEmailReceiptForCardSale() throws Exception {

        TransactionResourceDetails transaction = new TransactionResourceDetails();

        logStep("Hit the send receipt endpoint for card sale");
        Response response = transaction.sendReceipt(testData.getProperty("base.url.v1"),
                testData.getProperty("customer.id"),
                testData.getProperty("email.address"),
                testData.getProperty("card.sale.order.id"), testData.getProperty("card.sale.transaction.id"));

        Assert.assertTrue(response.getStatusCode() == 200);
        YopMail yopMail = new YopMail(driver);

        String emailContent = testData.getProperty("receipt.payment.status") + "\n" +testData.getProperty("card.sale.receipt.amount")+ "\n"
                + testData.getProperty("card.sale.receipt.date") + testData.getProperty("card.sale.receipt.sale.type")+ "\n"
                + testData.getProperty("card.sale.receipt.patient.name") + testData.getProperty("card.sale.receipt.auth.code") + "\n"
                + testData.getProperty("card.sale.receipt.card.holder") + testData.getProperty("card.sale.receipt.card.type") + "\n"
                + testData.getProperty("card.sale.receipt.card.number") + testData.getProperty("card.sale.receipt.response.code") + "\n"
                + testData.getProperty("card.sale.receipt.mode") + testData.getProperty("card.sale.receipt.agreement");
        log("The full text present in the email is " + emailContent);

        logStep("Verify if email is present in the inbox");
        yopMail.isMessageInEmailInbox(testData.getProperty("email.address"),
                testData.getProperty("email.subject"), emailContent, 3);
        log("Email is present in the inbox");

        logStep("Verify if email the email content is correct");
        yopMail.getEmailContent(testData.getProperty("email.address"),testData.getProperty("email.subject"),
                emailContent, 3);

    }
}
