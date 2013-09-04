package com.intuit.ihg.common.utils.mail;

/**
 * References: 
 *  http://www.mindfiresolutions.com/Retrive-email-from-Yahoo--Gmail-server-using-IMAP-in-JAVA-1244.php
 *  http://stackoverflow.com/questions/61176/getting-mail-from-gmail-into-java-application-using-imap
 *  http://stackoverflow.com/questions/5366767/retrieve-unread-emails-from-gmail-javamail-api-imap
 *  http://stackoverflow.com/questions/2561784/how-to-get-recipients-addresses-as-string-in-javamail
 *  http://stackoverflow.com/questions/7304860/cant-get-emails-in-drafts-spam-and-trash-folders-from-google-using-javax-mail
 * 	http://stackoverflow.com/questions/483048/access-gmail-from-java
 * 	http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 */

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface MailBot {
	    
    public Session setup();
    
    public Session setup(MailSessionType mt);
    
    public Store connect(MailSessionType mt, String sUser, String sPassword) throws MessagingException;
    
    public List<HashMap<String, String>> findMessageTo(
            String recipient,
            String folder,
            String sSubjet,
            String sBody,
            int minutesAgo,
            boolean bFindSeen,
            boolean bMarkSeen) throws MessagingException, IOException;

}
