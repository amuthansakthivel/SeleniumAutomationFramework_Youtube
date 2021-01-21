package com.tmb.driver;

import java.util.Objects;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.tmb.constants.FrameworkConstants;
import com.tmb.enums.ConfigProperties;
import com.tmb.utils.PropertyUtils;

/**
 * 
 * Driver class is responsible for invoking and closing the browsers.
 * 
 * <p>
 * It is also responsible for 
 * setting the driver variable to DriverManager which handles the thread safety for the 
 * webdriver instance.
 * 
 * <pre>Checking whether it is formatted correctly</pre>
 * 
 * Jan 20, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 * @see DriverManager
 * @see com.tmb.tests.BaseTest
 */

public final class Driver {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private Driver() {}

	/**
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 20, 2021
	 * @param browser Value will be passed from {@link com.tmb.tests.BaseTest}. Values can be chrome and firefox
	 */
	public static void initDriver(String browser)  {

		if(Objects.isNull(DriverManager.getDriver())) {
			if(browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",FrameworkConstants.getChromeDriverPath()); 
				DriverManager.setDriver(new ChromeDriver());
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",FrameworkConstants.getGeckoDriverPath()); 
				DriverManager.setDriver(new FirefoxDriver());
			}
			DriverManager.getDriver().get(PropertyUtils.get(ConfigProperties.URL));
		}
	}

	public static void quitDriver() {
		if(Objects.nonNull(DriverManager.getDriver())) {
			DriverManager.getDriver().quit();
			DriverManager.unload();
		}
	}

}
