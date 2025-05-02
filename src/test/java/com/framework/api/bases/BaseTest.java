package com.framework.api.bases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import com.framework.api.routes.Routes;
import com.framework.api.utils.LogUtils;
import io.restassured.RestAssured;

/*
 * Base class for all API tests
 * This class sets up the base URI for all REST Assured requests and configures logging filters.
 */
@Listeners({ org.uncommons.reportng.HTMLReporter.class, org.uncommons.reportng.JUnitXMLReporter.class })
public class BaseTest {

	protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	protected LogUtils logUtils = new LogUtils();

	@BeforeClass
	public void setup() {
		/*
		 * Set the base URI for all REST Assured requests
		 */
		RestAssured.baseURI = Routes.BASE_URL;

		/*
		 * Setup filters for logging
		 */
		logUtils.initialiseLogging();

		logger.info("REST Assured configured with base URL: {} and initialised logging", Routes.BASE_URL);
	}
}
