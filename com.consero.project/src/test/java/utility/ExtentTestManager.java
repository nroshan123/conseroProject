package utility;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class ExtentTestManager {
	 static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	    private static ExtentReports extent = ExtentManager.getInstance();

	    public static synchronized ExtentTest getTest() {
	        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	    }

	    public static synchronized void endTest() {
	        extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
	    }

	    public static synchronized ExtentTest startTest(String testName, String desc) {
	        ExtentTest test = extent.startTest(testName, desc);
	        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

	        return test;
	    }
}
