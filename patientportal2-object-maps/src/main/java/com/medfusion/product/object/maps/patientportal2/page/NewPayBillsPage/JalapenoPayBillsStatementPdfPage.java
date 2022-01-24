package com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    
    @FindBy(how=How.XPATH,using="//div[@id='view_pdf2']")
    private WebElement statementPDF;
    
    @FindBy(how=How.XPATH,using="//a[@id='obplink']")
    private WebElement statementsHistory;
    
    @FindBy(how=How.XPATH,using="//*[@id=\"statementHistoryDiv\"]//table//tr[1]//td[2]")
    private WebElement statementDetails;
    
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

	public boolean isStatementPDFdisplayed(WebDriver driver){
		if(statementPDF.isDisplayed())
		{
			log("Statement PDF is displayed");
			return true;
		}
		else
		{
			log("Statement PDF is not displayed");
			return false;
		}
					
	}

	public void clickOnStatementsHistory(){
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(statementsHistory));
		statementsHistory.click();		
	}
	
	public void showStatementDetails(){
		statementDetails.click();
	}
	
	
}
