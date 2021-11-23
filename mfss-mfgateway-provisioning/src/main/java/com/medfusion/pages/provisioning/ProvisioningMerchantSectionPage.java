package com.medfusion.pages.provisioning;

import com.medfusion.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by lubson on 29.12.15.
 */
public abstract class ProvisioningMerchantSectionPage extends BasePage  implements ProvisioningSectionForm {

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    public WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//button[@data-ng-click='backToDetail()']")
    public WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//span[not(contains(@class,'ng-hide'))]/h1")
    public WebElement headLine;

    public ProvisioningMerchantSectionPage(WebDriver webDriver) {
        super(webDriver);
    }

    //TODO consider to remove
    public void scrollUp(){
        Actions actions = new Actions(webDriver);
        actions.moveToElement(headLine);
        actions.perform();
    }

    public ProvisioningMerchantDetailPage save() {
        submitButton.submit();
        return PageFactory.initElements(webDriver, ProvisioningMerchantDetailPage.class);
    }

    public ProvisioningMerchantDetailPage cancel() {
        click(cancelButton);
        return PageFactory.initElements(webDriver, ProvisioningMerchantDetailPage.class);
    }
}
