package pageclass;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.util.Strings;

public class UsersListPage extends BasePage {
	
	WebDriver driver;

    @FindBy(xpath = "//*[@class='page-title' and contains(text(),'Users')]")
	public WebElement pageTitle;
	
	@FindBy(id = "userSearch")
	WebElement userSearch;
	
	@FindBy(id = "btnAllEntities")
	WebElement bothUsers;
	
	@FindBy(id = "btnActiveEntities")
	WebElement activeUsers;
	
	@FindBy(id = "btnInActiveEntities")
	WebElement inactiveUsers;
	
	@FindBy(id = "userTableGrid")
	WebElement userTable;
	
	@FindBy(xpath = "//a[text()='Create A New User']")
	WebElement createNewUser;
	
	@FindBy(xpath = "//div[@id='userTableGrid']//span[text()='No matching records found']")
	WebElement emptyUserTable;
	
	@FindBy(xpath = "//div[@id='userTableGrid']//div[@ref='eBodyViewport']//div[@col-id='UserName']/span/a")
	List<WebElement> usernameinTable;
	
	@FindBy(xpath = "//div[@id='userTableGrid']//div[@ref='eBodyViewport']//div[@col-id='IsNewUser']/span/a")
	WebElement welcomeUser;
	
	@FindBy(xpath = "//div[@id='userTableGrid']//div[@ref='eBodyViewport']//div[@col-id='IsNewUser']/span/a[text()='Send token']")
	WebElement sendToken;
	
	@FindBy(xpath = "//div[@id='userTableGrid']//div[@ref='eBodyViewport']//div[@col-id='UserStatusColumn']/span/a[text()='Deactivate']")
	WebElement deactivate;
	
	@FindBy(xpath = "//div[@id='userTableGrid']//div[@ref='eBodyViewport']//div[@col-id='UserStatusColumn']/span/a[text()='Activate']")
	WebElement activate;
	
	@FindBy(xpath = "//div[contains(@class,'activeDeactiveUserModalContent')]")
	WebElement deactivateModalContent;
	
	@FindBy(id = "activeDeactiveUserModal")
	WebElement activateDeactivateModal;
	
	@FindBy(id = "activeDeactiveUserYesButton")
	WebElement confirmDeactivate;
	
	
	public UsersListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getUserSearchText() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",userSearch).toString();
	}
	
	public void setUserSearch(String value) {
		if(!Strings.isNullOrEmpty(this.getUserSearchText())) {
			userSearch.clear();
		}
		userSearch.sendKeys(value);
	}
	
	public boolean isActiveUserSelected() {
		if(activeUsers.getAttribute("class").contains("active")) {
			return true;
		}
		return false;
	}
	
	public void clickOnInactiveUser() {
		inactiveUsers.click();
	}
	
	public void clickOnBothUsers() {
		bothUsers.click();
	}

	public void clickOnCreateNewUser() {
		createNewUser.click();
	}
	
	public boolean isUserTableExist() {
		if(isElementPresent(userTable,30)) {
			return true;
		}
		return false;
	}
	
	public boolean isUsereExist(String name) {
		if(isElementPresent(emptyUserTable,20)) {
			return false;
		} else {
			for(WebElement username:usernameinTable) {
				if(username.getText().equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clickOnWelcomeUser() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", welcomeUser);
	}
	
	public void clickOnSendToken() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", sendToken);
	}
	
	public String getWelcomeUserText() {
		return welcomeUser.getText();
	}
	
	public void clickOnDeactivate() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", deactivate);
	}
	
	public void clickOnActivate() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", activate);
	}
	
	public boolean isModalExist(String content ) {
		if(isElementPresent(activateDeactivateModal,30) && deactivateModalContent.getText().contains(content)) {
			return true;
		}
		return false;
	}
	
	public void clickOnConfirm() {
		confirmDeactivate.click();
	}
	
	public void clickOnActiveUsers() {
		activeUsers.click();
	}
}
