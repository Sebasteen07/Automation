package modelPOJOS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.ProvisioningUtils;

public class AccountDetails {
	
	private String accountType;
	private String routingNumber;
	private String accountNumber;
	private String federalTaxId;
	private String preferredProcessor;
	private WorldPayAccountDetails worldPayAccountDetails;
	
	public static Map<String, Object> getMerchantAccountDetailsMap(String accountnumber, String routingnumber,String federaltaxid,
			String doingbusinessas, String businessestablisheddate, String businesstype, String mcccode, String ownershiptype, String websiteurl){
		Map<String, Object> accountdetails = new HashMap<String, Object>(); 
		accountdetails.put("accountType", "S");
		accountdetails.put("routingNumber",routingnumber); 
		accountdetails.put("accountNumber", accountnumber);
		accountdetails.put("federalTaxId",federaltaxid); 
		accountdetails.put("preferredProcessor", "ELEMENT");
		accountdetails.put("worldPayAccountDetails",WorldPayAccountDetails.getWPAccountDetailsMap(doingbusinessas,
				businessestablisheddate,businesstype,mcccode,ownershiptype,websiteurl )); 
		return accountdetails;
		
	}
	
	
	public WorldPayAccountDetails getWorldPayAccountDetails() {
		return worldPayAccountDetails;
	}
	public void setWorldPayAccountDetails(WorldPayAccountDetails worldPayAccountDetails) {
		this.worldPayAccountDetails = worldPayAccountDetails;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getFederalTaxId() {
		return federalTaxId;
	}
	public void setFederalTaxId(String federalTaxId) {
		this.federalTaxId = federalTaxId;
	}
	public String getPreferredProcessor() {
		return preferredProcessor;
	}
	public void setPreferredProcessor(String preferredProcessor) {
		this.preferredProcessor = preferredProcessor;
	}
	

}
