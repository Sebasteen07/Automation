// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class Message {

private String id;
private String parentId;
private String originalId;
private String senderName;
private String senderId;
private String routingRuleId;                        
private String routingRuleType;                    
private String routingRuleName;                   
private String aliasName;                  
private String subject;
private String body;
private String priority;
private Boolean isReadReceiptRequested =false;
private Boolean isUnreadNotificationRequested =false;
private Integer unreadNotificationInterval;
private Boolean canReply =false;
private String sentTimestamp;
private String scheduledTimestamp;
private String repliedWhenTimestamp;
private Boolean isClinical =false;
private String category;
private Boolean isDraft =false;
private List<Recipient> recipients = null;
private List<Attachment> attachments = null;

private Boolean isBulk =false;
private List<String> reportNames = null;

private String rootThreadId;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getParentId() {
return parentId;
}

public void setParentId(String parentId) {
this.parentId = parentId;
}

public String getOriginalId() {
return originalId;
}

public void setOriginalId(String originalId) {
this.originalId = originalId;
}

public String getSenderName() {
return senderName;
}

public void setSenderName(String senderName) {
this.senderName = senderName;
}

public String getSenderId() {
return senderId;
}

public void setSenderId(String senderId) {
this.senderId = senderId;
}

public String getRoutingRuleId() {
return routingRuleId;
}

public void setRoutingRuleId(String routingRuleId) {
this.routingRuleId = routingRuleId;
}

public String getRoutingRuleType() {
return routingRuleType;
}

public void setRoutingRuleType(String routingRuleType) {
this.routingRuleType = routingRuleType;
}

public String getRoutingRuleName() {
return routingRuleName;
}

public void setRoutingRuleName(String routingRuleName) {
this.routingRuleName = routingRuleName;
}

public String getAliasName() {
return aliasName;
}

public void setAliasName(String aliasName) {
this.aliasName = aliasName;
}

public String getSubject() {
return subject;
}

public void setSubject(String subject) {
this.subject = subject;
}

public String getBody() {
return body;
}

public void setBody(String body) {
this.body = body;
}

public String getPriority() {
return priority;
}

public void setPriority(String priority) {
this.priority = priority;
}

public Boolean getIsReadReceiptRequested() {
return isReadReceiptRequested;
}

public void setIsReadReceiptRequested(Boolean isReadReceiptRequested) {
this.isReadReceiptRequested = isReadReceiptRequested;
}

public Boolean getIsUnreadNotificationRequested() {
return isUnreadNotificationRequested;
}

public void setIsUnreadNotificationRequested(Boolean isUnreadNotificationRequested) {
this.isUnreadNotificationRequested = isUnreadNotificationRequested;
}

public Integer getUnreadNotificationInterval() {
return unreadNotificationInterval;
}

public void setUnreadNotificationInterval(Integer unreadNotificationInterval) {
this.unreadNotificationInterval = unreadNotificationInterval;
}

public Boolean getCanReply() {
return canReply;
}

public void setCanReply(Boolean canReply) {
this.canReply = canReply;
}

public String getSentTimestamp() {
return sentTimestamp;
}

public void setSentTimestamp(String sentTimestamp) {
this.sentTimestamp = sentTimestamp;
}

public String getScheduledTimestamp() {
return scheduledTimestamp;
}

public void setScheduledTimestamp(String scheduledTimestamp) {
this.scheduledTimestamp = scheduledTimestamp;
}

public String getRepliedWhenTimestamp() {
return repliedWhenTimestamp;
}

public void setRepliedWhenTimestamp(String repliedWhenTimestamp) {
this.repliedWhenTimestamp = repliedWhenTimestamp;
}

public Boolean getIsClinical() {
return isClinical;
}

public void setIsClinical(Boolean isClinical) {
this.isClinical = isClinical;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

public Boolean getIsDraft() {
return isDraft;
}

public void setIsDraft(Boolean isDraft) {
this.isDraft = isDraft;
}

public List<Recipient> getRecipients() {
return recipients;
}

public void setRecipients(List<Recipient> recipients) {
this.recipients = recipients;
}

public List<Attachment> getAttachments() {
return attachments;
}

public void setAttachments(List<Attachment> attachments) {
this.attachments = attachments;
}

public Boolean getIsBulk() {
return isBulk;
}

public void setIsBulk(Boolean isBulk) {
this.isBulk = isBulk;
}

public List<String> getReportNames() {
return reportNames;
}

public void setReportNames(List<String> reportNames) {
this.reportNames = reportNames;
}

public String getRootThreadId() {
return rootThreadId;
}

public void setRootThreadId(String rootThreadId) {
this.rootThreadId = rootThreadId;
}
}