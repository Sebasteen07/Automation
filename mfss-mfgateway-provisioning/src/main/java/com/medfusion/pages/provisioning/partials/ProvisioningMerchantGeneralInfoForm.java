package com.medfusion.pages.provisioning.partials;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.pages.provisioning.ProvisioningMerchantSectionPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lhrub on 08.12.2015.
 */
public class ProvisioningMerchantGeneralInfoForm extends ProvisioningMerchantSectionPage {

    @FindBy(how = How.ID, using = "merchantId")
    public WebElement merchantId;

    @FindBy(how = How.ID, using = "merchantName")
    public WebElement merchantNameInput;

    @FindBy(how = How.ID, using = "merchantLegalName")
    public WebElement merchantLegalNameInput;

    @FindBy(how = How.ID, using = "doingBusinessAs")
    public WebElement doingBusinessAsInput;

    @FindBy(how = How.ID, using = "externalMerchantId")
    public WebElement externalMerchantId;

    @FindBy(how = How.ID, using = "merchantPhone")
    public WebElement merchantPhoneInput;

    @FindBy(how = How.ID, using = "customerServicePhone")
    public WebElement customerServicePhoneInput;

    @FindBy(how = How.ID, using = "txLimit")
    public WebElement txLimitInput;

    @FindBy(how = How.ID, using = "addressLine1")
    public WebElement addressLine1Input;

    @FindBy(how = How.ID, using = "addressLine2")
    public WebElement addressLine2Input;

    @FindBy(how = How.ID, using = "city")
    public WebElement cityInput;

    @FindBy(how = How.ID, using = "state")
    public WebElement stateInput;

    @FindBy(how = How.ID, using = "zip")
    public WebElement zipInput;

    @FindBy(how = How.ID, using = "country")
    public WebElement countryInput;

    @FindBy(how = How.XPATH, using = "(//input[@type='checkbox' and @name='acceptedCards'])[1]" )
    public WebElement visaInput;

    @FindBy(how = How.ID, using = "merchantStatus")
    public WebElement merchantStatusInput;

    @FindBy(how = How.ID, using = "sicMccCode")
    public WebElement sicMccCodeInput;

    @FindBy(how = How.ID, using = "averageTicketPrice")
    public WebElement averageTicketPriceInput;

    @FindBy(how = How.ID, using="voucherFlag")
    public WebElement voucherFlag;

    public ProvisioningMerchantGeneralInfoForm(WebDriver webDriver){
        super(webDriver);
    }

    public void fillInForm(Merchant merchant){
        setText(merchantNameInput, merchant.name);
        setText(merchantLegalNameInput, merchant.legalName);
        setText(doingBusinessAsInput, merchant.doingBusinessAs);
        setText(externalMerchantId,merchant.externalId);
        setText(merchantPhoneInput, merchant.phone);
        setText(customerServicePhoneInput, merchant.customerServicePhone);
        setText(txLimitInput, merchant.txLimit);

        setText(addressLine1Input, merchant.addressLine1);
        setText(addressLine2Input, merchant.addressLine2);
        setText(cityInput, merchant.city);
        setText(stateInput, merchant.state);
        setText(zipInput, merchant.zip);
        setText(countryInput, merchant.country);

        //TODO Do no make it hardcoded
        click(visaInput);

        setText(merchantStatusInput, merchant.merchantStatus);
        setText(sicMccCodeInput, merchant.sicMccCode);
        setText(averageTicketPriceInput, merchant.averageTicketPrice);

        //TODO Do not make it hardcoded
        Select voucherFlagSelect = new Select(voucherFlag);
        voucherFlagSelect.selectByIndex(0);
    }

    public void getFormData(Merchant merchant){
        merchant.mmid = getText(merchantId);
        merchant.name = getText(merchantNameInput);
        merchant.legalName = getText(merchantLegalNameInput);
        merchant.doingBusinessAs = getText(doingBusinessAsInput);
        merchant.externalId = getText(externalMerchantId);
        merchant.phone = getText(merchantPhoneInput);
        merchant.customerServicePhone = getText(customerServicePhoneInput);
        merchant.txLimit = getText(txLimitInput);

        merchant.acceptedCards = getAcceptedCreditCards();

        merchant.addressLine1 = getText(addressLine1Input);
        merchant.addressLine2 = getText(addressLine2Input);
        merchant.city = getText(cityInput);
        merchant.state = getText(stateInput);
        merchant.zip = getText(zipInput);
        merchant.country = getText(countryInput);

        merchant.merchantStatus = getText(merchantStatusInput);
        merchant.sicMccCode = getText(sicMccCodeInput);
        merchant.averageTicketPrice = getText(averageTicketPriceInput);
        merchant.voucherFlag = getText(voucherFlag);
    }

    private Set<String> getAcceptedCreditCards(){
        Set<String> acceptedCrediCards = new HashSet<String>();
        List<WebElement> acceptedCardImgs = webDriver.findElements(By.xpath("//div[@data-ng-repeat='card in acceptedCards']/img"));
        for (WebElement cardImg : acceptedCardImgs) {
            acceptedCrediCards.add(cardImg.getAttribute("alt"));
        }

        return acceptedCrediCards;
    }
}
