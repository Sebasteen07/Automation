package com.intuit.ihg.product.object.maps.practice.page.apptrequest;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class ApptRequestDetailStep1Page extends BasePageObject {

	public static final String PAGE_NAME = "Appt Request Detail Step 1 Page";
	
	@FindBy(linkText="Appointment History")
	private WebElement lnkApptHistory;
	
	@FindBy(className="feedbackpanelERROR")
	private WebElement errorPanel;
	
	@FindBy(name="processAppointment:inputFields:appointmentDate")
	private WebElement apptDate;
	
	@FindBy(name="processAppointment:inputFields:appointmentTime:hour")
	private WebElement apptHour;
	
	@FindBy(name="processAppointment:inputFields:appointmentTime:min")
	private WebElement apptMin;
	
	@FindBy(name="processAppointment:inputFields:appointmentTime:ampm")
	private WebElement apptAmPm;
	
	@FindBy(name="treatmentPlan:treatmentPlanChoices")
	private WebElement treatmentPlanOptions;
	
	@FindBy(name="message:subject")
	private WebElement subject;
	
	@FindBy(name="message:body")
	private WebElement body;
	
	@FindBy(name="message:doNotCommunicate")
	private WebElement doNotCommunicate;
	
	@FindBy(name="message:denyReply")
	private WebElement noReply;
	
	@FindBy(name="processAppointment:action")
	private List<WebElement> processOptions;
	
	
	@FindBy(xpath=".//input[@value='Submit Appointment Request']")
	private WebElement btnSubmitApptRequest;
	@FindBy(xpath = ".//table/tbody/tr[1]/td/table/tbody/tr[5]/td[2]/span")
	private WebElement messageSubject;
	
	@FindBy(xpath = ".//table/tbody/tr[1]/td/table/tbody/tr[6]/td/span/p")
	private WebElement messageBody;

	private long createdTs;
	
	public ApptRequestDetailStep1Page(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		createdTs = System.currentTimeMillis();
	}
	
	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}
	
	/**
	 * Gives indication if the Appt Request Detail page loaded.
	 * @return true or false
	 */
	public boolean isApptRequestDetailPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			if (lnkApptHistory.isDisplayed() && !errorPanel.isDisplayed()) {			
				result = true;
			}
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	/**
	 * Will process an appt request based upon the values in the ApptRequestEntity and move to the next page.
	 * @param entity use one of the other 'choose' helper methods or build your own ApptRequestEntity
	 */
	public void processApptAndSubmit(ApptRequestEntity entity) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		// Select process option
		for (WebElement action : processOptions) {

			int actionValue = Integer.parseInt(action.getAttribute("value"));
			if (entity.getProcessOption() == actionValue) {
				action.click();
			}
			
			if ((entity.getProcessOption() == ApptRequestProcessOption.APPROVE) && !apptDate.getAttribute("value").equals(entity.getApptDate())) {
				apptDate.sendKeys(entity.getApptDate());
			}
		}
		PracticeUtil.setPracticeFrame(driver);
		// Set Subject
		subject.sendKeys(entity.getSubject() + " " + createdTs);
		
		// Set Body
		body.sendKeys(entity.getBody());
		
		// Set allow patient reply
		if (entity.isNoReply()) {
			noReply.click();
		}
		
		btnSubmitApptRequest.click();
	}
	
	/**
	 * Will mark request as cancelled and continue to next step
	 * @return appt request search page
	 */
	public ApptRequestDetailStep2Page chooseApproveAndSubmit() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		ApptRequestEntity entity = new ApptRequestEntity()
				.setProcessOption(ApptRequestProcessOption.APPROVE)
				.setApptDate("01/01/2020")
				.setSubject("Approved")
				.setBody("This is a response from the Practice")
				.setNoReply(false);
		
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}
	
	/**
	 * Will mark request as cancelled and continue to next step
	 * @return appt request search page
	 */
	public ApptRequestDetailStep2Page chooseCancelAndSubmit() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		ApptRequestEntity entity = new ApptRequestEntity()
			.setProcessOption(ApptRequestProcessOption.CANCEL)
			.setSubject("Approved")
			.setSubject("This is a response from the Practice");
		
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	/**
	 * Will check Secure Message subject text & return subject text
	 * @return
	 */
	public String getPracticeMessageSubject() 
	{
	  if(isApptRequestDetailPageLoaded()==false){
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver,60,messageSubject);
	  }
		return messageSubject.getText();
	  
	}
	

	/**
	 * Will check Secure Message reply text & returns message text
	 * @return
	 */
	public String getPracticeMessageBody() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver,1,messageBody);
		return messageBody.getText();
	}
}
