package com.medfusion.product.object.maps.forms.page;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.icu.util.Calendar;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;

public class HealthFormListPage extends BasePageObject {

	public HealthFormListPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		IHGUtil.setFrame(driver, "iframe");
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//iframe[@title='Forms']")
	private WebElement newFormIframe;
	
	@FindBy(xpath ="//iframe[@title='Forms']")
	private WebElement iframeforms;

	@FindBy(xpath = "//li[@id='signout'] | //a[./text()='Logout'] | //a[./text()='Log Out']")
	private WebElement logout;

	@FindBy(xpath = "//span[./text()='health forms'] | //span[./text()='Health Forms'] | //a[./text()='Health Forms'] | //a[./text()='health forms']")
	private WebElement healthFormsLink;

	@FindBy(xpath = "//span[./text()='Home'] | //*[./text()='my patient page'] | //*[./text()='My Patient Page']")
	private WebElement homeLink;
	
	@FindBy(xpath= "//a[text()='Continue']")
	private WebElement Continuebutton1;
	
	@FindBy(xpath="//li/a[text()='General Registration and Health History']")
	public static WebElement healthFormsRegistrationLink;
	
	@FindBy(xpath="//li/a[text()='General Registration and Health History Updated']")
	public static WebElement healthFormsRegistrationLinkUpdated;
	
	@FindBy(linkText="View as PDF")
	private static WebElement pdfLinkText;
	
	private static String formValueNew;
	
	public FormWelcomePage openDiscreteForm(String selectedForm) throws Exception {
		log("Waiting up to 50 seconds for forms to display");
		WebDriverWait wait = new WebDriverWait(driver, 50);
		javascriptClick(wait.until(ExpectedConditions.elementToBeClickable(By.linkText(selectedForm))));
		Thread.sleep(3000);
		if (!IHGUtil.exists(driver, newFormIframe)) {
			driver.switchTo().defaultContent();
		}
		driver.switchTo().frame(newFormIframe);
		return PageFactory.initElements(driver, FormWelcomePage.class);
	}

	public OldCustomFormPages openOldCustomForm(String formName) throws InterruptedException {
		javascriptClick(driver.findElement(By.linkText(formName)));
		Thread.sleep(3000);
		return PageFactory.initElements(driver, OldCustomFormPages.class);
	}

	public void clickOnHealthForms() {
		log("Clicking on Health Forms button");
		IHGUtil.setDefaultFrame(driver);
		javascriptClick(healthFormsLink);
		IHGUtil.setFrame(driver, "iframe");
	}
	public void clickOnHealthFormsRegistrationLink() throws InterruptedException{
		log("Clicking On General Registration and Health History ");
		IHGUtil.waitForElement(driver, 30, healthFormsRegistrationLink);
		healthFormsRegistrationLink.click();
		Thread.sleep(7000);
		formValueNew=healthFormsRegistrationLink.getText();
		//WebDriverWait wait = new WebDriverWait(driver,50);
		driver.switchTo().frame(iframeforms);
		//wait.until(ExpectedConditions.visibilityOf(Continuebutton1));
		IHGUtil.waitForElement(driver, 80, Continuebutton1);
		Continuebutton1.click();
	}
	public String getFormName()
	{
		return formValueNew;
	}
	public String getPDFDownloadLink(String formName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li[./a[starts-with(./text(), '" + formName + "')]]//a)[2]"))).getAttribute("href");
	}

	public void logout() throws InterruptedException, IOException {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(NoSuchFrameException.class);
		
		WebElement logout = wait.until(new Function<WebDriver, WebElement>() {
			     public WebElement apply(WebDriver driver) {
			    	IHGUtil.setFrame(driver, "iframebody");		
			 		return driver.findElement(By.xpath("//li[@id='signout'] | //a[./text()='Logout'] | //a[./text()='Log Out']"));
			       }
			     }
				);
		javascriptClick(logout);
	}

	/**
	 * 
	 * @param formName
	 * @return string in format "x/y" which means x of y pages completed
	 */
	public String getInfoAboutFormCompletion(String formName) {
		Pattern pattern = Pattern.compile(".+ completed (\\d) of (\\d) .+");
		Matcher matcher = pattern.matcher(getFormInfo(formName));
		if (matcher.find())
			return matcher.group(1) + "/" + matcher.group(2);
		else
			throw new IllegalStateException("Info about completed pages not found.");
	}

	public Date getSubmittedDate(String formName) {
		Pattern pattern = Pattern.compile(".+ completed on (\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}):(\\d{2}) (.M)");
		Matcher matcher = pattern.matcher(getFormInfo(formName));
		if (matcher.find()) {
			int hours = Integer.parseInt(matcher.group(4));
			if ("PM".equals(matcher.group(6))) {
				hours = hours + 12;
			}
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(1)) - 1, Integer.parseInt(matcher.group(2)), hours,
					Integer.parseInt(matcher.group(5)));
			return c.getTime();
		}
		throw new IllegalStateException("Date not found");
	}

	private String getFormInfo(String formName) {
		String formInfo;
		try {
			formInfo = driver.findElement(By.xpath("//li[./a/text()='" + formName + "']/table/tbody/tr/td/span")).getText();
		} catch (NoSuchElementException f) {
			log("Info about form named '" + formName + "' not found");
			throw f;
		}
		return formInfo;
	}

	public void goToHomePage() {
		IHGUtil.setDefaultFrame(driver);
		javascriptClick(homeLink);
	}
	
	public void getPDF() {
		IHGUtil.waitForElement(driver, 90, pdfLinkText);
		pdfLinkText.click();
	}
	
	public void clickOnHealthFormsRegistrationLinkUpdated(int i) throws InterruptedException{
		log("Clicking On General Registration and Health History Updated ");
		IHGUtil.waitForElement(driver, 60, healthFormsRegistrationLinkUpdated);
		healthFormsRegistrationLinkUpdated.click();
		driver.switchTo().frame(iframeforms);
		List<WebElement> continueButtonClick = driver.findElements(By.xpath("//a[text()='Continue']"));
		if(i==0 && !continueButtonClick.isEmpty()) {
			IHGUtil.waitForElement(driver, 60, Continuebutton1);
			javascriptClick(Continuebutton1);
		}
		
	}
}
