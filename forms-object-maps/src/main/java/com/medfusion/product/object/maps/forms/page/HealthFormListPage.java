package com.medfusion.product.object.maps.forms.page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.FormWelcomePage;

public class HealthFormListPage extends BasePageObject {

	public HealthFormListPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = "//iframe[@title='Forms']")
	private WebElement newFormIframe;

	@FindBy(how = How.ID, using = "iframe")
	private WebElement formsIframe;

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
		PortalUtil.setPortalFrame(driver); // switch focus to the correct frame
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(selectedForm))).click();
		PortalUtil.setquestionnarieFrame(driver);
		return PageFactory.initElements(driver, FormWelcomePage.class);
	}

	public OldCustomFormPages openOldCustomForm(String formName) throws InterruptedException {
		driver.findElement(By.linkText(formName)).click();
		Thread.sleep(2000);
		return PageFactory.initElements(driver, OldCustomFormPages.class);
	}

	public String getPDFDownloadLink(String formName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		return wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//a[starts-with(@title, '" + formName + "')]/../table/tbody/tr/td/a[@class='pdf text']")))
				.getAttribute("href");
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

	public String getSubmittedDate(String formName) {
		return IHGUtil.extractDateFromText(getFormInfo(formName));
	}

	private String getFormInfo(String formName) {
		String formInfo;
		boolean inDefaultFrame = IHGUtil.waitForElement(driver, 1, formsIframe);
		if (inDefaultFrame) {
			IHGUtil.setFrame(driver, "iframe");
		}
		try {
			formInfo = driver.findElement(By.xpath("//li[./a/text()='" + formName + "']/table/tbody/tr/td/span"))
					.getText();
		} catch (NoSuchElementException f) {
			log("Info about form named '" + formName + "' not found");
			throw f;
		}
		if (inDefaultFrame) {
			IHGUtil.setDefaultFrame(driver);
		}
		return formInfo;
	}

}
