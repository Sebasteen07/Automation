package com.intuit.ihg.product.mobile.page.solutions.askaquestion;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.common.SubmissionConfirmationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Prokop Rehacek.
 * User: prehacek
 * Date: 2/12/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class AskAQuestionPage extends MobileBasePage {

	@FindBy( how = How.XPATH, using = "//select[@id='staffSelect']")
	private Select selectStaff;
	
	@FindBy( how = How.ID, using = "questionSubject")
	private WebElement questionSubject;
	
	@FindBy( how = How.ID, using = "questionBody")
	private WebElement questionBody;
	
	@FindBy( how = How.XPATH, using = "//div[@id='askQuestion']//a[contains(.,'Submit')]")
	private WebElement btnSubmit;


    public AskAQuestionPage(WebDriver driver) {
        super(driver);
    }
    
    public SubmissionConfirmationPage fillAndSubmitQuestion(String staff, String subject, String body) throws InterruptedException{
    	IHGUtil.waitForElement(driver, 10, questionSubject);
    	selectStaff = new Select( driver.findElement(By.id("staffSelect")));
    	log("staff: "+staff);
    	selectStaff.selectByVisibleText(staff);
    	questionSubject.sendKeys(subject);
    	questionBody.sendKeys(body);
    	Thread.sleep(2000);
    	IHGUtil.waitForElement(driver, 10, btnSubmit);
    	btnSubmit.click();
    	Thread.sleep(2000);
    	return PageFactory.initElements(driver, SubmissionConfirmationPage.class);
    	
    }
}
