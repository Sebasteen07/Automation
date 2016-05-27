package com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.utils.PhrConstants;

public class VitalSignsPage extends BasePageObject {

	private static String noResultText = "No Vital Signs Added";
	private static String title = "Vital Signs";

	@FindBy( xpath = ".//td[@class= 'content_hdr']")
	private WebElement vitalSignsTitle;

	@FindBy( id = "vitalSign0")
	private WebElement weightField;

	@FindBy( id = "vitalSign1")
	private WebElement heightField;

	@FindBy( id = "vitalSign2")
	private WebElement bloodPressureField ;

	@FindBy( xpath = ".//input[@name='vitalSign[2].finding2']")
	private WebElement bpsecondField;

	@FindBy( id = "vitalSign3" )
	private WebElement temperatureField;

	@FindBy( id = "vitalSign4")
	private WebElement pulseField;

	@FindBy( id = "vitalSign5")
	private WebElement respirationRateField;

	@FindBy( id = "month")
	private WebElement monthDropDwn;

	@FindBy( id = "day")
	private WebElement dayDropDwn;

	@FindBy( id = "year")
	private WebElement yearDropDwn;

	@FindBy( xpath = ".//input[@value='Save Vital Signs']")
	private WebElement saveVitalSignButton;

	@FindBy( xpath = ".//input[@value='Add Vital Signs']")
	private WebElement addVitalSignsButton;

	public VitalSignsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkVitalSignsPage() {
		IHGUtil.PrintMethodName();
		return vitalSignsTitle.getText().contains(title);
	}

	public void addVitalSigns(String vitalSign) throws Exception {
		WebElement tableList = driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td"));
		if(tableList.getText().contains(noResultText)) {
			addVitalSignsInfo(vitalSign);
		} else if(addVitalSignsButton.isDisplayed()) {
			addVitalSignsButton.click();
			addVitalSignsInfo(vitalSign);
		}
	}

	public void addVitalSignsInfo(String vitalSign ) throws Exception {

		Select selMonth = new Select(monthDropDwn);
		selMonth.selectByValue(PhrConstants.valueForMonth);
		Select selDay = new Select(dayDropDwn);
		selDay.selectByValue(PhrConstants.valueForMonth);
		Select selYear = new Select(yearDropDwn);
		selYear.selectByIndex(1);

		weightField.sendKeys(vitalSign);


		IHGUtil.waitForElement(driver, 30, saveVitalSignButton);
		saveVitalSignButton.click();
		Thread.sleep(8000);
	}


	public void removeVitalSigns(String value) throws Exception {

		driver.switchTo().defaultContent();
		int row_Size = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td/a/span")).size();
		int table_Size = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr")).size();
		int iValue = (table_Size-row_Size)+1;

		for( int i=iValue; i<=table_Size; i++) { 
//			DateFormat formatter;
//			Date date1;
//			Calendar cal;
//			long scalmili;
//			formatter = new SimpleDateFormat("MM/dd/yyyy");


			WebElement  ele = driver.findElement(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr["+i+"]/td/a/span"));
			log("textttttt :"+ele.getText());
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

	public boolean isAdded(String value) {
		Boolean isAdded = false;
		List<WebElement> all = driver.findElements(By.xpath(".//*[@id='custom_table_list_id']/tbody/tr/td"));
		for(WebElement each : all) {
			log("Date :"+each.getText());
			if( each.getText().contains(value)) {
				isAdded = true;
				break;
			} else{
				log("Vital sign is not added.");
				isAdded =false;
				continue;
			}
		}
		return isAdded;
	}

}
