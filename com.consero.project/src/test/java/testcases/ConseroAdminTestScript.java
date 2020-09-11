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
import pageclass.BasePage;
import pageclass.AddBulkUserPage;
import pageclass.AddCloseTemplatePage;
import pageclass.CompanyDetailsPage;
import pageclass.CompanyListPage;
import pageclass.ActivityListPage;
import pageclass.DeactivateBulkUserPage;
import pageclass.AddCompanyPage;
import pageclass.AddLinkPage;
import pageclass.AddTeamMemberPage;
import pageclass.AddUserPage;
import pageclass.HomePage;
import pageclass.KickOffClosePage;
import pageclass.KickOffsPage;
import pageclass.LoginPage;
import pageclass.ManageDepartmentExpensePage;
import pageclass.ManageEmailAutomationPage;
import pageclass.TemplateDetailsPage;
import pageclass.UsersListPage;
import pageclass.ViewEmailLogsPage;
import utility.DataReader;
import utility.ExtentTestManager;

public class ConseroAdminTestScript extends BaseTest {

	WebDriver driver;
	
	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	AddCompanyPage addCompanyPageObj = null;
	CompanyListPage companyListPageObj = null;
	CompanyDetailsPage companyDetailsPageObj = null;
	AddTeamMemberPage addTeamMemberPageObj = null;
	ViewEmailLogsPage viewEmailLogsPageObj = null;
	AddLinkPage addLinkPageObj = null;
	UsersListPage userListPageObj = null;
	AddUserPage addUserPageObj = null;
	AddBulkUserPage bulkUserPageObj = null;
	DeactivateBulkUserPage deactivateBulkUserPageObj = null;
	AddCloseTemplatePage addCloseTemplatePageObj = null;
	TemplateDetailsPage templateDetailsPageObj = null;
	KickOffClosePage kickOffClosePageObj = null;
	ActivityListPage activityListPageObj = null;
	KickOffsPage kickOffPageObj = null; 
	ManageEmailAutomationPage manageEmailPageObj = null;
	ManageDepartmentExpensePage manageDepartmentExpensePageObj = null;
	
