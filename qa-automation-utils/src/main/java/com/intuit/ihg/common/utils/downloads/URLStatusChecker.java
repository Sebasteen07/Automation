//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils.downloads;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.medfusion.common.utils.IHGUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

public class URLStatusChecker {

	private static final Logger LOG = Logger.getLogger(URLStatusChecker.class);
	private URI linkToCheck;
	private WebDriver driver;
	private boolean mimicWebDriverCookieState = true;
	private boolean followRedirects = false;
	private RequestMethod httpRequestMethod = RequestMethod.GET;

	public URLStatusChecker(WebDriver driverObject) throws MalformedURLException, URISyntaxException {
		this.driver = driverObject;
	}

	public void setURIToCheck(String linkToCheck) throws MalformedURLException, URISyntaxException {
		this.linkToCheck = new URI(linkToCheck);
	}

	public void setURIToCheck(URI linkToCheck) throws MalformedURLException {
		this.linkToCheck = linkToCheck;
	}

	public void setURIToCheck(URL linkToCheck) throws URISyntaxException {
		this.linkToCheck = linkToCheck.toURI();
	}

	public void setHTTPRequestMethod(RequestMethod requestMethod) {
		this.httpRequestMethod = requestMethod;
	}

	public void followRedirects(Boolean value) {
		this.followRedirects = value;
	}

	public int getHTTPStatusCode() throws IOException {

		HttpClient client = new DefaultHttpClient();
		BasicHttpContext localContext = new BasicHttpContext();

		LOG.info("Mimic WebDriver cookie state: " + this.mimicWebDriverCookieState);
		if (this.mimicWebDriverCookieState) {
			localContext.setAttribute(ClientContext.COOKIE_STORE, mimicCookieState(this.driver.manage().getCookies()));
		}
		HttpRequestBase requestMethod = this.httpRequestMethod.getRequestMethod();
		requestMethod.setURI(this.linkToCheck);
		HttpParams httpRequestParameters = requestMethod.getParams();
		httpRequestParameters.setParameter(ClientPNames.HANDLE_REDIRECTS, this.followRedirects);
		requestMethod.setParams(httpRequestParameters);

		LOG.info("Sending " + requestMethod.getMethod() + " request for: " + requestMethod.getURI());
		HttpResponse response = client.execute(requestMethod, localContext);
		LOG.info("HTTP " + requestMethod.getMethod() + " request status: " + response.getStatusLine().getStatusCode());

		return response.getStatusLine().getStatusCode();
	}

	public void mimicWebDriverCookieState(boolean value) {
		this.mimicWebDriverCookieState = value;
	}

	private BasicCookieStore mimicCookieState(Set<Cookie> seleniumCookieSet) {
		BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
		for (Cookie seleniumCookie : seleniumCookieSet) {
			BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
			duplicateCookie.setDomain(seleniumCookie.getDomain());
			duplicateCookie.setSecure(seleniumCookie.isSecure());
			duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
			duplicateCookie.setPath(seleniumCookie.getPath());
			mimicWebDriverCookieStore.addCookie(duplicateCookie);
		}

		return mimicWebDriverCookieStore;
	}

	public int getDownloadStatusCode(String url, RequestMethod method) throws URISyntaxException, IOException {
		IHGUtil.PrintMethodName();
		URLStatusChecker urlChecker = new URLStatusChecker(driver);
		urlChecker.setURIToCheck(url);
		urlChecker.setHTTPRequestMethod(RequestMethod.GET);
		urlChecker.mimicWebDriverCookieState(true);

		return urlChecker.getHTTPStatusCode();
	}

}
