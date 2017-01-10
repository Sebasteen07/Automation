package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.ConfiguratorFormPage;

public class VaccinationsPage extends ConfiguratorFormPage {

	@FindBy(id = "hide_immunizations_check")
	private WebElement hideImmunizationsCheck;

	@FindBy(id = "tetanusvaccination")
	private WebElement tetanusvaccination;

	@FindBy(id = "hpvvaccination")
	private WebElement hpvvaccination;

	@FindBy(id = "influeenzavaccination")
	private WebElement influeenzavaccination;

	@FindBy(id = "pneumoniavaccination")
	private WebElement pneumoniavaccination;

	@FindBy(id = "meningitis_immunization")
	private WebElement meningitisImmunization;

	@FindBy(id = "pertussis_immunization")
	private WebElement pertussisImmunization;

	@FindBy(id = "shingles_immunization")
	private WebElement shinglesImmunization;

	@FindBy(id = "chickenpoxorvaricella_immunization")
	private WebElement chickenpoxorvaricellaImmunization;

	@FindBy(id = "tdap_immunization")
	private WebElement tdapImmunization;

	@FindBy(id = "hipatitisa_immunization")
	private WebElement hipatitisaImmunization;

	@FindBy(id = "hipatitisb_immunization")
	private WebElement hipatitisbImmunization;

	@FindBy(id = "mmr_immunization")
	private WebElement mmrImmunization;

	@FindBy(id = "polio_immunization")
	private WebElement polioImmunization;

	@FindBy(id = "immunizations_other")
	private WebElement immunizationsOther;

	@FindBy(id = "immunizations_anythingelse")
	private WebElement immunizationsComments;


	public VaccinationsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
}


