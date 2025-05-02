# RestAssured based API Testing Framework

This is an automated testing framework, primarily designed for testing REST APIs, with a focus on the User endpoints of the PetStore REST API as an example. The framework supports comprehensive CRUD operations—Create, Read, Update, and Delete—while ensuring a scalable and maintainable architecture. The whole setup is dockerized as well.

## Overview

The framework tests the [Swagger PetStore API](https://petstore3.swagger.io/) User endpoints:
- Create a user
- Get user by username
- Update user details
- Delete user

## Tech Stack

The framework is built using the following technologies:

- **Java 11** - Programming language
- **Maven** - Dependency management and build tool
- **TestNG** - Test orchestration and execution
- **REST Assured** - API testing library
- **Java Faker** - Test data generation
- **ReportNG**: Enhanced HTML reporting for TestNG
- **SLF4J & Log4j2**: Comprehensive logging solution
- **Commons IO**: Utilities for file operations

## Project Structure

```
src/main/java
└── com
    └── framework
        └── api
            ├── payloads                        # POJO class
            │   └── UserPayloads.java
            ├── pojos                           # Request payload builders
            │   └── User.java
            ├── routes                          # API endpoints
            │   └── Routes.java
            └── utils
                ├── LogUtils                    # Logging
                └── RestAssuredUtils.java       # API request utilities

src/test/java
└── com
    └── framework
        └── api
            ├── base
            │   └── BaseTest.java               # Common test configuration
            └── tests
                  └── UserEndpointTests.java    # User endpoint tests

src/test/resources
└── schemas 
    ├── userSchema.json                         # Schema configurations
    └── api-tests.xml                           # TestNG configuration

pom.xml                                         # Dependencies configuration
Dockerfile                                      # Docker image
results                                         # Execution reports
logs                                            # Execution logs
```

## Setup Instructions

### Prerequisites

- Java JDK 11
- Maven 3.9 or higher

## Running Tests

### Execute tests in docker

Run the command
```
docker-compose up test-monefy
```

### Execute tests locally
Run the command
```
mvn clean test
```

## Test Reports

After test execution, reports can be found at:
```
results/index.html
```

A sample execution report could be found [here](results/index.html).

And the logs reside in:

```
logs/appium-tests.log
```


## Framework Design

### Key Components

1. **POJO Classes (Models)**
   - Java objects representing API entities
   - Used for serialization/deserialization

2. **Payload Factories**
   - Generate test data using Java Faker
   - Create request payloads for API calls

3. **Routes**
   - Central repository for all API endpoints
   - Easy to update or extend

4. **RestAssured Utilities**
   - Wrapper methods for API calls
   - Consistent request/response handling

5. **Base Test Class**
   - Common setup and configuration
   - TestNG configurations

6. **Test Classes**
   - Actual test implementations
   - AssertJ assertions for validation

7. **Reporting**
   - ReportNG Reports for detailed HTML reports
   - Test execution logs

### Test Flow

The tests follow a sequence that demonstrates API chaining:

1. Create a new user (POST)
2. Retrieve the user (GET)
3. Update the user (PUT)
4. Delete the user (DELETE)
5. Verify user schema

## Future Enhancements

Future versions of this framework could include:

- More detailed response validation
- Authentication testing
- Headers and cookies validation
- Data-driven testing from external sources

## Approach and Tech Stack Justification

### POJO Classes vs Alternatives (HashMap, org.json, external files)
I chose **POJO (Plain Old Java Object)** classes over alternatives like HashMap, org.json, or external JSON files for several reasons:

- **Type Safety**: POJOs provide compile-time type checking, reducing runtime errors that might occur with dynamic structures like HashMaps.
- **Code Maintainability**: Classes represent the API structure explicitly in code, making it easier to understand the data model.
- **Serialization/Deserialization**: Automatic mapping between Java objects and JSON, reducing manual conversion code.
- **Object-Oriented Features**: Inheritance, encapsulation, and other OO principles can be applied to test data models.

### TestNG Instead of Cucumber Despite BDD in RestAssured
While RestAssured supports BDD-style syntax, I chose **TestNG** over Cucumber for these reasons:

- **Test Organization**: TestNG provides powerful features for test organization (groups, dependencies, parallel execution) that are built into the framework.
- **Data Providers**: TestNG's data provider mechanism is more flexible for API testing than Cucumber's examples table.
- **API Testing Context**: BDD is more valuable for behavior specifications that bridge technical and non-technical stakeholders. For API testing that's primarily technical, TestNG's direct approach offers more flexibility with less overhead.
- **Technical Focus**: API testing is sometimes more technical and complex and might not always be easy to be handled with "Given-When-Then" plain language approach that Cucumber works with.

### Other Key Tech Stack Choices

- **Java Faker**: Chosen for dynamic data generation to ensure tests use diverse data sets, improving test coverage and reducing data maintenance.
- **ReportNG**: Offers a cleaner, more visually appealing and customizable report format, making it easier to analyze test results compared to the default TestNG reports.
- **Maven**: Industry standard for Java project management, dependency handling, and build automation.
- **Slf4j**: Offers flexibility logging that allows to plug in different logging frameworks (like Log4j, Logback, etc.) at runtime.

## Scalability and Maintainability

This solution (using POJOs, Routes, Payloads, RestUtils) is easy to scale and maintain for several reasons:

### Scalability Benefits
- **Modular Architecture**: Each component has a single responsibility, making it easy to add new endpoints or test cases.
- **Centralized Endpoint Management**: The `Routes` class makes it easy to update API endpoints in one place.
- **Reusable Utilities**: Common API operations are abstracted in `RestAssuredUtils`, reducing code duplication.
- **Parameterized Testing**: The framework supports data-driven testing, allowing for many test scenarios with minimal code.

### Maintainability Benefits
- **Separation of Concerns**: Clear separation between test data generation, API interactions, and assertions.
- **Reduced Duplication**: Common code is abstracted into reusable components.
- **Consistent Patterns**: Uniform approach to testing across all endpoints.
- **Centralized Configuration**: Environment-specific settings can be managed in configuration files.

## Shortcomings and Limitations
While the framework has many strengths, there are some potential shortcomings:

- **Unstable behaviour of petstore API**: Even when the service is operational, the Petstore API occasionally returns a 500 error, causing tests to fail or be skipped.
- **Learning Curve**: New team members might need time to understand the architecture and patterns.
- **Overkill for Simple Tests**: The structure might be overkill for very simple APIs with few endpoints.
- **Java-Specific**: Not easily transferable to other programming languages if the team works in a polyglot environment.
- **Dependency Management**: Requires keeping multiple dependencies updated (RestAssured, TestNG, ExtentReports, etc.).
- **Limited Environment Support**: More work would be needed to support testing across multiple environments (dev, staging, prod).
- **Exception Handling, Logging and Javadoc**: Exception handling could be made better and more strong. Log handling could be made mouch more stronger and Javadocs could be more descriptive.

These shortcomings could be addressed in future iterations of the framework.