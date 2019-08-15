package com.medfusion.product.object.maps.practice.page.familyManagement;

import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.practice.api.utils.PracticeConstants;

public class AgeOutReportPage extends BasePageObject {

	public AgeOutReportPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
	}

	@FindBy(xpath = "//input[@value='DEPENDENTS_ONLY']")
	private WebElement dependentsInput;

	@FindBy(xpath = "//input[@value='AGED_OUT_ONLY']")
	private WebElement agedoutInput;

	@FindBy(xpath = "//select[@class='monthSelect choice']")
	private WebElement monthSelect;

	@FindBy(xpath = "//select[@class='daySelect choice']")
	private WebElement daySelect;

	@FindBy(xpath = "//select[@class='yearSelect choice']")
	private WebElement yearSelect;

	@FindBy(xpath = "//input[@name='buttons:submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//form[@id='resultsForm']/table/tbody/tr/td/span/a")
	private WebElement firstPatientName;

	@FindBy(xpath = "//form[@id='resultsForm']/table/tbody/tr/td[11]/span/a")
	private WebElement firstPatientAgeOutButton;

	@FindBy(xpath = "//div[@class='wicket-modal']//iframe")
	private WebElement iFrameOfLightbox;

	@FindBy(xpath = "//table//input[@value='OK']")
	private WebElement iFrameOkButton;

	@FindBy(xpath = "//*[contains(text(),'The patient has been unlinked from his guardian(s) and his email address has been deleted.')]")
	private WebElement infoMessageAgedOut;


	public void searchInAgeOutReport(boolean dependents, boolean agedout, String month, String day, String year) throws InterruptedException {
		IHGUtil.PrintMethodName();

		try {
			dependentsInput.getAttribute("checked").equals("checked");
			if (!dependents)
				dependentsInput.click();
		} catch (java.lang.NullPointerException e) {
			if (dependents)
				dependentsInput.click();
		}

		try {
			agedoutInput.getAttribute("checked").equals("checked");
			if (!agedout)
				agedoutInput.click();
		} catch (java.lang.NullPointerException e) {
			if (agedout)
				agedoutInput.click();
		}

		Select dobDay = new Select(daySelect);
		dobDay.selectByValue(day);
		Select dobMonth = new Select(monthSelect);
		dobMonth.selectByValue(month);
		Select dobYear = new Select(yearSelect);
		dobYear.selectByValue(year);

		submitButton.click();
	}

	public String ageOutFirstPatient() {
		IHGUtil.PrintMethodName();

		assertTrue(IHGUtil.waitForElement(driver, 30, firstPatientName));
		String name = firstPatientName.getText();
		firstPatientAgeOutButton.click();

		log("Switch to Lightbox iFrame");
		driver.switchTo().frame(iFrameOfLightbox);
		IHGUtil.waitForElement(driver, 10, iFrameOkButton);
		iFrameOkButton.click();

		log("Switch back");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);

		log("Check info message");
		assertTrue(isElementVisible(infoMessageAgedOut,30));

		return name;
	}

	public PatientDashboardPage findPatientInList(String name) {
		IHGUtil.PrintMethodName();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + name + "')]"))).click();

		return PageFactory.initElements(driver, PatientDashboardPage.class);
	}

	public boolean isElementVisible(WebElement element, int timeOutInSeconds){
		try{
				log("Checking visibility of " + element.toString());
			new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
				}catch (Exception ex){
						log("Element is not visible.");
						return false;
				}
				log("Element is visible.");
				return true;
		}

}
