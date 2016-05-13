package com.medfusion.product.object.maps.patientportal1.page.myAccount.familyAccount;

import java.awt.AWTException;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class CreatefamilymemberPage extends BasePageObject{

	@FindBy(how=How.NAME, using ="editPersonalInfo:border:editForm:inputs:0:input:input")
	private WebElement txtPatientFirstName ;
		
	@FindBy(how=How.NAME, using ="editPersonalInfo:border:editForm:inputs:2:input:input")
	private WebElement txtLastName ;
	
	@FindBy(how=How.NAME, using ="editPersonalInfo:border:editForm:inputs:3:input:input")
	private WebElement PopUpDateofBirth ;
	
	@FindBy(how=How.NAME, using ="editPersonalInfo:border:editForm:inputs:4:input:input")
	private WebElement dropDownRelationWithPatient ;
	
	@FindBy(how=How.NAME, using ="editPersonalInfo:border:editForm:inputs:5:input:input")
	private WebElement txtSSN ;
	
	@FindBy(how=How.NAME, using ="editPersonalInfo:border:editForm:inputs:6:input:input")
	private WebElement rbtGender ;
	
	
	@FindBy(how=How.NAME, using ="editMailingAddress:border:editForm:inputs:0:input:input")
	private WebElement txtAddressLine1 ;
		
	@FindBy(how=How.NAME, using ="editMailingAddress:border:editForm:inputs:2:input:input")
	private WebElement txtcity;
	
	@FindBy(how=How.NAME, using ="editMailingAddress:border:editForm:inputs:3:input:input")
	private WebElement DropDownState ;
	
	
	@FindBy(how=How.NAME, using ="editMailingAddress:border:editForm:inputs:4:input:input")
	private WebElement txtZipCode;
	
	@FindBy(how=How.NAME, using ="editContactInfo:border:editForm:inputs:0:input:input")
	private WebElement txtHomePhone;
	
	@FindBy(how=How.NAME, using ="touWrapper:touack")
	private WebElement chckAcknowledgeIntuitTC;
	
	@FindBy(how=How.NAME, using ="border:nppack")
	private WebElement chckAcknowledgePatientreceipt;
		
	@FindBy(how=How.NAME, using ="buttons:submit")
	private WebElement btnCreateAccount;
	
	@FindBy(name = "username")
	private WebElement userid;
	
	@FindBy(name = "password")
	private WebElement familypassword;
	
	@FindBy(name = "type")
	private WebElement Relationship;
	
	@FindBy(name = "buttons:submit")
	private WebElement submitbutton;
	
	@FindBy(xpath = "//div[@class='paperPad']/div[3]//span")
	private WebElement conformationmessage;
	
	@FindBy(linkText = "Unlink Dependent")
	private WebElement unlink;
	
	public CreatefamilymemberPage(WebDriver driver)
	{
		super(driver);
	}
	
	public void waitforPreferencesLink(WebDriver driver,int n)
	{   IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,n, txtPatientFirstName);
	}
	
	public MyPatientPage createFamilyMember (String firstName,String LastName, String dob_year,String dob_month,String dob_Day, String relationWithPatient,String SSN) 
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		waitforPreferencesLink(driver, 60);
		txtPatientFirstName.sendKeys(firstName);
		txtLastName.sendKeys(LastName);
		String date=dob_Day+"/"+dob_Day+"/"+dob_year;
		log(date+":=====date");
		PopUpDateofBirth.sendKeys(date);
		txtPatientFirstName.click();
		Select relation=new Select(dropDownRelationWithPatient);
		relation.selectByVisibleText(relationWithPatient);
		txtSSN.sendKeys(SSN);
		rbtGender.click();
						
		if (chckAcknowledgePatientreceipt.isSelected())
		{
			log("check box chckAcknowledgePatientreceipt is already slecected");
		}
		else{
			chckAcknowledgePatientreceipt.click();
		}
		
		if (chckAcknowledgeIntuitTC.isSelected())
		{
			log("check box AcknowledgeIntuitTC is already slecected");
		}
		else{
			chckAcknowledgeIntuitTC.click();
		}
		
		btnCreateAccount.click();
		return PageFactory.initElements(driver,MyPatientPage.class);
	}
	public MyPatientPage FamilyMemberdetails (String username,String password,String Relation) 
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		userid.sendKeys(username);
		familypassword.sendKeys(password);
		Select relation=new Select(Relationship);
		relation.selectByVisibleText(Relation);
		submitbutton.click();
		return PageFactory.initElements(driver,MyPatientPage.class);
	}
	
	public MyPatientPage Verifymember() throws AWTException, InterruptedException 
	{
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		/*String str=conformationmessage.getText();
		log("+++++Conformation Message is: "+str);*/
		
		unlink.click();
		Thread.sleep(10000);
		driver.switchTo().alert().accept();
		/*Robot robot=new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		IHGUtil.waitForElement(driver, 10, conformationmessage);*/
		String strcon=conformationmessage.getText();
		log("+++++Conformation Message after unlink is: "+strcon);
		Assert.assertNotNull("The warning message not found", strcon!="");
		return PageFactory.initElements(driver,MyPatientPage.class);
	}
	
}
