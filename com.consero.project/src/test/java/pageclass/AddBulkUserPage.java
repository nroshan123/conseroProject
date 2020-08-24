package pageclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddBulkUserPage extends BasePage {

	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(),'Bulk Insert Users')]")
	public WebElement pageTitle;
	
	@FindBy(id = "fileInput")
	WebElement bulkUser;
	
	@FindBy(xpath = "//input[contains(@class,'insertUserAlertPopup')]")
	WebElement importBulkUser;
	
	@FindBy(xpath = "//div[@id='insertUserAlertPopupModal']//div[@class='modal-dialog']")
	WebElement modal;
	
	@FindBy(xpath = "//div[@id='insertUserAlertPopupModal']//div[@class='modal-dialog']//a[text()='Yes']")
	WebElement confirmModal;
	
	@FindBy(id = "successfullUserTable")
	WebElement successfullUserTable;
	
	@FindBy(id = "duplicateUserTable_wrapper")
	WebElement duplicateUserTable;
	
	public AddBulkUserPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
   
	public void setBulkUser(String filepath) {
		bulkUser.sendKeys(filepath);
	}
	
	public void clickOnImportBulkUser() {
		importBulkUser.click();
	}
	
	public boolean isModalExist() {
		if(isElementPresent(modal, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnConfirmModal() {
		confirmModal.click();
	}
	
	public boolean isSuccessfullUserTableExist() {
		if(isElementPresent(successfullUserTable,60)) {
			return true;
		}
		return false;
	}
	
	public boolean isDuplicateUserTableExist() {
		if(isElementPresent(duplicateUserTable,60)) {
			return true;
		}
		return false;
	}
}
