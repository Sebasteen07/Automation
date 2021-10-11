package pageobjects;

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

	@FindBy(how = How.XPATH, using = "/html/body/div[3]/div/div[3]/div[5]/div/fieldset/legend/div[2]/div/div/")
	private WebElement collectChargebackButton;

	@FindBy(how = How.XPATH, using = "//*[@id='Processor']")
	private WebElement processor;

	@FindBy(how = How.XPATH, using = "//*[@id='merchantIDExternal']")
	private WebElement merchantIdExternal;

	@FindBy(how = How.ID, using = "MMIDInternal")
	private WebElement mmidInternal;

	@FindBy(how = How.ID, using = "vantivIBMMID")
	private WebElement vantivIBMMID;

	@FindBy(how = How.ID, using = "subMerchantId")
	private WebElement subMerchantId;

	@FindBy(how = How.ID, using = "elementAccountId")
	private WebElement elementAccountId;

	@FindBy(how = How.ID, using = "elementAcceptorId")
	private WebElement elementAcceptorId;

	@FindBy(how = How.XPATH, using = "//a[text()=' Get Underwriting Status ']")
	private WebElement underwiringStatusButton;

	@FindBy(how = How.ID, using = "merchantName")
	private WebElement merchantName;

	@FindBy(how = How.ID, using = "practiceID")
	private WebElement practiceID;

	@FindBy(how = How.ID, using = "customerAccountNumber")
	private WebElement customerAccountNumber;

	@FindBy(how = How.ID, using = "merchantPhone")
	private WebElement merchantPhone;

	@FindBy(how = How.ID, using = "txLimit")
	private WebElement txLimit;

	@FindBy(how = How.ID, using = "billingDescriptor")
	private WebElement billingDescriptor;

	@FindBy(how = How.ID, using = "ownershipType")
	private WebElement ownershipType;

	@FindBy(how = How.ID, using = "businessType")
	private WebElement businessType;

	@FindBy(how = How.ID, using = "businessEstablishedDate")
	private WebElement businessEstablishedDate;

	@FindBy(how = How.ID, using = "sicMccCode")
	private WebElement sicMccCode;

	@FindBy(how = How.ID, using = "payApiCustomer")
	private WebElement payApiCustomer;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit General Merchant Info ']")
	private WebElement editGeneralMerchantInfoButton;

	// Customer contact
	@FindBy(how = How.ID, using = "primaryContactFirstName")
	private WebElement primaryContactFirstName;

	@FindBy(how = How.ID, using = "primaryContactLastName")
	private WebElement primaryContactLastName;

	@FindBy(how = How.ID, using = "primaryContactEmail")
	private WebElement primaryContactEmail;

	@FindBy(how = How.ID, using = "primaryContactPhoneNumber")
	private WebElement primaryContactPhoneNumber;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Customer Contact ']")
	private WebElement editCustomerContactButton;

