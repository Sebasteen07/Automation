package com.medfusion.product.patientportal2.flows;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;
import org.openqa.selenium.WebDriver;

public interface ICreatePatient {

		public Patient selfRegisterPatient(WebDriver webDriver, PropertyFileLoader testData, Patient patient);
}
