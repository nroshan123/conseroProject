package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.util.Strings;

public class AddUserPage extends BasePage{

	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(),'Create A New User')]")
	public WebElement pageTitle;
	
	@FindBy(id = "UserName")
	WebElement userName;
	
	@FindBy(id = "Password")
	WebElement password;
	
	@FindBy(id = "ConfirmPassword")
	WebElement confirmPassword;
	
	@FindBy(id = "FirstName")
	WebElement firstName;
	
	@FindBy(id = "LastName")
	WebElement lastName;
	
	@FindBy(id = "AddressLineOne")
	WebElement addressLineOne;
	
	@FindBy(id = "IsSuperUser")
	WebElement isSuperUser;
	
	@FindBy(id = "SuperUserErrorDiv")
	WebElement superUserMessage;
	
	@FindBy(xpath = "//a[text()='Back to List']")
	WebElement backToList;
	
	@FindBy(xpath = "//input[@value='Create']")
	WebElement create;
	
	public AddUserPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getUserName() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",userName).toString();
	}
	
	public String getPassword() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",password).toString();
	}
	
	public void setUserName(String name) {
		if(!Strings.isNullOrEmpty(this.getUserName())) {
			userName.clear();
		}
		userName.sendKeys(name);
	}
	
	public void setPassword(String pwd) {
		if(!Strings.isNullOrEmpty(this.getPassword())) {
			password.clear();
		}
		password.sendKeys(pwd);
	}
	
	public void setConfirmPassword(String pwd) {
		confirmPassword.sendKeys(pwd);
	}
	
	
	public void setFirstName(String name) {
		firstName.sendKeys(name);
	}
	
	public void setLastName(String name) {
		lastName.sendKeys(name);
	}
	
	
	public void setAddressLineOne(String address) {
		addressLineOne.sendKeys(address);
	}
	
	public void clickOnIsSuperUser() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", isSuperUser);
	}
	
	public void clickOnCreate() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", create);
	}
	
	public void clickOnBackToList() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", backToList);
	}
	
	public boolean isSuperUserMessageExist(String msg) {
		if(isElementPresent(superUserMessage,30) && superUserMessage.getText().equals(msg)) {
			return true;
		}
		return false;
	}
	
}
