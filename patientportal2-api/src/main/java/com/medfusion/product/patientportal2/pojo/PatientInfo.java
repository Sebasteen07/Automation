package com.medfusion.product.patientportal2.pojo;

public class PatientInfo {

    // all are public instead of boilerplate setters+getters. NEVER USE FOR SENSITIVE DATA
    public String memberId;
    public String practicePatientId;
    public int billingAccountNumber;
    public String balance;
    public String stmtId;
    public String username;
    public String password;
    public int deliveryPref;
    public String unlockLink;
    public String firstName;
    public String lastName;
    public String zipCode;
    public String email;

    public PatientInfo(String memberId, String practicePatientId, int billingAccountNumber, String balance,
            String stmtId, String username, String password, int deliveryPref) {
        this.memberId = memberId;
        this.practicePatientId = practicePatientId;
        this.billingAccountNumber = billingAccountNumber;
        this.balance = balance;
        this.stmtId = stmtId;
        this.username = username;
        this.password = password;
        this.deliveryPref = deliveryPref;
    }

    public PatientInfo(String memberId, String practicePatientId, int billingAccountNumber, String balance,
            String stmtId, String username, String password, int deliveryPref, String unlockLink, String firstName,
            String lastName, String zipCode, String email) {
        this.memberId = memberId;
        this.practicePatientId = practicePatientId;
        this.billingAccountNumber = billingAccountNumber;
        this.balance = balance;
        this.stmtId = stmtId;
        this.username = username;
        this.password = password;
        this.deliveryPref = deliveryPref;
        this.unlockLink = unlockLink;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.email = email;

    }

    public PatientInfo() {

    }
}
