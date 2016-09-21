package com.intuit.ihg.common.utils.downloads;

import org.apache.http.client.methods.*;

// Source: http://ardesco.lazerycode.com/index.php/2012/07/how-to-download-files-with-selenium-and-why-you-shouldnt/
public enum RequestMethod {
	OPTIONS(new HttpOptions()), GET(new HttpGet()), HEAD(new HttpHead()), POST(new HttpPost()), PUT(new HttpPut()), DELETE(new HttpDelete()), TRACE(
			new HttpTrace());

	private final HttpRequestBase requestMethod;

	RequestMethod(HttpRequestBase requestMethod) {
		this.requestMethod = requestMethod;
	}

	public HttpRequestBase getRequestMethod() {
		return this.requestMethod;
	}
}
