package com.medfusion.product.practice.api.flows;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.practice.api.pojo.PatientInfo;

public interface IPatientActivation {

	public PatientInfo activatePatient(WebDriver driver, PropertyFileLoader testData, String mail)
			throws InterruptedException, ClassNotFoundException, IllegalAccessException, IOException;

	public PatientInfo editPatientRSDKExternalID(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo)
			throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException;

    public PatientInfo editPatientSetExternalID(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo,
            String externalSystemId)
            throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException;

	PatientInfo editPatientSetPrimaryId(WebDriver driver, PropertyFileLoader testData, PatientInfo patientInfo, String emrid)
			throws ClassNotFoundException, IllegalAccessException, IOException, InterruptedException;
}
