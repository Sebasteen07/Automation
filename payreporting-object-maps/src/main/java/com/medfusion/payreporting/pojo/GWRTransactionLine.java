//  Copyright 2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.pojo;

public class GWRTransactionLine {
	public String activityDate;
	public String fundedDate;
	public String paymentSource;
	public String location;
	public String staffName;
	public String patientName;
	public String accountNumber;
	public String cardType;
	public String cardHolder;
	public String cardNumber;
	public String transactionID;
	public String orderId;
	public String status;
	public String paymentAmount;
	public String refundAmount;
	
	public GWRTransactionLine(String activityDate, String fundedDate, String paymentSource, String location, String staffName, String patientName, String accountNumber, String cardType, String cardHolder, String cardNumber, String transactionID, String orderId, String status, String paymentAmount, String refundAmount){
		this.activityDate = activityDate;
		this.fundedDate = fundedDate;
		this.paymentSource = paymentSource;
		this.location = location;
		this.staffName = staffName;
		this.patientName = patientName;
		this.accountNumber = accountNumber;
		this.cardType = cardType;
		this.cardHolder = cardHolder;
		this.cardNumber = cardNumber;
		this.transactionID = transactionID;
		this.orderId = orderId;
		this.status = status;
		this.paymentAmount = paymentAmount;
		this.refundAmount = refundAmount;
		
	}
	/**
	 * Almost an equals, but if the method-originating object has empty fields, those are skipped (facts: this.x = "", other.x = "x", this.equalsSkipLeftEmpties(other) == true && other.equalsSkipLeftEmpties(this) != true)
	 * CARD TYPE CHECKED WITH IGNORECASE 
	 * @param other
	 * 
	 * @return boolean
	 */
	public boolean equalsSkipLeftEmpties(GWRTransactionLine other){
		boolean pass = true;
		if (!this.activityDate.equals("")){
			if (!this.activityDate.equals(other.activityDate)){
				System.out.println("ActivityDate: " + this.activityDate + " doesn't match found: " + other.activityDate);
				return false;
			}
		}
		if (!this.fundedDate.equals("")){
			if (!this.fundedDate.equals(other.fundedDate)){
				System.out.println("FundedDate: " + this.fundedDate + " doesn't match found: " + other.fundedDate);
				return false;
			}
		}
		if (!this.paymentSource.equals("")){
			if (!this.paymentSource.equals(other.paymentSource)){
				System.out.println("PaymentSource: " + this.paymentSource + " doesn't match found: " + other.paymentSource);
				return false;
			}
		}
		if (!this.location.equals("")){
			if (!this.location.equals(other.location)){
				System.out.println("Location: " +this.location + " doesn't match found: " +other.location);
				return false;
			}
		}
		if (!this.staffName.equals("")){
			if (!this.staffName.equals(other.staffName)){
				System.out.println("StaffName: " + this.staffName + " doesn't match found: " + other.staffName);
				return false;
			}
		}
		if (!this.patientName.equals("")){
			if (!this.patientName.equals(other.patientName)){
				System.out.println("PatientName: " + this.patientName + " doesn't match found: " + other.patientName);
				return false;
			}
		}
		if (!this.accountNumber.equals("")){
			if (!this.accountNumber.equals(other.accountNumber)){
				System.out.println("AccountNumber: " + this.accountNumber + " doesn't match found: " + other.accountNumber);
				return false;
			}
		}
		if (!this.cardType.equals("")){
			if (!this.cardType.equalsIgnoreCase(other.cardType)){
				System.out.println("CardType: " + this.cardType + " doesn't match found: " + other.cardType);
				return false;
			}
		}
		if (!this.cardHolder.equals("")){
			if (!this.cardHolder.equals(other.cardHolder)){
				System.out.println("CardHolder: " + this.cardHolder + " doesn't match found: " + other.cardHolder);
				return false;
			}
		}
		if (!this.transactionID.equals("")){
			if (!this.transactionID.equals(other.transactionID)){
				System.out.println("TransactionID: " + this.transactionID + " doesn't match found: " + other.transactionID);
				return false;
			}
		}
		if (!this.orderId.equals("")){
			if (!this.orderId.equals(other.orderId)){
				System.out.println("OrderId: " + this.orderId + " doesn't match found: " + other.orderId);
				return false;
			}
		}
		if (!this.status.equals("")){
			if (!this.status.equals(other.status)){
				System.out.println("Status: " + this.status + " doesn't match found: " + other.status);
				return false;
			}
		}
		if (!this.paymentAmount.equals("")){
			if (!this.paymentAmount.equals(other.paymentAmount)){
				System.out.println("PaymentAmount: " + this.paymentAmount + " doesn't match found: " + other.paymentAmount);
				return false;
			}
		}
		if (!this.refundAmount.equals("")){
			if (!this.refundAmount.equals(other.refundAmount)){
				System.out.println("RefundAmount: " + this.refundAmount + " doesn't match found: " + other.refundAmount);
				return false;
			}
		}
		return pass;
	}
}