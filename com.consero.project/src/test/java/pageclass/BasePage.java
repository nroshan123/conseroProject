package pageclass;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
}
