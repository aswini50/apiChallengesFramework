package com.api.automation.testNG;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.automation.ApiChallengerUtil;
import com.api.automation.JSONUtil;
import com.api.automation.RestActions;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class ApiChallengesTest {

	// Instance of RestActions Class
	RestActions restActions = new RestActions();
	// Instance of JsonUtil
	JSONUtil jsonUtil = null;

	// Instance of ApiChallengerUtil Class
	ApiChallengerUtil apiChallengerUtil = new ApiChallengerUtil();
	String baseUri = "";
	String basePath = "";
	String challengerId = "";
	String responseBody = "";
	String requestBody = "";
	String updateBody = "";
	int statusCode;
	String toDoId = "";
	String tempTooId = "1";

	@BeforeClass
	public void setUp() {

		apiChallengerUtil.loadProperties();
		baseUri = ApiChallengerUtil.props.getProperty("api.challenges.base.uri");
		jsonUtil = new JSONUtil();
	}

	// Scenario : To get a list of ToDos
	// @Test
	@Test(enabled = false)
	public void getToDos() {
		// given() : base uri, endpoint, header (xchallenger id) --> Externalize
		// this
		// Class Name : Camel Casing (1st letter : Upper case)
		// Method Name : Pascal Casing (1st letter : Lower case)

		basePath = ApiChallengerUtil.props.getProperty("todos.base.path");
		try {
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("get");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			responseBody = restActions.getRequestResponseAsString();
			System.out.println(responseBody);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Scenario : Get a ToDo item for a particular ID
	@Test(priority = 3, enabled = true, dependsOnMethods = { "postToDo", "generateChallengerId" })
	public void getToDosWithId() {
		try {
			basePath = ApiChallengerUtil.props.getProperty("param.todos.base.path").replace("{id}", toDoId);
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("get");
			System.out.println(restActions.getStatusCode());

			// restActions.getRequestResponse("id", toDoId);
			System.out.println(restActions.getResponseAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Scenario : To get a list of challenges
	@Test(enabled = false)
	public void getChallenges() {
		// Overriding the class variable

		basePath = ApiChallengerUtil.props.getProperty("challenges.base.path");
		try {
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("get");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			responseBody = restActions.getRequestResponseAsString();
			System.out.println(responseBody);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// Scenario : To create a challenger id
	@Test(priority = 1, enabled = true)
	public void generateChallengerId() throws Throwable {
		basePath = ApiChallengerUtil.props.getProperty("challenger.base.path");
		try {
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader();
			restActions.performRequest("post");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			// Below lines are not working.Is there an exiting method to get
			// header?

			challengerId = restActions.getHeaderFromResponse("x-challenger");// Make
																				// this
																				// challenger
																				// ID
																				// a
																				// static
																				// variable
																				// and
																				// call
																				// with
																				// package
																				// name
			System.out.println(challengerId);
			//challengerId = null;
			Assert.assertNotNull(challengerId);

		} catch (AssertionError | Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}

	}

	// Scenario : Post ToDo Request Body
	@Test(priority = 2, enabled = true , dependsOnMethods = { "generateChallengerId" })

	public void postToDo() {
		basePath = ApiChallengerUtil.props.getProperty("todos.base.path");

		try {
			requestBody = jsonUtil.getRequestBodyJSONString("requestbody", "post_todo_reqbody");
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.addRequestBody(requestBody);
			// restActions.postRequest();
			restActions.performRequest("post");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			responseBody = restActions.getResponseAsString();
			System.out.println(responseBody);
			toDoId = restActions.getValueByJSONPath("$.id");
			// assert that id is not null
			// throw some exception in catch block otherwise this will always
			// pass
			// logs --> "calling api " (rest Assured default logs)
			System.out.println(toDoId);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// Scenario : Update challenges
	@Test(enabled = false)
	public void partialUpdate() {
		basePath = ApiChallengerUtil.props.getProperty("param.todos.base.path");
		updateBody = "{\r\n" + "  \"title\": \"Partial update \",\r\n" + "  \"doneStatus\": true,\r\n"
				+ "  \"description\": \"officia deserunt mol\"\r\n" + "}";
		try {
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.addRequestBody(updateBody);
			restActions.postRequest("id", "162");
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getRequestResponseAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Scenario : Delete toDo with ID
	@Test(enabled = false)
	public void deleteToDo() {

		basePath = ApiChallengerUtil.props.getProperty("param.todos.base.path");
		try {
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.deleteRequest("id", "187");
			// restActions.performRequest("delete");
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getRequestResponseAsString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
