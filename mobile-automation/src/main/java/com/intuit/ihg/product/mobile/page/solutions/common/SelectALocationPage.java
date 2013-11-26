package com.intuit.ihg.product.mobile.page.solutions.common;

import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.apptrequest.ARSubmissionPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/21/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectALocationPage extends MobileBasePage {
    public SelectALocationPage(WebDriver driver) {
        super(driver);
    }

    private WebElement location;

    public MobileBasePage selectFirstLocation() throws InterruptedException {
    	location = driver.findElement(By.xpath("//ul[@class='insetList']/li[1]/a[@*[contains(name(), 'locationname')]]"));
    	log(location.getText());
        location.click();
        Thread.sleep(2000);
        if (null!=this.getHeaderText() && getHeaderText().contains("Appointment")) {
            return PageFactory.initElements(driver, ARSubmissionPage.class);
        }

        return PageFactory.initElements(driver, MobileBasePage.class);
    }
}
