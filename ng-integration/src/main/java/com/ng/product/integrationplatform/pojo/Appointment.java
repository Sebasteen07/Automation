package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class Appointment {

private String personId;
private String eventId;
private String locationId;
private List<String> resourceIds = null;
private String appointmentDate;
private String durationMinutes;
private String details="";
private String description;
private Boolean procedureWithResident =false;
private Boolean allowDoubleBooking =false;
private Boolean allowCategoryConflictOverride =false;
private Boolean conflictOverrideOnLogin =false;
private Boolean allowEventLocationOverride =false;
private String renderingProviderId;
private String referringProviderId;
private String userDefined1;
private String userDefined2;
private String userDefined3;
private String userDefined4;
private String userDefined5;
private String userDefined6;
private String userDefined7;
private String userDefined8;
private String marketingPlanId;
private String marketingSourceId;
private String marketingPlanComments;
private String caseManagementCaseId;

public String getPersonId() {
return personId;
}

public void setPersonId(String personId) {
this.personId = personId;
}

public String getEventId() {
return eventId;
}

public void setEventId(String eventId) {
this.eventId = eventId;
}

public String getLocationId() {
return locationId;
}

public void setLocationId(String locationId) {
this.locationId = locationId;
}

public List<String> getResourceIds() {
return resourceIds;
}

public void setResourceIds(List<String> resourceIds) {
this.resourceIds = resourceIds;
}

public String getAppointmentDate() {
return appointmentDate;
}

public void setAppointmentDate(String appointmentDate) {
this.appointmentDate = appointmentDate;
}

public String getDurationMinutes() {
return durationMinutes;
}

public void setDurationMinutes(String durationMinutes) {
this.durationMinutes = durationMinutes;
}

public String getDetails() {
return details;
}

public void setDetails(String details) {
this.details = details;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Boolean getProcedureWithResident() {
return procedureWithResident;
}

public void setProcedureWithResident(Boolean procedureWithResident) {
this.procedureWithResident = procedureWithResident;
}

public Boolean getAllowDoubleBooking() {
return allowDoubleBooking;
}

public void setAllowDoubleBooking(Boolean allowDoubleBooking) {
this.allowDoubleBooking = allowDoubleBooking;
}

public Boolean getAllowCategoryConflictOverride() {
return allowCategoryConflictOverride;
}

public void setAllowCategoryConflictOverride(Boolean allowCategoryConflictOverride) {
this.allowCategoryConflictOverride = allowCategoryConflictOverride;
}

public Boolean getConflictOverrideOnLogin() {
return conflictOverrideOnLogin;
}

public void setConflictOverrideOnLogin(Boolean conflictOverrideOnLogin) {
this.conflictOverrideOnLogin = conflictOverrideOnLogin;
}

public Boolean getAllowEventLocationOverride() {
return allowEventLocationOverride;
}

public void setAllowEventLocationOverride(Boolean allowEventLocationOverride) {
this.allowEventLocationOverride = allowEventLocationOverride;
}

public String getRenderingProviderId() {
return renderingProviderId;
}

public void setRenderingProviderId(String renderingProviderId) {
this.renderingProviderId = renderingProviderId;
}

public String getReferringProviderId() {
return referringProviderId;
}

public void setReferringProviderId(String referringProviderId) {
this.referringProviderId = referringProviderId;
}

public String getUserDefined1() {
return userDefined1;
}

public void setUserDefined1(String userDefined1) {
this.userDefined1 = userDefined1;
}

public String getUserDefined2() {
return userDefined2;
}

public void setUserDefined2(String userDefined2) {
this.userDefined2 = userDefined2;
}

public String getUserDefined3() {
return userDefined3;
}

public void setUserDefined3(String userDefined3) {
this.userDefined3 = userDefined3;
}

public String getUserDefined4() {
return userDefined4;
}

public void setUserDefined4(String userDefined4) {
this.userDefined4 = userDefined4;
}

public String getUserDefined5() {
return userDefined5;
}

public void setUserDefined5(String userDefined5) {
this.userDefined5 = userDefined5;
}

public String getUserDefined6() {
return userDefined6;
}

public void setUserDefined6(String userDefined6) {
this.userDefined6 = userDefined6;
}

public String getUserDefined7() {
return userDefined7;
}

public void setUserDefined7(String userDefined7) {
this.userDefined7 = userDefined7;
}

public String getUserDefined8() {
return userDefined8;
}

public void setUserDefined8(String userDefined8) {
this.userDefined8 = userDefined8;
}

public String getMarketingPlanId() {
return marketingPlanId;
}

public void setMarketingPlanId(String marketingPlanId) {
this.marketingPlanId = marketingPlanId;
}

public String getMarketingSourceId() {
return marketingSourceId;
}

public void setMarketingSourceId(String marketingSourceId) {
this.marketingSourceId = marketingSourceId;
}

public String getMarketingPlanComments() {
return marketingPlanComments;
}

public void setMarketingPlanComments(String marketingPlanComments) {
this.marketingPlanComments = marketingPlanComments;
}

public String getCaseManagementCaseId() {
return caseManagementCaseId;
}

public void setCaseManagementCaseId(String caseManagementCaseId) {
this.caseManagementCaseId = caseManagementCaseId;
}

}