package pageobjects;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class InstaMedMerchantDetailsPage extends NavigationMenu{

    protected static PropertyFileLoader testData;

    public InstaMedMerchantDetailsPage(WebDriver driver) throws IOException {
        super(driver);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        testData = new PropertyFileLoader();
    }

    @FindBy(how = How.XPATH, using = "//*[@id='merchantDetail']/div[3]/fieldset[1]/div[1]/div/fieldset/div[1]/div[2]")
    private WebElement processor;

    @FindBy(how = How.ID, using = "MMIDInternal")
    private WebElement mmidInternal;

    @FindBy(how = How.XPATH, using = "//*[@id='merchantDetail']/div[3]/fieldset[1]/div[2]/div/fieldset/div[1]/div[2]")
    private WebElement merchantName;

    @FindBy(how = How.ID, using = "practiceID")
    private WebElement practiceID;

    @FindBy(how = How.ID, using = "customerAccountNumber")
    private WebElement customerAccountNumber;

    @FindBy(how = How.ID, using = "MerchantID")
    private WebElement mid;

    @FindBy(how = How.ID, using = "ClientID")
    private WebElement clientID;

    @FindBy(how = How.ID, using = "ClientSecret")
    private WebElement clientSecret;

    @FindBy(how = How.ID, using = "StoreID")
    private WebElement storeID;

    @FindBy(how = How.ID, using = "TerminalIDPP")
    private WebElement terminalIDPP;

    @FindBy(how = How.ID, using = "TerminalIDP")
    private WebElement terminalIDP;

    @FindBy(how = How.ID, using = "TerminalIDVV")
    private WebElement terminalIDVV;

    @FindBy(how = How.ID, using = "platformFeeAuth")
    private WebElement platformFeeAuth;

    @FindBy(how = How.ID, using = "qTierFee")
    private WebElement qTierFee;

    @FindBy(how = How.ID, using = "mQTierFee")
    private WebElement mQTierFee;

    @FindBy(how = How.ID, using = "nQTierFee")
    private WebElement nQTierFee;

    @FindBy(how = How.ID, using = "amexFee")
    private WebElement amexFee;

    public String getProcessorInformation(){
        return processor.getText();
    }

    public Boolean verifyMmidPresent(){
        return mmidInternal.isDisplayed();
    }

    public String getMerchantName(){
        return merchantName.getText();
    }

    public String getPracticeID(){
        return practiceID.getText();
    }

    public String getAccountNumber(){
        return customerAccountNumber.getText();
    }

    public String getMIDText(){
        return mid.getText();
    }

    public String getClientID(){
        return clientID.getText();
    }
    public String getClientSecret(){
        return clientSecret.getText();
    }
    public String getStoreID(){
        return storeID.getText();
    }
    public String getTerminalIdPatientPortal(){
        return terminalIDPP.getText();
    }
    public String getTerminalIdPreCheck(){
        return terminalIDP.getText();
    }

    public String getTerminalIdVirtualVisits(){
        return terminalIDVV.getText();
    }

    public String getPlatformFeeAuth(){
        return platformFeeAuth.getText();
    }

    public String getMidTierQFee(){
        return mQTierFee.getText();
    }

    public String getQTierQFee(){
        return qTierFee.getText();
    }

    public String getNonTierQFee(){
        return nQTierFee.getText();
    }

}
