package com.medfusion.product.practice.pojo;

import java.io.IOException;
import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.practice.pojo.Practice;

public class PracticeTestData {

	private Practice practiceData = null;
	private ExcelSheetReader excelReader = null;
	
	public PracticeTestData(){
		//stub
	}
	
	public PracticeTestData(Practice practice) throws IOException, ClassNotFoundException, IllegalAccessException{
		// Find the environment
		String temp= IHGUtil.getEnvironmentType().toString();
		
		// Pull data based upon environment
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); 	// data file location
		excelReader = new ExcelSheetReader(url.getFile());						// read data file
		practiceData = (Practice) excelReader.getSingleExcelRow(practice,temp);	//filtering the entire file
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
	
	public String getFormUser() {
		return practiceData.formUser;
	}
	
	public String getFormPassword() {
		return practiceData.formPassword;
	}
	
	public String getPatientUser() {
		return practiceData.patientUser;
	}
	
	public String getPatientEmail() {
		return practiceData.patientEmail;
	}
	
	public String getPatientPassword() {
		return practiceData.patientPassword;
	}
	
	public String getPayPalDoctor() {
		return practiceData.payPalDoctor;
	}
	
	public String getPayPalPassword() {
		return practiceData.payPalPassword;
	}
	
	public void setPractice(Practice practice) {
		this.practiceData = practice;
	}
}
