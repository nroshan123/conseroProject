package pageclass;

import java.util.List;

import org.openqa.selenium.By;
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
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr")
	public WebElement activityTable;
	
	@FindBy(id = "selectedActivityType")
	WebElement activityTypeDropdown;
	
	@FindBy(id = "selectedTower")
	WebElement towerDropdown;
	
	@FindBy(name = "activitiesTable_length")
	WebElement entriesDropdown;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr//td[1]")
	List<WebElement> activityList;
	
	@FindBy(id = "btnactivityheader")
	public WebElement btnActivityheader;
	
	@FindBy(id = "activitiesheadertable")
	WebElement activitiesHeadertable;
	
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
	
	public String getActivityTable() {
		return activityTable.getText();
	}
	
	public void selctShowEntries() {
		select = new Select(entriesDropdown);
		List<WebElement> options = select.getOptions();
		int index = 0;
		if(options.size()>1){
		    index = random.nextInt(options.size()-1);
		 }
		 if (index >= 0){
			 select.selectByIndex(index);
		 }
	}
	
	public String getSelectedEntries() {
		select = new Select(entriesDropdown);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getLastPageCount() {
		int paginationCount = pagination.size()-2;
		WebElement lastPageNo = driver.findElement(By.xpath("//div[@id='activitiesTable_paginate']//span//a[" + paginationCount + "]"));
		return lastPageNo.getText();
	}
	
	public int getActivityCount() {
		return activityList.size();
	}
	
	public void clickOnBtnActivityheader() {
		btnActivityheader.click();
	}
	
	public boolean isActivitiesHeadertableVisible() {
		return activitiesHeadertable.isDisplayed();
	}
	
	public boolean checkDownloadFileButton() {
		if(isElementPresent(csv, 30) && isElementPresent(excel, 30) && isElementPresent(pdf, 30)) {
			return true;
		}
		return false;
	}
	
	public boolean isclearFilterExist() {
		return isElementPresent(clearFilter, 40);
	}
}
