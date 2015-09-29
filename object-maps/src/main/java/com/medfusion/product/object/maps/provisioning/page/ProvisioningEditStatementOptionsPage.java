package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningEditStatementOptionsPage extends BasePageObject {

	public ProvisioningEditStatementOptionsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	//Controls
	@FindBy(how = How.XPATH, using="//nav[@id='top-nav']//button[contains(@data-ng-click,'logout()')]")
	public WebElement logoutButton;	
	@FindBy(how = How.XPATH, using="//button[@type='submit']")
	public WebElement submitButton;	
	@FindBy(how = How.XPATH, using="//button[@class='btn btn-secondary actionButton']")
	public WebElement cancelButton;
	
	//Statement options
	@FindBy(how = How.XPATH, using="//*[@id='merchantTagLine']")
	public WebElement merchantTagline;	
	@FindBy(how = How.XPATH, using="//*[@id='merchantLogoFilename']")
	public WebElement statementLogoName;
	@FindBy(how = How.XPATH, using="//*[@id='payOrBillByPhoneNumber']")
	public WebElement payByPhoneNumber;	
	@FindBy(how = How.XPATH, using="//*[@id='payOrBillByPhoneHours']")
	public WebElement payByPhoneHours;
	@FindBy(how = How.XPATH, using="//*[@id='payOrBillBillQueryNumber']")
	public WebElement billQueryPhoneNumber;
	@FindBy(how = How.XPATH, using="//*[@id='payOrBillBillQueryHours']")
	public WebElement billQueryHours;
	@FindBy(how = How.XPATH, using="//*[@id='detailsAgingBoxes']")
	public WebElement displayAgingBoxes;
	@FindBy(how = How.XPATH, using="//*[@id='detailsInsuranceAgingBoxes']")
	public WebElement displayInsuranceBoxes;
	@FindBy(how = How.XPATH, using="//*[@id='detailsStatementDetail']")
	public WebElement displayDetails;	
	@FindBy(how = How.XPATH, using="//*[@id='payOrBillByCheck']")
	public WebElement payByCheck;
	@FindBy(how = How.XPATH, using="//*[@id='payOrBillByMoneyOrder']")
	public WebElement payByMoneyOrder;
	@FindBy(how = How.XPATH, using="//*[@id='detailsDetachReturnByMail']")
	public WebElement displayDetachReturnByMail;
	@FindBy(how = How.XPATH, using="//*[@id='detailsMerchantName']")
	public WebElement displayMerchantName;
	
	
		
	
	public boolean verifySettings(String merchantTagline, String payByPhoneNumber, 
			String payByPhoneHours, String billQueryPhoneNumber, String billQueryHours, boolean displayAgingBoxes,
			boolean displayInsuranceBoxes, boolean displayDetails, boolean payByCheck, 
			boolean payByMoneyOrder, boolean displayDetachReturnByMail, boolean displayMerchantName){
		IHGUtil.PrintMethodName();
		log("Expecting: " + merchantTagline + payByPhoneNumber +
				payByPhoneHours + billQueryPhoneNumber + billQueryHours + displayAgingBoxes + 
				displayInsuranceBoxes + displayDetails + payByCheck + payByMoneyOrder + 
				displayDetachReturnByMail + displayMerchantName);
		log("Found: " + this.merchantTagline.getAttribute("value") + this.payByPhoneNumber.getAttribute("value") + this.payByPhoneHours.getAttribute("value") + 
				this.billQueryPhoneNumber.getAttribute("value") + this.billQueryHours.getAttribute("value") + this.displayAgingBoxes.isSelected() + 
				this.displayInsuranceBoxes.isSelected() + this.displayDetails.isSelected() + this.payByCheck.isSelected() + 
				this.payByMoneyOrder.isSelected() + this.displayDetachReturnByMail.isSelected() + this.displayMerchantName.isSelected());
		if (merchantTagline.equals(this.merchantTagline.getAttribute("value")) 				
				&& payByPhoneNumber.equals(this.payByPhoneNumber.getAttribute("value"))
				&& payByPhoneHours.equals(this.payByPhoneHours.getAttribute("value"))
				&& billQueryPhoneNumber.equals(this.billQueryPhoneNumber.getAttribute("value"))
				&& billQueryHours.equals(this.billQueryHours.getAttribute("value"))
				&& displayAgingBoxes == (this.displayAgingBoxes.isSelected())
				&& displayInsuranceBoxes == (this.displayInsuranceBoxes.isSelected())
				&& displayDetails == (this.displayDetails.isSelected())
				&& payByCheck == (this.payByCheck.isSelected())
				&& payByMoneyOrder == (this.payByMoneyOrder.isSelected())
				&& displayDetachReturnByMail == (this.displayDetachReturnByMail.isSelected())
				&& displayMerchantName == (this.displayMerchantName.isSelected())
				) 
			 return true;
		else return false;	
	}
	/**
	 * 
	 * @param clear
	 * @param merchantTagline
	 * @param statementLogoName
	 * @param payByPhoneNumber
	 * @param payByPhoneHours
	 * @param billQueryPhoneNumber
	 * @param billQueryHours
	 * @param displayAgingBoxes
	 * @param displayInsuranceBoxes
	 * @param displayDetails
	 * @param payByCheck
	 * @param payByMoneyOrder
	 * @param displayDetachReturnByMail
	 * @param displayMerchantName
	 * @return
	 */
	public ProvisioningMerchantDetailPage fillSettingsAndSubmit(boolean clear, String merchantTagline, String payByPhoneNumber, 
			String payByPhoneHours, String billQueryPhoneNumber, String billQueryHours, boolean displayAgingBoxes,
			boolean displayInsuranceBoxes, boolean displayDetails, boolean payByCheck, 
			boolean payByMoneyOrder, boolean displayDetachReturnByMail, boolean displayMerchantName){
		IHGUtil.PrintMethodName();
		//Load ajax, prevent race condition on sendKeys
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(clear) this.merchantTagline.clear();
		this.merchantTagline.sendKeys(merchantTagline);
		/*
		if(clear) this.statementLogoName.clear();
		this.statementLogoName.sendKeys(statementLogoName);
		*/
		if(clear) this.payByPhoneNumber.clear();
		this.payByPhoneNumber.sendKeys(payByPhoneNumber);
		if(clear) this.payByPhoneHours.clear();
		this.payByPhoneHours.sendKeys(payByPhoneHours);
		if(clear) this.billQueryPhoneNumber.clear();
		this.billQueryPhoneNumber.sendKeys(billQueryPhoneNumber);
		if(clear) this.billQueryHours.clear();
		this.billQueryHours.sendKeys(billQueryHours);
		if((!displayAgingBoxes && displayInsuranceBoxes)||(!payByMoneyOrder && displayDetachReturnByMail)) throw new IllegalArgumentException("DISPLAY AGING BOXES must be true for DISPLAY INSURANCE AGING BOXES, PAY BY MONEY ORDER must be true for DISPLAY DETACH RETURN BY MAIL");
		if(displayAgingBoxes) {
			if(!this.displayAgingBoxes.isSelected()) this.displayAgingBoxes.click();
		}
		else {
			if(this.displayAgingBoxes.isSelected()) this.displayAgingBoxes.click();
		}	
		if(displayInsuranceBoxes) {
			if(!this.displayInsuranceBoxes.isSelected()) this.displayInsuranceBoxes.click();
		}
		else {
			if(this.displayInsuranceBoxes.isSelected()) this.displayInsuranceBoxes.click();
		}
		
		if(displayDetails) {
			if(!this.displayDetails.isSelected()) this.displayDetails.click();
		}
		else {
			if(this.displayDetails.isSelected()) this.displayDetails.click();
		}
		
		if(payByCheck) {
			if(!this.payByCheck.isSelected()) this.payByCheck.click();
		}
		else {
			if(this.payByCheck.isSelected()) this.payByCheck.click();
		}
		if(payByMoneyOrder) {
			if(!this.payByMoneyOrder.isSelected()) this.payByMoneyOrder.click();
		}
		else {
			if(this.payByMoneyOrder.isSelected()) this.payByMoneyOrder.click();
		}
		if(displayDetachReturnByMail) {
			if(!this.displayDetachReturnByMail.isSelected()) this.displayDetachReturnByMail.click();
		}
		else {
			if(this.displayDetachReturnByMail.isSelected()) this.displayDetachReturnByMail.click();
		}
		if(displayMerchantName) {
			if(!this.displayMerchantName.isSelected()) this.displayMerchantName.click();
		}
		else {
			if(this.displayMerchantName.isSelected()) this.displayMerchantName.click();
		}
		
		submitButton.click();
		return PageFactory.initElements(driver, ProvisioningMerchantDetailPage.class);	
	}
	
	public void waitTillLoaded() {		
		log("Loading, waiting for merchant ID element");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//form[@name='addStatement']"))));		
	}

	
	public ProvisioningMerchantDetailPage clickCancel(){
		cancelButton.click();
		return PageFactory.initElements(driver, ProvisioningMerchantDetailPage.class);	
	}
}
