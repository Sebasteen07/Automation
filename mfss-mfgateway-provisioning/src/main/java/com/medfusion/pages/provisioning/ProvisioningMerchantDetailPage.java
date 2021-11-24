package com.medfusion.pages.provisioning;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.pages.provisioning.partials.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by lhrub on 26.11.2015.
 */
public class ProvisioningMerchantDetailPage extends ProvisioningAuthenticatedPage {

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'navMerchantStatus')]/span[contains(@class,'label')]")
    public WebElement status;

    @FindBy(how = How.XPATH, using = "//a[contains(@href,'users')]")
    public WebElement userRolesSectionButton;


    private ProvisioningMerchantGeneralInfoForm merchantFormPartialPage = new ProvisioningMerchantGeneralInfoForm(webDriver);
    private ProvisioningMerchantAccountsIdsForm merchantAccountsIdsFormPartialPage = new ProvisioningMerchantAccountsIdsForm(webDriver);
    private ProvisioningMerchantStatementOptionsForm merchantStatementOptionsForm = new ProvisioningMerchantStatementOptionsForm(webDriver);
    private ProvisioningMerchantRatesForm merchantRatesForm = new ProvisioningMerchantRatesForm(webDriver);
    private ProvisioningMerchantFraudsRisksForm merchantFraudsRisksForm = new ProvisioningMerchantFraudsRisksForm(webDriver);

    public ProvisioningMerchantDetailPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ProvisioningMerchantDetailPage(WebDriver webDriver, String mmid) {
        super(webDriver);
        webDriver.navigate().to(webDriver.getCurrentUrl() + "/" + mmid);
    }

    public Merchant getDisplayedMerchant(){

        Merchant merchant = new Merchant();

        (new WebDriverWait(webDriver, 10, 200)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return getText(d.findElement(By.id("merchantId"))).length() == 10;
            }
        });

        merchant.status = getText(status);
        merchantFormPartialPage.getFormData(merchant);
        merchantAccountsIdsFormPartialPage.getFormData(merchant);
        merchantStatementOptionsForm.getFormData(merchant);
        merchantFraudsRisksForm.getFormData(merchant);
        merchantRatesForm.getFormData(merchant);

        return merchant;
    }

    public ProvisioningSectionForm openSection(Section section) {
        WebElement topOfSectionForm = webDriver.findElement(By.xpath("//a[contains(@href,'" + section +"')]/../../../.."));
        ProvisioningSectionForm sectionPage = null;

        sleep(1500);

        Actions actions = new Actions(webDriver);
        actions.moveToElement(topOfSectionForm).perform();

        WebElement sectionButton = webDriver.findElement(By.xpath("//a[contains(@href,'" + section + "')]"));

        click(sectionButton);

        switch (section) {
            case GENERAL_INFO:
                sectionPage = PageFactory.initElements(webDriver, ProvisioningMerchantGeneralInfoForm.class);
                break;
            case ACCOUNTS_IDS:
                sectionPage =  PageFactory.initElements(webDriver, ProvisioningMerchantAccountsIdsForm.class);
                break;
            case STATEMENT_OPTIONS:
                sectionPage = PageFactory.initElements(webDriver, ProvisioningMerchantStatementOptionsForm.class);
                break;
            case RATES:
                sectionPage =  PageFactory.initElements(webDriver, ProvisioningMerchantRatesForm.class);
                break;
            case FRAUDS_RISKS:
                sectionPage = PageFactory.initElements(webDriver, ProvisioningMerchantFraudsRisksForm.class);
                break;
        }

        sleep(1500);

        actions.moveByOffset(0,0);
        actions.perform();

        return sectionPage;
    }

    public ProvisioningMerchantUsersRolesPage openUserRolesSection() {
        sleep(2500);
        WebElement usersHeadline = webDriver.findElement(By.xpath("//a[contains(@href,'users')]/../../../.."));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(usersHeadline).perform();
        sleep(500);
        click(userRolesSectionButton);
        return PageFactory.initElements(webDriver, ProvisioningMerchantUsersRolesPage.class);
    }


    public ProvisioningMerchantSearchPage back(){
        webDriver.navigate().back();
        sleep(200);
        return PageFactory.initElements(webDriver,ProvisioningMerchantSearchPage.class);
    }
}
