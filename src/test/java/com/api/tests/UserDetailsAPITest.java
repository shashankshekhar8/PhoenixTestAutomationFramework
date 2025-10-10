package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.constants.Role.*;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;

public class UserDetailsAPITest {

	@Test
	public void userDetailsAPITest() throws IOException {

		Header header = new Header("Authorization", getToken(FD));

		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.and()
			.accept(ContentType.JSON)
			.and()
			.header(header)
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("userdetails")
		.then()
			.log().all()
			.statusCode(Matchers.equalTo(200))
			.and()
			.time(Matchers.lessThan(5000L))
			.and()
			.body("message", Matchers.equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema" + File.separator + "UserDetailsResponseSchema.json"));
	}

}
