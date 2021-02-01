package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class PrescriptionRenewalRequest {

private String renewalResponse;
private String responseDate;
private List<String> comments = null;
private String encounterType;
private String renderingProviderId;
private String locationId;
private List<PrescriptionRenewalRequestStatus> PrescriptionRenewalRequestStatus = null;

public String getRenewalResponse() {
return renewalResponse;
}

public void setRenewalResponse(String renewalResponse) {
this.renewalResponse = renewalResponse;
}

public String getResponseDate() {
return responseDate;
}

public void setResponseDate(String responseDate) {
this.responseDate = responseDate;
}

public List<String> getComments() {
return comments;
}

public void setComments(List<String> comments) {
this.comments = comments;
}

public String getEncounterType() {
return encounterType;
}

public void setEncounterType(String encounterType) {
this.encounterType = encounterType;
}

public String getRenderingProviderId() {
return renderingProviderId;
}

public void setRenderingProviderId(String renderingProviderId) {
this.renderingProviderId = renderingProviderId;
}

public String getLocationId() {
return locationId;
}

public void setLocationId(String locationId) {
this.locationId = locationId;
}

public List<PrescriptionRenewalRequestStatus> getPrescriptionRenewalRequestStatus() {
return PrescriptionRenewalRequestStatus;
}

public void setPrescriptionRenewalRequestStatus(List<PrescriptionRenewalRequestStatus> PrescriptionRenewalRequestStatus) {
this.PrescriptionRenewalRequestStatus = PrescriptionRenewalRequestStatus;
}

}