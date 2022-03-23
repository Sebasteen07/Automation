// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Logging Utilities class, include general methods that assist with logging functionality. Configure Loggers
 * in the log4j2.xml file.
 */
public class LogUtils {

    private static final Logger LOGGER = LogUtils.getLoggerForThisClass();
    private static final String CONSOLE_LOGGER_NAME = "Console";
    private static final String CUKE_LINE = "*******************************************************************************************************************************";
    public static final String CUCUMBER_LOG_LEVEL = "CUKE";

    /**
     * Call this method within another class to instantiate the Logger and name it using the appropriate class name
     * automatically. Use the following example within the desired class to instantiate the Logger correctly:
     * <pre>
     *     {@code
     *     private static final Logger LOGGER = LogUtils.getLoggerForThisClass();
     *     }
     * </pre>
     *
     * @return log4j Logger object with appropriate calling class name set automatically.
     */
    public static Logger getLoggerForThisClass() { //method instantiates LOGGER, cannot log this method without null pointer
        StackTraceElement callingClass = Thread.currentThread().getStackTrace()[2]; // third stack trace element is name of calling class
        return LogManager.getLogger(callingClass.getClassName());
    }

    /**
     * Returns the input String enclosed in single quotes, eg. 'inputString'.
     *
     * @param string String to enclose.
     * @return String enclosed in single quotes.
     */
    public static String singleQuotes(String string) {
        return "'" + string + "'";
    }

    /**
     * Returns the input String enclosed in double quotes.
     *
     * @param string String to enclose.
     * @return String enclosed in double quotes.
     */
    public static String doubleQuotes(String string) {
        return "\"" + string + "\"";
    }

    /**
     * Returns String enclosed in curly brackets, eg: {inputString}.
     *
     * @param string String to enclose.
     * @return String enclosed in curly brackets.
     */
    public static String curlyBrackets(String string) {
        return "{" + string + "}";
    }

    /**
     * Returns String enclosed in square brackets, eg: [inputString].
     *
     * @param string String to enclose.
     * @return String enclosed in square brackets.
     */
    public static String squareBrackets(String string) {
        return "[" + string + "]";
    }

    /**
     * Returns a String enclosed in system line separators, eg: \r\n [inputString] \r\n
     *
     * @param string String to enclose.
     * @return String enclosed in system line separators.
     */
    public static String lineSeparators(String string) {
        return System.lineSeparator() + string + System.lineSeparator();
    }

    /**
     * Set the logging level for a specific Logger, used to specify logging levels at run time.
     *
     * @param loggerName   String name of Logger whose logging level is being set.
     * @param loggingLevel String logging level to set the Logger to.
     */
    private static void setLoggingLevel(String loggerName, String loggingLevel) {
        LOGGER.trace("setLoggingLevel started");
        Level levelObject = Level.valueOf(loggingLevel); //convert String to Log4j Level object

        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Configuration loggerConfig = loggerContext.getConfiguration(); //get log4j logging configurations

        LoggerConfig consoleLoggerConfig = loggerConfig.getLoggerConfig(loggerName); //get log4j logging configurations for specific Logger
        consoleLoggerConfig.setLevel(levelObject);
        loggerContext.updateLoggers();

        LOGGER.log(levelObject, "Logging level: " + levelObject.toString());
        LOGGER.trace("setLoggingLevel completed");
    }

    /**
     * Sets logging level of Console Logger.
     *
     * @param level String Level to set Console Logger to.
     */
    public static void setConsoleLoggingLevel(String level) {
        setLoggingLevel(CONSOLE_LOGGER_NAME, level);
    }

    /**
     * Returns String in highly-visible format, used for easily distinguishing Scenario starts / stops in
     * Logger and Console output.
     *
     * @param message String value to format.
     * @return String in highly-visible format.
     */
    public static String formatScenarioStartMsg(String message) {
        LOGGER.trace("formatScenarioStartMsg started");
        StringBuilder cucumberMessage = new StringBuilder();
        cucumberMessage.append("\n" + CUKE_LINE + "\n")
                .append(message).
                append("\n" + CUKE_LINE + "\n");
        String builtString = cucumberMessage.toString();
        LOGGER.trace("formatScenarioStartMsg completed");
        return builtString;
    }
}