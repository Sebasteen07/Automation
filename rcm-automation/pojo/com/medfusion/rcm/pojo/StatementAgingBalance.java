package com.medfusion.rcm.pojo;

public class StatementAgingBalance {

    private String agingBalanceType;
    private String agingBalanceRange;
    private double agingBalanceAmount;
    
    /**
     * 
     * @param agingBalanceType INSURANCE or PATIENT
     * @param agingBalanceRange "Deposit" "0-30" "31-60" "61-90" "91-120" "Over 120"
     * @param agingBalanceAmount double, two decimal points precision
     */
    public StatementAgingBalance(String agingBalanceType, String agingBalanceRange, double agingBalanceAmount){
        if(!agingBalanceType.equals("INSURANCE") && !agingBalanceType.equals("PATIENT")) {
            throw new IllegalArgumentException("The only agingBalanceTypes allowed are INSURANCE or PATIENT in caps");
        }
        if(!agingBalanceRange.equals("Deposit") && !agingBalanceRange.equals("0-30") && !agingBalanceRange.equals("31-60") && !agingBalanceRange.equals("61-90") && !agingBalanceRange.equals("91-120") && !agingBalanceRange.equals("Over 120")) {
            throw new IllegalArgumentException("Check allowed agingBalanceRanges for aging brackets!");
        }
        this.agingBalanceType = agingBalanceType;
        this.agingBalanceRange = agingBalanceRange;
        this.agingBalanceAmount = agingBalanceAmount;
    }
    
    public String getAgingBalanceType() {
        return agingBalanceType;
    }
    
    public String getAgingBalanceRange() {
        return agingBalanceRange;
    }

    public double getAgingBalanceAmount() {
        return agingBalanceAmount;
    }
    
}