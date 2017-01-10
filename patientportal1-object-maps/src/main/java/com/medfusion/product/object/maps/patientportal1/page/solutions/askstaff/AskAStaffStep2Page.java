package com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class AskAStaffStep2Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Page - Step 2";

	@FindBy(name = "buttons:submit")
	private WebElement btnSubmit;

	@FindBy(name = "ccpanel:newccdetails:nameOnCreditCard")
	private WebElement cardHolderName;

	@FindBy(name = "ccpanel:newccdetails:creditCardNumber")
	private WebElement creditCardNumber;

	@FindBy(name = "ccpanel:newccdetails:creditCardType")
	private WebElement creditCardType;

	@FindBy(name = "ccpanel:newccdetails:expirationMonth")
	private WebElement expirationDateMonth;

	@FindBy(name = "ccpanel:newccdetails:expirationYear")
	private WebElement expirationDateYear;
	@FindBy(name = "ccpanel:existingccdetailscontainer:ccRadioGroup:existingccdetails:existingCvvCode")
	private WebElement cvvCodeExistingCard;
	@FindBy(name = "ccpanel:newccdetails:newccdetailscvv:cvvCode")
	private WebElement cvvCode;

	@FindBy(name = "ccpanel:newccdetails:addressZip")
	private WebElement zipCode;

	@FindBy(linkText = "Edit")
	private WebElement lnkEditCreditCard;

	public AskAStaffStep2Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Review Ask A Staff details, including payment details when fee's are turned on, and finally submit the question.
	 * 
	 * @return AskAStaffStep3Page
	 */
	public AskAStaffStep3Page submitQuestion() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		if (!(IHGUtil.waitForElement(driver, 2, lnkEditCreditCard))) {
			payWithNewCreditCard();
		} else {
			if (IHGUtil.waitForElement(driver, 2, cvvCodeExistingCard)) {
				cvvCodeExistingCard.sendKeys("123");
			}
		}
		btnSubmit.click();
		return PageFactory.initElements(driver, AskAStaffStep3Page.class);
	}

	/**
	 * Submit unpaid question.
	 * 
	 * @return AskAStaffStep3Page
	 */
	public AskAStaffStep3Page submitUnpaidQuestion() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		btnSubmit.click();
		return PageFactory.initElements(driver, AskAStaffStep3Page.class);
	}

	/**
	 * May need to break this out to its own class if this is the same across all solutions that charge a fee (maybe include the ability to indicate which card)
	 * 
	 * Will add a credit card.
	 */
	private void payWithNewCreditCard() {
		IHGUtil.PrintMethodName();

		cardHolderName.sendKeys("Automation Patient");
		creditCardNumber.sendKeys("4111111111111111");

		Select ccType = new Select(creditCardType);
		ccType.selectByVisibleText("Visa");

		Select expireMonth = new Select(expirationDateMonth);
		expireMonth.selectByVisibleText("December");

		// Randomly select year from choices
		Select expireYear = new Select(expirationDateYear);
		Random rand = new Random();
		int choice = rand.nextInt(11) + 1;
		expireYear.selectByIndex(choice);

		cvvCode.sendKeys("123");
		zipCode.sendKeys("12345");
	}

}
