// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class LabOrder {

private String testLocationId;
private String orderingProvider;
private Boolean orderedElsewhere= false;
private List<Object> copyToProviders = null;
private List<Object> payers = null;
private String generalComment= "LabOrder";
private String orderComment= "LabOrder";
private String patientComment= "LabOrder";
private String cancelReason= "";
private String clinicalInfo= "ClincialInforTest";
private Integer labId= 2250;
private String generatedBy= "OrderModule";
private String billingType= "2";
private String orderControl= "2";
private String orderPriority= "2";
private Integer abnCode= 3;
private Integer specimenAction= 2;

public String getTestLocationId() {
return testLocationId;
}

public void setTestLocationId(String testLocationId) {
this.testLocationId = testLocationId;
}

public String getOrderingProvider() {
return orderingProvider;
}

public void setOrderingProvider(String orderingProvider) {
this.orderingProvider = orderingProvider;
}

public Boolean getOrderedElsewhere() {
return orderedElsewhere;
}

public void setOrderedElsewhere(Boolean orderedElsewhere) {
this.orderedElsewhere = orderedElsewhere;
}

public List<Object> getCopyToProviders() {
return copyToProviders;
}

public void setCopyToProviders(List<Object> copyToProviders) {
this.copyToProviders = copyToProviders;
}

public List<Object> getPayers() {
return payers;
}

public void setPayers(List<Object> payers) {
this.payers = payers;
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

public String getCancelReason() {
return cancelReason;
}

public void setCancelReason(String cancelReason) {
this.cancelReason = cancelReason;
}

public String getClinicalInfo() {
return clinicalInfo;
}

public void setClinicalInfo(String clinicalInfo) {
this.clinicalInfo = clinicalInfo;
}

public Integer getLabId() {
return labId;
}

public void setLabId(Integer labId) {
this.labId = labId;
}

public String getGeneratedBy() {
return generatedBy;
}

public void setGeneratedBy(String generatedBy) {
this.generatedBy = generatedBy;
}

public String getBillingType() {
return billingType;
}

public void setBillingType(String billingType) {
this.billingType = billingType;
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

public Integer getAbnCode() {
return abnCode;
}

public void setAbnCode(Integer abnCode) {
this.abnCode = abnCode;
}

public Integer getSpecimenAction() {
return specimenAction;
}

public void setSpecimenAction(Integer specimenAction) {
this.specimenAction = specimenAction;
}

}