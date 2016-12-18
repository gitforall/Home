package com.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class google {

	//static WebDriver driver;

	public static void main(String[] args) {

	
		  System.setProperty("webdriver.chrome.driver",  "H:\\Testing\\selenium\\drivers\\chromedriver.exe"); 
		  ChromeOptions  options = new ChromeOptions();
		  options.addArguments("--disable-extensions");
		  options.addArguments("--test-type");
		  WebDriver  driver = new  ChromeDriver(options);
		 

		/*System.setProperty("webdriver.gecko.driver",
				"H:\\Testing\\selenium\\drivers\\geckodriver.exe");
		driver = new FirefoxDriver();*/

		driver.get("https://www.google.co.in/");
		System.out.println("Hari");
	}
}
