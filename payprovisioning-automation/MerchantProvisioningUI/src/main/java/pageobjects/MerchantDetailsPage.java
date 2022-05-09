package pageobjects;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertNotNull;

public class MerchantDetailsPage extends NavigationMenu {
	protected static PropertyFileLoader testData;

	public MerchantDetailsPage(WebDriver driver) throws IOException {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
		testData = new PropertyFileLoader();
	}

	List<String> userRoles = Collections.unmodifiableList(
			Arrays.asList("Fees and Funding", "Point-of-Sale Admin", "Void/Refund", "Full DRR", "Point-of-Sale"));

	List<String> payApiCustomers = Collections.unmodifiableList(Arrays.asList("NG Pay", "NG Enterprise"));

	List<String> settlementType = Collections.unmodifiableList(Arrays.asList("DAILY", "MONTHLY"));

	@FindBy(how = How.XPATH, using = "//*[@id='Processor']")
	private WebElement processor;

	@FindBy(how = How.XPATH, using = "//*[@id='merchantIDExternal']")
	private WebElement merchantIdExternal;

	@FindBy(how = How.ID, using = "MMIDInternal")
	private WebElement mmidInternal;

	@FindBy(how = How.ID, using = "elementAccountId")
	private WebElement elementAccountId;

	@FindBy(how = How.ID, using = "elementAcceptorId")
	private WebElement elementAcceptorId;

	@FindBy(how = How.ID, using = "practiceID")
	private WebElement practiceID;

	@FindBy(how = How.ID, using = "payApiCustomer")
	private WebElement payApiCustomer;

	@FindBy(how = How.ID, using = "doingBusinessAs")
	private WebElement doingBusinessAs;

	@FindBy(how = How.ID, using = "customerAccountNumber")
	private WebElement customerAccountNumber;

	// Accepted cards
	@FindBy(how = How.XPATH, using = "//div[@data-ng-repeat='card in acceptedCards']/img[@alt='Visa']")
	private WebElement visaCard;

	@FindBy(how = How.XPATH, using = "//div[@data-ng-repeat='card in acceptedCards']/img[@alt='Mastercard']")
	private WebElement masterCard;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Accepted Cards ']")
	private WebElement editAcceptedCardsButton;

	// Account Details

	@FindBy(how = How.ID, using = "routingNumber")
	private WebElement routingNumber;

	@FindBy(how = How.ID, using = "accountNumber")
	private WebElement accountNumber;

	@FindBy(how = How.ID, using = "federalTaxId")
	private WebElement federalTaxId;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Account Details ']")
	private WebElement editAccountDetailsButton;

	// eStatement Options

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Statement Options ']")
	private WebElement editStatementOptionsButton;

	// Users and Roles - need to try

	@FindAll({ @FindBy(how = How.XPATH, using = "//span[@data-ng-repeat='role in one.roles']") })
	private List<WebElement> userRolesList;

	// Rates and Contract Information

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Rates and Contract Information ']")
	private WebElement editRatesContractButton;

	// Percentage Fee Tiers
	@FindBy(how = How.XPATH, using = "//span[@class='ng-binding']")
	private WebElement settlement;

	@FindBy(how = How.XPATH, using = "//a[text()=' Collect Chargeback ']")
	private WebElement chargeBackButton;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Fraud/Risk Variables ']")
	private WebElement editFraudButton;

	@FindBy(how = How.XPATH, using = "//*[@id='top-nav']/div/div[2]/div[1]/span[1]")
	private WebElement mmid;

	@FindBy(how = How.XPATH, using = "//div[@id='feeSettlement']/following-sibling::div")
	private WebElement feeSettlement;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit General Merchant Info ']")
	private WebElement editGeneralMerchantInfoButton;

	@FindBy(how = How.XPATH, using = "//a[text()=' Add New Beneficial Owner ']")
	private WebElement addNewBeneficialOwnerButton;
	
	//Add User Roles
	
    @FindBy(how = How.XPATH, using ="//a[text()=' Add Users and Roles ']")
	private WebElement addUsersRoleButton;
    
    @FindAll({ @FindBy(how = How.XPATH, using = "//span[@data-ng-repeat='role in user.roles']") })
	private List<WebElement> AddedUserRolesList;

	@FindBys({
			@FindBy(how = How.CLASS_NAME, using="btn btn-danger"),
			@FindBy(how = How.CSS, using = "button[data-ng-click='deleteBeneficialOwner(principal.beneficialOwnerId)']")
	})
	private List<WebElement> removeBeneficialOwnerButtons;

	@FindBys({
			@FindBy(how = How.ID, using="beneficialOwnerFirstName")
	})
	private List<WebElement> beneficialOwnerFirstName;

	@FindBys({
			@FindBy(how = How.XPATH, using = "beneficialOwnerLastName")
	})
	private List<WebElement> beneficialOwnerLastName;

