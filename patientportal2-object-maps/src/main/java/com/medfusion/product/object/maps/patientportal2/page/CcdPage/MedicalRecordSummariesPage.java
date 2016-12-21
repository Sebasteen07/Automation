package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import static org.testng.AssertJUnit.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;

public class MedicalRecordSummariesPage extends MedfusionPage {

	@FindBy(how = How.ID, using = "from-date")
	private WebElement fromDate;

	@FindBy(how = How.ID, using = "to-date")
	private WebElement toDate;

	@FindBy(how = How.ID, using = "select-all")
	private WebElement selectAll;

	@FindBy(how = How.ID, using = "emailButton")
	private WebElement emailButton;

	@FindBy(how = How.ID, using = "downloadButton")
	private WebElement downloadButton;

	@FindBy(how = How.XPATH, using = "//*[@data-ng-repeat='ccd in vm.ccdList'][1]//input")
	private WebElement firstVisibleCCDCheckbox;
	
	@FindBy(how = How.XPATH, using = "//*[@data-ng-repeat='ccd in vm.ccdList'][2]//input")
	private WebElement secondVisibleCCDCheckbox;
	
	@FindBy(how = How.XPATH, using = "//*[@data-ng-repeat='ccd in vm.ccdList'][1]//a")
	private WebElement firstVisibleCCDDate;

	@FindBy(how = How.XPATH, using = "//*[@data-ng-repeat='ccd in vm.ccdList'][2]//a")
	private WebElement secondVisibleCCDDate;

	@FindBy(how = How.XPATH, using = "//*[@data-ng-repeat='ccd in vm.ccdList'][3]//a")
	private WebElement thirdVisibleCCDDate;

	@FindBy(how = How.ID, using = "emailAddress")
	private WebElement emailAddressInput;
	
	@FindBy(how = How.ID, using = "transmitButton")
	private WebElement transmitButton;
	
	@FindBy(how = How.XPATH, using = "//*[@class='notification-message']/p[@data-ng-if='vm.transmitSuccess']")
	private WebElement successNotificationMessage;	
	
	
	public MedicalRecordSummariesPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(fromDate);
		webElementsList.add(toDate);
		webElementsList.add(selectAll);
		webElementsList.add(emailButton);
		webElementsList.add(downloadButton);
		webElementsList.add(firstVisibleCCDCheckbox);
		webElementsList.add(firstVisibleCCDDate);
		webElementsList.add(secondVisibleCCDDate);
		webElementsList.add(thirdVisibleCCDDate);

		return assessPageElements(webElementsList);
	}

	public void sendCCDIfNewestIsOlderThan(int days) {
		if (System.currentTimeMillis() >= getTimeStampOfDate(firstVisibleCCDDate) + (days * 86400000)) {
			//log("The test will send newer CCD to a patient.");
			// TODO: Send CCD
		}else{
			//log("The newest CCD isn't older than " + days + " days.");
		}
	}

	public void setFilterToThirdCCDDate() {
		setFilterToElementsDate(thirdVisibleCCDDate);
	}

	public void selectFirstVisibleCCD(){
		firstVisibleCCDCheckbox.click();
	}
	
	public void selectSecondVisibleCCD(){
		secondVisibleCCDCheckbox.click();
	}
	
	public void sendFirstVisibleCCDUsingDirectProtocol(String directEmailAddress){
		emailButton.click();
		assertTrue(areEmailLightboxElementsPresent());
		emailAddressInput.sendKeys(directEmailAddress);
		transmitButton.click();
		log("Wait for success notification message");
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(successNotificationMessage, "Your health data has been successfully sent"));
	}
	
	public void setFilterToDefaultPositionAndCheckElements(){
		filterCCDs(getOnlyDateFromElement(firstVisibleCCDDate), getDateFromTimeStamp(System.currentTimeMillis()));
		assertTrue(areBasicPageElementsPresent());
	}
	
	private boolean areEmailLightboxElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(emailAddressInput);
		webElementsList.add(transmitButton);

		return assessPageElements(webElementsList);
	}
	
	private void setFilterToElementsDate(WebElement element) {
		String date = getOnlyDateFromElement(element);
		filterCCDs(date, date);
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@data-ng-repeat='ccd in vm.ccdList'][2]//a")));
		assertEquals("Something went wrong when filtering CCDs.", date, getOnlyDateFromElement(firstVisibleCCDDate));
	}

	private long getTimeStampOfDate(WebElement element) {
		Pattern p = Pattern.compile("\\w+\\s\\d+,\\s\\d+");
		Matcher m = p.matcher(element.getText());
		m.find();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
		try {
			Date convertedDate = (Date) formatter.parse(m.group(0));
			long timeStamp = convertedDate.getTime();
			return timeStamp;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Couln't parse given date.", e);
		}
	}

	private String getDateFromTimeStamp(long timeStamp) {
		Date dateToConvert = new Date(timeStamp);
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
		String convertedDate = formatter.format(dateToConvert);
		return convertedDate;
	}

	private String getOnlyDateFromElement(WebElement element) {
		return element.getText().substring(0, element.getText().length() - 9);
	}

	private void filterCCDs(String fromDate, String toDate) {
		updateWebElement(this.fromDate, fromDate);
		updateWebElement(this.toDate, toDate);
	}

	public void selectDownload() {
		downloadButton.click();
	}

	public void selectFirstVisibleCCDDate() {
		firstVisibleCCDDate.click();
	}

}
