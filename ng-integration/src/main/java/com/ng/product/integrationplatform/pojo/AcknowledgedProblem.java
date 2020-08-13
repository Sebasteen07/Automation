// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class AcknowledgedProblem {

private String personId;
private Boolean isRequired= false;
private Boolean acknowledged= false;
private Integer severityLevel= 0;
private String description= "Testing";
private String entityName= "Testing";
private Integer eventType= 0;
private Integer sourceId= 0;
private String cause= "Unknown";
private String warningDetail= "DOIT";
private Integer warningType= 1;
private String warning= "DOITs";
private String causeText= "None";
private String overrideText= "Test";
private String durAuditKey= "TestTest";
private Boolean isSuppressed= false;
private Boolean isRecordedElseWhere= false;
private String practiceId;
private Boolean display= false;

public String getPersonId() {
return personId;
}

public void setPersonId(String personId) {
this.personId = personId;
}

public Boolean getIsRequired() {
return isRequired;
}

public void setIsRequired(Boolean isRequired) {
this.isRequired = isRequired;
}

public Boolean getAcknowledged() {
return acknowledged;
}

public void setAcknowledged(Boolean acknowledged) {
this.acknowledged = acknowledged;
}

public Integer getSeverityLevel() {
return severityLevel;
}

public void setSeverityLevel(Integer severityLevel) {
this.severityLevel = severityLevel;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getEntityName() {
return entityName;
}

public void setEntityName(String entityName) {
this.entityName = entityName;
}

public Integer getEventType() {
return eventType;
}

public void setEventType(Integer eventType) {
this.eventType = eventType;
}

public Integer getSourceId() {
return sourceId;
}

public void setSourceId(Integer sourceId) {
this.sourceId = sourceId;
}

public String getCause() {
return cause;
}

public void setCause(String cause) {
this.cause = cause;
}

public String getWarningDetail() {
return warningDetail;
}

public void setWarningDetail(String warningDetail) {
this.warningDetail = warningDetail;
}

public Integer getWarningType() {
return warningType;
}

public void setWarningType(Integer warningType) {
this.warningType = warningType;
}

public String getWarning() {
return warning;
}

public void setWarning(String warning) {
this.warning = warning;
}

public String getCauseText() {
return causeText;
}

public void setCauseText(String causeText) {
this.causeText = causeText;
}

public String getOverrideText() {
return overrideText;
}

public void setOverrideText(String overrideText) {
this.overrideText = overrideText;
}

public String getDurAuditKey() {
return durAuditKey;
}

public void setDurAuditKey(String durAuditKey) {
this.durAuditKey = durAuditKey;
}

public Boolean getIsSuppressed() {
return isSuppressed;
}

public void setIsSuppressed(Boolean isSuppressed) {
this.isSuppressed = isSuppressed;
}

public Boolean getIsRecordedElseWhere() {
return isRecordedElseWhere;
}

public void setIsRecordedElseWhere(Boolean isRecordedElseWhere) {
this.isRecordedElseWhere = isRecordedElseWhere;
}

public String getPracticeId() {
return practiceId;
}

public void setPracticeId(String practiceId) {
this.practiceId = practiceId;
}

public Boolean getDisplay() {
return display;
}

public void setDisplay(Boolean display) {
this.display = display;
}

}