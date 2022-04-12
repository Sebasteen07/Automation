//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.payreporting.pojo.TransactionDetailInfo;

public class ReportingTransactionDetail {
	protected WebDriver driver;

	public ReportingTransactionDetail(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//Transaction detail page
	@FindBy(how = How.ID, using = "refBtn")
	public WebElement refundButton;

	@FindBy(how = How.ID, using = "creditSubmit")
	private WebElement refundSubmitButton;

	@FindBy(how = How.ID, using = "amount")
	private WebElement refundAmount;

	@FindBy(how = How.ID, using = "voidBtn")
	public WebElement voidButton;

	@FindBy(how = How.ID, using = "voidSubmit")
	private WebElement voidSubmitButton;

	@FindBy(how = How.ID, using = "closeBtn")
	private WebElement closeButton;

	@FindBy(how = How.ID, using = "viewReceipt")
	private WebElement viewReceiptButton;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[1]/td[2]")
	private WebElement activityDateDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[2]/td[2]")
	private WebElement fundedDateDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[3]/td[2]")
	private WebElement paymentSourceDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[4]/td[2]")
	private WebElement locationDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[5]/td[2]")
	private WebElement staffNameDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[6]/td[2]")
	private WebElement patientNameDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[7]/td[2]")
	private WebElement accountNumberDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[8]/td[2]")
	private WebElement cardTypeDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[9]/td[2]")
	private WebElement cardHolderDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[10]/td[2]")
	private WebElement cardNumberDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[11]/td[2]")
	private WebElement orderIdDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[11]/td[2]")
	private WebElement transactionIdDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[15]/td[2]")
	private WebElement statusDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[16]/td[2]")
	private WebElement paymentDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[17]/td[2]")
	private WebElement refundDetail;

	@FindBy(how=How.XPATH,using ="//*[@id='transactionTable']/tbody/tr[12]/td[2]")
	private WebElement transactionType;

	@FindBy(how = How.XPATH, using = "//*[@id='void-refund-message']/div[1]/span")
	private WebElement voidRefundMessage;

	@FindBy(how = How.XPATH, using = "//*[@id='receiptContent']/dv/h5")
	private WebElement refundTransaction;

	@FindBy(how = How.ID, using = "viewParent")
	private WebElement viewParentButton;

	@FindBy(how = How.XPATH, using = "//*[@id='receiptContent']/dv/h5")
	private WebElement merchantNameInVoidRefundTransaction;

	@FindBy(how = How.XPATH, using = "//*[@id='receiptTable']/tbody/tr[2]/td[2]")
	private WebElement refundTransactionID;

	public String getTransactionDetailActivity() { 
		return activityDateDetail.getText();
	}
	public String getTransactionDetailFunded(){ 
		return fundedDateDetail.getText();
	}
	public String getTransactionDetailSource(){ 
		return paymentSourceDetail.getText();
	}
	public String getTransactionDetailLocation(){ 
		return locationDetail.getText();
	}
	public String getTransactionDetailStaff(){ 
		return staffNameDetail.getText();
	}
	public String getTransactionDetailPatient(){ 
		return patientNameDetail.getText();
	}
	public String getTransactionDetailAccount(){ 
		return accountNumberDetail.getText();
	}
	public String getTransactionDetailCardType(){ 
		return cardTypeDetail.getText();
	}
	public String getTransactionDetailCardHolder(){ 
		return cardHolderDetail.getText();
	}
	public String getTransactionDetailCardNumber(){ 
		return cardNumberDetail.getText();
	}
	public String getTransactionDetailOrderId(){
		return orderIdDetail.getText();
	}
	public String getTransactionDetailTransactionId(){ 
		return transactionIdDetail.getText();
	}
	public String getTransactionDetailStatus(){ 
		return statusDetail.getText();
	}
	public String getTransactionDetailPayment(){ 
		return paymentDetail.getText();
	}
	public String getTransactionDetailRefund(){ 
		return refundDetail.getText();
	}
	public String getTransactionDetailType(){ 
		return transactionType.getText();
	}

	public void clickVoidButton(){
		voidButton.click();
	}
	public void clickCloseButton(){
		closeButton.click();
	}

	public void clickRefundButton(){
		refundButton.click();
	}

	public void clickVoidSubmitButton(){
		voidSubmitButton.click();
	}

	public void clickRefundSubmitButton(){
		refundSubmitButton.click();
	}	

	public void fillRefundAmount(String amount){
		refundAmount.clear();
		refundAmount.sendKeys(amount);
	}


	public ReportingDailyReportPage closeTransactionDetail(){
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
		return PageFactory.initElements(driver, ReportingDailyReportPage.class);
	}

	public void assertTransactionDetailObjects(boolean withRefund){

		WebDriverWait wait = new WebDriverWait(driver, 23);
		wait.until(ExpectedConditions.visibilityOf(activityDateDetail));
		Log4jUtil.log("Void Button is present");
		if (withRefund) {
			Assert.assertTrue("Refund button is not present", refundButton.isDisplayed());
			Log4jUtil.log("Success:Refund button is present.");
		} else {
			Assert.assertFalse("Refund button is present", refundButton.isDisplayed());
			Log4jUtil.log("Success:Refund button is not present.");
		}
		Assert.assertTrue("Close button is not present", closeButton.isDisplayed());
		Assert.assertEquals("Payment", getTransactionDetailType());
		Assert.assertEquals("VCS", getTransactionDetailSource());		
	}
	public void waitForDetailActivityDate(){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(activityDateDetail));
	}
	public boolean isDisplayedVoid(){
		return voidButton.isDisplayed();
	}

