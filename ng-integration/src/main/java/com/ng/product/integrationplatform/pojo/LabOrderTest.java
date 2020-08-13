// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class LabOrderTest {

private String testCodeId;
private Boolean isNextGenCompendiumTest= true;
private String scheduleDateTime;
private String volumeQuantity= "1";
private String volumeUnit= "mg";
private String collectionDate;
private String sourceSite= "test";
private String sourceDescription= "test";
private String additives= "";
private String bodySite= "Left";
private String siteModifier= "Left";
private String specimenRole= "";
private String specimenStorage= "";
private String collectionMethod= "";
private String comment= "Test";
private String orderingReason= "";
private String expectedResultDate;
private String generatedBy= "OrderModule";

public String getTestCodeId() {
return testCodeId;
}

public void setTestCodeId(String testCodeId) {
this.testCodeId = testCodeId;
}

public Boolean getIsNextGenCompendiumTest() {
return isNextGenCompendiumTest;
}

public void setIsNextGenCompendiumTest(Boolean isNextGenCompendiumTest) {
this.isNextGenCompendiumTest = isNextGenCompendiumTest;
}

public String getScheduleDateTime() {
return scheduleDateTime;
}

public void setScheduleDateTime(String scheduleDateTime) {
this.scheduleDateTime = scheduleDateTime;
}

public String getVolumeQuantity() {
return volumeQuantity;
}

public void setVolumeQuantity(String volumeQuantity) {
this.volumeQuantity = volumeQuantity;
}

public String getVolumeUnit() {
return volumeUnit;
}

public void setVolumeUnit(String volumeUnit) {
this.volumeUnit = volumeUnit;
}

public String getCollectionDate() {
return collectionDate;
}

public void setCollectionDate(String collectionDate) {
this.collectionDate = collectionDate;
}

public String getSourceSite() {
return sourceSite;
}

public void setSourceSite(String sourceSite) {
this.sourceSite = sourceSite;
}

public String getSourceDescription() {
return sourceDescription;
}

public void setSourceDescription(String sourceDescription) {
this.sourceDescription = sourceDescription;
}

public String getAdditives() {
return additives;
}

public void setAdditives(String additives) {
this.additives = additives;
}

public String getBodySite() {
return bodySite;
}

public void setBodySite(String bodySite) {
this.bodySite = bodySite;
}

public String getSiteModifier() {
return siteModifier;
}

public void setSiteModifier(String siteModifier) {
this.siteModifier = siteModifier;
}

public String getSpecimenRole() {
return specimenRole;
}

public void setSpecimenRole(String specimenRole) {
this.specimenRole = specimenRole;
}

public String getSpecimenStorage() {
return specimenStorage;
}

public void setSpecimenStorage(String specimenStorage) {
this.specimenStorage = specimenStorage;
}

public String getCollectionMethod() {
return collectionMethod;
}

public void setCollectionMethod(String collectionMethod) {
this.collectionMethod = collectionMethod;
}

public String getComment() {
return comment;
}

public void setComment(String comment) {
this.comment = comment;
}

public String getOrderingReason() {
return orderingReason;
}

public void setOrderingReason(String orderingReason) {
this.orderingReason = orderingReason;
}

public String getExpectedResultDate() {
return expectedResultDate;
}

public void setExpectedResultDate(String expectedResultDate) {
this.expectedResultDate = expectedResultDate;
}

public String getGeneratedBy() {
return generatedBy;
}

public void setGeneratedBy(String generatedBy) {
this.generatedBy = generatedBy;
}

}