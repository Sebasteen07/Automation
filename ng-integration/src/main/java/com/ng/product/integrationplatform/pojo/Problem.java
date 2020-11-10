// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class Problem {

private String description= "Anaemia associated with acquired immunodeficiency syndrome";
private String fullySpecifiedName= "Malignant neoplasti";
private String conceptId;
private String sideId;
private String site= "anysite";
private String onsetDate;
private String lastAddressedDate;
private Object resolvedDate;
private Object resolvedReason;
private Object resolvedBy;
private String problemStatusId;
private String problemStatus;
private Object recentNoteId;
private Object clinicalStatusId;
private Boolean isChronic= false;
private Boolean hasSecondaryCondition= false;
private Boolean isDeleted= false;
private String locationId;
private String providerId;
private Boolean isRecordedElsewhere= false;
private String recordedElsewhereSource;
private Object responsibleProviderId;
private Boolean isComorbid= false;
private String interactions;

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getFullySpecifiedName() {
return fullySpecifiedName;
}

public void setFullySpecifiedName(String fullySpecifiedName) {
this.fullySpecifiedName = fullySpecifiedName;
}

public String getConceptId() {
return conceptId;
}

public void setConceptId(String conceptId) {
this.conceptId = conceptId;
}

public String getSideId() {
return sideId;
}

public void setSideId(String sideId) {
this.sideId = sideId;
}

public String getSite() {
return site;
}

public void setSite(String site) {
this.site = site;
}

public String getOnsetDate() {
return onsetDate;
}

public void setOnsetDate(String onsetDate) {
this.onsetDate = onsetDate;
}

public String getLastAddressedDate() {
return lastAddressedDate;
}

public void setLastAddressedDate(String lastAddressedDate) {
this.lastAddressedDate = lastAddressedDate;
}

public Object getResolvedDate() {
return resolvedDate;
}

public void setResolvedDate(Object resolvedDate) {
this.resolvedDate = resolvedDate;
}

public Object getResolvedReason() {
return resolvedReason;
}

public void setResolvedReason(Object resolvedReason) {
this.resolvedReason = resolvedReason;
}

public Object getResolvedBy() {
return resolvedBy;
}

public void setResolvedBy(Object resolvedBy) {
this.resolvedBy = resolvedBy;
}

public String getProblemStatusId() {
return problemStatusId;
}

public void setProblemStatusId(String problemStatusId) {
this.problemStatusId = problemStatusId;
}

public String getProblemStatus() {
return problemStatus;
}

public void setProblemStatus(String problemStatus) {
this.problemStatus = problemStatus;
}

public Object getRecentNoteId() {
return recentNoteId;
}

public void setRecentNoteId(Object recentNoteId) {
this.recentNoteId = recentNoteId;
}

public Object getClinicalStatusId() {
return clinicalStatusId;
}

public void setClinicalStatusId(Object clinicalStatusId) {
this.clinicalStatusId = clinicalStatusId;
}

public Boolean getIsChronic() {
return isChronic;
}

public void setIsChronic(Boolean isChronic) {
this.isChronic = isChronic;
}

public Boolean getHasSecondaryCondition() {
return hasSecondaryCondition;
}

public void setHasSecondaryCondition(Boolean hasSecondaryCondition) {
this.hasSecondaryCondition = hasSecondaryCondition;
}

public Boolean getIsDeleted() {
return isDeleted;
}

public void setIsDeleted(Boolean isDeleted) {
this.isDeleted = isDeleted;
}

public String getLocationId() {
return locationId;
}

public void setLocationId(String locationId) {
this.locationId = locationId;
}

public String getProviderId() {
return providerId;
}

public void setProviderId(String providerId) {
this.providerId = providerId;
}

public Boolean getIsRecordedElsewhere() {
return isRecordedElsewhere;
}

public void setIsRecordedElsewhere(Boolean isRecordedElsewhere) {
this.isRecordedElsewhere = isRecordedElsewhere;
}

public String getRecordedElsewhereSource() {
return recordedElsewhereSource;
}

public void setRecordedElsewhereSource(String recordedElsewhereSource) {
this.recordedElsewhereSource = recordedElsewhereSource;
}

public Object getResponsibleProviderId() {
return responsibleProviderId;
}

public void setResponsibleProviderId(Object responsibleProviderId) {
this.responsibleProviderId = responsibleProviderId;
}

public Boolean getIsComorbid() {
return isComorbid;
}

public void setIsComorbid(Boolean isComorbid) {
this.isComorbid = isComorbid;
}

public String getInteractions() {
return interactions;
}

public void setInteractions(String interactions) {
this.interactions = interactions;
}

}