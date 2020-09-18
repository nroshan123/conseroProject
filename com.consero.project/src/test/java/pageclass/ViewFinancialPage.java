package pageclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewFinancialPage extends BasePage {
	
	@FindBy(xpath= "//div[@id='cmsActivityFinancialHeader']//h4[contains(text(),'Generate Validate and Review Financials')]")
	WebElement viewFinancialPageTitle;

	@FindBy(xpath = "//button[text()='Verify Path for GDrive']")
	WebElement verifyPathForGDrive;
	
	@FindBy(xpath = "//a[text()='Activity Detail']")
	WebElement activityDetail;

	@FindBy(xpath = "//a[contains(text(),'Re-Generate Financials')]")
	WebElement regenerateFinancial;
	
	@FindBy(xpath = "//span[contains(text(),'Highlights')]")
	WebElement hignlights;
	
	@FindBy(xpath = "//div[contains(@class,'intacctValidationViewTab')]")
	WebElement intacctValidation;
	
	@FindBy(xpath = "//div[contains(@class,'reportValidationViewTab')]")
	WebElement reportValidation;
	
	@FindBy(xpath = "//div[@id='GDriveErrorBox']//div[@class='modal-dialog']")
	WebElement gDriveModal;
	
	@FindBy(xpath = "//div[@id='GDriveErrorBox']//div[contains(@class,'GDriveModalError')]")
	WebElement gDrivePathError;
	
	@FindBy(xpath = "//div[@id='GDriveErrorBox']//a[text()='OK']")
	WebElement acceptGdriveModal;
	
	@FindBy(id = "financialValidationLi")
	WebElement validationSummaryTab;
	
	@FindBy(id = "intacctTotalErrorCount")
	WebElement intacctTotalErrorCount;
	
	@FindBy(id = "reportValTotalErrorCount")
	WebElement reportTotalErrorCount;
	
	@FindBy(id = "totalValidationErrorCount")
	WebElement totalValidationErrorCount;
	
	@FindBy(id = "CompleteValidation")
	WebElement completeValidation;
	
	@FindBy(id = "SubmitDraft")
	WebElement submitDraft;
	
	@FindBy(id = "RejectSubmitDraft")
	WebElement reject;
	
	@FindBy(id = "ApproveDraft")
	WebElement approveDraft;
	
	@FindBy(id = "RejectDraft")
	WebElement rejectDraft;
	
	@FindBy(id = "PublishDraft")
	WebElement publishDraft;
	
	@FindBy(xpath = "//div[@id='excelWorkBookInProgressFinancialModal']//div[@class='modal-dialog']")
	WebElement progressFinancialModal;
	
	@FindBy(xpath = "//div[contains(@class,'publishReloadFinancialModalError')]")
	WebElement publishFinancialError;
	
	@FindBy(xpath = "//div[@class='modal-dialog']//a[text()='OK']")
	WebElement ok;
	
	
	
	public ViewFinancialPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isViewFinancialPageTitleExist() {
		return isElementPresent(viewFinancialPageTitle, 30);
	}
	
	public void clickOnActivityDetail() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", activityDetail);
	}
	
	public void clickOnVerifyPathForGDrive() {
		verifyPathForGDrive.click();
	}
	
	public boolean isVerifyPathForGDriveExist() {
		return isElementPresent(verifyPathForGDrive, 30);
	}
	
	public boolean isGDrivePathErrorExist() {
		return isElementPresent(gDrivePathError,30);
	}
	
	public String getGDrivePathError() {
		return gDrivePathError.getText();
	}
	
	public void clickOnAcceptGdriveModal() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", acceptGdriveModal);
	}
	
	public void clickOnRegenerateFinancial() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", regenerateFinancial);
	}
	
	public void clickOnHignlights() {
		hignlights.click();
	}
	
	public void clickOnIntacctValidation() {
		intacctValidation.click();
	}
	
	public void clickOnReportValidation() {
		reportValidation.click();
	}
	
	public boolean isValidationButtonExist() {
		if(isElementPresent(intacctValidation,30) && isElementPresent(reportValidation,30)) {
			return true;
		}
		return false;
	}
	
	public boolean isgDriveModalExist() {
		return isElementPresent(gDriveModal, 30);
	}
	
	public String getErrorMsg() {
		return gDrivePathError.getText();
	}
	
	public boolean isValidationSummaryTabSelected()  {
		if(validationSummaryTab.getAttribute("class").equals("active")) {
			return true;
		}
		return false;
	}
	
	public String getIntacctTotalErrorCount() {
		return intacctTotalErrorCount.getText();
	}
	
	public String getReportTotalErrorCount() {
		return reportTotalErrorCount.getText();
	}
	
	public String getTotalValidationErrorCount() {
		return totalValidationErrorCount.getText();
	}
	
	public boolean isCompleteValidationEnabled() {
		return completeValidation.isEnabled();
	}
	
	public boolean isCompleteValidationButtonExist() {
		return isElementPresent(completeValidation,30);
	}
	
	public void clickOnCompleteValidation() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", completeValidation);
	}
	
	public void clickOnSubmitDraft() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", submitDraft);
	}
	
	public void clickOnReject() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", reject);
	}
	
	public boolean isRejectButtonExist() {
		return isElementPresent(reject, 30);
	}
	
	public boolean isSubmitDraftButtonExist() {
		return isElementPresent(submitDraft, 30);
	}
	
	public boolean isValidateFinancialButtonExist() {
		if(this.isVerifyPathForGDriveExist() && isElementPresent(regenerateFinancial,30) && isElementPresent(completeValidation,30)) {
			return true;
		}
		return false;
	}
	
	public void clickOnApproveDraft() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", approveDraft);
	}
	
	public boolean isApproveDraftButtonExist() {
		return isElementPresent(approveDraft, 30);
	}
	
	public boolean isRejectDraftButtonExist() {
		return isElementPresent(rejectDraft, 30);
	}
	
	public void clickOnRejectDraft() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", rejectDraft);
	}
	
	public boolean isPublishDraftButtonExist() {
		return isElementPresent(publishDraft, 30);
	}
	
	public void clickOnPublishDraft() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", publishDraft);
	}
	
	public boolean isProgressFinancialModalExist() {
		return isElementPresent(progressFinancialModal, 30);
	}
	
	public boolean isPublishFinancialErrorExist() {
		return isElementPresent(publishFinancialError, 30);
	}
	
	public String getpublishFinancialError() {
		return publishFinancialError.getText();
	}
	
	public void clickOnOk() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", ok);
	}
	
}



