package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class JalapenoAskAStaffV2HistoryDetailPage extends JalapenoMenu {

		@FindBy(how = How.ID, using = "backButton")
		private WebElement backButton;

		@FindBy(how = How.ID, using = "created_ts_value")
		private WebElement requestDetailDate;

		@FindBy(how = How.ID, using = "provider_name_value") //hidden if there in only one provider
		private WebElement requestDetailProvider;

		@FindBy(how = How.ID, using = "location_name_value")
		private WebElement requestDetailLocation;

		@FindBy(how = How.ID, using = "subject_value")
		private WebElement requestDetailSubject;

		@FindBy(how = How.ID, using = "question_value")
		private WebElement requestDetailQuestion;

		@FindBy(how = How.ID, using = "status_value")
		private WebElement requestDetailStatus;

		public JalapenoAskAStaffV2HistoryDetailPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(signoutButton);
				webElementsList.add(backButton);
				webElementsList.add(requestDetailDate);
				webElementsList.add(requestDetailLocation);
				webElementsList.add(requestDetailSubject);
				webElementsList.add(requestDetailQuestion);
				webElementsList.add(requestDetailStatus);

				return assessPageElements(webElementsList);
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
}
