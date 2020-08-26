package testcases;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import basetest.BaseTest;
import pageclass.AddActivityPage;
import pageclass.BasePage;
import pageclass.ActivityListPage;
import pageclass.DashboardPage;
import pageclass.HomePage;
import pageclass.LoginPage;
import utility.DataReader;
import utility.ExtentTestManager;

public class ConseroManagerTestScript extends BaseTest {

	WebDriver driver;

	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	AddActivityPage addActivityPageObj = null;
	ActivityListPage activityListPageObj = null;
	DashboardPage dashboardPageObj = null;

	String sheetName = "credentials";
	String managerUsername = "", managerPassword = "", managerName = "";
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
			List<HashMap<String, String>> TCData = DataReader.getData(sheetName);
			for (int i = 0; i < TCData.size(); i++) {
				if(TCData.get(i).get("role").equals("manager")) {
					managerUsername = TCData.get(i).get("email");
					managerPassword = TCData.get(i).get("password");
					managerName =  TCData.get(i).get("username");
				}
			}
			loadConfig();
			test.log(LogStatus.INFO, "Loading config file");
			navigate(appUrl);
			test.log(LogStatus.INFO, "Navigated to url " + appUrl);
			loginPageObj = new LoginPage(driver);
			loginPageObj.login(managerUsername, managerPassword);
			
			test.log(LogStatus.PASS, "Logged in successfully!!");
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
		
		try {
			basePage.waitElementVisible(homePageObj.companyLogo, 60);
			if(basePage.isElementPresent(homePageObj.companyLogo, 60)) {
				test.log(LogStatus.PASS, "Home page loaded successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to load home page!!");
			}
			
			Assert.assertTrue(homePageObj.isDashboardSelected());
			test.log(LogStatus.INFO, "Dashboard tab is selected by default!!");
			dashboardPageObj.getPageTitle();
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to load home page after login. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=2)
	public void activityListTC() {
		try {
			
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify activity list. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=2)
	public void addCloseActivityTC() {
		homePageObj = new HomePage(driver);
		addActivityPageObj = new AddActivityPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		
		String submenu ="Add Activity",
			   description = generateRandomString(8),
			   day = generateNumberExceptZero(20),
			   checkList1 = generateRandomString(5),
			   checklist2 = generateRandomString(5),
			   duration = generateNumberExceptZero(10),
			   activityType = "CloseActivity";
			   
		try {
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			
			if(addActivityPageObj.isactivityModalExist()) {
				try {
					addActivityPageObj.selectCompany(companyName);
					addActivityPageObj.selectActivityType(activityType);
					addActivityPageObj.setActivityDetails(description, day, managerName );
					addActivityPageObj.clickOnChecklistTab();
					addActivityPageObj.setCheckListItemOne(checkList1);
					addActivityPageObj.clickOnChecklistLink();
					if(addActivityPageObj.isChecklistAdded()) {
						test.log(LogStatus.INFO, "checklist two added successfully!!");
						addActivityPageObj.setCheckListItemTwo(checklist2);
					}
					takeScreenshot();
					addActivityPageObj.clickOnReviwerTab();
					addActivityPageObj.setReviewerOneDetails(managerName, day, duration);
					addActivityPageObj.clickOnAddReviewerLink();
					if(addActivityPageObj.isReviewerTwoLabelExist()) {
						test.log(LogStatus.INFO, "reviewer two added successfully!!");
						addActivityPageObj.setReviewerTwoDetails(managerName, day, duration);
					}
					takeScreenshot();
					addActivityPageObj.clickOnSubmit();
				} catch(Exception e) {
					addActivityPageObj.clickOnCancelAddActivity();
				}
				
				if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
					test.log(LogStatus.PASS, "Redirected On Activity list Page successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to redirect On Activity list Page!!!!");
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add activity. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void addNoncloseActivityTC() {
		homePageObj = new HomePage(driver);
		addActivityPageObj = new AddActivityPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		
		String submenu ="Add Activity",
			   activityType = "NonCloseActivity";
		try {
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			
			if(addActivityPageObj.isactivityModalExist()) {
				try {
					addActivityPageObj.selectCompany(companyName);
					addActivityPageObj.selectActivityType(activityType);
					addActivityPageObj.clickOnSubmit();
				} catch(Exception e) {
					addActivityPageObj.clickOnCancelAddActivity();
				}
				
				if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
					test.log(LogStatus.PASS, "Redirected On Activity list Page successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to redirect On Activity list Page!!!!");
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add activity. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
}
