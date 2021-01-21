package com.tmb.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

	private ExtentManager() {}

	private static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>() ;

	public static ExtentTest getExtentTest() { //default --> it can be only accessed within the package --> private packages
		return extTest.get();
	}

	static void setExtentTest(ExtentTest test) {
		extTest.set(test);
	}

	static void unload() {
		extTest.remove();
	}
}
