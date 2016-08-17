package com.medfusion.product.patientportal1.flows;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.healthform.HealthFormPage;
import com.medfusion.product.patientportal1.utils.TestcasesData;
import org.openqa.selenium.support.PageFactory;


public class CheckOldCustomFormTest extends BaseTestNGWebDriver {
	
	private String url="";
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String str) {
		url = str;
	}

    public HealthFormPage checkOldCustomForm    (WebDriver driver,TestcasesData testcasesData, String formName) throws Exception {


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
		
	//	verifyEquals(verifyTextPresent(driver,"Insurance Type"),true,"Insurance Type is not present in form on Portal");
		verifyEquals(verifyTextPresent(driver,"First Name"),true, "Demographic information is not present in form on Portal");
	//	verifyEquals(verifyTextPresent(driver,"Vital"),true, "Vital information is not present in form on Portal");\
//
        pHealthForm.clickNext();
//
//        log("Step 4: Fill Vitals");
//        pHealthForm.fillVitals();
//
//        pHealthForm.clickNext();
//
        log("Step 5: exit non completed form");
        pHealthForm.clickHealthForms();
        
        Thread.sleep(8000);
        
        verifyEquals(verifyTextPresent(driver,"completed 2 of 3 pages"),true, "Partialy completed form not saved correctly");
        return PageFactory.initElements(driver, HealthFormPage.class);
            
	}
    
    public void checkDeletedPages(WebDriver driver, HealthFormPage page, String formName) throws Exception {
        
        page.clickHealthForms();
        Thread.sleep(8000);
        verifyEquals(verifyTextPresent(driver,"completed 0 of 1 pages"),true, "Partialy completed form after deleted pages not displayed correctly");
        page.selectOldCustomForm(formName);
        verifyEquals(verifyTextPresent(driver,"First Name"),true, "Demographic information is not present in form on Portal");
    }

	
}
