package com.medfusion.product.object.maps.practice.page.rxrenewal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;


public class RxRenewalDetailPage extends BasePageObject{

	@FindBy(xpath="//input[@name='rxrs:0:rxPanel:action' and @value='1']")
	private WebElement radioPrescribe;
	
	@FindBy(xpath="//input[@name='rxrs:0:rxPanel:action' and @value='2']")
	private WebElement radioDeny;
	
	@FindBy(xpath="//input[@name='rxrs:0:rxPanel:action' and @value='3']")
	private WebElement radioProcessExternally;
	
	@FindBy(xpath="//input[@name='rxrs:0:rxPanel:container:table:drugName']")
	private WebElement drugName;
	
	@FindBy(xpath="//input[@name='rxrs:0:rxPanel:container:table:dosage']")
	private WebElement dosage;
	
	@FindBy(xpath="//input[@name='rxrs:0:rxPanel:container:table:quantity']")
	private WebElement quantity;
	
	@FindBy(name="rxrs:0:rxPanel:container:table:frequency")
	private WebElement frequency;
	
	// rxrs:0:rxPanel:container:table:frequency
	//private Select selectFrequency;
	
	// rxrs:0:rxPanel:container:table:unitsForm:select
//	private Select selectUnitsForm;
	
	///////////////////////////////////
	// Secure Communication
	
	// sendmessage:from
	private Select selectMessageFrom;
	
	@FindBy(xpath="//input[@name='sendmessage:subject']")
	private WebElement messageSubject;
	
	@FindBy(xpath="//textarea[@name='sendmessage:body']")
	private WebElement messageBody;
	
	//////////////////////////////////////
	
	// communicateAndLeaveOpen
	@FindBy(xpath="//input[@name='communicateAndLeaveOpen']")
	private WebElement btnCommunicateAndLeaveOpen;
	
	@FindBy(xpath="//input[@name='communicateAndProcessRxRenewal']")
	private WebElement btnCommunicateAndProcessRxRenewal;
	
	////////////////////////////////////////
	
	
	public RxRenewalDetailPage(WebDriver driver) {
		super(driver);
		}
	
	public void setFrame() {
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame("iframebody");
	
		}
	
public void prescribe( String sQuantity, String sFrequency ) throws InterruptedException {
		
		radioPrescribe.click();
		
		Thread.sleep( 3000 );	// Fix issue of Quantity not being filled in.
		
		quantity.clear();
		quantity.sendKeys( sQuantity );
		
		Thread.sleep( 3000 );
		
		//selectFrequency = new Select( driver.findElement(By.name("rxrs:0:rxPanel:container:table:frequency")));
		
		frequency.sendKeys(sFrequency);	
	}

public void setMessageFrom( String sFrom ) {
	
	String sName = "sendmessage:from";
	
	if (driver.findElements(By.name(sName) ).size() != 0) {
		
		// Name might not be found.
	
		selectMessageFrom = new Select( driver.findElement(By.name( sName )));
		selectMessageFrom.selectByVisibleText( sFrom );
		
	} else {
		
		System.out.println( "### WARNING: Couldn't find element based on name: " + sName );
	}
}

	public void setSubject( String sSubject ) throws InterruptedException {
		
		messageSubject.sendKeys( sSubject );
		}
	
	public void setMessageBody( String sMessage ) {
			
			messageBody.sendKeys( sMessage );
		}
		
	
	public RxRenewalDetailPageConfirmation clickCommunicateAndProcessRxRenewal() {
			
			btnCommunicateAndProcessRxRenewal.click();
			
			return PageFactory.initElements(driver, RxRenewalDetailPageConfirmation.class);
		}
}




