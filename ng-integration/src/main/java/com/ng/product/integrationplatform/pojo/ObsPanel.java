// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

public class ObsPanel {

private String orderId;
private String orderedTestId;
private String collectionDateTime;
private Integer status= 2;
private String panelComment= "Test";

public String getOrderId() {
return orderId;
}

public void setOrderId(String orderId) {
this.orderId = orderId;
}

public String getOrderedTestId() {
return orderedTestId;
}

public void setOrderedTestId(String orderedTestId) {
this.orderedTestId = orderedTestId;
}

public String getCollectionDateTime() {
return collectionDateTime;
}

public void setCollectionDateTime(String collectionDateTime) {
this.collectionDateTime = collectionDateTime;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getPanelComment() {
return panelComment;
}

public void setPanelComment(String panelComment) {
this.panelComment = panelComment;
}

}