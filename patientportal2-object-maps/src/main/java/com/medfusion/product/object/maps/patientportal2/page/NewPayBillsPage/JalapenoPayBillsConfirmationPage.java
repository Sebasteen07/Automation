package com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoPayBillsConfirmationPage extends JalapenoMenu {

		@FindBy(how = How.ID, using = "comment")
		private WebElement comment;

		@FindBy(how = How.ID, using = "makepayment")
		private WebElement submitPayment;

		@FindBy(how = How.ID, using = "confirmationBack")
		private WebElement backButton;

		@FindBy(how = How.ID, using = "cardEnding")
		private WebElement cardEnding;

		public JalapenoPayBillsConfirmationPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

				webElementsList.add(comment);
				webElementsList.add(submitPayment);
				webElementsList.add(backButton);
				return assessPageElements(webElementsList);
		}

		public JalapenoHomePage commentAndSubmitPayment(String commentString) {
				log("Insert optional comment");
				comment.sendKeys(commentString);

				log("Click on Submit Payment button");
				javascriptClick(submitPayment);

				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		@Deprecated //same functionality as areBasicElementsPresent(), this method is used by integrations
		public boolean assessPayBillsConfirmationPageElements() {

				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

				webElementsList.add(comment);
				webElementsList.add(submitPayment);
				webElementsList.add(backButton);

				return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
		}

		public String getCreditCardEnding() {
				return cardEnding.getText().substring(cardEnding.getText().length() - 4);
		}
}
