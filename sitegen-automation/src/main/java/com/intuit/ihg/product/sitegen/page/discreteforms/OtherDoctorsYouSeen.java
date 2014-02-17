package com.intuit.ihg.product.sitegen.page.discreteforms;

	
	
	import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;
	
	public class OtherDoctorsYouSeen extends BasePageObject{
		
		@FindBy(xpath="//*[@id='form_form']/div[2]/div[1]/ul/li[1]/a")
		private WebElement lnkOtherDoctorsYouSeen;

		@FindBy(id="save_config_form")              
		private WebElement btnSave;
		
		@FindBy(id = "hide_currentproviders_check")
		private WebElement hideCurrentprovidersCheck;	

		@FindBy(id = "referring_physician_firstname")
		private WebElement referringPhysicianFirstname;	

		@FindBy(id = "referring_physician_phone_number")
		private WebElement referringPhysicianPhoneNumber;	

		@FindBy(id = "referring_physician_address")
		private WebElement referringPhysicianAddress;	

		@FindBy(id = "referring_physician_city")
		private WebElement referringPhysicianCity;	

		@FindBy(id = "referring_physician_state")
		private WebElement referringPhysicianState;	

		@FindBy(id = "referring_physician_zipcode")
		private WebElement referringPhysicianZipcode;	

		@FindBy(id = "referring_physician_speciality")
		private WebElement referringPhysicianSpeciality;	

		@FindBy(id = "referring_physician_speciality_is_primary")
		private WebElement referringPhysicianSpecialityIsPrimary;	

		@FindBy(id = "doctors_seen")
		private WebElement doctorsSeen;	

		@FindBy(id = "pharmacy_name")
		private WebElement pharmacyName;	

		@FindBy(id = "pharmacy_phone")
		private WebElement pharmacyPhone;
	
	
	public OtherDoctorsYouSeen(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkOtherDoctorsYouSeen);
		} catch (Exception e) {
			// Catch any element not found errors
		}
	
		return result;
	}
	
	
	/**
	 * Click on link - Other Doctors You Seen	
	 * @return
	 */
	
	public CurrentSymptomsPage clicklnkOtherDoctorsYouSeen() 
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkOtherDoctorsYouSeen);
		lnkOtherDoctorsYouSeen.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		// Close the browser window
		return PageFactory.initElements(driver,CurrentSymptomsPage.class);
	}

	
}
	
