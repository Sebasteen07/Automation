//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils.mail;

import java.io.IOException;
import java.util.Date;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;


public class Gmail {

	private String id;
	private String pwd;
	private final static String GMAIL_IMAP_HOST = "imap.gmail.com";
	private final static String GMAIL_STORE_PROTOCOL = "imaps";
	// private final static String GMAIL_IMAP_PORT = "993";

	private boolean connected = false;

	private Session session;
	private Store store;
	private Folder folder;

	Flags seen = new Flags(Flags.Flag.SEEN);
	FlagTerm unseenFlag = new FlagTerm(seen, false);

	public Gmail(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}

	public void connect() throws MessagingException {
		session = Session.getDefaultInstance(System.getProperties());
		store = session.getStore(GMAIL_STORE_PROTOCOL);
		store.connect(GMAIL_IMAP_HOST, id, pwd);

		connected = true;
	}

	public int getInboxNewMessageCount() throws MessagingException {
		if (!connected) {
			connect();
		}

		SearchTerm[] terms = {unseenFlag};

		Message[] messages = getMessages("inbox", terms);
		return messages.length;
	}

	public Message[] findInNewMessages(String recipient, String subject, Date startDate) throws MessagingException, IOException {
		if (!connected) {
			connect();
		}

		// Create search terms for recipient and subject
		RecipientStringTerm toTerm = new RecipientStringTerm(RecipientType.TO, recipient);
		SubjectTerm subjectTerm = new SubjectTerm(subject);

		SearchTerm[] terms;
		if (startDate != null) {
			DateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.GE, startDate);
			terms = new SearchTerm[4];
			terms[0] = unseenFlag;
			terms[1] = toTerm;
			terms[2] = dateTerm;
			terms[3] = subjectTerm;
		} else {
			terms = new SearchTerm[3];
			terms[0] = unseenFlag;
			terms[1] = toTerm;
			terms[2] = subjectTerm;
		}

		Message[] messages = getMessages("inbox", terms);

		for (Message m : messages) {
			System.out.println("Message retrieved from Gmail: Subject[" + m.getSubject() + "], SentDate[" + m.getSentDate() + "], ContentType[" + m.getContentType()
					+ "], Class[" + m.getContent().getClass() + "]");
		}

		return messages;
	}

	public Message[] findInNewMessages(String recipient, String subject) throws MessagingException, IOException {
		return findInNewMessages(recipient, subject, null);
	}

	private Message[] getMessages(String searchFolder, SearchTerm[] terms) throws MessagingException {
		getFolder(searchFolder, false);
		return folder.search(new AndTerm(terms));
	}

	private void getFolder(String searchFolder, boolean readWrite) throws MessagingException {
		folder = store.getFolder(searchFolder);
		if (readWrite) {
			folder.open(Folder.READ_WRITE);
		} else {
			folder.open(Folder.READ_ONLY);
		}
	}
}
