// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class Medication {

private String rxQuantity= "5";
private String rxRefills= "5";
private Boolean dispenseAsWritten= false;
private Boolean isPrescribedElsewhere= false;
private Boolean privacyIndicator= false;
private String prescribedElsewhereLocation="";
private String rxComment= "taking as per Docter recommendation";
private String rxSpecialInstruction= "";
private String startDate;
private String stopDate;
private String providerId;
private String locationId;
private String diagnosisCode;
private Boolean isPrn= false;
private String rxUnits;
private Integer pedOrderId= 1;
private Integer medicationId;
private Boolean isRepresentativeNdc= false;
private String sigDescription;
private List<AcknowledgedProblem> acknowledgedProblems = null;
private String supervisingProviderId;
private Boolean byPassDeaRefillLimitCheck= false;
private Boolean samplesIndicator= false;
private Integer refillLimit= 0;

public String getRxQuantity() {
return rxQuantity;
}

public void setRxQuantity(String rxQuantity) {
this.rxQuantity = rxQuantity;
}

public String getRxRefills() {
return rxRefills;
}

public void setRxRefills(String rxRefills) {
this.rxRefills = rxRefills;
}

public Boolean getDispenseAsWritten() {
return dispenseAsWritten;
}

public void setDispenseAsWritten(Boolean dispenseAsWritten) {
this.dispenseAsWritten = dispenseAsWritten;
}

public Boolean getIsPrescribedElsewhere() {
return isPrescribedElsewhere;
}

public void setIsPrescribedElsewhere(Boolean isPrescribedElsewhere) {
this.isPrescribedElsewhere = isPrescribedElsewhere;
}

public Boolean getPrivacyIndicator() {
return privacyIndicator;
}

public void setPrivacyIndicator(Boolean privacyIndicator) {
this.privacyIndicator = privacyIndicator;
}

public String getPrescribedElsewhereLocation() {
return prescribedElsewhereLocation;
}

public void setPrescribedElsewhereLocation(String prescribedElsewhereLocation) {
this.prescribedElsewhereLocation = prescribedElsewhereLocation;
}

public String getRxComment() {
return rxComment;
}

public void setRxComment(String rxComment) {
this.rxComment = rxComment;
}

public String getRxSpecialInstruction() {
return rxSpecialInstruction;
}

public void setRxSpecialInstruction(String rxSpecialInstruction) {
this.rxSpecialInstruction = rxSpecialInstruction;
}

public String getStartDate() {
return startDate;
}

public void setStartDate(String startDate) {
this.startDate = startDate;
}

public String getStopDate() {
return stopDate;
}

public void setStopDate(String stopDate) {
this.stopDate = stopDate;
}

public String getProviderId() {
return providerId;
}

public void setProviderId(String providerId) {
this.providerId = providerId;
}

public String getLocationId() {
return locationId;
}

public void setLocationId(String locationId) {
this.locationId = locationId;
}

public String getDiagnosisCode() {
return diagnosisCode;
}

public void setDiagnosisCode(String diagnosisCode) {
this.diagnosisCode = diagnosisCode;
}

public Boolean getIsPrn() {
return isPrn;
}

public void setIsPrn(Boolean isPrn) {
this.isPrn = isPrn;
}

public String getRxUnits() {
return rxUnits;
}

public void setRxUnits(String rxUnits) {
this.rxUnits = rxUnits;
}

public Integer getPedOrderId() {
return pedOrderId;
}

public void setPedOrderId(Integer pedOrderId) {
this.pedOrderId = pedOrderId;
}

public Integer getMedicationId() {
return medicationId;
}

public void setMedicationId(Integer medicationId) {
this.medicationId = medicationId;
}

public Boolean getIsRepresentativeNdc() {
return isRepresentativeNdc;
}

public void setIsRepresentativeNdc(Boolean isRepresentativeNdc) {
this.isRepresentativeNdc = isRepresentativeNdc;
}

public String getSigDescription() {
return sigDescription;
}

public void setSigDescription(String sigDescription) {
this.sigDescription = sigDescription;
}

public List<AcknowledgedProblem> getAcknowledgedProblems() {
return acknowledgedProblems;
}

public void setAcknowledgedProblems(List<AcknowledgedProblem> acknowledgedProblems) {
this.acknowledgedProblems = acknowledgedProblems;
}

public String getSupervisingProviderId() {
return supervisingProviderId;
}

public void setSupervisingProviderId(String supervisingProviderId) {
this.supervisingProviderId = supervisingProviderId;
}

public Boolean getByPassDeaRefillLimitCheck() {
return byPassDeaRefillLimitCheck;
}

public void setByPassDeaRefillLimitCheck(Boolean byPassDeaRefillLimitCheck) {
this.byPassDeaRefillLimitCheck = byPassDeaRefillLimitCheck;
}

public Boolean getSamplesIndicator() {
return samplesIndicator;
}

public void setSamplesIndicator(Boolean samplesIndicator) {
this.samplesIndicator = samplesIndicator;
}

public Integer getRefillLimit() {
return refillLimit;
}

public void setRefillLimit(Integer refillLimit) {
this.refillLimit = refillLimit;
}

}