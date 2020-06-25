package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

	WebDriver driver;

	@FindBy(id = "username")
	WebElement username;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(id = "showOffWarningPopAfrLgin")
	WebElement login;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setUsername(String name) {
		username.sendKeys(name);
	}
	
	public void setPassword(String pwd) {
		password.sendKeys(pwd);
	}
	
	public void clickOnLogin() {
		login.click();
	}
	
	public void login(String name, String pwd) {
		this.setUsername(name);
		this.setPassword(pwd);
		waitUntilClickable(login, 20);
		this.clickOnLogin();
	}
}
