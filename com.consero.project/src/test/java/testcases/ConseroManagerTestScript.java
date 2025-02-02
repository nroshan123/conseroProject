package testcases;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import basetest.BaseTest;
import pageclass.AddActivityPage;
import pageclass.BasePage;
import pageclass.ActivityDetailsPage;
import pageclass.ActivityListPage;
import pageclass.DashboardPage;
import pageclass.HomePage;
import pageclass.LoginPage;
import pageclass.MyProfilePage;
import pageclass.RecurranceManagementPage;
import pageclass.UploadActivityTemplatePage;
import pageclass.WorkloadPage;
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
		activityListPageObj = new ActivityListPage(driver);
		try {
			if(basePage.isElementPresent(homePageObj.companyLogo, 60)) {
				test.log(LogStatus.PASS, "Home page loaded successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to load home page!!");
			}
			sleep();
			homePageObj.clickOnEntityDropdown();
			homePageObj.selectEntity(companyName);
			homePageObj.clickOnGo();
			sleep();
			homePageObj.clickOnControlDockInSimpl();
			if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
				test.log(LogStatus.PASS, "Redirected On Control Dock Page successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to redirect On Control Dock Page!!!!");
			}
			sleep();
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
			Assert.assertTrue(basePage.isElementPresent(activityListPageObj.pageTitle, 60));
			test.log(LogStatus.INFO, "Activity list page opened sucessfully!!");
			sleep();
			if(activityListPageObj.isActivitiesHeadertableVisible()) {
				test.log(LogStatus.INFO, "All Filters Are visible in Activity list page!!");
			}
			
			if(activityListPageObj.getAssignee().equals(managerName)) {
				test.log(LogStatus.INFO, "assignee name is same as logged in user name!!");
			}
			
			activityListPageObj.clickOnBtnActivityheader();
			if(!activityListPageObj.isActivitiesHeadertableVisible()) {
				test.log(LogStatus.PASS, "All Filters Are hidden after click on 'Collapse' button!!");
				takeScreenshot();
				activityListPageObj.clickOnBtnActivityheader();
				if(activityListPageObj.isActivitiesHeadertableVisible()) {
					test.log(LogStatus.INFO, "All Filters Are visible after click on 'Collapse' button in Activity list page!!");
				}
			} 	
			takeScreenshot();
			
		    if(activityListPageObj.checkDownloadFileButton()) {
		    	test.log(LogStatus.INFO, "'CSV', 'EXCEL' and 'PDF' button is present in activity list page!!");
		    }
		    
		    if(activityListPageObj.isclearFilterExist()) {
		    	test.log(LogStatus.INFO, "'Clear Filter' button is present in activity list page!!");
		    }
		    
		    if(basePage.isElementPresent(activityListPageObj.activityTable, 30)) {
				test.log(LogStatus.INFO, "No Activity list found.");
			}
		    takeScreenshot();
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
			   filePath = "C:\\Users\\Admin\\Downloads";
		try {
			activityListPageObj.clickOnCsv();
			Thread.sleep(6000);
			if(isFileDownloaded(filePath, csvFilename)) {
				test.log(LogStatus.PASS, "CSV File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download CSV File!!");
			}
			activityListPageObj.clickOnExcel();
			Thread.sleep(6000);
			if(isFileDownloaded(filePath, excelFilename)) {
				test.log(LogStatus.PASS, "Excel File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download Excel File!!");
			}
			activityListPageObj.clickOnPdf();
			Thread.sleep(6000);
			if(isFileDownloaded(filePath, pdfFileName)) {
				test.log(LogStatus.PASS, "PDF File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download PDF File!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify download file. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void verfiyActivityPaginationTC() {
		activityListPageObj = new ActivityListPage(driver);
		try {
			if(basePage.isElementPresent(activityListPageObj.activityTable, 30)) {
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
			System.out.println(activityListPageObj.getSelectedEntries());
			try {
				entries = Integer.valueOf(activityListPageObj.getSelectedEntries());
			} catch(NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			int count = activityListPageObj.getActivityCount();
			if(entries == count) {
				test.log(LogStatus.PASS, "Activity count is equal to selected entries.");
			} else {
				test.log(LogStatus.FAIL, "Activity count is not equal to selected entries.");
			}
			takeScreenshot();
			test.log(LogStatus.INFO, "selected entry is : " + selectedEntries + "and page count displayed in pagination is : " + activityListPageObj.getLastPageCount());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=8)
	public void addCloseActivityTC() {
		homePageObj = new HomePage(driver);
		addActivityPageObj = new AddActivityPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		
		closeActivity = "test_close_activity_" + generateRandomString(5);
		String submenu ="Add Activity",
			   day = generateNumberExceptZero(20),
			   checkList1 = generateRandomString(5),
			   checklist2 = generateRandomString(5),
			   duration = generateNumberExceptZero(10),
			   activityType = "CloseActivity";
			   System.out.println("sfjhsd");

		int dueForClient = Integer.parseInt(day) + 1;
		String dueForClientBizDay = String.valueOf(dueForClient);
		try {
			sleep();
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			 System.out.println("fsdfsd");
			if(addActivityPageObj.isactivityModalExist()) {
				try {
					sleep();
					addActivityPageObj.selectCompany(companyName);
					addActivityPageObj.selectActivityType(activityType);
					addActivityPageObj.setActivityDetails(closeActivity, day, dueForClientBizDay, managerName );
					addActivityPageObj.clickOnChecklistTab();
					addActivityPageObj.setCheckListItemOne(checkList1);
					addActivityPageObj.clickOnChecklistLink();
					if(addActivityPageObj.isChecklistAdded()) {
						test.log(LogStatus.INFO, "checklist two added successfully!!");
						addActivityPageObj.setCheckListItemTwo(checklist2);
					}
					takeScreenshot();
//					addActivityPageObj.clickOnReviwerTab();
//					addActivityPageObj.setReviewerOneDetails(managerName, day, duration);
//					addActivityPageObj.clickOnAddReviewerLink();
//					if(addActivityPageObj.isReviewerTwoLabelExist()) {
//						test.log(LogStatus.INFO, "reviewer two added successfully!!");
//						addActivityPageObj.setReviewerTwoDetails(managerName, day, duration);
//					}
					addActivityPageObj.clickOnSubmit();
					takeScreenshot();
					if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
						test.log(LogStatus.INFO, "Redirected On Activity list Page successfully!!!!");
					} 
					sleep();
					activityListPageObj.setSearch(closeActivity);
					if(basePage.isElementPresent(activityListPageObj.activityTable, 30)) {
						test.log(LogStatus.INFO, "No Activity list found.");
					} else {
						if(activityListPageObj.isActivitiesExist(closeActivity)) {
							test.log(LogStatus.PASS, "Activity added sucessfully.");
						} else {
							test.log(LogStatus.PASS, "Failed to add Activity.");
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
//					addActivityPageObj.clickOnCancelAddActivity();
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
		
		nonCloseActivity = "test_nonclose_activity_" + generateRandomString(5);
		String submenu ="Add Activity",
			   activityType = "NonCloseActivity",
			   date = addDayToCurrentDate(1),
		       day = getdayFromDateFormat(date);
		try {
			homePageObj.moveToControlDock();
			homePageObj.clickOnAddActivity();
			
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
//					addActivityPageObj.clickOnIsSubActivity();
//					addActivityPageObj.clickOnValidateDueDate();
//					if(addActivityPageObj.isRelaventParentActivity()) {
//						test.log(LogStatus.INFO, "parent activities field is exist!!!!");
//					}
//					addActivityPageObj.clickOnIsSubActivity();
					addActivityPageObj.clickOnActivityTab();
					addActivityPageObj.setNonCloseActivityDetails(nonCloseActivity, day, managerName);
					 takeScreenshot();
					addActivityPageObj.clickOnSubmit();
				} catch(Exception e) {
					addActivityPageObj.clickOnCancelAddActivity();
				}
				
				if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
					test.log(LogStatus.PASS, "Redirected On Activity list Page successfully!!!!");
				} 
				sleep();
				activityListPageObj.setSearch(nonCloseActivity);
				if(basePage.isElementPresent(activityListPageObj.activityTable, 30)) {
					test.log(LogStatus.INFO, "No Activity list found.");
				} else {
					if(activityListPageObj.isActivitiesExist(nonCloseActivity)) {
						test.log(LogStatus.PASS, "Activity added sucessfully.");
					} else {
						test.log(LogStatus.PASS, "Failed to add Activity.");
					}
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add non close activity. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 10)
	public void activityDetailsTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		String company = "", function = "", pageTitle = "", activity = "", status = "", assignedTo = "";

		try {
			activityListPageObj.setSearch(closeActivity);
			Thread.sleep(6000);
			if (activityListPageObj.isActivitiesExist(closeActivity)) {
				company = activityListPageObj.getCompanyName();
				function = activityListPageObj.getFunction();
				activity = activityListPageObj.getActivityDetails();
				pageTitle = company + " :: " + function;
				status = activityListPageObj.getStatus();
				assignedTo = activityListPageObj.getAssignedTo();
				sleep();
				System.out.println("bdhga");
				System.out.println(pageTitle);
				activityListPageObj.clickOnActivityDetails();
				sleep();
				Assert.assertTrue(activityDetailsPageObj.isPageTitleExist(pageTitle));
				String activityName = activityDetailsPageObj.getActivityName(),
						assignee = activityDetailsPageObj.getAssignToLevel();

				Assert.assertEquals(activityName, activity);
				test.log(LogStatus.INFO, "Activity Name displayed in 'activity details' Page is correct!!!!");

				if (activityDetailsPageObj.checkPanelExist()) {
					test.log(LogStatus.INFO,
							"'Document', 'Note' and 'Activity Flow' panels is displayed in 'activity details'!!!!");
				}

				if (activityDetailsPageObj.isBackToActivityButtonExist()) {
					test.log(LogStatus.INFO, "'Back toActivity' button is displayed in 'activity details'!!!!");
				}

				if (assignee.equals(assignedTo)) {
					test.log(LogStatus.INFO, "assigned To displayed in 'activity details' Page is correct!!!!");
					activityDetailsPageObj.clickOnAssignToLevel();
					activityDetailsPageObj.selectAssignee(managerName);
					activityDetailsPageObj.clickOnSaveAssignedTo();
					if (assignee.equals(managerName)) {
						test.log(LogStatus.PASS, "assigned to changed successfully!!!!");
					} else {
						test.log(LogStatus.FAIL, "Failed to change Assigned To!!!!");
					}
				}

				if (status.equals(activityDetailsPageObj.getStatus())) {
					test.log(LogStatus.INFO, "status displayed in 'activity details' Page is correct!!!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify activity details. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=11)
	public void activityWorkFlowTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		try {
			String status = activityDetailsPageObj.getStatus();
			if(status.equals("Assigned") && activityDetailsPageObj.isStartButtonExist()) {
				test.log(LogStatus.INFO, "'start' button is displayed in 'activity details' Page for assigned status!!!!");
				activityDetailsPageObj.clickOnStart();
				sleep();
				
				if(activityDetailsPageObj.getStatus().equals("In Progress") && activityDetailsPageObj.isMarkAsCompleteButtonExist()) {
					test.log(LogStatus.PASS, "activity started sucessfully, status changed to 'In Progress' and 'mark as complete' button exist!!!!");
					takeScreenshot();
					if(!activityDetailsPageObj.isAssignedToReviewer("Review 1")) {
						activityDetailsPageObj.clickOnMarkAsComplete();
						sleep();
						Assert.assertEquals("Complete", activityDetailsPageObj.getStatus());
						test.log(LogStatus.PASS, "activity completed sucessfully, status changed to 'Completed' ");
						Assert.assertTrue(activityDetailsPageObj.isRecallButtonExist());
						test.log(LogStatus.INFO, "'Recall' button exist!!!!");
						takeScreenshot();
						activityDetailsPageObj.clickOnRecall();
						sleep();
						Assert.assertEquals("In Progress", activityDetailsPageObj.getStatus());
						test.log(LogStatus.PASS, "activity recalled sucessfully and status changed to 'In Progress' ");
						takeScreenshot();
					}
				} else {
					test.log(LogStatus.FAIL, "Failed to start activity!!!!");
				}
			} else {
				test.log(LogStatus.INFO, "'start' button should display in 'activity details' Page for assigned status!!!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add activity note. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=12)
	public void addActivityNoteTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		String note = generateRandomString(5),
		       message = "Note Added Successfully";
		try {
			activityDetailsPageObj.clickOnAddNote();
			Assert.assertTrue(activityDetailsPageObj.isAddNotePopoverExist());
			test.log(LogStatus.INFO, "'Add Note' popup opened successfully!!!!");
			activityDetailsPageObj.setNote(note);
			activityDetailsPageObj.clickOnAdd();
			if(basePage.checkSuccessMessage(message)) {
				test.log(LogStatus.INFO, "success message recieved- " + message);
			}
			takeScreenshot();
			sleep();
			if(activityDetailsPageObj.isNoteExist(note)) {
				test.log(LogStatus.PASS, "Activity note added successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to add activity Note!!!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add activity note. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=13)
	public void editActivityNoteTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		String note = generateRandomString(6),
			   message = "Note Added Successfully";
		
		try {
			Assert.assertTrue(activityDetailsPageObj.isNoteDivExist());
			activityDetailsPageObj.clickOnEdit();
			Assert.assertTrue(activityDetailsPageObj.isAddNotePopoverExist());
			activityDetailsPageObj.setEditNoteTextArea(note);
			activityDetailsPageObj.clickOnUpdate();
			if(basePage.checkSuccessMessage(message)) {
				test.log(LogStatus.INFO, "success message recieved- " + message);
			}
			takeScreenshot();
			sleep();
			if(activityDetailsPageObj.getUpdatedNote().equals(note)) {
				test.log(LogStatus.PASS, "Activity note updated successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to update activity Note!!!!");
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to edit activity note. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=14)
	public void uploadActivityDocumentTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		String modalTitle = "Upload",
			   fileName = "C:\\Users\\Admin\\conseroFiles\\sample.pdf",
		       message = "File Uploaded Successfully",
		       link = "https://" + generateRandomString(5),
		       linkName = generateRandomString(5);
		try {
			for(int i=0; i<2; i++) {
				activityDetailsPageObj.clickOnUploadDocument();
				Assert.assertTrue(activityDetailsPageObj.isUploadModalExist(modalTitle));
				if(i==0) {
					activityDetailsPageObj.setDocument(fileName);
				}
				
				if(i==1) {
					activityDetailsPageObj.clickOnLink();
					activityDetailsPageObj.setLinkDocument(link);
					activityDetailsPageObj.setLinkName(linkName);
				}
				
				activityDetailsPageObj.clickOnUpload();
				if(basePage.checkSuccessMessage(message)) {
					test.log(LogStatus.INFO, "success message recieved- " + message);
				}
				takeScreenshot();
				sleep();
				if(activityDetailsPageObj.isDocumentExist(fileName)) {
					test.log(LogStatus.PASS, "Activity Document uploaed successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to upload activity Document!!!!");
				}
				takeScreenshot();
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to upload activity note. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=15)
	public void deleteActivityDocumentTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		String message = "Document deleted Successfully";
		try {
			String name = activityDetailsPageObj.getDocumentName();
			activityDetailsPageObj.clickOnDeleteDocument();
			if(basePage.checkSuccessMessage(message)) {
				test.log(LogStatus.INFO, "success message recieved- " + message);
			}
			takeScreenshot();
			if(!activityDetailsPageObj.isDocumentExist(name)) {
				test.log(LogStatus.PASS, "Activity Document deleted successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to delete activity Document!!!!");
			}
			takeScreenshot();
			activityDetailsPageObj.clickOnBackToActivities();
			Assert.assertTrue(basePage.isElementPresent(activityListPageObj.pageTitle, 60));
			test.log(LogStatus.INFO, "Redirected to Activity List page!!!!");
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete activity note. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 16)
	public void verifyActivityNoteTC() {
		activityListPageObj = new ActivityListPage(driver);
		try {
			activityListPageObj.setSearch(closeActivity);
			sleep();
			if (!basePage.isElementPresent(activityListPageObj.activityTable, 30)) {
				if (activityListPageObj.isNoteButtonExist()) {
					test.log(LogStatus.INFO, "Activity Note button is visible!!!!");
					activityListPageObj.clickOnNoteButton();
					if (activityListPageObj.isNoteContainerExist()) {
						test.log(LogStatus.PASS, "Note is visible on click of note button!!!!");
						takeScreenshot();
						sleep();
						activityListPageObj.clickOnNoteButton();
						if (!activityListPageObj.isNoteContainerExist()) {
							test.log(LogStatus.PASS, "Note is hidden on click of note button!!!!");
						} else {
							test.log(LogStatus.FAIL, "Note is  not hidden on click of note button!!!!");
						}
					} else {
						test.log(LogStatus.FAIL, "Note is  not visible on click of note button!!!!");
					}
				} else {
					test.log(LogStatus.INFO, "Activity Note button should be visible for activity - " + closeActivity);
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify activity note. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=17)
	public void editActivityTC() {
		activityListPageObj = new ActivityListPage(driver);
		addActivityPageObj = new AddActivityPage(driver);
		String description = "test_close_activity_" + generateRandomString(5);
		String content = "Do you want to save changes to future month close templates?";
		try {
			activityListPageObj.setSearch(closeActivity);
			if(!basePage.isElementPresent(activityListPageObj.activityTable, 30)) {
				activityListPageObj.clickOnEdit();
				Assert.assertTrue(addActivityPageObj.isactivityModalExist());
				sleep();
				addActivityPageObj.setEditActivityDetails(description, content);
				sleep();
				if(activityListPageObj.isActivitiesExist(description)) {
					test.log(LogStatus.PASS, "Activity updated successfully!!!!");
					closeActivity = description;
				} else {
					test.log(LogStatus.PASS, "Failed to update activity!!!!");
				}
			} else {
				test.log(LogStatus.FAIL, "No Activity found.");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to edit activity. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	@Test(priority=18)
	public void addSubActivityTC() {
		activityListPageObj = new ActivityListPage(driver);
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		addActivityPageObj = new AddActivityPage(driver);
		subActivity = "test_aubactivity_" + generateRandomString(3);
		try {
			activityListPageObj.setSearch(nonCloseActivity);
			Assert.assertFalse(basePage.isElementPresent(activityListPageObj.activityTable, 20));
			String company = activityListPageObj.getCompanyName(),
				   function = activityListPageObj.getFunction(),
				   pageTitle = company + " :: " + function;
			activityListPageObj.clickOnActivityDetails();
			sleep();
			Assert.assertTrue(activityDetailsPageObj.isPageTitleExist(pageTitle));
			test.log(LogStatus.INFO, "Activity details Page opened successfully!!!!");
			if(activityDetailsPageObj.checkSubActivityButtonExist()) {
				test.log(LogStatus.INFO, "'Add SubActivity' and 'Convert to Sub Activity' button is present!!!!");
				activityDetailsPageObj.clickOnAddSubactivity();
				addActivityPageObj.setAddSubActivityDetails(subActivity);
				sleep();
				if(activityListPageObj.isTreeIconExist()) {
					test.log(LogStatus.INFO, "tree icon exist for activity - " + nonCloseActivity);
				}
				takeScreenshot();
				activityListPageObj.clickOnActivityDetails();
				sleep();
				if(activityDetailsPageObj.isSubActivityAdded(subActivity)) {
					test.log(LogStatus.PASS, "SubActivity added successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to add sub activity!!!!");
				}
				
			} else {
				test.log(LogStatus.INFO, "'Add SubActivity' and 'Convert to Sub Activity' button is not present.It should be available for non close activity!!!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add sub activity. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=19)
	public void attachSubActivityTC() {
		activityDetailsPageObj = new ActivityDetailsPage(driver);
		try {
			if(activityDetailsPageObj.isAttachSubActivityButtonExist()) {
				test.log(LogStatus.INFO, "'Attach Sub Activity' button is exist!!!!");
				activityDetailsPageObj.clickOnAttachSubactivity();
				basePage.waitElementVisible(activityDetailsPageObj.attachSubactivitySection, 30);
				activityDetailsPageObj.clickOnSubactivityDropdown();
				activityDetailsPageObj.selectSubactivity();
				String subActivity = activityDetailsPageObj.getSelectedSubactivity();
				activityDetailsPageObj.clickOnConfirmActivityChange();
				
				if(activityDetailsPageObj.isSubactivityAttached(subActivity)) {
					test.log(LogStatus.PASS, "Subactivity attached successfully!!!! " + subActivity);
				} else {
					test.log(LogStatus.FAIL, "Fialed to attach Subactivity!!!!");
				}
				takeScreenshot();
				activityDetailsPageObj.clickOnBackToActivities();
				sleep();
				Assert.assertTrue(basePage.isElementPresent(activityListPageObj.pageTitle, 60));
				test.log(LogStatus.INFO, "Redirected to Activity List page!!!!");
			} else {
				test.log(LogStatus.INFO, "'Attach Sub Activity' button doesn't exist!!!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=20)
	public void verifyActivityTreeTC() {
		activityListPageObj = new ActivityListPage(driver);
		try {
			sleep();
			Assert.assertTrue(activityListPageObj.isActivitiesExist(nonCloseActivity));
			if(activityListPageObj.isTreeIconExist()) {
				test.log(LogStatus.INFO, "tree icon exist for activity - " + nonCloseActivity);
				activityListPageObj.clickOnTreeIcon();
				Thread.sleep(6000);
				if(activityListPageObj.isActivityTreeModalExist()) {
					basePage.waitElementVisible(activityListPageObj.searchActivityInTree, 80);
					activityListPageObj.setSearchActivityInTree(subActivity);
					if(activityListPageObj.getSubActivity().equals(subActivity)) {
						test.log(LogStatus.INFO, "subactivity exist in tree View !!!");
					}
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify activity tree. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=21)
	public void exportActivityTreeViewTC() {
		activityListPageObj = new ActivityListPage(driver);
		String filePath = "C:\\Users\\Admin\\Downloads",
				csvFilename = "Tree View Structure.csv",
				excelFileName = "Tree View Structure.xlsx";
		try {
			if(activityListPageObj.isActivityTreeModalExist()) {
				activityListPageObj.clickOnExcelTreeView();
				Thread.sleep(6000);
				if(isFileDownloaded(filePath, excelFileName)) {
					test.log(LogStatus.PASS, "EXCEL tree view File Downloaded SuccessFully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to Download EXCEL tree view File!!");
				}
				activityListPageObj.clickOnCsvTreeView();
				Thread.sleep(6000);
				if(isFileDownloaded(filePath, csvFilename)) {
					test.log(LogStatus.PASS, "CSV tree view File Downloaded SuccessFully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to Download CSV tree view File!!");
				}
				activityListPageObj.clickOnBtnBackToActivity();
			}
			takeScreenshot();
		}catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to download activity tree view. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=22)
	public void uploadActivityTemplateTC() {
		homePageObj = new HomePage(driver);
		activityTemplatePageObj = new UploadActivityTemplatePage(driver);
		String submenu = "Upload Recurring Activity Template",
			   name = generateRandomString(5),
			   filePath = "C:\\Users\\Admin\\conseroFiles\\ODM_Template_Sample.csv";
		try {
			homePageObj.moveToControlDock();
			homePageObj.clickOnRecurringActivityTemplate();
			Assert.assertTrue(basePage.isElementPresent(activityTemplatePageObj.pageTitle, 60));
			test.log(LogStatus.INFO, submenu + " Page opened sucessfully!!");
			activityTemplatePageObj.setTemplateDetails(name, filePath);
			activityTemplatePageObj.clickOnValidateTemplate();
			if(activityTemplatePageObj.isErrorDivExist()) {
				test.log(LogStatus.INFO, "Get error while uploading Template. " + activityTemplatePageObj.getErrorMessage());
			} else {
				test.log(LogStatus.INFO, "Uploaded activity Template ");
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to upload activity template. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=23)
	public void recurranceManagementTC() {
		homePageObj = new HomePage(driver);
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		String submenu = "Recurrence Management", activityName = "";
		try {
			sleep();
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			Assert.assertTrue(basePage.isElementPresent(recurranceManagementPageObj.pageTitle, 60));
			test.log(LogStatus.INFO, submenu + " Page opened sucessfully!!");
			
			if(basePage.isElementPresent(recurranceManagementPageObj.recurranceTable, 30)) {
				test.log(LogStatus.INFO, "No reccrances found.");
			} else {
				test.log(LogStatus.INFO, "Recurrances is displayed in list.");
			}
			
			if(recurranceManagementPageObj.checkExportFileButton()) {
				test.log(LogStatus.INFO, "'CSV' and 'EXCEL' button is available to export file!!");
			}
			
			activityName = recurranceManagementPageObj.getActivtyName();
			recurranceManagementPageObj.setSearch(activityName);
			if(basePage.isElementPresent(recurranceManagementPageObj.recurranceTable, 10)) {
				test.log(LogStatus.INFO, "No reccrances found.");
			} else {
				if(recurranceManagementPageObj.isReccurranceSearched(activityName)) {
					test.log(LogStatus.PASS, "Recurrance searched successfully.");
				} else {
					test.log(LogStatus.FAIL, "Failed to search Recurrance.");
				}
			}
			takeScreenshot();
			recurranceManagementPageObj.clearSearch();
			test.log(LogStatus.INFO, "cleared search result.");
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify recuurence management. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=24)
	public void exportRecurrancesTC() {
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		String csvFileName = "Recurrence details.csv",
			   excelFileName = "Recurrence details.xlsx",
			   filePath = "C:\\Users\\Admin\\Downloads";
		try {
			recurranceManagementPageObj.clickOnExcel();
			Thread.sleep(4000);
			if(isFileDownloaded(filePath, excelFileName)) {
				test.log(LogStatus.PASS, "EXCEL Reccurance File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download EXCEL Reccurance File!!");
			}
			recurranceManagementPageObj.clickOnCsv();
			Thread.sleep(4000);
			if(isFileDownloaded(filePath, csvFileName)) {
				test.log(LogStatus.PASS, "CSV Reccurance File Downloaded SuccessFully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to Download CSV Reccurance File!!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=25)
	public void recurranceManagementPaginationTC() {
		homePageObj = new HomePage(driver);
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		try {
			Assert.assertTrue(basePage.isElementPresent(recurranceManagementPageObj.pageTitle, 60));
			if(basePage.pagination.size()>0) {
				test.log(LogStatus.INFO, "Pagination Found.");
				basePage.clickOnPagination();
			} else {
				test.log(LogStatus.INFO, "Pagination Not Found.");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=26)
	public void verifyRecurrancePreviousAndNextPageTC() {
		try {
			this.prevoiusAndNextPagination();
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify previous and next pagination in Recurrance Management. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=27)
	public void verifyRecurranceFirstAndLastPageTC() {
		try {
			this.firstAndLastPagination();
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify first and last pagination in Recurrance Management. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=28)
	public void showRecurranceEntriesTC() {
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		int entries = 0;
		int count = 0;
		try {
			if(basePage.firstButton.isEnabled()) {
				basePage.clickOnFirstPagination();
			}
			int selectedEntries = Integer.parseInt(recurranceManagementPageObj.getSelectedEntries());
			String pageCount = recurranceManagementPageObj.getLastPageCount();
			count = recurranceManagementPageObj.getRecurranceCount();
			if(selectedEntries == count) {
				test.log(LogStatus.PASS, "Recurrance count - " +count+  " is equal to selected entries - " + selectedEntries);
			} else {
				test.log(LogStatus.FAIL, "Recurrance count is not equal to selected entries.");
			}
			test.log(LogStatus.INFO, "selected entry is : " + selectedEntries + "and page count displayed in pagination is : " + pageCount);
			recurranceManagementPageObj.selectShowEntries();
			sleep();
			try {
				entries = Integer.parseInt(recurranceManagementPageObj.getSelectedEntries());
			} catch(NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			count = recurranceManagementPageObj.getRecurranceCount();
			if(entries == count) {
				test.log(LogStatus.PASS,  "Recurrance count - " +count+  " is equal to selected entries - " + entries);
			} else {
				test.log(LogStatus.FAIL, "Recurrance count is not equal to selected entries.");
			}
			takeScreenshot();
			test.log(LogStatus.INFO, "selected entry is : " + recurranceManagementPageObj.getSelectedEntries() + "and page count displayed in pagination is : " + recurranceManagementPageObj.getLastPageCount());
			sleep();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 29)
	public void sortingReccuranceByActivityNameTC() {
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		try {
			recurranceManagementPageObj.clickOnActivityName();
			if (recurranceManagementPageObj.isFieldNameAscendingOrder()) {
				test.log(LogStatus.PASS, "Activity Name sorted in ascending order!!!!");
				takeScreenshot();
				recurranceManagementPageObj.clickOnActivityName();
				if (recurranceManagementPageObj.isFieldNameDescendingOrder()) {
					test.log(LogStatus.PASS, "'Activity Name' sorted in descending order!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to sort 'Activity Name' in descending order!!!!");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to sort 'Activity Name' in ascending order!!!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 30)
	public void viewRecurranceTC() {
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		String status = "Terminated";
		try {
			recurranceManagementPageObj.setSearch(status);
			if(basePage.isElementPresent(recurranceManagementPageObj.recurranceTable, 10)) {
				test.log(LogStatus.INFO, "No reccrances found.");
			} else {
				recurranceManagementPageObj.clickOnView();
				if(recurranceManagementPageObj.isRecurranceModalExist()) {
					test.log(LogStatus.INFO, "'Recurrence Details' popup is opened !!!!");
					takeScreenshot();
					recurranceManagementPageObj.clickOnCancelRecurrance();
					basePage.waitUntilElementInvisible("//div[contains(@class,'addActivityDialogue')]", 30);
				}
			}
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to view Recurrance Management. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 31)
	public void editReccuranceTC() {
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		String status = "Active",
			   content = "Do you want to save changes to future month close templates?";
		try {
			recurranceManagementPageObj.setSearch(status);
			if(basePage.isElementPresent(recurranceManagementPageObj.recurranceTable, 10)) {
				test.log(LogStatus.INFO, "No reccrances found.");
			} else {
				recurranceManagementPageObj.clickOnEdit();
				if(recurranceManagementPageObj.isRecurranceModalExist()) {
					try {
						test.log(LogStatus.INFO, "'Edit Recurrence Details' popup is opened !!!!");
						recurranceManagementPageObj.confirmEditModal(content);
						basePage.waitUntilElementInvisible("//div[contains(@class,'addActivityDialogue')]", 30);
					} catch(Exception e) {
						recurranceManagementPageObj.clickOnCancelRecurrance();
					}
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to edit Recurrance Management. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 32)
	public void endReccuranceTC() {
		recurranceManagementPageObj = new RecurranceManagementPage(driver);
		homePageObj = new HomePage(driver);
		String submenu = "Recurrence Management",
			   status = "Active",
			   activityName = "";
		try {
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			Assert.assertTrue(basePage.isElementPresent(recurranceManagementPageObj.pageTitle, 60));
			test.log(LogStatus.INFO, submenu + " Page opened sucessfully!!");
			sleep();
			recurranceManagementPageObj.setSearch(status);
			if(basePage.isElementPresent(recurranceManagementPageObj.recurranceTable, 30)) {
				test.log(LogStatus.INFO, "No reccrances found.");
			} else {
				activityName = recurranceManagementPageObj.getActivity();
				recurranceManagementPageObj.clickOnEnd();
				driver.switchTo().alert().accept();
				sleep();
				recurranceManagementPageObj.setSearch(activityName);
				if(recurranceManagementPageObj.isActivityEnded(activityName)) {
					test.log(LogStatus.PASS, "Activity Ended sucessfully and status changed to terminated!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to end Activity!!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to end Recurrance Management. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 32)
	public void workloadTC() {
		homePageObj = new HomePage(driver);
		workloadPageObj = new WorkloadPage(driver);
		String submenu = "Workload";
		try {
			homePageObj.moveToControlDock();
			homePageObj.selectControldockSubmennu(submenu);
			Assert.assertTrue(basePage.isElementPresent(workloadPageObj.pageTitle, 60));
			test.log(LogStatus.INFO, submenu + " Page opened sucessfully!!");
			sleep();
			if(workloadPageObj.isWorkloadFilterDisplayed()) {
				test.log(LogStatus.INFO, "workload filter is displayed by default!!");
			}
			
			workloadPageObj.clickOnBtnHeader();
			if(!workloadPageObj.isWorkloadFilterDisplayed()) {
				test.log(LogStatus.PASS, "After click on collapse button workload filter is not displayed!!");
				workloadPageObj.clickOnBtnHeader();
				if(workloadPageObj.isWorkloadFilterDisplayed()) {
					test.log(LogStatus.PASS, "workload filter is displayed!!");
				}
			} else {
				test.log(LogStatus.FAIL, "After click on collapse button workload filter should not displayed!!");
			}
			
			if(workloadPageObj.ischartDisplayed()) {
				test.log(LogStatus.PASS, "workload Chart is displayed!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify workload. " + ExceptionUtils.getStackTrace(e));
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
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", basePage.paginatePanel);
			takeScreenshot();
			if(basePage.nextButton.isEnabled()) {
				basePage.clickOnNextPagination();
				test.log(LogStatus.INFO, "clicked on Next button successfully.");
			} else {
				basePage.clickOnPreviousPagination();
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", basePage.paginatePanel);
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
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", basePage.paginatePanel);
			sleep();
			takeScreenshot();
			
			if(basePage.lastButton.isEnabled()) {
				basePage.clickOnLastPagination();
				test.log(LogStatus.INFO, "clicked on last button successfully.");
			} else {
				basePage.clickOnFirstPagination();
				test.log(LogStatus.INFO, "Last page is already selected so, clicked on first pagination link successfully.");
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", basePage.paginatePanel);
			sleep();
		} else {
			test.log(LogStatus.INFO, "Pagination Not Found.");
		}
	}
}
