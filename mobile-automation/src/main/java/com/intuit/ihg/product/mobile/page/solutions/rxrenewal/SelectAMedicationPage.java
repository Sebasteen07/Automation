package com.intuit.ihg.product.mobile.page.solutions.rxrenewal;

import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectADoctorPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectAMedicationPage extends MobileBasePage {

   private WebElement medication;

//a[@href='/mobile/rxDocChoice']

    @FindBy(xpath="//a[text()='A different medication']")
    private WebElement btnDiffMedication;

    public SelectAMedicationPage(WebDriver driver) {
        super(driver);
    }

     public MobileBasePage selectMedication(int rxId){
        medication = driver.findElement(By.xpath("//a[@rxid='" + rxId + "']")); //116262
        medication.click();
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public MobileBasePage selectMedication(String rxName){
        medication = driver.findElement(By.xpath("//a[contains(text(),'" + rxName + "']")); //Claritin D
        medication.click();
        return PageFactory.initElements(driver, MobileBasePage.class);
    }

    public SelectADoctorPage selectDiffMed(){
       btnDiffMedication.click();
       return PageFactory.initElements(driver, SelectADoctorPage.class);
    }
}
