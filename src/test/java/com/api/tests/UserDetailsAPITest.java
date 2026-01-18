package com.api.tests;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.IOException;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {

	@Test(description = "Verify if the UserDetails API response is shown correctly", groups = {"api", "regression", "smoke"})
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
