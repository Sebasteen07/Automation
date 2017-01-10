package com.medfusion.product.practice.api.pojo;

public class VCSPaymentInfo {

	public VCSPaymentInfo(String cardHolderName, String creditCardType, String creditCardNumber, String creditCardExpirationYear,
			String creditCardExpirationMonth, String amountToCharge, String cVVCode, String cardholderZip, String accountNumber, String patientName,
			String paymentComment, String serviceLocation) {
		this.cardHolderName = cardHolderName;
		this.creditCardType = creditCardType;
		this.creditCardNumber = creditCardNumber;
		this.creditCardExpirationYear = creditCardExpirationYear;
		this.creditCardExpirationMonth = creditCardExpirationMonth;
		this.amountToCharge = amountToCharge;
		this.cVVCode = cVVCode;
		this.cardholderZip = cardholderZip;
		this.serviceLocation = serviceLocation;
		this.accountNumber = accountNumber;
		this.patientName = patientName;
		this.paymentComment = paymentComment;
	}

	public String cardHolderName;
	public String creditCardType;
	public String creditCardNumber;
	public String creditCardExpirationYear;
	public String creditCardExpirationMonth;
	public String amountToCharge;
	public String cVVCode;
	public String cardholderZip;
	public String serviceLocation;
	public String accountNumber;
	public String patientName;
	public String paymentComment;

	public String toString() {
		return "Payment info " + cardHolderName + ", " + creditCardType + ", " + creditCardNumber + ", " + creditCardExpirationYear + ", "
				+ creditCardExpirationMonth + ", " + amountToCharge + ", " + cVVCode + ", " + cardholderZip + ", " + accountNumber + ", " + patientName + ", "
				+ paymentComment + ", " + serviceLocation;

	}
}
