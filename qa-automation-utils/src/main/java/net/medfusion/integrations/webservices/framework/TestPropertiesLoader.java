

package net.medfusion.integrations.webservices.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class helps load the properties defined in the test.properties file.
 *
 * @version  1.0
 */
public class TestPropertiesLoader {
    //~ Static fields/initializers -------------------------------------------------------------------------------------

    // pattern matcher for ${...} expressions
    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    //~ Instance fields ------------------------------------------------------------------------------------------------

    private final String propertiesFile;
    private final Properties properties;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new TestPropertiesLoader object.
     *
     * @param  propertiesFile  the file to use when loading properties
     * @param  properties  Properties collection to populate with properties loaded
     */
    public TestPropertiesLoader(String propertiesFile, Properties properties) {
        this.propertiesFile = propertiesFile;
        this.properties = properties;
    }

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Load all properties in the file provided resolving placeholders and imported file references.
     */
    public void loadProperties() {
        File testProps = new File(propertiesFile);

        if (!testProps.exists()) {
            throw new ConfigError(testProps.getAbsolutePath() + " does not exist!");
        } // end if

        Collection<String> processedFiles = new HashSet<String>();
        recursiveLoadProperties(testProps, properties, processedFiles);
        resolvePlaceholders();
    }

    /**
     * Load the provided properties file which is assumed to exist. The provided properties collection will be used as
     * the destination. A collection of processed file names is also provided to aid in avoidance of recursive imports.
     *
     * @param  testPropsFile  the existing properties file to load
     * @param  targetProps  the properties collection to be populated
     * @param  processedFiles  the collection of processed file names to check for recursive imports
     *
     * @throws  ConfigError  if the files to be imported do not exist or any I/O errors are encountered
     */
    private void recursiveLoadProperties(File testPropsFile, Properties targetProps, Collection<String> processedFiles)
            throws ConfigError {
        try {
            // add file path to collection to allow check for import recursion
            processedFiles.add(testPropsFile.getCanonicalPath());

            Properties props = loadProperties(testPropsFile);

            // permit loading an imported properties file which is then extended by this one
            String testImportFileName = (String) props.remove("test.properties.import");

            if (testImportFileName != null) {
                File testImportFile = new File(testImportFileName);

                if (!testImportFile.exists()) {
                    // permit import relative to parent file
                    File parentDir = testPropsFile.getParentFile();
                    testImportFile = new File(parentDir, testImportFileName);

                    // try to see if parent relative file exists
                    if (!testImportFile.exists()) {
                        throw new ConfigError("Import from " + testPropsFile.getAbsolutePath()
                                + " file test.properties.import=" + testImportFileName + " does not exist!");
                    } // end if
                } // end if

                // see if we have encountered this file already!
                if (processedFiles.contains(testImportFile.getCanonicalPath())) {
                    System.err.println("WARNING: Recursive import from " + testPropsFile.getAbsolutePath()
                            + " ignored.");
                    System.err.println("         test.properties.import=" + testImportFile.getCanonicalPath());
                } else {
                    // load imported properties first to allow overloading same name properties
                    recursiveLoadProperties(testImportFile, targetProps, processedFiles);
                } // end if-else
            } // end if

            targetProps.putAll(props);
        } catch (IOException e) {
            throw new ConfigError("Unable to read properties file " + testPropsFile.getAbsolutePath(), e);
        } // end try-catch
    } // end method recursiveLoadProperties

    /**
     * Load a {@link Properties} collection from the provided file instance.
     *
     * @param  testProps  the {@link File} to load the properties from
     *
     * @return  the Properties collection ready to use
     *
     * @throws  FileNotFoundException  if the specified file does not exist
     * @throws  IOException  if another reason prevents reading the properties
     */
    private Properties loadProperties(File testProps)
            throws FileNotFoundException, IOException {
        InputStream tcin = new FileInputStream(testProps);
        Properties props = new Properties();

        try {
            props.load(tcin);
        } finally {
            tcin.close();
        } // end try-finally

        return props;
    } // end method loadProperties

    /**
     * Resolve any property placeholders in the collection.
     */
    private void resolvePlaceholders() {
        Collection<String> keys = new HashSet<String>();

        // need a new collection for this
        for (Object key : properties.keySet()) {
            keys.add((String) key);
        } // end for

        // tracking collection for diagnostic and cycle detection purposes
        Map<String, String> workingKeys = new LinkedHashMap<String, String>();

        for (String key : keys) {
            workingKeys.clear();

            String value = properties.getProperty(key);
            workingKeys.put(key, value);

            String newValue = resolve(workingKeys, value);
            properties.put(key, newValue);
        } // end for
    } // end method resolvePlaceholders

    /**
     * Recursive property placeholder resolver.
     *
     * @param  workingKeys  the cycle detection map
     * @param  value  the value to resolve placeholders within
     *
     * @return  the value updated with placeholders resolved
     */
    private String resolve(Map<String, String> workingKeys, String value) {
        Matcher matcher = PATTERN.matcher(value);
        StringBuffer buf = new StringBuffer(value.length() * 2);

        while (matcher.find()) {
            String property = matcher.group(1);

            // detect recursive property evaluation
            if (workingKeys.containsKey(property)) {
                System.err.println("WARNING: Recursive property evaluation!");

                for (Entry<String, String> entry : workingKeys.entrySet()) {
                    System.err.println("    " + entry.getKey() + "=" + entry.getValue());
                } // end for

                // leave the contents unchanged, replace matched region with itself
                matcher.appendReplacement(buf, Matcher.quoteReplacement(matcher.group()));

                continue;
            } // end if

            // try system properties first
            String replacement = System.getProperty(property);
            boolean update;

            // fallback to our own properties
            if (replacement == null) {
                replacement = properties.getProperty(property);
                update = true;
            } else {
                update = false;
            } // end if

            // leave matched string unchanged
            if (replacement == null) {
                replacement = matcher.group();
                System.err.println("WARNING: Placeholder not resolved! (" + replacement + ")");
            } else {
                // recursively resolve any additional placeholder references
                workingKeys.put(property, replacement);
                replacement = resolve(workingKeys, replacement);
                workingKeys.remove(property);

                // if we resolved one of our properties replace it now
                if (update) {
                    properties.put(property, replacement);
                } // end if
            } // end if

            replacement = Matcher.quoteReplacement(replacement);
            matcher.appendReplacement(buf, replacement);
        } // end while

        matcher.appendTail(buf);

        return buf.toString();
    } // end method resolve
} // end class TestPropertiesLoader
