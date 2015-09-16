package com.intuit.ihg.product.object.maps.mobile.page;


import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.askaquestion.SelectAQuestionPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SelectADoctorPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SelectALocationPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SelectAPracticePage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.rxrenewal.SelectAMedicationPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/20/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileHomePage extends MobileBasePage {

    @FindBy(xpath = "//a[@id='signout' or contains(@*,'doSignOut') or contains(@onclick,'doSignOut') or contains(@href,'doSignOut')]")
    private WebElement logout;

    @FindBy(id = "msgsLink")
    private WebElement myMessages;

    @FindBy(css = "#arLink")
    private WebElement apptRequest;

    @FindBy(css = "#rxLink")
    private WebElement rxRequest;

    @FindBy(css = "#askLink")
    private WebElement askAdoc;

    @FindBy(css = "#billPayLink")
    private WebElement billPay;

    @FindBy(id = "feedbackSubmit")
    private WebElement feedbackSubmit;

    @FindBy(id = "count")
    private WebElement count;

    @FindBy(how = How.ID, using = "pswrdRestMsg")
    private WebElement passwordResetSuccessMsg;            //Your password has been updated.

    @FindBy(css="#welcomeName")
    private WebElement welcomeName;

     @FindBy(css="#welcome")
    private WebElement welcome;

    public MobileHomePage(WebDriver driver) {
        super(driver);
    }

    public MobileSignInPage clickLogout() throws InterruptedException {
    	IHGUtil.PrintMethodName();
    	waitForlogoutLink(driver, 20);
    	Thread.sleep(1000); //without this it simply doesn't click it >:(
    	logout.click();
    	IHGUtil.waitForElement(driver, 20, feedbackSubmit);
    	feedbackSubmit.click();
    	return PageFactory.initElements(driver, MobileSignInPage.class);
    }

    public void waitForlogoutLink(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        WebDriverWait wait = new WebDriverWait(driver, n);
        wait.until(ExpectedConditions.elementToBeClickable(logout));
    }

    public MessageInboxPage clickMyMessages() throws InterruptedException {
        waitForCount(driver, 20);
        log("Number of messages: " + count.getText());
        IHGUtil.PrintMethodName();
        Thread.sleep(2000);
        myMessages.click();
        Thread.sleep(2000);
        return PageFactory.initElements(driver, MessageInboxPage.class);
    }

    public void waitforMyMessagesLink(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, myMessages);
    }

    public MobileBasePage clickARLink() throws InterruptedException {
        waitForCount(driver, 20);
        IHGUtil.PrintMethodName();
        apptRequest.click();
        waitForHome(driver, 20);
        Thread.sleep(2000);
        String instruction = this.getInstruction();
        if (instruction != null) {
            if (instruction.contains("Choose one of your preferred doctors or select a different doctor")) {    //TODO add other options here - vvalsan
                return PageFactory.initElements(driver, SelectADoctorPage.class);
            }
        }
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public void waitForARLink(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, apptRequest);
    }

    public MobileBasePage clickRXLink() throws InterruptedException {
        waitForCount(driver, 20);
        IHGUtil.PrintMethodName();
        rxRequest.click();
        waitForHome(driver, 20);
        String instruction = this.getInstruction();
        if (instruction != null) {
            if (instruction.contains("Select a medication")) {            //TODO add other options here - vvalsan
                return PageFactory.initElements(driver, SelectAMedicationPage.class);
            }
        }
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public void waitForRXLink(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, rxRequest);
    }

    public MobileBasePage clickAAQLink() throws InterruptedException {
        waitForCount(driver, 20);
        IHGUtil.PrintMethodName();
        askAdoc.click();
        waitForHome(driver, 20);
        String instruction = this.getInstruction();
        if (instruction != null) {
            if (instruction.contains("If you are experiencing an emergency, please dial 911")) {   //TODO add other options here - vvalsan
                Thread.sleep(2000);
            	return PageFactory.initElements(driver, SelectAQuestionPage.class);
                
            }else if (instruction.contains("Select a practice")) {
                return PageFactory.initElements(driver, SelectAPracticePage.class);
            }

        }
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public void waitForAAQLink(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, askAdoc);
    }

    public MobileBasePage clickBillPayLink() throws InterruptedException {
        waitForCount(driver, 20);
        IHGUtil.PrintMethodName();
        billPay.click();
        waitForHome(driver, 20);
        String instruction = this.getInstruction();
        if (instruction != null) {
            if (instruction.contains("Select a location")) {        //TODO add other options here - vvalsan
                return PageFactory.initElements(driver, SelectALocationPage.class);
            } else if (instruction.contains("Select a practice")) {
                return PageFactory.initElements(driver, SelectAPracticePage.class);
            }
        }
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public void waitForBillPayLink(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, askAdoc);
    }

    public void waitForCount(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        try{
            IHGUtil.waitForElement(driver, n, count);
        }catch (TimeoutException e){
          log("WARN : Timeout occurred waiting for messages count - either no messages or page did not load");
        }

    }

    public boolean isMyMessagesPresent() throws InterruptedException {
        Thread.sleep(5000);
        IHGUtil iUtil = new IHGUtil(driver);
        return iUtil.exists(myMessages);
    }


    public String getPasswordResetSuccessMsg() {
        return passwordResetSuccessMsg.getText();
    }

}
