package com.qa.wordpress;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.obj.Repo.LoginPage;
import com.qa.utility.Browser;

public class WordPressLogin {

	@Test
	public void login() {

		WebDriver driver = Browser.open("chrome","https://wordpress.com/wp-login.php");

		LoginPage login_page = PageFactory.initElements(driver, LoginPage.class);
		
		login_page.login("seleniumqain","07G31a1250");

	}
}
