// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;



public class InstamedTerminalIdDetails {


private String virtualVisit;

public String getVirtualVisit() {
	return virtualVisit;
}

public void setVirtualVisit(String virtualVisit) {
	this.virtualVisit = virtualVisit;
}

public String getPreCheck() {
	return preCheck;
}

public void setPreCheck(String preCheck) {
	this.preCheck = preCheck;
}

public String getPatientPortal() {
	return patientPortal;
}

public void setPatientPortal(String patientPortal) {
	this.patientPortal = patientPortal;
}

private String preCheck;
private String patientPortal;



@Override
public String toString() {
	return "instaMedTerminalIDList [preCheck="
			+ preCheck + ", patientPortal="
			+ patientPortal + ", virtualVisit=" + virtualVisit
			+ "]";
}

public static Map<String, Object> getInstaMedTerminalIdMap(String virtualVisit,String patientPortal,String preCheck) {
	
	Map<String, Object> instaMedTerminalIdList = new HashMap<String, Object>(); 
	if(virtualVisit==null&&patientPortal==null&&preCheck==null) {
	
	}
	else {
	instaMedTerminalIdList.put("VIRTUAL_VISITS", virtualVisit);
	instaMedTerminalIdList.put("PATIENT_PORTAL", patientPortal);
	instaMedTerminalIdList.put("PRECHECK", preCheck);
	}
	return instaMedTerminalIdList;
	
}

}
