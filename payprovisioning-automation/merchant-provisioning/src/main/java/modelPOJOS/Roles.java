package modelPOJOS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


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
	user.put("roles", Arrays.asList("FUNDINGANDFEES", "FULLDRR","POINTOFSALE", "POS_ADMIN", "VOIDREFUND"));
	return user;
	
}

}