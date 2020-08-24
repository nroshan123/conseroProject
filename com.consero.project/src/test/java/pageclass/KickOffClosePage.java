package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class KickOffClosePage extends BasePage {
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'Kickoff Close']")
	public WebElement pageTitle;
	
	@FindBy(id = "CompanyName")
	WebElement CompanyName;
	
	@FindBy(id = "TemplateName")
	WebElement templateName;
	
	@FindBy(id = "btnKickOff")
	WebElement kickOff;
	
	@FindBy(xpath = "//a[contains(text(),'Companies')]")
	WebElement companies;
	
	@FindBy(xpath = "//a[contains(text(),'Company Details')]")
	WebElement companyDetails;
	
	public KickOffClosePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isCompanyEnabled() {
		if(CompanyName.isEnabled()) {
			return true;
		}
		return false;
	}
	
	public boolean isTemplateEnabled() {
		if(templateName.isEnabled()) {
			return true;
		}
		return false;
	}
	
	public boolean checkKickOffCloseButton() {
		if(isElementPresent(kickOff, 30) && isElementPresent(companies, 30) && isElementPresent(companyDetails, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnKickOff() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", kickOff);
	}
}
