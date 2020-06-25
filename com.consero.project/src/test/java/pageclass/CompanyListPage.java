package pageclass;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompanyListPage extends BasePage{
	
	WebDriver driver;

	@FindBy(xpath = "//div[@class='dataTables_scroll']")
	public WebElement companyListTable;
	
	@FindBy(xpath = "//div[@id='companiesTable_filter']//input[@type='search']")
	WebElement companySearch;
	
	@FindBy(xpath = "//div[@class='dataTables_scroll']//table//tbody//tr//td[2]")
	List<WebElement> companyNameInList;
	
	public CompanyListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setCompanySearch(String value) {
		if(isElementPresent(companySearch, 60)) {
			companySearch.sendKeys(value);
		}
	}
	
	public boolean isCompanyExist(String name) {
		for(WebElement companyName:companyNameInList) {
			if(companyName.getText().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
}
