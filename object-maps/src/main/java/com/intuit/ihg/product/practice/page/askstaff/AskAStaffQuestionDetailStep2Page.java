package com.intuit.ihg.product.practice.page.askstaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class AskAStaffQuestionDetailStep2Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Question Detail Step 2 Page";
	
	private long createdTs;
	
	@FindBy(linkText="Patient Intake")
	private WebElement patientIntakeTab;
	
	@FindBy(linkText="Go Back To Search Page")
	private WebElement goBackToSearchPage;
	
	@FindBy(name="commpanel:subject")
	private WebElement subject;
	
	@FindBy(name="commpanel:body")
	private WebElement body;
	
	@FindBy(name="commpanel:denyReply")
	private WebElement denyReply;
	
	@FindBy(name="cpt:topContainer:code")
	private WebElement diagnosticCode;
	// Ajax search starts after 3 letters, and again searches after each additional letter
	// Setting this to 3 letters as when you add more it causes issues as each new search result
	// unloads the result table from the DOM and adds it again, which causes issues with the script.
	private final String diagnosticCodeContent = "COU";
	
	@FindBy(xpath=".//table[@class='dataview']/tbody/tr[1]/td[2]")
	private WebElement diagnosticCodeResultTable;
	
	@FindBy(name="buttons:submit")
	private WebElement btnProcess;
	
	@FindBy(name="buttons:cancel")
	private WebElement btnCancel;
	
	public AskAStaffQuestionDetailStep2Page(WebDriver driver) {
		super(driver);
		createdTs = System.currentTimeMillis();
	}
	
	/**
	 * Gives indication if the Ask A Staff Question Detail page loaded.
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
	 * @return the Ask A Staff search page
	 */
	public AskAStaffSearchPage clickGoBackToSearchPage() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		goBackToSearchPage.click();
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}
	
	/**
	 * Returns unique value that was added to subject to help with filtering in patient inbox
	 * @return the created time stamp
	 */
	public long getCreatedTimeStamp() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		return createdTs;
	}
	
	/**
	 * Process the question. Note: The diagnostic code is handled automatically 
	 * and will be set to "COUGH". The first item in the diagnostic code table that pops up will be selected.
	 * 
	 * @param subjectContent content to be put in reply subject to patient 
	 * (a unique time stamp will be appended by this method and accessible via getCreatedTimeStamp())
	 * @param bodyContent content to be put in reply body to patient
	 * 
	 * @return Ask A Staff Question Detail Step 3 page
	 */
	public AskAStaffQuestionDetailStep3Page processAndCommunicate(String subjectContent, String bodyContent) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		subject.sendKeys(subjectContent + " " + createdTs);
		body.sendKeys(bodyContent);
		
		diagnosticCode.sendKeys(diagnosticCodeContent);
		IHGUtil.waitForElement(driver,20, diagnosticCodeResultTable);
		diagnosticCodeResultTable.click();
		
		btnProcess.click();
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep3Page.class);		
	}

}
