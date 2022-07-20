package com.medfusion.product.object.maps.appt.precheck.page.Setting;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class FormsPage extends BasePageObject {
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Forms')]")
	private WebElement clickOnFormsTab;
	
	@FindBy(how = How.XPATH, using = "//div[@id='actionDropdown']")
	private WebElement clickOnAddFormButton;
	
	@FindBy(how = How.XPATH, using = "//button[@id='medfusion-form-add']")
	private WebElement clickOnMedfusionForm;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[1]")
	private WebElement enterFormName;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[2]")
	private WebElement enterFormURL;
	
	@FindBy(how = How.XPATH, using = "//button[@id='actionDropdown']")
	private WebElement clickOnAddformButton;
	
	@FindBy(how = How.XPATH, using = "//p[contains(text(),'General Registration and Health History-2022')]")
	private WebElement forms;
	
	@FindBy(how = How.XPATH, using = "//p[text()='General Registration and Health History-2022']/following::span[@class='mf-icon mf-icon__appointment-types mf-icon-pointer']")
	private WebElement clickOnAppointmentTypes;
	
	@FindBy(how = How.XPATH, using = "//input[@id='select-f40bdf17-fb57-4a78-bb7d-3290b016279b']")
	private WebElement selectAppointmentType;
	
	@FindBy(how = How.XPATH, using = "//span[@class='mf-icon mf-icon__backarrow']")
	private WebElement clickOnBackArrow;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='mf-list__element--content'])[6]")
	private WebElement countOfAppointmentType;
	
	@FindBy(how = How.XPATH, using = "//p[text()='General Registration and Health History-2022']/following::span[@class='mf-icon mf-icon__delete mf-icon-pointer']")
	private WebElement clickOnDeleteForm;
	
	@FindBy(how = How.XPATH, using = "//button[@id='primaryDelete']")
	private WebElement clickDeleteButton;
	
	@FindBy(how = How.XPATH, using = "//input[@class='form-search-input']")
	private WebElement searchForm;
	
	@FindBy(how = How.XPATH, using = "//button[@id='IMH-form-add']")
	private WebElement clickOnIMHForm;
	
	@FindBy(how = How.XPATH, using = "//input[@class='mf-form__input--text']")
	private WebElement addIMHForm;
	
	@FindBy(how = How.XPATH, using = "//button[@class='mf-cta__primary mf-imh-search-go-button']")
	private WebElement clickOnGoButton;
	
	@FindBy(how = How.XPATH, using = "//button[@class='mf-cta__primary']")
	private WebElement clickOnAddForm;
	
	@FindBy(how = How.XPATH, using = "//div[@title='Appointment types']")
	private WebElement clickOnAppointmentTypesForForms;
	
	@FindBy(how = How.XPATH, using = "//input[@id='select-2e2a986d-cd58-4272-ab91-0546daf77f26']")
	private WebElement selectAppointmentTypeForForms;
	
	@FindBy(how = How.XPATH, using = "//input[@id='select-c1d76bfa-676a-4788-8433-5cb3a91b8a81']")
	private WebElement selectAppointmentTypeForForm;
	
	@FindBy(how = How.XPATH, using = "//p[@class='mf-list__element--primary']")
	private WebElement IMHForm;


	
	public FormsPage(WebDriver driver) {
			super(driver);
			PageFactory.initElements(driver, this);
		}
		
		public void clickOnFormsTab() {
			IHGUtil.waitForElement(driver, 5, clickOnFormsTab);
			jse.executeScript("arguments[0].click();", clickOnFormsTab);
		}
		
		public void clickOnAddFormButton() {
			IHGUtil.waitForElement(driver, 5, clickOnAddFormButton);
			jse.executeScript("arguments[0].click();", clickOnAddFormButton);
		}
		
		public void clickOnMedfusionForm() {
			IHGUtil.waitForElement(driver, 5, clickOnMedfusionForm);
			jse.executeScript("arguments[0].click();", clickOnMedfusionForm);
		}
		
		public void enterFormName(String name) {
			IHGUtil.waitForElement(driver, 5, enterFormName);
			enterFormName.sendKeys(name);
		}
		
		public void enterFormURL(String url) {
			IHGUtil.waitForElement(driver, 5, enterFormURL);
			enterFormURL.sendKeys(url);
		}
		
		public void clickOnAddformButton() {
			IHGUtil.waitForElement(driver, 10, clickOnAddformButton);
			jse.executeScript("arguments[0].click();", clickOnAddformButton);
		}
		
		public boolean visibilityOfForms() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, forms);
			if(forms.isDisplayed()) {
				log("Form is displayed in formgrid");
				return true;
			}
			else {
				log("Form is not displayed in formgrid");
				return false;
			}
		}
		
		public void clickOnAppointmentTypes() throws InterruptedException {
			Actions act = new Actions(driver);
			act.moveToElement(forms).perform();
			Thread.sleep(5000);
			act.click(clickOnAppointmentTypes).perform();
			Thread.sleep(5000);
		}
		
		public void selectAppointmentType() throws InterruptedException {
			IHGUtil.waitForElement(driver, 10, selectAppointmentType);
			selectAppointmentType.click();
			Thread.sleep(3000);
		}
		
		public void clickOnBackArrow() {
			IHGUtil.waitForElement(driver, 10, clickOnBackArrow);
			jse.executeScript("arguments[0].click();", clickOnBackArrow);
		}
		
		public String countOfAppointmentType() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, countOfAppointmentType);
			return countOfAppointmentType.getText();
		}
		
		public void clickOnDeleteForm() throws InterruptedException {
			Actions act = new Actions(driver);
			act.moveToElement(forms).perform();
			Thread.sleep(5000);
			act.click(clickOnDeleteForm).perform();
			Thread.sleep(5000);
		}
		
		public void clickDeleteButton() {
			IHGUtil.waitForElement(driver, 10, clickDeleteButton);
			jse.executeScript("arguments[0].click();", clickDeleteButton);
		}
		
		public void searchForm(String form) throws InterruptedException {
			IHGUtil.PrintMethodName();
			searchForm.sendKeys(form);
			Thread.sleep(5000);
		}
		
		public boolean isFormDisplay(String form) {
			IHGUtil.PrintMethodName();
			boolean visibility = false;
			try {
				visibility=driver.findElement(By.xpath("//p[text()='"+form+"']")).isDisplayed();
					log("Form is displayed");
					return visibility;
				} catch (NoSuchElementException e) {
					log("Form is not displayed");
					return visibility;
				}
		}
		
		public void clickOnIMHForm() {
			IHGUtil.waitForElement(driver, 10, clickOnIMHForm);
			jse.executeScript("arguments[0].click();", clickOnIMHForm);
		}
		
		public void addIMHForm(String form) throws InterruptedException {
			IHGUtil.PrintMethodName();
			addIMHForm.sendKeys(form);
			IHGUtil.waitForElement(driver, 5, clickOnGoButton);
			clickOnGoButton.click();
			if(addIMHForm.isDisplayed()){
				WebElement element = driver.findElement(By.xpath("//div[@class='mf-form__error']"));
				log("form already exists");
				WebElement element1 = driver.findElement(By.xpath("//button[@class='mf-cta__secondary']"));
				element1.click();
			}
			else {
				addIMHForm.sendKeys(form);
				IHGUtil.waitForElement(driver, 5, clickOnGoButton);
				clickOnGoButton.click();
				IHGUtil.waitForElement(driver, 5, clickOnAddForm);
				clickOnAddForm.click();
				log("IMH form added");
			}
		}
		
		public void clickOnAppointmentTypesForForms() throws InterruptedException {
			Actions act = new Actions(driver);
			act.moveToElement(IMHForm).perform();
			Thread.sleep(5000);
			act.click(clickOnAppointmentTypesForForms).perform();
		}
		
		public void selectAppointmentTypeForForms() {
			IHGUtil.waitForElement(driver, 10, selectAppointmentTypeForForms);
			boolean selected = selectAppointmentTypeForForms.isSelected();
			if(!selected) {
				jse.executeScript("arguments[0].click();", selectAppointmentTypeForForms);
				log("select appointment type");
			}
		}
		
		public void selectAppointmentTypeForForm() {
			IHGUtil.waitForElement(driver, 10, selectAppointmentTypeForForm);
			boolean selected = selectAppointmentTypeForForm.isSelected();
			if(!selected) {
				jse.executeScript("arguments[0].click();", selectAppointmentTypeForForm);
				log("select appointment type");
			}
		}
		
		public void deselectAppointmentTypeForForms() {
			IHGUtil.waitForElement(driver, 10, selectAppointmentTypeForForms);
			jse.executeScript("arguments[0].click();", selectAppointmentTypeForForms);
		}

}
		
