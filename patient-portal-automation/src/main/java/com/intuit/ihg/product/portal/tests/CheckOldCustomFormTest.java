package com.intuit.ihg.product.portal.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.healthform.HealthFormPage;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class CheckOldCustomFormTest extends BaseTestNGWebDriver {
	
	private String url="";
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String str) {
		url = str;
	}


	public void checkOldCustomForm(WebDriver driver,TestcasesData testcasesData, String formName) throws Exception {

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if ( url.isEmpty() ){
			url = testcasesData.geturl();
		}
			
		log("step 1: Get Data from Excel");
		log("URL: " + url);

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, url);
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());
		
		log("step 2: Click on CustomForm");
		HealthFormPage pHealthForm = pMyPatientPage.clickFillOutFormsLink();
		pHealthForm.selectOldCustomForm(formName);
		
		verifyEquals(verifyTextPresent(driver,"Insurance Type"),true,"Insurance Type is not present in form on Portal");
		verifyEquals(verifyTextPresent(driver,"First Name"),true, "Demographic information is not present in form on Portal");
		verifyEquals(verifyTextPresent(driver,"Vital"),true, "Vital information is not present in form on Portal");

	}
	
}
