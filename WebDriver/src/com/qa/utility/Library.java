package com.qa.utility;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Library {

	static WebDriver driver;

	public Library(WebDriver ldriver) {
		Library.driver = ldriver;
	}

	public static WebDriver startBrowser(WebDriver driver, String browserType,
			String appURL) throws FileNotFoundException, IOException {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(driver, appURL);
			break;
		case "IEdriver":
			driver = IEdriver(driver, appURL);
			break;
		case "firefox":

			// driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
		return driver;
	}

	private static WebDriver IEdriver(WebDriver driver, String appURL)
			throws FileNotFoundException, IOException {
		
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		System.setProperty("webdriver.gecko.driver", "H:\\Testing\\selenium\\work\\drivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}

	private static WebDriver initChromeDriver(WebDriver driver, String appURL)
			throws FileNotFoundException, IOException {
		System.setProperty("webdriver.chrome.driver",
				"H:\\Testing\\selenium\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--test-type");
		driver = new ChromeDriver(options);
		return driver;
	}

}
