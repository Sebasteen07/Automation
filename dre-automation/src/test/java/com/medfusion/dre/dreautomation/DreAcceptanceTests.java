package com.medfusion.dre.dreautomation;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.medfusion.dre.objects.Retriever;
import com.medfusion.factory.RetrieversFactory;
import com.medfusion.dre.util.Data;
import com.medfusion.dre.util.HTTPCalls;

public class DreAcceptanceTests extends HTTPCalls {
	public static String retrieverName = null;

	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingEpicPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "Epic";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Epic");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingMeditechPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "Meditech";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Meditech");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingMayoClinicPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "Mayo";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Mayo");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingPracticeFusionPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "PracticeFusion";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Practice Fusion");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingCernerPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "Cerner";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Cerner");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingEClinicalPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "EClinical";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for EClinical");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingAthenaPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "Athena";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Athena");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingNextMDPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "NextMD";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for NextMD");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayloadWithSecret());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingMedfusionPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "Medfusion";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Medfusion");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
	
	@Test(enabled = true, groups = {"Retrievers"})
	public void testRetrievingGreenwayMHRPortal() throws InterruptedException {
		log("Step 1: Get Data from Properties");
		retrieverName = "GreenwayMHR";
		String claireRestUrl = Data.get("ClaireRESTUrl");
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		log("Step 2: Create a connection for Greenway MHR");
		HttpPost httpPost = buildHttpPost(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections", retriever.generatePayload());
		String newConnection = executeRequestGetContent(httpPost);
		
		log("Step 3: Verify that connection hasn't been made already");
		Assert.assertFalse(Retriever.isContain(newConnection, "jobStatus"), "A connection has been made.");
		
		log("Step 4: Refreshing the connection to start the retrieval process");
		String connectionId = newConnection.substring(newConnection.lastIndexOf("id\":") + 4);
		HttpPut httpPut = buildHttpPut(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}","") + "?refresh=true&hardRefresh=false", "{}");
		executeRequestGetContent(httpPut);
		
		log("Step 5: Verify the retrieval was successful");
		String status = retriever.verifyJobStatus(httpPost);
		Assert.assertEquals(status, "SUCCESS", "Job hasn't finished processing or has failed within the first 7 minutes");
		
		log("Step 6: Delete the connection");
		HttpDelete httpDelete = buildHttpDelete(claireRestUrl + "rest/v1/users/" + retriever.Useruuid + "/connections/" + connectionId.replace("}",""));
		executeRequestAndGetStatus(httpDelete);
		httpDelete.releaseConnection();
	}
}
