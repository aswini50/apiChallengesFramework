package com.api.automation.testNG;

import org.testng.annotations.Test;

import com.api.automation.ApiChallengerUtil;

public class SecretTokenTest extends BaseTest {

	String basePath = "";
	String xAuthToken = "";
	int statusCode;

	@Test(enabled = true)
	public String getSecretToken() {
		basePath = ApiChallengerUtil.props.getProperty("secret.token.base.path");
		try {
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.setupAuthentication("basic");
			restActions.performRequest("post");
			statusCode = restActions.getStatusCode();
			System.out.println(statusCode);
			xAuthToken = restActions.getHeaderFromResponse("x-auth-token");
			System.out.println(xAuthToken);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xAuthToken;
	}

}
