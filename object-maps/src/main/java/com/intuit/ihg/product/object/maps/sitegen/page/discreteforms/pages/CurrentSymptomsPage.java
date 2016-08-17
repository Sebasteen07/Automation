package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages.MedicationsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CurrentSymptomsPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='medications_section']/a")
	private WebElement lnkMedications;
	
	@FindBy(xpath = "//h5[contains(text(), 'General Health')]/span/input")               
	private WebElement chckGeneralHealth;
	
	@FindBy(id = "fever_symptom_general")
	private WebElement feverSymptomGeneral;
	
	@FindBy(id = "weakness_symptom_general")
	private WebElement weaknessSymptomGeneral;
	
	@FindBy(xpath = "//h5[contains(text(), 'Blood')]/span/input")              
	private WebElement chckBlood;
	
	@FindBy(id = "bleeding_symptom_blood")
	private WebElement bleedingSymptomBlood;	

	@FindBy(id = "bruising_symptom_blood")
	private WebElement bruisingSymptomBlood;
	
	@FindBy(xpath = "//h5[contains(text(), 'Ears, Nose & Throat')]/span/input")
	private WebElement chckEyesEarsNoseThroat;
	
	@FindBy(id = "bleedinggums_symptom_ent")
	private WebElement bleedinggumsSymptomEnt;	

	@FindBy(id = "difficultyswallowing_symptom_ent")
	private WebElement difficultyswallowingSymptomEnt;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;

	public CurrentSymptomsPage(WebDriver driver) 
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Click on link -  Current Symptoms
	 * @return PageFactory initialization for Medications Page
	 */
	
	public MedicationsPage clicklnkMedications()
	{	
		
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkMedications);
		lnkMedications.click();
		
		return PageFactory.initElements(driver,MedicationsPage.class);
	}
	
	/**
	 * Select basic symptoms to appear in the form
	 */
	
	public void selectBasicSymptoms() 
	{
		log("Check General Health");
		IHGUtil.waitForElement(driver, 30, chckGeneralHealth);
		chckGeneralHealth.click();
		
		log("Check Blood");
		chckBlood.click();
		
		log("Check Eyes, Ears,Nose and Throat");
		chckEyesEarsNoseThroat.click();
	}
	
	/**
	 * Click on save button and close the form editor
	 */
	
	public void clickSave() 
	{
		btnSave.click();
	}
	
}
