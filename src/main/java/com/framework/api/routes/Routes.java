package com.framework.api.routes;

/**
 * Contains all API endpoints for the PetStore API.
 */
public class Routes {
	
	/*
	 *  Base URL
	 */
	public static final String BASE_URL = System.getenv("API_BASE_URL") != null ? 
            System.getenv("API_BASE_URL") : 
            "https://petstore3.swagger.io/api/v3";

	/*
	 *  User endpoints
	 */
	public static final String USER_BASE = BASE_URL + "/user";
	public static final String USER_BY_USERNAME = USER_BASE + "/{username}";
}
