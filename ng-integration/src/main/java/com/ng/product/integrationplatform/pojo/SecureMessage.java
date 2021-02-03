// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class SecureMessage {

	private String senderType;
	private String sourceName;
	private String sourceDescription;
	private String encounterType;
	private String encounterId;
	private String renderingProviderId;
	private String locationId;
	private String applicationName;
	private List<Message> messages = null;
	private String messageType;

	public String getSenderType() {
	return senderType;
	}

	public void setSenderType(String senderType) {
	this.senderType = senderType;
	}

	public String getSourceName() {
	return sourceName;
	}

	public void setSourceName(String sourceName) {
	this.sourceName = sourceName;
	}

	public String getSourceDescription() {
	return sourceDescription;
	}

	public void setSourceDescription(String sourceDescription) {
	this.sourceDescription = sourceDescription;
	}

	public String getEncounterType() {
	return encounterType;
	}

	public void setEncounterType(String encounterType) {
	this.encounterType = encounterType;
	}

	public String getEncounterId() {
	return encounterId;
	}

	public void setEncounterId(String encounterId) {
	this.encounterId = encounterId;
	}

	public String getRenderingProviderId() {
	return renderingProviderId;
	}

	public void setRenderingProviderId(String renderingProviderId) {
	this.renderingProviderId = renderingProviderId;
	}

	public String getLocationId() {
	return locationId;
	}

	public void setLocationId(String locationId) {
	this.locationId = locationId;
	}

	public String getApplicationName() {
	return applicationName;
	}

	public void setApplicationName(String applicationName) {
	this.applicationName = applicationName;
	}

	public List<Message> getMessages() {
	return messages;
	}

	public void setMessages(List<Message> messages) {
	this.messages = messages;
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	}