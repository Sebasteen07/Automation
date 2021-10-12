package utils;

public class MerchantEntity {

    private String merchantName;
    private String doingBusinessAs;
    private String practiceID;
    private String customerAccountNo;
    private String vendorType;
    private String maxTransactionLimit;
    private String payAPICustomer;
    private String feeSettlementType;
//    private String acceptedCards;
    private String paypalPartner;
    private String paypalCardNotPresentUsername;
    private String paypalCardNotPresentPassword;
    private String paypalCardPresentUsername;
    private String paypalCardPresentPassword;

    public String getPayAPICustomer() {
        return payAPICustomer;
    }

    public void setPayAPICustomer(String payAPICustomer) {
        this.payAPICustomer = payAPICustomer;
    }

    public String getFeeSettlementType() {
        return feeSettlementType;
    }

    public void setFeeSettlementType(String feeSettlementType) {
        this.feeSettlementType = feeSettlementType;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getMaxTransactionLimit() {
        return maxTransactionLimit;
    }

    public void setMaxTransactionLimit(String maxTransactionLimit) {
        this.maxTransactionLimit = maxTransactionLimit;
    }

    public String getPaypalPartner() {
        return paypalPartner;
    }

    public void setPaypalPartner(String paypalPartner) {
        this.paypalPartner = paypalPartner;
    }

    public String getPaypalCardNotPresentUsername() {
        return paypalCardNotPresentUsername;
    }

    public void setPaypalCardNotPresentUsername(String paypalCardNotPresentUsername) {
        this.paypalCardNotPresentUsername = paypalCardNotPresentUsername;
    }

    public String getPaypalCardNotPresentPassword() {
        return paypalCardNotPresentPassword;
    }

    public void setPaypalCardNotPresentPassword(String paypalCardNotPresentPassword) {
        this.paypalCardNotPresentPassword = paypalCardNotPresentPassword;
    }

    public String getPaypalCardPresentUsername() {
        return paypalCardPresentUsername;
    }

    public void setPaypalCardPresentUsername(String paypalCardPresentUsername) {
        this.paypalCardPresentUsername = paypalCardPresentUsername;
    }

    public String getPaypalCardPresentPassword() {
        return paypalCardPresentPassword;
    }

    public void setPaypalCardPresentPassword(String paypalCardPresentPassword) {
        this.paypalCardPresentPassword = paypalCardPresentPassword;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPracticeID() {
        return practiceID;
    }

    public void setPracticeID(String practiceID) {
        this.practiceID = practiceID;
    }

    public String getCustomerAccountNo() {
        return customerAccountNo;
    }

    public void setCustomerAccountNo(String customerAccountNo) {
        this.customerAccountNo = customerAccountNo;
    }

    public String getDoingBusinessAs() {
        return doingBusinessAs;
    }

    public void setDoingBusinessAs(String doingBusinessAs) {
        this.doingBusinessAs = doingBusinessAs;
    }


    public MerchantEntity(String vendorType, String merchantName, String doingBusinessAs, String practiceID,
                          String payAPICustomer, String feeSettlementType) {
        this.vendorType = vendorType;
        this.merchantName = merchantName;
        this.doingBusinessAs = doingBusinessAs;
        this.practiceID = practiceID;
        this.payAPICustomer = payAPICustomer;
        this.feeSettlementType = feeSettlementType;
    }

    public MerchantEntity(String vendorType, String merchantName, String practiceID,
                          String customerAccountNo, String maxTransactionLimit, String paypalPartner,
                          String paypalCardNotPresentUsername, String paypalCardPresentUsername) {
        this.vendorType = vendorType;
        this.merchantName = merchantName;
        this.practiceID = practiceID;
        this.customerAccountNo = customerAccountNo;
        this.maxTransactionLimit = maxTransactionLimit;
        this.paypalPartner = paypalPartner;
        this.paypalCardNotPresentUsername = paypalCardNotPresentUsername;
        this.paypalCardPresentUsername = paypalCardPresentUsername;
    }
}

