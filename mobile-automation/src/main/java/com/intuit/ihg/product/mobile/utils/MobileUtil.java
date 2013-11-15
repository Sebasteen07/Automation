package com.intuit.ihg.product.mobile.utils;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.IHGUtil.*;
import com.intuit.ihg.common.utils.mail.GmailBot;

import com.intuit.ihg.common.utils.IHGUtil;

public class MobileUtil extends IHGUtil {

	public MobileUtil(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * REMOVE the emails from the inbox with the given subject
	 * @param userId
	 * @param password
	 * @param sSubject
	 * @throws Exception
	 */
	public void emailMessageRemover(String userId, String password,
			String sSubject) throws Exception {

		GmailBot gbot = new GmailBot();
		log("subject of mail is " + sSubject);
		gbot.deleteMessagesFromInbox(userId, password, sSubject);

	}
   
	/**
	 * REMOVE ALL EMAILS FROM INBOX
	 * @param userId
	 * @param password
	 * @param sSubject
	 * @throws Exception
	 */
	public void cleanInbox(String userId, String password, String sSubject)
			throws Exception {

		GmailBot gbot = new GmailBot();
		log("subject of mail is " + sSubject);
		gbot.deleteAllMessagesFromInbox(userId, password, sSubject);

	}

	/**
	 * GET SECURITY CODE FOR FORGOT-PASSWORD TESTCASE
	 * @param userName
	 * @param emailPassword
	 * @param Subject
	 * @return
	 * @throws Exception
	 */
	public String getSecurityCodeFromGmail(String userName,
			String emailPassword, String Subject) throws Exception {
		GmailBot gBot = new GmailBot();
		gBot.setUserId(userName);
		gBot.setPassword(emailPassword);
		String sCode = gBot.findTrashEmailSecurityCode(userName.trim(),
				Subject, // TODO - Subject: get from properties file
				20, false, true); // MUST MARK SEEN - so don't get wrong invalid
									// link on next pass.

		BaseTestNGWebDriver.verifyTrue(sCode.length() <= 0,
				"### Code not found in email");
		System.out.println("### Code: " + sCode);
		return sCode;
	}

	/**
	 * GET USER ID FOR FORGOT-USERID TESTCASE
	 * @param userName
	 * @param emailPassword
	 * @param Subject
	 * @return
	 * @throws Exception
	 */
	public String getUserEmailFromGmail(String userName, String emailPassword,
			String Subject) throws Exception {
		GmailBot gBot = new GmailBot();
		gBot.setUserId(userName);
		gBot.setPassword(emailPassword);
		String sCode = gBot.findTrashEmailID(userName.trim(), Subject, // TODO -// Subject:get from properties file
			         		20, false, true); // MUST MARK SEEN - so don't get wrong invalid
									// link on next pass.
		BaseTestNGWebDriver.verifyTrue(sCode.length() <= 0,
				"### Code not found in email");
		System.out.println("### Code: " + sCode);
		return sCode;
	}

}
