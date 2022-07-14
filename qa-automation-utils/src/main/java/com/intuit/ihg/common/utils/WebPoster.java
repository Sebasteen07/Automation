//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;


import com.medfusion.common.utils.IHGUtil;

/**
 * Base class for posting Request
 * 
 * @author bkrishnankutty
 * 
 */

public class WebPoster {

	String serviceUrl = null;
	String contentType = "text/xml;charset=UTF-8";
	int expectedStatusCode = 200;
	Map<String, String> headerMap = new HashMap<String, String>();

	/**
	 * sets the service URL
	 */
	public void setServiceUrl(String s) {

		IHGUtil.PrintMethodName();
		serviceUrl = s;
	}

	/**
	 * sets the content type
	 */
	public void setContentType(String s) {

		IHGUtil.PrintMethodName();
		contentType = s;
	}

	/**
	 * set ExpectedStatus Code (expected is 200)
	 * 
	 * @param i
	 */

	public void setExpectedStatusCode(int i) {

		IHGUtil.PrintMethodName();
		expectedStatusCode = i;
	}

	/**
	 * adds header
	 * 
	 * @param name
	 * @param value
	 */
	public void addHeader(String name, String value) {

		IHGUtil.PrintMethodName();
		headerMap.put(name, value);
	}

	/**
	 * Post your Request.xml for more details see line comments
	 * 
	 * @param sResourceFile
	 */
	public void postFromResourceFile(String sResourceFile) throws Exception {

		IHGUtil.PrintMethodName();
		assertNotNull("### Test error - serviceUrl not set", serviceUrl);

		System.out.println("### SERVICE URL: " + serviceUrl);
		System.out.println("### POSTING RESOURCE: " + sResourceFile);

		System.out.println("Create a hand coded requset to send to the server");
		ClientRequest request = new ClientRequest(serviceUrl);

		// Find a resource 'sResourceFile' from the search path used to load classes.
		// This method locates the resource through the system class loader
		URL url = ClassLoader.getSystemResource(sResourceFile);

		assertNotNull(url, "### getSystemResource returned null: [" + sResourceFile + "] ");

		// adding headers to request
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			System.out.println("... HEADER[" + key + "] = [" + value + "]");

			request.header(key, value);
		}


		// adding contentType to request
		request.body(contentType, url.openStream());

		try {

			ClientResponse<String> response = request.post(String.class);

			// asserting if reponse code is 200
			assertEquals(response.getStatus(), expectedStatusCode, "HTTP Status not what expected");

			// printing the response to the console
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("### START POST RESPONSE: \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			System.out.println("### END POST RESPONSE: \n");



		} catch (UnknownHostException ex) {

			if ((IHGUtil.getEnvironmentType().toString()).equalsIgnoreCase("PROD")) {

				throw new Exception("### On PROD, may have to use P10 to post to server: UnknownHostException" + ex.getMessage());
			} else {

				throw new Error(ex);
			}
		}
	}



	/**
	 * Post your Request.xml for more details see line comments
	 * 
	 * @param sResourceFile
	 */
	public String smpostFromResourceFile(String sResourceFile, String xmldata) throws Exception {

		IHGUtil.PrintMethodName();
		assertNotNull("### Test error - serviceUrl not set", serviceUrl);

		System.out.println("### SERVICE URL: " + serviceUrl);
		System.out.println("### POSTING RESOURCE: " + sResourceFile);

		System.out.println("Create a hand coded requset to send to the server");
		ClientRequest request = new ClientRequest(serviceUrl);

		// adding headers to request
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			System.out.println("... HEADER[" + key + "] = [" + value + "]");

			request.header(key, value);
		}

		// adding contentType to request
		request.body(contentType, xmldata);

