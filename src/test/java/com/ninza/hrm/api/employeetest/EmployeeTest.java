package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.constants.endpoints.IEndpoint;
import com.ninza.hrm.api.pojoclass.Employee_POJO;
import com.ninza.hrm.api.pojoclass.POJO_Project;

@Listeners(com.ninza.hrm.api.baseclass.ListenerImplementation.class)
public class EmployeeTest extends BaseAPIClass {
	
	String projectName = "Airtel_" + jLib.getRandomNumber();
	String userName = " \"User_" + jLib.getRandomNumber();

	@Test
	public void addEmployeeTest() throws Throwable 
	{
        String baseUrl = fLib.getDataFromPropertiesFile("BASEURI");

		// add project

		POJO_Project pobj = new POJO_Project(projectName, "created", "swati", 0);

		given().spec(specReqObj).body(pobj).when().post(IEndpoint.ADDProj).then().assertThat()
				.statusCode(201).spec(specRespObj).log().all();

		// add employee

		Employee_POJO empObj = new Employee_POJO("QA", "08/12/1999", "swathiiiiiiiiii@gmail.com",
				"User_" + jLib.getRandomNumber(), 5, "1234567891", projectName, "ROLE_EMPLOYEE", userName);

		given().spec(specReqObj).body(empObj).when().post(IEndpoint.ADDEmp).then().assertThat()
				.spec(specRespObj).assertThat().statusCode(201).and().time(Matchers.lessThan(3000L)).spec(specRespObj).log()
				.all();

		// verify employee name in database

		boolean flag = dLib.executeQueryVerifyAndGetData("select * from employee;", 5, userName);
		Assert.assertEquals(flag, true, "db is not verified");
		Assert.assertEquals(flag, true, "emp db is not verified");

		// add emp without email address
	}
		
		
		@Test
		public void addEmpWithoutMail() throws Throwable {
			
			String baseUrl = fLib.getDataFromPropertiesFile("BASEURI");


		Employee_POJO empObj1 = new Employee_POJO("QA", "08/12/1999", "",
				"User_" + jLib.getRandomNumber(), 5, "1234567891", projectName, "ROLE_EMPLOYEE", userName);

		given().spec(specReqObj).body(empObj1).when().post(IEndpoint.ADDEmp).then().assertThat()
				.assertThat().statusCode(500).and().time(Matchers.lessThan(3000L)).spec(specRespObj).log()
				.all();

		// verify employee name in database

		boolean flag1 = dLib.executeQueryVerifyAndGetData("select * from employee;", 5, userName);
		Assert.assertEquals(flag1, true, "db is not verified");
		Assert.assertEquals(flag1, true, "emp db is not verified");

	}
}
