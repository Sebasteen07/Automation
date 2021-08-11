//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.ibfibm.domain;

import java.sql.Date;

public class TransactionEntity {

    public TransactionEntity(String activityDate, String cardType, String cardNumber, String transactionId, 
    		Long mmid, String transactionType, long purchaseAmount, String expirationDate, String mid) {
		this.activityDate = activityDate;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.transactionId = transactionId;
		this.mmid = mmid;
		this.transactionType = transactionType;
		this.purchaseAmount = purchaseAmount;
		this.expirationDate = expirationDate;
		this.mid = mid;
	}

	public TransactionEntity(String transactionId) {
		super();
		this.transactionId = transactionId;
	}


	public TransactionEntity(TransactionEntity sourceTransaction) {
		this.submittedDate = sourceTransaction.getSubmittedDate();
	    this.activityDate = sourceTransaction.getActivityDate();
	    this.fundedDate = sourceTransaction.getFundedDate();
		this.patientId = sourceTransaction.getPatientId();
	    this.patientName = sourceTransaction.getPatientName();
	    this.location = sourceTransaction.getLocation();
	    this.cardType = sourceTransaction.getCardType();
	    this.cardholderName = sourceTransaction.getCardholderName();
	    this.cardNumber = sourceTransaction.getCardNumber();
	    this.transactionId = sourceTransaction.getTransactionId();
	    this.status = sourceTransaction.getStatus();
	    this.purchaseAmount = sourceTransaction.getPurchaseAmount();
	    this.staffName = sourceTransaction.getStaffName();
	    this.mmid = sourceTransaction.getMmid();
	    this.paymentSource = sourceTransaction.getPaymentSource();
	    this.transactionType = sourceTransaction.getTransactionType();
	    this.expirationDate = sourceTransaction.getExpirationDate();
	    this.mid = sourceTransaction.getMid();
	    

	}


	private Date submittedDate;
    private String activityDate;
    private String fundedDate;
	private String patientId;
    private String patientName;
    private String location;
    private String cardType;
    private String cardholderName;
    private String cardNumber;
    private String transactionId;
    private String status;
    private long purchaseAmount;
    private String staffName;
    private Long mmid;
    private String paymentSource;
    private String transactionType;
    private String expirationDate;
    private String mid;


    public String getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(String paymentSource) {
        this.paymentSource = paymentSource;
    }

    public Date getSubmittedDate() {
        if (submittedDate != null) {
            return new Date(submittedDate.getTime());
        }
        return null;
    }

    public void setSubmittedDate(Date submittedDate) {
        if (submittedDate != null) {
            this.submittedDate = new Date(submittedDate.getTime());
        }
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String depositDate) {
        this.activityDate = depositDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public long getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(long purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getMmid() {
        return mmid;
    }

    public void setMmid(Long mmid) {
        this.mmid = mmid;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


    public String getFundedDate() {
		return fundedDate;
	}

	public void setFundedDate(String fundedDate) {
		this.fundedDate = fundedDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}


}
