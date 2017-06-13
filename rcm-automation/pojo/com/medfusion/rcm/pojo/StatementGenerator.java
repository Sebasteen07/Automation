package com.medfusion.rcm.pojo;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.medfusion.rcm.pojo.Statement.StatementBuilder;

//  use something like JSONLint to indent the results to improve readability 
public class StatementGenerator {

    // TODO an actual generator
    
    public static Statement generateTestSampleStatement() throws IOException{                       
        StatementBuilder builder = new StatementBuilder();
        
        StatementGuarantorInformation guarInfo = new StatementGuarantorInformation("Derpina","Derp","1 Random Av.","Randotown","AL","12345","5","Spouse","","US");
        
        StatementEncounterPatientInformation encPatInfo = new StatementEncounterPatientInformation("rcmauto3");
        encPatInfo.setPatientDateofBirth(971136000000L).setPatientAccountNumber("131517");
        
        builder.setDunningMessage("Payyy, PaaaAaAAaayyyy").setStatementComment("statComment").setPatientBalance(116.00).setNewCharges(2.10)
            .setOtherAdjustments(0).setInsuranceBalance(0).setInsuranceContributions(0).setInsuranceAdjustments(0).setInsurancePayments(0)
            .setOutstandingBalance(116.00).setTotalCharges(100.29).setId(1).setPaymentDueDate("2016-08-09T07:44:00-04:00").setStatementDate("2016-08-09T07:44:00-04:00")
            .setStatementBillingAccountNumber("rsdkstp2").setStatementFormat("FORMAT_1").setEmrId("rsdkstp2").setSystemId(78).setPracticeManagementStatementId("rsdkstp2x2").setGuarantorInformation(guarInfo);
        
        builder.addBalanceForward(new StatementBalanceForward("PATIENT", 12.17));
        
        builder.addAgingBalance(new StatementAgingBalance("PATIENT", "Deposit", 0));
        builder.addAgingBalance(new StatementAgingBalance("PATIENT", "0-30", 116.00));
        builder.addAgingBalance(new StatementAgingBalance("PATIENT", "31-60", 0));
        builder.addAgingBalance(new StatementAgingBalance("PATIENT", "61-90", 0));
        builder.addAgingBalance(new StatementAgingBalance("PATIENT", "91-120", 0));
        builder.addAgingBalance(new StatementAgingBalance("PATIENT", "Over 120", 0));
        builder.addAgingBalance(new StatementAgingBalance("INSURANCE", "Deposit", 0));
        builder.addAgingBalance(new StatementAgingBalance("INSURANCE", "0-30", 116.00));
        builder.addAgingBalance(new StatementAgingBalance("INSURANCE", "31-60", 0));
        builder.addAgingBalance(new StatementAgingBalance("INSURANCE", "61-90", 0));
        builder.addAgingBalance(new StatementAgingBalance("INSURANCE", "91-120", 0));
        builder.addAgingBalance(new StatementAgingBalance("INSURANCE", "Over 120", 0));
        builder.setPatientPayments(100).setStatementDetail("");
                        
        StatementEncounter enc = new StatementEncounter();               
        enc.setPatientInformation(encPatInfo);
        
        enc.setExternalEncounterID("1").setEncounterDateTime("2016-08-09T07:44:00-04:00").setEncounterLocationId("28555").setEncounterLocation("IHGQA Automation RCMed")
            .setEncounterDescription("encDesc").setPracticeProviderId("1").setPracticeProviderName("Gimme, Moneynow")
            .setEncounterComments("encComment").setEncounterChargeTotal(100.1).setEncounterReductionTotal(100.11).setEncounterPatientChargesTotal(100.05)
            .setEncounterPatientPaymentsTotal(100.09).setEncounterInsuranceChargesTotal(100.16).setEncounterInsurancePaymentsTotal(100.16)
            .setEncounterInsuranceAdjustmentsTotal(100.06).setEncounterPatientTransferTotal(100.12).setEncounterInsuranceTransferTotal(100.07)
            .setEncounterInsuranceBalance(100.14).setEncounterPatientBalance(100.08).setEncounterInsuranceContribution(100.15).setEncounterOtherAdjustmentsTotal(100.04);
            
        enc.setEncounterICDCode("R10.11");              
        
        StatementEncounterCharge charge = new StatementEncounterCharge("2016-08-09T07:44:00-04:00", "MONEY", 100.2);
        
        charge.setEncounterChargeOtherAdjustmentsTotal(100.18).setEncounterChargeInsuranceAdjustmentsTotal(100.17).setInsurancePaymentAmount(100.2)
            .setInsuranceCharge(100.21).setPatientPaymentMethod("").setPatientPaymentAmount(100.19).setPatientCharge(100.17).setEncounterChargeCPTCode("57454")
            .setExternalEncounterChargeID("1");
        
        enc.addEncCharge(charge);        
        
        builder.addEncounter(enc);
        builder.setPatientPayments(100);
        builder.setPriorBalance(200);
        
        Statement result = builder.buildStatement();        

        System.out.println("jackson from object");
        ObjectMapper mapper = new ObjectMapper();
        String js = mapper.writeValueAsString(result);
        System.out.println(js);
        
        return result;
    }    
    //public static Statement generateRandomizedStatementForPatient();
}