//Merchant Address

	@FindBy(how = How.ID, using = "country")
	private WebElement country;

	@FindBy(how = How.ID, using = "zip")
	private WebElement zip;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Address ']")
	private WebElement editAddressButton;

	// Remit-to Address-
	@FindBy(how = How.ID, using = "remitZip")
	private WebElement remitZip;

	@FindBy(how = How.ID, using = "remitCountry")
	private WebElement remitCountry;

	// Legal Entity-

	@FindBy(how = How.ID, using = "merchantLegalName")
	private WebElement merchantLegalName;

	@FindBy(how = How.ID, using = "legalEntityPhoneNumber")
	private WebElement legalEntityPhoneNumber;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Legal Entity ']")
	private WebElement editLegalEntityButton;

	@FindBy(how = How.XPATH, using = "//a[text()='Edit Beneficial Owner 1']")
	private WebElement editBeneficialOwnerButton;

	// Accepted cards
	@FindBy(how = How.XPATH, using = "//div[@data-ng-repeat='card in acceptedCards']/img[@alt='Visa']")
	private WebElement visaCard;

	@FindBy(how = How.XPATH, using = "//div[@data-ng-repeat='card in acceptedCards']/img[@alt='Mastercard']")
	private WebElement masterCard;

	@FindBy(how = How.XPATH, using = "//div[@data-ng-repeat='card in acceptedCards']/img[@alt='Discover']")
	private WebElement discoverCard;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Accepted Cards ']")
	private WebElement editAcceptedCardsButton;

	// Account Details

	@FindBy(how = How.ID, using = "accountType")
	private WebElement accountType;

	@FindBy(how = How.ID, using = "amexSid")
	private WebElement amexSid;

	@FindBy(how = How.ID, using = "routingNumber")
	private WebElement routingNumber;

	@FindBy(how = How.ID, using = "accountNumber")
	private WebElement accountNumber;

	@FindBy(how = How.ID, using = "federalTaxId")
	private WebElement federalTaxId;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Account Details ']")
	private WebElement editAccountDetailsButton;

	// Beneficial owner 1

	@FindBy(how = How.ID, using = "beneficialOwnerFirstName")
	private WebElement beneficialOwnerFirstName;

	@FindBy(how = How.ID, using = "beneficialOwnerLastName")
	private WebElement beneficialOwnerLastName;

	@FindBy(how = How.ID, using = "beneficialOwnerPercent")
	private WebElement beneficialOwnerPercent;

	@FindBy(how = How.ID, using = "beneficialOwnerTitle")
	private WebElement beneficialOwnerTitle;

	@FindBy(how = How.ID, using = "beneficialOwnerSSN")
	private WebElement beneficialOwnerSSN;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Beneficial Owner 1 ']")
	private WebElement editBenificialOwnerButton;

	// eStatement Options
	@FindBy(how = How.ID, using = "merchantTagLine")
	private WebElement merchantTagLine;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Statement Options ']")
	private WebElement editStatementOptionsButton;

	// Users and Roles - need to try

	@FindAll({ @FindBy(how = How.XPATH, using = "//span[@data-ng-repeat='role in one.roles']") })
	private List<WebElement> userRolesList;

	// Rates and Contract Information

	@FindBy(how = How.ID, using = "platformFeeAuth")
	private WebElement platformFeeAuth;

	@FindBy(how = How.ID, using = "platformFeeRefund")
	private WebElement platformFeeRefund;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Rates and Contract Information ']")
	private WebElement editRatesContractButton;

	// Percentage Fee Tiers
	@FindBy(how = How.XPATH, using = "//span[@class='ng-binding'][text()='Daily']")
	private WebElement settlement;

	@FindBy(how = How.XPATH, using = "//a[text()=' Collect Chargeback ']")
	private WebElement chargeBackButton;

	@FindBy(how = How.XPATH, using = "//a[text()=' Edit Fraud/Risk Variables ']")
	private WebElement editFraudButton;

	public void verifyPageTitle() {

		String title = this.driver.getTitle();

	}

	public void verifyProcessorInformation() {

		Assert.assertEquals(processor.getText(), testData.getProperty("merchant.processor.get"));
		Assert.assertEquals(mmidInternal.getText(), testData.getProperty("merchant.mmid.internal.get"));
		Assert.assertEquals(merchantIdExternal.getText(), testData.getProperty("merchant.merchant.external.id.get"));
		Assert.assertEquals(vantivIBMMID.getText(), testData.getProperty("merchant.vantiv.ibmmid.get"));
		Assert.assertEquals(subMerchantId.getText(), testData.getProperty("merchant.sub.merchant.id.get"));
		Assert.assertEquals(elementAccountId.getText(), testData.getProperty("merchant.element.accountId.get"));
		Assert.assertEquals(elementAcceptorId.getText(), testData.getProperty("merchant.element.acceptor.id.get"));

	}

	public void verifyGeneralMerchantInformation() {

		Assert.assertEquals(merchantName.getText(), testData.getProperty("merchant.merchant.name.get"));
		Assert.assertEquals(practiceID.getText(), testData.getProperty("merchant.practice.id.get"));
		Assert.assertEquals(customerAccountNumber.getText(),
				testData.getProperty("merchant.customer.account.number.get"));
		Assert.assertEquals(merchantPhone.getText(), testData.getProperty("merchant.phone.get"));
		Assert.assertEquals(txLimit.getText(), testData.getProperty("merchant.tx.limit.get"));
		Assert.assertEquals(billingDescriptor.getText(), testData.getProperty("merchant.billing.descriptor.get"));
		Assert.assertEquals(ownershipType.getText(), testData.getProperty("merchant.ownership.type.get"));

		Assert.assertEquals(businessType.getText(), testData.getProperty("merchant.business.type.get"));
		Assert.assertEquals(businessEstablishedDate.getText(),
				testData.getProperty("merchant.business.established.date.get"));
		Assert.assertEquals(sicMccCode.getText(), testData.getProperty("merchant.sic.mcc.code.get"));
		Assert.assertEquals(payApiCustomer.getText(), testData.getProperty("merchant.pay.api.customer.get"));
		Assert.assertTrue(editGeneralMerchantInfoButton.isDisplayed());

	}

	public void verifyCustomerContact() {

		Assert.assertEquals(primaryContactFirstName.getText(),
				testData.getProperty("merchangt.primary.contact.firstname.get"));
		Assert.assertEquals(primaryContactLastName.getText(),
				testData.getProperty("merchangt.primary.contact.lastname.get"));
		Assert.assertEquals(primaryContactEmail.getText(), testData.getProperty("merchangt.primary.contact.email.get"));
		Assert.assertEquals(primaryContactPhoneNumber.getText(),
				testData.getProperty("merchangt.primary.contact.phonenumber.get"));
		Assert.assertTrue(editCustomerContactButton.isDisplayed());
	}

	public void verifyMerchantAddress() {
		Assert.assertEquals(remitZip.getText(), testData.getProperty("merchant.remitzip.get"));
		Assert.assertEquals(remitCountry.getText(), testData.getProperty("merchant.remitcountry.get"));
	}

	public void verifyRemitToAddress() {
		Assert.assertEquals(country.getText(), testData.getProperty("merchant.address.country.get"));
		Assert.assertEquals(zip.getText(), testData.getProperty("merchant.address.zip.get"));
		Assert.assertTrue(editAddressButton.isDisplayed());

	}

	public void verifyLegalEntity() {
		Assert.assertEquals(merchantLegalName.getText(), testData.getProperty("merchant.legalname.get"));
		Assert.assertEquals(legalEntityPhoneNumber.getText(), testData.getProperty("merchant.legalphnumber.get"));
		Assert.assertTrue(editLegalEntityButton.isDisplayed());

	}

	public void verifyAcceptedCards() {

		Assert.assertTrue(visaCard.isDisplayed());
		Assert.assertTrue(masterCard.isDisplayed());
		Assert.assertTrue(discoverCard.isDisplayed());
		Assert.assertTrue(editAcceptedCardsButton.isDisplayed());

	}

	public void verifyAccountDetails() {

		Assert.assertEquals(accountType.getText(), testData.getProperty("merchant.account.type.get"));
		Assert.assertEquals(amexSid.getText(), testData.getProperty("merchant.checking.deposit.type.get"));
		Assert.assertEquals(routingNumber.getText(), testData.getProperty("merchant.routing.number.get"));
		Assert.assertEquals(accountNumber.getText(), testData.getProperty("merchant.account.number.get"));
		Assert.assertEquals(federalTaxId.getText(), testData.getProperty("merchant.federal.tax.id.get"));

		Assert.assertTrue(editAccountDetailsButton.isDisplayed());

	}

	public void verifyStatementOption() {

		Assert.assertEquals(merchantTagLine.getText(), testData.getProperty("merchant.tag.line.get"));
		Assert.assertTrue(editStatementOptionsButton.isDisplayed());

	}

	public void verifyRatesContractInformation() {

		Assert.assertEquals(platformFeeAuth.getText(), testData.getProperty("merchant.platform.fee.auth.get"));
		Assert.assertEquals(platformFeeRefund.getText(), testData.getProperty("merchant.platform.fee.refund.get"));
		Assert.assertTrue(editRatesContractButton.isDisplayed());

	}

	public void verifyPercentageFeeTiers() {

		Assert.assertEquals(settlement.getText(), testData.getProperty("merchant.percent.fee.settelement.get"));

	}

	public void verifyChargeback() {

		Assert.assertTrue(chargeBackButton.isDisplayed());
	}

	public void verifyFraudRiskVariable() {

		Assert.assertTrue(editFraudButton.isDisplayed());

	}

	public void verifyUserRoles() {

		for (WebElement userRoleElement : userRolesList) {
			String userRole = userRoleElement.getText().replace(",", "");

			if (userRoles.contains(userRole)) {

				Assert.assertTrue(userRoles.contains(userRole));

			}
		}

	}

	public void verifyBeneficialOwners() {

		Assert.assertEquals(beneficialOwnerFirstName.getText(),
				testData.getProperty("merchant.beneficial.owner.firstname.get"));
		Assert.assertEquals(beneficialOwnerLastName.getText(),
				testData.getProperty("merchant.beneficial.owner.lastname.get"));
		Assert.assertEquals(beneficialOwnerPercent.getText(),
				testData.getProperty("merchant.beneficial.owner.percent.get"));
		Assert.assertEquals(beneficialOwnerTitle.getText(),
				testData.getProperty("merchant.beneficial.owner.title.get"));
		Assert.assertEquals(beneficialOwnerSSN.getText(), testData.getProperty("merchant.beneficial.owner.ssn.get"));

		Assert.assertTrue(editBenificialOwnerButton.isDisplayed());

	}

}
