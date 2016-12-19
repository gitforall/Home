package com.qa.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StartBrowser {

	static WebDriver driver;

	public static  WebDriver open(String BrowserName , String url){
		
		if(BrowserName.equalsIgnoreCase("Firefox")){
			System.setProperty("webdriver.gecko.driver", "H:\\Testing\\selenium\\work\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}else if(BrowserName.equalsIgnoreCase("Chrome")){
			  System.setProperty("webdriver.chrome.driver",  "H:\\Testing\\selenium\\work\\drivers\\chromedriver.exe"); 
			  ChromeOptions  options = new ChromeOptions();
			  options.addArguments("--disable-extensions");
			  options.addArguments("--test-type");
			    driver = new  ChromeDriver(options);
		}
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return driver;
		
	}

}
