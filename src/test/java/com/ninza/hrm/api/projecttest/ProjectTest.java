package com.ninza.hrm.api.projecttest;

import static io.restassured.RestAssured.*;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.constants.endpoints.IEndpoint;
import com.ninza.hrm.api.pojoclass.POJO_Project;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
@Listeners(com.ninza.hrm.api.baseclass.ListenerImplementation.class)
public class ProjectTest extends BaseAPIClass {

	POJO_Project pobj;
	public static String projectName;
	// public static String BASEUrl ;

	@Test
	public void addSingleProjectWithCreatedTest() throws Throwable {

		String baseUrl = fLib.getDataFromPropertiesFile("BASEURI");

		String expectedSucssMsg = "Successfully Added";
		 projectName = "CRM_" + jLib.getRandomNumber();

		pobj = new POJO_Project(projectName, "created", "swati", 0);

		// verify the project name in api layer

		Response resp = given().spec(specReqObj).body(pobj).when().post(IEndpoint.ADDProj);

		resp.then().assertThat().statusCode(201).assertThat().time(Matchers.lessThan(3000L)).assertThat()
				.spec(specRespObj).log().all();
       String projectId=resp.jsonPath().get("projectId");
		String actMsg = resp.jsonPath().get("msg");
		Assert.assertEquals(expectedSucssMsg, actMsg);
		

		// verify the project name in db layer
	boolean flag = dLib.executeQueryVerifyAndGetData("select project_name from project where project_id=\""+projectId+"\";", 1, projectName);
		Assert.assertEquals(flag, true, "db is not verified");

	}

	@Test(dependsOnMethods = "addSingleProjectWithCreatedTest")
	public void createDuplicateProject() throws Throwable {

		
		JSONObject jObj = new JSONObject();
		jObj.put("projectName", projectName);
		jObj.put("status", "created");
		jObj.put("createdBy", "swati");
		jObj.put("teamSize", 0);
		
		String baseUrl = fLib.getDataFromPropertiesFile("BASEURI");


		Response resp = given().spec(specReqObj).body(pobj).when().post(IEndpoint.ADDProj);

		resp.then().assertThat().statusCode(409).spec(specRespObj).log().all();

		
		//resp.then().assertThat().body("message", Matchers.equalTo("The Project name" + projectName + "Already Exists"));

	}

}
