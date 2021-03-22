package com.tmb.reports;

import java.util.Objects;

import com.aventstack.extentreports.ExtentTest;
import com.tmb.driver.Driver;

/**
 * ExtentManager class helps to achieve thread safety for the {@link com.aventstack.extentreports.ExtentTest} instance.
 * 
 * Jan 21, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 * @see Driver
 */
public class ExtentManager {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private ExtentManager() {}

	private static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>() ;

	/**
	 * Returns the thread safe {@link com.aventstack.extentreports.ExtentTest} instance fetched from ThreadLocal variable.
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 * @return Thread safe {@link com.aventstack.extentreports.ExtentTest} instance.
	 */
	 static ExtentTest getExtentTest() {
		return extTest.get();
	}

	/**
	 * Set the {@link com.aventstack.extentreports.ExtentTest} instance to thread local variable
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 * @param test {@link com.aventstack.extentreports.ExtentTest} instance that needs to saved from Thread safety issues.<p>
	 */
	static void setExtentTest(ExtentTest test) {
		if(Objects.nonNull(test)) {
		extTest.set(test);
		}
	}

	/**
	 * Calling remove method on Threadlocal variable ensures to set the default value to Threadlocal variable.
	 * It is much safer than assigning null value to ThreadLocal variable.
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 */
	static void unload() {
		extTest.remove();
	}
}
