package com.intuit.ihg.product.object.maps.portal.page.healthform;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class HealthFormPage extends BasePageObject {
	
	  public static final String PAGE_NAME = "Insurance Health Form Page";
		
		@FindBy(linkText="Ivan Insurance Health Form ( Testing)")
		private WebElement lnkInsuranceHealthForm;
		
		@FindBy(name="footer:submit")
		private WebElement btnSubmit;
		
		@FindBy(linkText="Ivan Insurance Health Form ( Testing)")
		private WebElement lnkclickForPdfDownload;
		
		@FindBy(xpath="//div[@class='heading1']")
		public WebElement InsuranceHelthform;
		
		@FindBy(xpath="//div[@id='formcontainer']//table/tbody/tr[5]/td")
		public WebElement Patientname;
		
		@FindBy(xpath="//input[@value='partial']")
		private WebElement blindPartial;
		
		@FindBy(xpath="//input[@value='blue']")
		private WebElement colorBlue;
		
		@FindBy(xpath="//input[@value='green']")
		private WebElement colorGreen;
		
		@FindBy(xpath="//input[@value='yes']")
		private WebElement cancerYes;
		
		@FindBy(name="content:categories:1:questions:3:question")
		private WebElement dateOfCancer;
		
		@FindBy(name="content:categories:1:questions:5:question")
		private WebElement feelings;
				
		@FindBy(name="content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:1:field")
		private WebElement middleName;
		
		@FindBy(name="content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:15:field")
		private WebElement martialStatus;
		
		@FindBy(name="content:categories:0:questions:0:question:inputFieldsPanel:fieldsContainer:fields:19:field")
		private WebElement communicationMethod;
		
		@FindBy(xpath="//div[@class='sectionInfo']")
		public WebElement formInfo;
	
	
	public HealthFormPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Gives an indication if the page loaded as expected
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
	 */
	public void fillInsuranceHealthForm() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 20, lnkInsuranceHealthForm);
		lnkInsuranceHealthForm.click();
		IHGUtil.waitForElement(driver, 20, middleName);
		
		middleName.sendKeys("Middle");
		Select selectstate1 = new Select(martialStatus);
		selectstate1.selectByVisibleText("Single");
		
		if (driver.getPageSource().contains("Preferred Communication Method:"))
		{
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
	public void submitInsuranceHealthForm()		
	{
		btnSubmit.click();
	}
	
	
	/**
     * Simulates Text Insurance HealthFormdownload link click by accessing the link URL and
     * downloading it via the URLStatusChecker class.
     *
     * @return the http status code from the download
     */
	public int clickInsuranceHealthFormDownloadText() throws Exception {
		IHGUtil.PrintMethodName();
	    PortalUtil.setPortalFrame(driver);
	    
	    return insuranceHealthFormDownloadCode(lnkclickForPdfDownload.getAttribute("href"), RequestMethod.GET);
	}
	
	/**
	 * steps 1:-sets URL that you want to perform an HTTP Status Check upon
	 * steps 2:-Set the HTTP Request Method (Defaults to 'GET')
	 * steps 3:-Mimic the cookie state of WebDriver (Defaults to true) This will enable you to access files that are only available when logged in.
	 * Perform an HTTP Status check and return the response code as int (200,300 etc)
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
	
	public CustomFormPageForSitegen selectCustomForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");	
		WebElement formLink = driver.findElement(By.xpath(".//ul/li/a[@title='"+formName+"']"));
		IHGUtil.waitForElement(driver, 10, formLink);
		formLink.click();
		return PageFactory.initElements(driver, CustomFormPageForSitegen.class);
	}
	
	public void selectOldCustomForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframe");		
		WebElement formLink = driver.findElement(By.xpath("//a[text()='"+formName+"']"));	
		IHGUtil.waitForElement(driver, 10, formLink);
		formLink.click();
		IHGUtil.waitForElement(driver, 15, formInfo);
	}
	
	public void clickLnkForPdfDownload() {
		lnkclickForPdfDownload.click();
	}

	public String getPDFDownloadLink() {
		return lnkclickForPdfDownload.getAttribute("href");
	}
}
