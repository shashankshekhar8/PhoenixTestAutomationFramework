package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	
	
	@Test(description = "Verify if the Master api is giving correct response", groups = {"api", "regression", "smoke"})
	public void masterAPITest() {
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD))
		.when()
			.post("master")			// default content type 'application/url-formencoded' is attached if nothing is send in the post request
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.hasKey("mst_oem"))
			.body("data", Matchers.hasKey("mst_model"))
			.body("$", Matchers.hasKey("message"))
			.body("$", Matchers.hasKey("data"))
			.body("data.mst_oem.size()", Matchers.greaterThan(0))
			.body("data.mst_model.size()", Matchers.greaterThan(0))
			.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
			.body("data.mst_model.id", Matchers.everyItem(Matchers.notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema"+ File.separator +"MasterAPIResponseSchema.json"));
	}
	
	@Test(description = "Verify if the Master api is giving correct status code for invalid token", groups = {"api", "negative", "regression", "smoke"})
	public void masterAPITestInvalidTokenTest() {
		given()
			.spec(SpecUtil.requestSpec())
		.when()
			.post("master")			
	    .then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
