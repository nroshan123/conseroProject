package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewEmailLogsPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'Email Notification Logs Details']")
	public WebElement pageHeader;

	@FindBy(id = "EmailNotificationLogsTable_filter")
	public WebElement search;
	
	@FindBy(xpath = "//table[@id='EmailNotificationLogsTable']//tbody//tr//td[@class='dataTables_empty']")
	public WebElement emptyTable;
	
	@FindBy(xpath = "//a[text()='Companies']")
	public WebElement companies;
	
	@FindBy(xpath = "//a[text()='Company Details']")
	public WebElement companyDetails;
	
	@FindBy(name = "EmailNotificationLogsTable_length")
	public WebElement entries;
	
	public ViewEmailLogsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	 
	 public void clickOnCompanyDetails() {
		 companyDetails.click();
	 }
	 
}
