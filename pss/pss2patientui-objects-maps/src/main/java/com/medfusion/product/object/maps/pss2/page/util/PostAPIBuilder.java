// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import java.util.HashMap;
import java.util.Map;

public class PostAPIBuilder {
	
	public Map<String,String> postbody(){
		
		Map<String, String> body= new HashMap<String,String>();
		body.put("specialty", "");
		body.put("location", "205400");
		body.put("book", "204151");
		body.put("appointmentType", "204201");
		body.put("startDateTime", "10/26/2020");		
		return body;		
	}

}
