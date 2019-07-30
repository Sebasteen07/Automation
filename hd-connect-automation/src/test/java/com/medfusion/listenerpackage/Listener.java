package com.medfusion.listenerpackage;



import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
 
public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {
 
	public void onStart(ISuite arg0) {
		Reporter.log(" Executing Suite " + arg0.getName(), true);
	}
 
	public void onFinish(ISuite arg0) {
		Reporter.log("Completed executing Suite " + arg0.getName(), true);
	}
 
	public void onStart(ITestContext arg0) {
		Reporter.log("Starting " + arg0.getName(), true);
	}
 
	public void onFinish(ITestContext arg0) {
		Reporter.log("Completed executing test " + arg0.getName(), true);
	}
 
	public void onTestSuccess(ITestResult arg0) { 
		printTestResults(arg0);
	}
  
	public void onTestFailure(ITestResult arg0) { 
		printTestResults(arg0);
	}
 
	public void onTestStart(ITestResult arg0) {
		System.out.println("The execution of the test methods begins now");
	}
 
	public void onTestSkipped(ITestResult arg0) {
	printTestResults(arg0);
	}
 
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
	}
 
	private void printTestResults(ITestResult result) {
		Reporter.log("Test Method resides in " + result.getTestClass().getName(), true);
		if (result.getParameters().length != 0) {
			String params = null;
				for (Object parameter : result.getParameters()) {
					params += parameter.toString() + ",";
				}
 
		Reporter.log("Test Method had the following parameters : " + params, true);
		}
 
		String status = null;
		switch (result.getStatus()) {
		
		case ITestResult.SUCCESS:
			status = "Passed";
			break;
 
		case ITestResult.FAILURE:
			status = "Failed";
			break;
 
		case ITestResult.SKIP:
			status = "Skipped";
		}
 
		Reporter.log("Test Status: " + status, true);
	}
 
 
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
 
		String textMsg = "Starting method : " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
	}
 
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
 
		String textMsg = "Completed executing : " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
 
	}
 
	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
	}
 
}