//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.StringTokenizer;



import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;

public class DirectorySearchUtils {
	public void directorySearchParam(String testType) throws Exception {
		Log4jUtil.log("Test Case: Setting data for directory search");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		Log4jUtil.log("Execution Browser: " + TestConfig.getBrowserType());
		Log4jUtil.log("Step 1: Set Test Data from Property file");
		DirectorySearch testData = new DirectorySearch();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadDirectorySearchDataFromProperty(testData);
		Thread.sleep(3000);
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.CSVFilePath;
		Log4jUtil.log("Step 2: Read Search values from external CSV File");
		String[][] searchDataValue = new String[500][500];
		int[] maxValue = new int[500];
		BufferedReader bufRdr = new BufferedReader(new FileReader(workingDir));
		String line1 = null;
		int row = 0;
		int col = 0;
		int nextLine = 0;
		while ((line1 = bufRdr.readLine()) != null ) {
			StringTokenizer st = new StringTokenizer(line1, ",");
			nextLine=0;
			while (st.hasMoreTokens()) {
				searchDataValue[row][col] = st.nextToken();
				if(searchDataValue[row][col].equalsIgnoreCase(" ")) {
					searchDataValue[row][col] = null;
				}
				
				col++;
				nextLine++;
			}
			
			row++;
			maxValue[row] = nextLine;
		}
		bufRdr.close();
		Arrays.sort(maxValue);
		int maxLength = (maxValue[maxValue.length - 1]-1);
		int counter = 0;
		String[][] setValues = new String[500][500];
		for(int i =0;i< row;i++) {
			for(int j=0;j<=maxLength;j++) {
				setValues[i][j] = searchDataValue[i][counter];
				counter++;
			}
			testData.DirectorySearchList.add(new DirectMessageParameter(setValues[i][0], setValues[i][1], setValues[i][2], setValues[i][3], setValues[i][4], setValues[i][5], setValues[i][6], setValues[i][7], setValues[i][8], setValues[i][9], setValues[i][10], setValues[i][11], setValues[i][12] ));
		}
		
		Log4jUtil.log("Step 3: Setup Oauth client");
	 	RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername, testData.OAuthPassword);
	 	Thread.sleep(3000);
	 	
		DirectorySearchPayload DirectorySearchObject = new DirectorySearchPayload();
		
		int maxSearchLength = 0;
		
		if(testData.SearchLength != null && !testData.SearchLength.isEmpty()) {
			maxSearchLength=Integer.parseInt(testData.SearchLength);
		}
		else {
			maxSearchLength=testData.DirectorySearchList.size()-1;
		}
		if(testType.equalsIgnoreCase("acceptance")) {
			maxSearchLength = 2;
		}
		for(int i=0;i<maxSearchLength;i++){
			String payload = DirectorySearchObject.getDirectSearchPayload(testData,i);
			assertTrue(payload!=null);
			Log4jUtil.log("Step 4: Do Directory Search Post Request");
			RestUtils.setupHttpPostRequest(testData.RestUrl, payload, testData.ResponsePath);
			
			Thread.sleep(4000);
			int count = i+1;
			String firstName = testData.DirectorySearchList.get(count).getFirstName();
			String lastName = testData.DirectorySearchList.get(count).getLastName();
			String OrganizationName = testData.DirectorySearchList.get(count).getOrganizationName();
			String nationalProvider = testData.DirectorySearchList.get(count).getNationalProviderId();
			String specialityType = testData.DirectorySearchList.get(count).getSpecialtyType();
			String classification = testData.DirectorySearchList.get(count).getSpecialtyClassification();
			String specialization = testData.DirectorySearchList.get(count).getSpecialtySpecialization();
			String street = testData.DirectorySearchList.get(count).getStreet();
			String city = testData.DirectorySearchList.get(count).getCity();
			String state = testData.DirectorySearchList.get(count).getState();
			String zipCode = testData.DirectorySearchList.get(count).getZipcode();
			String directAddress = testData.DirectorySearchList.get(count).getDirectAddress();
			String type  = testData.DirectorySearchList.get(count).getType();
			
			String displayHeaders="";
			if(firstName!=null) {
				displayHeaders=testData.DirectorySearchList.get(0).getFirstName();
			}
			if(lastName!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getLastName();
			}
			if(OrganizationName!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getOrganizationName();
			}
			if(nationalProvider!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getNationalProviderId();
			}
			if(specialityType!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getSpecialtyType();
			}
			if(classification!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getSpecialtyClassification();
			}
			if(specialization!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getSpecialtySpecialization();
			}
			if(street!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getStreet();
			}
			if(city!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getCity();
			}
			if(state!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getState();
			}
			if(zipCode!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getZipcode();
			}
			if(directAddress!=null) {
				displayHeaders=displayHeaders+" "+testData.DirectorySearchList.get(0).getDirectAddress();
			}
			
			Log4jUtil.log("Data Set "+(i+1)+" for Directory Search having type : "+type+" with values of "+displayHeaders);	
			
			Log4jUtil.log("Step 5: Validate the Response message");
			RestUtils.validateDirectorySearchResponse(testData.ResponsePath,firstName,lastName,OrganizationName,nationalProvider,specialityType,classification,specialization,street,city,state,zipCode,directAddress );
		}
	}
}
