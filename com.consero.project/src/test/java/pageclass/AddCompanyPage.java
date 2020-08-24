package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.util.Strings;

public class AddCompanyPage extends BasePage {

	WebDriver driver;

	@FindBy(id = "CreateForm")
	WebElement createCompanyForm;
	
	@FindBy(id = "ParentCompany")
	WebElement parentCompany;
	
	@FindBy(id = "IsAFRGroupingEnabled")
	WebElement afrGrouping;
	
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
	
	@FindBy(id = "CustomerEmailID")
	WebElement customerEmail;
	
	@FindBy(id = "CustomerEmailIDPassword")
	WebElement customerPassword;
	
	@FindBy(id = "CompanyLogo")
	WebElement companyLogo;
	
	@FindBy(id = "IsMonthEndSummaryEnabled")
	WebElement monthEndSummary;
	
	@FindBy(id = "IsRealTimeBalanceDashboardEnabled")
	WebElement realTimeDashboard;
	
	@FindBy(id = "createCompanySubmit")
	WebElement createCompany;
	
	@FindBy(xpath = "//a[text()='Back to List']")
	WebElement backToList;
	
	@FindBy(id = "EditForm")
	WebElement editCompanyForm; 
	
	@FindBy(id = "editCompanySubmit")
	WebElement updateCompany;
	
	public AddCompanyPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnAfrGrouping() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", afrGrouping);
	}
	
	public void setCompanyName(String name) {
		companyName.sendKeys(name);
	}
	
	public void setAddressLineOne(String address1) {
		addressLineOne.sendKeys(address1);
	}
	
	public String getAddressLineOne() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",addressLineOne).toString();
	}
	
	public String getAddressLineTwo() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",addressLineTwo).toString();
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
	
	public void setcustomerEmail(String email) {
		customerEmail.sendKeys(email);
	}
	
	public void setcustomerPassword(String password) {
		customerPassword.sendKeys(password);
	}
	
	public void clickOnCompanyLogo() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", companyLogo);
	}
	
	public void setCompanyLogo(String filePath) {
		companyLogo.sendKeys(filePath);
	}
	
	public void clickOnmonthEndSummary() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", monthEndSummary);
	}
	
	public void clickOnRealTimeDashboard() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", realTimeDashboard);
	}
	
	public void clickOnCreateCompany() {
		createCompany.submit();
	}
	
	public void clickOnBackToList() {
		backToList.submit();
	}
	
	public void clickOnUpdateCompany() {
		updateCompany.click();
	}
	
	public String getAddress1() {
		return addressLineOne.getText();
	}
	
	public void setCompanyDetails(String name, String address1, String cityName, String stateName, String zip, String email, String password) {
		try {
			if(isElementPresent(createCompanyForm, 60)) {
				this.setCompanyName(name);
				this.setAddressLineOne(address1);
				this.setCity(cityName);
				this.setState(stateName);
				this.setZipcode(zip);
				this.setcustomerEmail(email);
				this.setcustomerPassword(password);
				Thread.sleep(1000);
				this.setCompanyLogo("C:\\Users\\Admin\\conseroFiles\\companyLogo.png");
				/*this.clickOnCompanyLogo();
				Thread.sleep(2000);
				Runtime.getRuntime().exec("C:\\Users\\thinkBridge\\Desktop\\AutoIT\\companyLogo.exe");*/
				this.clickOnmonthEndSummary();
				this.clickOnRealTimeDashboard();
				this.clickOnCreateCompany();
			}
		} catch(Exception e) {
			this.clickOnBackToList();
		}
	}
	
	public void setUpdateCompanyDetails(String address1, String address2) {
		try {
			if(isElementPresent(editCompanyForm, 60)) {
				if(!Strings.isNullOrEmpty(this.getAddressLineOne())) {
					addressLineOne.clear();
				}
				this.setAddressLineOne(address1);
				if(!Strings.isNullOrEmpty(this.getAddressLineTwo())) {
					addressLineTwo.clear();
				}
				this.setAddressLineTwo(address2);
				this.clickOnUpdateCompany();
			}
		} catch(Exception e) {
			this.clickOnBackToList();
		}
	}
	
}
