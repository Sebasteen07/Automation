package com.intuit.ihg.product.practice.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class PracticeTestData {

	private Practice practiceData = null;
	private ExcelSheetReader excelReader = null;
	
	public PracticeTestData(Practice practice) throws Exception {
		// Find the environment
		String temp= IHGUtil.getEnvironmentType().toString();
		// Pull data based upon environment
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); 	// data file location
		excelReader = new ExcelSheetReader(url.getFile());						// read data file
		practiceData = (Practice) excelReader.getSingleExcelRow(practice,temp);	//filtering the entire file
	}


	public PracticeTestData(Practice practice,String m) throws Exception {
		String temp= IHGUtil.getEnvironmentType().toString();// which enviroment data need to picked
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); // file name
		excelReader=new ExcelSheetReader(url.getFile());//reading the entire file
		temp = m+ "-" +temp;
		System.out.println("Key value for this Testcase ###########:- "+temp);
		practiceData = (Practice) excelReader.getSingleExcelRow(practice,temp);//filtering the entire file
	}
	
	public String getUrl() {
		return practiceData.url;
	}

	public String getUsername() {
		return practiceData.username;
	}

	public String getPassword() {
		return practiceData.password;
	}
}
