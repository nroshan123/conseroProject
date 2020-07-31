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
import pageclass.CompanyDetailsPage;
import pageclass.CompanyListPage;
import pageclass.AddCompanyPage;
import pageclass.AddLinkPage;
import pageclass.AddTeamMemberPage;
import pageclass.HomePage;
import pageclass.LoginPage;
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
	
	String sheetName = "credentials";
	String adminUser = "", adminPassword = "";
	String companyName= "automation_test_company_DAn";
	
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
	
	@Test(priority = 2)
	public void checkAccountsPaginationTC() {
		companyListPageObj = new CompanyListPage(driver);
		try {
			Thread.sleep(10000);
			if(basePage.isElementPresent(companyListPageObj.companyListTable, 60)) {
				test.log(LogStatus.INFO, "Company List loaded successfully. ");
			} 
			
			if(basePage.pagination.size()>0) {
				test.log(LogStatus.INFO, "Pagination Found.");
				basePage.clickOnPagination();
			} else {
				test.log(LogStatus.INFO, "Pagination Not Found.");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to check pagination. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void verifyCompanyPreviousAndNextPaginationTC() {
		try {
			if(basePage.pagination.size()>0) {
				test.log(LogStatus.INFO, "Pagination Found.");
				if(basePage.previousButton.isEnabled()) {
					basePage.clickOnPreviousPagination();
					test.log(LogStatus.INFO, "clicked on previous button successfully.");
				} else {
					basePage.clickOnNextPagination();
					test.log(LogStatus.INFO, "Previous page is already selected so, clicked on Next pagination link successfully.");
				}
				takeScreenshot();
				if(basePage.nextButton.isEnabled()) {
					basePage.clickOnNextPagination();
					test.log(LogStatus.INFO, "clicked on Next button successfully.");
				} else {
					basePage.clickOnPreviousPagination();
					test.log(LogStatus.INFO, "Next page is already selected so, clicked on Previous pagination link successfully.");
				}
			} else {
				test.log(LogStatus.INFO, "Pagination Not Found.");
			}
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify previous and next pagination in company List. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void verifyCompanyFirstAndLastPaginationTC() {
		try {
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
			takeScreenshot();
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify first and last pagination in company List. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=5)
	public void verifyCompanyEntriesTC() {
		companyListPageObj = new CompanyListPage(driver);
		int entries = 0;
		String selectedEntries = "";
		try {
			selectedEntries = companyListPageObj.getSelectedEntries();
			String pageCount = companyListPageObj.getLastPageCount();
			test.log(LogStatus.INFO, "selected entry is : " + selectedEntries + "and page count displayed in pagination is : " + pageCount);
			companyListPageObj.selectCompanyEntries();
			sleep();
			selectedEntries = companyListPageObj.getSelectedEntries();
			try {
				entries = Integer.valueOf(selectedEntries);
			} catch(NumberFormatException nfe) {
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
			int count = companyListPageObj.getCompanyCount();
			if(entries == count) {
				test.log(LogStatus.PASS, "Company count is equal to selected entries.");
			} else {
				test.log(LogStatus.FAIL, "Company count is not equal to selected entries.");
			}
			takeScreenshot();
			test.log(LogStatus.INFO, "selected entry is : " + selectedEntries + "and page count displayed in pagination is : " + companyListPageObj.getLastPageCount());
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify company Entries. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	//@Test(priority=6)
	public void verifyEnableAndDisableClientsTC() {
		companyListPageObj = new CompanyListPage(driver);
		try {
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
	
	//@Test(priority=7)
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
			companyListPageObj.setCompanySearch(companyName);
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
	
	@Test(priority=8)
	public void editCompanyTC() {
		addCompanyPageObj = new AddCompanyPage(driver);
		companyListPageObj = new CompanyListPage(driver);
		String address1 = generateRandomString(5);
		String address2 = generateRandomString(4);
		try {
			companyListPageObj.setCompanySearch(companyName);
			sleep();
			companyListPageObj.clickOnEditCompany();
			addCompanyPageObj.setUpdateCompanyDetails(address1, address2);
			basePage.waitElementVisible(companyListPageObj.companyListTable, 60);
			companyListPageObj.setCompanySearch(companyName);
			sleep();
			Assert.assertTrue(companyListPageObj.isCompanyExist(companyName));
			if(companyListPageObj.isAddressUpdated(address1, address2)) {
				test.log(LogStatus.PASS, "Company updated successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to update company!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to edit Company. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=9)
	public void verifyCompanyDetails() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		companyListPageObj = new CompanyListPage(driver);
		try {
			companyListPageObj.clickOnDetails();
			if(basePage.isElementPresent(companyDetailsPageObj.companyDetailsHeader, 40)) {
				test.log(LogStatus.PASS, "Company details page opened successfully!!");
				if(companyDetailsPageObj.isEditAndBackToListExist()) {
					test.log(LogStatus.INFO, "Edit and Back to list button is visible!!");
				} else {
					test.log(LogStatus.INFO, "Edit and Back to list button is not visible!!");
				}
				takeScreenshot();
				if(companyDetailsPageObj.isTeamMemberTemplateButtonExist()) {
					test.log(LogStatus.INFO, "Add menmber and view Email logs button is visible!!");
				} else {
					test.log(LogStatus.INFO, "Add menmber and view Email logs button is not visible!!");
				}
				takeScreenshot();
				if(basePage.isElementPresent(companyDetailsPageObj.addLink, 40)) {
					test.log(LogStatus.INFO, "Add Link button is visible!!");
				} else {
					test.log(LogStatus.INFO, "Add Link button is not visible!!");
				}
				takeScreenshot();
				if(companyDetailsPageObj.isActivityTemplateButtonExist()) {
					test.log(LogStatus.INFO, "Add close template and kickoffs button is visible!!");
				} else {
					test.log(LogStatus.INFO, "Add close template and kickoffs button is not visible!!");
				}
				takeScreenshot();
			} else {
				test.log(LogStatus.FAIL, "Failed to open company details page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=10)
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
	
	@Test(priority=11)
	public void addTeamMemberTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addTeamMemberPageObj = new AddTeamMemberPage(driver);
		String role = "Consero - Manager",
				   emailId = "neha@thinkbridge.in",
				   title = "Senior Manager";
		try {
			companyDetailsPageObj.clickOnAddTeamMember();
			if(basePage.isElementPresent(addTeamMemberPageObj.addTeamMember, 60)) {
				addTeamMemberPageObj.setTeamMemberDetails(emailId, role, title);
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
	
	@Test(priority=12)
	public void removeTeamMemberTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		String emailId = "neha@thinkbridge.in";
		try {
			companyDetailsPageObj.clickOnshowTeamMembers();
			if(basePage.isElementPresent(companyDetailsPageObj.teamMemberTable, 60)) {
				companyDetailsPageObj.clickOnRemoveTeamMember(emailId);
				companyDetailsPageObj.clickOnshowTeamMembers();
				if(!companyDetailsPageObj.isTeamMemberExist(emailId)) {
					test.log(LogStatus.PASS, "Team member removed successfully!!");
				} else {
					test.log(LogStatus.FAIL, "Failed to remove team member!!");
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
	
	@Test(priority=13)
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
			} else {
				test.log(LogStatus.FAIL, "Failed to open Email notification member page!!");
			}
			takeScreenshot();
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to view email logs. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority=14)
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
					companyDetailsPageObj.clickOnAddLink();
				}
			} else {
				test.log(LogStatus.FAIL, "Failed to open add link page!!");
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	//@Test(priority=15)
	public void editLinkTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addLinkPageObj = new AddLinkPage(driver);
		try {
			companyDetailsPageObj.clickOnAddLink();
			if(basePage.isElementPresent(addLinkPageObj.pageHeader, 60)) {
				test.log(LogStatus.PASS, "Add link page opened successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to open add link page!!");
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	//@Test(priority=16)
	public void deleteLinkTC() {
		companyDetailsPageObj = new CompanyDetailsPage(driver);
		addLinkPageObj = new AddLinkPage(driver);
		try {
			companyDetailsPageObj.clickOnAddLink();
			if(basePage.isElementPresent(addLinkPageObj.pageHeader, 60)) {
				test.log(LogStatus.PASS, "Add link page opened successfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to open add link page!!");
			}
		} catch(Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to add link. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
}
