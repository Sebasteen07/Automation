package com.intuit.ihg.product.object.maps.mobile.page.solutions.apptrequest;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SubmissionConfirmationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ARSubmissionPage extends MobileBasePage {

    public ARSubmissionPage(WebDriver driver){
        super(driver);
        boolean res = false;
        try{
           res = btnSubmit.isDisplayed();
        }catch (Exception e){

        }
        if(!res ==true)
          {
            throw new NotFoundException("Not the AR submission page page");
          }
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(css="a[id='apptReqSubmit']")
	 private WebElement btnSubmit;

	 @FindBy(xpath="//*[@id='selectedProvider']")
	 private WebElement provider;

	 @FindBy(xpath="//*[@id='selectedPractice']")
	 private WebElement practice;

	 @FindBy(xpath="//*[@id='selectedPracticeLocationName']")
	 private WebElement location;

	 @FindBy(xpath="//*[@id='selectedPracticeAddress']")
	 private WebElement address;

	 //@FindBy(css = "select[id='selApptDate']")
     private Select selApptDate;

     //@FindBy(css = "select[id='apptTime']")
	 private Select selApptTime;

	 @FindBy(css="input[id='reasonForVisit']")
	 private WebElement reasonForVisit;

    /*public ARSubmissionPage(WebDriver driver) {
        super(driver);
    }*/

    public String getProvider() { return provider.getText(); }
	public String getPractice() { return practice.getText(); }
	public String getLocation() { return location.getText(); }
	public String getAddress()  { return address.getText(); }

    public SubmissionConfirmationPage fillWithSampleAndSubmit() throws InterruptedException {
        waitForARSubmissionText(driver, 10);
        selApptDate = new Select( driver.findElement(By.cssSelector("select[id='selApptDate']")));
        selApptTime = new Select( driver.findElement(By.cssSelector("select[id='apptTime']")));
        selApptDate.selectByVisibleText("First Available Date");
        selApptTime.selectByVisibleText("Any Time");
        reasonForVisit.sendKeys("Test");
        btnSubmit.click();
        return PageFactory.initElements(driver, SubmissionConfirmationPage.class);
    }

      public void waitForARSubmissionText(WebDriver driver,int n) throws InterruptedException
		{
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver,n, reasonForVisit);
		}

     public MobileBasePage fillWithDataAndSubmit(String apptDate,String apptReason,String apptTime) throws InterruptedException {
        waitForARSubmissionText(driver, 10);
        selApptDate = new Select( driver.findElement(By.cssSelector("select[id='selApptDate']")));
        selApptTime = new Select( driver.findElement(By.cssSelector("select[id='apptTime']")));
        selApptDate.selectByVisibleText(apptDate);
        selApptTime.selectByVisibleText(apptTime);
        reasonForVisit.sendKeys(apptReason);
        btnSubmit.click();
         if (isErrorMsgPresent())
        return new ARSubmissionPage(driver);
        else return PageFactory.initElements(driver, SubmissionConfirmationPage.class);
    }
}
