package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Adam Warzel on 29.10.2014.
 */
public class FormConfigPage extends BasePageObject {

    @FindBy(className = "back")
    private WebElement backToTheList;

    @FindBy(id = "save_config_form")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@class='green yes backToList']")
    private WebElement dialogSaveButton;

    @FindBy(xpath = "//a[@class='button blue backFloating']")
    private WebElement floatingBackButton;

    @FindBy(id = "save_config_form_floating")
    private WebElement floatingSaveButton;

    @FindBy(xpath = "//button[@class='blue no backToList']")
    private WebElement dialogCloseFormButton;

    @FindBy(xpath = "//a[@class='closeDialog red']")
    private WebElement closeDialogButton;

    @FindBy(id = "loading")
    private WebElement loadingNotification;

    public FormConfigPage(WebDriver driver) {
        super(driver);
    }

    /* In case the screen is scrolled down it is possible that save button and back link are not visible
    *  when that happens we can use the floating panel with save and back buttons*/

    public void clickBackToTheList() {
        if (backToTheList.isDisplayed())
            backToTheList.click();
        else
            floatingSaveButton.click();
    }

    public void clickSaveButton() {
        if (saveButton.isDisplayed())
            saveButton.click();
        else
            floatingSaveButton.click();
    }

    public void clickDialogSaveButton() {
        dialogSaveButton.click();
    }

    public void clickDialogCloseFormButton() {
        dialogCloseFormButton.click();
    }

    public void clickCloseDialogButton() {
        closeDialogButton.click();
    }

    public void saveOpenedForm() throws InterruptedException {
        IHGUtil utils = new IHGUtil(driver);

        clickSaveButton();
        utils.waitForElementToDisappear(loadingNotification, 2000, 8);
    }

}
