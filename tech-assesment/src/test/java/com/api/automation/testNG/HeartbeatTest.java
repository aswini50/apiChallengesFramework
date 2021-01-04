package com.api.automation.testNG;

import org.testng.annotations.Test;

import com.api.automation.ApiChallengerUtil;

import junit.framework.Assert;

public class HeartbeatTest extends BaseTest {
	public static String basePath;

	@Test(priority = 1)
	public void deleteHeartbeat() {
		basePath = ApiChallengerUtil.props.getProperty("heartbeat.base.path");
		try {

			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("delete");
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getResponseAsString());
			Assert.assertEquals(405, restActions.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 2)
	public void patchHeartbeat() {
		try {
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("patch");
			System.out.println(restActions.getStatusCode());
			Assert.assertEquals(500, restActions.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3, enabled = false)
	public void traceHeartbeat() {
		try {
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("trace"); // Unable to create trace
													// method
			System.out.println(restActions.getStatusCode());
			Assert.assertEquals(501, restActions.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4)
	public void getHeartbeat() {
		try {
			restActions.setBasePath(basePath);
			restActions.addHeader(challengerId);
			restActions.performRequest("get");
			System.out.println(restActions.getStatusCode());
			System.out.println(restActions.getResponseAsString());
			Assert.assertEquals(204, restActions.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
