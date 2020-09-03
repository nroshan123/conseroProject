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

public class RecurranceManagementPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//*[@class='page-title' and contains(text(), 'Recurrences')]")
	public WebElement pageTitle;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_filter']//input[@type='search']")
	WebElement search;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr//td[9]")
	WebElement status;
	
	@FindBy(xpath = "//div[@id='activityRecurrenceTable_wrapper']//table[@id='activityRecurrenceTable']//tbody//tr//td")
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
	
	public RecurranceManagementPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
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
	
	public String getSelectedEntries() {
		select = new Select(entriesDropdown);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getLastPageCount() {
		int paginationCount = pagination.size()-2;
		WebElement lastPageNo = driver.findElement(By.xpath("//div[@id='activityRecurrenceTable_paginate']//a[" + paginationCount + "]"));
		return lastPageNo.getText();
	}
	
	public int getRecurranceCount() {
		return recurranceList.size();
	}
	
	public void selectShowEntries() {
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
	
	public void clickOnCsv() {
		csv.click();
	}
	
	public void clickOnExcel() {
		excel.click();
	}
	
	public void clickOnActivityName() {
		activityName.click();
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
	
}