	String sheetName = "credentials";
	String adminUser = "", adminPassword = "", adminUsername = "";
	String companyName= "", username ="", emailTemplateName="";
	
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
		homePageObj = new HomePage(driver);
		try {
			List<HashMap<String, String>> TCData = DataReader.getData(sheetName);
			for (int i = 0; i < TCData.size(); i++) {
				if(TCData.get(i).get("role").equals("admin")) {
					adminUser = TCData.get(i).get("email");
					adminPassword = TCData.get(i).get("password");
					adminUsername =  TCData.get(i).get("username");
				}
			}
			loadConfig();
			test.log(LogStatus.INFO, "Loading config file");
			navigate(appUrl);
			test.log(LogStatus.INFO, "Navigated to url " + appUrl);
			loginPageObj.login(adminUser, adminPassword);
			
			if(homePageObj.isLoggedInUsernameExist() && homePageObj.getLoggedInUsername().equals(adminUsername)) {
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
			Thread.sleep(10000);
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
				test.log(LogStatus.INFO, "selected Inactive users!!!");
				takeScreenshot();
				userListPageObj.clickOnBothUsers();
				test.log(LogStatus.INFO, "selected both active and Inactive users!!!");
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
		username = generateRandomString(5) + "@gmail.com";
		String pwd = "Test123$",
			   confirmpwd = "Test123$",
			   firstname = generateRandomString(5),
			   lastname = generateRandomString(5),
			   address = generateRandomString(5);
		String msg = "This feature is available only for Consero Global, BTQ and Propeller Industries domain users";
			   
		try {
			sleep();
			userListPageObj.clickOnCreateNewUser();
			Thread.sleep(5000);
			if(basePage.isElementPresent(addUserPageObj.pageTitle, 60)) {
				test.log(LogStatus.INFO, "'create new user' page loaded successfully!!");
				try {
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
				} catch (Exception e) {
					addUserPageObj.clickOnBackToList();
				}
				Thread.sleep(8000);
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
	
	@Test(priority = 4)
	public void verifyWelcomeEmailTC() {
		userListPageObj = new UsersListPage(driver);
		String welcomeMessage = "Welcome Mail was send successfully to " + username,
				tokenMessage = "Reset Password Mail was send successfully to " + username;
		try {
			userListPageObj.setUserSearch(username);
			if (userListPageObj.isUsereExist(username)) {
				userListPageObj.clickOnWelcomeUser();
				if (basePage.checkSuccessMessage(welcomeMessage)) {
					test.log(LogStatus.PASS, "welcome email sent successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to sent welcome email!!");
				}
				takeScreenshot();

				if (userListPageObj.getWelcomeUserText().equals("Send token")) {
					test.log(LogStatus.INFO, "'sent token' text displayed successfully after welcome email!!");
					userListPageObj.clickOnSendToken();
					if (basePage.checkSuccessMessage(tokenMessage)) {
						test.log(LogStatus.PASS, "resend email sent successfully!!");
					} else {
						test.log(LogStatus.FAIL, "Failed to sent resend email!!");
					}
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to activate/deactivate user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 5)
	public void activateDeactivateUserTC() {
		userListPageObj = new UsersListPage(driver);
		String deactiveContent = "Are you sure to deactivate", activeContent = "Are you sure to activate";
		try {
			userListPageObj.setUserSearch(username);
			if (userListPageObj.isUsereExist(username)) {
				userListPageObj.clickOnDeactivate();
				if (userListPageObj.isModalExist(deactiveContent)) {
					userListPageObj.clickOnConfirm();
					takeScreenshot();
				}
				sleep();
				userListPageObj.clickOnInactiveUser();
				userListPageObj.setUserSearch(username);
				if (userListPageObj.isUsereExist(username)) {
					test.log(LogStatus.PASS, "user deactivated successfully!!");
					takeScreenshot();
					userListPageObj.clickOnActivate();
					if (userListPageObj.isModalExist(activeContent)) {
						userListPageObj.clickOnConfirm();
						takeScreenshot();
					}
					userListPageObj.clickOnActiveUsers();
					userListPageObj.setUserSearch(username);
					if (userListPageObj.isUsereExist(username)) {
						test.log(LogStatus.PASS, "user activated successfully!!");
					} else {
						test.log(LogStatus.FAIL, "Failed to activate user!!");
					}
					takeScreenshot();
				} else {
					test.log(LogStatus.FAIL, "Failed to deactivate user!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to activate/deactivate user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=6)
	public void addBulkUserTC() {
		homePageObj = new HomePage(driver);
		bulkUserPageObj = new AddBulkUserPage(driver);
		
		String menuName = "Add Users in Bulk",
			   fileName = "C:\\Users\\Admin\\conseroFiles\\userbulk-sample-file.csv";
		try {
			homePageObj.moveToUsers();
			homePageObj.selectUserSubmennu(menuName);
			if(basePage.isElementPresent(userListPageObj.pageTitle, 60)) {
				test.log(LogStatus.INFO, "'Bulk Insert Users' page loaded successfully!!");
				bulkUserPageObj.setBulkUser(fileName);
				bulkUserPageObj.clickOnImportBulkUser();
				if(bulkUserPageObj.isModalExist()) {
					bulkUserPageObj.clickOnConfirmModal();
				}
				
				if(bulkUserPageObj.isSuccessfullUserTableExist()) {
					test.log(LogStatus.PASS, "bulk user added successfully!!");
				}
				
				if(bulkUserPageObj.isDuplicateUserTableExist()) {
					test.log(LogStatus.PASS, "bulk user added but few of them is already exist");
				} else {
					test.log(LogStatus.FAIL, "No duplicate user found while adding bulk users!!");
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add bulk user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=7)
	public void deactivateBulkUserTC() {
		deactivateBulkUserPageObj = new DeactivateBulkUserPage(driver);
		homePageObj = new HomePage(driver);
		
		String menuName = "Deactivate Users in Bulk",
			   filePath = "C:\\Users\\Admin\\conseroFiles\\deactivate-userbulk-sample-file.csv";
		try {
			homePageObj.moveToUsers();
			homePageObj.selectUserSubmennu(menuName);
			if(basePage.isElementPresent(userListPageObj.pageTitle, 60)) {
				test.log(LogStatus.INFO, "'Bulk Insert Users' page loaded successfully!!");
				deactivateBulkUserPageObj.setDeactivateBulkUser(filePath);
				deactivateBulkUserPageObj.clickOnImportDeactivateUser();
				if(deactivateBulkUserPageObj.isModalExist()) {
					deactivateBulkUserPageObj.clickOnConfirmModal();
				}
				
				if(deactivateBulkUserPageObj.isErrorTableExist()) {
					test.log(LogStatus.FAIL, "Failed to deactivate bulk user!! " + deactivateBulkUserPageObj.getMessage());
					
				} else {
					test.log(LogStatus.PASS, "deactivated bulk user successfully!!");
				}
				takeScreenshot();
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to deactivate bulk user. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=8)
	public void verifyEnableAndDisableClientsTC() {
		homePageObj = new HomePage(driver);
		companyListPageObj = new CompanyListPage(driver);
		try {
			homePageObj.clickOnCompanies();
			Thread.sleep(10000);
			if(basePage.isElementPresent(companyListPageObj.companyListTable, 60)) {
				test.log(LogStatus.INFO, "Company List loaded successfully. ");
			} 
			
			sleep();
			if(companyListPageObj.isActiveClientSelected()) {
				test.log(LogStatus.INFO, "Active client option is displaying by default!!");
			}
			
			companyListPageObj.clickOnInactiveClients();
			test.log(LogStatus.INFO, "Displaying Inactive clients!!");
			takeScreenshot();
			
			companyListPageObj.clickOnAllClients();
			test.log(LogStatus.INFO, "Displaying both active & Inactive clients!!");
			takeScreenshot();
			
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify enable and disable clients. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=9)
	public void createCompanyTC() {
		addCompanyPageObj = new AddCompanyPage(driver);
		companyListPageObj = new CompanyListPage(driver);
		
		companyName = "automation_test_company_" + generateRandomString(3);
		String address1 = generateRandomString(3);
		String city = generateRandomString(3);
		String state = generateRandomString(4);
		String zipcode = generateRandomNumber(6);
		try {
			companyListPageObj.clickOnCreateCompany();
			addCompanyPageObj.setCompanyDetails(companyName, address1, city, state, zipcode, adminUser, adminPassword );
			basePage.waitElementVisible(companyListPageObj.companyListTable, 60);
			sleep();
			companyListPageObj.setCompanySearch(companyName);
			sleep();
			if(companyListPageObj.isCompanyExist(companyName)) {
				test.log(LogStatus.PASS, "Company created successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to create company!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to create Company. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
    @Test(priority = 10)
	public void editCompanyTC() {
		addCompanyPageObj = new AddCompanyPage(driver);
		companyListPageObj = new CompanyListPage(driver);
		String address1 = generateRandomString(5);
		String address2 = generateRandomString(4);
		try {
			companyListPageObj.setCompanySearch(companyName);
			sleep();
			if (companyListPageObj.isCompanyExist(companyName)) {
				companyListPageObj.clickOnEditCompany();
				addCompanyPageObj.setUpdateCompanyDetails(address1, address2);
				basePage.waitElementVisible(companyListPageObj.companyListTable, 60);
				companyListPageObj.setCompanySearch(companyName);
				sleep();
				Assert.assertTrue(companyListPageObj.isCompanyExist(companyName));
				if (companyListPageObj.isAddressUpdated(address1, address2)) {
					test.log(LogStatus.PASS, "Company updated successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to update company!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to edit Company. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	} 
	
	@Test(priority = 11)
	public void verifyCompanyDetails() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		companyListPageObj = new CompanyListPage(driver);
		try {
			companyListPageObj.setCompanySearch(companyName);
			pressEnter();
			sleep();
			if (companyListPageObj.isCompanyExist(companyName)) {
				companyListPageObj.clickOnDetails();
				if (basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 40)) {
					test.log(LogStatus.PASS, "Company details page opened successfully!!");
					if (companyDetailsPageObj.isEditAndBackToListExist()) {
						test.log(LogStatus.INFO, "Edit and Back to list button is visible!!");
					} else {
						test.log(LogStatus.INFO, "Edit and Back to list button is not visible!!");
					}
					takeScreenshot();
					if (companyDetailsPageObj.isTeamMemberTemplateButtonExist()) {
						test.log(LogStatus.INFO, "Add menmber and view Email logs button is visible!!");
					} else {
						test.log(LogStatus.INFO, "Add menmber and view Email logs button is not visible!!");
					}
					takeScreenshot();
					if (basePage.isElementPresent(companyDetailsPageObj.addLink, 40)) {
						test.log(LogStatus.INFO, "Add Link button is visible!!");
					} else {
						test.log(LogStatus.INFO, "Add Link button is not visible!!");
					}
					takeScreenshot();
					if (companyDetailsPageObj.isActivityTemplateButtonExist()) {
						test.log(LogStatus.INFO, "Add close template and kickoffs button is visible!!");
					} else {
						test.log(LogStatus.INFO, "Add close template and kickoffs button is not visible!!");
					}
					takeScreenshot();
				} else {
					test.log(LogStatus.FAIL, "Failed to open company details page!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=12)
	public void verifyButtonOnTeamMemberPageTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addTeamMemberPageObj = new AddTeamMemberPage(driver);
		try {
			companyDetailsPageObj.clickOnAddTeamMember();
			if(basePage.isElementPresent(addTeamMemberPageObj.addTeamMember, 60)) {
				test.log(LogStatus.PASS, "Add team member page opened successfully!!");
				if(addTeamMemberPageObj.checkTeamMemberButton()) {
					test.log(LogStatus.INFO, "Add, companies, and 'company details' button is visible!!");
				} else {
					test.log(LogStatus.INFO, "Add, companies, and 'company details' button is not visible!!");
				}
				
				if(!addTeamMemberPageObj.isAddButtonEnabled()) {
					test.log(LogStatus.INFO, "Add button is disabled!!");
				} else {
					test.log(LogStatus.INFO, "Add button is enabled. It should be disabled when member details are not set!!");
				}
				
				addTeamMemberPageObj.clickOnCompanies();
				if(basePage.isElementPresent(companyListPageObj.companyListTable, 60)) {
					test.log(LogStatus.PASS, "Redirected to Company List page successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to Redirect to Company List page!!");
				}
				sleep();
				companyListPageObj.setCompanySearch(companyName);
				sleep();
				Assert.assertTrue(companyListPageObj.isCompanyExist(companyName));
				companyListPageObj.clickOnDetails();
				Thread.sleep(6000);
				companyDetailsPageObj.clickOnAddTeamMember();
				sleep();
				addTeamMemberPageObj.clickOnCompanyDetails();
				if(basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 60)) {
					test.log(LogStatus.PASS, "Redirected to Company details page successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to Redirect to Company details page!!");
				}
				takeScreenshot();
			} else {
				test.log(LogStatus.FAIL, "Failed to open add team member page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=13)
	public void addTeamMemberTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addTeamMemberPageObj = new AddTeamMemberPage(driver);
		String role = "Consero - Manager",
				   emailId = "neha@thinkbridge.in",
				   title = "Senior Manager",
				   content = "Are you sure, you want to add client user ?";
		try {
			companyDetailsPageObj.clickOnAddTeamMember();
			if(basePage.isElementPresent(addTeamMemberPageObj.addTeamMember, 60)) {
				addTeamMemberPageObj.setTeamMemberDetails(emailId, role, title, content);
				companyDetailsPageObj.clickOnshowTeamMembers();
				if(companyDetailsPageObj.isTeamMemberExist(emailId)) {
					test.log(LogStatus.PASS, "Team member added successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to add team member!!");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to open add team member page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add team member. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=14)
	public void removeTeamMemberTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		String emailId = "neha@thinkbridge.in";
		try {
			companyDetailsPageObj.clickOnshowTeamMembers();
			if(basePage.isElementPresent(companyDetailsPageObj.teamMemberTable, 60)) {
				if(companyDetailsPageObj.isTeamMemberExist(emailId)) {
					test.log(LogStatus.PASS, "Team member added successfully!!");
					companyDetailsPageObj.clickOnRemoveTeamMember(emailId);
					companyDetailsPageObj.clickOnshowTeamMembers();
					if(!companyDetailsPageObj.isTeamMemberExist(emailId)) {
						test.log(LogStatus.PASS, "Team member removed successfully!!");
					} else {
						test.log(LogStatus.FAIL, "Failed to remove team member!!");
					}
				} 
			} else {
				test.log(LogStatus.FAIL, "Team member table is not present!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to remove team member. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=15)
	public void viewEmailLogsTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		viewEmailLogsPageObj = new ViewEmailLogsPage(driver);
		try {
			companyDetailsPageObj.clickOnViewEmailLogs();
			
			if(basePage.isElementPresent(viewEmailLogsPageObj.pageHeader, 60)) {
				test.log(LogStatus.PASS, "Email notification page opened successfully!!");
				
				if(basePage.isElementPresent(viewEmailLogsPageObj.search, 60)) {
					test.log(LogStatus.INFO, "search option is available in email notification page!!");
				}
				takeScreenshot();
				
				if(basePage.isElementPresent(viewEmailLogsPageObj.emptyTable, 60)) {
					test.log(LogStatus.INFO, "Email notification Logs table is empty!!");
				}
				takeScreenshot();
				
				if(basePage.isElementPresent(viewEmailLogsPageObj.entries, 30)) {
					test.log(LogStatus.INFO, "Email notification 'entries' option are visible!!");
				}
				takeScreenshot();
				
				if(basePage.pagination.size()>0) {
					test.log(LogStatus.INFO, "Pagination Found.");
					basePage.clickOnPagination();
				} else {
					test.log(LogStatus.INFO, "Pagination Not Found.");
				}
				takeScreenshot();
				
				viewEmailLogsPageObj.clickOnCompanyDetails();
				if(basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 60)) {
					test.log(LogStatus.INFO, "successfully Redirected to companyDeatils page.");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to open Email notification member page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to view email logs. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=16)
	public void addLinkTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addLinkPageObj = new AddLinkPage(driver);
		String linkName = "",
			   linkItself = "",
			   linkType = "";
		String financialStatmentUrl = prop.getProperty("FinancialURL");
		try {
			companyDetailsPageObj.clickOnAddLink();
			if(basePage.isElementPresent(addLinkPageObj.pageHeader, 60)) {
				test.log(LogStatus.PASS, "Add link page opened successfully!!");
				addLinkPageObj.setFinancialStatement(financialStatmentUrl);
				addLinkPageObj.clickOnVerify();
				sleep();
				test.log(LogStatus.INFO, "verification message is: " + addLinkPageObj.getValidateFolderMessage());
				takeScreenshot();
				addLinkPageObj.clickOnAddLinkButton();
				int size = addLinkPageObj.getReportType();
				for(int i=1; i<=size-1; i++) {
					linkName = generateRandomString(5);
					linkItself = generateRandomString(5);
					if(i==1) {
						linkType = "Other Report";
						addLinkPageObj.clickOnreport(linkType);
						addLinkPageObj.setLinkDetails(linkName, linkItself);
					} else if(i==2) {
						linkType = "Flash Report";
						addLinkPageObj.clickOnreport(linkType);
						addLinkPageObj.setLinkDetails(linkName, linkItself);
					} else if(i==3) {
						linkType = "Quick Links";
						addLinkPageObj.clickOnreport(linkType);
						addLinkPageObj.setLinkDetails(linkName, linkItself);
					}
					addLinkPageObj.clickOnAddLinkButton();
					sleep();
					addLinkPageObj.clickOnCompanyDetails();
					if(!companyDetailsPageObj.isLinkTableEmpty()) {
						if(companyDetailsPageObj.isLinkAdded(linkType, linkName, linkItself)) {
							test.log(LogStatus.PASS, "added link :" + linkType + "successfully");
						} else {
							test.log(LogStatus.FAIL, "Failed to add link.");
						}
					} else {
						test.log(LogStatus.FAIL, "Failed to add custom link!! linkTable is Empty!!!!!!!");
					}
					takeScreenshot();
					companyDetailsPageObj.clickOnAddLink();
				}
				
				viewEmailLogsPageObj.clickOnCompanyDetails();
				if(basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 60)) {
					test.log(LogStatus.INFO, "successfully Redirected to companyDeatils page.");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to open add link page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=17)
	public void editLinkTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addLinkPageObj = new AddLinkPage(driver);
		String name = generateRandomString(5),
			   link = generateRandomString(5);
		try {
			companyDetailsPageObj.clickOnEditLink();
			companyDetailsPageObj.setEditLinkDetails(name, link);
			if(companyDetailsPageObj.isLinkUpdated(name, link)) {
				test.log(LogStatus.PASS, "Link updated successfully.");
			} else {
				test.log(LogStatus.FAIL, "Failed to update link.");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to edit link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=18)
	public void deleteLinkTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addLinkPageObj = new AddLinkPage(driver);
		try {
			String linkName = companyDetailsPageObj.getUpdatedLinkName();
			companyDetailsPageObj.clickOnDeleteLink();
			
			if(companyDetailsPageObj.isLinkDeleted(linkName)) {
				test.log(LogStatus.PASS, "link deleted successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to delete link!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=19)
	public void addCloseTemplateTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addCloseTemplatePageObj = new AddCloseTemplatePage(driver);
		String templateName = generateRandomString(5),
			   templateText = prop.getProperty("templateText");
		System.out.println(templateText);
		try {
			companyDetailsPageObj.clickOnAddCloseTemplate();
			if(basePage.isElementPresent(addCloseTemplatePageObj.pageTitle, 60)) {
				test.log(LogStatus.PASS, "'Add close template' page opened successfully!!");
				
				if(!addCloseTemplatePageObj.isCompanyNameEnabled()) {
					test.log(LogStatus.PASS, "'company Name' is disabled!!");
				} else {
					test.log(LogStatus.FAIL, "'company Name' is enabled!! It should be disabled!!!");
				}
				takeScreenshot();
				
				if(addCloseTemplatePageObj.checkCloseTemplateButton()) {
					test.log(LogStatus.PASS, "'Add', 'companies' and 'Company Details' button present on page!!");
				}
				sleep();
				addCloseTemplatePageObj.setCloseTemplateDetails(templateName, templateText);
				if(companyDetailsPageObj.isTemplateAdded(templateName)) {
					test.log(LogStatus.PASS, "Close Template added sucessfully!!");
				} else {
					test.log(LogStatus.PASS, "Failed to add close template!!");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to open 'Add close template' page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=20)
	public void validateCloseTemplateTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		templateDetailsPageObj = new TemplateDetailsPage(driver);
		
		String successMessage = "Template Validated Succesfully. You can Kick off activites now!";
		try {
			companyDetailsPageObj.clickOnValidate();
			if(basePage.isElementPresent(templateDetailsPageObj.pageTitle, 60)) {
				test.log(LogStatus.PASS, "'Template Details' page opened successfully!!");
				if(templateDetailsPageObj.isWarningExist()) {
					test.log(LogStatus.INFO, "template not validated sucessfully " + templateDetailsPageObj.getWarning());
				} 
				
				if(templateDetailsPageObj.isCorrectTemplateMessageExist()) {
					templateDetailsPageObj.clickOnCompanyDetails();
					if(basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 60)) {
						test.log(LogStatus.INFO, "successfully Redirected to companyDeatils page.");
					}
				}
				
				if(templateDetailsPageObj.isTemplateValidated(successMessage)) {
					test.log(LogStatus.PASS, "Template validated successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to validated Template!!");
				}
				
				if(templateDetailsPageObj.isFieldDisabled()) {
					test.log(LogStatus.INFO, "All fields are disabled!!!!");
				}
				
				if(templateDetailsPageObj.checkTemplateDetailsButton()) {
					test.log(LogStatus.INFO, "All buttons are present!!!!");
				}
				takeScreenshot();
				
				templateDetailsPageObj.clickOnCompanyDetails();
				if(basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 60)) {
					test.log(LogStatus.INFO, "successfully Redirected to companyDeatils page.");
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to open 'Template Details' page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	//@Test(priority=21)
	public void templateDetailsTC() {
		try {
			
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=22)
	public void kickOffSetupTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		kickOffClosePageObj = new KickOffClosePage(driver);
		activityListPageObj = new ActivityListPage(driver);
		
		String message = "Kicked off template successfully.";
		
		try {
			companyDetailsPageObj.clickOnKickoffSetup();
			if(basePage.isElementPresent(kickOffClosePageObj.pageTitle, 60)) {
				test.log(LogStatus.PASS, "'Kickoff Close' page opened successfully!!");
				if(!kickOffClosePageObj.isCompanyEnabled() && !kickOffClosePageObj.isTemplateEnabled()) {
					test.log(LogStatus.INFO, "'Company Name' and 'Template Name' fields are disabled!!!!");
				}
				
				if(kickOffClosePageObj.checkKickOffCloseButton()) {
					test.log(LogStatus.INFO, "All buttons are present!!!!");
				}
				takeScreenshot();
				sleep();
				kickOffClosePageObj.clickOnKickOff();
				if(basePage.checkSuccessMessage(message)) {
					test.log(LogStatus.INFO, "get success message!!!!");
				}
				
				if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
					test.log(LogStatus.PASS, "Redirected On Control Dock Page successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to redirect On Control Dock Page!!!!");
				}
				sleep();
			} else {
				test.log(LogStatus.FAIL, "Failed to open 'Kickoff Close' page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=23)
	public void kickOffsTC() {
		homePageObj = new HomePage(driver);
		companyListPageObj = new CompanyListPage(driver);
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		kickOffPageObj = new KickOffsPage(driver);
		
		String title = "Kickoffs For " + companyName;
		try {
			sleep();
			homePageObj.clickOnCompanies();
			Thread.sleep(10000);
			if(basePage.isElementPresent(companyListPageObj.companyListTable, 60)) {
				test.log(LogStatus.INFO, "Company List loaded successfully. ");
			} 
			companyListPageObj.setCompanySearch(companyName);
			sleep();
			if (companyListPageObj.isCompanyExist(companyName)) {
				companyListPageObj.clickOnDetails();
				if (basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 40)) {
					test.log(LogStatus.PASS, "Company details page opened successfully!!");
					companyDetailsPageObj.clickOnKickOffs();
					if(kickOffPageObj.isPageTitleExist(title)) {
						test.log(LogStatus.PASS, "'kickoffs' page opened successfully!!");
						if(kickOffPageObj.checkKickOffsButton()) {
							test.log(LogStatus.INFO, "All buttons are present!!!!");
						}
					}
				}
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 24)
	public void closeKickOffTC() {
		kickOffPageObj = new KickOffsPage(driver);
		String title = "Kickoffs For " + companyName,
			   status ="Closed";
		try {
			if(kickOffPageObj.isPageTitleExist(title)) {
				test.log(LogStatus.PASS, "'kickoffs' page opened successfully!!");
				kickOffPageObj.clickOnClose();
				driver.switchTo().alert().accept();
				sleep();
				if(kickOffPageObj.getStatus().equals(status)) {
					test.log(LogStatus.PASS, "kickoff closed successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to close kickoff!!");
				}
				takeScreenshot();
				
				Assert.assertTrue(kickOffPageObj.isReopenButtonExist());
				test.log(LogStatus.INFO, "'Reopen' button present after close the kickoff!!");
				kickOffPageObj.clickOnReopen();
				sleep();
				if(kickOffPageObj.getStatus().equals("Open")) {
					test.log(LogStatus.PASS, "kickoff reopened successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to reopened kickoff!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to close and reopen kickoff. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 25)
	public void bizDaysTC() {
		kickOffPageObj = new KickOffsPage(driver);
		activityListPageObj = new ActivityListPage(driver);
		try {
			kickOffPageObj.clickOnBizdays();
			if(basePage.isElementPresent(kickOffPageObj.BizDaysTitle, 60)) {
				test.log(LogStatus.PASS, "'Biz Days' page opened successfully!!");
				kickOffPageObj.setNumberOfDaysToMove("1");
				if(kickOffPageObj.checkBizDaysButton()) {
					test.log(LogStatus.INFO, "All buttons are present!!!!");
				}
				
				kickOffPageObj.clickOnCheckDates();
				if(basePage.isElementPresent(kickOffPageObj.warning, 30)) {
					test.log(LogStatus.INFO, "check date warning exist!!!! " + kickOffPageObj.getWarning() );
				}
				kickOffPageObj.clickOnMoveDates();
				
				if(basePage.isElementPresent(activityListPageObj.pageTitle, 60)) {
					test.log(LogStatus.PASS, "Redirected On Control Dock Page successfully!!!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to redirect On Control Dock Page!!!!");
				}
				sleep();
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify Biz days. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	//@Test(priority = 26)
	public void deleteKickOffTC() {
		kickOffPageObj = new KickOffsPage(driver);
		try {
			kickOffPageObj.clickOnDelete();
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to delete kickoff. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 27)
	public void manageEmailAutomationTC() {
		homePageObj = new HomePage(driver);
		companyListPageObj = new CompanyListPage(driver);
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		emailTemplateName = generateRandomString(5);
		try {
			sleep();
			homePageObj.clickOnCompanies();
			Thread.sleep(10000);
			if(basePage.isElementPresent(companyListPageObj.companyListTable, 60)) {
				test.log(LogStatus.INFO, "Company List loaded successfully. ");
			} 
			companyListPageObj.setCompanySearch(companyName);
			pressEnter();
			sleep();
			if (companyListPageObj.isCompanyExist(companyName)) {
				companyListPageObj.clickOnDetails();
				if (basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 40)) {
					test.log(LogStatus.PASS, "Company details page opened successfully!!");
					companyDetailsPageObj.clickOnManageEmailAutomation();
					if (basePage.isElementPresent(manageEmailPageObj.pageTitle, 60)) {
						test.log(LogStatus.PASS, "'Manage Email Automation' page opened successfully!!");
						sleep();
						manageEmailPageObj.clickOnAddNewEmailTemplate();
						Assert.assertTrue(manageEmailPageObj.isModalExist());
						manageEmailPageObj.setNewBillsEmailTemplateName(emailTemplateName);
						manageEmailPageObj.clickOnSave();
						sleep();
						if (manageEmailPageObj.isNewEmailTemplateAdded(emailTemplateName)) {
							test.log(LogStatus.PASS, "New Template added successfully!!!!");
						} else {
							test.log(LogStatus.FAIL, "Failed to add new template!!!!");
						}
					}
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify manage email automation. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
		
	@Test(priority = 28)
	public void emailReminderLevelTC() {
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		String from = generateNumberExceptZero(1),
			   successMessage = "Email Reminder Level Configured successfully";
		int from1 = Integer.parseInt(from)+1;
		String reminderFrom = String.valueOf(from1);
		try {
			sleep();
			Assert.assertTrue(manageEmailPageObj.isEmailReminderLevelEnabled());
			manageEmailPageObj.clickOnEmailReminderLevel();
			manageEmailPageObj.setReminderLevelDetails(from, emailTemplateName);
			if(basePage.checkSuccessMessage(successMessage)) {
				test.log(LogStatus.PASS, "reminder level added successfully!!!!");
			}
			takeScreenshot();
			sleep();
			manageEmailPageObj.clickOnAddReminderLevel();
			if(!manageEmailPageObj.isReminderFromDisabled()) {
				test.log(LogStatus.INFO, "reminder level from is disabled!!!!");
			}
			String from2 = manageEmailPageObj.getReminderFrom();
			if(reminderFrom.equals(from2)) {
				test.log(LogStatus.INFO, "reminder level from is 1 greater that reminder from 1!!!!");
			}
			takeScreenshot();
			int reminderFrom1 = Integer.parseInt(from2) + 2;
			String reminderFrom2 = String.valueOf(reminderFrom1);
			manageEmailPageObj.setAddReminderLevel(reminderFrom2, emailTemplateName);
			if(basePage.checkSuccessMessage(successMessage)) {
				test.log(LogStatus.PASS, "reminder level added successfully!!!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add email reminder level. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 29)
	public void deleteEmailReminderLevelTC() {
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		try {
			Assert.assertTrue(manageEmailPageObj.isDeleteExist());
			manageEmailPageObj.clickOnDelete();
			if(!manageEmailPageObj.isReinderLevel2Exist()) {
				test.log(LogStatus.INFO, "email reminder level is deleted sucessfully!!!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify email reminder. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	@Test(priority = 30)
	public void emailFrequencyTC() {
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		String days= "2";
		String successMessage = "Email Frequency Configured Successfully";
		try {
			sleep();
			Assert.assertTrue(manageEmailPageObj.isEmailReminderLevelEnabled());
			manageEmailPageObj.clickOnEmailFrequency();
			sleep();
			manageEmailPageObj.clickOnWeekDays();
			manageEmailPageObj.setWeekDaysDetails(days);
			if(basePage.checkSuccessMessage(successMessage)) {
				test.log(LogStatus.PASS, "email frequency added successfully for week days!!!!");
			}
			takeScreenshot();
			sleep();
			manageEmailPageObj.clickOnBizDays();
			manageEmailPageObj.setBizDaysDetails(days);
			if(basePage.checkSuccessMessage(successMessage)) {
				test.log(LogStatus.PASS, "email frequency added successfully for biz days!!!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify email frequency. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}

	@Test(priority = 31)
	public void emailAuditLogTC() {
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		String date = getCurrentDateInFormatM_d_YYYY(),
				typeLevel = manageEmailPageObj.getTypeLevel(),
				billPendingLevel = manageEmailPageObj.getBillsPendingLevel(),
				bizDaysLevel = manageEmailPageObj.getBillsBizDayLevel(),
				typeValue = manageEmailPageObj.getCheckedFrequency(),
				billPendingInQueue = manageEmailPageObj.getbizdaysCutOffDays(),
				bizDays = manageEmailPageObj.getSelectedBizDays();
		System.out.println(bizDays);
		try {
			manageEmailPageObj.clickOnEmailAutomationAuditLog();
			Assert.assertTrue(manageEmailPageObj.isAuditModalExist());
			sleep();
			if(basePage.isElementPresent(manageEmailPageObj.emailAuditLogTable, 30)) {
				test.log(LogStatus.INFO, "Audit table is Empty!!!!");
			} else {
				if(manageEmailPageObj.checkAuditLog(date, typeLevel, typeValue)) {
					test.log(LogStatus.PASS, typeLevel + "field name successfully updated in audit logs!!!!");
				} else {
					test.log(LogStatus.FAIL, typeLevel + "field name not updated in audit logs!!!!");
				}
				
				if(manageEmailPageObj.checkAuditLog(date, billPendingLevel, billPendingInQueue)) {
					test.log(LogStatus.PASS, billPendingLevel + "field name successfully updated in audit logs!!!!");
				} else {
					test.log(LogStatus.FAIL, billPendingLevel + "field name not updated in audit logs!!!!");
				}
				
				if(manageEmailPageObj.checkAuditLog(date, bizDaysLevel, bizDays)) {
					test.log(LogStatus.PASS, bizDaysLevel + "field name successfully updated in audit logs!!!!");
				} else {
					test.log(LogStatus.FAIL, bizDaysLevel + "field name not updated in audit logs!!!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify email audit log. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 32)
	public void sortEmailAuditLogTC() {
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		try {
			Assert.assertTrue(manageEmailPageObj.isAuditModalExist());
			if(basePage.isElementPresent(manageEmailPageObj.emailAuditLogTable, 30)) {
				test.log(LogStatus.INFO, "Audit table is Empty!!!!");
			} else {
				manageEmailPageObj.clickOnFieldName();
				if(manageEmailPageObj.fieldName.getAttribute("class").equals("sorting_asc") && manageEmailPageObj.isFieldNameAscendingOrder()) {
					test.log(LogStatus.PASS, "field name sorted in ascending order!!!!");
					takeScreenshot();
					manageEmailPageObj.clickOnFieldName();
					if(manageEmailPageObj.fieldName.getAttribute("class").equals("sorting_desc") && manageEmailPageObj.isFieldNameDescendingOrder()) {
						test.log(LogStatus.PASS, "'field name' sorted in descending order!!!!");
					} else {
						test.log(LogStatus.FAIL, "Failed to sort 'field name' in descending order!!!!");
					}
				} else {
					test.log(LogStatus.FAIL, "Failed to sort 'field name' in ascending order!!!!");
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to sort email audit log by file name. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 33)
	public void searchEmailAuditLogTC() {
		manageEmailPageObj = new ManageEmailAutomationPage(driver);
		String date = getCurrentDateInFormatM_d_YYYY(),
		       level = "SaveETemplateForBills";
		try {
			Assert.assertTrue(manageEmailPageObj.isAuditModalExist());
			if(basePage.isElementPresent(manageEmailPageObj.emailAuditLogTable, 30)) {
				test.log(LogStatus.INFO, "Audit table is Empty!!!!");
			} else {
				manageEmailPageObj.setEmailAuditLogSearch(emailTemplateName);
				if(manageEmailPageObj.checkAuditLog(date, level, emailTemplateName)) {
					test.log(LogStatus.PASS, level + "field name successfully updated in audit logs!!!!");
				} else {
					test.log(LogStatus.FAIL, level + "field name not updated in audit logs!!!!");
				}
			}
			takeScreenshot();
			manageEmailPageObj.clickOnCloseAuditLogModal();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to search email audit log. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}


	@Test(priority = 34)
	public void manageDepartmentExpenseTC() {
		homePageObj = new HomePage(driver);
		companyListPageObj = new CompanyListPage(driver);
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		manageDepartmentExpensePageObj = new ManageDepartmentExpensePage(driver);
		String message = "Data updated successfully";
		String username = "Neha Roshan";
		try {
			homePageObj.clickOnCompanies();
			Thread.sleep(10000);
			if(basePage.isElementPresent(companyListPageObj.companyListTable, 60)) {
				test.log(LogStatus.INFO, "Company List loaded successfully. ");
			} 
			companyListPageObj.setCompanySearch(companyName);
			sleep();
			if (companyListPageObj.isCompanyExist(companyName)) {
				companyListPageObj.clickOnDetails();
				if (basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 40)) {
					companyDetailsPageObj.clickOnManageDepartmentExpense();
					if(basePage.isElementPresent(manageDepartmentExpensePageObj.pageTitle, 40)) {
						sleep();
						manageDepartmentExpensePageObj.setDepartmentDetails();
						if(basePage.checkSuccessMessage(message)) {
							test.log(LogStatus.INFO, "success message recieved!!!!");
						}
						if(manageDepartmentExpensePageObj.isDepartmentAdded()) {
							test.log(LogStatus.PASS, "Department added successfully in department view!!!!");
						} else {
							test.log(LogStatus.FAIL, "Failed to add department in department view!!!!");
						}
						takeScreenshot();
						
						manageDepartmentExpensePageObj.clickOnUserView();
						sleep();
						manageDepartmentExpensePageObj.clickOnUserDropdown();
						manageDepartmentExpensePageObj.setSearch(username);
						sleep();
						manageDepartmentExpensePageObj.selectUserOption(username);
						manageDepartmentExpensePageObj.clickOnSave();
						if(manageDepartmentExpensePageObj.getUsername().equals(username)) {
							test.log(LogStatus.PASS, "user added successfully in user view!!!!");
						} else {
							test.log(LogStatus.FAIL, "Failed to add user in user view!!!!");
						}
						takeScreenshot();
						if(manageDepartmentExpensePageObj.isDisableDepartment()) {
							test.log(LogStatus.INFO, "All department toggle button is disabled!!!!");
							manageDepartmentExpensePageObj.clickOnDepartmentToggle();
							manageDepartmentExpensePageObj.clickOnSave();
							test.log(LogStatus.INFO, "Enabled All department toggle button!!!!");
						}
					}
				}
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to manageDepartmentExpense. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
		
	@Test(priority = 35)
	public void departmentExpenseAuditLogTC() {
		manageDepartmentExpensePageObj = new ManageDepartmentExpensePage(driver);
		String date = getCurrentDateInFormatM_d_YYYY(),
			   username ="neha@thinkbridge.in";
		try {
			Assert.assertTrue(basePage.isElementPresent(manageDepartmentExpensePageObj.pageTitle, 40));
			manageDepartmentExpensePageObj.clickOnDepartmentAuditLog();
			if(manageDepartmentExpensePageObj.isAuditModalExist()) {
				if(basePage.isElementPresent(manageDepartmentExpensePageObj.departmentAuditLogTable, 30)) {
					test.log(LogStatus.INFO, "Audit table is Empty!!!!");
				} else {
					if(manageDepartmentExpensePageObj.checkDepartmentAuditLog(date, username)) {
						test.log(LogStatus.PASS, "Audit table updated successfully!!!!");
					}
				}
			}
			takeScreenshot();
			manageDepartmentExpensePageObj.clickOnCloseAuditLogModal();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to view department Expense Audit log. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 36)
	public void signOut() {
		homePageObj = new HomePage(driver);
		loginPageObj = new LoginPage(driver);
		try {
			homePageObj.clickOnProfileBar();
			if(homePageObj.isprofileDropdownExist()) {
				homePageObj.clickOnSignout();
			}
			if(basePage.isElementPresent(loginPageObj.loginDiv, 60)) {
				test.log(LogStatus.PASS, "logged out successfully!!!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to logged out!!!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to signout. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
}
