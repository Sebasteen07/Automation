package com.intuit.ihg.product.practice.page.onlinebillpay;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class PayMyBillOnlinePage extends BasePageObject{
	
	public PayMyBillOnlinePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//table[@class='searchForm']//input[@name='searchParams:0:input']")
	private WebElement firstName;
	
	@FindBy(xpath="//table[@class='searchForm']//input[@name='searchParams:1:input']")
	private WebElement lastName;
	
	@FindBy(xpath="//table[@class='searchForm']//input[@value='Search']")
	private WebElement searchForPatients;
	
	@FindBy(xpath="//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(),'automation, ihgqa')]")
	private WebElement searchResult;
	
	@FindBy(name="locationList")
	private WebElement location;
	
	@FindBy(xpath = "//select[@name='providerList']")
	private WebElement provider;
	
	@FindBy(xpath = "//input[@name='patientAccountNumber']")
	private WebElement patientAccountNumber;
	
	@FindBy(xpath = "//input[@name='paymentAmount']")
	private WebElement paymentAmount;
	
	@FindBy(xpath = "//input[@name='paymentComment']")
	private WebElement paymentComment;
	
	@FindBy(xpath = "//input[@value='radio7']")
	private WebElement donotSaveTransaction;
	
	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:nameOnCreditCard']")
	private WebElement cardHolder;
	
	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:creditCardNumber']")
	private WebElement cardNumber;
	
	@FindBy(xpath = "//select[@name='creditCardPanel:newccdetails:creditCardType']")
	private WebElement cardType;
	
	@FindBy(xpath = "//select[@name='creditCardPanel:newccdetails:expirationMonth']")
	private WebElement expirationMonth;
	
	@FindBy(xpath = "//select[@name='creditCardPanel:newccdetails:expirationYear']")
	private WebElement expirationYear;
	
	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:newccdetailscvv:cvvCode']")
	private WebElement cvvCode;
	
	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:addressZip']")
	private WebElement zipCode;
	
	@FindBy(xpath = "//input[@name='payBillButton']")
	private WebElement payBillButton;
	
	@FindBy(xpath = "//input[@name=':submit']")
	private WebElement submitPaymentButton;
	
	@FindBy(xpath = "//div[@id='content']/div[2]/strong/div/p")
	public WebElement paymentConfirmationText;
	
	@FindBy( xpath = ".//input[@name='voidPayment']")
	private WebElement voidPaymentButton;

	@FindBy( xpath = ".//textarea[@name='comment']")
	private WebElement commentForVoid;
	
	@FindBy( xpath = ".//input[@value='Void']")
	private WebElement voidButton;
	
//	@FindBy( xpath = ".//*[@id='_wicket_window_2']/a")
	@FindBy( xpath = ".//*[@id='_wicket_window_1']//div[@class='w_caption']/a")
	private WebElement closeVoidPopUp;
	
	@FindBy( xpath = ".//input[@value='Refund Payment']")
	private WebElement refundPayment;
	
	@FindBy( xpath = ".//input[@name ='amount']")
	private WebElement amountToRefundField;
	
	@FindBy( xpath = ".//textarea[@name ='comment']")
	private WebElement commentForRefund;
	
	@FindBy( xpath = ".//input[@value='Refund']")
	private WebElement refundButton;
	
	@FindBy( xpath = ".//*[@id='_wicket_window_15']/a")
	private WebElement closeRefundPopUp;
	
	@FindBy( xpath = ".//iframe[contains(@id,'_wicket_window_')]")
	private WebElement iFrameRefundWindow;
	
	/**
	 * @Description:Set First name
	 */
	public void setFirstName()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		firstName.clear();
		firstName.sendKeys(PracticeConstants.PatientFirstName);
	}
	
	/**
	 * @Description:Set Last Name
	 */
	public void setLastName()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		lastName.clear();
		lastName.sendKeys(PracticeConstants.PatientLastName);
	}
	
	/**
	 * @Description:Set Location
	 */
	public void setLocation()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(location);
		try{
			sel.selectByVisibleText(PracticeConstants.Location);
		}
		catch(Exception e)
		{
			sel.selectByVisibleText(PracticeConstants.Location2);
		}
		
	}
	
	
	
	/**
	 * @Description:Choose the provider name from Drop Down
	 * @param pProvider
	 */
	public void chooseProvider(String pProvider)
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,10,provider);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='providerList']/option"));
		for(WebElement li : list)
		{
			int count=1;
			if(li.getText().contains(pProvider))
			{
				Select selectProvider=new Select(provider);
				selectProvider.selectByIndex(count);
				break;
			}
			count++;
		}
		
	}
	
	/**
	 * @Description:Set Account Number
	 * @param accountNumber
	 */
	public void setPatientAccountNumber(String accountNumber)
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		patientAccountNumber.clear();
		patientAccountNumber.sendKeys(accountNumber);
	}
	
	/**
	 * @Description:Set Payment Amount
	 * @param amount
	 */
	public void setPaymentAmount(String amount)
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		paymentAmount.clear();
		paymentAmount.sendKeys(amount);
	}
	
	/**
	 * @Description:Set Payment Comment
	 * @param comment
	 */
	public void setPaymentComment(String comment)
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		paymentComment.clear();
		paymentComment.sendKeys(comment);
	}
	
	/**
	 * @Description:Set Don't Save Transaction radio button
	 * @throws Exception
	 */
	public void clickDonotSaveTransaction() throws Exception
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,30,donotSaveTransaction);
		donotSaveTransaction.click();
		Thread.sleep(4000);
	}
	
	/**
	 * @Description:Set Card Holder Name
	 * @throws Exception
	 */
	public void setCardholderName() throws Exception
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,30,cardHolder);
		cardHolder.clear();
		cardHolder.sendKeys(PracticeConstants.CardHolderName);
		Thread.sleep(4000);
		
	}
	
	/**
	 * @Description:Set Card Number
	 * @throws Exception
	 */
	public void setCardNumber() throws Exception
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,30,cardNumber);
		cardNumber.clear();
		cardNumber.sendKeys(PracticeConstants.CardNumber);
		Thread.sleep(4000);
		
	}
	
	/**
	 * @Description:Set Credit Card Type
	 */
	public void setCardType()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(cardType);
		sel.selectByVisibleText(PracticeConstants.CardType);
		
		
	}
	
	/**
	 * @Description:Set Expiration date
	 */
	public void setExpirationDate()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(expirationMonth);
		sel.selectByVisibleText(PracticeConstants.ExpirationMonth);
		Select sele=new Select(expirationYear);
		sele.selectByVisibleText(PracticeConstants.ExpirationYear);
		
	}
	/**
	 * @Description:Set CCV Code
	 */
	public void setCCVCode()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		cvvCode.clear();
		cvvCode.sendKeys(PracticeConstants.CCVCode);
		
	}
	
	/**
	 * @Description:Set Zip Code
	 */
	public void setZipCode()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		zipCode.clear();
		zipCode.sendKeys(PracticeConstants.ZipCode);
		
	}
	
	
	/**
	 * @Description:Search For Patient
	 * @throws Exception
	 */
	
	public void searchForPatient() throws Exception
	{
		IHGUtil.PrintMethodName();
		setFirstName();
		setLastName();
		searchForPatients.click();
		IHGUtil.waitForElement(driver,30,searchResult);
		searchResult.click();
		Thread.sleep(10000);
	}
	
	/**
	 *@Description:Set Patient Transaction Fields
	 * @throws Exception
	 */
	public void setPatientTransactionFields() throws Exception
	{
		IHGUtil.PrintMethodName();
		setLocation();
		chooseProvider(PracticeConstants.Provider);
		setPatientAccountNumber(IHGUtil.createRandomNumericString().substring(0,5));
		setPaymentAmount(IHGUtil.createRandomNumericString().substring(0, 2));
		try
		{
			setPaymentComment(PracticeConstants.PaymentComment.concat(IHGUtil.createRandomNumericString()));
		}
		catch(Exception e)
		{
			log("Payment Comment Field is not displayed");
		}
		
		clickDonotSaveTransaction();
		
		try
		{
			setCardholderName();
			setCardNumber();
			setCardType();
			setExpirationDate();
			setCCVCode();
			setZipCode();
			
		}
		catch(Exception e)
		{
			log("Card details is already added");
		}
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		payBillButton.click();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		submitPaymentButton.click();
		
	}
	
	public void searchForPatient( String fName, String lName) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		firstName.clear();
		firstName.sendKeys(fName);
		lastName.clear();
		lastName.sendKeys(lName);

		searchForPatients.click();
		IHGUtil.waitForElement(driver,30,searchResult);

		driver.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(),'"+lName+", "+fName+"')]")).click();
		Thread.sleep(10000);

	}


	public void setTransactionsForOnlineBillPayProcess(String acctNum, String amount, String cardHolderName, String cardNum, String cardTyp) throws Exception {
		IHGUtil.PrintMethodName();
		setLocation();
		chooseProvider(PracticeConstants.Provider);
		setPatientAccountNumber(acctNum);
		setPaymentAmount(amount);

		try
		{
			setPaymentComment(PracticeConstants.PaymentComment.concat(IHGUtil.createRandomNumericString()));
		}
		catch(Exception e)
		{
			log("Payment Comment Field is not displayed");
		}

		clickDonotSaveTransaction();

		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver,30,cardHolder);
		cardHolder.clear();
		cardHolder.sendKeys(cardHolderName);
		Thread.sleep(4000);

		IHGUtil.waitForElement(driver,30,cardNumber);
		cardNumber.clear();
		cardNumber.sendKeys(cardNum);
		Thread.sleep(4000);

		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(cardType);
		sel.selectByVisibleText(cardTyp);

		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select selexp =new Select(expirationMonth);
		selexp.selectByVisibleText(PracticeConstants.ExpirationMonth);
		Select sele=new Select(expirationYear);
		sele.selectByVisibleText(PracticeConstants.ExpirationYear);

		cvvCode.clear();
		cvvCode.sendKeys(PracticeConstants.CCVCode);

		zipCode.clear();
		zipCode.sendKeys(PracticeConstants.ZipCode);

		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		payBillButton.click();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		submitPaymentButton.click();
		Thread.sleep(5000);

	}
	
	public String voidPayment(String voidComment) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, voidPaymentButton);
		driver.switchTo().frame("iframe");
		voidPaymentButton.click();
	
		Thread.sleep(5000);
		driver.switchTo().activeElement();
