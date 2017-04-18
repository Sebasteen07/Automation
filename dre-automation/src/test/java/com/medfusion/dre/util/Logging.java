package com.medfusion.dre.util;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class Logging extends BaseTestNGWebDriver {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Rule
	public TestRule watchman = new TestWatcher() {

	    public void succeeded(final Description description) {
	        logger.info(String.format("Success: %s", description));
	    }

	    public void failed(final Throwable e, final Description description) {
	        logger.info(String.format("Failed: %s", description), e);
	    }

	    public void starting(final Description description) {
	        logger.info(String.format("Starting: %s", description));
	    }

	    public void finished(final Description description) {
	        logger.info(String.format("Finished: %s", description));
	    }

	    };
}
