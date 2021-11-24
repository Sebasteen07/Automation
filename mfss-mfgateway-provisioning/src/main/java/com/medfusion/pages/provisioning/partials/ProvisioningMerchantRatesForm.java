package com.medfusion.pages.provisioning.partials;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.pages.provisioning.ProvisioningMerchantSectionPage;

/**
 * Created by lhrub on 21.12.2015.
 */
public class ProvisioningMerchantRatesForm extends ProvisioningMerchantSectionPage {

    @FindBy(how = How.ID, using = "platformFeeAuth")
    public WebElement platformFeeAuth;

    @FindBy(how = How.ID, using = "platformFeeRefund")
    public WebElement platformFeeRefund;


    @FindBy(how = How.ID, using = "qTierBoundary")
    public WebElement qTierBoundary;

    @FindBy(how = How.ID, using = "qTierFee")
    public WebElement qTierFee;

    @FindBy(how = How.ID, using = "mQTierBoundary")
    public WebElement mQTierBoundary;

    @FindBy(how = How.ID, using = "mQTierFee")
    public WebElement mQTierFee;

    @FindBy(how = How.ID, using = "nQTierBoundary")
    public WebElement nQTierBoundary;

    @FindBy(how = How.ID, using = "nQTierFee")
    public WebElement nQTierFee;

    @FindBy(how = How.ID, using = "amexFee")
    public WebElement amexFeeGeneralInfo;

    @FindBy(how = How.ID, using = "amexRate")
    public WebElement amexFeeRatesPage;


    @FindBy(how = How.ID, using = "suppressFeeSettlement")
    public WebElement suppressFeeSettlement;

    public ProvisioningMerchantRatesForm(WebDriver webDriver) {
        super(webDriver);
    }

    public void getFormData(Merchant merchant) {
        merchant.platformFeeAuth = getText(platformFeeAuth);
        merchant.platformFeeRefund = getText(platformFeeRefund);

        merchant.qTierBoundary = getText(qTierBoundary);
        merchant.qTierFee = getText(qTierFee);
        merchant.mQTierBoundary = getText(mQTierBoundary);
        merchant.mQTierFee = getText(mQTierFee);
        merchant.nQTierBoundary = getText(nQTierBoundary);
        merchant.nQTierFee = getText(nQTierFee);
        merchant.amexFee = getText(amexFeeGeneralInfo);

        merchant.suppressFeeSettlement = getText(suppressFeeSettlement);
    }

    public void fillInForm(Merchant merchant) {
        setText(platformFeeAuth, merchant.platformFeeAuth);
        setText(platformFeeRefund, merchant.platformFeeRefund);

        setText(qTierBoundary, merchant.qTierBoundary);
        setText(qTierFee, merchant.qTierFee);
        setText(mQTierBoundary, merchant.mQTierBoundary);
        setText(mQTierFee, merchant.mQTierFee);
        setText(nQTierBoundary, merchant.nQTierBoundary);
        setText(nQTierFee, merchant.nQTierFee);
        setText(amexFeeRatesPage, merchant.amexFee);

        setText(suppressFeeSettlement, merchant.suppressFeeSettlement);
    }
}
