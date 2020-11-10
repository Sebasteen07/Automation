// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class Interaction {

private String personId;
private Boolean isRequired= false;
private Boolean acknowledged= true;
private String description= "Chest pain, unspecified";
private Integer eventType=4;
private Integer sourceId=6;
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

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
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