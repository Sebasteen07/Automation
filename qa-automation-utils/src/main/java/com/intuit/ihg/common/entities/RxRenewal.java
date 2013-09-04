package com.intuit.ihg.common.entities;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/26/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RxRenewal {

    private boolean isNewMed = false;
    private boolean isNewPharmacy = false;
    private boolean searchForPharmacy = false;
    private boolean findPharmacyNearMe = false;

    private String rxName;
    private String dosage;
    private String comments;
    private String pharmacyName;
    private String pharmacyPhone;
    private String zipOrStateToSearch;
    private String doctor;

    public RxRenewal(){
    	
    }
    
    public boolean isNewMed() {
        return isNewMed;
    }

    public void setNewMed(boolean newMed) {
        isNewMed = newMed;
    }

    public boolean isNewPharmacy() {
        return isNewPharmacy;
    }

    public void setNewPharmacy(boolean newPharmacy) {
        isNewPharmacy = newPharmacy;
    }

    public boolean isSearchForPharmacy() {
        return searchForPharmacy;
    }

    public void setSearchForPharmacy(boolean searchForPharmacy) {
        this.searchForPharmacy = searchForPharmacy;
    }

    public boolean isFindPharmacyNearMe() {
        return findPharmacyNearMe;
    }

    public void setFindPharmacyNearMe(boolean findPharmacyNearMe) {
        this.findPharmacyNearMe = findPharmacyNearMe;
    }

    public String getRxName() {
        return rxName;
    }

    public void setRxName(String rxName) {
        this.rxName = rxName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }

    public String getZipOrStateToSearch() {
        return zipOrStateToSearch;
    }

    public void setZipOrStateToSearch(String zipOrStateToSearch) {
        this.zipOrStateToSearch = zipOrStateToSearch;
    }
      

	public String getDoctor() {
		return doctor;
	}

	//existing pharmacy
    public RxRenewal(String rxName, String dosage, String comments) {
        this.rxName = rxName;
        this.dosage = dosage;
        this.comments = comments;
    }

    //to Add pharmacy with a name n phone number
    public RxRenewal(boolean newMed, boolean newPharmacy, String rxName, String dosage, String comments, String pharmacyName, String pharmacyPhone) {
        this(newMed, newPharmacy, false, false, rxName, dosage, comments, pharmacyName, pharmacyPhone, null,null);
    }

    //to Add pharmacy search near me
    public RxRenewal(boolean newMed, boolean findPharmacyNearMe, String rxName, String dosage, String comments) {
        this(newMed, true, true, true, rxName, dosage, comments, null, null, null,null);
    }

    //to Add pharmacy near a zip
    public RxRenewal(boolean newMed, boolean searchForPharmacy, String rxName, String dosage, String comments, String zipOrStateToSearch) {
        this(newMed, true, true, false, rxName, dosage, comments, null, null, zipOrStateToSearch,null);
    }
    
    //All data
    public RxRenewal(boolean newMed, boolean newPharmacy, boolean searchForPharmacy, boolean findPharmacyNearMe, String rxName, String dosage, String comments, String pharmacyName, String pharmacyPhone, String zipOrStateToSearch, String doctor) {
        isNewMed = newMed;
        isNewPharmacy = newPharmacy;
        this.searchForPharmacy = searchForPharmacy;
        this.findPharmacyNearMe = findPharmacyNearMe;
        this.rxName = rxName;
        this.dosage = dosage;
        this.comments = comments;
        this.pharmacyName = pharmacyName;
        this.pharmacyPhone = pharmacyPhone;
        this.zipOrStateToSearch = zipOrStateToSearch;
        this.doctor = doctor;
    }


}
