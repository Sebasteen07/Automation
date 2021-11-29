package com.medfusion.pages.provisioning;

import com.medfusion.pages.BasePage;
import com.medfusion.pages.provisioning.partials.ProvisioningMerchantGeneralInfoForm;
import com.medfusion.util.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubson on 27.11.15.
 */
public class ProvisioningAuthenticatedPage extends BasePage {

    public ProvisioningAuthenticatedPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(how = How.XPATH, using = "//nav[@id='menu']//li[@role='presentation' and not(contains(@class,'ng-hide'))]//a")
    public List<WebElement> menuLinks;

    public List<String> getVisibleMenuItems() {

        List<String> menuItemLabels = new ArrayList<String>();
        for (WebElement menuItem : menuLinks) {
            menuItemLabels.add(getText(menuItem).trim().toUpperCase().replace(' ', '_'));
        }

        return menuItemLabels;
    }

    public ProvisioningMerchantGeneralInfoForm openAddNewMerchantPage(){
        WebElement addNewMerchantLink = getMenuLink("merchants/add");
        click(addNewMerchantLink);
        return PageFactory.initElements(webDriver,ProvisioningMerchantGeneralInfoForm.class);
    }

    private WebElement getMenuLink(String actionName) {

        String normalizedActionName = Data.get("url") + "/#/" + actionName;
        for (WebElement menuLink : menuLinks) {
            if (menuLink.getAttribute("href").equals(normalizedActionName)) {
                return menuLink;
            }
        }
        return null; //TODO throw some nice exception
    }
}
