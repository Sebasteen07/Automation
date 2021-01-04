package com.ng.product.integrationplatform.pojo;

public class AppointmentResponse {

private String message;
private String suggestedAppointmentDate;
private String approvedDate;
private String appointmentId;
private String imhRequestId;
private String encounterId;
private String appointmentStatus;
private Integer sourceApplicationType;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getSuggestedAppointmentDate() {
return suggestedAppointmentDate;
}

public void setSuggestedAppointmentDate(String suggestedAppointmentDate) {
this.suggestedAppointmentDate = suggestedAppointmentDate;
}

public String getApprovedDate() {
return approvedDate;
}

public void setApprovedDate(String approvedDate) {
this.approvedDate = approvedDate;
}

public String getAppointmentId() {
return appointmentId;
}

public void setAppointmentId(String appointmentId) {
this.appointmentId = appointmentId;
}

public String getImhRequestId() {
return imhRequestId;
}

public void setImhRequestId(String imhRequestId) {
this.imhRequestId = imhRequestId;
}

public String getEncounterId() {
return encounterId;
}

public void setEncounterId(String encounterId) {
this.encounterId = encounterId;
}

public String getAppointmentStatus() {
return appointmentStatus;
}

public void setAppointmentStatus(String appointmentStatus) {
this.appointmentStatus = appointmentStatus;
}

public Integer getSourceApplicationType() {
return sourceApplicationType;
}

public void setSourceApplicationType(Integer sourceApplicationType) {
this.sourceApplicationType = sourceApplicationType;
}
}
