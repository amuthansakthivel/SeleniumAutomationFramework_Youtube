package com.tmb.pages;

import com.tmb.driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public final class AmazonHomePage extends BasePage{

	@FindBy(id="nav-hamburger-menu")
	private WebElement linkHamburger;
	
	/**
	 * Constructor to initialise the page factory elements. It is not recommended to use page factory
	 * This is an sample for demonstration purpose
	 */
	public AmazonHomePage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	public AmazonHamburgerMenuPage clickHamburger() {
		linkHamburger.click();
		return new AmazonHamburgerMenuPage();
	}
}
