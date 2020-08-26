package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ActivityDetailsPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'ControlDock']")
	public WebElement pageTitle;
	
	public ActivityDetailsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
