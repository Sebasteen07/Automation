package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProceduresPage extends ConfiguratorFormPage {

	@FindBy(id = "save_config_form")
	private WebElement btnSave;

	@FindBy(id = "hide_procedures_section_check")
	private WebElement hideProceduresSection;

	@FindBy(id = "sigmoidoscopy_line")
	private WebElement sigmoidoscopy;

	@FindBy(id = "eyeexam_line")
	private WebElement eyeexam;

	@FindBy(id = "dentalexam_line")
	private WebElement dentalexam;

	@FindBy(id = "cholestrolexam_line")
	private WebElement cholestrolexam;

	@FindBy(id = "diabetesexam_line")
	private WebElement diabetesexam;

	@FindBy(id = "colonoscopyexam_line")
	private WebElement colonoscopyexam;

	@FindBy(id = "mammogramexam_line")
	private WebElement mammogramexam;

	@FindBy(id = "papsmearexam_line")
	private WebElement papsmearexam;

	@FindBy(id = "bonedensityscanexam_line")
	private WebElement bonedensityscanexam;

	@FindBy(id = "psaexam_line")
	private WebElement psaexam;

	@FindBy(id = "prostateultrasoundexam_line")
	private WebElement prostateultrasoundexam;

	@FindBy(id = "digitalrtectalexam_line")
	private WebElement digitalrtectalexam;

	@FindBy(id = "procedures_other_line")
	private WebElement proceduresOther;

	@FindBy(id = "procedures_anythingelse_line")
	private WebElement proceduresComments;

	@FindBy(xpath = "//div[contains(@class,'conditions_section')]//*[contains(text(),'Female-Specific')]//input")
	private WebElement femaleGroupInput;


	public ProceduresPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
}