	public boolean isDisplayedRefund(){
		return refundButton.isDisplayed();
	}

	public void assertTransactionDetailObjectsForQBMSPaypal(){
		assertTransactionDetailObjects(false);
	}

	public void assertTransactionDetailObjectsForLitleElement(){
		assertTransactionDetailObjects(true);
	}

	public void performRefund(String refundamount) throws InterruptedException{
		//Need to use javascriptto perform click because of the transaction detail modal overlay issue during automation run
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", refundButton); 
		jse.executeScript("arguments[0].click();", refundAmount); 
		Thread.sleep(3000);
		refundAmount.sendKeys(refundamount);
		Assert.assertTrue("Refund submit button is not present!", refundSubmitButton.isDisplayed());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", refundSubmitButton);
	}

	public String getVoidRefundMessage(){
		WebDriverWait wait = new WebDriverWait(driver, 15);
		String status=wait.until(ExpectedConditions.visibilityOf(voidRefundMessage)).getText();
		Assert.assertNotNull("Refund message not displayed", status);	
		return status;
	}


	public void verifyRefundTransaction(){
		Assert.assertEquals("Refund", getTransactionDetailType());
		Assert.assertTrue("View Parent button is not present", viewParentButton.isDisplayed());
		Assert.assertTrue("Refund button is present in the refund transaction!",!refundButton.isDisplayed());
		closeButton.click();
	}

	public int countRows(){
		int rowcount=driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		return rowcount;
	}
	

	public void verifyVoidTransaction(){
		Assert.assertEquals(transactionType.getText(), "Void");
		Assert.assertTrue(!refundButton.isDisplayed());
		Assert.assertTrue(!voidButton.isDisplayed());
		Assert.assertTrue(closeButton.isDisplayed());
	}

	public void assertOnDeclinedTransactions() throws InterruptedException{
		int rowcount=countRows();
		for(int count=1;count<=rowcount;count++) {
			WebElement eye=driver.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr["+count+"]/td[15]/a/span"));
			eye.click();
			Thread.sleep(6000);
			String transaction=transactionIdDetail.getText();
			System.out.println("Verifying transaction ID:"+transaction);
			Assert.assertTrue("Void button is seen on declined transaction", !voidButton.isDisplayed());
			Assert.assertTrue("Refund button is seen on declined transaction", !refundButton.isDisplayed());
			Assert.assertTrue(activityDateDetail.isDisplayed());
			Assert.assertEquals("Declined", statusDetail.getText());
			closeTransactionDetail();
		}
	}
	
	public void assertOnChargebackTransactions() throws InterruptedException{
		int rowcount=countRows();
		for(int count=1;count<=rowcount;count++) {
			WebElement eye=driver.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr["+count+"]/td[15]/a/span"));
			eye.click();
			Thread.sleep(6000);
			String transaction=transactionIdDetail.getText();
			System.out.println("Verifying transaction ID:"+transaction);
			Assert.assertTrue("Void button is seen on declined transaction", !voidButton.isDisplayed());
			Assert.assertTrue("Refund button is seen on declined transaction", !refundButton.isDisplayed());
			Assert.assertTrue(activityDateDetail.isDisplayed());
			Assert.assertEquals("CHBK",paymentSourceDetail.getText());
			Assert.assertTrue(cardTypeDetail.isDisplayed());
			//Assert.assertEquals("Chargeback", transactionType.getText());
			Assert.assertTrue(viewParentButton.isDisplayed());
			closeTransactionDetail();
		}
	}
	
	public String viewReceipt(){
		Assert.assertTrue("View Receipt button is not present", viewReceiptButton.isDisplayed());
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewReceiptButton);
		Assert.assertTrue("Merchant name is not present in the refund receipt", merchantNameInVoidRefundTransaction.isDisplayed());
		String transactionid=refundTransactionID.getText();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewReceiptButton);
		return transactionid;
	}

	public void performVoid( ) throws InterruptedException{
		//Need to use javascriptto perform click because of the transaction detail modal overlay issue during automation run
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", voidButton);
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(voidSubmitButton));
		jse.executeScript("arguments[0].click();", voidSubmitButton); 
		verifyVoid();		
	}	

	public void verifyVoid(){

		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(voidRefundMessage));
		String status = getVoidRefundMessage();
		Log4jUtil.log("Void status: " + status);
		Assert.assertTrue("Void was not approved!", status.equalsIgnoreCase("Success")||status.equalsIgnoreCase("Approved"));				
	}

	public TransactionDetailInfo getTransactionDetailInfo(){
		return new TransactionDetailInfo(this.activityDateDetail.getText(), this.fundedDateDetail.getText(),
				this.paymentSourceDetail.getText(), this.locationDetail.getText(), this.staffNameDetail.getText(),
				this.patientNameDetail.getText(), this.accountNumberDetail.getText(), this.cardTypeDetail.getText(),
				this.cardHolderDetail.getText(), this.cardNumberDetail.getText(), this.orderIdDetail.getText(), this.transactionIdDetail.getText(),
				this.transactionType.getText(), this.statusDetail.getText(), this.paymentDetail.getText(),
				this.refundDetail.getText(), this.isDisplayedVoid(), this.isDisplayedRefund());
	}
}
