package com.medfusion.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TransactionResourceDetails {

    protected PropertyFileLoader testData;

    public Response sendReceipt (String basev1url, String customerID, String emailAddress, String orderId, String transactionId) {

        Map<String, Object> emailReceiptRequestBody = getPayloadForSaleEmailReceipt(customerID, emailAddress,
                orderId, transactionId);

        Response response = given().body(emailReceiptRequestBody).contentType(ContentType.JSON).when().log().all()
                .post(basev1url + "receipt/")
                .then().and().log().all().extract()
                .response();

        return response;
    }

    public static Map<String, Object> getPayloadForSaleEmailReceipt(String customerId, String emailAddress,
                                                                    String orderId, String transactionId) {

        Map<String, Object> emailReceiptMap = new HashMap<String, Object>();
        emailReceiptMap.put("customerId", customerId);
        emailReceiptMap.put("emailAddress", emailAddress);
        emailReceiptMap.put("orderId", orderId);
        emailReceiptMap.put("paymentId", transactionId);
        return emailReceiptMap;
    }
}
