package com.intuit.ihg.common.entities;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/26/13
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class BillPay  {
	public static final String PHONE_NUMBER = "BillPay.PhoneNumber";
	public static final String CARD_NUMBER = "BillPay.CardNumber";
	public static final String CARD_CCV = "BillPay.CardCcv";
	public static final String CARD_EXPIRATION_MONTH = "BillPay.CardExpirationMonth";
	public static final String CARD_EXPIRATION_YEAR = "BillPay.CardExpirationYear";
	public static final String CARD_ADDRESS_1 = "BillPay.CardAddress1";
	public static final String CARD_ZIP = "BillPay.CardZip";
	public static final String DESCRIPTION = "BillPay.Description";
	
	private String phoneNumber = "";
	private String cardNumber = "";
	private String cardCcv = "";
	private String cardExpirationMonth = "";
	private String cardExpirationYear = "";
	private String cardAddress1 = "";
	private String cardZip = "";
	private String description;
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getCardNumber() {
		return cardNumber;
	}



	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}



	public String getCardCcv() {
		return cardCcv;
	}



	public void setCardCcv(String cardCcv) {
		this.cardCcv = cardCcv;
	}



	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}



	public void setCardExpirationMonth(String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}



	public String getCardExpirationYear() {
		return cardExpirationYear;
	}



	public void setCardExpirationYear(String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}



	public String getCardAddress1() {
		return cardAddress1;
	}



	public void setCardAddress1(String cardAddress1) {
		this.cardAddress1 = cardAddress1;
	}



	public String getCardZip() {
		return cardZip;
	}



	public void setCardZip(String cardZip) {
		this.cardZip = cardZip;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}


}