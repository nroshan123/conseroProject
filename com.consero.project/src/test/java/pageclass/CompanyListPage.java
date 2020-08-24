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

public class CompanyListPage extends BasePage{
	
	WebDriver driver;

	@FindBy(id = "companyListViewGrid")
	public WebElement companyListTable;
	
	@FindBy(xpath = "//a[text()='Create A New Company']")
	WebElement createCompany;
	
	@FindBy(id = "companyListSearch")
	WebElement companySearch;
	
	@FindBy(xpath = "//table[@id='companiesTable']//tbody//tr")
	List<WebElement> companiesInList;
	
	@FindBy(xpath = "//div[@id='companyListViewGrid']//div[@ref='eBodyViewport']//div[@col-id='Name']")
	List<WebElement> companyNameInList;
	
	@FindBy(xpath = "//div[@id='companyListViewGrid']//div[@ref='eBodyViewport']//div[@col-id='AddressLineOne']")
	WebElement addressOneInList;
	
	@FindBy(xpath = "//div[@id='companyListViewGrid']//div[@ref='eBodyViewport']//div[@col-id='AddressLineTwo']")
	WebElement addressTwoInList;
	
	@FindBy(xpath = "//div[@id='companyListViewGrid']//div[@ref='eBodyViewport']//div[@col-id='Action']//a[text()='Edit']")
	WebElement edit;
	
	@FindBy(xpath = "//div[@id='companyListViewGrid']//div[@ref='eBodyViewport']//div[@col-id='Action']//a[contains(text(),'Details')]")
	WebElement details;
	
	@FindBy(xpath = "//div[@id='companyListViewGrid']//div[@ref='eBodyViewport']//div[@col-id='IsInactive']//a[text()='Deactivate']")
	WebElement deactivate;
	
	@FindBy(id = "btnActiveEntities")
	WebElement activeClients;
	
	@FindBy(id = "btnInActiveEntities")
	WebElement inactiveClients;
	
	@FindBy(id = "btnAllEntities")
	WebElement allClients;
	
	@FindBy(name = "companiesTable_length")
	WebElement companyCountDropdown;
	
	@FindBy(id = "companiesTable_last")
	WebElement last;
	
	@FindBy(id = "companiesTable_previous")
	WebElement previous;
	
	@FindBy(id = "companiesTable_next")
	WebElement next;
	
	@FindBy(id = "companiesTable_first")
	WebElement first;
	
	
	public CompanyListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getCompanySearch() {
		return ((JavascriptExecutor) driver).executeScript("return arguments[0].value;",companySearch).toString();
	}
	
	public void setCompanySearch(String value) {
		if(isElementPresent(companySearch, 60)) {
			if(!Strings.isNullOrEmpty(this.getCompanySearch())) {
				companySearch.clear();
			}
			companySearch.sendKeys(value);
		}
	}
	
	public void clickOnCreateCompany() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", createCompany);
	}
	
	public boolean isCompanyExist(String name) {
		for(WebElement companyName:companyNameInList) {
			if(companyName.getText().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void clickOnEditCompany() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", edit);
	}
	
	public void clickOnDetails() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", details);
	}
	
	public void clickOnDeactivate() {
		deactivate.click();
	}
	
	public void clickOnActiveClients() {
		activeClients.click();
	}
	
	public void clickOnInactiveClients() {
		inactiveClients.click();
	}
	
	public void clickOnAllClients() {
		allClients.click();
	}
	
	public boolean isActiveClientSelected() {
		if(activeClients.getAttribute("class").contains("active")) {
			return true;
		}
		return false;
	}
	
	public String getSelectedEntries() {
		select = new Select(companyCountDropdown);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getLastPageCount() {
		int paginationCount = pagination.size()-2;
		WebElement lastPageNo = driver.findElement(By.xpath("//div[contains(@class,'dataTables_paginate')]//a[" + paginationCount + "]"));
		return lastPageNo.getText();
	}
	
	public void selectCompanyEntries() {
		select = new Select(companyCountDropdown);
		List<WebElement> options = select.getOptions();
		int index = 0;
		if(options.size()>1){
		    index = random.nextInt(options.size()-1);
		 }
		 if (index >= 0){
			 select.selectByIndex(index);
		 }
	}
	
	public int getCompanyCount() {
		return companiesInList.size();
	}
	
	public String getAddressOne() {
		return addressOneInList.getText();
	}
	
	public String getAddressTwo() {
		return addressTwoInList.getText();
	}
	
	public boolean isAddressUpdated(String address1, String address2) {
		if(this.getAddressOne().equals(address1) && this.getAddressTwo().equals(address2)) {
			return true;
		}
		return false;
	}
	
}
