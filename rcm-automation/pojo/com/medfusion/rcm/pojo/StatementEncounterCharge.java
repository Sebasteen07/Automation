package com.medfusion.rcm.pojo;

/**
 * Encounter Charge object
 * 
 * Required - no setters!: encounterChargeDateTime, encounterChargeDescription, totalCharge
 * 
 * Optional: externalEncounterChargeID, encounterChargeCPTCode, patientCharge, patientPaymentAmount, patientPaymentMethod, insuranceCharge,
 *           insurancePaymentAmount, encounterChargeInsuranceAdjustmentsTotal, encounterChargeOtherAdjustmentsTotal
 *
 */
public class StatementEncounterCharge {
    private String externalEncounterChargeID;
    private String encounterChargeDateTime;
    private String encounterChargeCPTCode;
    private String encounterChargeDescription;
    private double totalCharge;
    private double patientCharge;
    private double patientPaymentAmount;
    private String patientPaymentMethod;
    private double insuranceCharge;
    private double insurancePaymentAmount;
    private double encounterChargeInsuranceAdjustmentsTotal;
    private double encounterChargeOtherAdjustmentsTotal;
    
    public String getExternalEncounterChargeID() {
        return externalEncounterChargeID;
    }
    public StatementEncounterCharge setExternalEncounterChargeID(String externalEncounterChargeID) {
        this.externalEncounterChargeID = externalEncounterChargeID;
        return this;
    }
    public String getEncounterChargeDateTime() {
        return encounterChargeDateTime;
    }   
    public String getEncounterChargeCPTCode() {
        return encounterChargeCPTCode;
    }
    public StatementEncounterCharge setEncounterChargeCPTCode(String encounterChargeCPTCode) {
        this.encounterChargeCPTCode = encounterChargeCPTCode;
        return this;
    }
    public String getEncounterChargeDescription() {
        return encounterChargeDescription;
    }
    public double getTotalCharge() {
        return totalCharge;
    }
    public double getPatientCharge() {
        return patientCharge;
    }
    public StatementEncounterCharge setPatientCharge(double patientCharge) {
        this.patientCharge = patientCharge;
        return this;
    }
    public double getPatientPaymentAmount() {
        return patientPaymentAmount;
    }
    public StatementEncounterCharge setPatientPaymentAmount(double patientPaymentAmount) {
        this.patientPaymentAmount = patientPaymentAmount;
        return this;
    }
    public String getPatientPaymentMethod() {
        return patientPaymentMethod;
    }
    public StatementEncounterCharge setPatientPaymentMethod(String patientPaymentMethod) {
        this.patientPaymentMethod = patientPaymentMethod;
        return this;
    }
    public double getInsuranceCharge() {
        return insuranceCharge;
    }
    public StatementEncounterCharge setInsuranceCharge(double insuranceCharge) {
        this.insuranceCharge = insuranceCharge;
        return this;
    }
    public double getInsurancePaymentAmount() {
        return insurancePaymentAmount;
    }
    public StatementEncounterCharge setInsurancePaymentAmount(double insurancePaymentAmount) {
        this.insurancePaymentAmount = insurancePaymentAmount;
        return this;
    }
    public double getEncounterChargeInsuranceAdjustmentsTotal() {
        return encounterChargeInsuranceAdjustmentsTotal;
    }
    public StatementEncounterCharge setEncounterChargeInsuranceAdjustmentsTotal(double encounterChargeInsuranceAdjustmentsTotal) {
        this.encounterChargeInsuranceAdjustmentsTotal = encounterChargeInsuranceAdjustmentsTotal;
        return this;
    }
    public double getEncounterChargeOtherAdjustmentsTotal() {
        return encounterChargeOtherAdjustmentsTotal;
    }
    public StatementEncounterCharge setEncounterChargeOtherAdjustmentsTotal(double encounterChargeOtherAdjustmentsTotal) {
        this.encounterChargeOtherAdjustmentsTotal = encounterChargeOtherAdjustmentsTotal;
        return this;
    }
    
    /**
     * EncounterCharge constructor, minimum required data
     * @param encounterChargeDateTime
     * @param encounterChargeDescription
     * @param totalCharge
     */
    public StatementEncounterCharge(String encounterChargeDateTime, String encounterChargeDescription, double totalCharge){
        if ((encounterChargeDateTime == null || encounterChargeDateTime.isEmpty()) 
                || (encounterChargeDescription == null || encounterChargeDescription.isEmpty())) { 
            throw new IllegalArgumentException("Required attributes were either null or empty");
        }
        this.encounterChargeDateTime = encounterChargeDateTime;
        this.encounterChargeDescription = encounterChargeDescription;
        this.totalCharge = totalCharge;
    }
    
    protected boolean isEmptyOrNull(String str){
        return (str == null || str.isEmpty());
    }
}