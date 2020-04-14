package com.medfusion.product.object.maps.practice.page.referrals;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

import static java.lang.Thread.sleep;

public class ReferralsPage extends BasePageObject {
		//left menu
		@FindBy(xpath = ".//a[contains(@href, 'rp.create')]")
		private WebElement sendReferrals;

		@FindBy(xpath = ".//a[contains(@href, 'rp.incoming')]")
		private WebElement incomingReferrals;

		@FindBy(xpath = ".//a[contains(@href, 'rp.outgoing')]")
		private WebElement outgoingReferrals;

		@FindBy(xpath = ".//a[contains(@href, 'rp.contacts')]")
		private WebElement contacts;

		@FindBy(xpath = ".//a[contains(@href, 'rp.invite')]")
		private WebElement sendInvitation;

		@FindBy(xpath = ".//a[contains(@href, 'rp.invitein')]")
		private WebElement incomingInvitation;

		@FindBy(xpath = ".//a[contains(@href, 'rp.inviteout')]")
		private WebElement outgoingInvitation;

		@FindBy(xpath = ".//a[contains(@href, 'home.main')]")
		private WebElement home;

		//send referral form
		@FindBy(xpath = "//*[@name='memberNameTextField']")
		private WebElement memberNameTestField;

		@FindBy(id = "id5")
		private WebElement practiceNameTestField;

		@FindBy(linkText = "Advanced Search")
		private WebElement advancedSearch;

		@FindBy(xpath = ".//select[@name='referralTypeDD']")
		private WebElement referralType;

		@FindBy(id = "id22")
		private WebElement attachCcdCheckBox;

		@FindBy(id = "id4")
		private WebElement buttonSubmit;

		@FindBy(id = "idb5")
		private WebElement reasonText;

		@FindBy(xpath = ".//input[@value='Open']")
		private WebElement openCheckBox;

		@FindBy(xpath = ".//input[@name='searchParams:2:input']")
		private WebElement patientFirstNameSearch;

		@FindBy(xpath = ".//input[@name='searchParams:3:input']")
		private WebElement patientLastNameSearch;

		@FindBy(xpath = ".//input[@name='buttons:submit']")
		private WebElement buttonSearch;

		@FindBy(xpath = "//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), 'IHGQA')]")
		private WebElement arrivedReferral;

		@FindBy(xpath = ".//input[@name='buttonpanel:submit']")
		private WebElement saveChanges;

		@FindBy(xpath = "//*[@id='id3-autocomplete']/ul/li")
		private WebElement autocompleteName;

		@FindBy(xpath = "//*[@id='id5-autocomplete']/ul/li")
		private WebElement autocompleteSurname;

		@FindBy(xpath = ".//input[@value='M']")
		private WebElement genger;

		@FindBy(xpath = ".//select[@name='patientNotFoundBlock:dateOfBirth:month']")
		private WebElement month;

		@FindBy(xpath = ".//select[@name='patientNotFoundBlock:dateOfBirth:day']")
		private WebElement day;

		@FindBy(xpath = ".//select[@name='patientNotFoundBlock:dateOfBirth:year']")
		private WebElement year;

		@FindBy(xpath = ".//input[@name='patientNotFoundBlock:homePhone']")
		private WebElement homePhone;

		@FindBy(xpath = ".//input['@name=practiceProviderRadio']")
		private WebElement practiceProviderRadio;

		@FindBy(xpath = ".//div[@class='referral-form']/ol/li/span[contains(text(), '123') or contains(text(), 'no')]")
		private WebElement fullForm;

		//advanced search
		@FindBy(className = "wicket_modal")
		private WebElement advancedSearchFrame;

		@FindBy(xpath = "//*[@id='searchform']/table/tbody/tr[1]/td[2]/input")
		private WebElement firstNameSearch;

		@FindBy(name = "searchParams:1:input")
		private WebElement lastNameSearch;

		@FindBy(name = "buttons:submit")
		private WebElement searchPatients;

		@FindBy(className = "even")
		private WebElement searchResult;

		@FindBy(xpath = "//*[@id='resultsLabelDiv']/strong/.//span[2]")
		private WebElement resultCount;  //formatted " (x)"

		@FindBy(xpath = "//a[@class='w_close']")
		private WebElement closeAdvancedSearch;


		public ReferralsPage(WebDriver driver) {
				super(driver);
				PageFactory.initElements(driver, this);
		}

		public void openAdvancedSearch() {
				advancedSearch.click();
		}

		public void closeAdvancedSearch() {
				closeAdvancedSearch.click();
		}

		/**
		 * Get Search results count from string formatted " (x)".
		 * @return number of results
		 */
		private int getSearchResultCount() {
				String result = resultCount.getText();
				result = result.trim(); //remove spaces
				result = result.substring(1, result.length() - 1); //remove brackets
				return Integer.parseInt(result);
		}

		private void fillPatientName(String firstname, String lastname) {
				String name = lastname + ", " + firstname;
				memberNameTestField.sendKeys(name + Keys.ENTER);
		}

		public void sendReferralToAnotherPractice(String patientFirstName, String patientLastName, String practiceName, boolean patientExists) throws Exception {
				IHGUtil.waitForElement(driver, 30, sendReferrals);
				sendReferrals.click();
				driver.switchTo().frame("iframe");
				advancedSearch.click();
				driver.switchTo().frame(advancedSearchFrame);
				log("Switched to advanced search iframe");
				IHGUtil.waitForElement(driver, 30, firstNameSearch);
				firstNameSearch.sendKeys(patientFirstName);
				lastNameSearch.sendKeys(patientLastName);
				searchPatients.click();
				sleep(2000);
				int searchResultCount = getSearchResultCount();
				log("Search result: " + searchResultCount + " patients found.");

				if (searchResultCount == 1 && patientExists) {
						//patient found - refer the patient
						searchResult.click();
						driver.switchTo().defaultContent();
						driver.switchTo().frame("iframe");
						log("Switch back from advanced search");
				} else if (searchResultCount == 0 && !patientExists) {
						//close and create new patient
						driver.switchTo().defaultContent();
						driver.switchTo().frame("iframe");
						log("Switch back from advanced search");
						closeAdvancedSearch();
						fillPatientName(patientFirstName, patientLastName);
						genger.click();
						new Select(month).selectByIndex(4);
						sleep(2000);
						new Select(day).selectByIndex(4);
						new Select(year).selectByIndex(4);
						homePhone.sendKeys("1234567893");

				} else {
						//TODO handle all unexpected states, not all are errors
						throw new Exception("Unexpected search results.");
				}
				practiceNameTestField.sendKeys(practiceName);
				IHGUtil.waitForElement(driver, 30, autocompleteSurname);
				autocompleteSurname.click();
				referralType.sendKeys("Other");
				IHGUtil.waitForElement(driver, 30, fullForm);
				buttonSubmit.click();
		}

		public void checkReferralArrived(String patientFirstName, String patientLastName) throws Exception {
				IHGUtil.waitForElement(driver, 30, incomingReferrals);
				incomingReferrals.click();
				driver.switchTo().frame("iframe");
				openCheckBox.click();
				patientFirstNameSearch.sendKeys(patientFirstName);
				patientLastNameSearch.sendKeys(patientLastName);
				buttonSearch.click();
				sleep(5000);
				arrivedReferral.click();
				IHGUtil.waitForElement(driver, 30, saveChanges);
				saveChanges.click();
		}
}
