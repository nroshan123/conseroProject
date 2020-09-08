package pageclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
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
	
	@FindBy(xpath = "//button[@name='Add New Template']")
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
	
	@FindBy(xpath = "//div[contains(@class,'billsReminderCountLevel')]")
	WebElement emailReminderLevel;
	
	@FindBy(id = "reminderLevelBillsTable_wrapper")
	WebElement emailReminderTable;
	
	@FindBy(name = "reminderLevelToForBills")
	WebElement reminderLevelToForBills;
	
	@FindBy(xpath = "//table[@id='reminderLevelBillsTable']//select[@id='templateId']")
	WebElement templateId;
	
	@FindBy(xpath = "//a[text()='Add Reminder Level']")
	WebElement addReminderLevel;
	
	@FindBy(xpath = "//table[@id='reminderLevelBillsTable']//tbody//tr[2]//input[@name='reminderLevelFromForBills']")
	WebElement reminderFrom1;
	
	@FindBy(xpath = "//table[@id='reminderLevelBillsTable']//tbody//tr[2]//input[@name='reminderLevelToForBills']")
	WebElement reminderTo1;
	
	@FindBy(xpath = "//table[@id='reminderLevelBillsTable']//tbody//tr[2]//select[contains(@class,'selectTemplateForBills')]")
	WebElement template1;
	
	@FindBy(xpath = "//div[@id='billsConfiguration']//button[text()='Save']")
	WebElement saveReminderLevel;
	
	@FindBy(xpath = "//table[@id='reminderLevelBillsTable']//tbody//tr[2]//a[@class='deleteReminderLevelForBills']")
	WebElement delete;
	
	@FindBy(xpath = "//table[@id='reminderLevelBillsTable']//tbody//tr[2]")
	WebElement reinderlevel2;
	
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
	
	@FindBy(xpath = "//select[contains(@class,'billsBizDaySelection')]//following-sibling::div/button[contains(@class,'multiselect')]")
	WebElement bizDayDropdown;
	
	@FindBy(xpath = "//select[contains(@class,'billsBizDaySelection')]//following-sibling::div/ul[contains(@class,'dropdown-menu')]//li[not(contains(@class,'multiselect-all'))]")
	List<WebElement> bizDayOptions;
	
	@FindBy(xpath = "//span[@class='multiselect-selected-text']")
	WebElement selectedBizDays;
	
	@FindBy(xpath = "//div[contains(@class,'billsFrequencyTypeDiv')]//span[contains(text(),'Biz Days')]//preceding-sibling::input")
	WebElement billsType;
	
	@FindBy(xpath = "//div[contains(@class,'billsFrequencyTypeDiv')]/label")
	WebElement typeLevel;
	
	@FindBy(xpath = "//div[contains(@class,'cutoffDaysDiv')]/label")
	WebElement billsPendingLevel;
	
	@FindBy(xpath = "//div[contains(@class,'billsBizDayDiv')]/label")
	WebElement billsBizDayLevel;
	
	//Email audit logs
	
	@FindBy(xpath = "//button[text()='Email Automation Audit logs']")
	WebElement emailAutomationAuditLog;
	
	@FindBy(xpath = "//div[@id='showEmailEditLogModal']//div[@class='modal-dialog']")
	WebElement emailAuditLogModal;
	
	@FindBy(xpath = "//div[@id='emailEditLogsTable_filter']//input[@type='search']")
	WebElement emailAuditLogSearch;
	
	@FindBy(xpath = "//table[@id='emailEditLogsTable']//tbody//tr//td[@class='dataTables_empty']")
	public WebElement emailAuditLogTable;
	
	@FindBy(xpath = "//table[@id='emailEditLogsTable']//thead//tr//th[contains(text(),'Field Name')]")
	public WebElement fieldName;

	@FindBy(xpath = "//table[@id='emailEditLogsTable']//tbody//tr//td[1]")
	List<WebElement> fieldNames;
	
	@FindBy(xpath = "//table[@id='emailEditLogsTable']//tbody//tr//td[5]")
	List<WebElement> auditedDates;
	
	@FindBy(xpath = "//div[@id='showEmailEditLogModal']//div[@class='announcementModalHeader']//a[contains(@class,'close-circle')]")
	WebElement closeAuditLogModal;
	
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
	
	public boolean isEmailReminderLevelEnabled() {
		return emailReminderLevel.isEnabled();
		
	}
	
	public void setReminderLevelToForBills(String from) {
		reminderLevelToForBills.sendKeys(from);
	}
	
	public void selectTemplate(String name) {
		Select select = new Select(templateId);
		select.selectByVisibleText(name);
	}
	
	public void clickOnAddReminderLevel() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addReminderLevel);
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
	
	public void setReminderTo1(String from) {
		reminderTo1.sendKeys(from);
	}
	
	public void selectTemplate1(String name) {
		Select select = new Select(template1);
		select.selectByVisibleText(name);
	}
	
	public String getReminderFrom() {
		return reminderFrom1.getAttribute("value");
	}
	
	public boolean isReminderFromDisabled() {
		return reminderFrom1.isEnabled();
	}
	
	public void setAddReminderLevel(String from, String name) {
		this.setReminderTo1(from);
		this.selectTemplate1(name);
		this.clickOnSaveReminderLevel();
	}
	
	public void clickOnDelete() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", delete);
	}
	
	public boolean isDeleteExist() {
		return isElementPresent(delete,30);
	}
	
	public boolean isReinderLevel2Exist() {
		return isElementPresent(reinderlevel2,30);
	}
	
	//Email Frequency
	
	public boolean isEmailFrequencyLevelEnabled() {
		return emailFrequency.isEnabled();
	}
	
	public void clickOnEmailFrequency() {
		emailFrequency.click();
	}
	
	public void clickOnBizDays() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", bizDays);
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
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", saveEmailFrequency);
	}
	
	public void setBizdaysCutOffDays(String day) {
		bizdaysCutOffDays.sendKeys(day);
	}
	
	public void clickOnBizDaysDropdown() {
		bizDayDropdown.click();
	}
	
	public void selectBillsBizDaySelection() {
		for(int i=0; i<=5; i++) {
			bizDayOptions.get(i).click();
		}
	}
	
	public void setWeekDaysDetails(String days) {
		this.setWeekdaysCutOffDays(days);
		this.clickOnAddSequence();
		this.clickOnSaveEmailFrequency();
	}
	
	public void setBizDaysDetails(String days) {
		this.setBizdaysCutOffDays(days);
		this.clickOnBizDaysDropdown();
		this.selectBillsBizDaySelection();
		this.clickOnSaveEmailFrequency();
	}
	
	public String getTypeLevel() {
		return typeLevel.getText().replace(":", "");
	}
	
	public String getBillsPendingLevel() {
		return billsPendingLevel.getText().replace(":", "");
	}
	
	public String getBillsBizDayLevel() {
		return billsBizDayLevel.getText().replace(":", "");
	}
	
	public String getbizdaysCutOffDays() {
		return bizdaysCutOffDays.getAttribute("value");
	}
	
	public String getSelectedBizDays() {
		return bizDayDropdown.getAttribute("title").replace(" ", "");
	}
	
	public String getCheckedFrequency() {
		String value = "";
		if(billsType.isSelected()) {
			value = billsType.getAttribute("value");
		}
		return value;
	}
	
	//Email audit logs
	
	public void clickOnEmailAutomationAuditLog() {
		emailAutomationAuditLog.click();
	}
	
	public boolean isAuditModalExist() {
		if(isElementPresent(emailAuditLogModal,60)) {
			return true;
		}
		return false;
	}
	
	public boolean checkAuditLog(String date, String field, String value) {
		try {
			for(int i=0; i<auditedDates.size(); i++) {
				System.out.println(auditedDates.get(i).getText().trim());
				System.out.println(date);
				if(auditedDates.get(i).getText().trim().contains(date)) {
					 for(int j=0; i< fieldNames.size(); j++) {
						 System.out.println(fieldNames.get(j).getText().trim());
							System.out.println(field);
						if(fieldNames.get(j).getText().trim().contains(field)) {
						  WebElement newValue = driver.findElement(By.xpath("//table[@id='emailEditLogsTable']//tbody//tr[" + (j+1) + "]//td[3]"));
						  System.out.println(newValue.getText().trim());
							System.out.println(value);
						  if (newValue.getText().trim().equals(value)) {
							  return true;
							}
						}
					 }
				}
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}
	
	public void setEmailAuditLogSearch(String value) {
		emailAuditLogSearch.sendKeys(value);
	}
	
	public void clickOnFieldName() {
		fieldName.click();
	}
	
	public boolean isFieldNameAscendingOrder() {
		List<String> list = new ArrayList<String>();
		for (WebElement fieldName : fieldNames) {
			list.add(fieldName.getText());
		}
		List<String> tempList= list;
		Collections.sort(tempList);
		if(tempList.equals(list)) {
			return true;
		}
		return false;
	}
	
	public boolean isFieldNameDescendingOrder() {
		List<String> list = new ArrayList<String>();
		for (WebElement fieldName : fieldNames) {
			list.add(fieldName.getText());
		}
		List<String> tempList= list;
		Collections.sort(tempList, Collections.reverseOrder());
		if(tempList.equals(list)) {
			return true;
		}
		return false;
	}
	
	public void clickOnCloseAuditLogModal() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", closeAuditLogModal);
	}
}
