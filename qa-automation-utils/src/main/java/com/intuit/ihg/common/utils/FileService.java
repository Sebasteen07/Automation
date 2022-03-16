// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils;

import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public final class FileService {

    private static final Logger LOGGER = LogUtils.getLoggerForThisClass();
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private FileService() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Note: the javadoc of Files.readAllLines says it's intended for small
     * files. But its implementation uses buffering, so it's likely good
     * even for fairly large files.
     *
     * @param filePath Name of the file to read, including the path
     * @return List of Strings representing lines in the file
     */
    public static List<String> readSmallTextFile(String filePath) {
        return readSmallTextFile(filePath, UTF_8);
    }

    /**
     * Note: the javadoc of Files.readAllLines says it's intended for small
     * files. But its implementation uses buffering, so it's likely good
     * even for fairly large files.
     *
     * @param filePath Name of the file to read, including the path
     * @param charset {@link Charset} Charset to read text file as. 
     * @return List of Strings representing lines in the file
     */
    public static List<String> readSmallTextFile(String filePath, Charset charset) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllLines(path, charset);
        } catch (IOException e) {
            LOGGER.error("Failed to read file: " + filePath, e);
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Returns text in a file as a String.
     *
     * @param filePath String path of file.
     * @return String text within file.
     */
    public static String readFileAsString(String filePath) {
        List<String> fileLines = readSmallTextFile(filePath);
        StringBuilder allLines = new StringBuilder();

        for (String line : fileLines) {
            allLines.append(line);
        }

        return allLines.toString();
    }

    /**
     * Convert the response to a ByteArray and write it to a file
     *
     * @param filePath path/file.ext
     * @param response The response from an API call
     * @return File
     */
    public static File writeResponseToFile(String filePath, Response response) {
        byte[] byteArray = response.getBody().asByteArray();
        return writeByteArrayToFile(filePath, byteArray);
    }

    /**
     * Writes an array of bytes to the specified filepath. If file already exists at filepath, it is deleted first.
     *
     * @param filePath  String of filepath to write to.
     * @param byteArray Array of bytes to write.
     * @return The created file.
     */
    public static File writeByteArrayToFile(String filePath, byte[] byteArray) {
        LOGGER.trace("writeByteArrayToFile started");
        File file = new File(filePath);
        OutputStream outStream = null;
        try {
            //remove file if already exists
            if (file.exists()) {
                Files.delete(Paths.get(filePath));
                LOGGER.info("Deleted file: " + filePath);
            }

            outStream = new FileOutputStream(file);
            outStream.write(byteArray);
            LOGGER.info("Wrote byte array to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Assert.fail(e.getMessage());
                }
            }
        }
        LOGGER.trace("writeByteArrayToFile completed");
        return file;
    }

    /**
     * Deletes all files in a directory.
     *
     * @param directoryPath String path of directory.
     */
    public static void deleteAllFilesInDirectory(String directoryPath) {
        LOGGER.trace("deleteAllFilesInDirectory started");
        List<File> files = getAllFilesInDirectory(directoryPath);

        int count = 0;
        for (File file : files) {
            boolean deleted = file.delete();
            if (deleted) {
                count++;
                LOGGER.debug("Deleted file: " + file);
            }
        }
        LOGGER.info("Deleted " + count + " file(s) in directory: " + directoryPath);
        LOGGER.trace("deleteAllFilesInDirectory completed");
    }

    /**
     * Returns all files in a directory as a list of File objects.
     *
     * @param directoryPath String path of directory.
     * @return List of File objects.
     */
    public static List<File> getAllFilesInDirectory(String directoryPath) {
        LOGGER.trace("getAllFilesInDirectory started");
        File dir = new File(directoryPath);
        List<File> files = Arrays.asList(dir.listFiles());
        LOGGER.trace("getAllFilesInDirectory completed");
        return files;
    }

    /**
     * Returns True if no files are present in a given directory.
     *
     * @param directoryPath String path of directory to check, eg. src/test/resources/
     * @return Boolean True if empty, false if not empty.
     */
    public static Boolean isDirectoryEmpty(String directoryPath) {
        LOGGER.trace("isDirectoryEmpty started");
        Boolean empty = null;
        try {
            empty = !Files.list(Paths.get(directoryPath)).findAny().isPresent();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        LOGGER.trace("isDirectoryEmpty completed");
        return empty;
    }

    /**
     * Returns the last modified file in a directory.
     *
     * @param directoryPath String path of directory, eg. src/test/resources/
     * @return File object of last modified file.
     */
    public static synchronized File getLastModifiedFile(String directoryPath) {
        LOGGER.trace("getLastModifiedFile started");
        Path dir = Paths.get(directoryPath);
        File fileToReturn = null;

        Optional<File> mostRecentFile =
                Arrays.stream(Objects.requireNonNull(dir.toFile().listFiles())).filter(File::isFile)
                        .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
        fileToReturn = mostRecentFile.get();
        LOGGER.trace("getLastModifiedFile completed");
        return fileToReturn;
    }

    /**
     * Creates a text file and writes list of Strings to it with new lines after each.
     *
     * @param textLines List of String values.
     * @param filePath  String file path of text file being created.
     */
    public static void writeToFile(List<String> textLines, String filePath) {
        LOGGER.trace("writeToFile started");
        File file = new File(filePath);
        try {
            FileUtils.writeLines(file, UTF_8.toString(), textLines);
            LOGGER.info("Wrote to file: " + filePath);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        LOGGER.trace("writeToFile completed");
    }

    /**
     * Writes a String to a file.
     *
     * @param string   String value to write.
     * @param filePath String file path to write to.
     */
    public static void writeToFile(String string, String filePath) {
        //uses overloaded method logging statements
        List<String> list = new ArrayList<>();
        list.add(string);
        writeToFile(list, filePath);
    }

    /**
     * Creates a temp file and writes list of Strings to it. Temp file is deleted when JVM terminates.
     *
     * @param textLines List of String values.
     * @return File object of temp file.
     */
    static File writeToTempTextFile(List<String> textLines) {
        LOGGER.trace("writeToTempTextFile started");
        Path tempPath = null;
        try {
            tempPath = Files.createTempFile("autotest", ".tmp");
            writeToFile(textLines, tempPath.toString());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        File tempFile = tempPath.toFile();
        tempFile.deleteOnExit(); //delete temp file on JVM exit
        LOGGER.trace("writeToTempTextFile completed");
        return tempFile;
    }

    /**
     * Creates a new File at the filepath specified if it doesn't exist. If it does exist, this method will not
     * overwrite it. Also creates directories in the specified file path if they don't already exist.
     *
     * @param filePath String file path to ensure exists.
     * @return boolean value True if new File was created. False if File already exists and was not created.
     */
    public static boolean ensureFilePathExists(String filePath) {
        LOGGER.trace("ensureFilePathExists started");
        Boolean created;
        try {
            File file = new File(filePath);

            String dirPath = file.getParent();
            ensureDirectoryExists(dirPath);

            created = file.createNewFile();
            if (created) {
                LOGGER.info("Created file: " + file);
            }
        } catch (IOException e) {
            created = null;
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        LOGGER.trace("ensureFilePathExists completed");
        return created;
    }

    /**
     * Ensures that each directory in a file path exists. If directoryPath contains a '.', it will
     * be treated as a file and will not be part of the created path
     *
     * @param directoryPath Relative path that will be created if it does not already exist.
     */
    public static void ensureDirectoryExists(String directoryPath) {
        ensureDirectoryExists(directoryPath, false);
    }

    /**
     * Ensures that each directory in a file path exists.  If directoryPath contains a '.', it will
     * create each directory in the path, but not the segment with the '.'.
     *
     * @param directoryPath      Relative path that will be created if it does not already exist.
     * @param pathMayContainDots if true, segments in the path that contain '.'s will be treated as
     *                           directories
     */
    public static void ensureDirectoryExists(String directoryPath, boolean pathMayContainDots) {
        if (!pathMayContainDots) {
            String[] fileParts = directoryPath.split(Pattern.quote(File.separator));

            if (fileParts[fileParts.length - 1].contains(".")) {
                fileParts = Arrays.copyOf(fileParts, fileParts.length - 1);
                directoryPath = String.join(File.separator, fileParts);
            }
        }

        File file = new File(directoryPath);
        if (file.isFile()) {
            file = file.getParentFile();
        } else if (file.isDirectory() && file.exists()) {
            return;
        }
        boolean directoryMade = file.mkdirs();
        if (!directoryMade) {
            LOGGER.error("Failed to create directory: " + directoryPath);
        }
    }

    /**
     * Returns true if file extension of File object matches expected.
     *
     * @param file              File object to check file extension of.
     * @param expectedExtension String expected file extension, omit the '.' character
     *                          (eg. use 'json' instead of '.json').
     * @return boolean True or False.
     */
    public static boolean isFileExtension(File file, String expectedExtension) {
        LOGGER.trace("isFileExtension started");
        boolean bool = com.google.common.io.Files.getFileExtension(file.getName()).equalsIgnoreCase(expectedExtension);
        LOGGER.trace("isFileExtension completed");
        return bool;
    }

    /**
     * Moves a source file to the location of the destination file.
     *
     * @param srcFile  File object of source file.
     * @param destFile File object of new destination for source file.
     */
    public static void moveFile(File srcFile, File destFile) {
        LOGGER.trace("moveFile started");
        try {
            FileUtils.moveFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        LOGGER.trace("moveFile completed");
    }


    /**
     * Deletes all files and directories in the target directory.
     *
     * @param directoryLocation Directory to be purged
     */
    public static void deleteDirectory(String directoryLocation) {
        File directory = new File(directoryLocation);
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }

    /**
     * Deletes all files and directories in the target directory.
     *
     * @param directory Directory to be purged
     */
    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    boolean fileDeleted = file.delete();
                    if (!fileDeleted) {
                        LOGGER.error("Failed to deleted file: " + file.getName());
                    }
                }
            }
        }

        boolean directoryDeleted = directory.delete();
        if (!directoryDeleted) {
            LOGGER.error("Failed to delete directory: " + directory.getName());
        }
    }

    public static void writeCsvFile(String filePath, List<String> allFileLines) {
        LOGGER.debug("Creating file at path: " + filePath);
        ensureDirectoryExists(filePath);

        StringBuilder fileLines = null;
        for (String allFileLine : allFileLines) {
            if (fileLines == null) {
                fileLines = new StringBuilder(allFileLine);
                continue;
            }
            fileLines.append(System.lineSeparator()).append(allFileLine);
        }

        if (fileLines == null) {
            return;
        }

        FileOutputStream fileOutputStream = null;

        try {
            File csvFile = new File(filePath);
            if (!csvFile.exists()) {
                boolean fileCreated = csvFile.createNewFile();
            }
            fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(fileLines.toString().getBytes());
        } catch (IOException e) {
            LOGGER.error("Failed to write file: " + filePath, e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Failed to close OutputStream for file: " + filePath, e);
                }
            }
        }
    }

    /**
     * Returns True if a file at the filepath exists, else false.
     *
     * @param filePath String filepath.
     * @return True if the file exists, else false.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}