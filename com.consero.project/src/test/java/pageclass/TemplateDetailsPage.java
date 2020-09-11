package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TemplateDetailsPage extends BasePage{

	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'Template Details']")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//span[@class='alert-warning']")
	WebElement warning;
	
	@FindBy(xpath = " //span[contains(text(),'Please add the corrected close template')]")
	WebElement correctTemplateMessage;

	@FindBy(xpath = "//span[@class='alert-success']")
	WebElement success;
	
	@FindBy(id = "CompanyName")
	WebElement CompanyName;
	
	@FindBy(id = "TemplateName")
	WebElement templateName;
	
	@FindBy(id = "NumberOfActivities")
	WebElement numberOfActivities;
	
	@FindBy(id = "NumberOfActivitiesWithDependency")
	WebElement numberOfActivitiesWithDependency;
	
	@FindBy(id = "NumberOfActivitiesWithDocumentRequired")
	WebElement numberOfActivitiesWithDocumentRequired;
	
	@FindBy(id = "NumberOfActivitiesWithCheckList")
	WebElement numberOfActivitiesWithCheckList;
	
	@FindBy(id = "NumberOfOwners")
	WebElement numberOfOwners;
	
	@FindBy(id = "NumberOfClientOwners")
	WebElement numberOfClientOwners;
	
	@FindBy(xpath = "//a[contains(text(),'Kickoff Setup')]")
	WebElement kickOffsetup;
	
	@FindBy(xpath = "//a[contains(text(),'Companies')]")
	WebElement companies;
	
	@FindBy(xpath = "//a[contains(text(),'Company Details')]")
	WebElement companyDetails;
	
	public TemplateDetailsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getWarning() {
		return warning.getText();
	}
	
	public boolean isWarningExist() {
		if(isElementPresent(warning, 60)) {
			return true;
		}
		return false;
	}
	
	public boolean isCorrectTemplateMessageExist() {
		return isElementPresent(correctTemplateMessage, 30);
	}
	
	public boolean isTemplateValidated(String msg) {
		if(isElementPresent(success, 60) && success.getText().equals(msg)) {
			return true;
		}
		return false;
	}
	
	public boolean checkTemplateDetailsButton() {
		if(isElementPresent(kickOffsetup, 30) && isElementPresent(companies, 30) && isElementPresent(companyDetails, 30)) {
			return true;
		}
		return false;
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
	
	public boolean isFieldDisabled() {
		if(!this.isCompanyEnabled() && !this.isTemplateEnabled()) {
			return true;
		}
		return false;
	}
	
	public void clickOnCompanyDetails() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", companyDetails);
	}
}
