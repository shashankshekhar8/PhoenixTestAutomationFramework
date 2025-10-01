package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.regex.Matcher;

public class MasterAPITest {
	
	
	@Test
	public void masterAPITest() throws IOException {
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.header("Authorization", getToken(Role.FD))
			.contentType("")
			.log().all()
		.when()
			.post("master")			// default content type 'application/url-formencoded' is attached if nothing is send in the post request
		.then()
			.log().all()
			.statusCode(200)
			.time(Matchers.lessThanOrEqualTo(1000L))
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.hasKey("mst_oem"))
			.body("data", Matchers.hasKey("mst_model"))
			.body("$", Matchers.hasKey("message"))
			.body("$", Matchers.hasKey("data"))
			.body("data.mst_oem.size()", Matchers.greaterThan(0))
			.body("data.mst_model.size()", Matchers.greaterThan(0))
			.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
			.body("data.mst_model.id", Matchers.everyItem(Matchers.notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
	}
	
	@Test
	public void masterAPITestInvalidTokenTest() throws IOException {
		given()
			.baseUri(getProperty("BASE_URI"))
			.header("Authorization", "")
			.contentType("")
			.log().all()
		.when()
			.post("master")			
	    .then()
			.log().all()
			.statusCode(401);
	}

}
