// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Roles {

private String practicestaffId;
private String userName;
private List<String> roles = null;

public String getPracticestaffId() {
return practicestaffId;
}

public void setPracticestaffId(String practicestaffId) {
this.practicestaffId = practicestaffId;
}

@Override
public String toString() {
	return "Roles [practicestaffId=" + this.practicestaffId + ", userName="
			+ this.userName + ", roles=" + this.roles + "]";
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public List<String> getRoles() {
return roles;
}

public void setRoles(List<String> roles) {
this.roles = roles;
}

public static Map<String, Object> getRolesMap(String staffusername, String practicestaffid){
	Map<String, Object> user = new HashMap<String, Object>(); 
	user.put("practicestaffId", practicestaffid);
	user.put("userName",staffusername); 
	user.put("roles", Arrays.asList("FUNDINGANDFEES", "FULLDRR","POINTOFSALE", "MERCHANT_POS_ADMIN", "VOIDREFUND"));
	return user;
	
}
public static Map<String, Object> getPracticeRoleMap(String staffusername, String practiceRoles){
	Map<String, Object> user = new HashMap<String, Object>();
	user.put("userName",staffusername);
	user.put("roles", Arrays.asList(practiceRoles));
	return user;
}

public static Map<String, Object> getPracticeRolesMap(String staffusername, List<String> roleList){
	Map<String, Object> user = new HashMap<String, Object>();
	user.put("userName",staffusername);
	user.put("roles", roleList);
	
	return user;
}
}