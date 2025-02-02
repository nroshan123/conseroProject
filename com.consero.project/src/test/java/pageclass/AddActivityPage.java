package pageclass;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.util.Strings;

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
	
	@FindBy(xpath = "//input[@id='DueForClientBizDay']")
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
	
	@FindBy(id = "checklistLink")
	WebElement checklistLink;
	
	@FindBy(id = "ReviewerOne")
	WebElement reviewerOne;
	
	@FindBy(id = "DueForReveiewOne")
	WebElement dueForReveiewOne;
	
	@FindBy(id = "ReviewOneDuration")
	WebElement reviewOneDuration;
	
	@FindBy(id = "ReviewerTwo")
	WebElement reviewerTwo;
	
	@FindBy(id = "DueForReveiewTwo")
	WebElement dueForReveiewTwo;
	
	@FindBy(id = "ReviewTwoDuration")
	WebElement reviewTwoDuration;
	
	@FindBy(id = "addReviewerLink")
	WebElement addReviewerLink;
	
	@FindBy(id = "submit")
	WebElement submit;
	
	@FindBy(id = "cancelAddActivity")
	WebElement cancelAddActivity;
	
	@FindBy(xpath = "//label[@for='CheckListItemTwo']")
	WebElement checkListItemTwoLabel;
	
	@FindBy(id = "CheckListItemTwo")
	WebElement CheckListItemTwo;
	
	@FindBy(id = "reviewer2")
	WebElement reviewerTwoLabel;
	
	//non close activity
	
	@FindBy(id = "recurrenceTab")
	WebElement recurrenceTab;
	
	@FindBy(xpath = "//div[@id='SelectPattern']//input[@id='Rb+1']")
	WebElement oneTime;
	
	@FindBy(xpath = "//div[@id='SelectPattern']//input[@id='Rb+2']")
	WebElement daily;
	
	@FindBy(xpath = "//div[@id='SelectPattern']//input[@id='Rb+3']")
	WebElement weekly;
	
	@FindBy(xpath = "//div[@id='SelectPattern']//input[@id='Rb+4']")
	WebElement monthly;
	
	@FindBy(id = "IsSubActivity")
	WebElement isSubActivity;
	
	@FindBy(id = "getDueDates")
	WebElement validateDueDate;
	
	@FindBy(id = "RelaventParentActivity")
	WebElement relaventParentActivity;
	
	@FindBy(id = "SaveAdhocActivity")
	WebElement saveAdhocActivity;
	
	@FindBy(id = "RelaventChildActivities")
	WebElement relaventChildActivities;
	
	@FindBy(id = "SubActivityField")
	WebElement subActivityField;
	
	@FindBy(id = "NonCloseCalender")
	WebElement nonCloseCalender;
	
	//edit activity
	
	@FindBy(id = "EditActivityConfirm")
	WebElement editActivityConfirm;
	
	@FindBy(id = "EditActivity")
	WebElement editActivity;
	
	@FindBy(xpath = "//div[@id='EditActivityConfirm']//h4")
	WebElement editModalContent;
	
	@FindBy(xpath = "//div[@id='EditActivityConfirm']//input[@value='Yes']")
	WebElement yes;
	
	//add subactivity
	
	@FindBy(id = "AddSubActivityButton")
	WebElement addSubActivity;
	
	@FindBy(id = "cancelEditActivity")
	WebElement cancelEditActivity;
	
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
	
	public void clickOnActivityTab() {
		activityTab.click();
	}
	
	public String getActivityDescription() {
		return activityDescription.getText(); 
	}
	
	public void setActivityDescritption(String description) {
		if(!Strings.isNullOrEmpty(this.getActivityDescription())) {
			activityDescription.clear();
		}
		activityDescription.sendKeys(description);
	}
	
	public void selectFunction() {
		Select select = new Select(functionDropdown);
		List <WebElement> options = select.getOptions();
		int index = random.nextInt(options.size());
		select.selectByIndex(index);
	}
	
	public void selectTower() {
		Select select = new Select(towerDropdown);
		List <WebElement> options = select.getOptions();
		int index = random.nextInt(options.size());
		select.selectByIndex(index);
	}
	
	public void selectMonth(String value) {
		Select select = new Select(monthDropdown);
		select.selectByVisibleText(value);
	}
	
	public String getStartOnBizDay() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",startOnBizDay).toString();
	}
	
	public String getDueForClientBizDay() {
		return dueForClientBizDay.getAttribute("value");
	}
	
	public void setStartOnBizDay(String day) {
		if(!Strings.isNullOrEmpty(this.getStartOnBizDay())) {
			startOnBizDay.clear();
		}
		startOnBizDay.sendKeys(day);
	}
	
	public void setDueForClientBizDay(String day) {
		dueForClientBizDay.clear();
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
	
	public void setCheckListItemTwo(String value) {
		CheckListItemTwo.sendKeys(value);
	}
	
	public void clickOnReviwerTab() {
		reviwerTab.click();
	}
	
	public void selectReviewerOne(String value) {
		Select select = new Select(reviewerOne);
		select.selectByVisibleText(value);
	}
	
	public String getDueForReveiewOne() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",dueForReveiewOne).toString();
	}
	
	public String getReviewOneDuration() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",reviewOneDuration).toString();
	}
	
	public void setDueForReveiewOne(String value) {
		if(!Strings.isNullOrEmpty(this.getDueForReveiewOne())) {
			dueForReveiewOne.clear();
		}
		dueForReveiewOne.sendKeys(value);
	}
	
	public void setReviewOneDuration(String value) {
		if(!Strings.isNullOrEmpty(this.getReviewOneDuration())) {
			reviewOneDuration.clear();
		}
		reviewOneDuration.sendKeys(value);
	}
	
	public void selectReviewerTwo(String value) {
		Select select = new Select(reviewerTwo);
		select.selectByVisibleText(value);
	}
	
	public String getDueForReveiewTwo() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",dueForReveiewTwo).toString();
	}
	
	public String getReviewTwoDuration() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",reviewTwoDuration).toString();
	}
	
	public void setDueForReveiewTwo(String value) {
		if(!Strings.isNullOrEmpty(this.getDueForReveiewTwo())) {
			dueForReveiewTwo.clear();
		}
		dueForReveiewTwo.sendKeys(value);
	}
	
	public void setReviewTwoDuration(String value) {
		if(!Strings.isNullOrEmpty(this.getReviewTwoDuration())) {
			reviewTwoDuration.clear();
		}
		reviewTwoDuration.sendKeys(value);
	}
	
	public void clickOnSubmit() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", submit);
	}
	
	public void clickOnCancelAddActivity() {
		cancelAddActivity.click();
	}
	
	public void clickOnChecklistLink() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", checklistLink);
	}
	
	public void setActivityDetails(String description, String day, String dueForClientBizDay,String owner) {
		
		this.setActivityDescritption(description);
		this.selectFunction();
		this.selectTower();
    	this.setStartOnBizDay(day);
		this.setDueForClientBizDay(dueForClientBizDay);
		this.selectOwner(owner);
	}
	
	public boolean isChecklistAdded() {
		return isElementPresent(checkListItemTwoLabel,30);
	}
	
	public void clickOnAddReviewerLink() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addReviewerLink);
	}
	
	public boolean isReviewerTwoLabelExist() {
		return isElementPresent(reviewerTwoLabel,30);
	}
	
	public void setReviewerOneDetails(String reviewer, String day, String duration) {
		this.selectReviewerOne(reviewer);
		this.setDueForReveiewOne(day);
		this.setReviewOneDuration(duration);
	}
	
	public void setReviewerTwoDetails(String reviewer, String day, String duration) {
		this.selectReviewerTwo(reviewer);
		this.setDueForReveiewOne(day);
		this.setReviewOneDuration(duration);
	}
	
	//non close activity 
	
	public boolean isRecurrenceTabSelected() {
		return recurrenceTab.getAttribute("class").equals("active");
	}
	
	public boolean isRelaventParentActivity() {
		return isElementPresent(relaventParentActivity,40);
	}
	
	public boolean isRelaventChildActivities() {
		return isElementPresent(relaventChildActivities,40);
	}
	
	public boolean isSubActivityField() {
		return isElementPresent(subActivityField,40);
	}
	
	public void clickOnIsSubActivity() {
		isSubActivity.click();
	}
	
	public void clickOnValidateDueDate() {
		validateDueDate.click();
	}
	
	public void clickOnNonCloseCalender() {
		nonCloseCalender.click();
	}
	
	public void setNonCloseActivityDetails(String description, String day, String owner) {
		this.setActivityDescritption(description);
		this.selectFunction();
		this.selectTower();
		this.clickOnNonCloseCalender();
		selectDateFromCalendar(day);
		this.selectOwner(owner);
	}
	
	//Edit activity
	
	public boolean isConfirmModalExist() {
		return isElementPresent(editActivityConfirm,30);
	}
	
	public void clickOnEditActivity() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", editActivity);
	}
	
	public String getEditModalContent() {
		return editModalContent.getText().trim();
	}
	
	public void confirmEditModal(String content) {
		if(this.isConfirmModalExist() && this.getEditModalContent().equals(content)) {
			this.clickOnYes();
		}
	}
	
	public void clickOnYes() {
		yes.click();
	}
	
	public void clickOnAddSubActivity() {
		addSubActivity.click();
	}
	
	public void clickOnCancelEditActivity() {
		cancelEditActivity.click();
	}
	
	public void setEditActivityDetails(String description, String content) {
		if(this.isactivityModalExist()) {
			try {
				this.setActivityDescritption(description);
				this.clickOnEditActivity();
				this.confirmEditModal(content);
				this.waitUntilElementInvisible("//div[contains(@class,'addActivityDialogue')]", 30);
			} catch(Exception e) {
				this.clickOnCancelEditActivity();
			}
		}
	}
	
	public void setAddSubActivityDetails(String description) {
		if (this.isactivityModalExist()) {
			try {
				Thread.sleep(1000);
				activityDescription.sendKeys(description);
				this.clickOnAddSubActivity();
				this.waitUntilElementInvisible("//div[contains(@class,'addActivityDialogue')]", 30);
			} catch (Exception e) {
				this.clickOnCancelEditActivity();
				e.printStackTrace();
			}
		}
	}
	
}
