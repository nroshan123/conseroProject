package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.util.Strings;

public class RecurranceManagementPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(), 'Recurrences')]")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_filter']//input[@type='search']")
	WebElement search;
	
	
	public RecurranceManagementPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getSearch() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",search).toString();
	}
	
	public void setSearch(String value) {
		if(!Strings.isNullOrEmpty(this.getSearch())) {
			search.clear();
		}
		search.sendKeys(value);
	}
}