		try {

			ClientResponse<String> response = request.post(String.class);

			// asserting if reponse code is 200
			assertEquals(response.getStatus(), expectedStatusCode, "HTTP Status not what expected");
			System.out.println(response.getEntity().toString());

			String str = response.getEntity().toString();

			return str;

		} catch (UnknownHostException ex) {

			if ((IHGUtil.getEnvironmentType().toString()).equalsIgnoreCase("PROD")) {

				throw new Exception("### On PROD, may have to use P10 to post to server: UnknownHostException" + ex.getMessage());
			} else {

				throw new Error(ex);
			}

		}

	}

	public String postFromString(String payload) throws Exception {

		IHGUtil.PrintMethodName();
		assertNotNull("### Test error - serviceUrl not set", serviceUrl);

		System.out.println("### SERVICE URL: " + serviceUrl);
		System.out.println("### POSTING PAYLOAD: " + payload);

		ClientRequest request = new ClientRequest(serviceUrl);

		// adding headers to request
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			System.out.println("... HEADER[" + key + "] = [" + value + "]");

			request.header(key, value);
		}

		// adding contentType to request
		request.body(contentType, payload);

		try {

			ClientResponse<String> response = request.post(String.class);

			// asserting if reponse code is as expected
			assertEquals(response.getStatus(), expectedStatusCode, "HTTP Status not what expected");
			System.out.println(response.getEntity().toString());

			String str = response.getEntity().toString();

			return str;

		} catch (UnknownHostException ex) {

			if ((IHGUtil.getEnvironmentType().toString()).equalsIgnoreCase("PROD")) {

				throw new Exception("### On PROD, may have to use P10 to post to server: UnknownHostException" + ex.getMessage());
			} else {

				throw new Error(ex);
			}

		}

	}

	public String postFromStringExplicitTimeout(String payload, int timeoutMillis) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		final HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, timeoutMillis);
		HttpConnectionParams.setSoTimeout(params, timeoutMillis * 10);
		HttpPost postRequest = new HttpPost(serviceUrl);
		IHGUtil.PrintMethodName();
		assertNotNull("### Test error - serviceUrl not set", serviceUrl);

		System.out.println("### SERVICE URL: " + serviceUrl);
		System.out.println("### POSTING PAYLOAD: " + payload);

		// adding headers to request
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			System.out.println("... HEADER[" + key + "] = [" + value + "]");

			postRequest.addHeader(key, value);
		}

		// adding contentType to request
		postRequest.setHeader("Content-Type", contentType);
		StringEntity payloadEntity = new StringEntity(payload);
		postRequest.setEntity(payloadEntity);

		try {

			HttpResponse response = httpClient.execute(postRequest);
			String str = EntityUtils.toString(response.getEntity());

			// asserting if reponse code is as expected
			assertEquals(response.getStatusLine().getStatusCode(), expectedStatusCode, "HTTP Status not what expected");
			System.out.println(str);

			return str;

		} catch (UnknownHostException ex) {

			if ((IHGUtil.getEnvironmentType().toString()).equalsIgnoreCase("PROD")) {

				throw new Exception("### On PROD, may have to use P10 to post to server: UnknownHostException" + ex.getMessage());
			} else {

				throw new Error(ex);
			}

		}

	}

	public boolean get() {
		return get(contentType);
	}

	public boolean get(String type) {
		try {

			ClientRequest request = new ClientRequest(serviceUrl);
			request.accept(type);
			// adding headers to request
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();

				System.out.println("... HEADER[" + key + "] = [" + value + "]");

				request.header(key, value);
			}
			ClientResponse<String> response = request.get(String.class);

			assertEquals(response.getStatus(), expectedStatusCode, "HTTP Status not what expected");

			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			return true;

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return false;

	}

	public boolean getAndSearchForMatch(String lookFor) {
		try {

			ClientRequest request = new ClientRequest(serviceUrl);
			request.accept(contentType);
			// adding headers to request
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();

				System.out.println("... HEADER[" + key + "] = [" + value + "]");

				request.header(key, value);
			}
			ClientResponse<String> response = request.get(String.class);

			assertEquals(response.getStatus(), expectedStatusCode, "HTTP Status not what expected");

			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				if (output.indexOf(lookFor) != -1) {
					System.out.println("Target found! returning \n");
					return true;
				}
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return false;

	}
}
