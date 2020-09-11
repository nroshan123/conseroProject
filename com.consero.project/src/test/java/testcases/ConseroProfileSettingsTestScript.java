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
import pageclass.AddUserPage;
import pageclass.BasePage;
import pageclass.HomePage;
import pageclass.LoginPage;
import pageclass.MyProfilePage;
import pageclass.UsersListPage;
import utility.DataReader;
import utility.ExtentTestManager;

public class ConseroProfileSettingsTestScript extends BaseTest {

    WebDriver driver;
	
	BasePage basePage = null;
	LoginPage loginPageObj = null;
	HomePage homePageObj = null;
	MyProfilePage myProfilePageObj = null;
	
	String sheetName = "credentials";
	String userEmail = "", password = "";
	String companyName= "test_company", username ="";
	
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
			System.out.println(appUrl);
			List<HashMap<String, String>> TCData = DataReader.getData(sheetName);
			for (int i = 0; i < TCData.size(); i++) {
				if(TCData.get(i).get("role").equals("profileSettings")) {
					userEmail = TCData.get(i).get("email");
					password = TCData.get(i).get("password");
					username = TCData.get(i).get("password");
				}
			}
			loadConfig();
			test.log(LogStatus.INFO, "Loading config file");
			navigate(appUrl);
			test.log(LogStatus.INFO, "Navigated to url " + appUrl);
			loginPageObj = new LoginPage(driver);
			loginPageObj.login(userEmail, password);
			
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
	public void verifyProfileMenuTC() {
		homePageObj = new HomePage(driver);
		myProfilePageObj = new MyProfilePage(driver);
		String title = "My Profile";
		try {
			if(homePageObj.isLoggedInUsernameExist() && homePageObj.getLoggedInUsername().equals(username)) {
				test.log(LogStatus.INFO, "username is displayed in profile section!!");
			}
			
			homePageObj.clickOnProfileBar();
			if(homePageObj.isProfileSettingsExist() && homePageObj.isSignoutExist()) {
				test.log(LogStatus.INFO, "'Profile Settings' and 'Sign Out' is displayed in profile list!!");
			}
			takeScreenshot();
			homePageObj.clickOnProfileSettings();
			Assert.assertTrue(myProfilePageObj.isPageTitleExist(title));
			test.log(LogStatus.INFO, "My Profile' page opened sucessfully!!");
			
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "Unsuccessful to verify profile. " + ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
	}
	
	@Test(priority = 3)
	public void generalInformationTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String[] arr=username.split(" ");
		 String firstName=arr[0],
		        lastName=arr[1],
		        address1 = generateRandomString(5),
		        address2 = generateRandomString(5),
		        filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\files\\userProfile.jpg";

		try {
			Assert.assertTrue(myProfilePageObj.isgeneralInformationSelected());
			if(myProfilePageObj.isUserNameEnabled() && myProfilePageObj.isEmailEnabled()) {
				test.log(LogStatus.FAIL, "'username' and 'email' should disabled in general information section!!");
			} else {
				test.log(LogStatus.PASS, "''username' and 'email' is disabled in general information section!!");
			}
			
			if(myProfilePageObj.getUserName().equals(userEmail)) {
				test.log(LogStatus.INFO, "username displayed is correct!!");
			}
			
			if(myProfilePageObj.getFirstName().equals(firstName) && myProfilePageObj.getLastName().equals(lastName)) {
				test.log(LogStatus.INFO, "firstName and lastName displayed is correct!!");
			}
			
			if(myProfilePageObj.getEmail().equals(userEmail)) {
				test.log(LogStatus.INFO, "Email displayed is correct!!");
			}
			
			myProfilePageObj.setInformationDetails(address1, address2, filePath);
			if(myProfilePageObj.isInformationUpdated(address1, address2)) {
				test.log(LogStatus.PASS, "general information updated sucessfully!!");
			} else {
				test.log(LogStatus.FAIL, "Failed to update general information!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 4)
	public void confirmPasswordValidationTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String profileTitle = "Change Password",
			   newPassword = "Consero" + generateRandomNumber(3) + "$",
			   confirmNewPassword =  "Consero" + generateRandomNumber(4) + "$";
		try {
			myProfilePageObj.clickOnChangePassword();
			Assert.assertTrue(myProfilePageObj.getProfileTitle().equals(profileTitle));
			test.log(LogStatus.INFO, "redirected to " + profileTitle + " sucessfully!!");
			myProfilePageObj.setChangePasswordDetails(password, newPassword, confirmNewPassword);
			if(!newPassword.equals(confirmNewPassword) && myProfilePageObj.isConfirmationErrorExist()) {
				test.log(LogStatus.INFO, "'New Password' and 'Confirm new password' is not same!!!");
				test.log(LogStatus.PASS, "Error message is displayed for password mismatch!!!" );
			} else {
				test.log(LogStatus.FAIL, "Failed to validate password mismatch");
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 5)
	public void changePasswordTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String profileTitle = "Change Password",
			   newPassword = "Consero" + generateRandomNumber(3) + "$",
			   successMessage = "Your password has been changed. Your Profile is updated.";
		try {
			myProfilePageObj.clickOnChangePassword();
			Assert.assertTrue(myProfilePageObj.getProfileTitle().equals(profileTitle));
			test.log(LogStatus.INFO, "redirected to " + profileTitle + " sucessfully!!");
			myProfilePageObj.setChangePasswordDetails(password, newPassword, newPassword);
			if(basePage.checkSuccessMessage(successMessage)) {
				test.log(LogStatus.PASS, "success message recieved and password updated successfully");
				DataReader.setCellData(newPassword, 5, 4);
			} else {
				test.log(LogStatus.FAIL, "Failed to update password");
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test(priority = 6)
	public void currentPasswordValidationTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String profileTitle = "Change Password",
			   newPassword = "Consero" + generateRandomNumber(3) + "$",
			   errorMessage = "";
		try {
			myProfilePageObj.clickOnChangePassword();
			Assert.assertTrue(myProfilePageObj.getProfileTitle().equals(profileTitle));
			test.log(LogStatus.INFO, "redirected to " + profileTitle + " sucessfully!!");
			myProfilePageObj.setChangePasswordDetails(password, newPassword, newPassword);
			if(basePage.checkSuccessMessage(errorMessage)) {
				test.log(LogStatus.PASS, "success message recieved and password updated successfully");
			} else {
				test.log(LogStatus.FAIL, "Failed to validate wrong current password");
			}
			takeScreenshot();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 7)
	public void notificationSetupTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String profileTitle = "Notification Setup",
			   successMessage = "Your Profile is updated.";
		try {
			myProfilePageObj.clickOnNotificationSetup();
			Assert.assertTrue(myProfilePageObj.getProfileTitle().equals(profileTitle));
			test.log(LogStatus.INFO, "redirected to " + profileTitle + " sucessfully!!");
			test.log(LogStatus.INFO, "redirected to " + profileTitle + " sucessfully!!");
			if(myProfilePageObj.isNotificationOptionsExist()) {
				test.log(LogStatus.PASS, "'Activities Email Notification', 'Expenses Email Notification', 'Timesheet Email Notification' , 'Credential Update Alert Notification' and 'Default Module' options exist!!");
			} else {
				test.log(LogStatus.FAIL, "Few notification setup option doesn't exist!!");
			}
			
			if(myProfilePageObj.isBillEmaiExist()) {
				test.log(LogStatus.PASS, "'Bill.Com Approval Email'and 'Bill.Com Payment Email' Notification options exist!!");
			} else {
				test.log(LogStatus.FAIL, "'Bill.Com Approval Email'and 'Bill.Com Payment Email' Notification options doesn't exist!!");
			}
			String oldActivitySetup = myProfilePageObj.getSelectedActivities();
			myProfilePageObj.slectRandomActivities();
			myProfilePageObj.clickOnSave();
			if(basePage.checkSuccessMessage(successMessage)) {
				test.log(LogStatus.PASS, "profile updated successfully");
			} else {
				test.log(LogStatus.FAIL, "Failed to update profile");
			}
			sleep();
			String newActivitySetup = myProfilePageObj.getSelectedActivities();
			test.log(LogStatus.INFO, "previous Activity setup was " + oldActivitySetup + "and is updated to- " +  newActivitySetup);
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 8)
	public void assignedRolesTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String profileTitle = "Assigned Company Roles",
			   successMessage = "Your Profile is updated.";
		try {
			myProfilePageObj.clickOnAssignedRoles();
			Assert.assertTrue(myProfilePageObj.getProfileTitle().equals(profileTitle));
			if(myProfilePageObj.isCompanyRolesTableExist()) {
				test.log(LogStatus.PASS, "'company' and 'roles' displayed successfully");
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 9)
	public void credentialSettingsTC() {
		myProfilePageObj = new MyProfilePage(driver);
		String profileTitle = "Update Application Credentials",
				billDotComUsername = "";
		try {
			myProfilePageObj.clickOnCredentialSettings();
			Assert.assertTrue(myProfilePageObj.getProfileTitle().equals(profileTitle));
			test.log(LogStatus.INFO, "redirected to " + profileTitle + " sucessfully!!");
			if(myProfilePageObj.isBillCredentialPanelDisplayed()) {
				test.log(LogStatus.INFO, "Bill.com credential panel is displayed successfully!!");
				myProfilePageObj.setBillCredentials(username, password);
				if(myProfilePageObj.isBillPasswordErrorMsgExist()) {
					test.log(LogStatus.FAIL, "Failed to verify Bill.com password!!! Displayed error msg - " + myProfilePageObj.getBillPasswordErrorMsg());
				}
			} else {
				test.log(LogStatus.INFO, "Bill.com credential panel is not displayed!!");
			}
			takeScreenshot();
			
			if(myProfilePageObj.isNexoniaCredentialPanelDisplayed()) {
				test.log(LogStatus.INFO, "Nexonia credential panel is displayed successfully!!");
				if(myProfilePageObj.isNexoniaCollapsed()) {
					test.log(LogStatus.INFO, "Nexonia credentials is collapsed!!");
					myProfilePageObj.clickOnNexoniaCollapseButton();
					Assert.assertFalse(myProfilePageObj.isNexoniaCollapsed());
					test.log(LogStatus.INFO, "Nexonia credentials displayed successfully!!");
					myProfilePageObj.setNexoniaCredentials(username, password);
					if(myProfilePageObj.isNexoniaPasswordErrorMsgExist()) {
						test.log(LogStatus.FAIL, "Failed to verify Nexonia password!!! Displayed error msg - " + myProfilePageObj.getNexoniaPasswordErrorMsg());
					}
				}
			} else {
				test.log(LogStatus.INFO, "Nexonia credential panel is not displayed!!");
			}
			takeScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 10)
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
	
