// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.patientportal2.pojo;

import java.util.Calendar;
import java.util.Random;

import com.medfusion.common.utils.IHGUtil;

public class CreditCard {

	public enum CardType { Visa, Mastercard, Discover, Amex}
	//https://www.paypalobjects.com/en_US/vhelp/paypalmanager_help/credit_card_numbers.htm
	private String[] visaNumbers = {"4111111111111111", "4111111111111111"};
	private String[] mastercardNumbers = {"5105105105105100", "5105105105105100"};
	private String[] discoverNumbers = {"6011111111111117", "6011000990139424"};
	private String[] amexNumbers = {"378282246310005", "371449635398431"};

	private CardType type;
	private String name;
	private String cardNumber;
	private String zipCode;
	private String expMonth;
	private String expYear;
	private String cvvCode;

	private int currMonth = Calendar.getInstance().get(Calendar.MONTH);
	private int currYear = Calendar.getInstance().get(Calendar.YEAR);

	public CreditCard() throws Exception {
		this.name = "Name" + IHGUtil.createRandomNumericString();
		Random rand = new Random();
		this.type = CardType.values()[rand.nextInt(3)];
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
		this.zipCode = IHGUtil.createRandomZip();
		this.cvvCode = IHGUtil.createRandomNumericString(3);
		this.expMonth = IHGUtil.createRandomNumericStringInRange(currMonth, 12);
		// to ensure that format of month will be MM
		if (expMonth.length() == 1)
			expMonth = "0" + expMonth;
		this.expYear = IHGUtil.createRandomNumericStringInRange(currYear, currYear + 10);
	}

	public CreditCard(CardType type, String name, String cardNumber, String zipCode, String expMonth, String expYear, String cvvCode) {
		this.type = type;
		this.name = name;
		this.cardNumber = cardNumber;
		this.zipCode = zipCode;
		this.expMonth = expMonth;
		this.expYear = expYear;
		this.cvvCode = cvvCode;
	}

	// TODO: Delete the constructor and solve conflicts
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

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpYear() {
		return expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
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
