package pageclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.util.Strings;

import com.relevantcodes.extentreports.LogStatus;

public class RecurranceManagementPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(), 'Recurrences')]")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_filter']//input[@type='search']")
	WebElement search;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr//td[9]")
	List<WebElement> status;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr[1]//td[3]")
	WebElement activtyName;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr//td[@class='dataTables_empty']")
	public WebElement recurranceTable;
	
	@FindBy(name = "activityRecurrenceTable_length")
	WebElement entriesDropdown;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr//td[1]")
	List<WebElement> recurranceList;
	
	@FindBy(xpath = "//button[contains(@class,'buttons-csv')]")
	WebElement csv;
	
	@FindBy(xpath = "//button[contains(@class,'buttons-excel')]")
	WebElement excel;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//thead//th[contains(text(),'Activity Name')]")
	WebElement activityName;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr//td[3]")
	List<WebElement> activityNames;
	
	@FindBy(xpath = "//input[@id='cancelEditActivity']")
	WebElement cancelRecurrance;
	
	@FindBy(id = "recurrenceEditActivityDialogue")
	WebElement recurranceModal;
	
	@FindBy(id = "EditActivity")
	WebElement editActivity;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr[1]//td//a[text()='Edit']")
	WebElement edit;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr[1]//td//a[text()='View']")
	WebElement view;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr[1]//td//a[text()='End']")
	WebElement end;
	
	@FindBy(id = "EditActivityConfirm")
	WebElement editActivityConfirm;
	
	@FindBy(xpath = "//div[@id='EditActivityConfirm']//h4")
	WebElement editModalContent;
	
	@FindBy(xpath = "//div[@id='EditActivityConfirm']//input[@value='Yes']")
	WebElement yes;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr[1]//td[3]")
	WebElement activity;
	
	public RecurranceManagementPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getSearch() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",search).toString();
	}
	
	public void clearSearch() {
		search.clear();
	}
	
	public void setSearch(String value) {
		if(!Strings.isNullOrEmpty(this.getSearch())) {
			this.clearSearch();
		}
		search.sendKeys(value);
	}
	
	public boolean isReccurranceSearched(String name) {
		for(WebElement activityName: activityNames) {
			if(!activityName.getText().contains(name)) {
				return false;
			}
		}
		return true;
	}
	
	public String getActivtyName() {
		return activtyName.getText().trim();
	}
	
	public String getSelectedEntries() {
		select = new Select(entriesDropdown);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getLastPageCount() {
		WebElement lastPageNo = driver.findElement(By.xpath("//div[@id='activityRecurrenceTable_paginate']//span//a[last()]"));
		return lastPageNo.getText();
	}
	
	public int getRecurranceCount() {
		return recurranceList.size();
	}
	
	public void selectShowEntries() {
		select = new Select(entriesDropdown);
		select.selectByVisibleText("25");
	}
	
	public void clickOnCsv() {
		csv.click();
	}
	
	public void clickOnExcel() {
		excel.click();
	}
	
	public boolean checkExportFileButton() {
		if(isElementPresent(csv, 30) && isElementPresent(excel, 30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnActivityName() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", activityName);
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", activityName);
	}

	public boolean isFieldNameAscendingOrder() {
		List<String> list = new ArrayList<String>();
		for (WebElement activityName : activityNames) {
			list.add(activityName.getText());
		}
		List<String> tempList= list;
		Collections.sort(tempList);
		if(tempList.equals(list)) {
			return true;
		}
		return false;
	}
	
	public boolean isFieldNameDescendingOrder() {
		List<String> list = new ArrayList<String>();
		for (WebElement activityName : activityNames) {
			list.add(activityName.getText());
		}
		List<String> tempList= list;
		Collections.sort(tempList, Collections.reverseOrder());
		if(tempList.equals(list)) {
			return true;
		}
		return false;
	}
	
	public void clickOnView() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", view);
	}
	
	public void clickOnEdit() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", edit);
	}
	
	public void clickOnEnd() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", end);
	}
	
	public void clickOnEditActivity() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", editActivity);
	}
	
	public boolean isRecurranceModalExist() {
		return isElementPresent(recurranceModal, 30);
	}
	
	public void clickOnCancelRecurrance() {
		cancelRecurrance.click();
	}
	
	public boolean isConfirmModalExist() {
		return isElementPresent(editActivityConfirm, 30);
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
	
	public void editRecurranceDetails(String content) {
		if(this.isRecurranceModalExist()) {
			this.confirmEditModal(content);
		}
	}
	
	public String getActivity() {
		return activity.getText().trim();
	}
	
	public boolean isActivityEnded(String name) {
		for(int i=0; i<activityNames.size(); i++) {
			if(activityName.getText().equals(name)) {
				WebElement status = driver.findElement(By.xpath(""));
				if(status.getText().trim().equals("Terminated")) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
