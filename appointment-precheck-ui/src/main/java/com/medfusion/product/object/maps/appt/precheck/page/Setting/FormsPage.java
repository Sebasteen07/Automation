package com.medfusion.product.object.maps.appt.precheck.page.Setting;


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
	
	@FindBy(how = How.XPATH, using = "//button[@id='primaryAddform']")
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
}
		
