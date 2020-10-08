// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class UpdateEncounter {

private String patientTypeId;
private String renderingProviderId;
private Boolean isClinical =true;
private Boolean isBillable =false;
private Boolean isOptical =false;
private String locationId;
private String remarks;
private String onsetDate;
private String onsetTime;
private String admitDate;
private String dischargeDate;
private String referringProviderId;
private String supervisingProviderId;
private Boolean printOnStatements =false;
private Boolean isSensitive;
private Boolean suppressPortal =false;
private Boolean suppressOutreach =false;
private String facilityId;
private String homelessStatusId;

public String getPatientTypeId() {
return patientTypeId;
}

public void setPatientTypeId(String patientTypeId) {
this.patientTypeId = patientTypeId;
}

public String getRenderingProviderId() {
return renderingProviderId;
}

public void setRenderingProviderId(String renderingProviderId) {
this.renderingProviderId = renderingProviderId;
}

public Boolean getIsClinical() {
return isClinical;
}

public void setIsClinical(Boolean isClinical) {
this.isClinical = isClinical;
}

public Boolean getIsBillable() {
return isBillable;
}

public void setIsBillable(Boolean isBillable) {
this.isBillable = isBillable;
}

public Boolean getIsOptical() {
return isOptical;
}

public void setIsOptical(Boolean isOptical) {
this.isOptical = isOptical;
}

public String getLocationId() {
return locationId;
}

public void setLocationId(String locationId) {
this.locationId = locationId;
}

public String getRemarks() {
return remarks;
}

public void setRemarks(String remarks) {
this.remarks = remarks;
}

public String getOnsetDate() {
return onsetDate;
}

public void setOnsetDate(String onsetDate) {
this.onsetDate = onsetDate;
}

public String getOnsetTime() {
return onsetTime;
}

public void setOnsetTime(String onsetTime) {
this.onsetTime = onsetTime;
}

public String getAdmitDate() {
return admitDate;
}

public void setAdmitDate(String admitDate) {
this.admitDate = admitDate;
}

public String getDischargeDate() {
return dischargeDate;
}

public void setDischargeDate(String dischargeDate) {
this.dischargeDate = dischargeDate;
}

public String getReferringProviderId() {
return referringProviderId;
}

public void setReferringProviderId(String referringProviderId) {
this.referringProviderId = referringProviderId;
}

public String getSupervisingProviderId() {
return supervisingProviderId;
}

public void setSupervisingProviderId(String supervisingProviderId) {
this.supervisingProviderId = supervisingProviderId;
}

public Boolean getPrintOnStatements() {
return printOnStatements;
}

public void setPrintOnStatements(Boolean printOnStatements) {
this.printOnStatements = printOnStatements;
}

public Boolean getIsSensitive() {
return isSensitive;
}

public void setIsSensitive(Boolean isSensitive) {
this.isSensitive = isSensitive;
}

public Boolean getSuppressPortal() {
return suppressPortal;
}

public void setSuppressPortal(Boolean suppressPortal) {
this.suppressPortal = suppressPortal;
}

public Boolean getSuppressOutreach() {
return suppressOutreach;
}

public void setSuppressOutreach(Boolean suppressOutreach) {
this.suppressOutreach = suppressOutreach;
}

public String getFacilityId() {
return facilityId;
}

public void setFacilityId(String facilityId) {
this.facilityId = facilityId;
}

public String getHomelessStatusId() {
return homelessStatusId;
}

public void setHomelessStatusId(String homelessStatusId) {
this.homelessStatusId = homelessStatusId;
}

}