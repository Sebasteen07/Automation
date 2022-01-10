// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfig {
	
	public Map<String,String> defaultHeader(){
		Map<String,String> df= new HashMap<String,String>();
		df.put("content-type", "application/json");
		return df;		
	}
	
	public Map<String, String> HeaderwithToken(String s) {
		Map<String, String> df = new HashMap<String, String>();
		df.put("content-type", "application/json");
		df.put("Authorization", "Bearer " + s);
		return df;
	}
	
	public Map<String, String> HeaderwithTokenES(String s) {
		Map<String, String> df = new HashMap<String, String>();
		df.put("content-type", "application/json");
		df.put("Authorization", "Bearer " + s);
		df.put("Language", "ES");
		return df;
	}
	
	public Map<String, String> HeaderwithTokenMulti(String s) {
		Map<String, String> df = new HashMap<String, String>();
		df.put("Authorization", "Bearer " + s);
		return df;
	}
	public Map<String,String> AWSHeader(){
		Map<String,String> df= new HashMap<String,String>();
		df.put("", "");
		df.put("", "");		
		return df;
		
	}
	
}