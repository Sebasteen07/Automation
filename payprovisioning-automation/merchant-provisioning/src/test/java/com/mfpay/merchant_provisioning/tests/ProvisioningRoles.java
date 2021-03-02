package com.mfpay.merchant_provisioning.tests;


import java.io.IOException;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import utils.ProvisioningUtils;
import com.jayway.restassured.http.ContentType;
import com.medfusion.common.utils.PropertyFileLoader;

public class ProvisioningRoles {
	protected PropertyFileLoader testData;
	


	public String getBaseUrl() throws IOException {
		testData = new PropertyFileLoader();
		String baseurl = testData.getProperty("baseurl");
		return baseurl;
	}
	
	
	//Verify finance user returns finance role
	@Test
	public void getFinanceRole() throws Exception {
		given().header("Authorization", ProvisioningUtils.financeAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().
		statusCode(200).and().body(("[0]").toString(),equalTo("merchant_provisioning_finance"));
	}

	
	//Verify Implementation user returns Implementation role
	@Test
	public void getImplementationRole() throws Exception {
		given().header("Authorization", ProvisioningUtils.implementationAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().
		statusCode(200).and().body(("[0]").toString(),equalTo("merchant_provisioning_implementation"));
	}
		
	
	//Verify admin user returns admin role
	@Test
	public void getAdminRole() throws Exception {
		given().header("Authorization", ProvisioningUtils.adminAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().
		statusCode(200).and().body(("[0]").toString(),equalTo("merchant_provisioning_admin"));
	}

	
	//Verify invalid user gets Unauthorized message
	@Test
	public void getInvalidUserRole() throws Exception {
		given().header("Authorization", ProvisioningUtils.invalidAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().statusCode(401);
	}


}
