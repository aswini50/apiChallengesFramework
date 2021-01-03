package com.api.automation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features" }, glue = { "com.obs.automation.steps" }, plugin = {
		"pretty", "html:reports/cucumber-report.html",
		"json:reports/cucumber-json/jsonReport.json" }, monochrome = true, dryRun = false, tags = "@Athletes")

public class APITestRunner extends AbstractTestNGCucumberTests {
	

}
