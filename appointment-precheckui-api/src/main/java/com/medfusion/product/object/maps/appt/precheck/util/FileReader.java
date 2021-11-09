package com.medfusion.product.object.maps.appt.precheck.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
	public String readJsonFile(String filePath) throws IOException {
		String readJsonPayload = new String(Files.readAllBytes(Paths.get(filePath)));
		return readJsonPayload;
	}
}
