//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.support.utils;

import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openqa.selenium.WebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil.BrowserType;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.WebPoster;
import com.medfusion.pojos.CcdType;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class SupportUtil extends IHGUtil {

	protected WebDriver driver;

	public static int timeout = 0;
	public static String[] exeArg = null;
	public static final int WAIT_INCR = 500; 

	public SupportUtil(WebDriver driver) {
		super(driver);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	public static void DeleteAllBrowsingDataIE(WebDriver driver) throws Exception {

		if (TestConfig.getBrowserType() == BrowserType.iexplore) {
			System.out.println("Browser is IE");

			Runtime rt = Runtime.getRuntime();
			// Clear temporary Internet files
			Process proc8 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 8");
			proc8.waitFor();
			// Clear Cookies
			Process proc2 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 2");
			proc2.waitFor();
			// Clear History
			Process proc1 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 1");
			proc1.waitFor();

			// Delete ALL
			Process proc255 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
			proc255.waitFor();

			// Delete Password
			Process proc32 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 32");
			proc32.waitFor();

			// Delete Form data
			Process proc16 = rt.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 16");
			proc16.waitFor();

		}

		else
			System.out.println("Browser is NOT IE");
	}

	public void ccdImportFromAllScripts(CcdType ccdType, String allScriptAdapterURL, String env) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();

		assertNotNull("### Test property PHR_ALLSCRIPTS_ADAPTER_URL not defined", allScriptAdapterURL);

		log("Before setting the allscript adapter url#####");
		poster.setServiceUrl(allScriptAdapterURL.trim());

		log("Before setting the content type#####");
		poster.setContentType("text/xml;charset=UTF-8");

		log("Before setting Expected codee#####");
		poster.setExpectedStatusCode(200); // HTTP Status Code

		log("Add headers #####");
		poster.addHeader("Noun", "Encounter");
		poster.addHeader("Verb", "Completed");

		if (ccdType == CcdType.CONSOLIDATED_CCD) {

			log("send consolidated CCD #####");
			poster.postFromResourceFile("testfiles/" + env + "/ccd/C-ccd.xml");

			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000);

		} else if (ccdType == CcdType.NON_CONSOLIDATED_CCD) {

			log("send non consolidated CCD. Sharing with another doctor is not possible for non C-CCD #####");
			poster.postFromResourceFile("testfiles/" + env + "/ccd/NonC-ccd.xml");
			log("sleep(10000) :- Need time to let system process CCD #####");
			Thread.sleep(10000);
		}


	}

	public String getFilepath(String directoryName) throws Exception {

		String filePath = "";
		File targetDataDrivenFile = null;
		targetDataDrivenFile =
				new File(TestConfig.getTestRoot() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + directoryName);

		// To extract the excel sheet from the jar and use it

		if (targetDataDrivenFile.exists()) {
			filePath = String.valueOf(targetDataDrivenFile.toString() + File.separator).trim();
		} else {
			new File(targetDataDrivenFile.getParent() + "/" + directoryName + "/").mkdirs();
			File destination = new File(targetDataDrivenFile.getParent() + "/" + directoryName + "/");
			copyResourcesRecursively(super.getClass().getResource("/" + directoryName + "/"), destination);
			filePath = String.valueOf(destination.toString()).trim();

		}
		return filePath;
	}
	
	public static boolean copyResourcesRecursively(final URL originUrl, final File destination) {
		try {
			final URLConnection urlConnection = originUrl.openConnection();
			if (urlConnection instanceof JarURLConnection) {
				return ReadFilePath.copyJarResourcesRecursively(destination, (JarURLConnection) urlConnection);
			} else {
				return ReadFilePath.copyFilesRecusively(new File(originUrl.getPath()), destination);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public String[] getExeArg() {
		return exeArg;
	}

	public HashMap<String, List<String>> getTestData(String xlFilePath, String sheetName, String tableName) throws Exception {

		HashMap<String, List<String>> hm = new HashMap<String, List<String>>();

		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol;
		Cell tableStart = sheet.findCell(tableName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();

		Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);

		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();

		for (int iCol = startCol + 1; iCol < endCol; iCol++) {
			List<String> ls = new ArrayList<String>();
			for (int iRow = startRow + 1; iRow < endRow; iRow++) {
				ls.add(sheet.getCell(iCol, iRow).getContents());
			}
			if (ls.contains("<END>")) {
				ls = ls.subList(0, ls.indexOf("<END>"));
			}
			hm.put(sheet.getCell(iCol, startRow).getContents(), ls);
		}
		return (hm);
	}

	public List<String> getTestDataList(HashMap<String, List<String>> td, String tableColumn) throws Exception {
		List<String> tdList = null;
		tdList = td.get(tableColumn);
		if (tdList == null) {
			throw new Exception("Table column- " + tableColumn
					+ " not found in the mentioned table name, Please ensure the table name and column name are exacly same as mentioned in excel");
		} else {
			return tdList;
		}
	}

	public HashMap<String, List<String>> getPatientsDetails() throws Exception {

		return getTestData(TestConfig.getDataDrivenSpreadsheet(), "Support", "PatientDetails");

	}

	public List<String> getPatientData(String tableColumn) throws Exception {
		List<String> tdList = null;
		HashMap<String, List<String>> td = getPatientsDetails();
		tdList = td.get(tableColumn);
		if (tdList == null) {
			throw new Exception("Table column- " + tableColumn
					+ " not found in the mentioned table name, Please ensure the table name and column name are exacly same as mentioned in excel");
		} else {
			return tdList;
		}
	}

	// Returns the contents of the file in a byte array.

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static void lineBreak() {
		System.out.println("\n------------------------------------------------------------\n");
	}


	public static String updateXML(String xmlInString, String tagName, String xmlValuestoChange, String value) throws Exception {

		String requiredTag = "<" + tagName + ">" + xmlValuestoChange + "</" + tagName + ">";
		String replacingTag = "<" + tagName + ">" + value + "</" + tagName + ">";
		if (xmlInString.contains(requiredTag)) {
			xmlInString = xmlInString.replace(requiredTag, replacingTag);
			System.out.println("XML Updated with " + tagName + " and value is:" + replacingTag);
		} else {
			System.out.println("****The TagName " + tagName + " is not found in the XML");
		}

		return xmlInString;
	}


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

	public static String convertXMLFileToString(String fileName) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String fileToString(String xmlFilePath) {
		String xmlInString = convertXMLFileToString(xmlFilePath);
		return xmlInString;
	}

	public static void postCCD1Request(String url, String xmlInString) throws Exception {

		IHGUtil.PrintMethodName();

		WebPoster poster = new WebPoster();
		Log4jUtil.log("Before setting the allscript adapter url#####");
		poster.setServiceUrl(url.trim());

		Log4jUtil.log("Before setting the content type#####");
		poster.setContentType("text/xml;charset=UTF-8");

		Log4jUtil.log("Before setting Expected codee#####");
		poster.setExpectedStatusCode(200); // HTTP Status Code

		Log4jUtil.log("Add headers #####");
		poster.addHeader("Noun", "Encounter");
		poster.addHeader("Verb", "Completed");

		String responseXML_Str = poster.smpostFromResourceFile(url, xmlInString);
		Log4jUtil.log("sleep(10000) :- Need time to let system process CCD #####");
		Thread.sleep(10000);
		System.out.println("RESPONSE XML****" + responseXML_Str);

	}
}
