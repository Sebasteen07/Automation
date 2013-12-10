package com.intuit.ihg.product.apiehcore.utils;

import java.net.URL;
import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author bkrishnankutty, bbinisha
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */
public class EhcoreAPITestData {

	EhcoreAPI ehcoreapiObj = null;
	ExcelSheetReader excelReader = null;

	/**
	 * 
	 * @param sitegen
	 * @throws Exception
	 */
	public EhcoreAPITestData(EhcoreAPI ehcoreAPI) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		ehcoreapiObj = (EhcoreAPI) excelReader.getSingleExcelRow(ehcoreAPI, temp);
	}

	/**
	 * Returns site host name from excel sheet
	 */
	public String getHost() {
		return ehcoreapiObj.host;
	}
	
	/**
	 * 
	 * @return : Returns port nuber from excel sheet
	 */
	public String getPort() {
		return ehcoreapiObj.port;
	}

	/**
	 * 
	 * @return : Returns CCD DB host name(db_ccd_host) from excel sheet.
	 */
	public String getDBCCDHost() {
		return ehcoreapiObj.db_ccd_host;
	}

	/**
	 * 
	 * @return : Returns CCD DB SID from excel sheet(db_ccd_sid)
	 */
	public String getDBCCDSID() {
		return ehcoreapiObj.db_ccd_sid;
	}

	/**
	 * 
	 * @return : Returns Mongo Server Address from excel sheet.
	 */
	public String getMongoServerAddress() {
		return ehcoreapiObj.mongo_serverAddress;
	}
	
	/**
	 * 
	 * @return : Returns Practice ID from excel sheet.
	 */
	public String getPracticeID() {
		return ehcoreapiObj.practiceId;
	}
	
	/**
	 * 
	 * @return : Returns Mongo Server Address from excel sheet.
	 */
	public String getProviderID() {
		return ehcoreapiObj.providerId;
	}
	
	/**
	 * 
	 * @return : Returns Mongo Server Address from excel sheet.
	 */
	public String getPatientID() {
		return ehcoreapiObj.patientId;
	}
	/**
	 * 
	 * @return : Returns Mongo Server Address from excel sheet.
	 */
	public String getCCD() {
		return ehcoreapiObj.ccd;
	}
	
	/**
	 * @return : Returns URL from excel sheet.
	 */
	public String getUrl() {
		return ehcoreapiObj.url;
	}
	
	public String getProtocol() {
		return ehcoreapiObj.protocol;
	}
	
	/**
	 * 
	 * @return : Returns All Scripts ccdexporturl from excel sheet.
	 */
	public String getAllscriptsccdexporturl() {
		return ehcoreapiObj.allscriptsccdexporturl;
	}
	
	
	/**
	 * 
	 * @return : Returns All Scripts ccd import url from excel sheet.
	 */
	public String getAllscriptsccdimporturl() {
		return ehcoreapiObj.allscriptsccdimporturl;
	}
	
	/**
	 * 
	 * @return : Returns mongo properties from excel sheet.
	 */
	public String getmongoproperty() {
		return ehcoreapiObj.mongoproperty;
	}

}
