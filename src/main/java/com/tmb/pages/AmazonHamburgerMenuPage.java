package com.tmb.pages;

import com.tmb.enums.WaitStrategy;
import com.tmb.utils.DynamicXpathUtils;
import org.openqa.selenium.By;

public final class AmazonHamburgerMenuPage extends BasePage{

	private String linkComputers = "//div[text()='Mobiles, Computers']/parent::a";
	private  String linkSubMenu = "//a[text()='%s']";
	private  String linkSubMenu2 = "//div[text()='%s']/parent::a[text()='%s']";
	
	public AmazonHamburgerMenuPage clickComputer() {
		click(By.xpath(linkComputers), WaitStrategy.CLICKABLE, "Mobiles and Computers");
		return this;
	}
	
	/**
	 * 
	 * @author Amuthan Sakthivel
	 * Mar 20, 2021
	 * @param menutext
	 * @return
	 * TODO Using generics to create instance of page classes
	 */
	public AmazonLaptopPage clickOnSubMenuItem(String menutext) {
		String newxpath = DynamicXpathUtils.getXpath(linkSubMenu, menutext);
		click(By.xpath(newxpath), WaitStrategy.CLICKABLE, menutext);
		if(menutext.contains("Laptops")) {
			return new AmazonLaptopPage();
		}
		return null;
	}
	
	

}
