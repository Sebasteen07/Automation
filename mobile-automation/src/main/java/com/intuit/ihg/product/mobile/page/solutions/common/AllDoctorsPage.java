package com.intuit.ihg.product.mobile.page.solutions.common;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.apptrequest.ARSubmissionPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllDoctorsPage extends MobileBasePage {

    @FindBy(css="input[id='txtSearchDoctors']")
	private WebElement txtSearchDoctors;

    @FindBy(xpath = "//a[@id='moreDoctors']")
    private WebElement moreDoctors;

    public AllDoctorsPage(WebDriver driver) {
        super(driver);
    }

    public MobileBasePage searchForAndSelectDoc(String docName) throws InterruptedException {    //Bot, Doc      //Always AR submission page or select loc page ?
        try{
            waitForSearchBox(driver,10);
            txtSearchDoctors.sendKeys(docName);
        }catch (TimeoutException e){
            log("WARN : Searchbox not found. Confirm if due to lesser number of doc or page didnot load");
        }
        
        WebElement element = driver.findElement( By.partialLinkText(docName) );
        element.click();
        Thread.sleep(3000);
        if (null!=this.getInstruction() && getInstruction().contains("sees patients at the following locations")){
        return PageFactory.initElements(driver, SelectALocationPage.class);
        } else if (this.getHeaderText().equalsIgnoreCase("Request Appointment")){
        return PageFactory.initElements(driver, ARSubmissionPage.class);
        }
        else return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public MobileBasePage selectDocFromList(String docName) throws InterruptedException {    //Bot, Doc      //Always AR submission page or select loc page ?
        Thread.sleep(1000);
        waitForSearchBox(driver,10);
        try{
            WebElement element = driver.findElement( By.partialLinkText(docName) );
            element.click();
        }  catch (NoSuchElementException ex){
            moreDoctors.click();
            WebElement element = driver.findElement( By.partialLinkText(docName) );
            element.click();
        }
        Thread.sleep(2000);
        if (null!=this.getInstruction() && getInstruction().contains("sees patients at the following locations")){
        return PageFactory.initElements(driver, SelectALocationPage.class);
    }
        return PageFactory.initElements(driver, ARSubmissionPage.class);
    }

    public void waitForSearchBox(WebDriver driver,int n) throws InterruptedException{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,n, txtSearchDoctors);
	}

}
