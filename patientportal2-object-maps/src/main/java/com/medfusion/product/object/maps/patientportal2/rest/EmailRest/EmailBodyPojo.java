package com.medfusion.product.object.maps.patientportal2.rest.EmailRest;

//TODO: Create enums for senderType, subtype, type and recipientType

public class EmailBodyPojo {

	private String senderType = "PRACTICESTAFFID";
	private String recipientType = "PRACTICEMEMBERID";
	private String type = "ALL";
	private String subtype = "ALL";
	private String senderValue;
	private String recipientValue;
	private String subject;
	private String textBody;
	private String htmlBody;

	public String getSenderValue() {
		return senderValue;
	}

	public void setSenderValue(String senderValue) {
		this.senderValue = senderValue;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSenderType() {
		return senderType;
	}

	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getRecipientValue() {
		return recipientValue;
	}

	public void setRecipientValue(String recipientValue) {
		this.recipientValue = recipientValue;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}
}
