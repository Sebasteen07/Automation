package com.intuit.ihg.product.integrationplatform.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

public class ExternalFileReader {

	public static String readFromFile(String filePath) throws IOException {
		String getTextFileString = null;
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			getTextFileString = sb.toString();
		} finally {
			br.close();
		}
		getTextFileString = StringEscapeUtils.unescapeXml(getTextFileString);
		return getTextFileString;
	}
}