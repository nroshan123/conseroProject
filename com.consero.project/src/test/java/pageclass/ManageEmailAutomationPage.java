package pageclass;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
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
	
	@FindBy(id = "reminderLevelBillsTable_wrapper")
	WebElement emailReminderTable;
	
	@FindBy(name = "reminderLevelToForBills")
	WebElement reminderLevelToForBills;
	
	@FindBy(id = "templateId")
	WebElement templateId;
	
	@FindBy(xpath = "//a[text()='Add Reminder Level']")
	WebElement addReminderLevel;
	
	@FindBy(name = "Save Email Reminder Level")
	WebElement saveReminderLevel;
	
	//email frequency
	
	
	@FindBy(xpath = "//div[contains(@class,'billsEmailFrequency')]")
	WebElement emailFrequency;
	
	@FindBy(xpath = "//span[contains(text(),'Biz Days')]")
	WebElement bizDays;
	
	@FindBy(xpath = "//span[contains(text(),'Week Days')]")
	WebElement weekDays;
	
	@FindBy(name = "weekdaysCutOffDays")
	WebElement weekdaysCutOffDays;
	
	@FindBy(xpath = "//select[contains(@class,'billsSequenceWeekSelection')]")
	WebElement weekselection;
	
	@FindBy(xpath = "//select[contains(@class,'billsSequenceWeekDaySelection')]")
	WebElement weekDayselection;
	
	@FindBy(xpath = "//a[text()='Add Sequence']")
	WebElement addSequence;
	
	@FindBy(name = "Save Bills Email Frequency")
	WebElement saveEmailFrequency;
	
	@FindBy(name = "bizdaysCutOffDays")
	WebElement bizdaysCutOffDays;
	
	@FindBy(xpath = "//select[contains(@class,'billsBizDaySelection')]")
	WebElement billsBizDaySelection;
	
	//Email audit logs
	
	@FindBy(xpath = "//button[text()='Email Automation Audit logs']")
	WebElement emailAutomationAuditLog;
	
	@FindBy(xpath = "//div[@id='showEmailEditLogModal']//div[@class='modal-dialog']")
	WebElement emailAuditLogModal;
	
	@FindBy(xpath = "//div[@id='emailEditLogsTable_filter']//input[@type='search']")
	WebElement emailAuditLogSearch;
	
	@FindBy(xpath = "//table[@id='emailEditLogsTable']//tbody")
	WebElement emailAuditLogTable;
	
	@FindBy(xpath = "//table[@id='emailEditLogsTable']//thead//tr//th[contains(text(),'Field Name')]")
	WebElement fieldName;

	@FindBy(xpath = "//table[@id='emailEditLogsTable']//tbody//tr//td[3]")
	List<WebElement> newValues;
	
	@FindBy(xpath = "//table[@id='emailEditLogsTable']//tbody//tr//td[1]")
	List<WebElement> fieldNames;
	
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
	
	public void clickOnSaveReminderLevel() {
		saveReminderLevel.click();
	}
	
	public void setReminderLevelDetails(String from, String name) {
		if(isElementPresent(emailReminderTable,30)) {
			this.setReminderLevelToForBills(from);
			this.selectTemplate(name);
			this.clickOnSaveReminderLevel();
		}
	}
	
	//Email Frequency
	
	public void clickOnEmailFrequency() {
		emailFrequency.click();
	}
	
	public void clickOnBizDays() {
		bizDays.click();
	}
	
	public void clickOnWeekDays() {
		weekDays.click();
	}
	
	public void setWeekdaysCutOffDays(String day) {
		weekdaysCutOffDays.sendKeys(day);
	}
	
	public void clickOnAddSequence() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addSequence);
	}
	
	public void clickOnSaveEmailFrequency() {
		saveEmailFrequency.click();
	}
	
	public void setBizdaysCutOffDays(String day) {
		bizdaysCutOffDays.sendKeys(day);
	}
	
	public void selectBillsBizDaySelection() {
		Select select = new Select(billsBizDaySelection);
		for(int i=0; i<=5; i++) {
			select.selectByIndex(i);
		}
	}
	
	public void setWeekDaysDetails(String days) {
		this.setWeekdaysCutOffDays(days);
		this.clickOnAddSequence();
		this.clickOnSaveEmailFrequency();
	}
	
	public void setBizDaysDetails(String days) {
		this.setBizdaysCutOffDays(days);
		this.selectBillsBizDaySelection();
		this.clickOnSaveEmailFrequency();
	}
	
	//Email audit logs
	
	public void clickOnEmailAutomationAuditLog() {
		emailAutomationAuditLog.click();
	}
}
