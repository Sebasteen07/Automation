package com.intuit.ihg.product.integrationplatform.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class SecureExchangeEmailPage {
	WebDriver driver;
	
	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Date, Descending')]")
	public WebElement searchOrder;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[5]/ul/li/a")
	public WebElement viewTOC;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Hi pmemrqa')]")
	public WebElement userName;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Sign Out')]")
	public WebElement signOut;

	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[4]/div[3]/span[1]/span")
	public WebElement fromEmailID;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[4]/div[3]/span[3]/span")
	public WebElement toEmailID;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[5]/ul/li/span[2]")
	public WebElement attachmentName;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[6]/div[3]/div")
	public WebElement subjectBody;
	
	public SecureExchangeEmailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public SecureExchangeEmailPage verifySecureEmail(String subject,String AttachmentType,String fileName,String toEmail,String fromEmail,String attachTOCName) throws InterruptedException {
		Log4jUtil.log("Switching frame");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("webMailFrame");
		Log4jUtil.log("SearchBy  "+searchOrder.getText());
		
		Thread.sleep(3000);
		WebElement secureEmail =driver.findElement(By.xpath("//*[contains(text(),'"+subject+"')]"));
		Log4jUtil.log("Verify Subject if matched actual "+secureEmail.getText()+" expected "+subject);
		Assert.assertEquals(secureEmail.getText(),subject);
		
		Log4jUtil.log("Secure Exchange Step 1: Verfiy Secure Message ");
		secureEmail.click();
		Thread.sleep(3000);
		Log4jUtil.log("Verify From Email address actual "+fromEmailID.getText()+" expected is "+fromEmail);
		Assert.assertEquals(fromEmailID.getText(), fromEmail);
		Log4jUtil.log("Verify To Email address actual "+toEmailID.getText()+" expected is "+toEmail);
		Assert.assertEquals(toEmailID.getText(), toEmail);
		if(AttachmentType!=null && !AttachmentType.isEmpty() && !AttachmentType.equalsIgnoreCase("none")) {
			WebElement attachmentName =driver.findElement(By.xpath("//*[contains(text(),'"+fileName+"')]"));
			Log4jUtil.log("Actual name is "+attachmentName.getText()+" should contain name: "+fileName);
			Assert.assertTrue(attachmentName.getText().contains(fileName), "filename not matched");
		}
		
		if(AttachmentType.equalsIgnoreCase("xml")) {
			viewTOC.click();
			Thread.sleep(3000);
			String winHandleBefore = driver.getWindowHandle();
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
			// Perform the actions on new window
			Log4jUtil.log("Secure Exchange Step 2: Verfiy if attachment is present");
			
			String[] attachTOCNameA = fileName.split("\\.");
			attachTOCNameA[0] = attachTOCNameA[0].toUpperCase();	
			Log4jUtil.log("attachTOCNameA: "+attachTOCNameA[0]);
			
			WebElement tocDocument= driver.findElement(By.id("explorerLink_1_"+attachTOCNameA[0]+"_XML"));
		
			Thread.sleep(3000);
			tocDocument.click();
			
			WebElement tocName= driver.findElement(By.xpath("//*[@id=\"contentDiv_1_"+attachTOCNameA[0]+"_XML\"]/h1"));
			Log4jUtil.log("Verify the TOC name Actual "+tocName.getText()+" expected name is "+attachTOCName);
			Assert.assertEquals(tocName.getText(), attachTOCName);
			driver.close();
			driver.switchTo().window(winHandleBefore);
		}
		
		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}
	
	public SecureExchangeEmailPage SignOut()  {
		driver.switchTo().defaultContent(); 
		userName.click();
		signOut.click();
		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}
}