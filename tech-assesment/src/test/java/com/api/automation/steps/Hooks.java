package com.api.automation.steps;

import com.api.automation.ApiChallengerUtil;
import com.api.automation.ReportUtil;
import com.aventstack.extentreports.ExtentReports;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	ApiChallengerUtil obsUtil = new ApiChallengerUtil();
	static ExtentReports report;

	@Before
	public void beforeHook(Scenario scenario) {
		if (report == null) {
			obsUtil.loadProperties();
			report = ReportUtil.initializeReport();
		}
		ReportUtil.createTest(report, scenario.getName());
	}

	@After
	public void afterHook() {
		ReportUtil.flushReport(report);
	}
}
