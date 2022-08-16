// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cvvNumber",
        "type",
        "accountNumber",
        "expirationDate",
        "bin",
        "cardPresent"
})

public class Card {

    private String cardHolderName;
    private String cvvNumber;
    private String type;
    private String accountNumber;
    private String expirationDate;
    private String bin;
    private Boolean cardPresent;
    private String cardAlias;
    private String zipCode;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Boolean getCardPresent() {
        return cardPresent;
    }

    public void setCardPresent(Boolean cardPresent) {
        this.cardPresent = cardPresent;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardAlias() {
        return cardAlias;
    }

    public void setCardAlias(String cardAlias) {
        this.cardAlias = cardAlias;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public static Map<String, Object> getCardMap(String cvv, String type, String cardnumber, String expirationnumber, String bin) {
        Map<String, Object> cardmap = new HashMap<String, Object>();
        cardmap.put("cvvNumber", cvv);
        cardmap.put("type", type);
        cardmap.put("accountNumber", cardnumber);
        cardmap.put("expirationDate", expirationnumber);
        cardmap.put("bin", bin);
        cardmap.put("cardPresent", false);
        return cardmap;
    }

    public static List<Map<String, Object>> getCardsListDigitalWallet(String cardHolderName, String type,
                                                                      String cardNumber, String expirationNumber,
                                                                      String cardAlias, String zipCode, boolean primaryCardFlag) {
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("cardHolderName", cardHolderName);
        cardMap.put("cardType", type);
        cardMap.put("cardNumber", cardNumber);
        cardMap.put("cardExpiryDate", expirationNumber);
        cardMap.put("cardAlias", cardAlias);
        cardMap.put("billingAddress", BillToAddress.getBillingAdressMap(zipCode));
        cardMap.put("primaryCard", primaryCardFlag);
        List<Map<String, Object>> cards = new ArrayList<Map<String, Object>>();
        cards.add(cardMap);
        return cards;
    }

    public static Map<String, Object> payloadForSale(String paymentSource, int transactionAmount) {
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("mfGatewayMerchant", MFGatewayMerchant.getMFGatewayMerchantMap(paymentSource));
        cardMap.put("transactionAmount", transactionAmount);
        return cardMap;
    }

    public static Map<String, Object> getCardsListDigitalWalletMap(String cardHolderName, String type,
                                                                   String cardNumber, String expirationNumber,
                                                                   String cardAlias, String zipCode, boolean primaryCardFlag) {
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("cardHolderName", cardHolderName);
        cardMap.put("cardType", type);
        cardMap.put("cardNumber", cardNumber);
        cardMap.put("cardExpiryDate", expirationNumber);
        cardMap.put("cardAlias", cardAlias);
        cardMap.put("billingAddress", BillToAddress.getBillingAdressMap(zipCode));
        cardMap.put("primaryCard", primaryCardFlag);
        return cardMap;
    }


    public static Map<String, Object> getMulitipleCardsDigitalWalletMap(String cardHolderName, String type,
                                                                        String cardNumber, String expirationNumber, String cardAlias, String zipCode, String primaryCardFlag) {
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("cardHolderName", cardHolderName);
        cardMap.put("cardType", type);
        cardMap.put("cardNumber", cardNumber);
        cardMap.put("cardExpiryDate", expirationNumber);
        cardMap.put("cardAlias", cardAlias);
        cardMap.put("billingAddress", BillToAddress.getBillingAdressMap(zipCode));

        if (primaryCardFlag != "ommit") {

            cardMap.put("primaryCard", primaryCardFlag);
        } else {
            System.out.println("Ommiting primary flag");

        }

        return cardMap;
    }

    public static Map<String, Object> getBankDetailsMap(String AccountType, String AccountNumber, String RoutingNumber,
                                                        String AccountHolderFirstName, String AccountHolderLastName) {
        Map<String, Object> cardmap = new HashMap<String, Object>();
        cardmap.put("accountType", AccountType);
        cardmap.put("accountNumber", AccountNumber);
        cardmap.put("routingNumber", RoutingNumber);
        cardmap.put("accountHolderFirstName", AccountHolderFirstName);
        cardmap.put("accountHolderLastName", AccountHolderLastName);
        return cardmap;
    }

    public static Map<String, Object> getCardsListForInstaMedDigitalWallet(String mmid, String defaultPaymentMethod, String patientUrn,
                                                                           String accountAlias, String accountHolderFirstName,
                                                                           String accountHolderLastName, String accountType,
                                                                           String accountNumber, String routingNumber) {
        Map<String, Object> wallet = new HashMap<String, Object>();
        wallet.put("mmid", mmid);
        wallet.put("defaultPaymentMethod", defaultPaymentMethod);
        wallet.put("patientUrn", patientUrn);

        Map<String, Object> account = new HashMap<String, Object>();
        account.put("bankAccountAlias", accountAlias);
        account.put("bankAccountHolderFirstName", accountHolderFirstName);
        account.put("bankAccountHolderLastName", accountHolderLastName);
        account.put("bankAccountType", accountType);
        account.put("bankAccountNumber", accountNumber);
        account.put("bankRoutingNumber", routingNumber);
        wallet.put("account", account);

        return wallet;
    }

    public static Map<String, Object> getCardsListForAddAccountToExistingWallet(String mmid, String defaultPaymentMethod,
                                                                                String accountAlias, String accountHolderFirstName,
                                                                                String accountHolderLastName, String accountType,
                                                                                String accountNumber, String routingNumber, boolean isPrimary) {
        Map<String, Object> wallet = new HashMap<String, Object>();
        wallet.put("mmid", mmid);
        wallet.put("defaultPaymentMethod", defaultPaymentMethod);

        Map<String, Object> account = new HashMap<String, Object>();
        account.put("bankAccountAlias", accountAlias);
        account.put("bankAccountHolderFirstName", accountHolderFirstName);
        account.put("bankAccountHolderLastName", accountHolderLastName);
        account.put("bankAccountType", accountType);
        account.put("bankAccountNumber", accountNumber);
        account.put("bankRoutingNumber", routingNumber);
        account.put("primaryAccount", isPrimary);
        wallet.put("account", account);

        return wallet;
    }

    public static Map<String, Object> getCardsPayload(String mmid, String defaultPaymentMethod, String patientUrn,
                                                      String cardAlias, String cardExpiryDate,
                                                      String cardHolderName, String cardNumber,
                                                      String cardType, String cvv) {
        Map<String, Object> wallet = new HashMap<String, Object>();
        wallet.put("mmid", mmid);
        wallet.put("defaultPaymentMethod", defaultPaymentMethod);
        wallet.put("patientUrn", patientUrn);

        Map<String, Object> card = new HashMap<String, Object>();
        card.put("cardAlias", cardAlias);
        card.put("cardExpiryDate", cardExpiryDate);
        card.put("cardHolderName", cardHolderName);
        card.put("cardNumber", cardNumber);
        card.put("cardType", cardType);
        card.put("cvv", cvv);
        wallet.put("card", card);

        return wallet;
    }

    public static Map<String, Object> payloadForAddCardToExistingWallet(String mmid, String defaultPaymentMethod,
                                                                        String accountAlias, String cardHolderName,
                                                                        String cardExpiryDate, String cardNumber,
                                                                        String cardType, String cvv, boolean isPrimary) {
        Map<String, Object> wallet = new HashMap<String, Object>();
        wallet.put("mmid", mmid);
        wallet.put("defaultPaymentMethod", defaultPaymentMethod);

        Map<String, Object> card = new HashMap<String, Object>();
        card.put("cardAlias", accountAlias);
        card.put("cardHolderName", cardHolderName);
        card.put("cardExpiryDate", cardExpiryDate);
        card.put("cardNumber", cardNumber);
        card.put("cardType", cardType);
        card.put("cvv", cvv);
        card.put("primaryCard", isPrimary);
        wallet.put("card", card);

        return wallet;
    }

    public static Map<String, Object> payloadForSaleUsingDigitalWallet(String accountNumber,
                                                                       String consumerFirstName,
                                                                       String consumerLastName, String source,
                                                                       String paymentMethodId, int transactionAmount) {
        Map<String, Object> salePayload = new HashMap<String, Object>();

        Map<String, Object> mfGatewayConsumer = new HashMap<String, Object>();
        mfGatewayConsumer.put("accountNumber", accountNumber);
        mfGatewayConsumer.put("consumerFirstName", consumerFirstName);
        mfGatewayConsumer.put("consumerLastName", consumerLastName);
        salePayload.put("mfGatewayConsumer", mfGatewayConsumer);

        Map<String, Object> mfGatewayMerchant = new HashMap<String, Object>();
        mfGatewayMerchant.put("paymentSource", source);
        salePayload.put("mfGatewayMerchant", mfGatewayMerchant);

        salePayload.put("paymentMethodId", paymentMethodId);
        salePayload.put("transactionAmount", transactionAmount);

        return salePayload;
    }
}
