// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.phr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.lang.StringUtils;
import com.intuit.ifs.csscat.core.TestConfig;


public class ReadFilePath {

	public String getExcelSheetFilepath(String Filename) throws Exception {
		String filePath = "";
		File targetDataDrivenFile = null;
		targetDataDrivenFile = new File(TestConfig.getTestRoot() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator
				+ "data-driven" + File.separator + Filename);

		// To extract the excel sheet from the jar and use it

		if (targetDataDrivenFile.exists()) {
			filePath = String.valueOf(targetDataDrivenFile.toString()).trim();
		} else {
			InputStream reader = null;
			reader = getClass().getResourceAsStream("/data-driven/" + Filename);
			// + Filename
			if (reader.available() > 0) {

				new File(targetDataDrivenFile.getParent()).mkdirs();
				FileOutputStream writer = new FileOutputStream(targetDataDrivenFile);
				int ch = reader.read();
				while (ch != -1) {
					writer.write(ch);
					ch = reader.read();
				}
				writer.close();
				reader.close();
				filePath = String.valueOf(targetDataDrivenFile.getAbsolutePath().toString()).trim();

			} else {
				throw new Exception("Cannot find Excel file in the jar");
			}
		}

		return filePath;
	}

	public String getPropertiesFilepath(String Filename) throws Exception {
		String filePath = "";
		File targetDataDrivenFile = null;
		targetDataDrivenFile = new File(TestConfig.getTestRoot() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator
				+ "data-driven" + File.separator + Filename);

		// To extract the properties sheet from the jar and use it

		if (targetDataDrivenFile.exists()) {
			filePath = String.valueOf(targetDataDrivenFile.toString()).trim();
		} else {
			InputStream reader = null;
			reader = getClass().getResourceAsStream("/data-driven/" + Filename);
			// + Filename
			if (reader.available() > 0) {

				new File(targetDataDrivenFile.getParent()).mkdirs();
				FileOutputStream writer = new FileOutputStream(targetDataDrivenFile);
				int ch = reader.read();
				while (ch != -1) {
					writer.write(ch);
					ch = reader.read();
				}
				writer.close();
				reader.close();
				filePath = String.valueOf(targetDataDrivenFile.getAbsolutePath().toString()).trim();

			} else {
				throw new Exception("Cannot find Excel file in the jar");
			}
		}

		return filePath;
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

	public static boolean copyFile(final File toCopy, final File destFile) {
		try {
			return ReadFilePath.copyStream(new FileInputStream(toCopy), new FileOutputStream(destFile));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean copyFilesRecusively(final File toCopy, final File destDir) {
		assert destDir.isDirectory();

		if (!toCopy.isDirectory()) {
			return ReadFilePath.copyFile(toCopy, new File(destDir, toCopy.getName()));
		} else {
			final File newDestDir = new File(destDir, toCopy.getName());
			if (!newDestDir.exists() && !newDestDir.mkdir()) {
				return false;
			}
			for (final File child : toCopy.listFiles()) {
				if (!ReadFilePath.copyFilesRecusively(child, newDestDir)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean copyJarResourcesRecursively(final File destDir, final JarURLConnection jarConnection) throws IOException {

		final JarFile jarFile = jarConnection.getJarFile();

		for (final Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
			final JarEntry entry = e.nextElement();
			if (entry.getName().startsWith(jarConnection.getEntryName())) {
				final String filename = StringUtils.removeStart(entry.getName(), //
						jarConnection.getEntryName());

				final File f = new File(destDir, filename);
				if (!entry.isDirectory()) {
					final InputStream entryInputStream = jarFile.getInputStream(entry);
					if (!ReadFilePath.copyStream(entryInputStream, f)) {
						return false;
					}
					entryInputStream.close();
				} else {
					if (!ReadFilePath.ensureDirectoryExists(f)) {
						throw new IOException("Could not create directory: " + f.getAbsolutePath());
					}
				}
			}
		}
		return true;
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

	private static boolean copyStream(final InputStream is, final File f) {
		try {
			return ReadFilePath.copyStream(is, new FileOutputStream(f));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean copyStream(final InputStream is, final OutputStream os) {
		try {
			final byte[] buf = new byte[1024];

			int len = 0;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			is.close();
			os.close();
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean ensureDirectoryExists(final File f) {
		return f.exists() || f.mkdir();
	}



}
