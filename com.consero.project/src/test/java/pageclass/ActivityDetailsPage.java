package pageclass;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.util.Strings;

public class ActivityDetailsPage extends BasePage {
	
	WebDriver driver;

	@FindBy(xpath = "//h5[@class='page-title']")
	WebElement pageTitle;
	
	@FindBy(xpath = "//a[text()='Back to Activities']")
	WebElement backToActivities;
	
	@FindBy(id = "assignedToLabel")
	WebElement assignToLevel;
	
	@FindBy(xpath = "//div[@id='titleDiv']//h4")
	WebElement activityName;
	
	@FindBy(id = "flowTable")
	WebElement activityFlowTable;
	
	@FindBy(xpath = "//form[contains(@class,'editableform')]//select")
	WebElement assignedToDropdown;
	
	@FindBy(xpath = "//form[contains(@class,'editableform')]//button[contains(@class,'editable-submit')]")
	WebElement saveAssignedTo;
	
	@FindBy(xpath = "//div[@class='stats']//div[1]//span[contains(@class,'stat-value')]")
	WebElement status;
	
	@FindBy(id = "submitStart")
	WebElement start;
	
	@FindBy(id = "submitMarkAsComplete")
	WebElement markAsComplete;
	
	@FindBy(id = "submitRecall")
	WebElement recall;
	
	@FindBy(xpath = "//table[@id='flowTable']//tbody//tr//td[1]")
	List<WebElement> activityFlows;
	
	//notes
	
	@FindBy(id = "notesMainDiv")
	WebElement notesMainDiv;
	
	@FindBy(xpath = "//span[contains(@class,'dashboard_Comment')]//a[text()='Add']")
	WebElement addNote;
	
	@FindBy(xpath = "//div[@class='popover-content']")
	WebElement addNotePopover;
	
	@FindBy(id = "addNoteTextArea")
	WebElement note;
	
	@FindBy(xpath = "//div[@class='popover-content']//button[text()='Add']")
	WebElement add;
	
	@FindBy(id = "ActivityNotes")
	WebElement noteDiv;
	
	@FindBy(xpath = "//div[@id='ActivityNotes']//div[contains(@class,'commentColumn')]//span[contains(@class,'note-text')]")
	List<WebElement> notes;
	
	@FindBy(id = "editNoteTextArea")
	WebElement editNoteTextArea;
	
	@FindBy(xpath = "//div[@class='popover-content']//button[text()='Update']")
	WebElement update;
	
	@FindBy(xpath = "//div[@id='ActivityNotes']//div[contains(@class,'commentColumn')][1]//span[@class='edit edit_Comment']/a")
	WebElement edit;
	
	@FindBy(xpath = "//div[@id='ActivityNotes']//div[contains(@class,'commentColumn')][1]//span[contains(@class,'note-text')]")
	WebElement updatedNote;
	
	//document 
	
	@FindBy(id = "documentsMainDiv")
	WebElement documentsMainDiv;
	
	@FindBy(xpath = "//div[@id='documentsMainDiv']//a[text()='Upload']")
	WebElement uploadDocument;
	
	@FindBy(id = "uplodDocumentDiv")
	WebElement uploadModal;
	
	@FindBy(xpath = "//div[@id='uplodDocumentDiv']//div[contains(@class,'upload-modal-header')]")
	WebElement uploadModalHeader;
	
	@FindBy(id = "documentUpload")
	WebElement document;
	
	@FindBy(xpath = "//div[@id='uplodDocumentDiv']//button[@type='button' and text()='Upload']")
	WebElement upload;
	
	@FindBy(xpath = "//div[@id='ActivityDocuments']//table")
	WebElement documentTable;
	
	@FindBy(xpath = "//div[@id='ActivityDocuments']//table//tbody//tr[1]//td//a")
	WebElement documentName;
	
	@FindBy(xpath = "//div[@id='ActivityDocuments']//table//tbody//tr//td//a")
	List<WebElement> documentNames;
	
	@FindBy(xpath = "//div[@id='ActivityDocuments']//table//tbody//tr[1]//td//i[@class='fa fa-trash']")
	WebElement deleteDocument;
	
	@FindBy(xpath = "//input[@value='Link']")
	WebElement link;
	
	@FindBy(id = "LinkDocument")
	WebElement linkDocument;
	
	@FindBy(id = "LinkName")
	WebElement linkName;
	
	//parent child Activity
	
	@FindBy(id = "btnConvertSubActivity")
	WebElement convertSubActivity;
	
	@FindBy(id = "btnAttachSubActivity")
	WebElement attachSubactivity;
	
	@FindBy(id = "btnaddSubActivity")
	WebElement addSubactivity;
	
	@FindBy(xpath = "//div[@id='ChildActivities']//table")
	WebElement childActivitiesTabel;
	
