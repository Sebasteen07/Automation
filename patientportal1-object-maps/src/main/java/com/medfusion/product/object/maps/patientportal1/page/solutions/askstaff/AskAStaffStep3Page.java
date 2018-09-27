package com.medfusion.product.object.maps.patientportal1.page.solutions.askstaff;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class AskAStaffStep3Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Page - Step 3";

	@FindBy(linkText = "Back to My Patient Page")
	private WebElement lnkBackToMyPatientPage;

	@FindBy(linkText = "History")
	private WebElement lnkHistory;
	
	@FindBy(xpath = "//*[@id=\'pageContent\']/div/table/tbody/tr/td/div/p[1]")
	private WebElement comfirmMessage;

	public AskAStaffStep3Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Sends user back to My Patient Page
	 * 
	 * @return MyPatientPage
	 */
	public MyPatientPage clickBackToMyPatientPage() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		lnkBackToMyPatientPage.click();

		return PageFactory.initElements(driver, MyPatientPage.class);
	}

	/**
	 * Sends user to Ask A Staff History page
	 * 
	 * @return AskAStaffHistoryPage
	 */
	public AskAStaffHistoryPage clickAskAStaffHistory() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);		
		lnkHistory.click();		
		log("waiting up to 120 seconds for the history list to load");
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)				
				.ignoring(WebDriverException.class);	
		wait.until(new Function<WebDriver, Boolean>() {
			     public Boolean apply(WebDriver driver) {			    		
			 		return driver.findElement(By.xpath(".//table[@id='MfAjaxFallbackDefaultDataTable']/tbody/tr/td[3]/span/table/tbody/tr/td[2]/span")).isDisplayed();
			       }
			     }
				);			
		return PageFactory.initElements(driver, AskAStaffHistoryPage.class);
	}
	
	public boolean askaConfirmationDisplayed() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 15, comfirmMessage);
				
		return comfirmMessage.getText().contains("Thank you for submitting your question");
	}

}
