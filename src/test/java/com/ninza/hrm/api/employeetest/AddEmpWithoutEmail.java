package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.constants.endpoints.IEndpoint;
import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;
import com.ninza.hrm.api.pojoclass.Employee_POJO;
import com.ninza.hrm.api.pojoclass.POJO_Project;

import io.restassured.http.ContentType;
@Listeners(com.ninza.hrm.api.baseclass.ListenerImplementation.class)
public class AddEmpWithoutEmail extends BaseAPIClass {
	
	
	@Test
	public void addEmpWithoutEmail() throws Throwable {

		String baseUrl=fLib.getDataFromPropertiesFile("BASEURI");
		String projectName = "Airtel_"+jLib.getRandomNumber();
		String userName = " \"User_"+jLib.getRandomNumber();

		// add project

		POJO_Project pobj = new POJO_Project(projectName, "created", "swati", 0);

		given().spec(specReqObj).body(pobj).when().post(IEndpoint.ADDProj).then()
				.assertThat().statusCode(201).spec(specRespObj).log().all();

		// add employee
		
		Employee_POJO empObj = new Employee_POJO("QA", "08/12/1999", "", "User_" + jLib.getRandomNumber(), 5, "1234567891", projectName,
				"ROLE_EMPLOYEE", userName);

		given().spec(specReqObj).body(empObj).when().post(IEndpoint.ADDEmp).then()
				.assertThat().statusCode(500).and()
				.time(Matchers.lessThan(3000L)).spec(specRespObj).log().all();
		
		
	
		boolean flag=dLib.executeQueryVerifyAndGetData("select * from project;", 5, userName);
		Assert.assertEquals(flag, true, "db is not verified");
		Assert.assertEquals( flag,true,"emp db is not verified");
		


}
	}