	@FindBy(xpath = "//div[@id='ChildActivities']//table//tbody//tr//td[1]")
	List<WebElement> subActivityNames;
	
	@FindBy(id = "SubActivitiesAction")
	public WebElement attachSubactivitySection;
	
	@FindBy(id = "RelavantSubActivitiesSection")
	WebElement subactivityDropdown;
	
	@FindBy(xpath = "//div[@id='RelavantSubActivitiesSection']//ul//li//label[@class='checkbox']")
	List<WebElement> subactivityOption;
	
	@FindBy(xpath = "//div[@id='RelavantSubActivitiesSection']//button")
	WebElement selectedSubactivity;

	@FindBy(id = "ConfirmActivityChange")
	WebElement confirmActivityChange;
	
	@FindBy(id = "detailsForm")
	WebElement activityDetailsPage;
	
	@FindBy(id = "CompleteValidation")
	WebElement completeValidation;
	
	@FindBy(id = "viewFinancials")
	WebElement viewFinancials;
	
	@FindBy(xpath = "//a[contains(text(),'View Financials')]")
	WebElement viewFinancial;
	
	@FindBy(id = "generateFinancials")
	WebElement generateFinancials;
	
	@FindBy(xpath = "//a[contains(text(),'COA Validations')]")
	WebElement coaValidation;
	
	@FindBy(id = "closeCMSFinancialReportStatusModal")
	WebElement closeFinancialReportStatusModal;
	
	@FindBy(xpath= "//div[@id='showCMSFinancialReportStatus']//div[@id='announcementModalContent']")
	WebElement financialReportStatusModal;
	
	@FindBy(xpath= "//div[@id='showCMSFinancialReportStatus']//div[@id='announcementModalContent']//span[@class='financialReportStatusMsg']")
	WebElement financialReportStatusMsg;
	
	@FindBy(id = "rejectTextBox")
	WebElement rejectNote;
	
	@FindBy(xpath = "//div[@class='popover-content']//button[text()='Reject']")
	WebElement reject;
	
	public ActivityDetailsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getPageTitle() {
		return pageTitle.getText().trim();
	}
	
	public boolean isPageTitleExist(String title) {
		if(isElementPresent(pageTitle, 60) && this.getPageTitle().equals(title)) {
			return true;
		}
		return false;
	}
	
	public String getActivityName() {
		return activityName.getText().trim();
	}
	
	public String getAssignToLevel() {
		return assignToLevel.getText().trim();
	}
	
	public void clickOnAssignToLevel() {
		assignToLevel.click();
	}
	
	public String getStatus() {
		return status.getText().trim();
	}
	
	public boolean isStartButtonExist() {
		return isElementPresent(start, 40);
	}
	
	public boolean isMarkAsCompleteButtonExist() {
		return isElementPresent(markAsComplete, 40);
	}
	
	public boolean isRecallButtonExist() {
		return isElementPresent(recall, 40);
	}
	
	public boolean isBackToActivityButtonExist() {
		return isElementPresent(backToActivities, 40);
	}
	
