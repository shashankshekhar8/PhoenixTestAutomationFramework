package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	UserCredentials userCredentials;
	
	@BeforeMethod(description = "Create the Payload for login API")
	public void setUp() {
		userCredentials = new UserCredentials("iamfd", "password");
	}

	@Test(description = "Verify if login api is working for FD user", groups = {"api", "regression", "smoke"})
	public void loginAPITest() {

		given()
			.spec(SpecUtil.requestSpec(userCredentials))
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.and()
			.body("message", Matchers.equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema" + File.separator + "LoginResponseSchema.json"));
	}

}
