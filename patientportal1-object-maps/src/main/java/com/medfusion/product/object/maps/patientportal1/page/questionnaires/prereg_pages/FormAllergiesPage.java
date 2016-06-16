package com.medfusion.product.object.maps.patientportal1.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.patientportal1.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class FormAllergiesPage extends PortalFormPage {

    public FormAllergiesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "idonot_drug_allergies")
    WebElement noDrugAllergies;

    @FindBy(id = "idonot_food_allergies")
    WebElement noFoodAllergies;

    @FindBy(id = "idonot_environmental_allergies")
    WebElement noEnvironmentalAllergies;

    @FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
    private WebElement saveAndContinuebtn;

    /**
     * @Description:Set No Drug Allergies
     * @throws Exception
     */
    public void setNoDrugAllergies() throws Exception {
        PortalUtil.PrintMethodName();
        PortalUtil.setquestionnarieFrame(driver);
        noDrugAllergies.click();

    }

    /**
     * @Description:Set No Food Allergies
     * @throws Exception
     */
    public void setNoFoodAllergies() throws Exception {
        PortalUtil.PrintMethodName();
        PortalUtil.setquestionnarieFrame(driver);
        noFoodAllergies.click();

    }

    /**
     * @Description:Set No Environment Allergies
     * @throws Exception
     */
    public void setNoEnvironmentalAllergies() throws Exception {
        PortalUtil.PrintMethodName();
        PortalUtil.setquestionnarieFrame(driver);
        noEnvironmentalAllergies.click();
    }
}
