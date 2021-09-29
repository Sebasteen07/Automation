package com.medfusion.product.appt.precheck.TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/main/java/com/medfusion/product/appt/precheck/Features/AppointmentsPrecheck.feature", glue = {
		"com.medfusion.product.appt.precheck.stepDefinations", "com.medfusion.product.appt.precheck.Hooks" }, plugin = {
				"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"json:target/cucumber-reports/cucumber.json", "html:target/cucumber-reports/cucumber.html",
				"junit:target/cucumber-reports/cucumber.xml", "pretty:target/cucumber-reports/cucumber-pretty.txt",
				"usage:target/cucumber-reports/cucumber-usage.json",
				"rerun:target/cucumber-reports/rerun.txt" }, tags = "@Regression1", dryRun = false, monochrome = true)
public class TestRunner {

}
