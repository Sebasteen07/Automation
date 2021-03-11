package modelPOJOS;

import java.util.HashMap;
import java.util.Map;

import utils.ProvisioningUtils;

public class MerchantAddress {
	
	private String address1;
	private String city;
	private String zip;
	private String country;
	private String state;
	
	public static Map<String, Object> getMerchantAddressMap(String merchantaddress1, String merchantcity, String merchantstate, String merchantzip){
		Map<String, Object> merchantaddress = new HashMap<String, Object>(); 
		merchantaddress.put("address1", merchantaddress1);
		merchantaddress.put("city",merchantcity); 
		merchantaddress.put("state", merchantstate);
		merchantaddress.put("zip",merchantzip); 
		merchantaddress.put("country", "US");
		return merchantaddress;
		
	}
	
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
