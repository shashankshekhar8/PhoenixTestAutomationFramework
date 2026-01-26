package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

public class CountAPITest {
	
	@Test(description = "Verify if the Count api is giving correct response", groups = {"api", "regression", "smoke"})
	public void verifyCountAPIResponse() {
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.notNullValue())
			.body("data.size()", Matchers.equalTo(3))
			.body("data.count",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
			.body("data.label", Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
			.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("response-schema" + File.separator + "CountAPIResponseSchema-FD.json"));
	}
	
	@Test(description = "Verify if the Count api is giving correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
	public void verifyCountAPI_MissingAuthToken() {
		given()
			.spec(SpecUtil.requestSpec())
		.when()
			.get("/dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
