package com.tmb.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.tmb.driver.DriverManager;

/**
 * Utility to take base64 screenshot.
 * 
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
 * 
 * Jan 22, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 * @see com.tmb.reports.ExtentLogger
 */
public final class ScreenshotUtils {
	
	/**
	 * Private constructor to avoid external instantiation
	 */
	private ScreenshotUtils() {}
	
	/**
	 * Captures screenshot of the current page, constructs a base64 string from the image and return to the caller.
	 * There is no temporary screenshot image generated here. If user needs separate screenshot image, they can construct
	 * a new method. It is advisable to use this method for many reasons.
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 22, 2021
	 * @return Image in the form of Base64 String which can be appended directly to report
	 */
	public static String getBase64Image() {
		return ((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
	}

}
