package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIResponse() throws IOException {
		given()
			.baseUri(getProperty("BASE_URI"))
			.header("Authorization", getToken(Role.FD))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.body("message", Matchers.equalTo("Success"))
			.time(Matchers.lessThan(1000L))
			.body("data", Matchers.notNullValue())
			.body("data.size()", Matchers.equalTo(3))
			.body("data.count",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
			.body("data.label", Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
			.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("response-schema" + File.separator + "CountAPIResponseSchema-FD.json"));
	}
	
	@Test
	public void verifyCountAPI_MissingAuthToken() throws IOException {
		given()
			.baseUri(getProperty("BASE_URI"))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(401);
	}

}
