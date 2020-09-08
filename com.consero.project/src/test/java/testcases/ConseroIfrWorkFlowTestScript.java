package testcases;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import basetest.BaseTest;
import pageclass.ActivityDetailsPage;
import pageclass.ActivityListPage;
import pageclass.AddActivityPage;
import pageclass.BasePage;
import pageclass.DashboardPage;
import pageclass.HomePage;
import pageclass.LoginPage;
import pageclass.RecurranceManagementPage;
import pageclass.UploadActivityTemplatePage;
import pageclass.WorkloadPage;
import utility.DataReader;
import utility.ExtentTestManager;

public class ConseroIfrWorkFlowTestScript extends BaseTest {
	WebDriver driver;

	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	AddActivityPage addActivityPageObj = null;
	ActivityListPage activityListPageObj = null;
	DashboardPage dashboardPageObj = null;
	ActivityDetailsPage activityDetailsPageObj = null;
	UploadActivityTemplatePage activityTemplatePageObj = null;
	RecurranceManagementPage recurranceManagementPageObj = null;
	WorkloadPage workloadPageObj = null;

	String sheetName = "credentials";
	String managerUsername = "", managerPassword = "", managerName = "";
	String companyName = "Product Demo", nonCloseActivity ="test_nonclose_activity_ZGdUd", closeActivity= "test_close_activity_EgdYG", subActivity = "" ;
	
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
		loginPageObj = new LoginPage(driver);
		try {
			List<HashMap<String, String>> TCData = DataReader.getData(sheetName);
			for (int i = 0; i < TCData.size(); i++) {
				if(TCData.get(i).get("role").equals("ConseroManger")) {
					managerUsername = TCData.get(i).get("email");
					managerPassword = TCData.get(i).get("password");
					managerName =  TCData.get(i).get("username");
				}
			}
			loadConfig();
			test.log(LogStatus.INFO, "Loading config file");
			navigate(appUrl);
			test.log(LogStatus.INFO, "Navigated to url " + appUrl);
			
			loginPageObj.login(managerUsername, managerPassword);
			
			if(homePageObj.isLoggedInUsernameExist() && homePageObj.getLoggedInUsername().equals(managerName)) {
				test.log(LogStatus.PASS, "Logged in successfully!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful Login. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=1)
	public void homePageTC() {
		homePageObj = new HomePage(driver);
		dashboardPageObj = new DashboardPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		try {
			if(basePage.isElementPresent(homePageObj.companyLogo, 60)) {
				test.log(LogStatus.PASS, "Home page loaded successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to load home page!!");
			}
			sleep();
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to load home page after login. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=2)
	public void activityTC() {
		homePageObj = new HomePage(driver);
		activityListPageObj = new ActivityListPage(driver);
		String activity = "Generate Validate and Review Financials";
		try {
			homePageObj.clickOnControlDock();
			activityListPageObj.clickOnClientDropdown();
			activityListPageObj.setSearchClient(companyName);
			activityListPageObj.selectClient(companyName);
			activityListPageObj.setSearch(activity);
			activityListPageObj.clickOnActivityDetails();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void activityTC1() {
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void activityTC2() {
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
