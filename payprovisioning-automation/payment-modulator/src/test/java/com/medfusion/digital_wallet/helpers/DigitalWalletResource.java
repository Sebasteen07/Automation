// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.tests.DigitalWalletBaseTest;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DigitalWalletResource extends DigitalWalletBaseTest {

    protected PropertyFileLoader testData;

    public Response getCountOfExpiringCards(String token, String fromMonth, String toMonth) throws IOException {
        testData = new PropertyFileLoader();

        Response response = given().that().spec(requestSpec).header("Authorization", "Bearer " + token).when()
                .get("cards-to-expire-count?fromMonth=" + fromMonth + "&toMonth=" + toMonth).then().extract()
                .response();
        return response;
    }

    public Response getDetailsOfCards(String token) throws IOException {
        testData = new PropertyFileLoader();

        Response response = given().that().spec(requestSpec).header("Authorization", "Bearer" + token).when()
                .get("/customer/" + testData.getProperty("test.pay.customer.uuid") + "/wallets/"
                        + testData.getProperty("external.wallet.id") + "/cards/" + testData.getProperty("external.card.id"))
                .then().extract().response();

        return response;

    }

    public Response createInstaMedWallet(String token, String enterprise_id, String patient_id, String mmid,
                                         String defaultPaymentMethod, String patientUrn, String accountAlias,
                                         String accountHolderFirstName, String accountHolderLasttName, String accountType,
                                         String accountNumber, String routingNumber) throws Exception {
        testData = new PropertyFileLoader();
        Map<String, Object> digitalWallet = PayloadDetails.getPayloadForCreatingInstaMedDigitalWallet(mmid,
                defaultPaymentMethod, patientUrn, accountAlias, accountHolderFirstName, accountHolderLasttName,
                accountType, accountNumber, routingNumber);

        Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
                .post("/enterprises/" + enterprise_id + "/patients/" + patient_id + "/wallets").then().extract().response();

        return response;
    }

    public Response addAccountToExistingInstaMedWallet(String token, String enterprise_id, String patient_id,
                                                       String mmid,
                                                       String defaultPaymentMethod, String accountAlias,
                                                       String accountHolderFirstName, String accountHolderLasttName, String accountType,
                                                       String accountNumber, String routingNumber, boolean isPrimary) throws Exception {
        testData = new PropertyFileLoader();
        Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingAccountToInstaMedDigitalWallet(mmid,
                defaultPaymentMethod, accountAlias, accountHolderFirstName, accountHolderLasttName,
                accountType, accountNumber, routingNumber, isPrimary);

        Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
                .put("/enterprises/" + enterprise_id + "/patients/" + patient_id + "/wallets").then().extract().response();

        return response;
    }

    public Response createInstaMedWalletForACard(String token, String enterprise_id, String patient_id, String mmid,
                                                 String defaultPaymentMethod, String patientUrn, String cardAlias,
                                                 String cardExpiryDate, String cardHolderName, String cardNumber,
                                                 String cardType, String cvv) throws Exception {
        testData = new PropertyFileLoader();
        Map<String, Object> digitalWallet = PayloadDetails.getPayloadForCreatingInstaMedDigitalWalletForACard(mmid, defaultPaymentMethod,
                patientUrn, cardAlias, cardExpiryDate, cardHolderName, cardNumber, cardType,
                cvv);

        Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
                .post("/enterprises/" + enterprise_id + "/patients/" + patient_id + "/wallets").then().extract().response();

        return response;
    }

    public Response addCardToExistingInstaMedWallet(String token, String enterprise_id, String patient_id,
                                                    String mmid,
                                                    String defaultPaymentMethod, String accountAlias,
                                                    String cardHolderName, String cardExpiryDate, String cardNumber,
                                                    String cardType, String cvv, boolean isPrimary) throws Exception {
        testData = new PropertyFileLoader();
        Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToInstaMedDigitalWallet(mmid,
                defaultPaymentMethod, accountAlias,
                cardHolderName, cardExpiryDate, cardNumber, cardType,
                cvv, isPrimary);

        Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
                .put("/enterprises/" + enterprise_id + "/patients/" + patient_id + "/wallets").then().extract().response();

        return response;
    }

}
