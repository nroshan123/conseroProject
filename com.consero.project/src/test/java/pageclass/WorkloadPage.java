package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WorkloadPage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text()= 'Workload']")
	public WebElement pageTitle;
	
	@FindBy(id = "btnactivityheader")
	WebElement btnHeader;
	
	@FindBy(id = "workloadheadertable")
	WebElement workloadFilter;
	
	@FindBy(xpath = "//div[contains(@class,'highcharts-container')]")
	WebElement chart;
	
	public WorkloadPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnBtnHeader() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", btnHeader);
	}
	
	public boolean isWorkloadFilterDisplayed() {
		return workloadFilter.isDisplayed();
	}
	
	public boolean ischartDisplayed() {
		return isElementPresent(chart, 30);
	}
	
	
}
