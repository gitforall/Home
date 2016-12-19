package com.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class FireFoxDriver {

	WebDriver driver;
	
	@Test
	public void fireFoxDriver(){
		System.setProperty("webdriver.gecko.driver", "H:\\Testing\\selenium\\work\\drivers\\geckodriver.exe");

		driver = new FirefoxDriver();
		driver.get("https://www.google.co.in/");
	}
}
