package com.ng.product.integrationplatform.flows;

import org.codehaus.jackson.map.ObjectMapper;

import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.pojo.ChartPojo;
import com.ng.product.integrationplatform.utils.DBUtils;

public class Chart {
	
	public static ChartPojo addChart(String personId) throws Throwable{
		ChartPojo chart = new ChartPojo();
		try{
			
			String strSqlQueryForProvider="select provider_id from provider_mstr where description='Krishnan, Cibi'";
			String strSqlQueryForLocation="select location_id from location_mstr where location_name='Default Location'";  
			chart.setFirstOfficeEncDate(NGAPIUtils.getXNGDate());
			chart.setLastOfficeEncDate(NGAPIUtils.getXNGDate());
			chart.setPreferredProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider));
			chart.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider));
			chart.setDefaultLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation));
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(chart);
			System.out.println("Chart request body \n"+requestbody);
	        
			String baseURL = apiRoutes.valueOf("BaseURL").getRouteURL();
		    String personURL =apiRoutes.valueOf("AddChart").getRouteURL().replace("personId", personId); 
			String finalURL = baseURL+personURL;
			String chart_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			System.out.println("Chart created with id "+chart_id);
			
	} catch (Exception e) {
        e.printStackTrace();
    }
		return chart;
	}

}
