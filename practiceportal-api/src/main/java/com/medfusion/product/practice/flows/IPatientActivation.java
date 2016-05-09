package com.medfusion.product.practice.flows;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.medfusion.product.practice.pojo.PatientInfo;

public interface IPatientActivation {
	
	public PatientInfo activatePatient(WebDriver driver, PropertyFileLoader testData, String mail) throws InterruptedException, ClassNotFoundException, IllegalAccessException, IOException;	
	public PatientInfo editPatientRSDKExternalID(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo) throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException;
}
