package pageclass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.util.Strings;

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
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr//td[@class='dataTables_empty']")
	public WebElement activityTable;
	
	@FindBy(xpath = "//table[@id='activitiesheadertable']//td[@class='activitySelectedAssignedTo']//span[@class='multiselect-selected-text']")
	WebElement assignee;
	
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
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr[1]//td[4]//a")
	WebElement activityDetails;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr//td[4]//a")
	List<WebElement> activities;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr[1]//td[2]")
	WebElement function;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr[1]//td[1]")
	WebElement companyName;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr[1]//td[6]")
	WebElement assignedTo;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr[1]//td[5]")
	WebElement status;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr//td//button[contains(@class,'hasNotesButton')]/i[@title='Notes']")
	WebElement noteButton;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//div[contains(@class,'note-shadow')]")
	WebElement noteContainer;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr//td//i[@class='fa fa-sitemap']")
	WebElement treeIcon;
	
	@FindBy(id = "LoadActivityTreeView")
	WebElement activityTreeModal;
	
	@FindBy(xpath = "//div[@id='treeViewStructureTable_filter']//input[@type='search']")
	public WebElement searchActivityInTree;
	
	@FindBy(xpath = "//table[@id='treeViewStructureTable']//tbody//tr[1]//td[2]//a")
	WebElement subActivity;
	
	@FindBy(xpath = "//div[@id='LoadActivityTreeView']//button[contains(@class,'buttons-excel')]")
	WebElement excelTreeView;
	
	@FindBy(xpath = "//div[@id='LoadActivityTreeView']//button[contains(@class,'buttons-csv')]")
	WebElement csvTreeView;
	
	@FindBy(id = "btnBackToActivityIndex")
	WebElement btnBackToActivity;
	
	@FindBy(xpath = "//select[@id='SelectedClient']//following-sibling::div")
	WebElement clientDropdown;
	
	@FindBy(xpath = "//td[@class='activitySelectedClient']//ul[contains(@class,'dropdown-menu')]//input[contains(@class,'multiselect-search')]")
	WebElement searchClient;
	
	@FindBy(xpath = "//td[@class='activitySelectedClient']//ul[contains(@class,'dropdown-menu')]//li")
	List<WebElement> clientOptions;
	
	@FindBy(xpath = "//select[@id='SelectedClient']//following-sibling::div/ul//li[contains(@class,'multiselect-all')]")
	WebElement selectAllClient;
	
	@FindBy(xpath = "//select[@id='SelectedClient']//following-sibling::div/button[contains(@class,'dropdown-toggle')]//span[@class='multiselect-selected-text']")
	WebElement selectedClient;
	
	@FindBy(xpath = "//table[@id='activitiesTable']//tbody//tr//td[5]")
	List<WebElement> clientStatus;
	
	@FindBy(id = "filterClientActivities")
	WebElement filterClientActivities;
	
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
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", edit);
	}
	
	public String getAssignee() {
		return assignee.getText();
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
	
	public String getSearch() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",search).toString();
	}
	
	public void setSearch(String value) {
		if(!Strings.isNullOrEmpty(this.getSearch())) {
			search.clear();
		}
		search.sendKeys(value);
	}
	
	public void selctShowEntries() {
		select = new Select(entriesDropdown);
		select.selectByVisibleText("25");
	}
	
	public String getSelectedEntries() {
		select = new Select(entriesDropdown);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getLastPageCount() {
		WebElement lastPageNo = driver.findElement(By.xpath("//div[@id='activitiesTable_paginate']//span//a[last()]"));
		return lastPageNo.getText();
	}
	
	public int getActivityCount() {
		return activityList.size();
	}
	
	public void clickOnBtnActivityheader() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", btnActivityheader);
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
	
	public void clickOnActivityDetails() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", activityDetails);
	}
	
	public String getActivityDetails() {
		return activityDetails.getText();
	}
	
	public String getFunction() {
		return function.getText();
	}
	
	public String getCompanyName() {
		return companyName.getText();
	}
	
	public String getAssignedTo() {
		return assignedTo.getText();
	}
	
	public String getStatus() {
		return status.getText();
	}
	
	public boolean isActivityTableExist() {
		return isElementPresent(activityTable,40);
	}
	
	public boolean isActivitiesExist(String name) {
		for(WebElement activity:activities) {
			if(activity.getText().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isNoteButtonExist() {
		return isElementPresent(noteButton,60);
	}
	
	public void clickOnNoteButton() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", noteButton);
	}
	
	public boolean isNoteContainerExist() {
		return isElementPresent(noteContainer,60);
	}
	
	public boolean isTreeIconExist() {
		return isElementPresent(treeIcon,60);
	}
	
	public void clickOnTreeIcon() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", treeIcon);
	}
	
	public boolean isActivityTreeModalExist() {
		return isElementPresent(activityTreeModal,60);
	}
	
	public String getSearchActivityInTree() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",searchActivityInTree).toString();
	}
	
	public void setSearchActivityInTree(String activity) {
		if(!Strings.isNullOrEmpty(this.getSearchActivityInTree())) {
			searchActivityInTree.clear();
		}
		searchActivityInTree.sendKeys(activity);
	}
	
	public String getSubActivity() {
		return subActivity.getText();
	}
	
	public void clickOnExcelTreeView() {
		excelTreeView.click();
	}
	
	public void clickOnCsvTreeView() {
		csvTreeView.click();
	}
	
	public void clickOnBtnBackToActivity() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", btnBackToActivity);
	}
	
	public void clickOnClientDropdown() {
		clientDropdown.click();
	}
	
	public void setSearchClient(String value) {
		searchClient.sendKeys(value);
	}
	
	public void selectClient(String client) {
		for(WebElement clientOption:clientOptions) {
			if(clientOption.getText().equals(client)) {
				clientOption.click();
				break;
			}
		}
	}
	
	public String getSelectedClient() {
		return selectedClient.getText();
	}
	
	public void clickOnSelectAllClient() {
		selectAllClient.click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click()", selectAllClient);
	}
	
	public void searchAndSelectClient(String client) {
		try {
			this.clickOnClientDropdown();
			Thread.sleep(2000);
			this.clickOnSelectAllClient();
			this.clickOnSelectAllClient();
			Thread.sleep(2000);
			this.setSearchClient(client);
			this.selectClient(client);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isClientSelected(String client) {
		if(this.getSelectedClient().equals(client)) {
			return true;
		}
		return false;
	}
	
	public boolean clickOnActivity(String status) {
		for(int i=0; i<clientStatus.size(); i++) {
			if(clientStatus.get(i).getText().equals(status)) {
				System.out.println(status);
				WebElement activity = driver.findElement(By.xpath("//table[@id='activitiesTable']//tbody//tr["+(i+1)+"]//td[4]//a"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", activity);
				System.out.println("clicked");
				return true;
			}
		}
		return false;
	}
	
	public void clickOnFilterClientActivities() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", filterClientActivities);
	}
	
}
