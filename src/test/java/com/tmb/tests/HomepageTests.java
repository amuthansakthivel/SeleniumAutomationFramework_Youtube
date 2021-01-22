package com.tmb.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.tmb.driver.DriverManager;


public final class HomepageTests extends BaseTest{
	
	private HomepageTests() {}
	/*
	 * Validate whether the title is containing Google Search or google search
	 * validate whether the title is not null and the length of title is greater than 15 and less than 100
	 * Check for the links in the pages --> Testing mini bytes - Youtube
	 * number of links displayed is exactly 10 or 15
	 */
	
	@Test
	public void test3() {
		DriverManager.getDriver().findElement(By.name("q")).sendKeys("Testing mini bytes - Youtube",Keys.ENTER);
			
		String title = DriverManager.getDriver().getTitle();
		
		assertThat(title)
			.as("Object is actually null").isNotNull()
			.as("It does not contains expected text").containsIgnoringCase("google search")
			.matches("\\w.*"+"Google Search")
			.hasSizeBetween(15, 100);
		
		
		List<WebElement> elements = DriverManager.getDriver().findElements(By.xpath("//h3/span"));
		
		assertThat(elements)
			.hasSize(10)
			.extracting(WebElement :: getText)
			.contains("Testing Mini Bytes - YouTube");
		
		
		
	}
	
	

}
