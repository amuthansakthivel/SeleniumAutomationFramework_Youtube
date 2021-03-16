package com.tmb.tests;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.tmb.annotations.FrameworkAnnotation;
import com.tmb.enums.CategoryType;
import com.tmb.pages.OrangeHRMLoginPage;
/**
 * Contains the tests related to Orange HRM page. For more details,
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
public final class OrangeHRMTests extends BaseTest {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private OrangeHRMTests() {}

	/**
	 * Test Name mentioned here should match the column name "testname" in excel sheet.This is mandatory to run this
	 * test. Otherwise it will be ignored.
	 * The match has to be there in both of the RUNMANAGER and TESTDATA sheet
	 * Set the authors who have the created the test which will be logged to the reports
	 * Set the category which this particular test case belongs to
	 * @author Amuthan Sakthivel
	 * Jan 22, 2021
	 * @param data HashMap containing all the values of test data needed to run the tests
	 */
	@Test
	@FrameworkAnnotation(author= {"Amuthan","Sachin"}, 
	category = {CategoryType.REGRESSION})
	public void loginLogoutTest(Map<String,String> data) {
		
		String title = new OrangeHRMLoginPage()
				.enterUserName(data.get("username")).enterPassWord(data.get("password")).clickLogin()
				.clickWelcome().clickLogout()
				.getTitle();
		Assertions.assertThat(title)
			.isEqualTo("OrangeHRM123");
		
	}
	
	@Test
	public void newTest(Map<String,String> data) {
		String title = new OrangeHRMLoginPage()
				.enterUserName(data.get("username")).enterPassWord(data.get("password")).clickLogin()
				.clickWelcome().clickLogout()
				.getTitle();
		throw new SkipException("skip");
		/*Assertions.assertThat(title)
			.isEqualTo("OrangeHRM");*/
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


	
}
