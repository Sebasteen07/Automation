package com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class JalapenoAppointmentRequestV2Step1 extends BasePageObject {


	@FindBy(how = How.ID, using = "history_button")
	private WebElement previousAppoitmentRequestsButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\'frame\']/div[3]/div[3]/div[1]/div/mf-providers-locations/div/div[1]/span")
	private WebElement providerSearchInput;

	@FindBy(how = How.ID, using = "showall")
	private WebElement providerSearchButton;

	@FindBy(how = How.ID, using = "showall")
	private WebElement showAllButton;

	@FindBy(how = How.XPATH, using = "(//div[@id='locationProviderResultList']/ol/li[1]/ul/li/label/span)")
	private WebElement doctorSelect;

	@FindBy(how = How.ID, using = "cancel_button")
	private WebElement cancelButton;

	@FindBy(how = How.ID, using = "continue_button")
	private WebElement continueButton;


	public JalapenoAppointmentRequestV2Step1(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}


	public boolean assessElements() {
		IHGUtil.PrintMethodName();

		log("Wait for elements");
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(previousAppoitmentRequestsButton);
		webElementsList.add(providerSearchInput);
		webElementsList.add(providerSearchButton);
		webElementsList.add(showAllButton);
		webElementsList.add(cancelButton);
		webElementsList.add(continueButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}


	public void chooseFirstProvider() {
		IHGUtil.PrintMethodName();
		showAllButton.click();
		doctorSelect.click();
	}

	public JalapenoAppointmentRequestV2HistoryPage goToHistory(WebDriver driver) {
		IHGUtil.PrintMethodName();
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(previousAppoitmentRequestsButton)).click();
		return PageFactory.initElements(driver, JalapenoAppointmentRequestV2HistoryPage.class);
	}


	public JalapenoAppointmentRequestV2Step2 continueToStep2(WebDriver driver) {
		IHGUtil.PrintMethodName();
		javascriptClick(continueButton);
		return PageFactory.initElements(driver, JalapenoAppointmentRequestV2Step2.class);
	}

}
