package com.framework.api.tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.framework.api.bases.BaseTest;
import com.framework.api.payloads.UserPayload;
import com.framework.api.pojos.User;
import com.framework.api.routes.Routes;
import com.framework.api.utils.RestAssuredUtils;

/**
 * Tests for the CRUD operations on User endpoints - with the concept of API chaining
 */
public class UserEndpointTests extends BaseTest {

	private User newUser;
	private Response responseForSchemaValidation;

	@Test(suiteName = "PetStore API Test Suite", testName = "Create User Test", 
			description = "Test A POST request that creates a user", priority = 1)
	public void testCreateUser() {
		Reporter.log("Running test: Create a new user");
		logger.info("Running test: Create a new user");

		/*
		 * Create a random user
		 */
		User createUserPayload = UserPayload.createRandomUser();
		Response response = RestAssuredUtils.post(Routes.USER_BASE, createUserPayload);

		/*
		 * Validate the response
		 */
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

		/*
		 * Save the created user for future tests
		 */
		newUser = createUserPayload;
	}

	@Test(suiteName = "PetStore API Test Suite", testName = "Get User Test", 
			description = "Test A GET request that tries to retrieve the newly created user created in previous test", priority = 2, 
			dependsOnMethods = "testCreateUser")
	public void testGetUserByUsername() {
		Reporter.log("Running test: Get user by username");
		logger.info("Running test: Get user by username");

		/*
		 * Get the user by username
		 */
		Response response = RestAssuredUtils.get(Routes.USER_BY_USERNAME, newUser.getUsername());
		
		/*
		 * To do schema validation later
		 */
		responseForSchemaValidation = response;

		/*
		 * Validate the response
		 */
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

		User retrievedUser = response.then().extract().body().as(User.class);

		Assert.assertEquals(retrievedUser.getId(), newUser.getId(),
				"IDs of the new user and retrieved user does not match");

		Assert.assertEquals(retrievedUser.getUsername(), newUser.getUsername(),
				"Usernames of the new user and retrieved user does not match");

		Assert.assertEquals(retrievedUser.getFirstName(), newUser.getFirstName(),
				"FirstNames of the new user and retrieved user does not match");

		Assert.assertEquals(retrievedUser.getLastName(), newUser.getLastName(),
				"LastNames of the new user and retrieved user does not match");

		Assert.assertEquals(retrievedUser.getEmail(), newUser.getEmail(),
				"Email-IDs of the new user and retrieved user does not match");

		Assert.assertEquals(retrievedUser.getPassword(), newUser.getPassword(),
				"Passwords of the new user and retrieved user does not match");

		Assert.assertEquals(retrievedUser.getPhone(), newUser.getPhone(),
				"User status of the new user and retrieved user does not match");
	}

	@Test(suiteName = "PetStore API Test Suite", testName = "Update user test", 
			description = "Test A PUT request that tries to update details of the newly created user created in first test", priority = 3, 
			dependsOnMethods = "testCreateUser")
	public void testUpdateUser() {
		Reporter.log("Running test: Update user");
		logger.info("Running test: Update user");

		/*
		 * Update the user details of the user
		 */
		User updateUserPayload = UserPayload.createRandomUser();
		updateUserPayload.setUsername(newUser.getUsername());

		/*
		 * Update the user email and password
		 */
		Response response = RestAssuredUtils.put(Routes.USER_BY_USERNAME, newUser.getUsername(), updateUserPayload);

		User retrieveUserFromResponse = response.then().extract().body().as(User.class);

		/*
		 * Validate the response
		 */
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

		Assert.assertEquals(retrieveUserFromResponse.getId(), updateUserPayload.getId(),
				"IDs was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getUsername(), newUser.getUsername(),
				"User names was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getFirstName(), updateUserPayload.getFirstName(),
				"FirstNames was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getLastName(), updateUserPayload.getLastName(),
				"LastNames was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getEmail(), updateUserPayload.getEmail(),
				"Email-IDs was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getPassword(), updateUserPayload.getPassword(),
				"Passwords was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getPhone(), updateUserPayload.getPhone(),
				"Phones was not updated successfully");

		Assert.assertEquals(retrieveUserFromResponse.getUserStatus(), updateUserPayload.getUserStatus(),
				"User status was not updated successfully");
		/*
		 * Update reference for folowing tests
		 */
		newUser = retrieveUserFromResponse;
	}

	@Test(suiteName = "PetStore API Test Suite", testName = "Test delete user operation", 
			description = "Test A DELETE request that tries to update details of the newly created user created in first test", priority = 4, 
			dependsOnMethods = "testUpdateUser")
	public void testDeleteUser() {
		Reporter.log("Running test: Delete user");
		logger.info("Running test: Delete user");

		/*
		 * Delete the user
		 */
		Response response = RestAssuredUtils.delete(Routes.USER_BY_USERNAME, newUser.getUsername());

		/*
		 * Validate the response
		 */
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

		/*
		 * Verify the user is deleted by trying a get request
		 */
		Response getResponse = RestAssuredUtils.get(Routes.USER_BY_USERNAME, newUser.getUsername());
		Assert.assertEquals(getResponse.getStatusCode(), 404, "User should be deleted and return 404");
	}
	
	@Test(suiteName = "PetStore API Test Suite", testName = "Verify user schema", 
			description = "Test if the user schema is as expected", priority = 5, 
			dependsOnMethods = "testGetUserByUsername")
	public void testUserSchema() {
		Reporter.log("Running test: Schema Validation");
		logger.info("Running test: Schema Validation");

		/*
		 * Response saved from get response
		 */
	    Assert.assertTrue(
	    		JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/userSchema.json").matches(responseForSchemaValidation.body().asString()),
	            "Response does not match expected schema"
	        );
	}
}