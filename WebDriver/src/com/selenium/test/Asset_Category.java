package com.selenium.test;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.GridType;
import com.relevantcodes.extentreports.LogStatus;
import com.webdriver.library.backup.utility;

public class Asset_Category {


	private WebDriver driver;
	Properties prop = new Properties();
	utility u = new utility();
	String browserType = "chrome";
	String appURL = "http://192.168.1.190/AMS";
	static final ExtentReports extent = ExtentReports.get(Asset_Category.class);
	
	private String G_Category_type; 
	private String G_catCode;
	private String G_catName;

	static ResultSet result;

	@BeforeClass
	public void Category_master() {
		try {
			prop.load(new FileInputStream(utility.locators_prop_file));
			driver = u.setDriver(driver, browserType, appURL); 
			utility.loginuser(driver, "admin", "admin",
					prop.getProperty("user_name_location"),
					prop.getProperty("passwd_location"));
			WebElement element = driver.findElement(By.linkText("Masters"));
			utility.Mousehover(driver, element);
			driver.findElement(By.linkText("Asset Category")).click();
			driver.findElement(By.cssSelector("a.create")).click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@BeforeTest
	public void loginit() {

		extent.init(utility.html_report_file + "Asset_Category.html", true,
				DisplayOrder.BY_OLDEST_TO_LATEST, GridType.STANDARD);
		extent.config().displayTestHeaders(true);
		extent.config().documentTitle("ASM test Reports");
		extent.config().reportHeadline("Automation Test Report for ASM");
	}

	@DataProvider(name = "DP1")
	public Object[][] createData1() throws Exception {

		// String path = prop.getProperty("Testdata_excel");
		String path = u.Excel_path;
		Object[][] retObjArr = u.getTableArray(path, "Masters", "cat_start",
				"cat_end");
		return (retObjArr);
	}

	@Test(dataProvider = "DP1")
	public void testDataProviderExample(String Category, String Code, String Name, String Satus ) throws Exception {
		
		this.G_Category_type=Category;	
		this.G_catCode=Code;
		this.G_catName=Name;
	

		try {

			extent.startTest("Data Provider successfully fetched Category master data from data Repository");
			extent.log(LogStatus.INFO,"Data provider trying to enter fetched data");

			boolean validate_Code = u.SpecialCharactervalidation(Code);
			boolean validate_Name = u.SpecialCharactervalidation(Name);
			
			// Assert.assertEquals( driver.getTitle(), "ASM");

			new Select(driver.findElement(By.id("Type"))).selectByVisibleText(Category);
	
			driver.findElement(By.id("Code")).clear();
			driver.findElement(By.id("Code")).sendKeys(Code);
			
			driver.findElement(By.cssSelector("#Value")).clear();
			driver.findElement(By.cssSelector("#Value")).sendKeys(Name);

			if (validate_Code && validate_Name ) {

				extent.log(LogStatus.INFO,
						"Category master details validation done");
				extent.log(LogStatus.INFO,
						"Category master details Entered sucessfully ");
				extent.attachScreenshot(utility.createScreenshot(driver),
						"This is to attach screenshot for Category  master");
				driver.findElement(By.cssSelector("#btnsubmit")).click();
				extent.log(LogStatus.INFO, "Click on submit button");
				System.out.println("submit");
				Thread.sleep(500L);
				//driver.switchTo().alert().dismiss();
				//extent.log(LogStatus.INFO,	"Switching to alert button and dismiss the alert");
				 dbvalidation();
				extent.log(LogStatus.INFO, "Validating DB details");

			} else {

				extent.startTest("Category master data validation failed");
				extent.log(LogStatus.ERROR,
						"Something went wrong in Category master data");
				extent.attachScreenshot(utility.createScreenshot(driver),
						"This is to attach screenshot for state  master");
			}

		} catch (Exception e) {
			Assert.fail();
		}

	}
	
	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();

			return true;
		}// try
		catch (NoAlertPresentException e) {
			return false;
		}// catch
	}
	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult)
			throws IOException, InterruptedException {

		if (testResult.getStatus() == ITestResult.FAILURE) {

			if (isAlertPresent(driver)) {
				extent.startTest("Test fail due to unexpected alert");
				String alert_text = driver.switchTo().alert().getText();
				Thread.sleep(50L);
				System.out.println("unexpected alert text : " + alert_text);
				driver.switchTo().alert().dismiss();
				extent.log(LogStatus.ERROR, "unexpected alert text : "
						+ alert_text);
				extent.attachScreenshot(utility.createScreenshot(driver),
						"This is to attach screenshot unexpected alert text : "
								+ alert_text);
				driver.quit();
			} else {

				extent.startTest("On Test Failure");
				System.out.println(testResult.getMethod());
				extent.log(LogStatus.ERROR, "Test has failed ");
				extent.attachScreenshot(utility.createScreenshot(driver),
						"This is to attach screenshot due to some thing went wrong");
				System.out.println("END");
				driver.quit();
			}

		}

	}
	
	
	public void dbvalidation() throws InterruptedException, AWTException,
	ClassNotFoundException, SQLException, FileNotFoundException, IOException {

		extent.startTest("Validating DB details");

		prop.load(new FileInputStream(utility.File_Prop));
		extent.log(LogStatus.INFO, "Trying to load config.property file");
		String Category_query = prop.getProperty("category_query");
		extent.log(LogStatus.INFO,"Passing query to fetch item master data from DB");

		driver.findElement(By.cssSelector("#search")).sendKeys(G_catName);
		extent.log(LogStatus.INFO, "search using item name");
		driver.findElement(By.cssSelector("#btnSubmit")).click();
		extent.log(LogStatus.INFO, "Click on search button");

		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By
				.cssSelector("a.edit")));
		driver.findElement(By.cssSelector("a.edit")).click();

		result = utility.mysqlconnection(Category_query, G_catCode);
		extent.log(LogStatus.INFO, "Reading data from DB using Result Set");

		String cat_Code = result.getString("code");		
		String cat_name = result.getString("Value");
		String cat_type = result.getString("Type");
		String cat_status = result.getString("Is_Active");
		
		if(cat_type.equalsIgnoreCase("ICAT")){			
			cat_type="Item";
		}else if(cat_type.equalsIgnoreCase("ACAT")){
			cat_type="Asset";
		}
		
		System.out.println(cat_Code + "  " + cat_name + "  " + cat_type + "  " + cat_status );
		extent.log(LogStatus.INFO, "Category master code: " + cat_Code
				+ "Category name:  " + cat_name + "Category Type  " + cat_type );

		extent.log(LogStatus.INFO, "Validating both inserted and DB data");
		if ((cat_Code.equalsIgnoreCase(G_catCode)
				&& cat_name.equalsIgnoreCase(G_catName)			
				&& cat_type.equalsIgnoreCase(G_Category_type)
				)) {

			System.out.println("data  Match");
			extent.log(LogStatus.INFO, "DATA MATCH");

		} else {
			System.out.println("data not Match");
			extent.log(LogStatus.INFO, "DATA NOT MATCH");
		}
		extent.log(LogStatus.INFO, "Driver Quit");
		driver.quit();

	}




}
