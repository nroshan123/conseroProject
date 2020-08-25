package pageclass;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//img[@class='company_logo']")
	public WebElement companyLogo;
	
	@FindBy(xpath = "//div[@id='sideNavList']//ul//li[@id='users']")
	WebElement users;
	
	@FindBy(xpath = "//div[@id='sideNavList']//ul//li[@id='Companies']")
	WebElement companies;
	
	@FindBy(xpath = "//li[@id='users']//ul[contains(@class,'sub-menu')]//li//a")
	List<WebElement> userSubmenu;
	
	@FindBy(xpath = "//li[@id='Companies']//ul[contains(@class,'sub-menu')]//li//a")
	List<WebElement> companiesSubmenu;
	
	@FindBy(id = "header_profile_bar")
	WebElement profileBar;
	
	@FindBy(xpath = "//ul[contains(@class,'header-profile-dropdown-menu')]//li//span[contains(text(),'Sign Out')]")
	WebElement signout;
	
	@FindBy(xpath = "//ul[contains(@class,'header-profile-dropdown-menu')]")
	WebElement profileDropdown;
	
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
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
	
	public void clickOnCompanies() {
		companies.click();
	}
	
	public void clickOnUsers() {
		users.click();
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
	
}
