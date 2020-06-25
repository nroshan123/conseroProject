package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCompanyPage extends BasePage {

	WebDriver driver;

	@FindBy(id = "CreateForm")
	WebElement createCompanyForm;
	
	@FindBy(id = "ParentCompany")
	WebElement parentCompany;
	
	@FindBy(id = "Name")
	WebElement companyName;
	
	@FindBy(id = "AddressLineOne")
	WebElement addressLineOne;
	
	@FindBy(id = "AddressLineTwo")
	WebElement addressLineTwo;
	
	@FindBy(id = "City")
	WebElement city;
	
	@FindBy(id = "State")
	WebElement state;
	
	@FindBy(id = "ZipCode")
	WebElement zipcode;
	
	@FindBy(id = "IsMonthEndSummaryEnabled")
	WebElement monthEndSummary;
	
	@FindBy(id = "IsRealTimeBalanceDashboardEnabled")
	WebElement realTimeDashboard;
	
	@FindBy(id = "createCompanySubmit")
	WebElement createCompany;
	
	@FindBy(xpath = "//a[text()='Back to List']")
	WebElement backToList;
	
	public AddCompanyPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setCompanyName(String name) {
		companyName.sendKeys(name);
	}
	
	public void setAddressLineOne(String address1) {
		addressLineOne.sendKeys(address1);
	}
	
	public void setAddressLineTwo(String address2) {
		addressLineTwo.sendKeys(address2);
	}
	
	public void setCity(String name) {
		city.sendKeys(name);
	}
	
	public void setState(String name) {
		state.sendKeys(name);
	}
	
	public void setZipcode(String name) {
		zipcode.sendKeys(name);
	}
	
	public void clickOnmonthEndSummary() {
		monthEndSummary.click();
	}
	
	public void clickOnRealTimeDashboard() {
		realTimeDashboard.click();
	}
	
	public void clickOnCreateCompany() {
		createCompany.submit();
	}
	
	public void clickOnBackToList() {
		backToList.submit();
	}
	
	public void setCompanyDetails(String name, String address1, String cityName, String stateName, String zip) {
		try {
			if(isElementPresent(createCompanyForm, 30)) {
				this.setCompanyName(name);
				this.setAddressLineOne(address1);
				this.setCity(cityName);
				this.setState(stateName);
				this.setZipcode(zip);
				this.clickOnmonthEndSummary();
				this.clickOnRealTimeDashboard();
				this.clickOnCreateCompany();
			}
		} catch(Exception e) {
			this.clickOnBackToList();
		}
	}
	
}