	@FindBys({
			@FindBy(how = How.XPATH, using = "beneficialOwnerPercent")
	})
	private List<WebElement> beneficialOwnerPercent;

	@FindBy(how = How.XPATH, using = "//*[@id='merchantDetail']/div[3]/fieldset/div[1]/div/fieldset/legend/div")
	private WebElement paymentProcessorInformation;

	@FindBy(how = How.XPATH, using ="//*[@id='merchantDetail']/div[3]/div[5]/div/fieldset/legend/div[2]/div/div/a")
	private WebElement editPartnersButton;

	public void verifyPageTitle() {

		String title = this.driver.getTitle();
		assertNotNull(title);

	}

	public void verifyProcessorInformation(String processorType){

		assertNotNull(processor);
		assertNotNull(mmidInternal);
		assertNotNull(merchantIdExternal);
		assertNotNull(elementAccountId);
		assertNotNull(elementAcceptorId);

		Assert.assertEquals(processor.getText(), processorType);

	}

	public void verifyGeneralMerchantInformation(String practiceId, String customerNo, String doingBusinessAsName) {

		assertNotNull(payApiCustomer);
		Assert.assertEquals(practiceID.getText(), practiceId);
		Assert.assertEquals(customerAccountNumber.getText(), customerNo);
		Assert.assertEquals(doingBusinessAs.getText(), doingBusinessAsName);

		if (payApiCustomers.contains(payApiCustomer.getText())) {

			Assert.assertTrue(payApiCustomers.contains(payApiCustomer.getText()));
		}

	}

	public void verifyAcceptedCards() {

		assertNotNull(visaCard);
		assertNotNull(masterCard);

	}

	public void verifyAccountDetails() {

		assertNotNull(routingNumber);
		assertNotNull(accountNumber);
		assertNotNull(federalTaxId);

	}

	public void verifyRatesContractInformation() {

		Assert.assertTrue(editRatesContractButton.isDisplayed());

	}

	public void verifyPercentageFeeTiers() {

		assertNotNull(settlement);
		if (settlementType.contains(settlement.getText())) {

			Assert.assertTrue(settlementType.contains(settlement.getText()));
		}

	}

	public void verifyUserRoles() {

		for (WebElement userRoleElement : userRolesList) {
			String userRole = userRoleElement.getText().replace(",", "");

			if (userRoles.contains(userRole)) {

				Assert.assertTrue(userRoles.contains(userRole));

			}
		}

	}

	public void verifyStatementOption() {

		Assert.assertTrue(editStatementOptionsButton.isDisplayed());

	}

	public void verifyChargeback() {

		Assert.assertTrue(chargeBackButton.isDisplayed());
	}

	public void verifyFraudRiskVariable() {

		Assert.assertTrue(editFraudButton.isDisplayed());

	}

	public String getMMID() {
		return mmid.getText();
	}

	public void clickOnRatesNContractButton() throws InterruptedException {
		editRatesContractButton.click();
		
	}

	public void verifySettlementType(String settlementType) throws InterruptedException {

		Assert.assertEquals(feeSettlement.getText().toUpperCase(), settlementType.toUpperCase());

	}

	public void waitToCheckEditRatesContractButton() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(editRatesContractButton));
		wait.until(ExpectedConditions.visibilityOf(settlement));
	
	}

	public GeneralMerchantInformationPage clickEditGeneralMerchantInfoButton() throws InterruptedException {
		editGeneralMerchantInfoButton.click();
		return PageFactory.initElements(driver, GeneralMerchantInformationPage.class);

	}

	public AddBeneficialOwnerPage clickAddNewBeneficialOwnerButton(){
		addNewBeneficialOwnerButton.click();
		return PageFactory.initElements(driver, AddBeneficialOwnerPage.class);

	}

	public void removeBeneficialOwner(){
		for(WebElement removeBeneficiary: removeBeneficialOwnerButtons){
			removeBeneficiary.click();
		}
	}

	public void waitForPageToLoad(){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(paymentProcessorInformation));
	}
	
	public AddUsersAndRolesPage clickAddUsersAndRoles() {
		addUsersRoleButton.click();
		return PageFactory.initElements(driver, AddUsersAndRolesPage.class);
	}
	
	public Boolean verifyAddUserRoleButtonsPresent(){
        if(addUsersRoleButton.isDisplayed()){
            return true;
        }else{return false;}
    }
	
	public void verifyAddedUserRoles() {

		for (WebElement userRoleElement : AddedUserRolesList) {
			String userRole = userRoleElement.getText().replace(",", "");

			if (userRoles.contains(userRole)) {

				Assert.assertTrue(userRoles.contains(userRole));

			}
		}

	}

	public PartnerPage clickOnEditpartner(){
		editPartnersButton.click();
		return PageFactory.initElements(driver, PartnerPage.class);
	}
}
