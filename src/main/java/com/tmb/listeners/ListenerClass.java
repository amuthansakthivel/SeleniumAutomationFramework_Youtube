package com.tmb.listeners;

import com.tmb.annotations.FrameworkAnnotation;
import com.tmb.reports.ExtentReport;
import com.tmb.utils.ELKUtils;
import org.testng.*;

import java.util.Arrays;

import static com.tmb.enums.LogType.*;
import static com.tmb.reports.FrameworkLogger.log;

/**
 * Implements {@link org.testng.ITestListener} and {@link org.testng.ISuiteListener} to leverage the abstract methods
 * Mostly used to help in extent report generation
 * 
 * <pre>Please make sure to add the listener details in the testng.xml file</pre>
 * 
 * Jan 21, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 */
public class ListenerClass implements ITestListener, ISuiteListener {

	/**
	 * Initialise the reports with the file name
	 * @see com.tmb.reports.ExtentReport
	 */
	@Override
	public void onStart(ISuite suite) {
			ExtentReport.initReports();
	}

	/**
	 * Terminate the reports
	 * @see com.tmb.reports.ExtentReport
	 */
	@Override
	public void onFinish(ISuite suite) {
			ExtentReport.flushReports();
			
	}

	/**
	 * Starts a test node for each testng test
	 * @see com.tmb.reports.ExtentReport
	 * @see com.tmb.annotations.FrameworkAnnotation
	 */
	@Override
	public void onTestStart(ITestResult result) {
	
		ExtentReport.createTest(result.getMethod().getDescription());
		ExtentReport.addAuthors(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class)
			.author());
		ExtentReport.addCategories(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class)
			.category());
	}

	/**
	 * Marks the test as pass and logs it in the report
	 * @see com.tmb.reports.FrameworkLogger
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		//ExtentLogger.pass(result.getMethod().getMethodName() +" is passed");
		log(PASS,result.getMethod().getMethodName() +" is passed");
		ELKUtils.sendDetailsToElk(result.getMethod().getDescription(), "pass");
	}

	/**
	 * Marks the test as fail,append base64 screenshot and logs it in the report
	 * @see com.tmb.reports.FrameworkLogger
	 * @see com.tmb.utils.ScreenshotUtils
	 */
	@Override
	public void onTestFailure(ITestResult result) {
			log(FAIL,result.getMethod().getMethodName() +" is failed");
			log(FAIL,result.getThrowable().toString());
			log(FAIL,Arrays.toString(result.getThrowable().getStackTrace()));
			ELKUtils.sendDetailsToElk(result.getMethod().getDescription(), "fail");
	}

	/**
	 * Marks the test as skip and logs it in the report
	 * @see com.tmb.reports.FrameworkLogger
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		//ExtentLogger.skip(result.getMethod().getMethodName() +" is skipped");
		log(SKIP,result.getMethod().getMethodName() +" is skipped");
		ELKUtils.sendDetailsToElk(result.getMethod().getDescription(), "skip");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		/*
		 * For now, we are not using this.
		 */
	}

	@Override
	public void onStart(ITestContext context) {
		/*
		 * We are having just one test in our suite. So we dont have any special implementation as of now
		 */
	}

	@Override
	public void onFinish(ITestContext context) {
		/*
		 * We are having just one test in our suite. So we dont have any special implementation as of now
		 */
		
	}

}
