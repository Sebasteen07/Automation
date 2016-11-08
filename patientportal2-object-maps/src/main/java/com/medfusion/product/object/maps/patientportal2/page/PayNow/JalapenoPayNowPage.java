package com.medfusion.product.object.maps.patientportal2.page.PayNow;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.patientportal2.pojo.PayNowInfo;

public class JalapenoPayNowPage extends IHGUtil {

	@FindBy(name = "cardNameWrapper:_body:cardName")
	private WebElement nameOnCardInput;

	@FindBy(name = "sameAsPatientWrapper:_body:sameAsPatient")
	private WebElement sameAsPatientCheckbox;

	@FindBy(name = "patientNameWrapper:_body:patientName")
	private WebElement patientNameInput;

	@FindBy(name = "birthdayWrapper:_body:birthday")
	private WebElement dateOfBirthInput;

	@FindBy(name = "accountNumWrapper:_body:accountNum")
	private WebElement patientAccountNumberInput;

	@FindBy(name = "cardTypeWrapper:_body:cardType")
	private WebElement cardTypeSelect;

	@FindBy(name = "cardNumberWrapper:_body:cardNumber")
	private WebElement cardNumberInput;

	@FindBy(name = "cardExpireWrapper:_body:cardExpire:month")
	private WebElement cardExpireMonthSelect;

	@FindBy(name = "cardExpireWrapper:_body:cardExpire:year")
	private WebElement cardExpireYearSelect;

	@FindBy(name = "amountWrapper:_body:amount")
	private WebElement amountInput;

	@FindBy(name = "cvvCodeWrapper:_body:cvvCode")
	private WebElement cvvCodeInput;

	@FindBy(name = "cardZipWrapper:_body:cardZip")
	private WebElement cardZipInput;

	@FindBy(name = "locationWrapper:_body:location")
	private WebElement locationSelect;

	@FindBy(name = "commentWrapper:_body:comment")
	private WebElement paymentCommentInput;

	@FindBy(name = "emailAddrWrapper:_body:emailAddr")
	private WebElement emailAddressInput;

	@FindBy(name = "buttons:submit")
	private WebElement submitButton;

	public JalapenoPayNowPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
		IHGUtil.setFrame(driver, "iframebody");
	}

	public void fillInputs(WebDriver driver, PayNowInfo payInfo) throws Exception {
		IHGUtil.PrintMethodName();

		CreditCard card = payInfo.getCard();
		nameOnCardInput.sendKeys(card.getName());
		patientNameInput.sendKeys(payInfo.getPatientName());
		dateOfBirthInput.sendKeys(payInfo.getDateOfBirth());
		patientAccountNumberInput.sendKeys(payInfo.getPatientAccountNumber());
		selectCardType(card.getType());
		cardNumberInput.sendKeys(card.getCardNumber());
		selectMonth(card.getExpMonth());
		selectYear(card.getExpYear());
		amountInput.sendKeys(payInfo.getAmount());
		cvvCodeInput.sendKeys(card.getCvvCode());
		cardZipInput.sendKeys(card.getZipCode());
		selectLocation(payInfo.getLocation());
		paymentCommentInput.sendKeys(payInfo.getPaymentComment());
		emailAddressInput.sendKeys(payInfo.getEmailAddress());
	}

	public JalapenoPayNowCompletePage processPayment() {
		IHGUtil.PrintMethodName();

		submitButton.click();
		return PageFactory.initElements(driver, JalapenoPayNowCompletePage.class);
	}

	public boolean assessPayNowPageElements() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(nameOnCardInput);
		webElementsList.add(patientNameInput);
		webElementsList.add(cardTypeSelect);
		webElementsList.add(dateOfBirthInput);
		webElementsList.add(patientAccountNumberInput);
		webElementsList.add(cardNumberInput);
		webElementsList.add(cardExpireMonthSelect);
		webElementsList.add(cardExpireYearSelect);
		webElementsList.add(amountInput);
		webElementsList.add(cvvCodeInput);
		webElementsList.add(cardZipInput);
		webElementsList.add(locationSelect);
		webElementsList.add(paymentCommentInput);
		webElementsList.add(emailAddressInput);
		webElementsList.add(submitButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	private void selectCardType(CardType type) {
		Select selectCardType = new Select(cardTypeSelect);
		switch (type) {
			case Visa:
				selectCardType.selectByValue("1");
			case Mastercard:
				selectCardType.selectByValue("2");
			case Discover:
				selectCardType.selectByValue("10");
			case Amex:
				selectCardType.selectByValue("3");
		}
	}

	private void selectMonth(String month) {
		if (month.substring(0, 1).equals("0")) {
			month = (month.substring(1));
		}
		Select selectMonthExpiration = new Select(cardExpireMonthSelect);
		selectMonthExpiration.selectByVisibleText(month);
	}

	private void selectYear(String year) {
		Select selectYearExpiration = new Select(cardExpireYearSelect);
		selectYearExpiration.selectByVisibleText(year);
	}

	private void selectLocation(String location) {
		Select selectLocation = new Select(locationSelect);
		if (location == null) {
			selectLocation.selectByIndex(1);
		} else {
			selectLocation.selectByVisibleText(location);
		}
	}
}
