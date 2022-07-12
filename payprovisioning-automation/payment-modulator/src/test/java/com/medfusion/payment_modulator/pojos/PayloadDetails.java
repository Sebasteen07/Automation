// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.pojos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.medfusion.common.utils.PropertyFileLoader;

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
			String cardNumber, String expirationNumber, String cardAlias, String zipCode, boolean primaryCardFlag) {
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cards",
				Card.getCardsListDigitalWallet(cardHolderName, type, cardNumber, expirationNumber, cardAlias, zipCode, primaryCardFlag));
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

	public static Map<String, Object> getPayloadForVoidSaleMap(String comment, String customerid,
			String externaltransactionid, String orderid) {

		Map<String, Object> voidmap = new HashMap<String, Object>();
		voidmap.put("comment", comment);
		voidmap.put("customerId", customerid);
		voidmap.put("externalTransactionId", externaltransactionid);
		voidmap.put("originalOrderId", orderid);
		return voidmap;
	}

	public static Map<String, Object> getPayloadForNewSaleAPI(String paymentSource, int transactionAmount) {
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.putAll(Card.payloadForSale(paymentSource, transactionAmount));
		return cardsMap;
	}
	
	public static Map<String, Object> getPayloadForCreatingChargeBack( String transactionid, String orderid,
			String chargeBackAmount) {

		Map<String, Object> chargeBackMap = new HashMap<String, Object>();
	
		chargeBackMap.put("parentExternalTransactionId", transactionid);
		chargeBackMap.put("parentOrderId", orderid);
		chargeBackMap.put("chargebackAmount", Integer.parseInt(chargeBackAmount));
		
		return chargeBackMap;
	}
	public static Map<String, Object> getPayloadForUpdatingCardDetails(String cardAlias, String zipCode, boolean makeCardPrimaryFlag) {
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cardAlias", cardAlias);
		cardsMap.put("makeCardPrimary", makeCardPrimaryFlag);
		cardsMap.put("zipCode", zipCode);
		return cardsMap;
	}

	public static Map<String, Object> getPayloadForAddingCardsToDigitalWallet(PropertyFileLoader testData, int noOfCards, int cardNoPrimary) throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		boolean primaryFlag = false;

		for(int i=1; i<noOfCards+1; i++){
			if(cardNoPrimary == i){
				primaryFlag = true;
			}
			Map<String, Object> cardMap = Card.getCardsListDigitalWalletMap(testData.getProperty("consumer.name"), testData.getProperty("type"+i),
					testData.getProperty("card.number"+i), testData.getProperty("expiration.number"+i), testData.getProperty("card.alias"+i),
					testData.getProperty("zipcode"+i), primaryFlag);

			list.add(cardMap);
			primaryFlag = false;
		}
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cards", list);
		return cardsMap;
	}

	public static Map<String, Object> getMultipleCardsPayload(String name1, String type1, String cardNumber1, String expiry1, String alias1, String zipCode1, boolean primaryFlag1,
											  String name2, String type2, String cardNumber2, String expiry2, String alias2, String zipCode2, boolean primaryFlag2,
											  String name3, String type3, String cardNumber3, String expiry3, String alias3, String zipCode3, boolean primaryFlag3) throws Exception {

		Map<String, Object> cardMap1 = Card.getCardsListDigitalWalletMap(name1, type1,
				cardNumber1, expiry1, alias1, zipCode1, primaryFlag1);

		Map<String, Object> cardMap2 = Card.getCardsListDigitalWalletMap(name2, type2,
				cardNumber2, expiry2, alias2, zipCode2, primaryFlag2);

		Map<String, Object> cardMap3 = Card.getCardsListDigitalWalletMap(name3, type3,
				cardNumber3, expiry3, alias3, zipCode3, primaryFlag3);


		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(cardMap1);
		list.add(cardMap2);
		list.add(cardMap3);

		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cards", list);
		return cardsMap;
	}
	
	public static Map<String, Object> getPayloadForAddingMultipeCardsToDigitalWallet(PropertyFileLoader testData,
			int noOfCards, int noofCrdsToBeTrue, int noOfCrdsToBeFalse, int noOfCrdsToBeNull, int noOfCrdsToBeOmit,
			int noOfCrdsToBeRandom) throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String primaryFlag = "false";
		int noOfTotalcards = noofCrdsToBeTrue + noOfCrdsToBeFalse + noOfCrdsToBeNull + noOfCrdsToBeOmit
				+ noOfCrdsToBeRandom;

		for (int i = 1; i < noOfCards + 1; i++) {

			if (noOfTotalcards != noOfCards) {
				System.out.println("Please enter proper flags which will match total no of cards");
				break;
			}
			if (noOfCards > 0 && noofCrdsToBeTrue > 0) {

				primaryFlag = "true";
				noofCrdsToBeTrue--;

			} else if (noOfCards > 0 && noOfCrdsToBeFalse > 0) {
				primaryFlag = "false";
				noOfCrdsToBeFalse--;
			}

			else if (noOfCards > 0 && noOfCrdsToBeNull > 0) {
				primaryFlag = null;
				noOfCrdsToBeNull--;

			} else if (noOfCards > 0 && noOfCrdsToBeOmit > 0) {
				primaryFlag = "ommit";
				noOfCrdsToBeOmit--;

			} else if (noOfCards > 0 && noOfCrdsToBeRandom > 0) {
				primaryFlag = "Random";
				noOfCrdsToBeRandom--;
			}

			Map<String, Object> cardMap = Card.getMulitipleCardsDigitalWalletMap(testData.getProperty("consumer.name"),
					testData.getProperty("type" + i), testData.getProperty("card.number" + i),
					testData.getProperty("expiration.number" + i), testData.getProperty("card.alias" + i),
					testData.getProperty("zipcode" + i), primaryFlag);

			list.add(cardMap);
			primaryFlag = "false";
		}
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cards", list);

		return cardsMap;
	}
	
	
	public static Map<String, Object> getPayloadForAddingMultipeCardsToExistingDigitalWallet(
			PropertyFileLoader testData, int noOfCards, int noofCrdsToBeTrue, int noOfCrdsToBeFalse,
			int noOfCrdsToBeNull, int noOfCrdsToBeOmit, int noOfCrdsToBeRandom, int flagForCardCount) throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String primaryFlag = "false";
		int noOfTotalcards = noofCrdsToBeTrue + noOfCrdsToBeFalse + noOfCrdsToBeNull + noOfCrdsToBeOmit
				+ noOfCrdsToBeRandom;

		int j = flagForCardCount;
		for (int i = 1; i < noOfCards + 1; i++, j++) {

			if (noOfTotalcards != noOfCards) {
				System.out.println("Please enter the flags which will match no of cards");
				break;
			}
			if (noOfCards > 0 && noofCrdsToBeTrue > 0) {

				primaryFlag = "true";
				noofCrdsToBeTrue--;
			} else if (noOfCards > 0 && noOfCrdsToBeFalse > 0) {
				primaryFlag = "false";
				noOfCrdsToBeFalse--;
			}

			else if (noOfCards > 0 && noOfCrdsToBeNull > 0) {
				primaryFlag = null;
				noOfCrdsToBeNull--;
			} else if (noOfCards > 0 && noOfCrdsToBeOmit > 0) {
				primaryFlag = "ommit";
				noOfCrdsToBeOmit--;
			} else if (noOfCards > 0 && noOfCrdsToBeRandom > 0) {
				primaryFlag = "Random";
				noOfCrdsToBeRandom--;
			}

			Map<String, Object> cardMap = Card
					.getMulitipleCardsDigitalWalletMap(testData.getProperty("consumer.name"),
					testData.getProperty("type" + j), testData.getProperty("card.number" + j),
					testData.getProperty("expiration.number" + j), testData.getProperty("card.alias" + j),
					testData.getProperty("zipcode" + j), primaryFlag);

			list.add(cardMap);
			primaryFlag = "false";
		}
		Map<String, Object> cardsMap = new HashMap<String, Object>();
		cardsMap.put("cards", list);

		return cardsMap;
	}

	public static Map<String, Object> getPayloadForECheckSaleMap(String transactionamount, String accountnumber,
																	String consumername, String paymentsource, String AccountType,
																 	String AccountNumber, String RoutingNumber,
																    String AccountHolderFirstName, String AccountHolderLastName) {

		Map<String, Object> authorizesalemap = new HashMap<String, Object>();
		authorizesalemap.put("transactionAmount", Integer.parseInt(transactionamount));
		authorizesalemap.put("mfGatewayConsumer",
				MFGatewayConsumer.getMFGatewayConsumerMap(accountnumber, consumername));
		authorizesalemap.put("mfGatewayMerchant", MFGatewayMerchant.getMFGatewayMerchantMap(paymentsource));
		authorizesalemap.put("account", Card.getBankDetailsMap(AccountType, AccountNumber, RoutingNumber,
				AccountHolderFirstName, AccountHolderLastName));
		return authorizesalemap;
	}

	public static Map<String, Object> getPayloadForCreatingInstaMedDigitalWallet(String mmid, String defaultPaymentMethod, String patientUrn,
																			 String accountAlias, String accountHolderFirstName,
																				 String accountHolderLastName, String accountType,
																				 String accountNumber, String routingNumber) {
		return Card.getCardsListForInstaMedDigitalWallet(mmid, defaultPaymentMethod, patientUrn, accountAlias,
						accountHolderFirstName, accountHolderLastName, accountType, accountNumber,
						routingNumber);
	}

	public static Map<String, Object> getPayloadForAddingAccountToInstaMedDigitalWallet(String mmid, String defaultPaymentMethod,
																						String accountAlias, String accountHolderFirstName,
																						String accountHolderLastName, String accountType,
																						String accountNumber, String routingNumber, boolean isPrimary) {
		return Card.getCardsListForAddAccountToExistingWallet(mmid, defaultPaymentMethod, accountAlias,
				accountHolderFirstName, accountHolderLastName, accountType, accountNumber,
				routingNumber, isPrimary);
	}
}
