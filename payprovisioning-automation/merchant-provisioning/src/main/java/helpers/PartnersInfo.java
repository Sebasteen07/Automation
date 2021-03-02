package helpers;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;

import utils.ProvisioningUtils;

import com.jayway.restassured.path.json.JsonPath;
import com.medfusion.common.utils.PropertyFileLoader;

public class PartnersInfo {
	
	protected PropertyFileLoader testData;
	
	 
	 public String createPartner(String postpartnerurl, String partnerusername, String partnerpassword) throws IOException {
		 
		 Map<String,String> credentials = new HashMap<String,String>();
		 credentials.put("username", partnerusername);
		 credentials.put("password", partnerpassword);
		 
		 Response response = given().contentType(ContentType.JSON).
		 header("Authorization", ProvisioningUtils.financeAuthorization).
		 body(credentials).and().log().all().when().
		 post(postpartnerurl).then().
		 assertThat().statusCode(200).and().
		 contentType(ContentType.JSON).and().extract().response();
		 
		 JsonPath jsonpath = new JsonPath(response.asString());
		 Assert.assertNotNull(jsonpath, "Response was null. Partner not created!");
		 System.out.println("Partner created successfully:" + jsonpath.get("id").toString());
		 ProvisioningUtils.savePartner(jsonpath.get("id").toString(),jsonpath.get("username").toString(),jsonpath.get("password").toString());
		 return jsonpath.get("password").toString();
		
		 
	 }

	 public void checkUserNamePassword(String postpartners, String username, String password) {
		 given().contentType(ContentType.JSON).
		 header("Authorization", ProvisioningUtils.financeAuthorization)
		 .param("username",username)
		 .param("password",password).
		 and().log().all().when().
		 get(postpartners+"/check").then().
		 assertThat().statusCode(200);
	}

	 
	 public String updateUserNamePassword(String postpartners, String usernameupdate, String passwordupdate) {
		 String partnerid = ProvisioningUtils.getPartnerId();
		 Response response = given().contentType(ContentType.JSON).
		 header("Authorization", ProvisioningUtils.financeAuthorization)
		.param("username",usernameupdate)
		.param("password",passwordupdate)
		.param("id",partnerid).
		 and().log().all().when().
		 put(postpartners+"/"+partnerid).then().
		 assertThat().statusCode(200).and().extract().response();
		 
		 JsonPath jsonpath = new JsonPath(response.asString());
		 Assert.assertNotNull(jsonpath, "Response was null. Partner was not updated!");
		 Assert.assertEquals(jsonpath.get("id").toString(), partnerid);
		 Assert.assertEquals(jsonpath.get("username").toString(), usernameupdate);
		 return jsonpath.get("password").toString();
		
	}

	  public void resetPartnerCredentials(String postpartners, String usernameupdate, String updatedpassword) {
		  String partnerid = ProvisioningUtils.getPartnerId();
		  Response response = given().contentType(ContentType.JSON).
		  header("Authorization", ProvisioningUtils.financeAuthorization).
		  and().log().all().when().
		  post(postpartners+"/"+partnerid).then().
		  assertThat().statusCode(200).and().extract().response();
			 
		  JsonPath jsonpath = new JsonPath(response.asString());
		  Assert.assertNotNull(jsonpath, "Response was null. Password update for partner was not successful");
		  Assert.assertEquals(jsonpath.get("id").toString(), partnerid);
		  Assert.assertEquals(jsonpath.get("username").toString(), usernameupdate);
		  Assert.assertNotSame(jsonpath.get("password").toString(), updatedpassword, "Password reset not successful");
		
	}

	  public void deletePartner(String postpartners) {
		  String partnerid = ProvisioningUtils.getPartnerId();
		  given().contentType(ContentType.JSON).
		  header("Authorization", ProvisioningUtils.financeAuthorization).
		  and().log().all().when().
		  delete(postpartners+"/"+partnerid).then().
		  assertThat().statusCode(200);
		
	}

	  public void getPartners(String postpartners, String username, String password) {
		  Response response = given().contentType(ContentType.JSON).
		  header("Authorization", ProvisioningUtils.financeAuthorization).
		  and().log().all().when().
		  get(postpartners).then().
		  assertThat().statusCode(200).and().
		  contentType(ContentType.JSON).and().extract().response();
					 
		  String partners = response.asString();
		  ArrayList<Map<String,?>> jsonAsArrayList = from(partners).get("");
		  System.out.println("MMID has:"+jsonAsArrayList.size()+" partners");
					 
	}

}