//		driver.switchTo().frame("_wicket_window_3");
		driver.switchTo().frame(iFrameRefundWindow);
		commentForVoid.sendKeys(voidComment);
		voidButton.click();
		Thread.sleep(5000);
		
		String errorText =driver.findElement(By.xpath(".//form[@id='voidRefund']//div[@class='warn']//span")).getText();
		log("Error*******" +errorText);
		
		
		PracticeUtil pUtil = new PracticeUtil(driver);
		log("active****"+driver.switchTo().activeElement().getAttribute("name"));
		driver.switchTo().defaultContent();
		driver.switchTo().activeElement();
		Thread.sleep(5000);
		pUtil.tabBrowsing(1);
		pUtil.tabBack(2);
		pUtil.pressEnterKey();
		Thread.sleep(3000);
		return errorText;
	}


	
	public void refundPayment(String refundAmount, String refundComment) throws Exception {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframe");
		refundPayment.click();
		driver.switchTo().activeElement();
		Thread.sleep(3000);
//		driver.switchTo().frame("_wicket_window_16");
		driver.switchTo().frame(iFrameRefundWindow);
		Thread.sleep(3000);
		PracticeUtil pUtil = new PracticeUtil(driver);
		pUtil.tabBack(2);
		amountToRefundField.sendKeys(refundAmount);
		commentForRefund.sendKeys(refundComment);
		refundButton.click();
		Thread.sleep(5000);
	}
	

}
