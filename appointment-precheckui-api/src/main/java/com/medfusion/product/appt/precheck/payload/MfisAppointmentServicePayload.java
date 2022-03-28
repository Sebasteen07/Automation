package com.medfusion.product.appt.precheck.payload;

import java.util.Calendar;
import java.util.TimeZone;

public class MfisAppointmentServicePayload {
	private static MfisAppointmentServicePayload payload = new MfisAppointmentServicePayload();
	
	private MfisAppointmentServicePayload() {
		
	}
	
	public static MfisAppointmentServicePayload getMfisAppointmentServicePayload() {
		return payload;
	}
	
	public String apptServicePayload() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		int strDate = cal.get(Calendar.DATE)-1;
		int edDate = cal.get(Calendar.DATE) + 1;
		int yyyy = cal.get(Calendar.YEAR);
		int strMonth = cal.get(Calendar.MONTH)+1;
		int edMonth = cal.get(Calendar.MONTH)+2;
		String startMonth=Integer.toString(strMonth);
		String endMonth=Integer.toString(edMonth);
		String startDate=Integer.toString(strDate);
		String endDate=Integer.toString(edDate);
		
		if(strMonth<=9) {
			startMonth= "0"+strMonth;
		}
		
		if(edMonth<=9) {
			endMonth= "0"+edMonth;
		}
		
		if(strDate<=9) {
			startDate= "0"+strDate;
		}
		
		if(edDate<=9) {
			endDate= "0"+edDate;
		}
		
		String apptService = "{\r\n"
				+ "  \"appointmentDateRangeStart\": \""+yyyy+"-"+startMonth+"-"+startDate+"T18:30:00Z\",\r\n"
				+ "  \"appointmentDateRangeEnd\": \""+yyyy+"-"+endMonth+"-"+endDate+"T18:30:00Z\",\r\n"
				+ "  \"locationPagingFilter\": {\r\n"
				+ "    \"pagingFilterType\": \"\",\r\n"
				+ "    \"pagingFilterAlg\": \"\",\r\n"
				+ "    \"value\": \"\"\r\n"
				+ "  },\r\n"
				+ "  \"sorting\": [\r\n"
				+ "    {\r\n"
				+ "      \"property\": \"\",\r\n"
				+ "      \"direction\": \"\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"pageNumber\": 0,\r\n"
				+ "  \"pageSize\": 50,\r\n"
				+ "  \"applyFilters\": true,\r\n"
				+ "  \"applyStatusMappings\": true,\r\n"
				+ "  \"applyResourceMappings\": false\r\n"
				+ "}\r\n"
				+ "";
		return apptService;
	}
	
	public String apptServiceWithoutDatePayload() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		int dd = cal.get(Calendar.DATE) + 1;
		int yyyy = cal.get(Calendar.YEAR);
		int endMonth = cal.get(Calendar.MONTH)+2;
		String apptService = "{\r\n"
				+ "  \"appointmentDateRangeStart\": \"\",\r\n"
				+ "  \"appointmentDateRangeEnd\": \""+yyyy+"-"+endMonth+"-"+dd+"T18:30:00Z\",\r\n"
				+ "  \"locationPagingFilter\": {\r\n"
				+ "    \"pagingFilterType\": \"\",\r\n"
				+ "    \"pagingFilterAlg\": \"\",\r\n"
				+ "    \"value\": \"\"\r\n"
				+ "  },\r\n"
				+ "  \"sorting\": [\r\n"
				+ "    {\r\n"
				+ "      \"property\": \"\",\r\n"
				+ "      \"direction\": \"\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"pageNumber\": 0,\r\n"
				+ "  \"pageSize\": 50,\r\n"
				+ "  \"applyFilters\": true,\r\n"
				+ "  \"applyStatusMappings\": true,\r\n"
				+ "  \"applyResourceMappings\": false\r\n"
				+ "}";
		return apptService;
	}
	
	public String apptServiceForBroadcastApptsPayload(String patientId, String apptId) {
		String apptService = "[\r\n"
				+ "  {\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+apptId+"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return apptService;
	}
	
	public String apptServiceDeletePayload(String practiceId,String patientId, String apptId) {
		String apptService = "[\r\n"
				+ "  {\r\n"
				+ "    \"practiceId\" : \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+apptId+"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return apptService;
	}
}
