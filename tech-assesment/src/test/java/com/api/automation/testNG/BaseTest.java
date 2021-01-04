package com.api.automation.testNG;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.api.automation.ApiChallengerUtil;
import com.api.automation.JSONUtil;
import com.api.automation.RestActions;

public class BaseTest {

	// Instance of RestActions Class
	RestActions restActions = new RestActions();

	// Instance of JsonUtil
	JSONUtil jsonUtil = null;

	// Instance of ApiChallengerUtil Class
	ApiChallengerUtil apiChallengerUtil = new ApiChallengerUtil();

	String baseUri = "";
	String challengerBasePath = "";
	static String challengerId = "";

	@BeforeClass
	public void setUp() throws Throwable {
		apiChallengerUtil.loadProperties();
		baseUri = ApiChallengerUtil.props.getProperty("api.challenges.base.uri");
		jsonUtil = new JSONUtil();
		generateChallengerId();
		restActions.setupRequest();
		restActions.setBaseURI(baseUri);
		restActions.addHeader(challengerId);
	}

	public void generateChallengerId() throws Throwable {
		challengerBasePath = ApiChallengerUtil.props.getProperty("challenger.base.path");
		try {
			restActions.setupRequest();
			restActions.setBaseURI(baseUri);
			restActions.setBasePath(challengerBasePath);
			restActions.addHeader();
			restActions.performRequest("post");
			challengerId = restActions.getHeaderFromResponse("x-challenger");
			System.out.println(challengerId);
			Assert.assertNotNull(challengerId);

		} catch (AssertionError | Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
