package com.intuit.ihg.common.utils.mail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class CheckEmail {

    public static boolean validateForgotUserID(
			Gmail gmail, 
			Date startEmailSearchDate, 
			String recipientEmail, 
			String subjectSubString, 
			String userName) throws InterruptedException, MessagingException, IOException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		
		boolean foundEmail = false;
		for(int i = 0; i < 6; i++) {
			int msgCount = 0;
			Log4jUtil.log("Checking Gmail now for message.");
			Message[] messages = gmail.findInNewMessages(recipientEmail, subjectSubString, startEmailSearchDate);
			if (messages.length > 0) {
				for(Message m : messages) {
					String emailContent = (String) m.getContent(); // Should be of content type text/html
					if(emailContent.contains(userName) && startEmailSearchDate.compareTo(m.getReceivedDate()) < 0) {
						foundEmail = true;
						return foundEmail;
					}
					
					msgCount++;
					Log4jUtil.log("Checking email " + msgCount + " (received at " + 
							dateFormat.format(m.getReceivedDate()) + ") of " + messages.length);
				}
			} else {
				Thread.sleep(10000);
			}		
		}
		
		return foundEmail;
	}
}
