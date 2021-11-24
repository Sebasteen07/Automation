package com.medfusion.pages.provisioning.partials;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.pages.provisioning.ProvisioningMerchantSectionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Created by lhrub on 17.12.2015.
 */
public class ProvisioningMerchantStatementOptionsForm extends ProvisioningMerchantSectionPage {

    @FindBy(how = How.ID, using = "merchantTagLine")
    public WebElement merchantTagLine;

    @FindBy(how = How.ID, using = "merchantLogoFilename")
    public WebElement merchantLogoFilename;

    @FindBy(how = How.ID, using = "payOrBillByPhoneNumber")
    public WebElement payOrBillByPhoneNumber;

    @FindBy(how = How.ID, using = "payOrBillByPhoneHours")
    public WebElement payOrBillByPhoneHours;

    @FindBy(how = How.ID, using = "payOrBillBillQueryNumber")
    public WebElement payOrBillBillQueryNumber;

    @FindBy(how = How.ID, using = "payOrBillBillQueryHours")
    public WebElement payOrBillBillQueryHours;

    @FindBy(how = How.ID, using = "stmtDueDateLeadDays")
    public WebElement stmtDueDateLeadDays;



    @FindBy(how = How.ID, using = "detailsAgingBoxes")
    public WebElement detailsAgingBoxes;

    @FindBy(how = How.ID, using = "detailsInsuranceAgingBoxes")
    public WebElement detailsInsuranceAgingBoxes;

    @FindBy(how = How.ID, using = "detailsDetachReturnByMail")
    public WebElement detailsDetachReturnByMail;

    @FindBy(how = How.ID, using = "detailsStatementDetail")
    public WebElement detailsStatementDetail;

    @FindBy(how = How.ID, using = "detailsMerchantName")
    public WebElement detailsMerchantName;


    @FindBy(how = How.ID, using = "payOrBillByCheck")
    public WebElement payOrBillByCheck;

    @FindBy(how = How.ID, using = "payOrBillByMoneyOrder")
    public WebElement payOrBillByMoneyOrder;

    public ProvisioningMerchantStatementOptionsForm(WebDriver webDriver) {
        super(webDriver);
    }

    public void getFormData(Merchant merchant){
        merchant.merchantTagLine = getText(merchantTagLine);

        //TODO uncomment after logo implementation
        //merchant.merchantLogoFilename = getText(merchantLogoFilename);
        merchant.merchantLogoFilename = "";
        merchant.payOrBillByPhoneNumber = getText(payOrBillByPhoneNumber);
        merchant.payOrBillByPhoneHours = getText(payOrBillByPhoneHours);
        merchant.payOrBillBillQueryNumber = getText(payOrBillBillQueryNumber);
        merchant.payOrBillBillQueryHours = getText(payOrBillBillQueryHours);
        merchant.stmtDueDateLeadDays = getText(stmtDueDateLeadDays);

        merchant.detailsAgingBoxes = getText(detailsAgingBoxes);
        merchant.detailsInsuranceAgingBoxes = getText(detailsInsuranceAgingBoxes);
        merchant.detailsDetachReturnByMail = getText(detailsDetachReturnByMail);
        merchant.detailsStatementDetail = getText(detailsStatementDetail);
        merchant.detailsMerchantName = getText(detailsMerchantName);

        merchant.payOrBillByCheck = getText(payOrBillByCheck);
        merchant.payOrBillByMoneyOrder = getText(payOrBillByMoneyOrder);
    }

    public void fillInForm(Merchant merchant) {
        setText(merchantTagLine, merchant.merchantTagLine);
        // TODO Upload Image
        // setText(merchantLogoFilename, merchant.merchantLogoFilename);
        setText(payOrBillByPhoneNumber, merchant.payOrBillByPhoneNumber);
        setText(payOrBillByPhoneHours, merchant.payOrBillByPhoneHours);
        setText(payOrBillBillQueryNumber, merchant.payOrBillBillQueryNumber);
        setText(payOrBillBillQueryHours, merchant.payOrBillBillQueryHours);
        setText(stmtDueDateLeadDays, merchant.stmtDueDateLeadDays);

        setText(detailsAgingBoxes, merchant.detailsAgingBoxes);
        setText(payOrBillByCheck, merchant.payOrBillByCheck);
        setText(detailsStatementDetail, merchant.detailsStatementDetail);
        setText(detailsMerchantName, merchant.detailsMerchantName);

        setText(payOrBillByMoneyOrder, merchant.payOrBillByMoneyOrder);

        setText(detailsInsuranceAgingBoxes, merchant.detailsInsuranceAgingBoxes);
        setText(detailsDetachReturnByMail, merchant.detailsDetachReturnByMail);
    }
}
