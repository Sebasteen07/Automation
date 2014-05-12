package com.intuit.ihg.product.object.maps.practice.page.patientMessaging;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeConstants;

public class PatientMessagingPage extends BasePageObject{

	public PatientMessagingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}




	@FindBy(xpath="//table[@class='searchForm']//select[@name='delivery']")
	private WebElement deliveryMode;

	@FindBy(xpath="//table[@class='searchForm']//select[@name='msgtype']")
	private WebElement messageType;

	@FindBy(xpath="//table[@class='searchForm']//select[@name='msgtemplate']")
	private WebElement template;

	@FindBy(xpath="//table[@class='searchForm']//input[@name='subject']")
	private WebElement subject;

	@FindBy(xpath="//input[@id='msgattachment_1_1']")
	private WebElement messageAttachment;

	@FindBy(xpath="//table[@class='searchForm']//select[@name='recipienttype']")
	private WebElement recipientType;

	@FindBy(xpath="//table[@class='searchForm']//input[@name='firstname']")
	private WebElement firstName;

	@FindBy(xpath="//table[@class='searchForm']//input[@name='lastname']")
	private WebElement lastName;

	@FindBy(xpath="//table[@class='searchForm']//input[@name='email']")
	private WebElement email;

	@FindBy(xpath="//table[@class='searchForm']//input[@value='Search for Patients']")
	private WebElement searchForPatients;

	@FindBy(xpath="//table[@id='patresultshead']//tr[@title='Click to add.']/td[2]")
	private WebElement searchResult;

	@FindBy(xpath="//input[@value='Publish Message']")
	private WebElement publishMessage;

	@FindBy(xpath="//div[@class='feedbackContainer']/div/div/ul/li")
	public WebElement publishedSuccessfullyMessage;

	/**
	 * @Description:Set Delivery Mode
	 */
	public void setDeliveryMode()
	{
		IHGUtil.PrintMethodName();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(deliveryMode);
		sel.selectByVisibleText(PracticeConstants.DeliveryMode);

	}

	/**
	 * @Description:Set Message Type
	 */
	public void setMessageType()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(messageType);
		sel.selectByVisibleText(PracticeConstants.MessageType);

	}

	/**
	 * @Description:Set Template
	 * @throws Exception
	 */
	public void setTemplate() throws Exception
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		Select sel=new Select(template);
		try{
			sel.selectByVisibleText(PracticeConstants.Template1);
		}
		catch(Exception e){
			sel.selectByVisibleText(PracticeConstants.Template2);
		}
		Thread.sleep(5000);

	}

	/**
	 * @Description:Set Subject
	 */
	public void setSubject()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		subject.clear();
		subject.sendKeys(PracticeConstants.Subject);
	}

	/**
	 * @Description:Set Recipient Type
	 */
	public void setRecipientType()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		IHGUtil.waitForElement(driver, 10, recipientType);
		Select sel=new Select(recipientType);
		sel.selectByVisibleText(PracticeConstants.RecipientType);

	}

	/**
	 * @Description:Set First Name
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
	 * @Description:Set Email
	 */
	public void setEmail()
	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver,PracticeConstants.frameName);
		email.clear();
		email.sendKeys(PracticeConstants.PatientEmail);
	}

	/**
     * @Description:Set Quick Send Fields
     * @param filePath
     * @throws Exception
     */
     public void setQuickSendFields(String filePath) throws Exception
     {
            IHGUtil.PrintMethodName();
            Thread.sleep(5000);
            setDeliveryMode();
            setMessageType();
            setTemplate();
            setSubject();
            
            URL QuickSendPDFUrl = ClassLoader.getSystemResource(PracticeConstants.QuickSendPdfFilePath);
            messageAttachment.sendKeys(QuickSendPDFUrl.getPath());
            
            Thread.sleep(2000);
            setRecipientType();
            setFirstName();
            setLastName();
            searchForPatients.click();
            Thread.sleep(5000);
            IHGUtil.setFrame(driver,PracticeConstants.frameName);
            IHGUtil.waitForElement(driver,60,searchResult);
            searchResult.click();
            Thread.sleep(12000);
            IHGUtil.setFrame(driver,PracticeConstants.frameName);
            email.click();
            publishMessage.click();
            Thread.sleep(3000);

     }



}
