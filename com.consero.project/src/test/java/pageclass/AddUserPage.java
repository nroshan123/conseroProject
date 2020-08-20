package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	
	public void setUserName(String name) {
		userName.sendKeys(name);
	}
	
	public void setPassword(String pwd) {
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
		isSuperUser.click();
	}
	
	public void clickOnCreate() {
		create.click();
	}
	
	public boolean isSuperUserMessageExist(String msg) {
		if(isElementPresent(superUserMessage,30) && superUserMessage.getText().equals(msg)) {
			return true;
		}
		return false;
	}
	
}
