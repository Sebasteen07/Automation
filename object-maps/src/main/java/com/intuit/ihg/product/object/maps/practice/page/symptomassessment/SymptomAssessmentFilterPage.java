package com.intuit.ihg.product.object.maps.practice.page.symptomassessment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class SymptomAssessmentFilterPage extends BasePageObject {

		
	@FindBy(name="month_1")
	private WebElement dropDownFromMonth;
		
	@FindBy(name="day_1")
	private WebElement dropDownFromDay;
	
	@FindBy(name="year_1")
	private WebElement dropDownFromYear;
		
	@FindBy(name="month_2")
	private WebElement dropDownToMonth;
		
	@FindBy(name="day_2")
	private WebElement dropDownToDay;
	
	@FindBy(name="year_2")
	private WebElement dropDownToYear;
	
	@FindBy(id="sasearch")
	private WebElement btnFilterSymptomAssessment;
	
	@FindBy(css="td.searchResultsDetails > a")
	private WebElement lnkView;
	
			
	public SymptomAssessmentFilterPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Gives an indication if the page loaded as expected
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = dropDownFromMonth.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found exceptions
		}
		
		return result;
	}
	
	
	public SymptomAssessmentDetailsPage searchSymptomAssessment() throws Exception
	{			
		Select start_m = new Select(dropDownFromMonth);
		start_m.selectByVisibleText(IHGUtil.getDate_Month());
		
		Select start_d = new Select(dropDownFromDay);
		start_d.selectByVisibleText(IHGUtil.getDate_d());
		
		Select start_y = new Select(dropDownFromYear);
		start_y.selectByVisibleText(IHGUtil.getDate_y());
		
		Select end_m = new Select(dropDownToMonth);
		end_m.selectByVisibleText(IHGUtil.getDate_Month());
		
		Select end_d = new Select(dropDownToDay);
		end_d.selectByVisibleText(IHGUtil.getDate_d());
		
		Select end_y = new Select(dropDownToYear);
		end_y.selectByVisibleText(IHGUtil.getDate_y());
		
		btnFilterSymptomAssessment.click();
		
		log("click on view button");
		lnkView.click();
		Thread.sleep(10000);
		
		return PageFactory.initElements(driver, SymptomAssessmentDetailsPage.class);
	}
	

	
}
