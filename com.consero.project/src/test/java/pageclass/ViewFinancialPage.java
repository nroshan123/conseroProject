package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewFinancialPage extends BasePage {

	@FindBy(xpath = "//button[text()='Verify Path for GDrive']")
	WebElement verifyPathForGDrive;
	
	@FindBy(xpath = "//a[text()='Activity Detail']")
	WebElement activityDetail;

	@FindBy(xpath = "//a[contains(text(),'Re-Generate Financials')]")
	WebElement regenerateFinancial;
	
	@FindBy(xpath = "//span[contains(text(),'Highlights')]")
	WebElement hignlights;
	
	@FindBy(xpath = "//div[contains(@class,'intacctValidationViewTab')]")
	WebElement intacctValidation;
	
	@FindBy(xpath = "//div[contains(@class,'reportValidationViewTab')]")
	WebElement reportValidation;
	
	@FindBy(xpath = "//div[@id='GDriveErrorBox']//div[@class='modal-dialog']")
	WebElement gDriveModal;
	
	@FindBy(xpath = "//div[@id='GDriveErrorBox']//div[contains(@class,'GDriveModalError')]")
	WebElement gDrivePathError;
	
	@FindBy(xpath = "//div[@id='GDriveErrorBox']//a[text()='OK']")
	WebElement acceptGdriveModal;
	
	//span[contains(text(),'Highlights')]
	
	public ViewFinancialPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnActivityDetail() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", activityDetail);
	}
	
	public void clickOnVerifyPathForGDrive() {
		verifyPathForGDrive.click();
	}
	
	public void clickOnRegenerateFinancial() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", regenerateFinancial);
	}
	
	public void clickOnHignlights() {
		hignlights.click();
	}
	
	public void clickOnIntacctValidation() {
		intacctValidation.click();
	}
	
	public void clickOnReportValidation() {
		reportValidation.click();
	}
	
	public boolean isgDriveModalExist() {
		return isElementPresent(gDriveModal, 30);
	}
	
	public String getErrorMsg() {
		return gDrivePathError.getText();
	}
	
	public boolean isgDriveErrorExist(String msg) {
		if(isElementPresent(gDrivePathError, 30) && this.getErrorMsg().equals(msg)) {
			return true;
		}
		return false;
	}
}



