package com.medfusion.ibfibm.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.ibfibm.domain.TransactionEntity;
import com.medfusion.ibfibm.service.IBFIBMService;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


public class FundingTests extends BaseTestNG {
	private IBFIBMService flows = new IBFIBMService();
	private Session session;
	private Cluster cluster;

	final static private TransactionEntity sourceTransaction = 
			new TransactionEntity("20160527", "VISA", "4111111111111111", "2006962188", 2560791218L, "SALE", 415L, "1219", "4445018686923");

	
	@BeforeMethod
	public void connectCassandra () {
		log("Connect to the db.");
		cluster = Cluster.builder().addContactPoints("172.18.82.148", "172.18.82.149", "172.18.82.150")
				.withCredentials("cassandra", "cassandra").build();
		session = cluster.connect("vantiv_emaf_fee_schedule");
		log("Connected succesfully");
	}

	@AfterMethod
	public void disconnectCassandra () {
		session.close();
		cluster.close();
		log("Connection closed");
	}


	@Test
	public void testBasicPostEmaf() throws Exception {

		log("Test posting emaf to funding endpoint");
		String transactionID = IHGUtil.createRandomNumericString(9);
		log("Transaction ID is: " + transactionID);
		TransactionEntity transactionToEmaf = new TransactionEntity(sourceTransaction);
		transactionToEmaf.setTransactionId(transactionID);

		flows.generateEmaf("source.emaf", transactionToEmaf, "test.emaf");
		log("Emaf prepared succesfully");
		log("Post emaf to the endpoint");
		
		transactionToEmaf.setFundedDate(IHGUtil.getFormattedCurrentDate("yyyyMMdd"));
		int responseCode = flows.postEmaf("test.emaf", transactionToEmaf.getFundedDate());
		assertEquals(responseCode, 202, "Error. Response code is: " + responseCode);
		log("Testing emaf succesfully posted.");
		
		flows.assesDataSettlementDetail(session, transactionToEmaf);
	}


}
