package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

	WebDriver driver;
	
	@FindBy(xpath = "//div[@class='login-box-content']")
	public WebElement loginDiv;
	
    @FindBy(id = "username")
	WebElement username;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(id = "showOffWarningPopAfrLgin")
	WebElement login;
	

	/*@FindBy(xpath = "//div[contains(@class,'login-container')]")
	public WebElement loginDiv;
	
	@FindBy(xpath = "//input[@placeholder='Email ID']")
	WebElement username;
	
	@FindBy(xpath = "//input[@placeholder='Password']")
	WebElement password;
	
	@FindBy(xpath = "//button[contains(@class,'loginbtn')]")
	WebElement login;*/
	
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
