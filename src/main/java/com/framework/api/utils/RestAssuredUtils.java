package com.framework.api.utils;

import static io.restassured.RestAssured.*;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Utility class for REST API operations using REST Assured.
 */
public class RestAssuredUtils {
	private static final Logger logger = LogManager.getLogger(RestAssuredUtils.class);

	/**
	 * To create a basic request specification
	 */
	public static RequestSpecification getRequestSpecification() {
		return given().contentType(ContentType.JSON).accept(ContentType.JSON); // same as specifying - application/json
	}

	/**
	 * To Perform GET request
	 */
	public static Response get(String endpoint) {
		logger.info("Sending GET request to: {}", endpoint);

		Response response = getRequestSpecification().get(endpoint);

		logResponse(response);
		return response;
	}

	/**
	 * To perform a GET request with path parameters
	 */
	public static Response get(String endpoint, Map<String, Object> pathParams) {
		logger.info("Sending DELETE request to: {} with path params: {}", endpoint, pathParams);

		Response response = getRequestSpecification().pathParams(pathParams).get(endpoint);

		logResponse(response);
		return response;
	}

	public static Response get(String endpoint, String userName) {
		logger.info("Sending GET request to: {} with userName: {}", endpoint, userName);

		Response response = getRequestSpecification().pathParams("username", userName).get(endpoint);

		logResponse(response);
		return response;
	}

	/**
	 * To perform a POST request with a payload
	 */
	public static Response post(String endpoint, Object payload) {
		logger.info("Sending POST request to: {} with payload: {}", endpoint, payload);

		Response response = getRequestSpecification().body(payload).post(endpoint);

		logResponse(response);
		return response;
	}

	/**
	 * To perform a PUT request with path parameters and a payload
	 */
	public static Response put(String endpoint, Map<String, Object> pathParams, Object payload) {
		logger.info("Sending PUT request to: {} with path params: {} and payload: {}", endpoint, pathParams, payload);

		Response response = getRequestSpecification().pathParams(pathParams).body(payload).put(endpoint);

		logResponse(response);
		return response;
	}

	public static Response put(String endpoint, String userName, Object payload) {
		logger.info("Sending PUT request to: {} with userName: {} and payload: {}", endpoint, userName, payload);

		Response response = getRequestSpecification().pathParams("username", userName).body(payload).put(endpoint);

		logResponse(response);
		return response;
	}

	/**
	 * To perform a DELETE request with path parameters
	 * 
	 * @param endpoint   API endpoint
	 * @param pathParams Path parameters
	 * @return Response object
	 */
	public static Response delete(String endpoint, Map<String, Object> pathParams) {
		logger.info("Sending DELETE request to: {} with path params: {}", endpoint, pathParams);

		Response response = getRequestSpecification().pathParams(pathParams).delete(endpoint);

		logResponse(response);
		return response;
	}

	public static Response delete(String endpoint, String userName) {
		logger.info("Sending DELETE request to: {} with userName: ", endpoint, userName);

		Response response = getRequestSpecification().pathParams("username", userName).delete(endpoint);

		logResponse(response);
		return response;
	}

	/**
	 * To log response details
	 */
	private static void logResponse(Response response) {
		/*
		 * Logging actual status code and body
		 */
		logger.info("Response Status Code: {}", response.getStatusCode());
		logger.info("Response Body: {}", response.getBody());
	}
}
