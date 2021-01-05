package com.api.automation.testNG;

import org.testng.annotations.Test;

import com.api.automation.ApiChallengerUtil;

public class ToDosTest extends BaseTest {

	String basePath = "";
	String responseBody = "";
	String requestBody = "";
	String updateBody = "";
	int statusCode;
	String toDoId = "";
	String tempTooId = "1";

	@Test(enabled = false)
	public void getToDos() {
		basePath = ApiChallengerUtil.props.getProperty("todos.base.path");
		try {
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(basePath);
			restActions.performRequest("get");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			responseBody = restActions.getRequestResponseAsString();
			System.out.println(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Scenario : Get a ToDo item for a particular ID
	@Test(priority = 3, enabled = false, dependsOnMethods = { "postToDo" })
	public void getToDosWithId() {
		try {
			basePath = ApiChallengerUtil.props.getProperty("param.todos.base.path").replace("{id}", toDoId);

			restActions.setBasePath(basePath);
			restActions.performRequest("get");
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getResponseAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Scenario : Post ToDo Request Body
	@Test(priority = 2, enabled = true)
	public void postToDo() {
		basePath = ApiChallengerUtil.props.getProperty("todos.base.path");
		try {
			requestBody = jsonUtil.getRequestBodyJSONString("requestbody", "post_todo_reqbody");

			restActions.setBasePath(basePath);
			restActions.addRequestBody(requestBody);
			restActions.performRequest("post");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			responseBody = restActions.getResponseAsString();
			System.out.println(responseBody);
			toDoId = restActions.getValueByJSONPath("$.id");
			System.out.println(toDoId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Scenario : Update challenges
	@Test(priority = 4, enabled = true, dependsOnMethods = { "postToDo" })
	public void partialUpdate() {
		basePath = ApiChallengerUtil.props.getProperty("param.todos.base.path");

		try {
			updateBody = jsonUtil.getRequestBodyJSONString("requestbody", "partial_update_todo_reqbody");
			restActions.setBasePath(basePath);
			restActions.addRequestBody(updateBody);
			restActions.postRequest("id", toDoId);
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getResponseAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Scenario : Delete toDo with ID
	@Test(priority = 5, enabled = true, dependsOnMethods = { "postToDo" })
	public void deleteToDo() {
		basePath = ApiChallengerUtil.props.getProperty("param.todos.base.path");
		try {

			restActions.setBasePath(basePath);
			restActions.deleteRequest("id", toDoId);
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getResponseAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
