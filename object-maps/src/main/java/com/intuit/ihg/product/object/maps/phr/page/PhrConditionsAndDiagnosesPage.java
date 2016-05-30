package com.intuit.ihg.product.object.maps.phr.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PhrConditionsAndDiagnosesPage extends BasePageObject {

	public static final String pageTitle = "Conditions and Diagnoses";
	public static final String noConditions = "No Conditions or Diagnoses Added";

	@FindBy( xpath = ".//*[@class='content_hdr']")
	private WebElement conditionsAndDiagnosesTitle;

	@FindBy( xpath = "//img[@alt='ADD CONDITIONS']")
	private WebElement addConditionsBtn;

	public PhrConditionsAndDiagnosesPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkconditionsAndDiagnosesPage() {
		IHGUtil.PrintMethodName();
		return conditionsAndDiagnosesTitle.getText().contains(pageTitle);
	}

	public void clickOnAddConditions() throws Exception {
		IHGUtil.PrintMethodName();
		WebElement ele = driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td"));
		if(ele.getText().contains(noConditions)){
			selectFirstCheckBox();
			IHGUtil.waitForElement(driver, 30, addConditionsBtn);
			addConditionsBtn.click();
			Thread.sleep(8000);
		}else {
			IHGUtil.waitForElement(driver, 30, addConditionsBtn);
			addConditionsBtn.click();
			Thread.sleep(5000);
			selectFirstCheckBox();
			IHGUtil.waitForElement(driver, 30, addConditionsBtn);
			addConditionsBtn.click();
			Thread.sleep(8000);
		}	
	}

	public void selectFirstCheckBox() {

		driver.switchTo().defaultContent();
		String xpath_AllCheckBox = "//input[@class='custom_form_check_box']";

		List<WebElement> allValues = driver.findElements(By.xpath(xpath_AllCheckBox));

		for(WebElement value : allValues) {
			if(value.isSelected()) {
				continue;
			}else {
				value.click();
				break;
			}
		}

	}

	public void removeDiagnoses(String value) throws Exception {

		driver.switchTo().defaultContent();
		int row_Size = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td/a/span")).size();
		int table_Size = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr")).size();
		int iValue = (table_Size-row_Size)+1;

		for( int i=iValue; i<=table_Size; i++) { 
			WebElement  ele = driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr["+i+"]/td/a/span"));

			if (ele.getText().contains(value)) {
				driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr["+i+"]/td/a/img")).click();
				driver.switchTo().alert().accept();
				Thread.sleep(10000);
				break;
			}else {
				continue;
			}

		}
	}

	public boolean isDiagnosesPresent(String value) throws Exception {
		Boolean isPresent = false;
		Thread.sleep(8000);
		driver.switchTo().defaultContent();
		List<WebElement> allConditions = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td/a/span"));
		for(WebElement cond : allConditions) {
			log("text :"+cond.getText());
			if(cond.getText().contains(value)) {
				isPresent = true;
			} else {
				isPresent = false;
			}
		}
		return isPresent;
	}

}
