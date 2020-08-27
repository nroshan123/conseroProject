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
		homePageObj = new HomePage(driver);
		activityListPageObj = new ActivityListPage(driver);
		try {
			homePageObj.clickOnControlDock();
			Assert.assertTrue(basePage.isElementPresent(activityListPageObj.pageTitle, 60));
			test.log(LogStatus.INFO, "Activity list page opened sucessfully!!");
			sleep();
			if(activityListPageObj.isActivitiesHeadertableVisible()) {
				test.log(LogStatus.INFO, "All Filters Are visible in Activity list page!!");
			}
			activityListPageObj.clickOnBtnActivityheader();
			if(!activityListPageObj.isActivitiesHeadertableVisible()) {
				test.log(LogStatus.PASS, "All Filters Are hidden after click on 'Collapse' button!!");
			} 	
			
		    if(activityListPageObj.checkDownloadFileButton()) {
		    	test.log(LogStatus.INFO, "'CSV', 'EXCEL' and 'PDF' button is present in activity list page!!");
		    }
		    
		    if(activityListPageObj.isclearFilterExist()) {
		    	test.log(LogStatus.INFO, "'Clear Filter' button is present in activity list page!!");
		    }
			
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify activity list. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void downloadActivityListTC() {
		activityListPageObj = new ActivityListPage(driver);
		String date = generateCurrentDateInFormatM_DD_YYYY(),
		       csvFilename = "Activities-" + date + ".csv",
			   excelFilename = "Activities-" + date + ".xlsx",
			   pdfFileName = "Activities-" + date + ".pdf",
			   filePath = "C:\\Users\\thinkBridge\\Downloads";
		try {
			activityListPageObj.clickOnCsv();
			sleep();
			if(isFileDownloaded(filePath, csvFilename)) {
				test.log(LogStatus.PASS, "CSV File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download CSV File!!");
			}
			activityListPageObj.clickOnExcel();
			sleep();
			if(isFileDownloaded(filePath, excelFilename)) {
				test.log(LogStatus.PASS, "Excel File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download Excel File!!");
			}
			activityListPageObj.clickOnPdf();
			sleep();
			if(isFileDownloaded(filePath, pdfFileName)) {
				test.log(LogStatus.PASS, "PDF File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download PDF File!!");
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify download file. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void verfiyActivityPaginationTC() {
		activityListPageObj = new ActivityListPage(driver);
		try {
			if(basePage.isElementPresent(activityListPageObj.activityTable, 30) && activityListPageObj.getActivityTable().equals("No records to display")) {
				test.log(LogStatus.INFO, "No Activity list found.");
			}
			
			if(basePage.pagination.size()>0) {
				test.log(LogStatus.INFO, "Pagination Found.");
				basePage.clickOnPagination();
			} else {
				test.log(LogStatus.INFO, "Pagination Not Found.");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify Activity pagination. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=5)
	public void verifyActivityPreviousAndNextPageTC() {
		try {
			this.prevoiusAndNextPagination();
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify previous and next pagination in Activity List. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=6)
	public void verifyActivityFirstAndLastPageTC() {
		try {
			this.firstAndLastPagination();
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify first and last pagination in Activity List. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=7)
	public void showActivityEntriesTC() {
		activityListPageObj = new ActivityListPage(driver);
		int entries = 0;
		String selectedEntries = "";
		try {
			Thread.sleep(5000);
			selectedEntries = activityListPageObj.getSelectedEntries();
			String pageCount = activityListPageObj.getLastPageCount();
			test.log(LogStatus.INFO, "selected entry is : " + selectedEntries + "and page count displayed in pagination is : " + pageCount);
			activityListPageObj.selctShowEntries();
			sleep();
			selectedEntries = activityListPageObj.getSelectedEntries();
			try {
				entries = Integer.valueOf(selectedEntries);
			} catch(NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			int count = activityListPageObj.getActivityCount();
			if(entries == count) {
				test.log(LogStatus.PASS, "employees count is equal to selected entries.");
			} else {
				test.log(LogStatus.FAIL, "employees count is not equal to selected entries.");
			}
			takeScreenshot();
			test.log(LogStatus.INFO, "selected entry is : " + selectedEntries + "and page count displayed in pagination is : " + activityListPageObj.getLastPageCount());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=8)
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
					sleep();
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
	
	@Test(priority=9)
	public void addNoncloseActivityTC() {
		homePageObj = new HomePage(driver);
		addActivityPageObj = new AddActivityPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		
		String submenu ="Add Activity",
			   activityType = "NonCloseActivity",
			   description = generateRandomString(8),
			   date = addDayToCurrentDate(1),
		       day = getdayFromDateFormat(date);
		try {
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			
			if(addActivityPageObj.isactivityModalExist()) {
				try {
					sleep();
					addActivityPageObj.selectCompany(companyName);
					addActivityPageObj.selectActivityType(activityType);
					Assert.assertTrue(addActivityPageObj.isRecurrenceTabSelected());
					test.log(LogStatus.INFO, "Recurrence tab is selcted by default!!!!");
					Assert.assertTrue(addActivityPageObj.isSubActivityField());
					test.log(LogStatus.INFO, "subactivity field is exist!!!!");
					addActivityPageObj.clickOnValidateDueDate();
					if(addActivityPageObj.isRelaventChildActivities()) {
						test.log(LogStatus.INFO, "sub activities field is exist!!!!");
					}
					addActivityPageObj.clickOnIsSubActivity();
					addActivityPageObj.clickOnValidateDueDate();
					if(addActivityPageObj.isRelaventParentActivity()) {
						test.log(LogStatus.INFO, "parent activities field is exist!!!!");
					}
					addActivityPageObj.clickOnActivityTab();
					addActivityPageObj.setNonCloseActivityDetails(description, day, managerName);
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
	
	public void prevoiusAndNextPagination() {
		if(basePage.pagination.size()>0) {
			test.log(LogStatus.INFO, "Pagination Found.");
			if(basePage.previousButton.isEnabled()) {
				basePage.clickOnPreviousPagination();
				test.log(LogStatus.INFO, "clicked on previous button successfully.");
			} else {
				basePage.clickOnNextPagination();
			}
			takeScreenshot();
			if(basePage.nextButton.isEnabled()) {
				basePage.clickOnNextPagination();
				test.log(LogStatus.INFO, "clicked on Next button successfully.");
			} else {
				basePage.clickOnPreviousPagination();
			}
		} else {
			test.log(LogStatus.INFO, "Pagination Not Found.");
		}
	}
	
	public void firstAndLastPagination() {
		if(basePage.pagination.size()>0) {
			test.log(LogStatus.INFO, "Pagination Found.");
			if(basePage.firstButton.isEnabled()) {
				basePage.clickOnFirstPagination();
				test.log(LogStatus.INFO, "clicked on first pagination link successfully.");
			} else {
				basePage.clickOnLastPagination();
				test.log(LogStatus.INFO, "First page is already selected so, clicked on last pagination link successfully.");
			}
			sleep();
			takeScreenshot();
			
			if(basePage.lastButton.isEnabled()) {
				basePage.clickOnLastPagination();
				test.log(LogStatus.INFO, "clicked on last button successfully.");
			} else {
				basePage.clickOnFirstPagination();
				test.log(LogStatus.INFO, "Last page is already selected so, clicked on first pagination link successfully.");
			}
			sleep();
		} else {
			test.log(LogStatus.INFO, "Pagination Not Found.");
		}
	}
}
