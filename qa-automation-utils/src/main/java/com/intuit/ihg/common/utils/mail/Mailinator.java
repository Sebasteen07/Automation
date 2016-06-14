package com.intuit.ihg.common.utils.mail;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.rest.RestUtils;
import org.apache.log4j.Level;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.ws.rs.core.MediaType;

public class Mailinator {

	private static final String API_TOKEN = "f2b71f25983947569828157c7de3c9ef";
	private static final String MAILINATOR_INBOX_TEMPLATE_URL = "https://api.mailinator.com/api/inbox?token="
			+ API_TOKEN + "&to=";
	private static final String MAILINATOR_EMAIL_TEMPLATE_URL = "https://api.mailinator.com/api/email?token="
			+ API_TOKEN + "&msgid=";
	private static final int TIME_TO_WAIT_MS = 10000;

	/**
	 * @Author Petr H
	 * This method checks the mailbox for an email with specified emailSubject starting from the most recent.
	 * If it finds some url in the link which contains text specified in findInEmail it will return it
	 *
	 * @return link with the text from findInEmail, null if it not found
	 */
	public String getLinkFromEmail(String username, String emailSubject, String findInEmail, int retries) throws InterruptedException {

		String url = MAILINATOR_INBOX_TEMPLATE_URL + username;

		for (int j = 1; j <= retries; j++) {
			JSONObject inbox = getJSONFromMailinator(url);
			try {
				JSONArray emails = inbox.getJSONArray("messages");
				for (int i = 0; i < emails.length(); i++) {
					JSONObject email = emails.getJSONObject(i);
					if (email.get("subject").toString().contains(emailSubject)) {
						String emailUrl = MAILINATOR_EMAIL_TEMPLATE_URL + email.get("id").toString();
						JSONObject emailDetail = getJSONFromMailinator(emailUrl);
						Document emailContent = getEmailContent(emailDetail);
						Elements links = emailContent.body().getElementsContainingText(findInEmail);
						String href = links.attr("href");
						if (!href.isEmpty()) {
							Log4jUtil.log("URL found: " + href);
							return href;
						}
					}
				}
			} catch (JSONException e) {
				Log4jUtil.log("Response from Mailinator could not be parsed: " + e);
			}
			Log4jUtil.log(
					"Email was not retrieved. Trial number " + j + "/" + retries
							+ ". Waiting for e-mail to arrive for " + TIME_TO_WAIT_MS / 1000 + " s.");
			Thread.sleep(TIME_TO_WAIT_MS);
		}
		return null;
	}

	/**
	 * Works just like {@link Mailinator#getLinkFromEmail(String, String, String, int)} with retries
	 * set to 5
	 * @see Mailinator#getLinkFromEmail(String, String, String, int)
	 */
	public String getLinkFromEmail(String username, String emailSubject, String findInEmail) throws Exception {

		return this.getLinkFromEmail(username, emailSubject, findInEmail, 5);
	}

	public boolean isMessageInInbox(String username, String emailSubject, String findInEmail,
			int retries) throws Exception {
		return (this.getLinkFromEmail(username, emailSubject, findInEmail, retries) != null);
	}

	/**
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
		JSONObject inbox = getJSONFromMailinator(inboxUrl);
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
			inbox = getJSONFromMailinator(inboxUrl);
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
							JSONObject email =getJSONFromMailinator(emailUrl);
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

	/**
	 * @author lhruban
	 * Gets json from Mailinator. If en exception occurs, empty JSON is returned.
	 * @param url URL of endpoint
	 * @return JSON response
	 */
	private JSONObject getJSONFromMailinator(String url) {
		Log4jUtil.log("GET JSON from: " + url);
		JSONObject result;
		try {
			result = new JSONObject(RestUtils.get(url, String.class, MediaType.APPLICATION_XML));
			Log4jUtil.log("JSON retrieved: " + result, Level.DEBUG);
		} catch (Exception e) {
			Log4jUtil.log("Mailinator did not respond properly: " + e);
			result = new JSONObject();
		}
		return result;
	}

	/**
	 * @author lhruban
	 * Gets email's body out of the JSON response from Mailinator.
	 * @throws JSONException when JSON cannot be parsed properly.
	 * @param emailDetail JSON containing email.
	 * @return email's body (empty Document if there are no 'parts' in email).
	 */
	private Document getEmailContent(JSONObject emailDetail) throws JSONException {
		Document emailContent = new Document("");
		JSONArray parts = emailDetail.getJSONObject("data").getJSONArray("parts");
		for (int i = 0; i < parts.length(); i++) {
			emailContent = Jsoup.parse(parts.getJSONObject(i).getString("body"));
		}
		return emailContent;
	}
}
