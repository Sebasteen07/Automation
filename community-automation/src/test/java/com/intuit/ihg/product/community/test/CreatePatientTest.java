package com.intuit.ihg.product.community.test;


import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.product.community.utils.Community;
import com.intuit.ihg.product.community.utils.CommunityTestData;
import com.intuit.ihg.product.community.utils.CreatePatientBeta;


public class CreatePatientTest extends BaseTestNGWebDriver {
	

	@Test(enabled = false, groups = { "AcceptanceTests" })
	public void createPatientTest() throws Exception {

		CreatePatientBeta createPatient = new CreatePatientBeta();
		
		Community community = new Community();
		
		CommunityTestData communityTestData = new CommunityTestData(community);

		createPatient.CreatePatientTest(driver, communityTestData);
		
	}
}
