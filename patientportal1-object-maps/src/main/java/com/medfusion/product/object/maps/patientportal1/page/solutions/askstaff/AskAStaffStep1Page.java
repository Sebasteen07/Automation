package com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class AskAStaffStep1Page extends BasePageObject{
	
	public static final String PAGE_NAME = "Ask A Staff Page - Step 1";
	
	@FindBy(name="locationWrapper:_body:location")
	private WebElement location; // Select Box
	
	@FindBy(name="providerWrapper:_body:provider")
	private WebElement staff; // Select Box
	
	@FindBy(name="subjectWrapper:_body:subject")
	private WebElement subject;
	
	@FindBy(name="questionWrapper:_body:question")
	private WebElement question;
	
	@FindBy(name=":submit")
	private WebElement btnContinue;
	
	@FindBy(name="agreeRadioGroupWrapper:_body:agreeRadioGroup")
	private List<WebElement> waiverAgreementChoices;
	
	private long createdTS;

	public AskAStaffStep1Page(WebDriver driver) {
		super(driver);
		
		createdTS = System.currentTimeMillis();
	}
	
	/**
	 * Returns the created timestamp which is appended to the Subject content
	 * 
	 * @return long
	 */
	public long getCreatedTimeStamp() {
		return createdTS;
	}
	
	/**
	 * Fill out an ask a staff question with subject and question.
	 * 
	 * @param subject
	 * @param question
	 * @throws Exception 
	 */
	public AskAStaffStep2Page askQuestion(String subjectContent, String questionContent) throws Exception {
		IHGUtil.PrintMethodName();
		return askQuestion(subjectContent, questionContent, null, null);
	}
	
	/**
	 * Fill out an ask a staff question with subject and question.
	 * 
	 * @param subject
	 * @param question
	 * @param location (visible text)
	 * @param staff (visible text)
	 * 
	 * @return AskAStaffStep2Page
	 * @throws Exception 
	 */
	public AskAStaffStep2Page askQuestion(String subjectContent, 
											String questionContent, 
											String locationSelection, 
											String staffSelection) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		if (subjectContent == null || questionContent == null) {
			throw new Exception("The content for the subject or question field was null");
			
		}
		
		// Select location if available
		if (locationSelection != null) {
			Select locationSelect = new Select(location);
			locationSelect.selectByVisibleText(locationSelection);
		}
		
		// Select staff if available
		if (staffSelection != null) {
			Select staffSelect = new Select(staff);
			staffSelect.selectByVisibleText(staffSelection);
		}
		
		// Populate inputs with supplied content
		// Add the created time stamp to enable searching on unique value
		subject.sendKeys(subjectContent + " - " + createdTS);
		question.sendKeys(questionContent);
		
		// Check if there is a waiver agreement that needs to be accepted
		try {
			if (waiverAgreementChoices.get(0).isDisplayed()) {
				if (!waiverAgreementChoices.get(0).getAttribute("onclick").contains("donotagre")) {
					waiverAgreementChoices.get(1).click();
				} else {
					waiverAgreementChoices.get(0).click();
				}
			}
		} catch (Exception e) {
			// The liability waiver wasn't displayed to user
		}
		
		// Click button to continue
		btnContinue.click();
		return PageFactory.initElements(driver, AskAStaffStep2Page.class);		
	}

}
