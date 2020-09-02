package basetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.interactions.Actions;

public class BaseTest {
	public WebDriver driver=null;
	public FileInputStream fis=null;
	public Properties prop;
	public ExtentReports report=null;
	public ExtentTest test=null;
	public SoftAssert softAssert = new SoftAssert();	
	
	public static String companyName = "";
	
	public WebDriver getDriver() {
        return driver;
    }
	
	@BeforeClass(alwaysRun = true)
	public void initializeTestBaseSetup() {
		try {
			setDriver("chrome");
		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}

	public void loadConfig() {
		try {
			fis=new FileInputStream("config.properties");
			prop=new Properties();
			prop.load(fis);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDriver(String browserType) {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver();
			break;
		case "firefox":
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
		}
	}
	
	private static WebDriver initChromeDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	public void navigate(String appUrl) {
		driver.get(appUrl);
	}
	
	/*
	 * Web-Actions
	 */
	
	public void pressEnter() {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void switchToWindow() {
		String baseWindow=driver.getWindowHandle();
		Set<String> allWinIDs=driver.getWindowHandles();
		for(String w:allWinIDs) {
			if(!w.equals(baseWindow)) {
				driver.switchTo().window(w);
			}
		}		
	}
	
	public void swithToBaseWindow() {
		driver.switchTo().defaultContent();		
	}
	public String getTitle() {
		return driver.getTitle();
	}
	
	public void back() {
		driver.navigate().back();		
	}
	
	public void refresh() {
		driver.navigate().refresh();		
	}
	
	/*********************Screenshot**************************/
	public void takeScreenshot() {
		Date d=new Date();
		String TimeStamp=d.toString().replace(" ", "_").replace(":", "_");
		String ScreenshotPath=System.getProperty
				("user.dir")+"\\src\\test\\resources\\screenshots\\s_"+TimeStamp+".PNG";

		TakesScreenshot screenshot=(TakesScreenshot)driver;
		File f=screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(f, 
					new File(ScreenshotPath));

			test.log(LogStatus.INFO,test.addScreenCapture(ScreenshotPath));


		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
	
	/*********************VerifyFileDownload**************************/
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				flag = true;
		}
		return flag;
	}
	
	/*************generate random string, number and date*************/
	
	public String generateRandomString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public String generateRandomDouble() {
		double min = 1.0, max = 9.0;
		double number = (Math.random() * ((max - min) + 1)) + min;
		String formatted = String.format("%.3f", number);
		return formatted;
	}

	public String generateRandomNumber(int length) {
		return RandomStringUtils.randomNumeric(length);
	}
	
	public String generateNumberExceptZero(int num) {
		Random rand = new Random();
		int ran = rand.nextInt(num);
		if (ran==0){          
			  ran= ran+1;
			}
		return Integer.toString(ran);
	}
	
	public String generateRandomAlphaNumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}
	 
	public String generateRandomWeekDate(int day) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//		LocalDate localDate = LocalDate.now();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			cal.add(Calendar.DATE, day);
		} else {
			cal.add(Calendar.DATE, 2);
		}
		String newDate = dateFormat.format(cal.getTime());
		return newDate;
	}
	
	public String addDayToCurrentDate(int day) {
		DateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, day);
		String newDate = dateFormat.format(cal.getTime());
		return newDate;
	}
	
	public boolean isWeekEnd(LocalDate date) {
		  DayOfWeek dow = date.getDayOfWeek();
		  if(dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
			  return true;
		  }
		  return false;
		}
	
	public String generateCurrentDateInFormatM_DD_YYYY() {
		DateFormat dateFormat = new SimpleDateFormat("M-dd-yyyy");
		Date dateobj = new Date();
		return dateFormat.format(dateobj);
	}
	
	public String getCurrentDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dateobj = new Date();
		return df.format(dateobj);
	}
	
	public String getCurrentDateInFormat() {
		DateFormat df = new SimpleDateFormat("M/dd/yyyy");
		Date dateobj = new Date();
		return df.format(dateobj);
	}
	
	public String getdayFromDateFormat(String date) {
		String dateParts[] = date.split("/");
		return dateParts[1];
	}
	
	public boolean isPastDate(String date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date dateobj = new Date();
		String date1 = dateFormat.format(dateobj);;
		Date todayDate = dateFormat.parse(date1);
		Date selectedDate = dateFormat.parse(date);
		if(selectedDate.before(todayDate)) {
			return true;
		}
		return false;
	}
	
	public String getCurrentDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}
	
	public void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

