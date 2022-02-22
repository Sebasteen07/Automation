package com.medfusion.factory.pojos.provisioning;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lhrub on 26.11.2015.
 */
public class Merchant {

    //GeneralInfo
    public String mmid;
    public String name;
    public String status;
    public String externalId;
    public String legalName;
    public String doingBusinessAs;
    public String phone;
    public String customerServicePhone;
    public String txLimit;

    public String addressLine1;
    public String addressLine2;
    public String city;
    public String state;
    public String zip;
    public String country;

    public String merchantStatus;
    public String sicMccCode;
    public String averageTicketPrice;
    public String voucherFlag;

    public Set<String> acceptedCards;

    //Accounts and IDs
    public String routingNumber;
    public String accountNumber;
    public String accountType;
    public String amexSid;
    public String processor;
    public String federalTaxId;

    public String vantivCoreMid;
    public String vantivTid;
    public String vantivIbfMid;
    public String vantivPbfMid;

    public String elementAccountId;
    public String elementAcceptorId;

    //Statement Options
    public String merchantTagLine;
    public String merchantLogoFilename;
    public String payOrBillByPhoneNumber;
    public String payOrBillByPhoneHours;
    public String payOrBillBillQueryNumber;
    public String payOrBillBillQueryHours;
    public String stmtDueDateLeadDays;

    public String statementTestMode;
    public String detailsAgingBoxes;
    public String detailsInsuranceAgingBoxes;
    public String detailsDetachReturnByMail;
    public String detailsStatementDetail;
    public String detailsMerchantName;

    public String payOrBillByCheck;
    public String payOrBillByMoneyOrder;


    //Rates
    public String platformFeeAuth;
    public String platformFeeRefund;

    public String qTierBoundary;
    public String qTierFee;
    public String mQTierBoundary;
    public String mQTierFee;
    public String nQTierBoundary;
    public String nQTierFee;

    public String amexFee;

    public String suppressFeeSettlement;


    //Frauds and Risks
    public String ddtCount;
    public String ddtAmount;

    public String evvDailyVs90DaysPercent;
    public String evvDailyVs90DaysAmount;
    public String evvDailyVs30DaysPercent;
    public String evvDailyVs30DaysAmount;
    public String evvDailyVs7DaysPercent;
    public String evvDailyVs7DaysAmount;
    public String evv7DayVs90DaysPercent;
    public String evv7DayVs90DaysAmount;

    public String ducCount;
    public String ducAmount;

    public String sctCount;
    public String sctAmount;

    public String tdfcCount;
    public String tdfcAmount;

    public String transactionLimitToAlert;
    public String singleForcedTransactionLimit;
    public String aggregateForcedTransactionLimit;
    public String staleDays;

    public String getStateAsJSONValue(){
        String result;

        if(state.equals("Alabama")) {
            result = "AL";
        } else if (state.equals("Alaska")) {
            result = "AS";
        } else {
            result = "UNSUPPORTED STATE";
        }
        return result;
    }

    public String getVoucherFlagAsJSONValue(){
        String result;

        if(voucherFlag.equals("Magnetic Stripe")) {
            result = "MAG_STRIPE";
        } else if (voucherFlag.equals("eCommerce")) {
            result = "E_COMMERCE";
        } else {
            result = "UNSUPPORTED VOUCHER FLAG";
        }

        return result;
    }

    public JSONArray getAcceeptedCreditCardsAsJSONArray(){
        Set<String> upperCreditCards = new HashSet<String>();
        for (String card : acceptedCards) {
            upperCreditCards.add(card.toUpperCase());
        }

        return new JSONArray(upperCreditCards);
    }

    //TODO use data from property file
    public Merchant(){
        acceptedCards = new HashSet<String>();
        acceptedCards.add("Discover");
        acceptedCards.add("Mastercard");

    }

    public JSONObject getMerchantDetailAsJSON(int version) {
        JSONObject merchantDetail = getGeneralInfoAsJSON();

        merchantDetail.put("accountDetails", getAccountsIdsAsJSON());
        merchantDetail.put("statementOptions", getStatementOptionsAsJSON());
        if (version > ApiVersions.OLD_API_VERSION) {
            merchantDetail.put("contractedRates", getRatesAsJSON(3));
        } else {
            merchantDetail.put("contractedRates", getRatesAsJSON(2));
        }
        merchantDetail.put("fraudVars", getFraudsAsJSON());

        return merchantDetail;
    }

