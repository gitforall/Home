
package com.qa.utility;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

public class utility {
	Workbook workbook;
	Sheet sheet;

	public String Excel_path = (System.getProperty("user.dir") + "\\src\\TestData\\Testdata.xls");
	public static String File_Prop = (System.getProperty("user.dir") + "\\src\\Config_Files\\pathFile.properties");
	public static String html_report_file = (System.getProperty("user.dir") + "\\src\\Execution_Reports\\HtmlReports\\");
	public static String imageLocation = (System.getProperty("user.dir") + "\\src\\Execution_Reports\\Screenshots\\");
	public static Actions builder;
	public static String locators_prop_file = (System.getProperty("user.dir") + "\\src\\UI_Maps\\UImapping.properties");
	// private String iedriver_path = "D:\\Hari\\IEdriver\\IEDriverServer.exe";

	static Properties prop = new Properties();

	String url = prop.getProperty("url");

	/**************************************/
	/* loginuser */
	/**************************************/

	public static void loginuser(WebDriver driver, String username,
			String password, String username_locator, String password_locator)
			throws InterruptedException, AWTException {
		try {
			driver.findElement(By.id(username_locator)).sendKeys(username);
			driver.findElement(By.id(password_locator)).sendKeys(password);
			driver.findElement(By.cssSelector("#Login")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			// Robot R = new Robot();
			// R.keyPress(KeyEvent.VK_ENTER);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/******************************/
	/* User createScreenshot */
	/******************************/
	public static String createScreenshot(WebDriver driver) {

		UUID uuid = UUID.randomUUID();

		// generate screenshot as a file object
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			// copy file object to designated location
			FileUtils
					.copyFile(scrFile, new File(imageLocation + uuid + ".png"));
			System.out.println(imageLocation + uuid + ".png" + "created");
		} catch (IOException e) {
			System.out.println("Error while generating screenshot:\n"
					+ e.toString());
		}
		return imageLocation + uuid + ".png";

	}

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		}// try
		catch (Exception e) {
			return false;
		}// catch
	}

	/*
	 * takeScreenShotOnFailure
	 */

