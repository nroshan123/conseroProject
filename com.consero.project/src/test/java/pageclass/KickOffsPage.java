package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.common.base.Strings;

public class KickOffsPage extends BasePage {
	WebDriver driver;

	@FindBy(xpath = "//h5[@class='page-title']")
	WebElement pageTitle;
	
	@FindBy(xpath = "//a[text()='Close']")
	WebElement close;
	
	@FindBy(xpath = "//a[text()='Delete']")
	WebElement delete;
	
	@FindBy(xpath = "//a[text()='BizDays']")
	WebElement bizdays;
	
	@FindBy(xpath = "//table[contains(@class,'dataTable')]//tbody//tr[2]//td//span")
	WebElement status;
	
	@FindBy(xpath = "//table[contains(@class,'dataTable')]//tbody//tr[2]//td//a[text()='Reopen']")
	WebElement reopen;
	
	//BizDays
	
	@FindBy(xpath = "//*[@class='page-title' and contains(text(),'Move Business Days For Monthly Close')]")
	public WebElement BizDaysTitle;
	
	@FindBy(id = "NumberOfDaysToMove")
	WebElement numberOfDaysToMove;
	
	@FindBy(id = "btnCheckDates")
	WebElement checkDates;
	
	@FindBy(id = "btnMoveDates")
	WebElement moveDates;
	
	@FindBy(xpath = "//a[contains(text(),'Companies')]")
	WebElement companies;
	
	@FindBy(xpath = "//a[contains(text(),'Company Details')]")
	WebElement companyDetails;
	
	@FindBy(id = "warning")
	public WebElement warning;
	
	
	public KickOffsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isPageTitleExist(String title) {
		if(isElementPresent(pageTitle, 40) && pageTitle.getText().equals(title)) {
			return true;
		}
		return false;
	}
	
	public void clickOnClose() {
		close.click();
	}
	
	public void clickOnDelete() {
		delete.click();
	}
	
	public void clickOnBizdays() {
		bizdays.click();
	}
	
	public boolean checkKickOffsButton() {
		if(isElementPresent(close, 30) && isElementPresent(delete, 30) && isElementPresent(bizdays, 30)) {
			return true;
		}
		return false;
	}
	
	public String getStatus() {
		return status.getText();
	}
	
	public boolean isReopenButtonExist() {
		if(isElementPresent(reopen, 60)) {
			return true;
		}
		return false;
	}
	
	//BizDays 
	
	public void clickOnReopen() {
		reopen.click();
	}
	
	public String getNumberOfDaysToMove() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",numberOfDaysToMove).toString();
	}
	
	public void setNumberOfDaysToMove(String days) {
		if(!Strings.isNullOrEmpty(this.getNumberOfDaysToMove())) {
			numberOfDaysToMove.clear();
		}
		numberOfDaysToMove.sendKeys(days);
	}
	
	public boolean checkBizDaysButton() {
		if(isElementPresent(checkDates, 30) && isElementPresent(moveDates, 30) && isElementPresent(companies, 30)  && isElementPresent(companyDetails, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnCheckDates() {
		checkDates.click();
	}
	
	public void clickOnMoveDates() {
		moveDates.click();
	}
	
	public String getWarning() {
		return warning.getText();
	}
}
