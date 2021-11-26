package com.medfusion.tests;

import com.medfusion.factory.pojos.provisioning.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by lubson on 07.03.16.
 */
public class BaseRestTest extends BaseTest {
    protected HttpClient client = HttpClientBuilder.create().build();


    public String executeRequest(HttpRequestBase httpRequestBase){
        String result = "";

        HttpResponse httpResponse = null;
        try {
            logger.info("Requesting: " + httpRequestBase.getMethod() + " " + httpRequestBase.getURI());
            httpResponse = client.execute(httpRequestBase);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (httpResponse != null) {
            logger.info("Response status: " + httpResponse.getStatusLine());

            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(httpResponse.getEntity().getContent(), writer);
                result = writer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("Response body: " + result);
        return result;
    }


    //TODO Generilze http building methods
    public HttpGet buildHttpGet(String url, User user){
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", user.getCredentialsEncodedInBase());

        return  httpGet;
    }

    public HttpPost buildHttpPost(String url, User user, JSONObject entity) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", user.getCredentialsEncodedInBase());

        HttpEntity httpEntity = null;
        try {
            httpEntity =new StringEntity(entity.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(httpEntity);

        return httpPost;
    }

    public HttpPut buildHttpPut(String url, User user, JSONObject entity) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json");
        httpPut.setHeader("Authorization", user.getCredentialsEncodedInBase());

        HttpEntity httpEntity = null;
        try {
            httpEntity =new StringEntity(entity.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPut.setEntity(httpEntity);

        return httpPut;
    }


}

