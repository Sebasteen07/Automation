package com.intuit.ihg.common.utils.mail;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test
public class GmailTest {

	public void testGmailInboxNewMessageCount() throws MessagingException {
		Gmail gmail = new Gmail("ihgqa.dev3@gmail.com", "intuit123");
		int count = gmail.getInboxNewMessageCount();
		System.out.println("Message Count: " + count);
		assertTrue(count >= 0, "There was an issue retrieving message count from Gmail.");
	}

	public void testGmailRetrieveInboxUnSeenMessages() throws MessagingException, IOException {
		Gmail gmail = new Gmail("ihgqa.dev3@gmail.com", "intuit123");
		gmail.findInNewMessages("ihgqa.dev3@gmail.com", ""); // Will just grab all unread/unseen messages

	}

	public void testGmailRetrieveInboxUnSeenMessagesWithStartDate() throws MessagingException, IOException {
		Gmail gmail = new Gmail("ihgqa.dev3@gmail.com", "intuit123");
		gmail.findInNewMessages("ihgqa.dev3@gmail.com", "", new Date()); // Will just grab all unread/unseen messages

	}
}
