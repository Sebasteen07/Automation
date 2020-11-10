// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class EncounterPojo {

private String renderingProviderId;
private String locationId;
private Boolean isClinical = true;

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

public Boolean getIsClinical() {
return isClinical;
}

public void setIsClinical(Boolean isClinical) {
this.isClinical = isClinical;
}

}