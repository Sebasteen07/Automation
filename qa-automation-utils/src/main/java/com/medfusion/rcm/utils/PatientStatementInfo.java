package com.medfusion.rcm.utils;

public class PatientStatementInfo {

	// all are public instead of boilerplate setters+getters. NEVER USE FOR SENSITIVE DATA
	public String memberId;
	public String practicePatientId;
	public int billingAccountNumber;
	public String balance;
	public String stmtId;
	public String username;
	public String password;
	public int deliveryPref;

	public PatientStatementInfo(String memberId, String practicePatientId, int billingAccountNumber, String balance, String stmtId, String username,
			String password, int deliveryPref) {
		this.memberId = memberId;
		this.practicePatientId = practicePatientId;
		this.billingAccountNumber = billingAccountNumber;
		this.balance = balance;
		this.stmtId = stmtId;
		this.username = username;
		this.password = password;
		this.deliveryPref = deliveryPref;
	}

	public PatientStatementInfo() {

	}
}
