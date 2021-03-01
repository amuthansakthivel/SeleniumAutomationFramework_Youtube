package com.tmb.tests;

import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.tmb.driver.Driver;
/**
 * Acts as a parent class for all the test classes in this framework.
 * All the test classes needs to extend this class. This class is responsible for invoking and terminating
 * browser under test.
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
 */
public class BaseTest {
	
	protected BaseTest() {}
	
	/**
	 * Invokes a new browser instance and loads the respective URL.
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 22, 2021
	 * @param data Have all the data feeded to the corresponding test method from data provider.In our case,it is
	 * a hashmap containing all the values from the excel sheet.
	 */
	@SuppressWarnings("unchecked")
	@BeforeMethod
	protected void setUp(Object[] data) { //Map<String,String>
		Map<String,String> map = (Map<String,String>)data[0];
		Driver.initDriver(map.get("browser"),map.get("version"));
	}

	/**
	 * Terminates the browser instance
	 * @author Amuthan Sakthivel
	 * Jan 22, 2021
	 */
	@AfterMethod
	protected void tearDown() {
		Driver.quitDriver();
	}



}
