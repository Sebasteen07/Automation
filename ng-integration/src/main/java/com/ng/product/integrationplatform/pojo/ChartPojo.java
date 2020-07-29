package com.ng.product.integrationplatform.pojo;

public class ChartPojo {

private String firstOfficeEncDate;
private String lastOfficeEncDate;
private String nextOfficeEncDate ="2021-08-21";
private String preferredProviderId;
private String renderingProviderId;
private String defaultLocationId;
private Boolean printStatement= true;
private Boolean suppressBilling=false;
private Boolean IsCoManaged=false;

public Boolean getsuppressBilling() {
return suppressBilling;
}

public void setsuppressBilling(Boolean suppressBilling) {
this.suppressBilling = suppressBilling;
}
public Boolean getIsCoManaged() {
return IsCoManaged;
}

public void setIsCoManaged(Boolean IsCoManaged) {
this.IsCoManaged = IsCoManaged;
}

public String getFirstOfficeEncDate() {
return firstOfficeEncDate;
}

public void setFirstOfficeEncDate(String firstOfficeEncDate) {
this.firstOfficeEncDate = firstOfficeEncDate;
}

public String getLastOfficeEncDate() {
return lastOfficeEncDate;
}

public void setLastOfficeEncDate(String lastOfficeEncDate) {
this.lastOfficeEncDate = lastOfficeEncDate;
}

public String getNextOfficeEncDate() {
return nextOfficeEncDate;
}

public void setNextOfficeEncDate(String nextOfficeEncDate) {
this.nextOfficeEncDate = nextOfficeEncDate;
}

public String getPreferredProviderId() {
return preferredProviderId;
}

public void setPreferredProviderId(String preferredProviderId) {
this.preferredProviderId = preferredProviderId;
}

public String getRenderingProviderId() {
return renderingProviderId;
}

public void setRenderingProviderId(String renderingProviderId) {
this.renderingProviderId = renderingProviderId;
}

public String getDefaultLocationId() {
return defaultLocationId;
}

public void setDefaultLocationId(String defaultLocationId) {
this.defaultLocationId = defaultLocationId;
}

public Boolean getPrintStatement() {
return printStatement;
}

public void setPrintStatement(Boolean printStatement) {
this.printStatement = printStatement;
}

}