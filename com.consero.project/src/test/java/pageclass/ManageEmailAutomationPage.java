package pageclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ManageEmailAutomationPage extends BasePage {
	WebDriver driver;

	@FindBy(xpath = "//div[@class='pageTitleSection']//div[contains(text(),'Manage Email Automation')]")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//button[text()='Add New Email Template']")
	WebElement addNewEmailTemplate;
	
	@FindBy(xpath = "//div[@id='addNewEmailTemplateModal']/div[@class='modal-dialog']")
	WebElement modalDialog;
	
	@FindBy(id = "newBillsEmailTemplateName")
	WebElement newBillsEmailTemplateName;
	
	@FindBy(xpath = "//div[@id='addNewEmailTemplateModal']//button[text()='Save']")
	WebElement save;
	
	@FindBy(id = "templateListForBills")
	WebElement emailTable;
	
	@FindBy(xpath = "//table[@id='templateListForBills']//tbody//tr//td[1]")
	List<WebElement> emailTemplateNames;
	
	@FindBy(xpath = "//span[contains(text(),'Email Reminder Level')]")
	WebElement emailReminderLevel;
	
	@FindBy(name = "reminderLevelToForBills")
	WebElement reminderLevelToForBills;
	
	@FindBy(name = "templateId")
	WebElement templateId;
	
	@FindBy(xpath = "//a[text()='Add Reminder Level']")
	WebElement addReminderLevel;
	
	public ManageEmailAutomationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnAddNewEmailTemplate() {
		addNewEmailTemplate.click();
	}
	
	public boolean isModalExist() {
		if(isElementPresent(modalDialog,60)) {
			return true;
		}
		return false;
	}
	
	public void setNewBillsEmailTemplateName(String name) {
		newBillsEmailTemplateName.sendKeys(name);
	}
	
	public void clickOnSave() {
		save.click();
	}
	
	public boolean isNewEmailTemplateAdded(String name) {
		if(isElementPresent(emailTable,60)) {
			for(WebElement emailTemplateName: emailTemplateNames) {
				if(emailTemplateName.getText().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clickOnEmailReminderLevel() {
		emailReminderLevel.click();
	}
	
	public void setReminderLevelToForBills(String from) {
		reminderLevelToForBills.sendKeys(from);
	}
	
	public void selectTemplate(String name) {
		Select select = new Select(templateId);
		select.selectByVisibleText(name);
	}
	
	public void clickOnAddReminderLevel() {
		addReminderLevel.click();
	}
}
