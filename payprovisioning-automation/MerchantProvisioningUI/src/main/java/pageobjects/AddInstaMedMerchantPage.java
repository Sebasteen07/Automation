package pageobjects;

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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddInstaMedMerchantPage extends NavigationMenu{

    @FindBy(how = How.ID, using = "vendorType")
    private WebElement vendorType;

    @FindBy(how = How.ID, using = "merchantName")
    private WebElement merchantName;

    @FindBy(how = How.ID, using = "externalMerchantId")
    private WebElement practiceId;

    @FindBy(how = How.ID, using = "customerAccountNumber")
    private WebElement customerAccountNumber;

    @FindAll({
            @FindBy(how = How.XPATH, using = "//input[@name = \"acceptedCards\"]"),
    })
    private List<WebElement> acceptedCardsList;

    @FindBy(how = How.ID, using = "platformFeeAuth")
    private WebElement perTransactionFeeAuth;

    @FindBy(how = How.ID, using = "qTierFee")
    private WebElement qualifiedTierFee;

    @FindBy(how = How.ID, using = "mQTierFee")
    private WebElement mQTierFee;

    @FindBy(how = How.ID, using = "nQTierFee")
    private WebElement nQTierFee;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[12]/div/div/button")
    private WebElement createMerchantButton;

    @FindBy(how = How.ID, using = "instamed_clientID")
    private WebElement instamed_clientID;

    @FindBy(how = How.ID, using = "instamed_clientSecret")
    private WebElement instamed_clientSecret;

    @FindBy(how = How.ID, using = "instamed_MerchantID")
    private WebElement instamed_MerchantID;

    @FindBy(how = How.ID, using = "instamed_StoreID")
    private WebElement instamed_StoreID;

    @FindBy(how = How.ID, using = "instamed_TerminalID_patientPortal")
    private WebElement instamed_TerminalID_patientPortal;

    @FindBy(how = How.ID, using = "instamed_TerminalID_preCheck")
    private WebElement instamed_TerminalID_preCheck;

    @FindBy(how = How.ID, using = "instamed_TerminalID_virtualVisits")
    private WebElement instamed_TerminalID_virtualVisits;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[7]/div[2]/div[1]/label[1]/input")
    private WebElement visa;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[7]/div[2]/div[1]/label[2]/input")
    private WebElement mastercard;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[7]/div[2]/div[1]/label[3]/input")
    private WebElement amex;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[7]/div[2]/div[1]/label[4]/input")
    private WebElement discover;

    @FindBy(how = How.XPATH, using = "//div[@data-ng-if = 'isCardSelectionInvalid()']")
    private WebElement atleastOneCardErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[2]/div[1]/div/div/div")
    private WebElement merchantNameErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[2]/div[3]/div/div[2]/div")
    private WebElement customerAccountNumberErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[3]/div[3]/div/div[2]/div")
    private WebElement midErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[3]/div[4]/div/div[2]/div")
    private WebElement storeIDErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[9]/div/div/div[2]/div")
    private WebElement perTransAuthFeeErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[10]/div[1]/div[2]/div/div/div[2]/div")
    private WebElement qTierFeeErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[10]/div[2]/div[2]/div/div/div[2]/div")
    private WebElement midQualifiedTierFeeErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[10]/div[3]/div[2]/div/div/div[2]/div")
    private WebElement nonQualifiedTierFeeErrorMessage;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[4]/div/div[4]/div/div")
    private WebElement terminalErrorMessage;

    @FindBy(how = How.ID, using = "amexRate")
    private WebElement amexRate;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div/div/form/fieldset[10]/div[4]/div[2]/div/div/div[2]/div")
    private WebElement amexFeeErrorMessage;

    public AddInstaMedMerchantPage(WebDriver driver) {
        super(driver);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void createMerchantButton(){
        createMerchantButton.click();
    }

    public void selectVendorType(String vendorTypeValue) {
        Select select = new Select(vendorType);
        select.selectByVisibleText(vendorTypeValue);
    }

    public void fillMerchantName(String merchant) {
        merchantName.clear();
        merchantName.sendKeys(merchant);
    }

    public void fillPracticeID(String practiceID) {
        practiceId.clear();
        practiceId.sendKeys(practiceID);
    }

    public void fillCustomerAccountNumber(String customerAccountNo) {
        customerAccountNumber.clear();
        customerAccountNumber.sendKeys(customerAccountNo);
    }

    public void fillClientID(String clientID) {
        instamed_clientID.clear();
        instamed_clientID.sendKeys(clientID);
    }

    public void fillClientSecret(String clientSecret) {
        instamed_clientSecret.clear();
        instamed_clientSecret.sendKeys(clientSecret);
    }

    public void fillMID(String mid) {
        instamed_MerchantID.clear();
        instamed_MerchantID.sendKeys(mid);
    }

    public void fillStoreID(String storeID) {
        instamed_StoreID.clear();
        instamed_StoreID.sendKeys(storeID);
    }

    public void fillTerminalIdPatientPortal(String patientPortal) {
        instamed_TerminalID_patientPortal.clear();
        instamed_TerminalID_patientPortal.sendKeys(patientPortal);
    }

    public void fillTerminalIdPRCC(String preCheckTerminalID) {
        instamed_TerminalID_preCheck.clear();
        instamed_TerminalID_preCheck.sendKeys(preCheckTerminalID);
    }

    public void fillTerminalIdVV(String virtualVisitsTerminalId) {
        instamed_TerminalID_virtualVisits.clear();
        instamed_TerminalID_virtualVisits.sendKeys(virtualVisitsTerminalId);
    }

    public String atleastOneCardErrorMessage(){
        visa.click();
        visa.click();
        return atleastOneCardErrorMessage.getText();
    }

    public void selectAnAcceptedCard(int card){
        switch(card){
            case 1:
                visa.click();
                break;
            case 2:
                mastercard.click();
                break;
            case 3:
                amex.click();
                break;
            case 4:
                discover.click();
                break;
        }
    }

    public Boolean isAmexFeeErrorPresent(){
        if(!amex.isSelected()){
            amex.click();
            createMerchantButton.click();
        }
        return amexFeeErrorMessage.isDisplayed();
    }

    public void fillAmexFee(String amexFee) {
        amexRate.clear();
        amexRate.sendKeys(amexFee);
    }

    public void fillPerTransactionFeeAuth(String transactionFeeAuth) {
        perTransactionFeeAuth.clear();
        perTransactionFeeAuth.sendKeys(transactionFeeAuth);
    }

    public void fillQualifiedTierFee(String qualifiedTierBoundaryValue) {
        qualifiedTierFee.clear();
        qualifiedTierFee.sendKeys(qualifiedTierBoundaryValue);
    }

    public void fillMidQualifiedTierFee(String midQTierBoundary) {
        mQTierFee.clear();
        mQTierFee.sendKeys(midQTierBoundary);
    }

    public void fillNonQualifiedTierFee(String nonQTierBoundary) {
        nQTierFee.clear();
        nQTierFee.sendKeys(nonQTierBoundary);
    }

    public MerchantDetailsPage clickCreateMerchantButton() {
        createMerchantButton();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='top-nav']/div/div[2]/div[1]/span[1]")));
        return PageFactory.initElements(driver, MerchantDetailsPage.class);
    }

    public void addVendorAndGeneralMerchantInfo(String vendor, String merchName, String pracID, String custAccountNo){
        selectVendorType(vendor);
        fillMerchantName(merchName);
        fillPracticeID(pracID);
        fillCustomerAccountNumber(custAccountNo);
    }

    public void addPaymentProcessorInformation(String clientID, String clientSecret, String mid, String storeID){
        fillClientID(clientID);
        fillClientSecret(clientSecret);
        fillMID(mid);
        fillStoreID(storeID);
    }

    public void addTerminalInformation(String terminalID1,
                                       String terminalID2, String terminalID3){
        fillTerminalIdPatientPortal(terminalID1);
        fillTerminalIdPRCC(terminalID2);
        fillTerminalIdVV(terminalID3);
    }

    public void addRatesAndFees(String fillPerTransactionFeeAuth, String fillQualifiedTierBoundary,
                                String fillMidQualifiedTierBoundary, String fillNonQualifiedTierBoundary){
        fillPerTransactionFeeAuth(fillPerTransactionFeeAuth);
        fillQualifiedTierFee(fillQualifiedTierBoundary);
        fillMidQualifiedTierFee(fillMidQualifiedTierBoundary);
        fillNonQualifiedTierFee(fillNonQualifiedTierBoundary);
    }

    public String merchantNameMandatoryValidation(){
        return merchantNameErrorMessage.getText();
    }

    public String customerAccountNumberMandatoryValidation(){
        return customerAccountNumberErrorMessage.getText();
    }

    public String midMandatoryValidation(){
        return midErrorMessage.getText();
    }

    public String storeIDMandatoryValidation(){
        return storeIDErrorMessage.getText();
    }

    public String terminalInformationMandatoryValidation(){
        return terminalErrorMessage.getText();
    }

    public String acceptedCardsMandatoryValidation(){
        return atleastOneCardErrorMessage.getText();
    }

    public String perTransactionAuthFeeMandatoryValidation(){
        return perTransAuthFeeErrorMessage.getText();
    }

    public String qualifiedTierFeeMandatoryValidation(){
        return qTierFeeErrorMessage.getText();
    }

    public String midQualifiedTierMandatoryValidation(){
        return midQualifiedTierFeeErrorMessage.getText();
    }

    public String nonQualifiedTierMandatoryValidation(){
        return  nonQualifiedTierFeeErrorMessage.getText();
    }
}
