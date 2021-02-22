package modelPOJOS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.ProvisioningUtils;

public class WorldPayAccountDetails {
	
	private String chainCode;
	private String billingDescriptor;
	private String businessEstablishedDate;
	private String businessType;
	private String ownershipType;
	private String websiteURL;
	private String mccCode;
	
	public static Map<String, Object> getWPAccountDetailsMap(String doingbusinessas, String businessestablisheddate,
			String businesstype, String mcccode, String ownershiptype, String websiteurl){
		Map<String, Object> wpdetails = new HashMap<String, Object>(); 
		wpdetails.put("chainCode", "0N3859");
		wpdetails.put("billingDescriptor","PFA*"+doingbusinessas); 
		wpdetails.put("businessEstablishedDate", businessestablisheddate);
		wpdetails.put("businessType",businesstype); 
		wpdetails.put("ownershipType", ownershiptype);
		wpdetails.put("websiteURL",websiteurl); 
		wpdetails.put("mccCode", mcccode);
		return wpdetails;
		
	}
	
	public String getChainCode() {
		return chainCode;
	}
	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}
	public String getBillingDescriptor() {
		return billingDescriptor;
	}
	public void setBillingDescriptor(String billingDescriptor) {
		this.billingDescriptor = billingDescriptor;
	}
	public String getBusinessEstablishedDate() {
		return businessEstablishedDate;
	}
	public void setBusinessEstablishedDate(String businessEstablishedDate) {
		this.businessEstablishedDate = businessEstablishedDate;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getOwnershipType() {
		return ownershipType;
	}
	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}
	public String getWebsiteURL() {
		return websiteURL;
	}
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}
	public String getMccCode() {
		return mccCode;
	}
	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}
	
	
	

}
