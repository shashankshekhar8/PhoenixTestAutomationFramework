package com.api.tests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {

	@Test
	public void loginAPITest() throws IOException {

		UserCredentials userCredentials = new UserCredentials("iamfd", "password");

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
