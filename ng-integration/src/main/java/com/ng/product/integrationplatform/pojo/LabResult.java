// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class LabResult {

private String type= "Component";
private String componentKey= "Test";
private String componentDescription= "Test";
private String value= "Test";
private String unit= "Test";
private String range= "Test";
private String comment= "Done";
private Integer abnormalFlag;
private String codeSystem= "Test";
private String observationDate;
private String loincCode;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getComponentKey() {
return componentKey;
}

public void setComponentKey(String componentKey) {
this.componentKey = componentKey;
}

public String getComponentDescription() {
return componentDescription;
}

public void setComponentDescription(String componentDescription) {
this.componentDescription = componentDescription;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

public String getUnit() {
return unit;
}

public void setUnit(String unit) {
this.unit = unit;
}

public String getRange() {
return range;
}

public void setRange(String range) {
this.range = range;
}

public String getComment() {
return comment;
}

public void setComment(String comment) {
this.comment = comment;
}

public Integer getAbnormalFlag() {
return abnormalFlag;
}

public void setAbnormalFlag(Integer abnormalFlag) {
this.abnormalFlag = abnormalFlag;
}

public String getCodeSystem() {
return codeSystem;
}

public void setCodeSystem(String codeSystem) {
this.codeSystem = codeSystem;
}

public String getObservationDate() {
return observationDate;
}

public void setObservationDate(String observationDate) {
this.observationDate = observationDate;
}

public String getLoincCode() {
return loincCode;
}

public void setLoincCode(String loincCode) {
this.loincCode = loincCode;
}

}