package com.intuit.ihg.product.object.maps.mobile.page.solutions.common;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.apptrequest.ARSubmissionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectADoctorPage extends MobileBasePage {

    @FindBy(xpath="//a[@id='apptAllDocs']")
	private WebElement btnDiffDoc;

    private WebElement doctor;


    public SelectADoctorPage(WebDriver driver) {
        super(driver);
    }

    public AllDoctorsPage selectDiffDoc() throws InterruptedException {
      waitforADiffDocLink(driver,10);
      btnDiffDoc.click();
      return PageFactory.initElements(driver, AllDoctorsPage.class);
    }

    public void waitforADiffDocLink(WebDriver driver,int n) throws InterruptedException{
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,n, btnDiffDoc);
	}



    public MobileBasePage selectDoctor(int docId){
        doctor = driver.findElement(By.xpath("//a[@docid='" + docId + "']")); //66732
        doctor.click();
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public MobileBasePage selectDoctor(String docPartialText) throws InterruptedException {
        WebElement doctor = driver.findElement( By.partialLinkText(docPartialText) );
        doctor.click();
        Thread.sleep(2000);
    if (null!=this.getInstruction() && getInstruction().contains("sees patients at the following locations")){
        return PageFactory.initElements(driver, SelectALocationPage.class);
    }
        return PageFactory.initElements(driver, ARSubmissionPage.class);
    }
}
