package pageobjects;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

public class AddBeneficialOwnerPage extends MerchantDetailsPage{
    protected static PropertyFileLoader testData;

    @FindBy(how = How.ID, using = "principalFirstName")
    private WebElement principalFirstName;

    @FindBy(how = How.ID, using = "principalLastName")
    private WebElement principalLastName;

    @FindBy(how = How.ID, using = "percentOwned")
    private WebElement percentOwned;

    @FindBy(how = How.ID, using = "principaladdressLine1")
    private WebElement principaladdressLine1;

    @FindBy(how = How.ID, using = "principalcity")
    private WebElement principalcity;

    @FindBy(how = How.ID, using = "principalstate")
    private WebElement principalstate;

    @FindBy(how = How.ID, using = "principalzip")
    private WebElement principalzip;

    @FindBy(how = How.ID, using = "principalcountry")
    private WebElement principalcountry;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div[2]/form/div[2]/fieldset/div/button[1]")
    private WebElement createBeneficialOwnerBtn;

    @FindBy(how = How.XPATH, using = "//*[@id='viewContent']/div/div[2]/form/div[2]/fieldset/div/button[2]")
    private WebElement cancelChangesBtn;

    public void editFirstName(String fname) {
        principalFirstName.clear();
        principalFirstName.sendKeys(fname);
    }

    public void editLastName(String lname) {
        principalLastName.clear();
        principalLastName.sendKeys(lname);
    }

    public void editPercentOwned(String percentageOwned) {
        percentOwned.clear();
        percentOwned.sendKeys(percentageOwned);
    }

    public void editPrincipalAddressLine1(String addressLine1) {
        principaladdressLine1.clear();
        principaladdressLine1.sendKeys(addressLine1);
    }

    public void editPrincipalCity(String city) {
        principalcity.clear();
        principalcity.sendKeys(city);
    }

    public void editPrincipalZip(String zipcode) {
        principalzip.clear();
        principalzip.sendKeys(zipcode);
    }

    public MerchantDetailsPage clickCreateBeneficialOwnerBtn() {
        createBeneficialOwnerBtn.click();
        return PageFactory.initElements(driver, MerchantDetailsPage.class);
    }

    public MerchantDetailsPage editCancelChangesBtn(String zipcode) {
        cancelChangesBtn.clear();
        return PageFactory.initElements(driver, MerchantDetailsPage.class);
    }

    public void selectState(String state) {
        Select select = new Select(principalstate);
        select.selectByVisibleText(state);
    }

    public AddBeneficialOwnerPage(WebDriver driver) throws IOException {
        super(driver);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        testData = new PropertyFileLoader();
    }

    public Boolean verifyFieldsOnEditAddbeneficialOwnerPage(){
        if(principalFirstName.isDisplayed() && principalLastName.isDisplayed()
                && percentOwned.isDisplayed() && principaladdressLine1.isDisplayed()
                && principalcity.isDisplayed() && principalstate.isDisplayed()
                && principalzip.isDisplayed() && principalcountry.isDisplayed()){
            return true;
        }else {return false;}
    }

    public Boolean verifyButtonsPresent(){
        if(createBeneficialOwnerBtn.isDisplayed() && cancelChangesBtn.isDisplayed()){
            return true;
        }else{return false;}
    }


}
