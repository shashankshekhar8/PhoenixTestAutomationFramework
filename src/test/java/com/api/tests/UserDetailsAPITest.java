package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.constants.Role.*;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;

public class UserDetailsAPITest {

	@Test
	public void userDetailsAPITest() throws IOException {

		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.get("userdetails")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.and()
			.body("message", Matchers.equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema" + File.separator + "UserDetailsResponseSchema.json"));
	}

}
