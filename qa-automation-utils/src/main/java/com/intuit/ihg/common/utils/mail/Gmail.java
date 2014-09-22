package com.intuit.ihg.common.utils.mail;

/**
 * References:
 * http://alvinalexander.com/scala/scala-imap-client-searchterm-andterm-fromstringterm-sentdateterm-flagterm
 * http://alvinalexander.com/scala/scala-imap-client-searchterm-andterm-fromstringterm-sentdateterm-flagterm
 */

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
//	private final static String GMAIL_IMAP_PORT = "993";
	
	private boolean connected = false;
	
	private Session session;
	private Store store;
	private Folder folder;
	
	// Setup some flags that will be used in searching
	Flags seen = new Flags(Flags.Flag.SEEN);
	FlagTerm unseenFlag = new FlagTerm(seen, false);

	public Gmail(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}
	
	/**
	 * Initiates connection to GMail given supplied user id and password passed in the constructor
	 * @throws MessagingException
	 */
	public void connect() throws MessagingException {
		session = Session.getDefaultInstance(System.getProperties());
		store = session.getStore(GMAIL_STORE_PROTOCOL);
		store.connect(GMAIL_IMAP_HOST, id, pwd);
		
		connected = true;
	}
	
	/**
	 * Retrieves the number of unread/unseen messages in the Inbox. Useful to execute before
	 * and after a test to see if any messages have arrived before trying to find a specific message.
	 * @return the count of unread/unseen messages
	 * @throws MessagingException
	 */
	public int getInboxNewMessageCount() throws MessagingException {
		if (!connected) {
			connect();
		}
		
		// Set filters (unseen/unread messages)
		SearchTerm[] terms = { unseenFlag };
		
		Message[] messages = getMessages("inbox", terms);		
		return messages.length;
	}
	
	
	/**
	 * Searches the inbox for unseen messages returns results filtered by supplied parameters.
	 * Please note that the start date doesn't seem to be as reliable as expected. It is STRONGLY
	 * advised to do a check against against the actual sent date of the message.
	 * @param recipient email address in To field
	 * @param subject substring of subject that can be searched 
	 * @param startDate a start date for filtering messages
	 * @return an array of messages
	 * @throws MessagingException
	 * @throws IOException
	 */
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
			System.out.println("Message retrieved from Gmail: Subject[" + m.getSubject() + "], SentDate[" + m.getSentDate() +
					"], ContentType[" + m.getContentType() + "], Class[" + m.getContent().getClass() + "]" );
		}
		
		return messages;
	}
	
	/**
	 * Searches the inbox for unseen messages returns results filtered by supplied parameters.
	 * @param recipient email address in To field
	 * @param subject substring of subject that can be searched
	 * @return an array of messages
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Message[] findInNewMessages(String recipient, String subject) throws MessagingException, IOException {
		return findInNewMessages(recipient, subject, null);
	}
	
	/**
	 * Retrieve all messages from the supplied folder that match the given search criteria.
	 * @param searchFolder the folder that you will be performing operations against.
	 * @param terms an array of search terms to filter on. This will be passed into the AndTerms constructor.
	 * @return the array of messages
	 * @throws MessagingException
	 */
	private Message[] getMessages(String searchFolder, SearchTerm[] terms) throws MessagingException {
		getFolder(searchFolder, false);
		return folder.search(new AndTerm(terms));
	}
	
	/**
	 * Will open the folder with an operation mode of READ/WRITE or READ-ONLY.
	 * @param searchFolder typically will be 'inbox'
	 * @param readWrite open the folder in READ/WRITE (true) or READ-ONLY (false) mode
	 * @return the folder
	 * @throws MessagingException
	 */
	private void getFolder(String searchFolder, boolean readWrite) throws MessagingException {
		folder = store.getFolder(searchFolder);
		if (readWrite) {
			folder.open(Folder.READ_WRITE);
		} else {
			folder.open(Folder.READ_ONLY);
		}
	}
}
