package pageobjects;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/form/div[2]/fieldset/div/button[1]")
    private WebElement updateButton ;

    public void verifyPageTitle() {
        String title = this.driver.getTitle();
        assertNotNull(title);
    }

    public void editMerchantName(String merchant) {
        merchantName.clear();
        merchantName.sendKeys(merchant);
    }

    public void editDoingBusinessAs(String doingBusinessAsName) {
        doingBusinessAs.clear();
        doingBusinessAs.sendKeys(doingBusinessAsName);
    }

    public void waitForAlert(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf((WebElement) By.cssSelector("div[role='alert']")));
    }

    public void editPracticeID(String practiceID) {
        practiceId.clear();
        practiceId.sendKeys(practiceID);
    }

    public void editCustomerAccountNumber(String customerAccountNo) {
        customerAccountNumber.clear();
        customerAccountNumber.sendKeys(customerAccountNo);
    }

    public void editPhoneNumber(String merchantPhoneNumber) {
        phoneNumber.clear();
        phoneNumber.sendKeys(merchantPhoneNumber);
    }

    public void editMaxTransactionLimit(String maxTxnLimit) {
        maxTransactionLimit.clear();
        maxTransactionLimit.sendKeys(maxTxnLimit);
    }

    public void editBillingDescriptor(String billingDescriptorValue) {
        billingDescriptor.clear();
        billingDescriptor.sendKeys(billingDescriptorValue);
    }

    public void editBusinessEstablishedDate(String businessEstablishedDateValue) {
        businessEstablishedDate.clear();
        businessEstablishedDate.sendKeys(businessEstablishedDateValue);
    }

    public void editWebsiteURL(String businessWebsiteURL) {
        websiteURL.clear();
        websiteURL.sendKeys(businessWebsiteURL);
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

    public void verifyFieldsOnEditGeneralMerchantInfoPage(){
        if(merchantName.isDisplayed() && doingBusinessAs.isDisplayed()
                && practiceId.isDisplayed() && customerAccountNumber.isDisplayed()
        && phoneNumber.isDisplayed() && maxTransactionLimit.isDisplayed()
                && businessEstablishedDate.isDisplayed() && websiteURL.isDisplayed());
    }

    public Boolean verifyBusinessType(){
        Select select = new Select(businessType);
        List<WebElement> businessType = select.getOptions();
        List<String> expectedbusinessType = new ArrayList<String>();
        for(int i=1; i<businessType.size(); i++){
            expectedbusinessType.add(businessType.get(i).getText());
        }

        List<String> actualBusinessTypes = Arrays.asList("Auto Rental", "eCommerce", "Lodging", "MOTO", "Restaurant", "Retail");
        return actualBusinessTypes.equals(expectedbusinessType);
    }

    public Boolean verifySICMCCCode(){
        Select select = new Select(sicMccCode);
        List<WebElement> businessType = select.getOptions();
        List<String> expectedbusinessType = new ArrayList<String>();
        for(int i=0; i<businessType.size(); i++){
            expectedbusinessType.add(businessType.get(i).getText());
        }
        System.out.println(expectedbusinessType);
        List<String> actualBusinessTypes = Arrays.asList("Doctors", "Dentists, Orthodontists", "Osteopaths",
                "Chiropractors", "Optometrists, Ophthalmologist", "Opticians, Eyeglasses", "Chiropodists, Podiatrists", "Nursing/Personal Care",
                "Hospitals", "Medical and Dental Labs", "Medical Services");
        return actualBusinessTypes.equals(expectedbusinessType);
    }

    public Boolean isOwnershipTypeDisabled(){
        String isDisabledAttributeValue = ownershipType.getAttribute("data-ng-disabled");
        if(isDisabledAttributeValue.equalsIgnoreCase("true")){
            return true;
        }else return false;
    }

    public MerchantDetailsPage clickUpdateGeneralMerchantInfoButton(){
        updateButton.click();
        return PageFactory.initElements(driver, MerchantDetailsPage.class);
    }

}
