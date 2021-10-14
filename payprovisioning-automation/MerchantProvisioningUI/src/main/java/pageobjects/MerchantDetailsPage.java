package pageobjects;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.PropertyFileLoader;

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

	public void verifyPageTitle() {

		String title = this.driver.getTitle();

	}

	public void verifyProcessorInformation(String processorType) throws InterruptedException {

		Thread.sleep(2000);
		assertNotNull(processor);
		assertNotNull(mmidInternal);
		assertNotNull(merchantIdExternal);
		assertNotNull(elementAccountId);
		assertNotNull(elementAcceptorId);

		Assert.assertEquals(processor.getText(), processorType);

	}

	public void verifyGeneralMerchantInformation(String practiceId) {

		assertNotNull(payApiCustomer);
		Assert.assertEquals(practiceID.getText(), practiceId);

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
		Thread.sleep(5000);
	}

	public void verifySettlementType(String settlementType) throws InterruptedException {

		Assert.assertEquals(feeSettlement.getText().toUpperCase(), settlementType.toUpperCase());

	}

	public void waitToCheckEditRatesContractButton() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(editRatesContractButton));
	}

}
