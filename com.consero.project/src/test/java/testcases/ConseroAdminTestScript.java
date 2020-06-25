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
import pageclass.BasePage;
import pageclass.CompanyListPage;
import pageclass.AddCompanyPage;
import pageclass.HomePage;
import pageclass.LoginPage;
import utility.DataReader;
import utility.ExtentTestManager;

public class ConseroAdminTestScript extends BaseTest {

	WebDriver driver;
	
	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	AddCompanyPage addCompanyPageObj = null;
	CompanyListPage companyListPageObj = null;
	
	String sheetName = "credentials";
	String adminUser = "", adminPassword = "";
	
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
	public void createCompanyTC() {
		addCompanyPageObj = new AddCompanyPage(driver);
		companyListPageObj = new CompanyListPage(driver);
		
		String companyName = "automation_test_company_" + generateRandomString(3);
		String address1 = generateRandomString(3);
		String city = generateRandomString(3);
		String state = generateRandomString(4);
		String zipcode = generateNumberExceptZero(6);
		try {
			addCompanyPageObj.setCompanyDetails(companyName, address1, city, state, zipcode);
			basePage.waitElementVisible(companyListPageObj.companyListTable, 60);
			companyListPageObj.setCompanySearch(companyName);
			if(companyListPageObj.isCompanyExist(companyName)) {
				test.log(LogStatus.PASS, "Company created successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to create company!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to createCompany. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
}
