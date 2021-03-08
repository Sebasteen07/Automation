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
	
	public Map<String,String> HeaderwithToken(){
		Map<String,String> df= new HashMap<String,String>();
		df.put("content-type", "application/json");
        df.put("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJwYXRpZW50SWQiOm51bGwsInByYWN0aWNlSWQiOiIyNDI0OCIsInR5cGUiOiJMT0dJTkxFU1MiLCJleHAiOjE2MDQ2NzI1NjMsInRva2VuIjoiMzQ2NGRhMTctYWM4Mi00NmQ3LWI4MzAtMWRiZDRkMmY2NTExIn0.gfuuSyP66W2Vv89bPHRmJpEo0W5bKGJN-vdfgdQMPyw");        
		return df;
		
	}
	
	public Map<String,String> AWSHeader(){
		Map<String,String> df= new HashMap<String,String>();
		df.put("", "");
		df.put("", "");		
		return df;
		
	}
	
}