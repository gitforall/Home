package com.qa.wordpress;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.qa.pages.LoginPage;
import com.qa.utility.StartBrowser;

public class WordPressLogin {

	@Test
	public void login() {

		WebDriver driver = StartBrowser.open("chrome","https://wordpress.com/wp-login.php");

		LoginPage login_page = PageFactory.initElements(driver, LoginPage.class);
		
		login_page.login("seleniumqain","07G31a1250");

	}
}
