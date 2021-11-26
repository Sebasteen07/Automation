package com.medfusion.pages.provisioning;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by lhrub on 24.11.2015.
 */
public class ProvisioningMerchantSearchPage extends ProvisioningAuthenticatedPage {

    @FindBy(how = How.XPATH, using = "//*[@id='top-nav']//div[contains(@class, 'nav-logout')]/div")
    public WebElement loggedAsText;

    @FindBy(how = How.XPATH, using = "//button[contains(@data-ng-click,logout)]")
    public WebElement logoutButton;

    @FindBy(how = How.XPATH, using = "//form[@id='merchantSearch']//button[contains(@type,'submit')]")
    public WebElement searchButton;

    @FindBy(how = How.ID, using = "merchantSearchField")
    public WebElement searchInput;

    @FindBy(how = How.XPATH, using = "//table")
    public WebElement merchantTable;

    @FindBy(how = How.CLASS_NAME, using = "toast-message")
    public WebElement errorToast;

    public ProvisioningMerchantSearchPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getWelcomeMessage() {
        return getText(loggedAsText);
    }

    public ProvisioningLoginPage logout() {
        click(logoutButton);
        return PageFactory.initElements(webDriver, ProvisioningLoginPage.class);
    }

    public ProvisioningMerchantDetailPage openMerchant(String mmid) {
        try {
            getRowWithMerchant(mmid).findElement(By.tagName("button")).click();
        } catch (StaleElementReferenceException e) {
            getRowWithMerchant(mmid).findElement(By.tagName("button")).click();
        }
        return PageFactory.initElements(webDriver, ProvisioningMerchantDetailPage.class);
    }

    public void searchMerchant(String query) {
        sleep(500);
        setText(searchInput, query);
        click(searchButton);
    }

    public WebElement getRowWithMerchant(String mmid) {
        List<WebElement> rows = merchantTable.findElements(By.xpath("//tbody/tr"));
        for (WebElement row : rows) {
            if (isMerchantInTheRow(mmid, row)) {
                return row;
            }
        }
        return null;
    }

    private boolean isMerchantInTheRow(String mmid, WebElement row) {
        String text = row.getText();
        return text.contains(mmid);
    }

    public boolean isErrorDisplayed() {
        try {
            return (errorToast.isDisplayed()) ? true : false;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

}
