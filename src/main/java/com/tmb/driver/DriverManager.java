package com.tmb.driver;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

/**
 * DriverManager class helps to achieve thread safety for the {@link org.openqa.selenium.WebDriver} instance.
 * 
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
 * 
 * Jan 21, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 * @see Driver
 */
public final class DriverManager {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private DriverManager() {}

	private static ThreadLocal<WebDriver> dr = new ThreadLocal<>() ;

	/**
	 * Returns the thread safe {@link org.openqa.selenium.WebDriver} instance fetched from ThreadLocal variable.
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 * @return {@link org.openqa.selenium.WebDriver} instance.
	 */
	public static WebDriver getDriver() {
		return dr.get();
	}

	/**
	 * Set the WebDriver instance to thread local variable
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 * @param driverref {@link org.openqa.selenium.WebDriver} instance that needs to saved from Thread safety issues.<p>
	 */
	static void setDriver(WebDriver driverref) {
		if(Objects.nonNull(driverref)) {
			dr.set(driverref);
		}
	}
	/**
	 * Calling remove method on Threadlocal variable ensures to set the default value to Threadlocal variable.
	 * It is much safer than assigning null value to ThreadLocal variable.
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 */
	static void unload() {
		dr.remove();
	}

}
