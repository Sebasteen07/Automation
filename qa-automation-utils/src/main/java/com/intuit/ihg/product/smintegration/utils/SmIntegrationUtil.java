package com.intuit.ihg.product.smintegration.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.WebPoster;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author bkrishnankutty
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */
public class SmIntegrationUtil extends IHGUtil {

	protected WebDriver driver;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public SmIntegrationUtil(WebDriver driver) {
		super(driver);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Get the driver
	 * @return WebDriver
	 * @param driver
	 */
	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Set the Site Gen Frame
	 * @return void
	 * @param driver
	 */
	public static void setSiteGenFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- set Default Frame
	 * @return void
	 * @param driver
	 */
	public static void setDefaultFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- For setting generic frame
	 * @return void
	 * @param driver
	 * @param frame
	 */
	public static void setFrame(WebDriver driver, String frame) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, frame);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- For setting Consolidated frame ,Here 2 frames iframebody &
	 *         externalframe
	 * @return void
	 * @param driver
	 * @param frame
	 */
	public static void setConsolidatedInboxFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();

		List<String> frames = new ArrayList<String>();
		frames.add("iframebody");
		frames.add("externalframe");

		IHGUtil.setFrameChain(driver, frames);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Verify text ,Note :- this fun is already present in IFS but
	 *         cannot be used pages,So redefining it
	 * @return true or false
	 * 
	 * @param driver
	 * @param value
	 * @param waitTime
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyTextPresent(WebDriver driver, String value,
			int waitTime) throws Exception {
		Thread.sleep(waitTime);
		return driver.getPageSource().contains(value);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- for dealing with browser alerts
	 * @return void
	 * @param driver
	 */
	public void checkAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			log("Alert detected: {}" + alert.getText());
			alert.accept();

		} catch (Exception e) {
			// exception handling
			log("no alert was present");
		}
	}

	/**
	 * @author Kiran_GT
	 * @Description:- This method returns the jdbc connection for the database
	 *                needed
	 * 
	 * @param dbName
	 *            Database name
	 * @param environment
	 *            Environment which has the needed database
	 * @return Connection Object instance for the required database
	 */
	public static Connection getDatabaseConnection(String dbName,
			String environment, String dbUsername, String dbPassword)
			throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		String url = ("jdbc:postgresql://" + environment + ":5432/" + dbName);
		System.out.println("DB url : " + url);
		connection = DriverManager.getConnection(url, dbUsername, dbPassword);
		return connection;
	}

	/**
	 * 
	 * @param dbName
	 * @param envi
	 * @return
	 * @throws Exception
	 */
	public static Connection getDBConnection(String dbName) throws Exception {
		Log4jUtil.log("Entering into getDBConnection");
		Log4jUtil.log("Getting DB Connection for Database :" + dbName
				+ " QA Region :");
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		String url = "jdbc:postgresql://db02." + ".dev.medfusion.net:5432/"
				+ dbName.trim();
		connection = DriverManager.getConnection(url, "postgres", "pgdb1");
		Log4jUtil.log("DB Connection is Successful");
		Log4jUtil.log("Returning from getDBConnection");
		return connection;

	}
	
	/**
	 * @author Vasudeo Parab
	 * @Descrption:- - This method retrieves Appointment request ID.
	 * 
	 */
	public static String  getAppointmentRequestid(String vAppointmentReason,String dbName, String qaRegion, String dbusername, String dbPassword) throws Exception{
	 	String appointmentrequestid= null;
	 	Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
	 	try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from apptreq where reason=? order by apptreqid desc limit 1;");
			Log4jUtil.log("PASSING APPOINTMENT REASON: " + vAppointmentReason);
			stmt.setString(1, vAppointmentReason);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(10000);
			while (rs.next()) {
				appointmentrequestid = rs
						.getString(SmIntegrationConstants.APPOINTTMENT_REQUEST_ID);
				
				Log4jUtil.log("APPOINTMENT REQUEST ID ---->" + appointmentrequestid);
			
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("APPOINTMENT REQUEST ID returned " + appointmentrequestid);
		return appointmentrequestid;
	
 		}
	/**
	 * @author Kiran_GT
	 * @Descrption:- - This method retrieves intchangeintegration status.
	 * 
	 * @param dbName
	 *            - DB Name either Medfusion or Service Mediator
	 * @param qaRegion
	 *            - QA region is "dev3-portal-pgsql01.mf.qhg.local"
	 * @param dbusername
	 *            and dbPassword
	 * @return
	 * @throws Exception
	 */
	public static String getintegrationStatusIDFromDB(int integrationID,
			String dbName, String qaRegion, String dbusername, String dbPassword)
			throws Exception {
		String integrationStatusId = null;
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from intchangeintegration where intintegrationid=? order by intchangeintegrationid desc limit 1;");
			Log4jUtil.log("PASSING INTEGRATIONID" + integrationID);
			stmt.setInt(1, integrationID);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(10000);
			while (rs.next()) {
				integrationStatusId = rs
						.getString(SmIntegrationConstants.INTEGRATIONSTATUS_ID_COLUMN);
				Log4jUtil.log("INTEGRATION STATUS ---->" + integrationStatusId);
				// BaseTestSoftAssert.assertEquals(integrationStatusId,SmIntegrationConstants.INTEGRATIONSTATUS_ID);
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("INTEGRATION STATUS returned " + integrationStatusId);
		return integrationStatusId;
	}

	/**
	 * @author Kiran_GT
	 * @Descrption:- - This method retrieves PartnerIntegration Status and State
	 *               Value.
	 * 
	 * @param dbName
	 *            - DB Name either Medfusion or Service Mediator
	 * @param qaRegion
	 *            - QA region is "dev3-portal-pgsql01.mf.qhg.local"
	 * @param dbusername
	 *            and dbPassword
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> getPartnerIntegrationIDFromDB(String app_req_id,
			String dbName, String qaRegion, String dbusername, String dbPassword)
			throws Exception {
		Thread.sleep(180000);
		String ppia_status = null;
		String ppia_state = null;
		long ppia_id = 0;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from practice_partner_integration_activity where ppia_ppi_id=3002 and ppia_request_id=? ;");
			stmt.setString(1, app_req_id);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(10000);
			while (rs.next()) {
				ppia_status = rs
						.getString(SmIntegrationConstants.PPIA_STATUS_COLUMN);
				ppia_state = rs
						.getString(SmIntegrationConstants.PPIA_STATE_COLUMN);
				ppia_id = rs.getLong(SmIntegrationConstants.PPIA_ID_COLUMN);				
				hashMap.put("Status", ppia_status);
				hashMap.put("State", ppia_state);
				hashMap.put("ppia_id", Long.toString(ppia_id));
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("PPIA STATUS " + ppia_status);
		Log4jUtil.log("PPIA STATE " + ppia_state);
		Log4jUtil.log("PPIA ID" + ppia_id);
		return hashMap;
	}

	/**
	 * @author bbinisha
	 * @Descrption:- - This method retrieves member id from DB.
	 * 
	 * @param dbName
	 *            - DB Name either Medfusion or Service Mediator
	 * @param qaRegion
	 *            - QA region is "dev3-portal-pgsql01.mf.qhg.local"
	 * @param dbusername
	 *            and dbPassword
	 * @return
	 * @throws Exception
	 */
	public static String getMemberIDFromDB(String emailID, String dbName,
			String qaRegion, String dbusername, String dbPassword)
			throws Exception {
		String memberID = null;
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from member where email=? ;");
			Log4jUtil.log("PASSING LASTNAME***" + emailID);
			stmt.setString(1, emailID);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(10000);
			while (rs.next()) {
				memberID = rs.getString(SmIntegrationConstants.MEMBERID_COLUMN);
				Log4jUtil.log("MEMBER ID ---->" + memberID);
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("MEMBER ID  returned" + memberID);
		return memberID;
	}

	/**
	 * @author bbinisha
	 * @Descrption:- - This method retrieves ppia_request_xml from db.
	 * 
	 * @param dbName
	 *            - DB Name either Medfusion or Service Mediator
	 * @param qaRegion
	 *            - QA region is "dev3-portal-pgsql01.mf.qhg.local"
	 * @param dbusername
	 *            and dbPassword
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> getPPIAReqXMLFromDB(int ppia_ppi_id,
			String dbName, String qaRegion, String dbusername, String dbPassword)
			throws Exception {
		String ppia_status = null;
		String ppia_state = null;
		String ppia_request_xml = null;
		long ppia_id = 0;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from practice_partner_integration_activity where ppia_ppi_id=? order by ppia_id desc limit 1;");
			stmt.setInt(1, ppia_ppi_id);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(30000);
			while (rs.next()) {
				ppia_status = rs
						.getString(SmIntegrationConstants.PPIA_STATUS_COLUMN);
				ppia_state = rs
						.getString(SmIntegrationConstants.PPIA_STATE_COLUMN);
				ppia_request_xml = rs
						.getString(SmIntegrationConstants.PPIA_REQUEST_XML_COLUMN);
				ppia_id = rs.getLong(SmIntegrationConstants.PPIA_ID_COLUMN);

				hashMap.put("Status", ppia_status);
				hashMap.put("State", ppia_state);
				hashMap.put("ppia_request_xml", ppia_request_xml);
				hashMap.put("ppia_id", Long.toString(ppia_id));
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("PPIA STATUS" + ppia_status);
		Log4jUtil.log("PPIA STATE" + ppia_state);
		Log4jUtil.log("PPIA REQUEST XML" + ppia_request_xml);
		Log4jUtil.log("PPIA ID" + ppia_id);
		return hashMap;
	}

	/**
	 * 
	 * @param xmlFilePath
	 * @return
	 */
	public String fileToString(String xmlFilePath) {
		String xmlInString = convertXMLFileToString(xmlFilePath);
		return xmlInString;
	}

	/**
	 * 
	 * @param xmlInString
	 * @param tagName
	 * @param xmlValuestoChange
	 * @param value
	 * @return
	 * @throws Exception
	 */

	public String updateXML(String xmlInString, String tagName,
			String xmlValuestoChange, String value) throws Exception {

		String requiredTag = "<" + tagName + ">" + xmlValuestoChange + "</"
				+ tagName + ">";
		String replacingTag = "<" + tagName + ">" + value + "</" + tagName
				+ ">";
		if (xmlInString.contains(requiredTag)) {
			xmlInString = xmlInString.replace(requiredTag, replacingTag);
			System.out.println("XML Updated with " + tagName + " and value is:"+ replacingTag
					);
		} else {
			log("****The TagName " + tagName + " is not found in the XML");
		}

		return xmlInString;
	}

	/**
	 * @author bkrishnankutty
	 * @Method Description
	 * 
	 *         Post an XML string
	 * 
	 * @param url
	 * @param xmlInString
	 * @return
	 * @throws Exception
	 */

	public String postXMLRequest(String url, String xmlInString)
			throws Exception {

		IHGUtil.PrintMethodName();
		WebPoster poster = new WebPoster();

		Assert.assertNotNull("### Test URL not defined", url);

		log("Before setting the url for POST method #####");
		poster.setServiceUrl(url.trim());

		log("Setting Expected http response code#####");
		poster.setExpectedStatusCode(200); // HTTP Status Code

		log("POST the request xml");
		String responseXML_Str = poster
				.smpostFromResourceFile(url, xmlInString);

		return responseXML_Str;

	}

	/**
	 * @author bkrishnankutty
	 * @Method Description
	 * 
	 *         Write String Data to File
	 * 
	 * @param url
	 * @param xmlInString
	 * @return
	 * @throws Exception
	 */

	public void writeStringToXml(String xmlFilePath, String xmlString) {
		try {
			FileWriter out = new FileWriter(xmlFilePath);
			out.write(xmlString);
			if (out != null) {
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param xmlFileName
	 * @param parentNode
	 * @param childNode
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */

	public static String findValueOfGrandChildNode(String xmlFileName,
			String parentNode, String childNode)
			throws ParserConfigurationException, SAXException, IOException {
			
		IHGUtil.PrintMethodName();
		String nodeValue = null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();

		Node pnode = doc.getElementsByTagName(parentNode).item(0);
		NodeList list = pnode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
	            NodeList subList = list.item(0).getChildNodes();

	            if (subList != null && subList.getLength() > 0) {
	            	if(childNode.equalsIgnoreCase(subList.item(0).getTextContent()))
	            		Log4jUtil.log("Patient ID found in DB response XML");
	            		nodeValue =subList.item(0).getTextContent();	            		
					break;
				}

			}
		System.out.println("NODE VALUE returned"+nodeValue);
		return nodeValue;
	}
	           


	/**
	 * 
	 * @param xmlFileName
	 * @param parentNode
	 * @param childNode
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */

	public static String findValueOfChildNode(String xmlFileName,
			String parentNode, String childNode)
			throws ParserConfigurationException, SAXException, IOException {

		IHGUtil.PrintMethodName();
		String nodeValue = null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();

		Node pnode = doc.getElementsByTagName(parentNode).item(0);

		NodeList child = pnode.getChildNodes();
		// isEventPresent =child.item(0).getFirstChild().getTextContent() ;
		for (int i = 0; i < child.getLength(); i++) {
			Node node = child.item(i);
			Element element = (Element) node;
			if (childNode.equals(element.getNodeName())) {
				nodeValue = element.getTextContent();
				break;
			}

		}
		return nodeValue;

	}
	           

	/**
	 * 
	 * @param xmlFileName
	 * @param parentNode
	 * @param childNode
	 * @param attribute
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public  static String findAttributeOfChildNode(String xmlFileName,
			String parentNode, String childNode, String attribute)
			throws ParserConfigurationException, SAXException, IOException {

		IHGUtil.PrintMethodName();
		String nodeValue = null;
		File xmlResponeFile = new File(xmlFileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlResponeFile);
		doc.getDocumentElement().normalize();

		Node pnode = doc.getElementsByTagName(parentNode).item(0);

		NodeList child = pnode.getChildNodes();

		for (int i = 0; i < child.getLength(); i++) {
			Node node = child.item(i);
			if (childNode.equals(node.getNodeName())) {
				NamedNodeMap attr = node.getAttributes();
				Node nodeAttr = attr.getNamedItem(attribute);
				nodeValue = nodeAttr.getTextContent();
				break;
			}

		}
		return nodeValue;

	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */

	public static String convertXMLFileToString(String fileName) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory
					.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance()
					.newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author Kiran_GT
	 * @param dbName
	 * @param query
	 * @param qaRegion
	 * @param dbusername
	 * @param dbPassword
	 * @return
	 * @throws Exception
	 */
	public  static boolean getRowCount(String dbName, String query,
			String qaRegion, String dbusername, String dbPassword)
			throws Exception {
		int i = 0;
		boolean flag = false;
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(5000);
			while (rs.next()) {
				mainLoop:for(int count=1;count<=20;count++){
					i = rs.getRow();
					if(i>0){  
						flag=true;
						break mainLoop;
					} 
					else{
						Thread.sleep(8000);
					} 
					
				}
			}
			stmt.close();
			} catch (Exception e) {
			System.out.println("Error###########" + e);
		}
		return flag;
	}

	
	/**
	 * @throws Exception 
	 * 
	 */
	public static String getPPIDFromSM(String dbName,
			 String dbusername, String dbPassword, String qaRegion ) throws Exception {
		String ppia_id = null;
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,
				dbPassword);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from practice_partner_integration_activity where ppia_ppi_id=3002 order by ppia_id desc limit 1;");

			ResultSet rs = stmt.executeQuery();
			Thread.sleep(30000);
			while (rs.next()) {
				ppia_id = rs
						.getString(SmIntegrationConstants.PPIA_ID_COLUMN);
								
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("PPIA ID" + ppia_id);
		return ppia_id;
	}
	


	
	
	
	public String xmlTagWithValue(String xmlInString, String tagName,
			 String value) throws Exception {

		String requiredTag = "<" + tagName + ">" + value + "</"
				+ tagName + ">";
		return requiredTag;
	}
	
	public static String getPPIAReqXMLFromActivityLog(String ppia_id, String dbName, String qaRegion, String dbusername,
			String dbPassword) throws Exception {
		String ppial_response = null;
		Long l=Long.parseLong(ppia_id);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Connection conn = getDatabaseConnection(dbName, qaRegion, dbusername,dbPassword);
		try {
			PreparedStatement stmt = conn
					.prepareStatement("select * from practice_partner_integration_activity_log where ppial_ppia_id=? ");
			stmt.setLong(1,l);
			ResultSet rs = stmt.executeQuery();
			Thread.sleep(30000);
			while (rs.next()) {
				ppial_response = rs.getString(SmIntegrationConstants.PPIAL_RESPONSE_COLUMN);
				hashMap.put("PPIAL_RESPONSE", ppial_response);
			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("PPIAL_RESPONSE" + ppial_response);
		return ppial_response;
	}
	
}
