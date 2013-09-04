package com.intuit.ihg.product.mobile.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class MobileTestCaseData {


		Mobile mobileobj=null;
		ExcelSheetReader excelReader=null;
		public MobileTestCaseData(Mobile mobile) throws Exception {
			String temp= IHGUtil.getEnvironmentType().toString();// which enviroment data need to picked
			URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); // file name
			excelReader=new ExcelSheetReader(url.getFile());//reading the entire file
			mobileobj=(Mobile) excelReader.getSingleExcelRow(mobile,temp);//filtering the entire file
		}
		
	  public String getUrl() {
			return mobileobj.url;
		}

		public String getUserName() {
			
			return mobileobj.username;
		}

		
		public String getPassword() {
			return mobileobj.password;
		}
	
	} 

