package com.ninza.hrm.api.baseclass;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ListenerImplementation implements ITestListener, ISuiteListener {

	public ExtentReports report;
	public static ExtentSparkReporter spark;
	public static ExtentTest test;

	public void onStart(ITestContext context) {

		System.out.println("Report Configuration");

		// Spark Report Config High level configuration

		String time = new Date().toString().replace(" ", "_").replace(":", "_");

		spark = new ExtentSparkReporter("./AdvanceReport/report" + time + ".html");
		spark.config().setDocumentTitle("CRM Test Suite Result");
		spark.config().setReportName("CRM report");
		spark.config().setTheme(Theme.DARK);

		// add Env. information and Create Test
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("os", "Windows-10");
		report.setSystemInfo("Browser", "Chrome-100");

	}

	public void onFinish(ITestContext context) {
		System.out.println("Report backUP");
		report.flush();

	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("======" + result.getMethod().getMethodName() + "=======START=======");
		test = report.createTest(result.getMethod().getMethodName());

		test.log(Status.INFO, result.getMethod().getMethodName() + "=======>STARTED>============");
		test.log(Status.INFO, result.getThrowable() + "=======>FAILED>=========");

	}

	@Override
	public void onTestSuccess(ITestResult result) {

		System.out.println("======" + result.getMethod().getMethodName() + "======END========");

		test.log(Status.PASS, result.getMethod().getMethodName() + "=======>COMPLETED<=======");
		test.log(Status.PASS, result.getThrowable() + "=======>FAILED>=========");

	}

	@Override
	public void onTestFailure(ITestResult result) {

		String testName = result.getMethod().getMethodName();
		System.out.println(testName);

		test.log(Status.FAIL, result.getMethod().getMethodName() + "=======>FAILED>=========");
		test.log(Status.FAIL, result.getThrowable() + "=======>FAILED>=========");

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ITestListener.super.onTestSkipped(result);
		test.log(Status.SKIP, result.getThrowable() + "=======>FAILED>=========");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
		test.log(Status.FAIL, result.getThrowable() + "=======>FAILED>=========");

	}

}
