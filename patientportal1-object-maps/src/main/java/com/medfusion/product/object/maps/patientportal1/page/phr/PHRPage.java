package com.medfusion.product.object.maps.patientportal1.page.phr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PHRPage extends BasePageObject {

    public PHRPage(WebDriver driver) {
        super(driver);
        // TODO Auto-generated constructor stub
    }

    @FindBy(xpath = "//input[@name='accept' and @value='Accept']")
    private WebElement acceptButton;

    @FindBy(xpath = "//div[@id='home_logo']/img")
    public WebElement phrHomeLogo;

    @FindBy(xpath = "//em[text()='Log out']")
    private WebElement btnLogout;

    /**
     * @Description:Click Accept Button
     * @throws Exception
     */
    public void clickAccept() throws Exception {
        IHGUtil.PrintMethodName();
        Thread.sleep(5000);
        try {
            IHGUtil.waitForElement(driver, 10, acceptButton);
            acceptButton.click();
        } catch (Exception e) {
            log("Accept button is not displayed");
        }

    }

    /**
     * @Description:Click LogOut Button
     */
    public void clickLogout() {

        IHGUtil.PrintMethodName();
        btnLogout.click();

    }

}
