// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class Attachment {

	private String attachmentId;
	private String name;
	private String type;
	private String contentBytes;
	private String documentId;
	private String format;

	public String getAttachmentId() {
	return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
	this.attachmentId = attachmentId;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getType() {
	return type;
	}

	public void setType(String type) {
	this.type = type;
	}

	public String getContentBytes() {
	return contentBytes;
	}

	public void setContentBytes(String contentBytes) {
	this.contentBytes = contentBytes;
	}

	public String getDocumentId() {
	return documentId;
	}

	public void setDocumentId(String documentId) {
	this.documentId = documentId;
	}

	public String getFormat() {
	return format;
	}

	public void setFormat(String format) {
	this.format = format;
	}

	}