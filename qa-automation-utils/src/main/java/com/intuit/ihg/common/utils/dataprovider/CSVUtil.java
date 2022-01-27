//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils.dataprovider;

import com.intuit.ihg.common.entities.TestObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CSVUtil {

	public static final String DOUBLE_QUOTE = "\"";
	public static final String DELIM_CHAR = ",";
	public static final String TAB_CHAR = "	";

	public static Iterator<Object[]> getDataFromCSVFile(Class<?> clazz, String filename, String[] fields, Filter filter,
			boolean readHeaders) {
		return getDataFromCSVFile(clazz, filename, fields, filter, readHeaders, null);
	}

	public static Iterator<Object[]> getDataFromCSVFile(Class<?> clazz, String filename, String[] fields, Filter filter,
			boolean readHeaders, String delimiter) {

		InputStream is = null;
		try {
			if (clazz != null)
				is = clazz.getResourceAsStream(filename);
			else
				is = new FileInputStream(filename);

			if (is == null)
				return new ArrayList<Object[]>().iterator();

			// Get the sheet
			String[][] csvData = read(is, delimiter);

			List<Object[]> sheetData = new ArrayList<Object[]>();
			if (readHeaders) {
				List<Object> rowData = new ArrayList<Object>();
				if (fields == null) {
					for (int j = 0; j < csvData[0].length; j++) {
						rowData.add(csvData[0][j]);
					}
				} else {
					for (int i = 0; i < fields.length; i++) {
						rowData.add(fields[i]);
					}
				}
				sheetData.add(rowData.toArray(new Object[rowData.size()]));
			}

			int testTitleColumnIndex = -1;
			int testEnvColumnIndex = -1;
			// Search for Title & Env column
			for (int i = 0; i < csvData[0].length; i++) {
				if (testTitleColumnIndex == -1 && TestObject.TEST_TITLE.equalsIgnoreCase(csvData[0][i])) {
					testTitleColumnIndex = i;
				} else if (testEnvColumnIndex == -1 && TestObject.TEST_ENV.equalsIgnoreCase(csvData[0][i])) {
					testEnvColumnIndex = i;
				}

				if (testTitleColumnIndex != -1 && testEnvColumnIndex != -1)
					break;
			}

			StringBuffer sbBlank = new StringBuffer();
			for (int i = 1; i < csvData.length; i++) {
				if (testTitleColumnIndex != -1 && testEnvColumnIndex != -1
						&& (csvData[i][testTitleColumnIndex].trim().length() == 0
								|| csvData[i][testEnvColumnIndex].trim().length() == 0)) {
					sbBlank.append(i + 1).append(',');
				}
			}
			if (sbBlank.length() > 0) {
				sbBlank.deleteCharAt(sbBlank.length() - 1);
				throw new Exception("Blank TestTitle and/or Env value(s) found on Row(s) " + sbBlank.toString() + ".");
			}

			Set<String> uniqueDataSet = new TreeSet<String>();

			for (int i = 1; i < csvData.length; i++) {

				// Check for duplicate Title & Env
				if (testTitleColumnIndex != -1 && testEnvColumnIndex != -1) {
					String uniqueString = csvData[i][testTitleColumnIndex] + "$$$$####$$$$"
							+ csvData[i][testEnvColumnIndex];
					if (uniqueDataSet.contains(uniqueString))
						throw new Exception("Duplicate TestTitle and Env combination found in the spreadsheet "
								+ "with TestTitle = {" + csvData[i][testTitleColumnIndex] + "} " + "and Env = {"
								+ csvData[i][testEnvColumnIndex] + "}");

					uniqueDataSet.add(uniqueString);
				}

				Map<String, Object> rowDataMap = new HashMap<String, Object>();
				List<Object> rowData = new ArrayList<Object>();

				for (int j = 0; j < csvData[i].length; j++) {
					rowDataMap.put(csvData[0][j], csvData[i][j]);
				}

				if (fields == null) {
					for (int j = 0; j < csvData[0].length; j++) {
						if (csvData[i].length > j)
							rowData.add(csvData[i][j]);
						else
							rowData.add(null);
					}
				} else {
					for (int k = 0; k < fields.length; k++) {
						rowData.add(ExcelSheetUtil.getValue(rowDataMap, fields[k]));
					}
				}

				if (filter == null || filter.match(rowDataMap)) {
					sheetData.add(rowData.toArray(new Object[rowData.size()]));
				}
			}

			if ((!readHeaders && sheetData.isEmpty()) || (readHeaders && sheetData.size() <= 1))
				System.out.println("No matching data found on csv file: " + filename + " with filter criteria: "
						+ filter.toString());

			return sheetData.iterator();
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				} 
			}
		}
	}

	public static String[] parseLine(String line, String delim) {
		if (line == null || line.trim().length() == 0) {
			return null;
		}

		List<String> tokenList = new ArrayList<String>();
		String[] result = null;

		String[] tokens = line.split(delim);
		int count = 0;
		while (count < tokens.length) {
			if (tokens[count] == null || tokens[count].length() == 0) {
				tokenList.add("");
				count++;
				continue;
			}

			if (tokens[count].startsWith(DOUBLE_QUOTE)) {
				StringBuffer sbToken = new StringBuffer(tokens[count].substring(1));
				while (count < tokens.length && !tokens[count].endsWith(DOUBLE_QUOTE)) {
					count++;
					sbToken.append(DELIM_CHAR).append(tokens[count]);
				}
				sbToken.deleteCharAt(sbToken.length() - 1);
				tokenList.add(sbToken.toString());
			} else {
				tokenList.add(tokens[count]);
			}
			count++;
		}

		if (tokenList.size() > 0) {
			result = new String[tokenList.size()];
			tokenList.toArray(result);
		}
		return result;

	}

	public static String[][] read(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		return read(fis);
	}

	public static String[][] read(InputStream is) throws IOException {
		return read(is, null);
	}

	public static String[][] read(InputStream is, String delim) throws IOException {

		String[][] result = null;
		List<String[]> list = new ArrayList<String[]>();
		String inputLine; // String that holds current file line

		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); // KEEPME
		while ((inputLine = reader.readLine()) != null) {
			try {
				String[] item = null;
				if (delim == null)
					item = parseLine(inputLine, DELIM_CHAR);
				else
					item = parseLine(inputLine, delim);
				if (item != null)
					list.add(item);
			} catch (Exception e) {// KEEPME
			}
		}
		reader.close();

		if (list.size() > 0) {
			result = new String[list.size()][];
			list.toArray(result);
		}
		return result;
	}

	public static String[][] read(URL url) throws IOException {
		URLConnection con = url.openConnection();
		return read(con.getInputStream());
	}
}
