package com.medfusion.util;

import org.junit.Assert;

import com.medfusion.factory.pojos.provisioning.Merchant;

/**
 * Created by lhrub on 14.12.2015.
 */
public class ProvisioningAssert extends Assert {

    public static void assertMerchant(Merchant expected, Merchant actual) {

        assertMerchantGeneralInfoEquals(expected, actual);
        assertMerchantAccountsIdsAs(expected,actual);
        assertMerchantStatementOptions(expected,actual);
        assertMerchantRates(expected,actual);
        assertMerchantFraudsRisks(expected,actual);

        assertEquals("Status is not equal", expected.status, actual.status);
    }

    public static void assertMerchantGeneralInfoEquals(Merchant expected, Merchant actual){

        assertEquals("MMID is not equal", expected.mmid, actual.mmid);
            assertEquals("Name is not equal", expected.name, actual.name);
            assertEquals("External ID is not equal", expected.externalId, actual.externalId);
            assertEquals("Legal Name is not equal", expected.legalName, actual.legalName);
            assertEquals("Doing Business as is not equal", expected.doingBusinessAs, actual.doingBusinessAs);
            assertEquals("Phone is not equal", expected.phone, actual.phone);
            assertEquals("Customer Service Phone is not equal", expected.customerServicePhone, actual.customerServicePhone);
            assertEquals("Transaction Limit is not equal", expected.txLimit, actual.txLimit);

            assertEquals("Address Line 1 is not equal", expected.addressLine1, actual.addressLine1);
            assertEquals("Address Line 2 is not equal", expected.addressLine2, actual.addressLine2);
            assertEquals("City is not equal", expected.city, actual.city);
            assertEquals("State is not equal", expected.state, actual.state);
            assertEquals("ZIP code is not equal", expected.zip, actual.zip);
            assertEquals("Country is not equal", expected.country, actual.country);

            assertEquals("Merchant Status is not equal", expected.merchantStatus, actual.merchantStatus);
            assertEquals("SIC/MCC is not equal", expected.sicMccCode, actual.sicMccCode);
            assertEquals("Average Ticket Price is not equal", expected.averageTicketPrice, actual.averageTicketPrice);
            assertEquals("Voucher Flag is not equal", expected.voucherFlag, actual.voucherFlag);

            assertEquals("Accepted credit cards are not equal", expected.acceptedCards, actual.acceptedCards);
    }

    public static void assertMerchantAccountsIdsAs( Merchant expected, Merchant actual) {

        assertEquals("Routing Number is not equal", expected.routingNumber, actual.routingNumber);
        assertEquals("Account Number is not equal", expected.accountNumber, actual.accountNumber);
        assertEquals("Account Type is not equal",   expected.accountType,   actual.accountType);
        assertEquals("AMEX SID is not equal",       expected.amexSid,       actual.amexSid);
        assertEquals("Processor is not equal",      expected.processor,     actual.processor);
        assertEquals("Federal Tax ID is not equal", expected.federalTaxId,  actual.federalTaxId);

        assertEquals("Vantiv Core MID is not equal", expected.vantivCoreMid, actual.vantivCoreMid);
        assertEquals("Vantiv IBF MID is not equal", expected.vantivIbfMid, actual.vantivIbfMid);
        assertEquals("Vantiv PBF MID is not equal", expected.vantivPbfMid, actual.vantivPbfMid);

        assertEquals("ElementPS Account ID is not equal", expected.elementAccountId, actual.elementAccountId);
        assertEquals("ElementPS Acceptor ID is not equal", expected.elementAcceptorId, actual.elementAcceptorId);
    }

    public static void assertMerchantStatementOptions(Merchant expected, Merchant actual) {
        assertEquals("Merchant Tagline is not equal", expected.merchantTagLine, actual.merchantTagLine);
        assertEquals("Pay By Phone Number is not equal", expected.payOrBillByPhoneNumber, actual.payOrBillByPhoneNumber);
        assertEquals("Pay By Phone Hours is not equal", expected.payOrBillByPhoneHours, actual.payOrBillByPhoneHours);
        assertEquals("Bill Query Phone Number is not equal", expected.payOrBillBillQueryNumber, actual.payOrBillBillQueryNumber);
        assertEquals("Bill Query Hours is not equal", expected.payOrBillBillQueryHours, actual.payOrBillBillQueryHours);
        assertEquals("Days between Statement Date and Due Date is not equal", expected.stmtDueDateLeadDays, actual.stmtDueDateLeadDays);

        assertEquals("Display Aging Boxes TID is not equal", expected.detailsAgingBoxes, actual.detailsAgingBoxes);
        assertEquals("Display Insurance Aging Boxes is not equal", expected.detailsInsuranceAgingBoxes, actual.detailsInsuranceAgingBoxes);
        assertEquals("Display Detach Return By Mail is not equal", expected.detailsDetachReturnByMail, actual.detailsDetachReturnByMail);
        assertEquals("Display Details is not equal", expected.detailsStatementDetail, actual.detailsStatementDetail);
        assertEquals("Display Merchant Name is not equal", expected.detailsMerchantName, actual.detailsMerchantName);

        assertEquals("Pay By Check is not equal", expected.payOrBillByCheck, actual.payOrBillByCheck);
        assertEquals("Pay By Money Order is not equal", expected.payOrBillByMoneyOrder, actual.payOrBillByMoneyOrder);
    }

