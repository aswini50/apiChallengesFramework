package com.api.automation.steps;

import java.util.List;

import org.everit.json.schema.ValidationException;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.api.automation.JSONUtil;
import com.api.automation.ApiChallengerUtil;
import com.api.automation.ReportUtil;
import com.api.automation.RestActions;
import com.aventstack.extentreports.ExtentReports;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AthletesSteps {

	RestActions restActions = new RestActions();
	String baseURI = "";
	
	@Given("User Setup the base uri {string}")
	public void user_setup_the_base_uri(String baseURI) {
		try {
			this.baseURI = ApiChallengerUtil.props.getProperty(baseURI);
			restActions.setupRequest();
			restActions.setRelaxedHTTPSValidationProtocolAsSSL();
			restActions.addHeader();
			restActions.setBaseURI(this.baseURI);
		} catch (Exception e) {
			ReportUtil.reportFail("Unable to set baseURI.. \n " + e);
		}
	}

	@Then("Setup request endpoint by using base path {string} for request {string}")
	public void setup_request_endpoint_by_using_base_path(String basePath, String apiName) {
		try {
			basePath = ApiChallengerUtil.props.getProperty(basePath);
			restActions.setBasePath(basePath);
			ReportUtil.reportPass("Setup request endpoint for: " + apiName + " as " + baseURI + basePath);
		} catch (Exception e) {
			ReportUtil.reportFail(
					"Setup request endpoint for: " + apiName + " as " + baseURI + basePath + " failed. \n" + e);
		}
	}

	@When("User sends a {string} request to retrive data")
	public void user_sends_a_request_to_retrive_data(String requestMethod) {
		try {
			restActions.performRequest(requestMethod);
			ReportUtil.reportPass("Send " + requestMethod + " Request");
		} catch (Exception e) {
			ReportUtil.reportFail("Send " + requestMethod + " Request failed \n" + e);
		}
	}

	@Then("Response status code should match with expected status code {int}")
	public void response_status_code_should_match_with_expected_status_code(int statusCode) {
		int actualStatusCode = 0;
		try {
			actualStatusCode = restActions.getStatusCode();
			ReportUtil.logJSONBlock("Response Schema: ", restActions.getRequestResponseAsString());
			Assert.assertEquals(actualStatusCode, statusCode);
			ReportUtil.reportPass("Response Status Code: " + actualStatusCode);
			ReportUtil.reportPass("Response Time: " + restActions.getResponseTime() + " miliseconds");
		} catch (AssertionError e) {
			ReportUtil.reportFail("Actual Response Status Code: " + actualStatusCode
					+ ", Expected Response Status Code: " + statusCode);
		}
	}

	@Then("The Response Json schema should match with expected schema from file {string}")
	public void the_response_json_schema_should_match_with_expected_schema_from_file(String schemaFile) {
		JSONUtil jsonUtil = new JSONUtil();
		try {
			String schemaToCheck = jsonUtil.parseJsonArrayToString(schemaFile);
			restActions.jsonSchemaValidator(schemaToCheck);
			ReportUtil.reportPass("Json Schema Validation Passed with File: " + schemaFile + ".json");
		} catch (Throwable e) {
			if (e instanceof ValidationException) {
				List<ValidationException> vExceptions = ((ValidationException) e).getCausingExceptions();
				for (ValidationException validationException : vExceptions) {
					if (validationException.getCausingExceptions() != null) {
						List<ValidationException> inList = validationException.getCausingExceptions();
						for (ValidationException validationException2 : inList) {
							ReportUtil.reportFail(
									"Schema Validation Failed: " + validationException2.getAllMessages().toString());
						}
					}
					ReportUtil.reportFail("Schema Validation Failed: " + validationException);
				}
			}
			ReportUtil.reportFail("Json Schema Validation Failed with File: " + schemaFile + " : " + e);
		}
	}

	@Then("The Response body athletes data should match with data from database table {string} and query {string}")
	public void the_response_body_athletes_data_should_match_with_data_from_database_table(String dbTableName,
			String queryName) throws JSONException, Exception {

		boolean compare = false;
		queryName = ApiChallengerUtil.props.getProperty(queryName);
		String responseString = restActions.getRequestResponseAsString();
		ApiChallengerUtil.openConnection();
		String dataBaseString = ApiChallengerUtil.resultSetToJson(queryName);

		if (responseString.startsWith("["))
			compare = JSONUtil.jsonValueCompare(responseString, dataBaseString);
		else if (responseString.startsWith("{")) {
			dataBaseString = dataBaseString.replace("[", "").replace("]", "").trim();
			compare = JSONUtil.jsonObjectValueCompare(responseString, dataBaseString);
		}

		if (compare) {
			ReportUtil.reportPass("API Response Data matching with Database records");
			// ReportUtil.logJSONBlock("Response Schema: ", responseString);
			ReportUtil.logJSONBlock("Database Schema: ", dataBaseString);
		} else {
			ReportUtil.reportFail("API Response Data is not matching with Database records");
			// ReportUtil.logJSONBlock("Response Schema: ", responseString);
			ReportUtil.logJSONBlock("Database Schema: ", dataBaseString);
		}
		ApiChallengerUtil.closeConnection();
	}

}
