//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.pojo;

public class TransactionDetailInfo {
	public String activityDate;
	public String fundedDate;
	public String paymentSource;
	public String location;
	public String staffName;
	public String patientName;
	public String accountNumber;
	public String cardType;
	public String cardholderName;
	//last 4
	public String cardNumber;
	public String transactionId;
	public String transactionType;
	public String status;
	public String paymentAmount;
	public String refundAmount;
	public boolean voidDisplayed;
	public boolean refundDisplayed;
	
	public TransactionDetailInfo(String activityDate, String fundedDate,
			String paymentSource, String location, String staffName, 
			String patientName, String accountNumber, String cardType, 
			String cardholderName, String cardNumber, String transactionId,
			String transactionType, String status, String paymentAmount, String refundAmount,
			boolean voidDisplayed, boolean refundDisplayed){
		this.activityDate = activityDate;
		this.fundedDate = fundedDate;
		this.paymentSource = paymentSource;
		this.location = location;
		this.staffName = staffName;
		this.patientName = patientName;
		this.accountNumber = accountNumber;
		this.cardType = cardType;
		this.cardholderName = cardholderName;
		this.cardNumber = cardNumber;
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.status = status;
		this.paymentAmount = paymentAmount;
		this.refundAmount = refundAmount;
		this.voidDisplayed = voidDisplayed;
		this.refundDisplayed = refundDisplayed;
	}
	
	public boolean verifyIgnoreLeftBlanks(TransactionDetailInfo other){
		boolean pass = true;
		boolean check = false;
		check = equalsIgnoreLeftBlank(this.activityDate,other.activityDate);
		if (!check){
			System.err.println("activityDates did not match! had " + this.activityDate + " found " + other.activityDate);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.fundedDate,other.fundedDate);
		if (!check){
			System.err.println("fundedDate did not match! had " + this.fundedDate + " found " + other.fundedDate);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.paymentSource,other.paymentSource);
		if (!check){
			System.err.println("paymentSource did not match! had " + this.paymentSource + " found " + other.paymentSource);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.location,other.location);
		if (!check){
			System.err.println("location did not match! had " + this.location + " found " + other.location);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.staffName,other.staffName);
		if (!check){
			System.err.println("staffName did not match! had " + this.staffName + " found " + other.staffName);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.patientName,other.patientName);
		if (!check){
			System.err.println("patientName did not match! had " + this.patientName + " found " + other.patientName);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.accountNumber,other.accountNumber);
		if (!check){
			System.err.println("accountNumber did not match! had " + this.accountNumber + " found " + other.accountNumber);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.cardholderName,other.cardholderName);
		if (!check){
			System.err.println("cardholderName did not match! had " + this.cardholderName + " found " + other.cardholderName);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.transactionId,other.transactionId);
		if (!check){
			System.err.println("transactionId did not match! had " + this.transactionId + " found " + other.transactionId);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.transactionType,other.transactionType);
		if (!check){
			System.err.println("transactionType did not match! had " + this.transactionType + " found " + other.transactionType);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.status,other.status);
		if (!check){
			System.err.println("status did not match! had " + this.status + " found " + other.status);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.paymentAmount,other.paymentAmount);
		if (!check){
			System.err.println("paymentAmount did not match! had " + this.paymentAmount + " found " + other.paymentAmount);
		}
		pass = pass && check;
		check = equalsIgnoreLeftBlank(this.refundAmount,other.refundAmount);
		if (!check){
			System.err.println("refundAmount did not match! had " + this.refundAmount + " found " + other.refundAmount);
		}
		pass = pass && check;
		check = this.voidDisplayed == other.voidDisplayed;
		if (!check){
			System.err.println("voidDisplayed did not match! had " + this.voidDisplayed + " found " + other.voidDisplayed);
		}
		pass = pass && check;
		check = this.refundDisplayed == other.refundDisplayed;
		if (!check){
			System.err.println("refundDisplayed did not match! had " + this.refundDisplayed + " found " + other.refundDisplayed);
		}
		pass = pass && check;		
		return pass;
		
	}
	public static boolean equalsIgnoreLeftBlank(String first, String other){
		if (first == null || first.equals("")) return true;  
		else return first.equalsIgnoreCase(other);
	}
	public String toString(){
		return new String("TransactionDetailInfo: activityDate: " + activityDate + " fundedDate: " + fundedDate + " paymentSource: " + paymentSource +
				"location: " + location + " staffName: " + staffName + " patientName: " + patientName + " accountNumber: " +
				accountNumber + " cardType: " + cardType + " cardholderName: " + cardholderName + " cardNumber: " + cardNumber + " transactionId: " + transactionId +
				" transactionType: " + transactionType + " status: " + status + " paymentAmount: " + paymentAmount + " refundAmount: " +
				refundAmount + " voidDisplayed: " + voidDisplayed + " refundDisplayed: " + refundDisplayed);
	}
}
