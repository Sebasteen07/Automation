package com.intuit.ihg.product.integrationplatform.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
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
	
	
	public static String base64Encoder(String sourceFile, boolean isChunked) throws Exception {
		byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);
		String EncodedFile = new String(base64EncodedData, StandardCharsets.UTF_8);
		return EncodedFile;
	}
	
    public static byte[] loadFileAsBytesArray(String fileName) throws Exception {
    	 
        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;
 
    }
}