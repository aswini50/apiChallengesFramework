package com.api.automation.testNG;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.api.automation.ApiChallengerUtil;

public class SecretNoteTest extends BaseTest {

	String authToken = "";
	SecretTokenTest secretTokenTest = new SecretTokenTest();
	String basePath = "";

	@Test(enabled = true, priority = 1)
	public void postSecertNote() throws Exception {
		basePath = ApiChallengerUtil.props.getProperty("secret.note.base.path");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-challenger", challengerId);
		authToken = secretTokenTest.getSecretToken();
		headers.put("Authorization", "Bearer " + authToken);

		restActions.setBasePath(basePath);
		restActions.addHeader();
		restActions.addMultipleHeaders(headers);
		restActions.addRequestBody("{\r\n" + "    \"note\": \"contents of note: Chaining with get operation\"\r\n" + "}");//TODO : Remove hardcoding
		restActions.performRequest("post");
		System.out.println(restActions.getResponseAsString());
		System.out.println(restActions.getStatusCode());
	}

	@Test(enabled = true, priority = 2)
	public void getSecertNote() throws Exception {

		Map<String, String> headers = new HashMap<String, String>();

		headers.put("x-challenger", challengerId);
		headers.put("Authorization", "Bearer " + authToken);

		restActions.setBasePath(basePath);
		restActions.addHeader();
		restActions.addMultipleHeaders(headers);
		restActions.performRequest("get");
		System.out.println(restActions.getResponseAsString());
		System.out.println(restActions.getStatusCode());
	}

}
