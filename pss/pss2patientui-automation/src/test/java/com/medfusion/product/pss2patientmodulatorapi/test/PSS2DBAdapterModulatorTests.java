package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestDBAdapter;
import com.medfusion.product.pss2patientapi.payload.PayloadDBAdapter;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2DBAdapterModulatorTests extends BaseTestNG {

	public static PayloadDBAdapter payloadDB;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestDBAdapter postAPIRequestDB;
	HeaderConfig headerConfig;
	public static String practiceId;

	Timestamp timestamp = new Timestamp();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification apv;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		payloadDB = new PayloadDBAdapter();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestDB = new PostAPIRequestDBAdapter();
		headerConfig = new HeaderConfig();
		apv = new APIVerification();
		practiceId = propertyData.getProperty("practice.id.db");
		postAPIRequestDB.setupRequestSpecBuilder(propertyData.getProperty("base.url.db"), headerConfig.defaultHeader());
	}

	
}
