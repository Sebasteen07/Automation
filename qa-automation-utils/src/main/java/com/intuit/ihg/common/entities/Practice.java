package com.intuit.ihg.common.entities;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/26/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Practice {

    private String docUName;
    private String docPassword;
    private String docId;
    private String pracName;
    private String pracAddress;
    private String pracId;
    private String portalTitle;
    private String locationName;
    private String locationId;
    private String docSearchString;
    private boolean prefDoc;
    private boolean searchForDoc;

    public String getDocUName() {
        return docUName;
    }

    public void setDocUName(String docUName) {
        this.docUName = docUName;
    }

    public String getDocPassword() {
        return docPassword;
    }

    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }

    public String getPracName() {
        return pracName;
    }

    public void setPracName(String pracName) {
        this.pracName = pracName;
    }

    public String getPracAddress() {
        return pracAddress;
    }

    public void setPracAddress(String pracAddress) {
        this.pracAddress = pracAddress;
    }

    public String getPortalTitle() {
        return portalTitle;
    }

    public void setPortalTitle(String portalTitle) {
        this.portalTitle = portalTitle;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDocSearchString() {
        return docSearchString;
    }

    public void setDocSearchString(String docSearchString) {
        this.docSearchString = docSearchString;
    }


    public boolean isPrefDoc() {
        return prefDoc;
    }

    public void setPrefDoc(boolean prefDoc) {
        this.prefDoc = prefDoc;
    }

    public boolean isSearchForDoc() {
        return searchForDoc;
    }

    public void setSearchForDoc(boolean searchForDoc) {
        this.searchForDoc = searchForDoc;
    }

     public String getDocId() {
        return docId;
    }

    public void setDocId(String doc) {
        this.docId = doc;
    }

    public String getPracId() {
        return pracId;
    }

    public void setPracId(String prac) {
        this.pracId = prac;
    }

}
