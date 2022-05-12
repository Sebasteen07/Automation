// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.askstaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class AskAStaffQuestionDetailStep2Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Question Detail Step 2 Page";

	private long createdTs;

	@FindBy(linkText = "Patient Intake")
	private WebElement patientIntakeTab;

	@FindBy(linkText = "Go Back To Search Page")
	private WebElement goBackToSearchPage;

	@FindBy(name = "rx1:container:table:drugName")
	private WebElement drugName;

	@FindBy(name = "rx1:container:table:dosage")
	private WebElement dosage;

	@FindBy(name = "rx1:container:table:quantity")
	private WebElement quantity;

	@FindBy(name = "rx1:container:table:frequency")
	private WebElement frequency;

	@FindBy(name = "pharmacy:container:pharmacy")
	private WebElement pharmacy;

	@FindBy(xpath = "//input[@name='pharmacy:container:action' and @value='1']")
	private WebElement sendToTheCallinQueue;

	@FindBy(name = "commpanel:from")
	private WebElement from;

	@FindBy(name = "commpanel:subject")
	private WebElement subject;

	@FindBy(name = "commpanel:body")
	private WebElement body;

	@FindBy(name = "commpanel:denyReply")
	private WebElement denyReply;

	@FindBy(name = "cpt:topContainer:code")
	private WebElement diagnosticCode;
	// Ajax search starts after 3 letters, and again searches after each additional
	// letter
	// Setting this to 3 letters as when you add more it causes issues as each new
	// search result
	// unloads the result table from the DOM and adds it again, which causes issues
	// with the script.
	private final String diagnosticCodeContent = "COU";
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Cough')]")
	private WebElement diagnosticContentButton;

	@FindBy(name = "buttons:submit")
	private WebElement btnProcess;

	@FindBy(name = "buttons:cancel")
	private WebElement btnCancel;
	
	@FindBy(linkText = "Click here")
	private WebElement clickhereToChargeCard;
	
	@FindBy(name = "payment:container:amount")
	private WebElement inputAmountText;
	

	public AskAStaffQuestionDetailStep2Page(WebDriver driver) {
		super(driver);
		createdTs = System.currentTimeMillis();
	}

	/**
	 * Gives indication if the Ask A Staff Question Detail page loaded.
	 * 
	 * @return true or false
	 */
	public boolean isQuestionDetailPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		boolean result = false;
		try {
			result = patientIntakeTab.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * Will click the 'Go Back To Search Page' link.
	 * 
	 * @return the Ask A Staff search page
	 */
	public AskAStaffSearchPage clickGoBackToSearchPage() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		goBackToSearchPage.click();
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}

	/**
	 * Returns unique value that was added to subject to help with filtering in
	 * patient inbox
	 * 
	 * @return the created time stamp
	 */
	public long getCreatedTimeStamp() {
		IHGUtil.PrintMethodName();

		return createdTs;
	}

	/**
	 * Process the question. Note: The diagnostic code is handled automatically and
	 * will be set to "COUGH". The first item in the diagnostic code table that pops
	 * up will be selected.
	 * 
	 * @param subjectContent content to be put in reply subject to patient (a unique
	 *                       time stamp will be appended by this method and
	 *                       accessible via getCreatedTimeStamp())
	 * @param bodyContent    content to be put in reply body to patient
	 * 
	 * @return Ask A Staff Question Detail Step 3 page 
	 */
	public AskAStaffQuestionDetailStep3Page processAndCommunicate(String subjectContent, String bodyContent) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		if (IHGUtil.exists(driver, from)) {
			Select selectFrom = new Select(from);
			selectFrom.selectByIndex(1);
		}
		subject.sendKeys(subjectContent + " " + createdTs);
		body.sendKeys(bodyContent);
		diagnosticCode.sendKeys(diagnosticCodeContent);
		IHGUtil.waitForElement(driver, 30, diagnosticContentButton);
		diagnosticContentButton.click();
		btnProcess.click();
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep3Page.class);
	}

	/**
	 * Fills out the Prescription, Pharmacy and the rest is handled by the
	 * processAndCommunicate method Units stay default (capsule), Refills 0 and Do
	 * not fill after = today 
	 */
	public AskAStaffQuestionDetailStep3Page prescribeAndCommunicate(String subjectContent, String bodyContent) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 20, drugName);
		drugName.sendKeys(PracticeConstants.MEDICATION_NAME);
		dosage.sendKeys(JalapenoConstants.DOSAGE);
		quantity.sendKeys(PracticeConstants.QUANTITY);
		Select selFrequency = new Select(frequency);
		selFrequency.selectByVisibleText(PracticeConstants.FREQUENCY);
		if (IHGUtil.exists(driver, 10, pharmacy)) {
			Select selPharmacy = new Select(pharmacy);
			selPharmacy.selectByIndex(1);
		}
		IHGUtil.waitForElement(driver, 20, sendToTheCallinQueue);
		sendToTheCallinQueue.click();
		return processAndCommunicate(subjectContent, bodyContent);
	}
	
	public AskAStaffQuestionDetailStep3Page chargeAmountAndCommunicate(String subjectContent, String bodyContent, String amount) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		if (IHGUtil.exists(driver, from)) {
			Select selectFrom = new Select(from);
			selectFrom.selectByIndex(1);
		}
		subject.sendKeys(subjectContent + " " + createdTs);
		body.sendKeys(bodyContent);
		diagnosticCode.sendKeys(diagnosticCodeContent);
		IHGUtil.waitForElement(driver, 30, diagnosticContentButton);
		diagnosticContentButton.click();
		chargeCreditCard(amount);
		btnProcess.click();
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep3Page.class);
	}
	
	public void chargeCreditCard(String amount) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		clickhereToChargeCard.click();
		inputAmountText.clear();
		inputAmountText.sendKeys(amount);
		
	}
}
