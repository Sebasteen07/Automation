package com.medfusion.pages.provisioning.partials;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.pages.provisioning.ProvisioningMerchantSectionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by lhrub on 21.12.2015.
 */
public class ProvisioningMerchantFraudsRisksForm extends ProvisioningMerchantSectionPage {

    @FindBy(how = How.ID, using = "ddtCount")
    public WebElement ddtCount;

    @FindBy(how = How.ID, using = "ddtAmount")
    public WebElement ddtAmount;


    @FindBy(how = How.ID, using = "evvDailyVs90DaysPercent")
    public WebElement evvDailyVs90DaysPercent;

    @FindBy(how = How.ID, using = "evvDailyVs90DaysAmount")
    public WebElement evvDailyVs90DaysAmount;

    @FindBy(how = How.ID, using = "evvDailyVs30DaysPercent")
    public WebElement evvDailyVs30DaysPercent;

    @FindBy(how = How.ID, using = "evvDailyVs30DaysAmount")
    public WebElement evvDailyVs30DaysAmount;

    @FindBy(how = How.ID, using = "evvDailyVs7DaysPercent")
    public WebElement evvDailyVs7DaysPercent;

    @FindBy(how = How.ID, using = "evvDailyVs7DaysAmount")
    public WebElement evvDailyVs7DaysAmount;

    @FindBy(how = How.ID, using = "evv7DayVs90DaysPercent")
    public WebElement evv7DayVs90DaysPercent;

    @FindBy(how = How.ID, using = "evv7DayVs90DaysAmount")
    public WebElement evv7DayVs90DaysAmount;


    @FindBy(how = How.ID, using = "ducCount")
    public WebElement ducCount;

    @FindBy(how = How.ID, using = "ducAmount")
    public WebElement ducAmount;


    @FindBy(how = How.ID, using = "sctCount")
    public WebElement sctCount;

    @FindBy(how = How.ID, using = "sctAmount")
    public WebElement sctAmount;


    @FindBy(how = How.ID, using = "tdfcCount")
    public WebElement tdfcCount;

    @FindBy(how = How.ID, using = "tdfcAmount")
    public WebElement tdfcAmount;


    @FindBy(how = How.ID, using = "transactionLimitToAlert")
    public WebElement transactionLimitToAlert;

    @FindBy(how = How.ID, using = "singleForcedTransactionLimit")
    public WebElement singleForcedTransactionLimit;

    @FindBy(how = How.ID, using = "aggregateForcedTransactionLimit")
    public WebElement aggregateForcedTransactionLimit;

    @FindBy(how = How.ID, using = "staleDays")
    public WebElement staleDays;

    public  ProvisioningMerchantFraudsRisksForm(WebDriver webDriver) {
        super(webDriver);
    }

    public void getFormData(Merchant merchant){
        merchant.ddtCount = getText(ddtCount);
        merchant.ddtAmount = getText(ddtAmount);

        merchant.evvDailyVs90DaysPercent = getText(evvDailyVs90DaysPercent);
        merchant.evvDailyVs90DaysAmount = getText(evvDailyVs90DaysAmount);
        merchant.evvDailyVs30DaysPercent = getText(evvDailyVs30DaysPercent);
        merchant.evvDailyVs30DaysAmount = getText(evvDailyVs30DaysAmount);
        merchant.evvDailyVs7DaysPercent = getText(evvDailyVs7DaysPercent);
        merchant.evvDailyVs7DaysAmount = getText(evvDailyVs7DaysAmount);
        merchant.evv7DayVs90DaysPercent = getText(evv7DayVs90DaysPercent);
        merchant.evv7DayVs90DaysAmount = getText(evv7DayVs90DaysAmount);

        merchant.ducCount = getText(ducCount);
        merchant.ducAmount = getText(ducAmount);

        merchant.sctCount = getText(sctCount);
        merchant.sctAmount = getText(sctAmount);

        merchant.tdfcCount = getText(tdfcCount);
        merchant.tdfcAmount = getText(tdfcAmount);

        merchant.transactionLimitToAlert = getText(transactionLimitToAlert);
        merchant.singleForcedTransactionLimit = getText(singleForcedTransactionLimit);
        merchant.aggregateForcedTransactionLimit = getText(aggregateForcedTransactionLimit);
        merchant.staleDays = getText(staleDays);

    }

    public void fillInForm(Merchant merchant) {

        setText(ddtCount, merchant.ddtCount);
        setText(ddtAmount, merchant.ddtAmount);

        setText(evvDailyVs90DaysPercent, merchant.evvDailyVs90DaysPercent);
        setText(evvDailyVs90DaysAmount, merchant.evvDailyVs90DaysAmount);
        setText(evvDailyVs30DaysPercent, merchant.evvDailyVs30DaysPercent);
        setText(evvDailyVs30DaysAmount, merchant.evvDailyVs30DaysAmount);
        setText(evvDailyVs7DaysPercent, merchant.evvDailyVs7DaysPercent);
        setText(evvDailyVs7DaysAmount, merchant.evvDailyVs7DaysAmount);
        setText(evv7DayVs90DaysPercent, merchant.evv7DayVs90DaysPercent);
        setText(evv7DayVs90DaysAmount, merchant.evv7DayVs90DaysAmount);

        setText(ducCount, merchant.ducCount);
        setText(ducAmount, merchant.ducAmount);

        setText(sctCount, merchant.sctCount);
        setText(sctAmount, merchant.sctAmount);

        setText(tdfcCount, merchant.tdfcCount);
        setText(tdfcAmount, merchant.tdfcAmount);

        setText(transactionLimitToAlert, merchant.transactionLimitToAlert);
        setText(singleForcedTransactionLimit, merchant.singleForcedTransactionLimit);
        setText(aggregateForcedTransactionLimit, merchant.aggregateForcedTransactionLimit);
        setText(staleDays, merchant.staleDays);
    }
}