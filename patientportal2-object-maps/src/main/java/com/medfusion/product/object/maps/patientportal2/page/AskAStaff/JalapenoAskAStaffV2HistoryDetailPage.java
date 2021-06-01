// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class JalapenoAskAStaffV2HistoryDetailPage extends JalapenoMenu {

	@FindBy(how = How.XPATH, using = "//a[text()='Back ']")
	private WebElement backButton;

	@FindBy(how = How.ID, using = "created_ts_value")
	private WebElement requestDetailDate;

	@FindBy(how = How.ID, using = "provider_name_value") // hidden if there in only one provider
	private WebElement requestDetailProvider;

	@FindBy(how = How.ID, using = "location_name_value")
	private WebElement requestDetailLocation;

	@FindBy(how = How.ID, using = "subject_value")
	private WebElement requestDetailSubject;

	@FindBy(how = How.ID, using = "question_value")
	private WebElement requestDetailQuestion;

	@FindBy(how = How.ID, using = "status_value")
	private WebElement requestDetailStatus;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'This online encounter was charged')]")
	private WebElement requestDetailsPayment;
	
	@FindBy(how = How.XPATH, using = "//li[@class='attachment ng-scope']")
	private WebElement requestAttachedFile;
	

	public JalapenoAskAStaffV2HistoryDetailPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	public String getRequestDetailDate() {
		return requestDetailDate.getText();
	}

	public WebElement getRequestDetailProvider() {
		return requestDetailProvider;
	}

	public String getRequestDetailLocation() {
		return requestDetailLocation.getText();
	}

	public String getRequestDetailSubject() {
		return requestDetailSubject.getText();
	}

	public String getRequestDetailQuestion() {
		return requestDetailQuestion.getText();
	}

	public String getRequestDetailStatus() {
		return requestDetailStatus.getText();
	}

	public String getRequestDetailPayment() {
		return requestDetailsPayment.getText();
	}

	public String getRequestAttachedFile() {
		return requestAttachedFile.getText();
	}
	
}
