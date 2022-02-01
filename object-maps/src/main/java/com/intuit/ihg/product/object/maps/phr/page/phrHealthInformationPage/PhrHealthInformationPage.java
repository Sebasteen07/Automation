//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.phr.page.PhrConditionsAndDiagnosesPage;
import com.intuit.ihg.product.phr.utils.PhrConstants;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrHealthInformationPage extends BasePageObject {

	@FindBy(id = "medicationInput")
	private WebElement medicationInput;

	@FindBy(id = "medicationAdd")
	private WebElement addMedication;

	@FindBy(xpath = ".//*[@class='content_hdr']")
	private WebElement pageHeader;

	@FindBy(xpath = "//a[contains(@href, 'condition.do')]")
	private WebElement conditionsAndDiagnosesLink;

	@FindBy(xpath = "//a[text() ='Laboratory and Test Results ']")
	private WebElement laboratoryAndTestResultLink;

	@FindBy(xpath = "//a[text() ='Vital Signs ']")
	private WebElement vitalSignsLink;

	@FindBy(xpath = ".//img[@alt='ADD PROCEDURES']")
	private WebElement addSurgeriesAndProcedures;

	private static String addAnotherXpath = ".//input[@type='text' and @name='other0']";

	@FindBy(xpath = ".//a//img[@alt='ADD PROCEDURES']")
	private WebElement addSurgeriesAndProceduresSubmit;

	@FindBy(xpath = ".//a//img[@alt='ADD IMMUNIZATION']")
	private WebElement addImmunizationsButton;

	public PhrHealthInformationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkHealthInfoPage() {
		IHGUtil.PrintMethodName();
		return pageHeader.getText().contains(PhrConstants.healthInfoPageTitle);
	}

	public PhrConditionsAndDiagnosesPage clickOnConditionsAndDiagnoses() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, conditionsAndDiagnosesLink);
		conditionsAndDiagnosesLink.click();
		return PageFactory.initElements(driver, PhrConditionsAndDiagnosesPage.class);
	}

	public void setMedication(String input) throws InterruptedException {
		PhrUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, medicationInput);
		medicationInput.sendKeys(input);
		selectAutoCompleteItem(input);
	}

	public void addMedication() {
		PhrUtil.PrintMethodName();
		addMedication.click();
	}

	public void selectAutoCompleteItem(String input) throws InterruptedException {
		PhrUtil.PrintMethodName();

		// Need time for previous sendKeys from calling method to load auto complete div
		Thread.sleep(10000);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String xpath = ".//*[@id='medicationContainer']/div/div[2]/ul/li[2]/div[@class='myCustomResult']";
		try {
			WebElement autocompleteItem = driver.findElement(By.xpath(xpath));
			autocompleteItem.click();
		} catch (Exception e) {
			System.out.println("### PhrHealthInformationPage.selectAutoCompleteItem --- No autocomplete options found."
					+ " Likely because no input was sent .");
		}
	}

	public void verifyAddedMedication() {
		PhrUtil.PrintMethodName();
		List<Object> list = IHGUtil.searchResultTable(driver, "//table[@id='custom_table_list_id']/tbody",
				new ArrayList<String>(Arrays.asList("UroXatral (alfuzosin), Extende...")));
		if (!list.isEmpty()) {
			assertTrue(((Boolean) list.get(1)).booleanValue());
		} else {
			assertTrue(false, "Mediaction Not Found");
		}
	}

	public void removeValues() throws InterruptedException, NoSuchElementException {
		PhrUtil.PrintMethodName();
		boolean visible = false;
		try {
			WebElement tablePath = driver.findElement(By.xpath("//table[@id='custom_table_list_id']"));
			List<WebElement> rows = tablePath.findElements(By.tagName("tr"));
			int rowSize = rows.size();
			while (rowSize > 1) {
				WebElement removeValuesFromTable = driver.findElement(By.xpath("//a/img[@alt='REMOVE']"));
				if (removeValuesFromTable.isDisplayed()) {
					visible = true;
					removeValuesFromTable.click();
					Thread.sleep(10000);
					driver.switchTo().alert().accept();
					Thread.sleep(5000);
					rowSize--;
					Thread.sleep(5000);
				} else {
					System.out.println("###PhrHealthInformationPage ---No more rows to remove");
				}
			}
		} catch (NoSuchElementException e) {
			assertFalse(visible);
			log("Medications Not Present in the Table");
		}
	}

	public LaboratoryAndTestResultPage clickOnLaboratoryAndTestResultLink() {
		IHGUtil.PrintMethodName();
		try {
			IHGUtil.waitForElementInDefaultFrame(driver, 30, laboratoryAndTestResultLink);
			laboratoryAndTestResultLink.click();
		} catch (Exception e) {
			laboratoryAndTestResultLink.click();
		}
		return PageFactory.initElements(driver, LaboratoryAndTestResultPage.class);
	}

	public VitalSignsPage clickOnVitalSigns() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, vitalSignsLink);
		vitalSignsLink.click();
		return PageFactory.initElements(driver, VitalSignsPage.class);
	}

	public boolean addSurgeriesButton() {
		PhrUtil.PrintMethodName();
		addSurgeriesAndProcedures.isDisplayed();
		return true;
	}

	public List<WebElement> findAllCheckBoxes() {
		PhrUtil.PrintMethodName();

		String xpath = "//input[@type='checkbox' and @class!='skipLine']";
		return driver.findElements(By.xpath(xpath));
	}

	public void selectFirstCheckBox(boolean input) throws InterruptedException {
		PhrUtil.PrintMethodName();

		List<WebElement> listCheckBoxes = findAllCheckBoxes();
		for (WebElement el : listCheckBoxes) {
			if (input && !el.isSelected()) {
				el.click();

				break;
			} else if (!input && el.isSelected()) {
				el.click();
				break;
			}
		}
	}

	public void setAnyOtherField(String input) {
		PhrUtil.PrintMethodName();
		WebElement anyOtherField = driver.findElement(By.xpath(addAnotherXpath));
		System.out.println("###going to clear");
		anyOtherField.clear();
		System.out.println("###cleared");
		anyOtherField.sendKeys(input);
	}

	public void submitSurgeriesAndProcedures() {
		PhrUtil.PrintMethodName();
		addSurgeriesAndProceduresSubmit.click();
	}

	public void addSurgeriesAndProcedures() throws InterruptedException {
		PhrUtil.PrintMethodName();
		Thread.sleep(5000);
		try {
			selectFirstCheckBox(true);
			setAnyOtherField("No more surgeries to share");
			submitSurgeriesAndProcedures();
		} catch (Exception e) {
			submitSurgeriesAndProcedures();
			selectFirstCheckBox(true);
			setAnyOtherField("No more surgeries to share");
			submitSurgeriesAndProcedures();
		}
	}

	public void removeSurgeriesAndProcedures() throws InterruptedException {
		PhrUtil.PrintMethodName();
		Thread.sleep(5000);
		removeValues();
	}

	public void verifyAddedSurgery() {
		PhrUtil.PrintMethodName();
		List<Object> list = IHGUtil.searchResultTable(driver, "//table[@id='custom_table_list_id']/tbody",
				new ArrayList<String>(Arrays.asList("Abdominal Surgery")));
		if (!list.isEmpty()) {
			assertTrue(((Boolean) list.get(1)).booleanValue());
		} else {
			assertTrue(false, "Surgery Not Found");
		}
	}

	public void submitImmunizations() {
		PhrUtil.PrintMethodName();
		addImmunizationsButton.click();
	}

	public void addImmunizations() throws Exception {
		PhrUtil.PrintMethodName();
		Thread.sleep(5000);
		try {
			selectFirstCheckBox(true);
			setAnyOtherField("No more immunizations to share");
			submitImmunizations();
		} catch (Exception e) {
			submitImmunizations();
			selectFirstCheckBox(true);
			setAnyOtherField("No more immunizations to share");
			submitImmunizations();
		}

	}

	public void removeImmunizations() throws InterruptedException {
		PhrUtil.PrintMethodName();
		Thread.sleep(5000);
		removeValues();
	}

	public void verifyAddedImmunization() {
		PhrUtil.PrintMethodName();
		List<Object> list = IHGUtil.searchResultTable(driver, "//table[@id='custom_table_list_id']/tbody",
				new ArrayList<String>(Arrays.asList("Adenovirus - Type 4, live, ora...")));
		if (!list.isEmpty()) {
			assertTrue(((Boolean) list.get(1)).booleanValue());
		} else {
			assertTrue(false, "immunization Not Found");
		}
	}

}
