package com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.product.object.maps.patientportal1.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class FormCurrentSymptomsPage extends PortalFormPage {

    public FormCurrentSymptomsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "idonot_symptoms_general_group")
    WebElement noSymptoms;

    @FindBy(id = "chills_symptom_general")
    WebElement checkChills;

    @FindBy(id = "insomnia_symptom_general")
    WebElement checkInsomnia;

    @FindBy(id = "bruising_symptom_blood")
    WebElement checkBruising;

    @FindBy(id = "earache_sumptom_ent")
    WebElement checkEarache;

    @FindBy(id = "symptoms_anythingelse")
    WebElement commentsField;

    @FindBy(linkText = "Save and finish another time")
    WebElement saveAndFinishLater;

    @FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
    private WebElement saveAndContinuebtn;

    public WebElement getCheckEarache() {
        return checkEarache;
    }

    public String getCommentTextContent() {
        WebDriverWait wait = new WebDriverWait(driver, 6, 2000);
        wait.until(ExpectedConditions.visibilityOf(commentsField));
        return commentsField.getText();
    }

    /**
     * @Description:Set No Symptoms
     * @throws Exception
     */
    public void setNoSymptoms() throws Exception {
        PortalUtil.PrintMethodName();
        PortalUtil.setquestionnarieFrame(driver);
        noSymptoms.click();
    }

    /**
     * @Description: Set some basic symptom for later PDF test
     * @throws: Exception
     */
    public void setBasicSymptoms() throws Exception {
        checkChills.click();
        checkInsomnia.click();
        checkBruising.click();
        checkEarache.click();
    }

    public void enterComment(String comment) {
        commentsField.sendKeys(comment);
    }

    public void closeForm() {
        saveAndFinishLater.click();
    }
}
