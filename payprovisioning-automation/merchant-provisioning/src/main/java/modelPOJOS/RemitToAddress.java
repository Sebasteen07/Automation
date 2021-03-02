package modelPOJOS;

import java.util.HashMap;
import java.util.Map;

public class RemitToAddress {
	
	private String address1;
	private String city;
	private String zip;
	private String country;
	private String state;
	
	public static Map<String, Object> getMerchantRemitAddressMap(String merchantaddress1, String merchantcity, String merchantstate, String merchantzip){
		Map<String, Object> remitaddress = new HashMap<String, Object>(); 
		remitaddress.put("address1", merchantaddress1);
		remitaddress.put("city",merchantcity); 
		remitaddress.put("state", merchantstate);
		remitaddress.put("zip",merchantzip); 
		remitaddress.put("country", "US");
		return remitaddress;
		
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
