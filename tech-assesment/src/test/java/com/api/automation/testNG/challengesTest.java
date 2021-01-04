package com.api.automation.testNG;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.automation.ApiChallengerUtil;

public class challengesTest extends BaseTest {

	String basePath;
	String challengesList = "";

	@Test(enabled = true)
	public void getChallenges() {
		basePath = ApiChallengerUtil.props.getProperty("challenges.base.path");
		try {

			restActions.setBasePath(basePath);
			restActions.performRequest("get");
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getResponseAsString());
			Assert.assertEquals(restActions.getStatusCode(), 200);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
