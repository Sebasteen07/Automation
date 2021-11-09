//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.ibfibm.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.ibfibm.domain.TransactionEntity;

public class IBFIBMService extends BaseTestNG {


	/*
	 * Generate Emaf from the source emaf
	 * transactionToEmaf contains data which should be changed in the emaf
	 * emafFileName is the name of the created testing emaf file
	 * it will trim the transactionID to 10 chars if it is longer
	 */
	public void generateEmaf(String emafSourceFileName, TransactionEntity transactionToEmaf, String emafTargetFileName) throws IOException {
		log("Creating test emaf file");
		URL emafPath = ClassLoader.getSystemResource("data-driven/" + emafSourceFileName);
		try (BufferedReader sourceReader = new BufferedReader(new FileReader(emafPath.getFile()))) {
			String emafLine;
			BufferedWriter emafWriter = new BufferedWriter(new FileWriter (emafTargetFileName));
			String transactionID = transactionToEmaf.getTransactionId();

			if (transactionID != null) {
				while (transactionID.length() < 11) {
					transactionID = transactionID.concat(" ");
				}
				transactionID = transactionID.substring(0, 11);
				log("Transaction ID inserted is: " + transactionID);
				log("Emaf file:");
			}
			while((emafLine = sourceReader.readLine()) != null) {
				if (emafLine.substring(9, 12).equals("300")) {
					emafLine = emafLine.substring(0, 123) + transactionID + emafLine.substring(134);
				}
				log(emafLine);
				emafWriter.write(emafLine);
				emafWriter.newLine();				
			}
			emafWriter.close();
		}
		catch(IOException ioException) {
			throw new IOException("Error reading/writing emaf file: " + ioException);
		}

	}	

	public int postEmaf(String emafFileName, String fundingDate) throws ClientProtocolException, IOException {
		log("Posting the emaf to the rest");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("http://d3-spark01.dev.medfusion.net:8090/ibf-ibm/services/emaf/fund");
		post.addHeader("accept", "application/json");
		post.addHeader("Content-Disposition", "filename=\"" + emafFileName + "\"");
		post.addHeader("funding-Date", fundingDate);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		FileBody fileBody = new FileBody(new File(emafFileName), ContentType.APPLICATION_OCTET_STREAM);
		multipartEntityBuilder.addPart("emaf", fileBody);
		post.setEntity(multipartEntityBuilder.build());
		HttpResponse httpResponse = client.execute(post);
		log("Response Body: " + EntityUtils.toString(httpResponse.getEntity()));
		return httpResponse.getStatusLine().getStatusCode();

	}

	public void assesDataSettlementDetail(Session session, TransactionEntity emafData) {
		log("Looking for transactionID: " + emafData.getTransactionId());
		Row result = session.execute("SELECT * FROM vantiv_emaf_fee_schedule.settlement_detail where payment_txn_ref = '" + emafData.getTransactionId() + "'").one();
		assertTrue((result != null), "Transaction not found in vantiv_emaf_fee_schedule");
		assertEquals(result.getString("account_number"), emafData.getCardNumber(), "Card number not correct");
		log("Correct. Card number: " + result.getString("account_number"));
		assertEquals(result.getLong("merchant_id"), emafData.getMmid().toString(), "MMID not correct");
		log("Correct. MMID: " + result.getLong("merchant_id"));
		assertEquals(result.getString("activity_date"), emafData.getActivityDate(), "Activity Date not correct");
		log("Correct. Activity date: " + result.getString("activity_date"));
		assertEquals(result.getString("funded_date"), emafData.getFundedDate(), "Funded Date not correct");
		log("Correct. Funded date: " + result.getString("funded_date"));
		assertEquals(result.getString("txn_type"), emafData.getTransactionType(), "Transaction Type not correct");
		log("Correct. Transaction Type : " + result.getString("txn_type"));
		assertEquals(result.getString("card_type"), emafData.getCardType(), "Card Type not correct");
		log("Correct. Card Type: " + result.getString("card_type"));
		assertEquals(result.getLong("amount"), emafData.getPurchaseAmount(), "Amount not correct");
		log("Correct. Amount: " + result.getLong("amount"));
		assertEquals(result.getString("expiration_date"), emafData.getExpirationDate(), "Card Expiration not correct");
		log("Correct. Card expiration: " + result.getString("expiration_date"));
		assertEquals(result.getString("processor_merchant_id"), emafData.getMid(), "MID not correct");
		log("Correct. MID: " + result.getString("processor_merchant_id"));

	}
}
