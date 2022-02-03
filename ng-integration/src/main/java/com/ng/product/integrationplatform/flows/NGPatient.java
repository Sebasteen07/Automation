// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.flows;

import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGPatient {

	public static NewPatient patientUsingJSON(PropertyFileLoader propertyLoaderObj,String mode) throws Throwable{
		Log4jUtil.log("Step Begins --- API Route Add New Person");
		NewPatient newpatient = new NewPatient();
		try {
			    
			if(mode.equalsIgnoreCase("withoutFirstName")){
				newpatient.setFirstName("");
			}
			else
                newpatient.setFirstName("Patient" + (new Date()).getTime());
			 
			if(mode.equalsIgnoreCase("withoutLastName")){
				 newpatient.setLastName("");
			}
			else
               newpatient.setLastName("Portal" + (new Date()).getTime());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//	        System.out.println(testData.getProperty("dob.day")+"-----"+testData.getProperty("dob.month")+"-----------"+testData.getProperty("dob.year"));
	        String sDate1 = propertyLoaderObj.getProperty("dob.day")+"/"+propertyLoaderObj.getProperty("dob.month")+"/"+propertyLoaderObj.getProperty("dob.year");
	        Date dateofbirth=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
	        System.out.println(sdf.format(dateofbirth));
	        
	        if(mode.equalsIgnoreCase("withoutDOB"))
	        	newpatient.setDateOfBirth("");
	        else
	            newpatient.setDateOfBirth(sdf.format(dateofbirth));
	        
	        String strEmailAddress = newpatient.getLastName() + "@yopmail.com";
	        
	        if(mode.equalsIgnoreCase("withoutEmailaddress"))
	        	newpatient.setEmailAddress("");
	        else
                newpatient.setEmailAddress(strEmailAddress);
	        
	        if(mode.equalsIgnoreCase("withoutSex"))
	        	newpatient.setSex("");
	        else
                newpatient.setSex("M");
	        
            String strZipCode = propertyLoaderObj.getProperty("zip.code");
            if(mode.equalsIgnoreCase("withoutZip"))
                newpatient.setZip("");
            else if(mode.equalsIgnoreCase("invalidZIP"))
            	newpatient.setZip("123");
            else
            	newpatient.setZip(strZipCode);
            
            if(mode.equalsIgnoreCase("Dependent")){
            	newpatient.setFirstName("Minor" + (new Date()).getTime());
            	newpatient.setLastName("Minor" + (new Date()).getTime());
            	newpatient.setSex("F");
            	newpatient.setZip(strZipCode);
            	newpatient.setEmailAddress(System.getProperty("ParentEmailAddress"));
            	
    	        String date = propertyLoaderObj.getProperty("dob.day")+"/"+propertyLoaderObj.getProperty("dob.month")+"/"+propertyLoaderObj.getProperty("dob.year.underage");
    	        Date guardiandateofbirth=new SimpleDateFormat("dd/MM/yyyy").parse(date);
    	        System.out.println(sdf.format(guardiandateofbirth));  
    	        
    	        newpatient.setDateOfBirth(sdf.format(guardiandateofbirth));
            }  
            
            if(mode.equalsIgnoreCase("trustedPatient")){
            	newpatient.setFirstName("trustedPatient" + (new Date()).getTime());
            	newpatient.setLastName("trustedPatient" + (new Date()).getTime());
            	newpatient.setSex("F");
            	newpatient.setZip(strZipCode);
            	newpatient.setEmailAddress(newpatient.getFirstName()+"@yopmail.com");
            	
     	        String dob = propertyLoaderObj.getProperty("dob.day")+"/"+propertyLoaderObj.getProperty("dob.month")+"/"+propertyLoaderObj.getProperty("dob.year");
     	        Date trustedPatientdob=new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            	newpatient.setDateOfBirth(sdf.format(trustedPatientdob));
            } 
            
            if(mode.equalsIgnoreCase("complete")){
            String strAddressLine1 = propertyLoaderObj.getProperty("address1");
            String strCity = propertyLoaderObj.getProperty("city");
            String strState = propertyLoaderObj.getProperty("state");
            
            newpatient.setAddressLine1(strAddressLine1);
            newpatient.setCity(strCity);
            newpatient.setState(strState);
            
            newpatient.setHomePhone(propertyLoaderObj.getPhoneNumber());
            newpatient.setContactSequence("HDASEC");
            newpatient.setCurrentGender("M");
            newpatient.setMiddleName("Auto");

            String strSqlQueryForEthnicity="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type ='ethnicity' and mstr_list_item_desc not in('Test','Unknown','Unknown Ethnicity (uds)','Other') and delete_ind='N' ORDER BY NEWID()";
            String strSqlQueryForRace="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type ='race' and mstr_list_item_desc not in('Other Pacific Islander (Not Hawaiian)') and delete_ind='N' ORDER BY NEWID()";
            String strSqlQueryForLanguage="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type = 'language' and mstr_list_item_desc not in('Other Pacific Islander (Not Hawaiian)') and delete_ind='N' ORDER BY NEWID()";
            String strSqlQueryForReligion="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type = 'religion' and mstr_list_item_desc not in('Jewish           jewish',' ','Other','None') and delete_ind='N' ORDER BY NEWID()";
            
            newpatient.setEthnicityId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForEthnicity));
            newpatient.setRaceId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRace));
            newpatient.setLanguageId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLanguage));
            newpatient.setReligionId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForReligion));
            }
        } catch (Exception e) {
        	Log4jUtil.log(e.getMessage());
        }
		return newpatient;
	}
	
	public static String CreateNGPatient(NewPatient argPayload) throws IOException{
		ObjectMapper objMap = new ObjectMapper();
		String PatientRequestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(argPayload);
        System.out.println("Request Body is \n" + PatientRequestbody);
		
        apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();
		
		String person_id=NGAPIUtils.setupNGHttpPostRequest("CAGateway",finalURL,PatientRequestbody , 201);
		return person_id;

	}
	
	public static NewPatient addDataToPatientDemographics(PropertyFileLoader propertyLoaderObj,NewPatient newpatient) throws Throwable{
        try{
            String strAddressLine1 = propertyLoaderObj.getProperty("address1");
            String strCity = propertyLoaderObj.getProperty("city");
            String strState = propertyLoaderObj.getProperty("state");
            
            newpatient.setAddressLine1(strAddressLine1);
            newpatient.setCity(strCity);
            newpatient.setState(strState);
            
            newpatient.setHomePhone(propertyLoaderObj.getPhoneNumber());
            newpatient.setContactSequence("HDASEC");
            newpatient.setCurrentGender("M");
            newpatient.setMiddleName("Auto");

            String strSqlQueryForEthnicity="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type ='ethnicity' and mstr_list_item_desc not in('Test','Unknown','Unknown Ethnicity (uds)','Other') and delete_ind='N' ORDER BY NEWID()";
            String strSqlQueryForRace="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type ='race' and mstr_list_item_desc not in('Other Pacific Islander (Not Hawaiian)') and delete_ind='N' ORDER BY NEWID()";
            String strSqlQueryForLanguage="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type = 'language' and mstr_list_item_desc not in('Other Pacific Islander (Not Hawaiian)') and delete_ind='N' ORDER BY NEWID()";
            String strSqlQueryForReligion="SELECT top 1 mstr_list_item_id FROM mstr_lists where mstr_list_type = 'religion' and mstr_list_item_desc not in('Jewish           jewish',' ','Other','None') and delete_ind='N' ORDER BY NEWID()";
            
            newpatient.setEthnicityId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForEthnicity));
            newpatient.setRaceId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRace));
            newpatient.setLanguageId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLanguage));
            newpatient.setReligionId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForReligion));
	    } catch (Exception e) {
        	Log4jUtil.log(e.getMessage());
        }
			return newpatient;            
	}
}
