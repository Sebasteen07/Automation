//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.funding.tests;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.funding.service.FundingUtils;
import com.medfusion.funding.service.IBFService;

import com.medfusion.transaction.details.TransactionEntity;

public class FundingTests extends BaseTestNG {
	private IBFService flows = new IBFService();
	private Session session;
	private Cluster cluster;
	public static PropertyFileLoader testData;
	private static String activitydate = loadActivityDate();
	final static private TransactionEntity sourceTransaction = new TransactionEntity(activitydate, "VISA",
			"4111111111111111", "79946106", 2560791218L, "SALE", 1000L, "1223", "4445018686923",
			"ZPxspWm1WwKNyuIyCwRKUGtni");

	@BeforeMethod
	public void connectCassandra() throws IOException {

		log("Connect to the db.");

		testData = new PropertyFileLoader();
		String contactpoint1 = testData.getProperty("contact.point1");
		String contactpoint2 = testData.getProperty("contact.point2");
		String contactpoint3 = testData.getProperty("contact.point3");

		cluster = Cluster.builder().addContactPoints(contactpoint1, contactpoint2, contactpoint3)
				.withCredentials("cassandra", "cassandra").build();
		session = cluster.connect("vantiv_emaf_fee_schedule");
		log("Connected succesfully");

	}

	@AfterMethod
	public void disconnectCassandra() {

		session.close();
		cluster.close();
		log("Connection closed");

	}

	@Test(enabled = true, groups = { "FundingAcceptanceTest" })
	public void testBasicPostEmaf() throws Exception {
		log("Test posting emaf to funding endpoint");
		FundingUtils fundutils = new FundingUtils();
		String transactionID = fundutils.loadTransactionId();
		String orderID = fundutils.loadOrderId();
		String amount = fundutils.loadDataFromProperty("amount");
		log("Transaction ID is: " + transactionID);
		TransactionEntity transactionToEmaf = new TransactionEntity(sourceTransaction);
		transactionToEmaf.setTransactionId(transactionID);
		transactionToEmaf.setOrderId(orderID);
		transactionToEmaf.setActivityDate(loadActivityDate());
		transactionToEmaf.setPurchaseAmount(Long.parseLong(amount));

		flows.generateEmaf("source.emaf", transactionToEmaf, "test.emaf");
		log("Emaf prepared succesfully");
		log("Post emaf to the endpoint");

		transactionToEmaf.setFundedDate(IHGUtil.getFormattedCurrentDate("yyyyMMdd"));
		int responseCode = flows.postEmaf("test.emaf", transactionToEmaf.getFundedDate());
		assertEquals(responseCode, 202, "Error. Response code is: " + responseCode);
		log("Testing emaf succesfully posted.");

		flows.assessDataSettlementDetail(session, transactionToEmaf);

	}

	public static String loadActivityDate() {

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("configurations.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String activitydate = prop.getProperty("activitydate");
		return activitydate;
	}

}