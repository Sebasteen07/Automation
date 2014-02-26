package com.intuit.ihg.product.practice.page.patientSearch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class PatientDashboardPage extends BasePageObject{
	@FindBy(xpath="//a[contains(.,'Send Password Reset Email To Patient')]")
	private WebElement userPasswordEmail;
	
	@FindBy(xpath="//a[contains(.,'Send email with the username to the patient')]")
	private WebElement userIdEmail;
	
	private WebElement feedback;
	
	public PatientDashboardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	/**
	 * @throws InterruptedException 
	 * @Description:click on Patient with name
	 */
	public PatientSearchPage sendEmailUserID () throws InterruptedException
	{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, userIdEmail);
		userIdEmail.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
		
	}
	
	public String getFeedback() throws InterruptedException{
		feedback = driver.findElement(By.xpath("//li[@class='feedbackPanelINFO']"));
		IHGUtil.waitForElement(driver, 15, feedback);
		return feedback.getText();
	}

}
