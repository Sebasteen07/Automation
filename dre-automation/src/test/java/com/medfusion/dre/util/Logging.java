package com.medfusion.dre.util;

import org.junit.Rule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class Logging extends BaseTestNGWebDriver {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	// TODO Add non deprecated logging
	@Rule
	public TestWatchman watchman = new TestWatchman() {
		public void starting(FrameworkMethod method) {
			logger.info("<<< Test {} has STARTED >>>", method.getName());
		}

		public void succeeded(FrameworkMethod method) {
			logger.info("<<< Test {} SUCCEED >>>\n\n", method.getName());
		}

		public void failed(Throwable e, FrameworkMethod method) {
			logger.error("<<< Test {} FAILED >>>", method.getName());
			logger.error("With reason:", method.getName(), e.getMessage());
		}
	};
}
