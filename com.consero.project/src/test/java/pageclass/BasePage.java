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
  	
  	@FindBy(xpath = "//div[contains(@class,'ui-dialog')]")
  	WebElement uiDialog;
  	
  	@FindBy(xpath = "//div[contains(@class,'ui-dialog')]//div[contains(@class,'ui-dialog-content') and (contains(@id,'ui-id'))]")
  	WebElement uiDialogContent;
  	
  	@FindBy(xpath = "//div[@class='ui-dialog-buttonset']//span[text()='Yes']")
  	WebElement confirmUiDialog;
  	
   //toaster
	
  	@FindBy(xpath = "//div[@id='toast-container']")
  	WebElement toastContainer;
  	
  	@FindBy(xpath = "//div[@id='toast-container']//div[@class='toast toast-success']")
  	WebElement toastSuccessContainer;
  	
  	@FindBy(xpath = "//div[@id='toast-container']//div[@class='toast toast-warning']")
  	WebElement toastWarningContainer;
  	
  	@FindBy(xpath = "//div[@id='toast-container']//div[@class='toast toast-error']")
  	WebElement toastErrorContainer;
  	
  	@FindBy(xpath = "//div[@id='toast-container']//div[@class='toast toast-success']//div[@class='toast-message']")
  	WebElement toastSuccessMessage;
  	
  	@FindBy(xpath = "//div[@id='toast-container']//div[@class='toast toast-error']//div[@class='toast-message']")
  	WebElement toastErrorMessage;
  	
  	@FindBy(xpath = "//div[@id='toast-container']//div[@class='toast toast-warning']//div[@class='toast-message']")
  	WebElement toasterWarningMessage;
  	
  	@FindBy(xpath = "//div[@class='toaster-height']")
  	WebElement toastContent;
	
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
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmUiDialog);
	}

	public void handleUiDialog(String content) {
		if (this.isElementPresent(uiDialogContent, 40)) {
			if (uiDialogContent.getText().contains(content)) {
				this.clickOnConfirmDialog();
			}
		}
	}
	
	// toaster methods

	public boolean checkToasterExists() {
		boolean isToasterExist = false;
		if (this.isElementPresent(toastContainer, 60)) {
			isToasterExist = true;
		}
		return isToasterExist;
	}

	public boolean checkWarningToasterExists() {
		boolean isToasterExist = false;
		if (this.isElementPresent(toastWarningContainer, 60)) {
			isToasterExist = true;
		}
		return isToasterExist;
	}

	public boolean checkSuccessToasterExists() {
		boolean isToasterExist = false;
		if (this.isElementPresent(toastSuccessContainer, 30)) {
			isToasterExist = true;
		}
		return isToasterExist;
	}

	public boolean checkErrorToasterExists() {
		boolean isToasterExist = false;
		if (this.isElementPresent(toastErrorContainer, 30)) {
			isToasterExist = true;
		}
		return isToasterExist;
	}

	public String getToastSuccessMessage() {
		return toastSuccessMessage.getText();
	}

	public String getToastErrorMessage() {
		return toastErrorMessage.getText();
	}

	public String getToastWarningMessage() {
		return toasterWarningMessage.getText();
	}

	public boolean checkSuccessMessage(String msg) {
		if (this.checkSuccessToasterExists() && this.getToastSuccessMessage().equals(msg)) {
			return true;
		}
		return false;
	}

	public boolean checkErrorMessage(String msg) {
		if (this.checkErrorToasterExists() && this.getToastErrorMessage().equals(msg)) {
			return true;
		}
		return false;
	}

	public boolean checkWarningMessage(String msg) {
		if (this.checkWarningToasterExists() && this.getToastWarningMessage().contains(msg)) {
			return true;
		}
		return false;
	}

}
