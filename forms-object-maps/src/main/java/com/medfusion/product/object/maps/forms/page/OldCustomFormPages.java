package com.medfusion.product.object.maps.forms.page;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.common.utils.IHGUtil;

/**
 * Class contains mix of elements/methods of old custom forms. Because old custom forms are to be replaced there is no need to refactor this.
 */
public class OldCustomFormPages extends BasePageObject {
	public static final String PAGE_NAME = "Insurance Health Form Page";

	@FindBy(name = "footer:submit")
	private WebElement btnSubmit;

	@FindBy(xpath = "//a[@class='pdf text']") // table/tbody/tr/td/a
	private WebElement lnkclickForPdfDownload;

	// Forms on the page

	@FindBy(linkText = "Ivan Insurance Health Form ( Testing)")
	private WebElement lnkInsuranceHealthForm;

	@FindBy(xpath = "//div[@class='heading1']")
	public WebElement InsuranceHelthform;

	@FindBy(xpath = "//div[@id='formcontainer']//table/tbody/tr[5]/td")
	public WebElement Patientname;

	@FindBy(xpath = "//input[@value='partial']")
	private WebElement blindPartial;

	@FindBy(name = "content:categories:1:questions:3:question")
	private WebElement dateOfCancer;

	@FindBy(name = "content:categories:1:questions:5:question")
	private WebElement feelings;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:1:field")
	private WebElement middleName;
	
	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:14:field")
	private WebElement sexOrientation;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:16:field")
	private WebElement genderIdentity;
	
	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:17:field")
	private WebElement genderSpecify;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:18:field")
	private WebElement maritalStatus;

	@FindBy(name = "content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:22:field")
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

	@FindBy(className = "delete")
	private WebElement delete;

	@FindBy(className = "add")
	private WebElement add;

	public OldCustomFormPages(WebDriver driver) {
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

	/**
	 * Click on the link InsuranceHealthForm and filling the form
	 * 
	 * @throws InterruptedException
	 */
	public void fillInsuranceHealthForm() throws InterruptedException {
		IHGUtil.waitForElement(driver, 20, middleName);
		middleName.clear();
		middleName.sendKeys("Middle");
		Select selectstate1 = new Select(sexOrientation);
		selectstate1.selectByVisibleText("Bisexual");
		Select selectstate2 = new Select(genderIdentity);
		selectstate2.selectByVisibleText("Additional gender category/(or other), please specify");
		genderSpecify.sendKeys("something");
		
		Select selectstate3 = new Select(maritalStatus);
		selectstate3.selectByVisibleText("Single");

		if (driver.getPageSource().contains("Preferred Communication Method")) {
			Select selectstate4 = new Select(communicationMethod);
			selectstate4.selectByVisibleText("US mail");
		}
		Thread.sleep(1000);
		blindPartial.click();

		// workaround for StaleElementReferenceException
		for (int i = 0; i < 3; i++) {
			try {
				driver.findElement(By.xpath("//input[@value='blue']")).click();
				driver.findElement(By.xpath("//input[@value='green']")).click();
				driver.findElement(By.xpath("//input[@value='yes']")).click();
				break;
			} catch (StaleElementReferenceException ex) {
				Log4jUtil.log((i + 1) + ". attempt to click on checkboxes");
			}
		}


		dateOfCancer.sendKeys("10/15/2013");

		Select selectstate5 = new Select(feelings);
		selectstate5.selectByVisibleText("happy");
	}

	/**
	 * Submit the form
	 * 
	 * @throws InterruptedException
	 */
	public void submitInsuranceHealthForm() throws InterruptedException {
		btnSubmit.click();
		Thread.sleep(1000);
	}

	/**
	 * Simulates Text Insurance HealthFormdownload link click by accessing the link URL and downloading it via the URLStatusChecker class.
	 *
	 * @return the http status code from the download
	 */
	public int clickInsuranceHealthFormDownloadText() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
		// log(driver.getPageSource());
		log(lnkclickForPdfDownload.getAttribute("href").toString());
		return insuranceHealthFormDownloadCode(lnkclickForPdfDownload.getAttribute("href"), RequestMethod.GET);
	}

	/**
	 * steps 1:-sets URL that you want to perform an HTTP Status Check upon steps 2:-Set the HTTP Request Method (Defaults to 'GET') steps 3:-Mimic the cookie
	 * state of WebDriver (Defaults to true) This will enable you to access files that are only available when logged in. Perform an HTTP Status check and return
	 * the response code as int (200,300 etc)
	 *
	 * @param url
	 * @param method
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */

	private int insuranceHealthFormDownloadCode(String url, RequestMethod method) throws URISyntaxException, IOException {
		IHGUtil.PrintMethodName();
		log("Download URL: " + url);
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
		urlChecker.setURIToCheck(url);
		urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		urlChecker.mimicWebDriverCookieState(true);

		return urlChecker.getHTTPStatusCode();
	}

	public void selectOldCustomForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");
		WebElement formLink = driver.findElement(By.xpath("//a[text()='" + formName + "']"));
		IHGUtil.waitForElement(driver, 10, formLink);
		formLink.click();
		IHGUtil.waitForElement(driver, 15, formInfo);
	}

	/**
	 * @brief Opens a form on the page selected by parameter and initializes its welcome page
	 * @param selectedForm String value determining which form to open
	 * @return method returns initialized object for Welcome page of the form
	 */

	public void clickNext() throws InterruptedException {
		IHGUtil.waitForElement(driver, 20, btnNext);
		javascriptClick(btnNext);
		Thread.sleep(1000);
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
}
