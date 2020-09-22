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
import pageclass.ViewFinancialPage;
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
	ViewFinancialPage viewFinancialPageObj = null;

	String sheetName = "credentials";
	String managerUsername = "", managerPassword = "", managerName = "";
	String companyName = "ALPFA, Inc.", nonCloseActivity ="test_nonclose_activity_ZGdUd", closeActivity= "test_close_activity_EgdYG", subActivity = "" ;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {
		driver = getDriver();
	}
	
	@BeforeMethod( alwaysRun = true)
    public void methodLevelSetup(Method method) {
		basePage = new BasePage(driver);
		test = ExtentTestManager.startTest(method.getName(), method.getName() + " DESCRIPTION.");
    }
	
	@Parameters({ "appurl" })
	@Test
	public void loginTC(String appUrl) {
		loginPageObj = new LoginPage(driver);
		try {
			List<HashMap<String, String>> TCData = DataReader.getData(sheetName);
			for (int i = 0; i < TCData.size(); i++) {
				if (TCData.get(i).get("role").equals("ConseroManger")) {
					managerUsername = TCData.get(i).get("email");
					managerPassword = TCData.get(i).get("password");
					managerName = TCData.get(i).get("username");
				}
			}
			loadConfig();
			test.log(LogStatus.INFO, "Loading config file");
			navigate(appUrl);
			test.log(LogStatus.INFO, "Navigated to url " + appUrl);

			loginPageObj.login(managerUsername, managerPassword);
			test.log(LogStatus.PASS, "Logged in successfully!!");
			takeScreenshot();
		} catch (Exception e) {
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
	
/*	@Test(priority=2)
	public void generateAndReviewFinancialActivityTC() {
		homePageObj = new HomePage(driver);
		activityListPageObj = new ActivityListPage(driver);
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		
		String activity = "Generate Validate and Review Financials",
			   status = "Assigned";
		try {
			homePageObj.clickOnControlDock();
			Thread.sleep(10000);
			activityListPageObj.searchAndSelectClient(companyName);
			activityListPageObj.clickOnFilterClientActivities();
			Thread.sleep(5000);
			Assert.assertTrue(activityListPageObj.isClientSelected(companyName));
			test.log(LogStatus.INFO, companyName + " Client Selected!!");
			activityListPageObj.setSearch(activity);
			Assert.assertFalse(activityListPageObj.isActivityTableExist());
			if(activityListPageObj.clickOnActivity(status)) {
				test.log(LogStatus.INFO,  " Assigned Activity is present in activity list!!");
			} else {
				test.log(LogStatus.INFO,  " No Assigned Activity is present in activity list!!");
				activityListPageObj.clickOnActivityDetails();
			}
			sleep();
			Assert.assertTrue(activityDetailsPageObj.getStatus().equals("Assigned"));
			if(activityDetailsPageObj.getAssignToLevel().equals(managerName)) {
				test.log(LogStatus.INFO,  " Activity is Assigned to logged in user!!");
				if(activityDetailsPageObj.isGenerateFinancialsButtonExist()) {
					test.log(LogStatus.PASS,  " 'Generate Financial' button is available for Owner!");
				} else {
					test.log(LogStatus.FAIL,  " 'Generate Financial' button is not available for Owner!");
				}
				takeScreenshot();
			} else {
				test.log(LogStatus.INFO,  " Activity is  not Assigned to logged in user!!");
				if(!activityDetailsPageObj.isGenerateFinancialsButtonExist()) {
					test.log(LogStatus.PASS,  " 'Generate Financial' button is not Available as activity is not assigned to ownwer!");
				} else {
					test.log(LogStatus.FAIL,  " 'Generate Financial' button is available as activity is not assigned to ownwer!");
				}
				this.assignActivityToSameUser();
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 3)
	public void generateFinancialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		String content = "Standard Reports Generation is In Progress";
		try {
			if (activityDetailsPageObj.getStatus().equals("Assigned")) {
				test.log(LogStatus.INFO,  " staus is 'Assigned'!!");
				if(activityDetailsPageObj.isGenerateFinancialsButtonExist()) {
					test.log(LogStatus.INFO,  " 'Generate Financial' button is available!!");
					activityDetailsPageObj.clickOnGenerateFinancials();
					if(activityDetailsPageObj.isFinancialReportStatusModalExist(content)) {
						test.log(LogStatus.INFO,  "Financial Report Status Modal opened suceessfully!!");
						activityDetailsPageObj.clickOnCloseFinancialReportStatusModal();
					}
				}
			} else {
				test.log(LogStatus.INFO,  " staus is  not 'Assigned'!!");
				activityDetailsPageObj.clickOnBackToActivities();
				if(activityListPageObj.isActivityTableExist()) {
					test.log(LogStatus.INFO,  "Activity list is Empty!!");
				} else {
					test.log(LogStatus.INFO,  "Activity list loaded successfully!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void viewFinancialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String status = "In Progress",
			   intacctErrorCount = "",
			   reportErrorCount = "",
			   totalErrorCount = "";
		try {
			sleep();
			if(activityListPageObj.clickOnActivity(status)) {
				test.log(LogStatus.INFO, status+ " Activity is present in activity list!!");
				activityDetailsPageObj.clickOnViewFinancials();
				Assert.assertTrue(viewFinancialPageObj.isViewFinancialPageTitleExist());
				test.log(LogStatus.INFO,  "'view Financial' page opened sucessfully!!");
				if(viewFinancialPageObj.isValidationSummaryTabSelected()) {
					test.log(LogStatus.PASS,  "'validation summary' tab is selected by default!!");
				} else {
					test.log(LogStatus.FAIL,  "'validation summary' tab is not selected by default!!");
				}
				
				if(viewFinancialPageObj.isVerifyPathForGDriveExist()) {
					viewFinancialPageObj.clickOnVerifyPathForGDrive();
					if(viewFinancialPageObj.isGDrivePathErrorExist()) {
						test.log(LogStatus.FAIL,  "Getting error in GDrive path - " + viewFinancialPageObj.getGDrivePathError());
						viewFinancialPageObj.clickOnAcceptGdriveModal();
					}
				}
				takeScreenshot();
				if(viewFinancialPageObj.isValidationButtonExist()) {
					test.log(LogStatus.INFO,  "'Intacct validation' and 'Report validation' button is present!!");
					viewFinancialPageObj.clickOnIntacctValidation();
					sleep();
					intacctErrorCount = viewFinancialPageObj.getIntacctTotalErrorCount();
					test.log(LogStatus.INFO,  "Intact Validation Error Count : " + intacctErrorCount);
					
					viewFinancialPageObj.clickOnReportValidation();
					sleep();
					reportErrorCount = viewFinancialPageObj.getReportTotalErrorCount();
					test.log(LogStatus.INFO,  "Report Validation Error Count : " + reportErrorCount);
					
					totalErrorCount = viewFinancialPageObj.getTotalValidationErrorCount();
					test.log(LogStatus.INFO,  "Total Validation Error Count : " + totalErrorCount);
					int totalValidationError = Integer.parseInt(totalErrorCount);
					
					if(totalValidationError > 0 && !viewFinancialPageObj.isCompleteValidationEnabled()) {
						test.log(LogStatus.INFO,  "Total Validation Error Count is : " + totalValidationError + "Complete Validation button is disabled!!!");
					} else if(totalValidationError == 0 && viewFinancialPageObj.isCompleteValidationEnabled()) {
						test.log(LogStatus.INFO,  "Total Validation Error Count is : " + totalValidationError + "Complete Validation button is enabled!!!");
					}
				}
			} else {
				test.log(LogStatus.INFO, status+ " Activity is not present in activity list!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=5)
	public void completeFinancialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		try {
			int totalValidationError = Integer.parseInt(viewFinancialPageObj.getTotalValidationErrorCount());
			System.out.println(totalValidationError);
			System.out.println(viewFinancialPageObj.isCompleteValidationEnabled());
			takeScreenshot();
			if(totalValidationError > 0 && !viewFinancialPageObj.isCompleteValidationEnabled()) {
				test.log(LogStatus.INFO,  "Total Validation Error Count is : " + totalValidationError + "Complete Validation button is disabled!!!");
				viewFinancialPageObj.clickOnActivityDetail();
				Assert.assertTrue(activityDetailsPageObj.isCompleteValidationButtonExist());
				activityDetailsPageObj.clickOnCompleteValidation();
			} else if(totalValidationError == 0 && viewFinancialPageObj.isCompleteValidationEnabled()) {
				test.log(LogStatus.INFO,  "Total Validation Error Count is : " + totalValidationError + "Complete Validation button is enabled!!!");
				viewFinancialPageObj.clickOnCompleteValidation();
			}
			
			sleep();
			if(activityDetailsPageObj.getStatus().equals("In Review")) {
				test.log(LogStatus.PASS,  "Financial Completed successfully!!!");
			} else {
				test.log(LogStatus.FAIL,  "Failed to Complete Financial!!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=6)
	public void reviewFinancialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String status = "In Review";
		try {
			sleep();
			Assert.assertTrue(activityDetailsPageObj.getStatus().equals(status));
			if (activityDetailsPageObj.getAssignToLevel().equals(managerName)) {
				test.log(LogStatus.INFO, "Activity is Assigned to logged in user for review!!");
			} else {
				test.log(LogStatus.INFO, " Activity is not Assigned to logged in user for review!!");
				activityDetailsPageObj.mousehoverOnViewFinancial();
				if (activityDetailsPageObj.isViewFinancialNotVisible()) {
					test.log(LogStatus.INFO, "view financial is disable for other users!!");
				}
				this.assignActivityToSameUser();
			}
			takeScreenshot();
			activityDetailsPageObj.clickOnViewFinancials();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=7)
	public void rejectFinancialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String note = generateRandomString(5),
			   status = "In Progress",
			   successMsg = "Draft Submitted Successfully for Stage : ReviewOne";
		try {
			Assert.assertTrue(viewFinancialPageObj.isRejectButtonExist());
			viewFinancialPageObj.clickOnReject();
			if(activityDetailsPageObj.isAddNotePopoverExist()) {
				activityDetailsPageObj.setRejectNote(note);
				activityDetailsPageObj.clickOnReject();
			}
			
			if(viewFinancialPageObj.isValidateFinancialButtonExist()) {
				test.log(LogStatus.INFO,  "'Verify Path for GDrive', 'Re-Generate Financials' and 'complete Financial' button is exist on view financial after rejection'!!" );
			} else {
				test.log(LogStatus.INFO,  "'Verify Path for GDrive', 'Re-Generate Financials' and 'complete Financial' button doesn't exist. It Should exist on view financial after rejection'!!" );
			}
			takeScreenshot();
			
			viewFinancialPageObj.clickOnActivityDetail();
			sleep();
			if(activityDetailsPageObj.getStatus().equals(status)) {
				test.log(LogStatus.PASS, "Financial rejected successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to reject financial!!!!");
			}
			takeScreenshot();
			
			if(activityDetailsPageObj.isCompleteValidationButtonExist()) {
				activityDetailsPageObj.clickOnCompleteValidation();
				if(basePage.checkSuccessMessage(successMsg)) {
					test.log(LogStatus.INFO, "success msg recieved - " + successMsg);
				}
				if(activityDetailsPageObj.getStatus().equals("In Review")) {
					test.log(LogStatus.INFO, "completed Financial again after rejection!!");
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=8)
	public void submitForDraftFinancialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String status = "In Review",
			   successMsg = "Draft Submitted Successfully for Stage : ReviewOne";
		try {
			Assert.assertTrue(activityDetailsPageObj.getStatus().equals(status));
			if (activityDetailsPageObj.getAssignToLevel().equals(managerName)) {
				test.log(LogStatus.INFO, "Activity is Assigned to logged in user for review!!");
			} else {
				test.log(LogStatus.INFO, " Activity is not Assigned to logged in user for review!!");
				activityDetailsPageObj.mousehoverOnViewFinancial();
				if (activityDetailsPageObj.isViewFinancialNotVisible()) {
					test.log(LogStatus.INFO, "view financial is disable for other users!!");
				}
				this.assignActivityToSameUser();
			}
			takeScreenshot();
			activityDetailsPageObj.clickOnViewFinancials();
			if(viewFinancialPageObj.isSubmitDraftButtonExist()) {
				viewFinancialPageObj.clickOnSubmitDraft();
				sleep();
				if(basePage.checkSuccessMessage(successMsg)) {
					test.log(LogStatus.INFO, "success message recieved successfully - " + successMsg);
				}
				
				if(activityDetailsPageObj.getStatus().equals("Complete")) {
					test.log(LogStatus.PASS, "Financial Completed successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to complete financial!!!!");
				}
			}
			takeScreenshot();
			activityDetailsPageObj.clickOnBackToActivities();
			sleep();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@Test(priority=9)
	public void reviewDraftAndFinancialActivityTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String status = "In Progress",
			   activity = "Review Draft and Publish Financials";
		try {
			homePageObj.clickOnControlDock();
			Thread.sleep(10000);
			activityListPageObj.searchAndSelectClient(companyName);
			activityListPageObj.clickOnFilterClientActivities();
			Thread.sleep(5000);
			Assert.assertTrue(activityListPageObj.isClientSelected(companyName));
			test.log(LogStatus.INFO, companyName + " Client Selected!!");
			activityListPageObj.setSearch(activity);
			Assert.assertFalse(activityListPageObj.isActivityTableExist());
			if(activityListPageObj.clickOnActivity(status)) {
				test.log(LogStatus.INFO,    status + " Activity is present in activity list!!");
			} else {
				activityListPageObj.clickOnActivityDetails();
			}
			
			if (activityDetailsPageObj.getStatus().equals("Waiting")) {
				test.log(LogStatus.INFO, "activity is in " + status +"status it seems financial is not completed!!");
			} else if(activityDetailsPageObj.getStatus().equals(status)) {
				test.log(LogStatus.INFO, "activity is in " + status +"status Financial is completed successfully!!");
				if (activityDetailsPageObj.getAssignToLevel().equals(managerName)) {
					test.log(LogStatus.INFO, "Activity is Assigned to logged in user for review!!");
				} else {
					test.log(LogStatus.INFO, " Activity is not Assigned to logged in user for review!!");
					this.assignActivityToSameUser();
				}
				takeScreenshot();
				activityDetailsPageObj.clickOnViewFinancials();
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test(priority=10)
	public void rejectReviewDraftTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String errorMsg = "Rejection note is mandatory!";
		try {
			Assert.assertTrue(viewFinancialPageObj.isRejectDraftButtonExist());
			viewFinancialPageObj.clickOnRejectDraft();
			if(activityDetailsPageObj.isAddNotePopoverExist()) {
				activityDetailsPageObj.clickOnReject();
			}
			
			if (basePage.checkErrorMessage(errorMsg)) {
				test.log(LogStatus.PASS, "recieved error msg successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to recieve error message!!");
			}
			takeScreenshot();
			activityDetailsPageObj.clickOnViewFinancials();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test(priority=11)
	public void approveReviewDraftTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String status = "In Review";
		try {
			Assert.assertTrue(viewFinancialPageObj.isApproveDraftButtonExist());
			viewFinancialPageObj.clickOnApproveDraft();
			sleep();
			if (activityDetailsPageObj.getStatus().equals(status)) {
				test.log(LogStatus.PASS, " Review Draft approved successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to approve Review Draft!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=12)
	public void reviewDraftTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		String status = "In Review";
		try {
			Assert.assertTrue(activityDetailsPageObj.getStatus().equals(status));
			if (activityDetailsPageObj.getAssignToLevel().equals(managerName)) {
				test.log(LogStatus.INFO, "Activity is Assigned to logged in user for review!!");
				if (activityDetailsPageObj.isViewFinancialsButtonExist()) {
					test.log(LogStatus.INFO, "view financial is should available for same users!!");
				}
			} else {
				test.log(LogStatus.INFO, " Activity is not Assigned to logged in user for review!!");
				if (!activityDetailsPageObj.isViewFinancialsButtonExist()) {
					test.log(LogStatus.INFO, "view financial should not available for other users!!");
				}
				this.assignActivityToSameUser();
			}
			sleep();
			takeScreenshot();
			activityDetailsPageObj.clickOnViewFinancials();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=12)
	public void publishFinacialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		try {
			sleep();
			Assert.assertTrue(viewFinancialPageObj.isPublishDraftButtonExist());
		    viewFinancialPageObj.clickOnPublishDraft();
		    if(viewFinancialPageObj.isProgressFinancialModalExist()) {
		    	test.log(LogStatus.INFO, "publish financial is in progress!!");
		    	if(viewFinancialPageObj.isPublishFinancialErrorExist()) {
		    		test.log(LogStatus.INFO, "Getting Error While publishing financial - " + viewFinancialPageObj.getpublishFinancialError());
		    		viewFinancialPageObj.clickOnOk();
		    		if(viewFinancialPageObj.isReloadFinancialButtonExist()) {
		    			test.log(LogStatus.INFO, "Report is not publised");
		    		}
		    	}
		    }
		    takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=13)
	public void reloadFinacialTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		viewFinancialPageObj = new ViewFinancialPage(driver);
		try {
			Assert.assertTrue(viewFinancialPageObj.isReloadFinancialButtonExist());
			viewFinancialPageObj.clickOnReloadFinancial();
			if(viewFinancialPageObj.isProgressFinancialModalExist()) {
		    	test.log(LogStatus.INFO, "publish financial is in progress!!");
		    	if(viewFinancialPageObj.isPublishFinancialErrorExist()) {
		    		test.log(LogStatus.INFO, "Getting Error While publishing financial - " + viewFinancialPageObj.getpublishFinancialError());
		    		viewFinancialPageObj.clickOnOk();
		    	}
		    }
			 Assert.assertTrue(activityDetailsPageObj.isActivityDetailsPageExist());
			if(activityDetailsPageObj.getStatus().equals("Complete")) {
				test.log(LogStatus.PASS, "Financial Completed successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to complete financial!!!!");
			}
		    takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void assignActivityToSameUser() {
		takeScreenshot();
		activityDetailsPageObj.clickOnAssignToLevel();
		activityDetailsPageObj.selectAssignee(managerName);
		activityDetailsPageObj.clickOnSaveAssignedTo();
		String assignee = activityDetailsPageObj.getAssignToLevel();
		if (assignee.equals(managerName)) {
			test.log(LogStatus.PASS, "assigned Activity to logged in user for review successfully!!!!");
		}
	}
}
