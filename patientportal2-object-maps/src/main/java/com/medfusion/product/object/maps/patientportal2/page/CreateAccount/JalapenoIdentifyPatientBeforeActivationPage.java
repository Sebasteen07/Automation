package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class JalapenoIdentifyPatientBeforeActivationPage extends MedfusionPage {

    @FindBy(how = How.ID, using = "postalCode")
    private WebElement inputPostalCode;

    @FindBy(how = How.ID, using = "birthDate_month")
    private WebElement inputBirthDateMonth;

    @FindBy(how = How.ID, using = "birthDate_day")
    private WebElement inputBirthDateDay;

    @FindBy(how = How.ID, using = "birthDate_year")
    private WebElement inputBirthDateYear;

    @FindBy(how = How.ID, using = "nextStep")
    private WebElement buttonContinue;

    public JalapenoIdentifyPatientBeforeActivationPage(WebDriver driver) {
        super(driver);
    }

    public JalapenoIdentifyPatientBeforeActivationPage(WebDriver driver, String url) {
        super(driver, url);
    }

    @Override
    public boolean assessBasicPageElements() {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(inputPostalCode);
        webElementsList.add(inputBirthDateMonth);
        webElementsList.add(inputBirthDateDay);
        webElementsList.add(inputBirthDateYear);
        webElementsList.add(buttonContinue);

        return assessPageElements(webElementsList);
    }

    public JalapenoPatientCreateSecurityDetailsPage fillInPatientDataAndSubmitForm(JalapenoPatient patient) {
        Map<WebElement, String> itemsToChange = new HashMap<WebElement, String>();
        itemsToChange.put(inputPostalCode, patient.getZipCode());
        itemsToChange.put(inputBirthDateMonth, patient.getDOBMonthText());
        itemsToChange.put(inputBirthDateDay, patient.getDOBDay());
        itemsToChange.put(inputBirthDateYear, patient.getDOBYear());

        updateAndValidateWebElements(itemsToChange, buttonContinue);
        return PageFactory.initElements(driver, JalapenoPatientCreateSecurityDetailsPage.class);
    }
}
