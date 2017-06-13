package com.medfusion.rcm.pojo;

/**
 * patientName is required, accountNumber and dateOfBirth are optional, see sample encouter file
 *
 */
public class StatementEncounterPatientInformation {
    private String patientAccountNumber;
    private String patientName;
    private long patientDateofBirth;
    
    public String getPatientAccountNumber() {
        return patientAccountNumber;
    }

    public StatementEncounterPatientInformation setPatientAccountNumber(String patientAccountNumber) {
        this.patientAccountNumber = patientAccountNumber;
        return this;
    }
    public StatementEncounterPatientInformation (StatementEncounterPatientInformation other) {
        this.patientAccountNumber = other.getPatientAccountNumber();
        this.patientName = other.getPatientName();
        this.patientDateofBirth = other.getPatientDateofBirth();
    }

    public String getPatientName() {
        return patientName;
    }  

    public long getPatientDateofBirth() {
        return patientDateofBirth;
    }

    public StatementEncounterPatientInformation setPatientDateofBirth(long patientDateofBirth) {
        this.patientDateofBirth = patientDateofBirth;
        return this;
    }

    public StatementEncounterPatientInformation(String patientName) {        
        if (patientName == null || patientName.isEmpty()){
            throw new IllegalArgumentException("passed patientName was null or empty");
        }
        this.patientName = patientName;
    }
    
    protected boolean isEmptyOrNull(String str){
        return (str == null || str.isEmpty());
    }
}