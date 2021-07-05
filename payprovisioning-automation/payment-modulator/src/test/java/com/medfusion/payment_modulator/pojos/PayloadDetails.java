// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.pojos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "mfGatewayConsumer", "mfGatewayMerchant", "transactionAmount", "initialTransactionInSeries",
		"card", "billToAddress" })

public class PayloadDetails {

	private MFGatewayConsumer mfGatewayConsumer;
	private MFGatewayMerchant mfGatewayMerchant;
	private Integer transactionAmount;
	private Boolean initialTransactionInSeries;
	private Card card;
	private BillToAddress billToAddress;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public MFGatewayConsumer getMfGatewayConsumer() {
		return mfGatewayConsumer;
	}

	public void setMfGatewayConsumer(MFGatewayConsumer mfGatewayConsumer) {
		this.mfGatewayConsumer = mfGatewayConsumer;
	}

	public MFGatewayMerchant getMfGatewayMerchant() {
		return mfGatewayMerchant;
	}

	public void setMfGatewayMerchant(MFGatewayMerchant mfGatewayMerchant) {
		this.mfGatewayMerchant = mfGatewayMerchant;
	}

	public Integer getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Integer transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Boolean getInitialTransactionInSeries() {
		return initialTransactionInSeries;
	}

	public void setInitialTransactionInSeries(Boolean initialTransactionInSeries) {
		this.initialTransactionInSeries = initialTransactionInSeries;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public BillToAddress getBillToAddress() {
		return billToAddress;
	}

	public void setBillToAddress(BillToAddress billToAddress) {
		this.billToAddress = billToAddress;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public static Map<String, Object> getPayloadForAuthorizeSaleMap(String transactionamount, String accountnumber,
			String consumername, String paymentsource, String cvv, String type, String cardnumber,
			String expirationnumber, String bin, String zipcode, String lastname, String addressline1, String city,
			String state, String firstname) throws IOException {

		Map<String, Object> authorizesalemap = new HashMap<String, Object>();
		authorizesalemap.put("transactionAmount", Integer.parseInt(transactionamount));
		authorizesalemap.put("mfGatewayConsumer",
				MFGatewayConsumer.getMFGatewayConsumerMap(accountnumber, consumername));
		authorizesalemap.put("mfGatewayMerchant", MFGatewayMerchant.getMFGatewayMerchantMap(paymentsource));
		authorizesalemap.put("card", Card.getCardMap(cvv, type, cardnumber, expirationnumber, bin));
		authorizesalemap.put("billToAddress",
				BillToAddress.getBillToAddressMap(zipcode, lastname, addressline1, city, state, firstname));
		return authorizesalemap;
	}

	public static Map<String, Object> getPayloadForCaptureMap(String transactionamount, String accountnumber,
			String consumername, String paymentsource, String cvv, String type, String cardnumber,
			String expirationnumber, String bin, String zipcode, String lastname, String addressline1, String city,
			String state, String firstname, String transactionid, String orderid) {

		Map<String, Object> capturemap = new HashMap<String, Object>();
		capturemap.put("amount", Integer.parseInt(transactionamount));
		capturemap.put("mfGatewayConsumer", MFGatewayConsumer.getMFGatewayConsumerMap(accountnumber, consumername));
		capturemap.put("mfGatewayMerchant", MFGatewayMerchant.getMFGatewayMerchantMap(paymentsource));
		capturemap.put("card", Card.getCardMap(cvv, type, cardnumber, expirationnumber, bin));
		capturemap.put("billToAddress",
				BillToAddress.getBillToAddressMap(zipcode, lastname, addressline1, city, state, firstname));
		capturemap.put("externalTransactionId", transactionid);
		capturemap.put("orderId", orderid);
		return capturemap;

	}

	public static Map<String, Object> getPayloadForVoidCreditMap(String refundamount, String accountnumber,
			String consumername, String paymentsource, String cvv, String type, String cardnumber,
			String expirationnumber, String bin, String zipcode, String lastname, String addressline1, String city,
			String state, String firstname, String transactionid) {

		Map<String, Object> voidmap = new HashMap<String, Object>();
		voidmap.put("mfGatewayConsumer", MFGatewayConsumer.getMFGatewayConsumerMap(accountnumber, consumername));
		voidmap.put("mfGatewayMerchant", MFGatewayMerchant.getMFGatewayMerchantMap(paymentsource));
		voidmap.put("card", Card.getCardMap(cvv, type, cardnumber, expirationnumber, bin));
		voidmap.put("billToAddress",
				BillToAddress.getBillToAddressMap(zipcode, lastname, addressline1, city, state, firstname));
		voidmap.put("externalTransactionId", transactionid);
		voidmap.put("transactionAmount", Integer.parseInt(refundamount));
		return voidmap;
	}

	public static Map<String, Object> getPayloadForChargeback(String mmid, String transactionid, String orderid,
			String chargebackamount) {
		Map<String, Object> chargebackmap = new HashMap<String, Object>();
		chargebackmap.put("parentExternalTransactionId", transactionid);
		chargebackmap.put("parentOrderId", orderid);
		chargebackmap.put("chargebackAmount", chargebackamount);
		return chargebackmap;
	}

	public static Map<String, Object> getPayloadForAddingCardToDigitalWallet(String cardHolderName, String type,
			String cardNumber, String expirationNumber, String cardAlias, String zipCode) {
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cards",
				Card.getCardsListDigitalWallet(cardHolderName, type, cardNumber, expirationNumber, cardAlias, zipCode));
		return cardsMap;
	}

	public static Map<String, Object> getPayloadForRefundSaleMap(String comment, String customerid,
			String externaltransactionid, String orderid, String refundamount) {

		Map<String, Object> refundmap = new HashMap<String, Object>();
		refundmap.put("comment", comment);
		refundmap.put("customerId", customerid);
		refundmap.put("externalTransactionId", externaltransactionid);
		refundmap.put("originalOrderId", orderid);
		refundmap.put("transactionAmount", Integer.parseInt(refundamount));
		return refundmap;
	}

	public static Map<String, Object> getPayloadForNewSaleAPI(String paymentSource, int transactionAmount) {
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.putAll(Card.payloadForSale(paymentSource, transactionAmount));
		return cardsMap;
	}
}
