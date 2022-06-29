//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.rcm.pojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;

/**  
 * See static encounter testfile for an example. Setters return the object for chaining.
 * Required: patientInformation (PatientName, the other two optional), encounterDateTime (with tz offset, yyyy-nn-ddThh:mm:ss[+/-]hh:mm), practiceProviderName, encounterChargeTotal, 
 * 
 * Optional: externalEncounterID, encounterLocationID, encounterLocation, encounterDescription, practiceProviderId, encounterComments, 
 *           encounterReductionTotal, encounterPatientChargesTotal, encounterPatientPaymentsTotal, encounterInsuranceChargesTotal, 
 *           encounterInsurancePaymentsTotal, encounterInsuranceAdjustmentsTotal, encounterPatientTransferTotal, encounterInsuranceTransferTotal,
 *           encounterInsuranceBalance, encounterPatientBalance, encounterInsuranceContribution, encounterOtherAdjustmentsTotal, encounterICDCodeEncounterCharges
 *           
 *   
**/
public class StatementEncounter {
    
    /**
     * This only reads a static  JSON file from test resources, see path.
     * It's completely sufficient for most operations as the pdf validation is not automated (and encounters are just in details)
     */
    public String retrieveGenericJSONEncounterFromFile() throws IOException{        
        Scanner scan = new Scanner( ClassLoader.getSystemResource("testfiles/sampleEncounterJSON.txt").openStream());
        String content = new String(scan.useDelimiter("\\Z").next());
        scan.close();
        return content;
    }
    public StatementEncounter(){
        this.encounterCharges = new ArrayList<StatementEncounterCharge>();
    }
    // Members are listed in the order they appear in statements. See class description for required/optional
    private StatementEncounterPatientInformation patientInformation;
    private String externalEncounterID;    
    //  yyyy-nn-ddThh:mm:ss[+/-]hh:mm  
    private String encounterDateTime;
    private String encounterLocationId;
    private String encounterLocation;
    private String encounterDescription;
    private String practiceProviderId;
    // "$LastN, $FirstN"
    private String practiceProviderName;
    private String encounterComments;
    private double encounterChargeTotal;
    private double encounterReductionTotal;
    private double encounterPatientChargesTotal;
    private double encounterPatientPaymentsTotal;
    private double encounterInsuranceChargesTotal;
    private double encounterInsurancePaymentsTotal;
    private double encounterInsuranceAdjustmentsTotal;
    private double encounterPatientTransferTotal;
    private double encounterInsuranceTransferTotal;
    private double encounterInsuranceBalance;
    private double encounterPatientBalance;
    private double encounterInsuranceContribution;
    private double encounterOtherAdjustmentsTotal;
    // Wrapped array of codes of the encounter, transformed e.g. "encounterICDCode":{"encounterICDCode":["R10.11"]}
    private StatementEncounterICDCode encounterICDCode;       
    // Array of encounter charge objects.
    private ArrayList<StatementEncounterCharge> encounterCharges;    
    
    public StatementEncounterPatientInformation getPatientInformation() {
        return patientInformation;
    }

    public StatementEncounter setPatientInformation(StatementEncounterPatientInformation patientInformation) {
        this.patientInformation = new StatementEncounterPatientInformation(patientInformation);
        return this;
    }

    public String getExternalEncounterID() {
        return externalEncounterID;
    }

    public StatementEncounter setExternalEncounterID(String externalEncounterID) {
        this.externalEncounterID = externalEncounterID;
        return this;
    }

    public String getEncounterDateTime() {
        return encounterDateTime;
    }

    public StatementEncounter setEncounterDateTime(String encounterDateTime) {
        this.encounterDateTime = encounterDateTime;
        return this;
    }

    public String getEncounterLocationId() {
        return encounterLocationId;
    }

    public StatementEncounter setEncounterLocationId(String encounterLocationId) {
        this.encounterLocationId = encounterLocationId;
        return this;
    }

    public String getEncounterLocation() {
        return encounterLocation;
    }

    public StatementEncounter setEncounterLocation(String encounterLocation) {
        this.encounterLocation = encounterLocation;
        return this;
    }

    public String getEncounterDescription() {
        return encounterDescription;
    }

    public StatementEncounter setEncounterDescription(String encounterDescription) {
        this.encounterDescription = encounterDescription;
        return this;
    }

    public String getPracticeProviderId() {
        return practiceProviderId;
    }

    public StatementEncounter setPracticeProviderId(String practiceProviderId) {
        this.practiceProviderId = practiceProviderId;
        return this;
    }

    public String getPracticeProviderName() {
        return practiceProviderName;
    }

    public StatementEncounter setPracticeProviderName(String practiceProviderName) {
        this.practiceProviderName = practiceProviderName;
        return this;
    }

    public String getEncounterComments() {
        return encounterComments;
    }

    public StatementEncounter setEncounterComments(String encounterComments) {
        this.encounterComments = encounterComments;
        return this;
    }

