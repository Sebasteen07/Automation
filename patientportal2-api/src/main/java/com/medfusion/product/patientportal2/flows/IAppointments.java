package com.medfusion.product.patientportal2.flows;

import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.patientportal2.pojo.Appointment;
import com.medfusion.product.patientportal2.pojo.Jalapeno;
import com.medfusion.product.patientportal2.pojo.PatientInfo;

/**
 * PI api for getting appointments.
 * 
 * @author Viktor
 */
public interface IAppointments {
	/**
	 * Gets past appointments for given patient in given practice.
	 * 
	 * @param driver
	 * @param portal - jalapeno patient portal URL is needed
	 * @param patientInfo - username and password are needed
	 * @throws ParseException
	 */
	public List<Appointment> getAllAppointmentsForPatient(WebDriver driver, Jalapeno portal, PatientInfo patientInfo) throws ParseException;
	
	/**
	 * Gets past appointments for given patient in given practice.
	 * 
	 * @param driver
	 * @param portal - jalapeno patient portal URL is needed
	 * @param patientInfo - username and password are needed
	 * @throws ParseException
	 */
	public List<Appointment> getUpcomingAppointmentsForPatient(WebDriver driver, Jalapeno portal, PatientInfo patientInfo) throws ParseException;
	
	/**
	 * Gets past appointments for given patient in given practice.
	 * 
	 * @param driver
	 * @param portal - jalapeno patient portal URL is needed
	 * @param patientInfo - username and password are needed
	 * @throws ParseException
	 */
	public List<Appointment> getPastAppointmentsForPatient(WebDriver driver, Jalapeno portal, PatientInfo patientInfo) throws ParseException;
}
