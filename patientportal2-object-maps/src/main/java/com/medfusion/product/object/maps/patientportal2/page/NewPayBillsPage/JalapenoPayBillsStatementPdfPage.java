package com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class JalapenoPayBillsStatementPdfPage extends BasePageObject {
	
	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[5]")
	private WebElement accountNumber;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[6]")
	private WebElement dueDate;

	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[7]")
	private WebElement amountDue;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[11]")
	private WebElement practiceName;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[34]")
	private WebElement dunningMessage;
	
    @FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[15]")
	private WebElement totalCharges;
    
    @FindBy(how = How.XPATH, using = "/html/body/div[1]/div[2]/div[4]/div/div[1]/div[2]/div[27]")
	private WebElement pleasePay;
    
	public JalapenoPayBillsStatementPdfPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
	}
	
	public String getAccountNumber() {
		return accountNumber.getText();
	}
	
	public String getDueDate() {
		return dueDate.getText();
	}
	
	public String getAmountDue() {
		return amountDue.getText();
	}
	
	public String getPracticeName() {
		return practiceName.getText();
	}
	
	public String getDunningMessage() {
		return dunningMessage.getText();
	}
	
	public String getTotalCharges() {
		return totalCharges.getText();
	}
	
	public String getPleasePay() {
		return pleasePay.getText();
	}
}
