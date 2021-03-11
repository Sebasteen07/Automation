package helpers;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import modelPOJOS.Roles;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ProvisioningUtils;


public class UsersDetails {

	public void getUsers(String getusers) {
		Response response = given().contentType(ContentType.JSON).
		header("Authorization", ProvisioningUtils.implementationAuthorization).
		and().log().all().when().get(getusers).then().
		assertThat().statusCode(200).and().extract().response();
				 
		String userdetails = response.asString();
		if(!(userdetails == null)){
			ArrayList<Map<String,?>> jsonAsArrayList = from(userdetails).get("");
			System.out.println("No.of staff users for this mmid:"+String.valueOf(jsonAsArrayList.size()));
		}

				
		
	}

	public void createStaffUser(String getusers, String staffusername, String practicestaffid) throws IOException {
		Map<String, Object> user = Roles.getRolesMap(staffusername,practicestaffid );
		Response response = given().contentType(ContentType.JSON).
		header("Authorization", ProvisioningUtils.implementationAuthorization).
		body(user).and().log().all().
		when().post(getusers).then().statusCode(200).assertThat()
	   .body("roles" ,containsInAnyOrder("USER","FUNDINGANDFEES", "FULLDRR","POINTOFSALE", "POS_ADMIN", "VOIDREFUND"))
	   .extract().response();
		Validations.validateStaffUser(staffusername, practicestaffid,response.asString())	;
					 
			
	}

	public void getRolesForUser(String getusers, String staffname, String practicestaffid) {
		given().contentType(ContentType.JSON).
		header("Authorization", ProvisioningUtils.implementationAuthorization).
		and().log().all().when().get(getusers+"/"+practicestaffid).then().statusCode(200)
		.assertThat()
	   .body("roles" ,containsInAnyOrder("USER","FUNDINGANDFEES", "FULLDRR","POINTOFSALE", "POS_ADMIN", "VOIDREFUND"));
	}
	
	

}
