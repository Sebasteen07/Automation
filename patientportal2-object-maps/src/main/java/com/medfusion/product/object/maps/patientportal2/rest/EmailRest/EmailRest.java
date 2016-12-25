package com.medfusion.product.object.maps.patientportal2.rest.EmailRest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

public class EmailRest {

	public EmailRest() {}

	public HttpResponse sendAnEmail(String postUrl, EmailBodyPojo emailBodyPojo) throws ClientProtocolException, IOException {
		Gson gson = new Gson();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString;

		postingString = new StringEntity(gson.toJson(emailBodyPojo));
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		return response;
	}

}
