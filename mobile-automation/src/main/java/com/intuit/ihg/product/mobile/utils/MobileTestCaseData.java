package com.intuit.ihg.product.mobile.utils;

import java.lang.reflect.Method;
import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class MobileTestCaseData {


		Mobile mobileobj=null;
		ExcelSheetReader excelReader=null;
		
		public MobileTestCaseData(Mobile mobile,String m) throws Exception {
			String temp= IHGUtil.getEnvironmentType().toString();// which enviroment data need to picked
			URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); // file name
			excelReader=new ExcelSheetReader(url.getFile());//reading the entire file
			temp = m+ "-" +temp;
			System.out.println("Key value for this Testcase ###########:- "+temp);
			mobileobj=(Mobile) excelReader.getSingleExcelRow(mobile,temp);//filtering the entire file
		}
		
	  public String getUrl() {
			return mobileobj.test_url;
		}

		public String getUserName() {
			
			return mobileobj.patient_username;
		}

		
		public String getPassword() {
			return mobileobj.patient_password;
		}
		
		public String getPractice_DocSearchString() {
			return mobileobj.practice_docsearchstring;
		}
	
		public String getTest_ErrorMessage() {
			return mobileobj.test_errormessage;
		}
	
		public String getTestMethodName() {
			return mobileobj.test_method;
		}
	} 

