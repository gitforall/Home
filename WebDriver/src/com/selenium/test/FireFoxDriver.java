package com.selenium.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class FireFoxDriver {

	WebDriver driver;
	
	@Test
	public void fireFoxDriver() throws IOException{
		System.setProperty("webdriver.gecko.driver", "H:\\Testing\\selenium\\work\\drivers\\geckodriver.exe");

		driver = new FirefoxDriver();
		driver.get("https://www.google.co.in/");
		
		
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(""));
		
	}
}
