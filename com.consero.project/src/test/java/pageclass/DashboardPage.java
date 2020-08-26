package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//h3[@class='cmsTitle']")
	WebElement pageTitle;

	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getPageTitle() {
		System.out.println(pageTitle.getText());
		return pageTitle.getText();
	}
}
