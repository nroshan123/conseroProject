package pageclass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompanyDetailsPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'Company Details']")
	public WebElement companyDetailsHeader;
	
	@FindBy(xpath = "//a[contains(text(),'Edit')]")
	WebElement edit;
	
	@FindBy(xpath = "//a[contains(text(),'Back to List')]")
	 WebElement backToList;
	
	@FindBy(xpath = "//a[text()='Manage Department Expense']")
	WebElement manageDepartmentExpense;
	
	@FindBy(xpath = "//a[contains(text(),'Add Member')]")
	WebElement addMember;
	
	@FindBy(xpath = "//a[contains(text(),'View Email Logs')]")
	WebElement viewEmailLogs;
	
	@FindBy(xpath = "//a[contains(text(),'Add Link')]")
	public WebElement addLink;
	
	@FindBy(xpath = "//a[text()='Manage Email Automation']")
	WebElement manageEmailAutomation;
	
	@FindBy(xpath = "//a[contains(text(),'Add Close Template')]")
	WebElement addCloseTemplate;
	
	@FindBy(xpath = "//a[contains(text(),'Kickoffs')]")
	WebElement kickOffs;
	
	@FindBy(xpath = "//input[@id='Company_City']")
	WebElement city;
	
	@FindBy(xpath = "//input[@id='Company_State']")
	WebElement state;
	
	@FindBy(xpath = "//input[@id='Company_ZipCode']")
	WebElement zipCode;

	@FindBy(xpath = "//input[@id='Company_CustomerEmailID']")
	WebElement customerEmailID;
	
	@FindBy(xpath = "//div[contains(@class,'panel-heading')]//i")
	WebElement showTeamMembers;
	
	@FindBy(xpath = "//div[contains(@class,'panel-body')]//table[contains(@class,'dataTable')]")
	public WebElement teamMemberTable;
	
	@FindBy(xpath = "//div[contains(@class,'panel-body')]//table[contains(@class,'dataTable')]//tbody//tr//td[2]")
	List<WebElement> memberEmailId;
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr//td[@class='dataTables_empty']")
	WebElement linkTable;
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr//td[1]")
	List<WebElement> linktypes;
	
	 public CompanyDetailsPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	 
	public boolean isEditAndBackToListExist() {
		if(isElementPresent(edit, 30) && isElementPresent(backToList, 30)) {
			return true;
		}
		return false;
	}
	
	public boolean isTeamMemberTemplateButtonExist() {
		if(isElementPresent(addMember, 30) && isElementPresent(viewEmailLogs, 30)) {
			return true;
		}
		return false;
	}
	
	public boolean isActivityTemplateButtonExist() {
		if(isElementPresent(addCloseTemplate, 30) && isElementPresent(kickOffs, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnshowTeamMembers() {
		if(showTeamMembers.getAttribute("class").contains("glyphicon-chevron-down")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", showTeamMembers);
		}
	}
	
	public boolean isTeamMemberExist(String email) {
		if(isElementPresent(teamMemberTable, 60)) {
			for(WebElement emailId:memberEmailId ) {
				if(emailId.getText().equals(email)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clickOnAddTeamMember() {
		addMember.click();
	}
	
	public void clickOnAddLink() {
		addLink.click();
	}
	
	public void clickOnViewEmailLogs() {
		viewEmailLogs.click();
	}
	
	public void clickOnManageEmailAutomation() {
		manageEmailAutomation.click();
	}
	
	public void clickOnRemoveTeamMember(String email) {
		for(int i=0; i<memberEmailId.size(); i++) {
			if(memberEmailId.get(i).getText().equals(email)) {
				WebElement remove= driver.findElement(By.xpath("//div[contains(@class,'panel-body')]//table[contains(@class,'dataTable')]//tbody//tr["+ (i+1) +"]//td//a[text()='Remove']"));
				remove.click();
				break;
			}
		}
	}
	
	public boolean isLinkTableEmpty() {
		if(isElementPresent(linkTable, 60)) {
			return true;
		}
		return false;
	}
	
	public boolean isLinkAdded(String linkType, String linkName, String linkItself) {
		for(int i=0; i<linktypes.size(); i++) {
			if(linktypes.get(i).getText().equals(linkType)) {
				WebElement name = driver.findElement(By.xpath("//table[@id='companyAddLinkID']//tbody//tr["+ (i+1) +"]//td[2]"));
				WebElement itself = driver.findElement(By.xpath("//table[@id='companyAddLinkID']//tbody//tr["+ (i+1) +"]//td[3]"));
				if(name.getText().equals(linkName) && itself.getText().equals(linkItself)) {
					return true;
				}
			}
		}
		return false;
	}
}
