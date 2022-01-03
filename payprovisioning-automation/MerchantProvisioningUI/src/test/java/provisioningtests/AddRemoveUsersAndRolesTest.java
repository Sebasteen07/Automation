//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package provisioningtests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;

import pageobjects.AddUsersAndRolesPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;

public class AddRemoveUsersAndRolesTest extends ProvisioningBaseTest {
	@Test
	public void testAddUsersAndRoles() throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);

		merchantSearchPage.findByMerchantId(testData.getProperty("merchant.ID"));
		merchantSearchPage.searchButtonClick();

		logStep("Going to click on view Merchant details page");
		merchantSearchPage.viewDetailsButtonClick();
		MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		logStep("Going to verify title of merchant details page");
		merchantDetailsPage.verifyPageTitle();	
		
		logStep("Going to verify Add User Role Button on Merchant Details page");
		merchantDetailsPage.verifyAddUserRoleButtonsPresent();
		
		logStep("Going to click on Add Users and Roles from merchant details page");
		merchantDetailsPage.clickAddUsersAndRoles();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		logStep("Going to click on Add Users and Roles from edit users and roles page");
		AddUsersAndRolesPage addUsersAndRolesPage = PageFactory.initElements(driver, AddUsersAndRolesPage.class);
		Assert.assertTrue(addUsersAndRolesPage.verifyButtonsPresent());
		addUsersAndRolesPage.clickAddUsersOrRolesButton();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
		
		logStep("Verify fields on Add User and Roles Page");
		Assert.assertTrue(addUsersAndRolesPage.verifyUserInfoFields());
		
		logStep("Fill in User ID and User Name");
		addUsersAndRolesPage.editPracticeStaffId(testData.getProperty("practice.staff.Id"));
		addUsersAndRolesPage.editUserName(testData.getProperty("practice.staff.Name"));
		
		logStep("Check all Roles");
		addUsersAndRolesPage.checkRolesCheckboxes();
		
		logStep("Going to click on Add Users and Roles");
		addUsersAndRolesPage.clickAddUsersAndRoleButton();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		logStep("Going to verify AddedUserRoles");
		merchantDetailsPage.verifyAddedUserRoles();
		
		logStep("Remove User Roles");
		Assert.assertTrue(addUsersAndRolesPage.verifyUpdateAndRemoveButtonsPresent());
		addUsersAndRolesPage.clickRemoveUserRoles();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		logStep("Confirm Remove User Roles");
		addUsersAndRolesPage.confirmClickOnRemoveUserRoles();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		logStep("All User Roles successfully added and removed");
}
}
