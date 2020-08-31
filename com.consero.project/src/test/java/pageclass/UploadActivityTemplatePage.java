package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UploadActivityTemplatePage extends BasePage {
	
	WebDriver driver;
	
	@FindBy(id = "TemplateFile")
	WebElement templateName;

	@FindBy(xpath = "//*[@class='page-title' and contains(text() , 'Upload Recurrence Activity Template')]")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//input[@value='ODM']")
	WebElement odm;
	
	@FindBy(xpath = "//input[@value='Weekly']")
	WebElement weekly;
	
	@FindBy(id = "TemplateFile")
	WebElement templateFile;
	
	@FindBy(id = "btnSubmit")
	WebElement validateTemplate;
	
	@FindBy(xpath = "//div[contains(@class,'alert-danger')]")
	WebElement errorDiv;
	
	@FindBy(xpath = "//div[contains(@class,'alert-danger')]//span[1]")
	WebElement errorMessage;
	
	public UploadActivityTemplatePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setTemplateName(String name) {
		templateName.sendKeys(name);
	}
	
	public boolean isOdmselected() {
		return odm.isSelected();
	}
	
	public void clickOnWeekly() {
		weekly.click();
	}
	
	public void setTemplateFile(String file) {
		templateFile.sendKeys(file);
	}
	
	public void clickOnValidateTemplate() {
		validateTemplate.click();
	}
	
	public void setTemplateDetails(String name, String file ) {
		this.setTemplateName(name);
		this.setTemplateFile(file);
	}
	
	public String getErrorMessage() {
		return errorMessage.getText();
	}
	
	public boolean isErrorDivExist() {
		return isElementPresent(errorDiv,40);
	}
}
