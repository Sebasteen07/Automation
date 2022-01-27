//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.apiehcore.utils;

import static org.testng.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.intuit.dc.framework.tracking.constants.TrackingEnumHolder;
import com.intuit.dc.framework.tracking.entity.Message;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.allscripts.uai.schemas._2010._02._15.AllscriptsMessageEnvelope;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIUtil;
import com.intuit.ihg.product.apiehcore.utils.constants.CCDExportConstants;
import com.intuit.ihg.product.apiehcore.utils.constants.CCDImportConstants;
import com.intuit.ihg.product.apiehcore.utils.constants.DataJobConstant;
import com.intuit.ihg.product.apiehcore.utils.constants.EhcoreAPIConstants;

import com.intuit.ihg.eh.core.dto.CcdExchange;
import com.intuit.ihg.eh.core.dto.DataJob;
import com.intuit.ihg.eh.core.dto.ProcessingResponse;
import com.intuit.ihg.eh.core.dto.ReprocessRequest;
import com.intuit.qhg.hub.schemas.messages.CCDMessageType;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class EhcoreAPIUtil extends IHGUtil {

	private static final String SOAP_BODY_END = "</soap:Body>";
	private static final String SOAP_BODY_START = "<soap:Body>";
	public static int expectedHttpCodeAccepted = HttpURLConnection.HTTP_ACCEPTED;
	public static String schemaSource = null;
	public static int expectedHttpCode = HttpURLConnection.HTTP_OK;
	public static int expectedHttpCode_InternalError = HttpURLConnection.HTTP_INTERNAL_ERROR;
	public static int expectedHttpCode_BadRequest = HttpURLConnection.HTTP_BAD_REQUEST;
	public static int expectedHttpCode_NotFound = HttpURLConnection.HTTP_NOT_FOUND;
	public static int expectedHttpCode_UnSupportedType = HttpURLConnection.HTTP_UNSUPPORTED_TYPE;
	public static String expectedResponseMessage_Datajob = "OK";

	protected WebDriver driver;

	public EhcoreAPIUtil(WebDriver driver) throws Exception {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	public static void setSiteGenFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
	}

	public static void setDefaultFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);
	}

	public static void setFrame(WebDriver driver, String frame) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, frame);
	}

	public static void setConsolidatedInboxFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();

		List<String> frames = new ArrayList<String>();
		frames.add("iframebody");
		frames.add("externalframe");

		IHGUtil.setFrameChain(driver, frames);
	}

	public static boolean verifyTextPresent(WebDriver driver, String value, int waitTime) throws Exception {
		Thread.sleep(waitTime);
		return driver.getPageSource().contains(value);
	}

	public void checkAlert(WebDriver driver) {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			log("Alert detected: {}" + alert.getText());
			alert.accept();
		} catch (Exception e) {
			// exception handling
			log("no alert was present");
		}
	}

	public static DataJob processRequest(String url, String requestType, String requestXml, String expectedResponse)
			throws Exception {

		HttpURLConnection conn = null;
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		String protocol = testData.getProtocol(); // getgetConfigItemValue(UtilConsts.PROTOCOL);

		Log4jUtil.log("Protocol************" + protocol);
		if (protocol.equalsIgnoreCase("https")) {
			conn = setupHttpsConnection(url, requestType, requestXml, "valid", "", false);
		} else if (protocol.equalsIgnoreCase("http")) {
			conn = setupHttpConnection(url, requestType, requestXml, "valid", "", false);
		} else {
			fail("Protocol can only be http or https, found " + protocol);
		}

		// read response from output stream of connection
		String xmlResponse = null;
		try {
			if (conn != null) {
				if (expectedResponse.equalsIgnoreCase(expectedResponseMessage_Datajob)) {
					xmlResponse = readResponse(conn.getInputStream());
					assertEquals(expectedHttpCode, conn.getResponseCode());
					// validate the response against xsd
					assertTrue(validateXML(DataJobConstant.DATAJOB_XSD, new String(fileToBytes(requestXml))));

				} else {
					fail("expected Response Code :" + expectedResponse + " ,actual Response Code :"
							+ conn.getResponseCode() + "are not same");
				}
				conn.disconnect();
			}
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		// unmarshall the response into Datajob object
		return EhcoreAPIUtil.unmarshallFromString(xmlResponse);
	}

	public static void processRequest_invalid(String url, String requestType, String requestXml, String djId,
			String contentType, String expectedResponse) throws Exception {

		HttpURLConnection conn = null;
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		String protocol = testData.getProtocol();

		// get connection based on the protocol (http or https) and write request to it
		if (protocol.equalsIgnoreCase("https") && djId == "") {
			conn = setupHttpsConnection(url, requestType, requestXml, contentType, "", false);
		} else if (protocol.equalsIgnoreCase("https") && djId != "") {
			conn = setupHttpsConnection(url, requestType, requestXml, contentType, djId, true);
		} else if (protocol.equalsIgnoreCase("http") && djId == "") {
			conn = setupHttpConnection(url, EhcoreAPIConstants.POST_REQUEST, requestXml, contentType, "", false);
		} else if (protocol.equalsIgnoreCase("http") && djId != "") {
			conn = setupHttpConnection(url, EhcoreAPIConstants.POST_REQUEST, requestXml, contentType, djId, true);
		} else {
			fail("Protocol can only be http or https, found " + protocol);
		}

		// read response code and assert with expected Response.
		try {
			if (conn != null) {

				if (expectedResponse.equalsIgnoreCase("InternalServerError")) {
					assertEquals(expectedHttpCode_InternalError, conn.getResponseCode());
				} else if (expectedResponse.equalsIgnoreCase("UnsuportedType")) {
					assertEquals(expectedHttpCode_UnSupportedType, conn.getResponseCode());
				} else if (expectedResponse.equalsIgnoreCase(EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST)) {
					assertEquals(expectedHttpCode_BadRequest, conn.getResponseCode());
				} else if (expectedResponse.equalsIgnoreCase("NotFound")) {
					assertEquals(expectedHttpCode_NotFound, conn.getResponseCode());
				} else {
					fail("expected Response Code :" + expectedResponse + "and actual response code :"
							+ conn.getResponseCode() + ", are not same");
				}
				conn.disconnect();
			}
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
	}

	public static HttpsURLConnection setupHttpsConnection(String strUrl, String reqMethod, String xmlFilePath,
			String value, String djId, boolean isCCDImport) {

		Log4jUtil.log("URL to be connected  to: " + strUrl);
		URL url = null;
		try {
			Log4jUtil.log("Creating URL object for above URL");
			url = new URL(strUrl);
			Log4jUtil.log("Created URL object");
		} catch (MalformedURLException mue) {
			fail(mue.getMessage(), mue);
			fail(mue.getMessage());
		}

		assertNotNull(url);
		HttpsURLConnection connection = null;
		try {
			Log4jUtil.log("Attempting to get secure connection");
			connection = (HttpsURLConnection) url.openConnection();
			Log4jUtil.log("Got connection");
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		assertNotNull(connection);
		try {
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(reqMethod);
			if (isCCDImport) {
				connection.setRequestProperty("DataJobId", djId);
			}
			if (value.equalsIgnoreCase("valid")) {
				connection.setRequestProperty("Content-Type", "application/xml");
			} else if (value.equalsIgnoreCase("validAllscriptsCCDImport")) {
				connection.setRequestProperty("Accept-Charset", "UTF-8");
				connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			} else if (value.equalsIgnoreCase("invalid")) {
				connection.setRequestProperty("Content-Type", "text/html");
			} else if (value.equalsIgnoreCase("null")) {
				connection.setRequestProperty("Content-Type", "null");
			}

			if (xmlFilePath != null) {
				Log4jUtil.log("Getting an output stream on connection and writting XML to it");
				OutputStream os = connection.getOutputStream();
				Log4jUtil.log("Output stream obtained");
				os.write(fileToBytes(xmlFilePath));
				os.flush();
				Log4jUtil.log("XML written to output stream");
			} else if (reqMethod.equals(EhcoreAPIConstants.GET_REQUEST)) {
				Log4jUtil.log("Path specified to XML input is null. Not writing anything "
						+ "to the connection since its a \"Get\" request");
			} else {
				fail("No XML input file specified for the \"Post\" request");
				fail("No XML input file specified for the \"Post\" request");
			}

			Log4jUtil.log("HTTPS response code: " + connection.getResponseCode());
			Log4jUtil.log("HTTPS response message: " + connection.getResponseMessage());
		} catch (ProtocolException pe) {
			fail(pe.getMessage(), pe);
			fail(pe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}

		return connection;
	}

	public static byte[] fileToBytes(String filePath) {
		File file = new File(filePath);
		InputStream is;
		byte[] bytes = null;

		try {
			is = new FileInputStream(file);

			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				fail("File is too large to be processed: " + filePath);
				fail("File is too large to be processed: " + filePath);
			}
			bytes = new byte[(int) length];

			int offset = 0, numRead;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset != bytes.length) {
				fail("Could not completely read file " + filePath);
				fail("Could not completely read file " + filePath);
			}
			// Log4jUtil.log("Request &&&&: " + new String(bytes));
			// Save actual to a file.
			String actualCcdFileName = EhcoreAPIConstants.MARSHAL_CCD + "marshallccd351.xml";
			try {
				FileUtils.writeStringToFile(new File(actualCcdFileName), new String(bytes), false);
			} catch (IOException e) {
				fail(e.getMessage(), e);
				fail("Failed to write actualCcd to file. " + e.getMessage());
			}
			is.close();
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		Log4jUtil.log("fileToBytes content::" + bytes.toString());
		return bytes;
	}

	public static HttpURLConnection setupHttpConnection(String strUrl, String reqMethod, String xmlFilePath,
			String contentType, String djId, boolean isValidDjID) {

		Log4jUtil.log("URL to be connected  to: " + strUrl);
		URL url = null;
		try {
			Log4jUtil.log("Creating URL object for above URL");
			url = new URL(strUrl);
			Log4jUtil.log("Created URL object");
		} catch (MalformedURLException mue) {
			fail(mue.getMessage(), mue);
			fail(mue.getMessage());
		}

		assertNotNull(url);
		HttpURLConnection connection = null;
		try {
			Log4jUtil.log("Opening URL connection");
			connection = (HttpURLConnection) url.openConnection();
			Log4jUtil.log("URL connection established");
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}

		assertNotNull(connection);

		try {
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(reqMethod);
			if (isValidDjID) {
				connection.setRequestProperty("DataJobId", djId);
			}

			if (contentType.equalsIgnoreCase("valid")) {
				connection.setRequestProperty("Content-Type", "application/xml");
			} else if (contentType.equalsIgnoreCase("validAS_CCD")) {
				connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			} else if (contentType.equalsIgnoreCase("invalid")) {
				connection.setRequestProperty("Content-Type", "text/json");
			} else if (contentType.equalsIgnoreCase("null")) {
				connection.setRequestProperty("Content-Type", "null");
			} else {
				Log4jUtil.log("Without Content-Type in Request Header");
			}

			if (xmlFilePath != null) {
				Log4jUtil.log("Getting an output stream on connection and writting XML to it");
				OutputStream os = connection.getOutputStream();
				Log4jUtil.log("Output stream obtained");
				os.write(fileToBytes(xmlFilePath));
				os.flush();
				Log4jUtil.log("XML written to output stream");
			} else if (reqMethod.equals(EhcoreAPIConstants.GET_REQUEST)) {
				Log4jUtil.log(
						"Path specified to XML input is null. Not writing anything to the connection since its a \"Get\" request");
			} else {
				fail("No XML input file specified for the \"Post\" request");
				fail("No XML input file specified for the \"Post\" request");
			}

			Log4jUtil.log("HTTP response code: " + connection.getResponseCode());
			Log4jUtil.log("HTTP response message: " + connection.getResponseMessage());

		} catch (ProtocolException pe) {
			fail(pe.getMessage(), pe);
			fail(pe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		return connection;
	}

	public static String readResponse(InputStream is) {
		StringBuilder response = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line).append("\n");
			}
			Log4jUtil.log("Response: " + response);
			reader.close();
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}

		return response.toString();
	}

	public static String readFile(String path) {

		FileInputStream stream = null;
		MappedByteBuffer bb = null;
		try {
			try {
				stream = new FileInputStream(new File(path));
			} catch (FileNotFoundException e1) {
				fail(e1.getMessage(), e1);
				fail(e1.getMessage());
			}
			FileChannel fc = stream.getChannel();

			try {
				bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			} catch (IOException e) {
				fail(e.getMessage(), e);
				fail(e.getMessage());
			}
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		}

		finally {
			try {
				stream.close();
			} catch (IOException e) {
				fail(e.getMessage(), e);
				fail(e.getMessage());
			}
		}

	}

	public static ProcessingResponse processRequestCCDMessage(String url, String requestType, String requestXml,
			String djId, String expectedResponse) throws Exception {

		HttpURLConnection conn = null;
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		String protocol = testData.getProtocol();

		if (protocol.equalsIgnoreCase("https") && djId != "") {
			conn = setupHttpsConnection(url, requestType, requestXml, "valid", djId, true);
		} else if (protocol.equalsIgnoreCase("http") && djId != "") {
			conn = setupHttpConnection(url, EhcoreAPIConstants.POST_REQUEST, requestXml, "valid", djId, true);
		}

		else if (protocol.equalsIgnoreCase("https") && djId == "") {
			conn = setupHttpsConnection(url, requestType, requestXml, "valid", djId, false);
		} else if (protocol.equalsIgnoreCase("http") && djId == "") {
			conn = setupHttpConnection(url, EhcoreAPIConstants.POST_REQUEST, requestXml, "valid", djId, false);
		} else {
			fail("Protocol can only be http or https, found " + protocol);
		}

		// read response from output stream of connection
		String xmlResponse = null;
		try {
			if (conn != null) {
				if (expectedResponse.equalsIgnoreCase(EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED)
						&& conn.getResponseCode() == expectedHttpCodeAccepted) {
					xmlResponse = readResponse(conn.getInputStream());
					assertEquals(expectedHttpCodeAccepted, conn.getResponseCode());
					// validate the response against xsd
					assertTrue(validateXML(EhcoreAPIConstants.PROCESSING_RESPONSE_XSD, xmlResponse),
							"Response XML is not valid");
				} else if (expectedResponse.equalsIgnoreCase(EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST)
						&& conn.getResponseCode() == expectedHttpCode_BadRequest) {
					assertEquals(expectedHttpCode_BadRequest, conn.getResponseCode());
					xmlResponse = readResponse(conn.getErrorStream());
					// validate the response against xsd
					assertTrue(validateXML(EhcoreAPIConstants.PROCESSING_RESPONSE_XSD, xmlResponse),
							"Response XML is not valid");
				} else {
					fail("expected response code :" + expectedResponse + ",actual response code :"
							+ conn.getResponseCode() + ",are not same");
				}
				conn.disconnect();
			}
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		return EhcoreAPIUtil.unmarshallCCDResponse(xmlResponse);
	}

	public static boolean reprocessRequest(String url, String requestType, String requestXml, String expectedResponse)
			throws Exception {

		HttpURLConnection conn = null;
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		String protocol = testData.getProtocol();

		if (protocol.equalsIgnoreCase("https")) {
			conn = setupHttpsConnection(url, requestType, requestXml, "valid", "", false);
		} else if (protocol.equalsIgnoreCase("http")) {
			conn = setupHttpConnection(url, EhcoreAPIConstants.POST_REQUEST, requestXml, "valid", "", false);
		} else {
			fail("Protocol can only be http or https, found " + protocol);
		}

		boolean xmlResponse = false;
		try {
			if (conn != null) {
				if (expectedResponse.equalsIgnoreCase(EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED)) {
					xmlResponse = conn.getDoInput();
					assertEquals(expectedHttpCodeAccepted, conn.getResponseCode());
				} else {
					fail("expected response code :" + expectedResponse + ",actual response code :"
							+ conn.getResponseCode() + "are not same");
				}

				conn.disconnect();
			}
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}

		return xmlResponse;
	}

	public static ProcessingResponse sendMessage(String djId, String UPN, String type) throws Exception {

		String url = null;
		String xmlFile = null;
		String toXML = null;
		String expectedResponse = null;
		// EHDC:CCD Import - Valid and Invalid Message
		if (type.equalsIgnoreCase(EhcoreAPIConstants.VALID_CCD)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			xmlFile = CCDImportConstants.CCD1 + "CCDExchange1.xml";
			toXML = CCDImportConstants.SAMPLE_CCD + "testCCDExchange1.xml";
			updateCCD_Data(xmlFile, toXML, UPN);
			assertTrue(isValidXML(new String(fileToBytes(toXML))), "Request XML is not valid");
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;

		}
		// Consolidated CCD
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.ConsolidatedCCD)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			xmlFile = CCDImportConstants.C_CCD + "ConsolidatedCCD1.xml";
			toXML = CCDImportConstants.SAMPLE_CCD + "testConsolidatedCCD1.xml";
			updateCCD_Data(xmlFile, toXML, UPN);
			assertTrue(isValidXML(new String(fileToBytes(toXML))), "Request XML is not valid");
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;
		}
		// No Known Consolidated CCD
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.NOKNOWN_C_CCD)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			xmlFile = CCDImportConstants.C_CCD + "NoKnownC_CCD1.xml";
			toXML = CCDImportConstants.SAMPLE_CCD + "testNoKnownC_CCD1.xml";
			updateCCD_Data(xmlFile, toXML, UPN);
			assertTrue(isValidXML(new String(fileToBytes(toXML))), "Request XML is not valid");
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;
		}

		// Invalid CCDExchange
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALIDCCD)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			toXML = CCDImportConstants.INVALID_CCD + "InvalidCCDExchange1.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST;
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALID_XML_VALIDATION)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			toXML = CCDImportConstants.INVALID_CCD + "InvalidXML_CCDExchange1.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST;
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALID_CCD_NOTIFICATION)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			xmlFile = CCDImportConstants.INVALID_CCD + "NullSocialHistory_CCDExchange1.xml";
			toXML = CCDImportConstants.SAMPLE_CCD + "testNullSocialHistory_CCDExchange1.xml";
			updateCCD_Data(xmlFile, toXML, UPN);
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.NULL_CODE_SOCIAL_HISTORY)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			toXML = CCDImportConstants.INVALID_CCD + "NullSocialHistory_CCDExchange1.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;
		}
		// Invalid Consolidated CCD
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALIDC_CCD)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			toXML = CCDImportConstants.INVALID_C_CCD + "InvalidCCCD1.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST;
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALID_C_CCD_XML_VALIDATION)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			toXML = CCDImportConstants.INVALID_C_CCD + "InvalidCCCD1_XMLValidate.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST;
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALID_C_CCD_NOTIFICATION)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			xmlFile = CCDImportConstants.INVALID_C_CCD + "NullSocialHistoryCCCD1.xml";
			toXML = CCDImportConstants.SAMPLE_CCD + "testNullSocialHistoryCCCD1.xml";
			updateCCD_Data(xmlFile, toXML, UPN);
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.NULL_CODE_C_CCD_SOCIALHISTORY)) {
			EhcoreAPI ehcoreApi = new EhcoreAPI();
			EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
			url = testData.getUrl() + EhcoreAPIConstants.CCDImport;
			toXML = CCDImportConstants.INVALID_C_CCD + "NullSocialHistoryCCCD1.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED;
		}
		ProcessingResponse response = processCCD_CheckResponse(url, EhcoreAPIConstants.POST_REQUEST, toXML, djId,
				expectedResponse, type);
		return response;

	}

	/**
	 * This Method is used to validate request xml against XSD.
	 */
	public static boolean validateXML(String xsdFilePath, String xml) {

		Log4jUtil.log(" Validate XML :XSD File Path ::" + xsdFilePath);
		// Log4jUtil.log(" Validate XML :Xml input ::"+xml);
		Schema schema = null;
		try {
			schema = getSchema(xsdFilePath);
		} catch (Exception e) {
			fail(e.getMessage(), e);
			fail(e.getMessage());
		}
		Validator validator = schema.newValidator();
		final List<String> allErrors = new ArrayList<String>();
		final List<String> warnings = new ArrayList<String>();
		final List<String> errors = new ArrayList<String>();
		final List<String> fatalErrors = new ArrayList<String>();
		synchronized (validator) {

			validator.setErrorHandler(new ErrorHandler() {
				public void warning(SAXParseException exception) throws SAXException {
					warnings.add("Warning in (" + exception.getLineNumber() + " , " + exception.getColumnNumber()
							+ " ) : " + exception.getMessage());
					Log4jUtil.log("Warning in (" + exception.getLineNumber() + " , " + exception.getColumnNumber()
							+ " ) : " + exception.getMessage());
				}

				public void fatalError(SAXParseException exception) throws SAXException {
					fatalErrors.add("Fatal Error in ( " + exception.getLineNumber() + " , "
							+ exception.getColumnNumber() + " ) : " + exception.getMessage());
					Log4jUtil.log("Fatal Error in ( " + exception.getLineNumber() + " , " + exception.getColumnNumber()
							+ " ) : " + exception.getMessage());
				}

				public void error(SAXParseException exception) throws SAXException {
					errors.add("Error in (" + exception.getLineNumber() + " , " + exception.getColumnNumber() + ") : "
							+ exception.getMessage());
					Log4jUtil.log("Error in (" + exception.getLineNumber() + " , " + exception.getColumnNumber()
							+ ") : " + exception.getMessage());
				}
			});

			try {
				validator.validate(new StreamSource(new StringReader(xml)));
			} catch (SAXException e) {
				fail(e.getMessage(), e);
				fail(e.getMessage());
			} catch (IOException e) {
				fail(e.getMessage(), e);
				fail(e.getMessage());
			}
		}
		allErrors.addAll(fatalErrors);
		allErrors.addAll(errors);
		Log4jUtil.log("XSD File Path ::" + xsdFilePath + ",Error size::" + allErrors.size());

		if (allErrors.size() > 0) {
			return false;
		}

		return true;

	}

	public static Schema getSchema(String xsdFilePath) {
		Schema schema = null;
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		try {
			schema = factory.newSchema(new StreamSource(xsdFilePath));
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return schema;
	}

	public static boolean isValidXML(String xml) {

		boolean isValidxml = false;
		String xmlWithoutCCD = new StringBuffer(xml.substring(0, xml.indexOf("<CcdXml>") + 8))
				.append(xml.substring(xml.indexOf("</CcdXml>"))).toString();
		boolean isValidCCDExchange = validateXML(EhcoreAPIConstants.CCDEXCHANGE_XSD, xmlWithoutCCD);
		Log4jUtil.log("isValidCCDExchange?" + isValidCCDExchange);
		String ccdXml = xml
				.substring(xml.indexOf("<CcdXml>") + 8, xml.indexOf("</CcdXml>")).replace("&lt;",
						"<")
				.replace("&gt;", ">").replace("&quot;", "\"").replace("&apos;", "'").replace("&amp;", "&")
				.replace("<![CDATA[", "").replace("]]>", "");

		boolean isValidCCD = validateXML(EhcoreAPIConstants.CCD_XSD, ccdXml);
		Log4jUtil.log("isValidCCD?" + isValidCCD);
		isValidxml = isValidCCDExchange && isValidCCD;
		Log4jUtil.log("isValidxml?" + isValidxml);
		return isValidxml;
	}

	public static ProcessingResponse processCCD_CheckResponse(String url, String requestType, String requestXml,
			String djId, String expectedResponse, String type) throws Exception {

		ProcessingResponse response = processRequestCCDMessage(url, requestType, requestXml, djId, expectedResponse);
		assertNotNull(response.getDataJobId());
		assertNotNull(response.getMessageId());
		if (type.equalsIgnoreCase(EhcoreAPIConstants.INVALIDCCD)) {
			assertEquals("Failure", response.getProcessingStatus());
			assertNotNull(response.getResponseMessage());
		}

		return response;
	}

	public static ProcessingResponse processInvalidCCD_CheckResponse(String url, String requestType, String requestXml,
			String djId, String expectedResponse) throws Exception {

		ProcessingResponse response = processRequestCCDMessage(url, requestType, requestXml, djId, expectedResponse);
		assertEquals(response.getProcessingStatus(), "Failure");
		assertNotNull(response.getResponseMessage());

		return response;
	}

	public static void marshall(DataJob dj, String filePath) {
		Marshaller marshaller = null;
		try {
			if (marshaller == null) {
				JAXBContext jc = JAXBContext.newInstance(DataJob.class);
				marshaller = jc.createMarshaller();
			}
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			OutputStream os = new FileOutputStream(filePath);
			marshaller.marshal(dj, os);
			os.close();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
	}

	public static void marshallReprocessRequest(ReprocessRequest req, String filePath) {
		Marshaller marshaller = null;
		try {
			if (marshaller == null) {
				JAXBContext jc = JAXBContext.newInstance(ReprocessRequest.class);
				marshaller = jc.createMarshaller();
			}
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			OutputStream os = new FileOutputStream(filePath);
			marshaller.marshal(req, os);
			os.close();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
	}

	public static DataJob unmarshallFromFile(String xmlFilepath) {
		DataJob dj = null;
		Unmarshaller um = null;
		try {
			if (um == null) {
				JAXBContext jc = JAXBContext.newInstance(DataJob.class);
				um = jc.createUnmarshaller();
			}
			InputStream is = new FileInputStream(xmlFilepath);
			dj = (DataJob) um.unmarshal(is);
			is.close();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		assertNotNull(dj);
		return dj;
	}

	public static CcdExchange unmarshallCCD(String xmlFilepath) {
		CcdExchange Ccd = null;
		try {
			StreamSource source = new StreamSource(new FileInputStream(xmlFilepath));
			JAXBContext jaxbContext = JAXBContext.newInstance("com.intuit.ihg.eh.core.dto");
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<CcdExchange> result = unMarshaller.unmarshal(source, CcdExchange.class);
			Ccd = (CcdExchange) result.getValue();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException e) {
			fail(e.getMessage(), e);
			fail(e.getMessage());
		}
		assertNotNull(Ccd);
		return Ccd;

	}

	public static ReprocessRequest unmarshallReprocessRequest(String xmlFilepath) {
		ReprocessRequest req = null;
		Unmarshaller um = null;

		try {
			if (um == null) {
				JAXBContext jc = JAXBContext.newInstance(ReprocessRequest.class);
				um = jc.createUnmarshaller();
			}
			InputStream is = new FileInputStream(xmlFilepath);
			req = (ReprocessRequest) um.unmarshal(is);
			is.close();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		assertNotNull(req);
		return req;
	}

	public static void marshallCCD(CcdExchange ccdExchange, String filePath) {

		Marshaller marshaller = null;
		try {
			if (marshaller == null) {
				JAXBContext jc = JAXBContext.newInstance(CcdExchange.class);
				marshaller = jc.createMarshaller();
			}
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"http://schema.intuit.com/health/ccd/v1 CCDExchange.xsd");
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
				@Override
				public String[] getPreDeclaredNamespaceUris() {
					return new String[] { "http://www.w3.org/2001/XMLSchema", "http://schema.intuit.com/health/ccd/v1",
							"http://www.w3.org/2001/XMLSchema-instance" };
				}

				@Override
				public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
					if ("http://www.w3.org/2001/XMLSchema".equals(namespaceUri)) {
						return "xs";
					}
					if ("http://schema.intuit.com/health/ccd/v1".equals(namespaceUri)) {
						return "eh";
					}
					if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri)) {
						return "xsi";
					}
					return "eh";
				}
			});
			JAXBElement<CcdExchange> ccdExchangeRootElement = new JAXBElement<CcdExchange>(
					new QName("http://schema.intuit.com/health/ccd/v1", "CcdExchange", "eh"), CcdExchange.class,
					ccdExchange);
			OutputStream os = new FileOutputStream(filePath);
			marshaller.marshal(ccdExchangeRootElement, os);
			os.close();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
	}

	/**
	 * Use this method to update datajob node values based on the test scenario's
	 */
	public static void updateOpenDataJobXml(String fromXML, String transStatus, String toXML) {
		DataJob dj = unmarshallFromFile(fromXML);

		dj.setDataChannel("EHDataChannel");
		dj.setDataFeed("CCDImportDataFeed");
		dj.setDataPartner("Allscripts");
		dj.setDataSource("EHR");
		dj.setMessageType("ccd");
		dj.setFileName("fileName20APR2011_A");
		dj.setTransmissionStatus(transStatus);

		marshall(dj, toXML);
	}

	public static void removeRequiredNode(String fromXML, String value, String transStatus, String toXML) {
		DataJob dj = unmarshallFromFile(fromXML);

		dj.setDataChannel(value);
		dj.setDataFeed(value);
		dj.setDataPartner("Allscripts");
		dj.setDataSource("EHR");
		dj.setMessageType(value);
		dj.setFileName("fileName20APR2011_A");
		dj.setTransmissionStatus(transStatus);

		marshall(dj, toXML);
	}

	public static void updateDataJobNodeValues(String fromXML, String value, String transStatus, String toXML) {
		DataJob dj = unmarshallFromFile(fromXML);

		dj.setTransmissionStatus(transStatus);
		dj.setDataChannel(value);
		dj.setDataFeed(value);
		dj.setDataPartner(value);
		dj.setDataSource(value);
		dj.setFileName(value);
		dj.setMessageType(value);

		marshall(dj, toXML);
	}

	public static void updateCompleteDataJobXml(String fromXML, String djId, String transStatus, String toXML) {

		DataJob dj = unmarshallFromFile(fromXML);

		dj.setDataJobId(djId);
		dj.setTransmissionStatus(transStatus);
		dj.setDataChannel("EHDataChannel");
		dj.setDataFeed("CCDImportDataFeed");
		dj.setMessageType("ccd");

		marshall(dj, toXML);
	}

	public static void updateDatajob(String xmlFilepath, String djId, String transStatus, String value, String toXML) {

		DataJob dj = unmarshallFromFile(xmlFilepath);

		dj.setDataJobId(djId);
		dj.setTransmissionStatus(transStatus);
		dj.setDataChannel(value);
		dj.setDataFeed(value);
		dj.setMessageType(value);

		marshall(dj, toXML);
	}

	public static void updateDataJobWithInvalidValues(String xmlFilepath, String djId, String transStatus,
			String toXML) {

		DataJob dj = unmarshallFromFile(xmlFilepath);

		dj.setDataJobId(djId);
		dj.setTransmissionStatus(transStatus);
		dj.setDataChannel("EHDataChannel1");
		dj.setDataFeed("CCDImportDataFeed1");
		dj.setMessageType("ccd1");

		marshall(dj, toXML);
	}

	public static DataJob unmarshallFromString(String str) {
		DataJob dj = null;
		Unmarshaller um = null;

		try {
			if (um == null) {
				JAXBContext jc = JAXBContext.newInstance(DataJob.class);
				um = jc.createUnmarshaller();
			}
			ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
			dj = (DataJob) um.unmarshal(is);
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		}
		assertNotNull(dj);
		return dj;
	}

	public static ProcessingResponse unmarshallCCDResponse(String str) {
		ProcessingResponse res = null;
		Unmarshaller um = null;

		try {
			if (um == null) {
				JAXBContext jc = JAXBContext.newInstance(ProcessingResponse.class);
				um = jc.createUnmarshaller();
			}
			ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
			res = (ProcessingResponse) um.unmarshal(is);
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		}
		assertNotNull(res);
		return res;
	}

	public static AllscriptsMessageEnvelope unmarshallAllScriptsCCDImportResponse(String str) {
		String envelope = null;
		if (null != str && str.contains(SOAP_BODY_START)) {
			envelope = str.substring(str.indexOf(SOAP_BODY_START) + 11, str.indexOf(SOAP_BODY_END));
		}
		AllscriptsMessageEnvelope res = null;
		Unmarshaller um = null;

		try {
			if (um == null) {
				JAXBContext jc = JAXBContext.newInstance(AllscriptsMessageEnvelope.class);
				um = jc.createUnmarshaller();
			}
			ByteArrayInputStream is = new ByteArrayInputStream(envelope.getBytes());
			res = (AllscriptsMessageEnvelope) um.unmarshal(is);
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		}
		assertNotNull(res);
		return res;
	}

	/**
	 * Use this method to update reprocess message
	 */
	public static void updateReprocessRequest(String fromXML, String djId, String msgId, String toXML) {

		ReprocessRequest req = unmarshallReprocessRequest(fromXML);

		req.setDataJobId(djId);
		req.setMessageId(msgId);

		marshallReprocessRequest(req, toXML);
	}

	public static void updateReprocessRequest(String fromXML, String value, String toXML) {

		ReprocessRequest req = unmarshallReprocessRequest(fromXML);

		req.setMessageId(value);

		marshallReprocessRequest(req, toXML);
	}

	public static void updateReprocessRequest_DjId(String fromXML, String djId, String toXML) {

		ReprocessRequest req = unmarshallReprocessRequest(fromXML);

		req.setDataJobId(djId);

		marshallReprocessRequest(req, toXML);
	}

	/**
	 * To Pause the current action for particular time
	 */
	public static void sleepMessage(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException ie) {
			fail(ie.getMessage(), ie);
			fail(ie.getMessage());
		}
	}

	/**
	 * Unmarshall CCDExport Message to JaxB Object
	 */
	public static CCDMessageType unmarshallMessage(String xmlFilepath) {
		CCDMessageType ccdMessage = null;
		try {
			// Reader reader = new StringReader(messageIn);
			InputStream is = new FileInputStream(xmlFilepath);
			JAXBContext jaxbContext = JAXBContext.newInstance("com.intuit.qhg.hub.schemas.messages");
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			@SuppressWarnings("unchecked")
			JAXBElement<CCDMessageType> jaxbSimple = (JAXBElement<CCDMessageType>) unMarshaller.unmarshal(is);
			ccdMessage = (CCDMessageType) jaxbSimple.getValue();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException e) {
			fail(e.getMessage(), e);
			fail(e.getMessage());
		}
		assertNotNull(ccdMessage);
		return ccdMessage;
	}

	/**
	 * Marshal Jaxb object to CCDMessageType
	 */
	public static void marshallMessage(CCDMessageType message, String filePath) {

		Marshaller marshaller = null;
		try {
			if (marshaller == null) {
				JAXBContext jc = JAXBContext.newInstance(CCDMessageType.class);
				marshaller = jc.createMarshaller();
			}
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"http://www.intuit.com/qhg/hub/schemas/Messages CCDMessageType.xsd");
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
				@Override
				public String[] getPreDeclaredNamespaceUris() {
					return new String[] { "http://www.w3.org/2001/XMLSchema",
							"http://www.w3.org/2001/XMLSchema-instance",
							"http://www.intuit.com/qhg/hub/schemas/Messages",
							"http://www.intuit.com/qhg/eh/schemas/CCD" };
				}

				@Override
				public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
					if ("http://www.w3.org/2001/XMLSchema".equals(namespaceUri))
						return "xs";
					if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri))
						return "xsi";
					if ("http://www.intuit.com/qhg/hub/schemas/Messages".equals(namespaceUri))
						return "msg";
					if ("http://www.intuit.com/qhg/eh/schemas/CCD".equals(namespaceUri))
						return "eh";
					return "msg";
				}
			});
			OutputStream os = new FileOutputStream(filePath);
			JAXBElement<CCDMessageType> rootElement = new JAXBElement<CCDMessageType>(
					new QName("http://www.intuit.com/qhg/hub/schemas/Messages", "CCDMessage", "msg"),
					CCDMessageType.class, message);
			marshaller.marshal(rootElement, os);
			os.close();
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		} catch (FileNotFoundException fnfe) {
			fail(fnfe.getMessage(), fnfe);
			fail(fnfe.getMessage());
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
	}

	public static ProcessingResponse unmarshallString(String str) {
		ProcessingResponse response = null;
		Unmarshaller um = null;

		try {
			if (um == null) {
				JAXBContext jc = JAXBContext.newInstance(ProcessingResponse.class);
				um = jc.createUnmarshaller();
			}
			ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
			response = (ProcessingResponse) um.unmarshal(is);
		} catch (JAXBException je) {
			fail(je.getMessage(), je);
			fail(je.getMessage());
		}
		assertNotNull(response);
		return response;
	}

	/**
	 * This method is used to update CCDExport data.(Updated -MsgId & UPN)
	 */
	public static void updateCCDExport_Data(String fromXML, String toXML, String msgId, String personUrnId,
			String practiceUrnId, String providerUrnId) {

		CCDMessageType message = unmarshallMessage(fromXML);

		message.getHeader().setMsgId(msgId);
		message.getCCDDocument().getPatientDemographics().getPatientIdentifier().setPortalStackPersonUrnId(personUrnId);
		message.getClinicalExchangeHeader().getPracticeList().get(0).getPracticeIdentifier()
				.setPortalStackPracticeUrnId(practiceUrnId);
		message.getClinicalExchangeHeader().getPracticeList().get(0).getProviderList().get(0).getProviderIdentifier()
				.setIntuitProviderId(providerUrnId);

		marshallMessage(message, toXML);
	}

	public static String getUrl(String parameter) throws Exception {
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		String url = testData.getUrl().concat(parameter);
		return url;
	}

	public static void verifyExpectedDataJobProcStatus(String djId, String expectedProcStatus) throws Exception {

		int procTime = Integer.parseInt(DataJobConstant.DJ_MGR_PROC_TIME);

		boolean isProcStatusAsExpected = false;
		String actualStatus = null;
		int pollTimeInSecs = 5;
		int i = 0;

		long t_beg, t_end;
		t_beg = System.currentTimeMillis();
		while (i < procTime / pollTimeInSecs) {
			actualStatus = EhcoreTrackingDBUtils.getDatajobDetails(djId);
			if (actualStatus.equalsIgnoreCase(expectedProcStatus)) {
				isProcStatusAsExpected = true;
				break;
			} else {
				sleepMessage(pollTimeInSecs);
			}
			i++;
		}
		t_end = System.currentTimeMillis();

		Log4jUtil.log("Total time taken for processing status to be updated " + (t_end - t_beg) / 1000);

		assertTrue(isProcStatusAsExpected, "Expected Job_Status_Type " + expectedProcStatus + ", found " + actualStatus
				+ " for DataJob Id " + djId);
	}

	public static void openDJWithTransmissionEnd(String transStatus) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String fromXML = DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml";

		EhcoreAPIUtil.updateOpenDataJobXml(fromXML, transStatus, toXML);

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	private static String getAllScriptsCCDExportURL() throws Exception {
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":" + EhcoreAPIConstants.PORT
				+ testData.getAllscriptsccdexporturl();
	}

	private static String getAllScriptsFormsExportURL() throws Exception {
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":" + EhcoreAPIConstants.PORT
				+ testData.getAllscriptsccdexporturl();
	}

	public static String getAllScriptsCCDImportURL() throws Exception {
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":" + EhcoreAPIConstants.PORT
				+ testData.getAllscriptsccdimporturl();
	}

	public static void requestWithInvalidHeader(String type, String djId, String transStatus, String contentType,
			String expectedResponse) throws Exception {

		String url = null;
		String xmlFile = null;

		// Datajob
		if (type == "opendatajob") {
			url = getUrl(EhcoreAPIConstants.DATAJOB);
			xmlFile = DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		} else if (type == "closedatajob") {
			url = getUrl(EhcoreAPIConstants.DATAJOB);
			xmlFile = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJob_template.xml";
		}

		// EHDC : CCD Import & Export
		else if (type == "ccdimport") {
			url = getUrl(EhcoreAPIConstants.CCDImport);
			xmlFile = CCDImportConstants.CCD + "CCDExchange1.xml";
		} else if (type == "ccdexport") {
			url = getUrl(EhcoreAPIConstants.CCDEXPORT);
			xmlFile = CCDExportConstants.CCD_EXPORT_DATA + "CCDMessageType.xml";
		}

		// Allscripts: CCDI mport & Export
		else if (type.equalsIgnoreCase(CCDImportConstants.AS_CCD)) {
			url = getAllScriptsCCDExportURL(); // if you get a failure, plz check it is for import or export
			// xmlFile = UtilConsts.SAMPLE_ALLSCRIPTS_ADAPTER_CDDIMPORT_INPUT_DATA +
			// "AllScriptsAdapter_CCDImport_Input.xml";
			xmlFile = CCDImportConstants.AS_REQ + "MyWay_HealthyPatient1.xml";
		} else if (type.equalsIgnoreCase(CCDExportConstants.AS_CCD_EXPORT)) {
			url = getAllScriptsCCDExportURL();
			xmlFile = CCDExportConstants.AS_CCDEXPORT_REQ + "AS_CCDExport_Request.xml";
		}

		// Allscripts :Questionnaire Export
		else if (type == "questionnaireAllscriptsexport") {
			url = getAllScriptsFormsExportURL();
			xmlFile = CCDExportConstants.SAMPLE_ALLSCRIPTS_FORMS_EXPORT_INPUT_DATA + "AllscriptsInput_FormsExport.xml";
		}
		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, xmlFile, "", contentType, expectedResponse);
	}

	private static String getURL_ERROR() {
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":"
				+ EhcoreAPIConstants.REPROCESSREQ_PORT + EhcoreAPIConstants.WS_URL;
	}

	public static void sendRequestWithInvalidURL(String djId, String type) throws Exception {

		String url = null;
		String xmlFile = null;

		// Datajob
		if (type == "opendatajob") {
			url = getUrl(EhcoreAPIConstants.DATAJOB) + type;
			xmlFile = DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		} else if (type == "closedatajob") {
			url = getUrl(EhcoreAPIConstants.DATAJOB) + type;
			xmlFile = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJob_template.xml";
		}

		// EHDC : CCD Import , Export & Reprocess Req
		else if (type == "ccdimport") {
			url = getUrl(EhcoreAPIConstants.CCDImport) + type;
			xmlFile = CCDImportConstants.CCD + "CCDExchange1.xml";
		} else if (type == "ccdexport") {
			url = getUrl(EhcoreAPIConstants.CCDEXPORT) + type;
			xmlFile = CCDExportConstants.CCD_EXPORT_DATA + "CCDMessageType.xml";
		} else if (type == "reprocessReq") {
			url = getURL_ERROR() + EhcoreAPIConstants.REPROCESS + type;
			xmlFile = EhcoreAPIConstants.REPROCESS_DATA + "ReprocessMessage_template.xml";
		}

		// Allscripts: CCDImport and Export
		else if (type.equalsIgnoreCase(CCDImportConstants.AS_CCD)) {
			url = getAllScriptsCCDImportURL() + type;
			xmlFile = CCDImportConstants.AS_REQ + "MyWay_HealthyPatient1.xml";
		} else if (type.equalsIgnoreCase(CCDExportConstants.AS_CCD_EXPORT)) {
			url = getAllScriptsCCDExportURL() + type;
			xmlFile = CCDExportConstants.AS_CCDEXPORT_REQ + "AS_CCDExport_Request.xml";
		}

		// Allscripts:Questionnaire Export
		else if (type == "questionnaireAllscriptsexport") {
			url = getAllScriptsFormsExportURL() + type;
			xmlFile = CCDExportConstants.SAMPLE_ALLSCRIPTS_FORMS_EXPORT_INPUT_DATA + "AllscriptsInput_FormsExport.xml";
		}

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, xmlFile, djId, "valid", "NotFound");
	}

	public static void openBlankDataJob(String value, String transStatus, String type) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String fromXML = DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml";
		if (type == "removeReqNode")
			EhcoreAPIUtil.removeRequiredNode(fromXML, value, transStatus, toXML);
		else
			EhcoreAPIUtil.updateDataJobNodeValues(fromXML, value, transStatus, toXML);

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	public static void openEmptyStringDataJob(String transStatus) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String xmlFile = DataJobConstant.DATA_JOB_INPUT + "OpenDataJobWithEmptyString.xml";

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, xmlFile, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	static public void assertIsNotNull(String message, Object object) {
		assertTrue(object != null, message);
	}

	public static DataJob completeDataJob(String djId, String transStatus) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String xmlFile = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testCompleteDataJob.xml";

		updateCompleteDataJobXml(xmlFile, djId, transStatus, toXML);

		DataJob dj = processRequest(url, EhcoreAPIConstants.POST_REQUEST, toXML, expectedResponseMessage_Datajob);

		assertIsNotNull(null, dj.getDataJobId());
		assertIsNotNull(null, dj.getDataChannel());
		assertIsNotNull(null, dj.getDataFeed());
		assertIsNotNull(null, dj.getMessageType());
		assertIsNotNull(null, dj.getTransmissionStatus());

		return dj;
	}

	public static DataJob openDataJob(String transStatus) throws Exception {
		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String fromXML = DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml";
		// update xml
		updateOpenDataJobXml(fromXML, transStatus, toXML);

		DataJob dj = processRequest(url, EhcoreAPIConstants.POST_REQUEST, toXML, expectedResponseMessage_Datajob);
		assertIsNotNull(null, dj.getDataJobId());
		assertIsNotNull(null, dj.getDataChannel());
		assertIsNotNull(null, dj.getDataFeed());
		assertIsNotNull(null, dj.getMessageType());
		assertIsNotNull(null, dj.getTransmissionStatus());

		return dj;
	}

	public static void completeDataJobInvalidId(String djId, String transStatus, String expectedResponse)
			throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String xmlFile = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testCompleteDataJob.xml";

		updateCompleteDataJobXml(xmlFile, djId, transStatus, toXML);

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, "", "valid", expectedResponse);
	}

	public static void completeDJWithInvalidXml(String djId, String transStatus, String value) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String xmlFile = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testCompleteDataJob.xml";

		EhcoreAPIUtil.updateDatajob(xmlFile, djId, transStatus, value, toXML);

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	public static void completeDataJobWithInvalid(String djId, String transStatus) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);
		String fromXML = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testCompleteDataJob.xml";

		EhcoreAPIUtil.updateDataJobWithInvalidValues(fromXML, djId, transStatus, toXML);

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	public static void CompleteDJWithEmptyStringValues(String djId, String transStatus) throws Exception {

		String url = getUrl(EhcoreAPIConstants.DATAJOB);

		String xmlFile = DataJobConstant.DATA_JOB_INPUT + "CompleteDataJobWithEmptyString.xml";

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, xmlFile, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	public static List<Message> verifyExpectedMessageProcStatus(int rowNo, String djId, String expectedStatus,
			String msg_type) throws Exception {
		int procTime = Integer.parseInt(DataJobConstant.DJ_MGR_PROC_TIME);

		List<Message> details = new ArrayList<Message>();
		boolean isProcStatusAsExpected = false;
		String actualStatus = null;
		int pollTimeInSecs = 5;
		int i = 0;

		long t_beg, t_end;
		t_beg = System.currentTimeMillis();
		while (i < procTime / pollTimeInSecs) {

			details = EhcoreTrackingDBUtils.getMessageDetails(djId, msg_type);

			actualStatus = details.get(rowNo).getProcessingStatusType().getCode();
			if ((actualStatus != "INPROGRESS") && (actualStatus.equalsIgnoreCase(expectedStatus))) {
				isProcStatusAsExpected = true;
				break;
			} else {
				sleepMessage(pollTimeInSecs);
			}
			i++;
		}
		t_end = System.currentTimeMillis();

		Log4jUtil.log("Total time taken for processing status to be updated ::" + (t_end - t_beg) / 1000);

		assertTrue(isProcStatusAsExpected, "Expected Message processing_status_type ::" + expectedStatus + ", found "
				+ actualStatus + " for DataJob Id " + djId);

		return details;
	}

	public static void updateCCD_Data(String fromXML, String toXML, String subjectId) throws Exception {

		CcdExchange Ccd = unmarshallCCD(fromXML);
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);

		Log4jUtil.log("SUBJECT*********" + subjectId);
		Log4jUtil.log("PRACTICE ID******" + testData.getPracticeID().trim());
		Log4jUtil.log("PROVIDER ID*********" + testData.getProviderID());

		Ccd.getPatientDemographics().getPatientIdentifier().setIntuitPatientId(subjectId);
		Ccd.getPracticeInformation().getPracticeIdentifier().setIntuitPracticeId(testData.getPracticeID());
		Ccd.getPracticeInformation().getProviderList().get(0).getProviderIdentifier()
				.setIntuitProviderId(testData.getProviderID());
		Ccd.getCcdMessageHeaders().getRoutingMap().getKeyValuePair().get(0).setValue(testData.getProviderID());
		Ccd.getCcdMessageHeaders().getRoutingMap().getKeyValuePair().get(1).setValue(testData.getProviderID());
		marshallCCD(Ccd, toXML);
	}

	public static AllscriptsMessageEnvelope sendAllscriptsImportMessage(EhcoreAPITestData testData, String type)
			throws Exception {
		String url = null;
		String toXML = null;
		String expectedResponse = null;
		if (type.equalsIgnoreCase(CCDImportConstants.AS_CCD)) {

			url = "http://dev3vip-eh-core-svc.qhg.local:80/asehr/service/hub";
			toXML = CCDImportConstants.AS_REQ + "Professional_AllisonReed_1.xml";
			expectedResponse = "OK";

		} else if (type == "invalidAllscriptsCCDImportMsg") {
			url = "http://dev3vip-eh-core-svc.qhg.local:80/asehr/service/hub";
			toXML = CCDImportConstants.AS_INVALID_REQ + "AllScriptsAdapter_CCDImport_InvalidInput.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST;
		}
		AllscriptsMessageEnvelope response = processAS_CCD(url, EhcoreAPIConstants.POST_REQUEST, toXML,
				expectedResponse);

		return response;
	}

	public static AllscriptsMessageEnvelope processAS_CCD(String url, String requestType, String requestXml,
			String expectedResponse) throws Exception {

		HttpURLConnection conn = null;
		EhcoreAPI ehcoreApi = new EhcoreAPI();
		EhcoreAPITestData testData = new EhcoreAPITestData(ehcoreApi);
		String protocol = testData.getProtocol();
		Log4jUtil.log("Protocol************" + protocol);

		if (protocol.equalsIgnoreCase("http")) {
			conn = setupHttpConnection(url, EhcoreAPIConstants.POST_REQUEST, requestXml, "validAS_CCD", "", false);
		} else {
			fail("Protocol can only be http or https, found " + protocol);
		}

		// read response from output stream of connection
		String xmlResponse = null;
		try {
			if (conn != null) {
				if (expectedResponse.equalsIgnoreCase(expectedResponseMessage_Datajob)
						&& conn.getResponseCode() == expectedHttpCode) {
					xmlResponse = readResponse(conn.getInputStream());
					assertEquals(expectedHttpCode, conn.getResponseCode());
					// validate the response against xsd

				} else if (expectedResponse.equalsIgnoreCase(EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST)
						&& conn.getResponseCode() == expectedHttpCode_BadRequest) {
					assertEquals(expectedHttpCode_BadRequest, conn.getResponseCode());
					xmlResponse = readResponse(conn.getErrorStream());
					// validate the response against xsd
					assertTrue(validateXML(EhcoreAPIConstants.PROCESSING_RESPONSE_XSD, xmlResponse),
							"Response XML is not valid");
				} else {
					fail("expected response code :" + expectedResponse + ",actual response code :"
							+ conn.getResponseCode() + ",are not same");
				}
				conn.disconnect();
			}
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			fail(ioe.getMessage());
		}
		return EhcoreAPIUtil.unmarshallAllScriptsCCDImportResponse(xmlResponse);
	}

	public static void verifyMongoDBResponseUsingAPI(String node, String obj_ref_id, String expected) {

		String url = null;

		if (node.equalsIgnoreCase(TrackingEnumHolder.OBJECTSTORE_NODE_TYPE.DELTA.toString())) {
			url = getDeltaResponseURL() + obj_ref_id;
		} else {
			url = getNodeResponseURL() + obj_ref_id;
		}

		processMongoResponse(url, EhcoreAPIConstants.GET_REQUEST, null, "Accepted", expected);

	}

	private static void processMongoResponse(String url, String requestType, String requestXml, String expectedCode,
			String expectedCcd) {

		HttpURLConnection conn = null;
		String protocol = EhcoreAPIConstants.PROTOCOL;

		if (protocol.equalsIgnoreCase("https")) {
			conn = setupHttpsConnection(url, requestType, requestXml, "valid", "", false);
		} else if (protocol.equalsIgnoreCase("http")) {
			conn = setupHttpConnection(url, requestType, requestXml, "valid", "", false);
		} else {
			// fail("Protocol can only be http or https, found " + protocol);
		}

		// read response from output stream of connection
		String SimpleCCD = null;
		try {
			if (conn != null) {
				if (expectedCode.equalsIgnoreCase(EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED)
						&& conn.getResponseCode() == expectedHttpCodeAccepted) { // EHCoreTestConsts.expectedResponse_Accepted
					SimpleCCD = readResponse(conn.getInputStream());
					assertEquals(expectedHttpCodeAccepted, conn.getResponseCode());
					// validate the response against xsd
					// assertTrue("Response XML is not
					// valid",validateXML(UtilConsts.PROCESSING_RESPONSE_XSD,xmlResponse));
				} else {
				}
				conn.disconnect();
			}
		} catch (IOException ioe) {
			fail(ioe.getMessage(), ioe);
			// fail(ioe.getMessage());
		}
		Log4jUtil.log(" *** SimpleCCD : ****" + SimpleCCD);

		// Save actual to a file.
		String actualCcd = EhcoreAPIConstants.ACTUAL_CCD + "actualMessageADDENTITY.xml";
		try {
			FileUtils.writeStringToFile(new File(actualCcd), SimpleCCD, false);
		} catch (IOException e) {
			fail(e.getMessage(), e);
			fail("Failed to write actualCcd to file. " + e.getMessage());
		}
		// Compare Actual objStore Message with Expected CCD Message using XMLUnit .
		// FAILRUES IN OLD FRAMEWORK TOOO
		// EhcoreXmlUnitUtil.assertEqualsXML(expectedCcd, actualCcd); XML comparision
		// check with Kavitha
	}

	/**
	 * Get the actual CDM List using message_guid from mongoDB.It runs in a loop and
	 * polls every 10 seconds to check on the status, until it times out.
	 */
	public static SortedMap<String, String> getActualCDMList(String msg_guid, String nodePath, String nodeName,
			String type) throws Exception {
		int procTime = Integer.parseInt(EhcoreAPIConstants.CDM_MSG_PROC_TIME);// (EHCoreTestConfigReader.getConfigItemValue(EHCoreTestConsts.UtilConsts.CDM_MSG_PROC_TIME));

		// check status every 10 seconds until - either the desired status has been
		// found or time-out happened.
		SortedMap<String, String> sortedMap = new TreeMap<String, String>();
		int pollTimeInSecs = 10;
		int i = 0;

		long t_beg, t_end;
		t_beg = System.currentTimeMillis();
		while (i < procTime / pollTimeInSecs) {

			sortedMap = EhcoreMongoDBUtils.checkCDMRetrieve(msg_guid, nodePath, nodeName);
			if (sortedMap.size() > 0 || (type.equalsIgnoreCase("NoUpdates") && (i == 1))) {
				break;
			} else {
				sleepMessage(pollTimeInSecs);
			}
			i++;
		}
		t_end = System.currentTimeMillis();

		Log4jUtil.log("Total time taken to get the actual CDM List from mongoDB ::" + (t_end - t_beg) / 1000);

		return sortedMap;
	}

	/**
	 * This Method is used to compare the actual CDM with expected using XMLUnit .
	 */
	public static void compareCDMList(String type, SortedMap<String, String> actualCDM) {

		List<String> expectedCDM = new ArrayList<String>();

		// Order the expected Canonical Message into a List based on the value of 'Root'
		// node.

		if (type.equalsIgnoreCase(EhcoreAPIConstants.addentityCCD)) {
			expectedCDM.add(0, EhcoreAPIConstants.ADD_ENTITY_CCD_RES + "CDM_CCDExchange1_AddEntity.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.addelementCCD)) {
			expectedCDM.add(0, EhcoreAPIConstants.ADD_ELEMENT_CCD_RES + "CDM_CCDExchange1_AddElement.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.updateCCD)) {
			expectedCDM.add(0, EhcoreAPIConstants.ADD_ENTITY_CCD_RES + "CDM_CCDExchange1_Update.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.deleteCCD)) {
			expectedCDM.add(0, EhcoreAPIConstants.ADD_ENTITY_CCD_RES + "delete_Immunization1.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.NEW_CCD)) {
			expectedCDM.add(0, CCDImportConstants.EXPECTED_CDMLIST + "CDM_CCDExchange1_New.xml");
		} /*
			 * else if(type.equalsIgnoreCase("NoUpdates")){
			 * expectedCDM.add(0,UtilConsts.EXPECTED_CDMLIST +
			 * "CDM_CCDExchange1_NoUpdates.xml"); }
			 */

		// Consolidated CCD
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.addentityC_CCD)) {
			expectedCDM.add(0, EhcoreAPIConstants.ADD_ENTITY_C_CCD_RES + "C_CDM_AddEntity.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.updateC_CCD)) {
			expectedCDM.add(0, EhcoreAPIConstants.ADD_ENTITY_C_CCD_RES + "C_CDM_Update.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.newC_CCD)) {
			expectedCDM.add(0, CCDImportConstants.EXPECTED_C_CDMLIST + "C_CDM_New.xml");
		} else if (type.equalsIgnoreCase(EhcoreAPIConstants.NOKNOWN_C_CCD)) {
			expectedCDM.add(0, CCDImportConstants.EXPECTED_C_CDMLIST + "C_CDM_NoKnown.xml");
		}
		compareXml(expectedCDM, actualCDM);
	}

	public static void compareXml(List<String> expectedList, SortedMap<String, String> actualList) {
		int count = 0;

		Log4jUtil.log("Sorted Map...");
		for (Map.Entry<String, String> entry : actualList.entrySet()) {
			Log4jUtil.log("Key : " + entry.getKey());
			String actualCdmFileName = null;
			String actualCdm = null;
			// Save actual to a file.
			actualCdm = entry.getValue().toString();
			actualCdmFileName = DataJobConstant.SAMPLE_CDM_LIST + "actual.xml";
			try {
				FileUtils.writeStringToFile(new File(actualCdmFileName), actualCdm, false);

			} catch (IOException e) {
				fail(e.getMessage(), e);
				fail("Failed to write actualCdm to file. " + e.getMessage());
			}
			String expectedCdmFileName = expectedList.get(count);
			EhcoreXmlUnitUtil.assertEqualsXML(expectedCdmFileName, actualCdmFileName);
			count++;
		}
	}

	private static String getDeltaResponseURL() {
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":" + EhcoreAPIConstants.PORT
				+ EhcoreAPIConstants.WS_URL + EhcoreAPIConstants.DELTA_RESPONSE;
	}

	private static String getNodeResponseURL() {
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":" + EhcoreAPIConstants.PORT
				+ EhcoreAPIConstants.WS_URL + EhcoreAPIConstants.NODE_RESPONSE;
	}

	public static AllscriptsMessageEnvelope sendAllscriptsImportMessage(String type) throws Exception {
		String url = null;
		String toXML = null;
		String expectedResponse = null;
		if (type.equalsIgnoreCase(CCDImportConstants.AS_CCD)) {

			url = getAllScriptsCCDImportURL();
			// toXML = UtilConsts.SAMPLE_ALLSCRIPTS_ADAPTER_CDDIMPORT_INPUT_DATA +
			// "AllScriptsAdapter_CCDImport_Input.xml";
			toXML = CCDImportConstants.AS_REQ + "Professional_AllisonReed_1.xml";
			expectedResponse = "OK";

		} else if (type == "invalidAllscriptsCCDImportMsg") {
			url = getAllScriptsCCDImportURL();
			toXML = CCDImportConstants.AS_INVALID_REQ + "AllScriptsAdapter_CCDImport_InvalidInput.xml";
			expectedResponse = EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST;
		}
		AllscriptsMessageEnvelope response = processAS_CCD(url, EhcoreAPIConstants.POST_REQUEST, toXML,
				expectedResponse);

		return response;
	}

	/**
	 * This Method is used to send the valid Export Message(CCD/Questionnaire) to
	 * EDI's rest interface
	 */
	public static ProcessingResponse sendCCDExportMessage(String value, String type) throws Exception {

		IHGUtil.PrintMethodName();

		String url = null;
		String xmlFile = null;
		String toXML = null;

		// EHDC:CCDExport -This Request is to check CCDExport Flow with Valid
		// and Invalid message
		if (type.equalsIgnoreCase("validExportMsg")) {
			Log4jUtil.log("**********validExportMsg");
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			Log4jUtil.log("***************VALID EXPORT MESSAGE**************" + url);
			xmlFile = CCDExportConstants.CCD_EXPORT_DATA + "CCDMessageType.xml";
			toXML = CCDExportConstants.SAMPLE_CCD_EXPORT_DATA + "testCCDMessageType.xml";
			updateCCDExport_Data(xmlFile, toXML, value, EhcoreAPIConstants.PORTAL_PERSONID_URN,
					EhcoreAPIConstants.PORTAL_PRACTICEID_URN, EhcoreAPIConstants.PORTAL_PROVIDERID_URN);
		} else if (type == "invalidExportMsg") {
			Log4jUtil.log("********** invalidExportMsg");
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			toXML = CCDExportConstants.INVALID_CCD_EXPORT_DATA + "InvalidCCDMessageType.xml";
		}

		// EHDC:QuestionnaireExport -This Request is to check
		// QuestionnaireExport Flow with Valid and Invalid message
		else if (type == "validQuestionnaireExportMsg") {

			Log4jUtil.log("********** validQuestionnaireExportMsg");
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			xmlFile = CCDExportConstants.QUESTIONNAIRE_EXPORT_DATA + "QuestionnaireMessage.xml";
			toXML = CCDExportConstants.SAMPLE_CCD_EXPORT_DATA + "testQuestionnaireMessage.xml";

			EhcoreAPIUtil.updateCCDExport_Data(xmlFile, toXML, "msgId", EhcoreAPIConstants.PORTAL_PERSONID_URN,
					EhcoreAPIConstants.PORTAL_PRACTICEID_URN, EhcoreAPIConstants.PORTAL_PROVIDERID_URN);
			// Validate Request xml against XSD

		} else if (type == "invalidKey_QuestionnaireExportMsg") {
			Log4jUtil.log("********** invalidKey_QuestionnaireExportMsg");
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			toXML = CCDExportConstants.QUESTIONNAIRE_EXPORT_DATA_INVALIDKEY + "QuestionnaireMessageInvalidKey.xml";
			// Validate Request xml against XSD

		} else if (type == "invalidQuestionnaireExportMsg") {
			Log4jUtil.log("********** invalidQuestionnaireExportMsg");
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			toXML = CCDExportConstants.INVALID_QUESTIONNAIRE_EXPORT_DATA + "InvalidQuestionnaireMessage.xml";
		}

		// Allscripts:CCDExport -This Request is to check CCDExport Flow with
		// Valid and Invalid message

		else if (type.equalsIgnoreCase(CCDExportConstants.AS_CCD_EXPORT)) {
			Log4jUtil.log("********** internal/exportccd");
			url = getAllScriptsCCDExportURL();
			toXML = CCDExportConstants.AS_CCDEXPORT_REQ + "AS_CCDExport_Request.xml";
		} else if (type == "invalidAllScriptsCCDExportMsg") {
			Log4jUtil.log("********** invalidAllScriptsCCDExportMsg");
			url = getAllScriptsCCDExportURL();
			toXML = CCDExportConstants.AS_CCDEXPORT_INVALID_REQ + "AllScriptsAdapter_CCDExport_InvalidInput.xml";
		}

		// Allscripts:Questionnaire Export -This Request is to check
		// QuestionnaireExport Flow with Valid and Invalid message

		else if (type == "validQuestionnaireAllscriptsExportMsg") {
			Log4jUtil.log("********** validQuestionnaireAllscriptsExportMsg");
			url = getAllScriptsFormsExportURL();
			toXML = CCDExportConstants.SAMPLE_ALLSCRIPTS_FORMS_EXPORT_INPUT_DATA + "AllscriptsInput_FormsExport.xml";
		} else if (type == "invalidQuestionnaireAllscriptsExportMsg") {
			Log4jUtil.log("********** invalidQuestionnaireAllscriptsExportMsg");
			url = getAllScriptsFormsExportURL();
			toXML = CCDExportConstants.INVALID_ALLSCRIPTS_EXPORT_DATA + "InvalidAllscriptsInput_FormsExport.xml";
		}

		ProcessingResponse response = processCCD_CheckResponse(url, EhcoreAPIConstants.POST_REQUEST, toXML, "",
				EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED, type);
		return response;
	}

	/**
	 * Get the URL (the common part of it for all messages) from the properties
	 * defined in config file
	 * 
	 */
	private static String getURL() {
		return EhcoreAPIConstants.PROTOCOL + "://" + EhcoreAPIConstants.HOST + ":" + EhcoreAPIConstants.PORT
				+ EhcoreAPIConstants.WS_URL;
	}

	public static ProcessingResponse sendMessage_EmptyStringValues(String djId, String type) throws Exception {

		String url = null;
		String xmlFile = null;
		if (type == "import") {
			url = getURL() + EhcoreAPIConstants.CCDImport;
			xmlFile = CCDImportConstants.CCD1 + "CCDExchangeWithEmptyStringUPN.xml";
		} else if (type == "export") {
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			xmlFile = CCDExportConstants.CCD_EXPORT_DATA + "CCDMessageTypeWithEmptyStringValues.xml";
		}
		/**
		 * The below code is to check Response for valid Questionnaire Export message
		 * with Empty String UPN and MsgId
		 */
		else if (type == "questionnaire_export") {
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			xmlFile = CCDExportConstants.QUESTIONNAIRE_EXPORT_DATA + "QuestionnaireMessageWithEmptyStringValues.xml";
		}
		ProcessingResponse response = processCCD_CheckResponse(url, EhcoreAPIConstants.POST_REQUEST, xmlFile, djId,
				EhcoreAPIConstants.EXPECTEDRESPONSE_ACCEPTED, type);
		return response;
	}

	public static void sendWithInvalidDetails(String djId) throws Exception {

		String url = getURL() + EhcoreAPIConstants.CCDImport;
		String xmlFile = CCDImportConstants.CCD + "CCDExchange1.xml";
		String toXML = CCDImportConstants.SAMPLE_CCD + "inputCCDExchange1.xml";

		updateCCD_Data(xmlFile, toXML, "valid");

		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, djId, "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	public static void sendMessage_InvalidUPN(String djId, String UPN, String expectedResponse) throws Exception {

		String url = getURL() + EhcoreAPIConstants.CCDImport;
		String xmlFile = CCDImportConstants.CCD + "CCDExchange1.xml";
		String toXML = CCDImportConstants.SAMPLE_CCD + "inputCCDExchange1.xml";

		updateCCD_Data(xmlFile, toXML, UPN);
		processRequestCCDMessage(url, EhcoreAPIConstants.POST_REQUEST, toXML, djId, expectedResponse);
	}

	/**
	 * This Method is used to send the invalidCCDMessage to check OUTBOUND flow(with
	 * null UPN,null MsgId)
	 */
	public static void sendInvalidCCDMessage(String UPN, String msgId, String type) throws Exception {

		String url = null;
		String xmlFile = null;
		String toXML = null;
		// EHDC:CCDExport -To check with invalid message
		if (type.equalsIgnoreCase("InvalidCCDExport")) {
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			xmlFile = CCDExportConstants.CCD_EXPORT_DATA + "CCDMessageType.xml";
			toXML = CCDExportConstants.SAMPLE_CCD_EXPORT_DATA + "testCCDMessageType.xml";
		}

		// EHDC:QuestionnaireExport -To check with invalid message
		else if (type.equalsIgnoreCase("InvalidQuestionnaireExport")) {
			url = getURL() + EhcoreAPIConstants.CCDEXPORT;
			xmlFile = CCDExportConstants.QUESTIONNAIRE_EXPORT_DATA + "QuestionnaireMessage.xml";
			toXML = CCDExportConstants.SAMPLE_CCD_EXPORT_DATA + "testCCDMessageType.xml";
		}

		EhcoreAPIUtil.updateCCDExport_Data(xmlFile, toXML, msgId, UPN, "samplePracticeId", "sampleProviderId");
		processRequest_invalid(url, EhcoreAPIConstants.POST_REQUEST, toXML, "", "valid",
				EhcoreAPIConstants.EXPECTEDRESPONSE_BADREQUEST);
	}

	/**
	 * Use this method to send the updated version of CCDEXchange.
	 */
	public static ProcessingResponse sendUpdatedMessage(String djId, String UPN, String type) throws Exception {

		String url = getURL() + EhcoreAPIConstants.CCDImport;
		String toXML = EhcoreAPIConstants.SAMPLE_UPDATE_CCD + "update_ValidCCD.xml";
		String xmlFile = null;
		if (type.equalsIgnoreCase(EhcoreAPIConstants.addentityCCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_CCD_REQ + "add_entity_CCDExchange1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.addelementCCD))
			xmlFile = EhcoreAPIConstants.ADD_ELEMENT_CCD_REQ + "add_element_CCDExchange1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.updateCCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_CCD_REQ + "update_CCDExchange1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.deleteCCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_CCD_REQ + "delete_entity_CCDExchange1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.delete2CCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_CCD_REQ + "delete2_entity_CCDExchange1.xml";
		else if (type == "NoUpdates")
			xmlFile = CCDImportConstants.CCD + "CCDExchange1.xml";
		// Consolidated CCD
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.addentityC_CCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_C_CCD_REQ + "add_entity_CCCD1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.addelementC_CCD))
			xmlFile = EhcoreAPIConstants.ADD_ELEMENT_C_CCD_REQ + "add_element_CCCD1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.updateC_CCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_C_CCD_REQ + "update_CCCD1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.deleteC_CCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_C_CCD_REQ + "delete_entity_CCCD1.xml";
		else if (type.equalsIgnoreCase(EhcoreAPIConstants.delete2C_CCD))
			xmlFile = EhcoreAPIConstants.ADD_ENTITY_C_CCD_REQ + "delete2_entity_CCCD1.xml";
		updateCCD_Data(xmlFile, toXML, UPN);

		ProcessingResponse response = processCCD_CheckResponse(url, EhcoreAPIConstants.POST_REQUEST, toXML, djId,
				EhcoreAPIConstants.expectedResponse_Accepted, type);
		return response;

	}

}
