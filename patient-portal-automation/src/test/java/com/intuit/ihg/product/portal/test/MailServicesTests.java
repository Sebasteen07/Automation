package com.intuit.ihg.product.portal.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.mail.Mailinator;


public class MailServicesTests extends BaseTestNGWebDriver {	

	@Test
	public void testMailinator() throws Exception {
		Mailinator mailinator = new Mailinator();
		String username = "test1854medfusion";
		String emailSubject = "test";
		String findInEmail = "testing";
		String targetUrl = "medfusion.com";
		int retries = 3;
		assertTrue(mailinator.isMessageInInbox(username, emailSubject, findInEmail, retries));
		assertFalse(mailinator.isMessageInInbox(username, emailSubject, "something not there", retries));
		assertTrue(mailinator.catchNewMessageCheckLinkUrl(username, emailSubject, findInEmail,
				targetUrl, retries));
		assertFalse(mailinator.catchNewMessageCheckLinkUrl(username, emailSubject, findInEmail,
				targetUrl + "nononono", retries));
	}
}
