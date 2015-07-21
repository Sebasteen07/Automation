package com.intuit.ihg.common.utils.mail;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.intuit.ihg.rest.RestUtils;

public class Mailinator {

	private static final String API_TOKEN = "f2b71f25983947569828157c7de3c9ef";
	private static final String MAILINATOR_INBOX_TEMPLATE_URL = "https://api.mailinator.com/api/inbox?token="
			+ API_TOKEN + "&to=";
	private static final String MAILINATOR_EMAIL_TEMPLATE_URL = "https://api.mailinator.com/api/email?token="
			+ API_TOKEN + "&msgid=";

	private static final int TIME_TO_WAIT_MS = 10000;

	/*
	 * @Author Petr H
	 * This method checks the mailbox for an email with specified emailSubject starting from the most recent.
	 * If it finds some url in the link which contains text specified in findInEmail it will return it
	 */
	private String email(String username, String emailSubject, String findInEmail, int retries)
			throws Exception {

		for (int j = 0; j != retries; j++) {
			System.out.println("Waiting for e-mail to arrive for " + TIME_TO_WAIT_MS / 1000 + " s.");
			Thread.sleep(TIME_TO_WAIT_MS);
			String url = MAILINATOR_INBOX_TEMPLATE_URL + username;

			System.out.println("GET inbox from: " + url);
			JSONObject inbox = new JSONObject(RestUtils.get(url, String.class,
					MediaType.APPLICATION_XML));
			JSONArray inboxArray = inbox.getJSONArray("messages");
			for (int i = inboxArray.length() - 1; i >= 0; i--) {
				JSONObject emailInInbox = inboxArray.getJSONObject(i);
				if (emailInInbox.get("subject").toString().contains(emailSubject)) {
					url = MAILINATOR_EMAIL_TEMPLATE_URL + emailInInbox.get("id").toString();
					System.out.println("GET e-mail from: " + url);
					JSONObject email = new JSONObject(RestUtils.get(url, String.class,
							MediaType.APPLICATION_XML));
					JSONArray parts = email.getJSONObject("data").getJSONArray("parts");
					for (int k = 0; k < parts.length(); k++) {
						Document emailContent = Jsoup.parse(parts.getJSONObject(k)
								.getString("body"));
						Elements links = emailContent.body().getElementsContainingText(findInEmail);
						String href = links.attr("href");
						if (!href.isEmpty()) {
							System.out.println("URL found: " + href);
							return href;
						}
					}
				}
			}
			System.out.println("Email not found. Trial number " + (j + 1) + " / " + retries);
		}
		return null;
	}

	public String email(String username, String emailSubject, String findInEmail) throws Exception {

		return this.email(username, emailSubject, findInEmail, 5);
	}

	public boolean isMessageInInbox(String username, String emailSubject, String findInEmail,
			int retries) throws Exception {
		return (this.email(username, emailSubject, findInEmail, retries) != null);
	}

	/*
	 * @Author Petr H
	 * This method first checks the inbox for number of emails.
	 * Then it waits for a new mail to arrive and checks if it contains the link specified in targetUrl
	 */
	public boolean catchNewMessageCheckLinkUrl(String username, String emailSubject,
			String findInEmail, String targetUrl, int retries) throws Exception {
		return catchNewMessage(username, emailSubject, findInEmail, targetUrl, retries) != null;
	}

	/**
	 * @author phajek
	 *         This method first checks the inbox for number of emails.
	 *         Then it waits for a new mail with specified subject to arrive.
	 * @return If findInEmail is not specified it returns full subject of the e-mail.
	 *         Else it checks it for a link with text specified in finInEmail:
	 * @return If target url is specified it compares it and returns the url only if it contains the target url
	 * @return If target url isn't specified it returns the url found using findInEmail value
	 */
	public String catchNewMessage(String username, String emailSubject, String findInEmail,
			String targetUrl, int retries) throws Exception {
		String inboxUrl = MAILINATOR_INBOX_TEMPLATE_URL + username;
		System.out.println("GET initial inbox from: " + inboxUrl);
		JSONObject inbox = new JSONObject(RestUtils.get(inboxUrl, String.class,
				MediaType.APPLICATION_XML));
		JSONArray inboxArray = inbox.getJSONArray("messages");
		int initialMailCount = inboxArray.length();
		System.out.println("Initial mail count: " + initialMailCount);
		String initialLastMailId;
		if (initialMailCount > 0) {
			initialLastMailId = inboxArray.getJSONObject(initialMailCount - 1).get("id").toString();
		}
		else {
			initialLastMailId = null;
		}
		for (int j = 0; j != retries; j++) {
			System.out
			.println("Waiting for e-mail to arrive for " + TIME_TO_WAIT_MS / 1000 + " s.");
			Thread.sleep(TIME_TO_WAIT_MS);
			System.out.println("GET inbox from: " + inboxUrl);
			inbox = new JSONObject(RestUtils.get(inboxUrl, String.class, MediaType.APPLICATION_XML));
			inboxArray = inbox.getJSONArray("messages");
			int currentMailCount = inboxArray.length();
			if (currentMailCount > 0) {
				JSONObject lastMailInInbox = inboxArray.getJSONObject(currentMailCount - 1);
				String currentLastMailId = lastMailInInbox.get("id").toString();
				if (!currentLastMailId.equals(initialLastMailId)) {			
					String fullSubject = lastMailInInbox.get("subject").toString();
					if (fullSubject.contains(emailSubject)) {
						if (findInEmail != null) {
							String emailUrl = MAILINATOR_EMAIL_TEMPLATE_URL
									+  currentLastMailId;
							System.out.println("GET e-mail from: " + emailUrl);
							JSONObject email = new JSONObject(RestUtils.get(emailUrl, String.class,
									MediaType.APPLICATION_XML));
							JSONArray parts = email.getJSONObject("data").getJSONArray("parts");
							for (int k = 0; k < parts.length(); k++) {
								Document emailContent = Jsoup.parse(parts.getJSONObject(k).getString(
										"body"));
								Elements links = emailContent.body().getElementsContainingText(
										findInEmail);
								String href = links.attr("href");
								if ((targetUrl == null && !href.isEmpty())
										|| (targetUrl != null && href.contains(targetUrl))) {
									System.out.println("URL found: " + href);
									return href;
								}
							}
						} else {
							System.out.println("Email with subject: \"" + fullSubject + "\" found.");
							return fullSubject;
						}
					}
				}
			}
			System.out.println("Email not found. Trial number " + (j + 1) + " / " + retries);

		}
		return null;
	}

	/**
	 * @author phajek
	 *         Catch a new mail received and get some url from it specified by findInEmail
	 *         See previous method - case when targetUrl is not specified
	 * @return Url found in the link found using findInEmail value
	 */
	public String catchNewMessage(String username, String emailSubject, String findInEmail,
			int retries) throws Exception {
		return catchNewMessage(username, emailSubject, findInEmail, null, retries);
	}

	/**
	 * @author phajek
	 *         Catch new email - Subject of it must contain the emailSubject
	 *         See previous methods - case when findInEmail is not specified
	 * @return Full subject of the email found (containing emailSUbject)
	 */
	public String catchNewMessage(String username, String emailSubject, int retries)
			throws Exception {
		return catchNewMessage(username, emailSubject, null, null, retries);
	}

	/**
	 * @author phajek
	 *         Checks if any new email arrives
	 * @return Full subject of the email found
	 */
	public String catchNewMessage(String username, int retries) throws Exception {
		return catchNewMessage(username, "", null, null, retries);
	}


}
