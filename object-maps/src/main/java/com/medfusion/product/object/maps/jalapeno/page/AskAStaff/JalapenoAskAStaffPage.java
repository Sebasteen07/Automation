package com.medfusion.product.object.maps.jalapeno.page.AskAStaff;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class JalapenoAskAStaffPage extends BasePageObject{
	
	//Navigation
	@FindBy(how = How.LINK_TEXT, using = "Ask (paid)")
	private WebElement askPaidTab;
	@FindBy(how = How.NAME, using = ":submit")
	private WebElement continueBut;
	@FindBy(how = How.NAME, using = "buttons:submit")
	private WebElement submitQuestionBut;
	
	
	@FindBy(how = How.NAME, using = "subjectWrapper:_body:subject")
	private WebElement subject;
	@FindBy(how = How.NAME, using = "questionWrapper:_body:question")
	private WebElement question;
	
	//Credit card 
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:nameOnCreditCard")
	private WebElement cardName;
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:creditCardNumber")
	private WebElement cardNumber;
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:creditCardType")
	private WebElement cardType;
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:expirationMonth")
	private WebElement cardDateMonth;
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:expirationYear")
	private WebElement cardDateYear;
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:newccdetailscvv:cvvCode")
	private WebElement cardCVV;
	@FindBy(how = How.NAME, using = "ccpanel:newccdetails:addressZip")
	private WebElement cardZip;
	
	private long createdTS;
	
	public JalapenoAskAStaffPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
		createdTS = System.currentTimeMillis();
	}
	
	public long getCreatedTimeStamp() {
		return createdTS;
	}

	public boolean fillAndSubmitAskAStaff(WebDriver driver) {
		IHGUtil.PrintMethodName();
		try{
			askPaidTab.click();
		}catch(org.openqa.selenium.NoSuchElementException e0) {
			log("--------------------------------------------------------------");
			log("I couldn't find 'Ask (paid)' button, please check at practice!");
			return false;
		}
		log("Fill message and continue");
		new WebDriverWait(driver, 20).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='viewContent']/div[1]/h2")));
		IHGUtil.setFrame(driver, "iframebody");
		subject.sendKeys("Ola! "+this.getCreatedTimeStamp());
		question.sendKeys("Ola Doc! Please help meh.");	
		continueBut.click();
		
		log("Fill a new credit card and submit");
		IHGUtil.waitForElement(driver, 20, submitQuestionBut);
		try{
			List<WebElement> li = driver.findElements(By.name("ccpanel:existingccdetailscontainer:ccRadioGroup"));
			li.get(1).click();
		} catch(IndexOutOfBoundsException e1) {}
		cardName.sendKeys("Joffrey Baratheon Lannister");
		cardNumber.sendKeys("4111111111111111");
		Calendar cal = Calendar.getInstance();
		String sYear = new Integer(cal.get(Calendar.YEAR)+1).toString();
		Select dropdown = new Select(cardType);
		dropdown.selectByVisibleText("Visa");
		dropdown = new Select(cardDateMonth);
		dropdown.selectByVisibleText("February");
		dropdown = new Select(cardDateYear);
		dropdown.selectByVisibleText(sYear);
		cardCVV.sendKeys("369");
		cardZip.sendKeys("36969");
		submitQuestionBut.click();

		log("Go home");
		new WebDriverWait(driver, 20).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Home')]"))).click();
		IHGUtil.setDefaultFrame(driver);
		return true;
	}
	
	
}