package com.api.tests;

import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.IOException;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.api.constants.Role;
import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	
	
	@Test
	public void masterAPITest() throws IOException {
		
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
	
	@Test
	public void masterAPITestInvalidTokenTest() throws IOException {
		given()
			.spec(SpecUtil.requestSpec())
		.when()
			.post("master")			
	    .then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
