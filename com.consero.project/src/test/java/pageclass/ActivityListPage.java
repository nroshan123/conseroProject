package pageclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ActivityListPage extends BasePage{
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and text() = 'ControlDock']")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//div[@id='activitiesTable_filter']//input[@type='search']")
	WebElement search;
	
	@FindBy(xpath = "//button[text()='Clear Filter']")
	WebElement clearFilter;
	
	@FindBy(id = "ToolTables_activitiesTable_0")
	WebElement csv;
	
	@FindBy(id = "ToolTables_activitiesTable_1")
	WebElement excel;
	
	@FindBy(id = "ToolTables_activitiesTable_2")
	WebElement pdf;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr[1]//td//a[@class='editActivityLink']")
	WebElement edit;
	
	@FindBy(id = "selectedActivityType")
	WebElement activityTypeDropdown;
	
	@FindBy(id = "selectedTower")
	WebElement towerDropdown;
	
	public ActivityListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnCsv() {
		csv.click();
	}
	
	public void clickOnExcel() {
		excel.click();
	}
	
	public void clickOnPdf() {
		pdf.click();
	}
	
	public void clickOnEdit() {
		edit.click();
	}
	
	public void selectActivityType(String value) {
		Select select = new Select(activityTypeDropdown);
		select.selectByVisibleText(value);
	}
	
	public void selectTower(String value) {
		Select select = new Select(towerDropdown);
		select.selectByVisibleText(value);
	}
	
	public void clickOnClearFilter() {
		clearFilter.click();
	}
	
	public void setSearch(String value) {
		search.sendKeys(value);
	}
}