    public double getEncounterChargeTotal() {
        return encounterChargeTotal;
    }

    public StatementEncounter setEncounterChargeTotal(double encounterChargeTotal) {
        this.encounterChargeTotal = encounterChargeTotal;
        return this;
    }

    public double getEncounterReductionTotal() {
        return encounterReductionTotal;
    }

    public StatementEncounter setEncounterReductionTotal(double encounterReductionTotal) {
        this.encounterReductionTotal = encounterReductionTotal;
        return this;
    }

    public double getEncounterPatientChargesTotal() {
        return encounterPatientChargesTotal;
    }

    public StatementEncounter setEncounterPatientChargesTotal(double encounterPatientChargesTotal) {
        this.encounterPatientChargesTotal = encounterPatientChargesTotal;
        return this;
    }

    public double getEncounterPatientPaymentsTotal() {
        return encounterPatientPaymentsTotal;
    }

    public StatementEncounter setEncounterPatientPaymentsTotal(double encounterPatientPaymentsTotal) {
        this.encounterPatientPaymentsTotal = encounterPatientPaymentsTotal;
        return this;
    }

    public double getEncounterInsuranceChargesTotal() {
        return encounterInsuranceChargesTotal;
    }

    public StatementEncounter setEncounterInsuranceChargesTotal(double encounterInsuranceChargesTotal) {
        this.encounterInsuranceChargesTotal = encounterInsuranceChargesTotal;
        return this;
    }

    public double getEncounterInsurancePaymentsTotal() {
        return encounterInsurancePaymentsTotal;
    }

    public StatementEncounter setEncounterInsurancePaymentsTotal(double encounterInsurancePaymentsTotal) {
        this.encounterInsurancePaymentsTotal = encounterInsurancePaymentsTotal;
        return this;
    }

    public double getEncounterInsuranceAdjustmentsTotal() {
        return encounterInsuranceAdjustmentsTotal;
    }

    public StatementEncounter setEncounterInsuranceAdjustmentsTotal(double encounterInsuranceAdjustmentsTotal) {
        this.encounterInsuranceAdjustmentsTotal = encounterInsuranceAdjustmentsTotal;
        return this;
    }

    public double getEncounterPatientTransferTotal() {
        return encounterPatientTransferTotal;
    }

    public StatementEncounter setEncounterPatientTransferTotal(double encounterPatientTransferTotal) {
        this.encounterPatientTransferTotal = encounterPatientTransferTotal;
        return this;
    }

    public double getEncounterInsuranceTransferTotal() {
        return encounterInsuranceTransferTotal;
    }

    public StatementEncounter setEncounterInsuranceTransferTotal(double encounterInsuranceTransferTotal) {
        this.encounterInsuranceTransferTotal = encounterInsuranceTransferTotal;
        return this;
    }

    public double getEncounterInsuranceBalance() {
        return encounterInsuranceBalance;
    }

    public StatementEncounter setEncounterInsuranceBalance(double encounterInsuranceBalance) {
        this.encounterInsuranceBalance = encounterInsuranceBalance;
        return this;
    }

    public double getEncounterPatientBalance() {
        return encounterPatientBalance;
    }

    public StatementEncounter setEncounterPatientBalance(double encounterPatientBalance) {
        this.encounterPatientBalance = encounterPatientBalance;
        return this;
    }

    public double getEncounterInsuranceContribution() {
        return encounterInsuranceContribution;
    }

    public StatementEncounter setEncounterInsuranceContribution(double encounterInsuranceContribution) {
        this.encounterInsuranceContribution = encounterInsuranceContribution;
        return this;
    }

    public double getEncounterOtherAdjustmentsTotal() {
        return encounterOtherAdjustmentsTotal;
    }

    public StatementEncounter setEncounterOtherAdjustmentsTotal(double encounterOtherAdjustmentsTotal) {
        this.encounterOtherAdjustmentsTotal = encounterOtherAdjustmentsTotal;
        return this;
    }

    public ArrayList<StatementEncounterCharge> getEncounterCharges() {
        return encounterCharges;
    }

    public StatementEncounter addEncCharge(StatementEncounterCharge encCharge) {
        encounterCharges.add(encCharge);
        return this;
    }
    public StatementEncounterICDCode getEncounterICDCode() {
        return encounterICDCode;
    }

    public void setEncounterICDCode(String code) {
        this.encounterICDCode = new StatementEncounterICDCode(code);
    }    
    
    protected boolean isEmptyOrNull(String str){
        return (str == null || str.isEmpty());
    }    
    
    public class StatementEncounterICDCode {
        private ArrayList<String> encounterICDCode;
        
        public ArrayList<String> getEncounterICDCode() throws JSONException {
            return encounterICDCode;
        }
        public StatementEncounterICDCode(){
            this.encounterICDCode = new ArrayList<String>();
        }
        public StatementEncounterICDCode(String code){
            this.encounterICDCode = new ArrayList<String>();
            encounterICDCode.add(code);
            }
    }
}