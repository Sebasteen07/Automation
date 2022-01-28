//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils.mail;

/**
 * References: http://www.mindfiresolutions.com/Retrive-email-from-Yahoo--Gmail-server-using-IMAP-in-JAVA-1244.php
 * http://stackoverflow.com/questions/61176/getting-mail-from-gmail-into-java-application-using-imap
 * http://stackoverflow.com/questions/5366767/retrieve-unread-emails-from-gmail-javamail-api-imap
 * http://stackoverflow.com/questions/2561784/how-to-get-recipients-addresses-as-string-in-javamail
 * http://stackoverflow.com/questions/7304860/cant-get-emails-in-drafts-spam-and-trash-folders-from-google-using-javax-mail
 * http://stackoverflow.com/questions/483048/access-gmail-from-java http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 */

import com.medfusion.common.utils.IHGUtil;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.*;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class GmailBot implements MailBot {

	private static final String GMAIL_FOLDER_INBOX = "Inbox";
	// private static final String GMAIL_FOLDER_DRAFTS = "[Gmail]/Drafts";
	private static final String GMAIL_FOLDER_SPAM = "[Gmail]/Spam";
	// private static final String GMAIL_FOLDER_TRASH = "[Gmail]/Trash"; // UK =
	private static final String GMAIL_FOLDER_TRASH = "Inbox"; // [Google
	// Mail]/Bin

	private static final String GMAIL_AUTH_USER = "com.test.ihg@gmail.com";
	private static final String GMAIL_AUTH_PWD = "Intuit123";

	private Session mailSession = null;
	private Store store = null;

	private String userId;
	private String password;

	// Setters for userID and password

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCredentials(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public Session setup() {

		return setup(MailSessionType.IMAP);
	}

	public Session setup(MailSessionType mt) {

		IHGUtil.PrintMethodName();

		Properties props = new Properties();

		switch (mt) {
		case SMTP:

			// TODO

			Log4jUtil.log("INTERNAL ERROR: SMTP not available for GmailBot yet.");

			break;

		case IMAP:

			// /////////////
			// IMAP
			props.setProperty("mail.store.protocol", "imaps");
			props.setProperty("mail.imap.host", "imap.gmail.com");
			props.setProperty("mail.imap.port", "993");
			props.setProperty("mail.imap.connectiontimeout", "5000");
			props.setProperty("mail.imap.timeout", "5000");
			props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.imap.socketFactory.fallback", "false");
			break;

		default:

			Log4jUtil.log("INTERNAL ERROR: Unhandled MailSessionType");
			return null;
		}

		mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);

		return mailSession;
	}

	public Store connect(MailSessionType mt, String sUser, String sPassword) throws MessagingException {

		IHGUtil.PrintMethodName();

		Log4jUtil.log("Gmail Password :" + sPassword);

		mailSession = setup(mt);

		switch (mt) {
		case SMTP:

			throw new Error("### GmailBot SMTP currently not available");

		case IMAP:

			store = mailSession.getStore("imaps");
			Log4jUtil.log(sPassword);
			store.connect("imap.gmail.com", sUser, sPassword);
			break;

		default:

			throw new Error("### GmailBot - Unhandled mail type");
		}

		/*
		 * if (!store.isConnected()) { store.connect(); }
		 */

		return store;
	}

	public void close() throws MessagingException {

		IHGUtil.PrintMethodName();

		if (store != null) {

			if (store.isConnected()) {

				store.close();
			}

		}
	}

	public List<HashMap<String, String>> findTrashMessageTo(String recipient, String sSubject, String sBody,
			int minutesAgo, boolean bFindSeen, boolean bMarkSeen) throws MessagingException, IOException {

		IHGUtil.PrintMethodName();

		return findMessageTo(recipient, GMAIL_FOLDER_TRASH, sSubject, sBody, minutesAgo, bFindSeen, bMarkSeen);
	}

	/**
	 * Find messages in inbox folder with Subject sSubject and returns it
	 * 
	 */
	public List<HashMap<String, String>> findMessageTo(String recipient, String folder, String sSubject, String sBody,
			int minutesAgo, boolean bFindSeen, boolean bMarkSeen) throws MessagingException, IOException {

		IHGUtil.PrintMethodName();

		System.out.println("\n####################################################");
		Log4jUtil.log("### Finding Message(s) for: " + recipient);

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		if (this.store == null) {

			System.out.println("### ERROR: need to setup and connect to mailbox first.");

			return null;
		}

		Folder inbox = store.getFolder(folder);

		inbox.open(Folder.READ_WRITE);

		SearchTerm searchTo = new RecipientStringTerm(Message.RecipientType.TO, recipient);

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, 0 - minutesAgo); // Look for emails within the
		// last x minutes.

		Date sinceDate = new Date(cal.getTimeInMillis());

		SearchTerm searchSinceDate = new ReceivedDateTerm(ComparisonTerm.GT, sinceDate);

		SearchTerm andTerm = new AndTerm(searchTo, searchSinceDate);

		// Decide if emails found were seen.

		FlagTerm ft = new FlagTerm(new Flags(Flag.SEEN), bFindSeen);

		andTerm = new AndTerm(andTerm, ft);

		// ////////////////////////////////
		// search subject.

		if (sSubject != null) {

			SubjectTerm subjectTerm = new SubjectTerm(sSubject);

			andTerm = new AndTerm(andTerm, subjectTerm);
		}

		// ////////////////////////////////
		// search body.

		if (sBody != null) {

			BodyTerm bodyTerm = new BodyTerm(sBody);

			andTerm = new AndTerm(andTerm, bodyTerm);
		}

		// //////

		Log4jUtil.log("### DEBUG EMAIL SEARCH: Looking for emails to: " + recipient + " since: " + sinceDate.toString()
				+ (sBody != null ? "containing: " + sBody : "") + " ( now: " + (new Date()).toString() + " )");

		Message messages[] = inbox.search(andTerm);

		Log4jUtil.log("### Messages: " + messages.length);

		for (Message message : messages) {

			System.out.println("\n####################################################");
			Log4jUtil.log("DATE: " + message.getSentDate().toString());
			Log4jUtil.log("FROM: " + message.getFrom()[0].toString());
			Log4jUtil.log("TO: " + message.getRecipients(Message.RecipientType.TO)[0]);
			Log4jUtil.log("SUBJECT: " + message.getSubject().toString());
			Log4jUtil.log("CONTENT: " + message.getContent().toString());

			HashMap<String, String> messageMap = new HashMap<String, String>();

			messageMap.put("DATE", message.getSentDate().toString());
			messageMap.put("FROM", message.getFrom()[0].toString());
			messageMap.put("TO", message.getRecipients(Message.RecipientType.TO)[0].toString());
			messageMap.put("SUBJECT", message.getSubject().toString());
			messageMap.put("CONTENT", message.getContent().toString());

			// Parse multipart message

			String sPlainText = "";
			String sHTML = "";

			Object content = message.getContent();

			if (content instanceof String) {

				messageMap.put("CONTENT-TYPE", MailContentType.STRING.getContentType());

				sPlainText = (String) message.getContent();

			} else if (content instanceof Multipart) {

				messageMap.put("CONTENT-TYPE", MailContentType.MULTIPART.getContentType());

				Multipart mp = (Multipart) message.getContent();

				for (int i = 0; i < mp.getCount(); i++) {

					Part part = mp.getBodyPart(i);

					String disposition = part.getDisposition();

					if (disposition == null) {

						if (part instanceof MimeBodyPart) {

							if (part.isMimeType("text/plain")) {
								sPlainText += (String) part.getContent();
							}

							if (part.isMimeType("text/html")) {
								sHTML += (String) part.getContent();
							}
						}

					} else {

						if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {

							System.out.println("### DEBUG: ATTACHMENT PART FOUND");

							String filename = MimeUtility.decodeText(part.getFileName());

							// TODO - filenames to an array in hash map.

							Log4jUtil.log("### DEBUG: Attachment Filename: " + filename);

							// TODO - save files to an array ??? What if very large ???

							// TODO - or save to target/ and put unique path in hash table ???

							// String path_filename = saveFile(filename, part.getInputStream());

						}

						if (disposition.equalsIgnoreCase(Part.INLINE)) {

							Log4jUtil.log("### DEBUG: INLINE PART FOUND");
						}
					}
				}

			} else {

				messageMap.put("CONTENT-TYPE", MailContentType.UNKNOWN.getContentType());

				System.out.println("### WARNING: GMAILBOT can't handle message type.");
			}

			messageMap.put("PLAIN_TEXT", sPlainText);
			messageMap.put("HTML", sHTML);

			list.add(messageMap);

			// TODO - redundant? Does READ_WRITE / scan automatically mark as SEEN ?
			// TODO - must come at very end in order to work ?
			message.setFlag(Flag.SEEN, bMarkSeen);
		}

		inbox.close(false);

		return list;
	}

	public void connectTestAccount() throws Exception {

		IHGUtil.PrintMethodName();

		this.store = connect(MailSessionType.IMAP, GMAIL_AUTH_USER, GMAIL_AUTH_PWD);
	}

	public void testGmail() throws Exception {

		IHGUtil.PrintMethodName();

		this.connectTestAccount();

		// For test account, all emails are sent to trash.

		Folder inbox = store.getFolder(GMAIL_FOLDER_TRASH);
		inbox.open(Folder.READ_ONLY);
		FlagTerm ft = new FlagTerm(new Flags(Flag.SEEN), false); // Only
		// search
		// SEEN
		// =
		// false
		Message messages[] = inbox.search(ft);

		Log4jUtil.log("### Messages: " + messages.length);

		int testLimit = 3;

		for (Message message : messages) {

			if (testLimit-- < 0)
				break;

			System.out.println("\n####################################################");
			Log4jUtil.log("DATE: " + message.getSentDate().toString());
			Log4jUtil.log("FROM: " + message.getFrom()[0].toString());
			Log4jUtil.log("TO: " + message.getRecipients(Message.RecipientType.TO)[0]);
			Log4jUtil.log("SUBJECT: " + message.getSubject().toString());
			Log4jUtil.log("CONTENT: " + message.getContent().toString());
		}

		inbox.close(false);

		this.close();
	}

	///////////////////////

	// TODO - moved to GmailBot
	// Find location of string original string and trim everything beyond that
	// location
	public String trimFromString(String s, String sEnd) {

		if (s.contains(sEnd)) {

			// Trim off trailing text - if any

			int iEnd = s.indexOf(sEnd);

			return s.substring(0, iEnd);

		}

		return s;
	}

	public String trimIfLastChar(String s, String sLast) {

		// TODO - make sure last is only 1 char long

		int iEnd = s.indexOf(sLast);

		if (s.length() == iEnd) {

			return s.substring(0, iEnd);
		}

		return s;
	}

	public String findTrashEmailLink(String sUser, String sSubjectContains, String sLinkContains, int minutesAgo,
			boolean bFindSeen, boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		Set<String> urlList = findTrashEmailLinks(sUser, sSubjectContains, minutesAgo, bFindSeen, bMarkSeen);

		String sURL = "";

		for (String url : urlList) {

			String testURL = url;

			testURL = trimFromString(testURL, " ");
			testURL = trimFromString(testURL, "<");
			testURL = trimIfLastChar(testURL, "."); // Can't trim from middle - that would be valid.

			Log4jUtil.log("DEBUG: URL [...]: " + testURL);

			if (testURL.contains(sLinkContains)) { // Find link with matching content.
				sURL = testURL;
				// break;
			}
		}

		Log4jUtil.log("DEBUG: URL: " + sURL);

		return sURL;
	}

	public Set<String> findTrashEmailLinks(String sUser, String sSubjectContains, int minutesAgo, boolean bFindSeen,
			boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		Set<String> urlList = new HashSet<String>();

		// /////////////////////////
		// Use GmailBot to parse email;

		// GmailBot gBot = gbotConnect();

		this.connect(MailSessionType.IMAP, userId, password);

		// Poll for email.

		List<HashMap<String, String>> messageList = null;

		int mailTries = IHGUtil.getTestMailLoop(); // Can be set
		// from command
		// lime:
		// -Dtest.mail.loop=10

		Log4jUtil.log("### DEBUG: MAIL TRIES: " + mailTries);

		int iSleep = 10000;

		Log4jUtil.log("### DEBUG: MAIL SLEEP (ms): " + iSleep);

		// String sDate = "", sFrom = "", sSubject = "",
		String sContentType = "", sTo = "", sContent = "", sPlainText = "", sHTML = "";

		boolean bSubjectFound = false;

		for (int safetyValve = 0; safetyValve < mailTries; safetyValve++) {

			Log4jUtil.log("Sleeping waiting for email man ...");

			Thread.sleep(iSleep); // TODO - get from properties file

			messageList = findTrashMessageTo(sUser, sSubjectContains, null, minutesAgo, bFindSeen, bMarkSeen);

			if (messageList == null)
				continue;

			for (HashMap<String, String> message : messageList) {

				Log4jUtil.log("########## SUBJECT: " + message.get("SUBJECT"));

				/*
				 * if (!message .get("SUBJECT") .contains(sSubjectContains)) continue;
				 */

				bSubjectFound = true;

				// TODO - mark messages as seen (via bot?).

				// TODO verify is recent - may have to account for time zones!
				// sDate = message.get("DATE");

				// sFrom = message.get("FROM"); // TODO verify expected FROM
				// address
				sTo = message.get("TO"); // Format is (sans []):
				// ["com.test.igh+FOO@gmail.com"
				// <com.test.igh+FOO@gmail.com>]
				// sSubject = message.get("SUBJECT"); // TODO verify expected
				// subject
				sContent = message.get("CONTENT");
				sPlainText = message.get("PLAIN_TEXT");
				sHTML = message.get("HTML");
				sContentType = message.get("CONTENT-TYPE");

				break;
			}

			if (bSubjectFound)
				break;
		}

		this.close();

		if (messageList == null) {

			throw new Exception("### Message list null - no messages found in trash folder for: " + sUser);
		}

		if (messageList.size() <= 0) {

			throw new Exception("### No messages found in trash folder for: " + sUser + ", retries: " + mailTries
					+ ", sleep(ms): " + iSleep);
		}

		// Parse message list.

		// HashMap<String, String> message = messageList.get(0);

		if (!bSubjectFound) {
			throw new Exception("### ERROR: Message with matching subject not found.");
		}

		if (!sTo.toLowerCase().contains(sUser.toLowerCase())) {
			throw new Exception("### ERROR: Recipient retrieved doesn't match. [" + sTo + "] vs. [" + sUser + "] ");
		}

		Log4jUtil.log("DEBUG: CONTENT: " + sContent);
		Log4jUtil.log("DEBUG: PLAIN TEXT: " + sPlainText);
		Log4jUtil.log("DEBUG: HTML: " + sHTML);

		// Find URL

		String sURL = "";

		if (sContentType.matches("MULTIPART")) {

			int iStartURL = sPlainText.indexOf("https://");

			if (iStartURL == -1) {

				iStartURL = sPlainText.indexOf("http://");
			}

			assertTrue(iStartURL > -1, "### ERROR: Can't find https or http link in email.");

			int iEndURL = sPlainText.indexOf("]", iStartURL);

			sURL = sPlainText.substring(iStartURL, iEndURL);

			// TODO - update to find multiples

			urlList.add(sURL);

		} else if (sContentType.matches("STRING")) {

			// ///////////////////////////////////////////////////
			// WORK AROUND (for <a href=qa.../>)

			int iPos = 0;

			// ////////////////////////////////
			// Search for URL in <a href=...>
			// <a href=qa/login>, <a href="qa/login">, <a href=http...>, <a
			// href="http...">

			while (iPos != -1) {

				// Search for: <a href=qa/login>, <a href="qa/login">, <a
				// href=http...>, <a href="http...">

				String aHref = "<a href=";

				// TODO - port this to multipart handler above

				int iStartHREF = sPlainText.indexOf(aHref, iPos);

				if (iStartHREF == -1)
					break; // No more <a href...

				iStartHREF += aHref.length();

				int iEndHREF = sPlainText.indexOf(">", iStartHREF);

				String sHREF = sPlainText.substring(iStartHREF, iEndHREF);

				Log4jUtil.log("#### HREF: " + sHREF);

				// //////////////////////////////////////////////////

				// Look for enclosing quote.
				int iStartURL = sHREF.indexOf("\"");

				if (iStartURL == -1) {

					// If no quote, just use extracted string.

					sURL = sHREF;

				} else {

					// If quote, find closing quote and extract URL from between
					// quotes

					int iEndURL = sHREF.indexOf("\"", iStartURL + 1);

					assertTrue(iEndURL > -1,
							"### ERROR: Can't find closing quote in HREF: [" + sHREF.substring(iStartURL) + "]");

					sURL = sHREF.substring(iStartURL + 1, iEndURL);
				}

				// //////////////////////////////////////////////////

				// Look for enclosing single quote.

				int iStartSingle = sURL.indexOf("\'");

				if (iStartSingle != -1) {

					int iEndSingle = sURL.indexOf("\'", iStartSingle + 1);

					assertTrue(iEndSingle > -1, "### ERROR: Can't find closing single quote in URL.");

					sURL = sURL.substring(iStartSingle + 1, iEndSingle);
				}

				urlList.add(sURL);

				iPos = iEndHREF;
			}

			// //////////////////////////////////
			// Search for http surrounded by square brackets ( "[http... ]" )

			iPos = 0;

			while (iPos != -1) {

				String spacePlusHttp = "[http";

				int iStartURL = sPlainText.indexOf(spacePlusHttp, iPos);

				if (iStartURL == -1)
					break; // No more <http...

				// Find closing space.

				int iEndURL = sPlainText.indexOf("]", iStartURL + 1);

				assertTrue(iEndURL > -1, "### ERROR: Can't find closing bracket for URL.");

				sURL = sPlainText.substring(iStartURL + 1, iEndURL);

				urlList.add(sURL);

				iPos = iEndURL;
			}

			// //////////////////////////////////
			// Search for http surrounded by spaces ( " http... " )

			iPos = 0;

			while (iPos != -1) {

				String spacePlusHttp = " http";

				int iStartURL = sPlainText.indexOf(spacePlusHttp, iPos);

				if (iStartURL == -1)
					break; // No more <http...

				// Find closing space.

				int iEndURL = sPlainText.indexOf(" ", iStartURL + 1);

				assertTrue(iEndURL > -1, "### ERROR: Can't find closing space for URL.");

				sURL = sPlainText.substring(iStartURL + 1, iEndURL);

				urlList.add(sURL);

				iPos = iEndURL;
			}

		} else {

			fail("### ERROR: Unable to process content type for email.");
		}

		// return sURL;

		return urlList;

	}

	public HashMap<String, String> findTrashEmailSubjectAndBodyContains(String sUser, String sSubjectContains,
			String sBodyContains, int minutesAgo, boolean bFindSeen, boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		// /////////////////////////
		// Use GmailBot to parse email;

		// GmailBot gBot = gbotConnect();

		this.connect(MailSessionType.IMAP, userId, password);

		// Poll for email.

		List<HashMap<String, String>> messageList = null;

		int mailTries = IHGUtil.getTestMailLoop(); // Can be set
		// from command
		// lime:
		// -Dtest.mail.loop=10

		Log4jUtil.log("### DEBUG: MAIL TRIES: " + mailTries);

		int iSleep = 10000;

		Log4jUtil.log("### DEBUG: MAIL SLEEP (ms): " + iSleep);

		// String sDate = "", sFrom = "", sSubject = "", sContentType = ""
		String sTo = "", sContent = "", sPlainText = "", sHTML = "";

		boolean bBodyContentsFound = false;

		HashMap<String, String> retMessage = null;

		for (int safetyValve = 0; safetyValve < mailTries; safetyValve++) {

			Log4jUtil.log("Sleeping waiting for email man ...");

			Thread.sleep(iSleep); // TODO - get from properties file

			messageList = findTrashMessageTo(sUser, sSubjectContains, null, minutesAgo, bFindSeen, bMarkSeen);

			if (messageList == null)
				continue;

			for (HashMap<String, String> message : messageList) {

				Log4jUtil.log("########## SUBJECT: " + message.get("SUBJECT"));

				if (!message.get("PLAIN_TEXT").contains(sBodyContains)

						&& !message.get("HTML").contains(sBodyContains)) {

					continue;
				}

				retMessage = message;

				bBodyContentsFound = true;

				// TODO verify is recent - may have to account for time zones!
				// sDate = message.get("DATE");

				// sFrom = message.get("FROM"); // TODO verify expected FROM
				// address
				sTo = message.get("TO"); // Format is (sans []):
				// ["com.test.igh+FOO@gmail.com"
				// <com.test.igh+FOO@gmail.com>]
				// sSubject = message.get("SUBJECT");
				sContent = message.get("CONTENT");
				sPlainText = message.get("PLAIN_TEXT");
				sHTML = message.get("HTML");
				// sContentType = message.get("CONTENT-TYPE");

				// TODO - mark messages as seen (via bot?).
				break;
			}

			if (bBodyContentsFound)
				break;
		}

		close();

		if (messageList == null) {

			throw new Exception("### Message list null - no messages found in trash folder for: " + sUser);
		}

		if (messageList.size() <= 0) {

			throw new Exception("### No messages found in trash folder for: " + sUser + ", retries: " + mailTries
					+ ", sleep(ms): " + iSleep);
		}

		// Parse message list.

		// HashMap<String, String> message = messageList.get(0);

		if (!bBodyContentsFound) {

			throw new Exception("### ERROR: Message with matching body contents not found.");
		}

		if (!sTo.toLowerCase().contains(sUser.toLowerCase())) {

			throw new Exception("### ERROR: Recipient retrieved doesn't match. [" + sTo + "] vs. [" + sUser + "] ");
		}

		Log4jUtil.log("DEBUG: CONTENT: " + sContent);
		Log4jUtil.log("DEBUG: PLAIN TEXT: " + sPlainText);
		Log4jUtil.log("DEBUG: HTML: " + sHTML);

		return retMessage;

	}

	public void markAllTrashMailsAsSeen() throws Exception {
		IHGUtil.PrintMethodName();

		this.connect(MailSessionType.IMAP, userId, password);

		// For test account, all emails are sent to trash.
		Folder inbox = store.getFolder(GMAIL_FOLDER_TRASH);
		inbox.open(Folder.READ_WRITE);

		// Only search SEEN = false
		FlagTerm ft = new FlagTerm(new Flags(Flag.SEEN), false);

		Message messages[] = inbox.search(ft);

		for (Message message : messages) {
			message.setFlag(Flag.SEEN, true);
		}

		inbox.close(false);
	}

	public String findTrashEmailSecurityCode(String sUser, String sSubjectContains, int minutesAgo, boolean bFindSeen,
			boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		// Set<String> urlList = new HashSet<String>();

		// /////////////////////////
		// Use GmailBot to parse email;

		// GmailBot gBot = gbotConnect();

		this.connect(MailSessionType.IMAP, userId, password);

		// Poll for email.

		List<HashMap<String, String>> messageList = null;

		int mailTries = IHGUtil.getTestMailLoop(); // Can be set
		// from command
		// lime:
		// -Dtest.mail.loop=10

		Log4jUtil.log("### DEBUG: MAIL TRIES: " + mailTries);

		int iSleep = 10000;

		Log4jUtil.log("### DEBUG: MAIL SLEEP (ms): " + iSleep);

		// String sDate = "", sFrom = "", sSubject = "", sContentType = ""
		String sTo = "", sContent = "", sPlainText = "", sHTML = "";

		boolean bSubjectFound = false;

		for (int safetyValve = 0; safetyValve < mailTries; safetyValve++) {

			Log4jUtil.log("Sleeping waiting for email man ...");

			Thread.sleep(iSleep); // TODO - get from properties file

			messageList = findTrashMessageTo(sUser, sSubjectContains, null, minutesAgo, bFindSeen, bMarkSeen);

			if (messageList == null)
				continue;

			for (HashMap<String, String> message : messageList) {

				Log4jUtil.log("########## SUBJECT: " + message.get("SUBJECT"));

				/*
				 * if (!message .get("SUBJECT") .contains(sSubjectContains)) continue;
				 */

				bSubjectFound = true;

				// TODO - mark messages as seen (via bot?).

				// TODO verify is recent - may have to account for time zones!
				// sDate = message.get("DATE");

				// sFrom = message.get("FROM"); // TODO verify expected FROM
				// address
				sTo = message.get("TO"); // Format is (sans []):
				// ["com.test.igh+FOO@gmail.com"
				// <com.test.igh+FOO@gmail.com>]
				// sSubject = message.get("SUBJECT"); // TODO verify expected
				// subject
				sContent = message.get("CONTENT");
				sPlainText = message.get("PLAIN_TEXT");
				sHTML = message.get("HTML");
				// sContentType = message.get("CONTENT-TYPE");

				break;
			}

			if (bSubjectFound)
				break;
		}

		this.close();

		if (messageList == null) {

			throw new Exception("### Message list null - no messages found in trash folder for: " + sUser);
		}

		if (messageList.size() <= 0) {

			throw new Exception("### No messages found in trash folder for: " + sUser + ", retries: " + mailTries
					+ ", sleep(ms): " + iSleep);
		}

		// Parse message list.

		// HashMap<String, String> message = messageList.get(0);

		if (!bSubjectFound) {
			throw new Exception("### ERROR: Message with matching subject not found.");
		}

		if (!sTo.toLowerCase().contains(sUser.toLowerCase())) {
			throw new Exception("### ERROR: Recipient retrieved doesn't match. [" + sTo + "] vs. [" + sUser + "] ");
		}

		Log4jUtil.log("DEBUG: CONTENT: " + sContent);
		Log4jUtil.log("DEBUG: PLAIN TEXT: " + sPlainText);
		Log4jUtil.log("DEBUG: HTML: " + sHTML);

		// Find URL

		String secCode = "";
		Pattern pattern = Pattern.compile("Security Code:\\s<.*>(\\w+)");
		Matcher matcher = pattern.matcher(sPlainText);

		if (matcher.find()) {
			Log4jUtil.log("code found");
			secCode = matcher.group(1);
		} else
			Log4jUtil.log("code Not found ");
		return secCode;

	}

	public String findTrashEmailID(String sUser, String sSubjectContains, int minutesAgo, boolean bFindSeen,
			boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		// Set<String> urlList = new HashSet<String>();

		// /////////////////////////
		// Use GmailBot to parse email;

		// GmailBot gBot = gbotConnect();

		this.connect(MailSessionType.IMAP, userId, password);

		// Poll for email.

		List<HashMap<String, String>> messageList = null;

		int mailTries = IHGUtil.getTestMailLoop(); // Can be set
		// from command
		// lime:
		// -Dtest.mail.loop=10

		Log4jUtil.log("### DEBUG: MAIL TRIES: " + mailTries);

		int iSleep = 10000;

		Log4jUtil.log("### DEBUG: MAIL SLEEP (ms): " + iSleep);

		// String sDate = "", sFrom = "", sSubject = "", sContentType = ""
		String sTo = "", sContent = "", sPlainText = "", sHTML = "";

		boolean bSubjectFound = false;

		for (int safetyValve = 0; safetyValve < mailTries; safetyValve++) {

			Log4jUtil.log("Sleeping waiting for email man ...");

			Thread.sleep(iSleep); // TODO - get from properties file

			messageList = findTrashMessageTo(sUser, sSubjectContains, null, minutesAgo, bFindSeen, bMarkSeen);

			if (messageList == null)
				continue;

			for (HashMap<String, String> message : messageList) {

				Log4jUtil.log("########## SUBJECT: " + message.get("SUBJECT"));

				/*
				 * if (!message .get("SUBJECT") .contains(sSubjectContains)) continue;
				 */

				bSubjectFound = true;

				// TODO - mark messages as seen (via bot?).

				// TODO verify is recent - may have to account for time zones!
				// sDate = message.get("DATE");

				// sFrom = message.get("FROM"); // TODO verify expected FROM
				// address
				sTo = message.get("TO"); // Format is (sans []):
				// ["com.test.igh+FOO@gmail.com"
				// <com.test.igh+FOO@gmail.com>]
				// sSubject = message.get("SUBJECT"); // TODO verify expected
				// subject
				sContent = message.get("CONTENT");
				sPlainText = message.get("PLAIN_TEXT");
				sHTML = message.get("HTML");
				// sContentType = message.get("CONTENT-TYPE");

				break;
			}

			if (bSubjectFound)
				break;
		}

		this.close();

		if (messageList == null) {

			throw new Exception("### Message list null - no messages found in trash folder for: " + sUser);
		}

		if (messageList.size() <= 0) {

			throw new Exception("### No messages found in trash folder for: " + sUser + ", retries: " + mailTries
					+ ", sleep(ms): " + iSleep);
		}

		// Parse message list.

		// HashMap<String, String> message = messageList.get(0);

		if (!bSubjectFound) {
			throw new Exception("### ERROR: Message with matching subject not found.");
		}

		if (!sTo.toLowerCase().contains(sUser.toLowerCase())) {
			throw new Exception("### ERROR: Recipient retrieved doesn't match. [" + sTo + "] vs. [" + sUser + "] ");
		}

		Log4jUtil.log("DEBUG: CONTENT: " + sContent);
		Log4jUtil.log("DEBUG: PLAIN TEXT: " + sPlainText);
		Log4jUtil.log("DEBUG: HTML: " + sHTML);

		// Find URL

		String secCode = "";

		Pattern pattern = Pattern.compile("Your user ID is:\\s<.*>(.+)</.*>"); // Should be "Your user ID
																				// is:\\s<.*>([^<]+)</.*>" but not
																				// working
		Matcher matcher = pattern.matcher(sPlainText);

		if (matcher.find()) {
			Log4jUtil.log("code found " + matcher.group(1));
			secCode = matcher.group(1).substring(0, matcher.group(1).indexOf("<"));
		} else
			Log4jUtil.log("code Not found ");
		return secCode;

	}

	public String findInboxEmailLink(String sUser, String sPassword, String sSubjectContains, String sLinkContains,
			int minutesAgo, boolean bFindSeen, boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		Set<String> urlList = findInboxEmailLinks(sUser, sPassword, sSubjectContains, minutesAgo, bFindSeen, bMarkSeen);

		String sURL = "";

		for (String url : urlList) {

			String testURL = url;
			testURL = trimFromString(testURL, " ");
			testURL = trimFromString(testURL, "<");
			testURL = trimIfLastChar(testURL, "."); // Can't trim from middle - that would be valid.
			Log4jUtil.log("DEBUG: URL [...]: " + testURL);

			if (testURL.contains(sLinkContains)) { // Find link with matching content.
				sURL = testURL;
				// break;
			}
		}

		Log4jUtil.log("DEBUG: URL: " + sURL);
		return sURL;
	}

	public Set<String> findInboxEmailLinks(String sUser, String sPassword, String sSubjectContains, int minutesAgo,
			boolean bFindSeen, boolean bMarkSeen) throws Exception {

		IHGUtil.PrintMethodName();

		Set<String> urlList = new HashSet<String>();

		Log4jUtil.log("Use GmailBot to parse email:--GmailBot gBot = gbotConnect()");
		this.connect(MailSessionType.IMAP, sUser, sPassword);

		Log4jUtil.log("Poll for email.");
		List<HashMap<String, String>> messageList = null;
		int mailTries = IHGUtil.getTestMailLoop(); // Can be set from command lime:-Dtest.mail.loop=10
		Log4jUtil.log("### DEBUG: MAIL TRIES: " + mailTries);
		int iSleep = 10000;
		Log4jUtil.log("### DEBUG: MAIL SLEEP (ms): " + iSleep);

		// String sDate = "", sFrom = "", sSubject = "", sTo= ""
		String sContentType = "", sContent = "", sPlainText = "", sHTML = "";
		boolean bSubjectFound = false;

		for (int safetyValve = 0; safetyValve < mailTries; safetyValve++) {

			Log4jUtil.log("Sleeping waiting for email man ...");

			Thread.sleep(iSleep); // TODO - get from properties file

			messageList = findInboxMessageTo(sUser, sSubjectContains, null, minutesAgo, bFindSeen, bMarkSeen);

			if (messageList == null)
				continue;

			for (HashMap<String, String> message : messageList) {

				Log4jUtil.log("########## SUBJECT: " + message.get("SUBJECT"));

				/*
				 * if (!message .get("SUBJECT") .contains(sSubjectContains)) continue;
				 */

				bSubjectFound = true;

				// TODO - mark messages as seen (via bot?).

				// TODO verify is recent - may have to account for time zones!
				// sDate = message.get("DATE");
				sContent = message.get("CONTENT");
				sPlainText = message.get("PLAIN_TEXT");
				sHTML = message.get("HTML");
				sContentType = message.get("CONTENT-TYPE");

				break;
			}

			if (bSubjectFound)
				break;
		}

		this.close();

		if (messageList == null) {

			throw new Exception("### Message list null - no messages found in Inbox folder for: " + sUser);
		}

		if (messageList.size() <= 0) {

			throw new Exception("### No messages found in Inbox folder for: " + sUser + ", retries: " + mailTries
					+ ", sleep(ms): " + iSleep);
		}

		if (!bSubjectFound) {
			throw new Exception("### ERROR: Message with matching subject not found.");
		}
		Log4jUtil.log("DEBUG: CONTENT: " + sContent);
		Log4jUtil.log("DEBUG: PLAIN TEXT: " + sPlainText);
		Log4jUtil.log("DEBUG: HTML: " + sHTML);
		Log4jUtil.log("DEBUG: ContentType: " + sContentType);
		// Find URL

		String sURL = "";

		if (sContentType.matches("MULTIPART")) {
			int iStartURL = sPlainText.indexOf("https://");

			if (iStartURL == -1) {
				iStartURL = sPlainText.indexOf("http://");
			}

			assertTrue(iStartURL > -1, "### ERROR: Can't find https or http link in email.");

			int iEndURL = sPlainText.indexOf("]", iStartURL);

			sURL = sPlainText.substring(iStartURL, iEndURL);

			// TODO - update to find multiples

			System.out.println("URL+++++++====MULTIPART====+++++:-  " + sURL);

			urlList.add(sURL);

		} else if (sContentType.matches("STRING")) {
			int iPos = 0;
			while (iPos != -1) {
				// Search for: <a href=qa/login>, <a href="qa/login">, <a
				// href=http...>, <a href="http...">

				String aHref = "href=";
				// TODO - port this to multipart handler above
				int iStartHREF = sPlainText.indexOf(aHref, iPos);
				if (iStartHREF == -1)
					break; // No more <a href...
				iStartHREF += aHref.length();
				int iEndHREF = sPlainText.indexOf(">", iStartHREF);
				String sHREF = sPlainText.substring(iStartHREF, iEndHREF);
				Log4jUtil.log("#### HREF: " + sHREF);
				int iStartURL = sHREF.indexOf("\"");
				if (iStartURL == -1) {
					sURL = sHREF;
				} else {
					int iEndURL = sHREF.indexOf("\"", iStartURL + 1);
					assertTrue(iEndURL > -1,
							"### ERROR: Can't find closing quote in HREF: [" + sHREF.substring(iStartURL) + "]");
					sURL = sHREF.substring(iStartURL + 1, iEndURL);
				}

				int iStartSingle = sURL.indexOf("\'");

				if (iStartSingle != -1) {
					int iEndSingle = sURL.indexOf("\'", iStartSingle + 1);
					assertTrue(iEndSingle > -1, "### ERROR: Can't find closing single quote in URL.");
					sURL = sURL.substring(iStartSingle + 1, iEndSingle);
				}
				System.out.println("URL+++++++====<a href=qa/login>====+++++:-  " + sURL);
				urlList.add(sURL);

				iPos = iEndHREF;
			}

			iPos = 0;

			while (iPos != -1) {

				String spacePlusHttp = "[http";

				int iStartURL = sPlainText.indexOf(spacePlusHttp, iPos);

				if (iStartURL == -1)
					break; // No more <http...
				// Find closing space.

				int iEndURL = sPlainText.indexOf("]", iStartURL + 1);
				assertTrue(iEndURL > -1, "### ERROR: Can't find closing bracket for URL.");

				sURL = sPlainText.substring(iStartURL + 1, iEndURL);
				System.out.println("URL+++++++====// No more <http... ====+++++:-  " + sURL);
				urlList.add(sURL);

				iPos = iEndURL;
			}

			iPos = 0;

			while (iPos != -1) {
				String spacePlusHttp = " http";
				int iStartURL = sPlainText.indexOf(spacePlusHttp, iPos);
				if (iStartURL == -1)
					break; // No more <http...

				// Find closing space.
				int iEndURL = sPlainText.indexOf(" ", iStartURL + 1);
				assertTrue(iEndURL > -1, "### ERROR: Can't find closing space for URL.");
				sURL = sPlainText.substring(iStartURL + 1, iEndURL);
				System.out.println("URL+++++++====Search for http surrounded by spaces ====+++++:-  " + sURL);
				urlList.add(sURL);

				iPos = iEndURL;
			}

		} else {

			fail("### ERROR: Unable to process content type for email.");
		}

		// return sURL;

		return urlList;

	}

	public List<HashMap<String, String>> findInboxMessageTo(String recipient, String sSubject, String sBody,
			int minutesAgo, boolean bFindSeen, boolean bMarkSeen) throws MessagingException, IOException {

		IHGUtil.PrintMethodName();
		List<HashMap<String, String>> messageList = null;

		messageList = findMessageTo(recipient, GMAIL_FOLDER_INBOX, sSubject, sBody, minutesAgo, bFindSeen, bMarkSeen);
		if (messageList == null) {
			messageList = findMessageTo(recipient, GMAIL_FOLDER_SPAM, sSubject, sBody, minutesAgo, bFindSeen,
					bMarkSeen);
		}
		return messageList;
	}

	public void deleteMessagesFromInbox(String userName, String password, String subjectToDelete) {
		IHGUtil.PrintMethodName();
		int j = 0;
		try {

			Log4jUtil.log("#############Connect to the Gmail########");
			this.connect(MailSessionType.IMAP, userName, password);

			Log4jUtil.log("#######Open the inbox folder in READ_WRITE mode.#######");
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			Log4jUtil.log("#######Add messages into array and filter with subject.#######");
			Message[] arrayMessages = folderInbox.getMessages();
			Date today = new Date();
			int i = arrayMessages.length - 1;
			Message message = arrayMessages[i];
			do {
				message = arrayMessages[i];
				Date receivedDate = message.getReceivedDate();

				if (receivedDate.after(today)) {
					break;
				}
				String subject = message.getSubject();
				if (subject.contains(subjectToDelete)) {
					Log4jUtil.log(
							"#######if one needs to be delete, mark it as deleted by invoking the below method.#######");
					message.setFlag(Flags.Flag.DELETED, true);
					System.out.println("Marked DELETE for message: " + subject);
					j = j + 1;
				}
				i--;
			} while (message.getReceivedDate().equals(today) && i > 0);

			Log4jUtil.log(
					"######## call the expunge() method on the Folder object, or close the folder with expunge set to trueto delete the messages marked Delete########");
			folderInbox.expunge();

			folderInbox.close(false);

			store.close();
			if (j == 0) {
				Log4jUtil.log("######## No messages with given subject to delete########");
			}
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
	}

	public void deleteAllMessagesFromInbox(String userName, String password) {
		IHGUtil.PrintMethodName();
		int j = 0;
		try {

			Log4jUtil.log("#############Connect to the Gmail########");
			this.connect(MailSessionType.IMAP, userName, password);

			Log4jUtil.log("#######Open the inbox folder in READ_WRITE mode.#######");
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			Log4jUtil.log("#######Add messages into array and filter with subject.#######");
			Message[] arrayMessages = folderInbox.getMessages();
			for (int i = 0; i < arrayMessages.length; i++) {
				Message message = arrayMessages[i];
				String subject = message.getSubject();
				/*
				 * if (subject.contains(subjectToDelete)) {
				 */
				Log4jUtil.log(
						"#######if one needs to be delete, mark it as deleted by invoking the below method.#######");
				message.setFlag(Flags.Flag.DELETED, true);
				System.out.println("Marked DELETE for message: " + subject);
				j = j + 1;
			}
			// }
			Log4jUtil.log(
					"######## call the expunge() method on the Folder object, or close the folder with expunge set to trueto delete the messages marked Delete########");
			folderInbox.expunge();

			folderInbox.close(false);

			store.close();
			if (j == 0) {
				Log4jUtil.log("######## No messages with given subject to delete########");
			}
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
	}

}
