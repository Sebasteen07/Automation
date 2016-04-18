package com.medfusion.product.jalapeno;

import java.util.Random;

import com.intuit.ihg.common.utils.IHGUtil;

public class CreditCard {
	
	public enum CardType { Visa, Mastercard, Discover, Amex}
	//https://www.paypalobjects.com/en_US/vhelp/paypalmanager_help/credit_card_numbers.htm
	private String[] visaNumbers = {"4012888888881881", "4222222222222"};
	private String[] mastercardNumbers = {"5555555555554444", "5105105105105100"};
	private String[] discoverNumbers = {"6011111111111117", "6011000990139424"};
	private String[] amexNumbers = {"378282246310005", "371449635398431"};

	private CardType type;
	private String name;
	private String cardNumber;
	private String zipCode;
	private String expirationDate;
	private String cvvCode;
	
	public CreditCard() throws Exception {
		this.zipCode = IHGUtil.createRandomZip();
		this.cvvCode = IHGUtil.createRandomNumericString(3);
		this.expirationDate = IHGUtil.createRandomNumericStringInRange(10, 12) + 
				IHGUtil.createRandomNumericStringInRange(17, 20);
	}
	
	public CreditCard(CardType type, String name, String cardNumber,
			String zipCode, String expirationDate, String cvvCode) {
		this.type = type;
		this.name = name;
		this.cardNumber = cardNumber;
		this.zipCode = zipCode;
		this.expirationDate = expirationDate;
		this.cvvCode = cvvCode;
	}

	public CreditCard(CardType type, String name) throws Exception {
		this();
		this.type = type;
		this.name = name;
		Random rand = new Random();
		
		switch (type) {
		case Visa:
			this.cardNumber = visaNumbers[rand.nextInt(visaNumbers.length)];
			break;
		case Mastercard:
			this.cardNumber = mastercardNumbers[rand.nextInt(mastercardNumbers.length)];
			break;
		case Discover:
			this.cardNumber = discoverNumbers[rand.nextInt(discoverNumbers.length)];
			break;
		case Amex:
			this.cardNumber = amexNumbers[rand.nextInt(amexNumbers.length)];
			break;
		}
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCvvCode() {
		return cvvCode;
	}

	public void setCvvCode(String cvvCode) {
		this.cvvCode = cvvCode;
	}	
	
	public String getLastFourDigits() {
		return this.cardNumber.substring(cardNumber.length() - 4);
	}
}
