// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.pojo;

public class UpdateLabOrder {

private Integer labId =2250;
private String testLocation;
private String orderingProvider;
private String signOffDate;
private String nextgenStatus ="Signed-Off";
private String orderControl ="2";
private String orderPriority ="2";
private String specimenActionCode ="2";
private String billingType ="2";
private String clinicalInformation ="ClincialInforTest";
private String cancelReason="";
private String generalComment ="LabOrder";
private String orderComment ="LabOrder";
private String patientComment ="LabOrder";
private Boolean isOrderedElseWhere =false;

public Integer getLabId() {
return labId;
}

public void setLabId(Integer labId) {
this.labId = labId;
}

public String getTestLocation() {
return testLocation;
}

public void setTestLocation(String testLocation) {
this.testLocation = testLocation;
}

public String getOrderingProvider() {
return orderingProvider;
}

public void setOrderingProvider(String orderingProvider) {
this.orderingProvider = orderingProvider;
}

public String getSignOffDate() {
return signOffDate;
}

public void setSignOffDate(String signOffDate) {
this.signOffDate = signOffDate;
}

public String getNextgenStatus() {
return nextgenStatus;
}

public void setNextgenStatus(String nextgenStatus) {
this.nextgenStatus = nextgenStatus;
}

public String getOrderControl() {
return orderControl;
}

public void setOrderControl(String orderControl) {
this.orderControl = orderControl;
}

public String getOrderPriority() {
return orderPriority;
}

public void setOrderPriority(String orderPriority) {
this.orderPriority = orderPriority;
}

public String getSpecimenActionCode() {
return specimenActionCode;
}

public void setSpecimenActionCode(String specimenActionCode) {
this.specimenActionCode = specimenActionCode;
}

public String getBillingType() {
return billingType;
}

public void setBillingType(String billingType) {
this.billingType = billingType;
}

public String getClinicalInformation() {
return clinicalInformation;
}

public void setClinicalInformation(String clinicalInformation) {
this.clinicalInformation = clinicalInformation;
}

public String getCancelReason() {
return cancelReason;
}

public void setCancelReason(String cancelReason) {
this.cancelReason = cancelReason;
}

public String getGeneralComment() {
return generalComment;
}

public void setGeneralComment(String generalComment) {
this.generalComment = generalComment;
}

public String getOrderComment() {
return orderComment;
}

public void setOrderComment(String orderComment) {
this.orderComment = orderComment;
}

public String getPatientComment() {
return patientComment;
}

public void setPatientComment(String patientComment) {
this.patientComment = patientComment;
}

public Boolean getIsOrderedElseWhere() {
return isOrderedElseWhere;
}

public void setIsOrderedElseWhere(Boolean isOrderedElseWhere) {
this.isOrderedElseWhere = isOrderedElseWhere;
}

}