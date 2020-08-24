package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCloseTemplatePage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'Add A Close Template']")
	public WebElement pageTitle;
	
	@FindBy(id = "CompanyName")
	WebElement companyName;
	
	@FindBy(id = "TemplateName")
	WebElement templateName;
	
	@FindBy(id = "TemplateText")
	WebElement templateText;
	
	@FindBy(xpath = "//input[@value='Add']")
	WebElement add;
	
	@FindBy(xpath = "//a[contains(text(),'Companies')]")
	WebElement companies;
	
	@FindBy(xpath = "//a[contains(text(),'Company Details')]")
	WebElement companyDetails;
	
	public AddCloseTemplatePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	public boolean isCompanyNameEnabled() {
		if(companyName.isEnabled()) {
			return true;
		}
		return false;
	}
	
	public void setTemplateName(String name) {
		templateName.sendKeys(name);
	}
	
	public void setTemplateText(String text) {
		templateText.sendKeys(text);
	}
	
	public boolean checkCloseTemplateButton() {
		if(isElementPresent(add, 30) && isElementPresent(companies, 30) && isElementPresent(companyDetails, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnAdd() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", add);
	}
	
	public void clickOnCompanyDetails() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", companyDetails);
	}
	
	public void setCloseTemplateDetails(String name, String text) {
		try {
			this.setTemplateName(name);
			this.setTemplateText(text);
			this.waitUntilClickable(add, 30);
			this.clickOnAdd();
		} catch(Exception e) {
			this.clickOnCompanyDetails();
		}
		
	}
}
