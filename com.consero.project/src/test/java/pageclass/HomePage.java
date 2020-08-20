package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//img[@class='company_logo']")
	public WebElement companyLogo;
	
	@FindBy(xpath = "//div[@id='sideNavList']//ul//li[@id='users']")
	WebElement users;
	
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnUsers() {
		users.click();
	}
}
