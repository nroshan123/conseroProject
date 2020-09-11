package pageclass;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//img[@class='company_logo']")
	public WebElement companyLogo;
	
	@FindBy(id = "SelectedCompany_chosen")
	WebElement entityDropdown;

	@FindBy(xpath = "//ul[@class='chosen-results']//li")
	List<WebElement> entityOption;
	
	@FindBy(id = "changeCompDashSideBar")
	WebElement go;
	
	@FindBy(xpath = "//div[@id='sideNavList']//ul//li[@id='users']")
	WebElement users;
	
	@FindBy(xpath = "//li[@id='header_profile_bar']//span[@class='username']")
	WebElement loggedInUsername;
	
	@FindBy(xpath = "//div[@id='sideNavList']//ul//li[@id='Companies']")
	WebElement companies;
	
	@FindBy(id = "cmsDashboard")
	WebElement dashboard;
	
	@FindBy(id = "Activities")
	WebElement controlDock;
	
	@FindBy(xpath = "//li[@id='users']//ul[contains(@class,'sub-menu')]//li//a")
	List<WebElement> userSubmenu;
	
	@FindBy(xpath = "//li[@id='Companies']//ul[contains(@class,'sub-menu')]//li//a")
	List<WebElement> companiesSubmenu;
	
	@FindBy(xpath = "//li[@id='Activities']//ul[contains(@class,'sub-menu')]//li//a")
	List<WebElement> controldockSubmenu;
	
	@FindBy(xpath = "//li[@id='Activities']//ul[contains(@class,'sub-menu')]//li//a[text()='Add Activity']")
	WebElement addActivity;
	
	@FindBy(xpath = "//li[@id='Activities']//ul[contains(@class,'sub-menu')]//li//a[text()='Upload Recurring Activity Template']")
	WebElement recurringActivityTemplate;
	
	@FindBy(id = "header_profile_bar")
	WebElement profileBar;
	
	@FindBy(xpath = "//ul[contains(@class,'header-profile-dropdown-menu')]//li//span[contains(text(),'Sign Out')]")
	WebElement signout;
	
	@FindBy(xpath = "//ul[contains(@class,'header-profile-dropdown-menu')]//li//span[contains(text(),'Profile Settings')]")
	WebElement profileSettings;
	
	@FindBy(xpath = "//ul[contains(@class,'header-profile-dropdown-menu')]")
	WebElement profileDropdown;
	
	@FindBy(xpath = "//ul[contains(@class,'page-sidebar-menu')]//li//a[@data-tooltip-text='ControlDock™']")
	WebElement controlDockInSimpl;
	
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnEntityDropdown() {
		entityDropdown.click();
	}
	
	public void selectEntity(String value) {
		for(WebElement entity:entityOption) {
			if(entity.getText().equals(value)) {
				entity.click();
				break;
			}
		}
	}
	
	public void clickOnGo() {
		go.click();
	}
	
	public boolean isDashboardSelected() {
		return dashboard.getAttribute("class").equals("active");
	}
	
	public void moveToUsers() {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(users).build().perform();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveToCompanies() {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(companies).build().perform();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveToControlDock() {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(controlDock).build().perform();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clickOnCompanies() {
		companies.click();
	}
	
	public void clickOnUsers() {
		users.click();
	}
	
	public void clickOnControlDock() {
		controlDock.click();
	}
	
	public void selectUserSubmennu(String name) {
		for(WebElement submenu:userSubmenu) {
			if(submenu.getText().equals(name)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", submenu);
				break;
			}
		}
	}
	
	public void selectCompaniesSubmennu(String name) {
		for(WebElement submenu:userSubmenu) {
			if(submenu.getText().equals(name)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", submenu);
				break;
			}
		}
	}
	
	public boolean isprofileDropdownExist() {
		if(isElementPresent(profileDropdown, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnProfileBar() {
		profileBar.click();
	}
	
	public void clickOnSignout() {
		signout.click();
	}
	
	public void selectControldockSubmennu(String name) {
		for(WebElement submenu:controldockSubmenu) {
			System.out.println(submenu.getText());
			System.out.println(name);
			if(submenu.getText().equals(name)) {
				System.out.println("equal");
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", submenu);
				break;
			}
		}
	}
	
	public void clickOnAddActivity() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addActivity);
	}
	
	public void clickOnControlDockInSimpl() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", controlDockInSimpl);
	}
	
	public void clickOnRecurringActivityTemplate() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", recurringActivityTemplate);
	}
	
	public boolean isLoggedInUsernameExist() {
		return isElementPresent(loggedInUsername, 60);
	}
	
	public String getLoggedInUsername() {
		return loggedInUsername.getText();
	}
	
	public boolean isProfileSettingsExist() {
		return isElementPresent(profileSettings,30);
	}
	
	public boolean isSignoutExist() {
		return isElementPresent(signout,30);
	}
	
	public void clickOnProfileSettings() {
		profileSettings.click();
	}
	
}
