package pageclass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageDepartmentExpensePage extends BasePage{
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'Manage Department Expense Permission']")
	public WebElement pageTitle;
	
	@FindBy(id = "departmentLevelDEPermissionTab")
	WebElement departmentView;
	
	@FindBy(id = "userLevelDEPermissionTab")
	WebElement userView;
	
	@FindBy(xpath = "//select[@id='userList']//following-sibling::div//button[contains(@title,'None selected')]")
	WebElement userDropdown;
	
	@FindBy(xpath = "//input[contains(@class,'multiselect-search')]")
	WebElement search;
	
	@FindBy(name = "Save")
	WebElement save;
	
	@FindBy(xpath = "//button[text()='Department Expense Audit logs']")
	WebElement departmentAuditLog;
	
	@FindBy(xpath = "//ul[@class='multiselect-container dropdown-menu']//li[not(contains(@class,'filter-hidden'))]/a//label")
	List<WebElement> userOption;
	
	@FindBy(xpath = "//div[@id='accordionUserWise']//div[contains(@class,'selectedUserList')]//span[@class='enableorDisableDepartment']")
	WebElement enableOrDisableDepartment;
	
	@FindBy(xpath = "//div[@id='accordionUserWise']//div[contains(@class,'selectedUserList')]//h4//span[@class='bold']")
	WebElement username;
	
	@FindBy(xpath = "//div[@id='accordionUserWise']//div[contains(@class,'selectedUserList')]//input[@class='userViewParentCheckbox']")
	WebElement departmentToggle;
	
	//audit log
	
	@FindBy(xpath = "//div[@id='showDepartmentAuditLogModal']//div[@class='modal-dialog']")
	WebElement departmentAuditLogModal;
	
	@FindBy(xpath = "//table[@id='departmentExpenseAuditLogsTable']//tbody//tr//td[@class='dataTables_empty']")
	public WebElement departmentAuditLogTable;
	
	@FindBy(xpath = "//table[@id='departmentExpenseAuditLogsTable']//tbody//tr//td[6]")
	List<WebElement> auditedDates;
	
	public ManageDepartmentExpensePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isDepartmentViewSelected() {
		if(departmentView.getAttribute("class").equals("active")) {
			return true;
		}
		return false;
	}
	
	public void clickOnUserView() {
		userView.click();
	}
	
	public void clickOnUserDropdown() {
		userDropdown.click();
	}
	
	public void setSearch(String value) {
		search.sendKeys(value);
	}
	
	public void selectUserOption(String name) {
		for(WebElement user:userOption) {
			if(user.getText().equals(name)) {
				user.click();
				break;
			}
		}
	}
	
	public boolean isDisableDepartment() {
		if(enableOrDisableDepartment.getText().equals("Enable")) {
			return true;
		} else if(enableOrDisableDepartment.getText().equals("Disable")) {
			return false;
		}
		return false;
	}
	
	public void clickOnSave() {
		save.click();
	}
	
	public String getUsername() {
		return username.getText();
	}
	
	public void clickOnDepartmentToggle() {
		departmentToggle.click();
	}
	
	//audit log
	
	public void clickOnDepartmentAuditLog() {
		departmentAuditLog.click();
	}
	
	public boolean isAuditModalExist() {
		if(isElementPresent(departmentAuditLogModal,60)) {
			return true;
		}
		return false;
	}
	
	public boolean checkDepartmentAuditLog(String date, String name) {
		try {
			for (int i = 0; i < auditedDates.size(); i++) {
				if (auditedDates.get(i).getText().contains(date)) {
					WebElement username = driver.findElement(By.xpath("//table[@id='departmentExpenseAuditLogsTable']//tbody//tr[" + (i + 1) + "]//td[2]"));
					if (username.getText().equals(name)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
