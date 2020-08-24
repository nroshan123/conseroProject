package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeactivateBulkUserPage extends BasePage{
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(),'Bulk Deactivate Users')]")
	public WebElement pageTitle;
	
	@FindBy(id = "deactivateFileInput")
	WebElement deactivateBulkUser;
	
	@FindBy(xpath = "//input[contains(@class,'deactivateUserAlertPopup')]")
	WebElement importDeactivateUser;
	
	@FindBy(xpath = "//div[@id='deactivateUserAlertPopupModal']//div[@class='modal-dialog']")
	WebElement modal;
	
	@FindBy(xpath = "//div[@id='deactivateUserAlertPopupModal']//div[@class='modal-dialog']//a[text()='Yes']")
	WebElement confirmModal;
	
	@FindBy(xpath = "//div[@class='bulkDeactivateUsers']//h5[@class='page-title']")
	WebElement message;
	
	@FindBy(id = "errorDeactivateDetailTable_wrapper")
	WebElement errorTable;
	
	public DeactivateBulkUserPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
   
	public void setDeactivateBulkUser(String filepath) {
		deactivateBulkUser.sendKeys(filepath);
	}
	
	public void clickOnImportDeactivateUser() {
		importDeactivateUser.click();
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
	
	public boolean isErrorTableExist() {
		if(isElementPresent(errorTable,60)) {
			return true;
		}
		return false;
	}
	
	public String getMessage() {
		return message.getText();
	}
}