    public static void assertMerchantRates(Merchant expected, Merchant actual) {
        assertEquals("Per Trans Auth Fee is not equal", expected.platformFeeAuth, actual.platformFeeAuth);
        assertEquals("Per Trans Refund Fee is not equal", expected.platformFeeRefund, actual.platformFeeRefund);

        assertEquals("Qualified Upper Boundary is not equal", expected.qTierBoundary, actual.qTierBoundary);
        assertEquals("Qualified Fee Percentage is not equal", expected.qTierFee, actual.qTierFee);
        assertEquals("Mid-Qualified Upper Boundary is not equal", expected.mQTierBoundary, actual.mQTierBoundary);
        assertEquals("Mid-Qualified Fee Percentage is not equal", expected.mQTierFee, actual.mQTierFee);
        assertEquals("Non-Qualified Upper Boundary is not equal", expected.nQTierBoundary, actual.nQTierBoundary);
        assertEquals("Non-Qualified Fee Percentage is not equal", expected.nQTierFee, actual.nQTierFee);

        assertEquals("Amex Fee Percentage is not equal", expected.amexFee, actual.amexFee);
        assertEquals("Suppress Fee Settlement is not equal", expected.suppressFeeSettlement, actual.suppressFeeSettlement);
    }

    public static void assertMerchantFraudsRisks(Merchant expected, Merchant actual) {
        assertEquals("Transaction Count is not equal", expected.ddtCount, actual.ddtCount);
        assertEquals("Transaction Amount is not equal", expected.ddtAmount, actual.ddtAmount);

        assertEquals("Amount - Daily vs. 90 Day is not equal", expected.evvDailyVs90DaysPercent, actual.evvDailyVs90DaysPercent);
        assertEquals("Pay By Phone Hours is not equal", expected.evvDailyVs90DaysAmount, actual.evvDailyVs90DaysAmount);
        assertEquals("Percentage - Daily vs. 30 Day is not equal", expected.evvDailyVs30DaysPercent, actual.evvDailyVs30DaysPercent);
        assertEquals("Amount - Daily vs. 30 Day is not equal", expected.evvDailyVs30DaysAmount, actual.evvDailyVs30DaysAmount);
        assertEquals("Percentage - Daily vs. 7 Day is not equal", expected.evvDailyVs7DaysPercent, actual.evvDailyVs7DaysPercent);
        assertEquals("Amount - Daily vs. 7 Day is not equal", expected.evvDailyVs7DaysAmount, actual.evvDailyVs7DaysAmount);
        assertEquals("Percentage - 7 Day vs. 90 Day is not equal", expected.evv7DayVs90DaysPercent, actual.evv7DayVs90DaysPercent);
        assertEquals("Amount - 7 Day vs. 90 Day is not equal", expected.evv7DayVs90DaysAmount, actual.evv7DayVs90DaysAmount);

        assertEquals("Credits Count is not equal", expected.ducCount, actual.ducCount);
        assertEquals("Credits Amount Aging Boxes is not equal", expected.ducAmount, actual.ducAmount);

        assertEquals("Transaction Count is not equal", expected.sctCount, actual.sctCount);
        assertEquals("Transaction Amount is not equal", expected.sctAmount, actual.sctAmount);

        assertEquals("Foreign Card Count is not equal", expected.tdfcCount, actual.tdfcCount);
        assertEquals("Foreign Card Amount is not equal", expected.tdfcAmount, actual.tdfcAmount);

        assertEquals("Transaction Limit to Alert is not equal", expected.transactionLimitToAlert, actual.transactionLimitToAlert);
        assertEquals("Single Forced Transaction Amount is not equal", expected.singleForcedTransactionLimit, actual.singleForcedTransactionLimit);
        assertEquals("Aggregate Forced Transaction Amount is not equal", expected.aggregateForcedTransactionLimit, actual.aggregateForcedTransactionLimit);
        assertEquals("Stale Transaction Days is not equal", expected.staleDays, actual.staleDays);
    }
}
