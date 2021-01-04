package com.api.automation;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.api.automation.testNG.HeartbeatTest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

public class RestActions {

	private static FilterableRequestSpecification request;
	private static Response response;
	private String param_id;
	private String id;

	/**
	 * Request Setup
	 */
	public void setupRequest() {
		try {
			request = (FilterableRequestSpecification) RestAssured.given();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Set Base URI for the Request
	 *
	 * @param baseURI
	 */
	public void setBaseURI(String baseURI) {
		try {
			request.baseUri(baseURI);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Set Base Path for the Request
	 *
	 * @param
	 */
	public void setBasePath(String basePath) {
		try {
			request.basePath(basePath);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * use Relaxed HTTPS validation with the request
	 */
	public void setRelaxedHTTPSValidationProtocolAsSSL() {
		try {
			request.relaxedHTTPSValidation();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Add String body to the Request
	 *
	 * @param
	 * @return
	 */
	public RequestSpecification addRequestBody(String requestBody) {
		try {
			request.body(requestBody);
		} catch (Exception e) {
			throw e;
		}
		return request;
	}

	/**
	 * Perform the Type of request as the Request type GET,POST
	 *
	 * @param requestMethod
	 * @throws Exception
	 */
	public void performRequest(String requestMethod) throws Exception {
		if (requestMethod.toUpperCase().equals("GET")) {
			getRequestResponse();
		}
		// Adding a new elseif condition
		else if (requestMethod.toUpperCase().equals("GETID")) {
			String param_id = null;
			String id = null;
			getRequestResponse(param_id, id);
		}

		else if (requestMethod.toUpperCase().equals("POST")) {
			postRequest();
		}
		// Adding a branch for Delete Method
		else if (requestMethod.toUpperCase().equals("DELETE")) {
			deleteRequest();
		}
		// Adding Patch method
		else if (requestMethod.toUpperCase().equals("PATCH")) {
			patchRequest();
		}
		/*
		 * // Adding Trace Method else if
		 * (requestMethod.toUpperCase().equals("TRACE")) { traceRequest(); }
		 */

		// TODO Ask Alan about Trace Request
		/*
		 * public Response traceRequest() { try { response = request.
		 * System.out.println("Response: " + response.asString()); } catch
		 * (Exception e) { throw e; }return response;
		 */

	}

	public Response patchRequest() {
		try {
			response = request.patch();
			System.out.println("Response: " + response.asString());
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	/**
	 * Peform a Delete Request for the request Specification
	 *
	 * @return
	 */
	public Response deleteRequest(String param_id, String id) {

		try {
			response = request.pathParam(param_id, id).delete();
			System.out.println("Response: " + response.asString());
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	/**
	 * Method Overload Peform a Delete Request for the request Specification
	 *
	 * @return
	 */
	public Response deleteRequest() {

		try {
			response = request.delete();
			System.out.println("Response: " + response.asString());
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	/**
	 * Peform a Post Request for the request Specification
	 *
	 * @return
	 */
	public Response postRequest() {

		try {
			response = request.post();
			// System.out.println("Response: " + response.asString());
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	/**
	 * Method Overloading Partial Update Peform a Post Request for the request
	 * Specification with Path Param
	 *
	 * @return
	 */
	public Response postRequest(String param_id, String id) {

		try {
			response = request.pathParam(param_id, id).post();
			// System.out.println("Response: " + response.asString());
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	/**
	 * Perform a get Request
	 *
	 * @return
	 */
	public Response getRequestResponse() {
		try {
			response = request.get();
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	/**
	 * Method Override Get Respsone as a string
	 *
	 * @return
	 */
	public String getResponseAsString() {
		String responseString = null;
		try {
			responseString = response.asString();
		} catch (Exception e) {
			throw e;
		}
		return responseString;
	}

	/**
	 * Get String value by JSONPath
	 *
	 * @param path
	 * @return
	 */
	public String getValueByJSONPath(String jsonPath) {
		String value = null;
		try {
			DocumentContext context = com.jayway.jsonpath.JsonPath.parse(getResponseAsString());
			value = context.read(jsonPath).toString();
		} catch (Exception e) {
			throw e;
		}
		return value;
	}

	/**
	 * Method Overloading Perform a get Request with a Path Param
	 *
	 * @return
	 */
	public Response getRequestResponse(String param_id, String id) {
		try {
			response = request.pathParam(param_id, id).get();
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	public String getRequestResponseAsString() {
		try {
			response = request.get();
		} catch (Exception e) {
			throw e;
		}
		return response.asString().trim();
	}

	public void addHeader(String challengerId) {
		try {
			request.header("Content-Type", "application/json");
			request.header("Accept", "application/json");
			request.header("x-challenger", challengerId);
		} catch (Exception e) {
			throw e;
		}

	}

	// Method Overload without argument
	public void addHeader() {
		try {
			request.header("Content-Type", "application/json");
			request.header("Accept", "application/json");

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Set the content type for the Request
	 *
	 * @param
	 */
	public void setContentType() {
		try {
			request.contentType(ContentType.JSON);
		} catch (Exception e) {
			throw e;
		}
	}

	public void setContentTypeXML() {
		try {
			request.contentType(ContentType.XML);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Get the Status code for the Request
	 *
	 * @return
	 */
	public int getStatusCode() {
		int statCode = 0;
		try {
			statCode = response.statusCode();
		} catch (Exception e) {
			throw e;
		}
		return statCode;
	}

	public int getResponseTime() {
		int time = 0;
		try {
			time = (int) response.getTimeIn(TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw e;
		}
		return time;
	}
	/*
	 * The additional methods that we need for my framework : getHeader() get()
	 * with param as an argument post() with param as an argument for editing
	 */

	@SuppressWarnings("deprecation")
	public boolean jsonSchemaValidator(String schemaString) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

		JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

		try {

			String responseSchema = getRequestResponseAsString();

			byte[] rByte = responseSchema.getBytes();
			byte[] dByte = schemaString.getBytes();

			JsonNode json = objectMapper.readTree(new String(rByte, "UTF8"));

			JsonSchema schema = schemaFactory.getSchema(new String(dByte, "UTF8"));

			Set<ValidationMessage> validationResult = schema.validate(json);

			if (validationResult.isEmpty()) {
				System.out.println("\t Both Schema matching-->no validation errors....");
				return true;
			} else {
				validationResult.forEach(vm -> System.out.println(vm.getMessage()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getStringValueFromResponse(String jsonPath, String responseJson) {
		String value = null;
		try {
			DocumentContext context = com.jayway.jsonpath.JsonPath.parse(responseJson);
			value = context.read(jsonPath).toString();
		} catch (Exception e) {
			throw e;
		}
		return value;
	}

	public JsonPath getJsonPathFromResponse() {
		JsonPath jPath = null;
		try {
			if (response != null) {
				jPath = response.jsonPath();
			}

		} catch (Exception e) {
			throw e;
		}
		return jPath;
	}

	/**
	 * Get Headers form Response by Name
	 *
	 * @param name
	 * @return
	 */
	public String getHeaderFromResponse(String name) {
		String header = null;
		try {
			header = response.getHeader(name);
		} catch (Exception e) {
			throw e;
		}
		return header;
	}

	public void setupAuthentication(String authType) {
		try {
			AuthenticationSpecification authenticationSpecification = request.auth();
			switch (authType.toUpperCase()) {
			case "BASIC":
				authenticationSpecification.basic("admin", "password");// TODO :
																		// Get
																		// this
																		// value
																		// from
																		// config
																		// file
				break;
			case "OAUTH2":
				authenticationSpecification.oauth2("accessToken");
				break;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void addMultipleHeaders(Map<String, String> headers) {
		try {
			request.headers(headers);
		} catch (Exception e) {
			throw e;
		}
	}
}
