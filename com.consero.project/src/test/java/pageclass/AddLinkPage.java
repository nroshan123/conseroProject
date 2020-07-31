package pageclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddLinkPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(),'Add A Custom Link')]")
	public WebElement pageHeader;
	
	@FindBy(id = "customLinkStandardReport")
	WebElement standardReportDiv;

	@FindBy(id = "FinancialStatementsFolder")
	public WebElement financialStatement;
	
	@FindBy(xpath = "//button[text()='Verify']")
	WebElement verify;
	
	@FindBy(id = "validateFolderMessage")
	WebElement validateFolderMessage;
	
	@FindBy(xpath = "//input[@id='links']//parent::label")
	List<WebElement> reportTypes;
	
	@FindBy(id = "LinkName")
	WebElement linkName;
	
	@FindBy(id = "LinkItself")
	WebElement linkItself;
	
	@FindBy(id = "addLinkButton")
	WebElement addLinkButton;
	
	@FindBy(xpath = "//a[text()='Companies']")
	WebElement companies;
	
	@FindBy(xpath = "//a[text()='Company Details']")
	WebElement companyDetails;
	
	@FindBy(xpath = "//input[@id='links']")
	List<WebElement> links;
	
	public AddLinkPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public void setFinancialStatement(String value) {
		financialStatement.sendKeys(value);
	}
	 
	public boolean isAddLinkTemplateExist() {
		if (isElementPresent(addLinkButton, 40) && isElementPresent(companies, 40) && isElementPresent(companyDetails, 40)) {
			return true;
		}
		return false;
	}

	public void clickOnreport(String name) {
		for(WebElement report:reportTypes) {
			if(report.getText().contains(name)) {
				report.click();
				break;
			}
		}
	}
	
	public void clickOnVerify() {
		verify.click();
	}
	
	public String getValidateFolderMessage() {
		return validateFolderMessage.getText();
	}
	
	public int getReportType() {
		return links.size();
	}
	 
	public void setLinkName(String name) {
		linkName.sendKeys(name);
	}
	
	public void setLinkItself(String value) {
		linkItself.sendKeys(value);
	}
	
	public void setLinkDetails(String name, String link) {
		this.setLinkName(name);
		this.setLinkItself(link);
	}
	
	public void clickOnAddLinkButton() {
		addLinkButton.click();
	}
	
	public void clickOnCompanyDetails() {
		companyDetails.click();
	}
}
