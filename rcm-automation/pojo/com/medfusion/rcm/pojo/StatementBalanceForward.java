package com.medfusion.rcm.pojo;

public class StatementBalanceForward {
    private String balanceForwardType;    
    private double balanceForwardAmount;
    
    /**
     * 
     * @param balanceForwardType INSURANCE or PATIENT, caps
     * @param amount double, two decimal points precision
     */
    public StatementBalanceForward(String balanceForwardType, double balanceForwardAmount){
        if(!balanceForwardType.equals("INSURANCE") && !balanceForwardType.equals("PATIENT")) {
            throw new IllegalArgumentException("The only balanceForwardTypes allowed are INSURANCE or PATIENT in caps");
        }
        this.balanceForwardType = balanceForwardType;
        this.balanceForwardAmount = balanceForwardAmount;
    }
    
    public String getBalanceForwardType() {
        return balanceForwardType;
    }

    public double getBalanceForwardAmount() {
        return balanceForwardAmount;
    }
    
}