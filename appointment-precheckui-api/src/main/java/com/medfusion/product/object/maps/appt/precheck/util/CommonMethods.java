// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;

import io.restassured.response.Response;

public class CommonMethods extends BaseTestNGWebDriver {
	JavascriptExecutor jse;

	public void highlightElement(WebElement element) {
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style','border: solid 6px red');", element);
	}

	public String getDate(Calendar cal) {
		return "" + cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
	}

	public String generateRandomNum() {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		return String.valueOf(randamNo);
	}

	public Response scheduleNewAppointment(String baseUrl, String practiceId, String phoneNo, String email,
			String getaccessToken) throws IOException {
		MfAppointmentSchedulerPayload schedulerPayload = MfAppointmentSchedulerPayload
				.getMfAppointmentSchedulerPayload();
		PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		Appointment.patientId = generateRandomNum();
		Appointment.apptId = generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		Appointment.plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + Appointment.plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(baseUrl, practiceId,
				schedulerPayload.putAppointmentPayload(Appointment.plus20Minutes, phoneNo, email),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		return scheduleResponse;
	}

}
