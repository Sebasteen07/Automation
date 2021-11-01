package pageobjects;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

public class GeneralMerchantInformationPage extends MerchantDetailsPage {
    protected static PropertyFileLoader testData;

    public GeneralMerchantInformationPage(WebDriver driver) throws IOException {
        super(driver);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        testData = new PropertyFileLoader();
    }

    @FindBy(how = How.XPATH, using = "//*[@id=\"viewContent\"]/div/form/div[1]/div/fieldset/legend")
    private WebElement generalMerchantInformationPageTitle;

    @FindBy(how = How.XPATH, using = "//*[@id='merchantName']")
    private WebElement merchantName;

    @FindBy(how = How.XPATH, using = "//*[@id='doingBusinessAs']")
    private WebElement doingBusinessAs ;

    @FindBy(how = How.XPATH, using = "//*[@id='externalMerchantId']")
    private WebElement practiceId ;

    @FindBy(how = How.XPATH, using = "//*[@id='customerAccountNumber']")
    private WebElement customerAccountNumber ;

    @FindBy(how = How.XPATH, using = "//*[@id='phoneNumber']")
    private WebElement phoneNumber ;

    @FindBy(how = How.XPATH, using = "//*[@id='maxTransactionLimit']")
    private WebElement maxTransactionLimit ;

    @FindBy(how = How.XPATH, using = "//*[@id='billingDescriptor']")
    private WebElement billingDescriptor ;

    @FindBy(how = How.XPATH, using = "//*[@id='businessEstablishedDate']")
    private WebElement businessEstablishedDate ;

    @FindBy(how = How.XPATH, using = "//*[@id='websiteURL']")
    private WebElement websiteURL ;

    @FindBy(how = How.XPATH, using = "//*[@id='payApiCustomers']")
    private WebElement payApiCustomers ;

    @FindBy(how = How.XPATH, using = "//*[@id='ownershipType']")
    private WebElement ownershipType ;

    @FindBy(how = How.XPATH, using = "//*[@id='businessType']")
    private WebElement businessType ;

    @FindBy(how = How.XPATH, using = "//*[@id='sicMccCode']")
    private WebElement sicMccCode ;

    public void verifyPageTitle() {
        String title = this.driver.getTitle();
        assertNotNull(title);
    }

    public void fillMerchantName(String merchant) {
        merchantName.clear();
        merchantName.sendKeys(merchant);
    }

    public void fillDoingBusinessAs(String doingBusinessAsName) {
        doingBusinessAs.clear();
        doingBusinessAs.sendKeys(doingBusinessAsName);
    }

    public void fillPracticeID(String practiceID) {
        practiceId.clear();
        practiceId.sendKeys(practiceID);
    }

    public void fillCustomerAccountNumber(String customerAccountNo) {
        customerAccountNumber.clear();
        customerAccountNumber.sendKeys(customerAccountNo);
    }

    public void fillPhoneNumber(String merchantPhoneNumber) {
        phoneNumber.clear();
        phoneNumber.sendKeys(merchantPhoneNumber);
    }

    public void fillMaxTransactionLimit(String maxTxnLimit) {
        maxTransactionLimit.clear();
        maxTransactionLimit.sendKeys(maxTxnLimit);
    }

    public void fillBillingDescriptor(String billingDescriptorValue) {
        billingDescriptor.clear();
        billingDescriptor.sendKeys(billingDescriptorValue);
    }

    public void fillBusinessEstablishedDate(String businessEstablishedDateValue) {
        businessEstablishedDate.clear();
        businessEstablishedDate.sendKeys(businessEstablishedDateValue);
    }

    public void fillWebsiteURL(String businessWebsiteURL) {
        websiteURL.clear();
        websiteURL.sendKeys(businessWebsiteURL);
    }

    public void selectOwnershipType(String ownershipTypeValue) {
        Select select = new Select(ownershipType);
        select.selectByVisibleText(ownershipTypeValue);
    }

    public void selectBusinessType(String businessTypeValue) {
        Select select = new Select(businessType);
        select.selectByVisibleText(businessTypeValue);
    }

    public void selectSicMccCode(String sicMccCodeValue) {
        Select select = new Select(sicMccCode);
        select.selectByVisibleText(sicMccCodeValue);
    }

    public void selectPayApiCustomers(String apiCustomers) {
        Select select = new Select(payApiCustomers);
        select.selectByVisibleText(apiCustomers);
    }

    public void verifyElementsOnEditGeneralMerchantInfoPage(){

    }

}
