// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.UsersDetails;
import com.medfusion.mfpay.merchant_provisioning.helpers.Validations;
import com.medfusion.mfpay.merchant_provisioning.utils.DBUtils;
import com.medfusion.mfpay.merchant_provisioning.utils.MPTestData;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PracticeRoleWorkflowsTest extends BaseRest {
	protected PropertyFileLoader testData;

	@BeforeTest
	public void setBaseUri() throws Exception {
		testData = new PropertyFileLoader();
		setupFinanceRequestSpecBuilder();
	}

	@Test(enabled = true, groups = { "MerchantProvisioningBEAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSetPracticeRoleToMerchantUser() throws Throwable {
		
		String practiceStaffId = IHGUtil.createRandomNumericString(5);

		String getusers = ProvisioningUtils.PRACTICE_ROLE + "practice/" + testData.getProperty("practice.id")
				+ "/practiceStaffId/" + practiceStaffId;
		UsersDetails usersdetails = new UsersDetails();

		Response response = usersdetails.createPracticeUser(getusers, testData.getProperty("staff.username"),
				testData.getProperty("practice.role"));

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations.validatePracticeRoles(jsonpath, practiceStaffId, testData.getProperty("practice.id"),
				Arrays.asList(testData.getProperty("practice.role")));
	}

	@Test(dataProvider = "practice_role_test", dataProviderClass = MPTestData.class,
			enabled = true, groups = { "MerchantProvisioningBEAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSetPracticeRoleToMerchantUserInvalidData(String practiceStaffId, String practiceId, String username,
			String practiceRole) throws IOException {
		String getusers = ProvisioningUtils.PRACTICE_ROLE + practiceStaffId + "/practice/" + practiceId;

		UsersDetails usersdetails = new UsersDetails();
		Response response = usersdetails.createPracticeUser(getusers, username, practiceRole);

		JsonPath jsonpath = new JsonPath(response.asString());
		if (response.getStatusCode() == 200) {
			Validations.validatePracticeRoles(jsonpath, practiceStaffId, practiceId, Arrays.asList(practiceRole));
		} else {
			Assert.assertNotNull(jsonpath.get("error"));
		}
	}

	@Test(enabled = true, groups = { "MerchantProvisioningBEAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetUsers() throws Throwable {
		String getusers = ProvisioningUtils.MERCHANT_USER + "/practice/" + testData.getProperty("practice.id")
				+ "/user/" + testData.getProperty("user.id") + "/metadata";

		UsersDetails usersdetails = new UsersDetails();

		Response response = usersdetails.getMerchantUserRoles(getusers);

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertEquals(jsonpath.get("practiceLevelRoles[0]").toString(), "PRACTICE_POS_ADMIN");
		Assert.assertEquals(jsonpath.get("practiceStaffId").toString(), testData.getProperty("user.id"));
		Assert.assertEquals(jsonpath.get("practiceId").toString(), testData.getProperty("practice.id"));
	}
	

	
	@Test(enabled = true, groups = { "MerchantProvisioningBEAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testVerifyDbForPracticeRolesToMerchantUser() throws Throwable {

		String practiceStaffId = IHGUtil.createRandomNumericString(5);

		// checking role before hitting endpoint
		Object roleCheckBeforeCreation = DBUtils.executeQueryOnDBGetResult("rcm",
				"SELECT * FROM public.practice_user_role where p_org_staff_id=" + practiceStaffId, "role_name");

		Assert.assertEquals("", roleCheckBeforeCreation);

		String getusers = ProvisioningUtils.PRACTICE_ROLE + "practice/" + testData.getProperty("practice.id")
				+ "/practiceStaffId/" + practiceStaffId;
		UsersDetails usersdetails = new UsersDetails();
		List<String> roleList = usersdetails.getRolesAsAList(testData.getProperty("practice.role"),
				Integer.parseInt(testData.getProperty("practice.role.count")));

		Response response = usersdetails.createPracticeUserWithMultipleRoles(getusers,
				testData.getProperty("staff.username"), roleList);

		JsonPath jsonPath = new JsonPath(response.asString());
		Validations.validatePracticeRoles(jsonPath, practiceStaffId, testData.getProperty("practice.id"), roleList);

		Object roleCheckAfterCreation = DBUtils.executeQueryOnDBGetResult("rcm",
				"SELECT * FROM public.practice_user_role where p_org_staff_id=" + practiceStaffId, "role_name");

		// Verifying role in DB after assigning role
		Assert.assertEquals(jsonPath.get("practiceLevelRoles[1]").toString(), roleCheckAfterCreation);
		
		
		String countOfRoleForPracticeStaff = DBUtils.executeQueryOnDB("rcm",
				"SELECT COUNT(*) FROM public.practice_user_role where p_org_staff_id=" + practiceStaffId);

		// Verifying role in DB after assigning role
		Assert.assertEquals(countOfRoleForPracticeStaff, testData.getProperty("practice.role.count"));
	}


}
