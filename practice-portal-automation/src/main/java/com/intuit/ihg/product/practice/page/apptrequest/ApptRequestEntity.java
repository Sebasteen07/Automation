package com.intuit.ihg.product.practice.page.apptrequest;

public class ApptRequestEntity {
	
	private int processOption;
	private String subject;
	private String body;
	private boolean noReply;
	private String apptHour;
	private String apptMin;
	private String apptAmPm;
	private String apptDate;

	public ApptRequestEntity GetApptRequestEntity() {
		return this;
	}

	public int getProcessOption() {
		return processOption;
	}

	public ApptRequestEntity setProcessOption(int processOption) {
		this.processOption = processOption;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public ApptRequestEntity setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getBody() {
		return body;
	}

	public ApptRequestEntity setBody(String body) {
		this.body = body;
		return this;
	}

	public boolean isNoReply() {
		return noReply;
	}

	public ApptRequestEntity setNoReply(boolean noReply) {
		this.noReply = noReply;
		return this;
	}

	public String getApptHour() {
		return apptHour;
	}

	public ApptRequestEntity setApptHour(String apptHour) {
		this.apptHour = apptHour;
		return this;
	}

	public String getApptMin() {
		return apptMin;
	}

	public ApptRequestEntity setApptMin(String apptMin) {
		this.apptMin = apptMin;
		return this;
	}

	public String getApptAmPm() {
		return apptAmPm;
	}

	public ApptRequestEntity setApptAmPm(String apptAmPm) {
		this.apptAmPm = apptAmPm;
		return this;
	}

	public String getApptDate() {
		return apptDate;
	}

	public ApptRequestEntity setApptDate(String apptDate) {
		this.apptDate = apptDate;
		return this;
	}
	
}
