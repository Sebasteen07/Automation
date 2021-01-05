// Copyright 2020 NXGN Management, LLC. All Rights Reserved.

/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.pojo;

import java.util.List;

public class CCDRequest {

private String ccdarequesttype;
private List<CCDRequestDetails> requests = null;

public String getCcdarequesttype() {
return ccdarequesttype;
}

public void setCcdarequesttype(String ccdarequesttype) {
this.ccdarequesttype = ccdarequesttype;
}

public List<CCDRequestDetails> getRequests() {
return requests;
}

public void setRequests(List<CCDRequestDetails> requests) {
this.requests = requests;
}

}