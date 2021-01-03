package com.api.automation;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ReportUtil {

	private static ExtentHtmlReporter extentHtmlReporter;
	private static ExtentReports extentReports;
	private static ExtentTest extentTest;
	static long currentTime;

	public static ExtentReports initializeReport() {
		String reportPath = System.getProperty("user.dir") + "/reports/";

		try {
			extentHtmlReporter = new ExtentHtmlReporter(reportPath + "extent-report.html");
			extentReports = new ExtentReports();

			extentReports.attachReporter(extentHtmlReporter);
			extentReports.setSystemInfo("OS", System.getProperty("osname"));
			extentReports.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			extentReports.setSystemInfo("Environment", "Windows");
			extentReports.setSystemInfo("User Name", "qa");

			extentHtmlReporter.config().setTheme(Theme.DARK);
			extentHtmlReporter.config().setDocumentTitle("Report");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return extentReports;
	}

	public static ExtentTest createTest(ExtentReports report, String testCaseName) {
		extentTest = report.createTest(testCaseName);
		return extentTest;
	}

	public static void flushReport(ExtentReports extentReports) {
		extentReports.flush();
	}

	private static void failedLogWithScreenshot(String message) {
		extentTest.log(Status.FAIL, message);
	}

	private static void passedLogWithScreenshot(String message) {
		extentTest.log(Status.PASS, message);
	}

	public static void reportPass(String msg) {
		passedLogWithScreenshot(msg);
	}

	public static void reportFail(String msg) {
		failedLogWithScreenshot(msg);
	}

	public static void logJSONBlock(String details, String jsonString) {
		JsonParser parser = new JsonParser();
		JsonElement json = parser.parse(jsonString);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);
		extentTest.log(Status.PASS, "<h6>" + details
				+ ":</h6><br><pre class=\"code-block\" style=\"resize: both; width: 721px; height: 335px;\"><br>"
				+ prettyJson + "<br></pre>");

	}

}
