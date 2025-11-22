package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

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
	
	@Test
	public void verifyCountAPI_MissingAuthToken() throws IOException {
		given()
			.spec(SpecUtil.requestSpec())
		.when()
			.get("/dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
