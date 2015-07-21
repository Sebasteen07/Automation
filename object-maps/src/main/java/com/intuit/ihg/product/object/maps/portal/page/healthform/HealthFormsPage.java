package com.intuit.ihg.product.object.maps.portal.page.healthform;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class HealthFormsPage extends BasePageObject {

	public static final String PAGE_NAME = "Insurance Health Form Page";

	@FindBy(name = "footer:submit")
	private WebElement btnSubmit;

	@FindBy(xpath = "//a[@class='pdf text']")
	private WebElement lnkclickForPdfDownload;

	// Forms on the page

	@FindBy(xpath = "//ul[@class='customforms']/li/a")
	private List<WebElement> formsLinks;

	@FindBy(linkText = "Ivan Insurance Health Form ( Testing)")
	private WebElement lnkInsuranceHealthForm;


	@FindBy(xpath = "//div[@class='heading1']")
	public WebElement InsuranceHelthform;

	@FindBy(xpath = "//div[@id='formcontainer']//table/tbody/tr[5]/td")
	public WebElement Patientname;

	@FindBy(xpath = "//input[@value='partial']")
	private WebElement blindPartial;

	@FindBy(xpath = "//input[@value='blue']")
	private WebElement colorBlue;

	@FindBy(xpath = "//input[@value='green']")
	private WebElement colorGreen;

	@FindBy(xpath = "//input[@value='yes']")
	private WebElement cancerYes;

	@FindBy(name = "content:categories:1:questions:3:question")
	private WebElement dateOfCancer;

	@FindBy(name = "content:categories:1:questions:5:question")
	private WebElement feelings;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:1:field")
	private WebElement middleName;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:14:field")
	private WebElement maritalStatus;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:18:field")
	private WebElement communicationMethod;

	@FindBy(xpath = "//div[@class='sectionInfo']")
	public WebElement formInfo;

	@FindBy(name = "footer:next")
	private WebElement btnNext;

	@FindBy(name = "content:categories:0:questions:0:question:vitalsForm:inputFieldsContainer:inputFieldsPanel:fieldsContainer:fields:0:field")
	private WebElement vitalName;

	@FindBy(name = "content:categories:0:questions:0:question:vitalsForm:inputFieldsContainer:inputFieldsPanel:fieldsContainer:fields:1:field")
	private WebElement vitalValue;

	@FindBy(name = "content:categories:0:questions:0:question:vitalsForm:inputFieldsContainer:inputFieldsPanel:submitContainer:inlineSubmit")
	private WebElement vitalSave;

	@FindBy(xpath = "//td[@class='MT_2']//a[contains(text(), 'health forms')]")
	private WebElement lnkHealthForms;

	@FindBy(className = "delete")
	private WebElement delete;

	@FindBy(className = "add")
	private WebElement add;

	public HealthFormsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	/**
	 * Gives an indication if the page loaded as expected
	 *
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = lnkInsuranceHealthForm.isDisplayed();
		} catch (Exception e) {
			// Catch element not found error
		}
		return result;
	}

	public WebElement getFormLink(String formName) {
		for (WebElement formLink : formsLinks) {
			if (formLink.getText().equals(formName)) return formLink;
		}
		throw new NoSuchElementException("Form called " + formName + " not found");
	}

	/**
	 * Click on the link InsuranceHealthForm and filling the form
	 */
	public void fillInsuranceHealthForm() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 20, lnkInsuranceHealthForm);
		lnkInsuranceHealthForm.click();
		IHGUtil.waitForElement(driver, 20, middleName);

		middleName.sendKeys("Middle");
		Select selectstate1 = new Select(maritalStatus);
		selectstate1.selectByVisibleText("Single");

		if (driver.getPageSource().contains("Preferred Communication Method:")) {
			Select selectstate2 = new Select(communicationMethod);
			selectstate2.selectByVisibleText("US mail");
		}

		blindPartial.click();
		colorBlue.click();
		colorGreen.click();
		cancerYes.click();

		dateOfCancer.sendKeys("10/15/2013");

		Select selectstate3 = new Select(feelings);
		selectstate3.selectByVisibleText("happy");

	}

	/**
	 * Submit the form
	 */
	public void submitInsuranceHealthForm() {
		btnSubmit.click();
	}

	/**
	 * Simulates Text Insurance HealthFormdownload link click by accessing the
	 * link URL and downloading it via the URLStatusChecker class.
	 *
	 * @return the http status code from the download
	 */
	public int clickInsuranceHealthFormDownloadText() throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		log(driver.getPageSource());
		log(lnkclickForPdfDownload.getAttribute("href").toString());
		return insuranceHealthFormDownloadCode(lnkclickForPdfDownload.getAttribute("href"), RequestMethod.GET);
	}

	/**
	 * steps 1:-sets URL that you want to perform an HTTP Status Check upon
	 * steps 2:-Set the HTTP Request Method (Defaults to 'GET') steps 3:-Mimic
	 * the cookie state of WebDriver (Defaults to true) This will enable you to
	 * access files that are only available when logged in. Perform an HTTP
	 * Status check and return the response code as int (200,300 etc)
	 *
	 * @param url
	 * @param method
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */

	private int insuranceHealthFormDownloadCode(String url, RequestMethod method)
			throws URISyntaxException, IOException {
		IHGUtil.PrintMethodName();
		log("Download URL: " + url);
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
		urlChecker.setURIToCheck(url);
		urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		urlChecker.mimicWebDriverCookieState(true);

		return urlChecker.getHTTPStatusCode();
	}

	public CustomFormPageForSitegen selectCustomForm(String formName)
			throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		WebElement formLink = driver.findElement(By.xpath(".//ul/li/a[@title='" + formName + "']"));
		IHGUtil.waitForElement(driver, 10, formLink);
		formLink.click();
		return PageFactory.initElements(driver, CustomFormPageForSitegen.class);
	}

	public void selectOldCustomForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		WebElement formLink = driver.findElement(By.xpath("//a[text()='"
				+ formName + "']"));
		IHGUtil.waitForElement(driver, 10, formLink);
		formLink.click();
		IHGUtil.waitForElement(driver, 15, formInfo);
	}


	public String getPDFDownloadLink(String formName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		return wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[starts-with(@title, '"
						+ formName + "')]/../table/tbody/tr/td/a[@class='pdf text']")))
				.getAttribute("href");
	}

	/**
	 * @brief Opens a form on the page selected by parameter and initializes its
	 *        welcome page
	 * @param selectedForm
	 *            String value determining which form to open
	 * @return method returns initialized object for Welcome page of the form
	 */
	public FormWelcomePage openDiscreteForm(String selectedForm) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		PortalUtil.setPortalFrame(driver); // switch focus to the correct frame
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(selectedForm)))
					.click();
		PortalUtil.setquestionnarieFrame(driver);
		return PageFactory.initElements(driver, FormWelcomePage.class);
	}

	public void clickNext() {
		IHGUtil.waitForElement(driver, 20, btnNext);
		btnNext.click();
	}

	public void fillVitals() throws Exception {
		if ((driver.findElements(By.className("delete")).size() > 0)) {
			delete.click();
			driver.switchTo().alert().accept();
			add.click();
		}

		IHGUtil.waitForElement(driver, 20, vitalName);
		Select dropdownquestion1 = new Select(vitalName);
		dropdownquestion1.selectByVisibleText("Height");
		vitalValue.sendKeys("1");
		vitalSave.click();
	}

	public void clickHealthForms() {
		driver.switchTo().defaultContent();
		lnkHealthForms.click();
	}

	public String getSubmittedDate(String formName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		String formattedDate;
		try {
			formattedDate = IHGUtil.extractDateFromText(wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@title, '"
							+ formName + "')]/../table/tbody/tr/td/span"))).getText());
		} catch (Exception e) {
			try {
				formattedDate = IHGUtil.extractDateFromText(wait.until(
						ExpectedConditions.visibilityOfElementLocated(By
								.xpath("//a[contains(@title, '" + formName
										+ "')]/../table/tbody/tr/td/span"))).getText());
			} catch (Exception f) {
				log("Date not found or it is in incorrect format!");
				throw f;
			}

		}
		return formattedDate;

	}


}
