// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class Diagnosis {

private String icdCode="R07.9";
private String diagnosisDescription="Chest pain, unspecified";
private List<Interaction> interactions = null;

public String getIcdCode() {
return icdCode;
}

public void setIcdCode(String icdCode) {
this.icdCode = icdCode;
}

public String getDiagnosisDescription() {
return diagnosisDescription;
}

public void setDiagnosisDescription(String diagnosisDescription) {
this.diagnosisDescription = diagnosisDescription;
}

public List<Interaction> getInteractions() {
return interactions;
}

public void setInteractions(List<Interaction> interactions) {
this.interactions = interactions;
}

}