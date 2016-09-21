package com.intuit.ihg.common.utils.mail;

public enum MailContentType {

	// Do not change strings or case - values are being used in String hash maps.

	UNKNOWN("UNKNOWN"), STRING("STRING"), MULTIPART("MULTIPART");

	private String sContentType;

	MailContentType(String sType) {

		this.sContentType = sType;
	}

	public String getContentType() {
		return sContentType;
	}


}
