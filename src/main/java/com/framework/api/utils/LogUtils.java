package com.framework.api.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

/**
 * Utility class for logging REST Assured requests and responses to a file.
 * This class sets up filters for logging the request and response details.
 */
public class LogUtils {

	private static final String LOG_PATH = "logs/restAssured-logs.log";

	/*
	 * For logging
	 */
	private RequestLoggingFilter requestLoggingFilter;
	private ResponseLoggingFilter responseLoggingFilter;

	public LogUtils() {
	}

	public void initialiseLogging() {
		/*
		 * Setup filters for logging with logging filters
		 */
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(LOG_PATH);
			PrintStream log = new PrintStream(fos, true);
			requestLoggingFilter = new RequestLoggingFilter(log);
			responseLoggingFilter = new ResponseLoggingFilter(log);
			RestAssured.filters(requestLoggingFilter, responseLoggingFilter);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
