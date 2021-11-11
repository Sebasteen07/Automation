package com.mfpay.merchant_provisioning.tests;


import java.io.IOException;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import utils.ProvisioningUtils;
import com.jayway.restassured.http.ContentType;
import com.medfusion.common.utils.PropertyFileLoader;

public class Provisioning_roles {
	protected PropertyFileLoader testData;
	


	public String getBaseUrl() throws IOException {
		testData = new PropertyFileLoader();
		String baseurl = testData.getProperty("baseurl");
		return baseurl;
	}
	
	
	//Verify finance user returns finance role
	@Test
	public void getFinanceRole() throws Exception {
		String roles = getBaseUrl()+ProvisioningUtils.getRoles;
		given().
		header("Authorization", ProvisioningUtils.financeAuthorization).
		contentType(ContentType.JSON).and().log().all().
		when().
		get(roles).
		then().assertThat().
		statusCode(200).and().contentType(ContentType.JSON).and().
		body(("[0]").toString(),equalTo("merchant_provisioning_finance"));
	}

	
	//Verify Implementation user returns Implementation role
	@Test
	public void getImplementationRole() throws Exception {
		String roles = getBaseUrl()+ProvisioningUtils.getRoles;
		given().
		header("Authorization", ProvisioningUtils.implementationAuthorization).
		contentType(ContentType.JSON).and().log().all().
		when().
		get(roles).
		then().assertThat().
		statusCode(200).and().contentType(ContentType.JSON).and().
		body(("[0]").toString(),equalTo("merchant_provisioning_implementation"));
		}
		
	
	//Verify admin user returns admin role
	@Test
	public void getAdminRole() throws Exception {
		String roles = getBaseUrl()+ProvisioningUtils.getRoles;
		given().
		header("Authorization", ProvisioningUtils.adminAuthorization).
		contentType(ContentType.JSON).and().log().all().
		when().
		get(roles).
		then().assertThat().
		statusCode(200).and().contentType(ContentType.JSON).and().
		body(("[0]").toString(),equalTo("merchant_provisioning_admin"));
	}

	
	//Verify invalid user gets Unauthorized message
	@Test
	public void getInvalidUserRole() throws Exception {
		String roles = getBaseUrl()+ProvisioningUtils.getRoles;
		given().
		header("Authorization", ProvisioningUtils.invalidAuthorization).
		contentType(ContentType.JSON).and().log().all().
		when().
		get(roles).
		then().assertThat().
		statusCode(401).and().contentType(ContentType.JSON);
	}


}
