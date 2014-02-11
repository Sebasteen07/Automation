package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class ExamsTestsAndProceduresPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[10]/a/em")
	private WebElement lnkExamsTestsAndProcedures;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_procedures_section_check")
	public WebElement hideProceduresSection;	

	@FindBy(id = "sigmoidoscopy_line")
	public WebElement sigmoidoscopy;	

	@FindBy(id = "eyeexam_line")
	public WebElement eyeexam;	

	@FindBy(id = "dentalexam_line")
	public WebElement dentalexam;	

	@FindBy(id = "cholestrolexam_line")
	public WebElement cholestrolexam;	

	@FindBy(id = "diabetesexam_line")
	public WebElement diabetesexam;	

	@FindBy(id = "colonoscopyexam_line")
	public WebElement colonoscopyexam;	

	@FindBy(id = "mammogramexam_line")
	public WebElement mammogramexam;	

	@FindBy(id = "papsmearexam_line")
	public WebElement papsmearexam;	

	@FindBy(id = "bonedensityscanexam_line")
	public WebElement bonedensityscanexam;	

	@FindBy(id = "psaexam_line")
	public WebElement psaexam;	

	@FindBy(id = "prostateultrasoundexam_line")
	public WebElement prostateultrasoundexam;	

	@FindBy(id = "digitalrtectalexam_line")
	public WebElement digitalrtectalexam;	

	@FindBy(id = "procedures_other_line")
	public WebElement proceduresOther;	

	@FindBy(id = "procedures_anythingelse_line")
	public WebElement proceduresComments;
	

	public ExamsTestsAndProceduresPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, lnkExamsTestsAndProcedures);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Exam Tests and Procedures
	 * @return
	 */
	
	public IllnessesAndConditionsPage clicklnkExamsTestsAndProcedures()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkExamsTestsAndProcedures);
		lnkExamsTestsAndProcedures.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		// Close the browser window
		return PageFactory.initElements(driver,IllnessesAndConditionsPage.class);
	}
}