	public void takeScreenShotOnFailure(ITestResult testResult, WebDriver driver)
			throws IOException {

		if (testResult.getStatus() == ITestResult.FAILURE) {

			System.out.println(testResult.getStatus());

			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("D:\\testScreenShot.jpg"));

			// createScreenshot(driver);

		}

	}

	/**********************************/
	/* User Data provider Get data from XLS */
	/**********************************/
	public String[][] getTableArray(String xlFilePath, String sheetName,
			String tableStartName, String tableEndName) throws Exception {
		String[][] tabArray = null;

		workbook = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol, ci, cj;
		Cell tableStart = sheet.findCell(tableStartName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();

		Cell tableEnd = sheet.findCell(tableEndName, startCol + 1,
				startRow + 1, 100, 100, false);

		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();
		tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;

		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tabArray[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}

		return (tabArray);
	}

	/**********************************/
	/* set Value Into XLS */
	/**********************************/
	public void setValueIntoCell(String xlspath, String SheetName,
			int iColumnNumber, int iRowNumber, String strData)
			throws WriteException {
		try {

			Workbook workbook1 = Workbook.getWorkbook(new File(xlspath));
			WritableWorkbook copy = Workbook.createWorkbook(new File(xlspath),
					workbook1);
			WritableSheet sheet2 = copy.getSheet(SheetName);

			Label label = new Label(iColumnNumber, iRowNumber, strData);
			sheet2.addCell(label);

			copy.write();
			copy.close();
		} catch (Exception E) {
			System.out.println(E);
		}

	}

	/********************************/
	/* Chrome driver properties */
	/*******************************/
	public static WebDriver chrome_driver(WebDriver driver, Properties prop) {

		System.setProperty("webdriver.chrome.driver",
				prop.getProperty("chrome_driver"));
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		capabilities.setCapability("chrome.binary",
				prop.getProperty("chrome_driver"));
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		return driver;
	}

	public static boolean isElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
	}

	/***********************************************************************************************************/
	/* Mouse Hover */
	/*******************************/

	public static WebDriver Mousehover(WebDriver driver, WebElement element) {

		builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 ***********************************************************************************************************/

	public WebDriver setDriver(WebDriver driver, String browserType,
			String appURL) throws FileNotFoundException, IOException {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(driver, appURL);
			break;
		case "IEdriver":
			driver = IEdriver(driver, appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
		return driver;
	}

	private WebDriver IEdriver(WebDriver driver, String appURL)
			throws FileNotFoundException, IOException {
		prop.load(new FileInputStream(File_Prop));
		System.out.println("Launching IEdriver..");
		System.setProperty("webdriver.ie.driver",
				prop.getProperty("IEdriver_path"));
		// File file = new File(iedriver_path);
		// System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private WebDriver initChromeDriver(WebDriver driver, String appURL)
			throws FileNotFoundException, IOException {
		prop.load(new FileInputStream(File_Prop));
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver",
				prop.getProperty("chrome_driver"));
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		capabilities.setCapability("chrome.binary",
				prop.getProperty("chrome_driver"));
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);
		driver.navigate().to(appURL);
		return driver;
	}

	/**
	 * 
	 * SpecialCharactervalidation
	 * 
	 ***********************************************************************************************************/
	public boolean SpecialCharactervalidation(String s) {
		boolean return_string;

		if (s == null || s.trim().isEmpty()) {
			System.out
					.println("Incorrect format of string : Either Null or Empty");
			return false;
		}

		// Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Pattern p = Pattern.compile("[^A-Za-z0-9 \\s]");
		Matcher m = p.matcher(s);

		// boolean b = m.matches();
		boolean b = m.find();
		if (b == true) {
			System.out.println("There is a special character in my string ");

			return_string = false;
		} else {
			return_string = true;

			System.out.println("There is no special char.");
		}
		return return_string;
	}

	/* numericvalidation */
	public boolean numericvalidation(String s) {
		boolean return_string;

		if (s == null || s.trim().isEmpty()) {
			System.out
					.println("Incorrect format of string : Either Null or Empty");
			return false;
		}

		// Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(s);

		// boolean b = m.matches();
		boolean b = m.find();
		if (b == true) {
			System.out.println("There are only nuemerics. ");

			return_string = true;
		} else {
			return_string = false;

			System.out.println("There are some special chars.");
		}
		return return_string;
	}

	/**
	 * 
	 * mysql connection
	 * 
	 ***********************************************************************************************************/

	public static ResultSet mysqlconnection(String Query, String value) {
		ResultSet result = null;
		Connection conn = null;

		String Url = "jdbc:mysql://192.168.1.190:3306/";
		String databasename = "ams_db";
		String username = "root";
		String password = "p@ssw0rd";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Url + databasename, username,
					password);
			Statement stm = conn.createStatement();
			result = stm.executeQuery(Query + value + "';");
			result.next();

		} catch (Exception e) {
			System.out.println(e);
		}

		finally {
			if (conn != null) {
				conn = null;
			}
		}
		return result;

	}

	/**
	 * 
	 * sql connection
	 * 
	 ***********************************************************************************************************/

	public static ResultSet sqlconnection(String Query, String value)
			throws ClassNotFoundException, SQLException {

		// new SQLserverConnection();
		ResultSet result = null;
		Connection conn = null;

		try {
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(driver).newInstance();
			String dbURL = "jdbc:sqlserver://DBSRV; DatabaseName=MANNDESHI_DB";
			String user = "dev";
			String pass = "p@ssw0rd1";

			conn = DriverManager.getConnection(dbURL, user, pass);
			Statement stmt = conn.createStatement();
			// String sqlquery = "Select * from " + tablename +" WHERE " + field
			// + "='" + value+"'" ;

			/*
			 * String sqlquery =
			 * "SELECT [d].[CensusCode],[d].[CbsDistrictCode],d.[DistrictName], [s].[StateName] "
			 * +
			 * "FROM [dbo].[StateMaster] s INNER JOIN [dbo].[DistrictMaster] d ON [d].[StateId] = [s].[StateId]  "
			 * + "WHERE [d].[CensusCode]='"+ value+"'";
			 */

			System.out.println(Query + value + "';");

			result = stmt.executeQuery(Query + value + "';");
			result.next();

			/* String query = "SELECT * FROM StateMaster"; 9-7-2015 */
			// Print the all data to the console
			// while (reset.next()) {
			// data.add(result.getString("StateName"));

			// String EmpNo = reset.getString("StateName");
			// System.out.println(EmpNo);
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				conn = null;
				// conn.close();

			}
		}
		return result;
	}

	public static String status(String Status) {

		if (Status.equalsIgnoreCase("0"))
			Status = "true";
		else
			Status = "false";

		return Status;
	}

	public static WebDriver waitTillElementFound(WebDriver driver,
			WebElement ElementTobeFound, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(ElementTobeFound));
		return driver;
	}

	public static WebDriver setWindowSize(WebDriver driver, int Dimension1,
			int dimension2) {
		driver.manage().window().setSize(new Dimension(Dimension1, dimension2));
		return driver;
	}

	public static void pressKeyDown(WebElement element) {
		element.sendKeys(Keys.DOWN);
	}

	public void pressKeyEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}

	public static void pressKeyUp(WebElement element) {
		element.sendKeys(Keys.UP);
	}

	public static void moveToTab(WebElement element) {
		element.sendKeys(Keys.chord(Keys.ALT, Keys.TAB));
	}

	public static WebDriver handleHTTPS_IEbrowser(WebDriver driver) {
		driver.navigate().to(
				"javascript:document.getElementById(‘overridelink’).click()");
		return driver;
	}

	public static WebDriver handleHTTPS_Firefox(WebDriver driver) {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(false);
		driver = new FirefoxDriver(profile);
		return driver;
	}

	public static void clickAllLinksInPage(WebDriver driver,
			String destinationOfScreenshot) throws Exception {

		List<WebElement> Links = driver.findElements(By.tagName("a"));
		System.out.println("Total number of links :" + Links.size());

		for (int p = 0; p < Links.size(); p++) {
			System.out.println("Elements present the body :"
					+ Links.get(p).getText());
			Links.get(p).click();
			Thread.sleep(3000);
			System.out.println("Url of the page " + p + ")"
					+ driver.getCurrentUrl());
			File f = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File(destinationOfScreenshot + p));
			driver.navigate().back();
			Thread.sleep(2000);
		}

	}

	public static WebDriver clickMultipleElements(WebDriver driver,
			WebElement someElement, WebElement someOtherElement) {
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.CONTROL).click(someElement)
				.click(someOtherElement).keyUp(Keys.CONTROL).build().perform();
		return driver;
	}
	
	
	public static void checkbox_Checking(WebElement checkbox) {
		boolean checkstatus;
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
		System.out.println("Checkbox is already checked");
		} else {
		checkbox.click();
		System.out.println("Checked the checkbox");
		}
		}
	
	public static void radiobutton_Select(WebElement Radio) {
		boolean checkstatus;
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
		System.out.println("RadioButton is already checked");
		} else {
		Radio.click();
		System.out.println("Selected the Radiobutton");
		}
		}
	
	public static WebDriver dragAndDrop(WebDriver driver ,WebElement fromWebElement,
			WebElement toWebElement) {
			Actions builder = new Actions(driver);
			builder.dragAndDrop(fromWebElement, toWebElement);
			return driver;
			}
	
	public static WebDriver doubleClickWebelement(WebDriver driver, WebElement doubleclickonWebElement)
			throws InterruptedException {
			Actions builder = new Actions(driver);
			builder.doubleClick(doubleclickonWebElement).perform();
			Thread.sleep(2000);
			return driver;

			}	
	
	public static WebDriver navigateToEveryLinkInPage(WebDriver driver) throws InterruptedException {

		List<WebElement> linksize = driver.findElements(By.tagName("a"));
		int linksCount = linksize.size();
		System.out.println("Total no of links Available: " + linksCount);
		String[] links = new String[linksCount];
		System.out.println("List of links Available: ");
		// print all the links from webpage
		for (int i = 0; i < linksCount; i++) {
		links[i] = linksize.get(i).getAttribute("href");
		System.out.println(linksize.get(i).getAttribute("href"));
		}
		// navigate to each Link on the webpage
		for (int i = 0; i < linksCount; i++) {
		driver.navigate().to(links[i]);
		Thread.sleep(3000);
		System.out.println(driver.getTitle());
		}
		return driver;
		}	
	

}
