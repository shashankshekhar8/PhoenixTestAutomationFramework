package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() throws IOException {
		
		Customer customer = new Customer("Shashank", "Shekhar", "9742666189", "", "101shashank@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("106", "Shroff Soleno", "Baner Mahalunge road", "Near Orchid Hotel", "Mahalunge", "411045", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z", "59999999999999", "59999999999999", "59999999999999", "2025-04-06T18:30:00.000Z", 1 , 1);
		Problems problems = new Problems(1, "Battery Issue");
		
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems); 
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList); 
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPISchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
	}

}
