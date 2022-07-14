//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.rcm.pojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Beware, don't mess with member names and types unless you know what you're doing

public class Statement {
    // TODO "load from text file" constructor 
    
    // required          
    protected int mfStatementId; // the db sequence id returned
    protected String practiceManagementStatementId;
    protected int systemId;
    protected String emrId;
    protected String statementFormat;
    protected String statementBillingAccountNumber;
    protected String statementDate;
    protected String paymentDueDate;        
    protected StatementGuarantorInformation guarantorInformation;
    
    
    
    // optional
    protected int id;// id set in the statement 
    protected double totalCharges;
    protected double newCharges;
    protected double outstandingBalance;
    protected double insurancePayments;
    protected double insuranceAdjustments;
    protected double insuranceContributions;
    protected double insuranceBalance;
    protected double otherAdjustments;
    protected double totalReductions;
    protected double patientPayments;
    protected double otherBalances;
    protected double patientBalance;
    protected String statementComment;
    protected String dunningMessage; 
    protected ArrayList<StatementBalanceForward> balancesForward;
    protected ArrayList<StatementAgingBalance> agingBalances;
    protected ArrayList<StatementEncounter> encounters;
    protected String statementDetail; 
    protected double priorBalance;
    
    public int getId() {
        return id;
    }

    public String getPracticeManagementStatementId() {
        return practiceManagementStatementId;
    }

    public int getSystemId() {
        return systemId;
    }

    public String getEmrId() {
        return emrId;
    }

    public String getStatementFormat() {
        return statementFormat;
    }

    public String getStatementBillingAccountNumber() {
        return statementBillingAccountNumber;
    }

    public String getStatementDate() {
        return statementDate;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }      

    public StatementGuarantorInformation getGuarantorInformation() {
        return guarantorInformation;
    }

    public double getTotalCharges() {
        return totalCharges;
    }