	public void clickOnBackToActivities() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", backToActivities);
	}
	
	public void clickOnStart() {
		start.click();
	}
	
	public void clickOnMarkAsComplete() {
		markAsComplete.click();
	}
	
	public void clickOnRecall() {
		recall.click();
	}
	
	public boolean checkPanelExist() {
		if(isElementPresent(documentsMainDiv, 30) && isElementPresent(notesMainDiv, 30) && isElementPresent(activityFlowTable, 30)) {
			return true;
		}
		return false;
	}
	
	public boolean isAssignedToReviewer(String name) {
		for(WebElement activityFlow:activityFlows) {
			if(activityFlow.getText().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void selectAssignee(String value) {
		Select select = new Select(assignedToDropdown);
		select.selectByVisibleText(value);
	}
	
	public void clickOnSaveAssignedTo() {
		saveAssignedTo.click();
	}
	
	public void clickOnAddNote() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", addNote);
	}
	
	public boolean isAddNotePopoverExist() {
		return isElementPresent(addNotePopover,40);
	}
	
	public void setNote(String value) {
		note.sendKeys(value);
	}
	
	public void clickOnAdd() {
		add.click();
	}
	
	public boolean isNoteDivExist() {
		return isElementPresent(noteDiv,40);
	}
	
	public boolean isNoteExist(String name) {
		if(isElementPresent(noteDiv,60)) {
			for(WebElement note:notes) {
				if(note.getText().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clickOnEdit() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", edit);
	}
	
	public String getEditNoteTextArea() {
		return editNoteTextArea.getText();
	}
	
	public void setEditNoteTextArea(String value) {
		if(Strings.isNullOrEmpty(this.getEditNoteTextArea())) {
			editNoteTextArea.clear();
		}
		editNoteTextArea.sendKeys(value);
	}
	
	public void clickOnUpdate() {
		update.click();
	}
	
	public String getUpdatedNote() {
		return updatedNote.getText();
	}
	
	public void clickOnUploadDocument() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", uploadDocument);
	}
	
	public String getUploadModalHeader() {
		return uploadModalHeader.getText().trim();
	}
	
	public boolean isUploadModalExist(String title) {
		if(isElementPresent(uploadModal, 60) && this.getUploadModalHeader().equals(title)) {
			return true;
		}
		return false;
	}
	
	public void setDocument(String fileName) {
		document.sendKeys(fileName);
	}
	
	public void clickOnUpload() {
		upload.click();
	}
	
	public void clickOnLink() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", link);
	}
	
	public void setLinkDocument(String link) {
		linkDocument.sendKeys(link);
	}
	
	public void setLinkName(String link) {
		linkName.sendKeys(link);
	}
	
	public boolean isDocumentExist(String name) {
		if(isElementPresent(documentTable,60)) {
			for(WebElement documentName:documentNames) {
				if(documentName.getText().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void clickOnDeleteDocument() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", deleteDocument);
	}
	
	public String getDocumentName() {
		return documentName.getText();
	}
	
	//parent child activity
	
	public boolean checkSubActivityButtonExist() {
		if(isElementPresent(convertSubActivity, 60) && isElementPresent(addSubactivity, 60)) {
			return true;
		}
		return false;
	}
	
	public void clickOnConvertSubActivity() {
		convertSubActivity.click();
	}
	
	public void clickOnAddSubactivity() {
		addSubactivity.click();
	}
	
	public void clickOnAttachSubactivity() {
		attachSubactivity.click();
	}
	
	public boolean isSubActivityAdded(String name) {
		if(isElementPresent(childActivitiesTabel, 60)) {
			for(WebElement subActivityName:subActivityNames) {
				if(subActivityName.getText().equals(name)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void clickOnSubactivityDropdown() {
		subactivityDropdown.click();
		
	}
	
	public void selectSubactivity() {
		for(int i=1;i<subactivityOption.size(); i++) {
			if(i==1||i==2||i==3) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", subactivityOption.get(i));
			} else {
				break;
			}
		}
	}
	
	public String getSelectedSubactivity() {
		return selectedSubactivity.getAttribute("title");
	}
	
	public boolean isSubactivityAttached(String subactivity) {
		String[] subactivities = subactivity.split(", ");
		List<String> selectedSubactivity = Arrays.asList(subactivities);
		String[]  subactivityInList= new String[subActivityNames.size()];
		for(int i=0; i<subActivityNames.size(); i++) {
			subactivityInList[i] = subActivityNames.get(i).getText();
		}
		
		if(Arrays.asList(subactivityInList).containsAll(selectedSubactivity)) {
			return true;
		}
		return false;
	}
	
	public void scrollToChildActivitiesTable() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", childActivitiesTabel);
	}
	
	public boolean isAttachSubActivityButtonExist() {
		return isElementPresent(attachSubactivity, 60);
	}
	
	public void clickOnConfirmActivityChange() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", confirmActivityChange);
	}
	
	public void clickOnCompleteValidation() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", completeValidation);
	}
	
	public void clickOnViewFinancials() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", viewFinancials);
	}
	
	public void clickOnCoaValidation() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", coaValidation);
	}
	
	public void clickOnGenerateFinancials() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", generateFinancials);
	}
	
	public boolean isCompleteValidationButtonExist() {
		return isElementPresent(completeValidation, 60);
	}
	
	public boolean isGenerateFinancialsButtonExist() {
		return isElementPresent(generateFinancials, 30);
	}
	
	public String getFinancialReportStatusMsg() {
		return financialReportStatusMsg.getText();
	}
	
	public boolean isFinancialReportStatusModalExist(String content) {
		if(isElementPresent(financialReportStatusModal, 30) && this.getFinancialReportStatusMsg().contains(content)) {
			return true;
		}
		return false;
	}
	
	public void clickOnCloseFinancialReportStatusModal() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", closeFinancialReportStatusModal);
	}
	
	public void mousehoverOnViewFinancial() {
		Actions actions = new Actions(driver);
		actions.moveToElement(viewFinancial).perform();
	}
	
	public boolean isViewFinancialNotVisible() {
		if(viewFinancial.getAttribute("data-tooltip-text").equals("Visible only to the owner of the activity.")) {
			return true;
		}
		return false;
	}
	
	public void setRejectNote(String note) {
		rejectNote.sendKeys(note);
	}
	
	public void clickOnReject() {
		reject.click();
	}
	
}
