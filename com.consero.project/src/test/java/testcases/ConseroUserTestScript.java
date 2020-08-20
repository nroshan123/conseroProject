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
import pageclass.AddUserPage;
import pageclass.BasePage;
import pageclass.HomePage;
import pageclass.LoginPage;
import pageclass.UsersListPage;
import utility.DataReader;
import utility.ExtentTestManager;

public class ConseroUserTestScript extends BaseTest {

    WebDriver driver;
	
	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	UsersListPage userListPageObj = null;
	AddUserPage addUserPageObj = null;
	
	String sheetName = "credentials";
	String adminUser = "", adminPassword = "";
	String companyName= "automation_test_company_DAn", username ="";
	
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
				if(TCData.get(i).get("role").equals("admin")) {
					adminUser = TCData.get(i).get("email");
					adminPassword = TCData.get(i).get("password");
				}
			}
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
	
	@Test(priority=1)
	public void homePageTC() {
		homePageObj = new HomePage(driver);
		try {
			basePage.waitElementVisible(homePageObj.companyLogo, 60);
			if(basePage.isElementPresent(homePageObj.companyLogo, 60)) {
				test.log(LogStatus.PASS, "Home page loaded successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to load home page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to load home page after login. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	
	@Test(priority=2)
	public void verifyUserListTC() {
		homePageObj = new HomePage(driver);
		userListPageObj = new UsersListPage(driver);
		try {
			homePageObj.clickOnUsers();
			if(basePage.isElementPresent(userListPageObj.pageTitle, 60)) {
				test.log(LogStatus.INFO, "user page loaded successfully!!");
				if(userListPageObj.isActiveUserSelected()) {
					test.log(LogStatus.PASS, "active user is selected by default!!!");
				}
				
				if(userListPageObj.isUserTableExist()) {
					test.log(LogStatus.INFO, "user table displayed successfully!!");
				}
				
				takeScreenshot();
				userListPageObj.clickOnInactiveUser();
				takeScreenshot();
				userListPageObj.clickOnBothUsers();
				takeScreenshot();
				
			} else {
				test.log(LogStatus.FAIL, "Failed to load Users page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify user page after login. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void addUserTC() {
		addUserPageObj = new AddUserPage(driver);
		userListPageObj = new UsersListPage(driver);
		username = generateRandomString(5) + ".gmail.com";
		String pwd = "Test123$",
			   confirmpwd = "Test123$",
			   firstname = generateRandomString(5),
			   lastname = generateRandomString(5),
			   address = generateRandomString(5);
		String msg = "This feature is available only for Consero Global, BTQ and Propeller Industries domain users";
			   
		try {
			userListPageObj.clickOnCreateNewUser();
			if(basePage.isElementPresent(userListPageObj.pageTitle, 60)) {
				test.log(LogStatus.INFO, "'create new user' page loaded successfully!!");
				addUserPageObj.setUserName(username);
				addUserPageObj.setPassword(pwd);
				addUserPageObj.setConfirmPassword(confirmpwd);
				addUserPageObj.setFirstName(firstname);
				addUserPageObj.setLastName(lastname);
				addUserPageObj.setAddressLineOne(address);
				addUserPageObj.clickOnIsSuperUser();
				if(addUserPageObj.isSuperUserMessageExist(msg)) {
					test.log(LogStatus.INFO, msg);
				} 
				addUserPageObj.clickOnCreate();
				sleep();
				userListPageObj.setUserSearch(username);
				if(userListPageObj.isUsereExist(username)) {
					test.log(LogStatus.PASS, "user created successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to create user!!");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to load 'create new user' page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to create user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void verifyWelcomeEmailTC() {
		userListPageObj = new UsersListPage(driver);
		String welcomeMessage = "Welcome Mail was send successfully to " + username,
			   tokenMessage = "Reset Password Mail was send successfully to " + username;
		try {
			userListPageObj.clickOnWelcomeUser();
			if(basePage.checkSuccessMessage(welcomeMessage)) {
				test.log(LogStatus.PASS, "welcome email sent successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to sent welcome email!!");
			}
			takeScreenshot();
			
			if(userListPageObj.getWelcomeUserText().equals("Send token")) {
				test.log(LogStatus.INFO, "'sent token' text displayed successfully after welcome email!!");
				userListPageObj.clickOnSendToken();
				if(basePage.checkSuccessMessage(tokenMessage)) {
					test.log(LogStatus.PASS, "resend email sent successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to sent resend email!!");
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to activate/deactivate user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void activateDeactivateUserTC() {
		userListPageObj = new UsersListPage(driver);
		String deactiveContent = "Are you sure to deactivate",
			   activeContent = "Are you sure to activate";
		try {
			userListPageObj.clickOnDeactivate();
			if(userListPageObj.isModalExist(deactiveContent)) {
				userListPageObj.clickOnConfirm();
				takeScreenshot();
			}
			sleep();
			userListPageObj.clickOnInactiveUser();
			userListPageObj.setUserSearch(username);
			if(userListPageObj.isUsereExist(username)) {
				test.log(LogStatus.PASS, "user deactivated successfully!!");
				takeScreenshot();
				userListPageObj.clickOnActivate();
				if(userListPageObj.isModalExist(activeContent)) {
					userListPageObj.clickOnConfirm();
					takeScreenshot();
				}
				userListPageObj.clickOnActiveUsers();
				userListPageObj.setUserSearch(username);
				if(userListPageObj.isUsereExist(username)) {
					test.log(LogStatus.PASS, "user activated successfully!!");
				}else {
					test.log(LogStatus.FAIL, "Failed to activate user!!");
				}
				takeScreenshot();
			} else {
				test.log(LogStatus.FAIL, "Failed to deactivate user!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to activate/deactivate user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
}
