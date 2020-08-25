package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddActivityPage extends BasePage {
	
    WebDriver driver;
    
    @FindBy(xpath = "//div[contains(@class,'addActivityDialogue')]")
	WebElement activityModal;
	
	@FindBy(id = "CompanyId")
	WebElement companyDropdown;
	
	@FindBy(id = "Occurrence")
	WebElement activityTypeDropdown;
	
	@FindBy(id = "ActivityDescription")
	WebElement activityDescription;
	
	@FindBy(id = "Function")
	WebElement functionDropdown;
	
	@FindBy(id = "TowerId")
	WebElement towerDropdown;
	
	@FindBy(id = "KickOffId")
	WebElement monthDropdown;
	
	@FindBy(id = "StartOnBizDay")
	WebElement startOnBizDay;
	
	@FindBy(id = "DueForClientBizDay")
	WebElement dueForClientBizDay;
	
	@FindBy(id = "Owner")
	WebElement ownerDropdown;
	
	@FindBy(id = "reviewerTab")
	WebElement reviwerTab;

	@FindBy(id = "generalTab")
	WebElement activityTab;
	
	@FindBy(id = "checkListTab")
	WebElement checkListTab;
	
	@FindBy(id = "CheckListItemOne")
	WebElement checkListItemOne;
	
	@FindBy(id = "ReviewerOne")
	WebElement reviewerOne;
	
	@FindBy(id = "DueForReveiewOne")
	WebElement dueForReveiewOne;
	
	@FindBy(id = "ReviewOneDuration")
	WebElement reviewOneDuration;
	
	@FindBy(id = "addReviewerLink")
	WebElement addReviewerLink;
	
	@FindBy(id = "submit")
	WebElement submit;
	
	@FindBy(id = "cancelAddActivity")
	WebElement cancelAddActivity;
	
	
	public AddActivityPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isactivityModalExist() {
		if(isElementPresent(activityModal, 60)) {
			return true;
		}
		return false;
	}
	
	public void selectCompany(String value) {
		Select select = new Select(companyDropdown);
		select.selectByVisibleText(value);
	}
	
	public void selectActivityType(String value) {
		Select select = new Select(activityTypeDropdown);
		select.selectByVisibleText(value);
	}
	
	public boolean isActivityTabSelected() {
		if(activityTab.getAttribute("class").equals("active")) {
			return true;
		}
		return false;
	}
	
	public void setActivityDescritption(String description) {
		activityDescription.sendKeys(description);
	}
	
	public void selectFunction(String value) {
		Select select = new Select(functionDropdown);
		select.selectByVisibleText(value);
	}
	
	public void selectTower(String value) {
		Select select = new Select(towerDropdown);
		select.selectByVisibleText(value);
	}
	
	public void selectMonth(String value) {
		Select select = new Select(monthDropdown);
		select.selectByVisibleText(value);
	}
	
	public void setStartOnBizDay(String day) {
		startOnBizDay.sendKeys(day);
	}
	
	public void setDueForClientBizDay(String day) {
		dueForClientBizDay.sendKeys(day);
	}
	
	public void selectOwner(String value) {
		Select select = new Select(ownerDropdown);
		select.selectByVisibleText(value);
	}
	
	public void clickOnChecklistTab() {
		checkListTab.click();
	}
	
	public void setCheckListItemOne(String value) {
		checkListItemOne.sendKeys(value);
	}
	
	public void clickOnReviwerTab() {
		reviwerTab.click();
	}
	
	public void selectReviewerOne(String value) {
		Select select = new Select(reviewerOne);
		select.selectByVisibleText(value);
	}
	
	public void setDueForReveiewOne(String value) {
		dueForReveiewOne.sendKeys(value);
	}
	
	public void setReviewOneDuration(String value) {
		reviewOneDuration.sendKeys(value);
	}
}
