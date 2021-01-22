/*
MIT License
Copyright (c) 2021 Amuthan Sakthivel
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

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
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
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
