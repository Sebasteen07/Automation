package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

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
	
	@FindBy(id ="generalanesthetic_allergy_drug")
	WebElement GeneralAnesthetic;
	
	@FindBy(id = "peanuts_allergy_food")
	WebElement Peanuts;
	
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
	public void setGeneralAnesthetic_20() throws Exception {
		PortalUtil.PrintMethodName();
		WebElement W1=driver.findElement(By.xpath("//iframe[@title='Forms']"));
		driver.switchTo().frame(W1);
		GeneralAnesthetic.click();
	}
	public void setPeanuts_20() throws Exception {
		PortalUtil.PrintMethodName();
		Peanuts.click();
	}
	public FormVaccinePage SetAllergies() throws Exception
	{
		Thread.sleep(2000);
		setGeneralAnesthetic_20();
		setPeanuts_20();
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormVaccinePage.class);

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

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Allergies"))).isDisplayed();
	}
}
