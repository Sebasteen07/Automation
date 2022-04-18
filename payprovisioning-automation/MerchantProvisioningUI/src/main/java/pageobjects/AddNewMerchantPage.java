package pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.MerchantEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AddNewMerchantPage extends NavigationMenu{

    @FindBy(how = How.ID, using = "vendorType")
    private WebElement vendorType;

    @FindBy(how = How.ID, using = "merchantName")
    private WebElement merchantName;

    @FindBy(how = How.ID, using = "doingBusinessAs")
    private WebElement doingBusinessAs;

    @FindBy(how = How.ID, using = "externalMerchantId")
    private WebElement practiceId;

    @FindBy(how = How.ID, using = "customerAccountNumber")
    private WebElement customerAccountNumber;

    @FindBy(how = How.ID, using = "phoneNumber")
    private WebElement phoneNumber;

    @FindBy(how = How.ID, using = "maxTransactionLimit")
    private WebElement maxTransactionLimit;

    @FindBy(how = How.ID, using = "billingDescriptor")
    private WebElement billingDescriptor;

    @FindBy(how = How.ID, using = "ownershipType")
    private WebElement ownershipType;

    @FindBy(how = How.ID, using = "businessType")
    private WebElement businessType;

    @FindBy(how = How.ID, using = "sicMccCode")
    private WebElement sicMccCode;

    @FindBy(how = How.ID, using = "businessEstablishedDate")
    private WebElement businessEstablishedDate;

    @FindBy(how = How.ID, using = "websiteURL")
    private WebElement websiteURL;

    @FindBy(how = How.ID, using = "payApiCustomers")
    private WebElement payApiCustomers;

    @FindBy(how = How.ID, using = "primaryContactFirstName")
    private WebElement customerContactFirstName;

    @FindBy(how = How.ID, using = "primaryContactLastName")
    private WebElement customerContactLastName;

    @FindBy(how = How.ID, using = "primaryContactEmail")
    private WebElement customerContactEmail;

    @FindBy(how = How.ID, using = "primaryContactPhoneNumber")
    private WebElement customerPhoneNumber;

    @FindBy(how = How.ID, using = "address1")
    private WebElement merchantAddressLine1;

    @FindBy(how = How.ID, using = "city")
    private WebElement merchantAddressCity;

    @FindBy(how = How.ID, using = "state")
    private WebElement merchantAddressState;

    @FindBy(how = How.ID, using = "zip")
    private WebElement merchantAddressZip;

    @FindBy(how = How.ID, using = "country")
    private WebElement merchantAddressCountry;

    @FindBy(how = How.ID, using = "merchantLegalName")
    private WebElement merchantLegalName;

    @FindBy(how = How.ID, using = "principalFirstName_1")
    private WebElement beneficialOwnerFirstName;

    @FindBy(how = How.ID, using = "principalLastName_1")
    private WebElement beneficialOwnerLastName;

    @FindBy(how = How.ID, using = "percentOwnership_1")
    private WebElement percentOwnership;

    @FindBy(how = How.ID, using = "principaladdressLine1_1")
    private WebElement beneficialOwnerAddressLine1;

    @FindBy(how = How.ID, using = "principalcity_1")
    private WebElement beneficialOwnerCity;

    @FindBy(how = How.ID, using = "principalstate_1")
    private WebElement beneficialOwnerState;

    @FindBy(how = How.ID, using = "principalzip_1")
    private WebElement beneficialOwnerZip;

    @FindBy(how = How.ID, using = "principalcountry_1")
    private WebElement beneficialOwnerCountry;

    @FindBy(how = How.ID, using = "contactType_1")
    private WebElement controlOwnerType;

    @FindBy(how = How.ID, using = "accountType")
    private WebElement accountType;

    @FindBy(how = How.ID, using = "accountUsage")
    private WebElement accountUsage;

    @FindBy(how = How.ID, using = "routingNumber")
    private WebElement routingNumber;

    @FindBy(how = How.ID, using = "accountNumber")
    private WebElement accountNumber;

    @FindBy(how = How.ID, using = "federalTaxId")
    private WebElement federalTaxId;

    @FindBy(how = How.ID, using = "platformFeeAuth")
    private WebElement perTransactionFeeAuth;

    @FindBy(how = How.ID, using = "platformFeeRefund")
    private WebElement perTransactionFeeRefund;

    @FindBy(how = How.ID, using = "qTierBoundary")
    private WebElement qualifiedTierBoundary;

    @FindBy(how = How.ID, using = "qTierFee")
    private WebElement qualifiedTierFee;

    @FindBy(how = How.ID, using = "mQTierBoundary")
    private WebElement midQualifiedTierBoundary;

    @FindBy(how = How.ID, using = "mQTierFee")
    private WebElement midQualifiedTierFee;

    @FindBy(how = How.ID, using = "nQTierBoundary")
    private WebElement nonQualifiedTierBoundary;

    @FindBy(how = How.ID, using = "nQTierFee")
    private WebElement nonQualifiedTierFee;

    @FindBy(how = How.ID, using = "feeSettlementType")
    private WebElement feeSettlementType;

    @FindBy(how = How.NAME, using = "enableRemitToAddress")
    private WebElement enableDisableRemitToAddress;

    @FindBy(how = How.ID, using = "remitAddressLine1")
    private WebElement remitAddressLine1;

    @FindBy(how = How.ID, using = "remitCity")
    private WebElement remitCity;

    @FindBy(how = How.ID, using = "remitState")
    private WebElement remitState;

    @FindBy(how = How.ID, using = "remitZip")
    private WebElement remitZip;

    @FindBy(how = How.ID, using = "remitCountry")
    private WebElement remitCountry;

    @FindAll({
            @FindBy(className = "checkbox-inline ng-scope"),
    })
    private List<WebElement> acceptedCardsList;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[5]/div/div[2]/div/span")
    private WebElement copyRemitToAddressBtn;

    @FindBy(how = How.ID, using = "PaypalPartner")
    private WebElement paypalPartner;

    @FindBy(how = How.ID, using = "PaypalCardNotPresentUsername")
    private WebElement paypalCardNotPresentUsername;

    @FindBy(how = How.ID, using = "PaypalCardNotPresentPassword")
    private WebElement paypalCardNotPresentPassword;

    @FindBy(how = How.ID, using = "PaypalCardPresentUsername")
    private WebElement paypalCardPresentUsername;

    @FindBy(how = How.ID, using = "PaypalCardPresentPassword")
    private WebElement paypalCardPresentPassword;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[13]/div/div/button")
    private WebElement createMerchantButton;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[7]/fieldset[2]/div[16]/div/span[1]")
    private WebElement addbeneficialOwnerButton;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[7]/fieldset[2]/div[16]/div/span[2]")
    private WebElement removeBeneficialOwnerButton;

    public AddNewMerchantPage(WebDriver driver) {
        super(driver);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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

    public void fillCustomerContactFirstName(String customerContactFName) {
        customerContactFirstName.clear();
        customerContactFirstName.sendKeys(customerContactFName);
    }

    public void fillCustomerContactLastName(String customerContactLName) {
        customerContactLastName.clear();
        customerContactLastName.sendKeys(customerContactLName);
    }

    public void fillCustomerContactEmail(String customerEmail) {
        customerContactEmail.clear();
        customerContactEmail.sendKeys(customerEmail);
    }

    public void fillCustomerPhoneNumber(String customerPhoneNo) {
        customerPhoneNumber.clear();
        customerPhoneNumber.sendKeys(customerPhoneNo);
    }

    public void fillMerchantAddressLine1(String merchantAddressLineOne) {
        merchantAddressLine1.clear();
        merchantAddressLine1.sendKeys(merchantAddressLineOne);
    }

    public void fillCity(String merchantCity) {
        merchantAddressCity.clear();
        merchantAddressCity.sendKeys(merchantCity);
    }

    public void fillMerchantZip(String merchantZipcode) {
        merchantAddressZip.clear();
        merchantAddressZip.sendKeys(merchantZipcode);
    }

    public void fillLegalEntityName(String legalEntityName) {
        merchantLegalName.clear();
        merchantLegalName.sendKeys(legalEntityName);
    }

    public void fillBeneficialOwnerFirstName(String beneficialOwnerFName, int ownerNumber) {
        WebElement firstName = driver.findElement(By.id("principalFirstName_"+ownerNumber));
        firstName.clear();
        firstName.sendKeys(beneficialOwnerFName);
    }

    public void fillBeneficialOwnerLastName(String beneficialOwnerLName, int ownerNumber) {
        WebElement lastName = driver.findElement(By.id("principalLastName_"+ownerNumber));
        lastName.clear();
        lastName.sendKeys(beneficialOwnerLName);
    }

    public void fillPercentOwnership(String ownershipPercentage, int ownerNumber) {
        WebElement percentageOwnership = driver.findElement(By.id("percentOwnership_"+ownerNumber));
        percentageOwnership.clear();
        percentageOwnership.sendKeys(ownershipPercentage);
    }

    public void fillBeneficialOwnerAddressLine1(String beneficialOwnerAddLine1, int ownerNumber) {
        WebElement beneficialOwnerAdd = driver.findElement(By.id("principaladdressLine1_"+ownerNumber));
        beneficialOwnerAdd.clear();
        beneficialOwnerAdd.sendKeys(beneficialOwnerAddLine1);
    }

    public void fillBeneficialOwnerCity(String beneficialOwnerCityName, int ownerNumber) {
        WebElement beneficialOwnerCity = driver.findElement(By.id("principalcity_"+ownerNumber));
        beneficialOwnerCity.clear();
        beneficialOwnerCity.sendKeys(beneficialOwnerCityName);
    }

    public void fillBeneficialOwnerZip(String beneficialOwnerZipcode, int ownerNumber) {
        WebElement beneficialOwnerZip = driver.findElement(By.id("principalzip_"+ownerNumber));
        beneficialOwnerZip.clear();
        beneficialOwnerZip.sendKeys(beneficialOwnerZipcode);
    }

    public void fillBankAccountNumber(String bankAccountNo) {
        accountNumber.clear();
        accountNumber.sendKeys(bankAccountNo);
    }

    public void fillRoutingNumber(String routingNo) {
        routingNumber.clear();
        routingNumber.sendKeys(routingNo);
    }

    public void fillFederalTaxId(String federalTaxIdValue) {
        federalTaxId.clear();
        federalTaxId.sendKeys(federalTaxIdValue);
    }

    public void fillPerTransactionFeeAuth(String transactionFeeAuth) {
        perTransactionFeeAuth.clear();
        perTransactionFeeAuth.sendKeys(transactionFeeAuth);
    }

    public void fillPerTransactionFeeRefund(String transactionFeeRefund) {
        perTransactionFeeRefund.clear();
        perTransactionFeeRefund.sendKeys(transactionFeeRefund);
    }

    public void fillQualifiedTierBoundary(String qualifiedTierBoundaryValue) {
        qualifiedTierBoundary.clear();
        qualifiedTierBoundary.sendKeys(qualifiedTierBoundaryValue);
    }

    public void fillQualifiedTierFee(String qTierFee) {
        qualifiedTierFee.clear();
        qualifiedTierFee.sendKeys(qTierFee);
    }

    public void fillMidQualifiedTierBoundary(String midQTierBoundary) {
        midQualifiedTierBoundary.clear();
        midQualifiedTierBoundary.sendKeys(midQTierBoundary);
    }

    public void fillMidQualifiedTierFee(String midQTierFee) {
        midQualifiedTierFee.clear();
        midQualifiedTierFee.sendKeys(midQTierFee);
    }

    public void fillNonQualifiedTierBoundary(String nonQTierBoundary) {
        nonQualifiedTierBoundary.clear();
        nonQualifiedTierBoundary.sendKeys(nonQTierBoundary);
    }

    public void fillNonQualifiedTierFee(String nonQTierFee) {
        nonQualifiedTierFee.clear();
        nonQualifiedTierFee.sendKeys(nonQTierFee);
    }
    public void clickCopyRemitToAddressButton() {
        copyRemitToAddressBtn.click();
    }

    public void fillRemitAdressLine1(String remitAddressLine) {
        remitAddressLine1.clear();
        remitAddressLine1.sendKeys(remitAddressLine);
    }

    public void fillRemitCityName(String remitCityName) {
        remitCity.clear();
        remitCity.sendKeys(remitCityName);
    }

    public void fillRemitZipcode(String remitZipcode) {
        remitZip.clear();
        remitZip.sendKeys(remitZipcode);
    }

    public void checkUncheckRemitToAddress() {
        if(!enableDisableRemitToAddress.isSelected()){
            enableDisableRemitToAddress.click();
        }
    }

    public void clickAddBeneficialOwnerBtn(int ownerNumber) {
        WebElement addbeneficialOwnerBtn = driver.findElement(By.xpath("//*[@id='viewContent']/div/div/div/form/fieldset[7]/fieldset["+ownerNumber+"]/div[16]/div/span[1]"));
        addbeneficialOwnerBtn.click();
    }

    public void clickRemoveBeneficialOwnerBtn() {
        removeBeneficialOwnerButton.click();
    }

    public void fillPaypalCardNotPresentUsername(String paypalCardNotPresentUsernm) {
        paypalCardNotPresentUsername.clear();
        paypalCardNotPresentUsername.sendKeys(paypalCardNotPresentUsernm);
    }

    public void fillPaypalCardNotPresentPassword(String paypalCardNotPresentPwd) {
        paypalCardNotPresentPassword.clear();
        paypalCardNotPresentPassword.sendKeys(paypalCardNotPresentPwd);
    }

    public void fillPaypalCardPresentUsername(String paypalCardPresentUsernm) {
        paypalCardPresentUsername.clear();
        paypalCardPresentUsername.sendKeys(paypalCardPresentUsernm);
    }

    public void fillPaypalCardPresentPassword(String paypalCardPresentPwd) {
        paypalCardPresentPassword.clear();
        paypalCardPresentPassword.sendKeys(paypalCardPresentPwd);
    }

    public void selectPaypalPartner(String paypalPartnerName) {
        Select select = new Select(paypalPartner);
        select.selectByVisibleText(paypalPartnerName);
    }

    public void selectRemitCountry(String remitAddressCountry) {
        Select select = new Select(remitCountry);
        select.selectByVisibleText(remitAddressCountry);
    }

    public void selectRemitState(String remitAddressState) {
        Select select = new Select(remitState);
        select.selectByVisibleText(remitAddressState);
    }

    public void selectVendorType(String vendorTypeValue) {
        Select select = new Select(vendorType);
        select.selectByVisibleText(vendorTypeValue);
    }

    public List<String> getVendorTypes() {
        Select select = new Select(vendorType);
        List<String> vendors = new ArrayList<String>();

        for(WebElement vendor: select.getOptions()){
            vendors.add(vendor.getText());
        }
        return vendors;
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

    public void selectMerchantAddressState(String merchantState) {
        Select select = new Select(merchantAddressState);
        select.selectByVisibleText(merchantState);
    }

    public void selectMerchantAddressCountry(String merchantCountry) {
        Select select = new Select(merchantAddressCountry);
        select.selectByVisibleText(merchantCountry);
    }

    public void selectBeneficialOwnerState(String beneficialOwnersState, int beneficialOwnerNumber) {
        WebElement stateBeneficialOwner = driver.findElement(By.id("principalstate_"+beneficialOwnerNumber));
        Select select = new Select(stateBeneficialOwner);
        select.selectByVisibleText(beneficialOwnersState);
    }

    public void selectBeneficialOwnerCountry(String beneficialOwnersCountry, int beneficialOwnerNumber) {
        WebElement stateBeneficialOwner = driver.findElement(By.id("principalcountry_"+beneficialOwnerNumber));
        Select select = new Select(stateBeneficialOwner);
        select.selectByVisibleText(beneficialOwnersCountry);
    }

    public void selectFeeSettlementType(String feeSettlementTypeValue) {
        Select select = new Select(feeSettlementType);
        select.selectByVisibleText(feeSettlementTypeValue);
    }

    public void selectAccountType(String bankAccountType) {
        Select select = new Select(accountType);
        select.selectByVisibleText(bankAccountType);
    }

    public void selectAccountUsage(String bankAccountUsage) {
        Select select = new Select(accountUsage);
        select.selectByVisibleText(bankAccountUsage);
    }

    public void selectControlOwnerType(String controlOwnerTypeValue) {
        Select select = new Select(controlOwnerType);
        select.selectByVisibleText(controlOwnerTypeValue);
    }

    public MerchantDetailsPage clickCreateMerchantButton() {
        createMerchantButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='top-nav']/div/div[2]/div[1]/span[1]")));
        return PageFactory.initElements(driver, MerchantDetailsPage.class);
    }

    public boolean areDefaultCardsChecked(){
        for(WebElement card : acceptedCardsList){
            String cardName = card.findElement(By.tagName("title")).getText();
            if(cardName.equalsIgnoreCase("Visa")|| cardName.equalsIgnoreCase("Mastercard")){
                Assert.assertTrue("Card is not selected by default", card.isSelected());
            }
        }
        return true;
    }

    public void checkRemitToAdressAndFillDetails() throws InterruptedException {
        if(!enableDisableRemitToAddress.isSelected()){
            enableDisableRemitToAddress.click();
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOf(remitAddressLine1));
            clickCopyRemitToAddressButton();
        }
    }

    public void isRemitToAdress(Boolean isRemitToAdress) throws InterruptedException {
        if(isRemitToAdress == true){
            checkRemitToAdressAndFillDetails();
        }
        else {
            Assert.assertTrue(!enableDisableRemitToAddress.isSelected());
        }
    }

    public void fillGeneralMerchantInformation(MerchantEntity merchantEntity, String accountNo, String phoneNo,
                                               String txnLimit, String billingDesc, String ownershipType, String businessType,
                                               String sicMccCode, String estbDate, String url) {
        fillMerchantName(merchantEntity.getMerchantName());
        fillDoingBusinessAs(merchantEntity.getDoingBusinessAs());
        fillPracticeID(merchantEntity.getPracticeID());
        fillCustomerAccountNumber(accountNo);
        fillPhoneNumber(phoneNo);
        fillMaxTransactionLimit(txnLimit);
        fillBillingDescriptor(billingDesc);
        selectOwnershipType(ownershipType);
        selectBusinessType(businessType);
        selectSicMccCode(sicMccCode);
        fillBusinessEstablishedDate(estbDate);
        fillWebsiteURL(url);
        selectPayApiCustomers(merchantEntity.getPayAPICustomer());
    }

    public void fillCustomerContact(String customerFirstName, String customerLastName,
                                               String customerEmail, String customerPhone) {
            fillCustomerContactFirstName(customerFirstName);
            fillCustomerContactLastName(customerLastName);
            fillCustomerContactEmail(customerEmail);
            fillCustomerPhoneNumber(customerPhone);
    }

    public void fillMerchantAddressInformation(String merchantAddressLine1, String merchantCity,
                                    String merchantState, String merchantZip, String country) {
        fillMerchantAddressLine1(merchantAddressLine1);
        fillCity(merchantCity);
        selectMerchantAddressState(merchantState);
        fillMerchantZip(merchantZip);
        selectMerchantAddressCountry(country);
    }

    public void fillRemitToAddressIfTrue(Boolean isRemitToAddress) throws InterruptedException {
        isRemitToAdress(isRemitToAddress);
    }

    public void fillBeneficialOwnerInformation(Integer numberOfBeneficialOwners, String firstName,
                                               String lastName, String ownershipPercent,
                                               String addressLine1, String city, String state,
                                               String zipcode, String country, String ownerType) {
        if(numberOfBeneficialOwners <= 1) {
            fillBeneficialOwnerFirstName(firstName, numberOfBeneficialOwners);
            fillBeneficialOwnerLastName(lastName, numberOfBeneficialOwners);
            fillPercentOwnership(ownershipPercent, numberOfBeneficialOwners);
            fillBeneficialOwnerAddressLine1(addressLine1, numberOfBeneficialOwners);
            fillBeneficialOwnerCity(city, numberOfBeneficialOwners);
            selectBeneficialOwnerState(state,numberOfBeneficialOwners);
            fillBeneficialOwnerZip(zipcode, numberOfBeneficialOwners);
            selectBeneficialOwnerCountry(country, numberOfBeneficialOwners);
            selectControlOwnerType(ownerType);
        }
        else {
            for(int i=1;i<=numberOfBeneficialOwners+1;i++){
                System.out.println("Beneficial Owner Number : "+i);
                fillBeneficialOwnerFirstName(firstName, i);
                fillBeneficialOwnerLastName(lastName, i);
                fillPercentOwnership(ownershipPercent, i);
                fillBeneficialOwnerAddressLine1(addressLine1, i);
                fillBeneficialOwnerCity(city, i);
                selectBeneficialOwnerState(state, i);
                fillBeneficialOwnerZip(zipcode, i);
                selectBeneficialOwnerCountry(country, i);
                if(i<numberOfBeneficialOwners+1){
                clickAddBeneficialOwnerBtn(i+1);}
            }

        }
    }

    public void fillBankAccountDetails(String accountUsage, String accountType, String routingNumber,
                                       String bankAccountNo, String federalTaxId) {
            selectAccountUsage(accountUsage);
            selectAccountType(accountType);
            fillRoutingNumber(routingNumber);
            fillBankAccountNumber(bankAccountNo);
            fillFederalTaxId(federalTaxId);
    }

    public void fillRatesAndFees(String perTransactionAuthFee, String perTransactionRefundFee,
                                 String qTierUpper, String qTierFee,
                                 String mTierUpper, String mTierFee,
                                 String nonQTierUpper, String nonQTierFee) {
            fillPerTransactionFeeAuth(perTransactionAuthFee);
            fillPerTransactionFeeRefund(perTransactionRefundFee);
            fillQualifiedTierBoundary(qTierUpper);
            fillQualifiedTierFee(qTierFee);
            fillMidQualifiedTierBoundary(mTierUpper);
            fillMidQualifiedTierFee(mTierFee);
            fillNonQualifiedTierBoundary(nonQTierUpper);
            fillNonQualifiedTierFee(nonQTierFee);
    }

    public void addMerchant(String vendorType, MerchantEntity merchantEntity,
                            String cardPresentPassword, String cardNotPresentPassword) throws InterruptedException {
        if(vendorType.equalsIgnoreCase("Paypal"))
        {
            selectVendorType(merchantEntity.getVendorType());
            fillMerchantName(merchantEntity.getMerchantName());
            fillPracticeID(merchantEntity.getPracticeID());
            fillCustomerAccountNumber(merchantEntity.getCustomerAccountNo());
            fillMaxTransactionLimit(merchantEntity.getMaxTransactionLimit());
            selectPaypalPartner(merchantEntity.getPaypalPartner());
            fillPaypalCardNotPresentUsername(merchantEntity.getPaypalCardNotPresentUsername());
            fillPaypalCardNotPresentPassword(cardPresentPassword);
            fillPaypalCardPresentUsername(merchantEntity.getPaypalCardPresentUsername());
            fillPaypalCardPresentPassword(cardNotPresentPassword);
        }
    }

}
