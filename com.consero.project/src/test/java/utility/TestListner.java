package utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.relevantcodes.extentreports.LogStatus;

import basetest.BaseTest;

public class TestListner extends BaseTest implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result.getName()+" test case started");
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("The name of the testcase passed is :"+result.getName());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("The name of the testcase failed is :"+result.getName());
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("The name of the testcase Skipped is :"+result.getName());
		DataReader.setCellData("Skipped", 1, 10);
		 ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext iTestContext) {
		// TODO Auto-generated method stub
		   System.out.println("I am in onStart method " + iTestContext.getName());
		   iTestContext.setAttribute("WebDriver", this.driver);
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ExtentTestManager.endTest();
		  ExtentManager.getInstance().flush();
	}

}
