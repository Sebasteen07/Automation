//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class P2PUnseenMessageList {
	public void verifyUnseenMessage(SendDirectMessage testData) throws ParserConfigurationException, SAXException, IOException, TransformerException, InterruptedException {
		Log4jUtil.log("Step 6 : Check for new Unseen Message ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		
		Log4jUtil.log("Step 7 : Verify UnseenMessage and get its UID ");
		String msgUid=RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath,testData.Subject);
		String getMessageBody = testData.unseenMessageBody;
		getMessageBody = getMessageBody+"/"+msgUid;
		
		Log4jUtil.log("Step 8 : Get UnseenMessage Body ");
		Thread.sleep(10000);
		RestUtils.setupHttpGetRequest(getMessageBody, testData.ResponsePath);
		
		Log4jUtil.log("Step 9 : Verify UnseenMessage Body Cotent");
		String attachFile="";
		if(testData.AttachmentType!=null && !testData.AttachmentType.isEmpty() && !testData.AttachmentType.equalsIgnoreCase("none")) { 
			String workingDir = System.getProperty("user.dir");
			String filepathLocation=null;
			if(testData.AttachmentType.equalsIgnoreCase("pdf")) {
				filepathLocation = testData.PDFAttachmentFileLocation;
			}
			if(testData.AttachmentType.equalsIgnoreCase("xml")) {
				filepathLocation = testData.XMLAttachmentFileLocation;
			}
			workingDir = workingDir + filepathLocation;
			attachFile = ExternalFileReader.readFromFile(workingDir);
		}
		RestUtils.verifyUnseenMessageBody(testData.ResponsePath, testData.Subject, testData.FileName,testData.ToEmalID,testData.FromEmalID,testData.MessageBody,attachFile);
		
		Log4jUtil.log("Step 10 : Create message status update URL");
		String messageUpdateURL = testData.messageStatusUpdate +"/"+msgUid+"/status/"+testData.messageStatusToUpdate;
		Log4jUtil.log("message Status : "+messageUpdateURL);
		
		Log4jUtil.log("Step 11 : Post  message to Update message Status to READ");
		RestUtils.setupHttpPostRequest(messageUpdateURL," " , testData.ResponsePath);
		
		Log4jUtil.log("Step 12 : Verify above message is removed from UnseenMessageList ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		
		Log4jUtil.log("Step 13: Post message to Update message Status as NEW");
		String messageUpdateURLNew = messageUpdateURL.replaceAll("READ", "NEW");
		RestUtils.setupHttpPostRequest(messageUpdateURLNew," " , testData.ResponsePath);
		
		Log4jUtil.log("Step 14: Get unseen Message Header");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		
		Log4jUtil.log("Step 15: Verify UnseenMessage and Match msgUID");
		String msgUidNEW=RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath,testData.Subject);
		assertEquals(msgUid, msgUidNEW);
	}
	
	public void ExtractErrorMessage(String xmlFileName,String PatternToMatch,String invalidMessage) throws IOException {
		
		String responseInvalidPratice = ExternalFileReader.readFromFile(xmlFileName);
		Log4jUtil.log("response "+responseInvalidPratice);
		Log4jUtil.log("PatternToMatch "+PatternToMatch);
		Log4jUtil.log("invalidMessage "+invalidMessage);
		Pattern TAG_REGEX = Pattern.compile(PatternToMatch);
	 	final List<String> tagValues = new ArrayList<String>();
	    final Matcher matcher = TAG_REGEX.matcher(responseInvalidPratice);
	    Log4jUtil.log("response "+responseInvalidPratice);	
	    while (matcher.find()) {
	        tagValues.add(matcher.group(1));
	    }
	    Log4jUtil.log("response "+responseInvalidPratice);
	    Log4jUtil.log("Error Message "+tagValues.get(0));
	 	if(tagValues.get(0).contains(invalidMessage)) {
	 		Log4jUtil.log("Error Response Matched");
	 		assertTrue(true, "Error Response Matched");
	 	}
	}
}
