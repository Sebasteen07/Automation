//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.apptrequest;

import static org.testng.Assert.assertEquals;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class ApptRequestDetailStep1Page extends BasePageObject {

	public static final String PAGE_NAME = "Appt Request Detail Step 1 Page";

	@FindBy(linkText = "Appointment History")
	private WebElement lnkApptHistory;

	@FindBy(className = "feedbackpanelERROR")
	private WebElement errorPanel;

	@FindBy(name = "processAppointment:inputFields:appointmentDate")
	private WebElement apptDate;

	@FindBy(name = "processAppointment:inputFields:appointmentTime:hour")
	private WebElement apptHour;

	@FindBy(name = "processAppointment:inputFields:appointmentTime:min")
	private WebElement apptMin;

	@FindBy(name = "processAppointment:inputFields:appointmentTime:ampm")
	private WebElement apptAmPm;

	@FindBy(name = "treatmentPlan:treatmentPlanChoices")
	private WebElement treatmentPlanOptions;

	@FindBy(name = "message:subject")
	private WebElement subject;

	@FindBy(name = "message:body")
	private WebElement body;

	@FindBy(name = "message:doNotCommunicate")
	private WebElement doNotCommunicate;

	@FindBy(name = "message:denyReply")
	private WebElement noReply;

	@FindBy(name = "processAppointment:action")
	private List<WebElement> processOptions;

	@FindBy(xpath = ".//input[@value='Submit Appointment Request']")
	private WebElement btnSubmitApptRequest;

	@FindBy(xpath = "//input[@type='file']")
	private WebElement addAttachment;

	@FindBy(xpath = "//span[text()='Upload must be less than 5M']")
	private WebElement fileErrorMessage;

	@FindBy(xpath = ".//table/tbody/tr[1]/td/table/tbody/tr[5]/td[2]/span")
	private WebElement messageSubject;

	@FindBy(xpath = ".//table/tbody/tr[1]/td/table/tbody/tr[6]/td/span/p")
	private WebElement messageBody;

	@FindBy(xpath = "//label[text()='Update Appointment']")
	private WebElement updateAppointmentStatus;

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

	public void processApptAndSubmitAttachment(ApptRequestEntity entity, String MessageErrorfilePath,
			String MessagefilePath) throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();

		// For one reason or another, diving further into another iframe is no longer
		// required.
		// If you encounter element not found etc exceptions inside this method,
		// uncomment the following

		// driver.switchTo().frame("iframebody");

		// Select process option
		for (WebElement action : processOptions) {
			int actionValue = Integer.parseInt(action.getAttribute("value"));
			if (entity.getProcessOption() == actionValue) {
				action.click();
			}

			if ((entity.getProcessOption() == ApptRequestProcessOption.APPROVE)
					&& !apptDate.getAttribute("value").equals(entity.getApptDate())) {
				apptDate.sendKeys(entity.getApptDate());
			}
		}
		PracticeUtil.setPracticeFrame(driver);
		// Set Subject
		subject.sendKeys(entity.getSubject() + " " + createdTs);

		// Set Body
		body.sendKeys(entity.getBody());

		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				addAttachment.sendKeys(MessageErrorfilePath);
				btnSubmitApptRequest.click();
				fileErrorMessage.getText();
				assertEquals("Upload must be less than 5M", fileErrorMessage.getText());
				continue;
			}
			if (i == 1) {
				subject.sendKeys(entity.getSubject() + " " + createdTs);
				body.sendKeys(entity.getBody());
				addAttachment.sendKeys(MessagefilePath);
				continue;
			} else {
				addAttachment.sendKeys(MessagefilePath);

			}

		}

		// Set allow patient reply
		if (entity.isNoReply()) {
			noReply.click();
		}
		btnSubmitApptRequest.click();
	}

	public void processApptAndSubmit(ApptRequestEntity entity) {
		IHGUtil.PrintMethodName();

		// For one reason or another, diving further into another iframe is no longer
		// required.
		// If you encounter element not found etc exceptions inside this method,
		// uncomment the following

		// driver.switchTo().frame("iframebody");

		// Select process option
		for (WebElement action : processOptions) {
			int actionValue = Integer.parseInt(action.getAttribute("value"));
			if (entity.getProcessOption() == actionValue) {
				action.click();
			}

			if ((entity.getProcessOption() == ApptRequestProcessOption.APPROVE)
					&& !apptDate.getAttribute("value").equals(entity.getApptDate())) {
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
	
	public ApptRequestDetailStep2Page chooseApproveAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.APPROVE).setApptDate(entity.getApptDate())
				.setSubject("Approved").setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}
	
	public ApptRequestDetailStep2Page chooseprocessedExternallyAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.PROCESS_EXTERNALLY).setApptDate(entity.getApptDate())
				.setSubject("Process Externally").setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public ApptRequestDetailStep2Page chooseSetToPendingAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.SET_PENDING).setApptDate(entity.getApptDate())
				.setSubject("Set to Pending").setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public ApptRequestDetailStep2Page chooseRequestCancelAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.CANCEL).setSubject("Cancel")
				.setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public ApptRequestDetailStep2Page chooseUpdateAppointmentAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		updateAppointmentStatus.click();
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.UPDATEAPPOINTMENT).setApptDate(entity.getApptDate())
				.setSubject("Update Appointment").setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public ApptRequestDetailStep2Page chooseCommunicateAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.COMMUNICATE_ONLY).setApptDate(entity.getApptDate())
				.setSubject("Communicate only").setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public ApptRequestDetailStep2Page chooseApproveAndSubmitAttachment(String MessageErrorfilePath,
			String MessagefilePath) throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		ApptRequestEntity entity = new ApptRequestEntity();
		entity.setProcessOption(ApptRequestProcessOption.APPROVE).setApptDate(entity.getApptDate())
				.setSubject("Approved").setBody("This is a response from the Practice").setNoReply(false);
		processApptAndSubmitAttachment(entity, MessageErrorfilePath, MessagefilePath);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public ApptRequestDetailStep2Page chooseCancelAndSubmit() throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		ApptRequestEntity entity = new ApptRequestEntity().setProcessOption(ApptRequestProcessOption.CANCEL)
				.setSubject("Approved").setSubject("This is a response from the Practice");
		processApptAndSubmit(entity);
		return PageFactory.initElements(driver, ApptRequestDetailStep2Page.class);
	}

	public String getPracticeMessageSubject() {
		if (isApptRequestDetailPageLoaded() == false) {
			IHGUtil.PrintMethodName();
			PracticeUtil.setPracticeFrame(driver);
			IHGUtil.waitForElement(driver, 60, messageSubject);
		}
		return messageSubject.getText();

	}

	public String getPracticeMessageBody() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 1, messageBody);
		return messageBody.getText();
	}

	public boolean checkAppointmentDetails(String preferredTimeFrame, String preferredDay, String preferredTimeOfDay,
			String reason) throws InterruptedException {
		Thread.sleep(5000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		try {
			driver.findElement(By.xpath("//*[contains(text(),'" + preferredTimeFrame + "')]"));
			driver.findElement(By.xpath("//*[contains(text(),'" + preferredDay + "')]"));
			driver.findElement(By.xpath("//*[contains(text(),'" + preferredTimeOfDay + "')]"));
			driver.findElement(By.xpath("//*[contains(text(),'" + reason + "')]"));
			return true;
		} catch (Exception e) {
			log(e.getCause().toString());
			return false;
		}

	}
}