    public JSONObject getGeneralInfoAsJSON(){
        JSONObject generalInfo = new JSONObject();

        JSONObject merchantAddress = new JSONObject();;
        JSONObject remitToAddress = new JSONObject(); //TODO when I implement remit to I should initalized that
        JSONObject vantivBoardingData = new JSONObject();
        JSONArray acceptedCreditCards = new JSONArray(new String[] {"DISCV","MCARD"});

        merchantAddress.put("address1",addressLine1);
        merchantAddress.put("address2", addressLine2);
        merchantAddress.put("city", city);
        merchantAddress.put("country", (country.equals("United States"))?"US":"UNSUPPORTED COUNTRY");
        merchantAddress.put("state",getStateAsJSONValue());
        merchantAddress.put("zip",zip);

        vantivBoardingData.put("avgTicketPrice",  Long.parseLong(averageTicketPrice));
        vantivBoardingData.put("merchantStatus", merchantStatus.toUpperCase());
        vantivBoardingData.put("sicMccCode", sicMccCode);
        vantivBoardingData.put("voucherFlag", getVoucherFlagAsJSONValue());

        generalInfo.put("merchantAddress",merchantAddress);
        generalInfo.put("remitToAddress", (remitToAddress.length()==0)?JSONObject.NULL:remitToAddress);
        generalInfo.put("vantivBoardingData", vantivBoardingData);
        generalInfo.put("acceptedCreditCards", acceptedCreditCards);
        if (!mmid.isEmpty()) {
            generalInfo.put("id", Long.parseLong(mmid));
        }
        generalInfo.put("customerServicePhoneNumber",customerServicePhone );
        generalInfo.put("doingBusinessAs", doingBusinessAs);
        generalInfo.put("externalMerchantId", Long.parseLong(externalId));
        generalInfo.put("maxTransactionLimit",  Long.parseLong(txLimit));
        generalInfo.put("merchantLegalName", legalName);
        generalInfo.put("merchantName",name);
        generalInfo.put("phoneNumber", phone);

        return generalInfo;
    }

    public JSONObject getAccountsIdsAsJSON() {
        JSONObject accountsIds = new JSONObject();

        accountsIds.put("elementAccountId", elementAccountId);
        accountsIds.put("elementAcceptorId", elementAcceptorId);
        accountsIds.put("elementTerminalId", vantivTid);
        accountsIds.put("vantivIbmMid",vantivCoreMid);
        accountsIds.put("routingNumber",routingNumber);
        accountsIds.put("accountNumber", accountNumber);
        accountsIds.put("accountType",(accountType.equals("Checking"))?"C":"S");
        accountsIds.put("amexSid", amexSid);
        accountsIds.put("vantivLitleIbfMid", vantivIbfMid);
        accountsIds.put("vantivLitlePbfMid", vantivPbfMid);
        accountsIds.put("preferredProcessor", (processor.equals("Vantiv Litle"))?"LITLE":"ELEMENT");
        accountsIds.put("federalTaxId", federalTaxId);

        return accountsIds;
    }


    public JSONObject getStatementOptionsAsJSON(){
        JSONObject statementOptions = new JSONObject();

        //statementOptions.put("STMTLOGOFNAME","2560787637_ok_w123_h137.jpg"); //TODO fix statement options in automatization
        statementOptions.put("BILLQUERYHOURS",payOrBillBillQueryHours);
        statementOptions.put("BILLQUERYPHONENO",payOrBillBillQueryNumber);
        statementOptions.put("DISPLAYDETACHRETURNBYMAIL",detailsDetachReturnByMail);
        statementOptions.put("STMTDUEDATELEADDAYS",stmtDueDateLeadDays);
        statementOptions.put("PAYBYPHONENO",payOrBillByPhoneNumber);
        statementOptions.put("DISPLAYSTMTDETAIL",detailsStatementDetail);
        statementOptions.put("PAYBYPHONEHOURS",payOrBillByPhoneHours);
        statementOptions.put("PAYBYCHECKOK",payOrBillByCheck);
        statementOptions.put("DISPLAYINSAGEBRACKETS", detailsInsuranceAgingBoxes);
        statementOptions.put("MERCHANTTAGLINE",merchantTagLine);
        statementOptions.put("STMTTESTMODE",statementTestMode);
        statementOptions.put("DISPLAYMERCHANTNAME", detailsMerchantName);
        statementOptions.put("PAYBYMONEYORDEROK", payOrBillByMoneyOrder);
        statementOptions.put("DISPLAYGUARAGEBRACKETS",detailsAgingBoxes);

        return statementOptions;
    }

