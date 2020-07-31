package pageclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddTeamMemberPage extends BasePage{
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title'  and text()='Add A Team Member']")
	public WebElement addTeamMember;
	
	@FindBy(id = "CompanyName")
	WebElement companyName;
	
	@FindBy(id = "select2-user-container")
	WebElement userDropdown;
	
	@FindBy(className = "select2-search__field")
	WebElement searchResult;
	
	@FindBy(xpath = "//ul[@id='select2-user-results']//li")
	WebElement userResult;
	
	@FindBy(id = "select2-role-container")
	WebElement roleDropdown;
	
	@FindBy(xpath = "//ul[@id='select2-role-results']//li")
	List<WebElement> roleResult;
	
	@FindBy(id = "select2-roleTitle-container")
	WebElement titleDropdown;
	
	@FindBy(xpath = "//ul[@id='select2-roleTitle-results']//li")
	List<WebElement> titleResult;
	
	@FindBy(id = "teamMemberAdd")
	WebElement add;
	
	@FindBy(xpath = "//a[text()='Companies']")
	WebElement companies;
	
	@FindBy(xpath = "//a[text()='Company Details']")
	WebElement companyDetails;
	
	public AddTeamMemberPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isCompanyNameDisabled() {
		if(companyName.getAttribute("disabled").equals("disabled")) {
			return true;
		}
		return false;
	}
	
	public void clickOnUserDropdown() {
		userDropdown.click();
	}
	
	public void setSearchResult(String value) {
		searchResult.sendKeys(value);
	}
	
	public void clickOnUserResult() {
		userResult.click();
	}
	
	public void clickOnRoleDropdown() {
		roleDropdown.click();
	}
	
	public void clickOnRoleResult(String value) {
		for(WebElement role:roleResult) {
			if(role.getText().equals(value)) {
				role.click();
				break;
			}
		}
	}
	
	public void clickOnTitleDropdown() {
		titleDropdown.click();
	}
	
	public void clickOnTitleResult(String value) {
		for(WebElement title:titleResult) {
			if(title.getText().equals(value)) {
				title.click();
				break;
			}
		}
	}
	public boolean isAddButtonEnabled() {
		if(add.isEnabled()) {
			return true;
		}
		return false;
	}
	
	public void clickOnAdd() {
		add.click();
	}
	
	public void clickOnCompanies() {
		companies.click();
	}
	
	public void clickOnCompanyDetails() {
		companyDetails.click();
	}
	
	public void setTeamMemberDetails(String user, String role,String title) {
		try {
			this.clickOnUserDropdown();
			this.setSearchResult(user);
			this.clickOnUserResult();
			this.clickOnRoleDropdown();
			this.setSearchResult(role);
			this.clickOnRoleResult(role);
			this.clickOnTitleDropdown();
			this.clickOnTitleResult(title);
		} catch(Exception e) {
			this.clickOnCompanyDetails();
		}
	}
	
	public boolean checkTeamMemberButton() {
		if(isElementPresent(add, 30) && isElementPresent(companies, 30) && isElementPresent(companyDetails, 30)) {
			return true;
		}
		return false;
	}
}
