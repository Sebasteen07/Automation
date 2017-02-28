package com.medfusion.dre.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.util.Base64;
import org.json.JSONObject;

import com.medfusion.factory.RetrieversFactory;
import com.medfusion.dre.objects.Retriever;

public class HTTPCalls extends Logging {
	protected HttpClient client = HttpClientBuilder.create().build();

	private String buildBasicAuthorizationString() {
		Retriever retriever = RetrieversFactory.getRetriever(Data.getMapFor("retriever"));

		String credentials = retriever.Cusername + ":" + retriever.Cpassword;
		String encodedBytes = Base64.encodeBytes(credentials.getBytes(), 0);
		return "Basic " + encodedBytes;
	}

	public HttpPost buildHttpPost(String url, JSONObject payload) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", buildBasicAuthorizationString());

		HttpEntity httpEntity = null;
		try {
			httpEntity = new StringEntity(payload.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(httpEntity);

		return httpPost;
	}

	public HttpPut buildHttpPut(String url, String entity) {
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Content-Type", "application/json");
		httpPut.setHeader("Authorization", buildBasicAuthorizationString());

		HttpEntity httpEntity = null;
		try {
			httpEntity = new StringEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPut.setEntity(httpEntity);

		return httpPut;
	}

	public HttpDelete buildHttpDelete(String url) {
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setHeader("Content-Type", "application/json");
		httpDelete.setHeader("Authorization", buildBasicAuthorizationString());

		return httpDelete;
	}

	public HttpResponse executeRequestGetResponse(HttpRequestBase httpRequestBase) {

		HttpResponse httpResponse = null;
		try {
			logger.info("Requesting: " + httpRequestBase.getMethod() + " " + httpRequestBase.getURI());
			httpResponse = client.execute(httpRequestBase);

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (httpResponse != null) {
			logger.info("Response status: " + httpResponse.getStatusLine());
		}

		return httpResponse;
	}

	public String executeRequestGetContent(HttpRequestBase httpRequestBase) {
		String result = null;

		HttpResponse httpResponse = executeRequestGetResponse(httpRequestBase);

		if (httpResponse != null) {
			result = getContentFromResponse(httpResponse);
		}
		logger.info("Response body: " + result);
		return result;
	}

	public String getContentFromResponse(HttpResponse httpResponse) {
		String result = null;
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(httpResponse.getEntity().getContent(), writer);
			result = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public StatusLine executeRequestAndGetStatus(HttpRequestBase httpRequestBase) {
		StatusLine status = null;

		HttpResponse httpResponse = executeRequestGetResponse(httpRequestBase);

		if (httpResponse != null) {
			status = httpResponse.getStatusLine();
		}
		return status;
	}

}
