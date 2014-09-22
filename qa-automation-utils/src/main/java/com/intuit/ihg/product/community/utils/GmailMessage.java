package com.intuit.ihg.product.community.utils;

public class GmailMessage {
	
	private String message;
	
	private String message2;
	
	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public GmailMessage(){
		
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String toString() {
		return "[Message=" + message+ "]";
	}
	
}
