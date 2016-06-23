package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class JalapenoMyAccountPage extends MedfusionPage {

    @FindBy(how = How.LINK_TEXT, using = "Profile")
    private WebElement profileTab;

    @FindBy(how = How.LINK_TEXT, using = "E-mail")
    private WebElement emailTab;

    @FindBy(how = How.LINK_TEXT, using = "Password & User ID")
    private WebElement passwordAndIdTab;

    @FindBy(how = How.LINK_TEXT, using = "Preferences")
    private WebElement preferencesTab;

    @FindBy(how = How.LINK_TEXT, using = "Wallet")
    private WebElement walletTab;

    @FindBy(how = How.LINK_TEXT, using = "Family")
    private WebElement familyTab;

    @FindBy(how = How.LINK_TEXT, using = "Account Activity")
    private WebElement accountActivityTab;

    @FindBy(how = How.XPATH, using = "//input[@id='address1']")
    private WebElement address1Textbox;

    @FindBy(how = How.XPATH, using = "//input[@id='city']")
    private WebElement cityTextbox;

    @FindBy(how = How.XPATH, using = "//input[@id='postalCode']")
    private WebElement zipCodeTextbox;

    @FindBy(how = How.XPATH, using = "//input[@id='gender_male']")
    private WebElement maleRadioButton;

    @FindBy(how = How.ID, using = "state")
    private WebElement stateSelect;

    @FindBy(how = How.ID, using = "saveAccountChanges")
    private WebElement submitButton;

    public JalapenoMyAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkForAddress(WebDriver driver, String addressLine1, String city, String zipCode) {

        log("Checking address in My Account");

        String savedAddressLine1 = address1Textbox.getAttribute("value");
        String savedCity = cityTextbox.getAttribute("value");
        String savedZipCode = zipCodeTextbox.getAttribute("value");

        if (savedAddressLine1.isEmpty() || savedCity.isEmpty() || savedZipCode.isEmpty()) {
            log("One of the address information if missing");
            return false;
        }

        if (!addressLine1.equals(savedAddressLine1)) {
            log("Line 1 is incorrect: " + savedAddressLine1);
            return false;
        }
        if (!city.equals(savedCity)) {
            log("City is incorrect: " + savedCity);
            return false;
        }
        if (!zipCode.equals(savedZipCode)) {
            log("ZipCode is incorrect: " + savedZipCode);
            return false;
        }

        log("Everything is saved, values are as follows");

        log("Address line 1 value: " + savedAddressLine1);
        log("City value: " + savedCity);
        log("ZipCode value: " + savedZipCode);

        return true;
    }

    public boolean validatePageContent(JalapenoPatient jalapenoPatient) {

        Map<WebElement, String> itemsTovalidate = new HashMap<WebElement, String>();
        itemsTovalidate.put(address1Textbox, jalapenoPatient.getAddress1());
        itemsTovalidate.put(cityTextbox, jalapenoPatient.getCity());
        itemsTovalidate.put(stateSelect, jalapenoPatient.getState());
        itemsTovalidate.put(zipCodeTextbox, jalapenoPatient.getZipCode());

        return validateWebElements(itemsTovalidate);
    }

    public boolean checkGender(Gender genderExpected) {
        log("Checking gender");
        Gender genderOnPage = maleRadioButton.isSelected() ? Gender.MALE : Gender.MALE;
        return genderExpected == genderOnPage;
    }

    public boolean assessBasicPageElements() {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

        webElementsList.add(profileTab);
        webElementsList.add(preferencesTab);
        webElementsList.add(address1Textbox);
        webElementsList.add(cityTextbox);
        webElementsList.add(zipCodeTextbox);
        webElementsList.add(maleRadioButton);

        return assessPageElements(webElementsList);
    }

    public boolean modifyAndValidatePageContent() {
        Map<WebElement, String> itemsToChange = new HashMap<WebElement, String>();
        itemsToChange.put(address1Textbox, "address");
        itemsToChange.put(cityTextbox, "city");
        itemsToChange.put(stateSelect, "Alaska");
        itemsToChange.put(zipCodeTextbox, "54321");

        return updateAndValidateWebElements(itemsToChange, submitButton);
    }

}
