package com.medfusion.pages.provisioning.partials;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.pages.provisioning.ProvisioningMerchantSectionPage;

/**
 * Created by lubson on 15.12.15.
 */
public class ProvisioningMerchantAccountsIdsForm extends ProvisioningMerchantSectionPage {

    @FindBy(how = How.ID, using = "routingNumber")
    public WebElement routingNumber;

    @FindBy(how = How.ID, using = "accountNumber")
    public WebElement accountNumber;

    @FindBy(how = How.ID, using = "accountType")
    public WebElement accountType;

    @FindBy(how = How.ID, using = "amexSid")
    public WebElement amexSid;

    @FindBy(how = How.ID, using = "processor")
    public WebElement processor;

    @FindBy(how = How.ID, using = "federalTaxId")
    public WebElement federalTaxId;

    @FindBy(how = How.ID, using = "vantivCoreMid")
    public WebElement vantivCoreMid;

    @FindBy(how = How.ID, using = "vantivTid")
    public WebElement vantivTid;

    @FindBy(how = How.ID, using = "vantivIbfMid")
    public WebElement vantivIbfMid;

    @FindBy(how = How.ID, using = "vantivPbfMid")
    public WebElement vantivPbfMid;

    @FindBy(how = How.ID, using = "elementAccountId")
    public WebElement elementAccountId;

    @FindBy(how = How.ID, using = "elementAcceptorId")
    public WebElement elementAcceptorId;


    public ProvisioningMerchantAccountsIdsForm(WebDriver webDriver){
        super(webDriver);
    }

    public void getFormData(Merchant merchant){

        merchant.routingNumber = getText(routingNumber);
        merchant.accountNumber = getText(accountNumber);
        merchant.accountType = getText(accountType);
        merchant.amexSid = getText(amexSid);
        merchant.processor = getText(processor);
        merchant.federalTaxId = getText(federalTaxId);

        merchant.vantivCoreMid = getText(vantivCoreMid);
        merchant.vantivTid = getText(vantivTid);
        merchant.vantivIbfMid = getText(vantivIbfMid);
        merchant.vantivPbfMid = getText(vantivPbfMid);

        merchant.elementAccountId  = getText(elementAccountId);
        merchant.elementAcceptorId = getText(elementAcceptorId);
    }

    public void fillInForm(Merchant merchant){
        setText(routingNumber, merchant.routingNumber);
        setText(accountNumber, merchant.accountNumber);
        setText(accountType, merchant.accountType);
        setText(amexSid, merchant.amexSid);
        setText(processor, merchant.processor);
        setText(federalTaxId, merchant.federalTaxId);

        setText(vantivCoreMid, merchant.vantivCoreMid);
        setText(vantivIbfMid, merchant.vantivIbfMid);
        setText(vantivPbfMid, merchant.vantivPbfMid);

        setText(elementAccountId, merchant.elementAccountId);
    }
}
