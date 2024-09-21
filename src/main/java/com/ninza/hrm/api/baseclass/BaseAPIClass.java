package com.ninza.hrm.api.baseclass;

import java.sql.SQLException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIClass {

	public JavaUtility jLib = new JavaUtility();
	public FileUtility fLib = new FileUtility();
	public DataBaseUtility dLib = new DataBaseUtility();

	public ExtentReports report;
	public static ExtentSparkReporter spark;
	public static ExtentTest test;

	public static RequestSpecification specReqObj;
	public static ResponseSpecification specRespObj;

	@BeforeSuite
	public void configBS() throws Throwable {
		dLib.getDbConnection();
		System.out.println("===========connect to db=============");

		RequestSpecBuilder reqSpec = new RequestSpecBuilder();
		reqSpec.setContentType(ContentType.JSON);
		// reqSpec.setAuth(basic("UN", "PWD"));
		// reqSpec.addHeader("", "");
		reqSpec.setBaseUri(fLib.getDataFromPropertiesFile("BASEURI"));
		specReqObj = reqSpec.build();

		ResponseSpecBuilder respSpec = new ResponseSpecBuilder();
		respSpec.expectContentType(ContentType.JSON);
		specRespObj = respSpec.build();

	}

	@AfterSuite
	public void configAS() throws SQLException {
		dLib.closeDbConnection();
		System.out.println("=============close db connection==============");

	}

}
