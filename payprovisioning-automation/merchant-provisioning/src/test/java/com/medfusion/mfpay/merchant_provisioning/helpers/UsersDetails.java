// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.medfusion.mfpay.merchant_provisioning.pojos.Roles;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import io.restassured.response.Response;


public class UsersDetails extends BaseRest {

	public void getUsers(String getusers) {
		Response response = given().spec(requestSpec).
		when().get(getusers).then().
		spec(responseSpec).and().extract().response();
				 
		String userdetails = response.asString();
		if(!(userdetails == null)){
			ArrayList<Map<String,?>> jsonAsArrayList = from(userdetails).get("");
			System.out.println("No.of staff users for this mmid:"+String.valueOf(jsonAsArrayList.size()));
		}		
		
	}

	public void createStaffUser(String getusers, String staffusername, String practicestaffid) throws IOException {
		Map<String, Object> user = Roles.getRolesMap(staffusername,practicestaffid );
		Response response = given().spec(requestSpec).
		body(user).when().post(getusers).then().assertThat()
	   .body("roles" ,containsInAnyOrder("USER","FUNDINGANDFEES", "FULLDRR","POINTOFSALE", "POS_ADMIN", "VOIDREFUND"))
	   .extract().response();
		Validations.validateStaffUser(staffusername, practicestaffid,response.asString())	;
					 		
	}

	public void getRolesForUser(String getusers, String staffname, String practicestaffid) {
		given().spec(requestSpec).when().get(getusers+"/"+practicestaffid).then().spec(responseSpec)
		.assertThat().body("roles" ,containsInAnyOrder("USER","FUNDINGANDFEES", "FULLDRR","POINTOFSALE", "POS_ADMIN", "VOIDREFUND"));
	}

	public Response createPracticeUser(String getusers, String staffusername, String practiceRoles) throws IOException {
		Map<String, Object> body = Roles.getPracticeRoleMap(staffusername, practiceRoles);
		return given().spec(requestSpec).log().all().
				body(body).when().post(getusers).then().extract().response();
	}
	
	public Response getMerchantUserRoles(String getusers) {
		return given().spec(requestSpec).when().get(getusers).then().extract().response();
		
	}
	
	
	public List<String> getRolesAsAList(String practiceRoles, int noofRollesToBeAdded) {
		ArrayList<String> rolesList = new ArrayList<String>();
		for (int i = 1; i <= noofRollesToBeAdded; i++) {
			String roleToAddExtract = "practice.role" + i;
			String roleToAdd = testData.getProperty(roleToAddExtract);
			rolesList.add(roleToAdd);
		}

		return rolesList;
	}
	
	public Response createPracticeUserWithMultipleRoles(String getusers, String staffusername, List<String> roleList)
			throws IOException {
		
		Map<String, Object> body = Roles.getPracticeRolesMap(staffusername, roleList);
		return given().spec(requestSpec).log().all().body(body).when().post(getusers).then().extract().response();
	}



}
