package pageclass;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	WebDriver driver;
	WebDriverWait d_wait;
	Actions action;
	Select select;
	
    Random random = new Random();
    
  //pagination 
	
  	@FindBy(xpath = "//div[contains(@class,'dataTables_paginate')]//a[contains(@class,'paginate_button') and not(contains(@class,'disabled'))]")
  	public List<WebElement> pagination;
  	
  	@FindBy(xpath = "//div[contains(@class,'dataTables_paginate')]//a[text()='Previous']")
  	public WebElement previousButton;
  	
  	@FindBy(xpath = "//div[contains(@class,'dataTables_paginate')]//a[text()='Next']")
  	public WebElement nextButton;
  	
  	@FindBy(xpath = "//div[contains(@class,'dataTables_paginate')]//a[text()='First']")
  	public WebElement firstButton;
  	
  	@FindBy(xpath = "//div[contains(@class,'dataTables_paginate')]//a[text()='Last']")
  	public WebElement lastButton;
  	
    //ui-dialog
  	
  	@FindBy(xpath = "//div[contains(@class,'ui-dialog']")
  	WebElement uiDialog;
  	
  	@FindBy(xpath = "//div[contains(@class,'ui-dialog']")
  	WebElement uiDialogContent;
  	
  	@FindBy(xpath = "//div[@class='ui-dialog-buttonset']//span[text()='Yes']")
  	WebElement confirmUiDialog;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isElementPresent(WebElement element, int timeout) {
		try {
			d_wait = new WebDriverWait(driver, timeout);
			d_wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void waitElementVisible(WebElement element,int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(element));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void waitUntilElementInvisible(String locatorKey,int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locatorKey)));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void waitUntilClickable(WebElement element,int timeout) {
		try {
			WebDriverWait d_wait=new WebDriverWait(driver,timeout);
			d_wait.until(ExpectedConditions.elementToBeClickable(element));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//pagination function
	
	public void clickOnPagination() {
		try {
			for (int i = 0; i < pagination.size(); i++) {
				if (!pagination.get(i).getAttribute("class").contains("disabled")) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", pagination.get(i));
					Thread.sleep(2000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void clickOnPreviousPagination() {
		try {
			for (int i = 1; i < pagination.size(); i++) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", previousButton);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void clickOnNextPagination() {
		try {
			for (int i = 1; i < pagination.size(); i++) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextButton);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnFirstPagination() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstButton);
	}

	public void clickOnLastPagination() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", lastButton);
	}

	// ui-dialog function

	public void clickOnConfirmDialog() {
		confirmUiDialog.click();
	}

	public void handleUiDialog(String content) {
		if (this.isElementPresent(uiDialog, 40)) {
			if (uiDialogContent.getText().contains(content)) {
				this.clickOnConfirmDialog();
			}
		}
	}
		
}