    public JSONObject getRatesAsJSON(int version) {
        JSONObject rates = new JSONObject();

        rates.put("perTransAuthFee", Double.parseDouble(platformFeeAuth));
        rates.put("perTransRefundFee", Double.parseDouble(platformFeeRefund));
        rates.put("qualifiedUpperBoundaryPercent", Double.parseDouble(qTierBoundary));
        rates.put("qualifiedFeePercent", Double.parseDouble(qTierFee));
        rates.put("midQualifiedUpperBoundaryPercent",Double.parseDouble(mQTierBoundary));
        rates.put("midQualifiedFeePercent",Double.parseDouble(mQTierFee));
        rates.put("nonQualifiedUpperBoundaryPercent",Double.parseDouble(nQTierBoundary));
        rates.put("nonQualifiedFeePercent",Double.parseDouble(nQTierFee));
        rates.put("suppressFeeSettlement",Boolean.parseBoolean(suppressFeeSettlement));
        if (version > ApiVersions.OLD_RATE_API_VERSION) {
            return rates.put("amexPercent", Double.parseDouble(amexFee));
        } else {
            return rates;
        }
    }

    public JSONObject getFraudsAsJSON(){
        JSONObject frauds = new JSONObject();

        frauds.put("duplicateDollarTxCount",Double.parseDouble(ddtCount));
        frauds.put("duplicateDollarTxAmount",Double.parseDouble(ddtAmount));
        frauds.put("excessiveVolumeIncreasePercent1vs90",Double.parseDouble(evvDailyVs90DaysPercent));
        frauds.put("excessiveVolumeIncreaseAmount1vs90",Double.parseDouble(evvDailyVs90DaysAmount));
        frauds.put("excessiveVolumeIncreasePercent1vs30",Double.parseDouble(evvDailyVs30DaysPercent));
        frauds.put("excessiveVolumeIncreaseAmount1vs30",Double.parseDouble(evvDailyVs30DaysAmount));
        frauds.put("excessiveVolumeIncreasePercent1vs7",Double.parseDouble(evvDailyVs7DaysPercent));
        frauds.put("excessiveVolumeIncreaseAmount1vs7",Double.parseDouble(evvDailyVs7DaysAmount));
        frauds.put("excessiveVolumeIncreasePercent7vs90",Double.parseDouble(evv7DayVs90DaysPercent));
        frauds.put("excessiveVolumeIncreaseAmount7vs90",Double.parseDouble(evv7DayVs90DaysAmount));
        frauds.put("dailyUnmatchedCreditsCount",Double.parseDouble(ducCount));
        frauds.put("dailyUnmatchedCreditsAmount",Double.parseDouble(ducAmount));
        frauds.put("sameCardTxCount",Double.parseDouble(sctCount));
        frauds.put("sameCardTxAmount",Double.parseDouble(sctAmount));
        frauds.put("totalDailyForeignCardCount",Double.parseDouble(tdfcCount));
        frauds.put("totalDailyForeignCardTxAmount",Double.parseDouble(tdfcAmount));
        frauds.put("txLimitToAlert",Double.parseDouble(transactionLimitToAlert));
        frauds.put("singleForcedTxAmount",Double.parseDouble(singleForcedTransactionLimit));
        frauds.put("aggregateForcedTxAmount",Double.parseDouble(aggregateForcedTransactionLimit));

        return frauds;
    }
}

