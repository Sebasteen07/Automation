package com.medfusion.product.object.maps.patientportal1.page.makePaymentpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class MakePaymentPage extends BasePageObject {

	public MakePaymentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	@FindBy(xpath = "//input[@name='accountNumberWrapper:_body:accountNumber']")
	private WebElement patientAccountNumber;

	@FindBy(xpath = "//input[@name='paymentAmountWrapper:_body:paymentAmount']")
	private WebElement paymentAmount;

	@FindBy(xpath = "//input[@name='paymentCommentWrapper:_body:paymentComment']")
	private WebElement paymentComment;

	@FindBy(xpath = "//select[@name='availCreditCardsWrapper:_body:availCreditCards']")
	private WebElement creditCard;

	@FindBy(name = ":submit")
	private WebElement clickContinuebtn;

	@FindBy(name = "submitButton")
	private WebElement clickSubmitbtn;

	@FindBy(xpath = "//form[@id='form']/table//div[@class='heading1']")
	private WebElement paymentConfirmation;

	@FindBy(xpath = "//input[@name='wrapper:cardName']")
	private WebElement nameOnCrediCard;

	@FindBy(xpath = "//input[@name='wrapper:cardNumber']")
	private WebElement crediCardNumber;

	@FindBy(xpath = "//select[@name='wrapper:type']")
	private WebElement creditCardType;

	@FindBy(xpath = "//select[@name='wrapper:cardExpire:month']")
	private WebElement expirationMonth;

	@FindBy(xpath = "//select[@name='wrapper:cardExpire:year']")
	private WebElement expirationYear;

	@FindBy(xpath = "//input[@name='wrapper:cardCVVWrapper:_body:cardCVV']")
	private WebElement ccvCode;

	@FindBy(xpath = "//input[@name='wrapper:cardAddress1']")
	private WebElement billingAddressLine1;

	@FindBy(xpath = "//input[@name='wrapper:cardAddress2']")
	private WebElement billingAddressLine2;

	@FindBy(xpath = "//input[@name='wrapper:cardCity']")
	private WebElement cardCity;

	@FindBy(xpath = "//select[@name='wrapper:Billing address state']")
	private WebElement billingaddressState;

	@FindBy(xpath = "//input[@name='wrapper:cardZip']")
	private WebElement billingaddressZipCode;

	@FindBy(xpath = "//input[@class='button' and @value='Submit Payment']")
	private WebElement clickOnSubmitbtn;

	@FindBy(xpath = ".//td[@class='table_text']/span")
	private WebElement txtConfirmationNumber;

	/**
	 * @Description:Set Make Payment Fields
	 */

	public void setMakePaymentFields(String accountNumber) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		patientAccountNumber.clear();
		if (accountNumber != null) {
			patientAccountNumber.sendKeys(accountNumber);
		} else {
			patientAccountNumber.sendKeys(PortalConstants.PatientAccountNumber);
		}
		paymentAmount.clear();
		paymentAmount.sendKeys(PortalConstants.PaymentAmount);
		try {
			paymentComment.clear();
			paymentComment.sendKeys(PortalConstants.PaymentComment);
		} catch (Exception e) {
			log("Payment Comment Field is not Displayed");
		}
		Select selectcreditCard = new Select(creditCard);
		try {
			selectcreditCard.selectByVisibleText("Test Card 111");
		} catch (Exception e) {
			selectcreditCard.selectByVisibleText(PortalConstants.NewCardType);
			IHGUtil.waitForElement(driver, 40, nameOnCrediCard);
			nameOnCrediCard.sendKeys("Test Card");
			crediCardNumber.sendKeys("4111111111111111");
			Select selectcreditCardType = new Select(creditCardType);
			selectcreditCardType.selectByVisibleText("Visa");
			Select selectExpirationMonth = new Select(expirationMonth);
			selectExpirationMonth.selectByVisibleText("12");
			Select selectExpirationYear = new Select(expirationYear);
			selectExpirationYear.selectByVisibleText("2022");
			ccvCode.sendKeys("111");
			billingAddressLine1.sendKeys("123 Main Street");
			billingAddressLine2.sendKeys("Unit 1");
			cardCity.sendKeys("Mountain View");
			Select selectState = new Select(billingaddressState);
			selectState.selectByVisibleText("California");
			billingaddressZipCode.sendKeys("94043");
		}
		clickContinuebtn.click();
		// This is temporary fix for Dev3 and need to be made generic after element value change is available in Demo and Production after 15.1 deployment
		if (IHGUtil.getEnvironmentType().toString().equalsIgnoreCase("DEV3")) {
			IHGUtil.waitForElement(driver, 10, clickOnSubmitbtn);
			clickOnSubmitbtn.click();
		} else {
			IHGUtil.waitForElement(driver, 10, clickSubmitbtn);
			clickSubmitbtn.click();
		}
		IHGUtil.waitForElement(driver, 10, paymentConfirmation);
		BaseTestSoftAssert.verifyEquals(paymentConfirmation.getText(), PortalConstants.PaymentConfirmation);
	}


	/**
	 * 
	 * @return
	 */
	public String readConfirmationNumber() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		String confirmationNumber = txtConfirmationNumber.getText().toString();
		return confirmationNumber.substring(confirmationNumber.indexOf("confirmation number is ") + "confirmation number is ".length(),
				confirmationNumber.indexOf(". Please retain"));
	}

}