    public double getNewCharges() {
        return newCharges;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public double getInsurancePayments() {
        return insurancePayments;
    }

    public double getInsuranceAdjustments() {
        return insuranceAdjustments;
    }

    public double getInsuranceContributions() {
        return insuranceContributions;
    }

    public double getInsuranceBalance() {
        return insuranceBalance;
    }

    public double getOtherAdjustments() {
        return otherAdjustments;
    }

    public double getTotalReductions() {
        return totalReductions;
    }
    
    public double getPatientPayments() {
        return patientPayments;
    }

    public double getOtherBalances() {
        return otherBalances;
    }

    public double getPatientBalance() {
        return patientBalance;
    }

    public String getStatementComment() {
        return statementComment;
    }

    public String getDunningMessage() {
        return dunningMessage;
    }

    public ArrayList<StatementBalanceForward> getBalancesForward() {
        return balancesForward;
    }

    public ArrayList<StatementAgingBalance> getAgingBalances() {
        return agingBalances;
    }

    public ArrayList<StatementEncounter> getEncounters() {
        return encounters;
    }

    public String getStatementDetail() {
        return statementDetail;
    }
    
    public int getMfStatementId() {
        return mfStatementId;
    }
    public double getPriorBalance() {
        return priorBalance;
    }



    // And since you can't tell beforehand, a public setter for internalId and prior balance (returned to a successful POST)    
    public Statement setMfStatementId(int internalStatementId) {
        this.mfStatementId = internalStatementId;
        return this;
    }
    public Statement setPriorBalance(double priorBalance) {
        this.priorBalance = priorBalance;
        return this;
    }
    
    // Builder
    /** 
     *  Statement builder, verifies required information is present and immutable, as a result only the internal statement id can be changed (e.g. once posted)
     */
    public static class StatementBuilder extends Statement{

        public StatementBuilder() {            
            this.agingBalances = new ArrayList<StatementAgingBalance>();
            this.balancesForward = new ArrayList<StatementBalanceForward>();
            this.encounters = new ArrayList<StatementEncounter>();
        }
        // the bunch of setter methods...
        public StatementBuilder setId(int id) {
            this.id = id;
            return this;
        }


        public StatementBuilder setPracticeManagementStatementId(String practiceManagementStatementId) {
            this.practiceManagementStatementId = practiceManagementStatementId;
            return this;
        }


        public StatementBuilder setSystemId(int systemId) {
            this.systemId = systemId;
            return this;
        }


        public StatementBuilder setEmrId(String emrId) {
            this.emrId = emrId;
            return this;
        }


        public StatementBuilder setStatementFormat(String statementFormat) {
            if (!statementFormat.equals("FORMAT_1") && !statementFormat.equals("FORMAT_2")) {
                throw new IllegalArgumentException("Use either FORMAT_1 or FORMAT_2.");
            }
            this.statementFormat = statementFormat;
            return this;
            
        }


        public StatementBuilder setStatementBillingAccountNumber(String statementBillingAccountNumber) {
            this.statementBillingAccountNumber = statementBillingAccountNumber;
            return this;
        }


        public StatementBuilder setStatementDate(String statementDate) {
            this.statementDate = statementDate;
            return this;
        }


        public StatementBuilder setPaymentDueDate(String paymentDueDate) {
            this.paymentDueDate = paymentDueDate;
            return this;
        }


        public StatementBuilder setGuarantorInformation(StatementGuarantorInformation guarantorInformation) {
            this.guarantorInformation = guarantorInformation;
            return this;
        }


        public StatementBuilder setTotalCharges(double totalCharges) {
            this.totalCharges = totalCharges;
            return this;
        }


        public StatementBuilder setNewCharges(double newCharges) {
            this.newCharges = newCharges;
            return this;
        }


        public StatementBuilder setOutstandingBalance(double outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
            return this;
        }


        public StatementBuilder setInsurancePayments(double insurancePayments) {
            this.insurancePayments = insurancePayments;
            return this;
        }


        public StatementBuilder setInsuranceAdjustments(double insuranceAdjustments) {
            this.insuranceAdjustments = insuranceAdjustments;
            return this;
        }


        public StatementBuilder setInsuranceContributions(double insuranceContributions) {
            this.insuranceContributions = insuranceContributions;
            return this;
        }


        public StatementBuilder setInsuranceBalance(double insuranceBalance) {
            this.insuranceBalance = insuranceBalance;
            return this;
        }


        public StatementBuilder setOtherAdjustments(double otherAdjustments) {
            this.otherAdjustments = otherAdjustments;
            return this;
        }
        
        public StatementBuilder setPatientPayments(double patientPayments) {
            this.patientPayments = patientPayments;
            return this;
        }
        
        public StatementBuilder setTotalReductions(double totalReductions) {
            this.totalReductions = totalReductions;
            return this;
        }


        public StatementBuilder setOtherBalances(double otherBalances) {
            this.otherBalances = otherBalances;
            return this;
        }


        public StatementBuilder setPatientBalance(double patientBalance) {
            this.patientBalance = patientBalance;
            return this;
        }


        public StatementBuilder setStatementComment(String statementComment) {
            this.statementComment = statementComment;
            return this;
        }


        public StatementBuilder setDunningMessage(String dunningMessage) {
            this.dunningMessage = dunningMessage;
            return this;
        }


        public StatementBuilder setBalancesForward(ArrayList<StatementBalanceForward> balancesForward) {
            this.balancesForward = balancesForward;
            return this;
        }


        public StatementBuilder setAgingBalances(ArrayList<StatementAgingBalance> agingBalances) {
            this.agingBalances = agingBalances;
            return this;
        }


        public StatementBuilder addEncounter(StatementEncounter encounter) {
            this.encounters.add(encounter);
            return this;
        }


        public StatementBuilder setStatementDetail(String statementDetail) {
            this.statementDetail = statementDetail;
            return this;
        }
        
        // the builder return
        /**
         *  practiceManagementStatementId, systemId, emrId, statementFormat, statementBillingAccountNumber, 
         *  statementDate, paymentDueDate, and some (see doc) guarantor details are required for a valid statement
         */
        public Statement buildStatement() throws IOException{
            if ((systemId == 0)         
                    || isEmptyOrNull(practiceManagementStatementId)
                    || isEmptyOrNull(emrId)
                    || isEmptyOrNull(statementFormat)
                    || isEmptyOrNull(statementBillingAccountNumber)
                    || isEmptyOrNull(statementDate)
                    || isEmptyOrNull(paymentDueDate)
                    // constructor checks required constraints already, guarantor is either valid or null
                    || (guarantorInformation == null)){
                throw new IllegalStateException("Required parameters are missing, cannot build statement. See docs");
            }
                    
            return new Statement(this);
        }
        
        // the utility methods        
        public StatementBuilder setPatientIdentifiers(int systemId, String emrId, String statementBillingAccountNumber){
            return this.setSystemId(systemId).setEmrId(emrId).setStatementBillingAccountNumber(statementBillingAccountNumber);            
        }
        
        public StatementBuilder setStatementAndPaymentDueDate(String statementDate, String paymentDueDate){
            this.statementDate = statementDate;
            this.paymentDueDate = paymentDueDate; 
            return this;
        }
        
        // shorthand, guarantor info is required but not used, so some generic strings suffice to check data saves
        public StatementBuilder addGenericGuarantor(){
            Random r = new Random();            
            this.guarantorInformation  = new StatementGuarantorInformation(("randGuarFN"+r.nextInt(100)),
                    ("randGuarLN"+r.nextInt(100)),("randGuarAdd1"+r.nextInt(100)),("randGuarCity1"+r.nextInt(100)),
                    "AL","12345",Integer.toString(r.nextInt(100)),"Self",("randGuarAdd2"+r.nextInt(100)),"US");
            return this;
        }
        
        public StatementBuilder addSampleStatementPdf() throws IOException{
            Scanner scan = new Scanner( ClassLoader.getSystemResource("testfiles/sampleb64stmtpdf.txt").openStream());
            this.statementDetail = new String(scan.useDelimiter("\\Z").next());
            scan.close();
            return this;
        }                       
        
        public StatementBuilder addBalanceForward(StatementBalanceForward newbal){
            for(StatementBalanceForward bal : balancesForward){
                if (bal.getBalanceForwardType().equals(newbal.getBalanceForwardType())) throw new IllegalArgumentException("Balances forward only support one of each type max.");
            }
            this.balancesForward.add(new StatementBalanceForward(newbal.getBalanceForwardType(),newbal.getBalanceForwardAmount()));
            return this;
            
        }
        
        public StatementBuilder addAgingBalance(StatementAgingBalance newbal){
            for(StatementAgingBalance bal : agingBalances){
                if (bal.getAgingBalanceType().equals(newbal.getAgingBalanceType()) && bal.getAgingBalanceRange().equals(newbal.getAgingBalanceRange())) throw new IllegalArgumentException("There already is an aging bracket of that type and range.");
            }
            this.agingBalances.add(new StatementAgingBalance(newbal.getAgingBalanceType(),newbal.getAgingBalanceRange(),newbal.getAgingBalanceAmount()));
            return this;            
        }
        public StatementBuilder addEncounter() throws IOException{                   
            this.encounters.add(new StatementEncounter());
            return this;
        }
    }   
    private Statement(Statement.StatementBuilder other) throws IOException{
        this.agingBalances = new ArrayList<StatementAgingBalance>();
        this.balancesForward = new ArrayList<StatementBalanceForward>();
        this.encounters = new ArrayList<StatementEncounter>();
        this.practiceManagementStatementId = other.practiceManagementStatementId;
        this.systemId = other.systemId;
        this.emrId = other.emrId;
        this.statementFormat = other.statementFormat;
        this.statementBillingAccountNumber = other.statementBillingAccountNumber;
        this.statementDate = other.statementDate;
        this.paymentDueDate = other.paymentDueDate;
        this.guarantorInformation = new StatementGuarantorInformation(other.guarantorInformation);
        this.totalCharges = other.totalCharges;
        this.newCharges = other.newCharges;
        this.outstandingBalance = other.outstandingBalance;
        this.insurancePayments = other.insurancePayments;
        this.insuranceAdjustments = other.insuranceAdjustments;
        this.insuranceContributions = other.insuranceContributions;
        this.insuranceBalance = other.insuranceBalance;        
        this.otherAdjustments = other.otherAdjustments;
        this.totalReductions = other.totalReductions;
        this.otherBalances = other.otherBalances;
        this.patientBalance = other.patientBalance;
        this.statementComment = other.statementComment;
        this.dunningMessage = other.dunningMessage;
        this.statementDetail = other.statementDetail;
        // init
        this.balancesForward = new ArrayList<StatementBalanceForward>();
        this.agingBalances = new ArrayList<StatementAgingBalance>();
        this.encounters = new ArrayList<StatementEncounter>();
        
        for(StatementBalanceForward otherBal : other.balancesForward){
            this.balancesForward.add(otherBal);
        }
        for(StatementAgingBalance otherBal : other.agingBalances){
            this.agingBalances.add(otherBal);
        }
        
        for(StatementEncounter enc : other.encounters){            
            this.encounters.add(enc);
        }
    }
    protected Statement(){
        this.balancesForward = new ArrayList<StatementBalanceForward>();
        this.agingBalances = new ArrayList<StatementAgingBalance>();
        this.encounters = new ArrayList<StatementEncounter>();
    }    
    
    protected boolean isEmptyOrNull(String str){
        return (str == null || str.isEmpty());
    }
    
}