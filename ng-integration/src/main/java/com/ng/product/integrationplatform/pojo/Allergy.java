// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class Allergy {

private String allergyId;
private Integer allergyTypeId;
private Boolean allowDuplicateAllergies= true;
private String onsetDate;
private String providerId;
private String locationId;

public String getAllergyId() {
return allergyId;
}

public void setAllergyId(String allergyId) {
this.allergyId = allergyId;
}

public Integer getAllergyTypeId() {
return allergyTypeId;
}

public void setAllergyTypeId(Integer allergyTypeId) {
this.allergyTypeId = allergyTypeId;
}

public Boolean getAllowDuplicateAllergies() {
return allowDuplicateAllergies;
}

public void setAllowDuplicateAllergies(Boolean allowDuplicateAllergies) {
this.allowDuplicateAllergies = allowDuplicateAllergies;
}

public String getOnsetDate() {
return onsetDate;
}

public void setOnsetDate(String onsetDate) {
this.onsetDate = onsetDate;
}

public void setproviderId (String providerId){
this.providerId =providerId;	
}

public String getproviderId(){
return providerId;	
}

public void setlocationId (String locationId){
this.locationId =locationId;	
}

public String getlocationId(){
return locationId;	
}


}