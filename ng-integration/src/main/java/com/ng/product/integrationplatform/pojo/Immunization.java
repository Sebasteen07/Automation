// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class Immunization {

private String generatedBy= "OrderModule";
private String locationId;
private String orderingProviderId;
private Object supervisorProviderId;
private Object registryId= 0;
private String comment= "Testing";
private Boolean verbalOrderIndicator= false;
private List<String> allergiesReviewed = null;

public String getGeneratedBy() {
return generatedBy;
}

public void setGeneratedBy(String generatedBy) {
this.generatedBy = generatedBy;
}

public String getLocationId() {
return locationId;
}

public void setLocationId(String locationId) {
this.locationId = locationId;
}

public String getOrderingProviderId() {
return orderingProviderId;
}

public void setOrderingProviderId(String orderingProviderId) {
this.orderingProviderId = orderingProviderId;
}

public Object getSupervisorProviderId() {
return supervisorProviderId;
}

public void setSupervisorProviderId(Object supervisorProviderId) {
this.supervisorProviderId = supervisorProviderId;
}

public Object getRegistryId() {
return registryId;
}

public void setRegistryId(Object registryId) {
this.registryId = registryId;
}

public String getComment() {
return comment;
}

public void setComment(String comment) {
this.comment = comment;
}

public Boolean getVerbalOrderIndicator() {
return verbalOrderIndicator;
}

public void setVerbalOrderIndicator(Boolean verbalOrderIndicator) {
this.verbalOrderIndicator = verbalOrderIndicator;
}

public List<String> getAllergiesReviewed() {
return allergiesReviewed;
}

public void setAllergiesReviewed(List<String> allergiesReviewed) {
this.allergiesReviewed = allergiesReviewed;
}

}