package com.tmb.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.tmb.driver.DriverManager;


public final class LoginpageTests extends BaseTest{
	
	private LoginpageTests(){}
	
	@Test
	public void test2() {
		DriverManager.getDriver().findElement(By.name("q")).sendKeys("Automation",Keys.ENTER);
	}

}
