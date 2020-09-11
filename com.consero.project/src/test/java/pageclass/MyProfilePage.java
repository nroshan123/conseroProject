package pageclass;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyProfilePage extends BasePage {
	WebDriver driver;

	@FindBy(xpath = "//h5[@class='page-title']")
	WebElement pageTitle;
	
	@FindBy(xpath = "//ul[contains(@class,'nav-tabs')]//li[5]")
	WebElement generalInformation;
	
	@FindBy(xpath = "//ul[contains(@class,'nav-tabs')]//strong[contains(text(),'Change Password')]")
	WebElement changePassword;
	
	@FindBy(xpath = "//ul[contains(@class,'nav-tabs')]//strong[contains(text(),'Notification Setup')]")
	WebElement notificationSetup;
	
	@FindBy(xpath = "//ul[contains(@class,'nav-tabs')]//strong[contains(text(),'Assigned Roles')]")
	WebElement assignedRoles;
	
	@FindBy(xpath = "//ul[contains(@class,'nav-tabs')]//strong[contains(text(),'Credential Settings')]")
	WebElement credentialSettings;
	
	@FindBy(xpath = "//input[@id='UserName' and @type='text']")
	WebElement userName;
	
	@FindBy(id = "FirstName")
	WebElement firstName;
	
	@FindBy(id = "LastName")
	WebElement lastName;
	
	@FindBy(xpath = "//input[@id='Email' and @type='text']")
	WebElement email;
	
	@FindBy(id = "editUserDeatils")
	WebElement save;
	
	@FindBy(id = "AddressLineOne")
	WebElement addressOne;
	
	@FindBy(id = "AddressLineOne")
	WebElement addressTwo;
	
	@FindBy(id = "UserPhoto")
	WebElement userPhoto;
	
	@FindBy(id = "profileTab")
	WebElement profileTitle;
	
	//change password
	
	@FindBy(id = "ChangePasswordData_OldPassword")
	WebElement currentPassword;
	
	@FindBy(id = "ChangePasswordData_NewPassword")
	WebElement newPassword;
	
	@FindBy(id = "ChangePasswordData_ConfirmPassword")
	WebElement confirmNewPassword;
	
	@FindBy(xpath = "//span[text()='The new password and confirmation password do not match.']")
	WebElement confirmationError;
	
	//notification setup
	
	@FindBy(xpath = "//div[contains(text(),'Activities Email Notification')]")
	WebElement activitesEmail;
	
	@FindBy(xpath = "//div[contains(text(),'Bill.Com Approval Email Notification')]")
	WebElement billApprovalEmail;
	
	@FindBy(xpath = "//div[contains(text(),'Bill.Com Payment Email Notification')]")
	WebElement billPaymentEmail;
	
	@FindBy(xpath = "//div[contains(text(),'Expenses Email Notification')]")
	WebElement expencesEmail;
	
	@FindBy(xpath = "//div[contains(text(),'Timesheet Email Notification')]")
	WebElement timesheetEmail;
	
	@FindBy(xpath = "//div[contains(text(),'Credential Update Alert Notification')]")
	WebElement credentialUpdate;
	
	@FindBy(xpath = "//div[contains(text(),'Default Module')]")
	WebElement defaultModule;
	
	@FindBy(id = "ActivityEmailNotification")
	List<WebElement> activityEmailOptions;
	
	//Assigned roles
	
	@FindBy(xpath = "//table[contains(@class,'dataTable')]//tbody")
	WebElement companyRolesTable;
	
	//credential settings
	
	@FindBy(xpath = "//h4[contains(text(),'Bill.Com Credentials')]")
	WebElement billCredentialPanel;
	
	@FindBy(xpath = "//h4[contains(text(),'Nexonia Credentials')]")
	WebElement nexoniaCredentialPanel;
	
	@FindBy(id = "UserNameBillDotCom")
	WebElement billUserName;
	
	@FindBy(id = "UserNameBillDotCom")
	WebElement billPassword;
	
	@FindBy(id = "verifyBillDotcomPassword")
	WebElement verifyBillDotcomPassword;
	
	@FindBy(id = "verifyBillDotcomPasswordMsg")
	WebElement billDotcomPasswordMsg;
	
	@FindBy(id = "collapseCredential")
	WebElement collapseBillDotCom;
	
	@FindBy(id = "UserNameBillDotCom")
	WebElement nexoniaUserName;
	
	@FindBy(id = "UserNameBillDotCom")
	WebElement nexoniaPassword;
	
	@FindBy(id = "verifyNexoniaPassword")
	WebElement verifyNexoniaPassword;
	
	@FindBy(id = "collapseNexonia")
	WebElement collapseNexonia;
	
	@FindBy(xpath = "//h4[contains(text(),'Nexonia Credentials')]//following-sibling::a[contains(@class,'accordion-toggle')]")
	WebElement nexoniaCollapseButton;
	
	@FindBy(id = "verifyNexoniaPasswordMsg")
	WebElement verifyNexoniaPasswordMsg;
	
	public MyProfilePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getPageTitle() {
		return pageTitle.getText().trim();
	}
	
	public boolean isPageTitleExist(String title) {
		if(isElementPresent(pageTitle, 30) && this.getPageTitle().equals(title)) {
			return true;
		}
		return false;
	}
	
	public boolean isgeneralInformationSelected() {
		if(generalInformation.getAttribute("class").equals("active")) {
			return true;
		}
		return false;
	}
	
	public void clickOnChangePassword() {
		changePassword.click();
	}
	
	public void clickOnNotificationSetup() {
		notificationSetup.click();
	}
	
	public void clickOnAssignedRoles() {
		assignedRoles.click();
	}
	
	public void clickOnCredentialSettings() {
		credentialSettings.click();
	}
	
	public boolean isUserNameEnabled() {
		return userName.isEnabled();
	}
	
	public String getUserName() {
		return userName.getAttribute("value");
	}
	
	public boolean isgGeneralInformationSelected() {
		return generalInformation.getAttribute("class").equals("active");
	}
	
	public String getFirstName() {
		return firstName.getAttribute("value");
	}
	
	public String getLastName() {
		return lastName.getAttribute("value");
	}
	
	public boolean isEmailEnabled() {
		return email.isEnabled();
	}
	
	public String getEmail() {
		return email.getAttribute("value");
	}
	
	public void clickOnSave() {
		save.click();
	}
	
	public void setAddressOne(String address1) {
		addressOne.sendKeys(address1);
	}
	
	public void setAddressTwo(String address2) {
		addressTwo.sendKeys(address2);
	}
	
	public String getAddress1() {
		return addressOne.getAttribute("value");
	}
	
	public String getAddress2() {
		return addressTwo.getAttribute("value");
	}	
	
	public void setInformationDetails(String address1, String address2, String fileName) {
		this.setuserPhoto(fileName);
		this.setAddressOne(address1);
		this.setAddressTwo(address2);
	}
	
	public void setuserPhoto(String fileName) {
		userPhoto.sendKeys(fileName);
	}
	
	public boolean isInformationUpdated(String address1, String address2) {
		if(this.getAddress1().equals(address1) && this.getAddress2().equals(address2)) {
			return true;
		}
		return false;
	}
	
	public String getProfileTitle() {
		return profileTitle.getText();
	}
	
	//change password
	
	public void setCurrentPassword(String password) {
		currentPassword.sendKeys(password);
	}
	
	public void setNewPassword(String password) {
		newPassword.sendKeys(password);
	}
	
	public void setConfirmNewPassword(String password) {
		confirmNewPassword.sendKeys(password);
	}
	
	public boolean isConfirmationErrorExist() {
		return isElementPresent(confirmationError, 30);
	}
	
	public void setChangePasswordDetails(String pwd, String newPwd, String confirmNewPwd) {
		this.setCurrentPassword(pwd);
		this.setNewPassword(newPwd);
		this.setConfirmNewPassword(confirmNewPwd);
		this.clickOnSave();
	}
	
	//notification  setup
	
	public boolean isActivitesEmailExist() {
		return isElementPresent(activitesEmail, 30);
	}
	
	public boolean isExpencesEmailExist() {
		return isElementPresent(expencesEmail, 30);
	}
	
	public boolean isTimesheetEmailExist() {
		return isElementPresent(timesheetEmail, 30);
	}
	
	public boolean isCredentialUpdateExist() {
		return isElementPresent(credentialUpdate, 30);
	}
	
	public boolean isDefaultModuleExist() {
		return isElementPresent(credentialUpdate, 30);
	}
	
	public boolean isBillEmaiExist() {
		if(isElementPresent(billPaymentEmail, 30) && isElementPresent(billApprovalEmail, 30)) {
			return true;
		}
		return false;
	}
	
	public boolean isNotificationOptionsExist() {
		if(this.isActivitesEmailExist() && this.isExpencesEmailExist() && this.isTimesheetEmailExist() && this.isCredentialUpdateExist() && this.isDefaultModuleExist()) {
			return true;
		}
		return false;
	}
	
	public void slectRandomActivities() {
		int size = activityEmailOptions.size();
		activityEmailOptions.get(random.nextInt(size)).click();
	}
	
	public String getSelectedActivities() {
		String activity = "";
		for (WebElement activityEmailOption : activityEmailOptions) {
			if (activityEmailOption.isSelected()) {
				activity = activityEmailOption.getAttribute("value");
			}
		}
		return activity;
	}
	
	//Assigned Roles 
	
	public boolean isCompanyRolesTableExist() {
		return isElementPresent(companyRolesTable, 30);
	}
	
	//credentail settings
	
	public boolean isBillCredentialPanelDisplayed() {
		return billCredentialPanel.isDisplayed();
	}
	
	public boolean isNexoniaCredentialPanelDisplayed() {
		return nexoniaCredentialPanel.isDisplayed();
	}
	
	public void setBillUserName(String username) {
		billUserName.sendKeys(username);
	}
	
	public void setBillPassword(String password) {
		billPassword.sendKeys(password);
	}
	
	public void clickOnVerifyBillDotcomPassword() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", verifyBillDotcomPassword);
	}
	
	public String getBillPasswordErrorMsg() {
		return billDotcomPasswordMsg.getText();
	}
	
	public boolean isBillPasswordErrorMsgExist() {
		return isElementPresent(billDotcomPasswordMsg, 30);
	}
	
	public void setBillCredentials(String username, String password) {
		this.setBillUserName(username);
		this.setBillPassword(password);
		this.clickOnVerifyBillDotcomPassword();
	}
	
	public void setNexoniaUserName(String username) {
		nexoniaUserName.sendKeys(username);
	}
	
	public void setNexoniaPassword(String password) {
		nexoniaPassword.sendKeys(password);
	}
	
	public void clickOVerifyNexoniaPassword() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", verifyNexoniaPassword);
	}

	public void setNexoniaCredentials(String username, String password) {
		this.setNexoniaUserName(username);
		this.setNexoniaPassword(password);
		this.clickOVerifyNexoniaPassword();
	}
	
	public boolean isNexoniaCollapsed() {
		if(nexoniaCollapseButton.getAttribute("class").contains("collapsed")) {
			return true;
		}
		return false;
	}
	
	public void clickOnNexoniaCollapseButton() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", nexoniaCollapseButton);
	}
	
	public String getNexoniaPasswordErrorMsg() {
		return verifyNexoniaPasswordMsg.getText();
	}
	
	public boolean isNexoniaPasswordErrorMsgExist() {
		return isElementPresent(verifyNexoniaPasswordMsg, 30);
	}
}
