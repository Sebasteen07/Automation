package com.medfusion.product.object.maps.forms.page;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//iframe[@title='Forms']")
	private WebElement newFormIframe;

	@FindBy(id = "iframe")
	private WebElement formsIframe;

	@FindBy(xpath = "//li[@id='signout'] | //a[./text()='Logout']")
	private WebElement logout;

	@FindBy(xpath = "//span[./text()='health forms'] | //span[./text()='Health Forms'] | //a[./text()='Health Forms']")
	private WebElement healthFormsLink;

	/**
	 * automatically switches to corresponding iframe
	 * ({@link com.medfusion.product.object.maps.patientportal2.page.JalapenoPage#JalapenoNewCustomHealthFormPage(WebDriver driver)
	 * see customFormPage constructor})
	 * 
	 * @param formName
	 * @return
	 * @throws InterruptedException
	 */
	public NewCustomFormPage openNewCustomForm(String formName) {
		driver.findElement(By.linkText(formName)).click();
		if (!IHGUtil.exists(driver, newFormIframe)) {
			driver.switchTo().defaultContent();
		}
		driver.switchTo().frame(newFormIframe);
		return PageFactory.initElements(driver, NewCustomFormPage.class);
	}

	public FormWelcomePage openDiscreteForm(String selectedForm) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(selectedForm))).click();
		if (!IHGUtil.exists(driver, newFormIframe)) {
			driver.switchTo().defaultContent();
		}
		driver.switchTo().frame(newFormIframe);
		return PageFactory.initElements(driver, FormWelcomePage.class);
	}

	public OldCustomFormPages openOldCustomForm(String formName) throws InterruptedException {
		driver.findElement(By.linkText(formName)).click();
		Thread.sleep(2000);
		return PageFactory.initElements(driver, OldCustomFormPages.class);
	}

	public void clickOnHealthForms() {
		log("Clicking on Health Forms button");
		IHGUtil.setDefaultFrame(driver);
		healthFormsLink.click();
		IHGUtil.setFrame(driver, "iframe");
	}

	public String getPDFDownloadLink(String formName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li[./a[starts-with(./text(), '" + formName + "')]]//a)[2]"))).getAttribute("href");
	}

	public void logout() throws InterruptedException, IOException {
		IHGUtil.setDefaultFrame(driver);
		scrollAndWait(0, 0, 500);
		logout.click();
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

}
