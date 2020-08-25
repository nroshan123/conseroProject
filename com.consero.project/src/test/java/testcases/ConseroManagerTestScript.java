package testcases;

import java.lang.reflect.Method;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import basetest.BaseTest;
import pageclass.AddActivityPage;
import pageclass.BasePage;
import pageclass.ControlDockPage;
import pageclass.HomePage;
import pageclass.LoginPage;
import utility.ExtentTestManager;

public class ConseroManagerTestScript extends BaseTest {

	WebDriver driver;

	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	AddActivityPage addActivityPageObj = null;
	ControlDockPage controlDockPageObj = null;

	String sheetName = "credentials";
	String adminUser = "prasanna@thinkbridge.in", adminPassword = "Consero123$";
	String companyName = "automation_test_company_DAn", username = "";
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {
		driver = getDriver();
	}
	
	@BeforeMethod( alwaysRun = true)
    public void methodLevelSetup(Method method) {
		basePage = new BasePage(driver);
		test = ExtentTestManager.startTest(method.getName(), method.getName() + " DESCRIPTION.");
    }
	
	@Parameters({"appurl" })
	@Test
	public void loginTC(String appUrl) {
		try {
			System.out.println(appUrl);
			/*List<HashMap<String, String>> TCData = DataReader.getData(sheetName);
			for (int i = 0; i < TCData.size(); i++) {
				if(TCData.get(i).get("role").equals("admin")) {
					adminUser = TCData.get(i).get("email");
					adminPassword = TCData.get(i).get("password");
				}
			}*/
			loadConfig();
			test.log(LogStatus.INFO, "Loading config file");
			navigate(appUrl);
			test.log(LogStatus.INFO, "Navigated to url " + appUrl);
			loginPageObj = new LoginPage(driver);
			loginPageObj.login(adminUser, adminPassword);
			
			test.log(LogStatus.PASS, "Logged in successfully!!");
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful Login. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
}
