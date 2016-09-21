package com.medfusion.product.patientportal2.pojo;

import com.medfusion.common.utils.IHGUtil;

public class PayNowInfo {

	private String patientName;
	private String dateOfBirth;
	private String patientAccountNumber;
	private String amount;
	private String location;
	private String paymentComment;
	private String emailAddress;
	private CreditCard card;

	public PayNowInfo() throws Exception {
		long timeStamp = System.currentTimeMillis();
		this.patientName = "MyName " + timeStamp;
		this.dateOfBirth = "01/01/1990";
		this.patientAccountNumber = IHGUtil.createRandomNumericString(5);
		this.amount = IHGUtil.createRandomNumericString(3);
		this.paymentComment = "My PayNow comment. " + timeStamp;
		this.emailAddress = IHGUtil.createRandomEmailAddress("paynow@mailinator.com");
		CreditCard card = new CreditCard();
		this.card = card;
	}

	public PayNowInfo(CreditCard card, String patientName, String dateOfBirth, String patientAccountNumber, String amount, String location, String paymentComment,
			String emailAddress) {
		this.patientName = patientName;
		this.dateOfBirth = dateOfBirth;
		this.patientAccountNumber = patientAccountNumber;
		this.amount = amount;
		this.location = location;
		this.paymentComment = paymentComment;
		this.emailAddress = emailAddress;
		this.card = card;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPatientAccountNumber() {
		return patientAccountNumber;
	}

	public void setPatientAccountNumber(String patientAccountNumber) {
		this.patientAccountNumber = patientAccountNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPaymentComment() {
		return paymentComment;
	}

	public void setPaymentComment(String paymentComment) {
		this.paymentComment = paymentComment;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public CreditCard getCard() {
		return card;
	}

	public void setCard(CreditCard card) {
		this.card = card;
	}
}
