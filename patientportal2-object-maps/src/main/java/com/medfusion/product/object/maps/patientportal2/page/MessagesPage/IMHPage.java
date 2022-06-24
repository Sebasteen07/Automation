//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MessagesPage;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import com.medfusion.common.utils.IHGUtil;

public class IMHPage extends JalapenoMessagesPage{
	
	@FindBy(how = How.XPATH, using = "//*[@Value='Next']")
	private WebElement imhNextButton;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Welcome')]")
	private WebElement imhWelcomeText;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Emergency Warning')]")
	private WebElement imhEmergencyWarningText;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Patient Information')]")
	private WebElement imhPatientInformationText;
	
	@FindBy(how = How.ID, using = "FirstName")
	private WebElement imhFirstName;
	
	@FindBy(how = How.ID, using = "LastName")
	private WebElement imhLastName;
	
	@FindBy(how = How.ID, using = "Male")
	private WebElement imhGender;
	
	@FindBy(how = How.ID, using = "DOBMonth")
	private WebElement imhDobMon;
	
	@FindBy(how = How.ID, using = "DOBDay")
	private WebElement imhDobDay;
	
	@FindBy(how = How.ID, using = "DOBYear")
	private WebElement imhDobYear;

	@FindBy(how = How.XPATH, using = "//*[@value='IMH-Answer-0']")
	private WebElement selectFirstRadioButton;
	
	@FindBy(how = How.NAME, using = "submitAnswer")
	private WebElement imhSubmitAnswerButton;
	
	@FindBy(how = How.NAME, using = "skipQuestion")
	private WebElement imhSkipQuesButton;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Report')]")
	private WebElement imhReportText;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Interview Complete')]")
	private WebElement imhFormCompleteText;
	
	public IMHPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}
	
	public static WebDriver switchWindow(WebDriver driver) {
		String parentWindow = driver.getWindowHandle();
		Set<String> newTabs = driver.getWindowHandles();

		for (String newTab : newTabs)
			{
				if (!parentWindow.equalsIgnoreCase(newTab)) {
					driver.switchTo().window(newTab);
				}
			}
		return driver;
		}
	
	public void fillIMH(WebDriver driver, String attachmentName,String firstName,String lastName,String dobMon,String dobDay,String dobYear,String imhForm) {
		IHGUtil.PrintMethodName();
		WebElement element;
		element = driver.findElement(By.xpath("//a[contains(text(),'" + attachmentName + "')]"));
		log("Clicking to IMH Form " + attachmentName);
		element.click();
		
		driver = switchWindow(driver);
		
		IHGUtil.waitForElement(driver, 60, imhWelcomeText);			
		imhNextButton.click();
		
		IHGUtil.waitForElement(driver, 60, imhEmergencyWarningText);
		imhNextButton.click();			
		
		IHGUtil.waitForElement(driver, 60, imhPatientInformationText);
		
		log("Filling Personal details in IMH Form");
		imhFirstName.sendKeys(firstName);;
		imhLastName.sendKeys(lastName);
		imhGender.click();
		imhDobMon.sendKeys(dobMon);
		imhDobDay.sendKeys(dobDay);
		imhDobYear.sendKeys(dobYear);
		imhNextButton.click();
		
		log("Clicking the questioniare "+imhForm);
		IHGUtil.waitForElement(driver, 60, driver.findElement(By.xpath("//*[contains(text(),'"+imhForm+"')]")));
		driver.findElement(By.xpath("//*[contains(text(),'"+imhForm+"')]")).click();			
		imhNextButton.click();
		IHGUtil.waitForElement(driver, 60, imhNextButton);
		imhNextButton.click();			
		
		if (imhSkipQuesButton.isDisplayed())
			imhSkipQuesButton.click();
		else
			log(imhSkipQuesButton+" element is not found");
		
		imhNextButton.click();
		
		IHGUtil.waitForElement(driver, 60, imhReportText);
		imhNextButton.click();
		if (imhFormCompleteText.isDisplayed())
			log("Form is submitted");
		else
			log("Form is not submitted");		
	}
}
