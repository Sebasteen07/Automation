package com.intuit.ihg.common.utils.mail;

public enum MailSessionType {

	IMAP("IMAP"), SMTP("SMTP");

	private String mType = "UNKNOWN";

	MailSessionType(String t) {

		mType = t;
	}

	public String getType() {
		return mType;
	}

}
