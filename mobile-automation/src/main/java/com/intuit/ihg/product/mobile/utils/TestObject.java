package com.intuit.ihg.product.mobile.utils;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/20/13
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestObject  {
	public static final String TEST_CASE_ID = "TestObject.TestCaseId";
	public static final String TEST_METHOD = "TestObject.TestMethod";
	public static final String TEST_TITLE = "TestObject.TestTitle";
	public static final String TEST_ENV = "TestObject.TestEnv";

	private String testCaseId = "";
	private String testMethod = "";
	private String testTitle = "";
	private String testEnv = "";

	public String getTestCaseId() {
		return testCaseId;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public String getTestEnv() {
		return testEnv;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public void settestEnv(String testEnv) {
		this.testEnv = testEnv;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public String toString() {
		return "[TestCaseId=" + testCaseId + "|TestMethod=" + testMethod + "|TestTitle=" + testTitle + "|Env=" + testEnv + "]";
	}

}