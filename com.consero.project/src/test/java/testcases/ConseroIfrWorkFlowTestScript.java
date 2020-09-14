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
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		
		String activity = "Generate Validate and Review Financials",
			   status = "Assigned";
		try {
			homePageObj.clickOnControlDock();
			activityListPageObj.searchAndSelectClient(companyName);
			Assert.assertTrue(activityListPageObj.isClientSelected(companyName));
			test.log(LogStatus.INFO, companyName + " Client Selected!!");
			sleep();
			activityListPageObj.setSearch(activity);
			if(activityListPageObj.clickOnAssignedActivity(status)) {
				test.log(LogStatus.INFO,  " Assigned Activity is present in activity list!!");
			} else {
				activityListPageObj.clickOnActivityDetails();
			}
			
			if(activityDetailsPageObj.getAssignToLevel().equals(managerName)) {
				test.log(LogStatus.INFO,  " Activity is Assigned to logged in user!!");
				if(activityDetailsPageObj.isGenerateFinancialsButtonExist()) {
					test.log(LogStatus.PASS,  " 'Generate Financial' button is available for Owner!");
				} else {
					test.log(LogStatus.FAIL,  " 'Generate Financial' button is not available for Owner!");
				}
			} else {
				test.log(LogStatus.INFO,  " Activity is  not Assigned to logged in user!!");
				if(!activityDetailsPageObj.isGenerateFinancialsButtonExist()) {
					test.log(LogStatus.PASS,  " 'Generate Financial' button is not Available as activity is not assigned to ownwer!");
				} else {
					test.log(LogStatus.FAIL,  " 'Generate Financial' button is available as activity is not assigned to ownwer!");
				}
				activityDetailsPageObj.clickOnAssignToLevel();
				activityDetailsPageObj.selectAssignee(managerName);
				activityDetailsPageObj.clickOnSaveAssignedTo();
				String assignee = activityDetailsPageObj.getAssignToLevel();
				if (assignee.equals(managerName)) {
					test.log(LogStatus.PASS, "assigned Activity to logged in user successfully!!!!");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 3)
	public void generateActivityTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		String content = "Standard Reports Generation is In Progress";
		try {
			if (activityDetailsPageObj.getStatus().equals("Assigned")) {
				if(activityDetailsPageObj.isGenerateFinancialsButtonExist()) {
					test.log(LogStatus.INFO,  " 'Generate Financial' button is available!!");
					activityDetailsPageObj.clickOnGenerateFinancials();
					if(activityDetailsPageObj.isFinancialReportStatusModalExist(content)) {
						test.log(LogStatus.INFO,  "Financial Report Status Modal opened suceessfully!!");
						activityDetailsPageObj.clickOnCloseFinancialReportStatusModal();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void viewActivityTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		try {
			activityDetailsPageObj.clickOnViewFinancials();
			Assert.assertTrue(activityDetailsPageObj.isViewFinancialPageTitleExist());
			test.log(LogStatus.INFO,  "'view Financial' page opened sucessfully!!");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
