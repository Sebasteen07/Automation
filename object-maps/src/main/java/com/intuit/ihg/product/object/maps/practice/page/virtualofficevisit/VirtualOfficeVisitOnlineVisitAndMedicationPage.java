package com.intuit.ihg.product.object.maps.practice.page.virtualofficevisit;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author rperkinsjr
 *
 */
public class VirtualOfficeVisitOnlineVisitAndMedicationPage extends BasePageObject {

	public static final String PAGE_NAME = "Virtual Office Visit Online Visit and Prescribe Medication Page";

	private static final String MESSAGE_BODY = "This prescription was generated from an automated test";
	
	@FindBy(name="rx1")
	private WebElement prescriptionBox;
	
	// Prescription fields -- Start
	@FindBy(name="medication_1")
	private WebElement drugName;
	
	@FindBy(name="dosage_1")
	private WebElement dosage;
	
	@FindBy(name="units_1")
	private WebElement units;	
	
	@FindBy(name="quantity_1")
	private WebElement quantity;
	
	@FindBy(name="sigcode_1")
	private WebElement frequency;
	
	@FindBy(xpath=".//a[text()='Add a Frequency']")
	private List<WebElement> addAFrequencyLink;
	
	@FindBy(name="sigabbr_1")
	private WebElement frequencyAbbreviation;
	private static final String FREQUENCY_ABBREVIATION_CONTENT = "DAILY";
	
	@FindBy(name="sigdesc_1")
	private WebElement frequencyDescription;
	private static final String FREQUENCY_DESCRIPTION_CONTENT = "Once per day";
	
	@FindBy(xpath=".//input[@value='Add a Frequency (SIG CODE)']")
	private WebElement btnAddFrequency;
	// Prescription fields -- End
	
	// Communication fields -- Start
	@FindBy(name="subject")
	private WebElement subject;
	
	@FindBy(name="body")
	private WebElement body;
	
	@FindBy(name="noreply")
	private WebElement noPatientReply;
	// Communication fields -- End
	
	// Diagnostic fields -- Start
	@FindBy(name="diagsearch")
	private WebElement diagnosticCode;
	// Ajax search starts after 3 letters, and again searches after each additional letter
	// Setting this to 3 letters as when you add more it causes issues as each new search result
	// unloads the result table from the DOM and adds it again, which causes issues with the script.
	private static final String DIAGNOSTIC_CODE_CONTENT = "COU"; 
	
	@FindBy(xpath=".//*[@id='table-1']/tbody/tr[1]/td[2]")
	private WebElement diagnosticResultLink;
	// Diagnostic fields -- End
	
	@FindBy(name="callorfax")
	private List<WebElement> callOrFaxOptions;
	
	@FindBy(xpath=".//input[@value='Process and communicate']")
	private WebElement btnProcessAndCommunicate;
	
	private long createdTs;
	
	public VirtualOfficeVisitOnlineVisitAndMedicationPage(WebDriver driver) {
		super(driver);
		
		createdTs = System.currentTimeMillis();
	}
	
	
	/**
	 * Will return the timestamp appended to the subject of the communication.
	 * Useful for searching for the secure message in the Inbox.
	 * @return timestamp as a string
	 */
	public String getCreatedTs() {
		return Long.toString(createdTs);
	}
	
	
	/**
	 * Gives an indication if the page loaded correctly
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = prescriptionBox.isDisplayed();			
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	public VirtualOfficeVisitPracticeConfirmationPage completePrescriptionAndCommunication(String subjectContent) {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		prescriptionBox.click();
		fillOutPrescription();
		fillOutDiagnosticCode();
		fillOutMessage(subjectContent);
		chooseCallOrFaxOption(VirtualOfficeVisitCallOrFaxOption.SEND_TO_CALLIN_QUEUE);
		btnProcessAndCommunicate.click();
		
		return PageFactory.initElements(driver, VirtualOfficeVisitPracticeConfirmationPage.class);
	}
	
	private void fillOutDiagnosticCode() {
		IHGUtil.PrintMethodName();
		
		diagnosticCode.sendKeys(DIAGNOSTIC_CODE_CONTENT);
		IHGUtil.waitForElement(driver,20, diagnosticResultLink);
		diagnosticResultLink.click();
	}


	private void fillOutPrescription() {
		IHGUtil.PrintMethodName();
		
		drugName.sendKeys("Tylenol");
		dosage.sendKeys("100");
		// Leave unit at default
		quantity.sendKeys("50");
		setFrequency(FREQUENCY_ABBREVIATION_CONTENT);
	}
	
	private void setFrequency(String frequencyText) {
		IHGUtil.PrintMethodName();
		
		Select frequencySelect = new Select(frequency);
		try {
			// If an item is selected, just use that
			frequencySelect.getFirstSelectedOption();
		} catch (Exception e) {
			// No items selected so create one.
			createDailyFrequencyOption();
		}
	}
	
	private void createDailyFrequencyOption() {
		IHGUtil.PrintMethodName();
		
		addAFrequencyLink.get(1).click(); // There are 2 Add a Frequency links on the page, we need the second one
		frequencyAbbreviation.sendKeys(FREQUENCY_ABBREVIATION_CONTENT);
		frequencyDescription.sendKeys(FREQUENCY_DESCRIPTION_CONTENT);
		btnAddFrequency.click();
	}

	
	private void fillOutMessage(String subjectContent) {
		IHGUtil.PrintMethodName();
		
		subject.sendKeys(subjectContent + " " + createdTs);
		body.sendKeys(MESSAGE_BODY);
	}
	
	private void chooseCallOrFaxOption(String optionValue) {
		IHGUtil.PrintMethodName();
		
		if (optionValue == null || optionValue.isEmpty()) {
			optionValue = "1";
		}

		for (WebElement option : callOrFaxOptions) {
			if (option.getAttribute("value").equalsIgnoreCase(optionValue)) {
				option.click();
			}
		}
	}

}
