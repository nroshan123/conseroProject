package pageclass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.util.Strings;

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
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr[1]//td//span[contains(text(),'Edit')]")
	WebElement editLink;
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr[1]//td//a[contains(text(),'Delete')]")
	WebElement deleteLink;
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr//td[2]")
	List<WebElement> linkNames;
	
	//edit Link
	
	@FindBy(id = "editOtherReports")
	WebElement editLinkModal;
	
	@FindBy(xpath = "//div[@id='editOtherReportsHTML']//table//tbody//input[contains(@id,'editName')]")
	WebElement linkName;
	
	@FindBy(xpath = "//div[@id='editOtherReportsHTML']//table//tbody//input[contains(@id,'editLink')]")
	WebElement linkItself;
	
	@FindBy(xpath = "//div[@id='editOtherReportsHTML']//button[@id='addLinkButton']")
	WebElement saveLink;
	
	@FindBy(id = "btnCloseOtherReports")
	WebElement closeLinkModal;
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr[1]//td[2]")
	WebElement updatedLinkName;
	
	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr[1]//td[3]")
	WebElement updatedLinkItself;
	
	//close template 
	
	@FindBy(xpath = "//h3[contains(text(),'Close Activity Template')]//following-sibling::table//tbody//tr//td[1]")
	WebElement template;
	
	@FindBy(xpath = "//h3[contains(text(),'Close Activity Template')]//following-sibling::table//tbody//tr//td//a[text()='Validate']")
	WebElement validate;
	
	@FindBy(xpath = "//h3[contains(text(),'Close Activity Template')]//following-sibling::table//tbody//tr//td//a[contains(text(),'Kickoff Setup')]")
	WebElement kickoffSetup;
	
	
//	@FindBy(xpath = "//table[@id='companyAddLinkID']//tbody//tr[1]//td[3]")
//	WebElement updatedLinkItself;
	
	
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
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addMember);
	}
	
	public void clickOnAddLink() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addLink);
	}
	
	public void clickOnViewEmailLogs() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", viewEmailLogs);
	}
	
	public void clickOnManageEmailAutomation() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", manageEmailAutomation);
	}
	
	public void clickOnRemoveTeamMember(String email) {
		for(int i=0; i<memberEmailId.size(); i++) {
			if(memberEmailId.get(i).getText().equals(email)) {
				WebElement remove= driver.findElement(By.xpath("//div[contains(@class,'panel-body')]//table[contains(@class,'dataTable')]//tbody//tr["+ (i+1) +"]//td//a[text()='Remove']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", remove);
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
			if(linktypes.get(i).getText().trim().equals(linkType)) {
				WebElement name = driver.findElement(By.xpath("//table[@id='companyAddLinkID']//tbody//tr["+ (i+1) +"]//td[2]"));
				WebElement itself = driver.findElement(By.xpath("//table[@id='companyAddLinkID']//tbody//tr["+ (i+1) +"]//td[3]"));
				if(name.getText().trim().equals(linkName) && itself.getText().trim().equals(linkItself)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clickOnEditLink() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", editLink);
	}
	
	public void clickOnDeleteLink() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", deleteLink);
	}
	
	public void setLinkName(String name) {
		linkName.sendKeys(name);
	}
	
	public void setLinkItself(String link) {
		linkItself.sendKeys(link);
	}
	
	public void clickOnSaveLink() {
		saveLink.click();
	}
	
	public void clickOnCloseLinkModal() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", closeLinkModal);
	}
	
	public String getLinkName() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",linkName).toString();
	}
	
	public String getLinkItself() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",linkItself).toString();
	}
	
	public String getUpdatedLinkName() {
		return updatedLinkName.getText().trim();
	}
	
	public String getUpdatedLinkItself() {
		return updatedLinkItself.getText().trim();
	}
	
	public void setEditLinkDetails(String name, String link) {
		try {
			if(isElementPresent(editLinkModal, 40)) {
				if(!Strings.isNullOrEmpty(this.getLinkName())) {
					linkName.clear();
				}
				this.setLinkName(name);
				if(!Strings.isNullOrEmpty(this.getLinkItself())) {
					linkItself.clear();
				}
				this.setLinkItself(link);
				this.clickOnSaveLink();
			}
		} catch(Exception e) {
			this.clickOnCloseLinkModal();
		}
	}
	
	public boolean isLinkUpdated(String name, String link) {
		if(this.getUpdatedLinkName().equals(name) && this.getUpdatedLinkItself().equals(link)) {
			return true;
		}
		return false;
	}
	
	public boolean isLinkDeleted(String name) {
		for (WebElement linkName : linkNames) {
			if (linkName.getText().trim().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public void clickOnAddCloseTemplate() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addCloseTemplate);
	}
	
	//close template method
	
	public String getTemplate() {
		return template.getText();
	}
	
	public boolean isTemplateAdded(String template) {
		if(this.getTemplate().equals(template)) {
			return true;
		}
		return false;
	}
	
	public void clickOnValidate() {
		if(isElementPresent(validate,40)) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", validate);
		}
	}
	
	public void clickOnKickoffSetup() {
		if(isElementPresent(kickoffSetup,40)) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", kickoffSetup);
		}
	}
	
	public void clickOnKickOffs() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", kickOffs);
	}
	
}
