package com.selenium.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

public class google {
	
	@Test
	public void test(){
		System.setProperty("webdriver.gecko.driver", "H:\\Testing\\selenium\\work\\drivers\\geckodriver.exe");
		final WebDriver driver= new FirefoxDriver();
		driver.get("http://toolsqa.com/iframe-practice-page/");
		WebElement ele;
		Dimension d = new Dimension(100, 100);
		driver.manage().window().setSize(d);
	//	driver.navigate().to("javascript:document.getElementById(‘overridelink’).click()");		
		JavascriptExecutor js = (JavascriptExecutor)driver;
	//	js.executeScript("alert('hello world');");
		js.executeScript("window.scrollBy(0,150)");
		
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver).register(new AbstractWebDriverEventListener() {
			public void onException() {
			// Take the screenshot using the Webdriver.
			File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			// Now you can copy the screenshot somewhere on your system.
			try {
				FileUtils.copyFile(screen, new File("c:\\Selenium Testing Questions\\screen.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			});
		
	}
}
